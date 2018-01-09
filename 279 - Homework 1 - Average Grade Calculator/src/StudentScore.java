/*
 * Phillip Benoit
 * CIS 279
 * Fall 2017
 */

//Object to hold student information
public class StudentScore {
	private int id;
	private String name;
	private double score;
	
	public StudentScore() {
		setId(0);
		setName("");
		setScore(0.0);
	}
	
	public StudentScore(int id, String name, double score) {
		this.setId(id);
		this.setName(name);
		this.setScore(score);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	//returns a letter grade based on the score
	private static char getGrade(double score) {
		if (score >= 90) return 'A';
		if (score >= 80) return 'B';
		if (score >= 70) return 'C';
		if (score >= 60) return 'D';
		return 'F';
	}
	
	//returns a char representation of a comparison between a score and class average
	private static char compareAverage(double score, double average) {
		if (score > average) return '>';
		if (score < average) return '<';
		return '=';
	}
	
	//displays student as a line of text
	public static void printStudent(StudentScore student, double average, int[] column_widths) {
		IR.displayCell(column_widths[0], student.name);
		IR.displayCell(column_widths[1], Double.toString(student.score));
		IR.displayCell(column_widths[2], String.format("%c Average", compareAverage(student.score, average)));
		IR.displayCell(column_widths[3], Character.toString(getGrade(student.score)));
		System.out.println();
	}
}