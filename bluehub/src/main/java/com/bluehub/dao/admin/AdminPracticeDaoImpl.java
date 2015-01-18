/**
 * 
 * 
 */
package com.bluehub.dao.admin;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.bluehub.vo.admin.AdminPracticeVO;

public class AdminPracticeDaoImpl extends HibernateDaoSupport implements
		AdminPracticeDao {

	private static Logger logger = Logger.getLogger(AdminPracticeDaoImpl.class);

	public void saveAdminPractice(AdminPracticeVO adminPracticeVO) {
		logger.info("AdminPracticeDaoImpl saveAdminPracticeFormVO() starts.");
		getHibernateTemplate().save(adminPracticeVO);
	}

	public List<AdminPracticeVO> getAdminPractice(Map map) {
		int startindex = (Integer) map.get("iDisplayStart");
		int endindex = (Integer) map.get("iDisplayLength");
		logger.info("getAdminOrganizaion");
		logger.info("AdminPracticeDaoImpl : getAdminOrganizaion() ==> starts.");
		String query = "from AdminPracticeVO where status='1'";
		// Object[] queryParam = { childId, userMail };
		List<AdminPracticeVO> userList = getSession()
				.createQuery(
						"select a from AdminPracticeVO a where a.status='1'")
				.setFirstResult(startindex).setMaxResults(endindex).list();

		return userList;

	}

	/*
	 * public void deleteAdminPractice( AdminPracticeVO adminPracticeVO) { //
	 * logger.info("AdminPracticeDaoImpl deleteAdminPracticeFormVO() starts.");
	 * logger.info("print the delete Practice");
	 * //adminPracticeVO.setId(id);
	 * getHibernateTemplate().delete(adminPracticeVO); }
	 */

	public void deleteAdminPractice(Integer practiceid) {
		// TODO Auto-generated method stub
		logger.info("AdminPracticeDaoImpl deleteAdminPracticeFormVO() starts.");
		int enableStatus = 0;
		String query = null;
		SQLQuery sqlQuery = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = "update practice set status='" + enableStatus
					+ "' where id='" + practiceid + "'";
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
	public List<AdminPracticeVO> getPracticeList() {
		// TODO Auto-generated method stub
		logger.info("AdminPracticeDaoImpl getPracticeList() Starts==>");

		String query = "from AdminPracticeVO where status='1'";
		List<AdminPracticeVO> practiceList = getHibernateTemplate().find(query);

		logger.info("AdminPracticeDaoImpl getPracticeList() Ends==>");
		return practiceList;
	}

	public List<AdminPracticeVO> editAdminPractice(
			AdminPracticeVO adminPracticeVO, int id) {
		// TODO Auto-generated method stub
		logger.info("AdminPracticeDaoImpl editAdminPracticeFormVO() starts.");
		List<AdminPracticeVO> userList = getHibernateTemplate().find(
				"from AdminPracticeVO a where a.id = ?", id);
		return userList;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public AdminPracticeVO geAdminPracticeVOById(int id)
			throws DataAccessException {
		AdminPracticeVO PracticeVO = null;
		List<AdminPracticeVO> orgList = null;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			String query = "from AdminPracticeVO orgVo where (orgVo.id = :id)";
			Query q = session.createQuery(query).setParameter("id", id);
			if (q.list() != null && !q.list().isEmpty()) {
				orgList = q.list();
				if (orgList != null && orgList.size() > 0) {
					PracticeVO = (AdminPracticeVO) orgList.get(0);
				}
			}
		} finally {
			session.flush();
			session.close();

		}
		return PracticeVO;
	}

	@Override
	public void updateAdminPractice(AdminPracticeVO adminPracticeVO) {
		logger.info("AdminPracticeVODaoImpl updateAdminPractice() starts.");
		getHibernateTemplate().update(adminPracticeVO);
		logger.info("AdminPracticeVODaoImpl updateAdminPractice() ends.");
	}

	@Override
	public AdminPracticeVO findPracticeById(Integer practiceId) {
		// TODO Auto-generated method stub
		AdminPracticeVO dto = (AdminPracticeVO) getSession()
				.createQuery("select d from AdminPracticeVO d where d.id=:id")
				.setParameter("id", practiceId).uniqueResult();
		return dto;
	}

	@Override
	public Long getAdminPracticeCount(Map map) {
		// TODO Auto-generated method stub

		Long count = (Long) getSession().createQuery(
				"select count(*) from AdminPracticeVO a where a.status='1'")

		.uniqueResult();

		if (count == null) {
			count = 0L;
		}
		return count;
	}

}
