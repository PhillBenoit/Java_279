package application;
/*
** Program    : ComboBoxDemoV1.java
**
** Purpose    : To demonstrate using JavaFX to create a simple form using a ComboBox within a GridPane. It also uses Buttons.
**
**		This program has a ComboBox named stateCombo that will contain a list of states in the USA. It also has a 
**		ComboBox named cityCombo. When the user selects a state in the stateCombo, its event handler executes
**		addCitiesToCombo() which populates cityCombo with cities that exist within the selected state. When the 
**		user selects a city in cityCombo, its event handler executes setNotification() which changes the value of the 
**		Text named stateAndCityText to city name, state name. 
**
**		Two of the more confusing lines of code are
**
**		ObservableList<String> olState = FXCollections.observableArrayList( stateList ); 
**
**		ObservableList<String> olCity = FXCollections.observableArrayList( cityList );
**
**		The first statement adds the items from the stateList ArrayList to the ObervableList olState.
**
**		The second statement adds the items from the cityList ArrayList to the ObervableList olCity.
**
**		This is necessary because ComboBox requires an ObservableList of objects, not an array or ArrayList.
**		Java uses Observable objects to register changes such as being selected from a list.
**
**		The data for the ComboBoxes comes from a file named StatesAndCities.txt. The vast majority of the time, programs
**		should read data from with either files or database tables instead of using hard coded data.
**
**		Feel free to use any of this code, especially code related to backgrounds and borders.
**
** Developer  : F DAngelo
**
*/

import javafx.application.Application; // JavaFX Application
import javafx.scene.text.Font; // needed for JavaFX Font object
import javafx.scene.text.FontWeight; // needed for JavaFX FontWeight object
import javafx.scene.text.Text; // needed for JavaFX Text object
//import javafx.scene.layout.HBox; // Needed for HBox object
import javafx.scene.Scene; // needed for JavaFX scene object
import javafx.scene.layout.GridPane; // needed for JavaFX GridPane object
import javafx.scene.control.Label; // needed for JavaFX Label object
//import javafx.scene.control.TextField; // needed for JavaFX TextField control
import javafx.scene.control.ComboBox; // needed for JavaFX ComboBox control
import javafx.scene.control.Button; // needed for JavaFX Button control
import javafx.scene.paint.Color; // needed for JavaFX color object
import javafx.geometry.Insets; // needed for JavaFX Insets object
import javafx.geometry.Pos; // needed for JavaFX Pos object
import javafx.event.ActionEvent; // needed for JavaFX ActionEvent
import javafx.event.EventHandler; // needed for JavaFX EventHandler
//import javafx.scene.input.KeyEvent; // needed for JavaFX KeyEvent
//import javafx.scene.input.KeyCode; // needed for JavaFX KeyCodes
import javafx.stage.Stage; // needed for JavaFX Stage object
import javafx.application.Platform; // needed for  Platform.exit()

import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader ;

import java.util.StringTokenizer;

import java.util.List;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class ComboBoxDemoV1 extends Application
{
    // These data members and attributes need to be known thorughout the class so that various methods can refer to them.

    private Text stateAndCityText = null;

    private ComboBox<String> stateCombo = null;
    private ComboBox<String> cityCombo = null;

    private final String FILE_NAME = "StatesAndCities.txt";

    private List<String[]> stateCityList = new ArrayList<String[]>(); // An ArrayList of String arrays. The 1st element in each array is the state; the 2nd is a related city.

    private List<String> stateList = new ArrayList<String>(); // An ArrayList of states used for populating the stateCombo. getData adds items to this ArrayList.

    private List<String> cityList = new ArrayList<String>(); // An ArrayList of cities used for populating the cityCombo. This ArrayList is extracted from stateCityList.

    final int STATE_ELEMENT = 0;

    final int CITY_ELEMENT = 1;


    public void start(Stage primaryStage) 
    {
	final String FONT_NAME = "Georgia";

        	primaryStage.setTitle("JavaFX ComboBox Demo");
        
	GridPane grid = new GridPane();
		
	grid.setAlignment(Pos.CENTER);
	grid.setHgap(10);
	grid.setVgap(10);
	grid.setPadding(new Insets(25, 25, 25, 25)); // Top Right Bottom Left (Trouble)
		
	Text scenetitle = new Text("Select a state then select a city");
	scenetitle.setFont(Font.font("Georgia", FontWeight.NORMAL, 20)); // Literal Font name
	grid.add(scenetitle, 0, 0, 2, 1); // object to add, column, row, column span, row span

	Label stateLabel = new Label("State:");
	stateLabel.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20)); // named constant Font name
	grid.add(stateLabel, 0, 1); // column 0, row 1

	Label cityLabel = new Label("City:");
	cityLabel.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20)); // named constant Font name
	grid.add(cityLabel, 1, 1); // column 1, row 1

	getData();

	ObservableList<String> olState = FXCollections.observableArrayList( stateList ); 	// ComboBox requires an ObeservableList of objects.

	stateCombo = new ComboBox<String>();				// Instantiate a ComboBox that will contain a list of Strings.
	stateCombo.setEditable(false); 					// In this case, we don't want users to be able to type their own values for city.
	stateCombo.setPrefWidth(150);
	stateCombo.setPromptText("Select city");				// This displays a message in the selected value area.
	stateCombo.setItems( olState);					// Add the list of states to the ComboBox.
	//stateCombo.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20));	// Oddly, ComboBox doesn't support changing its font.
	grid.add(stateCombo, 0, 2); 						// Add stateCombo to the grid at column 0, row 2.

	StateComboClicked stateComboClickHandler = new StateComboClicked( this );	// Instantiate the event handler for the ComboBox and, 
									// pass a reference to the form itself into it using "this".

	stateCombo.setOnAction( stateComboClickHandler  );			// Register the event handler with the ComboBox.

	cityCombo = new ComboBox<String>();
	cityCombo.setEditable(false); 	
	cityCombo.setPrefWidth( 150);
	cityCombo.setPromptText("Select city");
	//cityCombo.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20));
	grid.add(cityCombo, 1, 2); // column 1, row 2

	CityComboClicked cityComboClickHandler = new CityComboClicked( this );

	cityCombo.setOnAction( cityComboClickHandler  );

	Scene scene = new Scene(grid, 450, 325); // Width, Height
		
	Button clearBtn = new Button("Clear");    				// Create a Button control
	clearBtn.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20)); 		// Set its font, weight and size
	grid.add(clearBtn, 0, 4);                 					// Add the Button to the grid at column 0 row 4.

	ClearButtonClicked clearBtnHandler = new ClearButtonClicked( this );		// Instantiate event handler for the Button and,
									// pass a reference to the form itself into it using "this".

	clearBtn.setOnAction( clearBtnHandler ); 				// Register the event handler with the Button.

	Button exitBtn = new Button("Exit");    
	exitBtn.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20)); 
	grid.add(exitBtn, 1, 4);        

	ExitButtonClicked exitBtnHandler = new ExitButtonClicked( this );

	exitBtn.setOnAction(exitBtnHandler); // Associate Button clearBtn with the named EventHandler exitBtnHandler.        
		
	stateAndCityText = new Text();
	stateAndCityText.setFill(Color.FIREBRICK);
	stateAndCityText.setWrappingWidth(200); // width in pixels allowed before the text wrpas
	stateAndCityText.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20));
    	grid.add(stateAndCityText, 0, 6);          // column 0, row 6

	// Instantiate event handler and pass a reference to the form itself into it using "this".

//	TypedKey typedKeyHandler = new TypedKey( this ) ;
	//stateCombo.setOnKeyTyped( typedKeyHandler ) ;
		
	primaryStage.setScene(scene);
		
        	primaryStage.show();
    }
	
	public static void main(String[] args)
	{
		launch(args);
	}

   public void setNotifications()
   {	
	try
	{
		System.out.println("\ncityCombo.getValue().length(): " +cityCombo.getValue().length() + " !cityCombo.getValue().equals(\"\") : " +  !cityCombo.getValue().equals("") + ".\n");

		if (cityCombo.getValue() != null)
		{
			stateAndCityText.setText(cityCombo.getValue() + ", " + stateCombo.getValue());
		}
	}

	catch (Exception ex)
	{
		// Do nothing. This is here to catch annoying problems that occur when attempting to check cityCombo.getValue() != null. No idea why it happens.
	}
   }
   

   public void clearNotifications() // Needed by ClearButtonClicked and StateComboClicked event handler.
   {
	stateAndCityText.setText("");
   }

   public void clearComboBoxValues() // Needed by ClearButtonClicked event handler.
   {
	stateCombo.setValue(null);

	cityCombo.setValue(null);
   }

   public void unselectCity()
   {
	cityCombo.setValue(null);
   }

   public void exit()
   {
	Platform.exit();
   }

  // stateCityList is a LinkedList of String arrays. Each 2 element array contains a state and a city.

   public void getData() // This method reads a file into a stateList and a stateCityList. It does this to avoid having to read the file more than once.
   {
	String inputValue       = "";

	File fileObject         = null;

	FileReader frObject     = null;

	BufferedReader brObject = null;

	try
	{
		fileObject = new File( FILE_NAME);

		frObject   = new FileReader( fileObject );

		brObject = new BufferedReader( frObject );
	}

	catch (Exception ex)
	{
		System.out.println("\nAn Exception occured while creating a BufferedReader for " + FILE_NAME + " : " + ex.getMessage() + ".\n");

		Platform.exit();
	}

	try
	{
		String previousState =""; // Needed to prevent adding duplicate state values to stateList.

		while ( (inputValue = brObject.readLine()) != null)
		{
			String[] stateCityArray = new String[2]; 
 
			StringTokenizer st = new StringTokenizer(inputValue, "|"); // We'll use the pipe character as a delimiter because state and city names may have spaces.

     			while (st.hasMoreTokens()) 
			{
         				stateCityArray[ STATE_ELEMENT] = st.nextToken(); // From the file organization we know that state precedes city.

				stateCityArray[CITY_ELEMENT] = st.nextToken(); // From the file organization we know that city follows state.

				stateCityList.add( stateCityArray);

				if ( !stateCityArray[ STATE_ELEMENT].equals( previousState )) // Notice the "!". Don't add duplicate state values to stateList.
				{
					stateList.add( stateCityArray[ STATE_ELEMENT] ); // We need a separate list of states for stateCombo.

					previousState = stateCityArray[ STATE_ELEMENT] ;
				}
			}
		}

	}

	catch (IOException ex)
	{
		System.out.println("\nAn IOException occured while reading " + FILE_NAME + " : " + ex.getMessage() + ".\n");
	}

	catch (Exception ex)
	{
		System.out.println("\nAn Exception occured while reading " + FILE_NAME+ " : " + ex.getMessage() + ".\n");
	}

	finally
	{
		try
		{
			brObject.close();
		}

		catch( Exception ex)
		{
			System.out.println("\nAn Exception occured while closing " + FILE_NAME + " : " + ex.getMessage() + ".\n");
		}
	
	}

   }

   public void addCitiesToCombo() // This method reads the stateAndCity ArrayList to extract cities related to a selected state and add them to an ArrayList that populates cityCombo.
   {
	cityList.removeAll(cityList); // Make sure the list is empty before the cities of another state are added to it. It's odd, but you must pass the list of elements to remove.

	String selectedState = stateCombo.getValue();

	for (String[] stateAndCityArray : stateCityList )
	{
		// System.out.println("\n:selectedState: " + selectedState + " : " + " stateAndCityArray[STATE_ELEMENT]: " +  stateAndCityArray[STATE_ELEMENT] + " stateAndCityArray[CITY_ELEMENT]: " +  stateAndCityArray[CITY_ELEMENT] + ".\n");

		if ( stateAndCityArray[STATE_ELEMENT].equals( selectedState ))
		{
			cityList.add( stateAndCityArray[CITY_ELEMENT] );
		}
	}

	ObservableList<String> olCity = FXCollections.observableArrayList( cityList );

	cityCombo.setItems( olCity );
   }

	
}

// The code below creates a separate, user-defined class that implements EventHandler. 
// To perform its work, the handle method of this class invokes a public method named
// setNotifications() within the form class.

class ClearButtonClicked implements EventHandler<ActionEvent>  
{
 	ComboBoxDemoV1 formObj = null; // Declare a data member to represent an object of the form class.

	public ClearButtonClicked(ComboBoxDemoV1 formObj) // This constructor receives an object of the form class.
	{
		this.formObj = formObj;
	}

	public void handle(ActionEvent e) 
	{
		formObj.clearNotifications(); 		//  a public method in ComboBoxDemoV1

		formObj.clearComboBoxValues(); 	// a public method in ComboBoxDemoV1
	}
}

class ExitButtonClicked implements EventHandler<ActionEvent>  
{
 	ComboBoxDemoV1 formObj = null; // Declare a data member to represent an object of the form class.

	public ExitButtonClicked(ComboBoxDemoV1 formObj) // This constructor receives an object of the form class.
	{
		this.formObj = formObj;
	}

	public void handle(ActionEvent e) 
	{
		formObj.exit();
	}
}

class StateComboClicked implements EventHandler<ActionEvent>
{
	 ComboBoxDemoV1 formObj = null; // Declare a data member to represent an object of the form class.

	public  StateComboClicked(ComboBoxDemoV1 formObj) // This constructor receives an object of the form class.
	{
		this.formObj = formObj;
	}

	public void handle(ActionEvent e) 
	{
		formObj.clearNotifications();

		formObj.addCitiesToCombo();

		formObj.unselectCity();

	}
}

class CityComboClicked implements EventHandler<ActionEvent>
{
	 ComboBoxDemoV1 formObj = null; // Declare a data member to represent an object of the form class.

	public  CityComboClicked(ComboBoxDemoV1 formObj) // This constructor receives an object of the form class.
	{
		this.formObj = formObj;
	}

	public void handle(ActionEvent e) 
	{
		formObj.setNotifications();

	}
}