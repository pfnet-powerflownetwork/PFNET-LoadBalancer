// File: LoadBalancer.java
package com.pfnet.loadbalancer;

import java.util.*;

public class LoadBalancer {

    private final NodeMonitor nodeMonitor;
    private final TaskAllocator taskAllocator;

    public LoadBalancer(NodeMonitor nodeMonitor, TaskAllocator taskAllocator) {
        this.nodeMonitor = nodeMonitor;
        this.taskAllocator = taskAllocator;
    }

    public void distributeTasks(List<Task> tasks) {
        Map<String, Node> nodes = nodeMonitor.getAvailableNodes();

        if (nodes.isEmpty()) {
            System.err.println("No nodes available for task allocation.");
            return;
        }

        for (Task task : tasks) {
            Node selectedNode = taskAllocator.allocateTask(nodes, task);
            if (selectedNode != null) {
                selectedNode.assignTask(task);
                System.out.printf("Task %s assigned to node %s.%n", task.getId(), selectedNode.getId());
            } else {
                System.err.printf("Task %s could not be allocated.%n", task.getId());
            }
        }
    }

    public static void main(String[] args) {
        NodeMonitor nodeMonitor = new NodeMonitor();
        TaskAllocator taskAllocator = new TaskAllocator();

        LoadBalancer loadBalancer = new LoadBalancer(nodeMonitor, taskAllocator);

        List<Task> tasks = List.of(
                new Task("Task1", 50),
                new Task("Task2", 120),
                new Task("Task3", 75)
        );

        loadBalancer.distributeTasks(tasks);
    }
}

class Task {
    private final String id;
    private final int requiredCapacity;

    public Task(String id, int requiredCapacity) {
        this.id = id;
        this.requiredCapacity = requiredCapacity;
    }

    public String getId() {
        return id;
    }

    public int getRequiredCapacity() {
        return requiredCapacity;
    }
}

class Node {
    private final String id;
    private int availableCapacity;

    public Node(String id, int availableCapacity) {
        this.id = id;
        this.availableCapacity = availableCapacity;
    }

    public String getId() {
        return id;
    }

    public int getAvailableCapacity() {
        return availableCapacity;
    }

    public void assignTask(Task task) {
        this.availableCapacity -= task.getRequiredCapacity();
    }
}
