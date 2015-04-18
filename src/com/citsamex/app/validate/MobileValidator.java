package com.citsamex.app.validate;

import com.citsamex.app.util.CommonUtil;

public class MobileValidator extends AbstractValidator {

	@Override
	public boolean valid(Object object) {
		if (object == null) {
			return false;
		}
		String value = object.toString();
		if (CommonUtil.isEmpty(value)) {
			return true;
		}
		if (!checkMobile(value)) {
			return false;
		}
		return true;
	}

	@Override
	public void setParameter(String params) {

	}

	public boolean checkMobile(String mobile) {
		String regex = "\\+{0,1}\\d{1,}";
		return CommonUtil.checkText(mobile, regex);
	}
}
