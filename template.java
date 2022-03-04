import java.io.*;
import java.util.*;
import java.lang.*;

public class template{ //left_brace_1

    public static void main(String[] args) throws IOException { //left_brace_2

    /* This primitive_sample_template only provides some hints about what kinds of 
       input data and output data could be involved when the Preemptive SJF Scheduling 
       Algorithm described in the book by Silberschatz, Galvin, and Gagne page 209 
       is used to compute the Average Waiting Time for a given set of processes  */

        int N = 4;  // number of processes
        int[] Arrival_Time = new int[N];
        int[] Burst_Time = new int[N];

    /* Time Quantum and Process Priorities are not used in the Preemptive SJF 
       Scheduling Algorithm; but Time Quantum and Process Priorities are used 
       in the Combined Round-Robin and Priority Scheduling Algorithm described 
       in the book by Silberschatz, Galvin, and Gagne page 213 */

        int Time_Quantum = 0;

        int[] Priority = new int[N];

        /* Assuming that process indexes start from 0, instead of 1, the processes
           in the example on page 209 could be initialized to have the following 
           input parameter values */

        Arrival_Time[0] = 0;
        Arrival_Time[1] = 1;
        Arrival_Time[2] = 2;
        Arrival_Time[3] = 3;

        Burst_Time[0] = 8;
        Burst_Time[1] = 4;
        Burst_Time[2] = 9;
        Burst_Time[3] = 5;

        /* The schedule or Gantt chart when the Preemptive SJF 
           Scheduling Algorithm is used, should be computed
           according to the explanation on page 209 of the material in
           Silberschatz, Galvin, and Gagne’s book */

        int M = 5000; // number of time intervals in the schedule or Gantt chart;

        int[] schedule_interval_start_time = new int[M];

        int[] schedule_interval_length = new int[M];

        int[] schedule_interval_process_index = new int[M];


        /* For the preemptive SJF schedule or Gantt chart shown in the example 
           on page 209, assuming that process indexes start from 0, instead of 1,
           data associated with the time intervals could have the following values:

           schedule_interval_start_time[0] = 0;
           schedule_interval_length[0] = 1;
           schedule_interval_process_index[0] = 0;

           schedule_interval_start_time[1] = 1;
           schedule_interval_length[1] = 4;
           schedule_interval_process_index[1] = 1;
           ...
           schedule_interval_start_time[4] = 17;
           schedule_interval_length[4] = 9;
           schedule_interval_process_index[4] = 2;
           */

        /* The Waiting_Time for each process when the Preemptive SJF s
           Scheduling Algorithm is used, should be determined
           according to the explanation on page 209 of the material in
           Silberschatz, Galvin, and Gagne’s book,
           in order to finally compute the Average Waiting Time */

        int[] Waiting_Time = new int[N];

        Waiting_Time[0] = 0;
        Waiting_Time[1] = 0;
        Waiting_Time[2] = 0;
        Waiting_Time[3] = 0;

        int Waiting_Time_Sum = 0;

        double Average_Waiting_Time = 0.0;

        /* If the Waiting_Time for each process in the example 
           of the preemptive SJF scheduling algorithm on page 209
           is computed correctly according to the explanation on 
           page 209 of the material in Silberschatz, Galvin, and Gagne’s 
           book, their values would be:
            */
           Waiting_Time[0] = 9;
           Waiting_Time[1] = 0;
           Waiting_Time[2] = 15;
           Waiting_Time[3] = 2;   

        for (int i = 0; i <= N - 1; i++) {
            Waiting_Time_Sum = Waiting_Time_Sum + Waiting_Time[i];
        }

        Average_Waiting_Time = Waiting_Time_Sum / N; 

        // print out the Average_Waiting_Time

        System.out.println("Average_Waiting_Time = "+Average_Waiting_Time);

        /* If the Average Waiting_Time in the example 
           of the preemptive SJF scheduling algorithm on page 209
           is computed correctly according to the explanation on 
           page 209 of the material in Silberschatz, Galvin, and Gagne’s 
           book, the program would print out:

           "Average_Waiting_Time = 6.5" */

    } //right_brace_2

} //right_brace_1



