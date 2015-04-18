package com.citsamex.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.citsamex.ws.Employee;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		// if (Server.isLock()) {
		// response.sendRedirect(request.getContextPath()
		// +
		// "/login.jsp?message=System is locked, please wait for a few minutes.");
		// }

		if (request.getRequestURL().indexOf(".action") == -1 && request.getRequestURL().indexOf(".jsp") == -1) {
			// non-jsp, non-action request.
			chain.doFilter(req, res);
		} else if (request.getRequestURL().indexOf("login.") != -1) {
			chain.doFilter(req, res);
		} else {
			Employee employee = (Employee) request.getSession().getAttribute("passport");

			// Server.setCurrentUser(employee);
			// else {
			// if (Server.getCurrentUser() == null) {
			// response.sendRedirect(request.getContextPath() + "/login.jsp");
			// }
			// chain.doFilter(req, res);
			// }
			try {
				if (employee == null) {
					response.sendRedirect(request.getContextPath() + "/login.jsp");
				} else {
					chain.doFilter(req, res);
				}
			} catch (Exception ex) {
				Logger.getRootLogger().warn("login filter",ex);
				//ex.printStackTrace();
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
