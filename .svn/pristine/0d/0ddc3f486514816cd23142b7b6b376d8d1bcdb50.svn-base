package com.citsamex.app.tag;

import java.util.Map;

public class CheckboxWriter extends WebControlWriter {

	@Override
	protected String createHeaderObject(Map<String, Object> props) {
		String obj = "<input type=\"checkbox\" ";
		String name = (String) props.get("name");
		obj += "name=\"checkall_" + name + "\" ";
		obj += "onclick=\"checkall(this,'" + name + "')\"";
		obj += " />";
		return obj;
	}

	@Override
	public String createObject(Map<String, Object> props) {
		String obj = "<input type=\"checkbox\" ";
		String name = (String) props.get("name");
		String value = (String) props.get("value");
		String onclick = (String) props.get("onclick");
		obj += "name=\"" + name + "\" ";
		obj += "value=\"" + value + "\" ";
		obj += "onclick=\"checkone(this)";
		if (onclick != null && !onclick.trim().equals("")) {
			obj += ";" + onclick;
		}

		obj += " \" />";
		return obj;
	}
}
