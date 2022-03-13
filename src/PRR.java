/*This Class figures out the Priority based Round Robin avarage waiting time of the processes being given the Arrival Time, Burst Time, 
 * priorities for each process, and the Quantum Time
 * @author Tuan Nguyen & Syed Numair Shah
 * */

import java.util.Deque;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Scanner;

// http://www.henrikfrank.dk/abaptips/javaforsap/javabasics/calling_class_i_another_file.htm
public class PRR{
    
	private int cap ;       
	private int[] ArrivalTime ;
	private int[] BurstTime ; 
	private int[] WaitingTime; 
	private int[] Priority; 
	private int quant; 
	private int currentTime; 
    
	//Constructor for class PRR
	public PRR(int[]a ,int[] b,int[] prio, int quant) throws NotSameSizeException {
		//Throws exception if a and b and prio are not the same length
		if (a.length != b.length || b.length != prio.length || prio.length != a.length) throw new NotSameSizeException("Arrival and Burst or/and Priority don't contain same amount of values");
		this.cap = a.length;
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
		
		//Sort the process in term of arrival time also swapping the location of elements in BurstTime accordingly  
		sort( ArrivalTime ,BurstTime,quant ) ;
		computeWaitTime();
	} 

	//Accessor for ArrivalTime array
	public int[] getArrivalTime() {
		return this.ArrivalTime;
	}

	//Accessor for BurstTime array
	public int[] getBurstTime() {
		return this.BurstTime;
	}

	//Accessor for Priority array
	public int[] getPriority() {
		return this.Priority;
	}

	//Accessor for WaitingTime array
	public int[] getWaitingTime() {
		return this.WaitingTime;
	}

	//Accessor for timeQuantum
	public int getTimeQuantum() {
		return this.quant;
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
    
	/*Put each process multi level qeueu and start a RR schedule on those processes , keep doing until all process are done 
	 *A ArrivalTime check for the processes should be included 
	 * (See book page 214)
	 * */
	public void computeWaitTime(){
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
		    int[] wait = roundRobin(arrivalPrio,burstPrio,this.quant) ;
		    // Update the wait time of process based on the index proivided in prioIndex
		    // that store the index of processes that have the same priority
		    for(int i =0;i<wait.length;i++){ 
			WaitingTime[prioIndex.get(i)] = wait[i] ;
		    }
		}
	}
    
	/*Computes the average waiting time
	* */
	public float computeAvgTime(){
		int sum=0 ;
		for(int i=0;i<this.cap;i++){
		    sum+=WaitingTime[i]; 
		}
		return (float) sum/cap 
	}
    
	/*This method displays the Arrival times, Burst times, 
	* and Waiting times from the class
	* */
	public String displayInfo(){
		String info = "";
		info += "Round Robin (PRR)\n";
		info += "Process\tArrival\tBurst\tPrio\tWait\n" ;
		for(int i=0;i<this.cap;i++){
		    //Display Info here
		   info += String.format("%d\t%d\t%d\t%d\t%d\n",(i+1),ArrivalTime[i],BurstTime[i],Priority[i],WaitingTime[i]) ;
		}
		info += ("Average Wait Time:"+this.computeAvgTime()) ;
		return info;
	}
    
	/* The main method for this class asks the user to input values for
	* the Arrival, Burst Times, Quantum time, and Priority and then 
	* displays the info. If the arrays are of different size or does 
	* not contain a number, it'll say an error.
	* The program keeps asking the user to create a new PRR until they say "n"
	* */
	public static void main(String args[]){
		//The class take line of number as input  
		//and output the resulting table  
		//Get the number from the user

		//Scanner created for user input
		Scanner sc = new Scanner(System.in);

		//String for user option at end of loop
		String cont;

		System.out.println("-------PRR Program-------");
		System.out.println("_______________________________________________________________");

		//Creates a PRR object first time and then loops again and creates a new PRR if the user inputs "y"
		do {
			//Put a new PRR wont be created if Arrival and Burst are of two different sizes or if a non number value is entered
			try {
				    System.out.println("Enter Arrival Time (Seperated by commas ,):");
				    String Atime = sc.nextLine();
				    String[] AtimeS = Atime.replaceAll(" ", "").split(",");
				    int[] AtimeN = new int[AtimeS.length];
				    for (int i = 0; i<AtimeS.length; i++) {AtimeN[i] = Integer.valueOf(AtimeS[i]);}
				    System.out.println("Enter Burst Time (Seperated by commas ,):");
				    String Btime = sc.nextLine();
				    String[] BtimeS = Btime.replaceAll(" ", "").split(",");
				    int[] BtimeN = new int[BtimeS.length];
				    for (int i = 0; i<BtimeS.length; i++) {BtimeN[i] = Integer.valueOf(BtimeS[i]);}
				    System.out.println("Enter Priority (Seperated by commas ,):");
				    String prio = sc.nextLine();
				    String[] PrioS = prio.replaceAll(" ", "").split(",");
				    int[] PrioN = new int[PrioS.length];
				    for (int i = 0; i<PrioS.length; i++) {PrioN[i] = Integer.valueOf(PrioS[i]);}
				    System.out.println("Enter Quantum Number:");
				    int quant = Integer.valueOf(sc.nextLine());
				    PRR scheduler = new PRR(AtimeN, BtimeN, PrioN, quant);
				    System.out.println(scheduler.displayInfo());
			}
			//catches exception for if a non number value is passed to Arrival/Burst time
			catch (NumberFormatException e) {
				System.out.println("Arrival Time or Burst Time input had a non number entered!");
			}
			//catches exception if Burst and Arrival don't have the same amount of numbers
			catch (NotSameSizeException e) {
				System.out.println(e.getMessage());
			}

			//loops through options until user inputs "y" or "n"
			do {
				//Prints a line to sperate any new PRR creations
				System.out.println("_______________________________________________________________");

				System.out.println("Do you want to create a new test (y/n):");
				//gets user input for the option and makes it lowercase just incase
				cont = sc.nextLine().toLowerCase();
				if (cont.equals("y")) {System.out.println("Enter New Values");}
				else if (cont.equals("n")) {System.out.println("Thank You and Have A Nice Day! :)");}
				else {System.out.println("Not a option, try again!");}
			}while(!cont.equals("y") && !cont.equals("n"));

		}while(cont.equals("y"));
	}
	
	//Helper Methods
	
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
	
	//Helper Method for the roundRobin method
	private void  checkArrival( Queue<Integer> q , int[] arrive,boolean[] inQ,boolean[] complete) {
		int n = arrive.length  ;
		for(int i=0;i<n;i++){
		    if(arrive[i] <=this.currentTime && !inQ[i] && !complete[i]) {
			q.add(i);  
			inQ[i]= true ;
		    }
		}
    	}
}




