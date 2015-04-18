package com.citsamex.app.validate;

import com.citsamex.app.util.CommonUtil;

public class EmailValidator extends AbstractValidator {

	private int count = -1;

	@Override
	public boolean valid(Object object) {
		if (object == null) {
			return false;
		}
		String value = object.toString();
		String[] emails = CommonUtil.split(value, ",;");
		if (emails.length > count && count != -1) {
			return false;
		}

		for (int i = 0; i < emails.length; i++) {
			if (!checkEmail(emails[i]))
				return false;
		}
		return true;
	}

	@Override
	public void setParameter(String params) {
		count = -1;
		if (CommonUtil.isEmpty(params)) {
			return;
		}

		String[] parameters = params.split(",");
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i].indexOf("count=") != -1) {
				try {
					count = Integer.parseInt(parameters[i].substring(parameters[i].indexOf("=") + 1));
				} catch (Exception ex) {
					count = -1;
				}
			}
		}
	}

	public boolean checkEmail(String mail) {
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		return CommonUtil.checkText(mail, regex);
	}
	
	public static void main (String args[]) {
	    String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	    boolean f = CommonUtil.checkText("lily.lei@klatencor.com", regex);
	    System.out.println(f);
	}

}
