package com.amd.dropwizardPOC.db;

import java.util.List;


import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.amd.dropwizardPOC.core.ScanMaps;


import io.dropwizard.hibernate.AbstractDAO;

public class ScanDao extends AbstractDAO<ScanMaps> {
	
	public ScanDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	public List<ScanMaps> findall() {
		return list((Query<ScanMaps>) namedQuery("com.amd.dropwizardPOC.core.ScanMaps.findAll"));
	}
}
