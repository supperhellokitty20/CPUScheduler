import java.util.Deque; 
import java.util.LinkedList; 
import java.util.PriorityQueue; 
import java.util.ArrayList ;
import java.util.Iterator ; 
import java.util.SortedSet ;
import java.util.TreeSet ; 

// http://www.henrikfrank.dk/abaptips/javaforsap/javabasics/calling_class_i_another_file.htm
public class PRR{
    private int cap ;       
    private int[] ArrivalTime ;
    private int[] BurstTime ; 
    private int[] WaitingTime; 
    private int[] Priority; 
    private int quant; 
    private int currentTime; 
    public PRR(int[]a ,int[] b,int[] prio, int quant ,int cap){
        this.cap = cap ;
        ArrivalTime = new int[cap] ;
        BurstTime = new int[cap] ;
        WaitingTime = new int[cap] ;
        Priority = new int[cap] ;
        this.quant = quant; 
        this.currentTime= 0; 
        //Deep coppy the element  
        for(int i=0;i<this.cap;i++){
            ArrivalTime[i] = a[i] ;
            BurstTime[i] = b[i] ;
            Priority[i] = prio[i]; 
        }
        computeWaitTime();
    } 

    /* Run a round robin schedule on the processes,calculating the wait time in the processes 
     * The processes passed in the function should be having the same priority . 
     * */
    private int[] roundRobin( int[] arrive,int[] burst , int currentTime , int quant){
         int[] rmbt = new int[burst.length] ;
         int[] wait = new int[burst.length] ;
        Deque<Integer> q = new LinkedList<Integer>() ;    
         for(int i=0 ;i<burst.length;i++){
            rmbt[i]  = burst[i] ;
            q.add(i) ;
         }
        while(q.size()>0){
            int j = q.getFirst();
            if(arrive[j]<=currentTime){
                //Add the current process to ready qeueu if the process is not already in q 
                //Move the first element to the last element and continue  
                if(rmbt[j]>quant){
                    currentTime+=quant ; 
                    rmbt[j]-=quant ;
                    q.removeFirst() ;
                    q.addLast(j) ;
                }else{
                    //The last cycle for this process
                    currentTime+=rmbt[j] ;
                    //Update the current time with the class 
                    this.currentTime = currentTime ;
                    wait[j] = currentTime - burst[j] ;
                    rmbt[j]=0 ; // This process is complete 
                    q.removeFirst() ;
                }
            }
            //If the process does not arrive yet move on with the time
            else{
                currentTime+=quant ; 
                continue; 
            }
        }
        return wait;
    }
    public void computeWaitTime(){
        /*Put each process multi level qeueu and start a RR schedule on those processes , keep doing until all process are done 
         *A ArrivalTime check for the processes should be included 
         * (See book page 214)
         * */
        //Categorize the process in the array of array ;           
        //Or apply a PriorityQueue
        SortedSet<Integer> l = new TreeSet<Integer>() ;
        //Sort the priority in descending order 
        for(int i=0;i<this.cap;i++){
            if(!l.contains(Priority[i]) ){
                l.add(Priority[i]) ;
            }
        }

        PriorityQueue<Integer>  qList = new PriorityQueue<Integer>(l)  ;         
        Iterator<Integer> it = qList.iterator() ;
        /* 
         * */
        while(it.hasNext()){
            int prio = it.next() ;
            int c=0;
            /* Find the index of the element with the same priority level . 
             * An a queue to keep track of the prio index
             * */
            ArrayList<Integer>  prioIndex = new ArrayList<Integer>() ;
            for(int i =0 ; i<this.cap; i++){
                if(Priority[i]==prio){
                    prioIndex.add(i) ;
                    c++ ;
                }
            }
            //Use the collected prio Index to spawn an RR schedule based on the processes
            int[] arrivalPrio = new int[c]; 
            int[] burstPrio = new int[c] ; 
            for(int i =0 ;i<prioIndex.size();i++){
                arrivalPrio[i]=ArrivalTime[prioIndex.get(i)];
                burstPrio[i] = BurstTime[prioIndex.get(i)] ;
            }
            // Start an RR schedule on the processes ,keep updating the currentTime  
            int[] wait = roundRobin(arrivalPrio,burstPrio,currentTime,this.quant) ;
            for(int i =0;i<wait.length;i++){ 
                WaitingTime[prioIndex.get(i)] = wait[i] ;
            }
        }
    }
    public float computeAvgTime(){
        int sum=0 ;
        for(int i=0;i<this.cap;i++){
            sum+=WaitingTime[i]; 
        }
        return (float) sum/cap ;

    }
    public void displayInfo(){
        System.out.println("Round Robin (RR)");
        System.out.println("Process\tArrival\tBurst\tPrio\tWait") ;
        for(int i=0;i<this.cap;i++){
            //Display Info here
            System.out.printf("%d\t%d\t%d\t%d\t%d\n",i,ArrivalTime[i],BurstTime[i],Priority[i],WaitingTime[i]) ;
        }
        System.out.println("Average Wait Time:"+this.computeAvgTime()) ;

    }
    public static void main(String args[]){
        int[] arrive = {0,1,2,3,4,5} ;
        int[] burst = {4,5,8,7,3} ;
        int[] prio= {3,2,2,1,3} ;
        int q=7   ; 
        int cap=5 ; 
        PRR scheduler = new PRR(arrive,burst,prio,q,cap) ;
        scheduler.displayInfo() ;
    }
}




