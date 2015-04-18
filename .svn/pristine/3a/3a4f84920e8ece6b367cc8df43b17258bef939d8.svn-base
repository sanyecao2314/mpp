package com.citsamex.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;

import com.citsamex.app.Server;

public class Logger {

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public void afterThrowing(JoinPoint jPoint, Throwable throwable) {
		Log log = LogFactory.getLog(jPoint.getSignature().getDeclaringType());
		log.error("exception occurs in " + jPoint.getSignature().getName(), throwable);
		throwable.printStackTrace();
	}

	public void before(JoinPoint jPoint) {

		Log log = LogFactory.getLog(jPoint.getSignature().getDeclaringType());

		String username = Server.getCurrentUsername();
		log.info("#### " + format.format(new Date()));
		log.info("#### User " + username + " call " + jPoint.getSignature().getDeclaringTypeName() + "."
				+ jPoint.getSignature().getName());
		Object[] objs = jPoint.getArgs();
		String parameters = "";
		for (int i = 0; i < objs.length; i++) {
			parameters += objs[i];
			if (i < objs.length - 1) {
				parameters += ",";
			}
		}
		log.info("parameters: " + parameters);
	}
}
