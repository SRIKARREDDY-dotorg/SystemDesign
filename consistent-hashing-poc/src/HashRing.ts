import { hash } from "./hash";


interface RingEntry {
    position: number;
    node: string;
}

export class HashRing {
    private ring: RingEntry[] = [];
    private readonly virtualNodes: number;

    constructor(virtualNodes: number = 100) {
        this.virtualNodes = virtualNodes;
    }

    // add a node to the ring.
    addNode(node: string): void {
        // place the node N times on the ring with different virtual labels
        for (let i = 0; i < this.virtualNodes; i ++) {
            const virtualKey = `${node}:vnode-${i}`;
            const position = hash(virtualKey);
            this.ring.push({position, node});
        }
        this.ring.sort((a, b) => a.position - b.position);
        console.log(`Added "${node}" with ${this.virtualNodes} virtual nodes on the ring`);
    }

    removeNode(node: string): void {
        // Remove all virtual nodes on the ring.
        const before = this.ring.length;
        this.ring = this.ring.filter((entry) => entry.node !== node);
        const removed = before - this.ring.length;
        console.log(`Removed "${node}" (${removed} virtual nodes cleared)`);
    }

    // Find which node owns this key
    getNode(key: string): string {
        if (this.ring.length === 0) throw new Error("Ring is empty");

        const keyPosition = hash(key);

        // Walk the ring clockwise to find the first node >= keyPosition
        for(const entry of this.ring) {
            if (keyPosition <= entry.position) {
                return entry.node;
            }
        }

        // wrap around - key is past the last node, so it goes to the first.
        return this.ring[0]!.node;
    }

    getDistribution(): Record<string, number> {
        const distribution: Record<string, number> = {};
        for (const entry of this.ring) {
            distribution[entry.node] = (distribution[entry.node] || 0) + 1;
        }
        return distribution;
    }

    // snapshot: returns a map of key -> node for all given keys
    snapshotKeys(keys: string[]): Record<string, string> {
        const snapshot: Record<string, string> = {};

        for (const key of keys) {
            snapshot[key] = this.getNode(key);
        }

        return snapshot;
    }

    printRing(): void {
        console.log("\n--- Hash Ring ---");
        this.ring.forEach(({ node, position }) => {
            console.log(`   [${position.toString().padStart(10)}] -> ${node}`);
        });
        console.log("------------------\n");
    }
};