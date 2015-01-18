/**
 * 
 */
package com.bluehub.manager.admin;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.bluehub.dao.admin.AdminPracticeDao;
import com.bluehub.vo.admin.AdminPracticeVO;


public class AdminPracticeManager {
	private static Logger logger = Logger.getLogger(AdminPracticeManager.class);
	
	@Autowired
	private AdminPracticeDao adminPracticeDao;



	/**
	 * @param adminPracticeDao the adminPracticeDao to set
	 */
	public void setAdminPracticeDao(AdminPracticeDao adminPracticeDao) {
		this.adminPracticeDao = adminPracticeDao;
	}
	
			
	public void saveAdminPractice(AdminPracticeVO PracticeDto) {
		// TODO Auto-generated method stub
		logger.info("saveAdminPractice ===> starts.");
	
		adminPracticeDao.saveAdminPractice(PracticeDto);
	}
	
	/*public void deleteAdminPractice(AdminPracticeVO PracticeDto) {
		// TODO Auto-generated method stub
		logger.info("deleteAdminPractice ===> starts.");
	
		adminPracticeDao.deleteAdminPractice(PracticeDto);
	}*/
	
	public void deleteAdminPractice(Integer practiceid) {
		// TODO Auto-generated method stub
		logger.info("deleteAdminPractice ===> starts.");
	
		adminPracticeDao.deleteAdminPractice(practiceid);
	}
	
	public List<AdminPracticeVO> editAdminPractice(AdminPracticeVO PracticeDto,int id) {
		// TODO Auto-generated method stub
		logger.info("editAdminPractice ===> starts.");
	
		return adminPracticeDao.editAdminPractice(PracticeDto,id);
	}
	
	public AdminPracticeVO geAdminPracticeVOById(int id){
		return adminPracticeDao.geAdminPracticeVOById(id);
	}
	
	public void updateAdminPractice(AdminPracticeVO PracticeDto) {
		logger.info("updateAdminPractice() starts.");
		adminPracticeDao.updateAdminPractice(PracticeDto);
	}
	
		public List<AdminPracticeVO> getPracticeList() {
		// TODO Auto-generated method stub
		return adminPracticeDao.getPracticeList();
	}

		public List<AdminPracticeVO> getAdminPractice(Map map) {
			// TODO Auto-generated method stub
			logger.info("getAdminPractice");
		
			return adminPracticeDao.getAdminPractice(map);
		}


		public AdminPracticeVO findPracticeById(Integer practiceId) {
			// TODO Auto-generated method stub
			return adminPracticeDao.findPracticeById(practiceId);
		}


		public Long getAdminPracticeCount(Map map) {
			// TODO Auto-generated method stub
			return adminPracticeDao.getAdminPracticeCount(map);
		}
}
