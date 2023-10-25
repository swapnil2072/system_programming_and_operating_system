import java.util.ArrayList;
import java.util.Scanner;

class MemoryBlock {
    int id;
    int size;
    boolean allocated;
    int processId;

    MemoryBlock(int id, int size) {
        this.id = id;
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

        for (int i = 1; i <= numBlocks; i++) {
            System.out.print("Enter size of memory block " + i + ": ");
            int blockSize = scanner.nextInt();
            memory.add(new MemoryBlock(i, blockSize));
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
        System.out.println("Memory Allocation:");
        System.out.println("Memory Block\tProcess Allocated");

        for (MemoryBlock block : memory) {
            if (block.allocated) {
                System.out.println("Block " + block.id + "\t\tProcess " + block.processId);
            } else {
                System.out.println("Block " + block.id + "\t\tNot Allocated");
            }
        }

        scanner.close();
    }

    public static void allocateMemory(ArrayList<MemoryBlock> memory, ArrayList<Process> processes) {
        for (Process process : processes) {
            int bestFitBlockIdx = -1;
            int bestFitSize = Integer.MAX_VALUE;

            for (int i = 0; i < memory.size(); i++) {
                MemoryBlock block = memory.get(i);
                if (!block.allocated && block.size >= process.size && block.size < bestFitSize) {
                    bestFitBlockIdx = i;
                    bestFitSize = block.size;
                }
            }

            if (bestFitBlockIdx != -1) {
                MemoryBlock bestFitBlock = memory.get(bestFitBlockIdx);
                bestFitBlock.allocated = true;
                bestFitBlock.processId = process.id;
            }
        }
    }
}