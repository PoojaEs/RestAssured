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

public class GET_NegativeTestCase {
	
	@BeforeClass
	public void setupData() throws FileNotFoundException, IOException
	{
		ExcelUtility obj = new ExcelUtility();
		obj.getAuthorizationData();
		obj.getEndPointData();
     }
	
	@Test(dataProvider="PositiveTestData" ,dataProviderClass=ExcelUtility.class)

	public void validate_GetByincorrectUseriD(Map<String,String> TEST_DATA)
	
	{LoggerLog4j.info(" Get operation Negative Testcase started");
		int userId = 118999;
		System.out.println("Get request path is :"+Routes.GET_FIRSTNAME_URL);
		Response response = given()
				.auth().basic(EnvironmentConfiguration.USERNAME,EnvironmentConfiguration.PASSWORD)
				.pathParam("userId",userId).when()
				.get(Routes.GET_USERID_URL);
		
		Assert.assertEquals(response.getStatusCode(), 404);
		Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
		//Assert.assertEquals(response.getStatusLine(), "Not Found");
				
	}
		//user not found by username
	
	@Test(dataProvider="PositiveTestData" ,dataProviderClass=ExcelUtility.class)

	public void validate_GetByincorrectFirstName(Map<String,String> TEST_DATA)
	
	{
		String userName=TEST_DATA.get("firstname");
		System.out.println("Get request path is :"+Routes.GET_FIRSTNAME_URL);
		Response response = given()
				.auth().basic(EnvironmentConfiguration.USERNAME,EnvironmentConfiguration.PASSWORD)
				.pathParam("username",userName).when()
				.get(Routes.GET_FIRSTNAME_URL);
		
		Assert.assertEquals(response.getStatusCode(), 404);
		Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
		//Assert.assertEquals(response.getStatusLine(), "Not Found");	
				
	}
		//invalid endpoint
	
	@Test(dataProvider="PositiveTestData" ,dataProviderClass=ExcelUtility.class)

	public void validate_GetEndpoint(Map<String,String> TEST_DATA)
	
	{
		String userName=TEST_DATA.get("firstname");
		System.out.println("Get request path is :"+Routes.GET_FIRSTNAME_URL);
		Response response = given()
				.auth().basic(EnvironmentConfiguration.USERNAME,EnvironmentConfiguration.PASSWORD)
				.pathParam("username",userName).when()
				.get(Routes.GET_FIRSTNAME_URL+"");
		
		Assert.assertEquals(response.getStatusCode(), 404);
		Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
		//Assert.assertEquals(response.getStatusLine(), "Bad Request");	
		
		LoggerLog4j.info(" Get operation Negative Testcase completed");
	}
		

}
