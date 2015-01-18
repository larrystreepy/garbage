package com.bluehub.dao.secure;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserDAO extends UserDetailsService {

	/**
	 * Load the UserDetails object by userId.
	 * 
	 * @param userId
	 *            as String
	 * @return UserDetails
	 */
	UserDetails loadUserByUsername(String userId);

}
