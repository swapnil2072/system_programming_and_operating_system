import java.util.Scanner;
import java.util.ArrayList;


class MemoryBlock{
	int blockNo;
	int size;
	int startAddress;
	boolean allocated;
	int processId;
	
	MemoryBlock(int blockNo,int startAddress,int size ){
		this.blockNo = blockNo;
		this.size = size;
		this.startAddress = startAddress;
		this.allocated = false;
		this.processId = -1;
	}
}

class Process{
	int id;
	int size;
	
	Process(int id , int size){
		this.id =id;
		this.size = size;
	}
}


public class WosrtFit {


		public static void main(String[] args) {
			Scanner in = new Scanner(System.in);
			
			System.out.println("Enter the number of Memory Blocks:");
			int numberOfBlocks = in.nextInt();
			ArrayList<MemoryBlock> memory = new ArrayList<>();
			
			int startAddress = 0 ;
			
			for(int i = 0 ; i < numberOfBlocks ; i++) {
				System.out.println("Enter the size of Block :" + (i+1));
				int size = in.nextInt();
				MemoryBlock block = new MemoryBlock(i+1,startAddress,size);
				memory.add(block);
				startAddress += size;	
			}
			
			
			System.out.println("Enter the number of Process:");
			int numberOfProcess = in.nextInt();
			
			ArrayList<Process> processes = new ArrayList<>();
			for(int i = 0 ; i < numberOfProcess ; i++) {
				System.out.println("Enter the size of Process :" + (i+1));
				int size = in.nextInt();
				Process process = new Process(i+1,size);
				processes.add(process);
			}
			
			allocateMemory(memory,processes);
			
			System.out.println("blockNo \t Start Address\tSize\t\tProcess Allocated");
			for(MemoryBlock block:memory) {
				if(block.allocated) {
					System.out.println(block.blockNo +"\t\t\t"+block.startAddress + "\t\t\t" + block.size + "\t\t\t\tProcess " + block.processId);
				}
				else {
					System.out.println(block.blockNo +"\t\t\t" +block.startAddress + "\t\t\t" + block.size + "\t\t\t\tNot Allocated");
				}
			}
		}
	
	
		
		private static void allocateMemory(ArrayList<MemoryBlock>memory,ArrayList<Process>processes) {
			
			for(Process process:processes) {
				int worstFitBlockIndex = -1;
				int worstFitsize = Integer.MIN_VALUE;
				
				for(int i = 0; i < memory.size() ; i++) {
					MemoryBlock block = memory.get(i);
					int maxValue = block.size;
					if(!block.allocated && maxValue >= worstFitsize) {
						worstFitsize = maxValue;
						worstFitBlockIndex = i;
					}
					}
					
				if(worstFitBlockIndex != -1 ) {
					
					MemoryBlock worstFitBlock = memory.get(worstFitBlockIndex);
					if(worstFitBlock.size >= process.size ){
					MemoryBlock allocatedBlock = new MemoryBlock(worstFitBlock.blockNo,worstFitBlock.startAddress,process.size);
					
					if(worstFitBlock.size > process.size) {
						MemoryBlock freeBlock = new MemoryBlock(worstFitBlock.blockNo,worstFitBlock.startAddress + process.size,worstFitBlock.size - process.size);
						memory.add(worstFitBlockIndex + 1, freeBlock);
					}
					
					memory.remove(worstFitBlockIndex);
					memory.add(worstFitBlockIndex,allocatedBlock);
					
					allocatedBlock.allocated = true;
					allocatedBlock.processId = process.id;
					}
					}
				}
				}
}
