package com.amd.dropwizardPOC.db;

import org.hibernate.SessionFactory;

import com.amd.dropwizardPOC.core.AuthTokens;

import io.dropwizard.hibernate.AbstractDAO;

public class AuthDao extends AbstractDAO<AuthTokens> {
	
	public AuthDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public AuthTokens findByPrinciapl(String principal) {
		return (AuthTokens) currentSession().get(AuthTokens.class, principal);
	}
	
	public AuthTokens insertTokens(AuthTokens authTokens) {
		try {
//			currentSession().beginTransaction();
//			currentSession().save(authTokens);
//			currentSession().getTransaction().commit();
			currentSession().persist(authTokens);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return authTokens;
		
	}
	
	public void deleteToken(AuthTokens authTokens) {
		currentSession().delete(authTokens);
	}
	
	public void updateToken(AuthTokens authTokens) {
		currentSession().saveOrUpdate(authTokens);
	}

}
