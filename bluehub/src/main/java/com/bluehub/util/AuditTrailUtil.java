package com.bluehub.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bluehub.bean.user.UserProfile;
import com.bluehub.manager.user.UserManager;
import com.bluehub.vo.common.AuditTrailVO;

public   class AuditTrailUtil {
	
	private static Logger logger = Logger.getLogger(AuditTrailUtil.class);
	
	
	UserManager userManager;


	public UserManager getUserManager() {
		return userManager;
	}


	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	
	public static AuditTrailVO SaveAuditTrailDetails(Integer entityid,HttpServletRequest request,String entityname,Integer actionType,String logtext){
		
		UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AuditTrailVO dto = new AuditTrailVO();
		
		dto.setEntityid(entityid);
		
		dto.setCreatedon(new Date());
		dto.setIpaddress(request.getRemoteHost());
		
		dto.setUserid(user.getId());
		
		dto.setCreatedby(user.getId());
		
		dto.setLogtext(logtext);
		
		dto.setActiontype(actionType);
		
		dto.setEntityname(entityname);
		
		return dto;
		 
	}
	
	

}
