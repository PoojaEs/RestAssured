package com.nn.apibootcamp.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.nn.apibootcamp.constants.FileNameConstants;

public class ExcelUtility2 {
	FileNameConstants filepaths = new FileNameConstants();
	
	public static Map<String,String> readExcelFile() throws IOException
	{
		Map<String,String> testData =new HashMap<String,String>();
		try {
			FileInputStream fileInputStream = new FileInputStream("");
			//Workbook workbook = new XSSFWorkbook(fileInputStream);
		}
		catch(FileNotFoundException exception)
		
		{
			
		}
		
		
		return testData;
		
	}

	@DataProvider(name="ExcelData")
	public Object[][] getTestData()
	{
		//Excel properties
		String query="SELECT * FROM TEST_DATA ";
		//String query="SELECT * FROM AUTHORIZATION";
		Object[][] objArray = null;
		List<Map<String,String>> testDataList = new ArrayList<>();
		
		//Fillo properties
		Fillo fillo  = new Fillo();
		Connection connection = null;
		Recordset recordset=null;
		
		try {
			
			connection=fillo.getConnection(filepaths.EXCEL_TEST_DATA);
			recordset=connection.executeQuery(query);
			
			System.out.println("Query executed");
			
			while(recordset.next())
			{
				Map<String,String> testData = new LinkedHashMap<>();
				for(String field:recordset.getFieldNames())
				{
					testData.put(field,recordset.getField(field));
					
				}
				testDataList.add(testData);
				
			}
			
			objArray=new Object[testDataList.size()][1];
			for(int i=0;i<testDataList.size();i++)
			{
				objArray[i][0]=testDataList.get(i);
				
			}
		}
		catch(FilloException exception){
			exception.printStackTrace();
			
		}
		return objArray;
		
	}

	@Test(dataProvider="ExcelData")
public void DataDrivenTesting(Map<String,String> testData)
{
	System.out.println("test data is"+testData.get("username"));
}
}
