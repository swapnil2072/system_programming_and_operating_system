//final SJF CODE 

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int remainingTime; // Remaining time for SJF preemptive
    int completionTime;
    int turnaroundTime;
    int waitingTime;
}

public class SJFPreemptive {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the number of processes:");
        int numberOfProcesses = in.nextInt();

        ArrayList<Process> processes = new ArrayList<>();
        ArrayList<Process> completedProcesses = new ArrayList<>();

        for (int i = 0; i < numberOfProcesses; i++) {
            Process process = new Process();
            process.id = i + 1;
            System.out.println("Enter the burst time of Process " + process.id + ":");
            process.burstTime = in.nextInt();
            System.out.println("Enter the arrival time of Process " + process.id + ":");
            process.arrivalTime = in.nextInt();
            process.remainingTime = process.burstTime; // Initialize remaining time
            processes.add(process);
        }

        int currentTime = 0;
        ArrayList<Process> readyQueue = new ArrayList<>();

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
                readyQueue.sort(Comparator.comparingInt(p -> p.remainingTime));

                Process shortestJob = readyQueue.get(0);
                readyQueue.remove(0);

                int executionTime = Math.min(shortestJob.remainingTime, 1);
                currentTime += executionTime;
                shortestJob.remainingTime -= executionTime;

                if (shortestJob.remainingTime <= 0) {
                    shortestJob.completionTime = currentTime;
                    shortestJob.turnaroundTime = shortestJob.completionTime - shortestJob.arrivalTime;
                    shortestJob.waitingTime = shortestJob.turnaroundTime - shortestJob.burstTime;
                    completedProcesses.add(shortestJob);
                } else {
                    readyQueue.add(shortestJob);
                }
            } else {
                currentTime++;
            }
        }

        // Display the results
        System.out.println("Process\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (Process process : completedProcesses) {
            System.out.println(process.id + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" +
                    process.completionTime + "\t\t" + process.turnaroundTime + "\t\t" + process.waitingTime);
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
