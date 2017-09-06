package models;

public class Employee {
	private String firstName;
	private String lastName;
	private int age;
	private String payType;
	private double payAmount;
	
	public Employee (String first, String last, int age, String type, double amt){
		this.setFirstName(first);
		this.setLastName(last);
		this.setAge(age);
		this.setPayType(type);
		this.setPayAmount(amt);
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
	
	public String printEmployeeInformationAsTableRow(){
		return String.format("%-30s %-3d  %-12s $%12.2f\n", firstName + " " + lastName, age
				, payType, payAmount);
	}
}
