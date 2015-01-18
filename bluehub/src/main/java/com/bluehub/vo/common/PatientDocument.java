package com.bluehub.vo.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "patientdocument")
public class PatientDocument {

	@Id
	@org.hibernate.annotations.GenericGenerator(name = "hilo-strategy", strategy = "hilo")
	@GeneratedValue(generator = "hilo-strategy")
	@Column(name = "id")
	private int id;

	@Column(name = "patientid")
	private Integer patientid;

	@Column(name = "docname")
	private String docname;

	@Column(name = "doctype")
	private String doctype;

	@Column(name = "docpath")
	private String docpath;

	@Column(name = "uploadtype")
	private String uploadType;

	@Column(name = "createdon")
	private Date createdon;

	@Column(name = "createdby")
	private Integer createdby;

	@Column(name = "keyid")
	private Integer keyid;

	@Column(name = "key")
	private String key;
	
	@Column(name = "receiveddate")
	private String receiveddate;
	
	@Column(name = "associateddate")
	private String associateddate;
	
	

	/**
	 * @return the receiveddate
	 */
	public String getReceiveddate() {
		return receiveddate;
	}

	/**
	 * @param receiveddate the receiveddate to set
	 */
	public void setReceiveddate(String receiveddate) {
		this.receiveddate = receiveddate;
	}

	/**
	 * @return the associateddate
	 */
	public String getAssociateddate() {
		return associateddate;
	}

	/**
	 * @param associateddate the associateddate to set
	 */
	public void setAssociateddate(String associateddate) {
		this.associateddate = associateddate;
	}

	public int getId() {
		return id;
	}

	public Integer getKeyid() {
		return keyid;
	}

	public void setKeyid(Integer keyid) {
		this.keyid = keyid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getCreatedby() {
		return createdby;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getPatientid() {
		return patientid;
	}

	public void setPatientid(Integer patientid) {
		this.patientid = patientid;
	}

	public String getDocname() {
		return docname;
	}

	public void setDocname(String docname) {
		this.docname = docname;
	}

	public String getDoctype() {
		return doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public String getDocpath() {
		return docpath;
	}

	public void setDocpath(String docpath) {
		this.docpath = docpath;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public Date getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

	public Integer getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(Integer updatedby) {
		this.updatedby = updatedby;
	}

	@Column(name = "updatedon")
	private Date updatedon;

	@Column(name = "updatedby")
	private Integer updatedby;

}
