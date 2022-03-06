/* SJF (Shortest job first) will run the shortest job first, the class compute the waiting time for each process upon initialization 
 * and then compute the average waiting time for each processs
 * @author : Tuan Nguyen 
 * */
package scheduler; 
public class SJF { 
    /* ArrrivalTime array use to contain the  timestamps on the gantt chart does the process arrive 
     * BurstTime array use to contain the amount it needed to complete the process from the CPU  
     * */
    private int[] ArrivalTime; 
    private int[] BurstTime ; 
    private int[] WaitingTime ; 
    private int cap ; 
    //Gantt chart in the schedule 

    /* Compute the wait time for each process   
     * by simulating the gantt chart in 3 arrays 
     * */ 

    /*Compute each process waititing time 
     * */
    public void  computeWaitingTime(){
        WaitingTime = new int[cap] ;
        //Store the number of completed process 
        int completed=0;  
        int currentTime=0; 
        //Store the remaining runtime of all the process 
        int[] runTime= new int[this.cap];
        for(int i=0;i<this.cap;i++){
            runTime[i]=BurstTime[i] ;
        }
        int indexMin=0 ;  
        int minRunTime = Integer.MAX_VALUE ;  
        boolean checkedForMinProcess= false ;
        while(completed!=cap){
            //Find the min runTime from the array ,update based on the currentTime 
            for(int i=0;i<this.cap;i++){
                if(ArrivalTime[i]<=currentTime && runTime[i]<minRunTime && runTime[i]>0){
                    minRunTime = runTime[i] ;
                    indexMin =i ; 
                    checkedForMinProcess =true ;
                }               
            }
            if(checkedForMinProcess==false){
                currentTime++ ; 
                continue ; 
            }
            //Reduce the run time of the shortest process 
            runTime[indexMin]--; 
            
            //Update minRunTime 
            minRunTime = runTime[indexMin] ;
            if(minRunTime==0) minRunTime = Integer.MAX_VALUE ;

            //If a process get completed proceed to calculate the waiting time of the process 
            if(runTime[indexMin]==0){
                completed++ ; 
                //Update the checked flag so in the next iteration ,the function will update the currentTime accordingly 
                checkedForMinProcess =false ;
                //Compute the finnish time of the process  
                int finnishTime = currentTime+1 ;
                WaitingTime[indexMin] = finnishTime - BurstTime[indexMin] - ArrivalTime[indexMin] ;
            }
            //Increment currentTime 
            currentTime++ ; 
        }
    }
    //Construct a queu for the processes
    public SJF(int[] a, int[] b,int c ){
        this.cap = c ;
        this.ArrivalTime = new int[cap] ;   
        this.BurstTime  = new int[cap] ;
        for(int i=0;i<c;i++){
            this.ArrivalTime[i]= a[i] ;
            this.BurstTime[i] = b[i] ;
        }
        computeWaitingTime() ;
    }

    /*Follow the procedure describe in page 209 compute the gantt 
     * schedule for the current prorcesses
     * */
    public float computeAvgTime(){
        int sum=0 ;
        for(int i=0;i<this.cap;i++){
            sum+=WaitingTime[i]; 
        }
        return (float) sum/cap ;
    }
    public void displayInfo(){
        System.out.println("Shortest Job First (SJF)");
        System.out.println("Process\tArrival\t Burst \tWait") ;
        for(int i=0;i<this.cap;i++){
            //Display Info here
            System.out.printf("%d\t%d\t%d\t%d\n",i,ArrivalTime[i],BurstTime[i],WaitingTime[i]) ;
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



