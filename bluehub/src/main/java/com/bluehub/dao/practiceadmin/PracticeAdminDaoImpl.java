package com.bluehub.dao.practiceadmin;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bluehub.bean.admin.SearchPhysicianForm;
import com.bluehub.vo.user.UserMappingVO;

@Transactional(readOnly = true)
public class PracticeAdminDaoImpl extends HibernateDaoSupport implements
		PracticeAdminDao {

	private static Logger logger = Logger.getLogger(PracticeAdminDaoImpl.class);

	@SuppressWarnings("unchecked")
	public List<UserMappingVO> getPracticeAdminMap(Integer userId)
			throws DataAccessException {

		List<UserMappingVO> usermap = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {

			String query = "from UserMappingVO user where user.userid ='"
					+ userId + "'";
			Query q = session.createQuery(query);
			if (q.list() != null && !q.list().isEmpty()) {
				usermap = q.list();

			}
		} finally {
			session.flush();
			session.close();

		}
		return usermap;
	}

	@SuppressWarnings("unchecked")
	public List<SearchPhysicianForm> getPhysicianMappingRecord(Integer userId,
			Integer organizationId, Integer practiceId)
			throws DataAccessException {

		List<SearchPhysicianForm> searchPhysician = null;

		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			// String querystr
			// ="select ui.firstname,ui.userid,org.organizationname,pr.practicename,uin.status from user_add_info ui,organization org,usermapping um,userroles ur,practice pr,userinformation uin where org.id=um.organizationid and ui.userid=um.userid and ui.userid=ur.userid and ur.roleid='Physician' and uin.id=ui.userid and org.id='"+organizationId+"' and pr.id='"+practiceId+"' and um.userid='"+userId+"'";

			String querystr = "select concat(ui.firstname,' ',ui.middlename,' ',ui.lastname) as firstname,ui.userid,org.organizationname,pr.practicename from "
					+ "user_add_info ui,organization org,usermapping um,practice pr "
					+ "where "
					+ " org.id='"
					+ organizationId
					+ "' and pr.id='"
					+ practiceId
					+ "' "
					+ "and um.practiceid = pr.id and org.id=um.organizationid "
					+ "and org.id = pr.organizationid and ui.userid= um.userid order by um.id asc";

			System.out.print("practice admin query : "+querystr);
			Query q = session
					.createSQLQuery(querystr)
					.addScalar("firstname")
					.addScalar("userid")
					.addScalar("organizationName")
					.addScalar("practicename")
					.setResultTransformer(
							Transformers.aliasToBean(SearchPhysicianForm.class));
			if (q.list() != null && !q.list().isEmpty()) {
				searchPhysician = q.list();
			}
			/*
			 * String querystr =
			 * "select ui.firstname,ui.userid,org.organizationname,pr.practicename from user_add_info ui,organization org,practice pr where org.id='"
			 * +
			 * organizationId+"' and pr.id='"+practiceId+"' and ui.userid='"+userId
			 * +"'"; Query q = session.createQuery(querystr); if (q.list() !=
			 * null && !q.list().isEmpty()) { searchPhysician = q.list();
			 */

		} finally {
			session.flush();
			session.close();

		}
		return searchPhysician;
	}
}
