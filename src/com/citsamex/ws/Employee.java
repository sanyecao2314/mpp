package com.citsamex.ws;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

public class Employee {

	private String id;
	private String usernameCn;
	private String usernameEn;
	private String firstnameCn;
	private String lastnameCn;
	private String firstnameEn;
	private String lastnameEn;
	private String nation;
	private String city;
	private String email;
	private String role;
	private boolean isSuccess;
	private String message;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsernameCn() {
		return usernameCn;
	}

	public void setUsernameCn(String usernameCn) {
		this.usernameCn = usernameCn;
	}

	public String getUsernameEn() {
		return usernameEn;
	}

	public void setUsernameEn(String usernameEn) {
		this.usernameEn = usernameEn;
	}

	public String getFirstnameCn() {
		return firstnameCn;
	}

	public void setFirstnameCn(String firstnameCn) {
		this.firstnameCn = firstnameCn;
	}

	public String getLastnameCn() {
		return lastnameCn;
	}

	public void setLastnameCn(String lastnameCn) {
		this.lastnameCn = lastnameCn;
	}

	public String getFirstnameEn() {
		return firstnameEn;
	}

	public void setFirstnameEn(String firstnameEn) {
		this.firstnameEn = firstnameEn;
	}

	public String getLastnameEn() {
		return lastnameEn;
	}

	public void setLastnameEn(String lastnameEn) {
		this.lastnameEn = lastnameEn;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static Employee load(String xml) {
		XMLDecoder decoder = null;
		try {
			decoder = new XMLDecoder(new ByteArrayInputStream(
					xml.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Employee employee = (Employee) decoder.readObject();
		return employee;
	}

}
