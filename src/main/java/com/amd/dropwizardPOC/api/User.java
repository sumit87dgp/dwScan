package com.amd.dropwizardPOC.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	
	private int id;
	private String userId;
	private String firstName;
	private String lastName;
	private String emailId;
	
	
	private User() {
		
	}
	
	public User(String userId,String firstName, String lastName, String emailId ) {
		// TODO Auto-generated constructor stub
		//this.id= id;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
	}
	
	@JsonProperty
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@JsonProperty
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@JsonProperty
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@JsonProperty
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonProperty
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
