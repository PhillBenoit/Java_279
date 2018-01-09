package application;
/*
 * Phillip Benoit
 * CIS 279
 * Fall 2017
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
			ERROR = " is not valid",
			FONT_NAME = "Georgia";
	
	//labels used
	Label wages_lbl, loan_interest_lbl, inc_interest_lbl, loan_term_lbl, inc_investment_lbl,
	loan_amount_lbl, inc_other_lbl, monthly_payment_lbl, inc_total_lbl, total_payments_lbl,
	qualification, percent_of_income;
	
	//text fields used
	TextField wages_txt, loan_interest_txt, inc_interest_txt, loan_term_txt, inc_investment_txt,
	loan_amount_txt, inc_other_txt, monthly_payment_txt, inc_total_txt, total_payments_txt;


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

			loan_interest_txt = new TextField();
			loan_interest_txt.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(loan_interest_txt, column, row++);
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

			loan_term_txt = new TextField();
			loan_term_txt.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
			grid.add(loan_term_txt, column, row++);
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
			Button calc_btn = new Button(CALC_PAYMENT_LABEL);
			calc_btn.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 12));
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
					//check for valid input before calculation
					if (validateFields()) {
						//calculate and display total income
						double total_income = Double.parseDouble(wages_txt.getText()) +
								Double.parseDouble(inc_interest_txt.getText()) +
								Double.parseDouble(inc_investment_txt.getText()) +
								Double.parseDouble(inc_other_txt.getText());
						inc_total_txt.setText(IR.money_format.format(total_income));
						
						//establish mortgage object
						Mortgage loan = new Mortgage(0, 0,
								Double.parseDouble(loan_interest_txt.getText()) / 100,
								Double.parseDouble(loan_amount_txt.getText()),
								Integer.parseInt(loan_term_txt.getText()));
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
				}
			});
			
			//clear button click handler
			clear_btn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) 
				{
					//clear all labels and text boxes
					wages_txt.setText("");
					loan_interest_txt.setText("");
					inc_interest_txt.setText("");
					loan_term_txt.setText("");
					inc_investment_txt.setText("");
					loan_amount_txt.setText("");
					inc_other_txt.setText("");
					monthly_payment_txt.setText("");
					inc_total_txt.setText("");
					total_payments_txt.setText("");
					qualification.setText("");
					percent_of_income.setText("");
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

			//assign and show stage
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//tests loan and income values for valid input
	private boolean validateFields() {
		//tests income values
		if (!IR.tryDouble(wages_txt.getText())) {
			qualification.setText(WAGES_LABEL + ERROR);
			return false;
		}
		if (!IR.tryDouble(inc_interest_txt.getText())) {
			qualification.setText(INC_INTEREST_LABEL + ERROR);
			return false;
		}
		if (!IR.tryDouble(inc_investment_txt.getText())) {
			qualification.setText(INC_INVESTMENT_LABEL + ERROR);
			return false;
		}
		if (!IR.tryDouble(inc_other_txt.getText())) {
			qualification.setText(INC_OTHER_LABEL + ERROR);
			return false;
		}
		
		//tests loan values
		if (!IR.tryDouble(loan_interest_txt.getText())) {
			qualification.setText(INTEREST_SHORT_LABEL + ERROR);
			return false;
		}
		if (!IR.tryInt(loan_term_txt.getText())) {
			qualification.setText(LOAN_TERM_LABEL + ERROR);
			return false;
		}
		if (!IR.tryDouble(loan_amount_txt.getText())) {
			qualification.setText(LOAN_AMOUNT_LABEL + ERROR);
			return false;
		}
		
		//if all are valid return true
		return true;
	}

	//launches main program
	public static void main(String[] args) {
		launch(args);
	}
}
