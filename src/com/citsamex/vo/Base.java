package com.citsamex.vo;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

import com.citsamex.app.util.CommonUtil;

@SuppressWarnings("serial")
public abstract class Base implements Serializable, Cloneable {

	protected String primaryKey;
	protected String action;
	protected String syncsystems;

	public String getSyncsystems() {
		return syncsystems;
	}

	public void setSyncsystems(String syncsystems) {
		this.syncsystems = syncsystems;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	};

	@Override
	public Base clone() {
		Object object = null;
		try {
			object = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return (Base) object;
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
			System.out.println(e);
		} catch (IOException e1) {
			System.out.println(e1);
		}

		return buffer.toString();
	}

	// for converter.
	public String getSingleClassname() {
		String classname = getClass().getName();
		if (classname.indexOf(".") != -1) {
			classname = classname.substring(classname.lastIndexOf(".") + 1);
		}
		return classname;
	}

	public String toJson() {
		StringBuffer buffer = new StringBuffer();
		Field[] fields = this.getClass().getDeclaredFields();
		buffer.append("{");
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			buffer.append("\"");
			buffer.append(fieldName);
			buffer.append("\":\"");
			buffer.append(CommonUtil.getValue(this, fieldName));
			buffer.append("\"");
			if (i < fields.length - 1) {
				buffer.append(",");
			}

		}
		buffer.append("}");
		return buffer.toString();
	}
}
