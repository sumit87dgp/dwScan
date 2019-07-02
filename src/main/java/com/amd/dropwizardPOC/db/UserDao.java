package com.amd.dropwizardPOC.db;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.amd.dropwizardPOC.core.User;
import com.google.common.base.Optional;

import io.dropwizard.hibernate.AbstractDAO;

public class UserDao extends AbstractDAO<User> {

	public UserDao(SessionFactory sessionFactory) {
		// TODO Auto-generated constructor stub
		super(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findall() {
		return list((Query<User>) namedQuery("com.amd.dropwizardPOC.core.User.findAll"));
	}
	
	public Optional<User> findById(int id) {
		return Optional.fromNullable(get(id));
	}
	
	public int adduser(User user) {
		System.out.println(user.getFirstName());
		System.out.println(user.getLastName());
	   return persist(user).getId();
	}
}
