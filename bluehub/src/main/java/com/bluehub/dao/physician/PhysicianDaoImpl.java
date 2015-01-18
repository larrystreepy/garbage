package com.bluehub.dao.physician;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bluehub.bean.admin.SearchPatientForm;
import com.bluehub.util.CommonUtils;
import com.bluehub.vo.admin.FaxVo;
import com.bluehub.vo.common.EncounterVO;
import com.bluehub.vo.common.PatientPrivateNoteVO;
import com.bluehub.vo.common.RequestInfoOfPatientVO;
import com.bluehub.vo.common.ShareClinicalInfo;
import com.bluehub.vo.physician.UserPersonalInfoVO;
import com.bluehub.vo.physician.VisitsVO;
import com.bluehub.vo.user.PatientRequestVO;

@Transactional(readOnly = true)
public class PhysicianDaoImpl extends HibernateDaoSupport implements
		PhysicianDao {

	@Override
	public int savePhysicianVisit(VisitsVO physicianDto) {
		logger.info("PhysicianDaoImpl savePhysicianVisit() starts.");
		Integer id = (Integer) getHibernateTemplate().save(physicianDto);
		logger.info("PhysicianDaoImpl savePhysicianVisit() ends.");

		return id;
	}

	public void savePhysicianPersonalDetails(
			UserPersonalInfoVO physicianPersonalInfoDto) {
		logger.info("PhysicianDaoImpl savePhysicianPersonalDetails() starts.");
		getHibernateTemplate().save(physicianPersonalInfoDto);
		logger.info("PhysicianDaoImpl savePhysicianPersonalDetails() ends.");
	}

	public void savePatientPersonalDetails(
			UserPersonalInfoVO userPersonalInfoDto) {
		logger.info("PhysicianDaoImpl savePhysicianVisit() starts.");
		getHibernateTemplate().save(userPersonalInfoDto);
		logger.info("PhysicianDaoImpl savePhysicianVisit() ends.");
	}

	/*
	 * @SuppressWarnings("unchecked") public List<UserPersonalInfoVO>
	 * getPatientDetails(String userEmail) throws DataAccessException {
	 * 
	 * List<UserPersonalInfoVO> searchPhysician = null; Query query=null;
	 * Session session =
	 * getHibernateTemplate().getSessionFactory().openSession(); try {
	 * 
	 * String query =
	 * "select concat(u.userName,'_',u.id) from UserVO u,UserRoleVO ur,RoleVO r where u.id = ur.userOid"
	 * + " and ur.roleOid = r.oid and r.oid='Patient'"; //String query =
	 * "from UserPersonalInfoVO where ";
	 * 
	 * String querystr =
	 * "select ad.userid,ad.firstname,ur.userid,ur.roleid,ad.dob,ad.address  from user_add_info ad,userroles ur where ad.userid=ur.userid and ur.roleid='Physician'"
	 * ; query =
	 * session.createSQLQuery(querystr).addScalar("userid").addScalar("firstname"
	 * ).addScalar("dob").addScalar("address")
	 * .setResultTransformer(Transformers
	 * .aliasToBean(SearchPhysicianForm.class)); searchPhysician = query.list();
	 * //Query q = session.createQuery(query); if (q.list() != null &&
	 * !q.list().isEmpty()) { searchPhysician = q.list(); if (userList != null
	 * && userList.size() > 0) { registrationForm1VO = (UserVO) userList.get(0);
	 * } } } finally { session.flush(); session.close();
	 * 
	 * } return searchPhysician; }
	 */

	@SuppressWarnings({ "rawtypes" })
	public int findSsnExist(String ssn) {
		logger.info("ssn " + ssn);
		int userId = 0;
		String sqlQuery = null;
		List userList = null;
		sqlQuery = "select up.id from UserPersonalInfoVO up where up.ssn ='"
				+ ssn + "'";
		userList = getHibernateTemplate().find(sqlQuery);
		if (userList != null && userList.size() > 0) {
			userId = (Integer) userList.get(0);
		}
		return userId;
	}

	@SuppressWarnings({ "rawtypes" })
	public int findOrgId(Integer physicianId) {
		int orgId = 0;
		String sqlQuery = null;
		List userList = null;
		sqlQuery = "select um.organizationid from UserMappingVO um where um.userid ='"
				+ physicianId + "'";
		userList = getHibernateTemplate().find(sqlQuery);
		if (userList != null && userList.size() > 0) {
			orgId = (Integer) userList.get(0);
		}
		return orgId;
	}

	@SuppressWarnings("unchecked")
	public List<SearchPatientForm> getAllPatientDetails()
			throws DataAccessException {

		List<SearchPatientForm> searchPhysician = null;
		Query query = null;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String querystr = "select ad.userid,CONCAT(ad.firstname,' ',ad.lastname) as firstname,ur.userid,ur.roleid,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ad.ssn  from user_add_info ad,userroles ur where ad.userid=ur.userid and ur.roleid='Patient'";
			query = session
					.createSQLQuery(querystr)
					.addScalar("userid")
					.addScalar("firstname")
					.addScalar("dob")
					.addScalar("address")
					.addScalar("ssn")
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
	public List<SearchPatientForm> getPatientDetails(String searchPatient,
			Map map) throws DataAccessException {

		int startIndex = (Integer) map.get("iDisplayStart");
		int endIndex = (Integer) map.get("iDisplayLength");
		List<SearchPatientForm> searchPhysician = null;
		Query query = null;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			String querystr = null;
			Integer orgid = (Integer) map.get("orgid");
			// and (CONCAT(LOWER(ui.firstname),LOWER(ui.lastname)))
			/*
			 * if(searchPatient!=null && !searchPatient.equals("")){
			 * 
			 * querystr =
			 * "select ad.userid,CONCAT(ad.firstname,' ',ad.lastname) as firstname,ur.userid,ur.roleid,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ad.lastname,ad.ssn,"
			 * +
			 * "ad.contact_number  from user_add_info ad,userroles ur where ad.userid=ur.userid and ur.roleid='Patient' and "
			 * + "(CONCAT(LOWER(ad.firstname),LOWER(ad.lastname))) like '%"+
			 * searchPatient+"%'";
			 * 
			 * }else{
			 * 
			 * 
			 * }
			 * 
			 * querystr =
			 * "select ad.userid,CONCAT(ad.firstname,' ',ad.lastname) as firstname,ur.userid,ur.roleid,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ad.lastname,ad.ssn,"
			 * +
			 * "ad.contact_number  from user_add_info ad,userroles ur where ad.userid=ur.userid and ur.roleid='Patient'"
			 * ;
			 * 
			 * }
			 */

			if (orgid.equals(-1)) {
				if (searchPatient != null && !searchPatient.equals("")) {

					querystr = "select ad.userid,CONCAT(ad.firstname,' ',ad.lastname) as firstname,ur.userid,ur.roleid,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ad.lastname,ad.ssn,"
							+ "ad.contact_number  from user_add_info ad,userroles ur where ad.userid=ur.userid and ur.roleid='Patient' and "
							+ "(CONCAT(LOWER(ad.firstname),LOWER(ad.lastname))) like '%"
							+ searchPatient + "%'";

				} else {

					querystr = "select ad.userid,CONCAT(ad.firstname,' ',ad.lastname) as firstname,ur.userid,ur.roleid,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ad.lastname,ad.ssn,"
							+ "ad.contact_number  from user_add_info ad,userroles ur where ad.userid=ur.userid and ur.roleid='Patient'";

				}
			} else {
				if (searchPatient != null && !searchPatient.equals("")) {

					querystr = "select ad.userid,CONCAT(ad.firstname,' ',ad.lastname) as firstname,ur.userid,ur.roleid,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ad.lastname,ad.ssn,"
							+ "ad.contact_number  from user_add_info ad,userroles ur,patientmapping pm where pm.userid=ad.userid and pm.organizationid='"
							+ orgid
							+ "' and ad.userid=ur.userid and ur.roleid='Patient' and "
							+ "(CONCAT(LOWER(ad.firstname),LOWER(ad.lastname))) like '%"
							+ searchPatient + "%'";
				} else {

					/*
					 * querystr =
					 * "select ad.userid,CONCAT(ad.firstname,' ',ad.lastname) as firstname,ur.userid,ur.roleid,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ad.lastname,ad.ssn,"
					 * +
					 * "ad.contact_number  from user_add_info ad,userroles ur where ad.userid=ur.userid and ur.roleid='Patient'"
					 * ;
					 */

					querystr = "select ad.userid,CONCAT(ad.firstname,' ',ad.lastname) as firstname,ur.userid,ur.roleid,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ad.lastname,ad.ssn,"
							+ "ad.contact_number  from user_add_info ad,userroles ur,patientmapping pm where pm.userid=ad.userid and pm.organizationid='"
							+ orgid
							+ "' and "
							+ "ad.userid=ur.userid and ur.roleid='Patient'";

				}
			}

			query = session
					.createSQLQuery(querystr)
					.addScalar("userid")
					.addScalar("firstname")
					.addScalar("dob")
					.addScalar("address")
					.addScalar("lastname")
					.addScalar("ssn")
					.addScalar("contact_number")
					.setFetchSize(startIndex)
					.setMaxResults(endIndex)
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
	public List<VisitsVO> getSearchVisitRecords(String searchDate,
			Integer userId, String role, Map map) throws DataAccessException {

		int startIndex = (Integer) map.get("iDisplayStart");
		int endIndex = (Integer) map.get("iDisplayLength");
		List<VisitsVO> searchPhysician = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			Date date = CommonUtils
					.parseDateFromStringForDttComaprison(searchDate);

			logger.info("date : " + date);

			String query = null;

			if (StringUtils.isNotBlank(role)
					&& StringUtils.equalsIgnoreCase(role, "Physician")) {
				query = "from VisitsVO where physicianid='" + userId
						+ "' and dateofvisit ='" + date + "' ";
			} else if (StringUtils.isNotBlank(role)
					&& StringUtils.equalsIgnoreCase(role, "Patient")) {
				query = "from VisitsVO where patientid='" + userId
						+ "'  and dateofvisit ='" + date + "'";
			} else {
				query = "from VisitsVO where dateofvisit ='" + date + "'";
			}

			/*
			 * String query =
			 * "select ui.user_name,vi.dateofvisit,vi.reasonforvisit from userinformation ui,visitinformation vi where ui.id=vi.patientid and vi.dateofvisit ='"
			 * + date + "'"; Query q =
			 * session.createSQLQuery(query).addScalar("user_name"
			 * ).addScalar("dateofvisit").addScalar("reasonforvisit")
			 * .setResultTransformer
			 * (Transformers.aliasToBean(AdminSearchDateForm.class));
			 */
			// searchPhysician = query.list();
			Query q = session.createQuery(query).setFirstResult(startIndex)
					.setMaxResults(endIndex);
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

	@SuppressWarnings("unchecked")
	public List<Object[]> getPatientRequest(Integer userId,
			String role, Map map) throws DataAccessException {
		
		List<Object[]> list = new ArrayList<Object[]>();

		int startIndex = (Integer) map.get("iDisplayStart");
		int endIndex = (Integer) map.get("iDisplayLength");
		List<PatientRequestVO> searchPhysician = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String query = null;

			if (StringUtils.isNotBlank(role)
					&& StringUtils.equalsIgnoreCase(role, "Physician")) {
				query = "from PatientRequestVO where physicianid='" + userId
						+ "'";
				if(!map.get("searchVal").equals("NO")){
					
					list = (List<Object[]>) getSession().createQuery("select p.id,p.patientid,p.mailstatus from PatientRequestVO p," +
							"UserPersonalInfoVO u where p.patientid=u.userid and " +
							"(CONCAT(LOWER(u.firstname),' ',LOWER(u.lastname))) like :searchVal" +
							" and physicianid=:userId")
							.setParameter("searchVal", "%"+map.get("searchVal")+"%")
							.setParameter("userId", userId)
							.setFirstResult(startIndex)
							.setMaxResults(endIndex)
							.list();
					
//					query = "select p.id,p.patientid,p.mailstatus from PatientRequestVO p," +
//							"UserPersonalInfoVO u where p.patientid=u.userid and " +
//							"(CONCAT(LOWER(u.firstname),LOWER(u.lastname))) like :searchVal" +
//							" and physicianid=:userId";
//					
				}else{
					
					list = getSession().createQuery("select p.id,p.patientid,p.mailstatus from PatientRequestVO p," +
							"UserPersonalInfoVO u where p.patientid=u.userid "+
							" and physicianid=:userId")
							.setParameter("userId", userId)
							.setFirstResult(startIndex)
							.setMaxResults(endIndex)
							.list();
					
//					query = "select p.id,p.patientid,p.mailstatus from PatientRequestVO p," +
//							"UserPersonalInfoVO u where p.patientid=u.userid "+
//							" and physicianid=:userId";
					
				}
				
			}
			
			
//			
//			Query q = session.createQuery(query).setFirstResult(startIndex)
//					.setMaxResults(endIndex);
//			if (q.list() != null && !q.list().isEmpty()) {
//				searchPhysician = q.list();
				/*
				 * if (userList != null && userList.size() > 0) {
				 * registrationForm1VO = (UserVO) userList.get(0); }
				 */
//			}
		} finally {
			session.flush();
			session.close();

		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<VisitsVO> getAllList(Integer userId, String role, Map map)
			throws DataAccessException {

		List<VisitsVO> searchPhysician = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			int startindex = (Integer) map.get("iDisplayStart");
			int endindex = (Integer) map.get("iDisplayLength");

			String query = null;
			if (StringUtils.isNotEmpty(role)
					&& StringUtils.equalsIgnoreCase(role, "Physician")) {
				query = "from VisitsVO where  physicianid ='" + userId + "'";
			} else {
				query = "from VisitsVO where  patientid ='" + userId + "'";
			}

			Query q = session.createQuery(query).setFirstResult(startindex)
					.setMaxResults(endindex);
			if (q.list() != null && !q.list().isEmpty()) {
				searchPhysician = q.list();
			}
		} finally {
			session.flush();
			session.close();

		}
		return searchPhysician;
	}

	@Override
	public Long getAllListCount(Integer userId, String role, Map map) {
		// TODO Auto-generated method stub
		List<VisitsVO> searchPhysician = null;
		Long count = 0L;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			String query = null;
			if (StringUtils.isNotEmpty(role)
					&& StringUtils.equalsIgnoreCase(role, "Physician")) {
				query = "from VisitsVO where  physicianid ='" + userId + "'";
			} else {
				query = "from VisitsVO where  patientid ='" + userId + "'";
			}
			Query q = session.createQuery(query);
			if (q.list() != null && !q.list().isEmpty()) {
				searchPhysician = q.list();
				count = Long.parseLong(searchPhysician.size() + "");
			}
		} finally {
			session.flush();
			session.close();

		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<EncounterVO> getEncountersDetails(Integer visitid)
			throws DataAccessException {

		List<EncounterVO> searchPhysician = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String query = "from EncounterVO where status='1' and visitid ='"
					+ visitid + "'";

			Query q = session.createQuery(query).setMaxResults(100)
					.setFirstResult(0);
			if (q.list() != null && !q.list().isEmpty()) {
				searchPhysician = q.list();
			}
		} finally {
			session.flush();
			session.close();

		}
		return searchPhysician;
	}

	@SuppressWarnings("unchecked")
	public List<EncounterVO> editEncountersDetails(Integer encounterid)
			throws DataAccessException {

		List<EncounterVO> searchPhysician = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String query = "from EncounterVO where status='1' and id ='"
					+ encounterid + "'";

			Query q = session.createQuery(query).setMaxResults(100)
					.setFirstResult(0);
			if (q.list() != null && !q.list().isEmpty()) {
				searchPhysician = q.list();
			}
		} finally {
			session.flush();
			session.close();

		}
		return searchPhysician;
	}

	/*
	 * @SuppressWarnings("unchecked") public List<VisitsVO>
	 * getSearchPhysicianVisitRecords(String physicianid,Map map) throws
	 * DataAccessException {
	 * 
	 * List<VisitsVO> searchPhysician = null;
	 * 
	 * Session session =
	 * getHibernateTemplate().getSessionFactory().openSession();
	 * 
	 * int startindex = (Integer) map.get("iDisplayStart"); int endindex =
	 * (Integer) map.get("iDisplayLength");
	 * 
	 * try {
	 * 
	 * String query = "from VisitsVO user where user.physicianid ='" +
	 * physicianid + "'";
	 * 
	 * String query =
	 * "select ui.user_name,vi.dateofvisit,vi.reasonforvisit from userinformation ui,visitinformation vi where ui.id=vi.patientid and vi.dateofvisit ='"
	 * + date + "'"; Query q =
	 * session.createSQLQuery(query).addScalar("user_name"
	 * ).addScalar("dateofvisit").addScalar("reasonforvisit")
	 * .setResultTransformer
	 * (Transformers.aliasToBean(AdminSearchDateForm.class)); //searchPhysician
	 * = query.list(); Query q = session.createQuery(query); if (q.list() !=
	 * null && !q.list().isEmpty()) { searchPhysician = q.list(); if (userList
	 * != null && userList.size() > 0) { registrationForm1VO = (UserVO)
	 * userList.get(0); } } } finally { session.flush(); session.close();
	 * 
	 * } return searchPhysician; }
	 */

	@SuppressWarnings("unchecked")
	public List<VisitsVO> getSearchPhysicianVisitRecords(String physicianid,
			Map map, Integer userId, String role) throws DataAccessException {

		List<VisitsVO> searchPhysician = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();

		String query = null;

		int startindex = (Integer) map.get("iDisplayStart");
		int endindex = (Integer) map.get("iDisplayLength");
		try {
			if (StringUtils.isNotBlank(role)
					&& StringUtils.equalsIgnoreCase(role, "Physician")) {
				query = "from VisitsVO user where user.physicianid ='"
						+ physicianid + "'";
			} else if (StringUtils.isNotBlank(role)
					&& StringUtils.equalsIgnoreCase(role, "Patient")) {
				query = "from VisitsVO user where user.patientid='" + userId
						+ "' and  user.physicianid ='" + physicianid + "'";
			} else {
				query = "from VisitsVO user where user.physicianid ='"
						+ physicianid + "'";
			}

			/*
			 * String query =
			 * "select ui.user_name,vi.dateofvisit,vi.reasonforvisit from userinformation ui,visitinformation vi where ui.id=vi.patientid and vi.dateofvisit ='"
			 * + date + "'"; Query q =
			 * session.createSQLQuery(query).addScalar("user_name"
			 * ).addScalar("dateofvisit").addScalar("reasonforvisit")
			 * .setResultTransformer
			 * (Transformers.aliasToBean(AdminSearchDateForm.class));
			 */
			// searchPhysician = query.list();
			Query q = session.createQuery(query).setFirstResult(startindex)
					.setMaxResults(endindex);
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

	@SuppressWarnings("unchecked")
	public List<VisitsVO> getSearchPatientVisitRecords(String patientid, Map map)
			throws DataAccessException {

		int startIndex = (Integer) map.get("iDisplayStart");
		int endIndex = (Integer) map.get("iDisplayLength");
		List<VisitsVO> searchPhysician = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String query = "from VisitsVO user where user.patientid ='"
					+ patientid + "'";
			Query q = session.createQuery(query).setFirstResult(startIndex)
					.setMaxResults(endIndex);
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

	@SuppressWarnings("unchecked")
	public List<VisitsVO> getTagVisitDetails(String tag, Map map)
			throws DataAccessException {
		int startIndex = (Integer) map.get("iDisplayStart");
		int endIndex = (Integer) map.get("iDisplayLength");
		List<VisitsVO> searchPhysician = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String query = "from VisitsVO user where tag like '%" + tag + "%'";

			/*
			 * String query =
			 * "select ui.user_name,vi.dateofvisit,vi.reasonforvisit from userinformation ui,visitinformation vi where ui.id=vi.patientid and vi.dateofvisit ='"
			 * + date + "'"; Query q =
			 * session.createSQLQuery(query).addScalar("user_name"
			 * ).addScalar("dateofvisit").addScalar("reasonforvisit")
			 * .setResultTransformer
			 * (Transformers.aliasToBean(AdminSearchDateForm.class));
			 */
			// searchPhysician = query.list();
			Query q = session.createQuery(query).setFirstResult(startIndex)
					.setMaxResults(endIndex);
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

	@SuppressWarnings("unchecked")
	public List<VisitsVO> getKeywordVisitDetails(String keyword)
			throws DataAccessException {

		List<VisitsVO> searchPhysician = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String query = "from VisitsVO where prescription like '%" + keyword
					+ "%'";

			/*
			 * String query =
			 * "select ui.user_name,vi.dateofvisit,vi.reasonforvisit from userinformation ui,visitinformation vi where ui.id=vi.patientid and vi.dateofvisit ='"
			 * + date + "'"; Query q =
			 * session.createSQLQuery(query).addScalar("user_name"
			 * ).addScalar("dateofvisit").addScalar("reasonforvisit")
			 * .setResultTransformer
			 * (Transformers.aliasToBean(AdminSearchDateForm.class));
			 */
			// searchPhysician = query.list();
			Query q = session.createQuery(query);
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
	@SuppressWarnings("unchecked")
	public List<Object[]> getPhysicianVisitRecords(int patId, int phyId, Map map)
			throws DataAccessException {
		int startIndex = (Integer) map.get("iDisplayStart");
		int endIndex = (Integer) map.get("iDisplayLength");

		VisitsVO physicianVO = null;
		List<Object[]> phyList = null;

		if (phyId != 1) {
			logger.info("if " + phyId);
			logger.info("patId " + patId);
			phyList = getSession()
					.createQuery(
							"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit"
									+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where v.patientid = u.userid "
									+ "and v.physicianid = uv.userid and v.patientid = "
									+ patId
									+ " and v.physicianid = "
									+ phyId
									+ " ").setFirstResult(startIndex)
					.setMaxResults(endIndex).list();
		} else {
			logger.info("else " + phyId);
			phyList = getSession()
					.createQuery(
							"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,p.practicename"
									+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv,AdminPracticeVO p where v.patientid = u.userid "
									+ "and v.physicianid = uv.userid and p.id = v.practiceid and v.patientid = "
									+ patId + " ").setFirstResult(startIndex)
					.setMaxResults(endIndex).list();
		}

		return phyList;
	}

	public List<Object[]> getPhysicianOrganizations(int phyId)
			throws DataAccessException {
		VisitsVO physicianVO = null;
		List<Object[]> phyList = null;

		phyList = getSession().createQuery(
				"select um.organizationid,um.practiceid "
						+ " from UserMappingVO um where um.userid = " + phyId
						+ " ").list();

		return phyList;
	}

	public void deletePhysicianVisit(VisitsVO physicianVO) {
		// TODO Auto-generated method stub
		logger.info("AdminOrganizationDaoImpl deletePhysicianVisit() starts.");
		getHibernateTemplate().delete(physicianVO);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getPhysicianVisitById(int vid)
			throws DataAccessException {
		VisitsVO physicianVO = null;
		List<Object[]> orgList = null;

		orgList = getSession()
				.createQuery(
						"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,v.organizationid,"
								+ "v.practiceid, v.patientid, v.physicianid"
								+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where v.patientid = u.userid "
								+ "and v.physicianid = uv.userid and v.id = "
								+ vid).list();

		/*
		 * String query = "from VisitsVO phyVo where (phyVo.id = :id)"; Query q
		 * = session.createQuery(query).setParameter("id", id); if (q.list() !=
		 * null && !q.list().isEmpty()) { orgList = q.list(); if (orgList !=
		 * null && orgList.size() > 0) { physicianVO = (VisitsVO)
		 * orgList.get(0); } }
		 */
		return orgList;

		// return physicianVO;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getPhysicianVisitById(int vid, Integer userid,
			String status,String role,Integer patientId,String searchDate) throws DataAccessException {
		
		System.out.println("vid : "+vid +"user : "+ userid +"   staus : "+status + " role : "+role +"patid : "+ patientId +" date : "+ searchDate);
		VisitsVO physicianVO = null;
		List<Object[]> orgList = null;
		String key="Upload";

		List<Object[]> orgList1 = null;

		String que = null;
		Date date = CommonUtils.parseDateFromStringForDttComaprison(searchDate);

	/*	if(vid==0){
	 
			que = "select pd.docname,pd.createdon,pd.keyid,0,pd.id"
					+ " from UserPersonalInfoVO u,PatientDocument pd where pd.uploadType = 'Normal' and" +
					" pd.patientid=u.userid";
	 
		}else{*/
		if (userid == 1) {
			if (status == "first") {
				que = "select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,v.organizationid,"
						+ "v.practiceid, v.patientid, v.physicianid"
						+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where v.patientid = u.userid "
						+ "and v.physicianid = uv.userid and v.id ='"
						+ vid
						+ "'";
			} else {
				
				if(vid ==0){
					
					que = "select pd.docname,pd.createdon,pd.keyid,0,pd.id"
							+ " from UserPersonalInfoVO u,PatientDocument pd where" +
							" pd.patientid=u.userid and u.userid='"+patientId+"' and pd.key='"+key+"'";
				}else{
				que = "select pd.docname,pd.createdon,pd.keyid,0"
						+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv,PatientDocument pd where pd.keyid=v.id and pd.patientid=u.userid  and  v.patientid = u.userid "
						+ "and v.physicianid = uv.userid and v.id ='" + vid
						+ "' and pd.key=";
				}
			}
		} else {
			if (status == "first") {
				if(role=="Physician"){
					que = "select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,v.organizationid,"
							+ "v.practiceid, v.patientid, v.physicianid"
							+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where u.userid='"
							+ userid
							+ "' "
							+ "and v.physicianid = uv.userid and v.id ='"
							+ vid
							+ "'";
				}else{
					if(vid==0){
						que = "select pd.docname,pd.createdon,pd.keyid,0,pd.id"
								+ " from UserPersonalInfoVO u,PatientDocument pd where" +
								" pd.patientid=u.userid and u.userid='"+patientId+"' and pd.key='"+key+"'";
					}else{
						
					
					que = "select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,v.organizationid,"
							+ "v.practiceid, v.patientid, v.physicianid"
							+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where u.userid='"
							+ userid
							+ "' and  v.patientid = uv.userid "
							+ "and v.id ='"
							+ vid
							+ "'";
					}
				}
				
			} else {
				if(role=="Physician"){
					if(vid==0){
						que = "select pd.docname,pd.createdon,pd.keyid,0,pd.id"
								+ " from UserPersonalInfoVO u,PatientDocument pd where" +
								" pd.patientid=u.userid and u.userid='"+patientId+"' and pd.key='"+key+"'";
					}else{
					que = "select pd.docname,pd.createdon,pd.keyid,0,pd.id"
							+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv,PatientDocument pd where pd.keyid=v.id and u.userid='"
							+ userid + "'"
							+ "and v.physicianid = uv.userid and v.id ='" + vid +"' and pd.key='"+key+"'";
					}
				}else{
					
					if(vid==0){
						que = "select pd.docname,pd.createdon,pd.keyid,0,pd.id"
								+ " from UserPersonalInfoVO u,PatientDocument pd where" +
								" pd.patientid=u.userid and u.userid='"+patientId+"' and pd.key='"+key+"'";
						
						if(date != null)
						{
							que = que + " and date(pd.createdon) ='" + date + "'";
						}
						System.out.println(que);
					}else{
						que = "select pd.docname,pd.createdon,pd.keyid,0,pd.id"
							+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv,PatientDocument pd where pd.keyid=v.id and pd.patientid=u.userid and u.userid='"
							+ userid + "' and  v.patientid = uv.userid  and v.id ='" + vid 	+ "' and pd.key='"+key+"'";
						if(date != null)
						{
							que = que + " and date(pd.createdon) ='" + date + "'";
						}
					
					}
				}
				
			}
		}
		
// }
//		System.out.println("que : "+que);
		orgList = getSession().createQuery(que).list();

		return orgList;

		// return physicianVO;
	}

	
	@SuppressWarnings("unchecked")
	public List<Object[]> getAdminClinicalSearchByDate(String searchDate) throws DataAccessException {
		List<Object[]> orgList = null;
		String que = null;
		Date date = CommonUtils.parseDateFromStringForDttComaprison(searchDate);
		que = "select pd.docname,pd.createdon,pd.keyid,0,pd.id,pd.patientid"
							+ " from PatientDocument pd";
		if(date != null)
		{
			que = que + " where date(pd.createdon) ='" + date + "'";
		}
	
		orgList = getSession().createQuery(que).list();

		return orgList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getSearchPhysicianVisitById(int patientId, Integer userid) throws DataAccessException {
		
		List<Object[]> orgList = null;
		String que = null;
			que = "select pd.docname,pd.createdon,pd.keyid,pd.id  from PatientDocument pd where pd.patientid='"+userid +"' and pd.createdby='"+patientId +"'";
		orgList = getSession().createQuery(que).list();

		return orgList;

	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getAdminSearchPhysicianVisitById(int physicianId) throws DataAccessException {
		
		
	List<Object[]> orgList = null;
		String que = null;
			que = "select pd.docname,pd.createdon,pd.keyid,pd.id,0,pd.patientid from PatientDocument pd where pd.createdby='"+physicianId +"'";

		orgList = getSession().createQuery(que).list();

		return orgList;

	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getAdminSearchPatientVisitById(int patientId) throws DataAccessException {
		
		List<Object[]> orgList = null;
		String que = null;
			que = "select pd.docname,pd.createdon,pd.keyid,pd.id,0,pd.patientid from PatientDocument pd where pd.patientid='"+patientId +"'";

		orgList = getSession().createQuery(que).list();
		return orgList;

	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getPhysicianSearchPatientVisitById(int patientid, int physicianid) throws DataAccessException {
		List<Object[]> orgList = null;
		orgList = getSession().createQuery("select pd.docname,pd.createdon,pd.keyid,pd.id,pd.patientid  from PatientDocument pd, ShareClinicalInfo " +
					"sc where sc.id=pd.keyid and sc.shareTo.id=:shareto and sc.shareFrom.id=:sharefrom and sc.requestStatus!='Pending'")
					.setParameter("sharefrom", physicianid)
					.setParameter("shareto", patientid)
					.list();
		return orgList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getPhysicianSearchVisitById(int physicianid, String searchDate) throws DataAccessException {
	
		List<Object[]> orgList = null;

	
		String que = null;
		Date date = CommonUtils.parseDateFromStringForDttComaprison(searchDate);

			orgList = getSession().createQuery("select pd.docname,pd.createdon,pd.keyid,pd.id,pd.patientid from PatientDocument pd, ShareClinicalInfo " +
					"sc where sc.id=pd.keyid and date(pd.createdon)=:createdon and sc.shareFrom.id=:sharefrom and sc.requestStatus!='Pending'")
					.setParameter("sharefrom", physicianid)
					.setParameter("createdon", date)
					.list();

		return orgList;

	}
	/*@SuppressWarnings("unchecked")
	public List<Object[]> getPhysicianVisitById(int vid, Integer userid,
			String status,String role) throws DataAccessException {
		VisitsVO physicianVO = null;
		List<Object[]> orgList = null;

		List<Object[]> orgList1 = null;

		String que = null;

		if (userid == 1) {
			if (status == "first") {
				que = "select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,v.organizationid,"
						+ "v.practiceid, v.patientid, v.physicianid"
						+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where v.patientid = u.userid "
						+ "and v.physicianid = uv.userid and v.id ='"
						+ vid
						+ "'";
			} else {
				que = "select pd.docname"
						+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv,PatientDocument pd where pd.keyid=v.id and pd.patientid=u.userid  and  v.patientid = u.userid "
						+ "and v.physicianid = uv.userid and v.id ='" + vid
						+ "'";
			}
		} else {
			if (status == "first") {
				que = "select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,v.organizationid,"
						+ "v.practiceid, v.patientid, v.physicianid"
						+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where u.userid='"
						+ userid
						+ "' and  v.patientid = u.userid "
						+ "and v.physicianid = uv.userid and v.id ='"
						+ vid
						+ "'";
			} else {
				que = "select pd.docname"
						+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv,PatientDocument pd where pd.keyid=v.id and pd.patientid=u.userid and u.userid='"
						+ userid + "' and  v.patientid = u.userid "
						+ "and v.physicianid = uv.userid and v.id ='" + vid
						+ "'";
			}
		}
		System.out.println("que : "+que);
		orgList = getSession().createQuery(que).list();

		return orgList;

		// return physicianVO;
	}*/

	@SuppressWarnings("unchecked")
	public List<Object[]> getAllVisitsByPhyId(int type, int phyId,
			String vDate, int patId, Map paramsMap) throws DataAccessException {
		VisitsVO physicianVO = null;
		List<Object[]> orgList = null;
		int iDisplayStart = (Integer) paramsMap.get("iDisplayStart");
		int iDisplayLength = (Integer) paramsMap.get("iDisplayLength");
		if (type == 1) {
			orgList = getSession()
					.createQuery(
							"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,"
									+ "v.patientid, v.physicianid"
									+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where v.patientid = u.userid "
									+ "and v.physicianid = uv.userid and v.physicianid = "
									+ phyId + " order by v.dateofvisit DESC ")
					.setFirstResult(iDisplayStart)
					.setMaxResults(iDisplayLength)

					.list();
		} else if (type == 2) {

			Date visitDate = CommonUtils
					.parseDateFromStringForDttComaprison(vDate);

			logger.info("vdate " + visitDate);
			orgList = getSession()
					.createQuery(
							"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,"
									+ "v.patientid, v.physicianid"
									+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where v.patientid = u.userid "
									+ "and v.physicianid = uv.userid and v.physicianid = "
									+ phyId
									+ " and  v.dateofvisit = '"
									+ visitDate + "' ")
					.setFirstResult(iDisplayStart)
					.setMaxResults(iDisplayLength).list();
		} else if (type == 3) {
			orgList = getSession()
					.createQuery(
							"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,"
									+ "v.patientid, v.physicianid"
									+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where v.patientid = u.userid "
									+ "and v.physicianid = uv.userid and v.physicianid = "
									+ phyId
									+ " and  v.patientid = "
									+ patId
									+ "order by v.dateofvisit DESC")
					.setFirstResult(iDisplayStart)
					.setMaxResults(iDisplayLength).list();
		}
		return orgList;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getAllVisitsByAdmin(int type, int patId, int orgId)
			throws DataAccessException {
		VisitsVO physicianVO = null;
		List<Object[]> orgList = null;
		logger.info("impl patient id " + patId);
		if (type == 1) {
			orgList = getSession()
					.createQuery(
							"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,"
									+ "v.patientid, v.physicianid"
									+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where v.patientid = u.userid "
									+ "and v.physicianid = uv.userid and v.patientid = "
									+ patId + "order by v.dateofvisit DESC")
					.list();
		} else if (type == 2) {
			orgList = getSession()
					.createQuery(
							"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,"
									+ "v.patientid, v.physicianid"
									+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where v.patientid = u.userid "
									+ "and v.physicianid = uv.userid and v.organizationid = "
									+ orgId + "order by v.dateofvisit DESC")
					.list();
		}

		return orgList;
	}

	@Override
	public void updatePhysicianVisit(VisitsVO physicianVO) {
		logger.info("physicianDaoImpl updatePhysicianVisit() starts.");
		getHibernateTemplate().update(physicianVO);
		logger.info("physicianDaoImpl updatePhysicianVisit() ends.");
	}

	@Override
	public void updatePersonalDetails(UserPersonalInfoVO userPersonalInfoDto) {
		logger.info("physicianDaoImpl updatePersonalDetails() starts.");
		getHibernateTemplate().update(userPersonalInfoDto);
		logger.info("physicianDaoImpl updatePersonalDetails() ends.");
	}

	@Override
	public void updatePhysicianPersonalDetails(
			UserPersonalInfoVO physicianPersonalInfoDto) {
		logger.info("physicianDaoImpl updatePhysicianPersonalDetails() starts.");
		getHibernateTemplate().update(physicianPersonalInfoDto);
		logger.info("physicianDaoImpl updatePhysicianPersonalDetails() ends.");
	}

	@Override
	public void savePrivateNote(PatientPrivateNoteVO patientPrivateNote) {
		logger.info("physicianDaoImpl savePrivateNote() starts.");
		getHibernateTemplate().save(patientPrivateNote);
		logger.info("physicianDaoImpl savePrivateNote() ends.");
	}

	@Override
	public void updatePrivateNote(String note, Integer noteid) {
		logger.info("physicianDaoImpl updatePrivateNote() starts.");
		int SelectId;
		String query = null;
		SQLQuery sqlQuery = null;
		Session session = null;

		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = "update privatenote set note='" + note + "' where id='"
					+ noteid + "'";
			sqlQuery = session.createSQLQuery(query);
			SelectId = sqlQuery.executeUpdate();
		} finally {
			query = null;
			sqlQuery = null;
			if (null != session) {
				session.flush();
				session.close();
			}
		}
		logger.info("physicianDaoImpl updatePrivateNote() ends.");
	}

	@Override
	public void saveEncounter(EncounterVO encounterVO) {
		logger.info("physicianDaoImpl saveEncounter() starts.");
		getHibernateTemplate().save(encounterVO);
		logger.info("physicianDaoImpl saveEncounter() ends.");
	}

	@Override
	public void updateTag(Integer visitid, String prescription) {
		logger.info("physicianDaoImpl updateTag() starts.");
		int SelectId;
		String query = null;
		SQLQuery sqlQuery = null;
		Session session = null;

		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = "update visitinformation set tag='" + prescription
					+ "' where id='" + visitid + "'";
			sqlQuery = session.createSQLQuery(query);
			SelectId = sqlQuery.executeUpdate();
		} finally {
			query = null;
			sqlQuery = null;
			if (null != session) {
				session.flush();
				session.close();
			}
		}
		// getHibernateTemplate().update(physicianVO);
		logger.info("physicianDaoImpl updateTag() ends.");
	}

	@SuppressWarnings("unchecked")
	public List<PatientPrivateNoteVO> checkNote(Integer visitid,String method,String type)
			throws DataAccessException {

		List<PatientPrivateNoteVO> searchPhysician = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String query = "from PatientPrivateNoteVO where visitid='"
					+ visitid + "' and method='"+method+"' and type ='"+type+"'";
			Query q = session.createQuery(query);
			if (q.list() != null && !q.list().isEmpty()) {
				searchPhysician = q.list();
			}
		} finally {
			session.flush();
			session.close();

		}
		return searchPhysician;
	}

	@Override
	public List<Object[]> getAllDocuments(Integer userid, String status) {
		// TODO Auto-generated method stub
		List<Object[]> dto = null;

		dto = getSession()
				.createQuery(
						"select p.id,p.patientid,p.docname,p.docpath,p.createdon,p.createdby from PatientDocument p,ShareClinicalInfo s"
								+ " where s.id =:shareid and s.shareTo.id = p.patientid and p.uploadType=:status")
				.setParameter("shareid", userid).setParameter("status", status)

				// and s.id = p.keyid and p.key ='Share'
				.list();
		return dto;

		/*
		 * List<PatientDocument> registrationList = null; RegistrationFormVO
		 * registrationFormVO = null; String queryString =
		 * "from PatientDocument p where p.patientid = '" + userid + "'";
		 * registrationList = getHibernateTemplate().find(queryString);
		 */

		// return registrationList;

	}

	@Override
	public VisitsVO findVisitDetails(Integer pkid) {
		// TODO Auto-generated method stub
		VisitsVO dto = new VisitsVO();
		logger.info("physicianDaoImpl findVisitDetails() starts.");

		dto = (VisitsVO) getSession().get(VisitsVO.class, pkid);

		logger.info("physicianDaoImpl findVisitDetails() ends.");
		return dto;
	}

	@Override
	public Integer saveShareRequest(ShareClinicalInfo shareVo) {
		// TODO Auto-generated method stub

		logger.info("physicianDaoImpl saveShareRequest() starts.");

		Integer id = (Integer) getHibernateTemplate().save(shareVo);

		logger.info("physicianDaoImpl saveShareRequest() ends.");
		return id;
	}

	@Override
	public List<Object[]> getPhysicianSharedDetails(Integer userId,
			String status) {
		// TODO Auto-generated method stub
		logger.info("physicianDaoImpl getPhysicianSharedDetails() starts.");
		/*
		 * List<Object[]> obj = getSession().createQuery(
		 * "select s.id, u1.firstname,v.dateofvisit,v.reasonforvisit,v.prescription,u1.userid,v.id "
		 * +
		 * " from ShareClinicalInfo s,UserPersonalInfoVO u, UserPersonalInfoVO u1,VisitsVO v where"
		 * +
		 * " s.shareFrom.id =:userid and s.shareFrom.id = u.userid and s.shareTo.id = u1.userid and u1.userid = v.patientid"
		 * + " and u.userid = v.physicianid and s.visit.id =v.id and" +
		 * " s.requestStatus =:status") .setParameter("userid", userId)
		 * .setParameter("status", Constants.APPROVED) .list();
		 */

		List<Object[]> obj = getSession()
				.createQuery(
						"select s.id,u.firstname,u.dob,u.lastname,u.ssn,u.contact_number,concat(u.street,' ',u.city,' ',u.zip),s.requestStatus"
								+ " from ShareClinicalInfo s, UserPersonalInfoVO u where s.shareFrom.id=:id and"
								+ "   u.userid = s.shareTo.id and s.status=1")
				.setParameter("id", userId)
				// .setParameter("status", status)
				.list();

		logger.info("physicianDaoImpl getPhysicianSharedDetails() ends.");
		return obj;
	}

	@Override
	public List<Object[]> getPatientVisitDetails(Map map) {
		// TODO Auto-generated method stub
		logger.info("physicianDaoImpl getPatientVisitDetails() starts.");
		List<Object[]> obj = getSession()
				.createQuery(
						"select u.firstname,u.lastname,u.dob,u.ssn,concat(u.street,' ',u.city,' ',u.zip),u.contact_number "
								+ "from UserPersonalInfoVO u,ShareClinicalInfo s where"
								+ " s.id = :shareid and s.shareTo.id = u.userid and s.requestStatus!='Pending'")
				// .setParameter("userid", map.get("patientId"))
				// .setParameter("visitid", map.get("visitId"))
				.setParameter("shareid", map.get("shareId"))
				// .setParameter("date", new Date())
				.setMaxResults(1).list();

		logger.info("physicianDaoImpl getPatientVisitDetails() ends.");

		return obj;
	}

	@Override
	public ShareClinicalInfo finShareDetails(Integer shareId) {
		logger.info("physicianDaoImpl finShareDetails() starts.");

		// ShareClinicalInfo dto = (ShareClinicalInfo)
		// getSession().load(ShareClinicalInfo.class, shareId);

		ShareClinicalInfo dto = (ShareClinicalInfo) getSession()
				.createQuery("select s from ShareClinicalInfo s where s.id=:id")
				.setParameter("id", shareId).uniqueResult();

		logger.info("physicianDaoImpl finShareDetails() ends.");
		return dto;
	}

	@Override
	public void updateShareDetails(ShareClinicalInfo shareVo) {
		// TODO Auto-generated method stub
		logger.info("physicianDaoImpl updateShareDetails() starts.");

		getHibernateTemplate().update(shareVo);

		logger.info("physicianDaoImpl updateShareDetails() ends.");
	}

	@Override
	public Boolean checkSharExpiry(Integer shareId) {
		// TODO Auto-generated method stub
		Boolean flag = false;

		Date dat = (Date) getSession()
				.createQuery(
						"select s.requestDate from ShareClinicalInfo s where s.id=:id")
				.setParameter("id", shareId).uniqueResult();

		Date minDate = DateUtils.addDays(dat, 30);
		Date curDate = new Date();

		if (minDate.compareTo(curDate) > 0) {
			logger.info("Date1 is after Date2");
			flag = true;
		} else if (minDate.compareTo(curDate) < 0) {
			logger.info("Date1 is before Date2");
			flag = false;
		} else if (minDate.compareTo(curDate) == 0) {
			logger.info("Date1 is equal to Date2");
			flag = true;
		} else {
			logger.info("How to get here?");
			flag = false;
		}

		return flag;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getPatientShareVisitRecords(int type, int patId,
			String vDate) throws DataAccessException {
		VisitsVO physicianVO = null;
		List<Object[]> patList = null;

		if (type == 1) {
			patList = getSession()
					.createQuery(
							"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,u.dob,v.patientid"
									+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where v.patientid = u.userid "
									+ "and v.physicianid = uv.userid and v.patientid = "
									+ patId + "").list();
		} else if (type == 2) {
			patList = getSession()
					.createQuery(
							"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,u.dob,v.patientid"
									+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where v.patientid = u.userid "
									+ "and v.physicianid = uv.userid and v.patientid = "
									+ patId + "").list();
		} else if (type == 3) {
			Date visitDate = CommonUtils
					.parseDateFromStringForDttComaprison(vDate);

			patList = getSession()
					.createQuery(
							"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,u.dob,v.patientid"
									+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where v.patientid = u.userid "
									+ "and v.physicianid = uv.userid  and v.patientid = "
									+ patId
									+ " and  v.dateofvisit = '"
									+ visitDate + "' ").list();
		}
		return patList;
	}

	@Override
	public String getPhysicianEmailId(Integer phyId) {
		// TODO Auto-generated method stub
		String phyEmail = "";
		logger.info("physicianDaoImpl saveShareRequest() starts.");

		// phyEmail =
		// getSession().createQuery("select user.user_name from UserVO user where user.id = "
		// + phyId + "").toString();

		String query = "select user.userName from UserVO user where user.id =?";
		Object[] queryParam = { phyId };
		List roleList = null;
		roleList = getHibernateTemplate().find(query, queryParam);
		if (roleList != null && roleList.size() > 0) {
			if (roleList.get(0) != null && !roleList.get(0).equals("")) {
				phyEmail = roleList.get(0).toString();
			}
		}

		logger.info("physicianDaoImpl saveShareRequest() ends.");
		return phyEmail;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getVisitDetailsforShare(Integer visitId) {
		// TODO Auto-generated method stub
		List<Object[]> patList = null;

		patList = getSession()
				.createQuery(
						"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname), v.dateofvisit,v.prescription,v.reasonforvisit,u.dob,v.patientid"
								+ " from VisitsVO v,UserPersonalInfoVO u, UserPersonalInfoVO uv where v.patientid = u.userid and v.physicianid = uv.userid "
								+ "and v.id = " + visitId + "").list();

		logger.info("physicianDaoImpl saveShareRequest() ends.");
		return patList;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getPatientRequestPending(Integer patId) {
		List<Object[]> patList = null;

		patList = getSession()
				.createQuery(
						"select sci.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),sci.createdon,sci.requestStatus,sci.shareFrom.id,sci.shareTo.id"
								+ " from UserPersonalInfoVO u,UserPersonalInfoVO uv,ShareClinicalInfo sci where sci.shareTo = u.userid "
								+ "and sci.shareFrom = uv.userid and sci.shareTo = "
								+ patId + " and sci.requestStatus = 'Pending' ")
				.list();

		return patList;
	}

	@Override
	public String getRequestStatus(Integer phyId, Integer patId) {
		// TODO Auto-generated method stub
		String requestStatus = "";
		String result = "";
		String requestId = "";
		int SelectId;
		// Integer requestId = 0;

		String query = "select share.id from ShareClinicalInfo share where share.shareFrom.id ="
				+ phyId
				+ " and share.shareTo.id = "
				+ patId
				+ " and share.requestStatus = 'Pending'";
		// Object[] queryParam = { phyId, patId };
		List shareList = null;
		shareList = getHibernateTemplate().find(query);

		/*
		 * logger.info("Answer shareList " + shareList[0][0]);
		 * logger.info("Answer shareList " + shareList[0][1]);
		 */
		if (shareList != null && shareList.size() > 0) {
			if (shareList.get(0) != null && !shareList.get(0).equals("")) {
				requestId = shareList.get(0).toString();
			}
		}

		/*
		 * logger.info("Answer requestStatus " + requestStatus);
		 * logger.info("Answer requestId " + requestId);
		 */

		logger.info("Answer requestId " + requestId);

		if (requestId != "" && requestId != "undefined") {
			Integer reqId = Integer.parseInt(requestId);
			SQLQuery sqlQuery = null;
			Session session = null;

			try {
				session = getHibernateTemplate().getSessionFactory()
						.openSession();
				query = "update shareclinicalinfo set requeststatus='Approved' where id='"
						+ reqId + "'";
				sqlQuery = session.createSQLQuery(query);
				SelectId = sqlQuery.executeUpdate();
				if (SelectId == 1) {
					result = "success";
				} else {
					result = "failure";
				}

			} finally {
				query = null;
				sqlQuery = null;
				if (null != session) {
					session.flush();
					session.close();
				}
			}
		} else {
			result = "new share";
		}

		return result;
	}

	@Override
	public Integer saveRequestBehalfOfPatient(RequestInfoOfPatientVO infoVO) {
		// TODO Auto-generated method stub

		logger.info("physicianDaoImpl saveRequestBehalfOfPatient() starts.");

		Integer id = (Integer) getHibernateTemplate().save(infoVO);

		logger.info("physicianDaoImpl saveRequestBehalfOfPatient() ends.");
		return id;
	}

	@Override
	public List<Object[]> getPhysicianrequestedSharedInfo(Integer userId) {
		// TODO Auto-generated method stub
		List<Object[]> list = null;

		list = getSession()
				.createQuery(
						"select r.id, concat(u.firstname,' ',u.lastname),concat(u1.firstname,' ',u1.lastname),r.requestStatus,r.requesttopatient "
								+ "from RequestInfoOfPatientVO r,UserPersonalInfoVO u,UserPersonalInfoVO u1 "
								+ "where r.requesttophysician=:id and r.requesttopatient = u.userid and r.requestfrom = u1.userid"
								+ " and r.requestStatus = 'Pending' order by r.id asc ")
				.setParameter("id", userId).list();
		return list;
	}

	@Override
	public RequestInfoOfPatientVO findReqBehalfOfPatient(Integer requestId) {
		// TODO Auto-generated method stub
		RequestInfoOfPatientVO dto = null;

		dto = (RequestInfoOfPatientVO) getSession()
				.createQuery(
						"select s from RequestInfoOfPatientVO s where s.id=:id")
				.setParameter("id", requestId).uniqueResult();
		return dto;
	}

	@Override
	public void updateReqBehalfOfPatient(RequestInfoOfPatientVO reqVo) {
		// TODO Auto-generated method stub
		logger.info("physicianDaoImpl updateReqBehalfOfPatient() starts.");

		getHibernateTemplate().update(reqVo);

		logger.info("physicianDaoImpl updateReqBehalfOfPatient() ends.");
	}

	@Override
	public List<Object[]> getPhysicianSharedInfoDetails(Map map) {
		// TODO Auto-generated method stub
		logger.info("physicianDaoImpl getPhysicianSharedInfoDetails() starts.");

		List<Object[]> obj = getSession()
				.createQuery(
						"select s.id,u.firstname,u.dob,u.lastname,u.ssn,u.contact_number,concat(u.street,' ',u.city,' ',u.zip),s.requestStatus"
								+ " from RequestInfoOfPatientVO s, UserPersonalInfoVO u where s.requestfrom=:id and"
								+ "   u.userid = s.requesttopatient and s.status=1 and s.requestStatus!='Pending'")
				.setParameter("id", map.get("userId"))
				// .setParameter("status", status)
				.list();

		logger.info("physicianDaoImpl getPhysicianSharedInfoDetails() ends.");
		return obj;
	}

	@Override
	public Boolean checkRequestExpiry(Integer requestId) {
		// TODO Auto-generated method stub
		Boolean flag = false;

		Date dat = (Date) getSession()
				.createQuery(
						"select s.requestDate from RequestInfoOfPatientVO s where s.id=:id")
				.setParameter("id", requestId).uniqueResult();

		Date minDate = DateUtils.addDays(dat, 30);
		Date curDate = new Date();

		if (minDate.compareTo(curDate) > 0) {
			logger.info("Date1 is after Date2");
			flag = true;
		} else if (minDate.compareTo(curDate) < 0) {
			logger.info("Date1 is before Date2");
			flag = false;
		} else if (minDate.compareTo(curDate) == 0) {
			logger.info("Date1 is equal to Date2");
			flag = true;
		} else {
			logger.info("How to get here?");
			flag = false;
		}

		return flag;
	}

	@Override
	public List<Object[]> getAllDocumentsRequestBehalfOfPatient(
			Integer requestId, String status) {
		// TODO Auto-generated method stub
		List<Object[]> dto = null;

		dto = getSession()
				.createQuery(
						"select p.id,p.patientid,p.docname,p.docpath,p.createdon from "
								+ "PatientDocument p,RequestInfoOfPatientVO s"
								+ " where s.id =:shareid and s.id = p.keyid and p.key = 'RequestInfo' and s.requesttopatient = p.patientid and p.uploadType=:status")
				.setParameter("shareid", requestId)
				.setParameter("status", status).list();
		return dto;

	}

	@Override
	public List<Object[]> getPatientDetails(Map map) {
		// TODO Auto-generated method stub
		logger.info("physicianDaoImpl getPatientDetails() starts.");
		List<Object[]> obj = getSession()
				.createQuery(
						"select u.firstname,u.lastname,u.dob,u.ssn,concat(u.street,' ',u.city,' ',u.zip),u.contact_number "
								+ "from UserPersonalInfoVO u,RequestInfoOfPatientVO s where"
								+ " s.id = :id and s.requesttopatient = u.userid and s.requestStatus!='Pending'")
				// .setParameter("userid", map.get("patientId"))
				// .setParameter("visitid", map.get("visitId"))
				.setParameter("id", map.get("requestId"))
				// .setParameter("date", new Date())
				.setMaxResults(1).list();

		logger.info("physicianDaoImpl getPatientDetails() ends.");

		return obj;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getPatientPersonalDetails(Integer patId) {
		List<Object[]> patList = null;

		patList = getSession().createQuery(
				"select user.id,concat(user.firstname,' ',user.lastname),user.dob,user.userid"
						+ " from UserPersonalInfoVO user where user.userid = "
						+ patId + " ").list();

		return patList;
	}

	@Override
	public List<Object[]> getAllDocumentsByUserId(Integer userid) {
		// TODO Auto-generated method stub
		List<Object[]> dto = null;
		
		String key="Upload";

		dto = getSession()
				.createQuery(
						"select p.id,p.patientid,p.docname,p.docpath,p.createdon,p.createdby from PatientDocument p"
								+ " where p.patientid=:id and key=:key")
				.setParameter("id", userid).setParameter("key", key).list();
		return dto;

	}
	
	@Override
	public List<Object[]> getShareDocumentsByUserId(Integer userid) {
		// TODO Auto-generated method stub
		List<Object[]> dto = null;
		
		String key="Upload";

		dto = getSession()
				.createQuery(
						"select p.id,p.patientid,p.docname,p.docpath,p.createdon,p.createdby from PatientDocument p"
								+ " where p.patientid=:id and key=:key")
				.setParameter("id", userid).setParameter("key", key).list();
		return dto;

	}

	@Override
	public List<Object[]> getAllDocumentsBypatIdInPhysician(Integer patid) {
		// TODO Auto-generated method stub
		List<Object[]> dto = null;

		dto = getSession()
				.createQuery(
						"select p.id,p.patientid,p.docname,p.docpath,p.createdon,concat(user.firstname,' ',user.lastname),user.dob from PatientDocument p, UserPersonalInfoVO user"
								+ " where user.userid = "
								+ patid
								+ " and p.patientid=:id")
				.setParameter("id", patid).list();
		return dto;

	}

	@Override
	public Boolean checkAlreadyExistingPhysician(Map map) {
		// TODO Auto-generated method stub
		Long count = 0L;

		count = (Long) getSession()
				.createQuery(
						"select count(*) from PhysicianMappingVO p where p.userid =:userid"
								+ " and p.status = 1")
				.setParameter("userid", map.get("userId"))
				// .setParameter("organizationId", map.get("organizationId"))
				// .setParameter("practiceId", map.get("practiceId"))
				.uniqueResult();

		if (count.equals(0L)) {

			return true;
		} else {

			return false;
		}

	}

	public List<UserPersonalInfoVO> getPatientAdditionalInformation(
			Integer patId) throws DataAccessException {

		List<UserPersonalInfoVO> patientaddinfo = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String query = "from UserPersonalInfoVO user where userid = "
					+ patId + "";

			Query q = session.createQuery(query);
			if (q.list() != null && !q.list().isEmpty()) {
				patientaddinfo = q.list();
			}
		} finally {
			session.flush();
			session.close();

		}
		return patientaddinfo;
	}

	public List<UserPersonalInfoVO> getPhysicianAdditionalInformation(
			Integer phyId) throws DataAccessException {

		List<UserPersonalInfoVO> physicianaddinfo = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String query = "from UserPersonalInfoVO user where userid = "
					+ phyId + "";

			Query q = session.createQuery(query);
			if (q.list() != null && !q.list().isEmpty()) {
				physicianaddinfo = q.list();
			}
		} finally {
			session.flush();
			session.close();

		}
		return physicianaddinfo;
	}

	@Override
	public Long getAllVisitsountsByPhyId(Integer type, Integer phyId,
			String vDate, Integer patId) {
		VisitsVO physicianVO = null;
		List<Object[]> orgList = null;

		if (type == 1) {
			orgList = getSession()
					.createQuery(
							"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,"
									+ "v.patientid, v.physicianid"
									+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where v.patientid = u.userid "
									+ "and v.physicianid = uv.userid and v.physicianid = "
									+ phyId + " order by v.dateofvisit DESC ")

					.list();
		} else if (type == 2) {

			Date visitDate = CommonUtils
					.parseDateFromStringForDttComaprison(vDate);

			logger.info("vdate " + visitDate);
			orgList = getSession()
					.createQuery(
							"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,"
									+ "v.patientid, v.physicianid"
									+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where v.patientid = u.userid "
									+ "and v.physicianid = uv.userid and v.physicianid = "
									+ phyId
									+ " and  v.dateofvisit = '"
									+ visitDate + "' ")

					.list();
		} else if (type == 3) {
			orgList = getSession()
					.createQuery(
							"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,"
									+ "v.patientid, v.physicianid"
									+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where v.patientid = u.userid "
									+ "and v.physicianid = uv.userid and v.physicianid = "
									+ phyId
									+ " and  v.patientid = "
									+ patId
									+ "order by v.dateofvisit DESC")

					.list();
		}
		// return orgList;
		int size = 0;
		if (orgList != null) {
			size = orgList.size();
		} else {
			size = 0;

		}

		return Long.parseLong(size + "");
	}

	@Override
	public Long getPatientDetailsCount(String searchPatient, Map map) {
		// TODO Auto-generated method stub
		List<SearchPatientForm> searchPhysician = null;
		Query query = null;
		Long count = 0L;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			String querystr = null;
			// and (CONCAT(LOWER(ui.firstname),LOWER(ui.lastname)))
			Integer orgid = (Integer) map.get("orgid");
			if (orgid.equals(-1)) {
				if (searchPatient != null && !searchPatient.equals("")) {

					querystr = "select ad.userid,CONCAT(ad.firstname,' ',ad.lastname) as firstname,ur.userid,ur.roleid,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ad.lastname,ad.ssn,"
							+ "ad.contact_number  from user_add_info ad,userroles ur where ad.userid=ur.userid and ur.roleid='Patient' and "
							+ "(CONCAT(LOWER(ad.firstname),LOWER(ad.lastname))) like '%"
							+ searchPatient + "%'";

				} else {

					querystr = "select ad.userid,CONCAT(ad.firstname,' ',ad.lastname) as firstname,ur.userid,ur.roleid,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ad.lastname,ad.ssn,"
							+ "ad.contact_number  from user_add_info ad,userroles ur where ad.userid=ur.userid and ur.roleid='Patient'";

				}
			} else {
				if (searchPatient != null && !searchPatient.equals("")) {

					querystr = "select ad.userid,CONCAT(ad.firstname,' ',ad.lastname) as firstname,ur.userid,ur.roleid,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ad.lastname,ad.ssn,"
							+ "ad.contact_number  from user_add_info ad,userroles ur,patientmapping pm where pm.userid=ad.userid and pm.organizationid='"
							+ orgid
							+ "' and ad.userid=ur.userid and ur.roleid='Patient' and "
							+ "(CONCAT(LOWER(ad.firstname),LOWER(ad.lastname))) like '%"
							+ searchPatient + "%'";
				} else {

					/*
					 * querystr =
					 * "select ad.userid,CONCAT(ad.firstname,' ',ad.lastname) as firstname,ur.userid,ur.roleid,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ad.lastname,ad.ssn,"
					 * +
					 * "ad.contact_number  from user_add_info ad,userroles ur where ad.userid=ur.userid and ur.roleid='Patient'"
					 * ;
					 */

					querystr = "select ad.userid,CONCAT(ad.firstname,' ',ad.lastname) as firstname,ur.userid,ur.roleid,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ad.lastname,ad.ssn,"
							+ "ad.contact_number  from user_add_info ad,userroles ur,patientmapping pm where pm.userid=ad.userid and pm.organizationid='"
							+ orgid
							+ "' and "
							+ "ad.userid=ur.userid and ur.roleid='Patient'";

				}
			}
			/*
			 * if(searchPatient!=null && !searchPatient.equals("")){
			 * 
			 * querystr =
			 * "select ad.userid,CONCAT(ad.firstname,' ',ad.lastname) as firstname,ur.userid,ur.roleid,ad.dob,CONCAT(ad.street,' ',ad.city,' ',ad.zip) as address,ad.lastname,ad.ssn,"
			 * +
			 * "ad.contact_number  from user_add_info ad,userroles ur where ad.userid=ur.userid and ur.roleid='Patient' and "
			 * + "(CONCAT(LOWER(ad.firstname),LOWER(ad.lastname))) like '%"+
			 * searchPatient+"%'"; }else{
			 * 
			 * 
			 * if (query.list() != null && !query.list().isEmpty()) {
			 * searchPhysician = query.list(); count =
			 * Long.parseLong(searchPhysician.size() + ""); }
			 * 
			 * }
			 */

			query = session
					.createSQLQuery(querystr)
					.addScalar("userid")
					.addScalar("firstname")
					.addScalar("dob")
					.addScalar("address")
					.addScalar("lastname")
					.addScalar("ssn")
					.addScalar("contact_number")
					.setResultTransformer(
							Transformers.aliasToBean(SearchPatientForm.class));

			if (query.list() != null && !query.list().isEmpty()) {
				searchPhysician = query.list();
				count = Long.parseLong(searchPhysician.size() + "");
			}

		} finally {
			session.flush();
			session.close();

		}
		return count;
	}

	@Override
	public Long getSearchPhysicianVisitRecordsCount(String physicianid, Map map) {
		// TODO Auto-generated method stub
		List<VisitsVO> searchPhysician = null;
		Long count = 0L;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();

		try {

			String query = "from VisitsVO user where user.physicianid ='"
					+ physicianid + "'";
			Query q = session.createQuery(query);
			if (q.list() != null && !q.list().isEmpty()) {
				searchPhysician = q.list();
				count = Long.parseLong(searchPhysician.size() + "");
			}
		} finally {
			session.flush();
			session.close();

		}
		return count;
	}

	@Override
	public Long getPatientRequestCount(Integer userId, String role,
			HashMap map) {
		List<Object[]> list = new ArrayList<Object[]>();

		int startIndex = (Integer) map.get("iDisplayStart");
		int endIndex = (Integer) map.get("iDisplayLength");
		List<PatientRequestVO> searchPhysician = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String query = null;

			if (StringUtils.isNotBlank(role)
					&& StringUtils.equalsIgnoreCase(role, "Physician")) {
				query = "from PatientRequestVO where physicianid='" + userId
						+ "'";
				if(!map.get("searchVal").equals("NO")){
					
					list = getSession().createQuery("select p.id,p.patientid,p.mailstatus from PatientRequestVO p," +
							"UserPersonalInfoVO u where p.patientid=u.userid and " +
							"(CONCAT(LOWER(u.firstname),LOWER(u.lastname))) like :searchVal" +
							" and physicianid=:userId")
							.setParameter("searchVal", "%"+map.get("searchVal")+"%")
							.setParameter("userId", userId)
							.list();
					
//					query = "select p.id,p.patientid,p.mailstatus from PatientRequestVO p," +
//							"UserPersonalInfoVO u where p.patientid=u.userid and " +
//							"(CONCAT(LOWER(u.firstname),LOWER(u.lastname))) like :searchVal" +
//							" and physicianid=:userId";
//					
				}else{
					
					list = getSession().createQuery("select p.id,p.patientid,p.mailstatus from PatientRequestVO p," +
							"UserPersonalInfoVO u where p.patientid=u.userid "+
							" and physicianid=:userId")
							.setParameter("userId", userId)
							.list();
					
//					query = "select p.id,p.patientid,p.mailstatus from PatientRequestVO p," +
//							"UserPersonalInfoVO u where p.patientid=u.userid "+
//							" and physicianid=:userId";
					
				}
				
			}
			
			
//			
//			Query q = session.createQuery(query).setFirstResult(startIndex)
//					.setMaxResults(endIndex);
//			if (q.list() != null && !q.list().isEmpty()) {
//				searchPhysician = q.list();
				/*
				 * if (userList != null && userList.size() > 0) {
				 * registrationForm1VO = (UserVO) userList.get(0); }
				 */
//			}
		} finally {
			session.flush();
			session.close();

		}
		return Long.parseLong(list.size()+"");
	}

	@Override
	public Long getSearchVisitRecordsCount(String searchDate, Integer userId,
			String role, HashMap paramsMap) {
		List<VisitsVO> searchPhysician = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		Long count = 0L;
		try {

			Date date = CommonUtils
					.parseDateFromStringForDttComaprison(searchDate);

			logger.info("date : " + date);

			String query = null;

			if (role == "Physician") {
				query = "from VisitsVO where physicianid='" + userId
						+ "' and dateofvisit ='" + date + "' ";
			} else if (role == "Patient") {
				query = "from VisitsVO where patientid='" + userId
						+ "'  and dateofvisit ='" + date + "'";
			} else {
				query = "from VisitsVO where dateofvisit ='" + date + "'";
			}

			/*
			 * String query =
			 * "select ui.user_name,vi.dateofvisit,vi.reasonforvisit from userinformation ui,visitinformation vi where ui.id=vi.patientid and vi.dateofvisit ='"
			 * + date + "'"; Query q =
			 * session.createSQLQuery(query).addScalar("user_name"
			 * ).addScalar("dateofvisit").addScalar("reasonforvisit")
			 * .setResultTransformer
			 * (Transformers.aliasToBean(AdminSearchDateForm.class));
			 */
			// searchPhysician = query.list();
			Query q = session.createQuery(query);
			if (q.list() != null && !q.list().isEmpty()) {
				searchPhysician = q.list();
				count = Long.parseLong(searchPhysician.size() + "");
				/*
				 * if (userList != null && userList.size() > 0) {
				 * registrationForm1VO = (UserVO) userList.get(0); }
				 */
			}
		} finally {
			session.flush();
			session.close();

		}
		return count;
	}

	@Override
	public Long getTagVisitDetailsCount(String tag, Map paramsMap) {
		// TODO Auto-generated method stub
		List<VisitsVO> searchPhysician = null;
		Long count = 0L;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String query = "from VisitsVO user where tag like '%" + tag + "%'";

			/*
			 * String query =
			 * "select ui.user_name,vi.dateofvisit,vi.reasonforvisit from userinformation ui,visitinformation vi where ui.id=vi.patientid and vi.dateofvisit ='"
			 * + date + "'"; Query q =
			 * session.createSQLQuery(query).addScalar("user_name"
			 * ).addScalar("dateofvisit").addScalar("reasonforvisit")
			 * .setResultTransformer
			 * (Transformers.aliasToBean(AdminSearchDateForm.class));
			 */
			// searchPhysician = query.list();
			Query q = session.createQuery(query);
			if (q.list() != null && !q.list().isEmpty()) {
				searchPhysician = q.list();
				count = Long.parseLong(searchPhysician.size() + "");
				/*
				 * if (userList != null && userList.size() > 0) {
				 * registrationForm1VO = (UserVO) userList.get(0); }
				 */
			}
		} finally {
			session.flush();
			session.close();

		}
		return count;
	}

	@Override
	public Long getSearchPatientVisitRecordsCount(String patientid, Map map) {
		// TODO Auto-generated method stub
		List<VisitsVO> searchPhysician = null;
		Long count = 0L;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String query = "from VisitsVO user where user.patientid ='"
					+ patientid + "'";
			Query q = session.createQuery(query);
			if (q.list() != null && !q.list().isEmpty()) {
				searchPhysician = q.list();

				count = Long.parseLong(searchPhysician.size() + "");
			}
		} finally {
			session.flush();
			session.close();

		}
		return count;
	}

	@Override
	public Long getPhysicianVisitRecordsCount(Integer patId, int phyId, Map map) {
		// TODO Auto-generated method stub
		Long count = 0L;
		VisitsVO physicianVO = null;
		List<Object[]> phyList = null;

		if (phyId > 0) {
			logger.info("if " + phyId);
			logger.info("patId " + patId);
			phyList = getSession()
					.createQuery(
							"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit"
									+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv where v.patientid = u.userid "
									+ "and v.physicianid = uv.userid and v.patientid = "
									+ patId
									+ " and v.physicianid = "
									+ phyId
									+ " ")

					.list();
		} else {
			logger.info("else " + phyId);
			phyList = getSession()
					.createQuery(
							"select v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),v.dateofvisit,v.prescription,v.reasonforvisit,p.practicename"
									+ " from VisitsVO v,UserPersonalInfoVO u,UserPersonalInfoVO uv,AdminPracticeVO p where v.patientid = u.userid "
									+ "and v.physicianid = uv.userid and p.id = v.practiceid and v.patientid = "
									+ patId + " ")

					.list();
		}

		if (phyList != null) {

			count = Long.parseLong(phyList.size() + "");
		}

		return count;
	}

	@Override
	public String getOrganizationNameByPatientId(Integer patientId) {
		// TODO Auto-generated method stub
		String name = null;
		name = (String) getSession()
				.createQuery(
						"select a.organizationname from AdminOrganizationVO a, PatientMappingVO p where "
								+ "p.userid =:id and p.organizationid = a.id and  a.status=1")
				.setParameter("id", patientId).uniqueResult();

		if (name == null) {
			name = "--";
		}
		return name;
	}

	@Override
	public void updatePatientRequestVo(Integer requestId) {
		// TODO Auto-generated method stub
		getSession().createSQLQuery("update patientrequest set mailstatus = 1 where id=:requestId")
					.setParameter("requestId", requestId)
					.executeUpdate();
	}

	@Override
	public Date getVisitDateByVisitId(Integer visitId) {
		Date visitDate = (Date) getSession().createQuery("select v.dateofvisit from VisitsVO v where v.id=:id")
				.setParameter("id", visitId)
				.uniqueResult();
		return visitDate;
	}

	@Override
	public List<Object[]> getAllPatientDetailsForFax() {
		// TODO Auto-generated method stub
		List<Object[]> list = null;
		try {
			list = getSession().createQuery("select u1.id,u.fax from UserPersonalInfoVO u,UserVO u1 " +
					"where u1.status='1' and u1.id = u.userid")
					   .list();
			
		} catch (Exception e) {
			logger.info("Error:"+ e.getMessage());
		}
		
		
		
		return list;
	}

	@Override
	public List<Object[]> getEfaxAssocoatedPatientDocuments(Integer patientId) {
		// TODO Auto-generated method stub
		List<Object[]> list = null;
		try {
			
			if(patientId.equals(0)){
				
				list = getSession().createQuery("select d.id,d.docname,d.docpath,concat(u.firstname,' ',u.lastname),u.userid," +
						"a.organizationname,d.receiveddate,d.associateddate " +
						"from UserPersonalInfoVO u,AdminOrganizationVO a,PatientMappingVO p,PatientDocument d" +
						" where d.doctype='Efax' and d.patientid = u.userid and u.userid=p.userid and p.organizationid = a.id and" +
						" p.status=1")
						   .list();
			}else{
				list =list = getSession().createQuery("select d.id,d.docname,d.docpath,concat(u.firstname,' ',u.lastname),u.userid," +
						"a.organizationname,d.receiveddate,d.associateddate " +
						"from UserPersonalInfoVO u,AdminOrganizationVO a,PatientMappingVO p,PatientDocument d" +
						" where u.userid=:id and d.doctype='Efax' and d.patientid = u.userid and u.userid=p.userid " +
						"and p.organizationid = a.id and" +
						" p.status=1")
						.setParameter("id", patientId)
						   .list();
			}
			
			
		} catch (Exception e) {
			logger.info("Error:"+ e.getMessage());
		}
		
		
		
		return list;
	}

	@Override
	public void deleteDocument(Integer docId) {
		// TODO Auto-generated method stub
		getSession().createSQLQuery("delete from PatientDocument where id=:id")
					.setParameter("id", docId)
		             .executeUpdate();
	}

	@Override
	public Object[] getPhysicianDetails(Integer physicianid) {
		// TODO Auto-generated method stub
		Object[] obj = null;
		
		obj = (Object[]) getSession().createQuery("select v.emailId,u.fax from UserPersonalInfoVO u,UserVO v where v.id=:id" +
				" and v.id = u.userid and v.status=1")
				.setParameter("id", physicianid)
				.uniqueResult();
		
		return obj;
	}

	@Override
	public String getOrganizationIdForPatient(Integer orgId, Integer patientId) {
		// TODO Auto-generated method stub
		String fax = null;
		fax = (String) getSession().createQuery("select a.fax from AdminOrganizationVO a,PatientMappingVO p where a.id=:orgId" +
				" and a.id = p.organizationid and p.userid=:patientId and a.status=1")
				.setParameter("orgId", orgId)
				.setParameter("patientId", patientId)
				.uniqueResult();
		return fax;
	}

	@Override
	public Integer findPatientOrganizationId(Integer userId) {
		Integer orgId = null;
		orgId = (Integer) getSession().createQuery("select p.organizationid from PatientMappingVO p where p.userid=:id" +
				" and p.status=1")
				.setParameter("id", userId)
				.uniqueResult();
		
		if(orgId ==null){
			orgId = 0;
		}
		return orgId;
	}

	@Override
	public Object[] getPatientPersonalDetailsForEfax(Integer userId) {
		// TODO Auto-generated method stub
Object[] obj = null;
		
		obj = (Object[]) getSession().createQuery("select concat(u.firstname,' ',u.lastname), v.emailId,u.contact_number from" +
				" UserPersonalInfoVO u,UserVO v where v.id=:id" +
				" and v.id = u.userid and v.status=1")
				.setParameter("id", userId)
				.uniqueResult();
		
		return obj;
	}

	@Override
	public Integer saveEFaxDetails(FaxVo faxVo) {
		// TODO Auto-generated method stub
		logger.info("PhysicianDaoImpl saveEFaxDetails() starts.");
		Integer id = (Integer) getHibernateTemplate().save(faxVo);
		logger.info("PhysicianDaoImpl saveEFaxDetails() ends.");

		return id;
	}

	@Override
	public void updateShareStatus(Integer shareId) {
		// TODO Auto-generated method stub
		getSession().createSQLQuery("update shareclinicalinfo  set requeststatus='Approved' where id=:id")
		.setParameter("id", shareId)
		.executeUpdate();
		
	}

	@Override
	public List<Object[]> getAllSharedDocuments(Integer shareId, String status) {
		// TODO Auto-generated method stub
		List<Object[]> dto = null;

		dto = getSession()
				.createQuery(
						"select p.id,p.patientid,p.docname,p.docpath,p.createdon,p.createdby from PatientDocument p,ShareClinicalInfo s"
								+ " where s.id =:shareid and s.shareTo.id = p.patientid and s.id=p.keyid and p.uploadType=:status")
				.setParameter("shareid", shareId).setParameter("status", status)

				// and s.id = p.keyid and p.key ='Share'
				.list();
		return dto;
	}
}
