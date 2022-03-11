/* Round Robin to determine the average waiting time of  the processes given Arrival Time , Burst Time and Time Quantum   
 * @author : Tuan Nguyen & Syed Numair Shah
 * */
import java.util.Deque;
import java.util.LinkedList ; 
import java.util.Iterator; 
import java.util.Scanner; 

public  class RR{
    private int cap ;       
    private int[] ArrivalTime ;
    private int[] BurstTime ; 
    private int[] WaitingTime; 
    private int timeQuantum ; 
    
    public RR(int[] a , int[]b , int quant) throws NotSameSizeException{
        //Throws exception if a and b are not the same length
    	if (a.length != b.length) throw new NotSameSizeException("Arrival and Burst don't contain same amount of values");
        this.cap = a.length; 
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
    
    //Accessor for timeQuantum
    public int getTimeQuantum() {
    	return this.timeQuantum;
    }
    
    public void computeWaitTime(){
        //This will store all of the index of the next process to work with
        //The array rmbt will keep track of the remaining time left for the process 
        int currentTime = 0 ; 
        int[]  rmbt = new int[cap] ;
        Deque<Integer> q = new LinkedList<Integer>() ;    
        for(int i =0;i<this.cap ;i++){
            rmbt[i] = BurstTime[i]  ;
            q.add(i) ;
        }
        while(q.size()>0){
            int j = q.getFirst();
            if(ArrivalTime[j]<=currentTime){
                //Add the current process to ready qeueu if the process is not already in q 
                //Move the first element to the last element and continue  
                if(rmbt[j]>timeQuantum){
                    currentTime+=timeQuantum ; 
                    rmbt[j]-=timeQuantum ;
                    q.removeFirst() ;
                    q.addLast(j) ;
                }else{
                    //The last cycle for this process
                    currentTime+=rmbt[j] ;
                    WaitingTime[j] = currentTime - BurstTime[j] ;
                    rmbt[j]=0 ; // This process is complete 
                    q.removeFirst() ;
                }
            }
            //If the process does not arrive yet move on with the time
            else{
                currentTime+=timeQuantum ; 
                continue; 
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
        return (float) sum/cap ;
    }
    
    /*This method displays the Arrival times, Burst times, 
     * and Waiting times from the class
     * */
    public void displayInfo(){
        System.out.println("Round Robin (RR)");
        System.out.println("Process\tArrival\t Burst \tWait") ;
        for(int i=0;i<this.cap;i++){
            //Display Info here
            System.out.printf("%d\t%d\t%d\t%d\n",i,ArrivalTime[i],BurstTime[i],WaitingTime[i]) ;
        }
        System.out.println("Average Wait Time:"+this.computeAvgTime()) ;

    }
    
    /*The main method for this class asks the user to input values for
     * the Arrival, Burst Times, and Quantum Time and then displays the info. If the arrays
     * are of different size or does not contain a number, it'll say an error.
     * The program keeps asking the user to create a new RR until they input "n"*/
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
			    int quant = sc.nextInt();
			    RR scheduler = new RR(AtimeN, BtimeN, quant);
			    scheduler.displayInfo();
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
} 
