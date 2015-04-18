package com.citsamex.app.tag;

import java.util.Iterator;
import java.util.Map;

public class TextWriter extends WebControlWriter {

	@Override
	public String createObject(Map<String, Object> props) {

		String obj = "<input type=\"text\" ";

		Iterator<String> it = props.keySet().iterator();

		while (it.hasNext()) {
			String key = it.next();
			if (key.equals("params")) {
				obj += props.get(key);
				continue;
			}
			String value = (String) props.get(key);
			if (value == null) {
				continue;
			}
			obj += key + "=\"" + value + "\" ";
		}
		obj += " />";
		return obj;
	}
}
