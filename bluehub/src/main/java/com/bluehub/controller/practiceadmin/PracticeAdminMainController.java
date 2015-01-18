package com.bluehub.controller.practiceadmin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bluehub.bean.admin.SearchPhysicianForm;
import com.bluehub.bean.user.UserProfile;
import com.bluehub.dao.secure.UserProfileDAOImpl;
import com.bluehub.manager.admin.AdminOrganizationManager;
import com.bluehub.manager.physician.PhysicianManager;
import com.bluehub.manager.practiceadmin.PracticeAdminManager;
import com.bluehub.manager.user.UserManager;
import com.bluehub.util.Constants;
import com.bluehub.vo.user.UserMappingVO;

@Controller
public class PracticeAdminMainController {

	public static Logger logger = Logger
			.getLogger(PracticeAdminMainController.class);

	@Autowired
	private AdminOrganizationManager adminOrganizationManager;

	public void setAdminOrganizationManager(AdminOrganizationManager adminOrganizationManager) {
		this.adminOrganizationManager = adminOrganizationManager;
	}

	@Autowired
	private PhysicianManager physicianManager;

	@Autowired
	private UserManager userManager;

	@Autowired
	private PracticeAdminManager practiceAdminManager;

	private UserProfileDAOImpl userProfileDAOImpl = new UserProfileDAOImpl();

	/**
	 * @return the userProfileDAOImpl
	 */
	public UserProfileDAOImpl getUserProfileDAOImpl() {
		return userProfileDAOImpl;
	}

	/**
	 * @param userProfileDAOImpl
	 *            the userProfileDAOImpl to set
	 */
	public void setUserProfileDAOImpl(UserProfileDAOImpl userProfileDAOImpl) {
		this.userProfileDAOImpl = userProfileDAOImpl;
	}

	/*
	 * @param practiceAdminManager the practiceAdminManager to set
	 */
	public void setPracticeAdminManager(
			PracticeAdminManager practiceAdminManager) {
		this.practiceAdminManager = practiceAdminManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setPhysicianManager(PhysicianManager physicianManager) {
		this.physicianManager = physicianManager;
	}

	@RequestMapping("/practiceadmin/home.do")
	public String physicianMain(ModelMap model, HttpServletRequest request) {
		logger.debug("PhysicianController physicianhome==>");
		boolean flag = false;
		String viewpage = null;
		String json = null;

		// Here we are changing user role from security context
		/************
		 * SecurityContext context = SecurityContextHolder.getContext(); Object
		 * principal = context.getAuthentication().getPrincipal(); Object
		 * credentials = context.getAuthentication().getCredentials();
		 * //GrantedAuthority[] authorities = new GrantedAuthority[1];
		 * 
		 * 
		 * 
		 * List<GrantedAuthority> authorities = new
		 * ArrayList<GrantedAuthority>(1); authorities.add(new
		 * GrantedAuthorityImpl("ROLE_PRACTICE_ADMINISTRATOR"));
		 * //authorities[0] = new GrantedAuthorityImpl("PracticeAdmin");
		 * 
		 * Authentication auth = new
		 * UsernamePasswordAuthenticationToken(principal, credentials,
		 * authorities);
		 * SecurityContextHolder.getContext().setAuthentication(auth);
		 ****************/

		HttpSession session = request.getSession(true);
		session.setAttribute("role", "PracticeAdmin");

		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Integer userid = user.getId();

		Integer uid = (Integer) session.getAttribute("practiceid");
		if (uid != null) {
			userid = uid;

		} else {
			session.setAttribute("practiceid", userid);
		}

		String userName = user.getUsername();
		String password = user.getPassword();
		String email = user.getEmail();

		flag = userManager.checkUserAdditionalInfo(userid);

		List<SearchPhysicianForm> searchPhysicianFormNew = new ArrayList<SearchPhysicianForm>();
		List<SearchPhysicianForm> searchPhysicianForm = null;
		viewpage = Constants.HOME_PRACTICE_ADMIN_VIEW;

		List<UserMappingVO> userMappingVO = practiceAdminManager
				.getPracticeAdminMap(userid);

		if (userMappingVO != null) {
			for (UserMappingVO usermap : userMappingVO) {

				searchPhysicianForm = practiceAdminManager
						.getPhysicianMappingRecord(userid,
								usermap.getOrganizationid(),
								usermap.getPracticeid());
				if (searchPhysicianForm != null) {
					searchPhysicianFormNew.add(searchPhysicianForm.get(0));
				}
			}

		}

		// json = new Gson().toJson(searchPhysicianFormNew);

		// Long totalUsers = userManager.getTotalUsers();
		model.addAttribute("userList", searchPhysicianForm);
		return viewpage;
	}

	@RequestMapping("/physician/loadphysiciandashboard.do")
	public String loadPhysicianDashboard(@RequestParam Integer id,
			ModelMap model) {
		logger.debug("PhysicianController physicianhome==>");
		boolean flag = false;
		String viewpage = null;
		UserProfile user = (UserProfile) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		user.setId(id);
		Integer userid = user.getId();

		user.setRoleid("Physician");
		flag = userManager.checkUserAdditionalInfo(userid);

		// Here we are changing user role from security context
		/******
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
		 **************/

		viewpage = Constants.HOME_PHYSICIAN_DASHBOARD;
		/*
		 * if(flag){ //additional info viewpage =
		 * Constants.PHYSICIAN_PERSONAL_DETAILS_VIEW; } else{ viewpage =
		 * Constants.HOME_PHYSICIAN_VIEW; }
		 */

		Long totalUsers = userManager.getTotalUsers("Patient");

		model.addAttribute("users", totalUsers);
		return viewpage;
	}

}
