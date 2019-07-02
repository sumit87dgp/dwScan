package com.amd.dropwizardPOC.security;

import java.security.Principal;
import java.util.Set;

// Adding Custom Principal

public class User implements Principal {
	
	private final String name;
	private final String emailId;	
	private final Set<String> roles;
	
	public User(String name,String emailId) {
		 this.name = name;
		 this.emailId = emailId;
		 this.roles = null;
	}
	
	@Override
	public String getName() {		
		return name;
	}
	
	public String getEmailId() {
		return emailId;
	}
	
	public Set<String> getRoles() {
		return roles;
	}

}
