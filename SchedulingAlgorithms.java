import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;
    int priority;
    int remainingTime;
}

public class SchedulingAlgorithms {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the number of processes:");
        int numberOfProcesses = in.nextInt();

        ArrayList<Process> processes = new ArrayList<>();

        for (int i = 0; i < numberOfProcesses; i++) {
            Process process = new Process();
            process.id = i + 1;
            System.out.println("Enter the burst time of Process " + process.id + ":");
            process.burstTime = in.nextInt();
            System.out.println("Enter the arrival time of Process " + process.id + ":");
            process.arrivalTime = in.nextInt();

            System.out.println("Enter the priority of Process " + process.id + ":");
            process.priority = in.nextInt();

            process.remainingTime = process.burstTime;
            processes.add(process);
        }

        Collections.sort(processes, (p1, p2) -> p1.arrivalTime - p2.arrivalTime);

        while (true) {
            System.out.println("\nChoose a scheduling algorithm:");
            System.out.println("1. FCFS");
            System.out.println("2. SJF");
            System.out.println("3. Priority");
            System.out.println("4. Round Robin");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = in.nextInt();

            switch (choice) {
                case 1:
                    runFCFS(processes);
                    break;
                case 2:
                    runSJF(processes);
                    break;
                case 3:
                    runPriority(processes);
                    break;
                case 4:
                    runRoundRobin(processes);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Implement FCFS algorithm
    private static void runFCFS(ArrayList<Process> processes) {
        int currentTime = 0;
        for (Process process : processes) {
            if (process.arrivalTime > currentTime) {
                currentTime = process.arrivalTime;
            }

            process.completionTime = currentTime + process.burstTime;
            process.turnaroundTime = process.completionTime - process.arrivalTime;
            process.waitingTime = process.turnaroundTime - process.burstTime;

            currentTime = process.completionTime;
        }
        displayResults(processes);
    }

    // Implement SJF algorithm
   private static void runSJF(ArrayList<Process> processes) {
    int n = processes.size();
    int currentTime = 0;

    ArrayList<Process> readyQueue = new ArrayList<>();
    ArrayList<Process> completedProcesses = new ArrayList<>();

    boolean allProcessesProcessed = false;

    while (!allProcessesProcessed) {
        for (int i = 0; i < processes.size(); i++) {
            if (processes.get(i).arrivalTime <= currentTime) {
                readyQueue.add(processes.remove(i));
                i--;
            }
        }

        if (!readyQueue.isEmpty()) {
            readyQueue.sort(Comparator.comparingInt(p -> p.burstTime));

            Process shortestJob = readyQueue.get(0);
            readyQueue.remove(0);

            shortestJob.completionTime = currentTime + shortestJob.burstTime;
            shortestJob.turnaroundTime = shortestJob.completionTime - shortestJob.arrivalTime;
            shortestJob.waitingTime = shortestJob.turnaroundTime - shortestJob.burstTime;

            completedProcesses.add(shortestJob);

            currentTime = shortestJob.completionTime;
        } else {
            currentTime++;
        }

        allProcessesProcessed = processes.isEmpty() && readyQueue.isEmpty();
    }

    displayResults(completedProcesses);
}


    // Implement Priority algorithm
    private static void runPriority(ArrayList<Process> processes) {
        int n = processes.size();
        int currentTime = 0;

        ArrayList<Process> readyQueue = new ArrayList<>();
        ArrayList<Process> completedProcesses = new ArrayList<>();

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            for (int i = 0; i < processes.size(); i++) {
                if (processes.get(i).arrivalTime <= currentTime) {
                    readyQueue.add(processes.remove(i));
                    i--;
                }
            }

            if (!readyQueue.isEmpty()) {
                readyQueue.sort(Comparator.comparingInt(p -> p.priority));

                Process highestPriorityProcess = readyQueue.get(0);
                readyQueue.remove(0);

                int executionTime = Math.min(highestPriorityProcess.remainingTime, 1);
                currentTime += executionTime;
                highestPriorityProcess.remainingTime -= executionTime;

                if (highestPriorityProcess.remainingTime <= 0) {
                    highestPriorityProcess.completionTime = currentTime;
                    highestPriorityProcess.turnaroundTime = highestPriorityProcess.completionTime - highestPriorityProcess.arrivalTime;
                    highestPriorityProcess.waitingTime = highestPriorityProcess.turnaroundTime - highestPriorityProcess.burstTime;
                    completedProcesses.add(highestPriorityProcess);
                } else {
                    readyQueue.add(highestPriorityProcess);
                }
            } else {
                currentTime++;
            }
        }
        displayResults(completedProcesses);
    }

    // Implement Round Robin algorithm
    private static void runRoundRobin(ArrayList<Process> processes) {
        int n = processes.size();
        int quantum = 2;
        int currentTime = 0;

        ArrayList<Process> readyQueue = new ArrayList<>();
        ArrayList<Process> completedProcesses = new ArrayList<>();

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            for (int i = 0; i < processes.size(); i++) {
                if (processes.get(i).arrivalTime <= currentTime) {
                    readyQueue.add(processes.remove(i));
                    i--;
                }
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.get(0);
                readyQueue.remove(0);

                int executionTime = Math.min(currentProcess.remainingTime, quantum);
                currentTime += executionTime;
                currentProcess.remainingTime -= executionTime;

                if (currentProcess.remainingTime <= 0) {
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    completedProcesses.add(currentProcess);
                } else {
                    readyQueue.add(currentProcess);
                }
            } else {
                currentTime++;
            }
        }
        displayResults(completedProcesses);
    }

    // Display results
    private static void displayResults(ArrayList<Process> processes) {
        System.out.println("Process\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (Process process : processes) {
            System.out.println(process.id + "\t" + process.arrivalTime + "\t" + process.burstTime + "\t" +
                    process.completionTime + "\t" + process.turnaroundTime + "\t" + process.waitingTime);
        }
        double avgTurnaroundTime = processes.stream().mapToDouble(p -> p.turnaroundTime).average().orElse(0);
        double avgWaitingTime = processes.stream().mapToDouble(p -> p.waitingTime).average().orElse(0);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
    }
}
