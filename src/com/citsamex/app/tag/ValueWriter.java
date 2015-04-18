package com.citsamex.app.tag;

import java.util.Map;

public class ValueWriter extends WebControlWriter {

	@Override
	public String createObject(Map<String, Object> props) {
		String obj = (String) props.get("value");
		return obj;

	}

}
