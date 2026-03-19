import { HashRing } from "./HashRing";

// ----------Helpers ---------------------------

function printDistribution(label: string, dist: Record<string, number>) {
    console.log(`\n----- ${label} -----`);
    Object.entries(dist).forEach(([node, count]) => {
        const bar = "█".repeat(Math.floor(count/10));
        console.log(`  ${node}: ${count} keys ${bar}`);
    });
}

function compareSnapshots(
    label: string,
    before: Record<string, string>,
    after: Record<string, string>
) {
    const keys = Object.keys(before);
    let remapped = 0;
    let stayed = 0;

    for (const key of keys) {
        if (before[key] !== after[key]) {
            remapped++;
        } else {
            stayed++;
        }
    }

    const remappedPct = ((remapped / keys.length) * 100).toFixed(1);
    const stayedPct = ((stayed / keys.length) * 100).toFixed(1);
    
    console.log(`\n--- Impact of ${label}`);
    console.log(`   Total keys   : ${keys.length}`);
    console.log(`   Stayed       : ${stayed} (${stayedPct}%) ✅`);
    console.log(`   Remapped     : ${remapped} (${remappedPct}%) 🔄`);
    console.log(`   Ideal remap  : ~${(100 / (Object.keys(after).length + 1)).toFixed(1)}% (1/N of total)`);
}

// ---------- Setup ------------------------------------
const TOTAL_KEYS = 1000;
const testKeys = Array.from({ length: TOTAL_KEYS }, (_, i) => `key-${i}`);

const ring = new HashRing(100);

// Add nodes (simulating servers)
ring.addNode("Server-A");
ring.addNode("Server-B");
ring.addNode("Server-C");

// --------- Baseline ---------------------------

console.log("\n ==============================================");
console.log("    Baseline: 3 Nodes");
console.log("============================================");

const baseline = ring.snapshotKeys(testKeys);

const baselineDist: Record<string, number> = {};
Object.values(baseline).forEach((node) => {
    baselineDist[node] = (baselineDist[node] || 0) + 1;
});

printDistribution("Key Distribution (3 nodes)", baselineDist);

// --------  Add a Node ------------------------------------------------

console.log("\n =========================================");
console.log("     Event: Adding Server-D");
console.log("===========================================");

ring.addNode("Server-D");

const afterAdd = ring.snapshotKeys(testKeys);
compareSnapshots("Adding Server-D", baseline, afterAdd);

const afterAddDist: Record<string, number> = {};
Object.values(afterAdd).forEach((node) => {
    afterAddDist[node] = (afterAddDist[node] || 0) + 1;
});
printDistribution("Key Distribution (4 nodes)", afterAddDist);

// --------  Remove a Node ---------------------------------------------


console.log("\n======================================");
console.log("     Event: Removing Server-B (simulating crash)");

ring.removeNode("Server-B");

const afterRemove = ring.snapshotKeys(testKeys);
compareSnapshots("Removing Server-B", afterAdd, afterRemove);

const afterRemoveDist: Record<string, number> = {};
Object.values(afterRemove).forEach((node) => {
    afterRemoveDist[node] = (afterRemoveDist[node] || 0) + 1;
});
printDistribution("Key Distribution (3 nodes, B removed)", afterRemoveDist);
