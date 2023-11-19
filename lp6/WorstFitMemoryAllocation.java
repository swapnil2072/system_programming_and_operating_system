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

public class WorstFitMemoryAllocation {
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
            MemoryBlock worstFitBlock = null;
            int maxRemainingSpace = -1;

            for (MemoryBlock block : memory) {
                if (!block.allocated && block.size >= process.size) {
                    int remainingSpace = block.size - process.size;
                    if (worstFitBlock == null || remainingSpace > maxRemainingSpace) {
                        worstFitBlock = block;
                        maxRemainingSpace = remainingSpace;
                    }
                }
            }

            if (worstFitBlock != null) {
                worstFitBlock.allocated = true;
                worstFitBlock.processId = process.id;

                // Split the block into an allocated part and a free part
                if (worstFitBlock.size > process.size) {
                    MemoryBlock freeBlock = new MemoryBlock(worstFitBlock.startAddress + process.size, worstFitBlock.size - process.size);
                    memory.add(memory.indexOf(worstFitBlock) + 1, freeBlock); // Add the free block after the allocated block
                    worstFitBlock.size = process.size; // Adjust the size of the allocated block
                }
            } else {
                System.out.println("Process " + process.id + " cannot be allocated.");
            }
        }
    }
}
