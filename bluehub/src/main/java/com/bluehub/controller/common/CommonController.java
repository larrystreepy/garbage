package com.bluehub.controller.common;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bluehub.manager.physician.PhysicianManager;
import com.bluehub.manager.user.UserManager;
import com.bluehub.util.CommonUtils;
import com.bluehub.util.Constants;
import com.bluehub.vo.common.RequestInfoOfPatientVO;

@Controller
public class CommonController {
	public static Logger logger = Logger.getLogger(CommonController.class);

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

	@RequestMapping("/common/views.do")
	public String testValue(ModelMap model) {

		return "success";
	}

	@RequestMapping("/common/viewShareRequestInfo.do")
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

}
