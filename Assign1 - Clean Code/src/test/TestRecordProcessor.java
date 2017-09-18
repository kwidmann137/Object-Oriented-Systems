package test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import controllers.EmployeeRecordImporter;

public class TestRecordProcessor {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
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
	public void testFileData1() throws Exception {
		
		String result = null;
		
		result = EmployeeRecordImporter.importEmployeeRecordsFromFile("./src/data1.txt");
		
		assertEquals(expectedFromData1, result);
	}

	@Test
	public void testFileData2(){
		try {
			EmployeeRecordImporter.importEmployeeRecordsFromFile("./src/data2.txt");
			fail("Expected an Exception due to empty record file");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No records found in data file");
		}
	}

	@Test
	public void testFileData3(){
		try {
			EmployeeRecordImporter.importEmployeeRecordsFromFile("./src/data3.txt");
			fail("Expected an Exception for invalid employee age");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Invalid argument supplied for an employees age.  Please check your records.");
		}
	}
}
