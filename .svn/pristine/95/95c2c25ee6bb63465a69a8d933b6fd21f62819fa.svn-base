package com.citsamex.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.citsamex.service.SyncTravThread;
import com.citsamex.ws.Employee;

public class Server {

	public static Object lock = new Object();

	// public static final ThreadLocal<Employee> userpool = new
	// ThreadLocal<Employee>();

	public static final Map<Integer, Integer> taskpool = new HashMap<Integer, Integer>();
	public static final ConcurrentHashMap<String, SyncTravThread> syncPool = new ConcurrentHashMap<String, SyncTravThread>();

	private static int taskid = 0;

	public synchronized static int addTask() {

		taskpool.put(++taskid, 0);
		return taskid;
	}

	public static void removeTask(Integer taskid) {
		taskpool.remove(taskid);
	}

	public static Integer getTaskStatus(Integer taskid) {
		return taskpool.get(taskid);
	}

	public static void setTaskStatus(Integer taskid, Integer status) {
		taskpool.put(taskid, status);
	}

	static Properties props = new Properties();
	public static int PROPS_PAGESIZE = 30;
	static {

		try {
			props.load(Server.class.getClassLoader().getResourceAsStream(
					"sm.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Server() {

	}

	public static String getCurrentUsername() {
		HttpSession session = null;
		Employee employee = null;
		String id = "";
		try {
			if (org.directwebremoting.WebContextFactory.get() != null) {
				session = org.directwebremoting.WebContextFactory.get()
						.getHttpServletRequest().getSession();
				if (session != null) {
					employee = (Employee) session.getAttribute("passport");
				}
			}

			if (employee == null) {
				session = ServletActionContext.getRequest().getSession();
				employee = (Employee) session.getAttribute("passport");
			}

			if (employee == null) {
				return "UNKNOWN";
			}
			id = employee.getId();
		} catch (Exception ex) {
			return "UNKNOWN";
		}
		return id;
	}

	public static String getCurrentUser() {
		HttpSession session = null;
		if (org.directwebremoting.WebContextFactory.get() != null) {
			session = org.directwebremoting.WebContextFactory.get()
					.getHttpServletRequest().getSession();
		} else {
			session = ServletActionContext.getRequest().getSession();
		}
		Employee employee = (Employee) session.getAttribute("passport");
		if (employee == null) {
			return null;
		}

		return employee.getId() + "/" + employee.getUsernameCn();
	}

	// public static void setCurrentUser(Employee employee) {
	// userpool.set(employee);
	// }

	public static String getMessage(String id) {
		String message = props.getProperty(id);
		return message == null ? id : message;
	}

}
