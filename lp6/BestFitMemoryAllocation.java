//final code

import java.util.ArrayList;
import java.util.Scanner;

class MemoryBlock {
    int startAddress;
    int size;
    boolean allocated;
    int processId;

    MemoryBlock(int startAddress, int size) {
        this.startAddress = startAddress;
        this.size = size;
        this.allocated = false;
        this.processId = -1;
    }
}

class Process {
    int id;
    int size;

    Process(int id, int size) {
        this.id = id;
        this.size = size;
    }
}

public class BestFitMemoryAllocation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of memory blocks: ");
        int numBlocks = scanner.nextInt();

        ArrayList<MemoryBlock> memory = new ArrayList<>();

        int startAddress = 0;

        for (int i = 1; i <= numBlocks; i++) {
            System.out.print("Enter size of memory block " + i + ": ");
            int blockSize = scanner.nextInt();
            memory.add(new MemoryBlock(startAddress, blockSize));
            startAddress += blockSize; // Move the start address for the next block
        }

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        ArrayList<Process> processes = new ArrayList<>();

        for (int i = 1; i <= numProcesses; i++) {
            System.out.print("Enter size of process " + i + ": ");
            int processSize = scanner.nextInt();
            processes.add(new Process(i, processSize));
        }

        allocateMemory(memory, processes);

        // Display memory allocation results
        System.out.println("\nMemory Allocation:");
        System.out.println("Start Address\tSize\t\tProcess Allocated");

        for (MemoryBlock block : memory) {
            if (block.allocated) {
                System.out.println(block.startAddress + "\t\t\t" + block.size + "\t\t\t\tProcess " + block.processId);
            } else {
                System.out.println(block.startAddress + "\t\t\t" + block.size + "\t\t\t\tNot Allocated");
            }
            System.out.println("------------------------------------------------------------------------------");
        }

        scanner.close();
    }

    public static void allocateMemory(ArrayList<MemoryBlock> memory, ArrayList<Process> processes) {
        for (Process process : processes) {
            int bestFitBlockIdx = -1;
            int bestFitSize = Integer.MAX_VALUE;

            for (int i = 0; i < memory.size(); i++) {
                MemoryBlock block = memory.get(i);
                int blockSizeDifference = block.size - process.size;

                // Check if the block is not allocated, and the size is sufficient for the process
                if (!block.allocated && blockSizeDifference >= 0 && blockSizeDifference < bestFitSize) {
                    bestFitBlockIdx = i;
                    bestFitSize = blockSizeDifference;
                }
            }

            if (bestFitBlockIdx != -1) {
                MemoryBlock bestFitBlock = memory.get(bestFitBlockIdx);

                // Split the block into an allocated part and a free part
                MemoryBlock allocatedBlock = new MemoryBlock(bestFitBlock.startAddress, process.size);
                MemoryBlock freeBlock = new MemoryBlock(bestFitBlock.startAddress + process.size, bestFitBlock.size - process.size);

                // Update the memory list
                memory.remove(bestFitBlockIdx);
                memory.add(bestFitBlockIdx, allocatedBlock);

                // Only add the free block if it has a positive size
                if (freeBlock.size > 0) {
                    memory.add(bestFitBlockIdx + 1, freeBlock);
                }

                allocatedBlock.allocated = true;
                allocatedBlock.processId = process.id;
            }
        }
    }
}
