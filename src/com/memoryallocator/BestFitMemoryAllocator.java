package src.com.memoryallocator;

import java.util.ArrayList;
import java.util.List;

public class BestFitMemoryAllocator {
    // List of memory blocks
    private List<MemoryBlock> blocks;

    // Counter to assign unique task IDs
    private int taskCounter;

    // Constructor: Initializes memory allocator with given block sizes
    public BestFitMemoryAllocator(List<Integer> blockSizes) {
        blocks = new ArrayList<>();
        taskCounter = 1;
        for (int size : blockSizes) {
            blocks.add(new MemoryBlock(size));
        }
    }

    // Allocates memory for a process using the Best Fit algorithm
    public int allocateMemory(int processSize) {
        MemoryBlock bestBlock = null;
        int bestBlockIndex = -1;
        int minDifference = Integer.MAX_VALUE;

        // Loop through the blocks to find the best fit
        for (int i = 0; i < blocks.size(); i++) {
            MemoryBlock block = blocks.get(i);
            if (block.canAccommodate(processSize)) {
                int difference = block.getSize() - processSize;
                if (difference < minDifference) {
                    bestBlock = block;
                    bestBlockIndex = i;
                    minDifference = difference;
                }
            }
        }

        // If no block is found that can fit the process, return -1
        if (bestBlock == null) {
            return -1;
        }

        // Assign the task to the best block
        bestBlock.assignTask(taskCounter);

        // If there's leftover space, create a smaller block for the remainder
        if (minDifference > 0) {
            MemoryBlock remainingBlock = new MemoryBlock(minDifference);
            blocks.add(bestBlockIndex + 1, remainingBlock);
            bestBlock.setSize(processSize);
        }

        // Return the task ID and increment the counter for the next task
        return taskCounter++;
    }

    // Deallocates memory for a specific task by task ID
    public boolean deallocateMemory(int taskId) {
        for (MemoryBlock block : blocks) {
            if (block.getAssignedTasks().contains(taskId)) {
                block.removeTask(taskId);
                return true;
            }
        }
        return false;
    }

    // Prints the status of all memory blocks
    public void printBlockStatus() {

        // ANSI escape codes for colors
        String purple = "\u001B[35m";
        String reset = "\u001B[0m";

        System.out.println(purple + "\nBlock \t Size \t Assigned Tasks" + reset);
        for (int i = 1; i <= blocks.size(); i++) {
            MemoryBlock block = blocks.get(i - 1);
            System.out.printf(purple + "%d \t %d \t %s%n" + reset,
                    i,
                    block.getSize(),
                    block.getAssignedTasks().isEmpty() ? "[]" : block.getAssignedTasks());
        }
    }

    // Returns the list of memory blocks
    public List<MemoryBlock> getBlocks() {
        return blocks;
    }
}