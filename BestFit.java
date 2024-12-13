import java.util.Scanner;

public class BestFit {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input memory block sizes
        System.out.print("Enter the number of memory blocks: ");
        int numBlocks = scanner.nextInt();
        int[] blockSizes = new int[numBlocks];
        System.out.println("Enter the sizes of the memory blocks: ");
        for (int i = 0; i < numBlocks; i++) {
            blockSizes[i] = scanner.nextInt();
        }

        // Input process sizes
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();
        int[] processSizes = new int[numProcesses];
        System.out.println("Enter the sizes of the processes: ");
        for (int i = 0; i < numProcesses; i++) {
            processSizes[i] = scanner.nextInt();
        }

        // Array to store block assignment for each process
        int[] allocation = new int[numProcesses];
        for (int i = 0; i < numProcesses; i++) {
            allocation[i] = -1; // -1 means not allocated
        }

        // Best Fit Allocation
        for (int i = 0; i < numProcesses; i++) {
            int bestIdx = -1;
            for (int j = 0; j < numBlocks; j++) {
                if (blockSizes[j] >= processSizes[i]) {
                    if (bestIdx == -1 || blockSizes[j] < blockSizes[bestIdx]) {
                        bestIdx = j;
                    }
                }
            }

            // Allocate the block if a suitable one is found
            if (bestIdx != -1) {
                allocation[i] = bestIdx;
                blockSizes[bestIdx] -= processSizes[i];
            }
        }

        // Output allocation results
        System.out.println("\nProcess No.\tProcess Size\tBlock No.");
        for (int i = 0; i < numProcesses; i++) {
            System.out.print((i + 1) + "\t\t" + processSizes[i] + "\t\t");
            if (allocation[i] != -1) {
                System.out.println(allocation[i] + 1);
            } else {
                System.out.println("Not Allocated");
            }
        }

        scanner.close();
    }
}
