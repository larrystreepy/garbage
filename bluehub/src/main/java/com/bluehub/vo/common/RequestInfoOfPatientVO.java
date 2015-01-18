package com.bluehub.vo.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "requestinfobehalfofpatient")
public class RequestInfoOfPatientVO {

	@Id
	@org.hibernate.annotations.GenericGenerator(name = "hilo-strategy", strategy = "hilo")
	@GeneratedValue(generator = "hilo-strategy")
	private Integer id;

	@Column(name = "requestfrom")
	private Integer requestfrom;

	@Column(name = "requesttopatient")
	private Integer requesttopatient;

	@Column(name = "requesttophysician")
	private Integer requesttophysician;

	@Column(name = "requestdate")
	private Date requestDate;

	@Column(name = "requeststatus")
	private String requestStatus;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRequestfrom() {
		return requestfrom;
	}

	public void setRequestfrom(Integer requestfrom) {
		this.requestfrom = requestfrom;
	}

	public Integer getRequesttopatient() {
		return requesttopatient;
	}

	public void setRequesttopatient(Integer requesttopatient) {
		this.requesttopatient = requesttopatient;
	}

	public Integer getRequesttophysician() {
		return requesttophysician;
	}

	public void setRequesttophysician(Integer requesttophysician) {
		this.requesttophysician = requesttophysician;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
