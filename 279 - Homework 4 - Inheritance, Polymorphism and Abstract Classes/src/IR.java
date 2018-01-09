/*******************************************************************************************************************
 * IR.java
 * Author: David A. Freitag
 * Edited by: Phillip Benoit 
 * Pima Community College 
 * Last edited September 18, 2017
 ****************************************************************************************************************** 
 * This program gets input from a user using Scanner. 
 * Use at your own risk. Test your program well. No guarantee this code works in all situations. 
 ******************************************************************************************************************/ 
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Scanner;

public class IR {
    //Putting the Scanner object here makes it global so it does not have to be passed to modules.
    static Scanner keyboard = new Scanner(System.in);
    
    private static final String LOW_MSG="The value you entered is below the minimum of ";
    private static final String HIGH_MSG="The value you entered is above the maximum of ";
    private static final String CONFIRMATION="You entered: ";
    
    public static final NumberFormat percent_format = NumberFormat.getPercentInstance();
    public static final NumberFormat money_format = NumberFormat.getCurrencyInstance();

    public static void main(String[] args) {
        /** Main () - 
         * This program demonstrates various generalized input routines. 
         * This program also demonstrates generating a random number.
         * @param args Arguments can be passed to this program but they are not used. 
         */
        
        displayProgramInformation();

        do {
            String stringData = getString("Please enter a word or two");
            System.out.println("This is what you entered: " + stringData);

            int intData = getInteger("Please enter an integer");     
            System.out.println("This is what you entered: " + intData);

            intData = getIntegerBetweenLowAndHigh("Please enter a number between 1 and 12", 1, 12);     
            System.out.println("This is what you entered: " + intData);

            //Generating random numbers. 
            int numberOfRandomNbrs = getIntegerBetweenLowAndHigh("How many random numbers do you want to generate? (1 to 20)", 1, 20);
            int highNumber = getIntegerGTE("What is the highest random number to be generated?", 1);
            for (int i = 0; i < numberOfRandomNbrs; i++) {
                System.out.println("random number " + (i + 1) + ": " + getRandomNumber(0, highNumber));
            }

        } while (getYorN("\nDo you want to start over? (y/n)"));

        //close the Scanner
        closeScanner();

        displayEndOfProgram();
    }//end of main

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GENERALIZED VALIDATION FUNCTIONS 
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /** legacy support for older versions of IR 
     */
    public static int getIntegerBetweenLowAndHigh(String msg, int low, int high, String legacy) {
        return getIntegerBetweenLowAndHigh(msg, low, high);
    }
    
    /** Gets an integer greater than and less than the supplied parameters. 
     * Rejects null entries, any number of spaces, and non-numbers.
     * @param msg is the text that will be displayed the user to ask them to enter a value.
     * @param low is the lowest acceptable input value.
     * @param high is the highest acceptable input value.
     * @return Returns an int from the keyboard. 
     */
    public static int getIntegerBetweenLowAndHigh(String msg, int low, int high) {
        msg = msg +	String.format(": (%d - %d)", low, high);
    	int number = getInteger(msg);
        while (number < low || number > high) {
            if (number < low) System.err.println(LOW_MSG + low);
            if (number > high) System.err.println(HIGH_MSG + high);
            number = getInteger(msg);
        }
        return number;
    }

    /** Gets a float greater than and less than the supplied parameters. 
     * Rejects null entries, any number of spaces, and non-numbers.
     * @param msg is the text that will be displayed the user to ask them to enter a value.
     * @param low is the lowest acceptable input value.
     * @param high is the highest acceptable input value.
     * @return Returns the input from the keyboard. 
     */
    public static float getFloatBetweenLowAndHigh(String msg, float low, float high) {
    	msg = msg +	String.format(": (%f - %f)", low, high);
    	float number = getFloat(msg);
        while (number < low || number > high) {
            if (number < low) System.err.println(LOW_MSG + low);
            if (number > high) System.err.println(HIGH_MSG + high);
            number = getFloat(msg);
        }
        return number;
    }
    
    /** Gets a double precision floating point greater than and less than the supplied parameters. 
     * Rejects null entries, any number of spaces, and non-numbers.
     * @param msg is the text that will be displayed the user to ask them to enter a value.
     * @param low is the lowest acceptable input value.
     * @param high is the highest acceptable input value.
     * @return Returns the input from the keyboard. 
     */
    public static Double getDoubleBetweenLowAndHigh(String msg, double low, double high) {
    	msg = msg +	String.format(": (%f - %f)", low, high);
    	double number = getDouble(msg);
        while (number < low || number > high) {
            if (number < low) System.err.println(LOW_MSG + low);
            if (number > high) System.err.println(HIGH_MSG + high);
            number = getDouble(msg);
        }
        return number;
    }
    
    /** Gets a big decimal greater than and less than the supplied parameters. 
     * Rejects null entries, any number of spaces, and non-numbers.
     * @param msg is the text that will be displayed the user to ask them to enter a value.
     * @param low is the lowest acceptable input value.
     * @param high is the highest acceptable input value.
     * @return Returns the input from the keyboard. 
     */
    public static BigDecimal getBDBetweenLowAndHigh(String msg, BigDecimal low, BigDecimal high) {
        BigDecimal number = BigDecimal.valueOf(getDouble(msg));
        while (number.compareTo(low) == -1 || number.compareTo(high) == 1) {
            if (number.compareTo(low) == -1) System.err.println(LOW_MSG + low);
            if (number.compareTo(high) == 1) System.err.println(HIGH_MSG + high);
            number = BigDecimal.valueOf(getDouble(msg));
        }
        return number;
    }
    
    /** Gets an integer greater than the supplied parameter. 
     * Rejects null entries, any number of spaces, and non-numbers.
     * @param msg is the text that will be displayed the user to ask them to enter a value.
     * @param low is the highest unacceptable input value. 
     * @return Returns an int from the keyboard. 
     */ 
    public static int getIntegerGT(String msg, int low, String errorMsg) {
        int number = getInteger(msg);
        while (number <= low) {
            System.err.println(errorMsg);
            number = getInteger(msg);
        }
        return number;
    }

    /** Gets an integer greater than or equal to the supplied parameter. 
     * Rejects null entries, any number of spaces, and non-numbers.
     * @param msg is the text that will be displayed the user to ask them to enter a value.
     * @param low is the lowest acceptable input value.
     * @return Returns an int from the keyboard. 
     */ 
    public static int getIntegerGTE(String msg, int low) {
        int number = getInteger(msg);
        while (number < low) {
            System.err.println("Invalid input. Number is out of range.");
            number = getInteger(msg);
        }
        return number;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GENERALIZED INPUT FUNCTIONS 
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /** Gets a String from the keyboard. Returns the first character of that string.
     * @param msg is the text that will be displayed the user to ask them to enter a value.
     * @return Returns the first character of the input string. 
     */ 
    public static char getChar(String msg) {
        char input;
        System.out.println(msg);
        input = keyboard.nextLine().charAt(0);
        System.out.println(CONFIRMATION + input + "\n");
        return input;
    }
    
    /** Gets a String from the keyboard. Returns a letter as an upper case letter.
     * @param msg is the text that will be displayed the user to ask them to enter a value.
     * @return Returns the letter as an upper case letter.
     */ 
    public static char getLetter(String msg) {
        String input;
        do {
            System.out.println(msg);
            input = keyboard.nextLine();
        } while (!tryCase(input));
        System.out.println(CONFIRMATION + input + "\n");
        return input.toUpperCase().charAt(0);
    }
    
  //------------------------------------------------------------------------------------------------------------------
    /** Gets a String and returns a boolean that will tell you if it has a single letter
     * @param test is the String to test for integer data.
     * @return Returns a boolean that tells if test will parse in to an integer 
     */
    public static boolean tryCase(String test){
        if(!Character.isLetter(test.charAt(0))) {
            System.err.println("That is not a single letter. Try again.");
            return false;
        }
        try{
            test.charAt(1);
            System.err.println("There is more than one character in your string. Try again.");
            return false;
        } catch (IndexOutOfBoundsException e) {
            return true;
        }
    }
    
    /** Gets a String from the keyboard. Rejects null entry or any number of spaces.
     * @param msg is the text that will be displayed the user to ask them to enter a value.
     * @return Returns a String from the keyboard. 
     */ 
    public static String getString(String msg) {
        String answer = "";
        System.out.println(msg);
        try {
            answer = keyboard.nextLine(); 
        }
        catch (Exception e) {
            System.err.println("Error reading input from user. Ending program.");
            System.exit(-1);
        } 
        

        while (answer.replace(" ", "").equals("")) {
            System.err.println("Error: Missing input.");
            try {
                System.out.println(msg);
                answer = keyboard.nextLine(); 
            }
            catch (Exception e) {
                System.err.println("Error reading input from user. Ending program.");
                System.exit(-1);
            } 
        }
        System.out.println(CONFIRMATION + answer + "\n");
        return answer;            
    }

    //------------------------------------------------------------------------------------------------------------------
    /** Gets an Integer from the keyboard. Rejects null, spaces and non-integers.
     * @param msg is the text that will be displayed the user to ask them to enter a number.
     * @return Returns an int from input. 
     */  
    public static int getInteger(String msg) {
        String stringInput;
        do {
            System.out.println(msg);
            stringInput = keyboard.nextLine();
        } while (!tryInt(stringInput));
        int number = Integer.parseInt(stringInput);
        System.out.println(CONFIRMATION + number + "\n");
        return number;
    }
    
  //------------------------------------------------------------------------------------------------------------------
    /** Gets a String and returns a boolean that will tell you if it will parse in to an integer
     * @param test is the String to test for integer data.
     * @return Returns a boolean that tells if test will parse in to an integer 
     */
    public static boolean tryInt(String test){//boolean test for valid integer data
        try{
            Integer.parseInt(test);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Invalid integer. Try again.");
            return false;
        }
    }

    
  //------------------------------------------------------------------------------------------------------------------
    /** Gets a float from the keyboard. Rejects null, spaces and non-floating point numbers.
     * @param msg is the text that will be displayed the user to ask them to enter a number.
     * @return Returns a float from input. 
     */  
    public static float getFloat(String msg) {
        String stringInput;
        do {
            System.out.println(msg);
            stringInput = keyboard.nextLine();
        } while (!tryFloat(stringInput));
        float number = Float.parseFloat(stringInput);
        return number;
    }
    
  //------------------------------------------------------------------------------------------------------------------
    /** Gets a String and returns a boolean that will tell you if it will parse in to a floating point
     * @param test is the String to test for floating point data.
     * @return Returns a boolean that tells if test will parse in to a floating point 
     */
    public static boolean tryFloat(String test){
        try{
            Float.parseFloat(test);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Invalid floting point number. Try again.");
            return false;
        }
    }
    
    //------------------------------------------------------------------------------------------------------------------
    /** Gets a Double from the keyboard. Rejects null, spaces and non-numbers.
     * @param msg is the text that will be displayed the user to ask them to enter a number.
     * @return Returns a double from the keyboard. 
     */ 
    public static double getDouble(String msg) {
        String stringInput;
        do {
            System.out.println(msg);
            stringInput = keyboard.nextLine();
        } while (!tryDouble(stringInput));
        System.out.println(CONFIRMATION + stringInput + "\n");
        double number = Double.parseDouble(stringInput);
        return number;
    }
    
    /** Gets a String and returns a boolean that will tell you if it will parse in to a double
     * @param test is the String to test for integer data.
     * @return Returns a boolean that tells if test will parse in to a double
     */
    public static boolean tryDouble(String test){//boolean test for valid double data
        try{
            Double.parseDouble(test);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Invalid Double. Try again.");
            return false;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /** Gets a Yes or No answer from the keyboard. Calls getString to rejects null input and spaces.
     * @param msg is the text that will be displayed the user.
     * @return Returns a boolean value. True = yes; False = no. 
     */ 
    public static boolean getYorN(String msg) {
        String answer = getString(msg);

        while (answer.compareToIgnoreCase("y")   != 0 
                && answer.compareToIgnoreCase("yes") != 0 
                && answer.compareToIgnoreCase("n")   != 0 
                && answer.compareToIgnoreCase("no")  != 0) {

            if (answer.replace(" ", "").equals("")) {
                System.err.println("Error: Missing y/n input.");
            } else {
                if (answer.compareToIgnoreCase("y")   != 0 
                        && answer.compareToIgnoreCase("yes") != 0 
                        && answer.compareToIgnoreCase("n")   != 0 
                        && answer.compareToIgnoreCase("no")  != 0) {
                    System.err.println("Error: Unexpected input.");
                }
            }
            answer = getString(msg);
        } 

        if  (answer.compareToIgnoreCase("y")   == 0  
                || answer.compareToIgnoreCase("yes") == 0) {
            return true;
        } 
        else {
            return false;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /** Closes the scanner.
     */ 
    public static void closeScanner() {
        try { 
            if(keyboard != null) {
                keyboard.close(); 
            }
        } 
        catch (Exception e) { // (Exception) catches all errors java might throw here
            System.err.println("Error closing reader.");
        }
    }
    
    //------------------------------------------------------------------------------------------------------------------
    /** Generates a random number between low and high, inclusive.
     * @param low is the smallest number that will be randomly generated.
     * @param high is the largest number that will be randomly generated.
     * @return Returns the random number as an integer.
     */
    public static int getRandomNumber (int low, int high) {
        return (int)(Math.random() * ((high + 1) - low)) + low;
    }
    
  //------------------------------------------------------------------------------------------------------------------
    /** Displays IR information
     */
    public static void displayProgramInformation() {
        System.out.println("********************************************************************************");
        System.out.println("* IR.java                                                                     *");
        System.out.println("* Author: David A. Freitag                                                     *");
        System.out.println("* Edited by: Phillip Benoit 9-18-17                                           *");
        System.out.println("********************************************************************************"); 
        System.out.println("* This program gets input from a user using Scanner. Use at your own risk.     *");
        System.out.println("* Test your program well. No guarantee this code works in all situations.      *");
        System.out.println("********************************************************************************");
        System.out.println("");
     }
    
    /** Displays formatted information about a program
     * @param program is the class name
     * @param date is the date created
     * @param assignment is the Lab or Exercise identifier from the course syllabus
     * @param assignmentName is a short description of what your program does
     * @param description1 is detailed information about what your program does
     * @param description2 is available for more detail
     */
    public static void displayProgramInformation(String program, String date, String assignment, String assignmentName, String description1, String description2) {
        program += ".java";
        date = "Created by: Phillip Benoit " + date;
        System.out.println("********************************************************************************");
        System.out.format("* %-77s*\n", program);
        System.out.format("* %-77s*\n", date);
        System.out.println("* CIS 279 - Java                                                               *"); 
        System.out.println("* Pima Community College - Fall 2017                                           *");
        System.out.println("********************************************************************************"); 
        System.out.format("* %-77s*\n", assignment);
        System.out.format("* %-77s*\n", assignmentName);
        System.out.println("********************************************************************************"); 
        System.out.format("* %-77s*\n", description1);
        System.out.format("* %-77s*\n", description2);
        System.out.println("********************************************************************************");
        System.out.println("");
     }
    
    /**Displays an end of program message
     */
    public static void displayEndOfProgram() {
        closeScanner();
    	System.out.println("\"`-._,-'\"`-._,-'\"`-._,-'\"`-._,-'END OF PROGRAM\"`-._,-'\"`-._,-'\"`-._,-'\"`-._,-'");
    }
    
    /**
     * Pauses a program until 'Enter' is pressed
     */
    static void pause() {
        System.out.println("Press 'Enter' to coninue.");
        keyboard.nextLine();
    }
    
    /**
     * Used in loops to display tables.  Trims text for buffer.
     * @param cellWidth Maximum size of the cell (integer count of characters)
     * @param text Text to be printed
     */
    static void displayCell(int cellWidth, String text) {
        int max = Math.min(text.length(), cellWidth-1); 
        System.out.print(String.format("%" + cellWidth + "s", text.substring(0, max)));
    }

}//end of class