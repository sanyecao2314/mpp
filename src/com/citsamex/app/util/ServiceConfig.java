package com.citsamex.app.util;

import java.io.IOException;
import java.util.Properties;

public class ServiceConfig {

	private static final String CONFIG_FILE = "/mpp_config.properties";
	private static Properties props = new Properties();

	static {
		try {
			props.load(ServiceConfig.class.getResourceAsStream(CONFIG_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		return props.getProperty(key);
	}
}
