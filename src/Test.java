import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class Test {

	public static String SJFtest1() {
		int[] Arrival = { 0, 1, 2, 3 };
		int[] Burst = { 8, 4, 9, 5 };
		try {
			SJF test = new SJF(Arrival, Burst);
			return test.displayInfo();
		} catch (NotSameSizeException e) {
			return (e.getMessage());
		}
	}

	public static String SJFtest2() {
		int[] Arrival = { 2, 3, 0, 1 };
		int[] Burst = { 9, 5, 8, 4 };
		try {
			SJF test = new SJF(Arrival, Burst);
			return test.displayInfo();
		} catch (NotSameSizeException e) {
			return (e.getMessage());
		}
	}

	public static String SJFtest3() {
		int[] Arrival = { 0 };
		int[] Burst = { 5 };
		try {
			SJF test = new SJF(Arrival, Burst);
			return test.displayInfo();
		} catch (NotSameSizeException e) {
			return (e.getMessage());
		}
	}

	public static String SJFtest4() {
		int[] Arrival = { 7, 2 };
		int[] Burst = { 8, 3 };
		try {
			SJF test = new SJF(Arrival, Burst);
			return test.displayInfo();
		} catch (NotSameSizeException e) {
			return (e.getMessage());
		}
	}

	public static String SJFtest5() {
		int[] Arrival = { 2, 5, 1, 0, 4 };
		int[] Burst = { 6, 2, 8, 3, 4 };
		try {
			SJF test = new SJF(Arrival, Burst);
			return test.displayInfo();
		} catch (NotSameSizeException e) {
			return (e.getMessage());
		}
	}

	public static String SJFtest6() {
		int[] Arrival = { 2, 5, 1, 0, 1 };
		int[] Burst = { 6, 6, 8, 10, 1 };
		try {
			SJF test = new SJF(Arrival, Burst);
			return test.displayInfo();
		} catch (NotSameSizeException e) {
			return (e.getMessage());
		}
	}

	public static String RRtest1() {
		int[] Arrival = { 0, 1, 2, 3 };
		int[] Burst = { 8, 4, 9, 5 };
		int q = 2;
		try {
			RR test = new RR(Arrival, Burst, q);
			return test.displayInfo();
		} catch (NotSameSizeException e) {
			return (e.getMessage());
		}
	}

	public static String RRtest2() {
		int[] Arrival = { 0, 1, 2 };
		int[] Burst = { 24, 3, 3 };
		int q = 4;
		try {
			RR test = new RR(Arrival, Burst, q);
			return test.displayInfo();
		} catch (NotSameSizeException e) {
			return (e.getMessage());
		}
	}

	public static String RRtest3() {
		int[] Arrival = { 0 };
		int[] Burst = { 5 };
		int q = 1;
		try {
			RR test = new RR(Arrival, Burst, q);
			return test.displayInfo();
		} catch (NotSameSizeException e) {
			return (e.getMessage());
		}
	}

	public static String RRtest4() {
		int[] Arrival = { 3, 1 };
		int[] Burst = { 11, 9 };
		int q = 3;
		try {
			RR test = new RR(Arrival, Burst, q);
			return test.displayInfo();
		} catch (NotSameSizeException e) {
			return (e.getMessage());
		}
	}

	public static String RRtest5() {
		int[] Arrival = { 0, 1, 2, 3, 4, 6 };
		int[] Burst = { 4, 5, 2, 1, 6, 3 };
		int q = 2;
		try {
			RR test = new RR(Arrival, Burst, q);
			return test.displayInfo();
		} catch (NotSameSizeException e) {
			return (e.getMessage());
		}
	}

	public static String PRRtest1() {
		int[] Arrival = { 0, 0, 0 , 0, 0};
		int[] Burst = { 10, 1, 2, 1, 5 };
		int[] Prio = {3,1,4,5,2};
		int q = 10;
		try {
			PRR test = new PRR(Arrival, Burst, Prio, q);
			return test.displayInfo();
		} catch (NotSameSizeException e) {
			return (e.getMessage());
		}
	}

	public static String PRRtest2() {
		int[] Arrival = { 0, 1, 2 , 3, 4};
		int[] Burst = { 4, 5, 8, 7, 3 };
		int[] Prio = {3,2,2,1,3};
		int q = 2;
		try {
			PRR test = new PRR(Arrival, Burst, Prio, q);
			return test.displayInfo();
		} catch (NotSameSizeException e) {
			return (e.getMessage());
		}
	}
	public static String PRRtest3() {
		int[] Arrival = { 3, 4, 0 , 2, 1};
		int[] Burst = {7, 3, 4, 8, 5 };
		int[] Prio = {1,3,3,2,2};
		int q = 2;
		try {
			PRR test = new PRR(Arrival, Burst, Prio, q);
			return test.displayInfo();
		} catch (NotSameSizeException e) {
			return (e.getMessage());
		}
	}

	public static void main(String[] args) throws IOException {

		File f = new File("Test_Output");
		f.createNewFile();
		FileWriter fw = new FileWriter("Test_Output");
		fw.write("Test Output\n_________________________________________\nSJF Test 1:\n" + SJFtest1()
				+ "\n\nSJF Test 2:\n" + SJFtest2() + "\n\nSJF Test 3:\n" + SJFtest3() + "\n\nSJF Test 4:\n" + SJFtest4()
				+ "\n\nSJF Test 5:\n" + SJFtest5() + "\n\nSJF Test 6:\n" + SJFtest6() + "\n");
		fw.write("_________________________________________\nRR Test 1:\n" + RRtest1() + "\n\nRR Test 2:\n" + RRtest2()
				+ "\n\nRR Test 3:\n" + RRtest3() + "\n\nRR Test 4:\n" + RRtest4() + "\n\nRR Test 5:\n" + RRtest5()
				+ "\n");
		fw.write("_________________________________________\nPRR Test 1:\n" + PRRtest1() + "\n\nPRR Test 2:\n" + PRRtest2()
		+ "\n\nPRR Test 3:\n" + PRRtest3() + "\n");
		fw.close();
	}

}
