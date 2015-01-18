package com.bluehub.vo.physician;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_add_info")
public class UserPersonalInfoVO {

	
	@Id
	@org.hibernate.annotations.GenericGenerator(name = "hilo-strategy", strategy = "hilo")
	@GeneratedValue(generator = "hilo-strategy")
	@Column(name = "id")
	private Integer id;
	@Column(name = "userid")
	private Integer userid;
	@Column(name = "firstname")
	private String firstname;
	@Column(name = "middlename")
	private String middlename;
	@Column(name = "lastname")
	private String lastname;
	@Column(name = "ssn")
	private String ssn;
	@Column(name = "dob")
	private Date dob;
	@Column(name = "street")
	private String street;
	@Column(name = "city")
	private String city;
	@Column(name = "zip")
	private String zip;
	@Column(name = "contact_number")
	private String contact_number;
	@Column(name = "policy_number")
	private String policy_number;
	@Column(name = "insurance_eff_date")
	private Date insurance_eff_date;
	@Column(name = "insurance_exp_date")
	private Date insurance_exp_date;
	@Column(name = "degree")
	private String degree;
	@Column(name = "email")
	private String email;
	@Column(name = "language")
	private String language;
	@Column(name = "office_phone")
	private String office_phone;
	@Column(name = "fax")
	private String fax;
	@Column(name = "signature")
	private String signature;

	/**
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * @param signature the signature to set
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getOffice_phone() {
		return office_phone;
	}

	public void setOffice_phone(String office_phone) {
		this.office_phone = office_phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "createdon")
	private Date createdon;
	@Column(name = "updatedon")
	private Date updatedon;
	@Column(name = "updatedby")
	private Date updatedby;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getContact_number() {
		return contact_number;
	}

	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}

	public String getPolicy_number() {
		return policy_number;
	}

	public void setPolicy_number(String policy_number) {
		this.policy_number = policy_number;
	}

	public Date getInsurance_eff_date() {
		return insurance_eff_date;
	}

	public void setInsurance_eff_date(Date insurance_eff_date) {
		this.insurance_eff_date = insurance_eff_date;
	}

	public Date getInsurance_exp_date() {
		return insurance_exp_date;
	}

	public void setInsurance_exp_date(Date insurance_exp_date) {
		this.insurance_exp_date = insurance_exp_date;
	}

	public Date getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

	public Date getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(Date updatedby) {
		this.updatedby = updatedby;
	}

}
