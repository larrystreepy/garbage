package com.bluehub.bean.admin;

import java.util.Date;

public class AdminSearchDateForm {

	private String patientname;

	private String physicianname;

	private Date patientdob;

	private Date dateofvisit;

	private String reasonforvisit;

	private Integer visitid;

	private String dob;

	private String visitdate;

	/**
	 * @return the visitid
	 */
	public Integer getVisitid() {
		return visitid;
	}

	/**
	 * @param visitid
	 *            the visitid to set
	 */
	public void setVisitid(Integer visitid) {
		this.visitid = visitid;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getVisitdate() {
		return visitdate;
	}

	public void setVisitdate(String visitdate) {
		this.visitdate = visitdate;
	}

	/**
	 * @return the patientdob
	 */
	public Date getPatientdob() {
		return patientdob;
	}

	/**
	 * @param patientdob
	 *            the patientdob to set
	 */
	public void setPatientdob(Date patientdob) {
		this.patientdob = patientdob;
	}

	/**
	 * @return the patientname
	 */
	public String getPatientname() {
		return patientname;
	}

	/**
	 * @param patientname
	 *            the patientname to set
	 */
	public void setPatientname(String patientname) {
		this.patientname = patientname;
	}

	/**
	 * @return the physicianname
	 */
	public String getPhysicianname() {
		return physicianname;
	}

	/**
	 * @param physicianname
	 *            the physicianname to set
	 */
	public void setPhysicianname(String physicianname) {
		this.physicianname = physicianname;
	}

	/**
	 * @return the dateofvisit
	 */
	public Date getDateofvisit() {
		return dateofvisit;
	}

	/**
	 * @param dateofvisit
	 *            the dateofvisit to set
	 */
	public void setDateofvisit(Date dateofvisit) {
		this.dateofvisit = dateofvisit;
	}

	/**
	 * @return the reasonforvisit
	 */
	public String getReasonforvisit() {
		return reasonforvisit;
	}

	/**
	 * @param reasonforvisit
	 *            the reasonforvisit to set
	 */
	public void setReasonforvisit(String reasonforvisit) {
		this.reasonforvisit = reasonforvisit;
	}

}
