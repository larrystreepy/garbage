package com.bluehub.bean.user;

public class UserForm {
	
	private String userTypePat;
	private String userEmail;
	private String password;
	private String recoveryQuestion;
	private String recoveryAnswer;
	
	
	private Integer selectPhyOrganization;
	
	
	
	
	
	
	/**
	 * @return the selectPhyOrganization
	 */
	public Integer getSelectPhyOrganization() {
		return selectPhyOrganization;
	}
	/**
	 * @param selectPhyOrganization the selectPhyOrganization to set
	 */
	public void setSelectPhyOrganization(Integer selectPhyOrganization) {
		this.selectPhyOrganization = selectPhyOrganization;
	}
	public String getUserTypePat() {
		return userTypePat;
	}
	public void setUserTypePat(String userTypePat) {
		this.userTypePat = userTypePat;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRecoveryQuestion() {
		return recoveryQuestion;
	}
	public void setRecoveryQuestion(String recoveryQuestion) {
		this.recoveryQuestion = recoveryQuestion;
	}
	public String getRecoveryAnswer() {
		return recoveryAnswer;
	}
	public void setRecoveryAnswer(String recoveryAnswer) {
		this.recoveryAnswer = recoveryAnswer;
	}
	 

}
