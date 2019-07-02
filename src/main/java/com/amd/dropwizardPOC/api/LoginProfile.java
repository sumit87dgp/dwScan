package com.amd.dropwizardPOC.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginProfile {
	
	private String loginIdString;
	private String passwordString;
	private Boolean rememberMeBoolean;
	
	@JsonProperty
	public String getLoginIdString() {
		return loginIdString;
	}
	public void setLoginIdString(String loginIdString) {
		this.loginIdString = loginIdString;
	}
	
	@JsonProperty
	public String getPasswordString() {
		return passwordString;
	}
	public void setPasswordString(String passwordString) {
		this.passwordString = passwordString;
	}
	
	@JsonProperty
	public Boolean getRememberMeBoolean() {
		return rememberMeBoolean;
	}
	public void setRememberMeBoolean(Boolean rememberMeBoolean) {
		this.rememberMeBoolean = rememberMeBoolean;
	}

}
