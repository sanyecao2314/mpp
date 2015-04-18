package com.citsamex.app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.citsamex.ws.BsPar;
import com.citsamex.ws.BsService;
import com.citsamex.ws.BsServiceProxy;

public class Test {

	/**
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {

		System.out.println("loading...");

		BsService service = new BsServiceProxy();
		FileInputStream fis = new FileInputStream("C:\\bar_list.txt");
		BufferedReader dr = new BufferedReader(new InputStreamReader(fis));

		FileOutputStream fos = new FileOutputStream("c:\\update.sql");

		String url = "jdbc:sqlserver://10.181.11.18:1433;databaseName=CENTER_PROFILE";
		String username = "sa";
		String password = "shadev696";

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection connection = DriverManager.getConnection(url, username, password);
		Statement statement = connection.createStatement();

		Date d1 = new Date();
		System.out.println(d1);

		String line = null;
		while ((line = dr.readLine()) != null) {
			System.out.println(line);

			Object[] list = null;

			try {
				//list = service.listTraveler(line);
			} catch (Exception ex) {
				System.out.println("bar not found or error: " + line);
				continue;
			}

			if (list == null) {
				list = new Object[0];
			}

			if (list.length != 0) {
				String sql = "delete from dbo.Pretrip_GDSID where PARNO like '" + line + "%';";

				statement.executeUpdate(sql);

				fos.write(sql.getBytes());

				for (int i = 0; i < list.length; i++) {
					BsPar bsPar = new BsPar();

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder;

					builder = factory.newDocumentBuilder();

					if (list[i] == null) {
						System.out.println(line);
						continue;
					}

					InputSource is = new InputSource(new StringReader(list[i].toString()));
					Document doc = builder.parse(is);

					XPathFactory xpathFactory = XPathFactory.newInstance();

					XPathExpression expr = xpathFactory.newXPath().compile("/Traveler/TID");
					Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
					String value = node.getTextContent();
					bsPar.setTid(value);

					expr = xpathFactory.newXPath().compile("/Traveler/PARNO");
					node = (Node) expr.evaluate(doc, XPathConstants.NODE);
					value = node.getTextContent();
					bsPar.setParno(value);

					expr = xpathFactory.newXPath().compile("/Traveler/CNNAME");
					node = (Node) expr.evaluate(doc, XPathConstants.NODE);
					value = node.getTextContent();
					bsPar.setCnname(value);

					expr = xpathFactory.newXPath().compile("/Traveler/HOSTNAME");
					node = (Node) expr.evaluate(doc, XPathConstants.NODE);
					value = node.getTextContent();
					bsPar.setHostname(value);

					expr = xpathFactory.newXPath().compile("/Traveler/STATUS");
					node = (Node) expr.evaluate(doc, XPathConstants.NODE);
					value = node.getTextContent();
					bsPar.setStatus(value);

					sql = "INSERT INTO CENTER_PROFILE.dbo.Pretrip_GDSID (GDSID, PARNO, CNNAME, HOSTNAME)"
							+ " VALUES ('" + bsPar.getTid() + "', '" + bsPar.getParno() + "', '"
							+ bsPar.getCnname().replaceAll("'", "''") + "', '"
							+ bsPar.getHostname().replaceAll("'", "''") + "');";

					statement.executeUpdate(sql);

					fos.write(sql.getBytes());

				}

			}

		}
		fis.close();
		fos.close();

		statement.close();
		connection.close();

		Date d2 = new Date();
		System.out.println(d2);
		System.out.println((d2.getTime() - d1.getTime()) / 1000);

	}
}
