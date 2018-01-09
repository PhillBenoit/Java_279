import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Hw3 {

	//name of the input file
	private static final String SOURCE_FILE = "HW3_Accounts.txt";

	//headers for the columns with student info
	private static final String[] headers1 = {"Account ", "", "", "", "Credit", "Credit  ",
			"Ending  ", "Account", "Penalty", "Average ", "Interest"};
	private static final String[] headers2 = {"Number ", "Customer ID", "Last Name ",
			"First Name ", "Score ", "Limit   ", "Balance ", "Status ", "Fee  ", "Balance ", "Rate  "};

	//string length for the column sizes
	private static int[] COLUMN_WIDTHS = new int[headers1.length];

	public static void main(String[] args) {
		IR.displayProgramInformation("Hw3", "9-11-17", "Homework 3", "",
				"Reads input from a file and calculates average scores,",
				"standard deviation of the average scores and assigns a letter grade");
		setColumnWidths();

		displayOutput();

		IR.displayEndOfProgram();
	}

	//sets column widths based on size of longest string in header
	private static void setColumnWidths() {
		for (int index = 0; index < COLUMN_WIDTHS.length; index++)
			COLUMN_WIDTHS[index] = Math.max(headers1[index].length(), headers2[index].length()) + 1;
	}

	//displays output for the program
	private static void displayOutput() {
		//headers
		displayHeader(headers1);
		displayHeader(headers2);

		//separator between headers and students
		displaySepataror();

		//file contents
		displayFile();

		//extra visual space
		System.out.println();
	}

	//displays contents of the file
	private static void displayFile() {
		//counts lines for error reporting
		int lineCounter = 0;
		
		//open file
		File input = new File(SOURCE_FILE);
		Scanner scan = tryFile(input);
		
		//boolean used to keep track of found error
		boolean continue_loop = true;
		
		//loops until end of file or an error is found
		while (scan.hasNextLine() && continue_loop) {
			//get record from file
			CardInfo record = tryRecord(scan, lineCounter);
			
			//displays record unless an error was found
			if (record != null) {
				lineCounter += 9;
				CardInfo.displayInfo(record, COLUMN_WIDTHS);
			} else continue_loop = false;
		}
		//displays separator if no errors were found
		if (continue_loop) displaySepataror();
		
		//close file
		scan.close();
	}

	
	//reads a record from file
	private static CardInfo tryRecord(Scanner scan, int lineCounter) {
		try {
			lineCounter++;
			int account_number = scan.nextInt();
			scan.nextLine();

			lineCounter++;
			double beginning_balance = scan.nextDouble();
			scan.nextLine();

			lineCounter++;
			double ending_balance = scan.nextDouble();
			scan.nextLine();

			lineCounter++;
			double credit_limit = scan.nextDouble();
			scan.nextLine();

			lineCounter++;
			double interest_rate = scan.nextDouble();
			scan.nextLine();

			lineCounter++;
			int customer_id = scan.nextInt();
			scan.nextLine();

			lineCounter++;
			String last_name = scan.nextLine();

			lineCounter++;
			String first_name = scan.nextLine();

			lineCounter++;
			int credit_score = scan.nextInt();
			scan.nextLine();

			return new CardInfo(account_number, beginning_balance, ending_balance,
					credit_limit, interest_rate, customer_id, first_name, last_name, credit_score);
			//exception for invalid type information read from scanner
		} catch(InputMismatchException i) {
			displaySepataror();
			System.out.println();
			System.out.println(
					String.format("Data type mismatch on line %d", lineCounter));
			//exception for invalid number of lines in the file
		} catch (NoSuchElementException e) {
			displaySepataror();
			System.out.println();
			System.out.println(
					String.format("File ended prematurly on line %d", lineCounter));
		}
		return null;
	}

	//catches file not found exception while opening file
	private static Scanner tryFile(File f) {
		try {
			return new Scanner(f);
		} catch (FileNotFoundException e) {
			System.err.println("Input file was not found");
			System.exit(-1);
		}
		return null;
	}

	//displays separator based on the size of each column
	//first space is empty, remaining spaces filled with "-"
	private static void displaySepataror() {
		for (int index = 0; index < COLUMN_WIDTHS.length; index++) {
			System.out.print(" ");
			for (int counter = 1; counter < COLUMN_WIDTHS[index]; counter++)
				System.out.print("-");
		}
		System.out.println();
	}

	//displays a passed header
	private static void displayHeader(String[] h) {
		for (int index = 0; index < h.length; index++)
			IR.displayCell(COLUMN_WIDTHS[index], h[index]);
		System.out.println();
	}

}
