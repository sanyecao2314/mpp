package com.citsamex.app.validate.db;

import com.citsamex.service.db.MppBasePo;

@SuppressWarnings("serial")
public class ValidObjectPo extends MppBasePo {
	private long id;
	private String name;
	private String validator;
	private String parameters;
	private String message;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
