package com.bluehub.util;

import java.util.Arrays;

import javax.mail.Multipart;

public class MailOption {

	private String from;

	private String password;

	private String mailusername;

	private String[] to;

	private String[] cc;

	private String[] bcc;

	private String subject;

	private String fileName;

	private Multipart multipart;

	private String body;

	private boolean forceSystemConfiguredSender;

	private boolean authenticate;

	/**
	 * @return the mailusername
	 */
	public String getMailusername() {
		return mailusername;
	}

	/**
	 * @param mailusername
	 *            the mailusername to set
	 */
	public void setMailusername(String mailusername) {
		this.mailusername = mailusername;
	}

	/**
	 * @return the multipart
	 */
	public Multipart getMultipart() {
		return multipart;
	}

	/**
	 * @param multipart
	 *            the multipart to set
	 */
	public void setMultipart(Multipart multipart) {
		this.multipart = multipart;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String... to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String... cc) {
		this.cc = cc;
	}

	public String[] getBcc() {
		return bcc;
	}

	public void setBcc(String... bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isForceSystemConfiguredSender() {
		return forceSystemConfiguredSender;
	}

	public void setForceSystemConfiguredSender(
			boolean forceSystemConfiguredSender) {
		this.forceSystemConfiguredSender = forceSystemConfiguredSender;
	}

	public boolean isAuthenticate() {
		return authenticate;
	}

	public void setAuthenticate(boolean authenticate) {
		this.authenticate = authenticate;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "MailOption [from=" + from + ", password=" + password + ", to="
				+ Arrays.toString(to) + ", cc=" + Arrays.toString(cc)
				+ ", bcc=" + Arrays.toString(bcc) + ", subject=" + subject
				+ ", body=" + body + ", forceSystemConfiguredSender="
				+ forceSystemConfiguredSender + ", authenticate="
				+ authenticate + ",linkmailusername=" + mailusername + "]";
	}

}
