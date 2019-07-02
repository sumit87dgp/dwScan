package com.amd.dropwizardPOC.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="auth_token")
@NamedQueries({
	@NamedQuery(
			name="com.amd.dropwizardPOC.core.AuthTokens.findByPrincipal",
			query="from AuthTokens at where at.principal = :principal"
			),
	@NamedQuery(
			name = "com.amd.dropwizardPOC.core.AuthTokens.deleteByPrincipal",
			query = "DELETE FROM AuthTokens at where at.principal = :principal"
			)
})

public class AuthTokens {
	
	@Transient
	private final int expiresIn = 300;
	
	@Id
	@PrimaryKeyJoinColumn
	@Column(name="principal")
	private String principal;
	
	@Column(name="token")
	private String token;
	
	@Column(name="issued_on",columnDefinition="DATETIME")
	private Date issuedOn;
	
	@Column(name="expiresOn",columnDefinition="DATETIME")
	private Date expiresOn;

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getToken() {
		return token;
	}
	
	@JsonProperty
	public void setToken(String token) {
		this.token = token;
	}

	public Date getIssuedOn() {
		return issuedOn;
	}

	public void setIssuedOn(Date issuedOn) {
		this.issuedOn = issuedOn;
	}

	public Date getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(Date expiresOn) {
		this.expiresOn = expiresOn;
	}
	
	@JsonProperty
	@Transient
	public int getExpiresIn() {
		return expiresIn;
		
	}

}
