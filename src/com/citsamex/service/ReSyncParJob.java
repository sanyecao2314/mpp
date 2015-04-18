package com.citsamex.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import com.citsamex.app.Server;
import com.citsamex.app.util.ServiceConfig;

public class ReSyncParJob {

	private static int isRunning = 0;
	private Connection connection = null;

	public ReSyncParJob() {
		connection = getNewConnection();
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
		while (hour == 7) {
			return;
		}

//		if (isRunning == 1) {
//			return;
//		} else {
//			isRunning = 1;
//		}

//		Connection connection = null;
		Statement statement = null;

//		String url = ServiceConfig.getProperty("MPP_URL");
//		String username = ServiceConfig.getProperty("MPP_USERNAME");
//		String password = ServiceConfig.getProperty("MPP_PASSWORD");

		synchronized (Server.lock) {
			try {
//				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//				connection = DriverManager.getConnection(url, username, password);
				// connection.setAutoCommit(false);

				// optimize get connection
				if(connection == null || connection.isClosed()){
					connection = getNewConnection();
				}
				statement = connection.createStatement();

				statement
						.executeUpdate("INSERT INTO T_MAIL_BUFFER(CUSTNO,PS_FLG,PS_MSG,BS_FLG,BS_MSG,AXO_FLG,AXO_MSG,SENT)"
								+ " SELECT TRAVELER_NO,PS_SUCCESS,PS_MESSAGE, BS_SUCCESS,BS_MESSAGE,AXO_SUCCESS,AXO_MESSAGE,0"
								+ " FROM T_SYNC_MESSAGE M WHERE TRAVELER_NO IN (SELECT CUSTNO FROM T_JOB_PRESYNC_PAR GROUP BY CUSTNO HAVING COUNT(1) > 5 )");

				statement
						.executeUpdate("DELETE FROM T_SYNC_MESSAGE WHERE TRAVELER_NO IN (SELECT CUSTNO FROM T_MAIL_BUFFER WHERE SENT=0)");

				statement
						.executeUpdate("DELETE FROM T_JOB_PRESYNC_PAR WHERE CUSTNO IN(SELECT CUSTNO FROM T_MAIL_BUFFER WHERE SENT=0)");

				String sql = "DELETE FROM T_SYNC_MESSAGE WHERE PS_SUCCESS=1 AND BS_SUCCESS=1 AND (AXO_SUCCESS=1 OR AXO_MESSAGE like '%Configuration not found in AXO.')";
				statement.executeUpdate(sql);

				sql = "DELETE FROM T_SYNC_MESSAGE WHERE PS_SUCCESS=1 AND LEN(TRAVELER_NO)>10";
				statement.executeUpdate(sql);

				sql = "INSERT INTO T_JOB_PRESYNC_PAR (CUSTNO,STATUS,PRIORITY)"
						+ " SELECT TRAVELER_NO,0,10000 FROM T_SYNC_MESSAGE "
						+ " WHERE (PS_SUCCESS=0 OR BS_SUCCESS=0 OR (AXO_SUCCESS=0 AND AXO_MESSAGE not like '%Configuration not found in AXO.'))"
						+ " AND EXISTS (SELECT TOP 1 * FROM T_JOB_SYNC_BPAR WHERE CUSTNO=TRAVELER_NO"
						+ " AND DATEDIFF(MINUTE,SYNCDATETIME,GETDATE())>30 AND STATUS=1 ORDER BY ID DESC)";
				statement.executeUpdate(sql);

				sql = "DELETE FROM T_SYNC_MESSAGE "
						+ " WHERE (PS_SUCCESS=0 OR BS_SUCCESS=0 OR (AXO_SUCCESS=0 AND AXO_MESSAGE not like '%Configuration not found in AXO.'))"
						+ " AND EXISTS (SELECT TOP 1 * FROM T_JOB_SYNC_BPAR WHERE CUSTNO=TRAVELER_NO"
						+ " AND DATEDIFF(MINUTE,SYNCDATETIME,GETDATE())>30 AND STATUS=1 ORDER BY ID DESC)";
				statement.executeUpdate(sql);

			} catch (Exception ex) {
				ex.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} finally {

				try {
					if (statement != null)
						statement.close();
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
		}

		isRunning = 0;
	}
}
