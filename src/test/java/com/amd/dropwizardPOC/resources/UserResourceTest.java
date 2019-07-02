package com.amd.dropwizardPOC.resources;

import com.amd.dropwizardPOC.api.User;
import com.amd.dropwizardPOC.db.UserDao;

import io.dropwizard.testing.junit.ResourceTestRule;
import javassist.expr.NewArray;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class UserResourceTest {
	private static final UserDao userdao = mock(UserDao.class);
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder() 
	.addResource(new UserResource(userdao))
	.build();
	
	private final User user = new User("a", "b", "c","d");
	private List<com.amd.dropwizardPOC.core.User> listofUsers = new ArrayList<com.amd.dropwizardPOC.core.User>();
	
	@Before
	public void setup() {
		com.amd.dropwizardPOC.core.User user1 = new com.amd.dropwizardPOC.core.User();
		user1.setId(5);
		user1.setFirstName("fname");
		user1.setLastName("lname");
		user1.setUserId("usid");
		//List<com.amd.dropwizardPOC.core.User> listofUsers = new ArrayList<>();
		listofUsers.add(user1);
		when(userdao.findall()).thenReturn(listofUsers);
		
		when(userdao.adduser(any(com.amd.dropwizardPOC.core.User.class))).thenReturn(1);
	}
	
	@After
	public void tearDown() {
		reset(userdao);
	}
	
	@Test
	public void testfetchAllUsers() {
		assertThat(resources.target("/user/allusers")
				.request().get(UserListExtention.class)).isEqualTo(listofUsers);
		
		verify(userdao).findall();
		
	}
	
	
	private static class UserListExtention extends ArrayList<com.amd.dropwizardPOC.core.User>{
		
	}
	
	
	
}
