package com.citsamex.app.validate;

import com.citsamex.app.util.CommonUtil;

public class PhoneValidator extends AbstractValidator {

	@Override
	public boolean valid(Object object) {
		if (object == null) {
			return false;
		}
		String value = object.toString();
		if (CommonUtil.isEmpty(value)) {
			return true;
		}
		if (!checkPhone(value)) {
			return false;
		}
		return true;
	}

	@Override
	public void setParameter(String params) {

	}

	public boolean checkPhone(String phone) {
		String regex = "\\+{0,1}\\d{0,} {0,1}\\d{0,}-{0,1}\\d{0,}(EXT\\.\\d{1,10}){0,1}";
		return CommonUtil.checkText(phone, regex);
	}

}
