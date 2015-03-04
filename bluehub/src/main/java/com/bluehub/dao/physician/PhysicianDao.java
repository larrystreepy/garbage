package com.bluehub.dao.physician;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bluehub.bean.admin.AdminSearchDateForm;
import com.bluehub.bean.admin.SearchPatientForm;
import com.bluehub.vo.admin.FaxVo;
import com.bluehub.vo.common.EncounterVO;
import com.bluehub.vo.common.PatientDocument;
import com.bluehub.vo.common.PatientPrivateNoteVO;
import com.bluehub.vo.common.RequestInfoOfPatientVO;
import com.bluehub.vo.common.ShareClinicalInfo;
import com.bluehub.vo.physician.VisitsVO;
import com.bluehub.vo.physician.UserPersonalInfoVO;
import com.bluehub.vo.physician.PhysicianPersonalInfoVO;
import com.bluehub.vo.user.PatientRequestVO;


public interface PhysicianDao {

	public int savePhysicianVisit(VisitsVO physicianDto);

	public void savePhysicianPersonalDetails(
			UserPersonalInfoVO physicianPersonalInfoDto);

	public void deletePhysicianVisit(VisitsVO physicianVO);

	/* public List<UserPersonalInfoVO> getPatientDetails(String userEmail); */

	public List<SearchPatientForm> getPatientDetails(String searchPatient,
			Map map);

	public List<SearchPatientForm> getAllPatientDetails();

	/* public List<VisitsVO> getSearchVisitRecords(String searchDate); */

	public List<VisitsVO> getSearchVisitRecords(String searchDate,
			Integer userId, String role, Map map);

	public List<Object[]> getPatientRequest(Integer userId,
			String role, Map map);

	public List<VisitsVO> getAllList(Integer userId, String role, Map map);

	public Long getAllListCount(Integer userId, String role, Map map);

	public List<EncounterVO> getEncountersDetails(Integer visitid);

	public List<EncounterVO> editEncountersDetails(Integer encounterid);

	public List<VisitsVO> getSearchPhysicianVisitRecords(String physicianid,
			Map map, Integer userId, String role);

	public List<VisitsVO> getSearchPatientVisitRecords(String patientid, Map map);

	public List<VisitsVO> getTagVisitDetails(String tag, Map map);

	public List<VisitsVO> getKeywordVisitDetails(String keyword);

	public List<Object[]> getPhysicianVisitRecords(int patId, int phyId, Map map);

	public List<Object[]> getPhysicianOrganizations(int phyId);

	public List<Object[]> getPhysicianVisitById(int id);

	public List<Object[]> getPhysicianVisitById(int id, Integer userid,
			String status,String role,Integer patientId, String searchDate);
	
	public List<Object[]> getAdminClinicalSearchByDate(String searchDate);
	
	public List<Object[]> getSearchPhysicianVisitById(int id, Integer userid);

	public List<Object[]> getAdminSearchPhysicianVisitById(int id);
	
	public List<Object[]> getAdminSearchPatientVisitById(int id);
	
	public List<Object[]> getPhysicianSearchPatientVisitById(int patientid, int userid);
	
	public List<Object[]> getPhysicianSearchVisitById(int id, String searchDate);
	
	public List<Object[]> getAllVisitsByPhyId(int type, int phyId, String date,
			int patId, Map paramsMap);

	public List<Object[]> getAllVisitsByAdmin(int type, int patId, int orgId);

	public void updatePhysicianVisit(VisitsVO physicianVO);

	public void updateTag(Integer visitid, String prescription);

	public void savePrivateNote(PatientPrivateNoteVO patientPrivateNote);

	public void updatePrivateNote(String note, Integer noteid);

	public void saveEncounter(EncounterVO encounterVO);

	public List<PatientPrivateNoteVO> checkNote(Integer visitid,String method,String type);

	public List<Object[]> getAllDocuments(Integer userid, String status);

	public int findSsnExist(String ssn);

	public int findOrgId(Integer physicianId);

	public VisitsVO findVisitDetails(Integer pkid);

	public List<Object[]> getPatientShareVisitRecords(int type, int patId,
			String vDate);

	public Integer saveShareRequest(ShareClinicalInfo shareVo);

	public List<Object[]> getPhysicianSharedDetails(Integer userId,
			String status);

	public List<Object[]> getPatientVisitDetails(Map map);

	public String getPhysicianEmailId(Integer phyId);

	public ShareClinicalInfo finShareDetails(Integer shareId);

	public void updateShareDetails(ShareClinicalInfo shareVo);

	public Boolean checkSharExpiry(Integer shareId);

	public List<Object[]> getVisitDetailsforShare(Integer visitId);

	public List<Object[]> getPatientRequestPending(Integer patId);

	public String getRequestStatus(Integer phyId, Integer patId);

	public List<Object[]> getPatientPersonalDetails(Integer patId);

	public List<Object[]> getAllDocumentsByUserId(Integer userid);
	
	public List<Object[]> getShareDocumentsByUserId(Integer userid);

	public List<Object[]> getAllDocumentsBypatIdInPhysician(Integer patid);

	public Integer saveRequestBehalfOfPatient(RequestInfoOfPatientVO infoVO);

	public List<Object[]> getPhysicianrequestedSharedInfo(Integer userId);

	public RequestInfoOfPatientVO findReqBehalfOfPatient(Integer requestId);

	public void updateReqBehalfOfPatient(RequestInfoOfPatientVO reqVo);

	public List<Object[]> getPhysicianSharedInfoDetails(Map map);

	public Boolean checkRequestExpiry(Integer requestId);

	public List<Object[]> getAllDocumentsRequestBehalfOfPatient(
			Integer requestId, String status);

	public List<Object[]> getPatientDetails(Map map);

	public List<UserPersonalInfoVO> getPatientAdditionalInformation(
			Integer patId);

	public void updatePersonalDetails(UserPersonalInfoVO userPersonalInfoDto);

	public Boolean checkAlreadyExistingPhysician(Map map);

	public void savePatientPersonalDetails(
			UserPersonalInfoVO userPersonalInfoDto);

	public List<UserPersonalInfoVO> getPhysicianAdditionalInformation(
			Integer phyId);

	public void updatePhysicianPersonalDetails(
			UserPersonalInfoVO physicianPersonalInfoDto);

	public Long getAllVisitsountsByPhyId(Integer type, Integer phyId,
			String visitDate, Integer patientId);

	public Long getPatientDetailsCount(String searchPatient, Map paramsMap);

	public Long getSearchPhysicianVisitRecordsCount(String physicianid, Map map);

	public Long getSearchVisitRecordsCount(String searchDate, Integer userId,
			String role, HashMap paramsMap);

	public Long getPatientRequestCount(Integer userId, String role,
			HashMap paramsMap);

	public Long getTagVisitDetailsCount(String tag, Map paramsMap);

	public Long getSearchPatientVisitRecordsCount(String patientid, Map map);

	public Long getPhysicianVisitRecordsCount(Integer patientId, int i, Map map);

	public String getOrganizationNameByPatientId(Integer patientId);

	public void updatePatientRequestVo(Integer requestId);

	public Date getVisitDateByVisitId(Integer visitId);

	public List<Object[]> getAllPatientDetailsForFax();

	public List<Object[]> getEfaxAssocoatedPatientDocuments(Integer patientId);

	public void deleteDocument(Integer docId);

	public Object[] getPhysicianDetails(Integer physicianid);

	public String getOrganizationIdForPatient(Integer orgId, Integer patientId);

	public Integer findPatientOrganizationId(Integer userId);

    /**
     * Get the extended user data needed to fill out the fax release form.
     *
     * @param userId Id of user
     * @return name, DOB (as a Date), address
     */
	public Object[] getPatientPersonalDetailsForEfax(Integer userId);

	public Integer saveEFaxDetails(FaxVo faxVo);

	public void updateShareStatus(Integer shareId);

	public List<Object[]> getAllSharedDocuments(Integer shareId, String status);

}
