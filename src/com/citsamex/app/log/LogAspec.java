package com.citsamex.app.log;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;

public class LogAspec {

	public void afterThrowing(JoinPoint jPoint, Throwable throwable) {
		Log log = LogFactory.getLog(jPoint.getSignature().getDeclaringType());
		log.error("exception occurs in " + jPoint.getSignature().getName(), throwable);
		throwable.printStackTrace();
	}

	public void before(JoinPoint jPoint) {
		Log log = LogFactory.getLog(jPoint.getSignature().getDeclaringType());

		String username = (String) ServletActionContext.getRequest().getSession().getAttribute("passport");
		StringBuffer paras = new StringBuffer();

		for (Object o : jPoint.getArgs()) {
			if (o != null) {
				if (o instanceof Object[]) {
					paras.append(Arrays.toString((Object[]) o));
				} else
					paras.append(o.toString());
			} else
				paras.append("null");
		}

		log.info("##User: " + username + " Call " + jPoint.getSignature().getDeclaringTypeName() + "."
				+ jPoint.getSignature().getName() + " parameters: " + paras.toString());

	}
}
