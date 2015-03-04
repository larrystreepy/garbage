package com.bluehub.controller.admin;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bluehub.util.OSUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import org.jpedal.grouping.PdfGroupingAlgorithms;
import org.jpedal.grouping.SearchType;
import org.jpedal.objects.PdfPageData;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bluehub.bean.admin.AdminSearchDateForm;
import com.bluehub.bean.admin.DocumentList;
import com.bluehub.bean.admin.SearchPatientForm;
import com.bluehub.bean.admin.SearchPhysicianForm;
import com.bluehub.bean.user.JsonResponse;
import com.bluehub.bean.user.UserProfile;
import com.bluehub.bean.user.phyVisitForm;
import com.bluehub.manager.admin.AdminOrganizationManager;
import com.bluehub.manager.physician.PhysicianManager;
import com.bluehub.manager.user.UserManager;
import com.bluehub.util.AuditTrailUtil;
import com.bluehub.util.CommonUtils;
import com.bluehub.util.Constants;
import com.bluehub.vo.admin.AdminOrganizationVO;
import com.bluehub.vo.admin.AdminPracticeVO;
import com.bluehub.vo.admin.FaxVo;
import com.bluehub.vo.admin.PhysicianMappingVO;
import com.bluehub.vo.common.AuditTrailVO;
import com.bluehub.vo.common.EncounterVO;
import com.bluehub.vo.common.PatientDocument;
import com.bluehub.vo.common.PatientPrivateNoteVO;
import com.bluehub.vo.physician.VisitsVO;
import com.bluehub.vo.user.PatientRequestVO;
import com.google.gson.Gson;
import com.sfax.rest.SFax;
import com.sfax.rest.outbound.OCreate;

@Controller
public class AdminMainController {

	static ArrayList<String> pathLists = null;

	private static Logger logger = Logger.getLogger(AdminMainController.class);

	@Autowired
	private PhysicianManager physicianManager;

	public void setPhysicianManager(PhysicianManager physicianManager) {
		this.physicianManager = physicianManager;
	}

	@Autowired
	private AdminOrganizationManager adminOrganizationManager;

	@Autowired
	private UserManager userManager;

	/**
	 * @param userManager
	 *            the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setAdminOrganizationManager(
			AdminOrganizationManager adminOrganizationManager) {
		this.adminOrganizationManager = adminOrganizationManager;
	}

	@RequestMapping("/administrator/home.do")
	public String adminHomeController(ModelMap model, HttpServletRequest request) {

		logger.debug("AdminController adminhome==>");
		String viewPage = Constants.HOME_ADMIN_VIEW;

		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Long totalVisits = 0L;

		Integer userId = user.getId();

		totalVisits = userManager.getTotlPatientsVisits();

		model.addAttribute("totalVisits", totalVisits);

		Long totalUsers = userManager.getTotalUsers("Patient");

		model.addAttribute("patient", totalUsers);

		Long totalpatients = userManager.getTotalUsers("Physician");

		model.addAttribute("physician", totalpatients);

		// String url = request.getRequestURL().toString();
		//
		// if(url.contains("?")){
		//
		// model.addAttribute("url","http://localhost:8080/bluehub/administrator/adminupload.do");
		//
		// return "redirect";
		// }else{
		//
		return viewPage;

		// }

	}
	
   @RequestMapping("/administrator/adminEfaxAssociated.do")
	public String adminEFaxAssociated(ModelMap model) {
		System.out.println("AdminController adminEFaxAssociated==>");
		String viewPage = Constants.ADMIN_EFAX_ASS;
	
		return viewPage;
	}
	@RequestMapping("/administrator/adminEfax.do")
	public String adminEFax(ModelMap model) {
		System.out.println("AdminController adminEFax==>");
		String viewPage = Constants.ADMIN_EFAX;
	
		return viewPage;
	}
	
	@RequestMapping("/administrator/test.do")
	public String test(ModelMap model) {

		System.out.println("AdminController test==>");
		/*String viewPage = Constants.ADMIN_VISIT_DETAILS_VIEW;*/
		return null;
	}
	
	
	@RequestMapping("/administrator/adminvisitdetails.do")
	public String adminVisitDetails(ModelMap model) {

		logger.debug("AdminController adminhome==>");
		String viewPage = Constants.ADMIN_VISIT_DETAILS_VIEW;
		return viewPage;
	}

	@RequestMapping(value = "/administrator/saveAdminVisitDetails.do", method = RequestMethod.POST)
	public String getPhysicianVisitsForm(
			@ModelAttribute("physicianVisitsForm") phyVisitForm physicianVisitsForm,
			SessionStatus sessionStatus, ModelMap model,
			HttpServletRequest request) {

		logger.info("PhysicianMainController physicianVisitsForm() starts");

		String viewPage = Constants.ADMIN_VISIT_DETAILS_VIEW;
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
			physicianDto.setPhysicianid(physicianVisitsForm.getPhysicianId());
			physicianDto.setCreatedby(userId);
			physicianDto.setPatientid(physicianVisitsForm.getPatientId());
			physicianDto.setCreatedon(timestamp);

			Integer id = physicianManager.savePhysicianVisit(physicianDto);

			AuditTrailVO dto = AuditTrailUtil.SaveAuditTrailDetails(id,
					request, Constants.VISITS, Constants.INSERT,
					"New Patient Visit Created");

			userManager.saveAuditTrails(dto);

			// SaveAuditTrailDetails(id, request, Constants.VISITS,
			// Constants.INSERT, "New Patient Visit Created");

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

	@RequestMapping("/administrator/adminallvisits.do")
	public String adminAllVisits(ModelMap model) {

		logger.debug("AdminController adminhome==>");
        return Constants.ADMIN_ALL_VISITS_VIEW;
	}

	@RequestMapping("/administrator/adminencounter.do")
	public String adminEncounters(ModelMap model) {

		logger.debug("AdminController adminEncounters==>");
        return Constants.ADMIN_ENCOUNTERS_VIEW;
	}

	@RequestMapping("/administrator/adminviewencounter.do")
	public String adminViewEncounters(ModelMap model) {

		logger.debug("AdminController adminViewEncounters==>");
        return Constants.ADMIN_VIEW_ENCOUNTERS_VIEW;
	}

	@RequestMapping("/administrator/adminsearch.do")
	public String adminSearchController(ModelMap model) {

		logger.debug("adminSearchController");
        return Constants.SEARCH_ADMIN_VIEW;
	}

	@RequestMapping("/administrator/admindocumentsearchlist.do")
	public @ResponseBody
	String adminDocumentSearchList(@RequestParam(value = "search") String search, ModelMap model) {

		logger.debug("adminDocumentSearchList");

		String json = null;

		ArrayList<DocumentList> documentLists = new ArrayList<>();
		DocumentList documentList = null;

		String saveDirectory = Constants.getPlatformProperyValue(Constants.FAREFILE_UPLOAD_PATH);

		File file = new File(saveDirectory);

		// get all the files from a directory
		File[] fList = file.listFiles();

		for (File files : fList) {
			if (files.isFile()) {

			} else if (files.isDirectory()) {
				pathLists = findText(files.getAbsolutePath(), search);
				for (int i = 0; i < pathLists.size(); i++) {

					documentList = new DocumentList();
					String replaceString = null;
					String filename = null;
					String[] pathSplit = null;
					/*
					 * replaceString= pathLists.get(i).replaceAll("\\\\", "/");
					 * documentList
					 * .setFilename(replaceString.substring((replaceString
					 * .lastIndexOf("/")+1), replaceString.length()));
					 */

					if (OSUtils.isWindows()) {
						logger.info("windows");
						replaceString = pathLists.get(i).replaceAll("\\\\", "/");

						pathSplit = replaceString.split("/");

						filename = replaceString.substring(
								(replaceString.lastIndexOf("/") + 1),
								replaceString.length());

						documentList.setFilename(pathSplit[3]);

						if (pathSplit[2].equals("1")) {
							documentList.setUsername("Admin");
						} else {
							documentList
									.setUsername(userManager
											.getUserName(Integer
													.parseInt(pathSplit[2])));
						}

					} else {
						logger.info("linux");
						filename = pathLists.get(i).substring(
                                (pathLists.get(i).lastIndexOf("/") + 1),
                                pathLists.get(i).length());
						documentList.setFilename(filename);
					}

					documentList.setFilepath(replaceString);
					documentLists.add(documentList);
				}

			}
		}

		/*
		 * pathLists = findText("d:/test/","patient");
		 * logger.info("pathLists.size() : "+pathLists.size()); for (int
		 * i=0;i<pathLists.size();i++) {
		 * 
		 * documentList=new DocumentList();
		 * logger.info("pathLists : "+pathLists.get(i).lastIndexOf("/"));
		 * 
		 * logger.info(""+pathLists.get(i).substring((pathLists.get(i).
		 * lastIndexOf("/")+2), pathLists.get(i).length()));
		 * documentList.setFilename
		 * (pathLists.get(i).substring((pathLists.get(i).lastIndexOf("/")+2),
		 * pathLists.get(i).length()));
		 * documentList.setFilepath(pathLists.get(i));
		 * documentLists.add(documentList); }
		 */

		json = new Gson().toJson(documentLists);

		return json;
	}

	@RequestMapping("/administrator/admindocumentsearch.do")
	public String adminDocumentSearchController(ModelMap model) {

		logger.debug("adminDocumentSearchController");
		String viewPage = Constants.SEARCH_DOCUMENT_ADMIN_VIEW;
		return viewPage;
	}

	//

	@RequestMapping("/administrator/physicianmapping.do")
	public String adminPhysicianMapping(ModelMap model) {

		logger.debug("adminSearchController");
		String viewPage = Constants.ADMIN_PHYSICIAN_MAPPING;
		return viewPage;
	}

	@RequestMapping("/administrator/adminupload.do")
	public String adminUploadController(ModelMap model) {

		logger.debug("adminUploadController");
		String viewPage = Constants.UPLOAD_ADMIN_VIEW;
		return viewPage;
	}

	@RequestMapping("/administrator/adminorganization.do")
	public String adminOrganizationController(ModelMap model) {
		logger.debug("adminOrganizationController");
		String viewPage = Constants.ORGANIZATION_ADMIN_VIEW;
		return viewPage;
	}

	@RequestMapping(value = "/administrator/saveadminorganization.do")
	public String adminOrganizationController(
			@RequestParam(value = "organizationname") String name,
			@RequestParam(value = "address") String address, String city,
			String state, String zipcode,@RequestParam(value = "fax") String fax, ModelMap model) {
		logger.debug("adminOrganizationController");
		String viewPage = Constants.ORGANIZATION_ADMIN_VIEW;

		int regsiter = 1;
		int userid;
		try {
			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			userid = user.getId();
			AdminOrganizationVO organizationDto = new AdminOrganizationVO();
			organizationDto.setOrganizationname(name.trim());
			organizationDto.setAddress(address.trim());
			organizationDto.setCity(city.trim());
			organizationDto.setState(state.trim());
			organizationDto.setZipcode(zipcode.trim());
			organizationDto.setFax(fax.trim());
			organizationDto.setCreatedon(new Date());
			organizationDto.setStatus(1);
			organizationDto.setCreatedby(userid);
			adminOrganizationManager.saveAdminOrganization(organizationDto);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		logger.info("adminOrganizationController ends");

		return viewPage;
	}

	@RequestMapping(value = "/administrator/deleteadminorganization.do")
	public String deleteAdminOrganization(@RequestParam Integer orgid,
			ModelMap model) {
		logger.debug("adminOrganizationController");
		String viewPage = Constants.ORGANIZATION_ADMIN_VIEW;

		int regsiter = 1;

		try {

			/*
			 * AdminOrganizationVO organizationDto = new AdminOrganizationVO();
			 * organizationDto.setId(orgid);
			 * adminOrganizationManager.deleteAdminOrganization
			 * (organizationDto);
			 */
			adminOrganizationManager.deleteAdminOrganization(orgid);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		logger.info("adminOrganizationController ends");

		return viewPage;
	}

	@RequestMapping(value = "/administrator/editadminorganization.do", method = RequestMethod.GET)
	public @ResponseBody
	String editAdminOrganization(@RequestParam Integer orgid, ModelMap model) {
		logger.debug("editAdminOrganization : " + orgid);
		String json = null;
		AdminOrganizationVO organizationDto = null;
		try {
			organizationDto = adminOrganizationManager
					.geAdminOrganizationVOById(orgid);

			if (organizationDto.getCity() == null) {
				organizationDto.setCity("");
			}
			if (organizationDto.getState() == null) {
				organizationDto.setState("");
			}
			if (organizationDto.getZipcode() == null) {

				organizationDto.setZipcode("");
			}
			
			if (organizationDto.getFax() == null) {

				organizationDto.setFax("");
			}

			json = new Gson().toJson(organizationDto);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("editadminorganization ends");
		return json;
	}

	@RequestMapping(value = "/administrator/updateadminorganization.do")
	public String updateAdminOrganization(
			@RequestParam(value = "organizationname") String name,
			@RequestParam(value = "address") String address,
			@RequestParam(value = "orgid") Integer orgid,
			@RequestParam(value = "city") String city,
			@RequestParam(value = "state") String state,
			@RequestParam(value = "zipcode") String zipcode,
			@RequestParam(value = "fax") String fax,ModelMap model) {
		logger.debug("adminOrganizationController");
		String viewPage = Constants.ORGANIZATION_ADMIN_VIEW;
		int regsiter = 1;
		int userid;
		try {
			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			userid = user.getId();
			AdminOrganizationVO organizationDto = new AdminOrganizationVO();
			organizationDto.setOrganizationname(name.trim());
			organizationDto.setAddress(address.trim());
			organizationDto.setCity(city.trim());
			organizationDto.setState(state.trim());
			organizationDto.setZipcode(zipcode.trim());
			organizationDto.setId(orgid);
			organizationDto.setFax(fax);
			organizationDto.setStatus(1);
			organizationDto.setUpdatedon(new Date());
			organizationDto.setUpdatedby(userid);
			adminOrganizationManager.updateAdminOrganization(organizationDto);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		logger.info("updateAdminOrganization ends");

		return viewPage;
	}

	@RequestMapping(value = "/administrator/adminorganizationlist.do", method = RequestMethod.GET)
	public @ResponseBody
	String adminOrganizationList(HttpServletRequest request, ModelMap model) {
		logger.info("adminOrganizationList starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();

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
			List<AdminOrganizationVO> adminOrganizationVO = adminOrganizationManager
					.getAdminOrganizaion(paramsMap);

			Long count = 0L;

			count = adminOrganizationManager
					.getAdminOrganizaionCount(paramsMap);

			for (AdminOrganizationVO dto : adminOrganizationVO) {

				if (dto.getCity() == null) {
					dto.setCity("");
				}
				if (dto.getState() == null) {
					dto.setState("");
				}

				if (dto.getZipcode() == null) {
					dto.setZipcode("");
				}
				if (dto.getFax() == null) {
					dto.setFax("");
				}
			}

			if (adminOrganizationVO != null) {

				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
				int reworkCount = 0;
				for (AdminOrganizationVO form : adminOrganizationVO) {
					HashMap searchReportMap = new HashMap();

					searchReportMap.put("Name", form.getOrganizationname());
					searchReportMap.put("Address", form.getAddress());
					searchReportMap.put("City", form.getCity());
					searchReportMap.put("State", form.getState());
					searchReportMap.put("Zipcode", form.getZipcode());
					searchReportMap.put("id", form.getId());
					searchReportMap.put("Fax", form.getFax());

					searchReportArrayList.add(searchReportMap);
				}

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
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("adminOrganizationList ends");
		return finalJson;
	}

	@RequestMapping(value = "/administrator/adminorganizations.do", method = RequestMethod.GET)
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

	// Test
	@RequestMapping(value = "/administrator/adminallphysiciandetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadAllPhysicianDetails(@RequestParam String orgid,
			@RequestParam String searchphysician,
			@RequestParam String practiceid, HttpServletRequest request,
			ModelMap model) {
		logger.info("loadAllPhysicianDetails starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();

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

		if (searchphysician != null) {
			searchphysician = searchphysician.toLowerCase();
			paramsMap.put("searchphysician", searchphysician);
		}

		List<SearchPhysicianForm> registrationFormVO = null;
		try {
			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();

			Integer userId = user.getId();

			String role = user.getRoleid();
			Long count = userManager.getAllPhysicianDetailsCOunt(orgid,
					practiceid, searchphysician);

			registrationFormVO = userManager.getAllPhysicianDetails(orgid,
					practiceid, paramsMap);

			List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
			int reworkCount = 0;
			for (SearchPhysicianForm searchReportListObject : registrationFormVO) {
				HashMap searchReportMap = new HashMap();

				searchReportMap.put("Physician Name",
						searchReportListObject.getFirstname());
				searchReportMap.put("Organization",
						searchReportListObject.getOrganizationName());
				searchReportMap.put("Practice",
						searchReportListObject.getPracticename());
				searchReportMap.put("id", searchReportListObject.getUserid());
				searchReportArrayList.add(searchReportMap);
			}

			finalJson = CommonUtils.convertMaptoJsonForGrid(
					searchReportArrayList, Long.parseLong(count + ""), sEcho);
			model.addAttribute("finalJson", finalJson);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("loadAllPhysicianDetails ends");
		return finalJson;
	}
	
	@RequestMapping(value = "/administrator/adminallphysiciandetailswithphysician.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadAllPhysicianDetailsWithPhysician(@RequestParam String searchphysician,HttpServletRequest request,
			ModelMap model) {
		logger.info("loadAllPhysicianDetails starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();

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

		if (searchphysician != null) {
			searchphysician = searchphysician.toLowerCase();
			paramsMap.put("searchphysician", searchphysician);
		}

		List<SearchPhysicianForm> registrationFormVO = null;
		try {
			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();

			Integer userId = user.getId();

			String role = user.getRoleid();
//			Long count = userManager.getAllPhysicianDetailsCOunt(orgid,
//					practiceid, searchphysician);
			
			Long count = userManager.getAllPhysicianDetailsCOuntByPhysicianName(searchphysician);

//			registrationFormVO = userManager.getAllPhysicianDetails(orgid,
//					practiceid, paramsMap);
			
			List<Object[]> searchPhysicianList = userManager.getAllPhysicianDetailsByPhysicianName(paramsMap);

			List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
			int reworkCount = 0;
			for (Object[] obj : searchPhysicianList) {
				HashMap searchReportMap = new HashMap();

				searchReportMap.put("Physician Name",
						obj[1]);
				searchReportMap.put("Organization",
						obj[2]);
				searchReportMap.put("Practice",
						obj[3]);
				searchReportMap.put("id", obj[0]);
				searchReportArrayList.add(searchReportMap);
			}

			finalJson = CommonUtils.convertMaptoJsonForGrid(
					searchReportArrayList, Long.parseLong(count + ""), sEcho);
			model.addAttribute("finalJson", finalJson);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("loadAllPhysicianDetails ends");
		return finalJson;
	}

	@RequestMapping(value = "/administrator/adminphysiciandetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadPhysicianDetails(@RequestParam String searchphysician,
			@RequestParam String orgid, @RequestParam String practiceid) {
		logger.info("adminphysiciandetails starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		int emailExist = 0;
		List<SearchPhysicianForm> registrationFormVO = null;
		try {
			if (searchphysician != null) {
				searchphysician = searchphysician.toLowerCase();
			}
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

	@RequestMapping(value = "/administrator/adminallpatientdetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadAllPatientDetails() {
		logger.info("AdminController loadPatientDetails starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		int emailExist = 0;
		// List<UserPersonalInfoVO> patientDetails = null;

		List<SearchPatientForm> patientDetails = null;

		try {

			// List<String>
			// physicianList=adminOrganizationManager.getPhysicianList();
			patientDetails = physicianManager.getAllPatientDetails();
			if (patientDetails != null && patientDetails.size() > 0) {
				for (SearchPatientForm form : patientDetails) {
					form.setDateofbirth(CommonUtils.parseStringFromDate(form
							.getDob()));
				}

				json = new Gson().toJson(patientDetails);
			} else {

				json = new Gson().toJson(null);
			}

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("AdminController loadPatientDetails ends");
		return json;
	}

	@RequestMapping(value = "/administrator/adminpatientdetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadPatientDetails(@RequestParam String searchPatient,
			@RequestParam Integer orgid, HttpServletRequest request,
			ModelMap model) {
		logger.info("AdminController loadPatientDetails starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		int emailExist = 0;
		// List<UserPersonalInfoVO> patientDetails = null;

		List<SearchPatientForm> patientDetails = null;

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

		if (searchPatient != null) {
			searchPatient = searchPatient.toLowerCase();
			paramsMap.put("searchPatient", searchPatient);
			paramsMap.put("orgid", orgid);
		}

		try {

			// List<String>
			// physicianList=adminOrganizationManager.getPhysicianList();
			if (searchPatient != null) {
				searchPatient = searchPatient.toLowerCase();
			}
			Long count = physicianManager.getPatientDetailsCount(searchPatient,
					paramsMap);

			patientDetails = physicianManager.getPatientDetails(searchPatient,
					paramsMap);

			if (patientDetails != null && patientDetails.size() > 0) {
				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
				int reworkCount = 0;
				for (SearchPatientForm form : patientDetails) {
					HashMap searchReportMap = new HashMap();

					searchReportMap.put("Patient Name", form.getFirstname());
					searchReportMap.put("Last Name", form.getLastname());
					searchReportMap.put("DOB",
							CommonUtils.parseStringFromDate(form.getDob()));
					searchReportMap.put("SSN", form.getSsn());
					searchReportMap.put("Contact Number",
							form.getContact_number());
					searchReportMap.put("Address", form.getAddress());
					searchReportMap.put("id", form.getUserid());
					searchReportMap.put("patId", form.getUserid());
					searchReportArrayList.add(searchReportMap);
				}

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
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("AdminController loadPatientDetails ends");
		return finalJson;
	}

	
	@RequestMapping(value = "/administrator/adminsearchkeywordvisitrecords.do", method = RequestMethod.GET)
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

			if (keyword != null) {
				keyword = keyword.toLowerCase();
			}
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

	@RequestMapping(value = "/administrator/adminsearchtagvisitrecords.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadTagVisitRecords(@RequestParam String tag,
			HttpServletRequest request, ModelMap model) {
		logger.info("AdminController loadTagVisitRecords starts ");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		// List<UserVO> registrationFormVO = null;
		List<VisitsVO> physicianVisitdetails = null;

		List<AdminSearchDateForm> adminSearchDateForms = null;
		AdminSearchDateForm adminSearchDateForm = null;

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
			adminSearchDateForms = new ArrayList<AdminSearchDateForm>();
			if (tag != null) {
				tag = tag.toLowerCase();
			}
			physicianVisitdetails = physicianManager.getTagVisitDetails(tag,
					paramsMap);

			if (physicianVisitdetails != null) {

				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
				int reworkCount = 0;
				for (VisitsVO form : physicianVisitdetails) {
					HashMap searchReportMap = new HashMap();

					searchReportMap.put("Patient Name",
							userManager.getUserName(form.getPatientid()));
					searchReportMap.put("DOB", CommonUtils
							.parseStringFromDate(userManager.getDob(form
									.getPatientid())));
					searchReportMap.put("Visit Date", CommonUtils
							.parseStringFromDate(form.getDateofvisit()));
					searchReportMap.put("Physician",
							userManager.getUserName(form.getPhysicianid()));
					searchReportMap.put("Reason For Visit",
							form.getReasonforvisit());
					searchReportMap.put("id", form.getId());
					searchReportMap.put("Tag", form.getTag());
					searchReportMap.put("patId", form.getPatientid());
					searchReportArrayList.add(searchReportMap);
				}

				Long count = physicianManager.getTagVisitDetailsCount(tag,
						paramsMap);
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

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("AdminController loadTagVisitRecords ends");
		return finalJson;
	}

	//

	@RequestMapping(value = "/administrator/adminsearchdatevisitrecords.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadSearchVisitRecords(@RequestParam String searchDate,
			HttpServletRequest request, ModelMap model) {
		logger.info("AdminController loadSearchVisitRecords starts ");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();

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

		List<VisitsVO> physicianVisitdetails = null;

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
			physicianVisitdetails = physicianManager.getSearchVisitRecords(
					searchDate, userId, role, paramsMap);

			if (physicianVisitdetails != null) {

				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
				int reworkCount = 0;
				for (VisitsVO form : physicianVisitdetails) {
					HashMap searchReportMap = new HashMap();

					searchReportMap.put("Patient Name",
							userManager.getUserName(form.getPatientid()));
					searchReportMap.put("DOB", CommonUtils
							.parseStringFromDate(userManager.getDob(form
									.getPatientid())));
					searchReportMap.put("Visit Date", CommonUtils
							.parseStringFromDate(form.getDateofvisit()));
					searchReportMap.put("Physician",
							userManager.getUserName(form.getPhysicianid()));
					searchReportMap.put("Reason For Visit",
							form.getReasonforvisit());
					searchReportMap.put("id", form.getId());
					searchReportMap.put("Tag", form.getTag());
					searchReportMap.put("patId", form.getPatientid());
					searchReportArrayList.add(searchReportMap);
				}
				Long count = physicianManager.getSearchVisitRecordsCount(
						searchDate, userId, role, paramsMap);

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
		logger.info("AdminController loadSearchVisitRecords ends");

		return finalJson;
	}

	@RequestMapping(value = "/administrator/adminsearchphysicianvisitrecords.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadSearchPhysicianVisitRecords(@RequestParam String physicianid,
			HttpServletRequest request, ModelMap model) {
		System.out
				.println("AdminController loadSearchPhysicianVisitRecords starts ");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		int emailExist = 0;
		// List<UserVO> registrationFormVO = null;
		List<VisitsVO> physicianVisitdetails = null;

		List<AdminSearchDateForm> adminSearchDateForms = null;
		AdminSearchDateForm adminSearchDateForm = null;

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

		Long count = physicianManager.getSearchPhysicianVisitRecordsCount(
				physicianid, paramsMap);
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userId = user.getId();

		String role = user.getRoleid();
		try {
			adminSearchDateForms = new ArrayList<AdminSearchDateForm>();
			physicianVisitdetails = physicianManager
					.getSearchPhysicianVisitRecords(physicianid, paramsMap,
							userId, role);

			if (physicianVisitdetails != null) {
				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
				int reworkCount = 0;
				for (VisitsVO form : physicianVisitdetails) {
					HashMap searchReportMap = new HashMap();

					searchReportMap.put("Patient Name",
							userManager.getUserName(form.getPatientid()));
					searchReportMap.put("DOB", CommonUtils
							.parseStringFromDate(userManager.getDob(form
									.getPatientid())));
					searchReportMap.put("Visit Date", CommonUtils
							.parseStringFromDate(form.getDateofvisit()));
					searchReportMap.put("Physician",
							userManager.getUserName(form.getPhysicianid()));
					searchReportMap.put("Reason For Visit",
							form.getReasonforvisit());
					searchReportMap.put("id", form.getId());
					searchReportMap.put("Tag", form.getTag());
					searchReportMap.put("patId", form.getPatientid());
					searchReportArrayList.add(searchReportMap);
				}

				finalJson = CommonUtils.convertMaptoJsonForGrid(
						searchReportArrayList, Long.parseLong(count + ""),
						sEcho);
				model.addAttribute("finalJson", finalJson);
			} else {
				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
				finalJson = CommonUtils.convertMaptoJsonForGrid(
						searchReportArrayList, 0L, sEcho);
			}

		} catch (Exception e) {

			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		System.out
				.println("AdminController loadSearchPhysicianVisitRecords ends");
		return finalJson;
	}

	@RequestMapping(value = "/administrator/adminsearchpatientvisitrecords.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadSearchPatientVisitRecords(@RequestParam String patientid,
			HttpServletRequest request, ModelMap model) {
		System.out
				.println("AdminController loadSearchPatientVisitRecords starts ");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		int emailExist = 0;
		// List<UserVO> registrationFormVO = null;
		List<VisitsVO> physicianVisitdetails = null;

		List<AdminSearchDateForm> adminSearchDateForms = null;
		AdminSearchDateForm adminSearchDateForm = null;
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
			
			
			adminSearchDateForms = new ArrayList<AdminSearchDateForm>();
			physicianVisitdetails = physicianManager
					.getSearchPatientVisitRecords(patientid, paramsMap);

			// registrationFormVO =
			// userManager.getSearchVisitDetails(searchDate);

			if (physicianVisitdetails != null) {

				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
				int reworkCount = 0;
				for (VisitsVO form : physicianVisitdetails) {
					HashMap searchReportMap = new HashMap();

					searchReportMap.put("Patient Name",
							userManager.getUserName(form.getPatientid()));
					searchReportMap.put("DOB", CommonUtils
							.parseStringFromDate(userManager.getDob(form
									.getPatientid())));
					searchReportMap.put("Visit Date", CommonUtils
							.parseStringFromDate(form.getDateofvisit()));
					searchReportMap.put("Physician",
							userManager.getUserName(form.getPhysicianid()));
					searchReportMap.put("Reason For Visit",
							form.getReasonforvisit());
					searchReportMap.put("id", form.getId());
					searchReportMap.put("Tag", form.getTag());
					searchReportMap.put("patId", form.getPatientid());
					searchReportArrayList.add(searchReportMap);
				}

				Long count = physicianManager
						.getSearchPatientVisitRecordsCount(patientid, paramsMap);
				finalJson = CommonUtils.convertMaptoJsonForGrid(
						searchReportArrayList, count, sEcho);
				model.addAttribute("finalJson", finalJson);

			} else {
				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
				finalJson = CommonUtils.convertMaptoJsonForGrid(
						searchReportArrayList, 0L, sEcho);
				model.addAttribute("finalJson", finalJson);

			}

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		System.out
				.println("AdminController loadSearchPatientVisitRecords ends");
		return finalJson;
	}

	//

	@RequestMapping(value = "/administrator/patientrequestinfo.do", method = RequestMethod.GET)
	public @ResponseBody
	String savePatientRequest(@RequestParam String physicianid) {
		logger.info("savePatientRequest starts" + physicianid);
		String json = null;

		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userId = user.getId();

		JsonResponse jsonResponse = new JsonResponse();
		int emailExist = 0;

		try {
			PatientRequestVO patientRequestVO = null;

			patientRequestVO = new PatientRequestVO();

			patientRequestVO.setPatientid(userId);
			patientRequestVO.setPhysicianid(Integer.parseInt(physicianid));
			patientRequestVO.setEmailid("");

			userManager.savePatientRequestVo(patientRequestVO);

			json = new Gson().toJson(patientRequestVO);
			// }
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("savePatientRequest ends");
		return json;
	}

	@RequestMapping(value = "/administrator/loadorganization.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadOrganization() {
		logger.info("UserController checkUserEmail() starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		int emailExist = 0;
		try {

			List<AdminOrganizationVO> adminOrganizationVO = adminOrganizationManager
					.getAdminOrganizaions();
			/*
			 * for (AdminOrganizationVO adminOrganizationVO2 :
			 * adminOrganizationVO) {
			 * jsonResponse.setMessage(adminOrganizationVO2
			 * .getOrganizationname()); }
			 */
			json = new Gson().toJson(adminOrganizationVO);
			// }
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("UserController checkUserEmail() ends");
		return json;
	}

	@RequestMapping(value = "/administrator/checkExistingPhysician.do")
	public @ResponseBody
	String checkExistingPhysician(ModelMap model, HttpServletRequest request) {

		String detail = "";

		// Integer mappingId =
		// Integer.parseInt(request.getParameter("mappingId"));

		Integer organizationId = Integer.parseInt(request
				.getParameter("organizationId"));
		Integer practiceId = Integer.parseInt(request
				.getParameter("practiceId"));
		String username = request.getParameter("userName");
		Integer userId = null;
		logger.debug("Admincontroller checkExistingPhysician mapping Strats() ==>");
		String json = null;
		Boolean flag = false;

		String[] userval = username.split("_");

		userId = Integer.parseInt(userval[1]);
		Map map = new HashMap();

		map.put("practiceId", practiceId);
		map.put("organizationId", organizationId);
		map.put("userId", userId);

		JsonResponse jsonResponse = new JsonResponse();
		try {

			flag = physicianManager.checkAlreadyExistingPhysician(map);

			if (!flag) {

				jsonResponse
						.setMessage("Physician Already Mapped to Selected Organization");
				jsonResponse.setStatus("NO");
				json = new Gson().toJson(jsonResponse);
			} else {

				jsonResponse.setMessage("");
				jsonResponse.setStatus("YES");
				json = new Gson().toJson(jsonResponse);
			}

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));

			jsonResponse.setMessage("error occured");
			json = new Gson().toJson(jsonResponse);
		}
		logger.info("Admincontroller checkExistingPhysician mapping Ends() ==>");

		return json;
	}

	@RequestMapping(value = "/administrator/savephysicianmapping.do")
	public @ResponseBody
	String savePhysicianmapping(ModelMap model, HttpServletRequest request) {

		String detail = "";

		// Integer mappingId =
		// Integer.parseInt(request.getParameter("mappingId"));

		Integer organizationId = Integer.parseInt(request
				.getParameter("organizationId"));
		Integer practiceId = Integer.parseInt(request
				.getParameter("practiceId"));
		String username = request.getParameter("userName");
		Integer userId = null;
		logger.debug("Admincontroller savephysician mapping Strats() ==>");
		String json = null;

		String[] userval = username.split("_");

		userId = Integer.parseInt(userval[1]);

		Timestamp createdOnTime = new Timestamp(new Date().getTime());
		JsonResponse jsonResponse = new JsonResponse();
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		try {

			PhysicianMappingVO mappingVo = null;

			mappingVo = new PhysicianMappingVO();

			if (request.getParameter("mappingId") != null
					&& !request.getParameter("mappingId").equals(null)
					&& !request.getParameter("mappingId").equals("")) {

				// update here

				mappingVo = userManager.findPhysicianMapping(Integer
						.parseInt(request.getParameter("mappingId")));

				if (!mappingVo.getOrganizationid().equals(organizationId)) {
					// detail = detail + " Libeary Fine is  modified from "+
					// libearyFine + " to " + libraryFineDTO.getLibearyFine() +
					// " ;";
					detail = detail + "Organization is Changed From "
							+ mappingVo.getOrganizationid() + " to "
							+ organizationId + ";";

					mappingVo.setOrganizationid(organizationId);
				}

				if (!mappingVo.getPracticeid().equals(practiceId)) {

					detail = detail + "Practice is Changed From "
							+ mappingVo.getPracticeid() + " to " + practiceId
							+ ";";

					mappingVo.setPracticeid(practiceId);
				}

				if (!mappingVo.getUserid().equals(userId)) {

					detail = detail + " UserId is Changed From "
							+ mappingVo.getUserid() + " to " + userId + ";";

					mappingVo.setUserid(userId);
				}

				mappingVo.setUpdatedon(createdOnTime);
				mappingVo.setUpdatedby(user.getId());

				userManager.updatePhysicianmappingVo(mappingVo);

				AuditTrailVO dto = AuditTrailUtil.SaveAuditTrailDetails(
						mappingVo.getId(), request,
						Constants.PHYSICIAN_MAPPING, Constants.UPDATE, detail);

				userManager.saveAuditTrails(dto);

				jsonResponse
						.setMessage("Physician Mapping Updated Successfully");
				json = new Gson().toJson(jsonResponse);
			} else {

				// save here

				mappingVo.setUserid(userId);
				mappingVo.setOrganizationid(organizationId);
				mappingVo.setPracticeid(practiceId);

				mappingVo.setCreatedon(createdOnTime);
				mappingVo.setCreatedby(user.getId());
				mappingVo.setStatus(1);

				Integer physicianMappingId = userManager
						.svaePhysicianmappingVo(mappingVo);

				AuditTrailVO dto = AuditTrailUtil.SaveAuditTrailDetails(
						physicianMappingId, request,
						Constants.PHYSICIAN_MAPPING, Constants.INSERT,
						"New Physician Assigned");
				userManager.saveAuditTrails(dto);

				jsonResponse.setMessage("Physician Mapping Saved Successfully");
				json = new Gson().toJson(jsonResponse);
			}

			model.addAttribute("successMsg",
					Constants.getPropertyValue(Constants.REGISTER_SUCCESS));
			model.addAttribute("redirectMsg",
					Constants.getPropertyValue(Constants.redirect_msg));
			model.addAttribute("regsiter", 1);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));

			jsonResponse.setMessage("error occured");
			json = new Gson().toJson(jsonResponse);
		}
		logger.info("Admincontroller savephysician mapping Ends() ==>");

		return json;
	}

	@RequestMapping(value = "/administrator/physicianmappinglist.do", method = RequestMethod.GET)
	public @ResponseBody
	String adminPhysicianMappingList(HttpServletRequest request, ModelMap model) {
		System.out
				.println("AdminMainController adminPhysicianMappingList starts");
		JsonResponse jsonResponse = new JsonResponse();
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

			Integer count = adminOrganizationManager.getPhysicianMappingCount();
			List<Object[]> physicianMaping = adminOrganizationManager
					.getPhysicianMapping(paramsMap);

			List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
			int reworkCount = 0;
			for (Object[] searchReportListObject : physicianMaping) {
				HashMap searchReportMap = new HashMap();

				searchReportMap
						.put("Physician Name", searchReportListObject[1]);
				searchReportMap.put("Organization", searchReportListObject[2]);
				searchReportMap.put("Practice", searchReportListObject[3]);
				searchReportMap.put("id", searchReportListObject[0]);
				searchReportMap.put("Actions", "");
				searchReportArrayList.add(searchReportMap);
			}

			finalJson = CommonUtils.convertMaptoJsonForGrid(
					searchReportArrayList, Long.parseLong(count + ""), sEcho);
			model.addAttribute("finalJson", finalJson);
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		System.out
				.println("AdminMainController adminPhysicianMappingList ends");
		return finalJson;
	}

	@RequestMapping(value = "/administrator/deletephysicianmapping.do", method = RequestMethod.GET)
	public @ResponseBody
	String deletePhysicianMapping(@RequestParam Integer mappingId,
			HttpServletRequest request) {
		logger.debug("AdminMainController deletePhysicianMapping starts==>");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		try {

			PhysicianMappingVO mappingVo = new PhysicianMappingVO();
			mappingVo.setId(mappingId);
			mappingVo.setStatus(0);
			adminOrganizationManager.deletePhysicianMapping(mappingVo);

			AuditTrailVO dto = AuditTrailUtil.SaveAuditTrailDetails(
					mappingVo.getId(), request, Constants.PHYSICIAN_MAPPING,
					Constants.DELETE, "Physician Mapping Deleted");

			userManager.saveAuditTrails(dto);

			jsonResponse.setMessage("Physician Mapping Deleted Successfully");
			json = new Gson().toJson(jsonResponse);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());

			jsonResponse.setMessage("Error");
			json = new Gson().toJson(jsonResponse);
		}
		logger.info("AdminMainController deletePhysicianMapping ends");

		return json;
	}

	@RequestMapping(value = "/administrator/loadalllist.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadAllList(HttpServletRequest request, ModelMap model) {
		logger.info("AdminMainController loadAllList() starts==>");
		String json = null;
		List<AdminSearchDateForm> adminSearchDateForms = null;
		AdminSearchDateForm adminSearchDateForm = null;
		JsonResponse jsonResponse = new JsonResponse();

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

		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userid = user.getId();
		String role = user.getRoleid();
		Long count = physicianManager.getAllListCount(userid, role, paramsMap);
		try {
			adminSearchDateForms = new ArrayList<AdminSearchDateForm>();
			List<VisitsVO> practiceVo = physicianManager.getAllList(userid,
					role, paramsMap);

			if (practiceVo != null) {
				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();

				for (VisitsVO form : practiceVo) {
					HashMap searchReportMap = new HashMap();

					searchReportMap.put("Patient Name",
							userManager.getUserName(form.getPatientid()));
					searchReportMap.put("DOB", CommonUtils
							.parseStringFromDate(userManager.getDob(form
									.getPatientid())));
					searchReportMap.put("Visit Date", CommonUtils
							.parseStringFromDate(form.getDateofvisit()));
					searchReportMap.put("Physician",
							userManager.getUserName(form.getPhysicianid()));
					searchReportMap.put("Reason For Visit",
							form.getReasonforvisit());
					searchReportMap.put("id", form.getId());
					searchReportMap.put("Tag", form.getTag());
					searchReportArrayList.add(searchReportMap);
				}
				finalJson = CommonUtils.convertMaptoJsonForGrid(
						searchReportArrayList, Long.parseLong(count + ""),
						sEcho);
				model.addAttribute("finalJson", finalJson);
			} else {
				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
				finalJson = CommonUtils.convertMaptoJsonForGrid(
						searchReportArrayList, 0L, sEcho);
			}

			/*
			 * for (VisitsVO physicianVO : practiceVo) { adminSearchDateForm=new
			 * AdminSearchDateForm();
			 * 
			 * adminSearchDateForm.setPatientname(userManager.getUserName(
			 * physicianVO.getPatientid()));
			 * adminSearchDateForm.setPhysicianname
			 * (userManager.getUserName(physicianVO.getPhysicianid()));
			 * adminSearchDateForm
			 * .setPatientdob(userManager.getDob(physicianVO.getPatientid()));
			 * //
			 * adminSearchDateForm.setDateofvisit(physicianVO.getDateofvisit());
			 * adminSearchDateForm
			 * .setReasonforvisit(physicianVO.getReasonforvisit());
			 * 
			 * adminSearchDateForm.setVisitid(physicianVO.getId());
			 * adminSearchDateForm
			 * .setVisitdate(CommonUtils.parseStringFromDate(physicianVO
			 * .getDateofvisit()));
			 * adminSearchDateForm.setDob(CommonUtils.parseStringFromDate
			 * (userManager.getDob(physicianVO.getPatientid())));
			 * 
			 * adminSearchDateForms.add(adminSearchDateForm); }
			 */

			// json = new Gson().toJson(adminSearchDateForms);
			// }
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			json = new Gson().toJson(null);
		}
		logger.info("AdminMainController loadAllList() ends==>");
		return finalJson;
	}

	@RequestMapping(value = "/administrator/loadpractice.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadPractices(@RequestParam Integer orgid) {
		logger.info("AdminMainController loadPractices() starts==>" + orgid);
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

	@RequestMapping(value = "/administrator/loadPhysicians.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadPhysicians() {
		logger.info("AdminMainController loadPhysicians() starts==>");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();

		try {

			List<String> physicianList = adminOrganizationManager
					.getPhysicianList();

			json = new Gson().toJson(physicianList);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			json = new Gson().toJson(null);
		}
		logger.info("AdminMainController loadPhysicians() ends==>");
		return json;
	}

	// prithivi
	@RequestMapping("/administrator/patientmanagement.do")
	public String patientManagement(ModelMap model) {

		logger.info("Entering the Admin controller patientmanagement method");
		String viewPage = Constants.PATIENT_MANAGEMENT;
		return viewPage;
	}

	// prithivi
	@RequestMapping(value = "/administrator/patientDetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String patientDetails(@RequestParam String firstName,
			@RequestParam String searchVal) {
		logger.info("AdminController PatientDetails() starts");

		String json = null;
		List<SearchPatientForm> searchPatientDetails = null;
		try {

			if (firstName != null) {
				firstName = firstName.toLowerCase();
			}

			searchPatientDetails = adminOrganizationManager.getPatientDetails(
					firstName, searchVal);

			for (SearchPatientForm form : searchPatientDetails) {

				form.setDateofbirth(CommonUtils.parseStringFromDate(form
						.getDob()));
			}

			json = new Gson().toJson(searchPatientDetails);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}

		return json;
	}

	// prithivi
	@RequestMapping(value = "/administrator/patientEnableStatus.do", method = RequestMethod.GET)
	public @ResponseBody
	void patientEnableStatus(@RequestParam String selectedId) {
		logger.info("Admin controller patientEnableStatus() starts");
		try {
			adminOrganizationManager.setStatus(selectedId);
			logger.info("Admin controller patientEnableStatus() ends");
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}

	}

	// prithivi
	@RequestMapping(value = "/administrator/patientDisableStatus.do", method = RequestMethod.GET)
	public @ResponseBody
	void patientDisableStatus(@RequestParam String selectedId) {
		logger.info("Admin controller patientDisableStatus() starts");
		try {
			adminOrganizationManager.setStatusDisable(selectedId);
			logger.info("Admin controller patientDisableStatus() ends");
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}

	}

	// prithivi
	@RequestMapping("/administrator/physicianmanagement.do")
	public String physicianManagement(ModelMap model) {
		logger.info("Entering the Admin controller physicianmanagement method");
		String viewPage = Constants.PHYSICIAN_MANAGEMENT;
		return viewPage;
	}

	// prithivi
	@RequestMapping(value = "/administrator/physicianDetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String physicianDetails(@RequestParam String firstName,
			@RequestParam String organizationName,
			@RequestParam String practiceName) {
		logger.info("AdminController PatientDetails() starts");

		String json = null;
		List<SearchPhysicianForm> searchPatientDetails = null;
		try {

			if (firstName != null) {
				firstName = firstName.toLowerCase();
			}
			String role = null;

			searchPatientDetails = adminOrganizationManager
					.getPhysicianDetails(firstName, organizationName,
							practiceName);

			for (SearchPhysicianForm dto : searchPatientDetails) {

				role = dto.getRole();

				if (role.equals("Physician") || role.equals("PracticeAdmin")) {

					String originalRole = adminOrganizationManager
							.getUserRoleByUserId(dto.getUserid());

					if (originalRole != null) {

						dto.setRole("Practice Admin");

					}
				}

			}

			json = new Gson().toJson(searchPatientDetails);
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}

		return json;
	}

	@RequestMapping(value = "/administrator/editphysicianmapping.do", method = RequestMethod.GET)
	public @ResponseBody
	String editPhysicianMapping(@RequestParam Integer mappingId) {
		logger.debug("AdminMainController editPhysicianMapping starts==>");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		List<Object[]> mappingList = null;
		try {

			PhysicianMappingVO mappingVo = new PhysicianMappingVO();
			// mappingVo.setId(mappingId);

			// adminOrganizationManager.deletePhysicianMapping(mappingVo);
			mappingList = adminOrganizationManager
					.findPhysicianMapping(mappingId);

			// jsonResponse.setMessage("Physician Mapping Deleted Successfully");
			json = new Gson().toJson(mappingList);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());

			// jsonResponse.setMessage("Error");
			json = new Gson().toJson(jsonResponse);
		}
		logger.info("AdminMainController editPhysicianMapping ends");

		return json;
	}

	@RequestMapping(value = "/uploadfile.do", method = RequestMethod.POST)
	public @ResponseBody
	String uploadFile(MultipartHttpServletRequest request) {
		String response = null;
		try {

			String returnValue=null;
			/*
			 * String docId = request.getParameter("docId"); String emailId =
			 * request.getParameter("emailId"); String type =
			 * request.getParameter("type");
			 */

			UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Iterator<String> it = request.getFileNames();
			while (it.hasNext()) {

				String fileName = it.next();
				MultipartFile file = request.getFile(fileName);
				String emailId = user.getId().toString();
				String role = user.getRoleid();

				if(role.equalsIgnoreCase("Patient")){
					returnValue=userManager.handleFileUploadPatient(file, emailId);
				}else{
					returnValue=userManager.handleFileUpload(file, emailId);
				}

				/*
				 * Integer createdby = user.getId(); Timestamp createdOnTime =
				 * new Timestamp(new Date().getTime());
				 * 
				 * PatientDocument documentVO = new PatientDocument();
				 * 
				 * documentVO.setCreatedby(createdby);
				 * documentVO.setCreatedon(createdOnTime);
				 * 
				 * documentVO.setDocname(file.getOriginalFilename());
				 * documentVO.setDocpath("D:\\FileUpload\\"+userid+"\\"
				 * +file.getOriginalFilename());
				 * documentVO.setPatientid(userid);
				 * 
				 * 
				 * userManager.savePatientDocument(documentVO);
				 */
			}
            if (logger.isDebugEnabled()) logger.debug("returnValue : "+returnValue);

			if(returnValue == null){
				response = "error";
			}else{
				response = Constants.SUCCESS;
			}
			
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage(), e);
		}
		return response;
	}

	@RequestMapping(value = "/administrator/checkorganizationname.do", method = RequestMethod.GET)
	public @ResponseBody
	String checkOrganizationName(@RequestParam String orgname) {
		logger.info("check organization name starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		int nameExist = 0;
		try {
			if (StringUtils.isNotBlank(orgname)) {
				nameExist = adminOrganizationManager
						.getOrganizationNameExist(orgname);
				if (nameExist > 0) {
					jsonResponse.setMessage(Constants
							.getPropertyValue("organization_already_exist"));
					jsonResponse.setStatus("Yes");
				}
				json = new Gson().toJson(jsonResponse);
			}
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("check organization name exists ends");
		return json;
	}

	@RequestMapping(value = "/administrator/checkpracticename.do", method = RequestMethod.GET)
	public @ResponseBody
	String checkPracticeName(@RequestParam String practicename, @RequestParam Integer orgId) {
		logger.info("check organization name starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		int nameExist = 0;
		try {
			if (StringUtils.isNotBlank(practicename)) {
				nameExist = adminOrganizationManager
						.getPractiveNameExist(practicename,orgId);
				if (nameExist > 0) {
					jsonResponse.setMessage("Practice Already Exists");
					jsonResponse.setStatus("Yes");
				}
				json = new Gson().toJson(jsonResponse);
			}
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("check organization name exists ends");
		return json;
	}

	// prithivi
	@RequestMapping(value = "/administrator/physicianEnableStatus.do", method = RequestMethod.GET)
	public @ResponseBody
	void physicianEnableStatus(@RequestParam String selectedId) {
		logger.info("Admin controller physicianEnableStatus() starts");
		try {
			adminOrganizationManager.setStatus(selectedId);
			logger.info("Admin controller physicianEnableStatus() ends");
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
	}

	// prithivi
	@RequestMapping(value = "/administrator/physicianDisableStatus.do", method = RequestMethod.GET)
	public @ResponseBody
	void physicianDisableStatus(@RequestParam String selectedId) {
		logger.info("Admin controller physicianDisableStatus() starts");
		try {
			adminOrganizationManager.setStatusDisable(selectedId);
			logger.info("Admin controller physicianDisableStatus() ends");
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
	}

	@RequestMapping("/administrator/fileuploadclinicalinformation.do")
	public String adminFileUploadClinicalInformation(ModelMap model) {

		logger.debug("adminFileUploadClinicalInformation");
		String viewPage = Constants.FILEUPLOAD_ADMIN_VIEW;
		return viewPage;
	}

	@RequestMapping(value = "/administrator/patientviewdetails.do", method = RequestMethod.GET)
	public String loadPatientVisitDetails(ModelMap model) {
		logger.info("loadPatientVisitDetails starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		int nameExist = 0;

		String viewPage = Constants.PATIENT_VISITDETAILS_VIEW;
		List<VisitsVO> physicianVisitdetails = null;

		List<AdminSearchDateForm> adminSearchDateForms = null;
		AdminSearchDateForm adminSearchDateForm = null;
		try {
			adminSearchDateForms = new ArrayList<AdminSearchDateForm>();
			physicianVisitdetails = physicianManager
					.getKeywordVisitDetails("s");

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

			model.addAttribute(adminSearchDateForm);

			// json = new Gson().toJson(adminSearchDateForms);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("loadPatientVisitDetails ends");
		return viewPage;
	}

	private String moveFileToCourseLocation(Integer userid,
			Integer destinationUserId, Integer visitid,
			HttpServletRequest request) {

		logger.info("moveFileToCourseLocation");
		String uploadPath = null;
		String downloadPath = null;
		File sourceFile = null;
		File destinationFile = null;
		String returnPath = null;
		String result = null;
		String sourcePath = Constants.getPlatformProperyValue(Constants.FILE_UPLOAD_PATH);
		String destinationPath = Constants.getPlatformProperyValue(Constants.FAREFILE_UPLOAD_PATH);

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
								documentVO.setKeyid(visitid);
								documentVO.setKey("Upload");
								documentVO.setUploadType("Normal");

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
								documentVO.setKeyid(visitid);
								documentVO.setKey("Upload");
								documentVO.setUploadType("Normal");

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
						logger.info("Directory " + listOfFiles[i].getName());
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

	@RequestMapping(value = "/administrator/uploadClinicalInformation.do", method = RequestMethod.GET)
	public @ResponseBody
	String uploadClinicalInformations(@RequestParam Integer patientid,@RequestParam Integer requestId,
			@RequestParam Integer visitid, HttpServletRequest request) {
		logger.info("Admin controller uploadClinicalInformations() starts");

		String json = null;
		JsonResponse jsonResponse = new JsonResponse();

		try {
			//update the patient request to outstanding to complete
			
			
			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();

			Integer userid = user.getId();
			String role=user.getRoleid();
			System.out.println("user.getRoleid()  : "+user.getRoleid());
			System.out.println("userid for doc upload : "+userid);
			if(role.equalsIgnoreCase("Patient")){
				patientid=userid;
			}
			String result = moveFileToCourseLocation(userid, patientid,
					visitid, request);

			if (result.equals("YES")) {
				physicianManager.updatePatientRequestVo(requestId);
				jsonResponse.setStatus("YES");
				jsonResponse.setMessage("File Uploaded Successfully");

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

		logger.info("Admin controller uploadClinicalInformations() ends");
		return json;
	}
	
	private String sendFax(Integer userid,
			Integer destinationUserId, Integer visitid,
			HttpServletRequest request,String faxid,Integer orgId) {

		logger.info("moveFileToCourseLocation");
		String uploadPath = null;
		String downloadPath = null;
		File sourceFile = null;
		File destinationFile = null;
		String returnPath = null;
		String result = null;
		String sourcePath = Constants.getPlatformProperyValue(Constants.FILE_UPLOAD_PATH);
		String destinationPath = Constants.getPlatformProperyValue(Constants.FAREFILE_UPLOAD_PATH);

		String faxUsername = null;
		String faxNumber=null;
		String webApiKey=null;
		String vector=null;
		String encryptionKey=null;
		String faxTrash = null;
		
				faxTrash = Constants.getPlatformProperyValue(Constants.FAX_TRASH_PATH);
		
				faxUsername = Constants.getEfaxPropertyValue("faxusername");
				faxNumber = Constants.getEfaxPropertyValue("faxnumber");
				webApiKey = Constants.getEfaxPropertyValue("webapikey");
				vector = Constants.getEfaxPropertyValue("vector");
				encryptionKey = Constants.getEfaxPropertyValue("encryptionkey");
				sourceFile = new File(sourcePath + userid.toString());

				Date newDate = new Date();
				String queueId ="-1";
				String time = newDate.getTime() + "";
				File folder = new File(sourcePath + userid.toString());
				File[] listOfFiles = folder.listFiles();
				Integer createdby = userid;
				FaxVo faxVo = null;
				Timestamp createdOnTime = new Timestamp(new Date().getTime());
		
				String response  = null;
		try {
			File fileName = null;
			
			SFax.init(faxUsername,webApiKey,vector,encryptionKey,faxNumber);//Enter a Valid Username, WebApiKey, Vector, EncryptionKey and Fax Number
			if (listOfFiles != null && listOfFiles.length > 0) { 
				for (int i = 0; i < listOfFiles.length; i++) {

				if (listOfFiles[i].isFile()) {
					  fileName = new File(sourcePath + userid.toString()
							+ "/" + listOfFiles[i].getName());
					  
					  File saveDir = new File(faxTrash+destinationUserId);
						if (!saveDir.exists()) {
							logger.info("creating directory: " + faxTrash+destinationUserId);
							boolean result1 = saveDir.mkdir();
							if (result1) {
								logger.info("DIR created");
							}
						}
						faxTrash=faxTrash+destinationUserId+"/"+listOfFiles[i].getName();
						File destFile = new File(faxTrash);
						 
						if(!destFile.exists()){
						  FileUtils.moveFile(fileName, destFile);
						}
					  response = OCreate.outboundFaxCreate(faxid, "Requesting Clinical Information",  destFile.getAbsolutePath(), ""); //Enter a valid Fax Number and a path with name to file to send 
					JSONParser parser = new JSONParser();
					if(response!=null){
					JSONObject json1 = (JSONObject) parser.parse(response);
					
					   queueId = (String) json1.get("SendFaxQueueId");
					
					
				 	if (queueId.equals("-1"))   {
						result = "NO";
						
					}else{
						faxVo = new FaxVo();
						faxVo.setCreatedby(userid);
						faxVo.setStatus(1);
						faxVo.setCreatedon(createdOnTime);
						faxVo.setPatientid(destinationUserId);
						faxVo.setTofax(faxid);
						faxVo.setFromfax(faxNumber);
						faxVo.setOrganizationid(orgId);
						faxVo.setSendfaxqueueid(queueId);

						Integer efaxid = physicianManager.saveEFaxDetails(faxVo);
						result = "YES";
						AuditTrailVO dto = AuditTrailUtil
								.SaveAuditTrailDetails(efaxid, request,
										Constants.PATIENT_REQUEST_CONTENT, Constants.INSERT,
										"Clinical Information sent to patient");
						userManager.saveAuditTrails(dto);
						
					} 
					}
					if(fileName.exists()){
						
						fileName.delete();
					}
				 	
				}  
				 
				}
			}
			
			else {
				result = "DOC";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		 
		}

		return result;
	}
	
	
	@RequestMapping(value = "/administrator/sendFaxPopup.do", method = RequestMethod.GET)
	public @ResponseBody
	String uploadFaxInformations(@RequestParam Integer patientId,@RequestParam Integer requestId,
			@RequestParam Integer visitid,@RequestParam String faxid, HttpServletRequest request) {
		logger.info("Admin controller uploadFaxInformations() starts");

		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		Integer orgId = null;
		try {
			//update the patient request to outstanding to complete
			physicianManager.updatePatientRequestVo(requestId);
			
			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			Integer userid = user.getId();
			orgId = physicianManager.findPatientOrganizationId(patientId);
			
			String role=user.getRoleid();
			System.out.println("user.getRoleid()  : "+user.getRoleid());
			System.out.println("userid for doc upload : "+userid);
			if(role.equalsIgnoreCase("Patient")){
				patientId=userid;
			}
			
			String result = sendFax(userid, patientId,
					visitid, request,faxid,orgId);

			if (result.equals("YES")) {

				jsonResponse.setStatus("YES");
				jsonResponse.setMessage("Document faxed to the patient successfully.");

			} else if(result.equals("NO")) {
				jsonResponse.setStatus("NO");
				jsonResponse.setMessage("Invalid  fax number.");

			}else{
				
				jsonResponse.setStatus("DOC");
				jsonResponse.setMessage("Please select a file to fax.");
			}

			json = new Gson().toJson(jsonResponse);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error Processing uploadFaxInformations : "
					+ e.getMessage());
		}

		logger.info("Admin controller uploadFaxInformations() ends");
		return json;
	}
	

	@RequestMapping(value = "/administrator/patienttagentry.do", method = RequestMethod.GET)
	public @ResponseBody
	String updatePatientTag(@RequestParam String prescription,
			@RequestParam Integer visitid) {
		logger.info("updatePatientTag starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();

		try {
			VisitsVO patientRequestVO = null;

			patientRequestVO = new VisitsVO();
			patientRequestVO.setId(visitid);

			patientRequestVO.setTag(prescription);
			physicianManager.updateTag(visitid, prescription);

			jsonResponse.setMessage("Tag Update Successfully");

			json = "Tag Update Completed";
			// }
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			jsonResponse.setMessage("Tag Update Failed");

			json = "Tag Update Failed";
		}
		logger.info("updatePatientTag ends");
		return json;
	}

	@RequestMapping(value = "/administrator/patientnoteentry.do", method = RequestMethod.GET)
	public @ResponseBody
	String savePatientNote(@RequestParam String note,
			@RequestParam Integer visitid,@RequestParam String method,@RequestParam String type) {
		logger.info("savePatientNote starts" + method);
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();

		try {
			PatientPrivateNoteVO note2 = null;

			note2 = new PatientPrivateNoteVO();

			if(method.equals("note")){
				note2.setMethod("note");
			}else{
				note2.setMethod("tag");
			}
			if(type.equals("Document")){
				note2.setType("Document");
			}else{
				note2.setType("Visit");
			}
			note2.setNote(note);
			note2.setVisitid(visitid);
			physicianManager.savePrivateNote(note2);

			jsonResponse.setMessage(method+" saved successfully");

			json =method+" saved successfully";
			// }
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			jsonResponse.setMessage(method+" save failed");

			json = method+" save failed";
		}
		logger.info("savePatientNote ends");
		return json;
	}

	@RequestMapping(value = "/administrator/updatepatientnoteentry.do", method = RequestMethod.GET)
	public @ResponseBody
	String updatePatientNote(@RequestParam String note,
			@RequestParam Integer noteid,@RequestParam String method,@RequestParam String type) {
		logger.info("savePatientNote starts" + method);
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();

		try {
			PatientPrivateNoteVO note2 = null;

			note2 = new PatientPrivateNoteVO();

			if(method.equals("note")){
				note2.setMethod("note");
			}else{
				note2.setMethod("tag");
			}
			if(type.equals("Document")){
				note2.setType("Document");
			}else{
				note2.setType("Visit");
			}
			note2.setNote(note);
			note2.setId(noteid);
			physicianManager.updatePrivateNote(note, noteid);

			jsonResponse.setMessage(method+" updated successfully");

			json = method+" updated successfully";
			// }
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			jsonResponse.setMessage(method+"update failed");

			json = method+" update failed";
		}
		logger.info("savePatientNote ends");
		return json;
	}

	@RequestMapping(value = "/administrator/physicianencounters.do", method = RequestMethod.GET)
	public @ResponseBody
	String saveEncounters(@RequestParam String note,
			@RequestParam Integer visitid) {
		logger.info("saveEncounters starts" + note);
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();

		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userid = user.getId();

		try {
			EncounterVO note2 = null;

			note2 = new EncounterVO();

			note2.setEncounter(note);
			note2.setStatus(1);
			note2.setVisitid(visitid);
			note2.setUserid(userid);
			physicianManager.saveEncounter(note2);

			jsonResponse.setMessage("Encounter Saved Successfully");

			json = "Encounter Saved Successfully";
			// }
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			jsonResponse.setMessage("Tag Update Failed");

			json = "Encounter Saved Failed";
		}
		logger.info("saveEncounters ends");
		return json;
	}

	@RequestMapping(value = "/administrator/adminencounterdetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String adminEncounterDetails(@RequestParam Integer visitid) {
		logger.info("adminEncounterDetails starts" + visitid);
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();

		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userid = user.getId();

		List<EncounterVO> encounterVOs = null;
		try {
			encounterVOs = physicianManager.getEncountersDetails(visitid);

			if (encounterVOs != null && encounterVOs.size() > 0) {
				json = new Gson().toJson(encounterVOs);
			} else {
				json = new Gson().toJson(null);
			}
		} catch (Exception e) {

		}

		logger.info("adminEncounterDetails ends");
		return json;
	}

	// /administrator/updateencounters.do

	@RequestMapping(value = "/administrator/updateencounters.do")
	public String updateAdminEncounter(@RequestParam String encountername,
			@RequestParam Integer encounterid, ModelMap model) {
		logger.debug("deleteAdminEncounter");
		String viewPage = Constants.ADMIN_VIEW_ENCOUNTERS_VIEW;

		int regsiter = 1;

		try {

			adminOrganizationManager.updateAdminEncounter(encountername,
					encounterid);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		logger.info("deleteAdminEncounter ends");

		return viewPage;
	}

	@RequestMapping(value = "/administrator/editadminencounter.do", method = RequestMethod.GET)
	public @ResponseBody
	String editAdminEncounter(@RequestParam Integer encounterid) {
		logger.info("editAdminEncounter starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();

		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userid = user.getId();

		List<EncounterVO> encounterVOs = null;
		try {
			encounterVOs = physicianManager.editEncountersDetails(encounterid);

			if (encounterVOs != null && encounterVOs.size() > 0) {
				json = new Gson().toJson(encounterVOs);
			} else {
				json = new Gson().toJson(null);
			}
		} catch (Exception e) {

		}

		logger.info("editAdminEncounter ends");
		return json;
	}

	@RequestMapping(value = "/administrator/deleteadminencounter.do")
	public String deleteAdminEncounter(@RequestParam Integer encounterid,
			ModelMap model) {
		logger.debug("deleteAdminEncounter");
		String viewPage = Constants.ADMIN_VIEW_ENCOUNTERS_VIEW;

		int regsiter = 1;

		try {

			adminOrganizationManager.deleteAdminEncounter(encounterid);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		logger.info("deleteAdminEncounter ends");

		return viewPage;
	}

	@RequestMapping(value = "/administrator/checknoteentry.do", method = RequestMethod.GET)
	public @ResponseBody
	String checkNote(@RequestParam Integer visitid,@RequestParam String method,@RequestParam String type) {
		logger.info("checkNote starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		List<PatientPrivateNoteVO> patientPrivateNoteVOs = new ArrayList<PatientPrivateNoteVO>();
		try {
			/*
			 * PatientPrivateNoteVO note2 = null;
			 * 
			 * note2 = new PatientPrivateNoteVO();
			 * 
			 * note2.setNote(note); note2.setVisitid(visitid);
			 */

			patientPrivateNoteVOs = physicianManager.checkNote(visitid,method,type);
			if (patientPrivateNoteVOs != null) {
				json = new Gson().toJson(patientPrivateNoteVOs);
			} else {
				json = new Gson().toJson(null);
			}

			// }
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("checkNote ends");
		return json;
	}

	@RequestMapping(value = "/administrator/audittrails.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadAuditTrails(@RequestParam String userType,
			@RequestParam Integer personId) {
		logger.info("AdminController loadAuditTrails starts ");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();

		List<AdminSearchDateForm> adminSearchDateForms = null;

		try {
			adminSearchDateForms = new ArrayList<AdminSearchDateForm>();
			List<Object[]> auditDet = userManager
					.getAuditTrailDetails(personId);

			if (auditDet != null && auditDet.size() > 0) {

				for (Object[] obj : auditDet) {

					Date dd = (Date) obj[1];
					obj[1] = CommonUtils.parseStringFromDate(dd);
				}
				json = new Gson().toJson(auditDet);
			} else {
				json = new Gson().toJson(null);
			}

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("AdminController loadAuditTrails ends");
		return json;
	}

	@RequestMapping(value = "/administrator/physicianPracticeAdminStatus.do", method = RequestMethod.GET)
	public @ResponseBody
	void physicianPracticeAdminStatus(@RequestParam String selectedId) {
		logger.info("Admin controller physicianPracticeAdminStatus() starts");
		try {
			adminOrganizationManager
					.setPhysicianPracticeAdminStatus(selectedId);
			logger.info("Admin controller physicianPracticeAdminStatus() ends");
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
	}

	@RequestMapping(value = "/administrator/physicianRemovePracticeAdminStatus.do", method = RequestMethod.GET)
	public @ResponseBody
	void physicianRemovePracticeAdminStatus(@RequestParam String selectedId) {
		logger.info("Admin controller physicianRemovePracticeAdminStatus() starts");
		try {
			adminOrganizationManager
					.removePhysicianPracticeAdminStatus(selectedId);
			logger.info("Admin controller physicianRemovePracticeAdminStatus() ends");
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
	}

	@RequestMapping("/administrator/adminshare.do")
	public String adminShareHome(ModelMap model) {
		String viewPage = Constants.ADMIN_SHARE_HOME_VIEW;
		return viewPage;
	}

	@RequestMapping(value = "/administrator/admindetailsvisit.do", method = RequestMethod.GET)
	public @ResponseBody
	String adminDetailVisit(@RequestParam Integer visitid,@RequestParam Integer patientId, @RequestParam String searchDate, ModelMap model) {
		logger.debug("adminDetailVisit : " + visitid);
		String json = null;
		List<Object[]> physicianDto = null;
		List<Object[]> physicianDto1 = null;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userId = user.getId();
		String  role = user.getRoleid();
		if(role.equals("Patient")){
			patientId = userId;
		}
		
		
		try {
			System.out.println("Phy sel vid : "+visitid);
			// physicianDto = physicianManager.getPhysicianVisitById(visitid);
			if(visitid == 0){
				physicianDto = null;
			}else{ 
			physicianDto = physicianManager.getPhysicianVisitById(visitid,
					userId, "first",role,patientId,searchDate);
			}
			System.out.println("sel date : "+searchDate);
			physicianDto1 = physicianManager.getPhysicianVisitById(visitid,
					userId, "second",role,patientId,searchDate);
			Integer keyId = 0;
			for(Object[] obj:physicianDto1){
				
				keyId = (Integer) obj[2];
				if(keyId==null){
					
					keyId = 0;
				}
				if(keyId.equals(0)){
					
					obj[2] ="Dummy";
				}else{
					
					Date visitDate = physicianManager.getVisitDateByVisitId(keyId);
					obj[2] = visitDate;
				}
				
			}
			

			model.addAttribute("visit", physicianDto);
			model.addAttribute("documents", physicianDto1);
			json = new Gson().toJson(model);
			System.out.println("json : "+json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("adminDetailVisit ends");
		return json;
	}
	
	
	
	@RequestMapping(value = "/administrator/adminphysiciansearchdetailsvisit.do", method = RequestMethod.GET)
	public @ResponseBody
	String adminPhysicianSearchDetailsVisit(@RequestParam Integer visitid,@RequestParam Integer patientId, ModelMap model) {
		logger.debug("adminPhyDetailVisit : " + visitid);
		String json = null;
		List<Object[]> physicianDto = null;
		List<Object[]> physicianDto1 = null;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userId = user.getId();
		String  role = user.getRoleid();
		if(role.equals("Patient")){
			patientId = userId;
		}
		
		
		try {
			System.out.println("Phy sel vid : "+visitid);
			
			physicianDto1 = physicianManager.getSearchPhysicianVisitById(visitid,	userId);
			Integer keyId = 0;
			for(Object[] obj:physicianDto1){
				
				keyId = (Integer) obj[2];
				if(keyId==null){
					
					keyId = 0;
				}
				if(keyId.equals(0)){
					
					obj[2] ="Dummy";
				}else{
					
					Date visitDate = physicianManager.getVisitDateByVisitId(keyId);
					obj[2] = visitDate;
				}
				
			}
			

			//model.addAttribute("visit", physicianDto);
			model.addAttribute("documents", physicianDto1);
			json = new Gson().toJson(model);
			System.out.println("json : "+json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("adminDetailVisit ends");
		return json;
	}
	
	@RequestMapping(value = "/administrator/adminClinicalSearchPhysiciandetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String adminClinicalSearchPhysiciandetails(@RequestParam Integer visitid, ModelMap model) {
		logger.debug("adminPhyDetailVisit : " + visitid);
		String json = null;
		List<Object[]> physicianDto = null;
		List<Object[]> physicianDto1 = null;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userId = user.getId();
		String  role = user.getRoleid();
			
		
		try {
			System.out.println("Phy sel vid : "+visitid);
			
			physicianDto1 = physicianManager.getAdminSearchPhysicianVisitById(visitid);
			Integer keyId = 0;
			for(Object[] obj:physicianDto1){
				
				keyId = (Integer) obj[2];
				if(keyId==null){
					
					keyId = 0;
				}
				if(keyId.equals(0)){
					
					obj[2] ="Dummy";
				}else{
					
					Date visitDate = physicianManager.getVisitDateByVisitId(keyId);
					obj[2] = visitDate;
				}
				
			}
			

			//model.addAttribute("visit", physicianDto);
			model.addAttribute("documents", physicianDto1);
			json = new Gson().toJson(model);
			System.out.println("json : "+json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("adminDetailVisit ends");
		return json;
	}
	
	
	@RequestMapping(value = "/administrator/adminClinicalSearchPatientdetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String adminClinicalSearchPatientdetails(@RequestParam Integer visitid, ModelMap model) {
		logger.debug("adminPhyDetailVisit : " + visitid);
		String json = null;
		List<Object[]> physicianDto = null;
		List<Object[]> physicianDto1 = null;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userId = user.getId();
		String  role = user.getRoleid();
			
		
		try {
			System.out.println("Phy sel vid : "+visitid);
			
			physicianDto1 = physicianManager.getAdminSearchPatientVisitById(visitid);
			Integer keyId = 0;
			for(Object[] obj:physicianDto1){
				
				keyId = (Integer) obj[2];
				if(keyId==null){
					
					keyId = 0;
				}
				if(keyId.equals(0)){
					
					obj[2] ="Dummy";
				}else{
					
					Date visitDate = physicianManager.getVisitDateByVisitId(keyId);
					obj[2] = visitDate;
				}
				
			}
			

			//model.addAttribute("visit", physicianDto);
			model.addAttribute("documents", physicianDto1);
			json = new Gson().toJson(model);
			System.out.println("json : "+json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("adminDetailVisit ends");
		return json;
	}
	
	
	@RequestMapping(value = "/administrator/adminPhysicianClinicalSearchPatientdetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String adminPhysicianClinicalSearchPatientdetails(@RequestParam Integer patientid, ModelMap model) {
	
		String json = null;
		List<Object[]> physicianDto = null;
		List<Object[]> physicianDto1 = null;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userId = user.getId();
		String  role = user.getRoleid();
			
		
		try {
			
			
			physicianDto1 = physicianManager.getPhysicianSearchPatientVisitById(patientid, userId);
			Integer keyId = 0;
			for(Object[] obj:physicianDto1){
				
				keyId = (Integer) obj[2];
				if(keyId==null){
					
					keyId = 0;
				}
				if(keyId.equals(0)){
					
					obj[2] ="Dummy";
				}else{
					
					Date visitDate = physicianManager.getVisitDateByVisitId(keyId);
					obj[2] = visitDate;
				}
				
			}
			

			//model.addAttribute("visit", physicianDto);
			model.addAttribute("documents", physicianDto1);
			json = new Gson().toJson(model);
			System.out.println("json : "+json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("adminDetailVisit ends");
		return json;
	}
	@RequestMapping(value = "/administrator/adminClinicalSearchPhysicianDatedetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String adminClinicalSearchPhysicianDatedetails(@RequestParam String searchDate, ModelMap model) {
		
		String json = null;
		List<Object[]> physicianDto = null;
		List<Object[]> physicianDto1 = null;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userId = user.getId();
		String  role = user.getRoleid();
			
		
		try {
		
			physicianDto1 = physicianManager.getPhysicianSearchVisitById(userId,searchDate);
			Integer keyId = 0;
			for(Object[] obj:physicianDto1){
				
				keyId = (Integer) obj[2];
				if(keyId==null){
					
					keyId = 0;
				}
				if(keyId.equals(0)){
					
					obj[2] ="Dummy";
				}else{
					
					Date visitDate = physicianManager.getVisitDateByVisitId(keyId);
					obj[2] = visitDate;
				}
				
			}
			

			//model.addAttribute("visit", physicianDto);
			model.addAttribute("documents", physicianDto1);
			json = new Gson().toJson(model);
			System.out.println("json : "+json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("adminDetailVisit ends");
		return json;
	}
	
	
	@RequestMapping(value = "/administrator/adminClinicalSearchdetails.do", method = RequestMethod.GET)
	public @ResponseBody
	String adminClinicalSearchdetails(@RequestParam Integer visitid,@RequestParam Integer patientId, @RequestParam String searchDate, ModelMap model) {
		logger.debug("adminSearchdetailsvisit : " + visitid);
		String json = null;
		List<Object[]> physicianDto = null;
		List<Object[]> physicianDto1 = null;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Integer userId = user.getId();
		String  role = user.getRoleid();
		
		
		
		try {
			System.out.println("Phy sel vid : "+visitid);
		
			System.out.println("sel date : "+searchDate);
			physicianDto1 = physicianManager.getAdminClinicalSearchByDate(searchDate);
			Integer keyId = 0;
			for(Object[] obj:physicianDto1){
				System.out.println("obj1 : "+obj[1]);
				System.out.println("obj1 : "+obj[2]);
				keyId = (Integer) obj[2];
				if(keyId==null){
					
					keyId = 0;
				}
				if(keyId.equals(0)){
					
					obj[2] ="Dummy";
				}else{
					
					Date visitDate = physicianManager.getVisitDateByVisitId(keyId);
					obj[2] = visitDate;
				}
				
			}
			model.addAttribute("documents", physicianDto1);
			json = new Gson().toJson(model);
			System.out.println("json : "+json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("adminClinicalSearchdetails ends");
		return json;
	}
	
	

	// For PDF Search

	ArrayList co_ords = null;

	int[] areaToScan = null;

	PdfDecoder decodePdf = null;

	private File xmlOutputFile;
	private static String xmlOutputPath;

	private static boolean enableXML = false;
	private static boolean enableSTDout = true;

	private ArrayList<String> findText(String file_name, String textToFind) {
		createXMLFile(true, textToFind);

		co_ords = new ArrayList();

		pathLists = new ArrayList<>();
		File targetFile = new File(file_name);

		if (file_name.toLowerCase().endsWith(".pdf")) {
			decodeFile(file_name, textToFind);
		} else if (targetFile.isDirectory()) {
			// get list of files and check directory
			String[] files = targetFile.list();

			// make sure name ends with a deliminator for correct path later
			if (!file_name.endsWith(File.separator)) {
				file_name = file_name + File.separator;
			}

			// now work through all pdf files
			long fileCount = files.length;

			for (int i = 0; i < fileCount; i++) {
				if (enableSTDout) {
					logger.info("File " + i + " of " + fileCount + ' '
							+ files[i]);
				}

				if (files[i].toLowerCase().endsWith(".pdf")) {
					if (enableSTDout) {
						logger.info(file_name + files[i]);
					}
					decodeFile(file_name + files[i], textToFind);
				}
			}
		} else {
			System.err.println(file_name
					+ " is not a directory. Exiting program");
		}

		// close XML file
		createXMLFile(false, textToFind);

		return pathLists;
	}

	public void createXMLFile(boolean open, String textToFind) {
		if (enableXML) {
			if (open) {
				xmlOutputFile = new File(xmlOutputPath);

				if (xmlOutputFile.exists()) {
					xmlOutputFile.delete();
					try {
						xmlOutputFile.createNewFile();
					} catch (Exception e) {
						enableXML = false;
						System.err.println("Unable to create XML file: " + e
								+ '\n');
					}
				}

				if (enableXML) {
					try {
						PrintWriter outputStream = new PrintWriter(
								new FileWriter(xmlOutputFile));
						outputStream
								.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
						outputStream.println("<search>");
						outputStream.println("<term>" + textToFind + "</term>");
						outputStream.close();
					} catch (Exception e) {
						enableXML = false;
						System.err.print("Failed to write to XML file: " + e
								+ '\n');
					}
				}
			} else {
				try {
					PrintWriter outputStream = new PrintWriter(new FileWriter(
							xmlOutputFile, true));
					outputStream.println("</search>");
					outputStream.close();
				} catch (Exception e) {
					System.err.print("Exception creating closing XML file: "
							+ e + '\n');
				}
			}
		}
	}

	private void decodeFile(String file_name, String textToFind) {

		/*
		 * LogWriter.setupLogFile(""); LogWriter.log_name =
		 * "/mnt/shared/log.txt";
		 */

		createFileXMLElement(file_name, true);

		String pathlist = null;

		try {
			decodePdf = new PdfDecoder(false);
			decodePdf.setExtractionMode(PdfDecoder.TEXT); // extract just text
			PdfDecoder.init(true);

			if (enableSTDout) {
				pathlist = file_name;
				// pathLists.add(pathlist);
			}
			decodePdf.openPdfFile(file_name);
		} catch (PdfException e) {
			System.err.println("Ignoring " + file_name);
			System.err.println("Due to: " + e);
			createFileXMLElement(file_name, false);
			return;
		}

		if ((decodePdf.isEncrypted() && (!decodePdf.isPasswordSupplied()))
				&& (!decodePdf.isExtractionAllowed())) {
			logger.info("Encrypted settings");
			System.out
					.println("Please look at Viewer for code sample to handle such files");
			logger.info("Or get support/consultancy");
		} else {
			// page range
			int start = 1, end = decodePdf.getPageCount();

			try {
				for (int page = start; page <= end; page++) { // read pages

					if (enableSTDout) {
						logger.info("=========================");
						logger.info("Page " + page);
						logger.info("=========================");
					}

					// decode the page
					decodePdf.decodePage(page);

					PdfGroupingAlgorithms currentGrouping = decodePdf
							.getGroupingObject();
					if (currentGrouping != null) {

						int x1, y1, x2, y2;

						if (areaToScan == null) {
							PdfPageData currentPageData = decodePdf
									.getPdfPageData();
							x1 = currentPageData.getMediaBoxX(page);
							x2 = currentPageData.getMediaBoxWidth(page) + x1;

							y2 = currentPageData.getMediaBoxY(page);
							y1 = currentPageData.getMediaBoxHeight(page) + y2;
						} else {
							x1 = areaToScan[0];
							y1 = areaToScan[1];
							x2 = areaToScan[2];
							y2 = areaToScan[3];
						}
						// tell user
						if (enableSTDout) {
							logger.info("Scanning for text (" + textToFind
									+ ") rectangle (" + x1 + ',' + y1 + ' '
									+ x2 + ',' + y2 + ')');
						}

						float[] co_ords;

						try {
							co_ords = currentGrouping.findText(null, y2,
									new String[] { textToFind },
									SearchType.MUTLI_LINE_RESULTS);

							this.co_ords.add(co_ords);

						} catch (PdfException e) {
							decodePdf.closePdfFile();
							System.err.println("Ignoring " + file_name);
							System.err.println("Due to: " + e);
							createFileXMLElement(file_name, false);
							return;
						}

						if (co_ords == null) {
							if (enableSTDout) {
								logger.info("Text not found on page.");
							}
						} else {
							if (enableSTDout) {
								logger.info("Found " + (co_ords.length / 5)
										+ " on page.");
							}
							for (int i = 0; i < co_ords.length; i += 5) {
								if (enableSTDout) {

									System.out
											.println("file found file_name : "
													+ file_name);
									logger.info("file found pathlist : "
											+ pathlist);

									if (!pathLists.contains(file_name)) {
										pathLists.add(file_name);
									}

									logger.info("Text found at " + co_ords[i]
											+ ", " + co_ords[i + 1]);
								}
								createFindXMLElement(co_ords[i],
										co_ords[i + 1], page);
							}

						}
					}
				}

				// remove data once written out
				decodePdf.flushObjectValues(false);

			} catch (Exception e) {
				decodePdf.closePdfFile();
				System.err.println("Exception: " + e.getMessage());
				createFileXMLElement(file_name, false);
				return;
			}

			decodePdf.flushObjectValues(true); // flush any text data read

			if (enableSTDout) {
				logger.info("File read...");
			}

		}

		// Close file xml element
		createFileXMLElement(file_name, false);

		logger.info("size : " + pathLists.size());

		decodePdf.closePdfFile();
	}

	public void createFileXMLElement(String filePath, boolean open) {
		if (enableXML) {
			try {
				PrintWriter outputStream = new PrintWriter(new FileWriter(
						xmlOutputFile, true));
				if (open) {
					outputStream.println("<file>");
					outputStream.println("<path>" + filePath + "</path>");
				} else {
					outputStream.println("</file>");
				}
				outputStream.close();
			} catch (Exception e) {
				System.out.print("Creating new outputFile: " + e);
			}
		}
	}

	public void createFindXMLElement(float x, float y, int pageNo) {
		if (enableXML) {
			try {
				PrintWriter outputStream = new PrintWriter(new FileWriter(
						xmlOutputFile, true));
				outputStream.println("<found>");
				outputStream.println("<pageNo>" + pageNo + "</pageNo>");
				outputStream.println("<x>" + x + "</x>");
				outputStream.println("<y>" + y + "</y>");
				outputStream.println("</found>");
				outputStream.close();
			} catch (Exception e) {
				System.out.print("Creating new outputFile: " + e);
			}
		}
	}

}
