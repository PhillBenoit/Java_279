/*
EmployeeDataForm.java
Adapted By: Phillip Benoit
Fall 2017
CIS 279

Original File:      StudentDatabaseForm.java
Original Developer: F DAngelo
*/

package application;

import javafx.application.Application; // JavaFX Application
import javafx.scene.text.Font; // needed for JavaFX Font object
import javafx.scene.text.FontWeight; // needed for JavaFX FontWeight object
import javafx.scene.Scene; // needed for JavaFX scene object
import javafx.scene.layout.GridPane; // needed for JavaFX GridPane object
import javafx.scene.control.Label; // needed for JavaFX Label object
import javafx.scene.control.Button; // needed for JavaFX Button control
import javafx.geometry.Insets; // needed for JavaFX Insets object
import javafx.geometry.Pos; // needed for JavaFX Pos object
import javafx.event.ActionEvent; // needed for JavaFX ActionEvent
import javafx.stage.Stage; // needed for JavaFX Stage object
import javafx.scene.control.TableView; // Needed for JavaFX TableView object
import javafx.scene.control.TableColumn; // Needed for JavaFX TableColumn object
import javafx.scene.control.ComboBox; // Needed for JavaFX ComboBox object
import javafx.scene.control.cell.PropertyValueFactory; // Needed for JavaFX PropertyValueFactory object
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.application.Platform; // Needed for Platform.exit()
import javafx.collections.ObservableList; // Needed for ObservableList
import javafx.collections.FXCollections; // Needed for FXCollections
import java.sql.*; // Needed for JDBC
import java.util.List; // Needed for List
import java.util.ArrayList; // Needed for ArrayList
import javafx.scene.control.TableColumn.CellEditEvent;


public class EmployeeDataForm extends Application
{
	// Declare the ComboBox:
	private ComboBox <String> departmentDataCombo = new ComboBox<>();

	// Declare the TableView:
	private TableView <EmployeeData> employeeDataTableView = new TableView<>();

	// Declare the database connection object:
	private Connection connObject = null; // We'll initialize it later in the initializeDB method.

	//list of changed Employees
	private ArrayList <Integer> changedObjectsList  = new ArrayList<>();

	//list of Employees in the gridview
	private List <EmployeeData> EmployeeDataList = new ArrayList<>();

	//parameter for select query
	private int departmentCodeParm = 0;

	public static void main(String[] args)
	{
		launch(args);
	}

	public void start(Stage primaryStage) 
	{
		final String FONT_NAME = "Georgia";

		initialzeDB(); // create the connection to the database.

		getDepartments(); // Get the data we need for the combo box.

		primaryStage.setTitle("Employee Database Interface");

		GridPane grid = new GridPane();

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);

		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Label departmentLabel = new Label("Department");
		departmentLabel.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20));
		grid.add(departmentLabel, 0, 0, 1, 1);

		// Configure the ComboBox:
		departmentDataCombo.setPrefWidth(250);

		departmentDataCombo.setOnAction((ActionEvent e)->
		{
			// Needed by getEmployeeData to create a parameter value for a stored procedure.
			String selectedDepartment = departmentDataCombo.getValue(); 
			departmentCodeParm = Integer.parseInt(selectedDepartment.substring(0, selectedDepartment.indexOf(" ")));

			getEmployeeData(); // Execute EmployeeDataStoredProcStmt to get the data for the TableView.
		});

		grid.add(departmentDataCombo, 1, 0, 1, 1);

		// Configure the Student Data TableView and its TableColumns:
		configureTableView();

		grid.add(employeeDataTableView, 0, 1, 3, 12);

		// Update database button
		Button btnUpdateDB = new Button("Update DB");
		btnUpdateDB.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20));
		grid.add(btnUpdateDB, 0, 13, 1, 1);
		btnUpdateDB.setOnAction((ActionEvent e)->
		{
			updateEmployeeData();
		});

		// Exit button
		Button btnExit = new Button("Exit");
		btnExit.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20));
		grid.add(btnExit, 1, 13, 1, 1);
		btnExit.setOnAction((ActionEvent e)->
		{
			Platform.exit();
		});

		Scene scene = new Scene(grid, 1050, 650);

		primaryStage.setScene(scene);

		primaryStage.show();

	}

	//configures the table
	private void configureTableView()
	{
		employeeDataTableView.setPrefWidth(925);

		// Configure TableColumns:
		TableColumn <EmployeeData, Integer> employeeIDTableCol = new TableColumn<>("Employee ID");
		employeeIDTableCol.setPrefWidth(100);
		employeeIDTableCol.setCellValueFactory( new PropertyValueFactory<>("id"));

		//---------------------------------------------
		TableColumn <EmployeeData, String> lastNameTableCol = new TableColumn<>("Last Name");
		lastNameTableCol.setPrefWidth(150);
		lastNameTableCol.setCellValueFactory( new PropertyValueFactory<>("last_name"));
		lastNameTableCol.setCellFactory(TextFieldTableCell.<EmployeeData>forTableColumn());
		lastNameTableCol.setOnEditCommit((CellEditEvent<EmployeeData, String> t) -> 
		{
			((EmployeeData) t.getTableView().getItems().get(
					t.getTablePosition().getRow())
					).setLast_name(t.getNewValue());
			if (!changedObjectsList.contains(t.getTablePosition().getRow()))
				changedObjectsList.add(t.getTablePosition().getRow());
		});

		//---------------------------------------------
		TableColumn <EmployeeData, String> firstNameTableCol = new TableColumn<>("First Name");
		firstNameTableCol.setPrefWidth(100);
		firstNameTableCol.setCellValueFactory( new PropertyValueFactory<>("first_name"));
		firstNameTableCol.setCellFactory(TextFieldTableCell.<EmployeeData>forTableColumn());
		firstNameTableCol.setOnEditCommit((CellEditEvent<EmployeeData, String> t) -> 
		{
			((EmployeeData) t.getTableView().getItems().get(
					t.getTablePosition().getRow())
					).setFirst_name(t.getNewValue());
			if (!changedObjectsList.contains(t.getTablePosition().getRow()))
				changedObjectsList.add(t.getTablePosition().getRow());
		});

		//---------------------------------------------
		TableColumn <EmployeeData, String> jobCodeTableCol = new TableColumn<>("Job Code");
		jobCodeTableCol.setPrefWidth(75);
		jobCodeTableCol.setCellValueFactory( new PropertyValueFactory<>("job_code"));
		jobCodeTableCol.setCellFactory(TextFieldTableCell.<EmployeeData>forTableColumn());
		jobCodeTableCol.setOnEditCommit((CellEditEvent<EmployeeData, String> t) -> 
		{
			((EmployeeData) t.getTableView().getItems().get(
					t.getTablePosition().getRow())
					).setJob_code(t.getNewValue());
			if (!changedObjectsList.contains(t.getTablePosition().getRow()))
				changedObjectsList.add(t.getTablePosition().getRow());
		});

		//---------------------------------------------
		TableColumn <EmployeeData, String> jobDescTableCol = new TableColumn<>("Description");
		jobDescTableCol.setPrefWidth(150);
		jobDescTableCol.setCellValueFactory( new PropertyValueFactory<>("job_desc"));
		
		//---------------------------------------------
		TableColumn <EmployeeData, String> dptCodeTableCol = new TableColumn<>("Depatment Code");
		TableColumn <EmployeeData, String> dptNameTableCol = new TableColumn<>("Department Name");
		dptCodeTableCol.setPrefWidth(100);
		dptCodeTableCol.setCellValueFactory( new PropertyValueFactory<>("dept_code"));
		dptCodeTableCol.setCellFactory(TextFieldTableCell.<EmployeeData>forTableColumn());
		dptCodeTableCol.setOnEditCommit((CellEditEvent<EmployeeData, String> t) -> 
		{
			((EmployeeData) t.getTableView().getItems().get(
					t.getTablePosition().getRow())
					).setDept_code(t.getNewValue());
			if (!changedObjectsList.contains(t.getTablePosition().getRow()))
				changedObjectsList.add(t.getTablePosition().getRow());
		});

		//---------------------------------------------
		dptNameTableCol.setPrefWidth(150);
		dptNameTableCol.setCellValueFactory( new PropertyValueFactory<>("dept_name"));
		
		//---------------------------------------------
		TableColumn <EmployeeData, String> payTableCol = new TableColumn<>("Pay");
		payTableCol.setPrefWidth(100);
		payTableCol.setCellValueFactory(new PropertyValueFactory<>("pay"));
		payTableCol.setCellFactory(TextFieldTableCell.<EmployeeData>forTableColumn());
		payTableCol.setOnEditCommit((CellEditEvent<EmployeeData, String> t) -> 
		{
			((EmployeeData) t.getTableView().getItems().get(
					t.getTablePosition().getRow())
					).setPay(t.getNewValue());
			if (!changedObjectsList.contains(t.getTablePosition().getRow()))
				changedObjectsList.add(t.getTablePosition().getRow());
		});

		//---------------------------------------------
		employeeDataTableView.setEditable(true); // make the table "editable".

		// Add the TableColumns to the TableView:
		employeeDataTableView.getColumns().setAll(employeeIDTableCol, lastNameTableCol, firstNameTableCol, 
				jobCodeTableCol, jobDescTableCol, dptCodeTableCol, dptNameTableCol, payTableCol );
	}

	private void initialzeDB() // See http://dev.mysql.com/doc/connector-j/en/
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver"); // Load the JDBC driver for MySQL. It lives in mysql-connector-java-5.1.38-bin.jar or whatever version of this you have.
			connObject = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb?useSSL=false","root", "public"); // Note port number 3306.
		}
		catch (Exception ex)
		{
			System.out.println("\nError in connecting to the database: " + ex.getMessage() + "\n");
			ex.printStackTrace();
		}
	}

	//populates combo box with a list of departments pulled from the database
	private void getDepartments()
	{
		try
		{
			String procCallString = "{call sp_get_depts()}";
			CallableStatement majorsStoredProcStmt = connObject.prepareCall(procCallString);
			ResultSet rsObject = majorsStoredProcStmt.executeQuery();
			List <String> deptList = new ArrayList<>(); 
			while (rsObject.next()) deptList.add(rsObject.getString(1));
			ObservableList <String> olDeptList = FXCollections.observableArrayList( deptList );
			departmentDataCombo.setItems( olDeptList);
		}
		catch(Exception ex)
		{
			System.out.println("\nSomething bad happened during the execution of sp_get_majors(): " + ex.getMessage());

			ex.printStackTrace();
		}
	}

	//populates gridview from database
	//pulls only employees from the selected department
	private void getEmployeeData()
	{
		try
		{
			String procCallString = "{call sp_get_employees_in_dept(?)}";
			CallableStatement EmployeeDataStoredProcStmt = connObject.prepareCall(procCallString);
			EmployeeDataStoredProcStmt.setInt(1, departmentCodeParm);
			ResultSet rsObject = EmployeeDataStoredProcStmt.executeQuery();
			EmployeeDataList.clear();
			while (rsObject.next())
			{			   
				EmployeeData employeeObject = new EmployeeData();
				
				employeeObject.setId(rsObject.getInt(1));
				employeeObject.setLast_name(rsObject.getString(2));
				employeeObject.setFirst_name(rsObject.getString(3));
				employeeObject.setJob_code(Integer.toString(rsObject.getInt(4)));
				employeeObject.setJob_desc(rsObject.getString(5));
				employeeObject.setDept_code(Integer.toString(rsObject.getInt(6)));
				employeeObject.setDept_name(rsObject.getString(7));
				employeeObject.setPay(IR.money_format.format(rsObject.getDouble(8)));
				
				EmployeeDataList.add(employeeObject);
			}
			

			ObservableList <EmployeeData> olStudentList = FXCollections.observableArrayList( EmployeeDataList );
			employeeDataTableView.setItems(olStudentList);

		}

		catch(Exception ex)
		{
			System.out.println("\nSomething bad happened during the execution of the select query: " + ex.getMessage());

			ex.printStackTrace();
		}
		
	}

	//updates the database and refreshes the gridview
	private void updateEmployeeData()
	{
		try
		{

			String procCallString = "{call sp_update_employee( ?, ?, ?, ?, ?, ?)}";

			CallableStatement EmployeeDataStoredProcStmt = connObject.prepareCall(procCallString); // prepare the query object, sort of like compiling it

			for (Integer i : changedObjectsList ) // iterate through the ResultSet.
			{			   
				//EmployeeData studentObject = new EmployeeData(); // We'll create a new Student object to receive the values from the ResultSet.

				// Iterate through the list indexes in changedObjectsList and extract the values of their attributes.

				int employeeIDParm = Integer.valueOf(EmployeeDataList.get(i).getId());
				String lastNameParm = EmployeeDataList.get(i).getLast_name(); 						
				String firstNameParm = EmployeeDataList.get(i).getFirst_name();			
				int jobCodeParm = Integer.parseInt(EmployeeDataList.get(i).getJob_code());
				int dptCodeParam = Integer.parseInt(EmployeeDataList.get(i).getDept_code());
				String pay = EmployeeDataList.get(i).getPay();
				pay = pay.replaceAll(",", "");
				pay = pay.replace("$", "");
				double payParm = Double.parseDouble(pay);

				EmployeeDataStoredProcStmt.setInt(1, employeeIDParm);
				EmployeeDataStoredProcStmt.setString(2, lastNameParm);
				EmployeeDataStoredProcStmt.setString(3, firstNameParm);
				EmployeeDataStoredProcStmt.setInt(4, jobCodeParm);
				EmployeeDataStoredProcStmt.setInt(5, dptCodeParam);
				EmployeeDataStoredProcStmt.setDouble(6, payParm);

				EmployeeDataStoredProcStmt.executeQuery();
			}

			changedObjectsList.clear(); // we're done with this data, clear the list.
			
			getEmployeeData();

		}

		catch(Exception ex)
		{
			System.out.println("\nSomething bad happened during the execution of the update query: " + ex.getMessage());

			ex.printStackTrace();
		}
	}

}



