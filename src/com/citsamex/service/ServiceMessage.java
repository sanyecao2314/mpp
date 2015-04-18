package com.citsamex.service;

import java.io.Serializable;

import com.citsamex.app.Server;

public class ServiceMessage implements Serializable {

	private static final long serialVersionUID = 8088500072684914219L;

	private String code;
	private String message;
	private Object data;
	private boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ServiceMessage() {

	}

	public ServiceMessage(String code) {
		setCode(code);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		this.message = Server.getMessage(code);
		this.success = code.endsWith("00");
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getServiceMessage() {
		return code + "-" + message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
