package com.citsamex.app.tag;

import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class LinkWriter extends WebControlWriter {

	@Override
	public String createObject(Map<String, Object> props) {
		String onclick = (String) props.get("onclick");
		String value = (String) props.get("value");
		String params = (String) props.get("params");
		Object element = props.get("element");

		if (params != null) {
			String[] parameters = params.split(",");
			params = "";
			for (String parameter : parameters) {
				params += parameter + "=";
				try {
					params += PropertyUtils.getProperty(element, parameter);
				} catch (Exception e) {
					e.printStackTrace();
				}
				params += "&";
			}

		}
		String obj = "<a href=\"" + onclick + "?" + params + "\">" + value + "</a>";
		return obj;
	}
}
