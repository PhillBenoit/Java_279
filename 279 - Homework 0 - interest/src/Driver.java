public class Driver {
	
	//column headers
	private static final String[] header1 = {"","","months","monthly","rate","total"};
	private static final String[] header2 = {"month","deposit","accrued","rate","accrued","value"};
	
	//separator string
	private static final String separator = "--------";
	
	//defines max column width for formatting
	private static final int column_width = separator.length() + 1;
	
	//main program
	public static void main(String[] args) {
		IR.displayProgramInformation("Driver", "8/23/17",
				"Homework 0", "Interest",
				"Calculates and displays interest accrued over several months", "");
		
		// Number of months
		System.out.println();
		int months = IR.getIntegerBetweenLowAndHigh("Enter the number of months",
				1, 240);
		
		// Periodic deposit
		double deposit = IR.getDoubleBetweenLowAndHigh("Enter the amount deposited every month",
				0.01, 10000);
		
		// Annual interest
		double interest = IR.getDoubleBetweenLowAndHigh("Enter the anual interest rate (in percent)",
				.01, 30);
		interest = interest / 100;
		
		// Monthly interest rate
		double monthly_interest = 1 + (interest/12);
		
		// declare totals
		double total = 0;
		double yearly_total = 0;
		
		//declare strings for values that don't change
		String deposit_string = Double.toString(deposit),
				monthly_interest_string = Double.toString(monthly_interest);
		
		//print headers
		printRow(header1);
		printRow(header2);
		printSeparator();
		
		// Perform a loop compounds the interest
		for (int month = 1; month < months + 1; month++) {
			
			//declare variables that change from month to month
			int months_accrued = months - month + 1;
			double rate_accrued = Math.pow(monthly_interest, months_accrued);
			double monthly_total = deposit * rate_accrued;
			
			//add to the yearly total
			yearly_total += monthly_total;
			
			// Output monthly values
			String[] row_data = {Integer.toString(month),
					deposit_string,
					Integer.toString(months_accrued),
					monthly_interest_string,
					Double.toString(rate_accrued),
					Double.toString(monthly_total)};
			printRow(row_data);
			
			//display yearly total, add it to the grand total then reset
			if (month % 12 == 0) {
				total += printYearly(yearly_total);
				yearly_total = 0;
			}
		}
		
		//Display yearly total if it had not already been displayed		
		if (months % 12 != 0) total += printYearly(yearly_total);
		
		// Show compounded value
		System.out.println("Total deposited: " + formatTotal(deposit * months));
		System.out.println("Total with interest: " + formatTotal(total));
		
		IR.pause();
		IR.displayEndOfProgram();
	}
	
	private static double printYearly(double total) {
		printSeparator();
		System.out.println(" Yearly Total: " + formatTotal(total));
		printSeparator();
		return total;
	}
	
	private static void printSeparator() {
		for (int x = 0; x < 6; x++) IR.displayCell(column_width, separator);
		System.out.println();
	}
	
	private static void printRow(String[] data) {
		for (int x = 0; x < 6; x++) IR.displayCell(column_width, data[x]);
		System.out.println();
	}
	
	private static String formatTotal(double total) {
		return String.format("%.2f", total);
	}

}
