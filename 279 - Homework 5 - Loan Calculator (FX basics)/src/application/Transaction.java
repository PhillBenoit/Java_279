package application;
/*
 * Phillip Benoit
 * CIS 279
 * Fall 2017
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
	private int transactionID;
	private Date transactionDate;
	private double transactionAmount;
	private char transactionType;
	
	//format for the date
	//August 29th, 1997 = "1997-08-29"
	private static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
	
	//constants for account types
	public static final char PAYMENT = 'P', CHECK = 'C', DEPOSIT = 'D';
	
	//default constructor
	public Transaction() {
		setTransactionID(0);
		setTransactionDate(new Date());
		setTransactionAmount(0.0);
		setTransactionType(' ');
	}
	
	//constructor with data
	public Transaction(int id, String date, double amount, char type) {
		setTransactionID(id);
		setTransactionDate(stringToDate(date));
		setTransactionAmount(amount);
		setTransactionType(type);
	}
	
	//converts string to date
	public Date stringToDate(String dateString) {
		try {
			
			//returns date from formated string
			return date_format.parse(dateString);
		
		} catch (ParseException e) {
			e.printStackTrace();
			
			//returns current date if none found in the string
			return new Date();
		}
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public char getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(char transactionType) {
		this.transactionType = transactionType;
	}
	
	//string value of class members
	public String  toString()
	{
        StringBuffer strBuf = new StringBuffer();

		strBuf.append("\nTransaction ID     : " );
		strBuf.append(transactionID);
		strBuf.append("\nTransaction Date   : ");
		strBuf.append(date_format.format(transactionDate));
		strBuf.append("\nTransaction Amount : ");
		strBuf.append(IR.money_format.format(transactionAmount));
		strBuf.append("\nTransaction Type   : ");
		strBuf.append(typeString());
		strBuf.append( "\n");
 
    	return strBuf.toString() ;
	}
	
	//returns string value of transactionType character
	private String typeString() {
		switch (transactionType) {
		case PAYMENT: return "Payment";
		case CHECK: return "Check";
		case DEPOSIT: return "Deposit";
		}
		return "(none)";
	}
	
}
