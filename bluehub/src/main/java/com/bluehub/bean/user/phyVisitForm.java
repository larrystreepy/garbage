package com.bluehub.bean.user;

import java.util.Date;

public class phyVisitForm {
	
	private String date_of_visit;
	private String reason_for_visit;
	private String prescription;
	private Integer organization;
	private Integer practice;
	private String tag;
	
	private Integer patientId;
	private Integer physicianId;
	
	
	
	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	public Integer getPhysicianId() {
		return physicianId;
	}
	public void setPhysicianId(Integer physicianId) {
		this.physicianId = physicianId;
	}
	public String getDate_of_visit() {
		return date_of_visit;
	}
	public void setDate_of_visit(String date_of_visit) {
		this.date_of_visit = date_of_visit;
	}
	public String getReason_for_visit() {
		return reason_for_visit;
	}
	public void setReason_for_visit(String reason_for_visit) {
		this.reason_for_visit = reason_for_visit;
	}
	public String getPrescription() {
		return prescription;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	public Integer getOrganization() {
		return organization;
	}
	public void setOrganization(Integer organization) {
		this.organization = organization;
	}
	public Integer getPractice() {
		return practice;
	}
	public void setPractice(Integer practice) {
		this.practice = practice;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	

}
