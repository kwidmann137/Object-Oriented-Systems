package test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

import controllers.EmployeeRecordImporter;

public class TestRecordProcessor {
	private static String expectedFromData1;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		StringBuffer fileContents = new StringBuffer();
		Scanner fileInput = new Scanner(new File("./src/expected1.txt"));
		while(fileInput.hasNextLine())
			fileContents.append(fileInput.nextLine() + "\n");
		expectedFromData1 = fileContents.toString();
		fileInput.close();
	}

	@Test
	public void testFileData1() {
		String result = null;
		try{
			result = EmployeeRecordImporter.importEmployeeRecordsFromFile("./src/data1.txt");
		}catch(Exception e){
			fail("Error processing valid input");
		}
		assertEquals(expectedFromData1, result);
	}

	@Test
	public void testFileData2() {
		try{
			EmployeeRecordImporter.importEmployeeRecordsFromFile("./src/data2.txt");
			
		}catch(Exception e){
			assertEquals(e.getMessage(), "No records found in data file");
		}
		
	}

	@Test
	public void testFileData3() {
		try{
			EmployeeRecordImporter.importEmployeeRecordsFromFile("./src/data2.txt");
			
		}catch(Exception e){
			assertEquals(e.getMessage(), "Invalid argument supplied for an employees age.  Please check your records.");
		}
	}
}
