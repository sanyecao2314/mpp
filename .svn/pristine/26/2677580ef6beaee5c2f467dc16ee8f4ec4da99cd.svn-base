package com.citsamex.app.util;

public class ClassFactory {
	public final static String CONTACT = "com.citsamex.service.db.ContactPo";

	@SuppressWarnings("rawtypes")
	public static Class create(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}

}
