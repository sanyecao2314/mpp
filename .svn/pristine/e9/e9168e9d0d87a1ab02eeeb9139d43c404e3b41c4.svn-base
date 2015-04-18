package com.citsamex.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.citsamex.app.util.ServiceConfig;

public class Sendmail {

	private static Sendmail sendmail = new Sendmail();

	private Sendmail() {
	}

	public static Sendmail getInstance() {
		return sendmail;
	}

	/**
	 * send a mail 
	 * @param subject
	 * @param body
	 */
	public void send(String subject, String body) {

		// String ip = ServiceConfig.getProperty("MAIL.SERVER.IP");
		String smtp = ServiceConfig.getProperty("MAIL.SMTP.HOST");
		String account = ServiceConfig.getProperty("MAIL.SERVER.ACCOUNT");
		String password = ServiceConfig.getProperty("MAIL.SERVER.PASSWORD");
		String from = ServiceConfig.getProperty("MAIL.FROM");
		String to = ServiceConfig.getProperty("MAIL.TO");
		try {
			Properties props = new Properties();

			Session session = Session.getInstance(props, null);

			session.setDebug(false);
			Transport transport = session.getTransport("smtp");
			transport.connect(smtp, account, password);

			MimeMessage newMessage = new MimeMessage(session);
			newMessage.setFrom(new InternetAddress(from));
			newMessage.setRecipients(Message.RecipientType.TO, to);

			newMessage.setSubject(subject);
			newMessage.setSentDate(new java.util.Date());
			newMessage.setContent(body, "text/html;charset=GBK");

			newMessage.saveChanges();
			transport.sendMessage(newMessage, newMessage.getAllRecipients());
			transport.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String args[]) {

		String subject = "123";// args[0];
		String body = "321";// args[1];
		String from = "aeadmin@citsamex.com.cn";// args[2];
		String to = "david.qian@citsamex.com";// args[3];
		String cc = "";// args[4];
		String bcc = "";// args[5];
		String attach = "";// args[6];
		String mbid = "";// args[7];
		try {

			Properties props = new Properties();
			props.load(new FileInputStream(Sendmail.class.getResource("").getPath() + "mail.properties"));

			String smtphost = (String) props.get("mail.smtp.host");
			String account = (String) props.get("mail.server.account");
			String password = (String) props.get("mail.server.password");

			Session session = Session.getInstance(props, null);

			session.setDebug(false);
			Transport transport = session.getTransport("smtp");
			transport.connect(smtphost, account, password);

			MimeMessage newMessage = new MimeMessage(session);
			newMessage.setFrom(new InternetAddress(from));
			newMessage.setRecipients(Message.RecipientType.TO, to);
			if (cc != null && !cc.equals(""))
				newMessage.setRecipients(Message.RecipientType.CC, cc);
			if (bcc != null && !bcc.equals(""))
				newMessage.setRecipients(Message.RecipientType.BCC, bcc);
			newMessage.setSubject(subject);
			newMessage.setSentDate(new java.util.Date());
			newMessage.setContent(body, "text/html;charset=GBK");

			if (attach != null && !attach.equals("")) {
				Multipart mp = new MimeMultipart();

				MimeBodyPart contentMbp = new MimeBodyPart();
				contentMbp.setContent(body, "text/html;charset=GBK");
				mp.addBodyPart(contentMbp);

				String arrayFile[] = attach.split(",");
				for (int i = 0; i < arrayFile.length; i++) {
					MimeBodyPart mbp = new MimeBodyPart();
					File file = new File(arrayFile[i]);
					FileDataSource fds = new FileDataSource(file);
					mbp.setDataHandler(new DataHandler(fds));
					mbp.setFileName(MimeUtility.encodeText(fds.getName(), "GBK", "B"));
					mp.addBodyPart(mbp);
				}

				newMessage.setContent(mp);
			}
			newMessage.saveChanges();
			transport.sendMessage(newMessage, newMessage.getAllRecipients());
			transport.close();
			System.out.print("Y" + mbid);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("S" + mbid);
		}

	}

	public static void usage() {
		System.out.println("1:subject 2:body 3:from 4:to 5:cc 6:bcc 7:attach");
	}
}
