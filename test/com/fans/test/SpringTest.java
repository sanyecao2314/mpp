package com.fans.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.citsamex.service.PPPImpServiceJob;

public class SpringTest {
	
	private static ApplicationContext ac = new ClassPathXmlApplicationContext(
			"/metadata/applicationContext-*.xml");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		ac.getBean("travelerService");
		PPPImpServiceJob pppImpServiceJob =	(PPPImpServiceJob) ac.getBean("pppImpServiceJob");
		pppImpServiceJob.start();
	}

}
