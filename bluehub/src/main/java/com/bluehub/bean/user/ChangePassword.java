package com.bluehub.bean.user;

public class ChangePassword {

	private String currentPassword;
	private String password;
	private String newpassword;
	private String passwordError;

	/**
	 * @return the currentPassword
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}

	/**
	 * @param currentPassword
	 *            the currentPassword to set
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the newpassword
	 */
	public String getNewpassword() {
		return newpassword;
	}

	/**
	 * @param newpassword
	 *            the newpassword to set
	 */
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	/**
	 * @return the passwordError
	 */
	public String getPasswordError() {
		return passwordError;
	}

	/**
	 * @param passwordError
	 *            the passwordError to set
	 */
	public void setPasswordError(String passwordError) {
		this.passwordError = passwordError;
	}

}
