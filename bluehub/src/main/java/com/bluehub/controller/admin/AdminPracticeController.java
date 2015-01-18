/**
 * 
 */
package com.bluehub.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bluehub.bean.user.JsonResponse;
import com.bluehub.bean.user.UserProfile;
import com.bluehub.manager.admin.AdminPracticeManager;
import com.bluehub.manager.user.UserManager;
import com.bluehub.util.CommonUtils;
import com.bluehub.util.Constants;
import com.bluehub.vo.admin.AdminPracticeVO;
import com.google.gson.Gson;

@Controller
public class AdminPracticeController {
	private static Logger logger = Logger
			.getLogger(AdminPracticeController.class);

	@Autowired
	private UserManager userManager;

	/**
	 * @param userManager
	 *            the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Autowired
	private AdminPracticeManager adminPracticeManager;

	public void setAdminPracticeManager(
			AdminPracticeManager adminPracticeManager) {
		this.adminPracticeManager = adminPracticeManager;
	}

	@RequestMapping(value = "/administrator/adminpractice.do", method = RequestMethod.GET)
	public String adminPractice(ModelMap model) {
		logger.debug("adminPracticeController");
		String viewPage = Constants.PRACTICE_ADMIN_VIEW;
		return viewPage;
	}

	@RequestMapping(value = "/administrator/saveadminpractice.do")
	public String saveAdminPractice(@RequestParam String practicename,
			Integer orgid, @RequestParam String address,
			@RequestParam String city, @RequestParam String state,
			@RequestParam String zipcode, ModelMap model) {

		logger.info("saveadminpractice : " + orgid);
		String viewPage = Constants.PRACTICE_ADMIN_VIEW;

		int regsiter = 1;
		int userid;
		try {
			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			userid = user.getId();
			AdminPracticeVO practiceDto = new AdminPracticeVO();
			practiceDto.setPracticename(practicename.trim());
			practiceDto.setAddress(address.trim());
			practiceDto.setCity(city.trim());
			practiceDto.setState(state.trim());
			practiceDto.setZipcode(zipcode.trim());
			practiceDto.setOrganizationid(orgid);
			practiceDto.setStatus(1);
			practiceDto.setCreatedon(new Date());
			practiceDto.setCreatedby(userid);
			adminPracticeManager.saveAdminPractice(practiceDto);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		logger.info("adminsaveadminpractice.do ends");

		return viewPage;
	}

	@RequestMapping(value = "/administrator/deleteadminpractice.do")
	public String deleteAdminPractice(@RequestParam Integer practiceid,
			ModelMap model) {
		logger.debug("adminPracticeController");
		String viewPage = Constants.PRACTICE_ADMIN_VIEW;

		int regsiter = 1;

		try {

			/*
			 * AdminPracticeVO practiceDto = new AdminPracticeVO();
			 * practiceDto.setId(orgid);
			 * adminPracticeManager.deleteAdminPractice(practiceDto);
			 */
			adminPracticeManager.deleteAdminPractice(practiceid);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		logger.info("adminPracticeController ends");

		return viewPage;
	}

	@RequestMapping(value = "/administrator/editadminpractice.do", method = RequestMethod.GET)
	public @ResponseBody
	String editAdminPractice(@RequestParam Integer orgid, ModelMap model) {
		logger.debug("editAdminPractice : " + orgid);
		String json = null;
		AdminPracticeVO practiceDto = null;
		try {
			practiceDto = adminPracticeManager.geAdminPracticeVOById(orgid);
			practiceDto.setOrganizationname(userManager
					.getOrganizationName(practiceDto.getOrganizationid()));

			if (practiceDto.getCity() == null) {
				practiceDto.setCity("");
			}
			if (practiceDto.getAddress() == null) {
				practiceDto.setAddress("");
			}
			if (practiceDto.getZipcode() == null) {
				practiceDto.setZipcode("");
			}
			if (practiceDto.getState() == null) {
				practiceDto.setState("");
			}

			json = new Gson().toJson(practiceDto);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			json = new Gson().toJson(Constants
					.getPropertyValue(Constants.SERVICE_ERROR));
		}
		logger.info("editadminPractice ends");

		return json;
	}

	@RequestMapping(value = "/administrator/updateadminpractice.do")
	public String updateAdminPractice(
			@RequestParam(value = "practicename") String name,
			@RequestParam(value = "practiceid") Integer orgid,
			@RequestParam(value = "address") String address,
			@RequestParam(value = "city") String city,
			@RequestParam(value = "state") String state,
			@RequestParam(value = "zipcode") String zipcode, ModelMap model) {
		logger.debug("updateAdminPractice starts");
		String viewPage = Constants.PRACTICE_ADMIN_VIEW;
		int regsiter = 1;
		int userid;
		try {
			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			userid = user.getId();
			AdminPracticeVO practiceDto = new AdminPracticeVO();

			practiceDto = adminPracticeManager.findPracticeById(orgid);

			practiceDto.setPracticename(name.trim());
			practiceDto.setAddress(address.trim());
			practiceDto.setCity(city.trim());
			practiceDto.setState(state.trim());
			practiceDto.setZipcode(zipcode.trim());
			// practiceDto.setId(orgid);
			practiceDto.setStatus(1);
			practiceDto.setUpdatedon(new Date());
			practiceDto.setUpdatedby(userid);
			adminPracticeManager.updateAdminPractice(practiceDto);

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		logger.info("updateAdminPractice ends");

		return viewPage;
	}

	@RequestMapping(value = "/administrator/adminpracticelist.do", method = RequestMethod.GET)
	public @ResponseBody
	String adminPracticeList(HttpServletRequest request, ModelMap model) {
		logger.info("adminPracticeList starts");
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

			Long count = 0L;
			List<AdminPracticeVO> adminPracticeVOs = adminPracticeManager
					.getAdminPractice(paramsMap);

			count = adminPracticeManager.getAdminPracticeCount(paramsMap);

			List<AdminPracticeVO> adminPracticeVOs2 = new ArrayList<AdminPracticeVO>();
			AdminPracticeVO adPracticeVO = null;
			for (AdminPracticeVO adminPracticeVO2 : adminPracticeVOs) {
				adPracticeVO = new AdminPracticeVO();

				adPracticeVO.setId(adminPracticeVO2.getId());
				adPracticeVO.setZipcode(adminPracticeVO2.getZipcode());
				adPracticeVO.setState(adminPracticeVO2.getState());
				adPracticeVO.setCity(adminPracticeVO2.getCity());
				adPracticeVO.setAddress(adminPracticeVO2.getAddress());
				adPracticeVO
						.setPracticename(adminPracticeVO2.getPracticename());
				adPracticeVO.setOrganizationname(userManager
						.getOrganizationName(adminPracticeVO2
								.getOrganizationid()));

				if (adminPracticeVO2.getAddress() == null) {
					adPracticeVO.setAddress("");
				}
				if (adminPracticeVO2.getCity() == null) {
					adPracticeVO.setCity("");
				}
				if (adminPracticeVO2.getZipcode() == null) {
					adPracticeVO.setZipcode("");
				}
				if (adminPracticeVO2.getState() == null) {
					adPracticeVO.setState("");
				}

				adminPracticeVOs2.add(adPracticeVO);
			}

			if (adminPracticeVOs != null) {

				List<HashMap> searchReportArrayList = new ArrayList<HashMap>();
				int reworkCount = 0;
				for (AdminPracticeVO form : adminPracticeVOs) {
					HashMap searchReportMap = new HashMap();

					searchReportMap.put("Organization", userManager
							.getOrganizationName(form.getOrganizationid()));
					searchReportMap.put("Practice", form.getPracticename());

					searchReportMap.put("Address", form.getAddress());
					searchReportMap.put("City", form.getCity());
					searchReportMap.put("State", form.getState());
					searchReportMap.put("Zipcode", form.getZipcode());

					searchReportMap.put("id", form.getId());

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

			json = new Gson().toJson(adminPracticeVOs2);
			// }
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("adminPracticeList ends");
		return finalJson;
	}

	@RequestMapping(value = "/administrator/adminaudittrial.do", method = RequestMethod.GET)
	public String adminAuditTrial(ModelMap model) {
		logger.debug("adminPracticeController");
		String viewPage = Constants.AUDITTRIAL_ADMIN_VIEW;
		return viewPage;
	}

}
