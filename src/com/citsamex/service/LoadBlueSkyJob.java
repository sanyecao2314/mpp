package com.citsamex.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.citsamex.ws.BsService;
import com.citsamex.ws.BsServiceProxy;

//load tid cid from bluesky to mpp.
public class LoadBlueSkyJob {

	private static int isRunning = 0;

	public void start() {

		// Calendar c = Calendar.getInstance();

		// int hour = c.get(Calendar.HOUR_OF_DAY);

		// while (hour == 0 || hour == 1 || hour == 2 || hour == 3 || hour >=
		// 18) {
		// return;
		// }

		if (isRunning == 1) {
			return;
		} else {
			isRunning = 1;
		}

		Connection connection = null;
		Statement statement = null;
		Statement statement2 = null;
		ResultSet rs = null;

		String url = "jdbc:sqlserver://10.181.57.61:1433;databaseName=MPP";
		String username = "mpp";
		String password = "mpp";

		// Log log = LogFactory.getLog(this.getClass());

		BsService bs = new BsServiceProxy();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(url, username, password);

			statement = connection.createStatement();
			statement2 = connection.createStatement();
			//
			// rs = statement
			// .executeQuery("SELECT TOP (2000) TRAVELER_NO FROM T_TRAVELER WHERE BS_ID IS NULL AND LEN(TRAVELER_NO) = 10");

			rs = statement.executeQuery("select top 10 BARNO from T_JOB_LOAD_BAR where status='0' ORDER BY BARNO");

			while (rs.next()) {

				String travelerNo = rs.getString("BARNO");

				try {
					bs.loadTraveler(travelerNo);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				// String message = bs.loadTraveler(travelerNo);

				/*
				 * if (!"".equals(message)) { String company =
				 * travelerNo.substring(0, 6);
				 * System.out.println("load company: " + company); message =
				 * bs.loadTraveler(company); if (!"".equals(message)) {
				 * statement2.executeUpdate(
				 * "UPDATE T_TRAVELER SET BS_ID='' WHERE TRAVELER_NO LIKE '" +
				 * company + "%'"); } break; }
				 */
				statement2.executeUpdate("UPDATE T_JOB_LOAD_BAR SET status='1' where barno='" + travelerNo + "'");
			}
			sync2CentralProfile();
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

	public void sync2CentralProfile() throws Exception {
	}
}
