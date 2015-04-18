package com.citsamex.app.validate;

import com.citsamex.app.util.CommonUtil;

public class TextViolentValidator extends AbstractValidator {

	private char[] cs = new char[] {};

	@Override
	public boolean valid(Object object) {
		if (object == null) {
			return false;
		}
		String value = object.toString();
		if (CommonUtil.isEmpty(value)) {
			return true;
		}
		if (!check(value)) {
			return false;
		}
		return true;
	}

	@Override
	public void setParameter(String params) {
		if (CommonUtil.isEmpty(params)) {
			return;
		}
		cs = params.toCharArray();
	}

	public boolean check(String value) {
		for (int i = 0; i < cs.length; i++) {
			if (value.indexOf(cs[i]) != -1) {
				return false;
			}
		}
		return true;
	}

}
