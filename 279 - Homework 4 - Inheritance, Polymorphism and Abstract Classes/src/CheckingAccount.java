/*
 * Phillip Benoit
 * CIS 279
 * Fall 2017
 */

public class CheckingAccount extends Account {

	//default constructor
	public CheckingAccount() {
		super(0, 0, Account.CHECKING, 0.0, 0.0);
	}

	//constructor with data
	public CheckingAccount(int customerID, int accountNumber,
			double interestRate, double balance) {
		super(customerID, accountNumber, Account.CHECKING, interestRate, balance);
	}

	//overrides abstract method
	@Override
	public void processTransaction(Transaction t) {
		//amount assigned locally for easer code visibility
		double amount = t.getTransactionAmount();

		//prevents 0 transactions
		if (amount != 0) {
			
			//boolean indicates if changes were made to the balance
			boolean changed = false;
			
			//forking logic for transaction type
			switch (t.getTransactionType()) {
			case Transaction.CHECK:
				balance -= amount;
				changed = true;
				break;
			case Transaction.DEPOSIT:
				balance += amount;
				changed = true;
				break;
			}
			
			//makes sure changes were committed before adding transaction
			if (changed) transactionList.add(t);
		}
	}

}
