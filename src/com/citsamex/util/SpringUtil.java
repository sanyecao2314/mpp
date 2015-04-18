package com.citsamex.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil {
	private static ApplicationContext ac = new ClassPathXmlApplicationContext(
			"/metadata/applicationContext-*.xml");

	public static Object getBean(String mappedName) {
		return ac.getBean(mappedName);
	}
}
