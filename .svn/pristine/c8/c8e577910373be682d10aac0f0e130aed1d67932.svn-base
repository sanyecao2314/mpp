package com.citsamex.service;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Message {
	private boolean isSuccess;
	private String head;
	private String body;
	private String message;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toXML() {
		StringBuffer buffer = new StringBuffer();
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(byteArray);
		encoder.writeObject(this);
		encoder.flush();
		encoder.close();
		byte[] b = byteArray.toByteArray();

		try {
			String xml = new String(b, "UTF-8");
			buffer.append(xml);
			byteArray.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return buffer.toString();
	}

}
