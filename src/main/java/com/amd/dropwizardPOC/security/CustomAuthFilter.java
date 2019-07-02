package com.amd.dropwizardPOC.security;

import java.io.IOException;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

import io.dropwizard.auth.AuthFilter;

public class CustomAuthFilter extends AuthFilter<CustomCredentials, CustomAuthUser> {

	private CustomAuthenticator authenticator;

	public CustomAuthFilter(CustomAuthenticator authenticator) {
		this.authenticator = authenticator;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Optional<CustomAuthUser> authenticatedUser;
		try {
			
			CustomCredentials credentials = getCredentials(requestContext);
			authenticatedUser = authenticator.authenticate(credentials);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}
		// TODO Auto-generated method stub
		
	}
	
	private CustomCredentials getCredentials(ContainerRequestContext requestContext) {
		CustomCredentials credentials = new CustomCredentials();
		try {
			String[] rawtokenString = requestContext.getHeaders().getFirst("Authorization").split(" ");
			credentials.setToken(rawtokenString[1]);
		} catch (Exception e) {
			throw new WebApplicationException("Unable to parse credentials", Response.Status.UNAUTHORIZED);
		}
		return credentials;
	}

}
