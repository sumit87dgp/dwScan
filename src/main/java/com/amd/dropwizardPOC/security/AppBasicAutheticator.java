package com.amd.dropwizardPOC.security;

import java.util.Optional;

import com.amd.dropwizardPOC.ldapService.AuthServiceWithLDAP;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class AppBasicAutheticator implements Authenticator<BasicCredentials, User> {

	@Override
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		// TODO Auto-generated method stub
		//return null;
		com.amd.dropwizardPOC.api.User objUserAPI = AuthServiceWithLDAP.AuthenticateUser(credentials.getUsername(), credentials.getPassword(), credentials.getUsername());
//		if(AuthServiceWithLDAP.AuthenticateUser(credentials.getUsername(), credentials.getPassword(), credentials.getUsername())) {
//			return Optional.of(new User(credentials.getUsername(),"abc.amd.com"));
//		}
		if(objUserAPI.getUserId().equals(credentials.getUsername())) {
			// Password need not be necessary
			//System.out.println("UserName : " +credentials.getUsername());
			//System.out.println("Password : " +credentials.getPassword());
			return Optional.of(new User(credentials.getUsername(),credentials.getPassword()));
		}
		return Optional.empty();
	}

}
