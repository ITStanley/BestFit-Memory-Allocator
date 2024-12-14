package src.com.memoryallocator;

import java.util.HashSet;
import java.util.Set;

public class MemoryBlock {
    private int size;                       // Size of the memory block
    private Set<Integer> assignedTasks;     // Tasks currently assigned to this memory block

    // Constructor: Creates a memory block with the given size
    public MemoryBlock(int size) {
        this.size = size;
        this.assignedTasks = new HashSet<>();
    }

    // Returns the size of the memory block
    public int getSize() {
        return size;
    }

    // Updates the size of the memory block
    public void setSize(int size) {
        this.size = size;
    }

    // Returns the set of task IDs assigned to this block
    public Set<Integer> getAssignedTasks() {
        return assignedTasks;
    }

    // Adds a task to this memory block
    public void assignTask(int taskId) {
        assignedTasks.add(taskId);
    }

    // Removes a task from this memory block
    public void removeTask(int taskId) {
        assignedTasks.remove(taskId);
    }

    // Checks if the block has enough space for a process
    public boolean canAccommodate(int processSize) {
        return size >= processSize;
    }
}
