/* Round Robin to determine the average waiting time of  the processes given Arrival Time , Burst Time and Time Quantum   
 *
 * */
/* Round Robin to determine the average waiting time of  the processes given Arrival Time , Burst Time and Time Quantum   
 * @author : Tuan Nguyen & Syed Numair Shah
 * */
/* Round Robin to determine the average waiting time of  the processes given Arrival Time , Burst Time and Time Quantum   
 *
 * */
import java.util.Queue;
import java.util.LinkedList ; 
import java.util.Scanner; 
public  class RR{
	private int cap ;       
	private int[] ArrivalTime ;
	private int[] BurstTime ; 
	private int[] WaitingTime; 
	private int timeQuantum ; 
    	private int currentTime  ;
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

	public RR(int[] a , int[]b , int quant,int cap){
		this.cap = cap ;
		this.ArrivalTime = new int[cap] ;
		this.BurstTime = new int[cap] ;
		this.WaitingTime = new int[cap] ;
		this.timeQuantum  = quant; 
        	this.currentTime = 0;  
		//Deep coppy the element  
		for(int i=0;i<this.cap;i++){
			ArrivalTime[i] = a[i] ;
			BurstTime[i] = b[i] ;
		}
		sort(this.ArrivalTime,this.BurstTime,this.cap) ; 
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
        //If the first process does not arrive the CPu is idle
        while(this.currentTime<arrive[0]){
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
	public void computeWaitTime(){
	    this.WaitingTime =	roundRobin( this.ArrivalTime, this.BurstTime, this.timeQuantum) ;
	}
	public float computeAvgTime(){
		int sum=0 ;
		for(int i=0;i<this.cap;i++){
			sum+=WaitingTime[i]; 
		}
		return (float) sum/cap ;
	}
	public int[] getWaittime(){
		return this.WaitingTime ;
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
		int[] arrival = {1,2,3} ;
		int[] burst = {1,2,3} ; 
		int quant = 4 ; 
		int cap = 3 ;
		/* TODO: The first line of input contain the number of processes in the array 
		 * The second line contain the number of arrival time of each process
		 * The third line contain  the number of burst time needed for each process
		 * */
		RR scheduler = new RR(arrival,burst,quant,cap)  ;
		scheduler.displayInfo()     ;
	}
} 





