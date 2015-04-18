package com.citsamex.app.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.citsamex.service.ServiceMessage;
import com.citsamex.ws.Employee;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = 5981519871944856163L;

	protected int tabSelected;
	protected String message;
	protected Page page;
	protected final Log log;

	protected Map<String, Object> map = new HashMap<String, Object>();

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getTabSelected() {
		return tabSelected;
	}

	public void setTabSelected(int tabSelected) {
		this.tabSelected = tabSelected;
	}

	public Page getPage() {
		return page;
	}

	public BaseAction() {
		log = LogFactory.getLog(getClass());
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public HttpSession getSession() {
		return getRequest().getSession();
	}

	public ServletContext getServletContext() {
		return getSession().getServletContext();
	}

	protected String getCurrentUsername() {
		String username = (String) getSession().getAttribute("username");
		return username == null ? "anonymous" : username;
	}

	protected boolean isEmpty(String a) {
		if (a == null || a.isEmpty())
			return true;
		return false;
	}

	protected boolean procServMessage(ServiceMessage sm) {
		this.message = sm.getServiceMessage();
		Employee user = (Employee) getSession().getAttribute("username");
		//Server.setCurrentUser(user);
		return sm.getCode().endsWith("00");
	}

	protected void addMessage(String field, String message) {
		this.getRequest().setAttribute(field + "_message", message);
	}

}
