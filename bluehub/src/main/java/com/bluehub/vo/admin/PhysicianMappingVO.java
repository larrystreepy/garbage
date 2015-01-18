package com.bluehub.vo.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usermapping")
public class PhysicianMappingVO {

	@Id
	@org.hibernate.annotations.GenericGenerator(name = "hilo-strategy", strategy = "hilo")
	@GeneratedValue(generator = "hilo-strategy")
	@Column(name = "id")
	private int id;

	@Column(name = "userid")
	private Integer userid;

	@Column(name = "organizationid")
	private Integer organizationid;

	@Column(name = "practiceid")
	private Integer practiceid;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getOrganizationid() {
		return organizationid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setOrganizationid(Integer organizationid) {
		this.organizationid = organizationid;
	}

	public Integer getPracticeid() {
		return practiceid;
	}

	public void setPracticeid(Integer practiceid) {
		this.practiceid = practiceid;
	}

	public Integer getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Integer getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(Integer updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

}
