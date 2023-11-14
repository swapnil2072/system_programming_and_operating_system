//final round robin code 

import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;
}

public class RoundRobin {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of processes:");
        int numberOfProcesses = in.nextInt();
        System.out.println("Enter the time quantum:");
        int timeQuantum = in.nextInt();
        ArrayList<Process> processes = new ArrayList<>();
        ArrayList<Process> completedProcesses = new ArrayList<>();

        for (int i = 0; i < numberOfProcesses; i++) {
            Process process = new Process();
            process.id = i + 1;
            System.out.println("Enter the burst time of Process " + process.id + ":");
            process.burstTime = in.nextInt();
            System.out.println("Enter the arrival time of Process " + process.id + ":");
            process.arrivalTime = in.nextInt();
            process.remainingTime = process.burstTime;
            processes.add(process);
        }

        int currentTime = 0;
        Queue<Process> readyQueue = new LinkedList<>();
        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            for (int i = 0; i < processes.size(); i++) {
                Process process = processes.get(i);
                if (process.arrivalTime <= currentTime) {
                    readyQueue.add(process);
                    processes.remove(i);
                    i--;
                }
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                if (currentProcess.remainingTime <= timeQuantum) {
                    currentTime += currentProcess.remainingTime;
                    currentProcess.remainingTime = 0;
                } else {
                    currentTime += timeQuantum;
                    currentProcess.remainingTime -= timeQuantum;
                }

                while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                    readyQueue.add(processes.remove(0));
                }

                if (currentProcess.remainingTime > 0) {
                    readyQueue.add(currentProcess);
                } else {
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    completedProcesses.add(currentProcess);
                }
            } else {
                currentTime++;
            }
        }

        // Display the results
        System.out.println("Process\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (Process process : completedProcesses) {
            System.out.println(process.id + "\t\t" + process.arrivalTime + "\t\t" +
                    process.burstTime + "\t\t" +
                    process.completionTime + "\t\t" + process.turnaroundTime + "\t\t" +
                    process.waitingTime);
        }

        // Calculate and display the average turnaround time and waiting time
        double totalTurnaroundTime = 0;
        for (Process p : completedProcesses) {
            totalTurnaroundTime += p.turnaroundTime;
        }
        double avgTurnaroundTime = completedProcesses.isEmpty() ? 0 : totalTurnaroundTime / completedProcesses.size();

        // Calculate average waiting time
        double totalWaitingTime = 0;
        for (Process p : completedProcesses) {
            totalWaitingTime += p.waitingTime;
        }
        double avgWaitingTime = completedProcesses.isEmpty() ? 0 : totalWaitingTime / completedProcesses.size();
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
    }
}
