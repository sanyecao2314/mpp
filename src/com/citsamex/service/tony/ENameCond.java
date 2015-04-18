package com.citsamex.service.tony;

public class ENameCond extends AbstractCondition {

	@Override
	public String visit(String filter) {

		return " (USER_NAME_EN like '%" + filter + "%' OR USER_CP_ID ='" + filter + "' OR USER_EMPNO ='" + filter
				+ "')";

	}

}
