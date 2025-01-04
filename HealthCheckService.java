// File: HealthCheckService.java
package com.pfnet.loadbalancer;

import java.util.*;

/**
 * HealthCheckService performs periodic health checks on nodes in the PFNET network.
 */
public class HealthCheckService {

    private final Map<String, Node> nodes;
    private final int checkInterval;
    private final List<String> unhealthyNodes;

    public HealthCheckService(Map<String, Node> nodes, int checkInterval) {
        this.nodes = nodes;
        this.checkInterval = checkInterval;
        this.unhealthyNodes = new ArrayList<>();
    }

    /**
     * Performs a health check on all nodes.
     */
    public void performHealthChecks() {
        for (Map.Entry<String, Node> entry : nodes.entrySet()) {
            String nodeId = entry.getKey();
            Node node = entry.getValue();

            if (isNodeHealthy(node)) {
                System.out.printf("Node %s is healthy.%n", nodeId);
                unhealthyNodes.remove(nodeId);
            } else {
                System.err.printf("Node %s is unhealthy.%n", nodeId);
                if (!unhealthyNodes.contains(nodeId)) {
                    unhealthyNodes.add(nodeId);
                }
            }
        }
    }

    /**
     * Checks whether a node is healthy.
     * 
     * @param node The node to check.
     * @return True if the node is healthy; false otherwise.
     */
    private boolean isNodeHealthy(Node node) {
        // Simulate a health check by validating node capacity and response time.
        return node.getAvailableCapacity() > 0 && node.getLastHeartbeat() <= checkInterval;
    }

    /**
     * Returns a list of unhealthy nodes.
     * 
     * @return List of node IDs that are unhealthy.
     */
    public List<String> getUnhealthyNodes() {
        return new ArrayList<>(unhealthyNodes);
    }

    public static void main(String[] args) {
        // Example usage
        Map<String, Node> nodes = new HashMap<>();
        nodes.put("Node1", new Node("Node1", 100));
        nodes.put("Node2", new Node("Node2", 0)); // Simulate unhealthy node
        nodes.put("Node3", new Node("Node3", 50));

        // Set heartbeat times to simulate node states
        nodes.get("Node1").setLastHeartbeat(2000);
        nodes.get("Node2").setLastHeartbeat(6000);
        nodes.get("Node3").setLastHeartbeat(3000);

        HealthCheckService healthCheckService = new HealthCheckService(nodes, 5000);
        healthCheckService.performHealthChecks();

        List<String> unhealthy = healthCheckService.getUnhealthyNodes();
        System.out.println("Unhealthy nodes: " + unhealthy);
    }
}
