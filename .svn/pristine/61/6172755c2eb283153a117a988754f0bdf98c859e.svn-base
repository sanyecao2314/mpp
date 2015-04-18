package com.citsamex.app.validate;

import com.citsamex.app.util.CommonUtil;

public class LengthValidator extends AbstractValidator {

	private int min = 0;
	private int max = 0;

	@Override
	public boolean valid(Object object) {
		String value = "";
		if (object != null) {
			value = object.toString();
		}
		if (min > 0) {
			if (value.length() < min) {
				return false;
			}
		}

		if (max > 0) {
			if (value.length() > max) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void setParameter(String params) {
		min = 0;
		max = 0;

		if (CommonUtil.isEmpty(params)) {
			return;
		}

		String[] parameters = params.split(",");
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i].indexOf("min=") != -1) {
				try {
					min = Integer.parseInt(parameters[i].substring(parameters[i].indexOf("=") + 1));
				} catch (Exception ex) {
					min = 0;
				}
			} else if (parameters[i].indexOf("max=") != -1) {
				try {
					max = Integer.parseInt(parameters[i].substring(parameters[i].indexOf("=") + 1));
				} catch (Exception ex) {
					max = 0;
				}
			}
		}

	}

}
