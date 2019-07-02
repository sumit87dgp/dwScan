package com.amd.dropwizardPOC.security;

import java.io.UnsupportedEncodingException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.time.Instant;
//import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.amd.dropwizardPOC.api.TokenAPI;
import com.amd.dropwizardPOC.core.AuthTokens;
import com.amd.dropwizardPOC.db.AuthDao;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class TokenServices {
	
	private AuthDao authDao;
	private TokenAPI tokenAPI;
	
	public TokenServices(AuthDao authDao) {
		this.authDao = authDao;		
	}
	
	public TokenAPI generateAuthTokens(String userId) {
		
		tokenAPI = null;
		AuthTokens authTokens = new AuthTokens();
		
		Calendar calendarinstance = Calendar.getInstance(); 
		
		
		
		Date issueDate = new Date(calendarinstance.getTimeInMillis());
		Date expiresDate = new Date(calendarinstance.getTimeInMillis() + (3*60000));
		
		//String issueddateString = dateFormat.format(issueDate);
		//String expiredateString = dateFormat.format(expiresDate);
		
		
		try {
			
			// Creating the jws Token
			String jwString =Jwts.builder()
					.setIssuer("DropWizPOC")
					.setSubject(userId)
					.claim("name", userId)
					.setIssuedAt(issueDate)
					.setExpiration(expiresDate)
					.signWith(SignatureAlgorithm.HS256, "dw-secret".getBytes("UTF-8"))
					.compact();
			
			
			if(!jwString.isEmpty()) {
				
				authTokens.setPrincipal(userId);
				authTokens.setToken(jwString);
				authTokens.setIssuedOn(issueDate);
				authTokens.setExpiresOn(expiresDate);
				authTokens =  authDao.insertTokens(authTokens);
				tokenAPI = new TokenAPI(authTokens.getToken());
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tokenAPI;
		
	}
	
	public String parseToken(String token) {
		String parseforPrinciaplString = null;
		try {
			Jws<Claims>jws =
					
					Jwts.parser().setSigningKey("dw-secret".getBytes("UTF-8")).parseClaimsJws(token);
					System.out.println(jws);
					//System.out.println();
					parseforPrinciaplString= jws.getBody().getSubject();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		return parseforPrinciaplString;
		
	}
	
	public boolean ValidateToken(String token) {
		boolean IsValidToken = false;
		try {
			String userIdString = parseToken(token);
			String tokenInDB = authDao.findByPrinciapl(userIdString).getToken();
			System.out.println(token);
			System.out.println(tokenInDB);
			if(tokenInDB.equals(token)) {
				IsValidToken = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		
		return IsValidToken;
	}
}
