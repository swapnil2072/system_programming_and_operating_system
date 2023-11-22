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


public class NextFit {


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
			
			int lastAllocated = 0 ;
			for(Process process : processes) {
				
				boolean allocated = false;
				
				for(int i = lastAllocated ; i < memory.size(); i++) {
					MemoryBlock block = memory.get(i);
					if(!block.allocated && block.size >= process.size) {
						MemoryBlock allocatedBlock = new MemoryBlock(block.blockNo,block.startAddress,process.size);
						memory.set(i, allocatedBlock);
						allocatedBlock.allocated = true;
						allocatedBlock.processId = process.id;
						
						allocated = true;
						lastAllocated = i+1;
						break;
					}
				}
				if(!allocated) {
					for(int i = 0 ; i < lastAllocated; i++) {
						MemoryBlock block = memory.get(i);
						if(!block.allocated && block.size >= process.size) {
							MemoryBlock allocatedBlock = new MemoryBlock(block.blockNo,block.startAddress,process.size);
							if(block.size > process.size) {
								MemoryBlock freeBlock = new MemoryBlock(block.blockNo,block.startAddress + process.size,block.size - process.size);
								memory.add(i+1,freeBlock);
							}
							memory.set(i, allocatedBlock);
							allocatedBlock.allocated = true;
							allocatedBlock.processId = process.id;
							
							allocated = true;
							lastAllocated = i+1;
							break;
						}
					}
				}
				if(!allocated) {
					System.out.println("Process" + process.id + "cannot be allocated.");
				}
			}
			
		}
		}
