package com.bluehub.util;

import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class SendEmailWithInvoice {

	private static Logger logger = Logger.getLogger(SendEmailWithInvoice.class);

	/**
	 * sends mail with invoice as attachment  
	 * @param mailList
	 * @param userEmail as String
	 * @param subject2 as String
	 * @param string as String
	 * @param pdfFileName as String
	 */
	@SuppressWarnings("unused")
	public void send(@SuppressWarnings("rawtypes") HashMap mailList,
			String userEmail, String subject2, String string, String pdfFileName) {
		
		MailOption mailOption = new MailOption();
		mailOption.setMailusername(Constants
				.getMailPropertyValue("mailusername"));
		String mailusername = mailOption.getMailusername();
		
		String userName=Constants.getMailPropertyValue("email");
		String passWord=Constants.getMailPropertyValue("password");
		
		Properties properties = System.getProperties();
		properties.put("mail.smtp.socketfactory.port",
				Constants.getMailPropertyValue("socketfactory.port"));
		properties.put("mail.smtp.host",
				Constants.getMailPropertyValue("smtp.host"));
		properties.put("mail.smtp.port",
				Constants.getMailPropertyValue("smtp.port"));
		
		Session session = Session.getInstance(properties, null);
		String to = (String) mailList.get("to");
		String cc = (String) mailList.get("cc");
		final String user = Constants.getMailPropertyValue("email");
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(to));
			message.addRecipient(Message.RecipientType.CC,
					new InternetAddress(cc));
			message.setSubject(subject2);

			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText(string);

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			String fileName = pdfFileName;
			String saveDirectory = Constants.getPlatformProperyValue(Constants.FILE_UPLOAD_PATH);
			saveDirectory = saveDirectory + fileName;
			DataSource source = new FileDataSource(saveDirectory);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(fileName);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);
			message.setContent(multipart);
			
			message.saveChanges();
	        Transport transport = session.getTransport("smtp");
	        if (StringUtils.isNotBlank(mailusername) && mailusername.equals("blank")) {
	        	 transport.connect(Constants.getMailPropertyValue("smtp.host"), 
		            		userName, passWord);
	        }else{
	        	 transport.connect(Constants.getMailPropertyValue("smtp.host"), 
		            		null, null);
	        }            	           
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
}
