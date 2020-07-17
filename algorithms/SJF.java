import java.util.PriorityQueue;

// Class used in order to put processes in the priority queue(minheap)
// stored in ascending order compared to their burst times
class Process implements Comparable<Process>
{
    private int id; // The id of the process
    private Integer burstTime; // The burst time of the process

    // Constructor of the Process class
    // Takes two integers as parameters, one for the id and one for the burst time of the process
    Process(int id, int burstTime)
    {
        this.id = id;
        this.burstTime = burstTime;
    }

    // Returns the id of the process
    Integer getId()
    {
        return id;
    }

    // Returns the burst time of the process
    // (the method is private since we only call it inside the class on the compareTo method)
    private Integer getBurstTime()
    {
        return burstTime;
    }

    // Overriding the compareTo method in order to have the processes of the minheap being compared by their bursts times
    // In case their burst time is equal, the process with the smallest id will go first, since normally it's checked first
    @Override
    public int compareTo(Process process) {
        if (this.getBurstTime().compareTo(process.getBurstTime()) == 0)
            return this.getId().compareTo(process.getId());
            else
                return this.getBurstTime().compareTo(process.getBurstTime());
    }



}

public class SJF
{
    // Method that calculates the waiting time for all processes
    static int[] calcWaitingTime(int burstTime[], int quantum) {

        // In order to implement the SFJ algorithm with a better time complexity we are going to
        // use a priority queue which is based on a priority heap.
        // Min heap is a suitable data structure since he root will have the process with the smallest burst time.
        // Adding and deleting an element takes O(logn).
        // So that way, the overall complexity of the algorithm will be O(nlogn).

        // Note: We totally ignore arrival times during the process since all of the processes arrive by the same time

        int n = burstTime.length; // Number of processes

        // Initializing waiting time array
        int[] waitingTime = new int[n];

        // Initializing the PriorityQueue (minheap) which holds the ids and burst times
        // of the processes since it has Process objects
        PriorityQueue<Process> processes = new PriorityQueue<>(n);
        for (int i = 0; i < n; i++)
            processes.add(new Process(i, burstTime[i]));

        int previousShortestProcessID = 0; // Holds the if of the process with the previous shortest burst time

        if (processes.size() > 0)
        {
            previousShortestProcessID = processes.poll().getId(); // The id of first process which is going to be executed
            waitingTime[previousShortestProcessID] = 0; // Waiting time for shortest(first) process is 0.
        }

        // Until all processes are completed
        while(processes.size() > 0)
        {
           int shortestProcessID = processes.poll().getId(); // The id of the next shortest process

            //Calculating it's waiting time, by adding it's burst time and the previous process waiting time
            waitingTime[shortestProcessID] = burstTime[previousShortestProcessID] + waitingTime[previousShortestProcessID];
            previousShortestProcessID = shortestProcessID;
        }

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