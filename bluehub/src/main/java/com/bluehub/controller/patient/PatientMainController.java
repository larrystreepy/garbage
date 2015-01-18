package com.bluehub.controller.patient;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.bluehub.bean.common.UserDetails;
import com.bluehub.bean.user.JsonResponse;
import com.bluehub.bean.user.UserProfile;
import com.bluehub.bean.user.phyPersonalForm;
import com.bluehub.manager.admin.AdminOrganizationManager;
import com.bluehub.manager.physician.PhysicianManager;
import com.bluehub.manager.user.UserManager;
import com.bluehub.util.AuditTrailUtil;
import com.bluehub.util.CommonUtils;
import com.bluehub.util.Constants;
import com.bluehub.util.EmailValidator;
import com.bluehub.util.MailSupport;
import com.bluehub.vo.admin.AdminOrganizationVO;
import com.bluehub.vo.admin.AdminPracticeVO;
import com.bluehub.vo.admin.FaxVo;
import com.bluehub.vo.common.AuditTrailVO;
import com.bluehub.vo.common.PatientDocument;
import com.bluehub.vo.common.ShareClinicalInfo;
import com.bluehub.vo.physician.UserPersonalInfoVO;
import com.bluehub.vo.physician.VisitsVO;
import com.bluehub.vo.user.PatientRequestVO;
import com.bluehub.vo.user.UserVO;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.sfax.rest.SFax;
import com.sfax.rest.outbound.OCreate;
import com.sfax.rest.xml.ServiceResponse;

@Controller
public class PatientMainController {

	public static Logger logger = Logger.getLogger(PatientMainController.class);

	@Autowired
	private PhysicianManager physicianManager;

	public void setPhysicianManager(PhysicianManager physicianManager) {
		this.physicianManager = physicianManager;
	}

	@Autowired
	private AdminOrganizationManager adminOrganizationManager;

	public void setAdminOrganizationManager(
			AdminOrganizationManager adminOrganizationManager) {
		this.adminOrganizationManager = adminOrganizationManager;
	}

	@Autowired
	private UserManager userManager;

	/**
	 * @param userManager
	 *            the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@RequestMapping("/patient/patientPersonalDetails.do")
	public String physicianPersonal(ModelMap model) {
		logger.debug("PhysicianController physicianhome==>");
		String viewpage = Constants.PATIENT_PERSONAL_DETAILS_VIEW;
		return viewpage;
	}

	@RequestMapping(value = "/patient/getPatientAddInfo.do", method = RequestMethod.GET)
	public @ResponseBody
	String getPatientAddInfo(ModelMap model) {
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		// List<UserVO> registrationFormVO = null;
		List<UserPersonalInfoVO> patientAddInfo = null;

		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer userId = user.getId();
        try {

			patientAddInfo = physicianManager
					.getPatientAdditionalInformation(userId);

			if (patientAddInfo != null && patientAddInfo.size() > 0) {
				json = new Gson().toJson(patientAddInfo);
			} else {
				json = new Gson().toJson(null);
			}

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("patientController getPatientAddInfo ends");
		return json;
	}

	//

	@RequestMapping("/patient/home.do")
	public String patientHome(ModelMap model) {
		Object[] obj = null;
		String viewPage = null;
		boolean flag = false;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userId = user.getId();

		obj = userManager.getPatientVisitRecords(userId);
		String note = null;
		int visitid = 0;

		if (obj != null) {

			visitid = Integer.parseInt(obj[3] + "");

			note = userManager.getPrivateNoteByVisitAndPatientId(visitid,
					userId);

			/*
			 * String visitDate = obj[0]+""; String [] str =
			 * visitDate.split(" 00"); String [] str1 = str[0].split("-");
			 */
			Date dd = (Date) obj[0];
			if (obj[2] != null) {

				model.addAttribute("tag", obj[2]);
			} else {

				model.addAttribute("tag", "---");
			}
			String totalVisits = obj[1] + "";
			model.addAttribute("date", CommonUtils.parseStringFromDate(dd));
			model.addAttribute("visits", totalVisits);
			model.addAttribute("note", note);

		} else {

			model.addAttribute("tag", "---");
			model.addAttribute("date", "---");
			model.addAttribute("visits", 0);
			model.addAttribute("note", "---");
		}

		Integer uid = user.getId();

		ShareClinicalInfo shareVo = new ShareClinicalInfo();

		List<ShareClinicalInfo> shareList = adminOrganizationManager
				.getListOfPatientShareVo(userId);
		int count = 0;
		int approved = 0;

		int pending = 0;
		if (shareList != null) {

			count = shareList.size();

			for (ShareClinicalInfo dto : shareList) {

				if (dto.getRequestStatus().equals(Constants.APPROVED)) {
					approved++;
				}

			}

			pending = count - approved;

			model.addAttribute("total", count);
			model.addAttribute("approved", approved);
			model.addAttribute("pending", pending);
			
			Integer faxcount = adminOrganizationManager.getPatientFaxRequestCount(userId);
			
			model.addAttribute("faxtotal", faxcount);
		}

		flag = userManager.checkUserAdditionalInfo(uid);

		// viewPage = Constants.HOME_PATIENT_VIEW;

		if (flag) {
			// additional info
			viewPage = Constants.PATIENT_PERSONAL_DETAILS_VIEW;
		} else {
			viewPage = Constants.HOME_PATIENT_VIEW;
		}

		logger.debug("PatientController patienthome==>");
		// String viewPage = Constants.HOME_PATIENT_VIEW;
		return viewPage;
	}

	@RequestMapping("/patient/patientsearch.do")
	public String patientSearch(ModelMap model) {

		logger.debug("patientSearch");
		String viewPage = Constants.SEARCH_PATIENT_VIEW;
		return viewPage;
	}

	@RequestMapping("/patient/patientshare.do")
	public String patientShare(ModelMap model) {

		logger.debug("patientShare");
		String viewPage = Constants.SHARE_PATIENT_VIEW;
		return viewPage;
	}

	@RequestMapping("/patient/patientsharetab.do")
	public String patientShareTab(ModelMap model) {

		logger.debug("patientShare");
		String viewPage = Constants.TAB_SHARE_PATIENT_VIEW;
		return viewPage;
	}

	@RequestMapping("/patient/patienttag.do")
	public String patientTag(ModelMap model) {

		logger.debug("patienttag");
		String viewPage = Constants.TAG_PATIENT_VIEW;
		return viewPage;
	}

	@RequestMapping("/patient/patientprivatenote.do")
	public String patientPrivateNote(ModelMap model) {

		logger.debug("patientprivatenote");
		String viewPage = Constants.PRIVATENOTE_PATIENT_VIEW;
		return viewPage;
	}
	
	@RequestMapping("/patient/patientupload.do")
	public String patientUpload(ModelMap model) {

		logger.debug("patientUpload");
		String viewPage = Constants.UPLOAD_PATIENT_VIEW;
		return viewPage;
	}

	@RequestMapping("/patient/patientrequest.do")
	public String patientRequest(ModelMap model) {

		logger.debug("patientRequest");
		String viewPage = Constants.REQUEST_PATIENT_VIEW;
		return viewPage;
	}
	
	@RequestMapping("/patient/patientrequestclinicalinformation.do")
	public String patientRequestClinicalInformation(ModelMap model) {

		logger.debug("patientRequest");
		String viewPage = Constants.REQUEST_PATIENT_VIEWS;
		return viewPage;
	}
	
	@RequestMapping("/patient/patientview.do")
	public String patientView(ModelMap model) {

		logger.debug("patientView");
		String viewPage = Constants.VIEW_PATIENT_VIEW;
		return viewPage;
	}

	/* Added For Search In Patient */

	@RequestMapping(value = "/patient/adminorganizations.do", method = RequestMethod.GET)
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

	@RequestMapping(value = "/patient/loadpractice.do", method = RequestMethod.GET)
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

	@RequestMapping(value = "/patient/adminphysiciandetails.do", method = RequestMethod.GET)
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

	@RequestMapping(value = "/patient/adminsearchkeywordvisitrecords.do", method = RequestMethod.GET)
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

				adminSearchDateForm.setVisitdate(CommonUtils
						.parseStringFromDate(physicianVO.getDateofvisit()));

				adminSearchDateForm.setDob(CommonUtils
						.parseStringFromDate(userManager.getDob(physicianVO
								.getPatientid())));

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

	@RequestMapping(value = "/patient/patientviewdetails.do", method = RequestMethod.GET)
	public String loadPatientVisitDetails(ModelMap model) {
		logger.info("loadPatientVisitDetails starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		int nameExist = 0;

		logger.info("loadPatientVisitDetails");
		String viewPage = Constants.PATIENT_VISITDETAILS_VIEW;
		List<VisitsVO> physicianVisitdetails = null;

		List<AdminSearchDateForm> adminSearchDateForms = null;
		AdminSearchDateForm adminSearchDateForm = null;
		try {
			adminSearchDateForms = new ArrayList<AdminSearchDateForm>();
			physicianVisitdetails = physicianManager
					.getKeywordVisitDetails("s");

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

				model.addAttribute("patientname"
						+ userManager.getUserName(physicianVO.getPatientid()));
				model.addAttribute("dob"
						+ userManager.getDob(physicianVO.getPatientid()));
				model.addAttribute("dateofvisit" + physicianVO.getDateofvisit());
				model.addAttribute("reasonforvisit"
						+ physicianVO.getReasonforvisit());
				// model.addAttribute("patientname"+userManager.getUserName(physicianVO.getPatientid()));
				adminSearchDateForms.add(adminSearchDateForm);
				adminSearchDateForm.setVisitdate(CommonUtils
						.parseStringFromDate(physicianVO.getDateofvisit()));

				adminSearchDateForm.setDob(CommonUtils
						.parseStringFromDate(userManager.getDob(physicianVO
								.getPatientid())));

			}

			model.addAttribute("adminSearchDateForms", adminSearchDateForms);

			// json = new Gson().toJson(adminSearchDateForms);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("loadPatientVisitDetails ends");

		return viewPage;
	}

	// Mail
	
	
	@RequestMapping(value = "/patient/patientsearchorganizationinfo.do", method = RequestMethod.GET)
	public @ResponseBody
	String patientSearchOrganizationInfo(@RequestParam Integer orgId,
			HttpServletRequest request) {
		logger.info("patientSearchPhysicianInfo starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		String fax = null;
		
		try{
			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();

			Integer patientId = user.getId();
			fax = physicianManager.getOrganizationIdForPatient(orgId,patientId);
			
		if(fax!=null){
		jsonResponse.setMessage(fax);
		
		}else{
			jsonResponse.setMessage("");
		}
		json = new Gson().toJson(jsonResponse);
		}
		catch(Exception e){
			logger.error(Constants.LOG_ERROR + e.getMessage());
			jsonResponse.setMessage("");
			json = new Gson().toJson(jsonResponse);
		}
		logger.info("patientSearchPhysicianInfo Ends");
		return json;
	}
	
	@RequestMapping(value = "/patient/patientsearchphysicianinfoinfo.do", method = RequestMethod.GET)
	public @ResponseBody
	String patientSearchPhysicianInfo(@RequestParam Integer physicianid,
			HttpServletRequest request) {
		logger.info("patientSearchPhysicianInfo starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		Object[] obj = null;
		
		try{
		obj = physicianManager.getPhysicianDetails(physicianid);
		if(obj!=null){
		jsonResponse.setMessage(obj[0]+"");
		jsonResponse.setStatus(obj[1]+"");
		
		}else{
			jsonResponse.setMessage("");
			jsonResponse.setStatus("");
		}
		json = new Gson().toJson(jsonResponse);
		}
		catch(Exception e){
			logger.error(Constants.LOG_ERROR + e.getMessage());
			jsonResponse.setMessage("");
			json = new Gson().toJson(jsonResponse);
		}
		logger.info("patientSearchPhysicianInfo Ends");
		return json;
	}
	
	@RequestMapping(value = "/patient/patientrequestinfo.do", method = RequestMethod.GET)
	public @ResponseBody
	String savePatientRequest(@RequestParam String physicianid,
			HttpServletRequest request) {
		logger.info("savePatientRequest starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userId = user.getId();

		int emailExist = 0;

		try {
			PatientRequestVO patientRequestVO = null;

			patientRequestVO = new PatientRequestVO();

			patientRequestVO.setPatientid(userId);
			patientRequestVO.setPhysicianid(Integer.parseInt(physicianid));
			patientRequestVO.setEmailid("");
			patientRequestVO.setMailstatus(0);

			Integer id = userManager.savePatientRequestVo(patientRequestVO);

			AuditTrailVO dto = AuditTrailUtil.SaveAuditTrailDetails(id,
					request, Constants.PATIENT_DOCUMENTS, Constants.INSERT,
					"Requesting For Clinical Documents");

			userManager.saveAuditTrails(dto);

			jsonResponse.setMessage("Request Completed");

			json = new Gson().toJson(jsonResponse);
			// }
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			jsonResponse.setMessage("Request Failed");

			json = new Gson().toJson(jsonResponse);
		}
		logger.info("savePatientRequest ends");
		return json;
	}
	
	
	@RequestMapping(value = "/patient/patientreqclinicalinfotoorg.do", method = RequestMethod.GET)
	public @ResponseBody
	String patientreqclinicalinfotoorg(@RequestParam Integer orgId,@RequestParam String fax, ModelMap model,
			HttpServletRequest request) throws ParseException {
		logger.info("patientreqclinicalinfotoorg starts");
		String json = null;
		Timestamp timestamp = new Timestamp(new Date().getTime());
		JsonResponse jsonResponse = new JsonResponse();
		FaxVo faxVo = null;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userId = user.getId();
		
		Integer organizationId = null;
		organizationId = orgId;
		Integer efaxid =0;
		String faxUsername = null;
		String faxNumber=null;
		String webApiKey=null;
		String vector=null;
		String encryptionKey=null;
		
		String senFaxQueveId = null;
		Object[] obj = null;
		obj = physicianManager.getPatientPersonalDetailsForEfax(userId);
		String name = obj[0]+"";
		String email = obj[1]+"";
		String phone = obj[2]+"";
				
		String filePath = null;
		try {
			filePath = getPatientRequestClinicalInformationTemplate(request,name,email,phone,userId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("filePath : "+filePath);
		faxVo = new FaxVo();
		faxUsername = Constants.getEfaxPropertyValue("faxusername");
		faxNumber = Constants.getEfaxPropertyValue("faxnumber");
		webApiKey = Constants.getEfaxPropertyValue("webapikey");
		vector = Constants.getEfaxPropertyValue("vector");
		encryptionKey = Constants.getEfaxPropertyValue("encryptionkey");
		
		SFax.init(faxUsername,webApiKey,vector,encryptionKey,faxNumber);//Enter a Valid Username, WebApiKey, Vector, EncryptionKey and Fax Number
		ServiceResponse sr = null;
//		 queueId = "-1";
	     String	response = OCreate.outboundFaxCreate(fax, "Requesting_Clinical_Information",  filePath, ""); //Enter a valid Fax Number and a path with name to file to send
	     JSONParser parser = new JSONParser();
			JSONObject json1 = (JSONObject) parser.parse(response);
			
			senFaxQueveId = (String) json1.get("SendFaxQueueId");
		 
		
 
		if(!senFaxQueveId.equals("-1")){
		faxVo.setCreatedby(userId);
		faxVo.setStatus(1);
		faxVo.setCreatedon(timestamp);
		faxVo.setPatientid(userId);
		faxVo.setTofax(fax);
		faxVo.setFromfax(faxNumber);
		faxVo.setOrganizationid(organizationId);
		faxVo.setSendfaxqueueid(senFaxQueveId);

		  efaxid = physicianManager.saveEFaxDetails(faxVo);
		
		jsonResponse.setMessage("Request completed");
		AuditTrailVO dto = AuditTrailUtil
				.SaveAuditTrailDetails(efaxid, request,
						Constants.PATIENT_REQUEST, Constants.INSERT,
						"Requesting For Clinical Information through Organization fax");
		userManager.saveAuditTrails(dto);
		}else{
			jsonResponse.setMessage((String) json1.get("message"));
		} 
				
			json = new Gson().toJson(jsonResponse);
		logger.info("sendPatientRequest ends");
		return json;
	}
	
	@RequestMapping(value = "/patient/patientreqclinicalinfotophysician.do", method = RequestMethod.GET)
	public @ResponseBody
	String patientreqclinicalinfotophysician(@RequestParam String mail,@RequestParam String fax
			,@RequestParam String physicianid, ModelMap model,
			HttpServletRequest request) throws ParseException {
		logger.info("sendPatientRequest starts");
		String json = null;
		Timestamp timestamp = new Timestamp(new Date().getTime());
		JsonResponse jsonResponse = new JsonResponse();
		FaxVo faxVo = null;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userId = user.getId();
		
		Integer organizationId = null;
		organizationId = physicianManager.findPatientOrganizationId(userId);
		boolean validEmail;
		Integer efaxid =0;
		String faxUsername = null;
		String faxNumber=null;
		String webApiKey=null;
		String vector=null;
		String encryptionKey=null;
		
		Object[] obj = null;
		obj = physicianManager.getPatientPersonalDetailsForEfax(userId);
		
		String name = obj[0]+"";
		String email = obj[1]+"";
		String phone = obj[2]+"";
				
		String filePath = null;
		try {
			filePath = getPatientRequestClinicalInformationTemplate(request,name,email,phone,userId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		faxVo = new FaxVo();
		faxUsername = Constants.getEfaxPropertyValue("faxusername");
		faxNumber = Constants.getEfaxPropertyValue("faxnumber");
		webApiKey = Constants.getEfaxPropertyValue("webapikey");
		vector = Constants.getEfaxPropertyValue("vector");
		encryptionKey = Constants.getEfaxPropertyValue("encryptionkey");
		
		SFax.init(faxUsername,webApiKey,vector,encryptionKey,faxNumber);//Enter a Valid Username, WebApiKey, Vector, EncryptionKey and Fax Number
		ServiceResponse sr = null;
		String queueId = "-1";
		 String res = OCreate.outboundFaxCreate(fax, "Requesting Clinical Information",  filePath, ""); //Enter a valid Fax Number and a path with name to file to send
		 
		 JSONParser parser = new JSONParser();
			JSONObject json1 = (JSONObject) parser.parse(res);
			
			   queueId = (String) json1.get("SendFaxQueueId");
			   
		 if(!queueId.equals("-1")){
		faxVo.setCreatedby(userId);
		faxVo.setStatus(1);
		faxVo.setCreatedon(timestamp);
		faxVo.setPatientid(userId);
		faxVo.setTofax(fax);
		faxVo.setFromfax(faxNumber);
		faxVo.setOrganizationid(organizationId);
		faxVo.setSendfaxqueueid(queueId);

		  efaxid = physicianManager.saveEFaxDetails(faxVo);
		
		jsonResponse.setMessage("Request completed");
		AuditTrailVO dto = AuditTrailUtil
				.SaveAuditTrailDetails(efaxid, request,
						Constants.PATIENT_REQUEST, Constants.INSERT,
						"Requesting For Clinical Information through physician fax");
		userManager.saveAuditTrails(dto);
		
		PatientRequestVO patientRequestVO = null;

		patientRequestVO = new PatientRequestVO();

		patientRequestVO.setPatientid(userId);
		patientRequestVO.setPhysicianid(Integer.parseInt(physicianid));
		patientRequestVO.setEmailid("");
		patientRequestVO.setMailstatus(0);

		Integer id = userManager.savePatientRequestVo(patientRequestVO);

		AuditTrailVO dto1 = AuditTrailUtil.SaveAuditTrailDetails(id,
				request, Constants.PATIENT_DOCUMENTS, Constants.INSERT,
				"Requesting For Clinical Documents");

		userManager.saveAuditTrails(dto);
		
		}else{
			jsonResponse.setMessage((String) json1.get("message"));
		} 
				
			EmailValidator emailValidator = new EmailValidator();
			validEmail = emailValidator.validate(mail);
			if (validEmail) {
				String serverUrl = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + request.getContextPath();

				MailSupport.sendPatientRequestMail(mail,
						userManager.getUserName(userId), serverUrl);
				 
			}  
			json = new Gson().toJson(jsonResponse);
		logger.info("sendPatientRequest ends");
		return json;
	}
	
	@RequestMapping(value = "/patient/patientrequestmailinfo.do", method = RequestMethod.GET)
	public @ResponseBody
	String savePatientMailRequest(@RequestParam String mailid, ModelMap model,
			HttpServletRequest request,@RequestParam String fax) {
		logger.info("sendPatientRequest starts");
		String json = null;

		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userId = user.getId();

		boolean validEmail;
		// "testvgs7@gmail.com"
		if(!fax.equals("")){
			
		
		
		Timestamp timestamp = new Timestamp(new Date().getTime());
		JsonResponse jsonResponse = new JsonResponse();
		FaxVo faxVo = null;
		
		Integer organizationId = null;
		organizationId = physicianManager.findPatientOrganizationId(userId);
		Integer efaxid =0;
		String faxUsername = null;
		String faxNumber=null;
		String webApiKey=null;
		String vector=null;
		String encryptionKey=null;
		
		Object[] obj = null;
		obj = physicianManager.getPatientPersonalDetailsForEfax(userId);
		
		String name = obj[0]+"";
		String email = obj[1]+"";
		String phone = obj[2]+"";
				
		String filePath = null;
		try {
			filePath = getPatientRequestClinicalInformationTemplate(request,name,email,phone,userId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("filePath : "+filePath);
		faxVo = new FaxVo();
		faxUsername = Constants.getEfaxPropertyValue("faxusername");
		faxNumber = Constants.getEfaxPropertyValue("faxnumber");
		webApiKey = Constants.getEfaxPropertyValue("webapikey");
		vector = Constants.getEfaxPropertyValue("vector");
		encryptionKey = Constants.getEfaxPropertyValue("encryptionkey");
		
		SFax.init(faxUsername,webApiKey,vector,encryptionKey,faxNumber);//Enter a Valid Username, WebApiKey, Vector, EncryptionKey and Fax Number
		ServiceResponse sr = null;
		String queueId = "-1";
		String res = OCreate.outboundFaxCreate(fax, "Requesting Clinical Information",  filePath, ""); //Enter a valid Fax Number and a path with name to file to send
		 
		 JSONParser parser = new JSONParser();
			JSONObject json1 = null;
			try {
				json1 = (JSONObject) parser.parse(res);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			   queueId = (String) json1.get("SendFaxQueueId");
			   json = (String) json1.get("message");
			   
		 if(!queueId.equals("-1")){
		faxVo.setCreatedby(userId);
		faxVo.setStatus(1);
		faxVo.setCreatedon(timestamp);
		faxVo.setPatientid(userId);
		faxVo.setTofax(fax);
		faxVo.setFromfax(faxNumber);
		faxVo.setOrganizationid(organizationId);
		faxVo.setSendfaxqueueid(queueId);

		  efaxid = physicianManager.saveEFaxDetails(faxVo);
		
//		jsonResponse.setMessage("Request completed");
		AuditTrailVO dto = AuditTrailUtil
				.SaveAuditTrailDetails(efaxid, request,
						Constants.PATIENT_REQUEST, Constants.INSERT,
						"Requesting For Clinical Information through physician fax");
		userManager.saveAuditTrails(dto);
		
		
		 }
		
		}else{
			
			json = "";
		}

		if (userManager.getUserName(userId) != null) {
			EmailValidator emailValidator = new EmailValidator();
			validEmail = emailValidator.validate(mailid);
			
			if(mailid!=""){
			
			if (validEmail) {
				String serverUrl = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + request.getContextPath();

				MailSupport.sendPatientRequestMail(mailid,
						userManager.getUserName(userId), serverUrl);
				json = json+" "+Constants.getPropertyValue(Constants.MAIL_SENT_SUCCESS);

				AuditTrailVO dto = AuditTrailUtil
						.SaveAuditTrailDetails(0, request,
								Constants.PATIENT_DOCUMENTS, Constants.NON_DB,
								"Requesting For Clinical Information through physician email");

				userManager.saveAuditTrails(dto);

			} else {
				logger.info("error mail");
				if(json!=""){
				json = json+", Invalid Mail Id";
				}
				else{
					json = "Invalid Mail Id";
				}
			}
			}else{
				
				if(json!=""){
					json = json+" ";
					}
					else{
						json = "";
					}
			}
		}

		logger.info("sendPatientRequest ends");
		return json;
	}

	@RequestMapping(value = "/patient/savePatientPersonalDetails.do", method = RequestMethod.POST)
	public String getPhysicianPersonalDetails(
			@ModelAttribute("physicianPersonalForm") phyPersonalForm physicianPersonalForm,
			SessionStatus sessionStatus, ModelMap model) {

		logger.info("PhysicianMainController physicianPersonalForm() starts");

		String viewPage = Constants.HOME_PATIENT_VIEW;
		int regsiter = 1;
		try {
			Timestamp timestamp = new Timestamp(new Date().getTime());
			// RoleVO roleDto = new RoleVO();

			// List<RoleVO> roleDto =
			// physicianManager.findRoleVoByRoleId(userRegistrationForm.getUserType());

			UserPersonalInfoVO userPersonalInfoDto = new UserPersonalInfoVO();
			userPersonalInfoDto.setFirstname(physicianPersonalForm
					.getFirstname());
			userPersonalInfoDto.setMiddlename(physicianPersonalForm
					.getMiddlename());
			userPersonalInfoDto
					.setLastname(physicianPersonalForm.getLastname());
			userPersonalInfoDto.setSsn(physicianPersonalForm.getSsn());

			userPersonalInfoDto.setContact_number(physicianPersonalForm
					.getContactNo());
			userPersonalInfoDto.setStreet(physicianPersonalForm.getStreet());
			userPersonalInfoDto.setCity(physicianPersonalForm.getCity());
			userPersonalInfoDto.setZip(physicianPersonalForm.getZip());
			userPersonalInfoDto.setStreet(physicianPersonalForm.getStreet());
			if (StringUtils.isNotBlank(physicianPersonalForm.getDob())) {
				userPersonalInfoDto.setDob(CommonUtils
						.parseDateFromString(physicianPersonalForm.getDob()));
			}
			if (StringUtils.isNotBlank(physicianPersonalForm
					.getInsurance_eff_date())) {
				userPersonalInfoDto.setInsurance_eff_date(CommonUtils
						.parseDateFromString(physicianPersonalForm
								.getInsurance_eff_date()));
			}
			if (StringUtils.isNotBlank(physicianPersonalForm
					.getInsurance_exp_date())) {
				userPersonalInfoDto.setInsurance_exp_date(CommonUtils
						.parseDateFromString(physicianPersonalForm
								.getInsurance_exp_date()));
			}
			userPersonalInfoDto.setPolicy_number(physicianPersonalForm
					.getPolicy_number());
			userPersonalInfoDto.setCreatedon(timestamp);

			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			Integer userid = user.getId();
			userPersonalInfoDto.setUserid(userid);
			// physicianPersonalDto.setId(56214);

			physicianManager.savePatientPersonalDetails(userPersonalInfoDto);
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

	@RequestMapping(value = "/patient/updatePatientPersonalDetails.do", method = RequestMethod.POST)
	public String updatePhysicianPersonalDetails(
			@ModelAttribute("physicianPersonalForm") phyPersonalForm physicianPersonalForm,
			SessionStatus sessionStatus, ModelMap model) {

		String viewPage = Constants.HOME_PATIENT_VIEW;
		int regsiter = 1;
		try {
			Timestamp timestamp = new Timestamp(new Date().getTime());
			// RoleVO roleDto = new RoleVO();

			// List<RoleVO> roleDto =
			// physicianManager.findRoleVoByRoleId(userRegistrationForm.getUserType());

			UserPersonalInfoVO userPersonalInfoDto = new UserPersonalInfoVO();
			userPersonalInfoDto.setFirstname(physicianPersonalForm
					.getFirstname());
			userPersonalInfoDto.setMiddlename(physicianPersonalForm
					.getMiddlename());
			userPersonalInfoDto
					.setLastname(physicianPersonalForm.getLastname());
			userPersonalInfoDto.setSsn(physicianPersonalForm.getSsn());

			userPersonalInfoDto.setContact_number(physicianPersonalForm
					.getContactNo());
			userPersonalInfoDto.setStreet(physicianPersonalForm.getStreet());
			userPersonalInfoDto.setCity(physicianPersonalForm.getCity());
			userPersonalInfoDto.setZip(physicianPersonalForm.getZip());
			if (StringUtils.isNotBlank(physicianPersonalForm.getDob())) {
				userPersonalInfoDto.setDob(CommonUtils
						.parseDateFromString(physicianPersonalForm.getDob()));
			}
			if (StringUtils.isNotBlank(physicianPersonalForm
					.getInsurance_eff_date())) {
				userPersonalInfoDto.setInsurance_eff_date(CommonUtils
						.parseDateFromString(physicianPersonalForm
								.getInsurance_eff_date()));
			}
			if (StringUtils.isNotBlank(physicianPersonalForm
					.getInsurance_exp_date())) {
				userPersonalInfoDto.setInsurance_exp_date(CommonUtils
						.parseDateFromString(physicianPersonalForm
								.getInsurance_exp_date()));
			}
			userPersonalInfoDto.setPolicy_number(physicianPersonalForm
					.getPolicy_number());
			userPersonalInfoDto.setUpdatedon(timestamp);

			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			Integer userid = user.getId();

			userPersonalInfoDto.setUserid(userid);
			userPersonalInfoDto.setId(physicianPersonalForm.getHdnPKID());
			// physicianPersonalDto.setId(56214);

			physicianManager.updatePersonalDetails(userPersonalInfoDto);
			sessionStatus.setComplete();

			model.addAttribute("successMsg",
					Constants.getPropertyValue(Constants.REGISTER_SUCCESS));
			model.addAttribute("redirectMsg",
					Constants.getPropertyValue(Constants.redirect_msg));
			model.addAttribute("regsiter", regsiter);

			Object[] obj = null;

			Integer userId = user.getId();

			obj = userManager.getPatientVisitRecords(userId);
			String note = null;
			int visitid = 0;

			if (obj != null) {

				visitid = Integer.parseInt(obj[3] + "");

				note = userManager.getPrivateNoteByVisitAndPatientId(visitid,
						userId);

				/*
				 * String visitDate = obj[0]+""; String [] str =
				 * visitDate.split(" 00"); String [] str1 = str[0].split("-");
				 */
				Date dd = (Date) obj[0];
				if (obj[2] != null) {

					model.addAttribute("tag", obj[2]);
				} else {

					model.addAttribute("tag", "---");
				}
				String totalVisits = obj[1] + "";
				model.addAttribute("date", CommonUtils.parseStringFromDate(dd));
				model.addAttribute("visits", totalVisits);
				model.addAttribute("note", note);

			} else {

				model.addAttribute("tag", "---");
				model.addAttribute("date", "---");
				model.addAttribute("visits", 0);
				model.addAttribute("note", "---");
			}

			Integer uid = user.getId();

			ShareClinicalInfo shareVo = new ShareClinicalInfo();

			List<ShareClinicalInfo> shareList = adminOrganizationManager
					.getListOfPatientShareVo(userId);
			int count = 0;
			int approved = 0;

			int pending = 0;
			if (shareList != null) {

				count = shareList.size();

				for (ShareClinicalInfo dto : shareList) {

					if (dto.getRequestStatus().equals(Constants.APPROVED)) {
						approved++;
					}

				}

				pending = count - approved;

				model.addAttribute("total", count);
				model.addAttribute("approved", approved);
				model.addAttribute("pending", pending);
			}

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		return viewPage;
	}

	@RequestMapping(value = "/patient/documentview.do", method = RequestMethod.POST)
	public String getAllDocuments(SessionStatus sessionStatus, ModelMap model) {

		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer userid = user.getId();
		String viewpage = Constants.PATIENT_DOCUMENT_VIEW;
		List<Object[]> documentVO = null;

		documentVO = physicianManager.getAllDocumentsByUserId(userid);
		for (Object[] obj : documentVO) {

			Timestamp d = (Timestamp) obj[4];
			Integer physicianid = (Integer) obj[5];

			obj[4] = CommonUtils.parseStringFromTimeStampData(d);
			if (physicianid == 1) {
				obj[5] = "Admin";
			} else {
				obj[5] = userManager.getUserName(physicianid);
			}

		}
		model.addAttribute("documentList", documentVO);

		return viewpage;

	}

	@RequestMapping(value = "/patient/getpatientOrganizationName.do", method = RequestMethod.GET)
	public @ResponseBody
	String getpatientOrganizationName(@RequestParam Integer patientId,
			ModelMap model) {
		String json = null;
		String orgName = null;
		try {
			JsonResponse jsonResponse = new JsonResponse();
			orgName = physicianManager
					.getOrganizationNameByPatientId(patientId);
			if (orgName == null) {
				orgName = "--";
			}
			jsonResponse.setMessage(orgName);
			json = new Gson().toJson(orgName);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		return json;
	}
	
	@RequestMapping(value = "/patient/getpatientalldocuments.do", method = RequestMethod.GET)
	public @ResponseBody
	String getPatientAllDocuments(@RequestParam Integer patientId,
			ModelMap model) {

		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer userid = user.getId();

		String json = null;
		String orgName = null;
		String viewpage = Constants.PATIENT_DOCUMENT_VIEW;
		List<Object[]> documentVO = null;
		JsonResponse jsonResponse = new JsonResponse();
		try {
			if (patientId > 0) {
				logger.info("patientId " + patientId);
				documentVO = physicianManager
						.getAllDocumentsByUserId(patientId);
				orgName = physicianManager
						.getOrganizationNameByPatientId(patientId);

			} else {
				logger.info("else");
				logger.info("user id" + userid);
				documentVO = physicianManager.getAllDocumentsByUserId(userid);
			}

			// p.id,p.patientid,p.docname,p.docpath,p.createdon

			for (Object[] obj : documentVO) {
				Timestamp d = (Timestamp) obj[4];
				obj[4] = CommonUtils.parseStringFromTimeStampData(d);
			}
			json = new Gson().toJson(documentVO);

			if (orgName == null) {
				orgName = "--";
			}
			// jsonResponse.setMessage(json);
			// jsonResponse.setStatus(orgName);
			// json = new Gson().toJson(jsonResponse);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		return json;
	}

	@RequestMapping(value = "/patient/getpatientsharedocuments.do", method = RequestMethod.GET)
	public @ResponseBody
	String getPatientShareDocuments(@RequestParam Integer patientId,
			ModelMap model) {

		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer userid = user.getId();

		String json = null;
		String orgName = null;
		String viewpage = Constants.PATIENT_DOCUMENT_VIEW;
		List<Object[]> documentVO = null;
		JsonResponse jsonResponse = new JsonResponse();
		try {
			if (patientId > 0) {
				logger.info("patientId " + patientId);
				documentVO = physicianManager
						.getShareDocumentsByUserId(patientId);
				orgName = physicianManager
						.getOrganizationNameByPatientId(patientId);

			} else {
				logger.info("else");
				logger.info("user id" + userid);
				documentVO = physicianManager.getShareDocumentsByUserId(userid);
			}

			// p.id,p.patientid,p.docname,p.docpath,p.createdon

			for (Object[] obj : documentVO) {
				Timestamp d = (Timestamp) obj[4];
				obj[4] = CommonUtils.parseStringFromTimeStampData(d);
			}
			json = new Gson().toJson(documentVO);

			if (orgName == null) {
				orgName = "--";
			}
			// jsonResponse.setMessage(json);
			// jsonResponse.setStatus(orgName);
			// json = new Gson().toJson(jsonResponse);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		return json;
	}

	@RequestMapping(value = "/patient/getPatientShareVisitDetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String getPatientShareVisitDetails(@RequestParam Integer type,
			@RequestParam Integer patientId, @RequestParam String visitDate,
			ModelMap model) {
		logger.debug("PatientMainController pa==> ");
		String json = null;
		List<Object[]> phyVisitsDto = null;
		String viewpage = Constants.SHARE_PATIENT_VIEW;
		try {

			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			Integer patId = user.getId();

			if (type == 1) {
				phyVisitsDto = physicianManager.getPatientShareVisitRecords(
						type, patId, visitDate);
				json = new Gson().toJson(phyVisitsDto);
			} else if (type == 2) {
				phyVisitsDto = physicianManager.getPatientShareVisitRecords(
						type, patientId, visitDate);
				json = new Gson().toJson(phyVisitsDto);
			} else if (type == 3) {
				phyVisitsDto = physicianManager.getPatientShareVisitRecords(
						type, patId, visitDate);
				json = new Gson().toJson(phyVisitsDto);
			}
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

	@RequestMapping(value = "/patient/sendSharePatientMail.do", method = RequestMethod.POST)
	public @ResponseBody
	String sendSharePatientMail(@RequestParam Integer physicianId,
			@RequestParam Integer patientId, @RequestParam String shareId,
			@RequestParam String phyMailId, @RequestParam String subject,
			@RequestParam String bodyContent, HttpServletRequest request) {
		logger.debug("PatientMainController pa==> ");
		logger.info("phyMailId => " + phyMailId);
		String phyEmail = null;
		String result = "";

		String viewpage = Constants.SHARE_PATIENT_VIEW;
		try {
			if (phyMailId != "") {
				logger.info("if phyMailId => " + phyMailId);
				phyEmail = phyMailId;
			} else {
				phyEmail = physicianManager.getPhysicianEmailId(physicianId);
			}
			
			UserDetails userVo = new UserDetails();

			// UserVO userVo = new UserVO();
			userVo = userManager.getUserDetilsByUserId(physicianId);
			
			String serverUrl = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();

			physicianManager.sendSharePatientDetails(phyEmail, subject,
					bodyContent, serverUrl, patientId, shareId,userVo);

			AuditTrailVO dto = AuditTrailUtil.SaveAuditTrailDetails(0, request,
					Constants.SHARE, Constants.INSERT,
					"Patient Share Their Clinical Information to " + phyEmail
							+ "");

			userManager.saveAuditTrails(dto);

			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			/*
			 * model.addAttribute("EXCEPTION",
			 * Constants.getPropertyValue(Constants.SERVICE_ERROR));
			 */
		}
		logger.info("PhysicianMainController physicianVisits ends ");

		return result;
	}

	@RequestMapping(value = "/patient/getVisitDetailsforShare.do", method = RequestMethod.GET)
	public @ResponseBody
	String getVisitDetailsforShare(@RequestParam Integer visitId, ModelMap model) {
		logger.debug("PatientMainController pa==> ");
		String json = null;
		List<Object[]> phyVisitsDto = null;
		String viewpage = Constants.SHARE_PATIENT_VIEW;
		try {

			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			Integer patId = user.getId();

			phyVisitsDto = physicianManager.getVisitDetailsforShare(visitId);
			json = new Gson().toJson(phyVisitsDto);

			logger.info("json " + json);

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

	@RequestMapping(value = "/patient/getPatientRequestPending.do", method = RequestMethod.GET)
	public @ResponseBody
	String getPatientRequestPending(@RequestParam Integer patientId,
			ModelMap model) {
		logger.debug("PatientMainController Pending request==> ");
		String json = null;
		List<Object[]> phyVisitsDto = null;
		try {

			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			Integer patId = user.getId();

			if (patientId != 0) {
				phyVisitsDto = physicianManager
						.getPatientRequestPending(patientId);
			} else {
				phyVisitsDto = physicianManager.getPatientRequestPending(patId);
			}
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
	

	public void fnmoveDocuments(Integer count,HttpServletRequest request,Integer patientId,Integer shareId){
		Timestamp timestamp = new Timestamp(new Date().getTime());
		String filepath = Constants
				.getPropertyValue(Constants.FAREFILE_UPLOAD_PATH);
		
		filepath = filepath+patientId+"/";
		
		for(int j = 0; j<count;j++){
			
			if(request.getParameter("checked1_"+j)!=null && !request.getParameter("checked1_"+j).equals("0")){
				
				String fileName = request.getParameter("checked1_"+j);
				Integer checkid=checkFileStatus(fileName,patientId);
				System.out.println("checkid : "+checkid);
				
				if(checkid>0){
					
				}else{
					filepath = filepath+""+fileName;
					
					PatientDocument documentVO = new PatientDocument();

					documentVO.setCreatedby(patientId);
					documentVO.setCreatedon(timestamp);
					documentVO.setDocname(fileName);
					documentVO.setDocpath(filepath);
					documentVO.setPatientid(patientId);
					documentVO.setKeyid(shareId);
					documentVO.setKey("Share Request");
					documentVO.setUploadType("Share");
					Integer id = userManager
							.savePatientDocument(documentVO);

					AuditTrailVO dto = AuditTrailUtil
							.SaveAuditTrailDetails(id, request,
									Constants.SHARE,
									Constants.INSERT,
									"Patient Share Clinical Documents to physician");
				}
				
			}
			
			}
			
		
	}
	public void fnmoveDocuments1(Integer count,HttpServletRequest request,Integer patientId,Integer shareId){
		Timestamp timestamp = new Timestamp(new Date().getTime());
		String filepath = Constants
				.getPropertyValue(Constants.FAREFILE_UPLOAD_PATH);
		
		filepath = filepath+patientId+"/";
		
		for(int j = 0; j<count;j++){
			
			if(request.getParameter("checked_"+j)!=null && !request.getParameter("checked_"+j).equals("0")){
				
				String fileName = request.getParameter("checked_"+j);
				Integer checkid=checkFileStatus(fileName,patientId);
				System.out.println("checkid : "+checkid);
				
				if(checkid>0){
					
				}else{
					filepath = filepath+""+fileName;
					
					PatientDocument documentVO = new PatientDocument();

					documentVO.setCreatedby(patientId);
					documentVO.setCreatedon(timestamp);
					documentVO.setDocname(fileName);
					documentVO.setDocpath(filepath);
					documentVO.setPatientid(patientId);
					documentVO.setKeyid(shareId);
					documentVO.setKey("Share Request");
					documentVO.setUploadType("Share");
					Integer id = userManager
							.savePatientDocument(documentVO);
					AuditTrailVO dto = AuditTrailUtil
							.SaveAuditTrailDetails(id, request,
									Constants.SHARE,
									Constants.INSERT,
									"Patient Share Clinical Documents to physician");
				}
				

				
			}
			
			}
			
		
	}
	
	@RequestMapping(value = "/patient/shareStatusChange.do", method = RequestMethod.POST)
	public @ResponseBody
	String shareStatusChange(@RequestParam Integer physicianId,@RequestParam String type,@RequestParam Integer shareId,
			@RequestParam Integer patientId, ModelMap model,@RequestParam Integer count,
			HttpServletRequest request) {
		logger.debug("PatientMainController pa==> ");
		String result = "";
		String returnResult = "";
		if (patientId == null) {
			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			patientId = user.getId();
		}

		String viewpage = Constants.SHARE_PATIENT_VIEW;
		try {
			String shareType = null;
			if(type.equals("Share")){
				shareType = "Approved";
				physicianManager.updateShareStatus(shareId);
				
				
				fnmoveDocuments1(count,request,patientId,shareId);
				
				
			}else{
				shareType = "New patient share";
			
//			

				String json = null;
				ShareClinicalInfo shareVo = new ShareClinicalInfo();
				UserVO userVo = new UserVO();
				UserVO userVo1 = new UserVO();

				UserProfile user = (UserProfile) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				Integer userid = user.getId();

				VisitsVO visitVo = new VisitsVO();
				Timestamp timestamp = new Timestamp(new Date().getTime());
				try {
					userVo = userManager.getUserRegistrationByUserId(patientId);

					userVo1 = userManager
							.getUserRegistrationByUserId(physicianId);

					// visitVo.setId(visitId);

					shareVo.setShareTo(userVo);

					shareVo.setShareFrom(userVo1);

					// shareVo.setVisit(visitVo);

					shareVo.setCreatedby(userid);

					shareVo.setCreatedon(timestamp);

					shareVo.setFromDate(timestamp);
					shareVo.setStatus(1);

					shareVo.setRequestStatus(shareType);

					Integer shareid = physicianManager
							.saveShareRequest(shareVo);

					returnResult = shareid.toString();

					fnmoveDocuments(count,request,patientId,shareid);
					
					AuditTrailVO dto = AuditTrailUtil
							.SaveAuditTrailDetails(shareId, request,
									Constants.SHARE, Constants.INSERT,
									"Patient Share Their Clinical Documents To Physician");

					userManager.saveAuditTrails(dto);
					
				} catch (Exception e) {
					logger.error(Constants.LOG_ERROR + e.getMessage());
					// jsonResponse.setMessage("Error To Requesting a Share.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("PhysicianMainController physicianVisits ends ");

		return returnResult;
	}

	@RequestMapping(value = "/patient/getPatientPersonalDetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String getPatientPersonalDetails(Integer patientId, ModelMap model) {
		String json = null;

		List<Object[]> phyVisitsDto = null;
		try {

			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			Integer patId = user.getId();

			if (patientId > 0) {
				phyVisitsDto = physicianManager
						.getPatientPersonalDetails(patientId);
			} else {
				phyVisitsDto = physicianManager
						.getPatientPersonalDetails(patId);
			}
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

	@RequestMapping("/patient/patientviewencounter.do")
	public String patientViewEncounters(ModelMap model) {
		logger.debug("PatientController patientViewEncounters==>");
		String viewpage = Constants.PATIENT_VIEW_ENCOUNTERS_VIEW;
		return viewpage;
	}
	
	
	@RequestMapping(value = "/patient/createSignatureFromText.do", method = RequestMethod.GET)
	public @ResponseBody
	String createDigitalSignatureFromText(HttpServletRequest request,ModelMap model,@RequestParam String signText) {
		String json = null;
		logger.info("PatientMainController createDigitalSignatureFromText Starts ");
		try {
			JsonResponse jsonResponse = new JsonResponse();
			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			Integer patId = user.getId();
//			String userName = user.getUserName();
			String file = CreateDigitalSign(request,signText,patId);
			jsonResponse.setMessage("Signature Created Successfully");
			json = new Gson().toJson(jsonResponse);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("PatientMainController createDigitalSignatureFromText ends ");

		return json;
	}
	
	
	synchronized
	public String 
	getPatientRequestClinicalInformationTemplate(HttpServletRequest request,String name,String mail,String phone,Integer userId) 
			throws IOException, Exception{
		
		
		
		String finalFax = null;
		 try {
			 
			 String destinationPath = Constants
						.getPropertyValue(Constants.FAREFILE_UPLOAD_PATH);
	            
	           // PdfReader pdfReader = new PdfReader("D:/test/sample.pdf");
	            System.out.println("request : "+request.getContextPath());
	            System.out.println("request : "+request.getServerName());
	            System.out.println("request : "+request.getServerPort());
	            PdfReader pdfReader = null;
	           //PdfReader pdfReader = new PdfReader(new URL("http://localhost:8090/bluehub/HIPAAForm.pdf"));
	            if(request.getContextPath().equalsIgnoreCase("") || request.getContextPath().equalsIgnoreCase(null)){
	            	System.out.println("1 server name");
	            	pdfReader = new PdfReader(new URL("http://"+request.getServerName()+":"+request.getServerPort()+"/"+"HIPAAForm.pdf"));
	            }else if(request.getServerPort()==0){
	            	System.out.println("2 server name context path");
	            	pdfReader = new PdfReader(new URL("http://"+request.getServerName()+"/"+request.getContextPath()+"/"+"HIPAAForm.pdf"));
	            }else{
	            	System.out.println("3 full ");
	            	pdfReader = new PdfReader(new URL("http://"+request.getServerName()+":"+request.getServerPort()+"/"+request.getContextPath()+"/"+"HIPAAForm.pdf"));
	            }
	            

	            File file =new File(destinationPath+userId.toString());
	            if(!file.exists()){
	            	file.mkdir();
	            }
	            
	            finalFax=file+"/fax.pdf";
	            
	            PdfStamper pdfStamper = new PdfStamper(pdfReader,
	                    new FileOutputStream(finalFax));

	          //  Image image = Image.getInstance(finalPath);

	            //for(int i=1; i<= pdfReader.getNumberOfPages(); i++){

	                //put content under
	                PdfContentByte content = pdfStamper.getUnderContent(1);
	                //image.setAbsolutePosition(60f, 100f);
	               // content.addImage(image);

	                //content.add
	                /*//put content over
	                content = pdfStamper.getOverContent(1);
	                image.setAbsolutePosition(300f, 150f);
	                content.addImage(image);*/
	                
	              //Text over the top name
	                BaseFont username = BaseFont.createFont(BaseFont.HELVETICA,
	                        BaseFont.WINANSI, BaseFont.EMBEDDED);
	                content.beginText();
	                content.setFontAndSize(username, 15);
	                content.showTextAligned(PdfContentByte.ALIGN_CENTER,name , 200f,660f,0);
	               // content.showTextAligned(PdfContentByte.ALIGN_LEFT,"Page No: " + 430,15,0);
	                content.endText();

	                //Text over the existing page
	                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
	                        BaseFont.WINANSI, BaseFont.EMBEDDED);
	                content.beginText();
	                content.setFontAndSize(bf, 20);
	                content.showTextAligned(PdfContentByte.ALIGN_CENTER,name , 150f,85f,0);
	               // content.showTextAligned(PdfContentByte.ALIGN_LEFT,"Page No: " + 430,15,0);
	                content.endText();
	                
	                
	                /*DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");*/
	                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	                Date date = new Date();
	                System.out.println(dateFormat.format(date).toString()); //2014/08/06
	                
	                BaseFont bf1 = BaseFont.createFont(BaseFont.HELVETICA,
	                        BaseFont.WINANSI, BaseFont.EMBEDDED);
	                content.beginText();
	                content.setFontAndSize(bf1, 20);
	                content.showTextAligned(PdfContentByte.ALIGN_CENTER,dateFormat.format(date).toString() , 450f,85f,0);
	               // content.showTextAligned(PdfContentByte.ALIGN_LEFT,"Page No: " + 430,15,0);
	                content.endText();

	         //   }
	            
	            pdfStamper.close();
	            } catch (Exception e) {
		              System.err.println(e.getMessage());
		            }
		
		
		
		 return finalFax;
		
		// return outputFileName;
		
		
		
		/*
		
		String header = null;
		String body = null;
		String footer = null;
		
		header = Constants.getEfaxPropertyValue("header");
		body = Constants.getEfaxPropertyValue("body");
		footer = Constants.getEfaxPropertyValue("footer");
        String outputFileName = Constants.getPropertyValue(Constants.FAX_TEMPLATE_PATH);
        File saveDir = new File(outputFileName);
		if (!saveDir.exists()) {
			logger.info("creating directory: " + outputFileName);
			boolean result = saveDir.mkdir();
			if (result) {
				logger.info("DIR created");
			}
		}
        outputFileName = outputFileName+"template.pdf";
        // Create a document and add a page to it
        PDDocument document = new PDDocument();
        PDPage page1 = new PDPage(PDPage.PAGE_SIZE_A4);
            // PDPage.PAGE_SIZE_LETTER is also possible
        PDRectangle rect = page1.getMediaBox();
            // rect can be used to get the page width and height
        document.addPage(page1);
        // Create a new font object selecting one of the PDF base fonts
        PDFont fontPlain = PDType1Font.HELVETICA;
        // Start a new content stream which will "hold" the to be created content
        PDPageContentStream cos = new PDPageContentStream(document, page1);

        int line = 0;
 
        cos.beginText();
        cos.setFont(fontPlain, 10);
        cos.moveTextPositionByAmount(100, rect.getHeight() - 30*(++line));
        cos.drawString("Patient Name:"+name);
        cos.endText();

        cos.beginText();
        cos.setFont(fontPlain, 10);
        cos.moveTextPositionByAmount(100, rect.getHeight() - 30*(++line));
        cos.drawString("Email:"+mail);
        cos.endText();
        
        cos.beginText();
        cos.setFont(fontPlain, 10);
        cos.moveTextPositionByAmount(100, rect.getHeight() - 30*(++line));
        cos.drawString("Phone:"+phone);
        cos.endText();
        
        
        
        cos.beginText();
        cos.setFont(fontPlain, 12);
        cos.moveTextPositionByAmount(250, rect.getHeight() - 50*(++line));
        cos.drawString(header);
        cos.endText(); 
        
        
        cos.beginText();
        cos.setFont(fontPlain, 11);
        cos.moveTextPositionByAmount(120, rect.getHeight() - 50*(++line));
//        cos.moveTextPositionByAmount(10, 10);  
        cos.drawString(body);  
//        cos.drawString("This is a document is Faxed to 3rd party provider/organization when the patient is requesting document.It should be available in properties to configure");
        cos.endText();
        
        cos.beginText();
        cos.setFont(fontPlain, 10);
        cos.moveTextPositionByAmount(200, rect.getHeight() - 130*(++line));
        cos.drawString(footer);
        cos.endText();
        cos.close();
        document.save(outputFileName);
        document.close();
		return outputFileName;
    
	*/}
	
	
	public String CreateDigitalSign(HttpServletRequest request,String text,Integer userName){
		
		 	BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2d = img.createGraphics();
	        Font font = new Font("Arial", Font.ITALIC, 48);
	        g2d.setFont(font);
	        FontMetrics fm = g2d.getFontMetrics();
	        int width = fm.stringWidth(text);
	        int height = fm.getHeight();
	        g2d.dispose();

	        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	        g2d = img.createGraphics();
	        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	        g2d.setFont(font);
	        fm = g2d.getFontMetrics();
	        g2d.setColor(Color.BLACK);
	        g2d.drawString(text, 0, fm.getAscent());
	        g2d.dispose();
	        String destinationPath = Constants
					.getPropertyValue(Constants.FAREFILE_UPLOAD_PATH);
	        File sourcefolder =new File(destinationPath);
		       if(!sourcefolder.exists()){
		    	   sourcefolder.mkdir();
	           }
	        
	        
	       File file =new File(destinationPath+userName.toString());
	       if(!file.exists()){
           	file.mkdir();
           }
	       String finalPath = file+"\\"+"Signature.png";
	        try {
	            ImageIO.write(img, "png", new File(finalPath));
	            
	            try {
	            
	           // PdfReader pdfReader = new PdfReader("D:/test/sample.pdf");
	            System.out.println("request : "+request.getContextPath());
	            System.out.println("request : "+request.getServerName());
	            System.out.println("request : "+request.getServerPort());
	           //PdfReader pdfReader = new PdfReader(new URL("http://localhost:8090/bluehub/HIPAAForm.pdf"));
	            PdfReader pdfReader = new PdfReader(new URL("http://"+request.getServerName()+":"+request.getServerPort()+"/"+request.getContextPath()+"/"+"HIPAAForm.pdf"));

	            PdfStamper pdfStamper = new PdfStamper(pdfReader,
	                    new FileOutputStream(destinationPath+userName+"/Signature.pdf"));

	            Image image = Image.getInstance(finalPath);

	            //for(int i=1; i<= pdfReader.getNumberOfPages(); i++){

	                //put content under
	                PdfContentByte content = pdfStamper.getUnderContent(1);
	                image.setAbsolutePosition(60f, 100f);
	               // content.addImage(image);

	                //content.add
	                /*//put content over
	                content = pdfStamper.getOverContent(1);
	                image.setAbsolutePosition(300f, 150f);
	                content.addImage(image);*/
	                
	              //Text over the top name
	                BaseFont username = BaseFont.createFont(BaseFont.HELVETICA,
	                        BaseFont.WINANSI, BaseFont.EMBEDDED);
	                content.beginText();
	                content.setFontAndSize(username, 15);
	                content.showTextAligned(PdfContentByte.ALIGN_CENTER,text , 200f,660f,0);
	               // content.showTextAligned(PdfContentByte.ALIGN_LEFT,"Page No: " + 430,15,0);
	                content.endText();

	                //Text over the existing page
	                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
	                        BaseFont.WINANSI, BaseFont.EMBEDDED);
	                content.beginText();
	                content.setFontAndSize(bf, 20);
	                content.showTextAligned(PdfContentByte.ALIGN_CENTER,text , 150f,85f,0);
	               // content.showTextAligned(PdfContentByte.ALIGN_LEFT,"Page No: " + 430,15,0);
	                content.endText();
	                
	                
	                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	                Date date = new Date();
	                System.out.println(dateFormat.format(date).toString()); //2014/08/06
	                
	                BaseFont bf1 = BaseFont.createFont(BaseFont.HELVETICA,
	                        BaseFont.WINANSI, BaseFont.EMBEDDED);
	                content.beginText();
	                content.setFontAndSize(bf1, 20);
	                content.showTextAligned(PdfContentByte.ALIGN_CENTER,dateFormat.format(date).toString() , 450f,85f,0);
	               // content.showTextAligned(PdfContentByte.ALIGN_LEFT,"Page No: " + 430,15,0);
	                content.endText();

	         //   }
	            
	            pdfStamper.close();
	            } catch (Exception e) {
		              System.err.println(e.getMessage());
		            }

	            
	            
	            /*Document document = new Document();
	            try {
	              PdfWriter.getInstance(document, new FileOutputStream("D:/test/sample.pdf"));
	              document.open();

	              Image imageRight = Image.getInstance(finalPath);
	              imageRight.setAlignment(Image.BOTTOM);

	              for (int i = 0; i < 100; i++) {
	                document.add(new Phrase("Text "));
	              }
	              document.add(imageRight);
	              for (int i = 0; i < 100; i++) {
	                document.add(new Phrase("Text "));
	              }
	            } catch (Exception e) {
	              System.err.println(e.getMessage());
	            }
	            document.close();*/
	           
	            
	            
	            
	            userManager.updateSignature(userName);
	            
	           /* Integer createdby = userName;
				Timestamp createdOnTime = new Timestamp(new Date().getTime());
	            
	        	PatientDocument documentVO = new PatientDocument();

				documentVO.setCreatedby(createdby);
				documentVO.setCreatedon(createdOnTime);

				documentVO.setDocname("Signature.png");
				documentVO.setDocpath(finalPath);
				documentVO.setPatientid(userName);
				documentVO.setKeyid(0);
				documentVO.setKey("Upload");
				documentVO.setUploadType("Normal");

				Integer id = userManager
						.savePatientDocument(documentVO);*/
	            
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
		
	return finalPath;
	}
	
	
	@RequestMapping(value = "/patient/getEfaxAssocoatedDocuments.do", method = RequestMethod.GET)
	public @ResponseBody
	String getEfaxAssociatedDocuments(ModelMap model,@RequestParam Integer patientId) {
		logger.info("moveFileToCourseLocation");
	 
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		List<Object[]> patList = null;
		try {
			patList = physicianManager.getEfaxAssocoatedPatientDocuments(patientId);
			
			json = new Gson().toJson(patList);
		} catch (Exception e) {
			// returnPath=uploadPath.toString();
			e.printStackTrace();
		}  

		return json;
	
		
	}
	@RequestMapping(value = "/patient/getEfaxDocuments.do", method = RequestMethod.GET)
	public @ResponseBody
	String getEfaxDocuments(ModelMap model,@RequestParam Integer patientId) {
		logger.info("moveFileToCourseLocation");
		File sourceFile = null;
		String result = null;
		String sourcePath = Constants.getPropertyValue(Constants.FAX_DOWNLOAD_PATH);
		List<Object[]> fileList = null;
		Object[] obj = null;
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		try {

			// create upload directory
			File saveDir = new File(sourcePath);
			if (!saveDir.exists()) {
				logger.info("creating directory: " + sourcePath);
				boolean result1 = saveDir.mkdir();
				if (result1) {
					logger.info("DIR created");
				}
			}
			 Date receivedDate=null;
			fileList = new LinkedList();
			sourceFile = new File(sourcePath);
			Date newDate = new Date();
			String time = newDate.getTime() + "";
			File folder = new File(sourcePath);
			File[] listOfFiles = folder.listFiles();
			Timestamp createdOnTime = new Timestamp(new Date().getTime());
			if (listOfFiles != null && listOfFiles.length > 0) {
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						File fileName = new File(sourcePath
								+ "/" + listOfFiles[i].getName());
						receivedDate = getCreatedDateFromFile(fileName.getAbsolutePath());
						
						obj = new Object[4];
						obj[0]= fileName.getName();
						obj[1] = fileName.getAbsolutePath();
						obj[2] = fileName.getPath();
						obj[3] = CommonUtils.parseStringFromDate(receivedDate);
						 
						fileList.add(obj);
					}  
				}

				json = new Gson().toJson(fileList);
			}
		} catch (Exception e) {
			// returnPath=uploadPath.toString();
			e.printStackTrace();
		}  

		return json;
	
		
	}
	
	@RequestMapping(value = "/patient/shareFaxDocuments.do", method = RequestMethod.GET)
	public @ResponseBody
	String shareEfaxDocuments(HttpServletRequest request, ModelMap model,@RequestParam Integer patientId, @RequestParam Integer count) {
		logger.info("moveFileToCourseLocation");
		File sourceFile = null;
		String sourcePath = Constants.getPropertyValue(Constants.FAX_DOWNLOAD_PATH);
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		try {
//			sourceFile = new File(sourcePath + patientId.toString());
			sourceFile = new File(sourcePath);
			Date newDate = new Date();
			String time = newDate.getTime() + "";
//			File folder = new File(sourcePath + patientId.toString());
			File folder = new File(sourcePath);
			File[] listOfFiles = folder.listFiles();
			Timestamp createdOnTime = new Timestamp(new Date().getTime());
			int k = 0; 
			
			
			if (listOfFiles != null && listOfFiles.length > 0) {
				
				//check the file here and move to fare document folder 
				
				for(int j = 0; j<count;j++){
					
				if(request.getParameter("hdnFaxFileCheckBox_"+j)!=null && request.getParameter("hdnFaxFileCheckBox_"+j).equals("true")){
					k++;
					
					if(patientId!=0){
					String fileFrom = request.getParameter("hdnFaxFile_"+j);
					for (int i = 0; i < listOfFiles.length; i++) {
						if (listOfFiles[i].isFile()) {
							File fileName = new File(sourcePath+ listOfFiles[i].getName());
							String fileTo = fileName.getAbsolutePath();
							if(fileFrom.equals(fileTo)){
								//here to move the files from fax folder to fare 
								String receivedDate = request.getParameter("date_"+i);
								moveFaxFileToCourseLocation(fileName,patientId,0,request,user.getId(),receivedDate);
								
								AuditTrailVO dto = AuditTrailUtil
										.SaveAuditTrailDetails(0, request,
												Constants.PATIENT_DOCUMENTS,
												Constants.INSERT,
												"Patient Clinical Associated With The Patient");

								userManager.saveAuditTrails(dto);
								
							}
						}  
					}//end of Second for loop
					}
			 	}
			

				}//end of first for loop
			}
			
			if(k>0 && patientId==0){
				
				jsonResponse.setMessage("Please select a patient.");	
				jsonResponse.setStatus("No");
			}else if(k>0 && patientId!=0){
				
				jsonResponse.setMessage("Documents shared successfully.");
				jsonResponse.setStatus("Yes");
			}else{
				
				jsonResponse.setMessage("Please select a document to share.");	
				jsonResponse.setStatus("No");
			}
			
//			if(k>0){
//				jsonResponse.setMessage("Documents shared succssfully.");
//				jsonResponse.setStatus("Yes");
//				}else{
//					if(patientId==0){
//						
//						jsonResponse.setMessage("Please select a document to share.");	
//						jsonResponse.setStatus("No");
//					}else{
//					
//				jsonResponse.setMessage("Please select a document to share.");	
//				jsonResponse.setStatus("No");
//					}
//				}
			json = new Gson().toJson(jsonResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}  

		return json;
		
	}
	
	
	private String moveFaxFileToCourseLocation(File incomingFIle,
			Integer destinationUserId, Integer visitid,
			HttpServletRequest request,Integer userid,String receivedDate) {

		logger.info("moveFileToCourseLocation");
		File sourceFile = null;
		File destinationFile = null;
		
		String result = null;
		String sourcePath = Constants
				.getPropertyValue(Constants.FAX_DOWNLOAD_PATH);
		String destinationPath = Constants
				.getPropertyValue(Constants.FAREFILE_UPLOAD_PATH);
		String associatedDate = null;
		try {

			sourceFile = incomingFIle;
			Timestamp createdOnTime = new Timestamp(new Date().getTime());

			  associatedDate = CommonUtils.parseStringFromTimeStampData(createdOnTime);
			
						File fileName =sourceFile;
						destinationFile = new File(destinationPath
								+ destinationUserId.toString() + "/"
								+ fileName.getName());

						if (fileName.exists()) {
							if (destinationFile.exists()) {
								
								incomingFIle.delete();

								PatientDocument documentVO = new PatientDocument();

								documentVO.setCreatedby(userid);
								documentVO.setCreatedon(createdOnTime);
								documentVO.setDoctype("Efax");
								documentVO.setDocname(fileName.getName());
								documentVO.setDocpath(destinationFile + "");
								documentVO.setPatientid(destinationUserId);
								documentVO.setKeyid(visitid);
								documentVO.setKey("Upload");
								documentVO.setUploadType("Normal");
								documentVO.setReceiveddate(receivedDate);
								documentVO.setAssociateddate(associatedDate);

								Integer id = userManager
										.savePatientDocument(documentVO);

								AuditTrailVO dto = AuditTrailUtil
										.SaveAuditTrailDetails(id, request,
												Constants.PATIENT_DOCUMENTS,
												Constants.INSERT,
												"Patient Clinical Documents Uploaded By Efax");

								userManager.saveAuditTrails(dto);

							} else {
								FileUtils.moveFile(fileName, destinationFile);
								PatientDocument documentVO = new PatientDocument();

								documentVO.setCreatedby(userid);
								documentVO.setCreatedon(createdOnTime);
								documentVO.setDoctype("Efax");
								documentVO.setDocname(fileName.getName());
								documentVO.setDocpath(destinationFile + "");
								documentVO.setPatientid(destinationUserId);
								documentVO.setKeyid(visitid);
								documentVO.setKey("Upload");
								documentVO.setUploadType("Normal");
								documentVO.setReceiveddate(receivedDate);
								documentVO.setAssociateddate(associatedDate);
								Integer id = userManager
										.savePatientDocument(documentVO);

								AuditTrailVO dto = AuditTrailUtil
										.SaveAuditTrailDetails(id, request,
												Constants.PATIENT_DOCUMENTS,
												Constants.INSERT,
												"Patient Clinical Documents Uploaded By Efax");

								userManager.saveAuditTrails(dto);
							}

						}


				result = "YES";

		 
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return result;
	}
	
	
	@RequestMapping(value = "/patient/deleteFaxDocument.do", method = RequestMethod.GET)
	public @ResponseBody
	String deleteEfaxDocuments(HttpServletRequest request, ModelMap model,@RequestParam String fileName) {
		logger.info("PatientMainController deleteEfaxDocuments Starts==>");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		try {
		String sourcePath = Constants.getPropertyValue(Constants.FAX_DOWNLOAD_PATH);
		File file = new File(sourcePath+fileName);
		if (file.exists()) {
		file.delete();
		jsonResponse.setMessage("Document deleted successfully");
		}else{
		jsonResponse.setMessage("Document unavailable");
		}
		json = new Gson().toJson(jsonResponse);
		logger.info("PatientMainController deleteEfaxDocuments Ends==>");
		} catch (Exception e) {
			logger.info("Error:"+e.getMessage());
		}
		return json;
	}
	
	
	
	public Date getCreatedDateFromFile(String location){
		
		File file = new File(location); 
		Long lastModified = file.lastModified(); 


		// Create a new date object and pass last modified information 
		// to the date object. 
		Date date = new Date(lastModified); 
		
		return date;
	}
	
	@RequestMapping(value = "/patient/unAssociateFaxDocuments.do", method = RequestMethod.GET)
	public @ResponseBody
	String unAssociateEfaxDocuments(HttpServletRequest request, ModelMap model,@RequestParam Integer count) {
		logger.info("PatientMainController unAssociateEfaxDocuments Starts==>");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		try {
			File sourceFile = null;
			File destinationFile = null;
			String result = null;
			String destinationPath = Constants
					.getPropertyValue(Constants.FAX_DOWNLOAD_PATH);
			String sourcePath = Constants
					.getPropertyValue(Constants.FAREFILE_UPLOAD_PATH);
			
			Integer docId = null;
			Integer patientId= null;
			String fileName = null;
			int j = 0;
			
			for(int i=0;i<count;i++){
				patientId = Integer.parseInt(request.getParameter("patientId_"+i));
				fileName = request.getParameter("hdnFaxFile_"+i);
				if(request.getParameter("hdnFaxFileCheckBox_"+i)!=null 
						&& request.getParameter("hdnFaxFileCheckBox_"+i).equals("true")){
					
					docId = Integer.parseInt(request.getParameter("docId_"+i));
					
					  sourceFile = new File(sourcePath+patientId+"/"+fileName);
					  
					  destinationFile = new File(destinationPath+fileName);
					
					  if(!destinationFile.exists()){
						  
						  FileUtils.moveFile(sourceFile, destinationFile);
					  }
					  if(sourceFile.exists()){
						  sourceFile.delete();
					  }
					  physicianManager.deleteDocument(docId);
					  j++;
					  
					 
						AuditTrailVO dto = AuditTrailUtil
								.SaveAuditTrailDetails(docId, request,
										Constants.PATIENT_DOCUMENTS,
										Constants.DELETE,
										"Patient Clinical Documents Un Associated");

						userManager.saveAuditTrails(dto);
					  
				}
			}
			
			if(j>0){
				
				jsonResponse.setMessage("Documents Un Associated successfully.");
			}else{
				
				jsonResponse.setMessage("Choose a document to Un Associate.");
			}
			
		json = new Gson().toJson(jsonResponse);
		logger.info("PatientMainController deleteEfaxDocuments Ends==>");
		} catch (Exception e) {
			logger.info("Error:"+e.getMessage());
		}
		return json;
	}
	
	
	public void retreiveAllFaxDocuments(){
		List<Object[]> patList = null;
		patList = physicianManager.getAllPatientDetailsForFax();
		String faxDownloadPath = Constants.getPropertyValue(Constants.FAX_DOWNLOAD_PATH);
		for(Object[] obj : patList){
			
			
			
		}
	}
	
	@RequestMapping(value = "/patient/checksignature.do", method = RequestMethod.GET)
	public @ResponseBody
	String checkSignature(HttpServletRequest request, ModelMap model) {
		logger.info("PatientMainController checkSignature Starts==>");
		
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer patId = user.getId();
		String sig=userManager.checkSignature(patId);
		System.out.println("sig : "+sig);
		if(sig==null){
			jsonResponse.setMessage("no");
		}else{
			jsonResponse.setMessage("yes");
		}
		json = new Gson().toJson(jsonResponse);
		return json;
	}
	
	public Integer checkFileStatus(String filename,Integer patientid){
		
		Integer id=userManager.checkFileStatus(filename,patientid);
		System.out.println("id : "+id);
		/*if(sig==null){
			jsonResponse.setMessage("no");
		}else{
			jsonResponse.setMessage("yes");
		}*/
		
		return id;
	}
	
	
}
