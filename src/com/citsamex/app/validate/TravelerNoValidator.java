package com.citsamex.app.validate;

public class TravelerNoValidator extends AbstractValidator {

	@Override
	public boolean valid(Object object) {
		if (object == null) {
			return false;
		}
		String value = object.toString();

		if (value.length() != 10) {
			if (value.indexOf("-") != 10) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void setParameter(String params) {

	}

}
