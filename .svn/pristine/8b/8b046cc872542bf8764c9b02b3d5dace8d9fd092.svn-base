package com.citsamex.service.tony;

import com.citsamex.app.util.CommonUtil;

public class EmailCond extends AbstractCondition {

	@Override
	public String visit(String filter) {
		String string = "";
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		if (filter.indexOf("*") != -1) {
			string = " USER_EMAIL like '" + filter.replaceAll("\\*", "%") + "'";
		} else if (CommonUtil.checkText(filter, regex))
			string = " USER_EMAIL='" + filter + "'";
		else {
			if (this.getNext() != null) {
				string = this.getNext().visit(filter);
			}
		}
		return string;
	}

}
