/*
 * Phillip Benoit
 * CIS 279
 * Fall 2017
 */

import java.util.LinkedList;

public class Hw4 {

	public static void main(String[] args) {
		IR.displayProgramInformation("Hw4", "9-18-17", "Homework 4",
				"Inheritance, Polymorphism and Abstract Classes",
				"Creates objects from extentions of an abstract class.",
				"Stores them in a list of the abstract class then displays them.");
		IR.percent_format.setMaximumFractionDigits(4);
		
		LinkedList<Account> accountList = generateAccounts();
		
		displayAccounts(accountList);

		IR.displayEndOfProgram();
	}

	private static void displayAccounts(LinkedList<Account> accountList) {
		for (Account a : accountList) {
			System.out.println("----------==========[ Begin Account ]==========----------");
			System.out.println(a.toString());
			System.out.println(a.listTransactions());
			System.out.println("----------==========[  End Account  ]==========----------\n");
		}
	}

	private static LinkedList<Account> generateAccounts() {
		LinkedList<Account> account_list = new LinkedList<Account>();
		int cust_id = 1001;
		int acc_id = 22330001;
		int tr_id = 1;
		
		CheckingAccount CA1 = new CheckingAccount(cust_id++, acc_id++, 0.0, 9000);
		CA1.processTransaction(new Transaction(tr_id++, "2001-01-17",
				400.00, Transaction.CHECK));
		CA1.processTransaction(new Transaction(tr_id++, "2001-02-18",
				40.00, Transaction.CHECK));
		CA1.processTransaction(new Transaction(tr_id++, "2001-03-19",
				4.00, Transaction.CHECK));
		CA1.processTransaction(new Transaction(tr_id++, "2001-04-20",
				1000.00, Transaction.DEPOSIT));
		account_list.add(CA1);
		
		CheckingAccount CA2 = new CheckingAccount(cust_id++, acc_id++, 0.0, 9000);
		CA2.processTransaction(new Transaction(tr_id++, "1999-09-17",
				400.00, Transaction.DEPOSIT));
		CA2.processTransaction(new Transaction(tr_id++, "1999-10-18",
				40.00, Transaction.DEPOSIT));
		CA2.processTransaction(new Transaction(tr_id++, "1999-11-19",
				4.00, Transaction.DEPOSIT));
		CA2.processTransaction(new Transaction(tr_id++, "1999-12-20",
				1000.00, Transaction.CHECK));
		account_list.add(CA2);
		
		Mortgage MO1 = new Mortgage(cust_id++, acc_id++, 0.06875, 95000, 15);
		MO1.processTransaction(new Transaction(tr_id++, "2015-01-01",
				MO1.getPeriodicPayment(), Transaction.PAYMENT));
		MO1.processTransaction(new Transaction(tr_id++, "2015-02-01",
				MO1.getPeriodicPayment(), Transaction.PAYMENT));
		MO1.processTransaction(new Transaction(tr_id++, "2015-03-01",
				MO1.getPeriodicPayment(), Transaction.PAYMENT));
		MO1.processTransaction(new Transaction(tr_id++, "2015-04-01",
				MO1.getPeriodicPayment(), Transaction.PAYMENT));
		account_list.add(MO1);
		
		Mortgage MO2 = new Mortgage(cust_id++, acc_id++, 0.04025, 165000, 30);
		MO2.processTransaction(new Transaction(tr_id++, "2017-01-01",
				MO2.getPeriodicPayment(), Transaction.PAYMENT));
		MO2.processTransaction(new Transaction(tr_id++, "2017-02-01",
				MO2.getPeriodicPayment(), Transaction.PAYMENT));
		MO2.processTransaction(new Transaction(tr_id++, "2017-03-01",
				MO2.getPeriodicPayment(), Transaction.PAYMENT));
		MO2.processTransaction(new Transaction(tr_id++, "2017-04-01",
				MO2.getPeriodicPayment(), Transaction.PAYMENT));
		account_list.add(MO2);
		
		return account_list;
	}
}
