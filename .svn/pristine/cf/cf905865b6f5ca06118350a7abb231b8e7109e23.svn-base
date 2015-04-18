package com.citsamex.app.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

public class MultiDataSource extends BasicDataSource {

	private static final String CONFIG_FILE = "/mpp_config.properties";
	private static Properties props = new Properties();

	static {
		try {
			props.load(MultiDataSource.class.getResourceAsStream(CONFIG_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		return props.getProperty(key);
	}

	public MultiDataSource(String name) {
		String driver = props.getProperty(name + "_DRIVER");
		String url = props.getProperty(name + "_URL");
		String username = props.getProperty(name + "_USERNAME");
		String password = props.getProperty(name + "_PASSWORD");

		this.setDriverClassName(driver);
		this.setUrl(url);
		this.setUsername(username);
		this.setPassword(password);
	}

}
