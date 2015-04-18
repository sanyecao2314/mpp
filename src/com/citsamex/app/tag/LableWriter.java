package com.citsamex.app.tag;

import java.util.Map;

public class LableWriter extends WebControlWriter {

	@Override
	public String createObject(Map<String, Object> props) {
		String obj = "<label>";
		obj += props.get("value");
		obj += "</label>";
		return obj;

	}

}
