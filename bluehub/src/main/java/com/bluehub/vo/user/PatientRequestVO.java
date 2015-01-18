package com.bluehub.vo.user;

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
@Table(name="patientrequest")
public class PatientRequestVO {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bluehub_user_id_seq")
//	@SequenceGenerator(name = "bluehub_user_id_seq", sequenceName = "bluehub_user_id_seq", allocationSize = 1)
	@Id
	@org.hibernate.annotations.GenericGenerator(name = "hilo-strategy", strategy = "hilo")
	@GeneratedValue(generator = "hilo-strategy")

	@Column(name = "id")
	private Integer id;
	@Column(name = "patientid")
	private Integer patientid;
	@Column(name = "physicianid")
	private Integer physicianid;
	@Column(name = "emailid")
	private String emailid;
	@Column(name = "mailstatus")
	private Integer mailstatus;
	@Column(name = "createdby")
	private Integer createdby;
	@Column(name = "createdon")
	private Date createdon;
	@Column(name = "updatedby")
	private Integer updatedby;
	@Column(name = "updatedon")
	private Date updatedon;
	
	
	
	
	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	
	
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
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
	 * @param patientid the patientid to set
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
	 * @param physicianid the physicianid to set
	 */
	public void setPhysicianid(Integer physicianid) {
		this.physicianid = physicianid;
	}
	/**
	 * @return the emailid
	 */
	public String getEmailid() {
		return emailid;
	}
	/**
	 * @param emailid the emailid to set
	 */
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	/**
	 * @return the mailstatus
	 */
	public Integer getMailstatus() {
		return mailstatus;
	}
	/**
	 * @param mailstatus the mailstatus to set
	 */
	public void setMailstatus(Integer mailstatus) {
		this.mailstatus = mailstatus;
	}
	/**
	 * @return the createdby
	 */
	public Integer getCreatedby() {
		return createdby;
	}
	/**
	 * @param createdby the createdby to set
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
	 * @param createdon the createdon to set
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
	 * @param updatedby the updatedby to set
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
	 * @param updatedon the updatedon to set
	 */
	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}
	
	
	
	
	
	
	
	
	
	
	

	
}
