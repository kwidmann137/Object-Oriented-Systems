package models;


/**
 * This class represents an employee who was imported
 * @author Kyle Widmann
 *
 */
public class Employee {
	private String firstName;
	private String lastName;
	private int age;
	private String payType;
	private double payAmount;
	
	/**
	 * The constructor for a newly imported employee
	 * @param firstName - employees first name 
	 * @param lastName - employees last name 
	 * @param age - employees age
	 * @param payType - employees pay type 
	 * @param payAmount - employees pay amount 
	 */
	public Employee (String firstName, String lastName, int age, String payType, double payAmount){
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setAge(age);
		this.setPayType(payType);
		this.setPayAmount(payAmount);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	/**
	 * This method returns an employees information formatted as a row
	 * for the EmployeeImportSummary class.
	 * @return string representation of employee info as table row
	 */
	public String printEmployeeInformationAsTableRow(){
		return String.format("%-30s %-3d  %-12s $%12.2f\n", firstName + " " + lastName, age
				, payType, payAmount);
	}
}
