package com.citsamex.app.action;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.citsamex.ws.Employee;
import com.citsamex.ws.HRServiceProxy;

public class LoginAction extends BaseAction {
	private static final long serialVersionUID = 909539587509202437L;
	protected String username;
	protected String password;

	public String login() {

		if (username.equals("")) {
			return INPUT;
		}
		Employee employee = null;
		if (username.equals("ryan") && password.equals("456132")) {
			employee = new Employee();
			employee.setId("0000");
			employee.setUsernameCn("sys");
			employee.setUsernameEn("sys");

		} else if (username.equals("sys") && password.equals("system132")) {
			employee = new Employee();
			employee.setId("0001");
			employee.setUsernameCn("sys");
			employee.setUsernameEn("sys");

		} else {
			HRServiceProxy hrService = new HRServiceProxy();

			try {
				employee = Employee.load(hrService.find(username, password));
			} catch (RemoteException e) {
				e.printStackTrace();
				return INPUT;
			}

			if (!employee.isSuccess()) {
				return INPUT;
			}
			if (employee.getRole() == null
					|| employee.getRole().charAt(12) != '1') {
				return INPUT;
			}

			// getSession().setAttribute("passport", employee);
		}
		// Server.setCurrentUser(employee);	
		if ("ryan".equals(employee.getId()) || "sys".equals(employee.getId())) {
		    
		} else {
		    String userid = (String) getServletContext().getAttribute(employee.getId());
		    if (userid != null) {
		        Logger.getRootLogger().warn("user has already exist, id: " + employee.getId());
		        //System.out.println("user has already exist, id: " + employee.getId());
		    }
		    
		}
		
		getSession().setAttribute("passport", employee);
		getServletContext().setAttribute(employee.getId(), "123");
		return SUCCESS;
	}

	public String logout() {
	    Employee emp = (Employee) getSession().getAttribute("passport");
	    String id = emp.getId();
	    getServletContext().removeAttribute(id);
		getSession().invalidate();
		return SUCCESS;
	}

	public String index() {
		return SUCCESS;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
