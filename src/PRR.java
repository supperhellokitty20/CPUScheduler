package scheduler ; 
import scheduler.* ; 
import java.util.Queue; 
import java.util.LinkedList; 
import java.util.Scanner ;
public class PRR{
    private int cap ;       
    private int[] ArrivalTime ;
    private int[] BurstTime ; 
    private int[] WaitingTime; 
    private int[] Priority; 
    private int quant; 
    public PRR(int[]a ,int[] b,int[] prio, int quant ,int cap){
        this.cap = cap ;
        ArrivalTime = new int[cap] ;
        BurstTime = new int[cap] ;
        WaitingTime = new int[cap] ;
        this.quant = quant; 
        //Deep coppy the element  
        for(int i=0;i<this.cap;i++){
            ArrivalTime[i] = a[i] ;
            BurstTime[i] = b[i] ;
            Priority[i] = prio[i]; 
        }
        computeWaitTime();
    } 
    public void computeWaitTime(){
        /*Put each process multi level qeueu and start a RR schedule on those processes , keep doing until all process are done 
         *A ArrivalTime check for the processes should be included 
         * (See book page 214)
         * */

        //Categorize the process in the array of Queue ;           
        //Or apply a PriorityQueue
        Queue<Integer>[]  qList   ;         
        for(int i=0;i<this.cap;i++){
            /*if(priority[i] not seen previous in the array){
             *      qList[i] = new Queue<Integer>() ;
             *      for(int j=i+1;j<this.cap-i,j++){ 
             *          if(prioty[i]==Priority[j]) qList[i].add( j );
             *      } 
             * }
             * */
        }
        // for(int j in qList){
        //     prepare the burst time and run the RR on the processes  
        //      
        // }

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
        int[] arrive = {0,1,2,3,4,5} ;
        int[] burst = {4,5,8,7,3} ;
        int[] prio= {3,2,2,1,3} ;
        int q=4  ; 
        int cap=5 ; 
        PRR scheduler = new PRR(arrive,burst,prio,q,cap) ;
        scheduler.displayInfo() ;
    } }




