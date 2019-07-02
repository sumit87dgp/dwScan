package com.amd.dropwizardPOC.security;

import java.util.Optional;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;

public class CustomAuthenticator implements Authenticator<CustomCredentials, CustomAuthUser> {

	private TokenServices tokenServices;
	
	public CustomAuthenticator(TokenServices tokenServices) {
		this.tokenServices = tokenServices;
	}

	@Override
	@UnitOfWork
	public Optional<CustomAuthUser> authenticate(CustomCredentials credentials) throws AuthenticationException {
		CustomAuthUser authenticatedUser = null;
		if(tokenServices.ValidateToken(credentials.getToken())) {
			authenticatedUser = new CustomAuthUser(tokenServices.parseToken(credentials.getToken()));
			//return Optional.of(new CustomAuthUser(tokenServices.parseToken(credentials.getToken())));
			//return Optional.of(new CustomAuthUser("testssu"));
		}
		return Optional.ofNullable(authenticatedUser);
	}
	
}
