export interface RingStats {
    totalKeys: number;
    nodeCount: number;
    distribution: Record<string, number>;
    idealKeysPerNode: number;
    maxKeys: number;
    minKeys: number;
    maxNode: string;
    minNode: string;
    standardDeviation: number;
    skewPercent: number;
}

export function analyzeDistribution(
    snapshot: Record<string, string>,
    nodeCount: number
): RingStats {
    const distribution: Record<string, number> = {};

    for(const node of Object.values(snapshot)) {
        distribution[node] = (distribution[node] || 0) + 1;
    }

    const totalKeys = Object.keys(snapshot).length;
    const counts = Object.values(distribution);
    const idealKeysPerNode = totalKeys / nodeCount;

    const maxKeys = Math.max(...counts);
    const minKeys = Math.min(...counts);
    const maxNode = Object.keys(distribution).find(
        (n) => distribution[n] === maxKeys
    )!;
    const minNode = Object.keys(distribution).find(
        (n) => distribution[n] === minKeys
    )!;

    // standard deviation - how far each node deviated from ideal
    const variance = counts.reduce((sum, c) => sum + Math.pow(c - idealKeysPerNode, 2), 0) / counts.length;

    const standardDeviation = Math.sqrt(variance);

    const skewPercent = ((maxKeys - idealKeysPerNode)/ idealKeysPerNode) * 100;

    return {
        totalKeys,
        nodeCount,
        distribution,
        idealKeysPerNode,
        maxKeys,
        minKeys,
        maxNode,
        minNode,
        standardDeviation,
        skewPercent
    };
}

export function printStats(label: string, stats: RingStats): void {
    console.log(`\n${"=".repeat(50)}`);
    console.log(`    ${label}`);
    console.log(`${"=".repeat(50)}`);
    console.log(` Nodes       :  ${stats.nodeCount}`);
    console.log(` Total Keys  :  ${stats.totalKeys}`);
    console.log(` Ideal/Node  :   ${stats.idealKeysPerNode.toFixed(1)} keys`);
    console.log(` Std Deviation  : ±${stats.standardDeviation.toFixed(1)} keys`);
    console.log(`  Skew           : ${stats.skewPercent.toFixed(1)}%`);
    console.log(
    `  Busiest Node   : ${stats.maxNode} (${stats.maxKeys} keys) 🔴`
  );
  console.log(
    `  Lightest Node  : ${stats.minNode} (${stats.minKeys} keys) 🟢`
  );

  console.log(`\n  Distribution:`);
  Object.entries(stats.distribution)
    .sort((a, b) => b[1] - a[1])
    .forEach(([node, count]) => {
      const deviation = count - stats.idealKeysPerNode;
      const sign = deviation >= 0 ? "+" : "";
      const bar = "█".repeat(Math.floor(count / 10));
      console.log(
        `    ${node.padEnd(12)}: ${String(count).padStart(4)} keys  (${sign}${deviation.toFixed(0)})  ${bar}`
      );
    });
}