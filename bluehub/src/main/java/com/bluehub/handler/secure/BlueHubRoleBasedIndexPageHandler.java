package com.bluehub.handler.secure;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.bluehub.util.Constants;


public class BlueHubRoleBasedIndexPageHandler  extends SimpleUrlAuthenticationSuccessHandler {
	private static Logger logger = Logger.getLogger
		     (BlueHubRoleBasedIndexPageHandler.class);
	public Map<String,String>  rolebasedURLMapping;
	
	public Map<String, String> getRolebasedURLMapping() {
		return rolebasedURLMapping;
	}

	public void setRolebasedURLMapping(Map<String, String> rolebasedURLMapping) {
		this.rolebasedURLMapping = rolebasedURLMapping;
	}

		@Override
		 public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

		      Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		      logger.debug("onAuthenticationSuccess getRoleName====>"+roles);		      
		      if (roles.contains(Constants.ROLE_PATIENT)) {
			         getRedirectStrategy().sendRedirect(request, response, rolebasedURLMapping.get(Constants.ROLE_PATIENT));
			      } else if (roles.contains(Constants.ROLE_PHYSICIAN)) {
				         getRedirectStrategy().sendRedirect(request, response, rolebasedURLMapping.get(Constants.ROLE_PHYSICIAN));
				  }			     
			      else if (roles.contains(Constants.ROLE_ADMINISTRATOR)) {
				         getRedirectStrategy().sendRedirect(request, response, rolebasedURLMapping.get(Constants.ROLE_ADMINISTRATOR));
				  }
			      else if (roles.contains(Constants.ROLE_PRACTICE_ADMINISTRATOR)) {
				         getRedirectStrategy().sendRedirect(request, response, rolebasedURLMapping.get(Constants.ROLE_PRACTICE_ADMINISTRATOR));
				  }
			     /* else if (roles.contains(Constants.ROLE_ACADEMICIAN)) {
				         getRedirectStrategy().sendRedirect(request, response, rolebasedURLMapping.get(Constants.ROLE_ACADEMICIAN));
				  }
			      else if (roles.contains(Constants.ROLE_OFFICE)) {
				         getRedirectStrategy().sendRedirect(request, response, rolebasedURLMapping.get(Constants.ROLE_OFFICE));
				  }
			      else if (roles.contains(Constants.ROLE_CUSTODIAN)) {
				         getRedirectStrategy().sendRedirect(request, response, rolebasedURLMapping.get(Constants.ROLE_CUSTODIAN));
				  }
			      else if (roles.contains(Constants.ROLE_AGENCY)) {
				         getRedirectStrategy().sendRedirect(request, response, rolebasedURLMapping.get(Constants.ROLE_AGENCY));
				  }else if (roles.contains(Constants.ROLE_INVITE)) {
				         getRedirectStrategy().sendRedirect(request, response, rolebasedURLMapping.get(Constants.ROLE_INVITE));
				  }else if (roles.contains(Constants.ROLE_PUBLIC)) {
				         getRedirectStrategy().sendRedirect(request, response, rolebasedURLMapping.get(Constants.ROLE_PUBLIC));
				  }
				  else if (roles.contains(Constants.ROLE_NOTACTIVE)) {
				         getRedirectStrategy().sendRedirect(request, response, rolebasedURLMapping.get(Constants.ROLE_NOTACTIVE));
				  }*/
			      else {
			         super.onAuthenticationSuccess(request, response, authentication);
			         return;
			      }
		      
		   }
		}