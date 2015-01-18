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
@Table(name = "encounter")
public class EncounterVO {

	
	@Id
	@org.hibernate.annotations.GenericGenerator(name = "hilo-strategy", strategy = "hilo")
	@GeneratedValue(generator = "hilo-strategy")
	@Column(name = "id")
	private Integer id;
	@Column(name = "visitid")
	private Integer visitid;
	@Column(name = "userid")
	private Integer userid;
	@Column(name = "encounter")
	private String encounter;
	@Column(name = "status")
	private Integer status;
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
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	 * @return the userid
	 */
	public Integer getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	/**
	 * @return the encounter
	 */
	public String getEncounter() {
		return encounter;
	}

	/**
	 * @param encounter
	 *            the encounter to set
	 */
	public void setEncounter(String encounter) {
		this.encounter = encounter;
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
