package com.bluehub.controller.physician;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bluehub.bean.admin.AdminSearchDateForm;
import com.bluehub.bean.admin.SearchPhysicianForm;
import com.bluehub.bean.common.RequestInfoBehalf;
import com.bluehub.bean.common.UserDetails;
import com.bluehub.bean.user.JsonResponse;
import com.bluehub.bean.user.UserProfile;
import com.bluehub.bean.user.phyPersonalForm;
import com.bluehub.bean.user.phyVisitForm;
import com.bluehub.manager.admin.AdminOrganizationManager;
import com.bluehub.manager.physician.PhysicianManager;
import com.bluehub.manager.user.UserManager;
import com.bluehub.util.AuditTrailUtil;
import com.bluehub.util.CommonUtils;
import com.bluehub.util.Constants;
import com.bluehub.util.MailSupport;
import com.bluehub.vo.admin.AdminOrganizationVO;
import com.bluehub.vo.admin.AdminPracticeVO;
import com.bluehub.vo.common.AuditTrailVO;
import com.bluehub.vo.common.PatientDocument;
import com.bluehub.vo.common.RequestInfoOfPatientVO;
import com.bluehub.vo.common.ShareClinicalInfo;
import com.bluehub.vo.physician.UserPersonalInfoVO;
import com.bluehub.vo.physician.VisitsVO;
import com.bluehub.vo.user.RoleVO;
import com.bluehub.vo.user.UserVO;
import com.google.gson.Gson;

@Controller
public class PhysicianMainController {

	public static Logger logger = Logger
			.getLogger(PhysicianMainController.class);

	@Autowired
	private AdminOrganizationManager adminOrganizationManager;

	public void setAdminOrganizationManager(
			AdminOrganizationManager adminOrganizationManager) {
		this.adminOrganizationManager = adminOrganizationManager;
	}

	@Autowired
	private PhysicianManager physicianManager;

	@Autowired
	private UserManager userManager;

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setPhysicianManager(PhysicianManager physicianManager) {
		this.physicianManager = physicianManager;
	}

	@RequestMapping("/physician/home.do")
	public String physicianMain(ModelMap model, HttpServletRequest request) {
		logger.debug("PhysicianController physicianhome==>");
		/*
		 * String qryParam = "";
		 * if(request.getSession().getAttribute("patient_id").toString() != ""){
		 * qryParam =
		 * request.getSession().getAttribute("patient_id").toString(); }
		 */
		// String qryString = session.getAttribute("patient_id").toString();
		// logger.info("query string " + qryString);
		boolean flag = false;
		boolean userflag = false;
		String viewpage = null;

		/*
		 * if(qryParam != ""){ logger.info("query param " + qryParam);
		 * //viewpage = Constants.HOME_PHYSICIAN_VIEW; viewpage =
		 * "redirect:/physician/physicianPatientShareDocuments.do?patientId="
		 * +qryParam; }else{
		 */
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer userid = user.getId();

		// Here we are changing user role from security context
		SecurityContext context = SecurityContextHolder.getContext();
		Object principal = context.getAuthentication().getPrincipal();
		Object credentials = context.getAuthentication().getCredentials();
		// GrantedAuthority[] authorities = new GrantedAuthority[1];

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(1);
		authorities.add(new GrantedAuthorityImpl("ROLE_PHYSICIAN"));
		// authorities[0] = new GrantedAuthorityImpl("PracticeAdmin");

		Authentication auth = new UsernamePasswordAuthenticationToken(
				principal, credentials, authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);

		HttpSession session = request.getSession(true);
		session.setAttribute("role", "Physician");
		user.setRoleid("Physician");

		flag = userManager.checkUserAdditionalInfo(userid);
		
		if(flag){
			//viewpage = Constants.HOME_PHYSICIAN_VIEW;
			viewpage = Constants.PHYSICIAN_PERSONAL_DETAILS_VIEW;
		}else{
			userflag = userManager.checkPracticeAdminUser(userid);
			if (userflag) {
				// additional info
				viewpage = Constants.HOME_PHYSICIAN_VIEW;
			} else {
				viewpage = Constants.HOME_PHYSICIAN_DASHBOARD;
			}
			
			//viewpage = Constants.HOME_PHYSICIAN_DASHBOARD;
		}

		Long totRequestPhysician = userManager.getOutstandingRequestsFromPhysician(userid);

		Long totRequestPatient = userManager.getOutstandingRequests(userid);
		Long totalUsers = userManager.getTotalUsers("Patient");

		model.addAttribute("users", totalUsers);
		Long totalVisits = userManager.getTotlPatientsVisits(userid);
		model.addAttribute("totalVisits", totalVisits);

		model.addAttribute("totRequests", totRequestPatient);
		
		model.addAttribute("totRequestPhysician", totRequestPhysician);
		return viewpage;
	}

	@RequestMapping("/physician/physiciandashboard.do")
	public String physicianDashboard(ModelMap model, HttpServletRequest request) {
		logger.debug("PhysicianController physicianhome==>");
		boolean flag = false;
		String viewpage = null;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userid = user.getId();

		HttpSession session = request.getSession(true);
		session.setAttribute("role", "Physician");
		user.setRoleid("Physician");
		// user.setRoleid("PracticeAdmin");

		// Here we are changing user role from security context
		/***************
		 * SecurityContext context = SecurityContextHolder.getContext(); Object
		 * principal = context.getAuthentication().getPrincipal(); Object
		 * credentials = context.getAuthentication().getCredentials();
		 * //GrantedAuthority[] authorities = new GrantedAuthority[1];
		 * 
		 * 
		 * 
		 * List<GrantedAuthority> authorities = new
		 * ArrayList<GrantedAuthority>(1); authorities.add(new
		 * GrantedAuthorityImpl("ROLE_PHYSICIAN")); //authorities[0] = new
		 * GrantedAuthorityImpl("PracticeAdmin");
		 * 
		 * Authentication auth = new
		 * UsernamePasswordAuthenticationToken(principal, credentials,
		 * authorities);
		 * SecurityContextHolder.getContext().setAuthentication(auth);
		 *******************/

		flag = userManager.checkUserAdditionalInfo(userid);

		viewpage = Constants.HOME_PHYSICIAN_DASHBOARD;
		Long totalUsers = userManager.getTotalUsers("Patient");
		
		
		Long totRequestPhysician = userManager.getOutstandingRequestsFromPhysician(userid);

		Long totRequestPatient = userManager.getOutstandingRequests(userid);
        model.addAttribute("totRequests", totRequestPatient);
		model.addAttribute("totRequestPhysician", totRequestPhysician);
		
		model.addAttribute("users", totalUsers);

		Long totalVisits = userManager.getTotlPatientsVisits(userid);
		model.addAttribute("totalVisits", totalVisits);
		return viewpage;
	}

	@RequestMapping(value = "/checkssn.do", method = RequestMethod.GET)
	public @ResponseBody
	String checkSsn(@RequestParam String ssn) {
		logger.info("PhysicianController checkssn() starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		int ssnExist = 0;
		try {
			if (StringUtils.isNotBlank(ssn)) {
				ssnExist = physicianManager.getSsnExist(ssn);
				logger.info("ssn exist " + ssnExist);
				if (ssnExist == 0) {
					jsonResponse.setMessage(Constants
							.getPropertyValue("ssn_not_exist"));
					jsonResponse.setStatus("No");
				} else {
					jsonResponse.setMessage(Constants
							.getPropertyValue("ssn_already_exist"));
					jsonResponse.setStatus("Yes");
				}
				json = new Gson().toJson(jsonResponse);
			}
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("UserController checkUserEmail() ends");
		return json;
	}

	@RequestMapping("/physician/physicianVisitDetails.do")
	public String physicianVisits(ModelMap model) {
		logger.debug("PhysicianController physicianvisits==>");
		String viewpage = Constants.PHYSICIAN_VISIT_DETAILS_VIEW;
		return viewpage;
	}

	@RequestMapping("/physician/physicianallvisits.do")
	public String physicianAllVisits(ModelMap model) {
		logger.debug("PhysicianController physicianvisits==>");
		String viewpage = Constants.PHYSICIAN_ALL_VISITS_VIEW;
		return viewpage;
	}

	@RequestMapping("/physician/physicianencounter.do")
	public String physicianEncounters(ModelMap model) {
		logger.debug("PhysicianController physicianvisits==>");
		String viewpage = Constants.PHYSICIAN_ENCOUNTERS_VIEW;
		return viewpage;
	}

	@RequestMapping("/physician/physicianviewencounter.do")
	public String physicianViewEncounters(ModelMap model) {
		logger.debug("PhysicianController physicianViewEncounters==>");
		String viewpage = Constants.PHYSICIAN_VIEW_ENCOUNTERS_VIEW;
		return viewpage;
	}

	//

	@RequestMapping(value = "/physician/getPhysicianVisitDetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String getPhysicianVisits(@RequestParam Integer patientId,
			HttpServletRequest request, ModelMap model) {
		logger.debug("PhysicianMainController physicianVisits==> ");
		String json = null;
		List<Object[]> phyVisitsDto = null;
		String viewpage = Constants.PHYSICIAN_VISIT_DETAILS_VIEW;
		Integer orgId = 0;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer phyId = user.getId();
		HashMap paramsMap = new HashMap();

		Integer iDisplayStart = null;
		Integer iDisplayLength = null;
		Integer sEcho = null;
		String finalJson = null;
		if (request.getParameter("iDisplayStart") != null
				&& request.getParameter("iDisplayLength") != null
				&& request.getParameter("sEcho") != null) {
			iDisplayStart = Integer.parseInt(request.getParameter(
					"iDisplayStart").toString());
			iDisplayLength = Integer.parseInt(request.getParameter(
					"iDisplayLength").toString());
			sEcho = Integer.parseInt(request.getParameter("sEcho").toString());
		}

		if (iDisplayStart != null && iDisplayLength != null && sEcho != null) {
			paramsMap.put("iDisplayStart", iDisplayStart);
			paramsMap.put("iDisplayLength", iDisplayLength);
			paramsMap.put("sEcho", sEcho);
		}

		String sSortDirection = request.getParameter("sSortDir_0");
		String iSortColumnIndex = request.getParameter("iSortCol_0");
		if (sSortDirection != null && iSortColumnIndex != null) {
			paramsMap.put("sSortDirection", sSortDirection);
			paramsMap.put("iSortColumnIndex", iSortColumnIndex);
		}
		try {
			// orgId = physicianManager.findOrgId(phyId);
			logger.info("physicianId " + phyId);
			phyVisitsDto = physicianManager.getPhysicianVisitRecords(patientId,
					phyId, paramsMap);

			if (phyVisitsDto != null && phyVisitsDto.size() > 0) {
				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
				int reworkCount = 0;
				for (Object[] obj : phyVisitsDto) {
					HashMap searchReportMap = new HashMap();
					Date dd = (Date) obj[3];

					searchReportMap.put("Patient Name", obj[1]);
					searchReportMap.put("Date of Visit",
							CommonUtils.parseStringFromDate(dd));
					searchReportMap.put("Reason for Visit", obj[5]);
					searchReportMap.put("Physician Consulted", obj[2]);
					searchReportMap.put("Prescription of Physician", obj[4]);
					searchReportMap.put("id", obj[0]);
					searchReportArrayList.add(searchReportMap);
				}

				Long count = physicianManager.getPhysicianVisitRecordsCount(
						patientId, 0, paramsMap);
				finalJson = CommonUtils.convertMaptoJsonForGrid(
						searchReportArrayList, Long.parseLong(count + ""),
						sEcho);
				model.addAttribute("finalJson", finalJson);
				// json = finalJson;
			} else {
				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();

				finalJson = CommonUtils.convertMaptoJsonForGrid(
						searchReportArrayList, 0L, sEcho);
				model.addAttribute("finalJson", finalJson);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			finalJson = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("PhysicianMainController physicianVisits ends ");

		return finalJson;
	}

	@RequestMapping(value = "/physician/getPhysicianOrganizations.do", method = RequestMethod.GET)
	public @ResponseBody
	String getPhysicianOrganizations(ModelMap model) {
		String json = null;
		List<Object[]> phyVisitsDto = null;
		String viewpage = Constants.PHYSICIAN_VISIT_DETAILS_VIEW;
		Integer orgId = 0;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer phyId = user.getId();

		try {
			phyVisitsDto = physicianManager.getPhysicianOrganizations(phyId);
			json = new Gson().toJson(phyVisitsDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("PhysicianMainController physicianVisits ends ");

		return json;
	}

	@RequestMapping(value = "/physician/getAdminPhysicianVisitDetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String getAdminPhysicianVisits(@RequestParam Integer patientId,
			ModelMap model, HttpServletRequest request) {
		logger.debug("PhysicianMainController physicianVisits==> ");
		String json = null;
		List<Object[]> phyVisitsDto = null;
		HashMap paramsMap = new HashMap();

		Integer iDisplayStart = null;
		Integer iDisplayLength = null;
		Integer sEcho = null;
		String finalJson = null;
		if (request.getParameter("iDisplayStart") != null
				&& request.getParameter("iDisplayLength") != null
				&& request.getParameter("sEcho") != null) {
			iDisplayStart = Integer.parseInt(request.getParameter(
					"iDisplayStart").toString());
			iDisplayLength = Integer.parseInt(request.getParameter(
					"iDisplayLength").toString());
			sEcho = Integer.parseInt(request.getParameter("sEcho").toString());
		}

		if (iDisplayStart != null && iDisplayLength != null && sEcho != null) {
			paramsMap.put("iDisplayStart", iDisplayStart);
			paramsMap.put("iDisplayLength", iDisplayLength);
			paramsMap.put("sEcho", sEcho);
		}

		String sSortDirection = request.getParameter("sSortDir_0");
		String iSortColumnIndex = request.getParameter("iSortCol_0");
		if (sSortDirection != null && iSortColumnIndex != null) {
			paramsMap.put("sSortDirection", sSortDirection);
			paramsMap.put("iSortColumnIndex", iSortColumnIndex);
		}

		// v.id,concat(u.firstname,' ',u.lastname),concat(uv.firstname,' ',uv.lastname),
		// v.dateofvisit,v.prescription,v.reasonforvisit"
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer userId = user.getId();
		
		try {
			phyVisitsDto = physicianManager.getPhysicianVisitRecords(patientId,
					userId, paramsMap);

			if (phyVisitsDto != null && phyVisitsDto.size() > 0) {
				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
				int reworkCount = 0;
				for (Object[] obj : phyVisitsDto) {
					HashMap searchReportMap = new HashMap();
					Date dd = (Date) obj[3];

					searchReportMap.put("Patient Name", obj[1]);
					searchReportMap.put("Date of Visit",
							CommonUtils.parseStringFromDate(dd));
					searchReportMap.put("Reason for Visit", obj[5]);
					searchReportMap.put("Physician Consulted", obj[2]);
					searchReportMap.put("Prescription of Physician", obj[4]);
					searchReportMap.put("id", obj[0]);
					searchReportArrayList.add(searchReportMap);
				}

				Long count = physicianManager.getPhysicianVisitRecordsCount(
						patientId, 0, paramsMap);
				finalJson = CommonUtils.convertMaptoJsonForGrid(
						searchReportArrayList, Long.parseLong(count + ""),
						sEcho);
				model.addAttribute("finalJson", finalJson);
				// json = finalJson;
			} else {
				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();

				finalJson = CommonUtils.convertMaptoJsonForGrid(
						searchReportArrayList, 0L, sEcho);
				model.addAttribute("finalJson", finalJson);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			finalJson = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("PhysicianMainController physicianVisits ends ");

		return finalJson;
	}

	@RequestMapping(value = "/physician/getAllVisitsByPhysicianId.do", method = RequestMethod.GET)
	public @ResponseBody
	String getAllVisitsByPhysicianId(@RequestParam Integer type,
			@RequestParam String visitDate, @RequestParam Integer patientId,
			ModelMap model, HttpServletRequest request) {
		logger.debug("getAllVisitsByPhysicianId physicianVisits==> ");
		List<Object[]> phyVisitsDto = null;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer phyId = user.getId();
		Integer iDisplayStart = null;
		Integer iDisplayLength = null;
		Integer sEcho = null;
		Long totalValue = 0L;
		String finalJson = "";
		try {

			if (request.getParameter("iDisplayStart") != null
					&& request.getParameter("iDisplayLength") != null
					&& request.getParameter("sEcho") != null) {
				iDisplayStart = Integer.parseInt(request.getParameter(
						"iDisplayStart").toString());
				iDisplayLength = Integer.parseInt(request.getParameter(
						"iDisplayLength").toString());
				sEcho = Integer.parseInt(request.getParameter("sEcho")
						.toString());
			}

			HashMap paramsMap = new HashMap();
			if (iDisplayStart != null && iDisplayLength != null
					&& sEcho != null) {
				paramsMap.put("iDisplayStart", iDisplayStart);
				paramsMap.put("iDisplayLength", iDisplayLength);
				paramsMap.put("sEcho", sEcho);
			}
			String sSortDirection = request.getParameter("sSortDir_0");
			String iSortColumnIndex = request.getParameter("iSortCol_0");
			if (sSortDirection != null && iSortColumnIndex != null) {
				paramsMap.put("sSortDirection", sSortDirection);
				paramsMap.put("iSortColumnIndex", iSortColumnIndex);
			}
			phyVisitsDto = physicianManager.getAllVisitsByPhyId(type, phyId,
					visitDate, patientId, paramsMap);
			List<HashMap> searchReportArrayList = new ArrayList<HashMap>();

			int reworkCount = 0;
			for (Object[] searchReportListObject : phyVisitsDto) {
				HashMap searchReportMap = new HashMap();
				Date dd = (Date) searchReportListObject[3];
				searchReportMap.put("visitId", searchReportListObject[0]);
				searchReportMap.put("Visit Date",
						CommonUtils.parseStringFromDate(dd));
				searchReportMap.put("Patient", searchReportListObject[1]);
				// searchReportMap.put("Physician", searchReportListObject[2]);

				searchReportMap.put("Reason", searchReportListObject[5]);
				searchReportMap.put("Prescription", searchReportListObject[4]);

				searchReportMap.put("patId", searchReportListObject[6]);
				searchReportMap.put("phyId", searchReportListObject[7]);
				searchReportArrayList.add(searchReportMap);
			}

			totalValue = physicianManager.getAllVisitsountsByPhyId(type, phyId,
					visitDate, patientId);

			finalJson = CommonUtils.convertMaptoJsonForGrid(
					searchReportArrayList, totalValue, sEcho);

			// json = new Gson().toJson(phyVisitsDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			finalJson = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("PhysicianMainController physicianVisits ends ");

		return finalJson;
	}

	@RequestMapping(value = "/physician/getAllVisitsByAdmin.do", method = RequestMethod.GET)
	public @ResponseBody
	String getAllVisitsByAdmin(@RequestParam Integer type,
			@RequestParam Integer patientId, @RequestParam Integer orgId,
			ModelMap model) {
		logger.debug("getAllVisitsByPhysicianId physicianVisits==> ");
		String json = null;
		List<Object[]> phyVisitsDto = null;
		logger.info("admin patient id " + patientId);
		try {
			phyVisitsDto = physicianManager.getAllVisitsByAdmin(type,
					patientId, orgId);
			json = new Gson().toJson(phyVisitsDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("PhysicianMainController physicianVisits ends ");

		return json;
	}

	@RequestMapping(value = "/physician/deletephysicianvisit.do")
	public String deletePhysicianVisit(@RequestParam Integer phyid,
			ModelMap model, HttpServletRequest request) {
		logger.info("delete physician visit starts");
		String viewPage = Constants.PHYSICIAN_VISIT_DETAILS_VIEW;

		int regsiter = 1;

		try {
			VisitsVO physicianDeleteVisitDto = new VisitsVO();
			physicianDeleteVisitDto.setId(phyid);
			physicianManager.deletePhysicianVisit(physicianDeleteVisitDto);

			AuditTrailVO dto = AuditTrailUtil.SaveAuditTrailDetails(phyid,
					request, Constants.VISITS, Constants.DELETE,
					"Patient Visit Record Deleted");
			userManager.saveAuditTrails(dto);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		logger.info("delete physician visit ends");

		return viewPage;
	}

	@RequestMapping(value = "/physician/editphysicianvisit.do", method = RequestMethod.GET)
	public @ResponseBody
	String editPhysicianVisit(@RequestParam Integer phyid, ModelMap model) {
		logger.debug("editPhysicianVisit : " + phyid);
		String json = null;
		List<Object[]> physicianDto = null;
		try {
			physicianDto = physicianManager.getPhysicianVisitById(phyid);
			json = new Gson().toJson(physicianDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("editPhysicianVisit ends");
		return json;
	}

	@RequestMapping(value = "/physician/updateadminphysicianvisit.do", method = RequestMethod.GET)
	public String updateAdminPhysicianVisit(
			@RequestParam(value = "phyid") Integer phyid,
			@RequestParam(value = "visitDate") String visitDate,
			@RequestParam(value = "reason") String reason,
			@RequestParam(value = "prescription") String prescription,
			@RequestParam(value = "org") Integer org,
			@RequestParam(value = "practice") Integer practice,
			@RequestParam(value = "patid") Integer patid,
			@RequestParam(value = "pkid") Integer pkid, ModelMap model,
			HttpServletRequest request) {
		logger.debug("physicianMainController");
		String viewPage = Constants.PHYSICIAN_VISIT_DETAILS_VIEW;
		int regsiter = 1;
		int userid;
		String detail = "";
		try {
			VisitsVO physicianDto;
			// UserProfile user = (UserProfile)
			// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// userid = user.getId();

			physicianDto = physicianManager.findVisitDetails(pkid);

			Timestamp timestamp = new Timestamp(new Date().getTime());

			if (!physicianDto.getPatientid().equals(patid)) {

				detail = detail + "Patient is Changed From "
						+ physicianDto.getPatientid() + " to " + patid + ";";

			}
			if (!physicianDto.getOrganizationid().equals(org)) {
				// detail = detail + " Libeary Fine is  modified from "+
				// libearyFine + " to " + libraryFineDTO.getLibearyFine() +
				// " ;";
				detail = detail + "Organization is Changed From "
						+ physicianDto.getOrganizationid() + " to " + org + ";";

			}

			if (!physicianDto.getPracticeid().equals(practice)) {

				detail = detail + "Practice is Changed From "
						+ physicianDto.getPracticeid() + " to " + practice
						+ ";";

			}

			if (StringUtils.isNotBlank(visitDate)) {
				physicianDto.setDateofvisit(CommonUtils
						.parseDateFromString(visitDate));

				if (!physicianDto.getDateofvisit().equals(
						CommonUtils.parseDateFromString(visitDate))) {

					detail = detail + "Visit Date is Changed From "
							+ physicianDto.getDateofvisit() + " to "
							+ visitDate + ";";
				}
			}

			if (!physicianDto.getReasonforvisit().equals(reason)) {

				detail = detail + "Reason is Changed From "
						+ physicianDto.getReasonforvisit() + " to " + reason
						+ ";";

			}

			if (!physicianDto.getPrescription().equals(prescription)) {

				detail = detail + "Prescription is Changed From "
						+ physicianDto.getPrescription() + " to "
						+ prescription + ";";
			}

			if (!physicianDto.getPhysicianid().equals(phyid)) {

				detail = detail + "Physician is Changed From "
						+ physicianDto.getPhysicianid() + " to " + phyid + ";";

			}

			physicianDto = new VisitsVO();

			if (StringUtils.isNotBlank(visitDate)) {
				physicianDto.setDateofvisit(CommonUtils
						.parseDateFromString(visitDate));
			}
			physicianDto.setReasonforvisit(reason);
			physicianDto.setPrescription(prescription);
			physicianDto.setOrganizationid(org);
			physicianDto.setPracticeid(practice);
			physicianDto.setPhysicianid(phyid);
			physicianDto.setPatientid(patid);
			physicianDto.setUpdatedon(timestamp);
			physicianDto.setId(pkid);

			physicianManager.updatePhysicianVisit(physicianDto);

			AuditTrailVO dto = AuditTrailUtil.SaveAuditTrailDetails(pkid,
					request, Constants.VISITS, Constants.UPDATE, detail);
			userManager.saveAuditTrails(dto);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		logger.info("updatePhysicianVisit ends");

		return viewPage;
	}

	@RequestMapping(value = "/physician/updatephysicianvisit.do")
	public String updatePhysicianVisit(
			@RequestParam(value = "phyid") Integer phyid,
			@RequestParam(value = "visitDate") String visitDate,
			@RequestParam(value = "reason") String reason,
			@RequestParam(value = "prescription") String prescription,
			@RequestParam(value = "org") Integer org,
			@RequestParam(value = "practice") Integer practice,
			@RequestParam(value = "patid") Integer patid,
			@RequestParam(value = "pkid") Integer pkid, ModelMap model,
			HttpServletRequest request) {
		logger.debug("physicianMainController");
		String viewPage = Constants.PHYSICIAN_VISIT_DETAILS_VIEW;
		int regsiter = 1;
		int userid;
		String detail = "";
		try {
			VisitsVO physicianDto;
			// UserProfile user = (UserProfile)
			// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// userid = user.getId();

			physicianDto = physicianManager.findVisitDetails(pkid);

			Timestamp timestamp = new Timestamp(new Date().getTime());

			if (!physicianDto.getPatientid().equals(patid)) {

				detail = detail + "Patient is Changed From "
						+ physicianDto.getPatientid() + " to " + patid + ";";

			}

			if (StringUtils.isNotBlank(visitDate)) {
				physicianDto.setDateofvisit(CommonUtils
						.parseDateFromString(visitDate));

				if (!physicianDto.getDateofvisit().equals(
						CommonUtils.parseDateFromString(visitDate))) {

					detail = detail + "Visit Date is Changed From "
							+ physicianDto.getDateofvisit() + " to "
							+ visitDate + ";";
				}
			}

			if (!physicianDto.getOrganizationid().equals(org)) {
				// detail = detail + " Libeary Fine is  modified from "+
				// libearyFine + " to " + libraryFineDTO.getLibearyFine() +
				// " ;";
				detail = detail + "Organization is Changed From "
						+ physicianDto.getOrganizationid() + " to " + org + ";";

			}

			if (!physicianDto.getPracticeid().equals(practice)) {

				detail = detail + "Practice is Changed From "
						+ physicianDto.getPracticeid() + " to " + practice
						+ ";";

			}
			if (!physicianDto.getReasonforvisit().equals(reason)) {

				detail = detail + "Reason is Changed From "
						+ physicianDto.getReasonforvisit() + " to " + reason
						+ ";";

			}

			if (!physicianDto.getPrescription().equals(prescription)) {

				detail = detail + "Prescription is Changed From "
						+ physicianDto.getPrescription() + " to "
						+ prescription + ";";
			}

			if (!physicianDto.getPhysicianid().equals(phyid)) {

				detail = detail + "Physician is Changed From "
						+ physicianDto.getPhysicianid() + " to " + phyid + ";";

			}

			physicianDto = new VisitsVO();

			if (StringUtils.isNotBlank(visitDate)) {
				physicianDto.setDateofvisit(CommonUtils
						.parseDateFromString(visitDate));
			}
			physicianDto.setReasonforvisit(reason);
			physicianDto.setPrescription(prescription);
			physicianDto.setOrganizationid(org);
			physicianDto.setPracticeid(practice);
			physicianDto.setPhysicianid(phyid);
			physicianDto.setPatientid(patid);
			physicianDto.setUpdatedon(timestamp);
			physicianDto.setId(pkid);

			physicianManager.updatePhysicianVisit(physicianDto);

			AuditTrailVO dto = AuditTrailUtil.SaveAuditTrailDetails(pkid,
					request, Constants.VISITS, Constants.UPDATE, detail);
			userManager.saveAuditTrails(dto);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		logger.info("updatePhysicianVisit ends");

		return viewPage;
	}

	@RequestMapping("/physician/physicianPersonalDetails.do")
	public String physicianPersonal(ModelMap model) {
		logger.debug("PhysicianController physicianhome==>");
		String viewpage = Constants.PHYSICIAN_PERSONAL_DETAILS_VIEW;
		return viewpage;
	}

	/**
	 * Gets the child user's registration form view
	 * 
	 * @param userRegistrationForm
	 * @param model
	 * @return String
	 * 
	 * 
	 * 
	 */
	@RequestMapping(value = "/physician/saveAdminVisitDetails.do", method = RequestMethod.POST)
	public String getAdminVisitsForm(
			@ModelAttribute("physicianVisitsForm") phyVisitForm physicianVisitsForm,
			SessionStatus sessionStatus, ModelMap model,
			HttpServletRequest request) {

		logger.info("PhysicianMainController physicianVisitsForm() starts");

		String viewPage = Constants.PHYSICIAN_VISIT_DETAILS_VIEW;
		int regsiter = 1;
		try {
			Timestamp timestamp = new Timestamp(new Date().getTime());
			// RoleVO roleDto = new RoleVO();

			// List<RoleVO> roleDto =
			// physicianManager.findRoleVoByRoleId(userRegistrationForm.getUserType());

			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			Integer userId = user.getId();

			VisitsVO physicianDto = new VisitsVO();
			// physicianDto.setDateofvisit(physicianVisitsForm.getDate_of_visit());
			if (StringUtils.isNotBlank(physicianVisitsForm.getDate_of_visit())) {
				physicianDto.setDateofvisit(CommonUtils
						.parseDateFromString(physicianVisitsForm
								.getDate_of_visit()));
			}

			physicianDto.setReasonforvisit(physicianVisitsForm
					.getReason_for_visit());
			physicianDto.setPrescription(physicianVisitsForm.getPrescription());
			physicianDto.setOrganizationid(physicianVisitsForm
					.getOrganization());
			physicianDto.setPracticeid(physicianVisitsForm.getPractice());
			physicianDto.setTag(physicianVisitsForm.getTag());
			physicianDto.setCreatedon(timestamp);
			physicianDto.setPatientid(physicianVisitsForm.getPatientId());
			physicianDto.setPhysicianid(physicianVisitsForm.getPhysicianId());
			Integer id = physicianManager.savePhysicianVisit(physicianDto);

			AuditTrailVO dto = AuditTrailUtil.SaveAuditTrailDetails(id,
					request, Constants.VISITS, Constants.INSERT,
					"New Patient Visit Created");
			userManager.saveAuditTrails(dto);

			sessionStatus.setComplete();

			model.addAttribute("successMsg",
					Constants.getPropertyValue(Constants.REGISTER_SUCCESS));
			model.addAttribute("redirectMsg",
					Constants.getPropertyValue(Constants.redirect_msg));
			model.addAttribute("regsiter", regsiter);
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		logger.info("PhysicianMainController physicianVisitsForm() ends");
		return viewPage;
	}

	/**
	 * Gets the child user's registration form view
	 * 
	 * @param userRegistrationForm
	 * @param model
	 * @return String
	 * 
	 * 
	 * 
	 */
	@RequestMapping(value = "/physician/savePhyVisitDetails.do", method = RequestMethod.POST)
	public String getPhysicianVisitsForm(
			@ModelAttribute("physicianVisitsForm") phyVisitForm physicianVisitsForm,
			SessionStatus sessionStatus, ModelMap model,
			HttpServletRequest request) {

		logger.info("PhysicianMainController physicianVisitsForm() starts");

		String viewPage = Constants.PHYSICIAN_VISIT_DETAILS_VIEW;
		int regsiter = 1;
		try {
			Timestamp timestamp = new Timestamp(new Date().getTime());
			// RoleVO roleDto = new RoleVO();

			// List<RoleVO> roleDto =
			// physicianManager.findRoleVoByRoleId(userRegistrationForm.getUserType());

			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			Integer userId = user.getId();

			VisitsVO physicianDto = new VisitsVO();
			// physicianDto.setDateofvisit(physicianVisitsForm.getDate_of_visit());
			if (StringUtils.isNotBlank(physicianVisitsForm.getDate_of_visit())) {
				physicianDto.setDateofvisit(CommonUtils
						.parseDateFromString(physicianVisitsForm
								.getDate_of_visit()));
			}

			physicianDto.setReasonforvisit(physicianVisitsForm
					.getReason_for_visit());
			physicianDto.setPrescription(physicianVisitsForm.getPrescription());
			physicianDto.setOrganizationid(physicianVisitsForm
					.getOrganization());
			physicianDto.setPracticeid(physicianVisitsForm.getPractice());
			physicianDto.setTag(physicianVisitsForm.getTag());
			physicianDto.setCreatedon(timestamp);
			physicianDto.setPatientid(physicianVisitsForm.getPatientId());
			physicianDto.setPhysicianid(userId);
			physicianDto.setCreatedby(userId);
			Integer id = physicianManager.savePhysicianVisit(physicianDto);

			AuditTrailVO dto = AuditTrailUtil.SaveAuditTrailDetails(id,
					request, Constants.VISITS, Constants.INSERT,
					"New Patient Visit Created");
			userManager.saveAuditTrails(dto);

			sessionStatus.setComplete();

			model.addAttribute("successMsg",
					Constants.getPropertyValue(Constants.REGISTER_SUCCESS));
			model.addAttribute("redirectMsg",
					Constants.getPropertyValue(Constants.redirect_msg));
			model.addAttribute("regsiter", regsiter);
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		logger.info("PhysicianMainController physicianVisitsForm() ends");
		return viewPage;
	}

	/**
	 * Gets the child user's registration form view
	 * 
	 * @param userRegistrationForm
	 * @param model
	 * @return String
	 * 
	 * 
	 * 
	 */
	@RequestMapping(value = "/savePhyPersonalDetails.do", method = RequestMethod.POST)
	public String getPhysicianPersonalDetails(
			@ModelAttribute("physicianPersonalForm") phyPersonalForm phyForm,
			SessionStatus sessionStatus, ModelMap model) {

		logger.info("PhysicianMainController physicianPersonalForm() starts");

		String viewPage = Constants.HOME_PHYSICIAN_DASHBOARD;
		int regsiter = 1;
		try {
			Timestamp timestamp = new Timestamp(new Date().getTime());
			// RoleVO roleDto = new RoleVO();

			// List<RoleVO> roleDto =
			// physicianManager.findRoleVoByRoleId(userRegistrationForm.getUserType());

			UserPersonalInfoVO userPersonalInfoDto = new UserPersonalInfoVO();
			userPersonalInfoDto.setFirstname(phyForm.getFirstname());
			userPersonalInfoDto.setMiddlename(phyForm.getMiddlename());
			userPersonalInfoDto.setLastname(phyForm.getLastname());
			userPersonalInfoDto.setContact_number(phyForm.getContactNo());
			userPersonalInfoDto.setStreet(phyForm.getStreet());
			userPersonalInfoDto.setCity(phyForm.getCity());
			userPersonalInfoDto.setZip(phyForm.getZip());
			userPersonalInfoDto.setDegree(phyForm.getDegree());
			userPersonalInfoDto.setEmail(phyForm.getEmail());
			userPersonalInfoDto.setLanguage(phyForm.getLanguage());
			userPersonalInfoDto.setOffice_phone(phyForm.getOffice_phone());
			userPersonalInfoDto.setFax(phyForm.getFax());

			/*
			 * if(StringUtils.isNotBlank(phyForm.getDateOfBirth())){
			 * userPersonalInfoDto
			 * .setDob(CommonUtils.parseDateFromString(phyForm
			 * .getDateOfBirth())); }
			 */

			userPersonalInfoDto.setCreatedon(timestamp);

			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			Integer userid = user.getId();

			userPersonalInfoDto.setUserid(userid);
			// physicianPersonalDto.setId(56214);

			physicianManager.savePhysicianPersonalDetails(userPersonalInfoDto);
			sessionStatus.setComplete();

			model.addAttribute("successMsg",
					Constants.getPropertyValue(Constants.REGISTER_SUCCESS));
			model.addAttribute("redirectMsg",
					Constants.getPropertyValue(Constants.redirect_msg));
			model.addAttribute("regsiter", regsiter);
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		logger.info("PhysicianMainController physicianPersonalForm() ends");
		return viewPage;
	}

	@RequestMapping("/physician/physiciansearch.do")
	public String physicianSearch(ModelMap model) {
		logger.debug("physicianSearch");
		String viewpage = Constants.SEARCH_PHYSICIAN_VIEW;

		return viewpage;
	}

	@RequestMapping("/physician/physicianupload.do")
	public String physicianUpload(ModelMap model) {
		logger.debug("physicianUpload");
		String viewpage = Constants.UPLOAD_PHYSICIAN_VIEW;
		return viewpage;
	}

	/* Added For Search In Patient */

	@RequestMapping(value = "/physician/adminorganizations.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadAdminOrganization() {
		logger.info("loadAdminOrganization starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		int emailExist = 0;
		try {

			List<AdminOrganizationVO> adminOrganizationVO = adminOrganizationManager
					.getAdminOrganizaions();
			for (AdminOrganizationVO adminOrganizationVO2 : adminOrganizationVO) {
				jsonResponse.setMessage(adminOrganizationVO2
						.getOrganizationname());
			}
			json = new Gson().toJson(adminOrganizationVO);
			// }
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("loadAdminOrganization ends");
		return json;
	}

	@RequestMapping(value = "/physician/loadpractice.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadPractices(@RequestParam Integer orgid) {
		logger.info("AdminMainController loadPractices() starts==>");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();

		try {

			List<AdminPracticeVO> practiceVo = adminOrganizationManager
					.getPracticeList(orgid);

			json = new Gson().toJson(practiceVo);
			// }
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			json = new Gson().toJson(null);
		}
		logger.info("AdminMainController loadPractices() ends==>");
		return json;
	}

	@RequestMapping(value = "/physician/adminphysiciandetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadPhysicianDetails(@RequestParam String searchphysician,
			@RequestParam String orgid, @RequestParam String practiceid) {
		logger.info("adminphysiciandetails starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		int emailExist = 0;
		List<SearchPhysicianForm> registrationFormVO = null;
		try {
			registrationFormVO = userManager.getPhysicianDetails(
					searchphysician, orgid, practiceid);

			if (registrationFormVO != null && registrationFormVO.size() > 0) {
				json = new Gson().toJson(registrationFormVO);
			} else {
				json = new Gson().toJson(null);
			}

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("adminphysiciandetails ends");
		return json;
	}

	@RequestMapping(value = "/physician/adminsearchkeywordvisitrecords.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadSearchKeywordVisitRecords(@RequestParam String keyword) {
		logger.info("AdminController loadTagVisitRecords starts ");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		// List<UserVO> registrationFormVO = null;
		List<VisitsVO> physicianVisitdetails = null;

		List<AdminSearchDateForm> adminSearchDateForms = null;
		AdminSearchDateForm adminSearchDateForm = null;
		try {
			adminSearchDateForms = new ArrayList<AdminSearchDateForm>();
			physicianVisitdetails = physicianManager
					.getKeywordVisitDetails(keyword);

			// registrationFormVO =
			// userManager.getSearchVisitDetails(searchDate);
			adminSearchDateForm = new AdminSearchDateForm();
			for (VisitsVO physicianVO : physicianVisitdetails) {

				adminSearchDateForm.setPatientname(userManager
						.getUserName(physicianVO.getPatientid()));
				adminSearchDateForm.setPhysicianname(userManager
						.getUserName(physicianVO.getPhysicianid()));
				adminSearchDateForm.setPatientdob(userManager
						.getDob(physicianVO.getPhysicianid()));
				adminSearchDateForm
						.setDateofvisit(physicianVO.getDateofvisit());
				adminSearchDateForm.setReasonforvisit(physicianVO
						.getReasonforvisit());

				adminSearchDateForms.add(adminSearchDateForm);
			}

			if (adminSearchDateForms != null && adminSearchDateForms.size() > 0) {
				json = new Gson().toJson(adminSearchDateForms);
			} else {
				json = new Gson().toJson(null);
			}

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("AdminController loadTagVisitRecords ends");
		return json;
	}

	@RequestMapping("/physician/requestshare.do")
	public String requestSharePhysician(ModelMap model) {
		logger.debug("PhysicianController requestSharePhysician starts==>");
		String viewpage = Constants.PHYSICIAN_SHARE_REQUEST;
		return viewpage;
	}

	@RequestMapping("/physician/requestfrompatient.do")
	public String requestFromPatient(ModelMap model) {
		logger.debug("PhysicianController requestFromPatient starts==>");
		String viewpage = Constants.PHYSICIAN_PATIENT_REQUEST;
		return viewpage;
	}

	@RequestMapping(value = "/physician/sharerequest.do", method = RequestMethod.GET)
	public @ResponseBody
	String RequestShareProcess(@RequestParam Integer option,
			@RequestParam Integer visitId, @RequestParam Integer patientId,
			HttpServletRequest request) {

		logger.info("PhysicianController RequestShareProcess starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		ShareClinicalInfo shareVo = new ShareClinicalInfo();
		UserVO userVo = new UserVO();
		UserVO userVo1 = new UserVO();
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer userid = user.getId();
		VisitsVO visitVo = new VisitsVO();
		Timestamp timestamp = new Timestamp(new Date().getTime());
		try {

			if (option.equals(2)) {

				userVo = userManager.getUserRegistrationByUserId(patientId);

				userVo1 = userManager.getUserRegistrationByUserId(userid);

				visitVo.setId(visitId);

				shareVo.setShareTo(userVo);

				shareVo.setShareFrom(userVo1);

				// shareVo.setVisit(visitVo);

				shareVo.setCreatedby(userid);

				shareVo.setCreatedon(timestamp);

				shareVo.setFromDate(timestamp);
				shareVo.setStatus(1);

				shareVo.setRequestStatus(Constants.PENDING);

				Integer shareReqId = physicianManager.saveShareRequest(shareVo);

				RoleVO role = userVo.getRole();

				// getUserDetilsByUserId
				UserDetails details = new UserDetails();
				details = userManager.getUserDetilsByUserId(patientId);

				UserDetails details1 = new UserDetails();
				details1 = userManager.getUserDetilsByUserId(userid);

				String serverUrl = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + request.getContextPath();
				String url = serverUrl
						+ "/physician/viewShareRequest.do?shareId="
						+ shareReqId;

				String req = "YES";
				MailSupport.sendShareRequestMail(details.getEmail(),
						details.getUsername(), shareReqId, role.getOid(), url,
						details1.getUsername(), req);

				jsonResponse.setMessage("Request For Share Send Successfully.");
			} else {

				userVo = userManager.getUserRegistrationByUserId(patientId);

				userVo1 = userManager.getUserRegistrationByUserId(userid);

				visitVo.setId(visitId);

				shareVo.setShareTo(userVo);

				shareVo.setShareFrom(userVo1);

				// shareVo.setVisit(visitVo);

				shareVo.setCreatedby(userid);

				shareVo.setCreatedon(timestamp);

				shareVo.setFromDate(timestamp);
				shareVo.setStatus(1);

				shareVo.setRequestStatus(Constants.PENDING);

				Integer shareReqId = physicianManager.saveShareRequest(shareVo);

				RoleVO role = userVo.getRole();

				// getUserDetilsByUserId
				UserDetails details = new UserDetails();
				details = userManager.getUserDetilsByUserId(patientId);

				UserDetails details1 = new UserDetails();
				details1 = userManager.getUserDetilsByUserId(userid);

				String serverUrl = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + request.getContextPath();
				String url = serverUrl
						+ "/physician/viewShareRequest.do?shareId="
						+ shareReqId;
				String req = "Requesting For Digital Signature";

				MailSupport.sendShareRequestMail(details.getEmail(),
						details.getUsername(), shareReqId, role.getOid(), url,
						details1.getUsername(), req);

				jsonResponse
						.setMessage("Request For Digital Signature Successfully.");
			}

			json = new Gson().toJson(jsonResponse);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			jsonResponse.setMessage("Error To Requesting a Share.");
		}
		logger.info("PhysicianController RequestShareProcess ends");
		return json;
	}

	@RequestMapping("/physician/requestinfopatient.do")
	public String physicianRequestInfoBehalfOfPatient(ModelMap model) {
		logger.debug("PhysicianController physicianRequestInfoBehalfOfPatient starts==>");
		String viewpage = Constants.PHYSICIAN_REQUEST_INFO;
		return viewpage;
	}

	@RequestMapping(value = "/physician/getPhysicianSharedRequest.do", method = RequestMethod.GET)
	public @ResponseBody
	String getPhysicianSharedRequest(ModelMap model, @RequestParam String status) {
		logger.debug("PhysicianMainController getPhysicianSharedRequest physicianVisits==> ");
		String json = null;
		List<Object[]> phySharedDto = null;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer userId = user.getId();
		String loadStatus = null;

		// status = "1";
		// if(status.equals("1")){
		//
		// loadStatus =Constants.APPROVED;
		// }else{
		//
		// loadStatus = Constants.PENDING;
		// }

		try {
			phySharedDto = physicianManager.getPhysicianSharedDetails(userId,
					loadStatus);
			for (Object[] obj : phySharedDto) {
				Date dd = (Date) obj[2];
				obj[2] = CommonUtils.parseStringFromDate(dd);
			}

			json = new Gson().toJson(phySharedDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("PhysicianMainController getPhysicianSharedRequest ends ");

		return json;
	}

	@RequestMapping("/physician/viewShareRequest.do")
	public String viewShareRequest(ModelMap model, @RequestParam Integer shareId) {
		logger.debug("PhysicianController viewShareRequest starts==>");
		List<Object[]> patDetails = null;
		Timestamp timestamp = new Timestamp(new Date().getTime());
		Map map = new HashMap();
		Boolean flag = false;
		// map.put("patientId", patientId);
		map.put("shareId", shareId);
		// map.put("visitId", visitId);
		String info = null;
		Map<String, Object> returnmap = new HashMap<String, Object>();

		String status = "Share";
		ShareClinicalInfo shareVo = new ShareClinicalInfo();

		shareVo = physicianManager.finShareDetails(shareId);

		if (shareVo.getRequestStatus() != null
				&& !shareVo.getRequestStatus().equals("Pending")) {
			if (shareVo.getRequestDate() == null) {

				shareVo.setRequestDate(timestamp);

				physicianManager.updateShareDetails(shareVo);
			}

			List<Object[]> documentVO = null;
			flag = physicianManager.checkSharExpiry(shareId);
			if (flag) {
				patDetails = physicianManager.getPatientVisitDetails(map);

				documentVO = physicianManager.getAllSharedDocuments(shareId, status);
				// p.id,p.patientid,p.docname,p.docpath,p.createdon
				for (Object[] obj : documentVO) {

					Timestamp d = (Timestamp) obj[4];
					obj[4] = CommonUtils.parseStringFromTimeStampData(d);

				}

			} else {

				info = "Share Request Data Expired.";

			}
			returnmap.put("patientList", patDetails);
			returnmap.put("docList", documentVO);

			model.addAttribute("shareDet", returnmap);

			model.addAttribute("info", info);
		} else {

			info = "Share Request  Not Approved By Patient.";
			model.addAttribute("info", info);
		}

		String viewpage = Constants.PHYSICIAN_SHARED_DOCUEMENT;
		return viewpage;
	}

	@RequestMapping("/physician/physicianPatShareView.do")
	public String physicianPatShareView(ModelMap model) {
		logger.debug("PhysicianController physicianvisits==>");
		String viewpage = Constants.PHYSICIAN_LOGIN_PATIENT_SHARE;
		return viewpage;
	}

	@RequestMapping("/physician/physicianPatientShareDocuments.do")
	public @ResponseBody
	String physicianPatientShareDocuments(@RequestParam String patientId,
			ModelMap model) {

		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer userid = user.getId();
		Integer patId = 0;
		if (patientId != "") {
			patId = Integer.parseInt(patientId);
		}
		String json = null;

		String viewpage = Constants.PHYSICIAN_LOGIN_PATIENT_SHARE;
		List<Object[]> documentVO = null;

		logger.info("patientId " + patientId);
		try {
			documentVO = physicianManager
					.getAllDocumentsBypatIdInPhysician(patId);

			// p.id,p.patientid,p.docname,p.docpath,p.createdon

			for (Object[] obj : documentVO) {
				Timestamp d = (Timestamp) obj[4];
				obj[4] = CommonUtils.parseStringFromTimeStampData(d);
			}
			json = new Gson().toJson(documentVO);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		return json;
	}

	@RequestMapping(value = "/physician/requestBehalfOfPatient.do", method = RequestMethod.POST)
	public String requestBehalfOfPatient(
			@ModelAttribute("RequestInfo") RequestInfoBehalf reqInfo,
			SessionStatus sessionStatus, ModelMap model,
			HttpServletRequest request) {

		logger.info("PhysicianMainController requestBehalfOfPatient() starts");

		String viewPage = Constants.PHYSICIAN_REQUEST_INFO;
		int regsiter = 1;
		try {
			Timestamp timestamp = new Timestamp(new Date().getTime());
			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			Integer userId = user.getId();

			RequestInfoOfPatientVO infoVO = new RequestInfoOfPatientVO();

			infoVO.setRequestfrom(userId);
			infoVO.setRequestStatus(Constants.PENDING);

			infoVO.setStatus(1);
			infoVO.setRequesttopatient(reqInfo.getPatientId());
			infoVO.setRequesttophysician(reqInfo.getPhysicianId());

			infoVO.setCreatedby(userId);
			infoVO.setCreatedon(timestamp);

			Integer id = physicianManager.saveRequestBehalfOfPatient(infoVO);

			// sendPatientRequestBehalfMail
			String serverUrl = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();

			String url = serverUrl
					+ "/physician/uploadClinicalDocuments.do?requestId=" + id
					+ "&patientId=" + reqInfo.getPatientId()
					+ "&patientName=Patient";

			// UserVO ursVo =
			// userManager.getUserRegistrationByUserId(reqInfo.getPatientId());

			// UserVO ursVo1 = userManager.getUserRegistrationByUserId(userId);

			// UserVO ursVo2 =
			// userManager.getUserRegistrationByUserId(reqInfo.getPhysicianId());

			UserDetails ursVo = new UserDetails();
			ursVo = userManager.getUserDetilsByUserId(reqInfo.getPatientId());

			UserDetails ursVo2 = new UserDetails();
			ursVo2 = userManager
					.getUserDetilsByUserId(reqInfo.getPhysicianId());

			UserDetails ursVo1 = new UserDetails();
			ursVo1 = userManager.getUserDetilsByUserId(userId);

			MailSupport.sendPatientRequestBehalfMail(ursVo.getEmail(),
					ursVo.getUsername(), serverUrl, ursVo1.getUsername(),
					ursVo2.getUsername());

			MailSupport.sendPhysicianRequestBehalfMail(ursVo2.getEmail(),
					ursVo2.getUsername(), serverUrl, ursVo.getUsername(),
					ursVo1.getUsername(), url);

			sessionStatus.setComplete();

			model.addAttribute("EXCEPTION",
					"Request Send To Patient and physician");

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		logger.info("PhysicianMainController requestBehalfOfPatient() ends");
		return viewPage;
	}

	//

	@RequestMapping("/physician/viewrequestinfopatient.do")
	public String viewPhysicianRequestInfoBehalfOfPatient(ModelMap model) {
		logger.debug("PhysicianController viewPhysicianRequestInfoBehalfOfPatient starts==>");
		String viewpage = Constants.PHYSICIAN_VIEW_REQUEST_INFO;
		return viewpage;
	}

	@RequestMapping(value = "/physician/getrequestinfoofpatient.do", method = RequestMethod.GET)
	public @ResponseBody
	String getrequestinfoofpatient(ModelMap model) {
		logger.debug("PhysicianMainController getrequestinfoofpatient physicianVisits==> ");
		String json = null;
		List<Object[]> phySharedDto = null;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer userId = user.getId();

		try {
			phySharedDto = physicianManager
					.getPhysicianrequestedSharedInfo(userId);
			/*
			 * for(Object[] obj:phySharedDto){ Date dd = (Date) obj[2]; obj[2] =
			 * CommonUtils.parseStringFromDate(dd); }
			 */
			json = new Gson().toJson(phySharedDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("PhysicianMainController getrequestinfoofpatient ends ");

		return json;
	}

	@RequestMapping("/physician/uploadClinicalDocuments.do")
	public String uploadClinicalDocuments(ModelMap model,
			@RequestParam Integer requestId, @RequestParam Integer patientId,
			@RequestParam String patientName) {
		logger.debug("PhysicianMainController uploadClinicalDocuments physicianVisits==> ");

		String viewpage = Constants.PHYSICIAN_VIEW_UPLOAD_DOCUMENTS;

		model.addAttribute("requestId", requestId);

		model.addAttribute("patientId", patientId);

		model.addAttribute("patientName", patientName);

		return viewpage;
	}

	private String moveFileToCourseLocation(Integer userid,
			Integer destinationUserId, HttpServletRequest request,
			Integer requestId) {

		logger.info("moveFileToCourseLocation");
		String uploadPath = null;
		String downloadPath = null;
		File sourceFile = null;
		File destinationFile = null;
		String returnPath = null;
		String result = null;
		String sourcePath = Constants
				.getPropertyValue(Constants.FILE_UPLOAD_PATH);
		String destinationPath = Constants
				.getPropertyValue(Constants.FAREFILE_UPLOAD_PATH);

		try {

			sourceFile = new File(sourcePath + userid.toString());

			Date newDate = new Date();

			String time = newDate.getTime() + "";
			File folder = new File(sourcePath + userid.toString());
			File[] listOfFiles = folder.listFiles();
			Integer createdby = userid;
			Timestamp createdOnTime = new Timestamp(new Date().getTime());

			if (listOfFiles != null && listOfFiles.length > 0) {
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						File fileName = new File(sourcePath + userid.toString()
								+ "/" + listOfFiles[i].getName());

						// destinationFile = new
						// File(destinationPath+destinationUserId.toString()+"/"+time+""+"/"+listOfFiles[i].getName());

						// Change for update
						destinationFile = new File(destinationPath
								+ destinationUserId.toString() + "/"
								+ listOfFiles[i].getName());

						if (fileName.exists()) {
							if (destinationFile.exists()) {
								File fileNameDel = new File(sourcePath
										+ userid.toString());

								File[] finlist = fileNameDel.listFiles();
								for (int n = 0; n < finlist.length; n++) {
									if (finlist[n].isFile()) {
										System.gc();
										finlist[n].delete();
									}
								}

								PatientDocument documentVO = new PatientDocument();

								documentVO.setCreatedby(createdby);
								documentVO.setCreatedon(createdOnTime);

								documentVO.setDocname(listOfFiles[i].getName());
								documentVO.setDocpath(destinationFile + "");
								documentVO.setPatientid(destinationUserId);
								documentVO.setUploadType("Request");
								documentVO.setKeyid(requestId);
								documentVO.setKey("RequestInfo");
								Integer id = userManager
										.savePatientDocument(documentVO);

								AuditTrailVO dto = AuditTrailUtil
										.SaveAuditTrailDetails(id, request,
												Constants.PATIENT_DOCUMENTS,
												Constants.INSERT,
												"Patient Clinical Documents Uploaded");

								userManager.saveAuditTrails(dto);

							} else {
								FileUtils.moveFile(fileName, destinationFile);
								PatientDocument documentVO = new PatientDocument();

								documentVO.setCreatedby(createdby);
								documentVO.setCreatedon(createdOnTime);

								documentVO.setDocname(listOfFiles[i].getName());
								documentVO.setDocpath(destinationFile + "");
								documentVO.setPatientid(destinationUserId);
								documentVO.setUploadType("Request");
								documentVO.setKeyid(requestId);
								documentVO.setKey("RequestInfo");

								Integer id = userManager
										.savePatientDocument(documentVO);

								AuditTrailVO dto = AuditTrailUtil
										.SaveAuditTrailDetails(id, request,
												Constants.PATIENT_DOCUMENTS,
												Constants.INSERT,
												"Patient Clinical Documents Uploaded");

								userManager.saveAuditTrails(dto);
							}

						}

					} else if (listOfFiles[i].isDirectory()) {
					}
				}

				result = "YES";
			}

			else {

				result = "NO";
			}

			// return uploadPath.toString();
			// returnPath=uploadPath.toString();
		} catch (Exception e) {
			// returnPath=uploadPath.toString();
			e.printStackTrace();
		} finally {
			sourceFile = null;
			downloadPath = null;
			destinationFile = null;
		}

		return result;
	}

	@RequestMapping(value = "/physician/saveShareRequestBehalfOfPatient.do", method = RequestMethod.GET)
	synchronized public @ResponseBody
	String saveShareRequestBehalfOfPatient(ModelMap model,
			@RequestParam Integer requestId, @RequestParam Integer patientid,
			HttpServletRequest request) {
		logger.debug("PhysicianMainController saveShareRequestBehalfOfPatient physicianVisits==> ");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		RequestInfoOfPatientVO reqVo = new RequestInfoOfPatientVO();

		try {

			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();

			Integer userid = user.getId();

			String result = moveFileToCourseLocation(userid, patientid,
					request, requestId);

			if (result.equals("YES")) {

				jsonResponse.setStatus("YES");
				jsonResponse.setMessage("File Uploaded Successfully");

				reqVo = physicianManager.findReqBehalfOfPatient(requestId);

				reqVo.setRequestStatus(Constants.APPROVED);
				physicianManager.updateReqBehalfOfPatient(reqVo);

				UserDetails userVo = new UserDetails();

				// UserVO userVo = new UserVO();
				userVo = userManager.getUserDetilsByUserId(reqVo
						.getRequesttophysician());

				UserDetails userVo1 = new UserDetails();

				userVo1 = userManager.getUserDetilsByUserId(reqVo
						.getRequestfrom());

				UserDetails userVo2 = new UserDetails();

				userVo2 = userManager.getUserDetilsByUserId(reqVo
						.getRequesttopatient());

				String serverUrl = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + request.getContextPath();

				String url = serverUrl
						+ "/physician/viewShareRequestInfo.do?requestId="
						+ requestId;

				MailSupport.sendPhysicianApprovedBehalfMail(userVo.getEmail(),
						userVo.getUsername(), userVo1.getUsername(), url,
						userVo2.getUsername());

			} else {
				jsonResponse.setStatus("NO");
				jsonResponse.setMessage("Please Choose the File");

			}

			json = new Gson().toJson(jsonResponse);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error Processing uploadClinicalInformations : "
					+ e.getMessage());
		}

		logger.info("PhysicianMainController saveShareRequestBehalfOfPatient ends");
		return json;
	}

	@RequestMapping("/physician/viewapprovedrequestinfopatient.do")
	public String viewapprovedrequestinfopatient(ModelMap model) {
		logger.debug("PhysicianController viewapprovedrequestinfopatient starts==>");
		String viewpage = Constants.PHYSICIAN_VIEW_APPROVED_REQUEST_INFO;
		return viewpage;
	}

	@RequestMapping(value = "/physician/getPhysicianSharedRequestInfo.do", method = RequestMethod.GET)
	public @ResponseBody
	String getPhysicianSharedRequestInfo(ModelMap model) {
		logger.debug("PhysicianMainController getPhysicianSharedRequestInfo physicianVisits==> ");
		String json = null;
		List<Object[]> phySharedDto = null;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer userId = user.getId();
		Map map = new HashMap();
		map.put("userId", userId);

		try {
			phySharedDto = physicianManager.getPhysicianSharedInfoDetails(map);

			for (Object[] obj : phySharedDto) {
				Date dd = (Date) obj[2];
				obj[2] = CommonUtils.parseStringFromDate(dd);
			}

			json = new Gson().toJson(phySharedDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("PhysicianMainController getPhysicianSharedRequestInfo ends ");

		return json;
	}

	@RequestMapping("/common/view.do")
	public String viewPage(ModelMap model) {

		return "registration/userregistrationsuccess";

	}

	@RequestMapping("/physician/viewShareRequestInfo.do")
	public String viewShareRequestInfo(ModelMap model,
			@RequestParam Integer requestId) {
		logger.debug("PhysicianController viewShareRequest starts==>");
		List<Object[]> patDetails = null;
		Timestamp timestamp = new Timestamp(new Date().getTime());
		Map map = new HashMap();
		Boolean flag = false;
		// map.put("patientId", patientId);
		map.put("requestId", requestId);
		// map.put("visitId", visitId);
		String info = null;
		Map<String, Object> returnmap = new HashMap<String, Object>();

		String status = "Request";
		RequestInfoOfPatientVO shareVo = new RequestInfoOfPatientVO();

		shareVo = physicianManager.findReqBehalfOfPatient(requestId);

		if (shareVo.getRequestStatus() != null
				&& !shareVo.getRequestStatus().equals("Pending")) {
			if (shareVo.getRequestDate() == null) {

				shareVo.setRequestDate(timestamp);

				physicianManager.updateReqBehalfOfPatient(shareVo);
			}

			List<Object[]> documentVO = null;
			flag = physicianManager.checkRequestExpiry(requestId);
			if (flag) {
				patDetails = physicianManager.getPatientDetails(map);

				documentVO = physicianManager
						.getAllDocumentsRequestBehalfOfPatient(requestId,
								status);
				// p.id,p.patientid,p.docname,p.docpath,p.createdon
				for (Object[] obj : documentVO) {

					Timestamp d = (Timestamp) obj[4];
					obj[4] = CommonUtils.parseStringFromTimeStampData(d);

				}

			} else {

				info = "Share Request Data Expired.";

			}
			returnmap.put("patientList", patDetails);
			returnmap.put("docList", documentVO);

			model.addAttribute("shareDet", returnmap);

			model.addAttribute("info", info);
		} else {

			info = "Share Request  Not Approved By Patient.";
			model.addAttribute("info", info);
		}

		String viewpage = Constants.PHYSICIAN_SHARED_DOCUEMENT_BEHALF;
		return viewpage;
	}

	@RequestMapping(value = "/physician/getPhysicianAddInfo.do", method = RequestMethod.GET)
	public @ResponseBody
	String getPhysicianAddInfo(ModelMap model) {
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		// List<UserVO> registrationFormVO = null;
		List<UserPersonalInfoVO> physicianAddInfo = null;

		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer userId = user.getId();

		try {

			physicianAddInfo = physicianManager
					.getPhysicianAdditionalInformation(userId);

			if (physicianAddInfo != null && physicianAddInfo.size() > 0) {
				json = new Gson().toJson(physicianAddInfo);
			} else {
				json = new Gson().toJson(null);
			}

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("physicianController getPhysicianAddInfo ends");
		return json;
	}

	@RequestMapping(value = "/physician/updatePhysicianPersonalDetails.do", method = RequestMethod.POST)
	public String updatePhysicianPersonalDetails(
			@ModelAttribute("PhysicianPersonalForm") phyPersonalForm phyForm,
			SessionStatus sessionStatus, ModelMap model) {

		logger.info("physician/updatePhysicianPersonalDetails.do");
		// String viewPage = Constants.HOME_PHYSICIAN_DASHBOARD;

		String viewPage = Constants.HOME_PHYSICIAN_DASHBOARD;
		int regsiter = 1;
		try {
			Timestamp timestamp = new Timestamp(new Date().getTime());
			// RoleVO roleDto = new RoleVO();

			// List<RoleVO> roleDto =
			// physicianManager.findRoleVoByRoleId(userRegistrationForm.getUserType());

			UserPersonalInfoVO userPersonalInfoDto = new UserPersonalInfoVO();
			userPersonalInfoDto.setFirstname(phyForm.getFirstname());
			userPersonalInfoDto.setMiddlename(phyForm.getMiddlename());
			userPersonalInfoDto.setLastname(phyForm.getLastname());

			userPersonalInfoDto.setContact_number(phyForm.getContactNo());
			userPersonalInfoDto.setStreet(phyForm.getStreet());
			userPersonalInfoDto.setCity(phyForm.getCity());
			userPersonalInfoDto.setZip(phyForm.getZip());
			userPersonalInfoDto.setDegree(phyForm.getDegree());
			userPersonalInfoDto.setEmail(phyForm.getEmail());
			userPersonalInfoDto.setLanguage(phyForm.getLanguage());
			userPersonalInfoDto.setFax(phyForm.getFax());
			userPersonalInfoDto.setOffice_phone(phyForm.getOffice_phone());
			userPersonalInfoDto.setUpdatedon(timestamp);

			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			Integer userid = user.getId();

			userPersonalInfoDto.setUserid(userid);
			userPersonalInfoDto.setId(phyForm.getHdnPKID());
			// physicianPersonalDto.setId(56214);

			physicianManager
					.updatePhysicianPersonalDetails(userPersonalInfoDto);
			sessionStatus.setComplete();

			Long totRequestPhysician = userManager.getOutstandingRequestsFromPhysician(userid);
			Long totRequest = userManager.getOutstandingRequests(userid);
			
			Long totalUsers = userManager.getTotalUsers("Patient");

			model.addAttribute("users", totalUsers);
			Long totalVisits = userManager.getTotlPatientsVisits(userid);
			model.addAttribute("totalVisits", totalVisits);
			model.addAttribute("totRequests", totRequest);
			
			model.addAttribute("totRequestPhysician", totRequestPhysician);
			
			model.addAttribute("successMsg",
					Constants.getPropertyValue(Constants.REGISTER_SUCCESS));
			model.addAttribute("redirectMsg",
					Constants.getPropertyValue(Constants.redirect_msg));
			model.addAttribute("regsiter", regsiter);
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		return viewPage;
	}

	@RequestMapping(value = "/physician/patientrequest.do", method = RequestMethod.GET)
	public @ResponseBody
	String requestFromPatient(HttpServletRequest request, ModelMap model,@RequestParam String searchVal) {
		System.out
				.println("PhysicianMainController requestFromPatient starts ");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();

		HashMap paramsMap = new HashMap();

		if(searchVal.equals("")){
			
			paramsMap.put("searchVal", "NO");
		}else{
			
			paramsMap.put("searchVal", searchVal.toLowerCase());
		}
		
		Integer iDisplayStart = null;
		Integer iDisplayLength = null;
		Integer sEcho = null;
		String finalJson = null;
		if (request.getParameter("iDisplayStart") != null
				&& request.getParameter("iDisplayLength") != null
				&& request.getParameter("sEcho") != null) {
			iDisplayStart = Integer.parseInt(request.getParameter(
					"iDisplayStart").toString());
			iDisplayLength = Integer.parseInt(request.getParameter(
					"iDisplayLength").toString());
			sEcho = Integer.parseInt(request.getParameter("sEcho").toString());
		}

		if (iDisplayStart != null && iDisplayLength != null && sEcho != null) {
			paramsMap.put("iDisplayStart", iDisplayStart);
			paramsMap.put("iDisplayLength", iDisplayLength);
			paramsMap.put("sEcho", sEcho);
		}

		String sSortDirection = request.getParameter("sSortDir_0");
		String iSortColumnIndex = request.getParameter("iSortCol_0");
		if (sSortDirection != null && iSortColumnIndex != null) {
			paramsMap.put("sSortDirection", sSortDirection);
			paramsMap.put("iSortColumnIndex", iSortColumnIndex);
		}

		List<Object[]> physicianVisitdetails = null;

		List<AdminSearchDateForm> adminSearchDateForms = null;
		AdminSearchDateForm adminSearchDateForm = null;

		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userId = user.getId();

		String role = user.getRoleid();
		try {
			adminSearchDateForms = new ArrayList<AdminSearchDateForm>();
			/*
			 * physicianVisitdetails =
			 * physicianManager.getSearchVisitRecords(searchDate);
			 */
			physicianVisitdetails = physicianManager.getPatientRequest(userId,
					role, paramsMap);

			if (physicianVisitdetails != null) {

				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
				int reworkCount = 0;
				for (Object[] obj : physicianVisitdetails) {
					Integer id = 0;
					if(obj[2]!=null){
						
						 id = Integer.parseInt(obj[2]+"");
					}
					
					 
					
					String status = null;
					if(id!=0){
						status = "Completed";
					}else{
						status = "Pending";
					}
					
					HashMap searchReportMap = new HashMap();

					searchReportMap.put("Request From",
							userManager.getUserName(Integer.parseInt(obj[1]+"")));
					searchReportMap.put("Status",status);

					searchReportMap.put("id", Integer.parseInt(obj[1]+""));
					searchReportMap.put("key", Integer.parseInt(obj[0]+""));
					
					searchReportArrayList.add(searchReportMap);
				}
				Long count = physicianManager.getPatientRequestCount(userId,
						role, paramsMap);

				finalJson = CommonUtils.convertMaptoJsonForGrid(
						searchReportArrayList, Long.parseLong(count + ""),
						sEcho);
				model.addAttribute("finalJson", finalJson);

			} else {

				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
				finalJson = CommonUtils.convertMaptoJsonForGrid(
						searchReportArrayList, 0L, sEcho);
				model.addAttribute("finalJson", finalJson);

			}

			// json = new Gson().toJson(adminSearchDateForms);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("PhysicianMainController requestFromPatient ends");

		return finalJson;
	}
}
