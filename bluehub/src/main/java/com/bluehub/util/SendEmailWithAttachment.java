package com.bluehub.util;

import java.io.File;
import java.io.IOException;
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

import com.bluehub.bean.common.UserDetails;



public class SendEmailWithAttachment {

	private static Logger logger = Logger.getLogger(SendEmailWithAttachment.class);

	/**
	 * sends mail with training certificate as attachment
	 * @param mailList 
	 * @param userEmail as String
	 * @param subject2 as String
	 * @param string as String
	 */
	@SuppressWarnings("unused")
	public void sendCertificate(@SuppressWarnings("rawtypes") HashMap mailList,
			String userEmail, String subject2, String string,String cerftificateFileName) {
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
		/*properties.put("mail.smtp.auth",
				Constants.getMailPropertyValue("smtp.auth"));*/
		
		Session session = Session.getInstance(properties, null);
		String to = (String) mailList.get("to");
		final String user = Constants.getMailPropertyValue("email");
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(to));
			message.setSubject(subject2);

			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText(string);

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			String saveDirectory = Constants
					.getPropertyValue(Constants.FILE_UPLOAD_PATH);
			saveDirectory = saveDirectory + cerftificateFileName;
			//logger.info("sendCertificate saveDirectory==>"+saveDirectory);
			DataSource source = new FileDataSource(saveDirectory);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(Constants.CERTIFICATE_FILE);

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
		
	public void sendPatientShareMailWithAttachment(String userEmail, String subject, 
			String bodyContent,Integer folderName,String filenameArr) {
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
		/*properties.put("mail.smtp.auth",
				Constants.getMailPropertyValue("smtp.auth"));*/
		
		Session session = Session.getInstance(properties, null);
		String to = userEmail;
		final String user = Constants.getMailPropertyValue("email");
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(to));
			message.setSubject(subject);

			BodyPart messageBodyPart1 = new MimeBodyPart();
			//messageBodyPart1.setHeader("Content-Type", "text/html");
			messageBodyPart1.setContent(bodyContent, "text/html");
			

			String saveDirectory = Constants
					.getPropertyValue(Constants.FAREFILE_UPLOAD_PATH);
			String pathFolder = folderName.toString();
			saveDirectory = saveDirectory + pathFolder;
			//logger.info("sendCertificate saveDirectory==>"+saveDirectory);
			//DataSource source = new FileDataSource(saveDirectory);
			//messageBodyPart2.setDataHandler(new DataHandler(source));
			//messageBodyPart2.setFileName(fileName);

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			/*MimeBodyPart messageBodyPart3 = new MimeBodyPart();
			MimeBodyPart messageBodyPart4 = new MimeBodyPart();
			MimeBodyPart messageBodyPart5 = new MimeBodyPart();
			MimeBodyPart messageBodyPart6 = new MimeBodyPart();*/
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			
			int count = 0;
			//StringTokenizer st = new StringTokenizer(filenameArr);
			String[] stArr = filenameArr.split(",");
			
			int len = stArr.length;
			
			for(int i=0;i<len;i++){
				messageBodyPart2 = new MimeBodyPart();
				try{					
					File att = new File(new File(saveDirectory), stArr[i]);
					messageBodyPart2.attachFile(att);
					multipart.addBodyPart(messageBodyPart2);
					//String messagebody = "messageBodyPart" + (i+2);
					/*if(i == 0){
						logger.info("value i " + i);
						messageBodyPart2.attachFile(att);
						multipart.addBodyPart(messageBodyPart2);
					}
					if(i == 1){
						logger.info("value i " + i);
						messageBodyPart3.attachFile(att);
						multipart.addBodyPart(messageBodyPart3);
					}
					if(i == 2){
						logger.info("value i " + i);
						messageBodyPart4.attachFile(att);
						multipart.addBodyPart(messageBodyPart4);
					}
					if(i == 3){
						logger.info("value i " + i);
						messageBodyPart5.attachFile(att);
						multipart.addBodyPart(messageBodyPart5);
					}					
					if(i == 4){
						logger.info("value i " + i);
						messageBodyPart6.attachFile(att);
						multipart.addBodyPart(messageBodyPart6);
					}*/					
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}
						
/*			File att = new File(new File(saveDirectory), "physician all visits.txt");
			File att2 = new File(new File(saveDirectory), "visit detail fields order.txt");
			try{							
				messageBodyPart2.attachFile(att);
				messageBodyPart3.attachFile(att2);
			}catch(IOException ex){
				ex.printStackTrace();
			}*/

/*			for(int i=0;i<2;i++){
				File att = new File(new File(saveDirectory), "physician all visits.txt");
				File att2 = new File(new File(saveDirectory), "visit detail fields order.txt");
				try{			
					messageBodyPart2.attachFile(att);
					messageBodyPart2.attachFile(att2);
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}*/
			
/*			int len = fileName.length;
			for(int i = 0; i<=len; i++){
				File att = new File(new File(saveDirectory), fileName[i]);
				try{
					messageBodyPart2.attachFile(att);
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}*/
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
	
	public void sendPatientShareMail(String userEmail, String subject, 
			String bodyContent,String serverUrl,Integer patientId,String shareId,UserDetails userVo) {
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
		/*properties.put("mail.smtp.auth",
				Constants.getMailPropertyValue("smtp.auth"));*/
		
		Session session = Session.getInstance(properties, null);
		String to = userEmail;
		final String user = Constants.getMailPropertyValue("email");
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(to));
			message.setSubject(subject);

			serverUrl=serverUrl+"/physician/viewShareRequest.do?shareId=" + shareId + "";
			
			String ss = "Dear "+userVo.getUsername()+" I have Shared My Clinical Documents. Click the link to see.";
			
			String activateLink=ss + "  <a href='"+serverUrl+"'>Click here to View the Shared Documents.</a>";
			
			BodyPart messageBodyPart1 = new MimeBodyPart();
			//messageBodyPart1.setHeader("Content-Type", "text/html");
			messageBodyPart1.setContent(activateLink, "text/html");
						
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			
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
