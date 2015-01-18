package com.bluehub.dao.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bluehub.bean.admin.SearchPhysicianForm;
import com.bluehub.bean.common.UserDetails;
import com.bluehub.util.CommonUtils;
import com.bluehub.vo.admin.PatientMappingVO;
import com.bluehub.vo.admin.PhysicianMappingVO;
import com.bluehub.vo.common.AuditTrailVO;
import com.bluehub.vo.common.PatientDocument;
import com.bluehub.vo.user.DocumentVO;
import com.bluehub.vo.user.PatientRequestVO;
import com.bluehub.vo.user.RoleVO;
import com.bluehub.vo.user.UserGroupVO;
import com.bluehub.vo.user.UserRoleVO;
import com.bluehub.vo.user.UserVO;
import com.bluehub.manager.user.UserManager;
@Transactional(readOnly = true)
public class UserRegistrationDaoImpl extends HibernateDaoSupport implements
		UserRegistrationDao {

	private static Logger logger = Logger
			.getLogger(UserRegistrationDaoImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserGroupVO> getAllUserGroups() {
		return getHibernateTemplate().find("from UserGroupVO");
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RoleVO> getAllRoles() {
		return getHibernateTemplate().find("from RoleVO");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateUserRegistration(UserVO registrationForm1VO) {
		logger.info("UserRegistrationDaoImpl updateUserRegistration() starts.");
		getHibernateTemplate().update(registrationForm1VO);
		logger.info("UserRegistrationDaoImpl updateUserRegistration() ends.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addUserRole(UserRoleVO userRoleVO) {
		getHibernateTemplate().save(userRoleVO);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RoleVO> getRolesByUserGroup(String userGroup) {
		String queryString = "from RoleVO role where role.groupId ='"
				+ userGroup + "'";
		return getHibernateTemplate().find(queryString);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "rawtypes" })
	public String getRoleById(final String roleName) {
		logger.info("UserRegistrationDaoImpl getRoleById() starts.");
		String roleId = null;
		List roleList = null;
		String query = "select role.oid from RoleVO role where role.roleName=?";
		Object[] queryParam = { roleName };
		roleList = getHibernateTemplate().find(query, queryParam);
		if (roleList != null && roleList.size() > 0) {
			if (roleList.get(0) != null && !roleList.get(0).equals("")) {
				roleId = roleList.get(0).toString();
			}
		}
		logger.info("UserRegistrationDaoImpl getRoleById() ends.");
		return roleId;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public UserVO getUserRegistrationByMail(String userEmail)
			throws DataAccessException {
		UserVO registrationForm1VO = null;
		List<UserVO> userList = null;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			String query = "from UserVO user where (user.userName = :userEmail)";
			Query q = session.createQuery(query).setParameter("userEmail",
					userEmail);
			if (q.list() != null && !q.list().isEmpty()) {
				userList = q.list();
				if (userList != null && userList.size() > 0) {
					registrationForm1VO = (UserVO) userList.get(0);
				}
			}
		} finally {
			session.flush();
			session.close();

		}
		return registrationForm1VO;
	}

	@SuppressWarnings("unchecked")
	public List<SearchPhysicianForm> getPhysicianDetails(
			String searchphysician, String orgid, String practiceid)
			throws DataAccessException {

		List<SearchPhysicianForm> searchPhysician = null;
		Query query = null;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			// and (CONCAT(LOWER(ui.firstname),LOWER(ui.lastname)))

			/*
			 * String querystr =
			 * "select CONCAT(ui.firstname,' ',ui.lastname) as firstname,organizationname,ui.userid,pr.practicename from user_add_info ui,organization "
			 * +
			 * "org,usermapping um,userroles ur,practice pr where org.id=um.organizationid and pr.id=um.practiceid and "
			 * +
			 * "ui.userid=um.userid and ui.userid=ur.userid and ur.roleid='Physician' and org.id='"
			 * +orgid+"' " +
			 * "and pr.id='"+practiceid+"' and LOWER(ui.firstname) like '%"
			 * +searchphysician+"%'";
			 */

			String querystr = "select CONCAT(ui.firstname,' ',ui.lastname) as firstname,organizationname,ui.userid,pr.practicename from user_add_info ui,organization "
					+ "org,usermapping um,userroles ur,practice pr where org.id=um.organizationid and pr.id=um.practiceid and "
					+ "ui.userid=um.userid and ui.userid=ur.userid and ur.roleid='Physician' and org.id='"
					+ orgid
					+ "' "
					+ "and pr.id='"
					+ practiceid
					+ "' and (CONCAT(LOWER(ui.firstname),LOWER(ui.lastname))) like '%"
					+ searchphysician + "%'";
			query = session
					.createSQLQuery(querystr)
					.addScalar("firstname")
					.addScalar("userid")
					.addScalar("organizationName")
					.addScalar("practicename")
					.setResultTransformer(
							Transformers.aliasToBean(SearchPhysicianForm.class));
			searchPhysician = query.list();
			/*
			 * Query q = session.createQuery(query).setParameter("userEmail",
			 * userEmail); if (q.list() != null && !q.list().isEmpty()) {
			 * searchPhysician = q.list(); if (userList != null &&
			 * userList.size() > 0) { registrationForm1VO = (UserVO)
			 * userList.get(0); } }
			 */
		} finally {
			session.flush();
			session.close();

		}
		return searchPhysician;
	}

	@SuppressWarnings("unchecked")
	public List<SearchPhysicianForm> getAllPhysicianDetails(String orgid,
			String practiceid, Map paramsMap) throws DataAccessException {
		int startindex = (Integer) paramsMap.get("iDisplayStart");
		int endindex = (Integer) paramsMap.get("iDisplayLength");
		List<SearchPhysicianForm> searchPhysician = null;
		Query query = null;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String querystr = null;

			logger.info("orgid : " + orgid + " practiceid : " + practiceid);

			if (StringUtils.isEmpty(orgid) && StringUtils.isEmpty(practiceid)) {

				logger.info("all");

				querystr = "select CONCAT(ui.firstname,' ',ui.lastname) as firstname,organizationname,ui.userid,pr.practicename from user_add_info ui,organization "
						+ "org,usermapping um,userroles ur,practice pr where org.id=um.organizationid and pr.id=um.practiceid and "
						+ "ui.userid=um.userid and ui.userid=ur.userid and ur.roleid='Physician' ";

			} else {

				logger.info("second");
				if (paramsMap.get("searchphysician") != null
						&& !paramsMap.get("searchphysician").equals("")) {

					String search = (String) paramsMap.get("searchphysician");

					querystr = "select CONCAT(ui.firstname,' ',ui.lastname) as firstname,organizationname,ui.userid,pr.practicename from user_add_info ui,organization "
							+ "org,usermapping um,userroles ur,practice pr where org.id=um.organizationid and pr.id=um.practiceid and "
							+ "ui.userid=um.userid and ui.userid=ur.userid and ur.roleid='Physician' and org.id='"
							+ orgid
							+ "'"
							+ "and pr.id='"
							+ practiceid
							+ "' and (CONCAT(LOWER(ui.firstname),LOWER(ui.lastname))) like '%"
							+ search + "%'";

				} else {

					querystr = "select CONCAT(ui.firstname,' ',ui.lastname) as firstname,organizationname,ui.userid,pr.practicename from user_add_info ui,organization "
							+ "org,usermapping um,userroles ur,practice pr where org.id=um.organizationid and pr.id=um.practiceid and "
							+ "ui.userid=um.userid and ui.userid=ur.userid and ur.roleid='Physician' and org.id='"
							+ orgid + "'" + "and pr.id='" + practiceid + "'";
				}
			}

			query = session
					.createSQLQuery(querystr)
					.addScalar("firstname")
					.addScalar("userid")
					.addScalar("organizationName")
					.addScalar("practicename")
					.setFirstResult(startindex)
					.setMaxResults(endindex)

					.setResultTransformer(
							Transformers.aliasToBean(SearchPhysicianForm.class));

			searchPhysician = query.list();
			/*
			 * Query q = session.createQuery(query).setParameter("userEmail",
			 * userEmail); if (q.list() != null && !q.list().isEmpty()) {
			 * searchPhysician = q.list(); if (userList != null &&
			 * userList.size() > 0) { registrationForm1VO = (UserVO)
			 * userList.get(0); } }
			 */
		} finally {
			session.flush();
			session.close();

		}
		return searchPhysician;
	}

	@SuppressWarnings("unchecked")
	public List<UserVO> getPatientDetails(String userEmail)
			throws DataAccessException {

		List<UserVO> searchPhysician = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			String query = "from UserVO user where (user.userName = :userEmail)";

			Query q = session.createQuery(query).setParameter("userEmail",
					userEmail);
			if (q.list() != null && !q.list().isEmpty()) {
				searchPhysician = q.list();
				/*
				 * if (userList != null && userList.size() > 0) {
				 * registrationForm1VO = (UserVO) userList.get(0); }
				 */
			}
		} finally {
			session.flush();
			session.close();

		}
		return searchPhysician;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "rawtypes" })
	public boolean checkPasswordExist(int userId, String password) {
		logger.info("UserRegisterationDaoImpl checkPasswordExist() ends.");
		UserVO registrationForm1VO = null;
		boolean existPassword = false;
		List userList = null;
		userList = getHibernateTemplate().find(
				"select rg from UserVO rg where rg.id='" + userId
						+ "' and rg.password='" + password + "'");
		if (userList != null && userList.size() > 0) {
			registrationForm1VO = (UserVO) userList.get(0);
			if (registrationForm1VO != null) {
				existPassword = true;
			}
		}
		logger.info("UserRegisterationDaoImpl checkPasswordExist() ends.");
		return existPassword;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public UserVO checkAnswerCorrect(String mailId, String answer) {
		logger.info("UserRegisterationDaoImpl checkAnswerCorrect() starts.");
		UserVO registrationFormVO = null;
		List<UserVO> userList = null;
		userList = getHibernateTemplate().find(
				"from UserVO user where user.userName='" + mailId
						+ "' and user.securityAnswer ='" + answer + "'");
		if (userList != null && userList.size() > 0) {
			registrationFormVO = userList.get(0);
		}
		logger.info("UserRegisterationDaoImpl checkAnswerCorrect() starts.");
		return registrationFormVO;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<DocumentVO> getDocumentByType(String type) {
		return getHibernateTemplate().find(
				"from DocumentVO doc where doc.type='" + type + "'");
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "rawtypes" })
	public int findEmailExist(String userEmail) {
		int userId = 0;
		String sqlQuery = null;
		List userList = null;
		sqlQuery = "select rg.id from UserVO rg where LOWER(rg.userName) LIKE LOWER('"
				+ userEmail + "')";
		userList = getHibernateTemplate().find(sqlQuery);
		if (userList != null && userList.size() > 0) {
			userId = (Integer) userList.get(0);
		}
		return userId;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public UserVO getUserRegistrationByUserId(int userId) {
		UserVO registrationForm1VO = null;
		List<UserVO> userList = null;
		logger.info("Start of in userregistration");
		userList = getHibernateTemplate().find(
				"from UserVO user where user.id=" + userId);
		if (userList != null && userList.size() > 0) {
			registrationForm1VO = userList.get(0);
		}
		return registrationForm1VO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteDocumentDetailsVOByEmailAndDocId(String emailId,
			Integer docId) {
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			String query = "delete DocumentDetailsVO doc where (doc.userEmail = :email) and (doc.docId = :docId)";
			Query q = session.createQuery(query).setParameter("email", emailId)
					.setParameter("docId", docId);
			q.executeUpdate();
		} finally {
			session.flush();
			session.close();

		}
	}

	@SuppressWarnings({ "rawtypes" })
	public String getUserName(int userId) {
		String userName = null;

		List userList = null;
		String query="select CONCAT(rg.firstname,' ',rg.lastname) as firstname from UserPersonalInfoVO rg where rg.userid='"
				+ userId + "'";
//		System.out.println("getUserName : "+query);
		userList = getHibernateTemplate()
				.find(query);
		if (userList != null && userList.size() > 0) {
			userName = (String) userList.get(0);

		}
		return userName;
	}

	@SuppressWarnings({ "rawtypes" })
	public String getOrganizationName(int orgid) {
		String organizationname = null;

		List userList = null;
		userList = getHibernateTemplate().find(
				"select rg.organizationname from AdminOrganizationVO rg where rg.id='"
						+ orgid + "'");
		if (userList != null && userList.size() > 0) {
			organizationname = (String) userList.get(0);

		}
		return organizationname;
	}

	@SuppressWarnings({ "rawtypes" })
	public Date getDob(int userId) {

		Date dob = null;
		List userList = null;
		userList = getHibernateTemplate().find(
				"select rg.dob from UserPersonalInfoVO rg where rg.userid='"
						+ userId + "'");
		if (userList != null && userList.size() > 0) {
			dob = (Date) userList.get(0);
		}
		logger.info("dob : " + dob);
		return dob;
	}

	@Override
	public int findSsnExist(String ssn) {

		return 0;
	}

	@Override
	public List<RoleVO> getRolesByUserGroupId(String sqlQuery) {

		return null;
	}

	@Override
	public Integer saveUserRegistration(UserVO userDto) {
		logger.info("UserRegistrationDaoImpl saveUserRegistration() starts.");

		Integer id = (Integer) getHibernateTemplate().save(userDto);

		logger.info("UserRegistrationDaoImpl saveUserRegistration() ends.");

		return id;
	}

	@Override
	public List<RoleVO> findRoleVoByRoleId(String roleId) {

		String queryString = "from RoleVO role where role.oid ='" + roleId
				+ "'";
		return getHibernateTemplate().find(queryString);

	}

	@SuppressWarnings("unchecked")
	public List<UserVO> getSearchVisitDetails(String searchDate)
			throws DataAccessException {
		List<UserVO> registrationForm1VO = null;
		List<UserVO> userList = null;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();

		logger.info("userEmail : " + searchDate);

		Date date = CommonUtils.parseDateFromStringForDttComaprison(searchDate);

		logger.info("date : " + date);
		try {
			String query = "from UserVO user where user.dateCreated ='" + date
					+ "'";
			// Query q = session.createQuery(query).setParameter("userEmail",
			// userEmail);
			Query q = session.createQuery(query);
			if (q.list() != null && !q.list().isEmpty()) {
				userList = q.list();
				/*
				 * if (userList != null && userList.size() > 0) {
				 * registrationForm1VO = (UserVO) userList.get(0); }
				 */
			}
		} finally {
			session.flush();
			session.close();

		}
		return userList;
	}

	@SuppressWarnings("unchecked")
	public List<UserVO> getTagVisitDetails(String tag)
			throws DataAccessException {

		List<UserVO> userList = null;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();

		logger.info("tag : " + tag);

		try {
			String query = "from UserVO user where security_answer like '%"
					+ tag + "%'";
			// Query q = session.createQuery(query).setParameter("userEmail",
			// userEmail);
			Query q = session.createQuery(query);
			if (q.list() != null && !q.list().isEmpty()) {
				userList = q.list();
				/*
				 * if (userList != null && userList.size() > 0) {
				 * registrationForm1VO = (UserVO) userList.get(0); }
				 */
			}
		} finally {
			session.flush();
			session.close();

		}
		return userList;
	}

	@Override
	public int svaePhysicianmappingVo(PhysicianMappingVO mappingVo) {

		logger.info("UserRegistrationDaoImpl svaePhysicianmappingVo() starts.");
		Integer id = (Integer) getHibernateTemplate().save(mappingVo);
		logger.info("UserRegistrationDaoImpl svaePhysicianmappingVo() ends.");

		return id;
	}

	@Override
	public int savePatientMappingVo(PatientMappingVO mappingVo) {

		logger.info("UserRegistrationDaoImpl savePatientMappingVo() starts.");
		Integer id = (Integer) getHibernateTemplate().save(mappingVo);
		logger.info("UserRegistrationDaoImpl savePatientMappingVo() ends.");

		return id;
	}

	@Override
	public int savePatientRequestVo(PatientRequestVO mappingVo) {

		logger.info("UserRegistrationDaoImpl savePatientRequestVo() starts.");
		Integer id = (Integer) getHibernateTemplate().save(mappingVo);
		logger.info("UserRegistrationDaoImpl savePatientRequestVo() ends.");

		return id;
	}

	@SuppressWarnings("unchecked")
	public List<UserVO> getKeywordVisitDetails(String keyword)
			throws DataAccessException {

		List<UserVO> userList = null;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();

		logger.info("keyword : " + keyword);

		try {
			String query = "from UserVO user where security_answer like '%"
					+ keyword + "%'";
			// Query q = session.createQuery(query).setParameter("userEmail",
			// userEmail);
			Query q = session.createQuery(query);
			if (q.list() != null && !q.list().isEmpty()) {
				userList = q.list();
				/*
				 * if (userList != null && userList.size() > 0) {
				 * registrationForm1VO = (UserVO) userList.get(0); }
				 */
			}
		} finally {
			session.flush();
			session.close();

		}
		return userList;
	}

	@Override
	public void updatePhysicianmappingVo(PhysicianMappingVO mappingVo) {

		logger.info("UserRegistrationDaoImpl updatePhysicianmappingVo() starts.");

		// getHibernateTemplate().update(mappingVo);

		getSession().update(mappingVo);
		logger.info("UserRegistrationDaoImpl updatePhysicianmappingVo() ends.");

	}

	@Override
	public PhysicianMappingVO findPhysicianMapping(int mappingId) {

		logger.info("UserRegistrationDaoImpl updatePhysicianmappingVo() starts.");
		// PhysicianMappingVO dto =
		// getsession().find(PhysicianMappingVO.class,mappingId);

		PhysicianMappingVO dto = (PhysicianMappingVO) getSession().get(
				PhysicianMappingVO.class, mappingId);
		logger.info("UserRegistrationDaoImpl updatePhysicianmappingVo() ends.");
		return dto;
	}

	@Override
	public boolean checkUserAdditionalInfo(int userid) {

		boolean flag = false;
		Long count = 0L;

		count = (Long) getSession()
				.createQuery(
						"select count(*) from UserVO u,UserPersonalInfoVO up where u.id = up.userid and"
								+ " u.id = :userId")
				.setParameter("userId", userid).uniqueResult();

		if (count.equals(0L)) {

			flag = true;
		} else {

			flag = false;
		}
		return flag;
	}

	@Override
	public int savePatientDocument(PatientDocument documentVO) {

		logger.info("UserRegistrationDaoImpl savePatientDocument() starts.");

		Integer id = (Integer) getHibernateTemplate().save(documentVO);

		logger.info("UserRegistrationDaoImpl savePatientDocument() ends.");

		return id;
	}

	@Override
	public Object[] getPatientVisitRecords(Integer userId) {

		logger.info("UserRegistrationDaoImpl getPatientVisitRecords() starts.");
		Object[] obj = new Object[4];

		obj = (Object[]) getSession()
				.createQuery(
						"select v.dateofvisit,(select count(*) from VisitsVO v where v.patientid=:userid)"
								+ ",v.tag,v.id from VisitsVO v where v.patientid = :userid"
								+ " order by v.id desc")
				.setParameter("userid", userId).setMaxResults(1).uniqueResult();

		/*
		 * Long count = (Long) getSession().createQuery(
		 * "select count(*) from VisitsVO v where v.patientid=:userid")
		 * .setParameter("userid", userId) .uniqueResult();
		 */

		logger.info("UserRegistrationDaoImpl getPatientVisitRecords() ends.");
		return obj;
	}

	@Override
	public Long getTotalUsers(String userType) {

		logger.info("UserRegistrationDaoImpl getTotalUsers() starts.");

		Long count = (Long) getSession()
				.createQuery(
						"select count(*) from UserVO u,UserRoleVO ur where ur.userOid=u.id and ur.roleOid=:userType")
				.setParameter("userType", userType).uniqueResult();

		logger.info("UserRegistrationDaoImpl getTotalUsers() ends.");
		return count;
	}

	@Override
	public void saveAuditTrails(AuditTrailVO dto) {

		logger.info("UserRegistrationDaoImpl saveAuditTrails() starts.");
		getHibernateTemplate().save(dto);
		logger.info("UserRegistrationDaoImpl saveAuditTrails() ends.");
	}

	@Override
	public List<Object[]> getAuditTrailDetails(Integer personId) {

		List<Object[]> obj = null;

		obj = getSession()
				.createQuery(
						"select a.id,a.createdon,a.ipaddress,a.logtext from AuditTrailVO a where a.userid=:userid")
				.setParameter("userid", personId).list();
		return obj;
	}

	@Override
	public boolean checkPracticeAdminUser(int userid) {

		boolean flag = false;
		Long count = 0L;
		logger.info("flag userid : " + userid);
		count = (Long) getSession()
				.createQuery(
						"select count(*) from PracticeUserRoleVO up where  up.userId =:userId")
				.setParameter("userId", userid).uniqueResult();

		if (count.equals(0L)) {

			flag = false;
		} else {

			flag = true;
		}
		System.out.print("flag  :" + flag);
		return flag;
	}

	@Override
	public UserDetails getUserDetilsByUserId(Integer userId) {

		UserDetails details = null;
		Query query = null;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String querystr = "select u.id as userid,u.user_name as email,CONCAT(ad.firstname,' ',ad.lastname) as username,"
					+ "CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ad.dob,ad.ssn,ad.contact_number as contactnumber from user_add_info ad,userinformation u "
					+ "where u.id= " + userId + " and u.id = ad.userid";

			query = session
					.createSQLQuery(querystr)
					.addScalar("userid")
					.addScalar("email")
					.addScalar("username")
					.addScalar("address")
					.addScalar("dob")
					.addScalar("ssn")
					.addScalar("contactnumber")

					.setResultTransformer(
							Transformers.aliasToBean(UserDetails.class));

			details = (UserDetails) query.uniqueResult();

		}

		catch (RuntimeException re) {

			re.printStackTrace();
		} finally {
			session.flush();
			session.close();

		}
		return details;
	}

	@Override
	public Long getOutstandingRequests(Integer providerId) {

		Long id = 0L;
		id = (Long) getSession()
				.createQuery(
						"select count(*) from PatientRequestVO p"
								+ " where p.physicianid =:id and p.mailstatus=0")
				.setParameter("id", providerId).uniqueResult();
		return id;
	}

	@Override
	public Long getAllPhysicianDetailsCOunt(String orgid, String practiceid,
			String searchphysician) {

		List<SearchPhysicianForm> searchPhysician = null;
		Query query = null;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			String querystr = null;

			if (StringUtils.isEmpty(orgid) && StringUtils.isEmpty(practiceid)) {

				querystr = "select CONCAT(ui.firstname,' ',ui.lastname) as firstname,organizationname,ui.userid,pr.practicename from user_add_info ui,organization "
						+ "org,usermapping um,userroles ur,practice pr where org.id=um.organizationid and pr.id=um.practiceid and "
						+ "ui.userid=um.userid and ui.userid=ur.userid and ur.roleid='Physician'";

			} else {
				if (searchphysician != null && !searchphysician.equals("")) {

					querystr = "select CONCAT(ui.firstname,' ',ui.lastname) as firstname,organizationname,ui.userid,pr.practicename from user_add_info ui,organization "
							+ "org,usermapping um,userroles ur,practice pr where org.id=um.organizationid and pr.id=um.practiceid and "
							+ "ui.userid=um.userid and ui.userid=ur.userid and ur.roleid='Physician' and org.id='"
							+ orgid
							+ "' "
							+ "and pr.id='"
							+ practiceid
							+ "' and (CONCAT(LOWER(ui.firstname),LOWER(ui.lastname))) like '%"
							+ searchphysician + "%'";

				} else {

					querystr = "select CONCAT(ui.firstname,' ',ui.lastname) as firstname,organizationname,ui.userid,pr.practicename from user_add_info ui,organization "
							+ "org,usermapping um,userroles ur,practice pr where org.id=um.organizationid and pr.id=um.practiceid and "
							+ "ui.userid=um.userid and ui.userid=ur.userid and ur.roleid='Physician' and org.id='"
							+ orgid + "' " + "and pr.id='" + practiceid + "'";
				}
			}

			query = session
					.createSQLQuery(querystr)
					.addScalar("firstname")
					.addScalar("userid")
					.addScalar("organizationName")
					.addScalar("practicename")
					.setResultTransformer(
							Transformers.aliasToBean(SearchPhysicianForm.class));
			searchPhysician = query.list();

		} finally {
			session.flush();
			session.close();

		}
		return Long.parseLong(searchPhysician.size() + "");

	}

	@Override
	public String getPrivateNoteByVisitAndPatientId(int visitid, Integer userId) {

		String note = null;

		note = (String) getSession()
				.createQuery(
						"select p.note from PatientPrivateNoteVO p where p.visitid = :visitid")
				.setParameter("visitid", visitid)
				// .setParameter("id", userId)
				.setMaxResults(1).uniqueResult();
		if (note == null) {
			note = "---";
		}

		return note;
	}

	@Override
	public Long getTotlPatientsVisits() {

		Long count = null;

		count = (Long) getSession()
				.createQuery(
						"select count(*) from VisitsVO v where v.dateofvisit=:date")
				.setParameter("date", new Date()).uniqueResult();

		if (count == null) {
			count = 0L;
		}
		return count;
	}

	@Override
	public Long getTotlPatientsVisits(Integer userid) {

		Long count = null;

		count = (Long) getSession()
				.createQuery(
						"select count(*) from VisitsVO v where v.dateofvisit<=:date"
								+ " and v.physicianid = :id")
				.setParameter("date", new Date()).setParameter("id", userid)
				.uniqueResult();

		if (count == null) {
			count = 0L;
		}
		return count;
	}

	@Override
	public Long getOutstandingRequestsFromPhysician(Integer providerId) {
		// TODO Auto-generated method stub
		Long id = 0L;
		id = (Long) getSession()
				.createQuery(
						"select count(*) from RequestInfoOfPatientVO p"
								+ " where p.requesttophysician =:id and p.requestStatus!='Approved'")
				.setParameter("id", providerId).uniqueResult();
		return id;
	}

	@Override
	public Long getAllPhysicianDetailsCOuntByPhysicianName(String searchVal) {
		logger.info("User Registration Dao getAllPhysicianDetailsCOuntByPhysicianName()Starts");
		List<Object[]> obj = null;
		try{
		obj = getSession().createQuery("select u.userid,concat(u.firstname,' ',u.lastname)," +
				"a.organizationname,ap.practicename" +
				" from PhysicianMappingVO p,UserPersonalInfoVO u,AdminOrganizationVO a,AdminPracticeVO ap where " +
				"(CONCAT(LOWER(u.firstname),' ',LOWER(u.lastname))) like '%"+ searchVal + "%' and u.userid = p.userid and" +
				" p.organizationid = a.id and p.practiceid = ap.id and ap.status=1 and p.status = 1 and" +
				" a.status = 1")
				.list();
		 } catch (Exception e) {
			 logger.info("Error:"+e.getMessage());
		}
		Long id = 0L;
		
		if(obj!=null){
			id = Long.parseLong(obj.size()+"");
		}
		logger.info("User Registration Dao getAllPhysicianDetailsCOuntByPhysicianName()Ends");
		return id;
	}

	@Override
	public List<Object[]> getAllPhysicianDetailsByPhysicianName(Map map) {
		logger.info("User Registration Dao getAllPhysicianDetailsByPhysicianName()Starts");
		List<Object[]> obj = null;
		int startindex = (Integer) map.get("iDisplayStart");
		int endindex = (Integer) map.get("iDisplayLength");
		try {
		String searchVal = (String) map.get("searchphysician");
		obj = getSession().createQuery("select u.userid,concat(u.firstname,' ',u.lastname)," +
				"a.organizationname,ap.practicename" +
				" from PhysicianMappingVO p,UserPersonalInfoVO u,AdminOrganizationVO a,AdminPracticeVO ap where " +
				"(CONCAT(LOWER(u.firstname),' ',LOWER(u.lastname))) like '%"+ searchVal + "%' and u.userid = p.userid and" +
				" p.organizationid = a.id and p.practiceid = ap.id and ap.status=1 and p.status = 1 and" +
				" a.status = 1")
				.setFirstResult(startindex)
				.setMaxResults(endindex)
				.list();
		} catch (Exception e) {
			logger.info("Error:"+e.getMessage());
		}
		
		logger.info("User Registration Dao getAllPhysicianDetailsByPhysicianName() Ends");
	return obj;	
	}
	
	
	
	public Long updateSignature(Integer userid) {
		// TODO Auto-generated method stub
		logger.info("AdminOrganizationDaoImpl deleteAdminOrganizationFormVO() starts.");
		String enableStatus = "yes";
		String query = null;
		SQLQuery sqlQuery = null;
		Session session = null;
		Long finalresult;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = "update user_add_info set signature='" + enableStatus
					+ "' where userid='" + userid + "'";
			sqlQuery = session.createSQLQuery(query);
			finalresult=(long) sqlQuery.executeUpdate();
		} finally {
			query = null;
			sqlQuery = null;
			if (null != session) {
				session.flush();
				session.close();
			}
		}
		return finalresult;	
		// getHibernateTemplate().delete(adminOrganizationVO);
	}
	
	
	public String checkSignature(Integer userid) {
		// TODO Auto-generated method stub
		logger.info("AdminOrganizationDaoImpl deleteAdminOrganizationFormVO() starts.");
		logger.info("UserRegisterationDaoImpl checkSignature() ends.");
		UserVO registrationForm1VO = null;
		boolean existPassword = false;
		String signature = null;
		
		signature = (String) getSession()
				.createQuery(
						"select signature from UserPersonalInfoVO rg where rg.userid=:userid")
				.setParameter("userid", userid).uniqueResult();
		 	
		logger.info("UserRegisterationDaoImpl checkSignature() ends.");
		
		
		return signature;
		// getHibernateTemplate().delete(adminOrganizationVO);
	}
	
	public Integer checkFileStatus(String filename,Integer userid) {
		// TODO Auto-generated method stub
		
		logger.info("checkFileStatus checkFileStatus() starts.");
		
		UserVO registrationForm1VO = null;
		boolean existPassword = false;
		String signature = null;
		
		System.out.println("filename : "+filename);
		
		String query=null;
		String type="Share";
		String key="Share Request";
		
		Integer userId = 0;
		String sqlQuery = null;
		List orgList = null;
		
		sqlQuery = "select pd.id from PatientDocument pd where pd.patientid = '"+userid+"' and pd.docname = '"+filename+"' and pd.uploadType='"+type+"' and pd.key='"+key+"'";
		System.out.println("sqlQuery : "+sqlQuery);
		orgList = getHibernateTemplate().find(sqlQuery);
		System.out.println("orgList : "+orgList);
		if (orgList != null && orgList.size() > 0) {
			userId = (Integer) orgList.get(0);
		}
				 	
		logger.info("checkFileStatus checkFileStatus() ends.");
		
		
		return userId;
		// getHibernateTemplate().delete(adminOrganizationVO);
	}
	
}
