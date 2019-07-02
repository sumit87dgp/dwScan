package com.amd.dropwizardPOC.resources;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.jetty.http.HttpStatus;

import com.amd.dropwizardPOC.api.ResponseRepresentation;
import com.amd.dropwizardPOC.core.ScanMaps;
import com.amd.dropwizardPOC.db.ScanDao;
import com.amd.dropwizardPOC.security.Authenticator;
import com.amd.dropwizardPOC.security.User;
import com.codahale.metrics.annotation.Timed;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

//@PermitAll
@Path("/scan")
@Produces(MediaType.APPLICATION_JSON)
public class ScanResource {

	private ScanDao scanDao;

	public ScanResource(ScanDao scanDao) {
		this.scanDao = scanDao;
	}
	
	//@Authenticator
	@GET
	@Path("/allscans")
	@UnitOfWork
	@Timed
	public List<ScanMaps> getAllScans(@Auth User user){
		return scanDao.findall();
	}
	
	@PermitAll
	@GET
	@Path("/scanmaps")
	@UnitOfWork
	@Timed
	public ResponseRepresentation<List<ScanMaps>> getScanMaps() {
		return new ResponseRepresentation<List<ScanMaps>>(HttpStatus.OK_200,scanDao.findall());
	}
}
