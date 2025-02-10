package com.nn.apibootcamp.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nn.apibootcamp.constants.EnvironmentConfiguration;
import com.nn.apibootcamp.endpoints.UserEndpoints;
import com.nn.apibootcamp.payload.User;
import com.nn.apibootcamp.payload.UserAddress;
import com.nn.apibootcamp.utilities.ExcelUtility;
import com.nn.apibootcamp.utilities.LoggerLog4j;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import junit.framework.Assert;

public class API_Chaining_PositiveTestCase {
	
	public int responseUserId;
	public String responseFirstName;
	public User responsePayload;
	
	@BeforeClass
	public void setupData() throws FileNotFoundException, IOException
	{
		ExcelUtility obj = new ExcelUtility();
		obj.getAuthorizationData();
		obj.getEndPointData();
     }
	
	@Test(priority=1, dataProvider="PositiveTestData", dataProviderClass=ExcelUtility.class)
public void testCreateUser(Map<String,String> TEST_DATA) throws JsonProcessingException
{
		LoggerLog4j.info(" API Request chaining implementation : Post Request started");
	int ZIP_CODE=Integer.parseInt(TEST_DATA.get("zipcode"));
	long CONTACT_NUM=Long.parseLong(TEST_DATA.get("contact_no"));
	
	UserAddress USER_ADDRESS = new UserAddress(TEST_DATA.get("Plot_no"),TEST_DATA.get("street"),TEST_DATA.get("state"),TEST_DATA.get("country"),ZIP_CODE);
	User USER_PAYLOAD= new User(TEST_DATA.get("firstname"),TEST_DATA.get("lastname"),TEST_DATA.get("Email"),CONTACT_NUM,USER_ADDRESS);
	
	Response response = UserEndpoints.createUser(USER_PAYLOAD);
	
	
	//Deserialization
	 User deserializedUser = response.as(User.class);
	 responsePayload = deserializedUser;
	
	
	responseUserId=response.jsonPath().getInt("user_id");
	responseFirstName=response.jsonPath().getString("user_first_name");
	
	
	Assert.assertEquals(response.getStatusCode(), 201);
	Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
	Assert.assertEquals(responseFirstName,TEST_DATA.get("firstname"));
	//Assert.assertEquals(response.jsonPath().getString("user_first_name"),TEST_DATA.get("firstname"),"User firstname matches with response");
	Assert.assertEquals(response.jsonPath().getString("user_last_name"),TEST_DATA.get("lastname"));
	//Assert.assertEquals(response.jsonPath().getLong(),CONTACT_NUM,"User firstname matches with response");
	Assert.assertEquals(response.jsonPath().getString("user_email_id"),TEST_DATA.get("Email"));
	//Assert.assertEquals(response.jsonPath().getString("user_first_name"),TEST_DATA.get("firstname"),"User firstname matches with response");
	//Assert.assertEquals(response.jsonPath().getString("user_first_name"),TEST_DATA.get("firstname"),"User firstname matches with response");

	LoggerLog4j.info(" Post Request Assertion completed");
	
}
	@Test(priority=3,dependsOnMethods= {"testGetUserByFirstName"})
	public void testGetUserByUserId()
	{
		LoggerLog4j.info(" API Request chaining implementation : Get by userId Request started");
		Response response = UserEndpoints.readUserByUserId(responseUserId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
		LoggerLog4j.info(" Get Request Assertion completed");
	}
	
	@Test(priority=2, dependsOnMethods= {"testCreateUser"})
	public void testGetUserByFirstName()
	{
		LoggerLog4j.info(" API Request chaining implementation : Get by firstname Request started");
		Response response = UserEndpoints.readUserByFirstName(responseFirstName);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
		LoggerLog4j.info(" Get Request Assertion completed");
	}

	@Test(priority=4,dependsOnMethods= {"testGetUserByUserId"})
	public void testUpdateUser() throws JsonProcessingException
	{
		LoggerLog4j.info(" API Request chaining implementation : Put Request started");
		Response response = UserEndpoints.updateuser(responseUserId, responsePayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
		LoggerLog4j.info(" Put Request Assertion completed");
	}
	
	@Test(priority=5,dependsOnMethods= {"testUpdateUser"})
	public void testDeleteByUserId()
	{
		LoggerLog4j.info(" API Request chaining implementation : Delete Request started");
		Response response = UserEndpoints.reduceUserByUserId(responseUserId);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
		LoggerLog4j.info(" Delete Request Assertion completed");
	}

}
