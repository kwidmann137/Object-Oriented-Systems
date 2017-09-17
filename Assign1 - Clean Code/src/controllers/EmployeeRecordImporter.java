package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.Employee;
import models.EmployeeImportSummary;

/**
 * 
 * @author Kyle Widmann
 *
 */
public class EmployeeRecordImporter {
	
	public static List<Employee> employees = new ArrayList<Employee>();
	
	private static Scanner recordScanner;
	
	private static final int FIRST_NAME_INDEX = 0;
	private static final int LAST_NAME_INDEX = 1;
	private static final int AGE_INDEX = 2;
	private static final int PAY_TYPE_INDEX = 3;
	private static final int PAY_AMOUNT_INDEX = 4;
	
	/**
	 * 
	 * @param employeeRecordFilePath
	 * @return
	 * @throws Exception
	 */
	public static String importEmployeeRecordsFromFile(String employeeRecordFilePath) throws Exception{
		
		importEmployees(employeeRecordFilePath);
		
		return  EmployeeImportSummary.generateEmployeeImportSummary(employees);
	}
	
	private static void importEmployees(String employeeRecordFilePath) throws Exception{
		try {
			recordScanner = new Scanner(new File(employeeRecordFilePath));
		} catch (FileNotFoundException e) {
			throw new Exception("Invalid input file supplied");
		}

		while(recordScanner.hasNextLine()) {
			String record = recordScanner.nextLine();
			if(record.length() > 0) {
				createEmployeeFromRecord(record);
			}
		}
		
		if(employees.size() == 0) {
			recordScanner.close();
			throw new Exception("No records found in data file");
		}
		
		recordScanner.close();
		
		sortEmployeesByLastName();
		
	}
	
	private static void createEmployeeFromRecord(String record) throws Exception{
		
		int employeeAge = 0;
		double employeePay = 0.0;
		
		String [] recordAttributes = record.split(",");
		
		try{
			employeeAge = Integer.parseInt(recordAttributes[AGE_INDEX]);
		}catch(Exception e){
			recordScanner.close();
			throw new Exception("Invalid argument supplied for an employees age.  Please check your records.");
		}
		
		try{
			employeePay = Double.parseDouble(recordAttributes[PAY_AMOUNT_INDEX]);
		}catch(Exception e){
			recordScanner.close();
			throw new Exception("Invalid argument supplied for an employees pay.  Please check your records.");
		}
		
		employees.add(new Employee(
				recordAttributes[FIRST_NAME_INDEX],
				recordAttributes[LAST_NAME_INDEX],
				employeeAge,
				recordAttributes[PAY_TYPE_INDEX],
				employeePay));
		
	}
	
	private static void sortEmployeesByLastName(){
		employees.sort((empl1, empl2) -> empl1.getLastName().compareTo(empl2.getLastName()));
	}
}
