package com.bluehub.dao.secure;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserProfileDAO extends UserDetailsService {

	UserDetails setUserRole(String userRole, int userId, String userName,
			String password, String emailId);

}
