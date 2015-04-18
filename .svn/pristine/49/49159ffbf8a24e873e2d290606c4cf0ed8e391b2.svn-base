package com.citsamex.app.tag;

import java.util.Map;

public class ShortLableWriter extends WebControlWriter {

	@Override
	public String createObject(Map<String, Object> props) {
		String obj = "<label>";
		String value = (String) props.get("value");
		String maxLengthStr = (String) props.get("maxLength");

		int maxLength = 0;
		if (maxLengthStr != null && !maxLengthStr.equals("")) {
			maxLength = Integer.parseInt(maxLengthStr);
		}

		if (value.length() > maxLength) {
			value = value.substring(0, maxLength) + "...";
		}
		obj += value;
		obj += "</label>";
		return obj;
	}
}
