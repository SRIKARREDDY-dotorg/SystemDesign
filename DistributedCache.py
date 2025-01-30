import hashlib
import bisect 

class ConsistentHashRing:
    def __init__(self, nodes=None, num_replicas=3):
        """
        Implements consistent hashing to distribute keys among nodes.
        :param nodes: List of nodes in the system.
        :param num_replicas: Number of virtual nodes per actual node.
        """
        self.ring = {}  # Stores the mapping of hash keys to node addresses.
        self.sorted_keys = []  # Keeps hash keys sorted for quick lookup.
        self.num_replicas = num_replicas  # Number of replicas per node.
        self.nodes = nodes or []
        
        for node in self.nodes:
            self.add_node(node)

    def _hash(self, key):
        """
        Generates a hash for a given key using MD5.
        :param key: The input key to be hashed.
        :return: A 128-bit integer hash.
        """
        return int(hashlib.md5(key.encode()).hexdigest(), 16)

    def add_node(self, node):
        """
        Adds a node to the hash ring along with its replicas.
        :param node: The node identifier.
        """
        for i in range(self.num_replicas):
            key = self._hash(f"{node}:{i}")
            self.ring[key] = node
            bisect.insort(self.sorted_keys, key)  # Insert key while maintaining order better due to binary search.

    def remove_node(self, node):
        """
        Removes a node and all its replicas from the hash ring.
        :param node: The node identifier to be removed.
        """
        for i in range(self.num_replicas):
            key = self._hash(f"{node}:{i}")
            if key in self.ring:
                self.ring.pop(key)
                self.sorted_keys.remove(key)

    def get_node(self, key):
        """
        Finds the appropriate node for the given key.
        :param key: The key to be assigned to a node.
        :return: The node responsible for storing the key.
        """
        if not self.ring:
            return None
        hashed_key = self._hash(key)
        idx = bisect.bisect(self.sorted_keys, hashed_key) % len(self.sorted_keys)
        return self.ring[self.sorted_keys[idx]]

class DistributedCache:
    def __init__(self, nodes):
        """
        Implements a distributed caching system using consistent hashing.
        :param nodes: List of node identifiers.
        """
        self.hash_ring = ConsistentHashRing(nodes)
        self.node_data = {node: {} for node in nodes}  # In-memory storage per node
    
    def set(self, key, value):
        """
        Stores a key-value pair in the appropriate node and prints the assigned node.
        :param key: The key to store.
        :param value: The value to store.
        """
        node = self.hash_ring.get_node(key)
        if node:
            self.node_data[node][key] = value
            print(f"Key '{key}' stored in {node}")
    
    def get(self, key):
        """
        Retrieves the value associated with a key from the correct node and prints the accessed node.
        :param key: The key to retrieve.
        :return: The stored value or None if key not found.
        """
        node = self.hash_ring.get_node(key)
        if node:
            print(f"Key '{key}' retrieved from {node}")
            return self.node_data[node].get(key, None)
        return None
    
    def delete(self, key):
        """
        Deletes a key from the appropriate node and prints the affected node.
        :param key: The key to delete.
        """
        node = self.hash_ring.get_node(key)
        if node and key in self.node_data[node]:
            del self.node_data[node][key]
            print(f"Key '{key}' deleted from {node}")

# Example usage
if __name__ == "__main__":
    nodes = ["Node1", "Node2", "Node3"]
    cache = DistributedCache(nodes)
    
    cache.set("user:123", "John Doe")  # Store value in cache.
    cache.set("user:124", "Srikar")  # Store value in cache.
    print(cache.get("user:123"))  # Fetches from the correct node.
    cache.delete("user:123")  # Deletes key from cache.
    cache.set("user:134", "Reddy")  # Store value in cache.
    print(cache.get("user:123"))  # Should return None
    print(cache.get("user:124"))
    print(cache.get("user:134"))
