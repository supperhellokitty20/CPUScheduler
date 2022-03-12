/* SJF (Shortest job first) will run the shortest job first, the class compute the waiting time for each process upon initialization 
 * and then compute the average waiting time for each processs
 * @author : Tuan Nguyen & Syed Numair Shah
 * */

import java.util.Scanner;

public class SJF { 
	/* ArrrivalTime array use to contain the  timestamps on the gantt chart does the process arrive 
	* BurstTime array use to contain the amount it needed to complete the process from the CPU  
	* */
	private int[] ArrivalTime; 
	private int[] BurstTime ; 
	private int[] WaitingTime ; 
	
	//cap is the length of the arrays, since they are all the same size
	private int cap ; 

	//Gantt chart in the schedule 

	//Construct a queu for the processes
	public SJF(int[] a, int[] b) throws NotSameSizeException {
		//Throws exception if a and b are not the same length
		if (a.length != b.length) throw new NotSameSizeException("Arrival and Burst don't contain same amount of values");
		this.cap = a.length;
		this.ArrivalTime = new int[cap] ;   
		this.BurstTime  = new int[cap] ;
		for(int i=0;i<cap;i++){
		    this.ArrivalTime[i]= a[i] ;
		    this.BurstTime[i] = b[i] ;
		}
		computeWaitingTime() ;
	}

	//Accessor for ArrivalTime array
	public int[] getArrivalTime() {
		return this.ArrivalTime;
	}

	//Accessor for BurstTime array
	public int[] getBurstTime() {
		return this.BurstTime;
	}

	//Accessor for WaitingTime array
	public int[] getWaitingTime() {
		return this.WaitingTime;
	}

	/* Compute the wait time for each process   
	* by simulating the gantt chart in 3 arrays 
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

	/*This method displays the Arrival times, Burst times, 
	* and Waiting times from the class
	* */
	public String displayInfo(){
		String info = "Shortest Job First (SJF)\n";
		info += "Process\tArrival\t Burst \tWait\n";
		for(int i=0;i<this.cap;i++){
		    //Display Info here
		    info += String.format("%d\t%d\t%d\t%d\n",(i+1),ArrivalTime[i],BurstTime[i],WaitingTime[i]) ;
		}
		info += ("Average Wait Time:"+this.computeAvgTime()) ;
		return info;
	}

	/*The main method for this class asks the user to input values for
	* the Arrival and Burst Times and then displays the info. If the arrays
	* are of different size or does not contain a number, it'll say an error.
	* The program keeps asking the user to create a new SJF until they say "n"*/
	public static void main( String[] args ){
		 //Get the number from the user

		//Scanner created for user input
		Scanner sc = new Scanner(System.in);

		//String for user option at end of loop
		String cont;

		System.out.println("-------SJF Program-------");
		System.out.println("_______________________________________________________________");

		//Creates a SJF object first time and then loops again and creates a new SJF if the user inputs "y"
		do {
			//Put a new SJF wont be created if Arrival and Burst are of two different sizes or if a non number value is entered
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
				    SJF scheduler = new SJF(AtimeN, BtimeN);
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
				//Prints a line to sperate any new SFJ creations
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
	
	//Helper Mehods

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
	
	//swaps index a with index b within a given int array
	private void swap( int[] arr, int a ,int b){
		int x= arr[a] ;
		arr[a] = arr[b] ;
		arr[b] = x ; 
    	}
}

//Custom Exception for Burst and Arrival

@SuppressWarnings("serial")
class NotSameSizeException extends Exception{
	
	public NotSameSizeException() {
		super();
	}
	
	public NotSameSizeException(String s) {
		super(s);
	}
}

