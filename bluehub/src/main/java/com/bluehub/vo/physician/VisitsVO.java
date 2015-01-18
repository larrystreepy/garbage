package com.bluehub.vo.physician;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "visitinformation")
public class VisitsVO {

	@Id
	@org.hibernate.annotations.GenericGenerator(name = "hilo-strategy", strategy = "hilo")
	@GeneratedValue(generator = "hilo-strategy")
	@Column(name = "id")
	private Integer id;
	@Column(name = "patientid")
	private Integer patientid;
	@Column(name = "physicianid")
	private Integer physicianid;
	@Column(name = "organizationid")
	private Integer organizationid;
	@Column(name = "practiceid")
	private Integer practiceid;
	@Column(name = "dateofvisit")
	private Date dateofvisit;
	@Column(name = "reasonforvisit")
	private String reasonforvisit;
	@Column(name = "prescription")
	private String prescription;
	@Column(name = "createdby")
	private Integer createdby;
	@Column(name = "createdon")
	private Date createdon;
	@Column(name = "tag")
	private String tag;

	@Column(name = "updatedby")
	private Integer updatedby;
	@Column(name = "updatedon")
	private Date updatedon;

	@javax.persistence.Transient
	private String patientname;

	@javax.persistence.Transient
	private String physicianname;

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
	 * @return the physicianid
	 */
	public Integer getPhysicianid() {
		return physicianid;
	}

	/**
	 * @param physicianid
	 *            the physicianid to set
	 */
	public void setPhysicianid(Integer physicianid) {
		this.physicianid = physicianid;
	}

	/**
	 * @return the organizationid
	 */
	public Integer getOrganizationid() {
		return organizationid;
	}

	/**
	 * @param organizationid
	 *            the organizationid to set
	 */
	public void setOrganizationid(Integer organizationid) {
		this.organizationid = organizationid;
	}

	/**
	 * @return the practiceid
	 */
	public Integer getPracticeid() {
		return practiceid;
	}

	/**
	 * @param practiceid
	 *            the practiceid to set
	 */
	public void setPracticeid(Integer practiceid) {
		this.practiceid = practiceid;
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

	/**
	 * @return the prescription
	 */
	public String getPrescription() {
		return prescription;
	}

	/**
	 * @param prescription
	 *            the prescription to set
	 */
	public void setPrescription(String prescription) {
		this.prescription = prescription;
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
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
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

	/**
	 * @return the patientname
	 */
	@Transient
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
	@Transient
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

}
