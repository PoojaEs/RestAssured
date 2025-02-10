package com.nn.apibootcamp.test;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nn.apibootcamp.constants.EnvironmentConfiguration;
import com.nn.apibootcamp.endpoints.Routes;
import com.nn.apibootcamp.payload.User;
import com.nn.apibootcamp.payload.UserAddress;
import com.nn.apibootcamp.utilities.ExcelUtility;
import com.nn.apibootcamp.utilities.LoggerLog4j;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PUT_NegativeTestCase {
	
	@BeforeClass
	public void setupData() throws FileNotFoundException, IOException
	{
		ExcelUtility obj = new ExcelUtility();
		obj.getAuthorizationData();
		obj.getEndPointData();
     }
	//Test for duplicate contactnumber
	@Test(dataProvider="PositiveTestData" ,dataProviderClass=ExcelUtility.class)

	public void validate_Update_uniqueField_constraint(Map<String,String> TEST_DATA) throws JsonProcessingException
	{
		LoggerLog4j.info(" Put operation Negative Testcase started");
		int ZIP_CODE=Integer.parseInt(TEST_DATA.get("zipcode"));
		long CONTACT_NUM=374170999;
		//int userId = Integer.parseInt(TEST_DATA.get("update_userId"));
		int userId =11860;
		
		UserAddress USER_ADDRESS = new UserAddress(TEST_DATA.get("Plot_no"),TEST_DATA.get("street"),TEST_DATA.get("state"),TEST_DATA.get("country"),ZIP_CODE);
		User USER_PAYLOAD= new User(TEST_DATA.get("firstname"),TEST_DATA.get("lastname"),TEST_DATA.get("Email"),CONTACT_NUM,USER_ADDRESS);
		ObjectMapper objectMapper = new ObjectMapper();
		String requestBody =objectMapper.writeValueAsString(USER_PAYLOAD);
		
		System.out.println("Update request"+Routes.PUT_URL);
		Response response = given()
				.auth().basic(EnvironmentConfiguration.USERNAME,EnvironmentConfiguration.PASSWORD)
				.contentType(ContentType.JSON)
		        .accept(ContentType.JSON)
		        .pathParam("userId", userId)
		        .body(requestBody)
		        .when()
		        .put(Routes.PUT_URL);
		System.out.println("Response Headers: " + response.getHeaders());

		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Message: " + response.getStatusLine());
Assert.assertEquals(response.getStatusCode(), 400);
Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
//Assert.assertEquals(response.getStatusLine(), "Conflict");
				
	}

		//Test for incorrect endpoints
	
	@Test(dataProvider="PositiveTestData" ,dataProviderClass=ExcelUtility.class)
	public  void validate_incorrect_putEndpoint(Map<String,String> TEST_DATA) throws JsonProcessingException {
			
			int ZIP_CODE=Integer.parseInt(TEST_DATA.get("zipcode"));
			long CONTACT_NUM=Long.parseLong(TEST_DATA.get("contact_no"));
			//int userId = Integer.parseInt(TEST_DATA.get("update_userId"));
			int userId =11860;
			
			UserAddress USER_ADDRESS = new UserAddress(TEST_DATA.get("Plot_no"),TEST_DATA.get("street"),TEST_DATA.get("state"),TEST_DATA.get("country"),ZIP_CODE);
			User USER_PAYLOAD= new User(TEST_DATA.get("firstname"),TEST_DATA.get("lastname"),TEST_DATA.get("Email"),CONTACT_NUM,USER_ADDRESS);
			ObjectMapper objectMapper = new ObjectMapper();
			String requestBody =objectMapper.writeValueAsString(USER_PAYLOAD);
			
			Response response =given()
					.auth().basic(EnvironmentConfiguration.USERNAME,EnvironmentConfiguration.PASSWORD)
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
					.body(requestBody)
					.when()
					.put(EnvironmentConfiguration.BASE_URL+"/")
					;
			
		
			System.out.println("Response Headers: " + response.getHeaders());

			System.out.println("Status Code: " + response.getStatusCode());
			System.out.println("Response Message: " + response.getStatusLine());
	Assert.assertEquals(response.getStatusCode(), 404);
	Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
	//Assert.assertEquals(response.getStatusLine(), "Bad Request");
			
	LoggerLog4j.info(" Put operation Negative Testcase completed");
		}

}
