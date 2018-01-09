package application;

public class EmployeeData {

	private int id;
	
	private String last_name, first_name, job_desc, dept_name, job_code, dept_code, pay;
	
	public EmployeeData(int id, String last_name, String first_name, String job_desc, String dept_name, String job_code,
			String dept_code, String pay) {
		super();
		this.id = id;
		this.last_name = last_name;
		this.first_name = first_name;
		this.job_desc = job_desc;
		this.dept_name = dept_name;
		this.job_code = job_code;
		this.dept_code = dept_code;
		this.pay = pay;
	}

	public EmployeeData() {
		setId(0); setJob_code("0"); setDept_code("0");
		setLast_name(""); setFirst_name(""); setJob_desc(""); setDept_name("");
		setPay("$0.00");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getJob_desc() {
		return job_desc;
	}

	public void setJob_desc(String job_desc) {
		this.job_desc = job_desc;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getJob_code() {
		return job_code;
	}

	public void setJob_code(String job_code) {
		this.job_code = job_code;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

}
