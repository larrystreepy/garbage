package com.bluehub.manager.physician;

import com.bluehub.bean.admin.SearchPatientForm;
import com.bluehub.bean.common.UserDetails;
import com.bluehub.dao.physician.PhysicianDao;
import com.bluehub.dao.user.UserRegistrationDao;
import com.bluehub.util.*;
import com.bluehub.vo.admin.FaxVo;
import com.bluehub.vo.common.EncounterVO;
import com.bluehub.vo.common.PatientPrivateNoteVO;
import com.bluehub.vo.common.RequestInfoOfPatientVO;
import com.bluehub.vo.common.ShareClinicalInfo;
import com.bluehub.vo.physician.UserPersonalInfoVO;
import com.bluehub.vo.physician.VisitsVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @Transactional(propagation = Propagation.REQUIRED)
public class PhysicianManager {
	private static Logger logger = Logger.getLogger(PhysicianManager.class);

	@Autowired
	private static PhysicianDao physicianDao;

	/**
	 * sets the {@link UserRegistrationDao} as userRegistrationDao
	 */
	public void setPhysicianDao(PhysicianDao physicianDao) {
		PhysicianManager.physicianDao = physicianDao;
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

	public int savePhysicianVisit(VisitsVO physicianDto) {
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
		return physicianDao.getAllList(userid, role, map);
	}

	public Long getAllListCount(Integer userid, String role, Map map) {
		return physicianDao.getAllListCount(userid, role, map);
	}

	public void deletePhysicianVisit(VisitsVO physicianDto) {
		logger.info("deletePhysicianVisit ===> starts.");
		physicianDao.deletePhysicianVisit(physicianDto);
	}

	public List<Object[]> getPhysicianVisitById(int id) {
		return physicianDao.getPhysicianVisitById(id);
	}

	public List<Object[]> getPhysicianVisitById(int id, Integer userid,
			String status,String role,Integer patientId, String searchDate) {
		return physicianDao.getPhysicianVisitById(id, userid, status, role, patientId, searchDate);
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
		return physicianDao.getPhysicianSearchPatientVisitById(patientid, userid);
	}
	
	public List<Object[]> getPhysicianSearchVisitById(int id, String searchDate) {
		return physicianDao.getPhysicianSearchVisitById(id, searchDate);
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
		return physicianDao.getAllDocuments(userid, status);
	}

	public VisitsVO findVisitDetails(Integer pkid) {
		return physicianDao.findVisitDetails(pkid);
	}

	public Integer saveShareRequest(ShareClinicalInfo shareVo) {
		return physicianDao.saveShareRequest(shareVo);
	}

	public List<Object[]> getPhysicianSharedDetails(Integer userId, String status) {
		return physicianDao.getPhysicianSharedDetails(userId, status);
	}

	public List<Object[]> getPatientVisitDetails(Map map) {
		return physicianDao.getPatientVisitDetails(map);
	}

	public ShareClinicalInfo finShareDetails(Integer shareId) {
		return physicianDao.finShareDetails(shareId);
	}

	public void updateShareDetails(ShareClinicalInfo shareVo) {
		physicianDao.updateShareDetails(shareVo);
	}

	public Boolean checkSharExpiry(Integer shareId) {
		return physicianDao.checkSharExpiry(shareId);
	}

	public List<Object[]> getPatientShareVisitRecords(int type, int patId, String vDate) {
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
			String bodyContent, String serverUrl, Integer patId, String shareId,UserDetails physUserVO) {

		boolean validEmail = EmailValidator.validate(phyEmail);

		if (validEmail) {
			logger.info("valid email");

			subject = Constants.getPropertyValue(Constants.PATIENT_SHARE_SUBJECT);
			final String message = Constants.getPropertyValue(Constants.PATIENT_SHARE_MESSAGE);
			final String linkText = Constants.getPropertyValue(Constants.PATIENT_SHARE_LINK_TEXT);

			final String link = String.format("  <a href='%s/physician/viewShareRequest.do?shareId=%s'>%s</a>",
					serverUrl, shareId, linkText);

			bodyContent = "<html><body>"
					+ "Dear " + physUserVO.getUsername() + ",<br><br>"
					+ message
					+ link
					+ "</body></html>";

			MailSupport.sendBasicEmail(phyEmail, subject, bodyContent);
		} else
		{
			logger.warn("Invalid email: " + phyEmail);
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
		return physicianDao.getAllDocumentsByUserId(userid);
	}
	
	public List<Object[]> getShareDocumentsByUserId(Integer userid) {
		return physicianDao.getShareDocumentsByUserId(userid);
	}

	public List<Object[]> getAllDocumentsBypatIdInPhysician(Integer patid) {
		return physicianDao.getAllDocumentsBypatIdInPhysician(patid);
	}

	public Integer saveRequestBehalfOfPatient(RequestInfoOfPatientVO infoVO) {
		return physicianDao.saveRequestBehalfOfPatient(infoVO);
	}

	public List<Object[]> getPhysicianrequestedSharedInfo(Integer userId) {
		return physicianDao.getPhysicianrequestedSharedInfo(userId);
	}

	public RequestInfoOfPatientVO findReqBehalfOfPatient(Integer requestId) {
		return physicianDao.findReqBehalfOfPatient(requestId);
	}

	public void updateReqBehalfOfPatient(RequestInfoOfPatientVO reqVo) {
		physicianDao.updateReqBehalfOfPatient(reqVo);
	}

	public List<Object[]> getPhysicianSharedInfoDetails(Map map) {
		return physicianDao.getPhysicianSharedInfoDetails(map);
	}

	public Boolean checkRequestExpiry(Integer requestId) {
		return physicianDao.checkRequestExpiry(requestId);
	}

	public List<Object[]> getAllDocumentsRequestBehalfOfPatient(Integer requestId, String status) {
		return physicianDao.getAllDocumentsRequestBehalfOfPatient(requestId, status);
	}

	public List<Object[]> getPatientDetails(Map map) {
		return physicianDao.getPatientDetails(map);
	}

	public List<UserPersonalInfoVO> getPatientAdditionalInformation(Integer patId) {
		return physicianDao.getPatientAdditionalInformation(patId);
	}

	public List<UserPersonalInfoVO> getPhysicianAdditionalInformation(Integer phyId) {
		return physicianDao.getPhysicianAdditionalInformation(phyId);
	}

	public void updatePersonalDetails(UserPersonalInfoVO userPersonalInfoDto) {
		physicianDao.updatePersonalDetails(userPersonalInfoDto);
	}

	public void updatePhysicianPersonalDetails(UserPersonalInfoVO physicianPersonalInfoDto) {
		physicianDao.updatePhysicianPersonalDetails(physicianPersonalInfoDto);
	}

	public Boolean checkAlreadyExistingPhysician(Map map) {
		return physicianDao.checkAlreadyExistingPhysician(map);
	}

	public Long getAllVisitsountsByPhyId(Integer type, Integer phyId, String visitDate, Integer patientId) {
		return physicianDao.getAllVisitsountsByPhyId(type, phyId, visitDate, patientId);
	}

	public Long getPatientDetailsCount(String searchPatient, Map paramsMap) {
		return physicianDao.getPatientDetailsCount(searchPatient, paramsMap);
	}

	public Long getSearchPhysicianVisitRecordsCount(String physicianid, Map map) {
		return physicianDao.getSearchPhysicianVisitRecordsCount(physicianid, map);
	}

	public Long getSearchVisitRecordsCount(String searchDate, Integer userId, String role, HashMap paramsMap) {
		return physicianDao.getSearchVisitRecordsCount(searchDate, userId, role, paramsMap);
	}

	public Long getPatientRequestCount(Integer userId, String role, HashMap paramsMap) {
		return physicianDao.getPatientRequestCount(userId, role, paramsMap);
	}

	public Long getTagVisitDetailsCount(String tag, Map paramsMap) {
		return physicianDao.getTagVisitDetailsCount(tag, paramsMap);
	}

	public Long getSearchPatientVisitRecordsCount(String patientid, Map map) {
		return physicianDao.getSearchPatientVisitRecordsCount(patientid, map);
	}

	public Long getPhysicianVisitRecordsCount(Integer patientId, int i, Map map) {
		return physicianDao.getPhysicianVisitRecordsCount(patientId, i, map);
	}

	public String getOrganizationNameByPatientId(Integer patientId) {
		return physicianDao.getOrganizationNameByPatientId(patientId);
	}

	public void updatePatientRequestVo(Integer requestId) {
		physicianDao.updatePatientRequestVo(requestId);
	}

	public Date getVisitDateByVisitId(Integer visitId) {
		return physicianDao.getVisitDateByVisitId(visitId);
	}

	public List<Object[]> getAllPatientDetailsForFax() {
		return physicianDao.getAllPatientDetailsForFax();
	}

	public List<Object[]> getEfaxAssocoatedPatientDocuments(Integer patientId) {
		return physicianDao.getEfaxAssocoatedPatientDocuments(patientId);
	}

	public void deleteDocument(Integer docId) {
		physicianDao.deleteDocument(docId);
	}

	public Object[] getPhysicianDetails(Integer physicianid) {
		return physicianDao.getPhysicianDetails(physicianid);
	}

	public String getOrganizationIdForPatient(Integer orgId, Integer patientId) {
		return physicianDao.getOrganizationIdForPatient(orgId,patientId);
	}

	public Integer findPatientOrganizationId(Integer userId) {
		return physicianDao.findPatientOrganizationId(userId);
	}

	public Object[] getPatientPersonalDetailsForEfax(Integer userId) {
		return physicianDao.getPatientPersonalDetailsForEfax(userId);
	}

	public Integer saveEFaxDetails(FaxVo faxVo) {
		return physicianDao.saveEFaxDetails(faxVo);
	}

	public void updateShareStatus(Integer shareId) {
		physicianDao.updateShareStatus(shareId);
	}

	public List<Object[]> getAllSharedDocuments(Integer shareId, String status) {
		return physicianDao.getAllSharedDocuments(shareId,status);
	}
}
