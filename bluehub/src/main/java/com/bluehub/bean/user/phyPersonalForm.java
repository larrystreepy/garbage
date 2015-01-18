package com.bluehub.bean.user;


public class phyPersonalForm {
	
	private String firstname;
	private String middlename;
	private String lastname;
	private String dob;
	private String ssn;
	private String contactNo;
	private String street;
	private String city;
	private String zip;
	private String policy_number;
	private String insurance_eff_date;
	private String insurance_exp_date;
	private String degree;
	private String email;
	private String language;
	private String office_phone;
	private String fax;
	
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
	private Integer hdnPKID;
	
	public Integer getHdnPKID() {
		return hdnPKID;
	}
	public void setHdnPKID(Integer hdnPKID) {
		this.hdnPKID = hdnPKID;
	}
	public String getPolicy_number() {
		return policy_number;
	}
	public void setPolicy_number(String policy_number) {
		this.policy_number = policy_number;
	}
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
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}
	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}
	/**
	 * @return the insurance_eff_date
	 */
	public String getInsurance_eff_date() {
		return insurance_eff_date;
	}
	/**
	 * @param insurance_eff_date the insurance_eff_date to set
	 */
	public void setInsurance_eff_date(String insurance_eff_date) {
		this.insurance_eff_date = insurance_eff_date;
	}
	/**
	 * @return the insurance_exp_date
	 */
	public String getInsurance_exp_date() {
		return insurance_exp_date;
	}
	/**
	 * @param insurance_exp_date the insurance_exp_date to set
	 */
	public void setInsurance_exp_date(String insurance_exp_date) {
		this.insurance_exp_date = insurance_exp_date;
	}
 
	

}
