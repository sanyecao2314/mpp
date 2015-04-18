package com.citsamex.service.tony;

import com.citsamex.app.util.CommonUtil;

public class MobileCond extends AbstractCondition {

	@Override
	public String visit(String filter) {
		String string = "";
		String regex = "\\d{11}";

		if (CommonUtil.checkText(filter, regex))
			string = " USER_MOBILE like '%" + filter + "%'";
		else {
			if (this.getNext() != null) {
				string = this.getNext().visit(filter);
			}
		}
		return string;
	}

}
