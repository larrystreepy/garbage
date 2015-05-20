package com.bluehub.controller.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bluehub.util.Constants;

@Controller
public class HomeController {

	private static Logger logger = Logger.getLogger(HomeController.class);

	@RequestMapping("/home.do")
	public String homeRedirect(ModelMap model, HttpServletRequest request) {
		logger.info("HomeController homeRedirect() starts");
		return Constants.HOME_VIEW;
	}

	@RequestMapping("/login.do")
	public String loginRedirect() {
		String viewPage = Constants.LOGIN_VIEW;
		logger.info("HomeController loginRedirect() starts");
		return viewPage;
	}

	@RequestMapping("/successlogin.do")
	public String successloginRedirect(ModelMap model) {
		String viewPage = Constants.LOGIN_VIEW;
		logger.info("HomeController successloginRedirect() starts");
		model.addAttribute("EXCEPTION",
				Constants.getPropertyValue(Constants.REGISTER_SUCCESS));
		return viewPage;
	}

	@RequestMapping("/services.do")
	public String serviceRedirect() {
		logger.info("HomeController serviceRedirect() starts");
		return Constants.SERVICES_VIEW;
	}

	@RequestMapping("/event.do")
	public String eventRedirect() {
		logger.info("HomeController eventRedirect() starts");
		return Constants.EVENT_VIEW;
	}

	@RequestMapping("/resources.do")
	public String resourcesRedirect() {
		logger.info("HomeController resourcesRedirect() starts");
		return Constants.RESOURCES_VIEW;
	}

	@RequestMapping("/contact.do")
	public String contactRedirect() {
		logger.info("HomeController contactRedirect() starts");
		return Constants.CONTACT_VIEW;
	}

	@RequestMapping("/child.do")
	public String childRedirect() {
		String viewPage = Constants.HOME_CHILD_VIEW;
		logger.info("HomeController childRedirect() starts");
		return viewPage;
	}

	@RequestMapping("/parent.do")
	public String parentRedirect() {
		String viewPage = Constants.HOME_PARENT_VIEW;
		logger.info("HomeController parentRedirect() starts");
		return viewPage;
	}

	@RequestMapping("/bcba.do")
	public String bcbaRedirect() {
		String viewPage = Constants.HOME_BCBA_VIEW;
		logger.info("HomeController bcbaRedirect() starts");
		return viewPage;
	}

	@RequestMapping("/bcaba.do")
	public String bcabaRedirect() {
		String viewPage = Constants.HOME_BCABA_VIEW;
		logger.info("HomeController bcabaRedirect() starts");
		return viewPage;
	}

	@RequestMapping("/student.do")
	public String studentRedirect() {
		String viewPage = Constants.HOME_STUDENT_VIEW;
		logger.info("HomeController studentRedirect() starts");
		return viewPage;
	}

	@RequestMapping("/loginfailed.do")
	public String loginfailedRedirect(ModelMap model) {
		String viewPage = Constants.LOGIN_VIEW;
		logger.info("HomeController loginfailedRedirect() starts");
		model.addAttribute("EXCEPTION",
				Constants.getPropertyValue(Constants.LOGINFAILED));
		return viewPage;
	}

	// @RequestMapping("/sessionexpired.do")
	// public String seesionexpired(ModelMap model) {
	// logger.info("HomeController seesionexpired() starts");
	// return "sessionexpired";
	// }

	@RequestMapping("/sessionexpired.do")
	public String seesionexpired(ModelMap model) {
		logger.info("HomeController seesionexpired() starts");
		model.addAttribute("EXCEPTION",
				"Your session was expired, Please login again.");
		return Constants.LOGIN_VIEW;
	}

	@RequestMapping("/family.do")
	public String familyRedirect(ModelMap model) {
		logger.info("HomeController familyRedirect() starts");
		String viewPage = Constants.FAMILY_VIEW;
		return viewPage;
	}

	@RequestMapping("/community.do")
	public String communityRedirect(ModelMap model) {
		logger.info("HomeController communityRedirect() starts");
		String viewPage = Constants.COMMUNITY_VIEW;
		return viewPage;
	}

	@RequestMapping("/loginnotactive.do")
	public String loginnotactive(HttpSession session, ModelMap model) {
		String viewPage = "redirect:/loginfail.do";
		logger.info("HomeController loginnotactive() starts");
		SecurityContextHolder.clearContext();
		return viewPage;
	}

	@RequestMapping("/loginfail.do")
	public String loginfail(HttpSession session, ModelMap model) {
		String viewPage = Constants.LOGIN_VIEW;
		logger.info("HomeController loginfail() starts");
		model.addAttribute("EXCEPTION",
				Constants.getPropertyValue(Constants.login_activated));
		return viewPage;
	}

}
