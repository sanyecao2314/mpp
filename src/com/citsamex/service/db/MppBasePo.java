package com.citsamex.service.db;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.citsamex.app.util.CommonUtil;
import com.citsamex.service.Base;

public class MppBasePo extends Base implements Serializable {
	protected static final long serialVersionUID = 7966552471606945960L;

	public void setValue(String fieldName, Object value) {
		try {
			Field field = this.getClass().getDeclaredField(fieldName);
			field.set(this, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object getValue(String fieldName) {
		Object retVal = null;
		try {
			Field field = this.getClass().getDeclaredField(fieldName);
			retVal = field.get(this);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return retVal;
	}

	public Object createHisObject() {

		String classname = this.getClass().getName();

		if (classname.endsWith("HisPo")) {
			return null;
		}
		if (classname.indexOf("Po") != -1) {
		    classname = classname.substring(0, classname.length() - 2) + "HisPo";
		} else {
		    classname = classname + "HisPo";
		}
		
		Object obj = CommonUtil.newInstance(classname);
		return obj;

	}

	public String noNull(String value) {
		return value == null ? "" : value;
	}
}
