package com.bluehub.controller.login;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bluehub.manager.user.UserManager;
import com.bluehub.util.Constants;

@Controller
public class LoginController {
	private static Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private UserManager userManager;

	/**
	 * @param userManager
	 *            the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		String viewPage = Constants.LOGIN_VIEW;
		logger.debug("LoginController logout==>");
		if (session != null) {
			session.invalidate();
		}
		return viewPage;
	}

	@RequestMapping("/forgotpassword.do")
	public String forgotpassword() {
		String viewPage = Constants.FORGOT_PASSWORD_VIEW;
		logger.debug("LoginController forgotpassword==>");
		return viewPage;
	}

	/**
	 * redirect into admindashboard screen.
	 */
	@RequestMapping("/common/publicuserdashboard.do")
	public String admindashboard() {
		String viewPage = Constants.PUBLIC_DASHBOARD_VIEW;
		logger.debug("LoginController publicuserdashboard==>");
		return viewPage;
	}

}
