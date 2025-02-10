package com.nn.apibootcamp.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nn.apibootcamp.constants.EnvironmentConfiguration;
import com.nn.apibootcamp.payload.User;

public class UserEndpoints {
	
	
	//create user
	public static Response createUser(User payload) throws JsonProcessingException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		String requestBody =objectMapper.writeValueAsString(payload);
		System.out.println("RequestBody is "+requestBody);
		System.out.println("POST URL is"+EnvironmentConfiguration.BASE_URL+EnvironmentConfiguration.POST);
		
		
		Response response =given()
				.auth().basic(EnvironmentConfiguration.USERNAME,EnvironmentConfiguration.PASSWORD)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(requestBody)
				.when()
				.post(EnvironmentConfiguration.BASE_URL+EnvironmentConfiguration.POST)
				;
		
		System.out.println("response is generated as "+response.getStatusCode());
		
		System.out.println("Response Body: " + response.getBody().asString());
		System.out.println("Response Headers: " + response.getHeaders());

		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Message: " + response.getStatusLine());

		return response;
	}

	//Get By FirstName
	public static Response readUserByFirstName(String userName)
	{
		System.out.println("Get request path is :"+Routes.GET_FIRSTNAME_URL);
		Response response = given()
				.auth().basic(EnvironmentConfiguration.USERNAME,EnvironmentConfiguration.PASSWORD)
				.pathParam("username",userName).when()
				.get(Routes.GET_FIRSTNAME_URL);
		
		return response;
				
	}
	
	//Get By UserId
	public static Response readUserByUserId(int userId)
	{
		System.out.println("Get request path is :"+Routes.GET_FIRSTNAME_URL);
		Response response = given()
				.auth().basic(EnvironmentConfiguration.USERNAME,EnvironmentConfiguration.PASSWORD)
				.pathParam("userId",userId).when()
				.get(Routes.GET_USERID_URL);
		
		return response;
				
	}

    //Put
	public static Response updateuser(int userId,User payload) throws JsonProcessingException
	{
		User update_request= new User(payload.getUser_first_name()+"aa",payload.getUser_last_name(),payload.getUser_email_id(),payload.getUser_contact_number()
				,payload.getUserAddress());
		
		ObjectMapper objectMapper = new ObjectMapper();
		String requestBody =objectMapper.writeValueAsString(update_request);
		System.out.println("Update request"+Routes.PUT_URL);
		Response response = given()
				.auth().basic(EnvironmentConfiguration.USERNAME,EnvironmentConfiguration.PASSWORD)
				.contentType(ContentType.JSON)
		        .accept(ContentType.JSON)
		        .pathParam("userId", userId)
		        .body(requestBody)
		        .when()
		        .put(Routes.PUT_URL);
		return response;
				
	}

	//Delete By UserId
	public static Response reduceUserByUserId(int  userId)
	{
		Response response=given()
				.auth().basic(EnvironmentConfiguration.USERNAME,EnvironmentConfiguration.PASSWORD)
				.pathParam("userId", userId)
				.when()
				.delete(Routes.DELETE_USERID_URL);
		return response;
	}
	
	//Delete By FirstName
	public static Response reduceUserByFirstName(String  userFirstName)
	{
		Response response=given()
				.auth().basic(EnvironmentConfiguration.USERNAME,EnvironmentConfiguration.PASSWORD)
				.pathParam("username", userFirstName)
				.when()
				.delete(Routes.DELETE_FIRSTNAME_URL);
		return response;
	}

}
