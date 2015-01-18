/**
 * 
 */
package com.bluehub.manager.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bluehub.bean.admin.AdminSearchDateForm;
import com.bluehub.bean.admin.SearchPatientForm;
import com.bluehub.bean.admin.SearchPhysicianForm;
import com.bluehub.dao.admin.AdminOrganizationDao;
import com.bluehub.vo.admin.AdminOrganizationVO;
import com.bluehub.vo.admin.AdminPracticeVO;
import com.bluehub.vo.admin.PhysicianMappingVO;
import com.bluehub.vo.common.ShareClinicalInfo;

public class AdminOrganizationManager {
	private static Logger logger = Logger.getLogger(AdminOrganizationManager.class);
	
	@Autowired
	private AdminOrganizationDao adminOrganizationDao;
	

	
	public void setAdminOrganizationDao(AdminOrganizationDao adminOrganizationDao) {
		this.adminOrganizationDao = adminOrganizationDao;
	}

	
	public List<AdminOrganizationVO> getAdminOrganizaion(Map map) {
		// TODO Auto-generated method stub
		logger.info("getAdminOrganizaion");
	
		return adminOrganizationDao.getAdminOrganizaion(map);
	}
	
	public List<AdminOrganizationVO> getPhysicianDetails() {
		// TODO Auto-generated method stub
		logger.info("getAdminOrganizaion");
		return null;
	
//		return adminOrganizationDao.getPhysicianDetails();
	}
	
		
	public void saveAdminOrganization(AdminOrganizationVO organizationDto) {
		// TODO Auto-generated method stub
		logger.info("saveAdminOrganization ===> starts.");
	
		adminOrganizationDao.saveAdminOrganization(organizationDto);
	}
	
	/*public void deleteAdminOrganization(AdminOrganizationVO organizationDto) {
		// TODO Auto-generated method stub
		logger.info("deleteAdminOrganization ===> starts.");
	
		adminOrganizationDao.deleteAdminOrganization(organizationDto);
	}*/
	
	public void deleteAdminOrganization(Integer orgid) {
		// TODO Auto-generated method stub
		logger.info("deleteAdminOrganization ===> starts.");
	
		adminOrganizationDao.deleteAdminOrganization(orgid);
	}
	
	
	public void deleteAdminEncounter(Integer encounterid) {
		// TODO Auto-generated method stub
		logger.info("deleteAdminEncounter ===> starts.");
	
		adminOrganizationDao.deleteAdminEncounter(encounterid);
	}

	public void updateAdminEncounter(String encounter,Integer encounterid) {
		// TODO Auto-generated method stub
		logger.info("deleteAdminEncounter ===> starts.");
	
		adminOrganizationDao.updateAdminEncounter(encounter,encounterid);
	}

	public List<Object[]> getPhysicianMapping(Map paramsMap) {
		// TODO Auto-generated method stub
		return adminOrganizationDao.getPhysicianMapping(paramsMap);
	}


	public List<AdminOrganizationVO> editAdminOrganization(AdminOrganizationVO organizationDto,int id) {
		// TODO Auto-generated method stub
		logger.info("editAdminOrganization ===> starts.");
	
		return adminOrganizationDao.editAdminOrganization(organizationDto,id);
	}
	
	public AdminOrganizationVO geAdminOrganizationVOById(int id){
		return adminOrganizationDao.geAdminOrganizationVOById(id);
	}
	
	public void updateAdminOrganization(AdminOrganizationVO organizationDto) {
		logger.info("updateAdminOrganization() starts.");
		adminOrganizationDao.updateAdminOrganization(organizationDto);
	}


	public void deletePhysicianMapping(PhysicianMappingVO mappingVo) {
		// TODO Auto-generated method stub
		adminOrganizationDao.deletePhysicianMapping(mappingVo);
	}

	
	
	public List<AdminPracticeVO> getPracticeList(Integer orgid) {
		// TODO Auto-generated method stub
		return adminOrganizationDao.getPracticeList(orgid);
	}


	public List<String> getPhysicianList() {
		// TODO Auto-generated method stub
		return adminOrganizationDao.getPhysicianList();
	}
	
	public List<SearchPatientForm> getPatientDetails(String userEmail,String searchVal) {
		return adminOrganizationDao.getPatientDetails(userEmail,searchVal);
	}
	public void setStatus(String userId) {
		 adminOrganizationDao.setStatus(userId);
	}
	public void setStatusDisable(String userId) {
		 adminOrganizationDao.setStatusDisable(userId);
	}


	public List<Object[]> findPhysicianMapping(Integer mappingId) {
		// TODO Auto-generated method stub
		return adminOrganizationDao.findPhysicianMapping(mappingId);
	}
	
	public int getOrganizationNameExist(String name) {
		return adminOrganizationDao.findOrganizationNameExist(name);
	}
	
	public int getPractiveNameExist(String name, Integer orgId) {
		return adminOrganizationDao.findPracticeNameExist(name,orgId);
	}
	
	public List<SearchPhysicianForm> getPhysicianDetails(String phyName,String orgName,String practName) {
		return adminOrganizationDao.getPhysicianDetails(phyName,orgName,practName);
	}
	
	public void setPhysicianPracticeAdminStatus(String userId) {
		 adminOrganizationDao.setPhysicianPracticeAdminStatus(userId);
	}
	
	public void removePhysicianPracticeAdminStatus(String userId) {
		 adminOrganizationDao.removePhysicianPracticeAdminStatus(userId);
	}


	public Integer getPhysicianMappingCount() {
		// TODO Auto-generated method stub
		return adminOrganizationDao.getPhysicianMappingCount();
	}


	public String getUserRoleByUserId(Integer userid) {
		// TODO Auto-generated method stub
		return adminOrganizationDao.getUserRoleByUserId(userid);
	}


	public List<ShareClinicalInfo> getListOfPatientShareVo(Integer userId) {
		// TODO Auto-generated method stub
		return adminOrganizationDao.getListOfPatientShareVo(userId);
	}


	public Integer getPatientFaxRequestCount(Integer userid)
	{
		// TODO Auto-generated method stub
				return adminOrganizationDao.getPatientFaxRequestCount(userid);
	}
	
	public Long getAdminOrganizaionCount(Map map) {
		// TODO Auto-generated method stub
		return adminOrganizationDao.getAdminOrganizaionCount(map);
	}


	public List<AdminOrganizationVO> getAdminOrganizaions() {
		// TODO Auto-generated method stub
		return adminOrganizationDao.getAdminOrganizaions();
	}
}
