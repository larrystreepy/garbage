package com.bluehub.vo.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "userroles")
@IdClass(UserRolePK.class)
public class UserRoleVO implements Serializable {

	private static final long serialVersionUID = 78018374285740564L;

	/**
	 * default constructor.
	 */
	public UserRoleVO() {
	}

	/**
	 * @param userVendorPK
	 */
	public UserRoleVO(UserRolePK userRolePK) {
		userOid = userRolePK.getUserOid();
		roleOid = userRolePK.getRoleOid();
	}

	@Id
	@Column(name = "userid", insertable = true, updatable = true)
	private Integer userOid;

	@Id
	@Column(name = "roleid", insertable = true, updatable = true)
	private String roleOid;

	/**
	 * @return the userOid
	 */
	public Integer getUserOid() {
		return userOid;
	}

	/**
	 * @param userOid
	 */
	public void setUserOid(Integer userOid) {
		this.userOid = userOid;
	}

	/**
	 * @return the roleOid
	 */
	public String getRoleOid() {
		return roleOid;
	}

	/**
	 * @param roleOid
	 *            the roleOid to set
	 */
	public void setRoleOid(String roleOid) {
		this.roleOid = roleOid;
	}

}
