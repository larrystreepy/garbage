package com.bluehub.vo.user;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="userinformation")
public class UserVO {
	

	@Id
	@org.hibernate.annotations.GenericGenerator(name = "hilo-strategy", strategy = "hilo")
	@GeneratedValue(generator = "hilo-strategy")

	@Column(name = "id")
	private Integer id;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "password")
	private String password;
	@Column(name = "email_id")
	private String emailId;

	@Column(name = "security_question")
	private String securityQuestion;
	@Column(name = "security_answer")
	private String securityAnswer;
	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)

	@JoinTable(name = "userroles", joinColumns = { @JoinColumn(name = "userid") }, inverseJoinColumns = { @JoinColumn(name = "roleid") })
	private RoleVO role;
	
	
	
	@Column(name = "createdon")
	private Date dateCreated;
	
	@Column(name = "updatedon")
	private Date dateModified;
	
	@Column(name = "createdby")
	private Integer creadtedBy;
	
	@Column(name = "updatedby")
	private Integer modifiedBy;
	
	@Column(name = "status")
	private Integer status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	public RoleVO getRole() {
		return role;
	}
	public void setRole(RoleVO role) {
		this.role = role;
	}
	
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	public Integer getCreadtedBy() {
		return creadtedBy;
	}
	public void setCreadtedBy(Integer creadtedBy) {
		this.creadtedBy = creadtedBy;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	 
	


}
