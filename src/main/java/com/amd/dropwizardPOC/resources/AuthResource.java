package com.amd.dropwizardPOC.resources;


import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.jetty.http.HttpStatus;

import com.amd.dropwizardPOC.api.LoginProfile;
import com.amd.dropwizardPOC.api.ResponseRepresentation;
import com.amd.dropwizardPOC.api.TokenAPI;
import com.amd.dropwizardPOC.api.User;
import com.amd.dropwizardPOC.ldapService.AuthServiceWithLDAP;
import com.amd.dropwizardPOC.security.TokenServices;
import com.codahale.metrics.annotation.Timed;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

	private TokenServices tokenServices;

	public AuthResource(TokenServices tokenServices) {
		// TODO Auto-generated constructor stub
		this.tokenServices = tokenServices;
	}

	@POST
	@Path("/login")
	public ResponseRepresentation<User> AuthenticateUser(LoginProfile loginUser) {
		// User usrUser =null;
		ResponseRepresentation<User> responseRepresentation = null;
		if (loginUser.getLoginIdString() == null || loginUser.getPasswordString() == null) {
			// return false;
			// return usrUser;
			responseRepresentation = new ResponseRepresentation<User>(HttpStatus.BAD_REQUEST_400, null);
		}
//		if( AuthServiceWithLDAP.AuthenticateUser(loginUser.getLoginIdString(), 
//					loginUser.getPasswordString(), loginUser.getLoginIdString())) {
//			usrUser = new User(loginUser.getLoginIdString(), "xyz.amd.com");
//		}
		User userObjApi = AuthServiceWithLDAP.AuthenticateUser(loginUser.getLoginIdString(),
				loginUser.getPasswordString(), loginUser.getLoginIdString());

		if (userObjApi == null) {
			responseRepresentation = new ResponseRepresentation<User>(HttpStatus.UNAUTHORIZED_401, null);
		} else {
			responseRepresentation = new ResponseRepresentation<User>(HttpStatus.OK_200, userObjApi);
		}

		return responseRepresentation;
	}


	@POST
	@Path("/login1")
	@UnitOfWork
	@Timed
	public ResponseRepresentation<TokenAPI> TokenAuthenticateUser(LoginProfile loginUser) {
		// User usrUser =null;
		ResponseRepresentation<TokenAPI> responseRepresentation = null;
		
		if (loginUser.getLoginIdString() == null || loginUser.getPasswordString() == null) {
			// return false;
			// return usrUser;
			responseRepresentation = new ResponseRepresentation<TokenAPI>(HttpStatus.BAD_REQUEST_400, null);
		}
//		if( AuthServiceWithLDAP.AuthenticateUser(loginUser.getLoginIdString(), 
//					loginUser.getPasswordString(), loginUser.getLoginIdString())) {
//			usrUser = new User(loginUser.getLoginIdString(), "xyz.amd.com");
//		}
		User userObjApi = AuthServiceWithLDAP.AuthenticateUser(loginUser.getLoginIdString(),
				loginUser.getPasswordString(), loginUser.getLoginIdString());

		if (userObjApi == null) {
			// responseRepresentation = new
			// ResponseRepresentation<User>(HttpStatus.UNAUTHORIZED_401,null);
			responseRepresentation = new ResponseRepresentation<TokenAPI>(HttpStatus.UNAUTHORIZED_401, null);
		} else {
			
			responseRepresentation = new ResponseRepresentation<TokenAPI>(HttpStatus.OK_200,
					tokenServices.generateAuthTokens(userObjApi.getUserId()));
		}

		return responseRepresentation;
	}

}
