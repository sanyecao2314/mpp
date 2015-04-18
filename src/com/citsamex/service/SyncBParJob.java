package com.citsamex.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.citsamex.app.util.ServiceConfig;


public class SyncBParJob {

	private static int isRunning = 0;
	private Connection connection = null;
	
	public static Object obj = new Object();
	
	// record id which write to xml file 
	public static Set<String> syncIdSet = new HashSet<String>();
	
	public SyncBParJob(){
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
    
	public  void start() {

		String dirStr = ServiceConfig.getProperty("ESB_PIPENAME");
		File dir = new File(dirStr);
		int length = dir.list().length;
		if (length > 3) {
			return;
		}

		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		if (hour == 0 
		        || hour == 1 
		        || hour == 2 
		        || hour == 3 
		        || hour == 19 
		        || hour >= 23) {
		    if (ServiceConfig.getProperty("keepgoing").equals("true")) {
		        
		    } else {
		          return;
		    }

		}

		if (isRunning == 1) {
			return;
		} else {
			isRunning = 1;
		}
		
		Statement statement = null;
		Statement statement2 = null;
		ResultSet rs = null;
		try {
			if(connection == null || connection.isClosed()){
				connection = getNewConnection();
			}
			statement = connection.createStatement();
			statement2 = connection.createStatement();
			Set<String> travSet = new HashSet<String>();
			synchronized (obj) {
				rs = statement.executeQuery("SELECT TOP 3 ID, CUSTNO, BPAR_XML FROM T_JOB_SYNC_BPAR WHERE   STATUS = 0 ORDER BY PRIORITY DESC, ID ASC");			
				while (rs.next()) {
					String id = rs.getString("ID");
					String custno = rs.getString("CUSTNO");
					String xml = rs.getString("BPAR_XML");
					
					//同一个traveler 在很短的时间内更新了多次，需要分开
					if (travSet.contains(custno)) {
					    continue;
					} else {
					    travSet.add(custno);
					}
					
					if (syncIdSet.contains(id)) {
						Logger.getRootLogger().error("syncbparjob, id recorded:" + id);
						continue;
					}
					write(custno, xml);
					// write2(custno, xml);
					statement2
							.executeUpdate("UPDATE T_JOB_SYNC_BPAR SET STATUS = 1,SYNCDATETIME=GETDATE() WHERE ID = "
									+ id);
					syncIdSet.add(id);
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

	private void write(String custno, String xml) {
		FileOutputStream fos = null;
		xml = custno + xml;
		try {
			String pipe = ServiceConfig.getProperty("ESB_PIPENAME");
			long now = new Date().getTime();
			String filename = custno + "_" + now + ".xml";
			fos = new FileOutputStream(pipe + "/" + filename);
			fos.write(xml.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void write2(String custno, String xml) {
		FileOutputStream fos = null;
		xml = custno + xml;
		try {
			String pipe = ServiceConfig.getProperty("ESB_PIPENAME2");
			long now = new Date().getTime();
			String filename = custno + "_" + now + "0.xml";
			fos = new FileOutputStream(pipe + "/" + filename);
			fos.write(xml.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
