package com.amd.dropwizardPOC.security;

import java.security.Principal;

public class CustomAuthUser implements Principal {

	private final String userId;
	private final Role role;

	public CustomAuthUser(String userId) {
		this.userId = userId;
		// For time being let every one be ADMIN
		// For Demonstration purpose
		this.role = Role.ADMIN;
	}

	@Override
	public String getName() {

		return userId;
	}

	public Role getRole() {
		return role;
	}

	public enum Role {
		ADMIN, NDA;
	}

}
