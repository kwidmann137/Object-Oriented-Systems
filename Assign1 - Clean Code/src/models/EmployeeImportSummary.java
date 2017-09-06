package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class EmployeeImportSummary {

	/**
	 * 
	 */
	private StringBuilder importSummary = new StringBuilder();
	
	/**
	 * 
	 */
	private List<Employee> employees = new ArrayList<Employee>();
	
	/**
	 *
	 */
	private HashMap<String, Integer> nameMap = new HashMap<String, Integer>();
	
	private EmployeeImportSummary(List<Employee> employees){
		this.employees = employees;
		generateImportSummary();
	}
	
	public static EmployeeImportSummary createImportSummaryFromListOfEmployees(List<Employee> employees) throws Exception{
		if(employees.size() == 0){
			throw new Exception("Failed to generate summary.\n"
					+ "You can not create a summary from an empyty list of employees.\n"
					+ "Please import employees and try again.\n");
		}
		return new EmployeeImportSummary(employees);
	}
	
	/**
	 * 
	 */
	private void generateImportSummary(){
		
		generateNumberOfEmployeesImported();
		
		generateEmployeeTable();
		
		generateEmployeeAverages();
		
		generateNameCounts();
	}
	
	private void generateNumberOfEmployeesImported(){
		importSummary.append(String.format("# of people imported: %d\n", employees.size()));
	}
	
	/**
	 * 
	 */
	private void generateEmployeeTable(){
	
		generateEmployeeTableHeader();
		
		for(Employee e: employees){
			importSummary.append(e.printEmployeeInformationAsTableRow());
		}
	}
	
	/**
	 * 
	 */
	private void generateEmployeeTableHeader(){
		
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
	
	/**
	 * 
	 */
	private void generateEmployeeAverages(){
		int  ageSum = 0,
				commissionCount = 0,
				hourlyCount = 0,
				salaryCount = 0;
		double commissionSum = 0 ,  
				hourlySum = 0, 
				salarySum = 0;
		
		for(Employee e: employees){
		
			ageSum += e.getAge();
			
			switch(e.getPayType()){
			case "Salary":
				salarySum += e.getPayAmount();
				salaryCount++;
				break;
			case "Commission":
				commissionSum += e.getPayAmount();
				commissionCount++;
				break;
			case "Hourly":
				hourlySum += e.getPayAmount();
				hourlyCount++;
				break;
			}
		}
		
		importSummary.append(String.format("\nAverage age:         %12.1f\n", (float) ageSum / employees.size()));
		
		importSummary.append(String.format("Average commission:  $%12.2f\n", commissionSum / commissionCount));
		
		importSummary.append(String.format("Average hourly wage: $%12.2f\n", hourlySum / hourlyCount));
		
		importSummary.append(String.format("Average salary:      $%12.2f\n", salarySum / salaryCount));
	}
	
	/**
	 * 
	 */
	private void generateNameCounts(){
		
		nameMap.clear();
		
		for(Employee e : employees){
			mapName(e.getFirstName());
		}

		importSummary.append(String.format("\nFirst names with more than one person sharing it:\n"));
		
		if(!nameMap.isEmpty()) {
			generateSimilairNamesFromMap();
		} else { 
			importSummary.append(String.format("All first names are unique"));
		}

		nameMap.clear();

		for(Employee e : employees){
			mapName(e.getLastName());
		}
		
		importSummary.append(String.format("\nLast names with more than one person sharing it:\n"));
		if(!nameMap.isEmpty()) {
			generateSimilairNamesFromMap();
		} else { 
			importSummary.append(String.format("All last names are unique"));
		}
	}
	
	/**
	 * 
	 * @param name
	 */
	private void mapName(String name){
		if(nameMap.containsKey(name)){
			nameMap.put(name, nameMap.get(name) + 1);
		}else{
			nameMap.put(name, 1);
		}
	}
	
	/**
	 * 
	 */
	private void generateSimilairNamesFromMap(){
		Set<String> names = nameMap.keySet();
		for(String name : names) {
			if(nameMap.get(name) > 1){
				importSummary.append(String.format("%s, # people with this name: %d\n", name, nameMap.get(name)));
			}
		}
	}
	
	/**
	 * 
	 */
	public String getSummary(){
		return this.importSummary.toString();
	}
}
