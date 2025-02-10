package com.nn.apibootcamp.utilities;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.nn.apibootcamp.constants.EnvironmentConfiguration;
import com.nn.apibootcamp.constants.FileNameConstants;

public class ExcelUtility {
	public static FileNameConstants filepaths = new FileNameConstants();

	// Excel properties
	public static String AUTHORIZATION_QUERY = "SELECT * FROM TEST_DATA";
	public static String POSITIVE_TEST_DATA_QUERY = "SELECT * FROM PositiveTest WHERE Run ='Yes'";
	public static String NEGATIVE_TEST_DATA_QUERY="SELECT * FROM  NegativeTest";
	public static String ENDPOINT_QUERY = "SELECT * FROM EndPoints";

	Object[][] EXCEL_DATA = null;
	Object[][] OUTPUT_DATA = null;
	List<Map<String, String>> EXCEL_DATA_LIST = new ArrayList<>();
	

	// Fillo properties	
	Fillo fillo = new Fillo();
	Connection connection = null;
	Recordset recordset = null;


	public void getAuthorizationData() throws FileNotFoundException, IOException {
		try {

			System.out.println("EXCEL FILE PATH IS "+filepaths.EXCEL_TEST_DATA);
			System.out.println("EXCEL Query"+AUTHORIZATION_QUERY);
			
			
			connection = fillo.getConnection(filepaths.EXCEL_TEST_DATA);
			
			System.out.println("created connection");
			recordset = connection.executeQuery(AUTHORIZATION_QUERY);
			Map<String, String> testData = new LinkedHashMap<>();
			while (recordset.next()) {
				
				for (String field : recordset.getFieldNames()) {
					testData.put(field, recordset.getField(field));

				}
				EXCEL_DATA_LIST.add(testData);
			}

		
			EnvironmentConfiguration.USERNAME=testData.get("username");
			EnvironmentConfiguration.PASSWORD=testData.get("password");
			
		} catch (FilloException exception) {
			exception.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (recordset != null) {
	            recordset.close();
	        }
		}
		

	}

	@DataProvider(name="PositiveTestData")
	public Object[][] getPosTestData() throws FileNotFoundException, IOException {

		try {

			connection = fillo.getConnection(filepaths.EXCEL_TEST_DATA);

			recordset = connection.executeQuery(POSITIVE_TEST_DATA_QUERY);

			while (recordset.next()) {
				Map<String, String> testData = new LinkedHashMap<>();
				for (String field : recordset.getFieldNames()) {
					testData.put(field, recordset.getField(field));

				}
				EXCEL_DATA_LIST.add(testData);

			}

			OUTPUT_DATA = prepareExcelData(EXCEL_DATA_LIST);
		} catch (FilloException exception) {
			exception.printStackTrace();

		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return OUTPUT_DATA;
	}

	@DataProvider(name="NegativeTestData")
	public Object[][] getNegTestData() throws FileNotFoundException, IOException {

		try {

			connection = fillo.getConnection(filepaths.EXCEL_TEST_DATA);
boolean result = checkIfHeadersExist(filepaths.EXCEL_TEST_DATA,"NegativeTest");
System.out.println("excel query "+NEGATIVE_TEST_DATA_QUERY);
			
			System.out.println("does header exist" +result);


			recordset = connection.executeQuery(NEGATIVE_TEST_DATA_QUERY);
			
			if (recordset != null) {
                System.out.println("Data found!");
            } else {
                System.out.println("No data found. Check your query or Excel file.");
            }
            
			
			while (recordset.next()) {
				Map<String, String> testData = new LinkedHashMap<>();
				for (String field : recordset.getFieldNames()) {
					testData.put(field, recordset.getField(field));

				}
				EXCEL_DATA_LIST.add(testData);

			}

			OUTPUT_DATA = prepareExcelData(EXCEL_DATA_LIST);
		} catch (FilloException exception) {
			exception.printStackTrace();

		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return OUTPUT_DATA;
	}
	
	
	
	public void getEndPointData() throws FileNotFoundException, IOException {

		try {

			
			connection = fillo.getConnection(filepaths.EXCEL_TEST_DATA);
			recordset = connection.executeQuery(ENDPOINT_QUERY);
			Map<String, String> testData = new LinkedHashMap<>();
			while (recordset.next()) {
				
				for (String field : recordset.getFieldNames()) {
					testData.put(field, recordset.getField(field));

				}
				EXCEL_DATA_LIST.add(testData);
			}

			//OUTPUT_DATA = prepareExcelData(EXCEL_DATA_LIST);
			
			EnvironmentConfiguration.GET_USERS=testData.get("Get_allUsers");
			EnvironmentConfiguration.GET_BY_USERID=testData.get("Get_UserId");
			EnvironmentConfiguration.GET_BY_FIRSTNAME=testData.get("Get_Firstname");
			EnvironmentConfiguration.POST=testData.get("Post");
			EnvironmentConfiguration.PUT=testData.get("Put");
			EnvironmentConfiguration.DELETE_BY_USERID=testData.get("Delete_userId");
			EnvironmentConfiguration.DELETE_BY_FIRSTNAME=testData.get("Delete_firstName");
			EnvironmentConfiguration.BASE_URL=testData.get("Base_Url");
			
		} catch (FilloException exception) {
			exception.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (recordset != null) {
	            recordset.close();
	        }
		}
		//return OUTPUT_DATA;
	}

	public Object[][] prepareExcelData(List<Map<String, String>> DATA_LIST) {

		EXCEL_DATA = new Object[DATA_LIST.size()][1];
		for (int i = 0; i < DATA_LIST.size(); i++) {
			EXCEL_DATA[i][0] = DATA_LIST.get(i);

		}

		return EXCEL_DATA;
	}

	
	public static boolean checkIfHeadersExist(String filePath, String sheetName) {
	    try {
	        Fillo fillo = new Fillo();
	        Connection connection = fillo.getConnection(filePath);
	        String query = "SELECT * FROM " + sheetName;
	        Recordset recordset = connection.executeQuery(query);
	        return recordset.getFieldNames().size() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Test(dataProvider = "NegativeTestData")
	public void DataDrivenTesting(Map<String, String> testData) {
		//System.out.println("test data is" + testData.get("username"));
		//System.out.println("testdata  is "+	testData.get("Get_allUsers"));
		System.out.println("testdata is "+testData.get("firstName"));
	}
}
