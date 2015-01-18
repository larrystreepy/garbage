package com.bluehub.dao.admin;

import java.util.List;
import java.util.Map;

import com.bluehub.bean.admin.SearchPatientForm;
import com.bluehub.bean.admin.SearchPhysicianForm;
import com.bluehub.vo.admin.AdminOrganizationVO;
import com.bluehub.vo.admin.AdminPracticeVO;
import com.bluehub.vo.admin.PhysicianMappingVO;
import com.bluehub.vo.common.ShareClinicalInfo;

public interface AdminOrganizationDao {


	/**
	 * Inserts the RegistrationFOrmVO in the DB
	 * 
	 * @param registrationFormVO
	 * @return int
	 */
	public void saveAdminOrganization(AdminOrganizationVO adminOrganizationVO);

	public List<AdminOrganizationVO> getAdminOrganizaion(Map map);

	
	public void deleteAdminOrganization(Integer orgid);

	public void deleteAdminEncounter(Integer encounterid);

	public void updateAdminEncounter(String encounter, Integer encounterid);

	public List<Object[]> getPhysicianMapping(Map paramsMap);

	public void deletePhysicianMapping(PhysicianMappingVO mappingVo);

	public List<AdminPracticeVO> getPracticeList(Integer orgid);

	public List<String> getPhysicianList();

	public List<AdminOrganizationVO> editAdminOrganization(
			AdminOrganizationVO adminOrganizationVO, int id);

	public AdminOrganizationVO geAdminOrganizationVOById(int id);

	public void updateAdminOrganization(AdminOrganizationVO adminOrganizationVO);

	public List<SearchPatientForm> getPatientDetails(String userEmail,
			String searchVal);

	public void setStatus(String userId);

	public void setStatusDisable(String userId);

	public List<Object[]> findPhysicianMapping(Integer mappingId);

	public int findOrganizationNameExist(String name);

	public int findPracticeNameExist(String name, Integer orgId);

	public List<SearchPhysicianForm> getPhysicianDetails(String name,
			String orgName, String practName);

	public void setPhysicianPracticeAdminStatus(String userId);

	public void removePhysicianPracticeAdminStatus(String userId);

	public Integer getPhysicianMappingCount();

	public String getUserRoleByUserId(Integer userid);

	public List<ShareClinicalInfo> getListOfPatientShareVo(Integer userId);
	
	public Integer getPatientFaxRequestCount(Integer userId);

	public Long getAdminOrganizaionCount(Map map);

	public List<AdminOrganizationVO> getAdminOrganizaions();
}
