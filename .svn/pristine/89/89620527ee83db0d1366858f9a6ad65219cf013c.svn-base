package com.citsamex.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.citsamex.app.util.ServiceConfig;
import com.citsamex.service.tony.AbstractCondition;
import com.citsamex.service.tony.CNameCond;
import com.citsamex.service.tony.CustNoCond;
import com.citsamex.service.tony.ENameCond;
import com.citsamex.service.tony.EmailCond;
import com.citsamex.service.tony.Md5PasswordEncoder;
import com.citsamex.service.tony.MobileCond;
import com.citsamex.service.tony.Tony;

public class Services implements IServices {
	Md5PasswordEncoder encoder = new Md5PasswordEncoder();
	private Connection connection = null;
	protected Map<String, Object> pool;

	public Map<String, Object> getPool() {
		return pool;
	}

	public void setPool(Map<String, Object> pool) {
		this.pool = pool;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object call(String service, String[] props) {
		Object retVal = new ServiceMessage();

		if (service.indexOf(".") == -1)
			return retVal;

		String servicename = service.substring(0, service.indexOf("."));
		String methodname = service.substring(service.indexOf(".") + 1);

		Object object = pool.get(servicename);

		Method[] methods = object.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			Class[] classes = methods[i].getParameterTypes();

			if (methods[i].getName().equals(methodname) && classes.length == props.length) {
				try {
					retVal = methods[i].invoke(object, convert(props, classes));
					break;
				} catch (Exception e) {
					Logger.getRootLogger().error("Service.call()",e);
				}
			}
		}

		return retVal;
	}

	@SuppressWarnings("rawtypes")
	private Object[] convert(String[] props, Class[] classes) {
		Object[] objects = new Object[classes.length];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = convert(props[i], classes[i]);
		}
		return objects;
	}

	@SuppressWarnings("rawtypes")
	private Object convert(String s, Class claz) {
		if (claz.getName().equals("java.lang.Integer"))
			return Integer.parseInt(s);
		else if (claz.getName().equals("java.lang.Long"))
			return Long.parseLong(s);
		else if (claz.getName().equals("java.lang.Short"))
			return Short.parseShort(s);
		else if (claz.getName().equals("java.lang.Float"))
			return Float.parseFloat(s);
		else if (claz.getName().equals("java.lang.Double"))
			return Double.parseDouble(s);
		else if (claz.getName().equals("java.lang.Boolean"))
			return Boolean.parseBoolean(s);
		// else if (claz.getName().equals("java.lang.Byte"))
		// else if (claz.getName().equals("java.lang.Character"))
		else
			return s;
	}

	@Override
	public List<Tony> queryTony(String tony) {
		List<Tony> list = new ArrayList<Tony>();
		String sql = "SELECT TOP 10 * FROM PROD_USERMASTER WHERE 1=1 ";
		String[] tonys = tony.split(" ");

		AbstractCondition cond = new EmailCond().setNext(new CustNoCond().setNext(new MobileCond()
				.setNext(new CNameCond().setNext(new ENameCond()))));

		for (int i = 0; i < tonys.length; i++) {
			String string = cond.visit(tonys[i]);
			if (!string.equals("")) {
				sql += " AND " + string;
			}
		}

		String url = ServiceConfig.getProperty("AXO_PRD_URL");
		String username = ServiceConfig.getProperty("AXO_PRD_USERNAME");
		String password = ServiceConfig.getProperty("AXO_PRD_PASSWORD");

		Statement stmt = null;

		CallableStatement proc = null;

		ResultSet rs1 = null;
		ResultSet rs2 = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			if (connection == null)
				connection = DriverManager.getConnection(url, username, password);

			stmt = connection.createStatement();
			proc = connection.prepareCall("{ call SETLOGNAME(?) }");
			rs1 = stmt.executeQuery(sql);

			while (rs1.next()) {
				proc.setString(1, rs1.getString("USER_ID"));
				proc.execute();
			}
			rs2 = stmt.executeQuery(sql);
			while (rs2.next())
				list.add(createTony(rs2));

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
			if (proc != null) {
				try {
					proc.close();
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
			if (rs1 != null) {
				try {
					rs1.close();
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
			if (rs2 != null) {
				try {
					rs2.close();
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
		}

		return list;
	}

	private Tony createTony(ResultSet rs) {
		Tony tony = new Tony();
		Field[] fields = Tony.class.getFields();
		for (int i = 0; i < fields.length; i++) {
			String name = fields[i].getName();

			try {
				String value = rs.getString(name);
				if (name.equals("USER_PASSWORD")) {
					value = encoder.decrypt(value);
				}
				fields[i].set(tony, value);
			} catch (IllegalArgumentException e) {
				System.out.println(e);
			} catch (IllegalAccessException e) {
				System.out.println(e);
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return tony;
	}
}
