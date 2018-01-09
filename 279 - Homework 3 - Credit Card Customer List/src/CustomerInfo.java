
public class CustomerInfo {
	
	private int id, credit_score;
	private String first_name, last_name;
	
	//default constructor
	public CustomerInfo() {
		id = 0;
		credit_score = 0;
		first_name = "";
		last_name = "";
	}
	
	//constructor with values
	public CustomerInfo(int id, String first_name, String last_name, int credit_score) {
		this.id = id;
		this.credit_score = credit_score;
		this.first_name = first_name;
		this.last_name = last_name;
	}
	
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public int getCredit_score() {
		return credit_score;
	}
	public void setCredit_score(int credit_score) {
		this.credit_score = credit_score;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
