public class RoundRobin
{
    // Method that calculates the waiting time for all processes
    static int[] calcWaitingTime(int burstTime[], int quantum)
    {
        int n = burstTime.length; // Number of processes (size of the arrays)
        int[]  remainingTime = new int[n]; // Stores the remaining burstTime of each process
        int[]  waitingTime = new int[n]; // Stores the waiting time of each process
        int time = 0; // Current time
        boolean processesFinished = false; // True if all processes are finished, false if any process is not finished

        // Copying the burst times
        System.arraycopy(burstTime, 0, remainingTime, 0, n);

        // Until are processes are completed
        while(!processesFinished)
        {
            processesFinished = true;

            // Going through all the processes
            for (int i = 0 ; i < n; i++)
            {
                // If a process is not yet completed
                if (remainingTime[i] > 0)
                {
                    processesFinished = false;

                    // Quantum is the allowance of CPU time of a process cycle
                    if (remainingTime[i] > quantum)
                    {
                        // Increasing the value of time and decreasing the burst time of current process by quantum
                        time = time + quantum;
                        remainingTime[i] -= quantum;
                    }

                    // If the burst time is smaller or equal to quantum, the process is going to end after this cycle
                    else
                    {
                        // Increasing the value of time by the remaining time
                        time = time + remainingTime[i];

                        // Waiting time is the total time without the burst time the process has taken
                        waitingTime[i] = time - burstTime[i];

                        // Since the process is now completed, make its remaining burst time equal to 0
                        remainingTime[i] = 0;
                    }
                }
            }
        }
        // Returns the waiting times for each process
        return waitingTime;
    }

    // Method that calculates turn around time for all processes
    static int[] calcTurnAroundTime(int burstTime[], int waitingTime[])
    {
        // Initializing turnaround time array.
        int[] turnAroundTime = new int[burstTime.length];

        // To calculate turnaround time we have to add the wait time + the burst time of the process.
        for (int i = 0; i < burstTime.length; i++)
            turnAroundTime[i] = burstTime[i] + waitingTime[i];

        return turnAroundTime;
    }

    // Method that prints the results and calculates the average waiting and
    // turnaround times
    static void printAvgTimes(int burstTime[], int quantum)
    {
        int n = burstTime.length;
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;

        // Find waiting time of all processes
        int[] waitingTime = calcWaitingTime(burstTime, quantum);

        // Find turn around time for all processes
        int[] turnAroundTime = calcTurnAroundTime(burstTime, waitingTime);

        // Display processes along with all details
        System.out.println("Process " + " Burst Time " +
                " Waiting Time " + " Turnaround Time");
        System.out.println("=======  ==========  ============  ===============");
        // Calculate total waiting time and total turn
        // around time
        for (int i = 0; i < n; i++) {
            totalWaitingTime += waitingTime[i];
            totalTurnAroundTime += turnAroundTime[i];
            System.out.println(i + "\t\t" + burstTime[i] +"\t " +
                    waitingTime[i] +"\t\t " + turnAroundTime[i]);
        }

        System.out.println("\nAverage waiting time = " +
                (float)totalWaitingTime / (float)n);
        System.out.println("Average turnaround time = " +
                (float)totalTurnAroundTime / (float)n);
    }

    // Driver Method to test your algorithm with a simple example
    public static void main(String[] args)
    {
        // Burst time of processes. The array index is the process ID.
        int burstTime[] = {5, 15, 4, 3};

        // Time quantum
        int quantum = 3;

        printAvgTimes(burstTime, quantum);
    }
}