/*
 * Phillip Benoit
 * CIS 279
 * Fall 2017
 */

import java.util.LinkedList;

public abstract class Account {

	//members
	protected int customerID, accountNumber;
	protected char accountType;
	protected double interestRate, monthlyInterestRate, currentMonthInterest, balance;
	protected LinkedList<Transaction> transactionList;

	//constants for account type definitions
	public static final char CHECKING = 'c', MORTGAGE = 'm';

	//default constructor
	public Account() {
		customerID = 0;
		accountNumber = 0;
		accountType = ' ';
		setInterestRate(0.0);
		currentMonthInterest = 0.0;
		balance = 0.0;
		transactionList = new LinkedList<Transaction>();
	}

	//constructor with data
	public Account(int customerID, int accountNumber, char accountType,
			double interestRate, double balance) {
		this.customerID = customerID;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		setInterestRate(interestRate);
		this.currentMonthInterest = 0.0;
		this.balance = balance;
		this.transactionList = new LinkedList<Transaction>();
	}

	//abstract method to process a transaction
	public abstract void processTransaction(Transaction t);

	//string value of members
	public String toString() {
		String return_string = "";

		return_string = return_string + "\nCustomer ID              : ";
		return_string = return_string + customerID;

		return_string = return_string + "\nAccount Number           : ";
		return_string = return_string + accountNumber;

		return_string = return_string + "\nAccount Type             : ";
		return_string = return_string + account_type();

		if (accountType == MORTGAGE) {
			return_string = return_string + "\nInterest Rate            : ";
			return_string = return_string + IR.percent_format.format(interestRate);

			return_string = return_string + "\nMonthly Interest Rate    : ";
			return_string = return_string + IR.percent_format.format(monthlyInterestRate);

			return_string = return_string + "\nCurrent Month's Interest : ";
			return_string = return_string + IR.money_format.format(currentMonthInterest);
		}

		return_string = return_string + "\nCurrent Balance          : ";
		return_string = return_string + IR.money_format.format(balance);

		return_string = return_string + "\n";

		return return_string;
	}

	//converts account type character to string
	private String account_type() {
		switch (accountType) {
		case CHECKING: return "Checking";
		case MORTGAGE: return "Mortgage";
		}
		return "(none)";
	}

	//string list of transactions
	public String listTransactions() {
		String return_string = "";

		for (Transaction t : transactionList) return_string = return_string + t.toString();

		return return_string;
	}

	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public char getAccountType() {
		return accountType;
	}
	public void setAccountType(char accountType) {
		this.accountType = accountType;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public double getMonthlyInterestRate() {
		return monthlyInterestRate;
	}

	//sets both interest and monthly interest
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
		monthlyInterestRate = interestRate / 12;
	}

	public double getCurrentMonthInterest() {
		return currentMonthInterest;
	}
	public void setCurrentMonthInterest(double currentMonthInterest) {
		this.currentMonthInterest = currentMonthInterest;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}

}
