package com.citsamex.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.citsamex.app.util.ServiceConfig;

public class LoadBarJob {

	private static int isRunning = 0;

	private ICompanyService service;

	private Connection connection = null;
	
	public LoadBarJob() {
		connection = getNewConnection();
	}
	
	public ICompanyService getService() {
		return service;
	}

	public void setService(ICompanyService service) {
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

		while (hour == 0 || hour == 1 || hour == 2 || hour == 3 || hour >= 18) {
			return;
		}

//		if (isRunning == 1) {
//			return;
//		} else {
//			isRunning = 1;
//		}

//		Connection connection = null;
		Statement statement = null;
		Statement statement2 = null;
		ResultSet rs = null;

//		String url = ServiceConfig.getProperty("MPP_URL");
//		String username = ServiceConfig.getProperty("MPP_USERNAME");
//		String password = ServiceConfig.getProperty("MPP_PASSWORD");

		Log log = LogFactory.getLog(this.getClass());

		try {
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			connection = DriverManager.getConnection(url, username, password);

			// optimize get connection
			if(connection == null || connection.isClosed()){
				connection = getNewConnection();
			}
			
			statement = connection.createStatement();
			statement2 = connection.createStatement();

			rs = statement.executeQuery("SELECT TOP (10) ID, BARNO, COMPANY, STATUS FROM T_JOB_LOAD_BAR "
					+ " WHERE STATUS = 0 AND LOCATE = 'SHA' ORDER BY ID ASC");

			while (rs.next()) {
				String barno = rs.getString("BARNO");
				String company = rs.getString("COMPANY");
				System.out.println("load bar: " + barno);
				log.info("load bar: " + barno);

				service.loadBar(1, company, barno);
				statement2.executeUpdate("UPDATE T_JOB_LOAD_BAR SET STATUS = 1 WHERE BARNO = '" + barno + "'");
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
