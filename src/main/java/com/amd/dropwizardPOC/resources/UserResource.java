package com.amd.dropwizardPOC.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.amd.dropwizardPOC.api.LoginProfile;
import com.amd.dropwizardPOC.core.User;
import com.amd.dropwizardPOC.db.UserDao;
import com.codahale.metrics.annotation.Timed;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	
	private UserDao _userDao;
	
	public UserResource(UserDao userDao) {
		this._userDao = userDao;
	}
	
	@GET
	@Path("/allusers")
	@UnitOfWork
	@Timed
	public List<User> fetchAllUser() {
		return _userDao.findall();
	
	}
	
	@POST
	@Path("/login")
	@UnitOfWork
	@Timed
	public int addUser(LoginProfile login) {
		// TO DO: logic for ldap authentication
		User user = new User();
		user.setUserId(login.getLoginIdString());
		user.setFirstName(login.getPasswordString());
		user.setLastName("attempted xyz");
		return _userDao.adduser(user);
	}

}
