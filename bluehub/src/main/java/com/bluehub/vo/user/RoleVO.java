package com.bluehub.vo.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleVO {

	@Id
	@Column(name = "roleid")
	private String oid;

	@Column(name = "rolename")
	private String roleName;

	@Column(name = "groupid")
	private String groupId;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "userroles", joinColumns = { @JoinColumn(name = "roleid") }, inverseJoinColumns = { @JoinColumn(name = "userid") })
	/**
	 * @return the roleName
	 */
	public final String getRoleName() {
		return roleName;
	}

	/**
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @param oid
	 *            the oid to set
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	/**
	 * @param rolName
	 *            the roleName to set
	 */
	public final void setRoleName(final String rolName) {
		this.roleName = rolName;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
