package com.bluehub.manager.practiceadmin;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bluehub.bean.admin.SearchPhysicianForm;
import com.bluehub.dao.physician.PhysicianDao;
import com.bluehub.dao.practiceadmin.PracticeAdminDao;
import com.bluehub.dao.user.UserRegistrationDao;
import com.bluehub.vo.user.UserMappingVO;

// @Transactional(propagation = Propagation.REQUIRED)
public class PracticeAdminManager {
	private static Logger logger = Logger.getLogger(PracticeAdminManager.class);

	@Autowired
	private static PhysicianDao physicianDao;

	@Autowired
	private static PracticeAdminDao practiceAdminDao;

	/**
	 * @param practiceAdminDao
	 *            the practiceAdminDao to set
	 */
	public static void setPracticeAdminDao(PracticeAdminDao practiceAdminDao) {
		PracticeAdminManager.practiceAdminDao = practiceAdminDao;
	}

	/**
	 * sets the {@link UserRegistrationDao} as userRegistrationDao
	 * 
	 * @param userRegistrationDao
	 */
	public void setPhysicianDao(PhysicianDao physicianDao) {
		this.physicianDao = physicianDao;
	}

	public List<UserMappingVO> getPracticeAdminMap(Integer userId) {
		return practiceAdminDao.getPracticeAdminMap(userId);

	}

	public List<SearchPhysicianForm> getPhysicianMappingRecord(Integer userId,
			Integer organizationId, Integer practiceId) {
		return practiceAdminDao.getPhysicianMappingRecord(userId,
				organizationId, practiceId);

	}

}
