package com.citsamex.service;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.citsamex.app.util.CommonUtil;

public class Base {
	protected final static CommonUtil uti = new CommonUtil();
	protected Log log = LogFactory.getLog(this.getClass());
	protected String syncsystems;

	public String getSyncsystems() {
		return syncsystems;
	}

	public void setSyncsystems(String syncsystems) {
		this.syncsystems = syncsystems;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			buffer.append(fields[i].getName());
			buffer.append("=\"");
			buffer.append(CommonUtil.getValue(this, fields[i].getName()));
			buffer.append("\"; ");
		}

		return buffer.toString();
	}

	public String noNull(String value) {
		return value == null ? "" : value;
	}
}
