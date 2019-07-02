package com.amd.dropwizardPOC.security;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AuthenticateFilter implements ContainerRequestFilter {

	private TokenServices tokenServices;

	private static final String PARAM_API_KEY = "authtoken";

	public AuthenticateFilter(TokenServices tokenServices) {
		this.tokenServices = tokenServices;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub
		System.out.println(requestContext.getHeaders());
		if (requestContext.getHeaders().getFirst(PARAM_API_KEY) == null) {
			
		 requestContext.abortWith(respondUnAutorized());
		 
		}
		else if (!authenticateToken(requestContext.getHeaderString(PARAM_API_KEY))) {
			requestContext.abortWith(respondUnAutorized());
		}

	}

	private Response respondUnAutorized() {
		return Response.status(Response.Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON).entity("UnAuthorized")
				.build();
	}

	private boolean authenticateToken(String token) {
		boolean isTokenValid = false;
		if (tokenServices.ValidateToken(token)) {
			isTokenValid = true;
		}
		return isTokenValid;
	}

}
