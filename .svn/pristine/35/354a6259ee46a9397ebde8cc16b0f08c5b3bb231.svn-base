package com.citsamex.app.validate;

import java.lang.reflect.Field;
import java.util.Calendar;

import com.citsamex.annotation.valid.Indicator;
import com.citsamex.annotation.valid.MaxLength;
import com.citsamex.app.util.CommonUtil;

public class ExcelDownlaodValidator {

	private String[] message = new String[0];
	private String[] value = new String[0];
	private String[] fieldname = new String[0];

	@Deprecated
	public boolean valid(Object obj) {
		boolean isValid = true;
		Field[] field = obj.getClass().getDeclaredFields();
		message = new String[field.length];
		value = new String[field.length];
		fieldname = new String[field.length];

		for (int i = 0; i < field.length; i++) {
			message[i] = "";
			fieldname[i] = field[i].getName();
			if (field[i].getType() == String.class) {
				value[i] = (String) CommonUtil.getValue(obj, field[i].getName());
				if (value[i] == null) {
					value[i] = "";
				}
			} else if (field[i].getType() == Calendar.class) {
				Calendar c = (Calendar) CommonUtil.getValue(obj, field[i].getName());
				CommonUtil uti = new CommonUtil();
				value[i] = uti.formatDate(c);
			} else {
				Object val = CommonUtil.getValue(obj, field[i].getName());
				if (val != null) {
					value[i] = val.toString();
				} else {
					value[i] = "";
				}
			}
			if (field[i].isAnnotationPresent(Indicator.class)) {
				String indicator = field[i].getAnnotation(Indicator.class).value().toLowerCase();
				if (indicator.equals("EMAIL")) {
					if (value[i].indexOf("@") == -1 || value[i].indexOf(".") == -1) {
						message[i] += "Invalid email format.";
						isValid = false;
					}
				} else if (indicator.equals("PHONE")) {
					for (int j = 0; j < value[i].length(); j++) {
						char c = value[i].charAt(j);
						if ((c >= '0' && c <= '9') || c == '-') {

						} else {
							message[i] += "Invalid phone format.";
							isValid = false;
						}
					}

				} else if (indicator.equals("MOBILE")) {
					for (int j = 0; j < value[i].length(); j++) {
						char c = value[i].charAt(j);
						if ((c >= '0' && c <= '9')) {

						} else {
							message[i] += "Invalid mobile format.";
							isValid = false;
						}
					}
				} else if (indicator.equals("PASSPORT")) {
					for (int j = 0; j < value[i].length(); j++) {
						char c = value[i].charAt(j);
						if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {

						} else {
							message[i] += "Invalid passport format.";
							isValid = false;
						}
					}
				} else if (indicator.equals("NUMERIC")) {
					for (int j = 0; j < value[i].length(); j++) {
						char c = value[i].charAt(j);
						if ((c >= '0' && c <= '9')) {

						} else {
							message[i] += "Invalid numeric format.";
							isValid = false;
						}
					}
				} else if (indicator.equals("CCDDATE")) {

				}

			}

			if (field[i].isAnnotationPresent(MaxLength.class)) {
				int min = field[i].getAnnotation(MaxLength.class).min();
				int max = field[i].getAnnotation(MaxLength.class).max();
				String value = (String) CommonUtil.getValue(obj, field[i].getName());
				if (value == null)
					value = "";

				if (value.length() < min || value.length() > max) {
					message[i] += "Length must between " + min + " and " + max;
				}
			}
		}
		return isValid;
	}

	public String[] getMessage() {
		return message;
	}

	public String[] getValue() {
		return value;
	}

	public String[] getFieldName() {
		return fieldname;
	}
	// private boolean regexMatch(String regex, String str) {
	// Pattern p = Pattern.compile(regex);
	// Matcher m = p.matcher(str);
	// return m.find();
	// }

}
