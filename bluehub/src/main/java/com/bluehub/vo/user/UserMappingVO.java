/**
 * 
 */
package com.bluehub.vo.user;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usermapping")
public class UserMappingVO {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "userid")
	private Integer userid;

	
	@Column(name="organizationid")
	private Integer organizationid;
	
	@Column(name="practiceid")
	private Integer practiceid;
	
	@Column(name = "createdby")
	private Integer createdby;
	
	@Column(name="createdon")
	private Timestamp  createdon;
	
	@Column(name="updatedby")
	private Integer updatedby;
	
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
	 * @return the userid
	 */
	public Integer getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
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
	 * @return the practiceid
	 */
	public Integer getPracticeid() {
		return practiceid;
	}

	/**
	 * @param practiceid the practiceid to set
	 */
	public void setPracticeid(Integer practiceid) {
		this.practiceid = practiceid;
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
	public Timestamp getCreatedon() {
		return createdon;
	}

	/**
	 * @param createdon the createdon to set
	 */
	public void setCreatedon(Timestamp createdon) {
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
	public Timestamp getUpdatedon() {
		return updatedon;
	}

	/**
	 * @param updatedon the updatedon to set
	 */
	public void setUpdatedon(Timestamp updatedon) {
		this.updatedon = updatedon;
	}

	@Column(name="updatedon")
	private Timestamp  updatedon;
	   

}
