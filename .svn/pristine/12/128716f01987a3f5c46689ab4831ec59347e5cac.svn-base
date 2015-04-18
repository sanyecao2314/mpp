package com.citsamex.service;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.citsamex.app.util.ServiceConfig;
import com.citsamex.ws.ServMessage;


public class MailJob {

	private static int isRunning = 0;
	
	private Connection connection = null;
	
	public MailJob() {
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
		if (hour >= 20 || hour <= 7) {
			return;
		}

		if (isRunning == 1) {
			return;
		} else {
			isRunning = 1;
		}
		Logger.getRootLogger().error("ready to send a mail...");
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
			if(connection == null || connection.isClosed()){
				connection = getNewConnection();
			}				
			connection.setAutoCommit(false);

			statement2 = connection.createStatement();

			rs = statement2.executeQuery("SELECT TOP 1000 * FROM T_SYNC_MESSAGE " 
					+ "WHERE MAIL_FLAG IS NULL AND FINISHED=1 " 
					+ "ORDER BY ID DESC");

//			rs = statement2.executeQuery("SELECT TOP 100 * FROM T_SYNC_MESSAGE " +
//					"WHERE bs_success=0 and len(traveler_no) >6 and bs_updatedatetime >'2012-08-01 00:48:52.060'");

//			try {
//                Thread.sleep(90000L);
//            } catch (Exception e1) {
//                Thread.sleep(90000L);
//            }
			
			StringBuffer buffer = new StringBuffer();

			String custnos = "";
			String custnos2 = "";
			StringBuffer idSyncFail = new StringBuffer();
			StringBuffer idSyncSucc = new StringBuffer();
			List<String> usedList = new ArrayList<String>();
			List<Integer> unusedIdList = new ArrayList<Integer>();
			
			while (rs.next()) {
				String custno = rs.getString("TRAVELER_NO");

				int psFlag = rs.getInt("PS_SUCCESS");
				int bsFlag = rs.getInt("BS_SUCCESS");
				int axoFlag = rs.getInt("AXO_SUCCESS");
				int id = rs.getInt("ID");
				
				

				// String psMsg = rs.getString("PS_MSG");
				String bsMsg = rs.getString("BS_MESSAGE");
				String axoMsg = rs.getString("AXO_MESSAGE");

				String x = axoMsg == null ? "" : axoMsg;
				String bs = bsMsg == null ? "" : bsMsg;
				String axo = "";
				ServMessage message = null;

				// 如果一个traveler 有多笔同步信息，只需要发送最新的同步信息
				if (usedList.contains(custno)) {
				    unusedIdList.add(id);
				} else {
				    usedList.add(custno);
				}
				
				if (bsMsg != null && !"".equals(bsMsg)) {
					InputStream is = new ByteArrayInputStream(bsMsg.getBytes());
					XMLDecoder decoder = new XMLDecoder(is);
					try {
						message = (ServMessage) decoder.readObject();
						bs = message.getBody();
					} catch (Exception e) {
				        int start = bsMsg.indexOf("<string>");
				        int end = bsMsg.indexOf("</string>");
				        String ret = "";
				        if (start != -1 && end != -1) {
				            ret = bsMsg.substring(start + 8, end);
				        }
						bs = ret;
						Logger.getRootLogger().error("bsMsg parse failure!!!" + custno, e);
					}
				}
				
				if (axoMsg != null && !"".equals(axoMsg)) {
					InputStream is = new ByteArrayInputStream(axoMsg.getBytes());
					XMLDecoder decoder = new XMLDecoder(is);
					try {
						message = (ServMessage) decoder.readObject();
						axo = message.getBody();
					} catch (Exception e) {
						axo = "parse xml data error!!!";
						e.printStackTrace();
					}
				}				
				
				// if ((psFlag == 1 && bsFlag == 1)
				if ((psFlag == 1 && bsFlag == 1)
						&& (axoFlag == 1 || (axoFlag == 0 && x
								.indexOf("Configuration not found in AXO") != -1))) {
					custnos2 += "'" + custno + "',";
					idSyncSucc.append(id + ",");
				} else {
					custnos += "'" + custno + "',";
					idSyncFail.append(id + ",");
					
					buffer.append("<tr>");
					buffer.append("<td width='100'>" + custno + "</td>");
					buffer.append("<td>");
					if (psFlag == 1) {
						buffer.append("<div style='background-color:red;width:20px;height:20px'>X</div>");
					} else {
						buffer.append("<div style='background-color:#ccc';width:20px;height:20px></div>");
					}
					buffer.append("</td>");
					buffer.append("<td>&nbsp;</td>");
	
					buffer.append("<td>");
					if (bsFlag == 1) {
						buffer.append("<div style='background-color:blue;width:20px;height:20px'>X</div>");
					} else {
						buffer.append("<div style='background-color:#ccc';width:20px;height:20px></div>");
					}
					buffer.append("</td>");
	
					buffer.append("<td>&nbsp;" + bs + "</td>");
	
					buffer.append("<td>");
					if (axoFlag == 1 ) {
						buffer.append("<div style='background-color:green;width:20px;height:20px'>X</div>");
					} else if (x.indexOf("Configuration not found in AXO") != -1){
						buffer.append("<div style='background-color:green;width:20px;height:20px'>&nbsp;</div>");
					} else {
						buffer.append("<div style='background-color:#ccc';width:20px;height:20px></div>");
					}
					buffer.append("<td>&nbsp;" + axo + "</td>");
					buffer.append("</tr>");

				}
			}

			if (!custnos.equals("")) {

				String body = buffer.toString();
				buffer = new StringBuffer();
				buffer.append("<table border='1'>");
				buffer.append("<tr>");
				buffer.append("<td>CUST NO</td>");
				buffer.append("<td>PS</td>");
				buffer.append("<td>MESSAGE</td>");
				buffer.append("<td>BS</td>");
				buffer.append("<td>MESSAGE</td>");
				buffer.append("<td>AXO</td>");
				buffer.append("<td>MESSAGE</td>");
				buffer.append("</tr>");
				buffer.append(body);
				buffer.append("</table>");
				Logger.getRootLogger().error(buffer.toString());
				Sendmail.getInstance().send("Failed synchronization list", buffer.toString());

				idSyncFail.append("-1");
				String sql = "UPDATE T_SYNC_MESSAGE SET MAIL_FLAG = 1 WHERE ID IN("
						+ idSyncFail.toString() + ")";
				statement = connection.createStatement();
				statement.executeUpdate(sql);
			}

			if (!custnos2.equals("")) {
			    idSyncSucc.append("-1");
				String sql = "UPDATE T_SYNC_MESSAGE SET MAIL_FLAG = 0 WHERE ID IN ("
						+ idSyncSucc.toString() + ")";
				statement = connection.createStatement();
				statement.executeUpdate(sql);
			}
			
			if (unusedIdList.size() > 0) {
			    StringBuffer unusedStr = new StringBuffer();
			    for (Integer i : unusedIdList) {
			        unusedStr.append(i);
			        unusedStr.append(",");
			    }
			    unusedStr.append("-1");
	            String sql = "UPDATE T_SYNC_MESSAGE SET MAIL_FLAG = 2 WHERE ID IN ("
	                        + unusedStr.toString() + ")";
	            statement = connection.createStatement();
	            statement.executeUpdate(sql);
			}

			connection.commit();

		} catch (Exception ex) {
			Logger.getRootLogger().error("error code: mj001",ex);
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
	
	
	
	public static void main (String [] args) {
		//new MailJob().start();
	}

}
