package com.citsamex.app.tag;

import java.util.Map;

public class HiddenWriter extends WebControlWriter {

	@Override
	public String createObject(Map<String, Object> props) {
		String obj = "<input type=\"hidden\" ";
		String name = (String) props.get("name");
		String value = (String) props.get("value");

		obj += "name=\"" + name + "\" ";
		obj += "value=\"" + value + "\" ";
		obj += " \" />&nbsp;";
		return obj;
	}

	@Override
	protected String createHeaderObject(Map<String, Object> props) {
		return "";
	}
}
