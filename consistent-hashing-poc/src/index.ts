import { HashRing } from "./HashRing";
import { analyzeDistribution, printStats } from "./stats";

const TOTAL_KEYS = 1000;
const testKeys = Array.from({ length: TOTAL_KEYS }, (_, i) => `key-${i}`);
const NODES = ["Server-A", "Server-B", "Server-C", "Server-D", "Server-E"];

// --------- Part 1 : Compare virtual node counts -----------------

console.log("\n");
console.log("╔══════════════════════════════════════════════════╗");
console.log("║   PART 1: Impact of Virtual Node Count           ║");
console.log("╚══════════════════════════════════════════════════╝");

const vnodeCounts = [1, 5, 25, 100, 500];

const summaryRows: Array<{
  vnodes: number;
  skew: string;
  stdDev: string;
}> = [];


for (const vnodes of vnodeCounts) {
  const ring = new HashRing(vnodes);
  NODES.forEach((n) => ring.addNode(n));
  const snapshot = ring.snapshotKeys(testKeys);
  const stats = analyzeDistribution(snapshot, NODES.length);
  printStats(`${vnodes} virtual nodes × ${NODES.length} servers`, stats);
  summaryRows.push({
    vnodes,
    skew: `${stats.skewPercent.toFixed(1)}%`,
    stdDev: `±${stats.standardDeviation.toFixed(1)}`,
  });
}

// Summary table
console.log("\n");
console.log("─".repeat(40));
console.log("  Virtual Nodes  |  Skew   |  Std Dev");
console.log("─".repeat(40));
summaryRows.forEach(({ vnodes, skew, stdDev }) => {
  console.log(
    `  ${String(vnodes).padEnd(15)}|  ${skew.padEnd(7)}|  ${stdDev}`
  );
});
console.log("─".repeat(40));


// ------------------ Part 2: Remap Analysis across scaling events -------------------

console.log("\n");
console.log("╔══════════════════════════════════════════════════╗");
console.log("║   PART 2: Remap % During Scaling Events          ║");
console.log("╚══════════════════════════════════════════════════╝");

const ring = new HashRing(100);
ring.addNode("Server-A");
ring.addNode("Server-B");
ring.addNode("Server-C");

let previousSnapshot = ring.snapshotKeys(testKeys);

const scalingEvents = [
  { type: "ADD", node: "Server-D" },
  { type: "ADD", node: "Server-E" },
  { type: "REMOVE", node: "Server-B" },
  { type: "REMOVE", node: "Server-D" },
];


for (const event of scalingEvents) {
  if (event.type === "ADD") {
    ring.addNode(event.node);
  } else {
    ring.removeNode(event.node);
  }

  const currentSnapshot = ring.snapshotKeys(testKeys);

  let remapped = 0;
  for (const key of testKeys) {
    if (previousSnapshot[key] !== currentSnapshot[key]) remapped++;
  }

  const remappedPct = ((remapped / TOTAL_KEYS) * 100).toFixed(1);
  const stayedPct = (100 - parseFloat(remappedPct)).toFixed(1);

  console.log(`\n  Event: ${event.type} ${event.node}`);
  console.log(`  ${"─".repeat(35)}`);
  console.log(`  Stayed    : ${stayedPct}%  ✅`);
  console.log(`  Remapped  : ${remappedPct}%  🔄`);

  previousSnapshot = currentSnapshot;
}