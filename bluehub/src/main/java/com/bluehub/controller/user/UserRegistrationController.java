package com.bluehub.controller.user;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bluehub.bean.user.ChangePassword;
import com.bluehub.bean.user.ForgotPassword;
import com.bluehub.bean.user.JsonResponse;
import com.bluehub.bean.user.UserForm;
import com.bluehub.bean.user.UserPhyForm;
import com.bluehub.bean.user.UserProfile;
import com.bluehub.exception.BlueHubBusinessException;
import com.bluehub.exception.BlueHubRuntimeException;
import com.bluehub.manager.admin.AdminOrganizationManager;
import com.bluehub.manager.user.UserManager;
import com.bluehub.util.Constants;
import com.bluehub.util.MailSupport;
import com.bluehub.vo.admin.AdminOrganizationVO;
import com.bluehub.vo.admin.AdminPracticeVO;
import com.bluehub.vo.admin.PatientMappingVO;
import com.bluehub.vo.admin.PhysicianMappingVO;
import com.bluehub.vo.user.RoleVO;
import com.bluehub.vo.user.UserVO;
import com.google.gson.Gson;

@Controller
public class UserRegistrationController {

	private static Logger logger = Logger
			.getLogger(UserRegistrationController.class);

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

	/**
	 * check if given email id exists or not.
	 * 
	 * @param userMail
	 *            as string
	 * 
	 * @return json response as string.
	 */
	@RequestMapping(value = "/checkUserEmail.do", method = RequestMethod.GET)
	public @ResponseBody
	String checkUserEmail(@RequestParam String userMail) {
		logger.info("UserController checkUserEmail() starts");
		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		int emailExist = 0;
		try {
			if (StringUtils.isNotBlank(userMail)) {
				emailExist = userManager.getEmailExist(userMail);
				if (emailExist == 0) {
					jsonResponse.setMessage(Constants
							.getPropertyValue("email_not_exist"));
					jsonResponse.setStatus("No");
				} else {
					jsonResponse.setMessage(Constants
							.getPropertyValue("email_already_exist"));
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

	// /
	@RequestMapping(value = "/getPhyUserRegisterForm.do", method = RequestMethod.POST)
	public String getPhyRegistrationForm(
			@ModelAttribute("userPhyForm") UserPhyForm userRegistrationForm,
			SessionStatus sessionStatus, ModelMap model,
			HttpServletRequest request) {

		logger.info("UserController getPhyUserRegisterForm() starts");

		String viewPage = Constants.USER_REGISTRATION_SUCCESS_VIEW;
		int regsiter = 1;
		try {
			Timestamp timestamp = new Timestamp(new Date().getTime());
			// RoleVO roleDto = new RoleVO();
			List<RoleVO> roleDto = userManager
					.findRoleVoByRoleId(userRegistrationForm.getUserTypePhy());

			UserVO userDto = new UserVO();
			userDto.setUserName(userRegistrationForm.getPhyUserEmail());
			userDto.setDateCreated(timestamp);
			userDto.setEmailId(userRegistrationForm.getPhyUserEmail());
			userDto.setSecurityQuestion(userRegistrationForm
					.getPhyRecoveryQuestion());
			userDto.setSecurityAnswer(userRegistrationForm
					.getPhyRecoveryAnswer());
			String password = userRegistrationForm.getPhyPassword();
			if (StringUtils.isNotBlank(password)) {
				password = Constants.getGeneratedPassword(password);
			}
			userDto.setPassword(password);

			userDto.setRole(roleDto.get(0));
			userDto.setStatus(1);

			Integer userid = userManager.saveUserRegistration(userDto);

			Timestamp createdOnTime = new Timestamp(new Date().getTime());

			PhysicianMappingVO mappingVo = null;

			mappingVo = new PhysicianMappingVO();

			Integer organizationId;
			Integer practiceId;
			if (userid != null) {
				organizationId = userRegistrationForm.getSelectOrganization();
				practiceId = userRegistrationForm.getSelectPractice();
				mappingVo.setUserid(userid);
				mappingVo.setOrganizationid(organizationId);
				mappingVo.setPracticeid(practiceId);

				mappingVo.setCreatedon(createdOnTime);
				mappingVo.setStatus(1);
			}
			userManager.svaePhysicianmappingVo(mappingVo);
			sessionStatus.setComplete();

			String adminEmail = Constants.getMailPropertyValue("adminemail");
			String adminName = Constants.getMailPropertyValue("adminname");

			String serverUrl = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();

			MailSupport.sendMailRegisterationBluehub(
					userRegistrationForm.getPhyUserEmail(),
					userRegistrationForm.getPhyUserEmail(), adminEmail,
					adminName, serverUrl);

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
		logger.info("UserController getUserRegistrationForm() ends");
		return viewPage;
	}

	@RequestMapping(value = "/getUserRegisterForm.do", method = RequestMethod.POST)
	public String getUserRegistrationForm(
			@ModelAttribute("userRegistrationForm") UserForm userRegistrationForm,
			SessionStatus sessionStatus, ModelMap model,
			HttpServletRequest request) {

		logger.info("UserController getUserRegistrationForm() starts");

		String viewPage = Constants.USER_REGISTRATION_SUCCESS_VIEW;
		int regsiter = 1;
		try {
			Timestamp timestamp = new Timestamp(new Date().getTime());
			// RoleVO roleDto = new RoleVO();

			List<RoleVO> roleDto = userManager
					.findRoleVoByRoleId(userRegistrationForm.getUserTypePat());

			UserVO userDto = new UserVO();
			userDto.setUserName(userRegistrationForm.getUserEmail());
			userDto.setDateCreated(timestamp);
			userDto.setEmailId(userRegistrationForm.getUserEmail());
			userDto.setSecurityQuestion(userRegistrationForm
					.getRecoveryQuestion());
			userDto.setSecurityAnswer(userRegistrationForm.getRecoveryAnswer());
			String password = userRegistrationForm.getPassword();
			if (StringUtils.isNotBlank(password)) {
				password = Constants.getGeneratedPassword(password);
			}
			userDto.setPassword(password);

			userDto.setRole(roleDto.get(0));
			userDto.setStatus(1);

			Integer userid = userManager.saveUserRegistration(userDto);

			PatientMappingVO mappingVo = null;

			mappingVo = new PatientMappingVO();

			Integer organizationId;
			if (userid != null) {
				organizationId = userRegistrationForm
						.getSelectPhyOrganization();
				// practiceId=userRegistrationForm.getSelectPractice();
				mappingVo.setUserid(userid);
				mappingVo.setOrganizationid(organizationId);
				mappingVo.setStatus(1);
			}
			userManager.savePatientMappingVo(mappingVo);

			// selectPhyOrganization
			sessionStatus.setComplete();

			String adminEmail = Constants.getMailPropertyValue("adminemail");
			String adminName = Constants.getMailPropertyValue("adminname");

			String serverUrl = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();

			MailSupport.sendMailRegisterationBluehub(
					userRegistrationForm.getUserEmail(),
					userRegistrationForm.getUserEmail(), adminEmail, adminName,
					serverUrl);

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
		logger.info("UserController getUserRegistrationForm() ends");
		return viewPage;
	}

	/**
	 * redirect into the user registration common screen.
	 * 
	 * @param model
	 *            as ModelMap
	 * 
	 * @return to the userRegistration view page.
	 */
	@RequestMapping(value = "/userregistration.do", method = RequestMethod.GET)
	public String showUserRegistration(ModelMap model)
			throws BlueHubRuntimeException {
		logger.info("UserController checkUserChildEmail() starts");
		String viewPage = Constants.USER_REGISTRATION_VIEW;
		/*
		 * List<UserGroupVO> userGroups = null; UserRegistrationForm
		 * userRegistrationForm = new UserRegistrationForm(); try { userGroups =
		 * userManager.getUserGroups(); if (userGroups != null &&
		 * !userGroups.isEmpty()) { userRegistrationForm.setGroups(userGroups);
		 * model.addAttribute(userRegistrationForm); } } catch (Exception e) {
		 * logger.error(Constants.LOG_ERROR + e.getMessage());
		 * model.addAttribute("EXCEPTION",
		 * Constants.getPropertyValue(Constants.SERVICE_ERROR)); viewPage =
		 * "error"; }
		 */
		// logger.info("UserController checkUserChildEmail() ends");
		return viewPage;
	}

	@RequestMapping(value = "/getForgetQuestion.do", method = RequestMethod.GET)
	public @ResponseBody
	String getForgetQuestion(@RequestParam String userMail) {
		logger.info("UserController getForgetQuestion() starts.");
		String mailId = null; // gets the user mail id
		UserVO registrationFormVO = null;
		String viewPage = Constants.FORGOT_PASSWORD_VIEW;

		String json = null;
		JsonResponse jsonResponse = new JsonResponse();
		try {

			// Check wheather mailid is not null
			if (StringUtils.isNotBlank(userMail)) {
				// checks the entered mail id exists or not
				registrationFormVO = userManager
						.getUserRegistrationDataByMailId(userMail);
			}// ends(Check whether mailid is not null)

			jsonResponse.setMessage(registrationFormVO.getSecurityQuestion());

			json = new Gson().toJson(jsonResponse);

			// Check whether registrationFormVO is not null
			// if (registrationFormVO != null) {
			// forgotPassword.setRecoveryQuestion(registrationFormVO.getSecurityQuestion());
			// }// ends(Check whether registrationFormVO is not null)
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("UserController getForgetQuestion() ends.");
		return json;
	}

	@RequestMapping(value = "/resetUserPassword.do", method = RequestMethod.GET)
	public @ResponseBody
	String resetUserPassword(@RequestParam Integer userId) {
		logger.info("UserController resetUserPassword() starts.");
		UserVO registrationFormVO = null;
		String encodedPassword = null; // gets the encoded password
		String newPassword = null; // gets the randomly generated password
		String json = null;
		String shortMessage = null; // to get exception message
		String longMessage = null; // to get the exception message
		JsonResponse jsonResponse = new JsonResponse();
		try {

			registrationFormVO = userManager
					.getUserRegistrationByUserId(userId);

			if (registrationFormVO != null) {
				newPassword = Constants.getRandomPassword();
				// Check whether newpassord entered is not null
				if (newPassword != null) {
					Md5PasswordEncoder ms = new Md5PasswordEncoder();
					encodedPassword = ms.encodePassword(newPassword, null);
					registrationFormVO.setPassword(encodedPassword);
					// updates the new password
					userManager.updateForgotPassword(registrationFormVO,
							newPassword);
					longMessage = Constants
							.getPropertyValue(Constants.MAILSEND_INBOX);

					jsonResponse
							.setMessage("Password Reseted, New Password Sent To User Registered Email");
					json = new Gson().toJson(jsonResponse);
				}

			}

		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("UserController resetUserPassword() ends.");
		return json;
	}

	//

	@RequestMapping(value = "/checkForgetQuestion.do", method = RequestMethod.POST)
	public String checkForgetQuestion(
			@ModelAttribute("forgotPassword") ForgotPassword forgotPassword,
			ModelMap model) {
		logger.info("UserController checkForgetQuestion() starts.");
		String viewPage = Constants.LOGIN_VIEW;
		String forgotPasswordViewPage = Constants.FORGOT_PASSWORD_VIEW;
		UserVO registrationFormVO = null;
		String encodedPassword = null; // gets the encoded password
		String newPassword = null; // gets the randomly generated password
		String emailId = null; // gets the user name
		String answer = null; // gets the recover answer
		String shortMessage = null; // to get exception message
		String longMessage = null; // to get the exception message
		try {
			emailId = forgotPassword.getUsername();
			answer = forgotPassword.getRecoveryAnswer();

			// Check whether emailid and answer is not null
			if (StringUtils.isNotBlank(emailId)
					&& StringUtils.isNotBlank(answer)) {
				// checks the entered answer is correct
				registrationFormVO = userManager.checkAnswerCorrect(emailId,
						answer);
			}// ends(Check whether emailid and answer is not null)

			// random password will be generated and encoded
			// Check whether registrationFormVO is not null
			if (registrationFormVO != null) {
				newPassword = Constants.getRandomPassword();
				// Check whether newpassord entered is not null
				if (newPassword != null) {
					Md5PasswordEncoder ms = new Md5PasswordEncoder();
					encodedPassword = ms.encodePassword(newPassword, null);
					registrationFormVO.setSecurityAnswer(answer);
					registrationFormVO.setPassword(encodedPassword);
					// updates the new password
					userManager.updateForgotPassword(registrationFormVO,
							newPassword);
					longMessage = Constants
							.getPropertyValue(Constants.MAILSEND_INBOX);
					model.addAttribute("EXCEPTION", longMessage);
					// viewPage = forgotPasswordViewPage;
					forgotPassword.setUsername("");
					forgotPassword.setRecoveryQuestion("");
					model.addAttribute("forgotPassword", forgotPassword);
				}// end(Check whether newpassord entered is not null)
			}/* end(Check whether registrationFormVO is not null) */else {
				forgotPassword.setRecoveryQuestion(forgotPassword
						.getRecoveryQuestion());
				forgotPassword.setUsername(emailId);
				model.addAttribute("forgotPassword", forgotPassword);
			}
		} catch (BlueHubBusinessException be) {
			shortMessage = be.getMessage();
			longMessage = Constants.getPropertyValue(shortMessage);
			model.addAttribute("EXCEPTION", longMessage);
			viewPage = forgotPasswordViewPage;
			logger.error(Constants.LOG_ERROR + be.getMessage());
		} catch (BlueHubRuntimeException rte) {
			logger.error(Constants.LOG_ERROR + rte.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
		}
		logger.info("UserController checkForgetQuestion() ends.");
		return viewPage;
	}

	@RequestMapping(value = "/changepassword.do", method = RequestMethod.GET)
	public String changePassword(ModelMap model) {
		logger.info("UserController changePassword() starts.");
		String viewPage = Constants.CHANGE_PASSWORD_VIEW;
		Integer userid = 0; // to get user id
		try {
			ChangePassword changePassword = new ChangePassword();
			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			userid = user.getId();
			model.addAttribute("userid", userid);
			model.addAttribute(changePassword);
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
			logger.info("UserController changePassword() ends.");
		}
		return viewPage;
	}

	@RequestMapping(value = "/editprofile.do", method = RequestMethod.GET)
	public String editProfile(ModelMap model) {
		logger.info("UserController editProfile() starts.");
		String viewPage = Constants.PHYSICIAN_PERSONAL_DETAILS_VIEW;

		return viewPage;
	}

	@RequestMapping(value = "/editpatientprofile.do", method = RequestMethod.GET)
	public String editPatientProfile(ModelMap model) {
		logger.info("UserController editPatientProfile() starts.");
		String viewPage = Constants.PATIENT_PERSONAL_DETAILS_VIEW;

		return viewPage;
	}

	/**
	 * This method gets new password and inserts them in the DB
	 * 
	 * @param changePassword
	 *            as bean class
	 * @param model
	 *            as modelmap
	 * @return changepassword as viewpage
	 * 
	 */
	@RequestMapping(value = "/changePasswordSubmit.do", method = RequestMethod.POST)
	public String changePasswordSubmit(
			@ModelAttribute("changePassword") ChangePassword changePassword,
			ModelMap model) {
		logger.info("UserController changePasswordSubmit() starts.");
		String viewPage = Constants.CHANGE_PASSWORD_VIEW;
		Integer userId = 0; // to get user id
		String currentPassword = null; // to get current password
		String longMessage = null; // to get exception message
		UserVO registrationFormVO = null;
		try {
			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			userId = user.getId();
			currentPassword = changePassword.getNewpassword();
			Timestamp createdOnTime = new Timestamp(new Date().getTime());

			// it encodes the password before update
			// Check whether current password is not null
			if (StringUtils.isNotBlank(currentPassword)
					&& StringUtils.isNotEmpty(currentPassword)) {
				currentPassword = Constants
						.getGeneratedPassword(currentPassword);
			}// end( Check whether current password is not null)

			// Check whether current password is not null
			if (currentPassword != null) {
				// it gets the user details using user id
				registrationFormVO = userManager
						.getUserRegistrationByUserId(userId);
				registrationFormVO.setPassword(currentPassword);
				// registrationFormVO.setUpdatedOn(createdOnTime);
				// registrationFormVO.setUpdatedBy(""+userId);

				// it updates the new password
				userManager.updateUserRegistration(registrationFormVO);
				longMessage = Constants
						.getPropertyValue(Constants.PASSWORD_CHANGE);
				model.addAttribute("EXCEPTION", longMessage);
				viewPage = Constants.CHANGE_PASSWORD_VIEW;
				model.addAttribute("changepassword", changePassword);
			} // end(Check whether currentpassword is not null)
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
			model.addAttribute("EXCEPTION",
					Constants.getPropertyValue(Constants.SERVICE_ERROR));
			viewPage = "error";
			logger.info("UserController changePasswordSubmit() ends.");
		}
		return viewPage;
	}

	@RequestMapping(value = "/checkUserPassword.do", method = RequestMethod.GET)
	public @ResponseBody
	String checkUserPassword(@RequestParam String userPassword, ModelMap model) {
		logger.info("UserController checkUserPassword() starts.");
		String json = null;
		String longMessage = null; // to get exception message
		int userId = 0; // to get the user id
		JsonResponse jsonResponse = new JsonResponse();
		boolean emailExist = false;
		try {
			UserProfile user = (UserProfile) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			userId = user.getId();
			if (userPassword != null) { // Check whether userpassword is not
										// null
				// it checks the given id and password matches or not
				emailExist = userManager.checkPasswordExist(userId,
						userPassword);
				if (emailExist == false) {
					longMessage = Constants
							.getPropertyValue(Constants.PASSWORD_MISMATCH);
					model.addAttribute("EXCEPTION", longMessage);
					jsonResponse.setMessage(Constants
							.getPropertyValue("password_mismatch"));
					jsonResponse.setStatus("No");
				} else {
					jsonResponse.setStatus("Yes");
				}
				json = new Gson().toJson(jsonResponse);
			}// end(Check whether userpassword is not null)
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		logger.info("UserController checkUserPassword() ends.");
		return json;
	}

	@RequestMapping(value = "/loadorganization.do", method = RequestMethod.GET)
	public @ResponseBody
	String loadOrganization() {
		logger.info("UserController loadOrganization() starts");
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
		logger.info("UserController loadOrganization() ends");
		return json;
	}

	@RequestMapping(value = "/loadpractice.do", method = RequestMethod.GET)
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

}
