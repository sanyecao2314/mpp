package com.citsamex.app.validate;

import com.citsamex.app.util.CommonUtil;

public class IDCDValidator extends AbstractValidator {

	@Override
	public boolean valid(Object object) {
		if (object == null) {
			return false;
		}
		String value = object.toString();
		if (CommonUtil.isEmpty(value)) {
			return true;
		}
		if (!checkIDCD(value)) {
			return false;
		}
		return true;
	}

	@Override
	public void setParameter(String params) {

	}

	public boolean checkIDCD(String idcd) {
		//String regex = "\\d{18}|\\d{17}[Xx]|\\d{15}|\\d{14}[Xx]";
	    
	    // non support 15 length id no
		String regex = "\\d{18}|\\d{17}[Xx]";
		if ("".equals(idcd)) {
		    return true;
		}
		return CommonUtil.checkText(idcd, regex);
	}

}
