package com.bluehub.dao.secure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.bluehub.bean.user.UserProfile;
import com.bluehub.util.Constants;
import com.bluehub.vo.user.RoleVO;

@Transactional(readOnly = true)
public class UserProfileDAOImpl implements UserProfileDAO {

	private static Logger logger = Logger.getLogger(UserProfileDAOImpl.class);

	public UserDetails setUserRole(String userRole, int userId,
			String userName, String password, String emailId)
			throws UsernameNotFoundException, DataAccessException {
		logger.debug("loadUserByUsername userId====>" + userId);
		try {
			List<GrantedAuthority> roleList = new ArrayList<GrantedAuthority>();
			String roleName = null;

			if (StringUtils.equalsIgnoreCase(Constants.PHYSICIAN, userRole)) {

				roleName = Constants.ROLE_PHYSICIAN;
			} else if (StringUtils.equalsIgnoreCase(
					Constants.PRACTICE_ADMINISTRATOR, userRole)) {
				roleName = Constants.ROLE_PRACTICE_ADMINISTRATOR;
			}
			logger.info("userRole===>" + userRole + "==roleName==>"
					+ roleName);
			RoleVO role = new RoleVO();
			role.setRoleName(userRole);
			role.setOid(userRole);
			role.setGroupId(userRole);

			@SuppressWarnings("deprecation")
			GrantedAuthority authority = new GrantedAuthorityImpl(roleName);
			roleList.add(authority);
			boolean credentialsNonExpired = true;

			try {
				Collection<? extends GrantedAuthority> array = (Collection<? extends GrantedAuthority>) roleList;
				UserDetails userDetails = new UserProfile(role, userId,
						userName, password, emailId, true, true, true,
						credentialsNonExpired, array);

				if (userDetails.getAuthorities() == null
						&& (userDetails.getAuthorities() != null && userDetails
								.getAuthorities().size() > 0)) {
					throw new UsernameNotFoundException("Authorities not found");

				}
				return userDetails;
			} catch (Exception e) {
				logger.error(Constants.LOG_ERROR + e.getMessage());
			}
		} catch (UsernameNotFoundException e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			throw e;
		} catch (Throwable e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			throw new UsernameNotFoundException(
					"Username or Password is invalid");
		}
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
