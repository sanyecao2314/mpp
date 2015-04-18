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
/**
 * from now, this job didn't use
 * @author ryan.wang
 *
 */
public class SyncSystemsJob {

	private static int isRunning = 0;

	private ITravelerService service;

	public ITravelerService getService() {
		return service;
	}

	public void setService(ITravelerService service) {
		this.service = service;
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

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		String url = ServiceConfig.getProperty("MPP_URL");
		String username = ServiceConfig.getProperty("MPP_USERNAME");
		String password = ServiceConfig.getProperty("MPP_PASSWORD");

		Log log = LogFactory.getLog(this.getClass());

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(url, username, password);

			statement = connection.createStatement();

			rs = statement
					.executeQuery("select TOP 50 TRAVELER_NO from T_TRAVELER where DIRTY=0 and LEN(TRAVELER_NO)=10");

			while (rs.next()) {
				final String travelerNo = rs.getString("TRAVELER_NO");
				service.synchronize(travelerNo, 0, 1, "PS,BS,AXO");

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
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		isRunning = 0;
	}
}
