package com.bluehub.vo.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "efax")
public class FaxVo {

	
	@Id
	@org.hibernate.annotations.GenericGenerator(name = "hilo-strategy", strategy = "hilo")
	@GeneratedValue(generator = "hilo-strategy")
	@Column(name = "id")
	private int id;
	
	@Column(name = "fromfax")
	private String fromfax;
	
	@Column(name = "tofax")
	private String tofax;
	
	@Column(name = "patientid")
	private Integer patientid;
	
	@Column(name = "sendfaxqueueid")
	private String sendfaxqueueid;
	
	@Column(name = "organizationid")
	private Integer organizationid;

	@Column(name = "status")
	private Integer status;

	@Column(name = "createdby")
	private Integer createdby;

	@Column(name = "updatedby")
	private Integer updatedby;

	@Column(name = "updatedon")
	private Date updatedon;

	@Column(name = "createdon")
	private Date createdon;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the fromfax
	 */
	public String getFromfax() {
		return fromfax;
	}

	/**
	 * @param fromfax the fromfax to set
	 */
	public void setFromfax(String fromfax) {
		this.fromfax = fromfax;
	}

	/**
	 * @return the tofax
	 */
	public String getTofax() {
		return tofax;
	}

	/**
	 * @param tofax the tofax to set
	 */
	public void setTofax(String tofax) {
		this.tofax = tofax;
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
	 * @return the sendfaxqueueid
	 */
	public String getSendfaxqueueid() {
		return sendfaxqueueid;
	}

	/**
	 * @param sendfaxqueueid the sendfaxqueueid to set
	 */
	public void setSendfaxqueueid(String sendfaxqueueid) {
		this.sendfaxqueueid = sendfaxqueueid;
	}

	/**
	 * @return the organizationid
	 */
	public Integer getOrganizationid() {
		return organizationid;
	}

	/**
	 * @param organizationid the organizationid to set
	 */
	public void setOrganizationid(Integer organizationid) {
		this.organizationid = organizationid;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	
	

}
