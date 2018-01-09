/*
 * Phillip Benoit
 * CIS 279
 * Fall 2017
 */

public class Mortgage extends Account {

	//new members
	private int termInYears, termInMonths;
	private double periodicPayment, balanceRepaid;

	//default constructor
	public Mortgage() {
		super(0, 0, Account.MORTGAGE, 0.0, 0.0);
		termInYears = 0;
		termInMonths = 0;
		setPeriodicPayment(0.0);
		setBalanceRepaid(0.0);
	}

	//constructor with variables
	public Mortgage(int customerID, int accountNumber, double interestRate,
			double balance, int term) {
		super(customerID, accountNumber, Account.MORTGAGE, interestRate, balance);
		setTerm(term);
		calcPeriodicPayment();
		setBalanceRepaid(0.0);
	}

	//overrides abstract method
	@Override
	public void processTransaction(Transaction t) {
		double payment_amount = t.getTransactionAmount();
		//makes sure transaction type is a payment and the amount is greater than 0
		if (t.getTransactionType() == Transaction.PAYMENT && payment_amount > 0) {
			//commits changes
			currentMonthInterest = balance * monthlyInterestRate;
			balanceRepaid = payment_amount - currentMonthInterest;
			balance -= balanceRepaid;
			transactionList.add(t);
		}
	}

	//returns string state of members
	@Override
	public String toString() {
		
		//calls parent toString
		String return_string = super.toString();

		return_string = return_string + "Term In Years            : ";
		return_string = return_string + termInYears;

		return_string = return_string + "\nTerm In Months           : ";
		return_string = return_string + termInMonths;

		return_string = return_string + "\nPerodic Payment          : ";
		return_string = return_string + IR.money_format.format(periodicPayment);

		return_string = return_string + "\nBalance Repaid This Month: ";
		return_string = return_string + IR.money_format.format(balanceRepaid);

		return return_string;
	}

	//calculates payment based on term and balance
	public void calcPeriodicPayment () {
		double rate = getMonthlyInterestRate();
		double annuityFactor = (1-(1/Math.pow((1+rate),termInMonths)))/rate;
		periodicPayment = balance / annuityFactor;
	}

	public int getTerm() {
		return termInYears;
	}

	public void setTerm(int termInYears) {
		this.termInYears = termInYears;
		termInMonths = termInYears * 12;
	}

	public double getPeriodicPayment() {
		return periodicPayment;
	}

	public void setPeriodicPayment(double periodicPayment) {
		this.periodicPayment = periodicPayment;
	}

	public double getBalanceRepaid() {
		return balanceRepaid;
	}

	public void setBalanceRepaid(double balanceRepaid) {
		this.balanceRepaid = balanceRepaid;
	}
}
