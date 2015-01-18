package com.bluehub.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class MailSupport  {
	private static Logger logger = Logger.getLogger(MailSupport.class);
	static Properties properties = new Properties();

	static {
		System.out.println("akdkahksdhkahsdksajk");
		properties.put("mail.smtp.socketfactory.port",
				Constants.getMailPropertyValue("socketfactory.port"));
		properties.put("mail.smtp.host",
				Constants.getMailPropertyValue("smtp.host"));
		properties.put("mail.smtp.port",
				Constants.getMailPropertyValue("smtp.port"));
		properties.put("mail.smtp.auth",
				Constants.getMailPropertyValue("smtp.auth"));
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
	}

	/**
	 * mail configuration
	 * 
	 * @param mailOption
	 */
	public boolean send(final MailOption mailOption) {
		try {			
			String userName=Constants.getMailPropertyValue("email");
			String passWord=Constants.getMailPropertyValue("password");
			String mailusername=Constants.getMailPropertyValue("mailusername");

			Session session = Session.getInstance(properties, null);
			MimeMessage message = new MimeMessage(session);
			message.setSubject(mailOption.getSubject());
			message.setText(mailOption.getBody());
			message.setFrom(new InternetAddress(Constants.getMailPropertyValue("email")));
									
			for(int i=0;i<mailOption.getTo().length;i++){
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailOption.getTo()[i]));
            }
			if (mailOption.getCc() != null) {
	            for(int i=0;i<mailOption.getCc().length;i++){              
	            	message.addRecipient(Message.RecipientType.CC, new InternetAddress(mailOption.getCc()[i])); 
	            }   
			}
            if (mailOption.getBcc() != null) {
	            for(int i=0;i<mailOption.getBcc().length;i++){
	            	message.addRecipient(Message.RecipientType.BCC, new InternetAddress(mailOption.getBcc()[i]));
	            }
            }			
			message.setSubject(mailOption.getSubject());
			message.setText(mailOption.getBody());
			
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
						
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		return true;
	}

	/**
	 * mail configuration for multipart content
	 * 
	 * @param mailOption
	 */
	public boolean sendMultipartMail(final MailOption mailOption) {
		try {
			if (mailOption.isForceSystemConfiguredSender()) {
				mailOption.setFrom(Constants.getMailPropertyValue("email"));
				mailOption.setPassword(Constants
						.getMailPropertyValue("password"));
				mailOption.setMailusername(Constants
						.getMailPropertyValue("mailusername"));
			}
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
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			
			Session session = Session.getInstance(properties, null);
			
			try {
				MimeMessage message = new MimeMessage(session);
				
				message.setFrom(new InternetAddress(mailOption.getFrom()));
				
				message.setRecipients(Message.RecipientType.TO,
						bindAddress(mailOption.getTo()));
				if (mailOption.getCc() != null) {
					message.setRecipients(Message.RecipientType.CC,
							bindAddress(mailOption.getCc()));
				}
				if (mailOption.getBcc() != null) {
					message.setRecipients(Message.RecipientType.BCC,
							bindAddress(mailOption.getBcc()));
				}
				message.setSubject(mailOption.getSubject());
				message.setContent(mailOption.getMultipart());
				
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
			
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		return true;
	}

	public InternetAddress[] bindAddress(String[] addressAsString)
			throws AddressException {
		InternetAddress[] addresses = new InternetAddress[addressAsString.length];
		int count = 0;
		for (String address : addressAsString) {
			addresses[count] = InternetAddress.parse(address)[0];
			count++;
		}
		return addresses;
	}

	/**
	 * sends email without cc
	 * 
	 * @param userEmail
	 *            as string
	 * @param subject
	 *            as string
	 * @param bodyContent
	 *            as string
	 */
	public static void sendEmail(String userEmail, String subject,
			String bodyContent) {
		MailOption mailOption = new MailOption();
		mailOption.setForceSystemConfiguredSender(true);
		mailOption.setAuthenticate(true);
		mailOption.setTo(new String[] { userEmail });
		mailOption.setSubject(subject);
		mailOption.setBody(bodyContent);
		new MailSupport().send(mailOption);
	}

	/**
	 * sends email with one cc
	 * 
	 * @param toEmail
	 *            as string
	 * @param ccEmail
	 *            as string
	 * @param subject
	 *            as string
	 * @param bodyContent
	 *            as string
	 */
	public static void sendEmailWithCCForClinical(String toEmail,
			String ccEmail, String subject, String bodyContent) {
		//logger.info("toEmail==>"+toEmail);
		//logger.info("ccEmail==>"+ccEmail);
		MailOption mailOption = new MailOption();
		mailOption.setForceSystemConfiguredSender(true);
		mailOption.setAuthenticate(true);
		mailOption.setTo(new String[] { toEmail });
		mailOption.setCc(new String[] { ccEmail });
		mailOption.setSubject(subject);
		mailOption.setBody(bodyContent);
		new MailSupport().send(mailOption);
	}

	
	/**
	 * sends email with multipart content
	 * 
	 * @param toEmail
	 *            as string
	 * @param ccEmail
	 *            as string
	 * @param subject
	 *            as string
	 * @param bodyContent
	 *            as string
	 */
	public static void sendMultipartEmailWithCC(String toEmail, String ccEmail,
			String subject, Multipart bodyContent) {

		MailOption mailOption = new MailOption();
		mailOption.setForceSystemConfiguredSender(true);
		mailOption.setAuthenticate(true);
		mailOption.setTo(new String[] { toEmail });
		mailOption.setCc(new String[] { ccEmail });
		mailOption.setSubject(subject);
		mailOption.setMultipart(bodyContent);
		new MailSupport().sendMultipartMail(mailOption);
	}

	/**
	 * Sends email with new passcode
	 * 
	 * @param userEmail
	 *            as string
	 * @param passcode
	 *            as string
	 * @param name
	 *            as string
	 * @param userName
	 *            as string
	 * @param agencyEmail
	 *            as string
	 * @param validity
	 *            as integer
	 */

	public static void sendMailCeuPasscode(String userEmail, String passcode,
			String name, String userName, String agencyEmail, String validity) {
		logger.info("MailSupport sendMailCeuPasscode() start");
		StringBuffer bodyContent = new StringBuffer();
		String subject = Constants
				.getPropertyValue(Constants.CEU_TRAINING_SUBJECT);
		String content = Constants
				.getPropertyValue(Constants.CEU_TRAINING_CONTENT);
		String greeting = Constants
				.getPropertyValue(Constants.CEU_TRAINING_DEAR);
		String mailcontent = Constants
				.getPropertyValue(Constants.CEU_TRAINING_MAILCONTENT);
		String passcodetext = Constants
				.getPropertyValue(Constants.CEU_TRAINING_PASSCODE);
		String valid = Constants.getPropertyValue(Constants.CEU_TRAINING_VALID);
		String days = Constants.getPropertyValue(Constants.CEU_TRAINING_DAYS);
		String regards = Constants
				.getPropertyValue(Constants.CEU_TRAINING_BEST);
		
		String notes = Constants
				.getPropertyValue(Constants.subscription_payment_note);
		
		

		String subject4 = subject;
		bodyContent.append(greeting + " " + userName);
		bodyContent.append("\n" + content + " " + "\"" + name + "\"" + " "
				+ mailcontent);
		bodyContent.append("\n" + passcodetext + " " + passcode+"\n");
		bodyContent.append("\n" + valid + " " + validity + " " + days+"\n");
		//bodyContent.append("\n" + notes);
		bodyContent.append("\n");
		bodyContent.append("\n" + regards + "\n");
		bodyContent.append(agencyEmail + "\n");
		logger.debug("MailSupport sendMailCeuPasscode() userEmail=>"
				+ userEmail);
		sendSubscribeEmail(userEmail, subject4, bodyContent.toString(),
				agencyEmail);
	}
	
	
	public static void reSentMailCeuPasscode(String userEmail, String passcode,
			String name, String userName, String agencyEmail, String validity) {
		logger.info("MailSupport sendMailCeuPasscode() start");
		StringBuffer bodyContent = new StringBuffer();
		String subject = Constants
				.getPropertyValue(Constants.RE_CEU_TRAINING_SUBJECT);
		String content = Constants
				.getPropertyValue(Constants.RE_CEU_TRAINING_CONTENT);
		String greeting = Constants
				.getPropertyValue(Constants.RE_CEU_TRAINING_DEAR);
		String mailcontent = Constants
				.getPropertyValue(Constants.RE_CEU_TRAINING_MAILCONTENT);
		String passcodetext = Constants
				.getPropertyValue(Constants.RE_CEU_TRAINING_PASSCODE);
		String valid = Constants.getPropertyValue(Constants.RE_CEU_TRAINING_VALID);
		String days = Constants.getPropertyValue(Constants.RE_CEU_TRAINING_DAYS);
		String regards = Constants
				.getPropertyValue(Constants.RE_CEU_TRAINING_BEST);
		
		String notes = Constants
				.getPropertyValue(Constants.subscription_payment_note);
		
		

		String subject4 = subject;
		bodyContent.append(greeting + " " + userName);
		bodyContent.append("\n");
		bodyContent.append("\n" + content + " " + "\"" + name + "\"" + " "
				+ mailcontent);
		bodyContent.append("\n" + passcodetext + " " + passcode+"\n");
		bodyContent.append("\n" + valid + " " + validity + " " + days+"\n");
		//bodyContent.append("\n" + notes);
		bodyContent.append("\n");
		bodyContent.append("\n" + regards + "\n");
		bodyContent.append(agencyEmail + "\n");
		logger.debug("MailSupport sendMailCeuPasscode() userEmail=>"
				+ userEmail);
		sendSubscribeEmail(userEmail, subject4, bodyContent.toString(),
				agencyEmail);
	}

	public static void sendSubscribeEmail(String userEmail, String subject,
			String bodyContent, String agencyEmail) {
		logger.info("MailSupport sendSubscribeEmail() start=>");
		MailOption mailOption = new MailOption();
		mailOption.setForceSystemConfiguredSender(true);
		mailOption.setAuthenticate(true);
		mailOption.setTo(new String[] { userEmail });
		mailOption.setCc(new String[] { agencyEmail });
		mailOption.setSubject(subject);
		mailOption.setBody(bodyContent);
		new MailSupport().send(mailOption);
	}

	

	
	/**
	 * Sends email when a child registers in the site
	 * 
	 * @param userEmail
	 *            as string
	 * @param userName
	 *            as string
	 * @param adminEmail
	 *            as string
	 * @param adminName
	 *            as string
	 */

	public static void sendMailRegisteration(String userEmail, String userName,
			String adminEmail, String adminName) {
		StringBuffer bodyContent = new StringBuffer();
		String subject1 = Constants
				.getPropertyValue(Constants.USER_REGISTERATION_SUBJECT);
		String subject2 = Constants
				.getPropertyValue(Constants.USER_REGISTERATION_SUBJECT1);
		String greetings = Constants
				.getPropertyValue(Constants.USER_REGISTERATION_GREETINGS);
		String welcome = Constants
				.getPropertyValue(Constants.USER_REGISTERATION_WELCOME);
		String content = Constants
				.getPropertyValue(Constants.USER_REGISTERATION_CONTENT);
		String mailContent = Constants
				.getPropertyValue(Constants.USER_REGISTERATION_MAILCONTENT);
		String best = Constants
				.getPropertyValue(Constants.USER_REGISTERATION_BEST);
		
		String donotreply = Constants
				.getPropertyValue(Constants.donotreply);
		String bluehub = null;
		
		adminEmail= Constants.getPropertyValue(Constants.SUPPORT_MAIL_ID);

		String subject = subject1 + " " + userName + " " + subject2;
		bodyContent.append(greetings + "\n");
		bodyContent.append(welcome + " "+userName+ "." + "\n");
		bodyContent.append(content + "\n");
		bodyContent.append("\n");
		bodyContent.append(mailContent + "\n");
		bodyContent.append(adminEmail);
		bodyContent.append("\n");
		bodyContent.append("\n" + best + "\n");
		//bodyContent.append(adminName + "\n");
		bodyContent.append(bluehub + "\n");		
		bodyContent.append("\n");
		bodyContent.append("" + donotreply + "\n");
		
		sendEmailWithCCForClinical(userEmail, adminEmail, subject,
				bodyContent.toString());
	}
	
	
	public static void sendMailRegisterationBluehub(String userEmail, String userName,
			String adminEmail, String adminName,String serverUrl) {
		StringBuffer bodyContent = new StringBuffer();
		String subject1 = Constants
				.getPropertyValue(Constants.USER_REGISTERATION_SUBJECT);
		String subject2 = Constants
				.getPropertyValue(Constants.USER_REGISTERATION_SUBJECT1);
		String greetings = Constants
				.getPropertyValue(Constants.USER_REGISTERATION_GREETINGS);
		String welcome = Constants
				.getPropertyValue(Constants.USER_REGISTERATION_WELCOME);
		String content = Constants
				.getPropertyValue(Constants.USER_REGISTERATION_CONTENT);
//		String mailContent = Constants
//				.getPropertyValue(Constants.USER_REGISTERATION_MAILCONTENT);
		String best = Constants
				.getPropertyValue(Constants.USER_REGISTERATION_BEST);
		
		String donotreply = Constants
				.getPropertyValue(Constants.donotreply);
		String bluehub = null;
		
		adminEmail= Constants.getPropertyValue(Constants.SUPPORT_MAIL_ID);

		String click_linkhere = Constants
			    .getPropertyValue(Constants.acc_activate);
		
		String activateLink="<a href='"+serverUrl+"'>"+click_linkhere+"</a>";
		
		String subject = subject1 + " " + userName + " " + subject2;
		bodyContent.append(greetings + "\n");
		bodyContent.append(welcome + " "+userName+ "." + "\n");
		bodyContent.append(content + "\n");
		bodyContent.append("\n");
// 		bodyContent.append(activateLink + "\n");
		//bodyContent.append(adminEmail);
		bodyContent.append("\n");
		bodyContent.append("\n" + best + "\n");
		//bodyContent.append(adminName + "\n");
		//bodyContent.append(bluehub + "\n");		
		bodyContent.append("\n");
		bodyContent.append("" + donotreply + "\n");
		
		sendEmailWithCCForClinical(userEmail, adminEmail, subject,
				bodyContent.toString());
	}

	
	

	/**
	 * new password will be e-mailed to the user
	 * 
	 * @param userEmail
	 *            as string
	 * @param userName
	 *            as string
	 * @param userPassword
	 *            as string
	 */

	public static void sendForgotPasswordMail(String userEmail,
			String userName, String userPassword) {

		StringBuffer bodyContent = new StringBuffer();
		String subject1 = Constants
				.getPropertyValue(Constants.FORGOT_PASSWORD_SUBJECT);
		String dear = Constants
				.getPropertyValue(Constants.FORGOT_PASSWORD_DEAR);
		String content = Constants
				.getPropertyValue(Constants.FORGOT_PASSWORD_CONTENT);
		String mailContent = Constants
				.getPropertyValue(Constants.FORGOT_PASSWORD_MAILCONTENT);
		String best = Constants
				.getPropertyValue(Constants.FORGOT_PASSWORD_BEST);
		String adminEmail = Constants.getMailPropertyValue("adminemail");

		String subject = subject1;
		bodyContent.append(dear + " " + userName + "." + "\n");
		bodyContent.append("\n");
		bodyContent.append(content + userPassword + "\n");
		bodyContent.append(mailContent + "\n");
		bodyContent.append("\n");
		bodyContent.append(best + "\n");
		bodyContent.append(adminEmail + "\n");
		sendEmail(userEmail, subject, bodyContent.toString());
	}
	
	
	/*public static void sendPatientRequestMail(String userEmail,
			String userName) {
		
		logger.info("sendPatientRequestMail : ");

		StringBuffer bodyContent = new StringBuffer();
		String subject1 = Constants
				.getPropertyValue(Constants.PATIENT_REQUEST_SUBJECT);
		String dear = Constants
				.getPropertyValue(Constants.PATIENT_REQUEST_DEAR);
		String mailContent = Constants
				.getPropertyValue(Constants.PATIENT_REQUEST_CONTENT);
		String best = Constants
				.getPropertyValue(Constants.PATIENT_REQUEST_BEST);
		String adminEmail = Constants.getMailPropertyValue("adminemail");

		String subject = subject1;
		bodyContent.append(dear + " " + userName + "." + "\n");
		bodyContent.append("\n");
		//bodyContent.append("<a href=>""\n");
		bodyContent.append(mailContent + "\n");
		bodyContent.append("\n");
		bodyContent.append(best + "\n");
		bodyContent.append(adminEmail + "\n");
		sendEmail(userEmail, subject, bodyContent.toString());
	}*/
	
	
	
	public static void sendPatientRequestMail(String userEmail,
			String userName,String serverUrl) {
		
		logger.info("sendPatientRequestMail : ");
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        //System.out.println(dateFormat.format(date).toString());

		StringBuffer bodyContent = new StringBuffer();
		String subject1 = Constants
				.getPropertyValue(Constants.PATIENT_REQUEST_SUBJECT);
		String dear = Constants
				.getPropertyValue(Constants.PATIENT_REQUEST_DEAR);
		String mailContent = Constants
				.getPropertyValue(Constants.PATIENT_REQUEST_CONTENT);
		String best = Constants
				.getPropertyValue(Constants.PATIENT_REQUEST_BEST);
		String click_linkhere = Constants
			    .getPropertyValue(Constants.click_linkhere);
		String adminEmail = Constants.getMailPropertyValue("adminemail");
		
		String newheadingsubject=Constants
			    .getPropertyValue(Constants.newheadingsubject);
		
		String newheadingsubject1=Constants
			    .getPropertyValue(Constants.newheadingsubject1);
		
		String newsubject=Constants
			    .getPropertyValue(Constants.newsubject);
		
		String newsubjectt=Constants
			    .getPropertyValue(Constants.newsubjectt);

		String newsubject1=Constants
			    .getPropertyValue(Constants.newsubject1);
		
		String newsubject2=Constants
			    .getPropertyValue(Constants.newsubject2);
		
		String newsubject3=Constants
			    .getPropertyValue(Constants.newsubject3);
		
		String newsubject4=Constants
			    .getPropertyValue(Constants.newsubject4);
		
		
		String newsubject5=Constants
			    .getPropertyValue(Constants.newsubject5);
		
		String newsubject6=Constants
			    .getPropertyValue(Constants.newsubject6);
		
		String newsubject7=Constants
			    .getPropertyValue(Constants.newsubject7);
		
		String newsubject8=Constants
			    .getPropertyValue(Constants.newsubject8);
		
		String newsubject9=Constants
			    .getPropertyValue(Constants.newsubject9);
		
		String newsubject10=Constants
			    .getPropertyValue(Constants.newsubject10);
		
		String newsubject11=Constants
			    .getPropertyValue(Constants.newsubject11);
		
		String newsubject12=Constants
			    .getPropertyValue(Constants.newsubject12);
		
		String newsubject13=Constants
			    .getPropertyValue(Constants.newsubject13);
		
		String newsubject14=Constants
			    .getPropertyValue(Constants.newsubject14);
		
		String newsubject15=Constants
			    .getPropertyValue(Constants.newsubject15);
		
		String newsubject16=Constants
			    .getPropertyValue(Constants.newsubject16);
		
		String newsubject17=Constants
			    .getPropertyValue(Constants.newsubject17);
		
		String newsubject18=Constants
			    .getPropertyValue(Constants.newsubject18);
		
		String newsubject19=Constants
			    .getPropertyValue(Constants.newsubject19);
		
		String newsubject20=Constants
			    .getPropertyValue(Constants.newsubject20);
		
		String newsubject21=Constants
			    .getPropertyValue(Constants.newsubject21);
		
		String subjecturl=Constants
			    .getPropertyValue(Constants.subjecturl);
		String newsubject111=Constants
			    .getPropertyValue(Constants.newsubject111);
		String newsubjectfaxno=Constants
			    .getPropertyValue(Constants.newsubjectfaxno);
		
		String subject = subject1;
		
		serverUrl=serverUrl+"/login.do";
		
		String activateLink="<a href='"+serverUrl+"'>"+click_linkhere+"</a>";
		
		String subjecturllink="<a href='www.bluehubhealth.com/requested'>"+subjecturl+"</a>";
		
		StringBuilder tableContent = new StringBuilder();
		tableContent.append("<html><body>");
		
		tableContent.append("<table border='0' style='border:none;width:100%;'>");	
		
		/*tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + dear + " " + userName + ", " + "</td>");
		tableContent.append("</tr>");*/
		tableContent.append("<br>");
		/*tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + mailContent + "</td>");
		tableContent.append("</tr>");*/
		tableContent.append("<br>");
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + newheadingsubject + "</b></td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		/*tableContent.append("<br>");*/
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + newheadingsubject1 + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		/*tableContent.append("<br>");*/
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + newsubject + " " + userName + " "+newsubjectt+ "</td>");
		tableContent.append("</tr>");
		/*tableContent.append("<br>");*/
		/*tableContent.append("<br>");*/
		/*tableContent.append("<br>");*/
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + newsubject1 + " "+subjecturllink+" "+newsubject111+" <b>"+newsubjectfaxno+"</b></td>");
		tableContent.append("</tr>");
		/*tableContent.append("<br>");*/
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + newsubject2 + "</td>");
		tableContent.append("</tr>");
		/*tableContent.append("<br>");*/
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + newsubject3 + "</td>");
		tableContent.append("</tr>");
		/*tableContent.append("<br>");*/
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + newsubject4 + "</td>");
		tableContent.append("</tr>");
		/*tableContent.append("<br>");*/
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + newsubject5 + "</td>");
		tableContent.append("</tr>");
		/*tableContent.append("<br>");*/
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + newsubject6 + "</td>");
		tableContent.append("</tr>");
		/*tableContent.append("<br>");*/
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + newsubject7 + "</td>");
		tableContent.append("</tr>");
		/*tableContent.append("<br>");*/
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + newsubject8 + "</td>");
		tableContent.append("</tr>");
		/*tableContent.append("<br>");*/
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + newsubject9 + "</td>");
		tableContent.append("</tr>");
		/*tableContent.append("<br>");*/
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + newsubject10 + "</td>");
		tableContent.append("</tr>");
		/*tableContent.append("<br>");*/
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + newsubject11 + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + newsubject12 + "</td>");
		tableContent.append("</tr>");
		/*tableContent.append("<br>");*/
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + newsubject13 + "</td>");
		tableContent.append("</tr>");
		/*tableContent.append("<br>");*/
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + newsubject14 + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		/*tableContent.append("<br>");*/
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + newsubject15 + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		/*tableContent.append("<br>");*/
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + newsubject16 + "</td>");
		tableContent.append("</tr>");
		/*tableContent.append("<br>");*/
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + newsubject17 + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		/*tableContent.append("<br>");*/
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + newsubject18 + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		/*tableContent.append("<br>");*/
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + newsubject19 + "</td>");
		tableContent.append("</tr>");
		/*tableContent.append("<br>");*/
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + newsubject20 + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		/*tableContent.append("<br>");*/
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'><b>" + userName + " / " + dateFormat.format(date).toString() + ",</b></td>");
		tableContent.append("</tr>");
		/*tableContent.append("<br>");
		tableContent.append("<br>");*/
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + newsubject21 + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + activateLink + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + best + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		
	/*	tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + emailinfo + "</td>");
		tableContent.append("</tr>");
		*/
		tableContent.append("</table>");
		
		tableContent.append("</table></body></html>");
		
		String[] sendEmailIds = new String[1];;
		sendEmailIds[0] = userEmail;

		Multipart mp = new MimeMultipart();
		MimeBodyPart htmlPart = new MimeBodyPart();
		System.out.println("tableContent : "+tableContent.toString());
		try {
			htmlPart.setContent(tableContent.toString(), "text/html");
			mp.addBodyPart(htmlPart);
		} catch (MessagingException e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		sendMultipartEmailWithForCase(sendEmailIds, adminEmail, subject,
				mp);
		
	}

	

	public static void sendMultipartEmailWithForCase(String[] clinicalEmailIds,
			String ccEmail, String subject, Multipart bodyContent) {
		MailOption mailOption = new MailOption();
		mailOption.setForceSystemConfiguredSender(true);
		mailOption.setAuthenticate(true);
		mailOption.setTo(clinicalEmailIds);
		mailOption.setCc(new String[] { ccEmail });
		mailOption.setSubject(subject);
		mailOption.setMultipart(bodyContent);
		new MailSupport().sendMultipartMail(mailOption);
	}

	

	
	
	
	public static void sendShareRequestMail(String userEmail,String userName,
			Integer shareReqId,String role,String serverUrl,String name,String req) {
		StringBuffer bodyContent = new StringBuffer();
		String subject1 = null;
		String dear = null;
		String mailContent = null;
		String best = null;
		String adminEmail = null;

		String subject = null;

		if (role.equals("Patient")) {

			logger.info("sendPatientRequestMail : ");

			subject1 = Constants
					.getPropertyValue(Constants.SHARE_REQUEST_SUBJECT);
			dear = Constants.getPropertyValue(Constants.PATIENT_REQUEST_DEAR);
			mailContent = null;
			best = Constants.getPropertyValue(Constants.PATIENT_REQUEST_BEST);
			String click_linkhere = Constants
				    .getPropertyValue(Constants.click_linkhere);
			adminEmail = Constants.getMailPropertyValue("adminemail");

			subject = subject1;
			
			if(req.equals("YES")){
				mailContent = "Mr/mrs "+name +" Requesting Share Your Clinical Documents.";	
				
			}else{
				
				mailContent = "Mr/mrs "+name +" Requesting Your Digital Signature.";
			}
			
//			serverUrl=serverUrl+"/login.do";
			
			String activateLink="<a href='"+serverUrl+"'>"+click_linkhere+"</a>";
			
			StringBuilder tableContent = new StringBuilder();
			tableContent.append("<html><body>");
			
			tableContent.append("<table border='0' style='border:none;width:100%;'>");	
			
			tableContent.append("<tr>");
			tableContent.append("<td colspan='4'>" + dear + " " + userName + ", " + "</td>");
			tableContent.append("</tr>");
			tableContent.append("<br>");
			tableContent.append("<tr>");
			tableContent.append("<td colspan='4'>" + mailContent + "</td>");
			tableContent.append("</tr>");
			tableContent.append("<br>");
			tableContent.append("<tr>");
			tableContent.append("<td colspan='4'>" + activateLink + "</td>");
			tableContent.append("</tr>");
			tableContent.append("<br>");
			tableContent.append("<tr>");
			tableContent.append("<td colspan='4'>" + best + "</td>");
			tableContent.append("</tr>");
			tableContent.append("<br>");
			
		/*	tableContent.append("<tr>");
			tableContent.append("<td colspan='4'>" + emailinfo + "</td>");
			tableContent.append("</tr>");
			*/
			tableContent.append("</table>");
			
			tableContent.append("</table></body></html>");
			
			String[] sendEmailIds = new String[1];;
			sendEmailIds[0] = userEmail;

			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			
			try {
				htmlPart.setContent(tableContent.toString(), "text/html");
				mp.addBodyPart(htmlPart);
			} catch (MessagingException e) {
				logger.error(Constants.LOG_ERROR + e.getMessage());
			}
			sendMultipartEmailWithForCase(sendEmailIds, adminEmail, subject,
					mp);
			/*bodyContent.append(dear + " " + userName + "." + "\n");
			bodyContent.append("\n");
			// bodyContent.append(content + userPassword + "\n");
			bodyContent.append(mailContent + "\n");
			bodyContent.append("\n");
			bodyContent.append(best + "\n");
			bodyContent.append(adminEmail + "\n");*/
		
		} else {

		}

		//sendEmail(userEmail, subject, bodyContent.toString());
		
	}
	
	
	

	/**
	 * sends new password generated to the parent of child who has no valid
	 * email id
	 * 
	 * @param mailParams
	 */

	public static void sendForgotPasswordMailChild(
			Map<String, Object> mailParams) {
		StringBuffer bodyContent = new StringBuffer();
		String subject1 = Constants
				.getPropertyValue(Constants.CHILD_FORGOT_PASSWORD_SUBJECT);
		String dear = Constants
				.getPropertyValue(Constants.CHILD_FORGOT_PASSWORD_DEAR);
		String content = Constants
				.getPropertyValue(Constants.CHILD_FORGOT_PASSWORD_CONTENT);
		String mailContent = Constants
				.getPropertyValue(Constants.CHILD_FORGOT_PASSWORD_MAILCONTENT);
		String best = Constants
				.getPropertyValue(Constants.CHILD_FORGOT_PASSWORD_BEST);
		String adminEmail = (String) mailParams.get(Constants.ADMIN_EMAIL);
		String parentAFirstName = (String) mailParams
				.get(Constants.PARENTA_FIRSTNAME);
		String parentALastName = (String) mailParams
				.get(Constants.PARENTA_LASTNAME);
		String parentAEmail = (String) mailParams.get(Constants.PARENTA_EMAIL);
		String newPassword = (String) mailParams.get(Constants.NEW_PASSWORD);
		//String childEmail = (String) mailParams.get(Constants.USER_EMAIL);
		String childEmail = Constants.TEAM_MEMBER;

		String subject = subject1;
		bodyContent.append(dear + " " + parentAFirstName + " "
				+ parentALastName + "\n");
		bodyContent.append("\n");
		bodyContent.append(content + " " + childEmail + " is: " + newPassword
				+ "\n");
		bodyContent.append(mailContent + "\n");
		bodyContent.append("\n");
		bodyContent.append(best + "\n");
		bodyContent.append(adminEmail + "\n");
		sendEmail(parentAEmail, subject, bodyContent.toString());
	}
	
	
	
	public static void sendMultipartEmailWithForCustodian(String custodianMailId,
			String agencyEmailId, String subject, Multipart bodyContent) {
		MailOption mailOption = new MailOption();
		mailOption.setForceSystemConfiguredSender(true);
		mailOption.setAuthenticate(true);
		mailOption.setTo(new String[] { custodianMailId });
		mailOption.setCc(new String[] { agencyEmailId });
		mailOption.setSubject(subject);
		mailOption.setMultipart(bodyContent);
		new MailSupport().sendMultipartMail(mailOption);
	}

	
	
	public static void sendEmailToMultipleUsers(String[] userEmail, String subject,	String bodyContent) {
		MailOption mailOption = new MailOption();
		mailOption.setForceSystemConfiguredSender(true);
		mailOption.setAuthenticate(true);
		mailOption.setTo(userEmail);
		mailOption.setSubject(subject);
		mailOption.setBody(bodyContent);
		new MailSupport().send(mailOption);
	}
	
	
	public static void sendPatientRequestBehalfMail(String userEmail,
			String userName,String serverUrl,String requestFrom,String RequestTo) {
		
		logger.info("sendPatientRequestBehalfMail : ");

		StringBuffer bodyContent = new StringBuffer();
		String subject1 = Constants
				.getPropertyValue(Constants.SHARE_REQUEST_SUBJECT);
		String dear = Constants
				.getPropertyValue(Constants.PATIENT_REQUEST_DEAR);
		String mailContent = "";
		String best = Constants
				.getPropertyValue(Constants.PATIENT_REQUEST_BEST);
		String click_linkhere = Constants
			    .getPropertyValue(Constants.click_linkhere);
		String adminEmail = Constants.getMailPropertyValue("adminemail");

		String subject = subject1;
		
		mailContent = "Mr/Mrs "+requestFrom +" Requesting Your Clinical Documents To "+RequestTo+". ";
		
		//serverUrl=serverUrl+"/login.do";
		
		String activateLink="<a href='"+serverUrl+"'>"+click_linkhere+"</a>";
		
		StringBuilder tableContent = new StringBuilder();
		tableContent.append("<html><body>");
		
		tableContent.append("<table border='0' style='border:none;width:100%;'>");	
		
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + dear + " " + userName + ", " + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + mailContent + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		tableContent.append("<tr>");
//		tableContent.append("<td colspan='4'>" + activateLink + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + best + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		
	/*	tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + emailinfo + "</td>");
		tableContent.append("</tr>");
		*/
		tableContent.append("</table>");
		
		tableContent.append("</table></body></html>");
		
		String[] sendEmailIds = new String[1];;
		sendEmailIds[0] = userEmail;

		Multipart mp = new MimeMultipart();
		MimeBodyPart htmlPart = new MimeBodyPart();
		
		try {
			htmlPart.setContent(tableContent.toString(), "text/html");
			mp.addBodyPart(htmlPart);
		} catch (MessagingException e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		sendMultipartEmailWithForCase(sendEmailIds, adminEmail, subject,
				mp);
		
	}
	

	public static void sendPhysicianRequestBehalfMail(String userEmail,
			String userName,String serverUrl,String patient,String physician,String url) {
		
		logger.info("sendPatientRequestMail : ");

		StringBuffer bodyContent = new StringBuffer();
		String subject1 = Constants
				.getPropertyValue(Constants.SHARE_REQUEST_SUBJECT);
		String dear = Constants
				.getPropertyValue(Constants.PATIENT_REQUEST_DEAR);
		String mailContent = "";
		String best = Constants
				.getPropertyValue(Constants.PATIENT_REQUEST_BEST);
		String click_linkhere = Constants
			    .getPropertyValue(Constants.click_linkhere);
		String adminEmail = Constants.getMailPropertyValue("adminemail");

		mailContent = "Mr/Mrs "+physician+" Requesting You To Share The Clinical Documents Of "+patient+" .";
		String subject = subject1;
//		physician/uploadClinicalDocuments.do?requestId=1703936&patientId=491520&patientName=Dinu Kumar
		serverUrl=url;
		
		String activateLink="<a href='"+serverUrl+"'>"+click_linkhere+"</a>";
		
		StringBuilder tableContent = new StringBuilder();
		tableContent.append("<html><body>");
		
		tableContent.append("<table border='0' style='border:none;width:100%;'>");	
		
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + dear + " " + userName + ", " + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + mailContent + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + activateLink + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + best + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		
	/*	tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + emailinfo + "</td>");
		tableContent.append("</tr>");
		*/
		tableContent.append("</table>");
		
		tableContent.append("</table></body></html>");
		
		String[] sendEmailIds = new String[1];;
		sendEmailIds[0] = userEmail;

		Multipart mp = new MimeMultipart();
		MimeBodyPart htmlPart = new MimeBodyPart();
		
		try {
			htmlPart.setContent(tableContent.toString(), "text/html");
			mp.addBodyPart(htmlPart);
		} catch (MessagingException e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		sendMultipartEmailWithForCase(sendEmailIds, adminEmail, subject,
				mp);
		
	}

	public static void sendPhysicianApprovedBehalfMail(String userEmail,
			String userName,String requestfrom, String url,String patient) {
		logger.info("sendPatientRequestMail : ");

		StringBuffer bodyContent = new StringBuffer();
		String subject1 = Constants
				.getPropertyValue(Constants.SHARE_REQUEST_SUBJECT);
		String dear = Constants
				.getPropertyValue(Constants.PATIENT_REQUEST_DEAR);
		String mailContent = "";
		String best = Constants
				.getPropertyValue(Constants.PATIENT_REQUEST_BEST);
		String click_linkhere = Constants
			    .getPropertyValue(Constants.click_linkview);
		String adminEmail = Constants.getMailPropertyValue("adminemail");

		mailContent = "Mr/Mrs "+requestfrom+" Has Uploaded The Clinical Documents Of "+patient+" .";
		String subject = subject1;
//		physician/uploadClinicalDocuments.do?requestId=1703936&patientId=491520&patientName=Dinu Kumar
		String serverUrl=url;
		
		String activateLink="<a href='"+serverUrl+"'>"+click_linkhere+"</a>";
		
		StringBuilder tableContent = new StringBuilder();
		tableContent.append("<html><body>");
		
		tableContent.append("<table border='0' style='border:none;width:100%;'>");	
		
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + dear + " " + userName + ", " + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + mailContent + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + activateLink + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + best + "</td>");
		tableContent.append("</tr>");
		tableContent.append("<br>");
		
	/*	tableContent.append("<tr>");
		tableContent.append("<td colspan='4'>" + emailinfo + "</td>");
		tableContent.append("</tr>");
		*/
		tableContent.append("</table>");
		
		tableContent.append("</table></body></html>");
		
		String[] sendEmailIds = new String[1];;
		sendEmailIds[0] = userEmail;

		Multipart mp = new MimeMultipart();
		MimeBodyPart htmlPart = new MimeBodyPart();
		
		try {
			htmlPart.setContent(tableContent.toString(), "text/html");
			mp.addBodyPart(htmlPart);
		} catch (MessagingException e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		sendMultipartEmailWithForCase(sendEmailIds, adminEmail, subject,
				mp);
	}
}