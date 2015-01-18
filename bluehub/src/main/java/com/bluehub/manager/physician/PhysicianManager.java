package com.bluehub.manager.physician;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.bluehub.bean.admin.SearchPatientForm;
import com.bluehub.bean.common.UserDetails;
import com.bluehub.dao.physician.PhysicianDao;
import com.bluehub.dao.user.UserRegistrationDao;
import com.bluehub.util.Constants;
import com.bluehub.util.EmailValidator;
import com.bluehub.util.SendEmailWithAttachment;
import com.bluehub.vo.admin.FaxVo;
import com.bluehub.vo.common.EncounterVO;
import com.bluehub.vo.common.PatientPrivateNoteVO;
import com.bluehub.vo.common.RequestInfoOfPatientVO;
import com.bluehub.vo.common.ShareClinicalInfo;
import com.bluehub.vo.physician.UserPersonalInfoVO;
import com.bluehub.vo.physician.VisitsVO;
import com.bluehub.vo.user.PatientRequestVO;

// @Transactional(propagation = Propagation.REQUIRED)
public class PhysicianManager {
	private static Logger logger = Logger.getLogger(PhysicianManager.class);

	@Autowired
	private static PhysicianDao physicianDao;

	/**
	 * sets the {@link UserRegistrationDao} as userRegistrationDao
	 * 
	 * @param userRegistrationDao
	 */
	public void setPhysicianDao(PhysicianDao physicianDao) {
		this.physicianDao = physicianDao;
	}

	public int getSsnExist(String ssn) {
		return physicianDao.findSsnExist(ssn);
	}

	public int findOrgId(Integer physicianId) {
		return physicianDao.findOrgId(physicianId);
	}

	/**
	 * Handles file upload
	 * 
	 * @param fileUpload
	 * @param email
	 * @return String
	 */
	public String handleFileUpload(MultipartFile fileUpload, String email) {
		logger.info("UserManager: handleFileUpload() ===> starts.");
		String saveDirectory = null;
		String fileName = null;
		String syntax = null;
		saveDirectory = Constants.getPropertyValue(Constants.FILE_UPLOAD_PATH);
		syntax = Constants.getPropertyValue(Constants.FILE_UPLOAD_SYNTAX);

		// create upload directory
		File saveDir = new File(saveDirectory);
		if (!saveDir.exists()) {
			logger.info("creating directory: " + saveDirectory);
			boolean result = saveDir.mkdir();
			if (result) {
				logger.info("DIR created");
			}
		}

		// modify file's save location based on the user's email
		saveDirectory = saveDirectory + email + syntax;
		File theDir = new File(saveDirectory);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			logger.info("creating directory: " + email);
			boolean result = theDir.mkdir();
			if (result) {
				logger.info("DIR created");
			}
		}
		if (!fileUpload.getOriginalFilename().equals("")) {
			try {
				fileName = fileUpload.getOriginalFilename();
				// save the document
				fileUpload.transferTo(new File(saveDirectory
						+ fileUpload.getOriginalFilename()));
			} catch (IllegalStateException e) {
				logger.error(Constants.LOG_ERROR + e.getMessage());
			} catch (IOException e) {
				logger.error(Constants.LOG_ERROR + e.getMessage());
			}
		}
		logger.info("UserManager: handleFileUpload() ===> ends.");
		return fileName;
	}

	public int savePhysicianVisit(VisitsVO physicianDto) {
		// TODO Auto-generated method stub
		logger.info("PhysicianManager: savePhysicianVisit() ===> starts.");
		return physicianDao.savePhysicianVisit(physicianDto);
	}

	public void savePhysicianPersonalDetails(
			UserPersonalInfoVO physicianPersonalInfoDto) {
		logger.info("PhysicianManager: savePhysicianPersonalDetails() ===> starts.");
		physicianDao.savePhysicianPersonalDetails(physicianPersonalInfoDto);
		logger.info("PhysicianManager: savePhysicianPersonalDetails() ===> ends.");
	}

	public void savePatientPersonalDetails(
			UserPersonalInfoVO userPersonalInfoDto) {
		logger.info("PhysicianManager: savePhysicianPersonalDetails() ===> starts.");
		physicianDao.savePatientPersonalDetails(userPersonalInfoDto);
		logger.info("PhysicianManager: savePhysicianPersonalDetails() ===> ends.");
	}

	/*
	 * public List<UserPersonalInfoVO> getPatientDetails(String userEmail) {
	 * return physicianDao.getPatientDetails(userEmail); }
	 */

	public List<SearchPatientForm> getPatientDetails(String searchPatient,
			Map map) {
		return physicianDao.getPatientDetails(searchPatient, map);
	}

	public List<SearchPatientForm> getAllPatientDetails() {
		return physicianDao.getAllPatientDetails();
	}

	public List<VisitsVO> getSearchVisitRecords(String searchDate,
			Integer userId, String role, Map map) {
		return physicianDao
				.getSearchVisitRecords(searchDate, userId, role, map);
	}

	public List<Object[]> getPatientRequest(Integer userId,
			String role, Map map) {
		return physicianDao.getPatientRequest(userId, role, map);
	}

	public List<Object[]> getPhysicianVisitRecords(int patId, int phyId, Map map) {
		logger.info("PhysicianManager: getPhysicianVisitRecords() ===> starts.");
		return physicianDao.getPhysicianVisitRecords(patId, phyId, map);
	}

	public List<Object[]> getPhysicianOrganizations(int phyId) {
		logger.info("PhysicianManager: getPhysicianVisitRecords() ===> starts.");
		return physicianDao.getPhysicianOrganizations(phyId);
	}

	public List<Object[]> getAllVisitsByPhyId(int type, int phyId, String date,
			int patId, Map paramsMap) {
		return physicianDao.getAllVisitsByPhyId(type, phyId, date, patId,
				paramsMap);
	}

	public List<Object[]> getAllVisitsByAdmin(int type, int patId, int orgId) {
		return physicianDao.getAllVisitsByAdmin(type, patId, orgId);
	}

	public List<VisitsVO> getAllList(Integer userid, String role, Map map) {
		// TODO Auto-generated method stub
		return physicianDao.getAllList(userid, role, map);
	}

	public Long getAllListCount(Integer userid, String role, Map map) {
		// TODO Auto-generated method stub
		return physicianDao.getAllListCount(userid, role, map);
	}

	public void deletePhysicianVisit(VisitsVO physicianDto) {
		// TODO Auto-generated method stub
		logger.info("deletePhysicianVisit ===> starts.");
		physicianDao.deletePhysicianVisit(physicianDto);
	}

	public List<Object[]> getPhysicianVisitById(int id) {
		return physicianDao.getPhysicianVisitById(id);
	}

	public List<Object[]> getPhysicianVisitById(int id, Integer userid,
			String status,String role,Integer patientId, String searchDate) {
		return physicianDao.getPhysicianVisitById(id, userid, status,role,patientId,searchDate);
	}

	public List<Object[]> getAdminClinicalSearchByDate( String searchDate) {
		return physicianDao.getAdminClinicalSearchByDate(searchDate);
	}
	public List<Object[]> getSearchPhysicianVisitById(int id, Integer userid) {
		return physicianDao.getSearchPhysicianVisitById(id, userid);
	}

	public List<Object[]> getAdminSearchPhysicianVisitById(int id) {
		return physicianDao.getAdminSearchPhysicianVisitById(id);
	}
	
	public List<Object[]> getAdminSearchPatientVisitById(int id) {
		return physicianDao.getAdminSearchPatientVisitById(id);
	}
	
	public List<Object[]> getPhysicianSearchPatientVisitById(int patientid,int userid) {
		return physicianDao.getPhysicianSearchPatientVisitById(patientid,userid);
	}
	
	public List<Object[]> getPhysicianSearchVisitById(int id, String searchDate) {
		return physicianDao.getPhysicianSearchVisitById(id,searchDate);
	}
	
	public void updatePhysicianVisit(VisitsVO physicianDto) {
		logger.info("updatePhysicianVisit() starts.");
		physicianDao.updatePhysicianVisit(physicianDto);
	}

	public void updateTag(Integer visitid, String prescription) {
		logger.info("updateTag() starts.");
		physicianDao.updateTag(visitid, prescription);
	}

	public void savePrivateNote(PatientPrivateNoteVO patientPrivateNote) {
		logger.info("savePrivateNote() starts.");
		physicianDao.savePrivateNote(patientPrivateNote);
	}

	public void updatePrivateNote(String note, Integer noteid) {
		logger.info("savePrivateNote() starts.");
		physicianDao.updatePrivateNote(note, noteid);
	}

	public void saveEncounter(EncounterVO encounterVO) {
		logger.info("saveEncounter() starts.");
		physicianDao.saveEncounter(encounterVO);
	}

	public List<PatientPrivateNoteVO> checkNote(Integer visitid,String method, String type) {
		return physicianDao.checkNote(visitid,method,type);
	}

	public List<EncounterVO> getEncountersDetails(Integer visitid) {
		return physicianDao.getEncountersDetails(visitid);
	}

	public List<EncounterVO> editEncountersDetails(Integer encounterid) {
		return physicianDao.editEncountersDetails(encounterid);
	}

	public List<VisitsVO> getSearchPhysicianVisitRecords(String physicianid,
			Map map, Integer userId, String role) {
		return physicianDao.getSearchPhysicianVisitRecords(physicianid, map,
				userId, role);
	}

	public List<VisitsVO> getSearchPatientVisitRecords(String patientid, Map map) {
		return physicianDao.getSearchPatientVisitRecords(patientid, map);
	}

	//
	public List<VisitsVO> getTagVisitDetails(String tag, Map map) {
		return physicianDao.getTagVisitDetails(tag, map);
	}

	public List<VisitsVO> getKeywordVisitDetails(String keyword) {
		return physicianDao.getKeywordVisitDetails(keyword);
	}

	public List<Object[]> getAllDocuments(Integer userid, String status) {
		// TODO Auto-generated method stub
		return physicianDao.getAllDocuments(userid, status);
	}

	public VisitsVO findVisitDetails(Integer pkid) {
		// TODO Auto-generated method stub
		return physicianDao.findVisitDetails(pkid);
	}

	public Integer saveShareRequest(ShareClinicalInfo shareVo) {
		// TODO Auto-generated method stub
		return physicianDao.saveShareRequest(shareVo);
	}

	public List<Object[]> getPhysicianSharedDetails(Integer userId,
			String status) {
		// TODO Auto-generated method stub
		return physicianDao.getPhysicianSharedDetails(userId, status);
	}

	public List<Object[]> getPatientVisitDetails(Map map) {
		// TODO Auto-generated method stub
		return physicianDao.getPatientVisitDetails(map);
	}

	public ShareClinicalInfo finShareDetails(Integer shareId) {
		// TODO Auto-generated method stub
		return physicianDao.finShareDetails(shareId);
	}

	public void updateShareDetails(ShareClinicalInfo shareVo) {
		// TODO Auto-generated method stub
		physicianDao.updateShareDetails(shareVo);
	}

	public Boolean checkSharExpiry(Integer shareId) {
		// TODO Auto-generated method stub
		return physicianDao.checkSharExpiry(shareId);
	}

	public List<Object[]> getPatientShareVisitRecords(int type, int patId,
			String vDate) {
		logger.info("PhysicianManager: getPhysicianVisitRecords() ===> starts.");
		return physicianDao.getPatientShareVisitRecords(type, patId, vDate);
	}

	public String getPhysicianEmailId(int phyId) {
		return physicianDao.getPhysicianEmailId(phyId);
	}

	public List<Object[]> getVisitDetailsforShare(int visitId) {
		logger.info("PhysicianManager: getPhysicianVisitRecords() ===> starts.");
		return physicianDao.getVisitDetailsforShare(visitId);
	}

	public void sendSharePatientDetails(String phyEmail, String subject,
			String bodyContent, String serverUrl, Integer patId, String shareId,UserDetails userVo) {
		// if child check whether email is valid
		// if valid email send to child email otherwise send to parent
		Boolean validEmail = Boolean.FALSE;
		EmailValidator emailValidator = new EmailValidator();
		validEmail = emailValidator.validate(phyEmail);

		if (validEmail) {
			logger.info("valid email");
			SendEmailWithAttachment mailAttach = new SendEmailWithAttachment();
			// mailAttach.sendPatientShareMailWithAttachment("maheshwarmca@gmail.com",
			// "welcome", "Hello and Welcome", filePath, filenameArr);
			// mailAttach.sendPatientShareMailWithAttachment(phyEmail,
			// "welcome", bodyContent, filePath, filenameArr);
			mailAttach.sendPatientShareMail(phyEmail, subject, bodyContent,
					serverUrl, patId, shareId,userVo);
			// MailSupport.sendEmail(phyEmail, subject, bodyContent);
			// MailSupport.sendForgotPasswordMail(phyEmail, filePath,
			// bodyContent);
		}
	}

	public List<Object[]> getPatientRequestPending(int patId) {
		logger.info("PhysicianManager: getPatientRequestPending() ===> starts.");
		return physicianDao.getPatientRequestPending(patId);
	}

	public String getRequestStatus(Integer phyId, Integer patId) {
		return physicianDao.getRequestStatus(phyId, patId);
	}

	public List<Object[]> getPatientPersonalDetails(int patId) {
		logger.info("PhysicianManager: getPatientPersonalDetails() ===> starts.");
		return physicianDao.getPatientPersonalDetails(patId);
	}

	public List<Object[]> getAllDocumentsByUserId(Integer userid) {
		// TODO Auto-generated method stub
		return physicianDao.getAllDocumentsByUserId(userid);

	}
	
	public List<Object[]> getShareDocumentsByUserId(Integer userid) {
		// TODO Auto-generated method stub
		return physicianDao.getShareDocumentsByUserId(userid);

	}

	public List<Object[]> getAllDocumentsBypatIdInPhysician(Integer patid) {
		// TODO Auto-generated method stub
		return physicianDao.getAllDocumentsBypatIdInPhysician(patid);

	}

	public Integer saveRequestBehalfOfPatient(RequestInfoOfPatientVO infoVO) {
		// TODO Auto-generated method stub
		return physicianDao.saveRequestBehalfOfPatient(infoVO);
	}

	public List<Object[]> getPhysicianrequestedSharedInfo(Integer userId) {
		// TODO Auto-generated method stub
		return physicianDao.getPhysicianrequestedSharedInfo(userId);
	}

	public RequestInfoOfPatientVO findReqBehalfOfPatient(Integer requestId) {
		// TODO Auto-generated method stub
		return physicianDao.findReqBehalfOfPatient(requestId);
	}

	public void updateReqBehalfOfPatient(RequestInfoOfPatientVO reqVo) {
		// TODO Auto-generated method stub
		physicianDao.updateReqBehalfOfPatient(reqVo);
	}

	public List<Object[]> getPhysicianSharedInfoDetails(Map map) {
		// TODO Auto-generated method stub
		return physicianDao.getPhysicianSharedInfoDetails(map);
	}

	public Boolean checkRequestExpiry(Integer requestId) {
		// TODO Auto-generated method stub
		return physicianDao.checkRequestExpiry(requestId);
	}

	public List<Object[]> getAllDocumentsRequestBehalfOfPatient(
			Integer requestId, String status) {
		// TODO Auto-generated method stub
		return physicianDao.getAllDocumentsRequestBehalfOfPatient(requestId,
				status);
	}

	public List<Object[]> getPatientDetails(Map map) {
		// TODO Auto-generated method stub
		return physicianDao.getPatientDetails(map);
	}

	public List<UserPersonalInfoVO> getPatientAdditionalInformation(
			Integer patId) {
		// TODO Auto-generated method stub
		return physicianDao.getPatientAdditionalInformation(patId);
	}

	public List<UserPersonalInfoVO> getPhysicianAdditionalInformation(
			Integer phyId) {
		// TODO Auto-generated method stub
		return physicianDao.getPhysicianAdditionalInformation(phyId);
	}

	public void updatePersonalDetails(UserPersonalInfoVO userPersonalInfoDto) {
		// TODO Auto-generated method stub
		physicianDao.updatePersonalDetails(userPersonalInfoDto);
	}

	public void updatePhysicianPersonalDetails(
			UserPersonalInfoVO physicianPersonalInfoDto) {
		// TODO Auto-generated method stub
		physicianDao.updatePhysicianPersonalDetails(physicianPersonalInfoDto);
	}

	public Boolean checkAlreadyExistingPhysician(Map map) {
		// TODO Auto-generated method stub
		return physicianDao.checkAlreadyExistingPhysician(map);
	}

	public Long getAllVisitsountsByPhyId(Integer type, Integer phyId,
			String visitDate, Integer patientId) {
		// TODO Auto-generated method stub
		return physicianDao.getAllVisitsountsByPhyId(type, phyId, visitDate,
				patientId);
	}

	public Long getPatientDetailsCount(String searchPatient, Map paramsMap) {
		// TODO Auto-generated method stub
		return physicianDao.getPatientDetailsCount(searchPatient, paramsMap);
	}

	public Long getSearchPhysicianVisitRecordsCount(String physicianid, Map map) {
		// TODO Auto-generated method stub
		return physicianDao.getSearchPhysicianVisitRecordsCount(physicianid,
				map);
	}

	public Long getSearchVisitRecordsCount(String searchDate, Integer userId,
			String role, HashMap paramsMap) {
		// TODO Auto-generated method stub
		return physicianDao.getSearchVisitRecordsCount(searchDate, userId,
				role, paramsMap);
	}

	public Long getPatientRequestCount(Integer userId, String role,
			HashMap paramsMap) {
		// TODO Auto-generated method stub
		return physicianDao.getPatientRequestCount(userId, role, paramsMap);
	}

	public Long getTagVisitDetailsCount(String tag, Map paramsMap) {
		// TODO Auto-generated method stub
		return physicianDao.getTagVisitDetailsCount(tag, paramsMap);
	}

	public Long getSearchPatientVisitRecordsCount(String patientid, Map map) {
		// TODO Auto-generated method stub
		return physicianDao.getSearchPatientVisitRecordsCount(patientid, map);
	}

	public Long getPhysicianVisitRecordsCount(Integer patientId, int i, Map map) {
		// TODO Auto-generated method stub
		return physicianDao.getPhysicianVisitRecordsCount(patientId, i, map);
	}

	public String getOrganizationNameByPatientId(Integer patientId) {
		// TODO Auto-generated method stub
		return physicianDao.getOrganizationNameByPatientId(patientId);
	}

	public void updatePatientRequestVo(Integer requestId) {
		// TODO Auto-generated method stub
		physicianDao.updatePatientRequestVo(requestId);
	}

	public Date getVisitDateByVisitId(Integer visitId) {
		// TODO Auto-generated method stub
		return physicianDao.getVisitDateByVisitId(visitId);
	}

	public List<Object[]> getAllPatientDetailsForFax() {
		// TODO Auto-generated method stub
		return physicianDao.getAllPatientDetailsForFax();
	}

	public List<Object[]> getEfaxAssocoatedPatientDocuments(Integer patientId) {
		// TODO Auto-generated method stub
		return physicianDao.getEfaxAssocoatedPatientDocuments(patientId);
	}

	public void deleteDocument(Integer docId) {
		// TODO Auto-generated method stub
		physicianDao.deleteDocument(docId);
	}

	public Object[] getPhysicianDetails(Integer physicianid) {
		// TODO Auto-generated method stub
		return physicianDao.getPhysicianDetails(physicianid);
	}

	public String getOrganizationIdForPatient(Integer orgId, Integer patientId) {
		// TODO Auto-generated method stub
		return physicianDao.getOrganizationIdForPatient(orgId,patientId);
	}

	public Integer findPatientOrganizationId(Integer userId) {
		// TODO Auto-generated method stub
		return physicianDao.findPatientOrganizationId(userId);
	}

	public Object[] getPatientPersonalDetailsForEfax(Integer userId) {
		// TODO Auto-generated method stub
		return physicianDao.getPatientPersonalDetailsForEfax(userId);
	}

	public Integer saveEFaxDetails(FaxVo faxVo) {
		// TODO Auto-generated method stub
		return physicianDao.saveEFaxDetails(faxVo);
	}

	public void updateShareStatus(Integer shareId) {
		physicianDao.updateShareStatus(shareId);
	}

	public List<Object[]> getAllSharedDocuments(Integer shareId, String status) {
		// TODO Auto-generated method stub
		return physicianDao.getAllSharedDocuments(shareId,status);
	}


}
