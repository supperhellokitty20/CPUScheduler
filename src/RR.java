/* Round Robin to determine the average waiting time of  the processes given Arrival Time , Burst Time and Time Quantum   
 *
 * */
import java.util.Deque;
import java.util.LinkedList ; 
import java.util.Scanner; 
public  class RR{
    private int cap ;       
    private int[] ArrivalTime ;
    private int[] BurstTime ; 
    private int[] WaitingTime; 
    private int timeQuantum ; 
    public RR(int[] a , int[]b , int quant,int cap){
        this.cap = cap ;
        ArrivalTime = new int[cap] ;
        BurstTime = new int[cap] ;
        WaitingTime = new int[cap] ;
        timeQuantum  = quant; 
        //Deep coppy the element  
        for(int i=0;i<this.cap;i++){
            ArrivalTime[i] = a[i] ;
            BurstTime[i] = b[i] ;
        }
        computeWaitTime();
    }
    public void computeWaitTime(){
       int completed =0;  
       //This will store all of the index of the next process to work with
       //The array rmbt will keep track of the remaining time left for the process 
       int currentTime = 0 ; 
       int[]  rmbt = new int[cap] ;
       Deque<Integer> q = new LinkedList<Integer>() ;    
       for(int i =0;i<this.cap ;i++){
            rmbt[i] = BurstTime[i]  ;
            q.add(i) ;
       }
       while(completed<=cap){
           for(int i =0;i<this.cap;i++){
            //Traverse all the process ,avoid  
               if(ArrivalTime[i]<=currentTime){
                   //Add the current process to ready qeueu if the process is not already in q 
                   int j = q.getFirst() ;
                   //Move the first element to the last element and continue  
                   if(rmbt[j]>timeQuantum){
                        currentTime+=timeQuantum ; 
                        rmbt[i]-=timeQuantum ;
                        q.removeFirst() ;
                        q.addLast(j) ;
                   }else{
                       //The last cycle for this process
                       currentTime+=rmbt[j] ;
                       WaitingTime[j] = currentTime - BurstTime[j] - ArrivalTime[j] ;
                       completed++ ;
                       rmbt[j]=0 ; // This process is complete 
                   }
               }
               //If the process does not arrive yet move on with the time
               else{
                   currentTime+=timeQuantum ; 
                   continue; 
               }
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
        System.out.println("Process\tArrival\t Burst \tWait") ;
        for(int i=0;i<this.cap;i++){
            //Display Info here
            System.out.printf("%d\t%d\t%d\t%d\n",i,ArrivalTime[i],BurstTime[i],WaitingTime[i]) ;
        }
        System.out.println("Average Wait Time:"+this.computeAvgTime()) ;

    }
    public static void main(String args[]){ 
        //The class take line of number as input  
        //and output the resulting table  
        //TODO: Work on taking user input and handling incorrect input 
        //While taking the user input make sure that they are sorted upon arrival order
        //and swap the burst[] accordingly 
        int[] arrival = {0,1,2} ;
        int[] burst = {24,3,3  } ; 
        int quant = 4 ; 
        int cap = 3 ;
        /*The first line of input contain the number of processes in the array 
         * The second line contain the number of arrival time of each process
         * The third line contain  the number of burst time needed for each process
         * */
        RR scheduler = new RR(arrival,burst,quant,cap)  ;
        scheduler.displayInfo()     ;
        
}
} 


