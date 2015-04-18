package com.citsamex.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import bsh.util.Util;

import com.citsamex.app.util.CommonUtil;
import com.citsamex.app.util.ServiceConfig;

public class ChangeBarJob {

	private static int isRunning = 0;

	private Connection connection = null;
	
	private ITravelerService service;

	private static Set<String> changedParnoSet = new HashSet<String> ();
	
	public ChangeBarJob() {
		connection = getNewConnection();
	}
	
	public ITravelerService getService() {
		return service;
	}

	public void setService(ITravelerService service) {
		this.service = service;
	}
	
	private Connection getNewConnection() {
		try {
			String url = ServiceConfig.getProperty("MPP_URL");
			String username = ServiceConfig.getProperty("MPP_USERNAME");
			String password = ServiceConfig.getProperty("MPP_PASSWORD");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void start() {

		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		while (hour == 0 || hour == 1 || hour == 2 || hour == 3 || hour >= 19) {
			return;
		}

		if (isRunning == 1) {
			return;
		} else {
			isRunning = 1;
		}

//		Connection connection = null;
		Statement statement = null;
		Statement statement2 = null;
		ResultSet rs = null;

//		String url = ServiceConfig.getProperty("MPP_URL");
//		String username = ServiceConfig.getProperty("MPP_USERNAME");
//		String password = ServiceConfig.getProperty("MPP_PASSWORD");

		try {
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			connection = DriverManager.getConnection(url, username, password);

			// optimize get connection
			if (connection != null && connection.isClosed()) {
				connection = getNewConnection();
			}			
			statement = connection.createStatement();
			statement2 = connection.createStatement();

			rs = statement
					.executeQuery("SELECT TOP 1 ID, OLD_PARNO, NEW_BAR, TMR_NO, INACTIVE_OLD, STATUSREMARK FROM T_JOB_CHANGE_BAR WHERE STATUS = 0");

			while (rs.next()) {
				String id = rs.getString("ID");
				String oldParno = rs.getString("OLD_PARNO");
				String newBar = rs.getString("NEW_BAR");
				String tmrPar = rs.getString("TMR_NO");
				int inactive = rs.getInt("INACTIVE_OLD");
				String remark = rs.getString("STATUSREMARK");

				if (CommonUtil.isEmpty(oldParno) || CommonUtil.isEmpty(newBar)) {
					continue;
				}

				if (changedParnoSet.contains(id)) {
					Logger.getRootLogger().error("change bar job, id recorded:" + id);
					continue;
				}
				if (tmrPar == null)
					tmrPar = "";
				if (remark == null)
					remark = "";
				statement2.executeUpdate("UPDATE T_JOB_CHANGE_BAR SET STATUS = 2 WHERE ID = " + id);
				service.changeBar(oldParno, null, newBar,tmrPar, inactive, remark);
				statement2.executeUpdate("UPDATE T_JOB_CHANGE_BAR SET STATUS = 1 WHERE ID = " + id);
				changedParnoSet.add(id);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (statement2 != null)
					statement2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		isRunning = 0;
	}

}
