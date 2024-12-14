package src.com.memoryallocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemoryAllocationApp {
    public static void main(String[] args) {
        String reset = "\u001B[0m";
        String red = "\u001B[31m";
        String green = "\u001B[32m";
        String yellow = "\u001B[33m";
        String blue = "\u001B[34m";

        try (Scanner scanner = new Scanner(System.in)) {
            // Ask user for the number of memory blocks
            System.out.print("\n\nEnter the number of memory blocks: ");
            int numBlocks = scanner.nextInt();

            // Ask user to input the size for each block
            List<Integer> blockSizes = new ArrayList<>();
            for (int i = 1; i <= numBlocks; i++) {
                System.out.print("Enter size for block " + i + ": ");
                blockSizes.add(scanner.nextInt());
            }

            // Create a memory allocator with the given block sizes
            BestFitMemoryAllocator allocator = new BestFitMemoryAllocator(blockSizes);

            // Main loop to interact with the user
            while (true) {
                System.out.println(green + "\nChoose an option:");
                System.out.println("1. Allocate a process");
                System.out.println("2. Deallocate a process");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: " + reset);
                String option = scanner.next();

                switch (option) {
                    case "1":
                        System.out.println(blue + "\n***PROCESS ALLOCATION***" + reset);

                        // Ask for the size of the process to allocate
                        System.out.print("\nEnter process size to allocate: ");
                        int processSize = scanner.nextInt();

                        // Try to allocate memory for the process
                        int taskId = allocator.allocateMemory(processSize);
                        if (taskId != -1) {
                            System.out.println(yellow + "Task " + taskId + " allocated to Block " +
                            // Find the block that got the task
                                    (allocator.getBlocks().indexOf(allocator.getBlocks().stream()
                                            .filter(b -> b.getAssignedTasks().contains(taskId))
                                            .findFirst()
                                            .orElse(null)) + 1) + reset);
                            // Print the status of the blocks
                            allocator.printBlockStatus();
                        }else {
                            System.out.println(red + "\nNo sufficient size of a block found!" + processSize + reset);
                        }break;

                    case "2":
                        System.out.println(blue + "\n***PROCESS DEALLOCATION***" + reset);

                        // Ask for the task ID to deallocate
                        System.out.print("\nEnter task index to deallocate: ");
                        int taskToRemove = scanner.nextInt();

                        // Try to deallocate memory for the task
                        if (allocator.deallocateMemory(taskToRemove)) {
                            System.out.println(yellow + "Task " + taskToRemove + " deallocated." + reset);
                            // Print the updated block status
                            allocator.printBlockStatus();
                        }else {
                            System.out.println(red + "\nInvalid process number!" + reset);
                        }break;

                    case "3":
                        System.out.println(blue + "\n***EXIT***" + reset);

                        // Print the current block status
                        allocator.printBlockStatus();

                        // Exit the program
                        System.exit(0);

                    default:
                        // Handle invalid option
                        System.out.println(red + "Invalid option." + reset);
                }
            }
        }
    }
}
