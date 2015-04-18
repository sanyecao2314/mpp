package com.citsamex.app.tag;

import java.util.Map;

public class RadioWriter extends WebControlWriter {
	@Override
	public String createObject(Map<String, Object> props) {
		String obj = "<input type=\"radio\" ";
		String name = (String) props.get("name");
		String value = (String) props.get("value");

		obj += "name=\"" + name + "\" ";
		obj += "value=\"" + value + "\" ";
		obj += "  />";
		return obj;
	}

	@Override
	protected String createHeaderObject(Map<String, Object> props) {
		return "";
	}
}
