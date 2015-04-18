package com.citsamex.app.prof;

import java.lang.reflect.Field;
import java.util.Arrays;

import com.citsamex.service.Base;

public class ProfileBuilder extends Base {

	/**
	 * transform a obj to xml
	 * 
	 * @param name
	 * @param obj
	 * @param include
	 * @param exclude
	 * @return
	 */
	public static String transform(String name, Object obj, String[] include, String[] exclude) {
		String xml = "<" + name;

		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			Object value = null;
			String fName = field.getName();
			if (fName.equals("serialVersionUID")) {
				continue;
			}

			// include field name list
			if (include != null && include.length != 0 && Arrays.binarySearch(include, fName) < 0) {
				continue;
			}
			// exclude field name list
			if (exclude != null && exclude.length != 0 && Arrays.binarySearch(exclude, fName) >= 0) {
				continue;
			}

			xml += " " + fName + "=\"";
			String method = "get" + fName.substring(0, 1).toUpperCase() + fName.substring(1);
			try {
				value = obj.getClass().getMethod(method, new Class[] {}).invoke(obj, new Object[] {});

			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			if (value instanceof java.util.Calendar) {
				value = uti.formatDate((java.util.Calendar) value);
			}

			xml += value + "\"";
		}
		xml += ">";
		return xml;
	}
}
