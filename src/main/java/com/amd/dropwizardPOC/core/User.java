package com.amd.dropwizardPOC.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Objects;
//import org.hibernate.*;
//import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "scan_Users")
@NamedQueries(
    {
        @NamedQuery(
            name = "com.amd.dropwizardPOC.core.User.findAll",
            query = "SELECT s FROM User s"
        )
    })
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "UserId")
	private String UserId;
	
	@Column(name = "FirstName")
	private String FirstName;
	
	@Column(name = "LastName")
	private String LastName;

	public User() {
		// TODO Auto-generated constructor stub
	}
	
//	public User( String UserId, String FirstName, String LastName) {
//		// TODO Auto-generated constructor stub
//		//this.id = id;
//		this.FirstName = FirstName;
//		this.LastName = LastName;
//		this.UserId = UserId;
//
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}
	
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (!(o instanceof User)) {
	            return false;
	        }

	        final User that = (User) o;

	        return Objects.equals(this.id, that.id) &&
	                Objects.equals(this.FirstName, that.FirstName) &&
	                Objects.equals(this.LastName, that.LastName);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id, FirstName, LastName);
	    }

}
