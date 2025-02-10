package com.nn.apibootcamp.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	
	
	private String user_first_name;
	private String user_last_name;
	private String user_email_id;
	private long user_contact_number;
	private UserAddress userAddress;
	
	
	
	
	public User(String user_first_name, String user_last_name, String user_email_id, long user_contact_number,
			UserAddress userAddress) {
		//super();
		this.user_first_name = user_first_name;
		this.user_last_name = user_last_name;
		this.user_email_id = user_email_id;
		this.user_contact_number = user_contact_number;
		this.userAddress = userAddress;
	}
	
	


	public String getUser_first_name() {
		return user_first_name;
	}


	public void setUser_first_name(String user_first_name) {
		this.user_first_name = user_first_name;
	}


	public String getUser_last_name() {
		return user_last_name;
	}


	public void setUser_last_name(String user_last_name) {
		this.user_last_name = user_last_name;
	}


	public String getUser_email_id() {
		return user_email_id;
	}


	public void setUser_email_id(String user_email_id) {
		this.user_email_id = user_email_id;
	}


	public long getUser_contact_number() {
		return user_contact_number;
	}


	public void setUser_contact_number(long user_contact_number) {
		this.user_contact_number = user_contact_number;
	}


	public UserAddress getUserAddress() {
		return userAddress;
	}


	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}




	public User() {
		
	}


	


	

}
