package com.amd.dropwizardPOC.api;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseRepresentation<T> {
	private long code;

	@Length(max = 3)
	private T data;

	public ResponseRepresentation() {
		// TODO Auto-generated constructor stub
	}

	public ResponseRepresentation(long code, T data) {
		this.code = code;
		this.data = data;
	}
	
	@JsonProperty
	public long getCode() {
		return code;
	}
	
	@JsonProperty
	public T getData() {
		return data;
	}

}
