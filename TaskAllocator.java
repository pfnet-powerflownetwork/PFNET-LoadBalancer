// File: TaskAllocator.java
package com.pfnet.loadbalancer;

import java.util.*;

public class TaskAllocator {

    /**
     * Allocates a task to the most suitable node based on its available capacity.
     *
     * @param nodes A map of node IDs to Node objects.
     * @param task  The task to be allocated.
     * @return The selected Node for the task, or null if no suitable node is found.
     */
    public Node allocateTask(Map<String, Node> nodes, Task task) {
        Node selectedNode = null;
        int maxCapacity = 0;

        for (Node node : nodes.values()) {
            if (node.getAvailableCapacity() >= task.getRequiredCapacity()) {
                if (node.getAvailableCapacity() > maxCapacity) {
                    selectedNode = node;
                    maxCapacity = node.getAvailableCapacity();
                }
            }
        }

        if (selectedNode != null) {
            System.out.printf("Task %s allocated to node %s with capacity %d.%n", 
                task.getId(), selectedNode.getId(), selectedNode.getAvailableCapacity());
        } else {
            System.err.printf("Task %s could not be allocated. Insufficient capacity.%n", task.getId());
        }

        return selectedNode;
    }

    /**
     * Alternative allocation strategy: Round-robin allocation.
     *
     * @param nodes A list of Node objects.
     * @param task  The task to be allocated.
     * @param lastAllocatedIndex The index of the last allocated node.
     * @return The next Node for the task, or null if no suitable node is found.
     */
    public Node allocateTaskRoundRobin(List<Node> nodes, Task task, int lastAllocatedIndex) {
        int size = nodes.size();
        if (size == 0) {
            System.err.println("No nodes available for allocation.");
            return null;
        }

        for (int i = 0; i < size; i++) {
            int index = (lastAllocatedIndex + 1 + i) % size;
            Node node = nodes.get(index);
            if (node.getAvailableCapacity() >= task.getRequiredCapacity()) {
                System.out.printf("Task %s allocated to node %s (Round-robin).%n", task.getId(), node.getId());
                return node;
            }
        }

        System.err.printf("Task %s could not be allocated (Round-robin). Insufficient capacity.%n", task.getId());
        return null;
    }

    public static void main(String[] args) {
        Map<String, Node> nodes = new HashMap<>();
        nodes.put("Node1", new Node("Node1", 100));
        nodes.put("Node2", new Node("Node2", 50));
        nodes.put("Node3", new Node("Node3", 200));

        TaskAllocator allocator = new TaskAllocator();

        Task task1 = new Task("Task1", 75);
        allocator.allocateTask(nodes, task1);

        List<Node> nodeList = new ArrayList<>(nodes.values());
        Task task2 = new Task("Task2", 60);
        allocator.allocateTaskRoundRobin(nodeList, task2, 1);
    }
}
