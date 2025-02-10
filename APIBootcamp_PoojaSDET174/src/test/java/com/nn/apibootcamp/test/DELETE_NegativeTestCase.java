package com.nn.apibootcamp.test;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.nn.apibootcamp.constants.EnvironmentConfiguration;
import com.nn.apibootcamp.endpoints.Routes;
import com.nn.apibootcamp.utilities.ExcelUtility;
import com.nn.apibootcamp.utilities.LoggerLog4j;

import io.restassured.response.Response;

public class DELETE_NegativeTestCase {
	//user not found by userid
	
	@BeforeClass
	public void setupData() throws FileNotFoundException, IOException
	{
		ExcelUtility obj = new ExcelUtility();
		obj.getAuthorizationData();
		obj.getEndPointData();
     }
	@Test(dataProvider="PositiveTestData" ,dataProviderClass=ExcelUtility.class)
	public void Validate_deleteBy_incorrectUserId(Map<String,String> TEST_DATA)
	{
		LoggerLog4j.info(" Delete operation Negative Testcase started");
		int userId = 118999;
		Response response=given()
				.auth().basic(EnvironmentConfiguration.USERNAME,EnvironmentConfiguration.PASSWORD)
				.pathParam("userId", userId)
				.when()
				.delete(Routes.DELETE_USERID_URL);
		
		Assert.assertEquals(response.getStatusCode(), 404);
		Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
		//Assert.assertEquals(response.getStatusLine(), "Not Found");
				
	}
	
	//invalid endpoint
	@Test(dataProvider="PositiveTestData" ,dataProviderClass=ExcelUtility.class)
	public void Validate_DeleteEndPoint(Map<String,String> TEST_DATA)
	{
		
		int userId = 118999;
		Response response=given()
				.auth().basic(EnvironmentConfiguration.USERNAME,EnvironmentConfiguration.PASSWORD)
				.when()
				.delete(EnvironmentConfiguration.BASE_URL+"/de");
		Assert.assertEquals(response.getStatusCode(), 404);
		Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
		//Assert.assertEquals(response.getStatusLine(), "Bad Request");	
		LoggerLog4j.info(" Delete operation Negative Testcase completed");
				
	}
	
	

}
