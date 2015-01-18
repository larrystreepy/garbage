package com.bluehub.vo.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluehub.vo.physician.VisitsVO;
import com.bluehub.vo.user.UserVO;

@Entity
@Table(name = "shareclinicalinfo")
public class ShareClinicalInfo {

	@Id
	@org.hibernate.annotations.GenericGenerator(name = "hilo-strategy", strategy = "hilo")
	@GeneratedValue(generator = "hilo-strategy")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sharefrom")
	private UserVO shareFrom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shareto")
	private UserVO shareTo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visitid")
	private VisitsVO visit;

	@Column(name = "fromdate")
	private Date fromDate;

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

	public UserVO getShareFrom() {
		return shareFrom;
	}

	public void setShareFrom(UserVO shareFrom) {
		this.shareFrom = shareFrom;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public UserVO getShareTo() {
		return shareTo;
	}

	public void setShareTo(UserVO shareTo) {
		this.shareTo = shareTo;
	}

	public VisitsVO getVisit() {
		return visit;
	}

	public void setVisit(VisitsVO visit) {
		this.visit = visit;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
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
