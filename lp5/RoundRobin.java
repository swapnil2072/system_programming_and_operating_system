//final round robin code 
import java.util.Scanner;
import java.util.ArrayList;

class Process{
	int id;
	int arrivalTime;
	int burstTime;
	int remainingTime;
	int completionTime;
	int turnaroundTime;
	int waitingTime;
}


public class RoundRobin {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter the number of processes:");
		int numberOfProcesses = in.nextInt();
		System.out.println("Enter the Quantum time:");
		int quantumTime = in.nextInt();

		ArrayList<Process> processes = new ArrayList<>();
		ArrayList<Process> completedProcesses = new ArrayList<>();
		
		for(int i = 0 ; i < numberOfProcesses; i++) {
			Process process = new Process();
			
			process.id = i+1;
			
			System.out.println("Enter arrival Time of process " + process.id + ":");
			process.arrivalTime = in.nextInt();
			System.out.println("Enter burst Time of process " + process.id + ":");
			process.burstTime = in.nextInt();
			process.remainingTime = process.burstTime;
			
			processes.add(process);
		}
		
		
		int currentTime = 0 ;
		ArrayList<Process> readyQueue = new ArrayList<>();
		while(!processes.isEmpty() || !readyQueue.isEmpty()) {
			for(int i = 0 ; i < processes.size(); i++) {
				
				Process process = processes.get(i);
				
				if(process.arrivalTime <= currentTime) {
					readyQueue.add(process);
					processes.remove(i);
					i--;	
				}
			}	
			
			if(!readyQueue.isEmpty()) {
				Process currentProcess = readyQueue.get(0);
				readyQueue.remove(0);
				
				if(currentProcess.remainingTime <= quantumTime) {
					currentTime += currentProcess.remainingTime;
					currentProcess.remainingTime = 0 ;	
					
					currentProcess.completionTime = currentTime;
					currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
					currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
					completedProcesses.add(currentProcess);
					
				}
				else {
					currentTime += quantumTime;
					currentProcess.remainingTime -= quantumTime;
					
					for(int i = 0 ; i < processes.size(); i++) {
						
						Process process = processes.get(i);
						
						if(process.arrivalTime <= currentTime) {
							readyQueue.add(process);
							processes.remove(i);
							i--;	
						}
					}
					readyQueue.add(currentProcess);
					
				}
			}
			else {
				currentTime++;
				}
		}
System.out.println("Process \t Arrival Time \t Burst Time \t Completion Time \t Turnaround Time \t Waiting Time");
		
		for(Process process: completedProcesses) {
			System.out.println(process.id + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" + process.completionTime + "\t\t" + process.turnaroundTime + "\t\t" + process.waitingTime);
		}
			double totalWaitingTime = 0;
			double totalTurnaroundTime = 0 ;
		for(Process process:completedProcesses) {
			 totalWaitingTime += process.waitingTime;
			 totalTurnaroundTime += process.turnaroundTime;
		}
		
		double avgWaitingTime = completedProcesses.isEmpty() ? 0 : totalWaitingTime/completedProcesses.size();
		double avgTurnaroundTime = completedProcesses.isEmpty() ? 0 : totalTurnaroundTime/completedProcesses.size();
		
		System.out.println("average waiting time:" + avgWaitingTime);
		System.out.println("average Turnaround time:" + avgTurnaroundTime);
	
		
		
	}

}
