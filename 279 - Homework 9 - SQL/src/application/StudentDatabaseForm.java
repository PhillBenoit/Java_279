/*
 ** Program    : StudentDatabaseForm.java
 **
 ** Purpose    : To demonstrate executing a stored procedure to retrieve data from a database and load it into a JavaFX TableView.
 **
 **		   		See page 1191 in the text regarding the classpath. I use the command line, so I run the statement below to modify the classpath variable.
 **				set classpath=%classpath%;C:\Program Files (x86)\MySQL\Connector.J 5.1\mysql-connector-java-5.1.34-bin.jar;. 
 **				Notice ;. at the end. It points to the current folder.
 **				Class.forName("com.mysql.jdbc.Driver"); // Load the JDBC driver for MySQL. It lives in mysql-connector-java-5.1.34-bin.jar or 
 **														// whatever version of this file you have.
 **
 **				Use your MySQL logon ID and password in the statement 
 **				connObject = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentsSimpleExample","yourID", "yourPassWord");
 **
 ** Developer  : F DAngelo
 **
 */

package application;

import javafx.application.Application; // JavaFX Application
import javafx.scene.text.Font; // needed for JavaFX Font object
import javafx.scene.text.FontWeight; // needed for JavaFX FontWeight object
//import javafx.scene.text.Text; // needed for JavaFX Text object
//import javafx.scene.layout.HBox; // Needed for HBox object
import javafx.scene.Scene; // needed for JavaFX scene object
import javafx.scene.layout.GridPane; // needed for JavaFX GridPane object
import javafx.scene.control.Label; // needed for JavaFX Label object
//import javafx.scene.control.TextField; // needed for JavaFX TextField control
//import javafx.scene.control.PasswordField; // needed for JavaFX PasswordField control
import javafx.scene.control.Button; // needed for JavaFX Button control
//import javafx.scene.paint.Color; // needed for JavaFX color object
import javafx.geometry.Insets; // needed for JavaFX Insets object
import javafx.geometry.Pos; // needed for JavaFX Pos object
import javafx.event.ActionEvent; // needed for JavaFX ActionEvent
//import javafx.event.EventHandler; // needed for JavaFX EventHandler
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
//import javafx.util.Callback;
//import javafx.beans.value.ObservableDoubleValue;
//import javafx.beans.value.ObservableValue;
//import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
//import javafx.beans.property.ReadOnlyObjectWrapper;
//import javafx.beans.property.SimpleDoubleProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.control.TableCell;


public class StudentDatabaseForm extends Application
{
	// These data members need to be known throughout the class so that the setNotifications method can refer to them.

	// Declare the Major Data ComboBox:
	private ComboBox <String> majorDataCombo = new ComboBox<>();

	// Declare the Student Data TableView:
	private TableView <StudentData> studentDataTableView = new TableView<>();

	// Declare the database connection object:
	private Connection connObject = null; // We'll initialize it later in the initializeDB method.

	private ArrayList <Integer> changedObjectsList  = new ArrayList<>();; // list of index numbers of Students whose values have changed.

	private List <StudentData> studentDataList = new ArrayList<>();  // needed in getStudentData & updateStudentData

	private String majorCodeParm = "";

	public static void main(String[] args)
	{
		launch(args);
	}

	public void start(Stage primaryStage) 
	{
		final String FONT_NAME = "Georgia";

		initialzeDB(); // create the connection to the database.

		getMajors(); // Get the major data we need for the majorDataCombo.

		primaryStage.setTitle("Student Database Interface");

		GridPane grid = new GridPane();

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);

		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25)); // Top Right Bottom Left (TRouBLe)

		Label majorLabel = new Label("Major");
		majorLabel.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20)); // named constant Font name
		grid.add(majorLabel, 0, 0, 1, 1); // column 0, row 0, colspan 1, rowspan 1

		// Configure the Major Data ComboBox:
		majorDataCombo.setPrefWidth(250);

		majorDataCombo.setOnAction((ActionEvent e)->
		{
			String selectedMajor = majorDataCombo.getValue(); // Needed by getStudentData to create a parameter value for a stored procedure.

			// System.out.println("\n********    1  selectedMajor: " + selectedMajor + " ************\n");

			majorCodeParm = selectedMajor.substring(0, 3); // majorCode is the 1st 3 characters of this String.

			getStudentData(); // Execute studentDataStoredProcStmt to get the data for the TableView.
		});

		grid.add(majorDataCombo, 1, 0, 1, 1); // column 1, row 0, colspan 1, rowspan 1

		// Configure the Student Data TableView and its TableColumns:
		configureTableView();

		/* I originally used this Button to execute that data retrieval, then changed to detecting an action on majorDataCombo.

		Button btnGetStudents = new Button("Get Students");    // Create a Button control
		btnGetStudents.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20)); // Set its font, weight and size

		btnGetStudents.setOnAction((ActionEvent e)->
		{
			String selectedMajor = majorDataCombo.getValue(); // Needed by getStudentData to create a parameter value for a stored procedure.

			majorCodeParm = selectedMajor.substring(0, 3);

			getStudentData(); // Execute studentDataStoredProcStmt to get the data for the TableView.

			for (StudentData sdv : olStudentList)
		   {
			   System.out.println("\n" + sdv.toString() + "\n");

				System.out.println("\n********    3    ************\n");
		   }	

		});

		grid.add(btnGetStudents, 2, 0, 1, 1);  // column 2, row 0, colspan 1, rowspan 1

		 */

		grid.add(studentDataTableView, 0, 1, 3, 12); // column 0, row 1, colspan 3, rowspan 12

		// Update database button
		Button btnUpdateDB = new Button("Update DB");    // Create a Button control
		btnUpdateDB.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20)); // Set its font, weight and size
		grid.add(btnUpdateDB, 0, 13, 1, 1);  // column 0, row 2, colspan 1, rowspan 1

		btnUpdateDB.setOnAction((ActionEvent e)->
		{
			updateStudentData();
		});

		// Exit button
		Button btnExit = new Button("Exit");    // Create a Button control
		btnExit.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20)); // Set its font, weight and size
		grid.add(btnExit, 1, 13, 1, 1);  // column 1, row 2, colspan 1, rowspan 1

		btnExit.setOnAction((ActionEvent e)->
		{
			Platform.exit();
		});

		Scene scene = new Scene(grid, 1050, 650); // Width, Height

		primaryStage.setScene(scene);

		primaryStage.show();

	}

	private void configureTableView() // this has enough code to merit its own method.
	{
		// Configure TableColumns:

		TableColumn <StudentData, String> studentIDTableCol = new TableColumn<>("Student ID"); // Give this column a heading.

		studentIDTableCol.setPrefWidth( 100);

		studentIDTableCol.setCellValueFactory( new PropertyValueFactory<>("studentID"));

		/* studentID is the primary key for the Student table. Typically we don't
		 * allow application to change the values of primary keys, so we wouldn't do the following:

		 * studentIDTableCol.setCellFactory(TextFieldTableCell.<StudentData>forTableColumn());

		 * studentIDTableCol.setOnEditCommit((CellEditEvent<StudentData, String> t) -> 
			{
				((StudentData) t.getTableView().getItems().get(
			            t.getTablePosition().getRow())
			            ).setStudentID(t.getNewValue());
			});

		 */		

		TableColumn <StudentData, String> lastNameTableCol = new TableColumn<>("Last Name"); // Give this column a heading.

		lastNameTableCol.setPrefWidth( 200);

		lastNameTableCol.setCellValueFactory( new PropertyValueFactory<>("lastName"));

		lastNameTableCol.setCellFactory(TextFieldTableCell.<StudentData>forTableColumn());

		lastNameTableCol.setOnEditCommit((CellEditEvent<StudentData, String> t) -> 
		{
			((StudentData) t.getTableView().getItems().get(
					t.getTablePosition().getRow())
					).setLastName(t.getNewValue());

			// The row number of a changed Student to the list of changed objects, 
			// if it is not already in the list.

			if (!changedObjectsList.contains(t.getTablePosition().getRow()))
			{
				changedObjectsList.add(t.getTablePosition().getRow());
			}

		});

		TableColumn <StudentData, String> firstNameTableCol = new TableColumn<>("First Name"); // Give this column a heading.

		firstNameTableCol.setPrefWidth( 150);

		firstNameTableCol.setCellValueFactory( new PropertyValueFactory<>("firstName"));

		firstNameTableCol.setCellFactory(TextFieldTableCell.<StudentData>forTableColumn());

		firstNameTableCol.setOnEditCommit((CellEditEvent<StudentData, String> t) -> 
		{
			((StudentData) t.getTableView().getItems().get(
					t.getTablePosition().getRow())
					).setFirstName(t.getNewValue());

			if (!changedObjectsList.contains(t.getTablePosition().getRow()))
			{
				changedObjectsList.add(t.getTablePosition().getRow());
			}

		});

		TableColumn <StudentData, String> majorCodeTableCol = new TableColumn<>("Major Code"); // Give this column a heading.

		majorCodeTableCol.setPrefWidth( 150);

		majorCodeTableCol.setCellValueFactory( new PropertyValueFactory<>("majorCode"));

		majorCodeTableCol.setCellFactory(TextFieldTableCell.<StudentData>forTableColumn());

		majorCodeTableCol.setOnEditCommit((CellEditEvent<StudentData, String> t) -> 
		{
			((StudentData) t.getTableView().getItems().get(
					t.getTablePosition().getRow())
					).setMajorCode(t.getNewValue());

			if (!changedObjectsList.contains(t.getTablePosition().getRow()))
			{
				changedObjectsList.add(t.getTablePosition().getRow());
			}

		});

		TableColumn <StudentData, String> majorDescTableCol = new TableColumn<>("Description"); // Give this column a heading.

		majorDescTableCol.setPrefWidth( 300);

		majorDescTableCol.setCellValueFactory( new PropertyValueFactory<>("majorDescription"));

		majorDescTableCol.setCellFactory(TextFieldTableCell.<StudentData>forTableColumn());

		majorDescTableCol.setOnEditCommit((CellEditEvent<StudentData, String> t) -> 
		{
			((StudentData) t.getTableView().getItems().get(
					t.getTablePosition().getRow())
					).setMajorDescription(t.getNewValue());

			if (!changedObjectsList.contains(t.getTablePosition().getRow()))
			{
				changedObjectsList.add(t.getTablePosition().getRow());
			}

		});

		TableColumn <StudentData, String> gradePointAvgTableCol = new TableColumn<>("Grade Point Avg"); // Give this column a heading.

		gradePointAvgTableCol.setPrefWidth( 100);

		studentDataTableView.setPrefWidth( 1000);

		gradePointAvgTableCol.setCellValueFactory(new PropertyValueFactory<>("gradePointAvg"));

		gradePointAvgTableCol.setCellFactory(TextFieldTableCell.<StudentData>forTableColumn());

		gradePointAvgTableCol.setOnEditCommit((CellEditEvent<StudentData, String> t) -> 
		{
			((StudentData) t.getTableView().getItems().get(
					t.getTablePosition().getRow())
					).setGradePointAvg(t.getNewValue());

			if (!changedObjectsList.contains(t.getTablePosition().getRow()))
			{
				changedObjectsList.add(t.getTablePosition().getRow());
			}

		});

		studentDataTableView.setEditable(true); // make the table "editable".


		// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableColumn.html discusses the following code.		


		// Add the TableColumns to the TableView:
		studentDataTableView.getColumns().setAll(studentIDTableCol, lastNameTableCol, firstNameTableCol, 
				majorCodeTableCol, majorDescTableCol, gradePointAvgTableCol );

		//studentDataTableView.addEventHandler(arg0, arg1);

	}

	private void initialzeDB() // See http://dev.mysql.com/doc/connector-j/en/
	{
		try
		{
			// If you use the Windows command line, run the statement below to modify the classpath variable.
			// set classpath=%classpath%;C:\Program Files (x86)\MySQL\Connector.J 5.1\mysql-connector-java-5.1.38-bin.jar;.
			// Your version o the connector may have a different version number.
			// Notice ;. at the end. the '.' points to the current folder. Mac OS and Linux are probably similar.

			Class.forName("com.mysql.jdbc.Driver"); // Load the JDBC driver for MySQL. It lives in mysql-connector-java-5.1.38-bin.jar or whatever version of this you have.

			// Connect to the StudentsSimpleExample" DB we previously created in MySQL.Use your MySQL logon ID and password.
			connObject = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentsSimpleExample?useSSL=false","root", "public"); // Note port number 3306.
		}

		catch (Exception ex)
		{
			System.out.println("\nError in connecting to the database: " + ex.getMessage() + "\n");

			ex.printStackTrace();
		}

	}

	private void getMajors()
	{
		try
		{
			String procCallString = "{call sp_get_majors()}";

			CallableStatement majorsStoredProcStmt = connObject.prepareCall(procCallString); // prepare the query object, sort of like compiling it

			ResultSet rsObject = majorsStoredProcStmt.executeQuery(); // Execute the call to the stored procedure and hope for the best.

			List <String> majorsList = new ArrayList<>(); 

			while (rsObject.next())
			{	   
				// The procedure's select statement: select concat(field_code,' - ', field_description)
				majorsList.add(rsObject.getString(1)); 	// Get the one and only column in the procedure's result set and add it to the ArrayList.
			}

			ObservableList <String> olMajorsList = FXCollections.observableArrayList( majorsList ); 	// ComboBox requires an ObservableList of objects.

			majorDataCombo.setItems( olMajorsList); // Assign the ObservableList to the ComboBox.
		}

		catch(Exception ex)
		{
			System.out.println("\nSomething bad happened during the execution of sp_get_majors(): " + ex.getMessage());

			ex.printStackTrace();
		}
	}

	private void getStudentData()
	{
		try
		{
			String procCallString = "{call sp_get_students_for_major( ?)}"; // the '?' is a placeholder for a substitutable parameter.

			CallableStatement studentDataStoredProcStmt = connObject.prepareCall(procCallString); // prepare the query object, sort of like compiling it

			studentDataStoredProcStmt.setString(1, majorCodeParm); // Parms begin at 1, not 0. Substitute the selected majorCode for the ? in studentDataStoredProcStmt.

			ResultSet rsObject = studentDataStoredProcStmt.executeQuery(); // Execute the call to the stored procedure and hope for the best.

			studentDataList.clear(); // clear the list before adding to it, so data from successive invi=ocations doesn't accumulate.

			while (rsObject.next()) // iterate through the ResultSet.
			{			   
				StudentData studentObject = new StudentData(); // We'll create a new Student object to receive the values from the ResultSet.

				// The procedure's select statement: select student_id, last_name, first_name, major_code, field_description, gpa
				// You must know the order of the columns being returned by a select statement and their data types.

				studentObject.setStudentID(Integer.toString(rsObject.getInt(1))); 		// Get the student_id, an int, the 1st column (they start at 1) in the procedure's result set.
				studentObject.setLastName(rsObject.getString(2)); 						// Get the last_name, a String, the 2nd column in the procedure's result set.
				studentObject.setFirstName(rsObject.getString(3)); 						// Get the first_name, a String, the 3rd column  in the procedure's result set.
				studentObject.setMajorCode(rsObject.getString(4)); 						// Get the major_code, a String, the 4th column  in the procedure's result set.
				studentObject.setMajorDescription(rsObject.getString(5)); 				// Get the major_description, a String, the 5th Scolumn in the procedure's result set.
				studentObject.setGradePointAvg(Double.toString(rsObject.getDouble(6))); // Get the gpa, a double, the 6th column in the procedure's result set.

				// System.out.println("\n******* 2 " + studentObject.toString() + " ******\n");

				studentDataList.add(studentObject);
			}

			ObservableList <StudentData> olStudentList = FXCollections.observableArrayList( studentDataList ); 	// TableView requires an ObservableList of objects.
			/*
		   for (StudentData sd : olStudentList)
		   {
			   System.out.println("\n********    3   " + sd.toString() + " ******\n");
		   }
			 */

			studentDataTableView.setItems(olStudentList); // Assign the ObservableList to the TableView.

		}

		catch(Exception ex)
		{
			System.out.println("\nSomething bad happened during the execution of the select query: " + ex.getMessage());

			ex.printStackTrace();
		}
	}

	private void updateStudentData()
	{
		try
		{
			/*
			 * Interface for stored procedure sp_update_student in the database:
			 * sp_update_student( in studentIDParm int, in lastNameParm char, 
						in firstNameParm char, in majorCodeParm char, in gpaParm double)
			 */

			String procCallString = "{call sp_update_student( ?, ?, ?, ?, ?)}"; // the '?' is a placeholder for a substitutable parameter.

			CallableStatement studentDataStoredProcStmt = connObject.prepareCall(procCallString); // prepare the query object, sort of like compiling it

			for (Integer i : changedObjectsList ) // iterate through the ResultSet.
			{			   
				StudentData studentObject = new StudentData(); // We'll create a new Student object to receive the values from the ResultSet.

				// Iterate through the list indexes in changedObjectsList and extract the values of their attributes.

				int studentIDParm = Integer.valueOf(studentDataList.get(i).getStudentID()); 		// Must convert the studentID back to an int for the database.
				String lastNameParm = studentDataList.get(i).getLastName(); 						
				String firstNameParm = studentDataList.get(i).getFirstName();			
				String majorCodeParm = studentDataList.get(i).getMajorCode(); 						
				// We do not update majorDescription. It resides in a different table, and we assume that the descriptions associated with the codes are correct. 				
				double gradePointParm = Double.valueOf(studentDataList.get(i).getGradePointAvg()); // Must convert the gradePoint back to a double for the database.

				//lastNameParm = lastNameParm.substring(0, lastNameParm.length()-1); // remove enter added by pressing enter.

				//firstNameParm = firstNameParm.substring(0, firstNameParm.length()-1); // remove enter added by pressing enter.

				//lastNameParm = lastNameParm.substring(0, lastNameParm.length()-1); // remove enter added by pressing enter.

				studentDataStoredProcStmt.setInt(1, studentIDParm); // Parms begin at 1, not 0. Substitute the the ? with studentID.
				studentDataStoredProcStmt.setString(2, lastNameParm); // Parms begin at 1, not 0. Substitute the the ? with lastNameParm.
				studentDataStoredProcStmt.setString(3, firstNameParm); // Parms begin at 1, not 0. Substitute the the ? with firstNameParm.
				studentDataStoredProcStmt.setString(4, majorCodeParm); // Parms begin at 1, not 0. Substitute the the ? with lastNameParm.
				studentDataStoredProcStmt.setDouble(5, gradePointParm); // Parms begin at 1, not 0. Substitute the the ? with gradePointParm.

				studentDataStoredProcStmt.executeQuery(); // Execute the stored procedure for each student object in turn.
			}

			changedObjectsList.clear(); // we're done with this data, clear the list.

		}

		catch(Exception ex)
		{
			System.out.println("\nSomething bad happened during the execution of the update query: " + ex.getMessage());

			ex.printStackTrace();
		}
	}

}



