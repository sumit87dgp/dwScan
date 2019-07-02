package com.amd.dropwizardPOC.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenAPI {
	
	private String token;
	private int expiresIn = 300;
	
	public TokenAPI(String token) {
		this.token = token;
		this.expiresIn = 300;
	}

	@JsonProperty
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@JsonProperty
	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	
	

}
