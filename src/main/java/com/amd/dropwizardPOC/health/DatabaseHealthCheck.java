package com.amd.dropwizardPOC.health;
import org.hibernate.boot.model.relational.Database;

import com.codahale.metrics.health.HealthCheck;
public class DatabaseHealthCheck extends HealthCheck {
	private final Database database;
	
	public DatabaseHealthCheck(Database database) {
		// TODO Auto-generated constructor stub
		this.database = database;
	}
 
	@Override
	protected Result check() throws Exception {
		
		//if()
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
