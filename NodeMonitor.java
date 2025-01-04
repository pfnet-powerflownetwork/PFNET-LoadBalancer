// File: NodeMonitor.java
package com.pfnet.loadbalancer;

import java.util.*;

public class NodeMonitor {

    private final Map<String, Node> nodes = new HashMap<>();

    public NodeMonitor() {
        // Initialize with some example nodes
        nodes.put("Node1", new Node("Node1", 100));
        nodes.put("Node2", new Node("Node2", 150));
        nodes.put("Node3", new Node("Node3", 200));
    }

    /**
     * Retrieves all available nodes.
     *
     * @return A map of node IDs to Node objects.
     */
    public Map<String, Node> getAvailableNodes() {
        Map<String, Node> availableNodes = new HashMap<>();
        for (Map.Entry<String, Node> entry : nodes.entrySet()) {
            if (entry.getValue().getAvailableCapacity() > 0) {
                availableNodes.put(entry.getKey(), entry.getValue());
            }
        }
        return availableNodes;
    }

    /**
     * Updates the capacity of a specific node.
     *
     * @param nodeId           The ID of the node to update.
     * @param newCapacity      The new capacity of the node.
     */
    public void updateNodeCapacity(String nodeId, int newCapacity) {
        Node node = nodes.get(nodeId);
        if (node != null) {
            node.assignTask(new Task("Capacity Update", node.getAvailableCapacity() - newCapacity));
            System.out.printf("Node %s capacity updated to %d.%n", nodeId, newCapacity);
        } else {
            System.err.printf("Node %s not found.%n", nodeId);
        }
    }

    /**
     * Adds a new node to the monitor.
     *
     * @param nodeId           The ID of the new node.
     * @param capacity         The capacity of the new node.
     */
    public void addNode(String nodeId, int capacity) {
        if (!nodes.containsKey(nodeId)) {
            nodes.put(nodeId, new Node(nodeId, capacity));
            System.out.printf("Node %s added with capacity %d.%n", nodeId, capacity);
        } else {
            System.err.printf("Node %s already exists.%n", nodeId);
        }
    }

    /**
     * Removes a node from the monitor.
     *
     * @param nodeId The ID of the node to remove.
     */
    public void removeNode(String nodeId) {
        if (nodes.remove(nodeId) != null) {
            System.out.printf("Node %s removed.%n", nodeId);
        } else {
            System.err.printf("Node %s not found.%n", nodeId);
        }
    }

    public static void main(String[] args) {
        NodeMonitor monitor = new NodeMonitor();

        System.out.println("Available nodes:");
        monitor.getAvailableNodes().forEach((id, node) ->
                System.out.printf("Node ID: %s, Capacity: %d%n", id, node.getAvailableCapacity()));

        monitor.updateNodeCapacity("Node1", 50);
        monitor.addNode("Node4", 250);
        monitor.removeNode("Node2");
    }
}
