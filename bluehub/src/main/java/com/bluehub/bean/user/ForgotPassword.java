package com.bluehub.bean.user;

public class ForgotPassword {

	private String username;
	private String pet;
	private String password;
	private String recoveryQuestion;
	private String recoveryAnswer;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the pet
	 */
	public String getPet() {
		return pet;
	}

	/**
	 * @param pet
	 *            the pet to set
	 */
	public void setPet(String pet) {
		this.pet = pet;
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
	 * @return the recoveryQuestion
	 */
	public String getRecoveryQuestion() {
		return recoveryQuestion;
	}

	/**
	 * @param recoveryQuestion
	 *            the recoveryQuestion to set
	 */
	public void setRecoveryQuestion(String recoveryQuestion) {
		this.recoveryQuestion = recoveryQuestion;
	}

	/**
	 * @return the recoveryAnswer
	 */
	public String getRecoveryAnswer() {
		return recoveryAnswer;
	}

	/**
	 * @param recoveryAnswer
	 *            the recoveryAnswer to set
	 */
	public void setRecoveryAnswer(String recoveryAnswer) {
		this.recoveryAnswer = recoveryAnswer;
	}

}
