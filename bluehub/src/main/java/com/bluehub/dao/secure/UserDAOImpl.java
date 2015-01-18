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
import com.bluehub.vo.user.UserVO;

@Transactional(readOnly = true)
public class UserDAOImpl extends HibernateDaoSupport implements UserDAO {

	private static Logger logger = Logger.getLogger(UserDAOImpl.class);

	@SuppressWarnings("unchecked")
	public UserDetails loadUserByUsername(String userId)
			throws UsernameNotFoundException, DataAccessException {
		logger.debug("loadUserByUsername userId====>" + userId);
		try {
			String userGroup = null;
			String roleName = null;
			List<GrantedAuthority> roleList = new ArrayList<GrantedAuthority>();
			UserVO registrationFormVO = null;
			List<UserVO> users = null;
			try {

				users = getSession()
						.createQuery(
								"select rg from UserVO rg where rg.userName = :id")
						.setParameter("id", userId).list();
				/*
				 * String sqlQuery=
				 * "select rg from UserVO rg where LOWER(rg.userName) LIKE LOWER('"
				 * + userId+"')"; users = getHibernateTemplate().find(sqlQuery);
				 */

			} catch (Exception e) {
				logger.error(Constants.LOG_ERROR + e.getMessage());
			}
			if (users != null && users.size() > 0) {
				registrationFormVO = (UserVO) users.get(0);
				userGroup = registrationFormVO.getRole().getOid();
				RoleVO role = registrationFormVO.getRole();
				if (role != null) {
					roleName = role.getRoleName();
				}

				if (StringUtils.equalsIgnoreCase(Constants.ADMINISTRATOR,
						userGroup)) {
					roleName = Constants.ROLE_ADMINISTRATOR;
				} else if (StringUtils.equalsIgnoreCase(Constants.PHYSICIAN,
						userGroup)) {

					roleName = Constants.ROLE_PHYSICIAN;
				} else if (StringUtils.equalsIgnoreCase(Constants.PATIENT,
						userGroup)) {

					roleName = Constants.ROLE_PATIENT;
				} else if (StringUtils.equalsIgnoreCase(
						Constants.PRACTICE_ADMINISTRATOR, userGroup)) {

					roleName = Constants.ROLE_PRACTICE_ADMINISTRATOR;
				}

				@SuppressWarnings("deprecation")
				GrantedAuthority authority = new GrantedAuthorityImpl(roleName);
				roleList.add(authority);
			} else {
				throw new UsernameNotFoundException(
						"Username or password is invalid");
			}
			boolean credentialsNonExpired = true;

			try {
				Collection<? extends GrantedAuthority> array = (Collection<? extends GrantedAuthority>) roleList;
				UserDetails userDetails = new UserProfile(
						registrationFormVO.getRole(),
						registrationFormVO.getId(),
						registrationFormVO.getUserName(),
						registrationFormVO.getPassword(),
						registrationFormVO.getEmailId(), true, true, true,
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
}
