package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class EmployeeImportSummary {

	private static StringBuilder importSummary = new StringBuilder();
	private static List<Employee> employees = new ArrayList<Employee>();
	private static HashMap<String, Integer> nameCount = new HashMap<String, Integer>();
	private static HashMap<String, PaySum> paySumByType = new HashMap<String, PaySum>();
	
	public static String generateEmployeeImportSummary(List<Employee> importedEmployees) throws Exception{
		if(importedEmployees.size() == 0){
			throw new Exception("Failed to generate summary.\n"
					+ "You can not create a summary from an empty list of employees.\n"
					+ "Please import employees and try again.\n");
		}
		employees = importedEmployees;
		generateImportSummary();
		return importSummary.toString();
	}
	
	private static void generateImportSummary(){
		
		generateNumberOfEmployeesImported();
		
		generateEmployeeTable();
		
		generateEmployeeAverages();
		
		generateNameCounts();
	}
	
	private static void generateNumberOfEmployeesImported(){
		importSummary.append(String.format("# of people imported: %d\n", employees.size()));
	}
	
	private static void generateEmployeeTable(){
	
		generateEmployeeTableHeader();
		
		for(Employee empl: employees){
			importSummary.append(empl.printEmployeeInformationAsTableRow());
		}
	}
	
	private static void generateEmployeeTableHeader(){
		
		importSummary.append(String.format("\n%-30s %s  %-12s %12s\n", "Person Name", "Age", "Emp. Type", "Pay"));
		
		for(int i = 0; i < 30; i++)
			importSummary.append(String.format("-"));
		importSummary.append(String.format(" ---  "));
		for(int i = 0; i < 12; i++)
			importSummary.append(String.format("-"));
		importSummary.append(String.format(" "));
		for(int i = 0; i < 12; i++)
			importSummary.append(String.format("-"));
		importSummary.append(String.format("\n"));
	}
	
	private static void generateEmployeeAverages(){
		
		generateAverageAge();
		generateAveragePayByType();
		
	}
	
	private static void generateAverageAge(){
		int  ageSum = 0;
		
		for(Employee e: employees){
			ageSum += e.getAge();
		}
	
		importSummary.append(String.format("\nAverage age:         %12.1f\n", (float) ageSum / employees.size()));
	}
	
	private static void generateAveragePayByType(){
		
		for(Employee empl: employees){
			mapEmployeeSalaryToAverage(empl);
		}
		
		SortedSet<String> payTypes = new TreeSet<String>(paySumByType.keySet());
//		ToDo: Fix ordering
		for(String payType : payTypes){
			importSummary.append(String.format("Average %s:  $%12.2f\n", payType.toLowerCase(), paySumByType.get(payType).getAverage()));
		}
	}
	
	private static void mapEmployeeSalaryToAverage(Employee empl){
		if(paySumByType.containsKey(empl.getPayType())){
			PaySum sum = paySumByType.get(empl.getPayType());
			sum.addToSum(empl.getPayAmount());
		}else{
			PaySum newSum = new PaySum(empl.getPayAmount());
			paySumByType.put(empl.getPayType(), newSum);
		}
	}
	
	private static void generateNameCounts(){
		
		nameCount.clear();
		
		for(Employee e : employees){
			mapNameToCount(e.getFirstName());
		}

		importSummary.append(String.format("\nFirst names with more than one person sharing it:\n"));
		
		if(!nameCount.isEmpty()) {
			generateSimilairNamesFromMap();
		} else { 
			importSummary.append(String.format("All first names are unique"));
		}

		nameCount.clear();

		for(Employee e : employees){
			mapNameToCount(e.getLastName());
		}
		
		importSummary.append(String.format("\nLast names with more than one person sharing it:\n"));
		if(!nameCount.isEmpty()) {
			generateSimilairNamesFromMap();
		} else { 
			importSummary.append(String.format("All last names are unique"));
		}
	}

	private static void mapNameToCount(String name){
		if(nameCount.containsKey(name)){
			nameCount.put(name, nameCount.get(name) + 1);
		}else{
			nameCount.put(name, 1);
		}
	}
	

	private static void generateSimilairNamesFromMap(){
		Set<String> names = nameCount.keySet();
		for(String name : names) {
			if(nameCount.get(name) > 1){
				importSummary.append(String.format("%s, # people with this name: %d\n", name, nameCount.get(name)));
			}
		}
	}
	
	private static class PaySum{
		private int count;
		private double sum;
		
		public PaySum(double initialValue){
			this.count = 1;
			this.sum = initialValue;
		}
		
		public void addToSum(double inc){
			sum += inc;
			count++;
		}
		
		public double getAverage(){
			return sum/count;
		}
		
	}
}
