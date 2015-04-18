package com.citsamex.service.tony;

import com.citsamex.app.util.CommonUtil;

public class CNameCond extends AbstractCondition {

	@Override
	public String visit(String filter) {
		String string = "";

		String regex = "[\u4E00-\u9FA5]*";

		if (CommonUtil.checkText(filter, regex))
			return " USER_NAME_CN like '%" + filter + "%'";
		else if (this.getNext() != null) {
			string = this.getNext().visit(filter);
		}

		return string;
	}

}
