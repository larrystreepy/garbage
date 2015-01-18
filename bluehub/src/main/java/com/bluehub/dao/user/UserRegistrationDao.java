package com.bluehub.dao.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bluehub.bean.admin.SearchPhysicianForm;
import com.bluehub.bean.common.UserDetails;
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

public interface UserRegistrationDao {

	/**
	 * geta all {@link UserGroupVO}s from database
	 * 
	 * @return List<UserGroupVO>
	 */
	public List<UserGroupVO> getAllUserGroups();

	/**
	 * gets all {@link RoleVO}s from database
	 * 
	 * @return List<RoleVO>
	 */
	public List<RoleVO> getAllRoles();

	/**
	 * gets list of {@link RoleVO}s by userGroup
	 * 
	 * @param userGroup
	 * @return List<RoleVO>
	 */
	public List<RoleVO> getRolesByUserGroup(String userGroup);

	
	/**
	 * Inserts the users role in DB
	 * 
	 * @param userRoleVO
	 * @return int
	 */
	public void addUserRole(UserRoleVO userRoleVO);

	/**
	 * gets the user's rolefrom DB
	 * 
	 * @param roleName
	 * @return int
	 */
	public String getRoleById(final String roleName);

	/**
	 * updates a {@link RegistrationFormVO}
	 * 
	 * @param registrationForm1VO
	 */
	public void updateUserRegistration(UserVO registrationForm1VO);

	/**
	 * gets {@link RegistrationFormVO} by userEmail
	 * 
	 * @param userEmail
	 * @return RegistrationFormVO
	 */
	public UserVO getUserRegistrationByMail(String userEmail);

	public List<SearchPhysicianForm> getPhysicianDetails(
			String searchphysician, String orgid, String practiceid);

	public List<SearchPhysicianForm> getAllPhysicianDetails(String orgid,
			String practiceid, Map paramsMap);

	public List<UserVO> getPatientDetails(String userEmail);

	public List<UserVO> getSearchVisitDetails(String searchDate);

	public List<UserVO> getTagVisitDetails(String tag);

	public List<UserVO> getKeywordVisitDetails(String keyword);

	
	
	/**
	 * Checks the password in database with the entered password
	 * 
	 * @param userId
	 * @param password
	 */
	public boolean checkPasswordExist(int userId, String password);

	/**
	 * Checks the answer in database with the entered answer
	 * 
	 * @param mailId
	 * @param answer
	 */
	public UserVO checkAnswerCorrect(String mailId, String answer);

	/**
	 * gets list of {@link DocumentDetailsVO}s by type
	 * 
	 * @param type
	 * @return List<DocumentVO>
	 */
	public List<DocumentVO> getDocumentByType(String type);

	

	/**
	 * gets {@link RegistrationFormVO} by userId
	 * 
	 * @param userId
	 * @return RegistrationFormVO
	 */
	public UserVO getUserRegistrationByUserId(int userId);

	/**
	 * deletes a {@link DocumentDetailsVO} from database by emailId and docId
	 * 
	 * @param emailId
	 * @param docId
	 */
	public void deleteDocumentDetailsVOByEmailAndDocId(String emailId,
			Integer docId);


	/**
	 * check if a user with given email exists
	 * 
	 * @param userEmail
	 * @return int
	 */
	public int findEmailExist(String userEmail);

	
	/**
	 * gets userName by userId
	 * 
	 * @param userId
	 * @return String
	 */
	public String getUserName(int userId);

	public String getOrganizationName(int orgid);

	public Date getDob(int userId);

	/**
	 * check whether the given ssn already exists in database
	 * 
	 * @param ssn
	 * @return int
	 */
	public int findSsnExist(String ssn);

	/**
	 * gets list of {@link RoleVO}s by groupId
	 * 
	 * @param sqlQuery
	 * @return List<RoleVO>
	 */
	public List<RoleVO> getRolesByUserGroupId(String sqlQuery);

	public Integer saveUserRegistration(UserVO userDto);

	public List<RoleVO> findRoleVoByRoleId(String roleId);

	public int svaePhysicianmappingVo(PhysicianMappingVO mappingVo);

	public int savePatientMappingVo(PatientMappingVO mappingVo);

	public void updatePhysicianmappingVo(PhysicianMappingVO mappingVo);

	public PhysicianMappingVO findPhysicianMapping(int mappingId);

	public int savePatientRequestVo(PatientRequestVO mappingVo);

	public boolean checkUserAdditionalInfo(int userid);

	public int savePatientDocument(PatientDocument documentVO);

	public Object[] getPatientVisitRecords(Integer userId);

	public Long getTotalUsers(String userType);

	public void saveAuditTrails(AuditTrailVO dto);

	public List<Object[]> getAuditTrailDetails(Integer personId);

	public boolean checkPracticeAdminUser(int userid);

	public UserDetails getUserDetilsByUserId(Integer userId);

	public Long getOutstandingRequests(Integer providerId);

	public Long getAllPhysicianDetailsCOunt(String orgid, String practiceid,
			String searchphysician);

	public String getPrivateNoteByVisitAndPatientId(int visitid, Integer userId);

	public Long getTotlPatientsVisits();

	public Long getTotlPatientsVisits(Integer userid);

	public Long getOutstandingRequestsFromPhysician(Integer userid);

	public Long getAllPhysicianDetailsCOuntByPhysicianName(String searchVal);

	public List<Object[]> getAllPhysicianDetailsByPhysicianName(Map map);
	
	public Long updateSignature(Integer userid);
	
	public String checkSignature(Integer userid);
	
	public Integer checkFileStatus(String filename,Integer userid);
	
}
