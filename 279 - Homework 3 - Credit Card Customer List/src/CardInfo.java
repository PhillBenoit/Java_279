/*
 * Phillip Benoit
 * CIS 279
 * Fall 2017
 */

public class CardInfo {
	
	private int account_number;
	
	private double beginning_balance, ending_balance, credit_limit, interest_rate;
	
	private CustomerInfo customer;
	
	//default constructor
	public CardInfo() {
		setAccount_number(0);
		setBeginning_balance(0.0);
		setEnding_balance(0.0);
		setCredit_limit(0.0);
		setInterest_rate(0.0);
		customer = new CustomerInfo();
	}
	
	//constructor with values
	public CardInfo(int account_number, double beginning_balance, double ending_balance, double credit_limit,
			double interest_rate, int id, String first_name, String last_name, int credit_score) {
		customer = new CustomerInfo(id, first_name, last_name, credit_score);
		this.setAccount_number(account_number);
		this.setBeginning_balance(beginning_balance);
		this.setEnding_balance(ending_balance);
		this.setCredit_limit(credit_limit);
		this.setInterest_rate(interest_rate);
	}
	
	//formats output as defined by the specifications
	public static void displayInfo(CardInfo info, int[] column_widths) {
		//strings for output that are not part of the object
		String status, fee, average;
		
		//Calculates average and converts to string
		average = String.format("%.2f", (info.beginning_balance + info.ending_balance) / 2);
		
		//forking logic to find account status and penalty fees
		if (info.ending_balance <= info.credit_limit) {
			status = "OK";
			fee = "0.00";
		} else {
			status = "OVER";
			fee = String.format("%.2f", info.ending_balance * 0.05);
		}
		
		IR.displayCell(column_widths[0], Integer.toString(info.getAccount_number()));
		IR.displayCell(column_widths[1], Integer.toString(info.getCustomer_id()));
		IR.displayCell(column_widths[2], info.getLast_name());
		IR.displayCell(column_widths[3], info.getFirst_name());
		IR.displayCell(column_widths[4], Integer.toString(info.getCredit_score()));
		IR.displayCell(column_widths[5], String.format("%.2f", info.getCredit_limit()));
		IR.displayCell(column_widths[6], String.format("%.2f", info.getEnding_balance()));
		IR.displayCell(column_widths[7], status);
		IR.displayCell(column_widths[8], fee);
		IR.displayCell(column_widths[9], average);
		IR.displayCell(column_widths[10], String.format("%.0f%%", info.getInterest_rate() * 100));
		System.out.println();
	}
	
	public int getCustomer_id() {
		return customer.getId();
	}
	
	public void setCustomer_id(int id) {
		customer.setId(id);
	}
	
	public int getCredit_score() {
		return customer.getCredit_score();
	}
	
	public void setCredit_score(int credit_score) {
		customer.setCredit_score(credit_score);
	}
	
	public String getFirst_name() {
		return customer.getFirst_name();
	}
	
	public void setFirst_name(String first_name) {
		customer.setFirst_name(first_name);
	}
	
	public String getLast_name() {
		return customer.getLast_name();
	}
	
	public void setLast_name(String last_name) {
		customer.setLast_name(last_name);
	}

	public int getAccount_number() {
		return account_number;
	}

	public void setAccount_number(int account_number) {
		this.account_number = account_number;
	}

	public double getBeginning_balance() {
		return beginning_balance;
	}

	public void setBeginning_balance(double beginning_balance) {
		this.beginning_balance = beginning_balance;
	}

	public double getEnding_balance() {
		return ending_balance;
	}

	public void setEnding_balance(double ending_balance) {
		this.ending_balance = ending_balance;
	}

	public double getCredit_limit() {
		return credit_limit;
	}

	public void setCredit_limit(double credit_limit) {
		this.credit_limit = credit_limit;
	}

	public double getInterest_rate() {
		return interest_rate;
	}

	public void setInterest_rate(double interest_rate) {
		this.interest_rate = interest_rate;
	}

}
