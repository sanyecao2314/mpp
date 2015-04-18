package com.citsamex.app.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

	protected final static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	protected final static SimpleDateFormat datetimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public void copyProperties(Object dest, Object src) {
		Field[] fields = src.getClass().getDeclaredFields();
		for (Field field : fields) {
			// it could be improved by annotation later.
			Object value = null;
			String fName = field.getName();
			String property = transform(fName);
			if (fName.equals("serialVersionUID")) {
				continue;
			}
			try {
				value = src.getClass()
						.getMethod("get" + property, new Class[] {})
						.invoke(src, new Object[] {});

				dest.getClass()
						.getMethod("set" + property,
								new Class[] { field.getType() })
						.invoke(dest, new Object[] { value });

			} catch (Exception e) {
				// e.printStackTrace();
				continue;
			}
		}
	}

	public void copyProperties(Object dest, Object src, boolean skipNull) {
		Field[] fields = src.getClass().getDeclaredFields();
		for (Field field : fields) {
			// it could be improved by annotation later.
			Object value = null;
			String fName = field.getName();
			String property = transform(fName);
			if (fName.equals("serialVersionUID")) {
				continue;
			}
			if (fName.equals("id")) {
				continue;
			}

			try {
				value = src.getClass()
						.getMethod("get" + property, new Class[] {})
						.invoke(src, new Object[] {});

				if (skipNull && value == null) {
					continue;
				}

				dest.getClass()
						.getMethod("set" + property,
								new Class[] { field.getType() })
						.invoke(dest, new Object[] { value });

			} catch (Exception e) {
				// e.printStackTrace();
				continue;
			}
		}
	}

	public static Object newInstance(String classname) {
		try {
			return Class.forName(classname).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Calendar parseDate(String source) {
		Calendar c = null;
		Date date = null;
		try {
			date = dateFormat.parse(source);
			c = Calendar.getInstance();
			c.setTime(date);
		} catch (ParseException e) {
			// e.printStackTrace();
		}

		return c;
	}

	public Calendar parseDatetime(String source) {
		Calendar c = null;
		Date date = null;
		try {
			date = datetimeFormat.parse(source);
			c = Calendar.getInstance();
			c.setTime(date);
		} catch (ParseException e) {
			// e.printStackTrace();
		}

		return c;
	}

	private String transform(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	public String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		return dateFormat.format(date);
	}

	public String formatDate(Calendar date) {
		if (date == null) {
			return "";
		}
		return dateFormat.format(date.getTime());
	}

	public static void setValue(Object obj, String fieldName, Object value) {
		try {

			Field field = obj.getClass().getDeclaredField(fieldName);

			// if calendar value.
			if (field.getType().equals(java.util.Calendar.class)
					&& value instanceof java.lang.String) {
				if (value != null && !value.equals("")) {
					try {

						SimpleDateFormat dateformat = new SimpleDateFormat(
								"yyyy-MM-dd");
						Calendar c = Calendar.getInstance();
						c.setTime(dateformat.parse((String) value));
						value = c;
					} catch (Exception ex) {
						value = null;
						ex.printStackTrace();
					}
				}
			}
			String methodName = "set" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			obj.getClass()
					.getMethod(methodName, new Class[] { field.getType() })
					.invoke(obj, new Object[] { value });
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public static Object getValue(Object obj, String fieldName) {
		Object retVal = null;
		try {
			String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);

			retVal = obj.getClass().getMethod(methodName, new Class[] {})
					.invoke(obj, new Object[] {});
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return retVal;
	}

	public static boolean isEmpty(String str) {
		if (str == null || str.isEmpty()) {
			return true;
		}
		return false;
	}

	public static String[] split(String text, String delimiter) {
		StringTokenizer token = new StringTokenizer(text, delimiter);
		String[] strs = new String[token.countTokens()];
		for (int i = 0; token.hasMoreTokens(); i++) {
			strs[i] = token.nextToken();
		}

		return strs;
	}

	public static boolean checkText(String text, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		if (m.find()) {
			if (m.group().equals(text)) {
				return true;
			}
		}
		return false;
	}

	public static String xmlCharFilter(String xml) {
		xml = xml.replaceAll("&#x0;", "");
		return xml;
	}

	public static void main(String args[]) {
		String regex = "[SBC][\\d\\S]{9}";
		checkText("C1425D1241",regex);
		String regex2 = "\\d{18}|\\d{17}[Xx]";
		System.out.println(checkText("",regex2));
	}
	
}
