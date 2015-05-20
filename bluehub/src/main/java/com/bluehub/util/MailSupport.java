package com.bluehub.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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


public class MailSupport {
    private static final Logger logger = Logger.getLogger(MailSupport.class);

    private static final Properties properties = new Properties();

    static {
        properties.put("mail.smtp.socketfactory.port", Constants.getMailPropertyValue("socketfactory.port"));
        properties.put("mail.smtp.host", Constants.getMailPropertyValue("smtp.host"));
        properties.put("mail.smtp.port", Constants.getMailPropertyValue("smtp.port"));
        properties.put("mail.smtp.auth", Constants.getMailPropertyValue("smtp.auth"));
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
            String userName = Constants.getMailPropertyValue("email");
            String passWord = Constants.getMailPropertyValue("password");
            String mailusername = Constants.getMailPropertyValue("mailusername");

            Session session = Session.getInstance(properties, null);
            MimeMessage message = new MimeMessage(session);
            message.setSubject(mailOption.getSubject());
            message.setText(mailOption.getBody());
            message.setFrom(new InternetAddress(Constants.getMailPropertyValue("email")));

            message.setRecipients(Message.RecipientType.TO, bindAddress(mailOption.getTo()));

            if (mailOption.getCc() != null) {
                message.setRecipients(Message.RecipientType.CC, bindAddress(mailOption.getCc()));
            }
            if (mailOption.getBcc() != null) {
                message.setRecipients(Message.RecipientType.BCC, bindAddress(mailOption.getBcc()));
            }

            message.setSubject(mailOption.getSubject());
            message.setText(mailOption.getBody());

            message.saveChanges();
            Transport transport = session.getTransport("smtp");
            if (StringUtils.isNotBlank(mailusername) && mailusername.equals("blank")) {
                transport.connect(Constants.getMailPropertyValue("smtp.host"), userName, passWord);
            } else {
                transport.connect(Constants.getMailPropertyValue("smtp.host"), null, null);
            }
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (Exception e) {
            logger.error(Constants.LOG_ERROR + e.getMessage(), e);
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
                mailOption.setPassword(Constants.getMailPropertyValue("password"));
                mailOption.setMailusername(Constants.getMailPropertyValue("mailusername"));
            }

            String mailusername = mailOption.getMailusername();
            String userName = Constants.getMailPropertyValue("email");
            String passWord = Constants.getMailPropertyValue("password");

            Properties properties = System.getProperties();
            properties.put("mail.smtp.socketfactory.port", Constants.getMailPropertyValue("socketfactory.port"));
            properties.put("mail.smtp.host", Constants.getMailPropertyValue("smtp.host"));
            properties.put("mail.smtp.port", Constants.getMailPropertyValue("smtp.port"));
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(properties, null);

            try {
                MimeMessage message = new MimeMessage(session);

                message.setFrom(new InternetAddress(mailOption.getFrom()));

                message.setRecipients(Message.RecipientType.TO, bindAddress(mailOption.getTo()));
                if (mailOption.getCc() != null) {
                    message.setRecipients(Message.RecipientType.CC, bindAddress(mailOption.getCc()));
                }
                if (mailOption.getBcc() != null) {
                    message.setRecipients(Message.RecipientType.BCC, bindAddress(mailOption.getBcc()));
                }
                message.setSubject(mailOption.getSubject());
                message.setContent(mailOption.getMultipart());

                message.saveChanges();
                Transport transport = session.getTransport("smtp");
                if (StringUtils.isNotBlank(mailusername) && mailusername.equals("blank")) {
                    transport.connect(Constants.getMailPropertyValue("smtp.host"), userName, passWord);
                } else {
                    transport.connect(Constants.getMailPropertyValue("smtp.host"), null, null);
                }
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();

            } catch (MessagingException e) {
                logger.error(Constants.LOG_ERROR + e.getMessage(), e);
            }

        } catch (Exception e) {
            logger.error(Constants.LOG_ERROR + e.getMessage(), e);
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
     * @param userEmail   as string
     * @param subject     as string
     * @param bodyContent as string
     */
    public static void sendEmail(String userEmail, String subject, String bodyContent) {
        MailOption mailOption = new MailOption();
        mailOption.setForceSystemConfiguredSender(true);
        mailOption.setAuthenticate(true);
        mailOption.setTo(new String[]{userEmail});
        mailOption.setSubject(subject);
        mailOption.setBody(bodyContent);
        new MailSupport().send(mailOption);
    }

    /**
     * sends email with one cc
     *
     * @param toEmail     as string
     * @param ccEmail     as string
     * @param subject     as string
     * @param bodyContent as string
     */
    public static void sendEmailWithCCForClinical(String toEmail, String ccEmail, String subject, String bodyContent) {
        //logger.info("toEmail==>"+toEmail);
        //logger.info("ccEmail==>"+ccEmail);
        MailOption mailOption = new MailOption();
        mailOption.setForceSystemConfiguredSender(true);
        mailOption.setAuthenticate(true);
        mailOption.setTo(toEmail);
        mailOption.setCc(ccEmail);
        mailOption.setSubject(subject);
        mailOption.setBody(bodyContent);
        new MailSupport().send(mailOption);
    }

    /**
     * sends email with multipart content
     *
     * @param toEmail     as string
     * @param ccEmail     as string
     * @param subject     as string
     * @param bodyContent as string
     */
    public static void sendMultipartEmailWithCC(String toEmail, String ccEmail,
                                                String subject, Multipart bodyContent) {

        MailOption mailOption = new MailOption();
        mailOption.setForceSystemConfiguredSender(true);
        mailOption.setAuthenticate(true);
        mailOption.setTo(toEmail);
        mailOption.setCc(ccEmail);
        mailOption.setSubject(subject);
        mailOption.setMultipart(bodyContent);
        new MailSupport().sendMultipartMail(mailOption);
    }

    public static void sendSubscribeEmail(String userEmail, String subject, String bodyContent, String agencyEmail) {
        logger.info("sendSubscribeEmail() start=>");
        MailOption mailOption = new MailOption();
        mailOption.setForceSystemConfiguredSender(true);
        mailOption.setAuthenticate(true);
        mailOption.setTo(userEmail);
        mailOption.setCc(agencyEmail);
        mailOption.setSubject(subject);
        mailOption.setBody(bodyContent);
        new MailSupport().send(mailOption);
    }

    /**
     * Sends email when a child registers in the site
     *
     * @param userEmail  as string
     * @param userName   as string
     * @param adminEmail as string
     * @param adminName  as string
     */
    public static void sendMailRegisteration(String userEmail, String userName,
                                             String adminEmail, String adminName) {

        StringBuilder bodyContent = new StringBuilder();
        String subject1 = Constants.getPropertyValue(Constants.USER_REGISTERATION_SUBJECT);
        String subject2 = Constants.getPropertyValue(Constants.USER_REGISTERATION_SUBJECT1);
        String greetings = Constants.getPropertyValue(Constants.USER_REGISTERATION_GREETINGS);
        String welcome = Constants.getPropertyValue(Constants.USER_REGISTERATION_WELCOME);
        String content = Constants.getPropertyValue(Constants.USER_REGISTERATION_CONTENT);
        String mailContent = Constants.getPropertyValue(Constants.USER_REGISTERATION_MAILCONTENT);
        String best = Constants.getPropertyValue(Constants.USER_REGISTERATION_BEST);

        String donotreply = Constants.getPropertyValue(Constants.donotreply);
        adminEmail = Constants.getPropertyValue(Constants.SUPPORT_MAIL_ID);

        String subject = subject1 + " " + userName + " " + subject2;
        bodyContent.append(greetings).append("\n");
        bodyContent.append(welcome).append(" ").append(userName).append(".").append("\n");
        bodyContent.append(content).append("\n");
        bodyContent.append("\n");
        bodyContent.append(mailContent).append("\n");
        bodyContent.append(adminEmail);
        bodyContent.append("\n");
        bodyContent.append("\n").append(best).append("\n");
        //bodyContent.append(adminName + "\n");
        bodyContent.append("\n");
        bodyContent.append("\n");
        bodyContent.append("").append(donotreply).append("\n");

        sendEmailWithCCForClinical(userEmail, adminEmail, subject, bodyContent.toString());
    }


    public static void sendMailRegisterationBluehub(String userEmail, String userName,
                                                    String adminEmail, String adminName, String serverUrl) {

        StringBuilder bodyContent = new StringBuilder();
        String subject1 = Constants.getPropertyValue(Constants.USER_REGISTERATION_SUBJECT);
        String subject2 = Constants.getPropertyValue(Constants.USER_REGISTERATION_SUBJECT1);
        String greetings = Constants.getPropertyValue(Constants.USER_REGISTERATION_GREETINGS);
        String welcome = Constants.getPropertyValue(Constants.USER_REGISTERATION_WELCOME);
        String content = Constants.getPropertyValue(Constants.USER_REGISTERATION_CONTENT);
        String best = Constants.getPropertyValue(Constants.USER_REGISTERATION_BEST);

        String donotreply = Constants.getPropertyValue(Constants.donotreply);
        adminEmail = Constants.getPropertyValue(Constants.SUPPORT_MAIL_ID);

        String subject = subject1 + " " + userName + " " + subject2;
        bodyContent.append(greetings).append("\n");
        bodyContent.append(welcome).append(" ").append(userName).append(".").append("\n");
        bodyContent.append(content).append("\n");
        bodyContent.append("\n");
        bodyContent.append("\n");
        bodyContent.append("\n").append(best).append("\n");
        bodyContent.append("\n");
        bodyContent.append("").append(donotreply).append("\n");

        sendEmailWithCCForClinical(userEmail, adminEmail, subject, bodyContent.toString());
    }

    /**
     * new password will be e-mailed to the user
     *
     * @param userEmail    as string
     * @param userName     as string
     * @param userPassword as string
     */
    public static void sendForgotPasswordMail(String userEmail, String userName, String userPassword) {

        StringBuilder bodyContent = new StringBuilder();
        String subject = Constants.getPropertyValue(Constants.FORGOT_PASSWORD_SUBJECT);
        String dear = Constants.getPropertyValue(Constants.FORGOT_PASSWORD_DEAR);
        String content = Constants.getPropertyValue(Constants.FORGOT_PASSWORD_CONTENT);
        String mailContent = Constants.getPropertyValue(Constants.FORGOT_PASSWORD_MAILCONTENT);
        String best = Constants.getPropertyValue(Constants.FORGOT_PASSWORD_BEST);
        String adminEmail = Constants.getMailPropertyValue("adminemail");

        bodyContent.append(dear).append(" ").append(userName).append(".").append("\n");
        bodyContent.append("\n");
        bodyContent.append(content).append(userPassword).append("\n");
        bodyContent.append(mailContent).append("\n");
        bodyContent.append("\n");
        bodyContent.append(best).append("\n");
        bodyContent.append(adminEmail).append("\n");
        sendEmail(userEmail, subject, bodyContent.toString());
    }

    public static void sendPatientRequestMail(String userEmail, String userName, String serverUrl) {

        logger.info("sendPatientRequestMail : ");

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();

        String subject = Constants.getPropertyValue(Constants.PATIENT_REQUEST_SUBJECT);
        String best = Constants.getPropertyValue(Constants.PATIENT_REQUEST_BEST);
        String click_linkhere = Constants.getPropertyValue(Constants.click_linkhere);

        String newheadingsubject = Constants.getPropertyValue(Constants.newheadingsubject);
        String newheadingsubject1 = Constants.getPropertyValue(Constants.newheadingsubject1);

        String newsubject = Constants.getPropertyValue(Constants.newsubject);
        String newsubjectt = Constants.getPropertyValue(Constants.newsubjectt);

        String newsubject1 = Constants.getPropertyValue(Constants.newsubject1);
        String newsubject2 = Constants.getPropertyValue(Constants.newsubject2);
        String newsubject3 = Constants.getPropertyValue(Constants.newsubject3);
        String newsubject4 = Constants.getPropertyValue(Constants.newsubject4);
        String newsubject5 = Constants.getPropertyValue(Constants.newsubject5);
        String newsubject6 = Constants.getPropertyValue(Constants.newsubject6);
        String newsubject7 = Constants.getPropertyValue(Constants.newsubject7);
        String newsubject8 = Constants.getPropertyValue(Constants.newsubject8);
        String newsubject9 = Constants.getPropertyValue(Constants.newsubject9);
        String newsubject10 = Constants.getPropertyValue(Constants.newsubject10);
        String newsubject11 = Constants.getPropertyValue(Constants.newsubject11);
        String newsubject12 = Constants.getPropertyValue(Constants.newsubject12);
        String newsubject13 = Constants.getPropertyValue(Constants.newsubject13);
        String newsubject14 = Constants.getPropertyValue(Constants.newsubject14);
        String newsubject15 = Constants.getPropertyValue(Constants.newsubject15);
        String newsubject16 = Constants.getPropertyValue(Constants.newsubject16);
        String newsubject17 = Constants.getPropertyValue(Constants.newsubject17);
        String newsubject18 = Constants.getPropertyValue(Constants.newsubject18);
        String newsubject19 = Constants.getPropertyValue(Constants.newsubject19);
        String newsubject20 = Constants.getPropertyValue(Constants.newsubject20);
        String newsubject21 = Constants.getPropertyValue(Constants.newsubject21);

        String subjecturl = Constants.getPropertyValue(Constants.subjecturl);
        String newsubject111 = Constants.getPropertyValue(Constants.newsubject111);
        String newsubjectfaxno = Constants.getPropertyValue(Constants.newsubjectfaxno);

        serverUrl = serverUrl + "/login.do";
        String activateLink = "<a href='" + serverUrl + "'>" + click_linkhere + "</a>";
        String subjecturllink = "<a href='http://www.bluehubhealth.com/requested'>" + subjecturl + "</a>";

        final String nbsp6 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        final String nbsp10 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

        String tableContent = createEmailTable(
                nbsp10 + nbsp10 + nbsp10 + "<b>" + newheadingsubject + "</b>",
                "<br>",
                nbsp10 + newheadingsubject1,
                newsubject + " " + userName + " " + newsubjectt,
                newsubject1 + " " + subjecturllink + " " + newsubject111 + " <b>" + newsubjectfaxno + "</b>",
                newsubject2,
                nbsp6 + newsubject3,
                nbsp6 + newsubject4,
                nbsp6 + newsubject5,
                nbsp6 + newsubject6,
                nbsp6 + newsubject7,
                nbsp6 + newsubject8,
                nbsp6 + newsubject9,
                nbsp6 + newsubject10,
                nbsp6 + newsubject11,
                "<br>",
                newsubject12,
                nbsp6 + newsubject13,
                nbsp6 + newsubject14,
                nbsp6 + newsubject15,
                nbsp6 + newsubject16,
                "<br>",
                newsubject17,
                newsubject18,
                newsubject19,
                newsubject20,
                "<b>" + userName + " / " + dateFormat.format(date) + ",</b>",
                newsubject21,
                activateLink,
                best
        );

        sendBasicEmail(userEmail, subject, tableContent);
    }

    public static void sendShareRequestMail(String userEmail, String userName,
                                            Integer shareReqId, String role, String serverUrl, String name, String req) {

        if (role.equals("Patient")) {

            logger.info("sendPatientRequestMail : ");

            final String subject = Constants.getPropertyValue(Constants.SHARE_REQUEST_SUBJECT);
            final String content1 = Constants.getPropertyValue(Constants.SHARE_REQUEST_CONTENT1);
            final String content2 = Constants.getPropertyValue(Constants.SHARE_REQUEST_CONTENT2);
            final String linkText = Constants.getPropertyValue(Constants.SHARE_REQUEST_LINK_TEXT);
            final String emailIntro = Constants.getPropertyValue(Constants.SHARE_REQUEST_EMAIL_INTRO);
            final String supportEmail = Constants.getPropertyValue(Constants.SUPPORT_EMAIL);
            final String dear = Constants.getPropertyValue(Constants.PATIENT_REQUEST_DEAR);
            final String best = Constants.getPropertyValue(Constants.PATIENT_REQUEST_BEST);

            final String link = String.format("  <a href='%s'>%s</a>", serverUrl, linkText);
            final String emailLink = String.format("<a href='mailto:%s'>%s</a>", supportEmail, supportEmail);

            String tableContent = createEmailTable(
                    dear + " " + userName + ", ",
                    content1 + link + content2,
                    emailIntro + emailLink,
                    best
            );

            sendBasicEmail(userEmail, subject, tableContent);
        }
    }

    public static void sendPatientRequestBehalfMail(String userEmail, String userName, String serverUrl, String requestFrom, String requestTo) {

        logger.info("sendPatientRequestBehalfMail : ");

        String subject = Constants.getPropertyValue(Constants.SHARE_REQUEST_SUBJECT);
        String dear = Constants.getPropertyValue(Constants.PATIENT_REQUEST_DEAR);
        String best = Constants.getPropertyValue(Constants.PATIENT_REQUEST_BEST);

        String mailContent = "Your provider, " + requestFrom + ", has requested your records from " + requestTo +
                ".  Once delivered, your records will be available to you in your myBlueHub account.";

        String content = createEmailTable(
                dear + " " + userName + ",",
                mailContent,
                best + "<br>BlueHub Health"
        );

        sendBasicEmail(userEmail, subject, content);
    }

    public static void sendPhysicianRequestBehalfMail(String userEmail,
                                                      String userName, String serverUrl, String patient, String physician, String url) {

        logger.info("sendPhysicianRequestBehalfMail : ");

        String subject = Constants.getPropertyValue(Constants.SHARE_REQUEST_SUBJECT);
        String dear = Constants.getPropertyValue(Constants.PATIENT_REQUEST_DEAR);
        String best = Constants.getPropertyValue(Constants.PATIENT_REQUEST_BEST);
        String click_linkhere = Constants.getPropertyValue(Constants.click_linkhere);

        String mailContent = "Mr/Mrs " + physician + " Requesting You To Share The Clinical Documents Of " + patient + " .";
        //		physician/uploadClinicalDocuments.do?requestId=1703936&patientId=491520&patientName=Dinu Kumar
        serverUrl = url;

        String activateLink = "<a href='" + serverUrl + "'>" + click_linkhere + "</a>";

        String tableContent = createEmailTable(
                dear + " " + userName + ", ",
                mailContent,
                activateLink,
                best
        );

        sendBasicEmail(userEmail, subject, tableContent);
    }

    public static void sendPhysicianApprovedBehalfMail(String userEmail,
                                                       String userName, String requestfrom, String url, String patient) {
        logger.info("sendPhysicianApprovedBehalfMail : ");

        String subject = Constants.getPropertyValue(Constants.SHARE_REQUEST_SUBJECT);
        String dear = Constants.getPropertyValue(Constants.PATIENT_REQUEST_DEAR);
        String best = Constants.getPropertyValue(Constants.PATIENT_REQUEST_BEST);
        String click_linkhere = Constants.getPropertyValue(Constants.click_linkview);

        String mailContent = "Mr/Mrs " + requestfrom + " Has Uploaded The Clinical Documents Of " + patient + " .";
        //		physician/uploadClinicalDocuments.do?requestId=1703936&patientId=491520&patientName=Dinu Kumar

        String activateLink = "<a href='" + url + "'>" + click_linkhere + "</a>";

        String tableContent = createEmailTable(
                dear + " " + userName + ", ",
                mailContent,
                activateLink,
                best
        );

        sendBasicEmail(userEmail, subject, tableContent);
    }

    /**
     * Generate a standard email content table using the lines provided.
     *
     * @param lines Lines to include in email
     * @return HTML for rendering the email content as a table
     */
    private static String createEmailTable(String... lines) {
        final StringBuilder tableContent = new StringBuilder();
        tableContent.append("<html><body>");
        tableContent.append("<table border='0' style='border:none;width:100%;'>");

        for (String line : lines) {
            tableContent.append("<tr>");
            tableContent.append("<td>").append(line).append("</td>");
            tableContent.append("</tr>\n");
        }

        tableContent.append("</table></body></html>");

        return tableContent.toString();
    }

    /**
     * Send  a basic HTML encoded email message with a CC to the admin email address.
     *
     * @param userEmail   To email address
     * @param subject     Email subject
     * @param htmlContent HTML content
     */
    public static void sendBasicEmail(String userEmail, String subject, String htmlContent) {
        final Multipart mp = new MimeMultipart();

        try {
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html");
            mp.addBodyPart(htmlPart);
        } catch (MessagingException e) {
            logger.error(Constants.LOG_ERROR + e.getMessage(), e);
        }

        final String adminEmail = Constants.getMailPropertyValue("adminemail");

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Email: to '%s' subject='%s' body=%s'", userEmail, subject, htmlContent));
        }

        // Setup the mail options and then send the email

        MailOption mailOption = new MailOption();
        mailOption.setForceSystemConfiguredSender(true);
        mailOption.setAuthenticate(true);
        mailOption.setTo(userEmail);
        mailOption.setCc(adminEmail);
        mailOption.setSubject(subject);
        mailOption.setMultipart(mp);

        new MailSupport().sendMultipartMail(mailOption);
    }
}