package com.citsamex.service.tony;

import com.citsamex.app.util.CommonUtil;

public class CustNoCond extends AbstractCondition {

	@Override
	public String visit(String filter) {
		String string = "";
		String regex = "\\S{6}\\d{4}";

		if (CommonUtil.checkText(filter, regex))
			string = " USER_CUSTNO = '" + filter + "'";
		else {
			if (this.getNext() != null) {
				string = this.getNext().visit(filter);
			}
		}
		return string;

	}

}
