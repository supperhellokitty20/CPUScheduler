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
            int[] wait = roundRobin(arrivalPrio,burstPrio,currentTime,this.quant) ;
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
        return (float) sum/cap ;

    }
    
     /*This method displays the Arrival times, Burst times, 
     * and Waiting times from the class
     * */
    public void displayInfo(){
        System.out.println("Round Robin (RR)");
        System.out.println("Process\tArrival\tBurst\tPrio\tWait") ;
        for(int i=0;i<this.cap;i++){
            //Display Info here
            System.out.printf("%d\t%d\t%d\t%d\t%d\n",(i+1),ArrivalTime[i],BurstTime[i],Priority[i],WaitingTime[i]) ;
        }
        System.out.println("Average Wait Time:"+this.computeAvgTime()) ;

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
			    int quant = sc.nextInt();
			    PRR scheduler = new PRR(AtimeN, BtimeN, PrioN, quant);
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
}




