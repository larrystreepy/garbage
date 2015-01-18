/**
 * 
 */
package com.bluehub.dao.admin;

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

import com.bluehub.bean.admin.SearchPatientForm;
import com.bluehub.bean.admin.SearchPhysicianForm;
import com.bluehub.util.CommonUtils;
import com.bluehub.vo.admin.AdminOrganizationVO;
import com.bluehub.vo.admin.AdminPracticeVO;
import com.bluehub.vo.admin.PhysicianMappingVO;
import com.bluehub.vo.admin.PracticeAdminRoleVO;
import com.bluehub.vo.common.ShareClinicalInfo;


public class AdminOrganizationDaoImpl extends HibernateDaoSupport implements
		AdminOrganizationDao {
	private static Logger logger = Logger
			.getLogger(AdminOrganizationDaoImpl.class);

	/*
	 * @Override public List<AdminOrganizationVO> getAllAdminOrganization() { //
	 * TODO Auto-generated method stub return null; }
	 */

	public void saveAdminOrganization(AdminOrganizationVO adminOrganizationVO) {
		// TODO Auto-generated method stub
		logger.info("AdminOrganizationDaoImpl saveAdminOrganizationFormVO() starts.");
		getHibernateTemplate().save(adminOrganizationVO);
	}

	public List<AdminOrganizationVO> getAdminOrganizaion(Map map) {
		// TODO Auto-generated method stub

		int startindex = (Integer) map.get("iDisplayStart");
		int endindex = (Integer) map.get("iDisplayLength");
		logger.info("getAdminOrganizaion");
		logger.info("AdminOrganizationDaoImpl : getAdminOrganizaion() ==> starts.");
		// String query = "from AdminOrganizationVO where status='1'";
		// Object[] queryParam = { childId, userMail };
		List<AdminOrganizationVO> userList = getSession()
				.createQuery(
						"select a from AdminOrganizationVO a where a.status=1 order by a.id asc")
				.setFirstResult(startindex).setMaxResults(endindex).list();
		return userList;

	}

	public void deleteAdminOrganization(Integer orgid) {
		// TODO Auto-generated method stub
		logger.info("AdminOrganizationDaoImpl deleteAdminOrganizationFormVO() starts.");
		int enableStatus = 0;
		String query = null;
		SQLQuery sqlQuery = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = "update organization set status='" + enableStatus
					+ "' where id='" + orgid + "'";
			sqlQuery = session.createSQLQuery(query);
			sqlQuery.executeUpdate();
		} finally {
			query = null;
			sqlQuery = null;
			if (null != session) {
				session.flush();
				session.close();
			}
		}

		// getHibernateTemplate().delete(adminOrganizationVO);
	}

	public void deleteAdminEncounter(Integer encounterid) {
		// TODO Auto-generated method stub
		logger.info("AdminOrganizationDaoImpl deleteAdminEncounter() starts.");
		int enableStatus = 0;
		String query = null;
		SQLQuery sqlQuery = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = "update encounter set status='" + enableStatus
					+ "' where id='" + encounterid + "'";
			sqlQuery = session.createSQLQuery(query);
			sqlQuery.executeUpdate();
		} finally {
			query = null;
			sqlQuery = null;
			if (null != session) {
				session.flush();
				session.close();
			}
		}

		// getHibernateTemplate().delete(adminOrganizationVO);
	}

	public void updateAdminEncounter(String encounter, Integer encounterid) {
		// TODO Auto-generated method stub
		logger.info("AdminOrganizationDaoImpl updateAdminEncounter() starts.");
		int enableStatus = 0;
		String query = null;
		SQLQuery sqlQuery = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = "update encounter set encounter='" + encounter
					+ "' where id='" + encounterid + "'";
			sqlQuery = session.createSQLQuery(query);
			sqlQuery.executeUpdate();
		} finally {
			query = null;
			sqlQuery = null;
			if (null != session) {
				session.flush();
				session.close();
			}
		}

		// getHibernateTemplate().delete(adminOrganizationVO);
	}

	@Override
	public List<Object[]> getPhysicianMapping(Map paramsMap) {
		// TODO Auto-generated method stub

		logger.info("AdminOrganizationDaoImpl : getPhysicianMapping() ==> starts.");
		// String query = "from PhysicianMappingVO";
		int iDisplayStart = (Integer) paramsMap.get("iDisplayStart");
		int iDisplayLength = (Integer) paramsMap.get("iDisplayLength");

		List<Object[]> mappingList = getSession()
				.createQuery(
						"select map.id,concat(up.firstname,' ',' ',up.lastname),"
								+ "a.organizationname,p.practicename"
								+ " from UserVO u, PhysicianMappingVO map,AdminOrganizationVO a,AdminPracticeVO p,UserPersonalInfoVO up"
								+ " where map.userid = u.id and map.organizationid = a.id and u.id = up.userid and"
								+ " map.practiceid = p.id and map.status ='1' order by map.id desc")
				.setFirstResult(iDisplayStart).setMaxResults(iDisplayLength)
				.list();
		// List<PhysicianMappingVO> mappingList =
		// getHibernateTemplate().find(query);
		return mappingList;
	}

	@Override
	public void deletePhysicianMapping(PhysicianMappingVO mappingVo) {
		// TODO Auto-generated method stub

		logger.info("AdminOrganizationDaoImpl deletePhysicianMapping() starts==>");

		// adminOrganizationVO.setId(id);
		getHibernateTemplate().delete(mappingVo);

		logger.info("AdminOrganizationDaoImpl deletePhysicianMapping() Ends==>");
	}

	@Override
	public List<AdminPracticeVO> getPracticeList(Integer orgid) {
		// TODO Auto-generated method stub
		logger.info("AdminOrganizationDaoImpl getPracticeList() Starts==>");

		String query = "from AdminPracticeVO where status=1 and organizationid='"
				+ orgid + "'";
		List<AdminPracticeVO> practiceList = getHibernateTemplate().find(query);

		logger.info("AdminOrganizationDaoImpl getPracticeList() Ends==>");
		return practiceList;
	}

	@Override
	public List<String> getPhysicianList() {
		// TODO Auto-generated method stub

		List<String> userList = getSession()
				.createQuery(
						"select concat(up.firstname,' ',up.lastname,'_',u.id) from UserVO u,UserRoleVO ur,"
								+ "RoleVO r,UserPersonalInfoVO up"
								+ " where u.id = ur.userOid and u.id = up.userid and ur.userOid = up.userid"
								+ " and ur.roleOid = r.oid and r.oid='Physician'")
				.list();
		return userList;
	}

	public List<AdminOrganizationVO> editAdminOrganization(
			AdminOrganizationVO adminOrganizationVO, int id) {
		// TODO Auto-generated method stub
		logger.info("AdminOrganizationDaoImpl editAdminOrganizationFormVO() starts.");
		// adminOrganizationVO.setId(id);
		List<AdminOrganizationVO> userList = getHibernateTemplate().find(
				"from AdminOrganizationVO a where a.id = ?", id);
		return userList;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public AdminOrganizationVO geAdminOrganizationVOById(int id)
			throws DataAccessException {
		AdminOrganizationVO OrganizationVO = null;
		List<AdminOrganizationVO> orgList = null;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			String query = "from AdminOrganizationVO orgVo where (orgVo.id = :id)";
			Query q = session.createQuery(query).setParameter("id", id);
			if (q.list() != null && !q.list().isEmpty()) {
				orgList = q.list();
				if (orgList != null && orgList.size() > 0) {
					OrganizationVO = (AdminOrganizationVO) orgList.get(0);
				}
			}
		} finally {
			session.flush();
			session.close();

		}
		return OrganizationVO;
	}

	@Override
	public void updateAdminOrganization(AdminOrganizationVO adminOrganizationVO) {
		logger.info("AdminOrganizationVODaoImpl updateAdminOrganization() starts.");
		getHibernateTemplate().update(adminOrganizationVO);
		logger.info("AdminOrganizationVODaoImpl updateAdminOrganization() ends.");
	}

	@SuppressWarnings("unchecked")
	public List<SearchPatientForm> getPatientDetails(String userEmail)
			throws DataAccessException {

		List<SearchPatientForm> searchPhysician = null;
		Query query = null;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String querystr = "select ad.userid,ad.firstname,ur.userid,ur.roleid,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address  from user_add_info ad,userroles ur where ad.userid=ur.userid and ur.roleid='Patient'";
			query = session
					.createSQLQuery(querystr)
					.addScalar("userid")
					.addScalar("firstname")
					.addScalar("dob")
					.addScalar("address")
					.setResultTransformer(
							Transformers.aliasToBean(SearchPatientForm.class));
			searchPhysician = query.list();

		} finally {
			session.flush();
			session.close();

		}
		return searchPhysician;
	}

	@SuppressWarnings("unchecked")
	public List<SearchPatientForm> getPatientDetails(String searchWord,
			String searchVal) throws DataAccessException {

		Query queryy = null;
		List<SearchPatientForm> searchPhysiciann = null;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();

		try {

			// select ad.userid,ad.firstname,ad.dob,ad.address,ui.status from
			// user_add_info ad,userroles ur,userinformation ui where
			// ad.userid=ur.userid and ad.userid=ui.id and ur.roleid='Patient'
			// and ad.firstname like '%s%';

			if (searchVal.equals("1")) {

				queryy = null;
				/*
				 * String querystr =
				 * "select ad.userid,concat(ad.firstname,' ',ad.lastname) as firstname,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ui.status  from user_add_info ad,userroles ur,userinformation ui where ad.userid=ur.userid and ad.userid=ui.id and ur.roleid='Patient' and lower(ad.firstname) like '%"
				 * +searchWord+"%'";
				 */
				String querystr = "select ad.userid, ad.firstname as firstname,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ui.status,ad.lastname  from user_add_info ad,userroles ur,userinformation ui where ad.userid=ur.userid and ad.userid=ui.id and ur.roleid='Patient' and (CONCAT(LOWER(ad.firstname),LOWER(ad.lastname))) like '%"
						+ searchWord + "%'";
				queryy = session
						.createSQLQuery(querystr)
						.addScalar("userid")
						.addScalar("firstname")
						.addScalar("dob")
						.addScalar("address")
						.addScalar("status")
						.addScalar("lastname")
						.setResultTransformer(
								Transformers
										.aliasToBean(SearchPatientForm.class));
				searchPhysiciann = queryy.list();

				// query =
				// "select user.id,user.firstname,user.dob,user.address from UserPersonalInfoVO user,UserRoleVO role where user.userid=role.userOid and role.roleOid='Patient' and user.firstname like '%"+searchWord+"%' ";
			}
			if (searchVal.equals("3")) {

				queryy = null;
				String querystr = "select ad.userid,ad.firstname as firstname,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ui.status,ad.lastname from user_add_info ad,userroles ur,userinformation ui where ad.userid=ur.userid and ad.userid=ui.id and ur.roleid='Patient' and (CONCAT(LOWER(ad.firstname),LOWER(ad.lastname))) like '%"
						+ searchWord + "%'";
				queryy = session
						.createSQLQuery(querystr)
						.addScalar("userid")
						.addScalar("firstname")
						.addScalar("dob")
						.addScalar("address")
						.addScalar("status")
						.addScalar("lastname")
						.setResultTransformer(
								Transformers
										.aliasToBean(SearchPatientForm.class));
				searchPhysiciann = queryy.list();

				// query =
				// "from UserPersonalInfoVO user where user.lastname like '%"+searchWord+"%' ";
			}
			if (searchVal.equals("4")) {

				queryy = null;
				Date dated = CommonUtils
						.parseDateFromStringForDttComaprison(searchWord);
				String querystr = "select ad.userid,ad.firstname as firstname,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ui.status,ad.lastname  from user_add_info ad,userroles ur,userinformation ui where ad.userid=ur.userid and ad.userid=ui.id and ur.roleid='Patient' and ad.dob='"
						+ dated + "'";
				queryy = session
						.createSQLQuery(querystr)
						.addScalar("userid")
						.addScalar("firstname")
						.addScalar("dob")
						.addScalar("address")
						.addScalar("status")
						.addScalar("lastname")
						.setResultTransformer(
								Transformers
										.aliasToBean(SearchPatientForm.class));
				searchPhysiciann = queryy.list();

				// Date
				// dated=CommonUtils.parseDateFromStringForDttComaprison(searchWord);
				// query =
				// "from UserPersonalInfoVO user where user.dob = '"+dated+"' ";
			}
			if (searchVal.equals("5")) {

				queryy = null;
				String querystr = "select ad.userid,ad.firstname as firstname,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ui.status,ad.lastname  from user_add_info ad,userroles ur,userinformation ui where ad.userid=ur.userid and ad.userid=ui.id and ur.roleid='Patient' and ad.ssn like '%"
						+ searchWord + "%'";
				queryy = session
						.createSQLQuery(querystr)
						.addScalar("userid")
						.addScalar("firstname")
						.addScalar("dob")
						.addScalar("address")
						.addScalar("status")
						.addScalar("lastname")
						.setResultTransformer(
								Transformers
										.aliasToBean(SearchPatientForm.class));
				searchPhysiciann = queryy.list();

				// query =
				// "from UserPersonalInfoVO user where user.ssn like '%"+searchWord+"%' ";
			}
			if (searchVal.equals("6")) {

				queryy = null;
				String querystr = "select ad.userid,ad.firstname as firstname,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ui.status,ad.lastname "
						+ " from user_add_info ad,userroles ur,userinformation ui "
						+ "where ad.userid=ur.userid and ad.userid=ui.id and ur.roleid='Patient'";
				queryy = session
						.createSQLQuery(querystr)
						.addScalar("userid")
						.addScalar("firstname")
						.addScalar("dob")
						.addScalar("address")
						.addScalar("status")
						.addScalar("lastname")
						.setResultTransformer(
								Transformers
										.aliasToBean(SearchPatientForm.class));
				searchPhysiciann = queryy.list();

				// query =
				// "from UserPersonalInfoVO user where user.ssn like '%"+searchWord+"%' ";
			}

		} finally {
			session.flush();
			session.close();

		}
		return searchPhysiciann;
	}

	@Override
	public void setStatus(String userId) {
		int enableStatus = 1;
		int SelectId = Integer.parseInt(userId);
		String query = null;
		SQLQuery sqlQuery = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = "update userinformation set status='" + enableStatus
					+ "' where id='" + SelectId + "'";
			sqlQuery = session.createSQLQuery(query);
			sqlQuery.executeUpdate();
		} finally {
			query = null;
			sqlQuery = null;
			if (null != session) {
				session.flush();
				session.close();
			}
		}
	}

	@Override
	public void setStatusDisable(String userId) {
		int disableStatus = 0;
		int SelectId = Integer.parseInt(userId);
		String query = null;
		SQLQuery sqlQuery = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = "update userinformation set status='" + disableStatus
					+ "' where id='" + SelectId + "'";
			sqlQuery = session.createSQLQuery(query);
			sqlQuery.executeUpdate();
		} finally {
			query = null;
			sqlQuery = null;
			if (null != session) {
				session.flush();
				session.close();
			}
		}
	}

	@Override
	public List<Object[]> findPhysicianMapping(Integer mappingId) {
		// TODO Auto-generated method stub
		List<Object[]> dto = null;

		dto = (List<Object[]>) getSession()
				.createQuery(
						"select map.id,concat(up.firstname,' ',up.lastname,'_',u.id),map.organizationid,map.practiceid"
								+ " from PhysicianMappingVO map,UserVO u,UserPersonalInfoVO up where map.userid = u.id and map.id =:mappingId and"
								+ " u.id = up.userid and up.userid = map.userid")
				.setParameter("mappingId", mappingId).setMaxResults(1).list();

		// dto = (PhysicianMappingVO) getSession().get(PhysicianMappingVO.class,
		// mappingId);

		return dto;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "rawtypes" })
	public int findOrganizationNameExist(String name) {
		int userId = 0;
		String sqlQuery = null;
		List orgList = null;
		sqlQuery = "select rg.id from AdminOrganizationVO rg where rg.status=1 and LOWER(rg.organizationname) LIKE LOWER('"
				+ name + "')";
		orgList = getHibernateTemplate().find(sqlQuery);
		if (orgList != null && orgList.size() > 0) {
			userId = (Integer) orgList.get(0);
		}
		return userId;
	}

	@SuppressWarnings({ "rawtypes" })
	public int findPracticeNameExist(String name,Integer orgId) {
		int userId = 0;
		String sqlQuery = null;
		List orgList = null;
		sqlQuery = "select rg.id from AdminPracticeVO rg where rg.status=1 and rg.organizationid = '"+orgId+"' " +
				"and LOWER(rg.practicename) LIKE LOWER('"
				+ name + "')";
		orgList = getHibernateTemplate().find(sqlQuery);
		if (orgList != null && orgList.size() > 0) {
			userId = (Integer) orgList.get(0);
		}
		return userId;
	}

	//
	@SuppressWarnings("unchecked")
	public List<SearchPhysicianForm> getPhysicianDetails(String name,
			String orgName, String practName) throws DataAccessException {

		Query query = null;
		List<SearchPhysicianForm> searchPhysician = null;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		String querystr = null;
		try {

			if (StringUtils.isEmpty(orgName) && StringUtils.isEmpty(practName)) {

				querystr = "select CONCAT(ui.firstname,' ',ui.lastname) as firstname,ui.userid,org.organizationname,pr.practicename,uin.status,ur.roleid as role from "
						+ "user_add_info ui,organization org,usermapping um,userroles ur,practice pr,userinformation uin "
						+ "where org.id=um.organizationid and pr.id=um.practiceid and ui.userid=um.userid and ui.userid=ur.userid and ur.roleid='Physician' and "
						+ "uin.id=ui.userid order by um.id asc";

			} else {
				if (!name.equals("")) {

					querystr = "select CONCAT(ui.firstname,' ',ui.lastname) as firstname,ui.userid,org.organizationname,pr.practicename,uin.status,ur.roleid as role "
							+ "from user_add_info ui,organization org,usermapping um,userroles ur,practice pr,userinformation uin "
							+ "where org.id=um.organizationid and ui.userid=um.userid and ui.userid=ur.userid and ur.roleid='Physician' "
							+ "and uin.id=ui.userid and org.id='"
							+ orgName
							+ "' and pr.id='"
							+ practName
							+ "' and (CONCAT(LOWER(ui.firstname),LOWER(ui.lastname))) like '%"
							+ name + "%' order by um.id asc";
				} else {

					querystr = "select CONCAT(ui.firstname,' ',ui.lastname) as firstname,ui.userid,org.organizationname,pr.practicename,uin.status,ur.roleid as role from "
							+ "user_add_info ui,organization org,usermapping um,userroles ur,practice pr,userinformation uin "
							+ "where org.id=um.organizationid and ui.userid=um.userid and ui.userid=ur.userid and ur.roleid='Physician' and "
							+ "uin.id=ui.userid and org.id='"
							+ orgName
							+ "' and pr.id='"
							+ practName
							+ "' order by um.id asc";
				}
			}

			query = session
					.createSQLQuery(querystr)
					.addScalar("firstname")
					.addScalar("userid")
					.addScalar("organizationName")
					.addScalar("practicename")
					.addScalar("status")
					.addScalar("role")
					.setResultTransformer(
							Transformers.aliasToBean(SearchPhysicianForm.class));
			searchPhysician = query.list();

			// query =
			// "select user.id,user.firstname,user.dob,user.address from UserPersonalInfoVO user,UserRoleVO role where user.userid=role.userOid and role.roleOid='Patient' and user.firstname like '%"+searchWord+"%' ";

		} finally {
			session.flush();
			session.close();
		}
		return searchPhysician;
	}

	@Override
	public void setPhysicianPracticeAdminStatus(String userId) {
		int disableStatus = 0;
		int SelectId = Integer.parseInt(userId);
		String query = null;
		SQLQuery sqlQuery = null;
		Session session = null;

		PracticeAdminRoleVO practiceAdminRoleVO1 = new PracticeAdminRoleVO();
		PracticeAdminRoleVO practiceAdminRoleVO = new PracticeAdminRoleVO();
		try {

			practiceAdminRoleVO = (PracticeAdminRoleVO) getSession()
					.createQuery(
							"select orgVo from PracticeAdminRoleVO orgVo where orgVo.userid = :id")
					.setParameter("id", SelectId).setMaxResults(1)
					.uniqueResult();

			if (practiceAdminRoleVO == null) {
				session = getHibernateTemplate().getSessionFactory()
						.openSession();
				practiceAdminRoleVO1.setUserid(Integer.parseInt(userId));
				practiceAdminRoleVO1.setRoleid("PracticeAdmin");
				session.save(practiceAdminRoleVO1);
			}

		} finally {
			query = null;
			sqlQuery = null;
			if (null != session) {
				session.flush();
				session.close();
			}
		}
	}

	@Override
	public void removePhysicianPracticeAdminStatus(String userId) {

		int SelectId = Integer.parseInt(userId);

		SQLQuery sqlQuery = null;
		Session session = null;

		PracticeAdminRoleVO practiceAdminRoleVO = new PracticeAdminRoleVO();
		try {

			practiceAdminRoleVO = (PracticeAdminRoleVO) getSession()
					.createQuery(
							"select orgVo from PracticeAdminRoleVO orgVo where orgVo.userid = :id")
					.setParameter("id", SelectId).setMaxResults(1)
					.uniqueResult();

			if (practiceAdminRoleVO != null) {

				getHibernateTemplate().delete(practiceAdminRoleVO);

			}

		} finally {

			if (null != session) {
				session.flush();
				session.close();
			}
		}
	}

	@Override
	public Integer getPhysicianMappingCount() {
		// TODO Auto-generated method stub

		Long count = (Long) getSession()
				.createQuery(
						"select count(*) from UserVO u, PhysicianMappingVO map,AdminOrganizationVO a,AdminPracticeVO p,UserPersonalInfoVO up"
								+ " where map.userid = u.id and map.organizationid = a.id and u.id = up.userid and"
								+ " map.practiceid = p.id and map.status ='1'")
				.uniqueResult();
		//
		// Long count = (Long)
		// getSession().createQuery("select count(*) from PhysicianMappingVO v where v.status =1 ")
		// .uniqueResult();
		return count.intValue();
	}

	@Override
	public String getUserRoleByUserId(Integer userid) {
		// TODO Auto-generated method stub
		String role = (String) getSession()
				.createQuery(
						"select p.roleid from PracticeAdminRoleVO p"
								+ " where p.userid=:userid")
				.setParameter("userid", userid).setMaxResults(1).uniqueResult();

		return role;
	}

	@Override
	public Integer getPatientFaxRequestCount(Integer userId) {
		// TODO Auto-generated method stub

		Long count = (Long) getSession()
				.createQuery(
						"select count(*) from FaxVo f "
								+ " where f.patientid = "+ userId )
				.uniqueResult();
		
		return count.intValue();
	}
	
	@Override
	public List<ShareClinicalInfo> getListOfPatientShareVo(Integer userId) {
		// TODO Auto-generated method stub
		List<ShareClinicalInfo> list = getSession()
				.createQuery(
						"select dto from ShareClinicalInfo dto where dto.shareTo.id = :id"
								+ " and dto.status=1 and dto.requestStatus!='New patient share'")
				.setParameter("id", userId).list();

		return list;
	}

	@Override
	public Long getAdminOrganizaionCount(Map map) {
		// TODO Auto-generated method stub
		List<AdminOrganizationVO> userList = getSession().createQuery(
				"select a from AdminOrganizationVO a where a.status=1")

		.list();
		Long count = 0L;
		if (userList != null) {

			count = Long.parseLong(userList.size() + "");
		}

		return count;
	}

	@Override
	public List<AdminOrganizationVO> getAdminOrganizaions() {
		List<AdminOrganizationVO> userList = getSession().createQuery(
				"select a from AdminOrganizationVO a where a.status=1")

		.list();
		return userList;
	}

}
