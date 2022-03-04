/* SJF (Shortest job first) will run the shortest job first, the class compute the waiting time for each process upon initialization 
 * and then compute the average waiting time for each processs
 * @author : Tuan Nguyen 
 * */
public class SJF { 
    /* ArrrivalTime array use to contain the  timestamps on the gantt chart does the process arrive 
     * BurstTime array use to contain the amount it needed to complete the process from the CPU  
     * */
    private int[] ArrivalTime; 
    private int[] BurstTime ; 
    private int[] WaitingTime; 
    private int cap ; 
    //Gantt chart in the schedule 

    /* Function to compute the gantt chart based on arrival and burst time of the process.  
     * 
     * */ 
    int M = 5000; 
    int[] startTime= new int[M];
    int[] intervalLength = new int[M];
    int[] processIndex= new int[M];

    private void computeGantt(){
        int[] sortBurstTime = new int[cap] ;

    }
    /*Compute each process waititing time 
     * */
    public void computeWaitingTime(){

    }
    //Construct a queu for the processes
    public SJF(int[] a, int[] b,int c ){
        ArrivalTime = new int[cap] ;   
        BurstTime  = new int[cap] ;
        WaitingTime = new int[cap] ;
        this.cap = c ;
        //Deep coppy all the element  
        computeGantt() ;
        computeWaitingTime() ;
    }

    /*Follow the procedure describe in page 209 compute the gantt 
     * schedule for the current prorcesses
     * */
    public float computeAvgTime(){
        return 0 ; 
    }
    public void displayInfo(){
        System.out.println("Shortest Job First (SJF)");
        System.out.print("Process\tArrival Time\t Burst Time\tWait Time") ;
        for(int i=0;i<this.cap;i++){
            System.out.printf("%d\t%d\t%d\t%f",i,ArrivalTime[i],BurstTime[i],WaitingTime[i]) ;
        }
        System.out.println("Average Wait Time:"+this.computeAvgTime()) ;
    }
    public static void main( String[] args ){
        //Get the number from the book 
        int cap = 4; 
        int[] arival= {0,1,2,3}  ;
        int[] burst=  {8,4,9,5} ; 
        SJF scheduler = new SJF(arival,burst,cap)  ;       
        scheduler.displayInfo() ;
    }
}



