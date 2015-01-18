package com.bluehub.bean.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bluehub.controller.physician.PhysicianMainController;
import com.bluehub.vo.user.RoleVO;

public class UserProfile implements UserDetails {
	
	
	public static Logger logger = Logger
			.getLogger(UserProfile.class);

	private static final long serialVersionUID = 1L;

	private boolean enabled;

	private boolean accountNonExpired = false;

	private boolean credentialsNonExpired;

	private boolean accountNonLocked = false;

	private String email;

	private String password;

	private String userId;

	private Integer id;

	private Map<String, Object> detail = new HashMap<String, Object>();

	private Collection<? extends GrantedAuthority> authorities;

	private String currentServiceURI;

	private String userName;
	private String serverName;
	private String userRole;
	private String roleid;

	/*
	 * public UserProfile() { id = 0; email =""; password = ""; userId = ""; }
	 */
	public UserProfile(RoleVO roleid, Integer id, String email,
			String password, String userName, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities)
			throws IllegalArgumentException {
		logger.info("UserProfile==>"+roleid.getRoleName());
		this.id = id;
		this.email = email;
		this.password = password;
		this.userName = userName;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = authorities;
		this.roleid = roleid.getRoleName();
	}

	/**
	 * @return the roleid
	 */
	public String getRoleid() {
		return roleid;
	}

	/**
	 * @param roleid
	 *            the roleid to set
	 */
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		if (id == null) {
			id = 0;
		}
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param authorities
	 *            the authorities to set
	 */
	public void setAuthorities(
			Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	/**
	 * @param accountNonExpired
	 *            the accountNonExpired to set
	 */
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	/**
	 * @param accountNonLocked
	 *            the accountNonLocked to set
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * @param credentialsNonExpired
	 *            the credentialsNonExpired to set
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the currentServiceURI
	 */
	public String getCurrentServiceURI() {
		return currentServiceURI;
	}

	/**
	 * @param currentServiceURI
	 *            the currentServiceURI to set
	 */
	public void setCurrentServiceURI(String currentServiceURI) {
		this.currentServiceURI = currentServiceURI;
	}

	/**
	 * @return the currentServiceURI
	 */
	// public List<GrantedAuthority> getGrantedAuthorities() {
	// return Arrays.asList(authorities);
	// }

	public Object getUserAttribute(String key) {
		return detail.get(key);
	}

	public void setUserAttribute(String key, Object value) {
		detail.put(key, value);
	}

	/**
	 * @return the detail
	 */
	public Map<String, Object> getDetail() {
		return detail;
	}

	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName
	 *            the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public String getUsername() {
		return userName;
	}

	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole
	 *            the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

}
