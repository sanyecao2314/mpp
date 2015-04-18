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

import com.citsamex.app.util.ServiceConfig;

public class PreSyncParJob {

	private static int isRunning = 0;

	private ITravelerService service;

	private static Set processedIdSet = new HashSet<String> ();
	
	private Connection connection = null;
	
	public PreSyncParJob() {
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
		if ( hour >= 8 && hour <= 18) {
		    String keepgoing = ServiceConfig.getProperty("keepgoing");
		    if (keepgoing != null && keepgoing.equalsIgnoreCase("true")) {
		        
		    } else {
	            return;
		    }

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
					.executeQuery("SELECT TOP 10 ID, CUSTNO, SYSTEMS FROM T_JOB_PRESYNC_PAR WHERE STATUS = 0 ORDER BY PRIORITY DESC");

			while (rs.next()) {
				String id = rs.getString("ID");
				String custno = rs.getString("CUSTNO");
				String systems = rs.getString("SYSTEMS");
				if (systems == null || systems.equals("")) {
					systems = "PS,BS,AXO";
				}
				if (processedIdSet.contains(id)) {
					Logger.getRootLogger().error("pre sync par job, id recorded:" + id);
					continue;
				}
				if (custno == null || "".equals(custno)) {
					processedIdSet.add(id);
					continue;
					
				}
				service.synchronize(custno, 0, 1, systems,"10");
				statement2
						.executeUpdate("UPDATE T_JOB_PRESYNC_PAR SET STATUS = 1 WHERE ID = "
								+ id);
				processedIdSet.add(id);
			}

		} catch (Exception ex) {

			Logger.getRootLogger().error("PreSyncParJob job failure", ex);

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
			isRunning = 0;
		}

		
	}

}
