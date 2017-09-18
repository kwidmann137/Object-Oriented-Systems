package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class generates an import summary from a list of imported employees.
 * The summary contains a list of all imported employees, a count, common names 
 * (first and last) and average pay sorted by type among all imported employees.
 * @author Kyle Widmann
 *
 */
public class EmployeeImportSummary {

	private static StringBuilder importSummary = new StringBuilder();
	private static List<Employee> employees = new ArrayList<Employee>();
	private static HashMap<String, Integer> nameCount = new HashMap<String, Integer>();
	private static HashMap<String, PaySum> paySumsByType = new HashMap<String, PaySum>();
	
	/**
	 * generates a string version of the import summary reports from a list of imported employees
	 * @param importedEmployees - a list of imported employees
	 * @return a string representation of the import summary reports
	 * @throws Exception - Exception encountered due to empty list of imported employees
	 */
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
		
		Double payAverage = 0.0;
		String payTypeString = "";
		
		for(Employee empl: employees){
			mapEmployeePayToPaySum(empl);
		}
		
		SortedSet<String> sortedPaySumsByType = new TreeSet<String>(paySumsByType.keySet());
		
		for(String payType : sortedPaySumsByType){
			payAverage = paySumsByType.get(payType).getAverage();
			
			//This is only a naive approach to maintain the integrity of provided test cases.  If
			//further types are to be added factor the Employee class to have a private PayType class
			//that can be sorted according to requirements.
			payType = payType.equals("Hourly") ? "hourly wage" : payType;
			
			payTypeString = "Average " + payType.toLowerCase() + ":";
			importSummary.append(String.format("%-20s $%12.2f\n", payTypeString, payAverage));
		}
	}
	
	private static void mapEmployeePayToPaySum(Employee empl){
		
		if(paySumsByType.containsKey(empl.getPayType())){
			PaySum sum = paySumsByType.get(empl.getPayType());
			sum.addToSum(empl.getPayAmount());
		}else{
			PaySum newSum = new PaySum(empl.getPayAmount());
			paySumsByType.put(empl.getPayType(), newSum);
		}
	}
	
	private static void generateNameCounts(){
		
		nameCount.clear();
		for(Employee e : employees){
			mapNameToCount(e.getFirstName());
		}
		generateNameCountReportString("First");
		
		nameCount.clear();
		for(Employee e : employees){
			mapNameToCount(e.getLastName());
		}
		generateNameCountReportString("Last");
		
	}

	private static void mapNameToCount(String name){
		if(nameCount.containsKey(name)){
			nameCount.put(name, nameCount.get(name) + 1);
		}else{
			nameCount.put(name, 1);
		}
	}
	
	private static void generateNameCountReportString(String nameType){
		importSummary.append(String.format("\n%s names with more than one person sharing it:\n", nameType));
		
		if(nameCount.isEmpty()) {
			importSummary.append(String.format("All %s names are unique", nameType.toLowerCase()));
		} else { 
			generateSimilairNamesFromMap();
		}
	}
	

	private static void generateSimilairNamesFromMap(){
		Set<String> names = nameCount.keySet();
		for(String name : names) {
			int count = nameCount.get(name);
			if(count > 1){
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
