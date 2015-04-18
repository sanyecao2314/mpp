package com.citsamex.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import com.citsamex.app.util.ServiceConfig;
import com.citsamex.ws.BsService;
import com.citsamex.ws.BsServiceProxy;

//sync to central profile.
public class SyncCentralProfileJob {

	private static int isRunning = 0;

	private Connection connection = null;
	private Connection connection1 = null;
	
	public SyncCentralProfileJob() {
		connection = getNewConnection();
		connection1 = getNewConnectionCP();
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
	private Connection getNewConnectionCP() {
		try {
			String url = ServiceConfig.getProperty("CENTRAL_PROFILE_URL");
			String username = ServiceConfig.getProperty("CENTRAL_PROFILE_USERNAME");
			String password = ServiceConfig.getProperty("CENTRAL_PROFILE_PASSWORD");
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
		while (hour != 3) {
			return;
		}
		
		if (isRunning == 1) {
			return;
		} else {
			isRunning = 1;
		}

//		Connection connection = null;
		Statement statement = null;
		Statement statement0 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
//		String url = ServiceConfig.getProperty("MPP_URL");
//		String username = ServiceConfig.getProperty("MPP_USERNAME");
//		String password = ServiceConfig.getProperty("MPP_PASSWORD");

//		Connection connection1 = null;
		Statement statement1 = null;

//		String url1 = ServiceConfig.getProperty("CENTRAL_PROFILE_URL");
//		String username1 = ServiceConfig.getProperty("CENTRAL_PROFILE_USERNAME");
//		String password1 = ServiceConfig.getProperty("CENTRAL_PROFILE_PASSWORD");

		// Log log = LogFactory.getLog(this.getClass());

		BsService bs = new BsServiceProxy();
		try {
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			connection = DriverManager.getConnection(url, username, password);
			
			// optimize get connection
			if(connection == null || connection.isClosed()){
				connection = getNewConnection();
			}				
			statement = connection.createStatement();
			statement0 = connection.createStatement();

//			connection1 = DriverManager.getConnection(url1, username1, password1);
			
			// optimize get connection
			if(connection1 == null || connection1.isClosed()){
				connection1 = getNewConnectionCP();
			}
			statement1 = connection1.createStatement();

			rs = statement
					.executeQuery("SELECT TOP (2000) TRAVELER_NO,BS_ID,BS_HOSTNAME,BS_TRAVELERNAME FROM T_TRAVELER "
							+ " WHERE BS_ID IS NOT NULL AND BS_SYNC = 0 AND LEN(TRAVELER_NO)=10");

			boolean hasRecord = false;
			while (rs.next()) {
				hasRecord = true;
				String travelerNo = rs.getString("TRAVELER_NO");
				String bsId = rs.getString("BS_ID");
				String bsHostname = rs.getString("BS_HOSTNAME");
				String bsTravelername = rs.getString("BS_TRAVELERNAME");

				String sql = "DELETE FROM PRETRIP_GDSID WHERE PARNO='" + travelerNo + "'";
				int i = statement1.executeUpdate(sql);

				sql = "INSERT INTO PRETRIP_GDSID(GDSID, PARNO, CNNAME, HOSTNAME) values('" + bsId + "', '" + travelerNo
						+ "', '" + bsTravelername + "', '" + bsHostname + "')";
				i = statement1.executeUpdate(sql);

				if (i == 1) {
					statement0.executeUpdate("UPDATE T_TRAVELER SET BS_SYNC=1 WHERE TRAVELER_NO='" + travelerNo + "'");
				}
			}

			if (!hasRecord) {
				rs1 = statement0
						.executeQuery("SELECT  TOP(100) TRAVELER_NO AS PARNO FROM T_TRAVELER "
								+ " WHERE BS_ID IS NULL AND BS_SYNC = 0 AND LEN(TRAVELER_NO)=10");
				while (rs1.next()) {
					String parNo = rs1.getString("PARNO");

					try {
						bs.loadTraveler(parNo);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
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
				if (rs1 != null)
					rs1.close();
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
				if (statement0 != null)
					statement0.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (statement1 != null)
					statement1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (connection1 != null)
					connection1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		isRunning = 0;
	}

	public void sync2CentralProfile() throws Exception {
		// String url =
		// "jdbc:sqlserver://10.181.11.18:1433;databaseName=CENTER_PROFILE";
		// String username = "sa";
		// String password = "shadev696";
		//
		// Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// Connection connection = DriverManager.getConnection(url, username,
		// password);
		// Statement statement = connection.createStatement();
		//
		// if (statement != null) {
		// statement.close();
		// }
		//
		// if (connection != null) {
		// connection.close();
		// }

	}
}
