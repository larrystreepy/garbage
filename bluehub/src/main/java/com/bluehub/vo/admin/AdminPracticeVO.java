/**
 * 
 */
package com.bluehub.vo.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "practice")
public class AdminPracticeVO {

	@Id
	@org.hibernate.annotations.GenericGenerator(name = "hilo-strategy", strategy = "hilo")
	@GeneratedValue(generator = "hilo-strategy")
	@Column(name = "id")
	private int id;

	@Column(name = "practicename")
	private String practicename;

	@Column(name = "organizationid")
	private Integer organizationid;

	@Column(name = "address")
	private String address;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "zipcode")
	private String zipcode;

	@Transient
	private String organizationname;

	/**
	 * @return the organizationname
	 */
	public String getOrganizationname() {
		return organizationname;
	}

	/**
	 * @param organizationname
	 *            the organizationname to set
	 */
	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * @param zipcode
	 *            the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the practicename
	 */
	public String getPracticename() {
		return practicename;
	}

	/**
	 * @param practicename
	 *            the practicename to set
	 */
	public void setPracticename(String practicename) {
		this.practicename = practicename;
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

	@Column(name = "createdon")
	private Date createdon;

	@Column(name = "createdby")
	private Integer createdby;

	@Column(name = "updatedon")
	private Date updatedon;

	@Column(name = "updatedby")
	private Integer updatedby;

	@Column(name = "status")
	private Integer status;

}
