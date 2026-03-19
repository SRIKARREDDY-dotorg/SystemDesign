import { HashRing } from "./HashRing";

const ring = new HashRing(50);

// Add nodes (simulating servers)
ring.addNode("Server-A");
ring.addNode("Server-B");
ring.addNode("Server-C");

// Check how evenly the ring slots are distributed.
console.log("\n------- Virtual Node Distribution (ring slots) -------");
const distribution = ring.getDistribution();
Object.entries(distribution).forEach(([node, count]) => {
    const bar = "█".repeat(Math.floor(count/3));
    console.log(`    ${node}: ${count} slots ${bar}`);
})

// simulate routing 1000 keys and count how many land on each server.
console.log("\n-------- Key routing distribution (1000 keys) -----");
const keyCounts: Record<string, number> = {};

for (let i = 0; i < 1000; i ++) {
    const node = ring.getNode(`key-${i}`);
    keyCounts[node] = (keyCounts[node] || 0) + 1;
}

Object.entries(keyCounts).forEach(([node, count]) => {
    const bar = "█".repeat(Math.floor(count/10));
    console.log(`   ${node}: ${count} keys ${bar}`);
});
