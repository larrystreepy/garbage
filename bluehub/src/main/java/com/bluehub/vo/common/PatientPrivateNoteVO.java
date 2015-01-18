package com.bluehub.vo.common;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "privatenote")
public class PatientPrivateNoteVO {

	
	@Id
	@org.hibernate.annotations.GenericGenerator(name = "hilo-strategy", strategy = "hilo")
	@GeneratedValue(generator = "hilo-strategy")
	@Column(name = "id")
	private Integer id;
	@Column(name = "visitid")
	private Integer visitid;
	@Column(name = "patientid")
	private Integer patientid;
	@Column(name = "note")
	private String note;
	@Column(name = "method")
	private String method;
	@Column(name = "type")
	private String type;
	
	
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	@Column(name = "createdby")
	private Integer createdby;
	@Column(name = "createdon")
	private Date createdon;
	@Column(name = "updatedby")
	private Integer updatedby;
	@Column(name = "updatedon")
	private Date updatedon;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

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

	/**
	 * @return the patientid
	 */
	public Integer getPatientid() {
		return patientid;
	}

	/**
	 * @param patientid
	 *            the patientid to set
	 */
	public void setPatientid(Integer patientid) {
		this.patientid = patientid;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the createdby
	 */
	public Integer getCreatedby() {
		return createdby;
	}

	/**
	 * @param createdby
	 *            the createdby to set
	 */
	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	/**
	 * @return the createdon
	 */
	public Date getCreatedon() {
		return createdon;
	}

	/**
	 * @param createdon
	 *            the createdon to set
	 */
	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	/**
	 * @return the updatedby
	 */
	public Integer getUpdatedby() {
		return updatedby;
	}

	/**
	 * @param updatedby
	 *            the updatedby to set
	 */
	public void setUpdatedby(Integer updatedby) {
		this.updatedby = updatedby;
	}

	/**
	 * @return the updatedon
	 */
	public Date getUpdatedon() {
		return updatedon;
	}

	/**
	 * @param updatedon
	 *            the updatedon to set
	 */
	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

}
