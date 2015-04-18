package com.citsamex.app.validate;

public class NumberEngcharValidator extends AbstractValidator {

	@Override
	public boolean valid(Object object) {
		if (object == null) {
			return false;
		}
		String value = object.toString();

		for (int i = 0; i < value.length(); i++) {
			if (!((value.charAt(i) >= 65 && value.charAt(i) <= 90) || (value.charAt(i) >= 97 && value.charAt(i) <= 122) || (value
					.charAt(i) >= 48 && value.charAt(i) <= 57))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void setParameter(String params) {

	}
}
