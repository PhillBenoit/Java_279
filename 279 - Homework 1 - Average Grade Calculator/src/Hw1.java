/*
 * Phillip Benoit
 * CIS 279
 * Fall 2017
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Hw1 {

	//name of the input file
	private static final String SOURCE_FILE = "HW1_Students.txt";
	
	//headers for the columns with student info
	private static final String[] headers1 = {"","","Relative to","Letter"};
	private static final String[] headers2 = {"Student Name","Score","Average","Grade"};
	
	//string length for the column sizes
	private static int[] COLUMN_WIDTHS = new int[headers1.length];
	
	//global variable so calculation can be done while loading
	private static double class_average;
	
	//main program
	public static void main(String[] args) {
		IR.displayProgramInformation("Hw1", "8-28-17", "Homework 1", "Average Grade Calculator",
				"Reads input from a file and calculates average scores,",
				"standard deviation of the average scores and assigns a letter grade");
		setColumnWidths();
		ArrayList<StudentScore> students = getStudents();
		displayClassInfo(students);
		IR.displayEndOfProgram();
	}

	//sets column widths based on size of longest string in header
	private static void setColumnWidths() {
		for (int index = 0; index < COLUMN_WIDTHS.length; index++)
			COLUMN_WIDTHS[index] = Math.max(headers1[index].length(), headers2[index].length()) + 1;
	}

	//displays output
	private static void displayClassInfo(ArrayList<StudentScore> students) {
		//get standard deviation before displaying information
		double deviation = getDeviation(students);
		
		//average and deviation
		System.out.println(String.format("Class average: %f"
				+ "   Standard deviation: %f\n", class_average, deviation));
		
		//headers
		displayHeader(headers1);
		displayHeader(headers2);
		
		//separator between headers and students
		displaySepataror();
		
		//students
		for (StudentScore student : students)
			StudentScore.printStudent(student, class_average, COLUMN_WIDTHS);		
	}

	//calculates standard deviation of an array list of student scores
	private static double getDeviation(ArrayList<StudentScore> students) {
		double deviation = 0;
		for (StudentScore student : students)
			deviation += Math.pow(student.getScore() - class_average, 2);
		//wrapped in if statement to prevent dividing by 0
		if (students.size() > 1) deviation /= students.size() - 1;
		deviation = Math.sqrt(deviation);
		return deviation;
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

	//reads in students from file
	//calculates average along the way
	private static ArrayList<StudentScore> getStudents() {
		ArrayList<StudentScore> students = new ArrayList<StudentScore>();
		class_average = 0;
		//keeps track of the current line to make error messages more helpful
		int lineCounter = 0;
		File input = new File(SOURCE_FILE);
		Scanner scan = tryFile(input);
		while (scan.hasNextLine()) {
			StudentScore student = tryStudent(scan, lineCounter);
			//increases line counter by 3 for each line read in the previous statement
			lineCounter += 3;
			students.add(student);
			class_average += student.getScore();
		}
		scan.close();
		//final calculation for average wrapped in if statement to prevent dividing by 0
		if (students.size() > 0) class_average /= students.size();
		return students;
	}

	//reads a student score entry from the input scanner
	//assigns max column width for name column along the way
	private static StudentScore tryStudent(Scanner scan, int lineCounter) {
		try {
			lineCounter++;
			int id = scan.nextInt();
			scan.nextLine();
			
			lineCounter++;
			String name = scan.nextLine();
			//increases the size of the name column if the name is the longest to far
			COLUMN_WIDTHS[0] = Math.max(COLUMN_WIDTHS[0], name.length() + 1);
			
			lineCounter++;
			double score = scan.nextDouble();
			scan.nextLine();
			
			return new StudentScore(id, name, score);
		//exception for invalid type information read from scanner
		} catch(InputMismatchException i) {
			System.err.println(
					String.format("Data type mismatch on line %d", lineCounter));
			scan.close();
            System.exit(-1);
        //exception for invalid number of lines in the file
		} catch (NoSuchElementException e) {
            System.err.println(
            		String.format("File ended prematurly on line %d", lineCounter));
            scan.close();
            System.exit(-1);
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

}
