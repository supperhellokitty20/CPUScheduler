import java.util.Deque;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
// http://www.henrikfrank.dk/abaptips/javaforsap/javabasics/calling_class_i_another_file.htm
//
public class PRR {
    private int cap;
    private int[] ArrivalTime;
    private int[] BurstTime;
    private int[] WaitingTime;
    private int[] Priority;
    private int quant;
    private int currentTime;
    /* Sort the process in term of arrival time 
     * */
    private void swap( int[] arr, int a ,int b){
        int x= arr[a] ;
        arr[a] = arr[b] ;
        arr[b] = x ; 
    }
    //Bubble Sort the arrive time array and burst array accordingly -> O(N) 
    public void sort( int[] a  , int[] b, int n ){
        if(n==1){
            return ; 
        }
        for(int i =0 ;i<n-1;i++){
            if(a[i] > a[i+1]) {
                swap(a,i,i+1) ;
                swap(b,i,i+1) ;
            }
        }
        sort(a,b,n-1) ;
    } 
    public PRR(int[] a, int[] b, int[] prio, int quant, int cap) {
        this.cap = cap;
        ArrivalTime = new int[cap];
        BurstTime = new int[cap];
        WaitingTime = new int[cap];
        Priority = new int[cap];
        this.quant = quant;
        this.currentTime = 0;
        // Deep coppy the element
        for (int i = 0; i < this.cap; i++) {
            ArrivalTime[i] = a[i];
            BurstTime[i] = b[i];
            Priority[i] = prio[i];
        }
        //Sort the process in term of arrival time also swapping the location of elements in BurstTime accordingly  
        sort( ArrivalTime ,BurstTime,quant ) ;
        computeWaitTime();
    }
    private void  checkArrival( Queue<Integer> q , int[] arrive,boolean[] inQ,boolean[] complete) {
        int n = arrive.length  ;
        for(int i=0;i<n;i++){
            if(arrive[i] <=this.currentTime && !inQ[i] && !complete[i]) {
                q.add(i);  
                inQ[i]= true ;
            }
        }
    }
    /*
     * Run a round robin schedule on the processes,calculating the wait time in the
     * processes
     * @precondition :The function assumes that arrive time is sorted in ascending orer  
     */
    private int[] roundRobin(int[] arrive, int[] burst, int quant) {
        int n = burst.length; 
        int[] wait = new int[n];
        int[] rmbt = new int[n];
        int[] timeCompleted = new int[n] ; 
        Queue<Integer>  q = new LinkedList<Integer>() ; 
        boolean[] complete = new boolean[n] ;
        int  countDone =0 ;
        boolean[] inQ =  new boolean[n];

        for(int i=0;i< n;i++){
            rmbt[i]= burst[i] ;
            //Initialize all the compete as false 
            complete[i] = false ;
        }
        //Add the first process index in the queue  
        // Check for arrival time of the first process if the process is not arrive the cpu will idle and do nothing 
        while(this.currentTime< arrive[0]){
            this.currentTime++ ;
        }
        q.add(0) ;
        inQ[0] = true ; 
        while(!q.isEmpty()){
            //update(arrive ,burst, quant) ;
            int j = q.poll() ; // Get the first element and remove the first element

            /* The process will be complete in this time quantum , proceed to calculate the waiting time of the process  
             * */
            if( rmbt[j]<=quant ) { 
               complete[j]  = true ;
               this.currentTime+= rmbt[j]  ;
                timeCompleted[j]  = currentTime ;  
                wait[j] = timeCompleted[j]-arrive[j]-burst[j] ;
                if(wait[j]<0) wait[j] =0 ; 
                rmbt[j] =0; 
                countDone++ ;
                if(countDone!=n){
                //Check for new process to arrive in the Queue update the q  
                //checkArrival( ) ;
                    checkArrival(q,arrive,inQ,complete)  ;
                }
            }
            /* The process doesn't finnish in this time quantum  
             * */
            else{
                rmbt[j] -=quant ;
                this.currentTime+= quant ;
                if(countDone!=n){
                    //Check new arrival
                     //checkArrival() ;
                    checkArrival(q,arrive,inQ,complete)  ;
                }
                //Push the incomplete process to the end of the queue 
                q.add(j) ;
            }
        }
        return wait;
    }

    public void computeWaitTime() {
        /*
         * Put each process multi level qeueu and start a RR schedule on those processes
         * , keep doing until all process are done
         * A ArrivalTime check for the processes should be included
         * (See book page 214)
         */
        // Categorize the process in the array of array ;
        // Or apply a PriorityQueue
        SortedSet<Integer> l = new TreeSet<Integer>();
        // Sort the priority in descending order
        for (int i = 0; i < this.cap; i++) {
            if (!l.contains(Priority[i])) {
                l.add(Priority[i]);
            }
        }

        PriorityQueue<Integer> qList = new PriorityQueue<Integer>(l);
        Iterator<Integer> it = qList.iterator();
        /* 
         * */
        while (it.hasNext()) {
            int prio = it.next();
            int c = 0;
            /*
             * Find the index of the element with the same priority level .
             * An a queue to keep track of the prio index
             */
            ArrayList<Integer> prioIndex = new ArrayList<Integer>();
            for (int i = 0; i < this.cap; i++) {
                if (Priority[i] == prio) {
                    prioIndex.add(i);
                    c++;
                }
            }
            // Use the collected prio Index to spawn an RR schedule based on the processes
            int[] arrivalPrio = new int[c];
            int[] burstPrio = new int[c];
            for (int i = 0; i < prioIndex.size(); i++) {
                arrivalPrio[i] = ArrivalTime[prioIndex.get(i)];
                burstPrio[i] = BurstTime[prioIndex.get(i)];
            }
            // Start an RR schedule on the processes ,keep updating the currentTime
            int[] wait = roundRobin(arrivalPrio, burstPrio, this.quant);
            // Update the wait time of process based on the index proivided in prioIndex
            // that store the index of processes that have the same priority
            for (int i = 0; i < wait.length; i++) {
                WaitingTime[prioIndex.get(i)] = wait[i];
            }
        }
    }

    public float computeAvgTime() {
        int sum = 0;
        for (int i = 0; i < this.cap; i++) {
            sum += WaitingTime[i];
        }
        return (float) sum / cap;

    }

    public void displayInfo() {
        System.out.println("Round Robin (RR)");
        System.out.println("Process\tArrival\tBurst\tPrio\tWait");
        for (int i = 0; i < this.cap; i++) {
            // Display Info here
            System.out.printf("%d\t%d\t%d\t%d\t%d\n", i, ArrivalTime[i], BurstTime[i], Priority[i], WaitingTime[i]);
        }
        System.out.println("Average Wait Time:" + this.computeAvgTime());

    }

    public static void main(String args[]) {
        //With this test case arrive time is sorted 
        int[] arrive = {5,3,2,4,1};
        int[] burst = { 4, 5, 8, 7, 3 };
        int[] prio = { 3, 2, 2, 1, 3 };
        int q = 4;
        int cap = 5;
        PRR scheduler = new PRR(arrive, burst, prio, q, cap);
        scheduler.displayInfo();
    }
}
