package com.bluehub.vo.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "audittrailrecord")
public class AuditTrailVO {

	@Id
	@org.hibernate.annotations.GenericGenerator(name = "hilo-strategy", strategy = "hilo")
	@GeneratedValue(generator = "hilo-strategy")
	private Integer id;

	@Column(name = "actiontype")
	private Integer actiontype;

	@Column(name = "entityid")
	private Integer entityid;

	@Column(name = "entityname")
	private String entityname;

	@Column(name = "logtext")
	private String logtext;

	@Column(name = "ipaddress")
	private String ipaddress;

	@Column(name = "userid")
	private Integer userid;

	@Column(name = "createdby")
	private Integer createdby;

	@Column(name = "createdon")
	private Date createdon;

	@Column(name = "updatedby")
	private Integer updatedby;

	@Column(name = "updatedon")
	private Date updatedon;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActiontype() {
		return actiontype;
	}

	public void setActiontype(Integer actiontype) {
		this.actiontype = actiontype;
	}

	public Integer getEntityid() {
		return entityid;
	}

	public void setEntityid(Integer entityid) {
		this.entityid = entityid;
	}

	public String getEntityname() {
		return entityname;
	}

	public void setEntityname(String entityname) {
		this.entityname = entityname;
	}

	public String getLogtext() {
		return logtext;
	}

	public void setLogtext(String logtext) {
		this.logtext = logtext;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
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

}
