package com.bluehub.vo.user;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@AttributeOverrides({
		@AttributeOverride(name = "userid", column = @Column(name = "userid")),
		@AttributeOverride(name = "roleid", column = @Column(name = "roleid")) })
public class UserRolePK implements Serializable {

	private static final long serialVersionUID = -7460093900781470614L;

	private Integer userOid;

	private String roleOid;

	public UserRolePK() {
	}

	public UserRolePK(Integer userOid, String roleOid) {
		this.userOid = userOid;
		this.roleOid = roleOid;
	}

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

	/**
	 * {@inheritDoc}
	 */
	public final boolean equals(final Object o) {
		
		if(null != o){
			UserRolePK userRolePK = (UserRolePK) o;

			if (userRolePK.getUserOid().equals(this.userOid)
					&& userRolePK.getRoleOid().equals(this.roleOid)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public final int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}