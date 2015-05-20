package com.bluehub.manager.user;

import com.bluehub.bean.admin.SearchPhysicianForm;
import com.bluehub.bean.common.UserDetails;
import com.bluehub.dao.user.UserRegistrationDao;
import com.bluehub.exception.BlueHubBusinessException;
import com.bluehub.exception.BlueHubRuntimeException;
import com.bluehub.util.CommonUtils;
import com.bluehub.util.Constants;
import com.bluehub.util.EmailValidator;
import com.bluehub.util.MailSupport;
import com.bluehub.vo.admin.PatientMappingVO;
import com.bluehub.vo.admin.PhysicianMappingVO;
import com.bluehub.vo.common.AuditTrailVO;
import com.bluehub.vo.common.PatientDocument;
import com.bluehub.vo.user.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

// @Transactional(propagation = Propagation.REQUIRED)
public class UserManager {
	private static Logger logger = Logger.getLogger(UserManager.class);

	@Autowired
	private UserRegistrationDao userRegistrationDao;

	/**
	 * sets the {@link UserRegistrationDao} as userRegistrationDao
	 * 
	 * @param userRegistrationDao
	 */
	public void setUserRegistrationDao(UserRegistrationDao userRegistrationDao) {
		this.userRegistrationDao = userRegistrationDao;
	}

	/**
	 * get all the user groups data.
	 * 
	 * @return collection of UserGroupVO object.
	 */
	@SuppressWarnings("unchecked")
	public List<UserGroupVO> getUserGroups() {
		return userRegistrationDao.getAllUserGroups();
	}

	/**
	 * get the roles data by group.
	 * 
	 * @param userGroup
	 *            as string.
	 * @return the collection of RoleVO object.
	 */
	@SuppressWarnings("unchecked")
	public List<RoleVO> getRoleNamesByUserGroup(String userGroup) {
		return userRegistrationDao.getRolesByUserGroup(userGroup);
	}

	/**
	 * get the User data based on userid.
	 * 
	 * @param userId
	 *            as integer.
	 * @return RegistrationFormVO as RegistrationFormVO object.
	 */
	public UserVO getUserRegistrationByUserId(Integer userId) {
		logger.info("userManager getUserRegistrationByUserId() starts");
        return userRegistrationDao.getUserRegistrationByUserId(userId);
	}

	/**
	 * update user data's into the database.
	 * 
	 * @param registrationFormVO
	 *            as RegistrationFormVO object.
	 */
	public void updateUserRegistration(UserVO registrationFormVO) {
		logger.info("UserManager updateUserRegistration() starts.");
		userRegistrationDao.updateUserRegistration(registrationFormVO);
	}

	/**
	 * insert user role into the database.
	 * 
	 * @param userRoleVO
	 *            as UserRoleVO object.
	 * 
	 */
	public void addUserRole(UserRoleVO userRoleVO) {
		logger.info("UserManager addUserRole() starts.");
		userRegistrationDao.addUserRole(userRoleVO);
	}

	/**
	 * gets the roleId
     *
	 * @return roleId
	 */
	public String getRoleById(String roleName) throws BlueHubBusinessException {
		String roleId = userRegistrationDao.getRoleById(roleName);
		if (roleId == null) {
			throw new BlueHubBusinessException("ROLE_NOT", null);
		}
		return roleId;
	}

	/**
	 * get the user data based on Email.
	 * 
	 * @param userEmail
	 *            as String
	 * @return RegistrationFormVO object.
	 */
	public UserVO getUserRegistrationDataByMailId(String userEmail) {
		return userRegistrationDao.getUserRegistrationByMail(userEmail);
	}

	public List<SearchPhysicianForm> getPhysicianDetails(String searchphysician, String orgid, String practiceid) {
		return userRegistrationDao.getPhysicianDetails(searchphysician, orgid, practiceid);
	}

	public List<SearchPhysicianForm> getAllPhysicianDetails(String orgid,
			String practiceid, Map paramsMap) {
		return userRegistrationDao.getAllPhysicianDetails(orgid, practiceid,
				paramsMap);
	}

	public List<UserVO> getPatientDetails(String userEmail) {
		return userRegistrationDao.getPatientDetails(userEmail);
	}

	public List<UserVO> getSearchVisitDetails(String searchDate) {
		return userRegistrationDao.getSearchVisitDetails(searchDate);
	}

	/*
	 * public List<UserVO> getTagVisitDetails(String tag) { return
	 * userRegistrationDao.getTagVisitDetails(tag); }
	 * 
	 * public List<UserVO> getKeywordVisitDetails(String keyword) { return
	 * userRegistrationDao.getKeywordVisitDetails(keyword); }
	 */

	/**
	 * get the given email id is exists or not.
	 * 
	 * @param userEmail as string
	 * @return integer value.
	 */
	public int getEmailExist(String userEmail) {
		return userRegistrationDao.findEmailExist(userEmail);
	}

	/**
	 * Handles file upload
	 * @return String
	 */
	public String handleFileUpload(MultipartFile fileUpload, String emailId) {
		logger.info("UserManager: handleFileUpload() ===> starts.");
		String fileName = null;
        final String originalFilename = fileUpload.getOriginalFilename();
        if (logger.isDebugEnabled()) logger.debug("fileName : " + originalFilename + "===" + fileUpload.getContentType());

		if (!originalFilename.equals("")) {
			try {
                File saveDir = CommonUtils.createFileUploadSubDir(emailId);
				fileName = originalFilename;
				fileUpload.transferTo(new File(saveDir, originalFilename)); // save the document
			} catch (IllegalStateException | IOException e) {
				logger.error(Constants.LOG_ERROR + e.getMessage(), e);
			}
        }
		logger.info("UserManager: handleFileUpload() ===> ends.");
		return fileName;
	}
	public String handleFileUploadPatient(MultipartFile fileUpload, String emailId) {
		logger.info("UserManager: handleFileUploadPatient() ===> starts.");
		String fileName = null;
        final String originalFilename = fileUpload.getOriginalFilename();
        if (logger.isDebugEnabled()) logger.debug("fileName : " + originalFilename + "===" + fileUpload.getContentType());

		if(fileUpload.getContentType().equalsIgnoreCase("application/pdf")){

			if (!originalFilename.equals("")) {
                // create upload directory
                File saveDir = CommonUtils.createFileUploadSubDir(emailId);

				try {
					fileName = originalFilename;
					// save the document
					fileUpload.transferTo(new File(saveDir, originalFilename));
				} catch (IllegalStateException | IOException e) {
					logger.error(Constants.LOG_ERROR + e.getMessage());
				}
            }
		}else{
            logger.warn("else file not pdf: " + originalFilename + " is " + fileUpload.getContentType());
			fileName = null;
		}
		
		logger.info("UserManager: handleFileUploadPatient() ===> ends.");
		return fileName;
	}
	
	public boolean checkPasswordExist(int userId, String password)
			throws BlueHubBusinessException, BlueHubRuntimeException {
		logger.info("UserManager checkPasswordExist() ends.");
		// to encode the password
		if (password != null) {
			Md5PasswordEncoder ms = new Md5PasswordEncoder();
			password = ms.encodePassword(password, null);
		} else {
			throw new BlueHubBusinessException(Constants.PASSWORD_MISMATCH, null);
		}

		logger.info("UserManager checkPasswordExist() ends.");
		return userRegistrationDao.checkPasswordExist(userId, password);
	}

	/**
	 * Retrieves all documents for a specific type.
	 * 
	 * @param type
	 *            as string
	 * @return documents as collection of DocumentVO object.
	 */
	public List<DocumentVO> getDocumentsByType(String type) {
		logger.info("UserManager: getDocumentsByType() ===> starts.");
		List<DocumentVO> documents = userRegistrationDao.getDocumentByType(type);
		logger.info("UserManager: getDocumentsByType() ===> ends.");
		return documents;
	}

	public UserVO checkAnswerCorrect(String emailId, String answer)
			throws Exception {
		logger.info("UserManager checkAnswerCorrect() starts.");
		UserVO registrationFormVO = userRegistrationDao.checkAnswerCorrect(
				emailId, answer);
		if (registrationFormVO == null) {
			throw new BlueHubBusinessException(Constants.ANSWER_MISMATCH, null);
		}
		logger.info("UserManager checkAnswerCorrect() ends.");
		return registrationFormVO;
	}

	public String getUserName(int userId) {
		logger.info("userManager getUserName() starts");
		return userRegistrationDao.getUserName(userId);
	}

	public String getOrganizationName(int orgid) {
		logger.info("userManager getOrganizationName() starts");
		return userRegistrationDao.getOrganizationName(orgid);
	}

	public Date getDob(int userId) {
		logger.info("userManager getDob() starts");
		return userRegistrationDao.getDob(userId);
	}

	/**
	 * Newly generated password will be updated in DB and mail will be sent to
	 * user
	 */
	public void updateForgotPassword(UserVO registrationFormVO, String userPassword) {
		logger.info("UserManager updateForgotPassword() starts.");

		String userName = registrationFormVO.getUserName();
        String userEmail = registrationFormVO.getUserName();
		RoleVO roleVO = registrationFormVO.getRole();

		// if child check whether email is valid
		// if valid email send to child email otherwise send to parent
		if (roleVO.getRoleName().equals(Constants.CHILD)) {
			boolean validEmail = EmailValidator.validate(userEmail);

			if (validEmail) {
				MailSupport.sendForgotPasswordMail(userEmail, userName, userPassword);
			}
		} else {
			MailSupport.sendForgotPasswordMail(userEmail, userName, userPassword);
		}
		userRegistrationDao.updateUserRegistration(registrationFormVO);
		logger.info("UserManager updateForgotPassword() ends.");
	}

	/**
	 * deletes DocumentDetailsVO from db by emailId and docId
	 */
	public void deleteDocumentDetailsVOByEmailAndDocId(String emailId, Integer docId) {
		logger.info("UserManager: deleteDocumentDetailsVOByEmailAndDocId() ===> starts.");
		userRegistrationDao.deleteDocumentDetailsVOByEmailAndDocId(emailId, docId);
	}

	public Integer saveUserRegistration(UserVO userDto) {

		logger.info("UserManager: saveUserRegistration() ===> starts.");
		return userRegistrationDao.saveUserRegistration(userDto);
	}

	public List<RoleVO> findRoleVoByRoleId(String roleId) {

		logger.info("UserManager: findRoleVoByRoleId() ===> starts.");
		return userRegistrationDao.findRoleVoByRoleId(roleId);
	}

	public int svaePhysicianmappingVo(PhysicianMappingVO mappingVo) {

		return userRegistrationDao.svaePhysicianmappingVo(mappingVo);

	}

	public int savePatientMappingVo(PatientMappingVO mappingVo) {

		return userRegistrationDao.savePatientMappingVo(mappingVo);

	}

	public void updatePhysicianmappingVo(PhysicianMappingVO mappingVo) {

		userRegistrationDao.updatePhysicianmappingVo(mappingVo);
	}

	public PhysicianMappingVO findPhysicianMapping(int mappingId) {

		return userRegistrationDao.findPhysicianMapping(mappingId);
	}

	public int savePatientRequestVo(PatientRequestVO mappingVo) {

		return userRegistrationDao.savePatientRequestVo(mappingVo);

	}

	public boolean checkUserAdditionalInfo(int userid) {

		return userRegistrationDao.checkUserAdditionalInfo(userid);

	}

	public int savePatientDocument(PatientDocument documentVO) {

		return userRegistrationDao.savePatientDocument(documentVO);

	}

	public Object[] getPatientVisitRecords(Integer userId) {

		return userRegistrationDao.getPatientVisitRecords(userId);
	}

	public Long getTotalUsers(String userType) {

		return userRegistrationDao.getTotalUsers(userType);
	}

	public void saveAuditTrails(AuditTrailVO dto) {

		userRegistrationDao.saveAuditTrails(dto);
	}

	public List<Object[]> getAuditTrailDetails(Integer personId) {

		return userRegistrationDao.getAuditTrailDetails(personId);
	}

	public boolean checkPracticeAdminUser(int userid) {

		return userRegistrationDao.checkPracticeAdminUser(userid);
	}

	public UserDetails getUserDetilsByUserId(Integer userId) {

		return userRegistrationDao.getUserDetilsByUserId(userId);

	}

	public Long getOutstandingRequests(Integer providerId) {

		return userRegistrationDao.getOutstandingRequests(providerId);
	}

	public Long getAllPhysicianDetailsCOunt(String orgid, String practiceid,
			String searchphysician) {

		return userRegistrationDao.getAllPhysicianDetailsCOunt(orgid,
				practiceid, searchphysician);
	}

	public String getPrivateNoteByVisitAndPatientId(int visitid, Integer userId) {

		return userRegistrationDao.getPrivateNoteByVisitAndPatientId(visitid,
				userId);
	}

	public Long getTotlPatientsVisits() {
		return userRegistrationDao.getTotlPatientsVisits();
	}

	public Long getTotlPatientsVisits(Integer userid) {

		return userRegistrationDao.getTotlPatientsVisits(userid);
	}

	public Long getOutstandingRequestsFromPhysician(Integer userid) {
		return userRegistrationDao.getOutstandingRequestsFromPhysician(userid);
	}

	public Long getAllPhysicianDetailsCOuntByPhysicianName(String searchVal) {
		return userRegistrationDao.getAllPhysicianDetailsCOuntByPhysicianName(searchVal);
	}

	public List<Object[]> getAllPhysicianDetailsByPhysicianName(Map map) {
		return userRegistrationDao.getAllPhysicianDetailsByPhysicianName(map);
	}
	public Long updateSignature(Integer userid) {
		return userRegistrationDao.updateSignature(userid);
	}

	public String checkSignature(Integer userid) {
		return userRegistrationDao.checkSignature(userid);
	}
	
	public Integer checkFileStatus(String filename,Integer userid) {
		return userRegistrationDao.checkFileStatus(filename,userid);
	}

}
