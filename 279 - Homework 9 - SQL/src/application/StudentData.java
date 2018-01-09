/*
** Program    : StudentData.java
**
** Purpose    : To provide objects with which to populate the studentDataTableView.
** 				It differs slightly from the Student class by having the major description.
**
** Developer  : F DAngelo
**
*/

package application;

//import javafx.beans.property.SimpleDoubleProperty;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleStringProperty;

public class StudentData
{
	//Declare private data members.
	// private  SimpleIntegerProperty	   studentID;
	private String studentID, lastName, firstName, majorCode, majorDescription, gradePointAvg ;
	
	// private SimpleDoubleProperty gpa ;
	
	public StudentData()
	{
		this.studentID = "0"; // new SimpleIntegerProperty(0);
		this.lastName = "";
		this.firstName = "";
		this.majorCode = "";
		this.majorDescription = ""; // not the "regular" Student class.
		this.gradePointAvg = "0.0"; // new SimpleDoubleProperty(0.0) ;  
	}

	// Custom constructor with parameters.
	public StudentData(String studentID, String lastName, String firstName, String majorCode, String majorDescription, String gradePointAvg )
	{
		setStudentID(studentID);
		setLastName(lastName) ;
		setFirstName(firstName) ;
		setMajorCode(majorCode);
		setMajorDescription(majorDescription);
		setGradePointAvg(gradePointAvg) ;
	}

	// Define "setter" a.k.a. mutator methods.
	public void setStudentID( String studentID ) // int studentID ) 
	{
		this.studentID = studentID;
	}
	
	public void setLastName( String lastName ) 
	{
		this.lastName = lastName;
	}
	
	public void setFirstName( String firstName ) 
	{
		this.firstName = firstName;
	}
	
	public void setMajorCode( String majorCode ) 
	{
		this.majorCode = majorCode;
	}
	
	public void setMajorDescription( String majorDescription ) 
	{
		this.majorDescription = majorDescription;
	}
	
	public void setGradePointAvg( String gradePointAvg ) 
	{
		this.gradePointAvg = gradePointAvg;
	}
	
	// Define "getter" a.k.a. accessor methods.
	public String getStudentID() 
	{
		return studentID;
	}
	
	public String getLastName() 
	{
		return lastName;
	}
	
	public String getFirstName() 
	{
		return firstName;
	}
	
	public String getMajorCode()  
	{
		return majorCode;
	}
	
	public String getMajorDescription() 
	{
		return majorDescription;
	}
	
	public String getGradePointAvg() 
	{
		return gradePointAvg;
	}

	public String toString()
	{
		return	"\n" +
				" Student ID       : " + studentID + "\n" +
				" Last name        : " + lastName + "\n" +
				" First name       : " + firstName + "\n" +
				" Major code       : " + majorCode + "\n" +
				" Major description: " + majorDescription + "\n" +
				" GPA              : " + gradePointAvg + "\n" ;
	}
	
}