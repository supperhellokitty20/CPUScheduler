/* Round Robin to determine the average waiting time of  the processes given Arrival Time , Burst Time and Time Quantum   
 * @author : Tuan Nguyen & Syed Numair Shah
 * */
import java.util.Queue;
import java.util.LinkedList ; 
import java.util.Scanner; 

public  class RR{
	
	//cap holds the lengths for all arrays
    	private int cap ;
	
    	private int[] ArrivalTime ;
    	private int[] BurstTime ; 
    	private int[] WaitingTime; 
    	private int timeQuantum ;
	private int currentTime;
	
    	/* Constructor for RR class
     	* @param a this holds the array that sets ArrivalTime
     	* @param b this holds the array that sets BurstTime
	* @param quant this holds int value for the timeQuantum
     	* @throws NotSameSizeException based on if both parameters are not the same size
     	* */
    	public RR(int[] a , int[]b , int quant) throws NotSameSizeException{
        	//Throws exception if a and b are not the same length
		if (a.length != b.length) throw new NotSameSizeException("Arrival and Burst don't contain same amount of values");
		this.cap = a.length; 
		ArrivalTime = new int[cap] ;
		BurstTime = new int[cap] ;
		WaitingTime = new int[cap] ;
		timeQuantum  = quant;
		    this.currentTime = 0;
		//Deep coppy the element  
		for(int i=0;i<this.cap;i++){
		    ArrivalTime[i] = a[i] ;
		    BurstTime[i] = b[i] ;
		}
		
		//Sort the process in term of arrival time also swapping the location of elements in BurstTime accordingly
		sort(this.ArrivalTime,this.BurstTime, this.cap);
		computeWaitTime();
    	}
    
    	/* Accessor for ArrivalTime array
     	* @return int[] This returns the ArrivalTime array
     	* */
    	public int[] getArrivalTime() {
    		return this.ArrivalTime;
   	 }
    
  	/* Accessor for BurstTime array
     	* @return int[] This returns the BurstTime array
     	* */
    	public int[] getBurstTime() {
    		return this.BurstTime;
    	}
    
  	/* Accessor for WaitingTime array
     	* @return int[] This returns the WaitingTime array
     	* */
    	public int[] getWaitingTime() {
    		return this.WaitingTime;
    	}
    
    	/* Accessor for the timeQuantum int
     	* @return int This returns the timeQuantum
     	* */
    	public int getTimeQuantum() {
    		return this.timeQuantum;
    	}
    
	/* Run a round robin schedule on the processes,calculating the wait time in the
	 * processes
	 * @precondition :The function assumes that arrive time is sorted in ascending orer  
	 */
    	public void computeWaitTime(){
        	//This will store all of the index of the next process to work with
        	//The array rmbt will keep track of the remaining time left for the process 
       		int n = this.cap; 
		int[] wait = new int[n];
		int[] rmbt = new int[n];
		int[] timeCompleted = new int[n] ; 
		Queue<Integer>  q = new LinkedList<Integer>() ; 
		boolean[] complete = new boolean[n] ;
		int  countDone =0 ;
		boolean[] inQ =  new boolean[n];

		for(int i=0;i< n;i++){
			rmbt[i]= this.BurstTime[i] ;
			//Initialize all the compete as false 
			complete[i] = false ;
		}
		//Add the first process index in the queue  
        	//If the first process does not arrive the CPu is idle
        	while(this.currentTime<this.ArrivalTime[0]){
          		this.currentTime++ ;
        	}
		q.add(0) ;
		inQ[0] = true ; 
		while(!q.isEmpty()){
			//update(arrive ,burst, quant) ;
			int j = q.poll() ; // Get the first element and remove the first element

			/* The process will be complete in this time quantum , proceed to calculate the waiting time of the process  
			 * */
			if( rmbt[j]<=this.timeQuantum ) { 
				complete[j]  = true ;
				this.currentTime+= rmbt[j]  ;
				timeCompleted[j]  = currentTime ;  
				wait[j] = timeCompleted[j]-this.ArrivalTime[j]-this.BurstTime[j] ;
				if(wait[j]<0) wait[j] =0 ; 
				rmbt[j] =0; 
				countDone++ ;

				if(countDone!=n){
					//Check for new process to arrive in the Queue update the q  
					//checkArrival( ) ;
					checkArrival(q,this.ArrivalTime,inQ,complete)  ;
				}
			}
			/* The process doesn't finnish in this time quantum  
			 * */
			else{
				rmbt[j] -=this.timeQuantum ;
				this.currentTime+= this.timeQuantum ;
				if(countDone!=n){
					//Check new arrival
					//checkArrival() ;
					checkArrival(q,this.ArrivalTime,inQ,complete)  ;
				}
				//Push the incomplete process to the end of the queue 
				q.add(j) ;
			}
		}
		this.WaitingTime = wait;
    	}
    
	/*Computes the average waiting time
	* @return float this returns the avarage of all the classes waitingTime
	* */
	public float computeAvgTime(){
		int sum=0 ;
		for(int i=0;i<this.cap;i++){
		    sum+=WaitingTime[i]; 
		}
		return (float) sum/cap ;
	}
    
	/*This method displays the Arrival times, Burst times, 
    	* and Waiting times from the class
    	* @return String This returns a string that holds all the information from this class
    	* */
	public String displayInfo(){
		String info = "";
		info += "Round Robin (RR)\n";
		info += "Process\tArrival\t Burst \tWait\n" ;
		for(int i=0;i<this.cap;i++){
		    //Display Info here
		    info += String.format("%d\t%d\t%d\t%d\n",(i+1),ArrivalTime[i],BurstTime[i],WaitingTime[i]) ;
		}
		info += ("Average Wait Time:"+this.computeAvgTime()) ;
		return info;
	}
    
	/*The main method for this class asks the user to input values for
	* the Arrival, Burst Times, and Quantum Time and then displays the info. If the arrays
	* are of different size or does not contain a number, it'll say an error.
	* The program keeps asking the user to create a new RR until they input "n"
	* @param args unused
	* @exception NumberFormatException is caught if one of the values given in the arrays/quant is not a number
	* @exception NotSameSizeException is caught if the two arrays given by the user are not the same size
	* */
	public static void main(String args[]){ 
		//The class take line of number as input  
		//and output the resulting table  
		//Get the number from the user

		//Scanner created for user input
		Scanner sc = new Scanner(System.in);

		//String for user option at end of loop
		String cont;

		System.out.println("-------RR Program-------");
		System.out.println("_______________________________________________________________");

		//Creates a RR object first time and then loops again and creates a new RR if the user inputs "y"
		do {
			//Put a new RR wont be created if Arrival and Burst are of two different sizes or if a non number value is entered
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
				    System.out.println("Enter Quantum Number:");
				    int quant = Integer.valueOf(sc.nextLine());
				    RR scheduler = new RR(AtimeN, BtimeN, quant);
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
				//Prints a line to sperate any new RR creations
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

	/* Bubble Sort the arrive time array and burst array accordingly -> O(N)
     	* @param a first array that would be sorted on by accenting order
     	* @param b this array is sorted based on the first array
     	* @param n the size of both given array
     	* */ 
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
	
	/* swaps index a with index b within a given int array
     	* @param arr the given array
     	* @param a the first index to switch with
     	* @param b the second index to switch with index a
     	* */
	private void swap( int[] arr, int a ,int b){
		int x= arr[a] ;
		arr[a] = arr[b] ;
		arr[b] = x ; 
    	}
	
	/* Helper method for the computeWaitingTime method
     	* @param q holds the Queue for int values
     	* @param arrive holds all the arrival times in the int array
     	* @param inQ holds the boolean array for if its each process is in queue
	* @param complete holds boolean array for if each process has completed
     	* */
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
