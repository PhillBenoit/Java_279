package application;
/*
 * Phillip Benoit
 * CIS 279
 * Fall 2017
 */

import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Main extends Application {

	//maximum percent of income for qualification
	static final double POI_LIMIT = 0.25;

	//strings used
	static final String WAGES_LABEL = "Salary and wages",
			LOAN_INTEREST_LABEL = "Anual interest rate (n.nnn%)",
			INTEREST_SHORT_LABEL = "interest rate",
			INC_INTEREST_LABEL = "Interest income",
			LOAN_TERM_LABEL = "Term in years",
			INC_INVESTMENT_LABEL = "Investment income",
			LOAN_AMOUNT_LABEL = "Loan amount",
			INC_OTHER_LABEL = "Other income",
			MONTHLY_PAYMENT_LABEL = "Monthly payment",
			INC_TOTAL_LABEL = "Total income",
			TOTAL_PAYMENTS_LABEL = "Total loan payments",
			CALC_PAYMENT_LABEL = "Calculate Payment",
			CLEAR_LABEL = "Clear",
			EXIT = "Exit",
			QUALIFIED = "Qualified",
			REJECTED = "Rejected",
			POI_LABEL = " monthly income",
			ERROR = "Input not yet valid",
			VALID = "Ready to calculate",
			FONT_NAME = "Georgia",
			RATES_FILE = "annual_interest_rates.txt",
			TERMS_FILE = "loan_terms.txt";

	//labels used
	Label wages_lbl, loan_interest_lbl, inc_interest_lbl, loan_term_lbl, inc_investment_lbl,
	loan_amount_lbl, inc_other_lbl, monthly_payment_lbl, inc_total_lbl, total_payments_lbl,
	qualification, percent_of_income;

	//text fields used
	TextField wages_txt, inc_interest_txt, inc_investment_txt,
	loan_amount_txt, inc_other_txt, monthly_payment_txt, inc_total_txt, total_payments_txt;
	
	//Combo boxes
	ComboBox<Double> loan_interest_cbx;
	ComboBox<Integer> loan_term_cbx;

	//calculate button
	Button calc_btn;

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Loan Calculator");

			GridPane grid = new GridPane();

			grid.setAlignment(Pos.TOP_CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));

			//variables used to step through grid assignments
			int row = 1, column = 0;

			//each pair of declared items is separated
			//--------------------------------------------------------------
			wages_lbl = new Label(WAGES_LABEL);
			wages_lbl.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(wages_lbl, column++, row);

			wages_txt = new TextField();
			wages_txt.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(wages_txt, column++, row);

			//--------------------------------------------------------------
			loan_interest_lbl = new Label(LOAN_INTEREST_LABEL);
			loan_interest_lbl.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(loan_interest_lbl, column++, row);

			loan_interest_cbx = new ComboBox<Double>();
			loan_interest_cbx.setEditable(false);
			loan_interest_cbx.setPrefWidth(160);
			loan_interest_cbx.setPromptText("Select Interest Rate");
			loan_interest_cbx.setItems(getRates());
			loan_interest_cbx.setOnAction(( ActionEvent e )->{validateFields();});
			grid.add(loan_interest_cbx, column, row++);
			column = 0;

			//--------------------------------------------------------------
			inc_interest_lbl = new Label(INC_INTEREST_LABEL);
			inc_interest_lbl.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(inc_interest_lbl, column++, row);

			inc_interest_txt = new TextField();
			inc_interest_txt.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(inc_interest_txt, column++, row);

			//--------------------------------------------------------------
			loan_term_lbl = new Label(LOAN_TERM_LABEL);
			loan_term_lbl.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(loan_term_lbl, column++, row);

			loan_term_cbx = new ComboBox<Integer>();
			loan_term_cbx.setEditable(false);
			loan_term_cbx.setPrefWidth(160);
			loan_term_cbx.setPromptText("Select Term");
			loan_term_cbx.setItems(getTerms());
			loan_term_cbx.setOnAction(( ActionEvent e )->{validateFields();});
			grid.add(loan_term_cbx, column, row++);
			column = 0;

			//--------------------------------------------------------------
			inc_investment_lbl = new Label(INC_INTEREST_LABEL);
			inc_investment_lbl.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(inc_investment_lbl, column++, row);

			inc_investment_txt = new TextField();
			inc_investment_txt.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(inc_investment_txt, column++, row);

			//--------------------------------------------------------------
			loan_amount_lbl = new Label(LOAN_AMOUNT_LABEL);
			loan_amount_lbl.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(loan_amount_lbl, column++, row);

			loan_amount_txt = new TextField();
			loan_amount_txt.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(loan_amount_txt, column, row++);
			column = 0;

			//--------------------------------------------------------------
			inc_other_lbl = new Label(INC_OTHER_LABEL);
			inc_other_lbl.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(inc_other_lbl, column++, row);

			inc_other_txt = new TextField();
			inc_other_txt.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(inc_other_txt, column++, row);

			//--------------------------------------------------------------
			monthly_payment_lbl = new Label(MONTHLY_PAYMENT_LABEL);
			monthly_payment_lbl.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(monthly_payment_lbl, column++, row);

			monthly_payment_txt = new TextField();
			monthly_payment_txt.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			monthly_payment_txt.setEditable(false);
			grid.add(monthly_payment_txt, column, row++);
			column = 0;

			//--------------------------------------------------------------
			inc_total_lbl = new Label(INC_TOTAL_LABEL);
			inc_total_lbl.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(inc_total_lbl, column++, row);

			inc_total_txt = new TextField();
			inc_total_txt.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			inc_total_txt.setEditable(false);
			grid.add(inc_total_txt, column++, row);

			//--------------------------------------------------------------
			total_payments_lbl = new Label(TOTAL_PAYMENTS_LABEL);
			total_payments_lbl.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(total_payments_lbl, column++, row);

			total_payments_txt = new TextField();
			total_payments_txt.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			total_payments_txt.setEditable(false);
			grid.add(total_payments_txt, column, row++);
			column = 0;

			//--------------------------------------------------------------
			//last row

			//calculate button
			//visibility set to false until all fields are valid
			calc_btn = new Button(CALC_PAYMENT_LABEL);
			calc_btn.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			calc_btn.setVisible(false);
			HBox calc_box = new HBox(10);
			calc_box.setAlignment(Pos.BOTTOM_RIGHT);
			calc_box.getChildren().add(calc_btn);
			grid.add(calc_box, column++, row);

			//clear button
			Button clear_btn = new Button(CLEAR_LABEL);
			clear_btn.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			HBox clear_exit_box = new HBox(10);
			clear_exit_box.setAlignment(Pos.BOTTOM_LEFT);
			clear_exit_box.getChildren().add(clear_btn);

			//exit button
			Button exit_btn = new Button(EXIT);
			exit_btn.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			clear_exit_box.getChildren().add(exit_btn);
			grid.add(clear_exit_box, column++, row);

			//label that shows qualification status
			qualification = new Label();
			qualification.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(qualification, column++, row);

			//label that shows percent of income
			percent_of_income = new Label();
			percent_of_income.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(percent_of_income, column, row);

			Scene scene = new Scene(grid, 700, 250); // Width, Height

			//calculate click handler
			calc_btn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) 
				{
					//calculate and display total income
					double total_income = Double.parseDouble(wages_txt.getText()) +
							Double.parseDouble(inc_interest_txt.getText()) +
							Double.parseDouble(inc_investment_txt.getText()) +
							Double.parseDouble(inc_other_txt.getText());
					inc_total_txt.setText(IR.money_format.format(total_income));

					//establish mortgage object
					Mortgage loan = new Mortgage(0, 0,
							loan_interest_cbx.getValue() / 100,
							Double.parseDouble(loan_amount_txt.getText()),
							loan_term_cbx.getValue());
					//assign values from object
					monthly_payment_txt.setText(IR.money_format.format(loan.getPeriodicPayment()));
					total_payments_txt.setText(Integer.toString(loan.getTermInMonths()));

					//declare significant digits in percent format
					IR.percent_format.setMaximumFractionDigits(2);

					//calculate percent of income
					double loan_percent_of_income = loan.getPeriodicPayment() / (total_income / 12);

					//display percent of income
					percent_of_income.setText(IR.percent_format.format(loan_percent_of_income) + POI_LABEL);

					//display qualification status
					if ( loan_percent_of_income <= POI_LIMIT) qualification.setText(QUALIFIED);
					else qualification.setText(REJECTED);
				}
			});

			//clear button click handler
			clear_btn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) 
				{
					//clear all labels, combo and text boxes
					wages_txt.setText("");
					loan_interest_cbx.setValue(null);
					inc_interest_txt.setText("");
					loan_term_cbx.setValue(null);
					inc_investment_txt.setText("");
					loan_amount_txt.setText("");
					inc_other_txt.setText("");
					monthly_payment_txt.setText("");
					inc_total_txt.setText("");
					total_payments_txt.setText("");
					qualification.setText("");
					percent_of_income.setText("");
					validateFields();
				}
			});

			//exit button click handler
			exit_btn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) 
				{
					//end program
					System.exit(0);
				}
			});

			//Validates fields when a key is pressed
			scene.setOnKeyReleased(( KeyEvent e ) ->{validateFields();});

			//assign and show stage
			primaryStage.setScene(scene);
			primaryStage.show();

			//sets initial backgrounds to red
			validateFields();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	//tests loan and income values for valid input
	private boolean validateFields() {
		//background colors
		Background white = new Background(new BackgroundFill(Color.rgb(255, 255, 255), null, null));
		Background red = new Background(new BackgroundFill(Color.rgb(255, 200, 200), null, null));

		//keeps track of valid fields
		boolean valid = true;

		//tests income values and sets backgrounds accordingly
		if (!IR.tryDouble(wages_txt.getText())) {
			wages_txt.setBackground(red);
			valid = false;
		} else wages_txt.setBackground(white);

		if (!IR.tryDouble(inc_interest_txt.getText())) {
			inc_interest_txt.setBackground(red);
			valid = false;
		} else inc_interest_txt.setBackground(white);

		if (!IR.tryDouble(inc_investment_txt.getText())) {
			inc_investment_txt.setBackground(red);
			valid = false;
		} else inc_investment_txt.setBackground(white);

		if (!IR.tryDouble(inc_other_txt.getText())) {
			inc_other_txt.setBackground(red);
			valid = false;
		} else inc_other_txt.setBackground(white);

		//tests loan values and sets backgrounds accordingly
		if (loan_interest_cbx.getValue() == null) {
			loan_interest_cbx.setBackground(red);
			valid = false;
		} else loan_interest_cbx.setBackground(white);

		if (loan_term_cbx.getValue() == null) {
			loan_term_cbx.setBackground(red);
			valid = false;
		} else loan_term_cbx.setBackground(white);

		if (!IR.tryDouble(loan_amount_txt.getText())) {
			loan_amount_txt.setBackground(red);
			valid = false;
		} else loan_amount_txt.setBackground(white);

		//forking logic for valid / invalid input sets visibility of
		//calculate button and displays validation status in qualification label
		if (valid) {
			calc_btn.setVisible(true);
			qualification.setText(VALID);
		} else {
			calc_btn.setVisible(false);
			qualification.setText(ERROR);
		}

		//clears out all derived values in case input was made after calculation
		monthly_payment_txt.setText("");
		inc_total_txt.setText("");
		total_payments_txt.setText("");
		percent_of_income.setText("");

		return valid;
	}
	
	//gets list of interest rates from file
	ObservableList<Double> getRates() {
		//establish scanner from file
		Scanner input = openScanner(RATES_FILE);
		
		//create list using file
		List<Double> rates = new ArrayList<Double>();
		while (input.hasNextDouble()) rates.add(input.nextDouble());
		input.close();
		
		//return observable list
		ObservableList<Double> observable_rates = FXCollections.observableArrayList(rates);
		return observable_rates;
	}
	
	//gets list of loan terms from file
	ObservableList<Integer> getTerms() {
		//establish scanner from file
		Scanner input = openScanner(TERMS_FILE);
		
		//create observable list using file
		List<Integer> terms = new ArrayList<Integer>();
		while (input.hasNextInt()) terms.add(input.nextInt());
		input.close();
		
		//return observable list
		ObservableList<Integer> observable_terms = FXCollections.observableArrayList(terms);
		return observable_terms;
	}
	
	//opens scanner for both reading methods
	private Scanner openScanner(String filename) {
		File input = new File(filename);
		Scanner scan = null;
		try {
			scan = new Scanner(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Error reading file: " + filename);
			System.exit(-1);
		}
		return scan;
	}

	//launches main program
	public static void main(String[] args) {
		launch(args);
	}
}
