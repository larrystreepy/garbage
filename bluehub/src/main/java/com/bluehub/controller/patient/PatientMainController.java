package com.bluehub.controller.patient;

import com.bluehub.bean.admin.AdminSearchDateForm;
import com.bluehub.bean.common.UserDetails;
import com.bluehub.bean.user.JsonResponse;
import com.bluehub.bean.user.UserProfile;
import com.bluehub.bean.user.phyPersonalForm;
import com.bluehub.manager.admin.AdminOrganizationManager;
import com.bluehub.manager.physician.PhysicianManager;
import com.bluehub.manager.user.UserManager;
import com.bluehub.util.*;
import com.bluehub.vo.admin.FaxVo;
import com.bluehub.vo.common.AuditTrailVO;
import com.bluehub.vo.common.PatientDocument;
import com.bluehub.vo.common.ShareClinicalInfo;
import com.bluehub.vo.physician.UserPersonalInfoVO;
import com.bluehub.vo.physician.VisitsVO;
import com.bluehub.vo.user.PatientRequestVO;
import com.bluehub.vo.user.UserVO;
import com.google.gson.Gson;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Controller
public class PatientMainController {

    public static final Logger logger = Logger.getLogger(PatientMainController.class);

    private final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private static final String NULL_JSON = new Gson().toJson(null);

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
     * @param userManager the userManager to set
     */
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @RequestMapping("/patient/patientPersonalDetails.do")
    public String physicianPersonal(ModelMap model) {
        logger.debug("PhysicianController physicianhome==>" + Constants.PATIENT_PERSONAL_DETAILS_VIEW);
        return Constants.PATIENT_PERSONAL_DETAILS_VIEW;
    }

    @RequestMapping(value = "/patient/getPatientAddInfo.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String getPatientAddInfo(ModelMap model) {

        UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = user.getId();
        String json = newJson(physicianManager.getPatientAdditionalInformation(userId));
        logger.info("patientController getPatientAddInfo ends");
        return json;
    }

    @RequestMapping("/patient/home.do")
    public String patientHome(ModelMap model) {
        UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = user.getId();

        Object[] obj = userManager.getPatientVisitRecords(userId);
        String note = null;
        int visitid = 0;

        if (obj != null) {

            visitid = Integer.parseInt(obj[3] + "");

            note = userManager.getPrivateNoteByVisitAndPatientId(visitid, userId);

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

        List<ShareClinicalInfo> shareList = adminOrganizationManager.getListOfPatientShareVo(userId);
        int approved = 0;

        if (shareList != null) {

            int count = shareList.size();

            for (ShareClinicalInfo dto : shareList) {
                if (dto.getRequestStatus().equals(Constants.APPROVED)) {
                    approved++;
                }
            }

            int pending = count - approved;

            model.addAttribute("total", count);
            model.addAttribute("approved", approved);
            model.addAttribute("pending", pending);

            Integer faxcount = adminOrganizationManager.getPatientFaxRequestCount(userId);

            model.addAttribute("faxtotal", faxcount);
        }

        String viewPage = userManager.checkUserAdditionalInfo(uid)
                ? Constants.PATIENT_PERSONAL_DETAILS_VIEW
                : Constants.HOME_PATIENT_VIEW;

        logger.debug("PatientController patienthome==>" + viewPage);
        return viewPage;
    }

    @RequestMapping("/patient/patientsearch.do")
    public String patientSearch(ModelMap model) {

        logger.debug("patientSearch");
        return Constants.SEARCH_PATIENT_VIEW;
    }

    @RequestMapping("/patient/patientshare.do")
    public String patientShare(ModelMap model) {

        logger.debug("patientShare");
        return Constants.SHARE_PATIENT_VIEW;
    }

    @RequestMapping("/patient/patientsharetab.do")
    public String patientShareTab(ModelMap model) {

        logger.debug("patientShare");
        return Constants.TAB_SHARE_PATIENT_VIEW;
    }

    @RequestMapping("/patient/patienttag.do")
    public String patientTag(ModelMap model) {

        logger.debug("patienttag");
        return Constants.TAG_PATIENT_VIEW;
    }

    @RequestMapping("/patient/patientprivatenote.do")
    public String patientPrivateNote(ModelMap model) {

        logger.debug("patientprivatenote");
        return Constants.PRIVATENOTE_PATIENT_VIEW;
    }

    @RequestMapping("/patient/patientupload.do")
    public String patientUpload(ModelMap model) {

        logger.debug("patientUpload");
        return Constants.UPLOAD_PATIENT_VIEW;
    }

    @RequestMapping("/patient/patientrequest.do")
    public String patientRequest(ModelMap model) {

        logger.debug("patientRequest");
        return Constants.REQUEST_PATIENT_VIEW;
    }

    @RequestMapping("/patient/patientrequestclinicalinformation.do")
    public String patientRequestClinicalInformation(ModelMap model) {

        logger.debug("patientRequest");
        return Constants.REQUEST_PATIENT_VIEWS;
    }

    @RequestMapping("/patient/patientview.do")
    public String patientView(ModelMap model) {

        logger.debug("patientView");
        return Constants.VIEW_PATIENT_VIEW;
    }

	/* Added For Search In Patient */

    @RequestMapping(value = "/patient/adminorganizations.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String loadAdminOrganization() {
        logger.info("loadAdminOrganization starts");
        String json = NULL_JSON;

        try {
            json = new Gson().toJson(adminOrganizationManager.getAdminOrganizaions());
        } catch (Exception e) {
            logger.error(Constants.LOG_ERROR + e.getMessage());
        }
        logger.info("loadAdminOrganization ends");
        return json;
    }

    @RequestMapping(value = "/patient/loadpractice.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String loadPractices(@RequestParam Integer orgid) {
        logger.info("PatientMainController loadPractices() starts==>");
        String json = NULL_JSON;

        try {
            json = newJson(adminOrganizationManager.getPracticeList(orgid));
        } catch (Exception e) {
            logger.error(Constants.LOG_ERROR + e.getMessage(), e);
        }
        logger.info("PatientMainController loadPractices() ends==>");
        return json;
    }

    @RequestMapping(value = "/patient/adminphysiciandetails.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String loadPhysicianDetails(@RequestParam String searchphysician,
                                @RequestParam String orgid, @RequestParam String practiceid) {
        logger.info("adminphysiciandetails starts");
        String json = NULL_JSON;

        try {
            json = newJson(userManager.getPhysicianDetails(searchphysician, orgid, practiceid));
        } catch (Exception e) {
            logger.error(Constants.LOG_ERROR + e.getMessage(), e);
        }
        logger.info("adminphysiciandetails ends");
        return json;
    }

    @RequestMapping(value = "/patient/adminsearchkeywordvisitrecords.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String loadSearchKeywordVisitRecords(@RequestParam String keyword) {
        logger.info("PatientController loadTagVisitRecords starts ");
        String json = NULL_JSON;

        try {
            List<AdminSearchDateForm> adminSearchDateForms = new ArrayList<>();
            List<VisitsVO> physicianVisitdetails = physicianManager.getKeywordVisitDetails(keyword);

            for (VisitsVO physicianVO : physicianVisitdetails) {
                AdminSearchDateForm adminSearchDateForm = new AdminSearchDateForm();

                adminSearchDateForm.setPatientname(userManager.getUserName(physicianVO.getPatientid()));
                adminSearchDateForm.setPhysicianname(userManager.getUserName(physicianVO.getPhysicianid()));
                adminSearchDateForm.setPatientdob(userManager.getDob(physicianVO.getPhysicianid()));
                adminSearchDateForm.setDateofvisit(physicianVO.getDateofvisit());
                adminSearchDateForm.setReasonforvisit(physicianVO.getReasonforvisit());
                adminSearchDateForm.setVisitdate(CommonUtils.parseStringFromDate(physicianVO.getDateofvisit()));
                adminSearchDateForm.setDob(CommonUtils.parseStringFromDate(userManager.getDob(physicianVO.getPatientid())));
                adminSearchDateForms.add(adminSearchDateForm);
            }

            json = newJson(adminSearchDateForms);

        } catch (Exception e) {
            logger.error(Constants.LOG_ERROR + e.getMessage(), e);
        }
        logger.info("PatientController loadTagVisitRecords ends");
        return json;
    }

    @RequestMapping(value = "/patient/patientviewdetails.do", method = RequestMethod.GET)
    public String loadPatientVisitDetails(ModelMap model) {
        logger.info("loadPatientVisitDetails starts");

        try {
            List<AdminSearchDateForm> adminSearchDateForms = new ArrayList<>();

            for (VisitsVO physicianVO : physicianManager.getKeywordVisitDetails("s")) {
                AdminSearchDateForm adminSearchDateForm = new AdminSearchDateForm();

                adminSearchDateForm.setPatientname(userManager.getUserName(physicianVO.getPatientid()));
                adminSearchDateForm.setPhysicianname(userManager.getUserName(physicianVO.getPhysicianid()));
                adminSearchDateForm.setPatientdob(userManager.getDob(physicianVO.getPhysicianid()));
                adminSearchDateForm.setDateofvisit(physicianVO.getDateofvisit());
                adminSearchDateForm.setReasonforvisit(physicianVO.getReasonforvisit());

                model.addAttribute("patientname" + userManager.getUserName(physicianVO.getPatientid()));
                model.addAttribute("dob" + userManager.getDob(physicianVO.getPatientid()));
                model.addAttribute("dateofvisit" + physicianVO.getDateofvisit());
                model.addAttribute("reasonforvisit" + physicianVO.getReasonforvisit());
                // model.addAttribute("patientname"+userManager.getUserName(physicianVO.getPatientid()));
                adminSearchDateForm.setVisitdate(CommonUtils.parseStringFromDate(physicianVO.getDateofvisit()));
                adminSearchDateForm.setDob(CommonUtils.parseStringFromDate(userManager.getDob(physicianVO.getPatientid())));

                adminSearchDateForms.add(adminSearchDateForm);
            }

            model.addAttribute("adminSearchDateForms", adminSearchDateForms);

        } catch (Exception e) {
            logger.error(Constants.LOG_ERROR + e.getMessage());
        }
        logger.info("loadPatientVisitDetails ends");

        return Constants.PATIENT_VISITDETAILS_VIEW;
    }

    // Mail


    @RequestMapping(value = "/patient/patientsearchorganizationinfo.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String patientSearchOrganizationInfo(@RequestParam Integer orgId, HttpServletRequest request) {
        logger.info("patientSearchPhysicianInfo starts");

        JsonResponse jsonResponse = new JsonResponse();

        try {
            UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Integer patientId = user.getId();
            String fax = physicianManager.getOrganizationIdForPatient(orgId, patientId);

            jsonResponse.setMessage(fax != null ? fax : "");
        } catch (Exception e) {
            logger.error(Constants.LOG_ERROR + e.getMessage(), e);
            jsonResponse.setMessage("");
        }

        logger.info("patientSearchPhysicianInfo Ends");
        return new Gson().toJson(jsonResponse);
    }

    @RequestMapping(value = "/patient/patientsearchphysicianinfoinfo.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String patientSearchPhysicianInfo(@RequestParam Integer physicianid, HttpServletRequest request) {
        logger.info("patientSearchPhysicianInfo starts");
        JsonResponse jsonResponse = new JsonResponse();

        try {
            Object[] obj = physicianManager.getPhysicianDetails(physicianid);

            if (obj != null) {
                jsonResponse.setMessage(obj[0] + "");
                jsonResponse.setStatus(obj[1] + "");

            } else {
                jsonResponse.setMessage("");
                jsonResponse.setStatus("");
            }
        } catch (Exception e) {
            logger.error(Constants.LOG_ERROR + e.getMessage());
            jsonResponse.setMessage("");
        }

        logger.info("patientSearchPhysicianInfo Ends");
        return new Gson().toJson(jsonResponse);
    }

    @RequestMapping(value = "/patient/patientrequestinfo.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String savePatientRequest(@RequestParam String physicianid, HttpServletRequest request) {
        logger.info("savePatientRequest starts");
        JsonResponse jsonResponse = new JsonResponse();
        UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = user.getId();

        try {
            PatientRequestVO patientRequestVO = new PatientRequestVO();

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

        } catch (Exception e) {
            logger.error(Constants.LOG_ERROR + e.getMessage(), e);
            jsonResponse.setMessage("Request Failed");
        }
        logger.info("savePatientRequest ends");
        return new Gson().toJson(jsonResponse);
    }


    @RequestMapping(value = "/patient/patientreqclinicalinfotoorg.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String patientreqclinicalinfotoorg(@RequestParam Integer orgId, @RequestParam String fax, ModelMap model,
                                       HttpServletRequest request) throws ParseException {
        logger.info("patientreqclinicalinfotoorg starts");
        JsonResponse jsonResponse = new JsonResponse();
        UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = user.getId();

        Object[] obj = physicianManager.getPatientPersonalDetailsForEfax(userId);
        String name = String.valueOf(obj[0]);
        Date dob = (Date) obj[1];
        String address = String.valueOf(obj[2]);

        String filePath;
        try {
            filePath = getPatientRequestClinicalInformationTemplate(request, name, dob, address, userId);
        } catch (Exception e) {
            logger.error("Failed to get request form", e);
            return NULL_JSON;
        }

        if (logger.isDebugEnabled()) logger.debug("filePath : " + filePath);

        JSONObject faxResponse = FaxSupport.sendFax(fax, filePath);
        String queueId = (String) faxResponse.get("SendFaxQueueId");

        if (!queueId.equals("-1")) {
            auditFax(request, userId, fax, orgId, queueId, "organization");
        } else {
            jsonResponse.setMessage((String) faxResponse.get("message"));
        }

        logger.info("patientreqclinicalinfotoorg ends");
        return new Gson().toJson(jsonResponse);
    }

    @RequestMapping(value = "/patient/patientreqclinicalinfotophysician.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String patientreqclinicalinfotophysician(@RequestParam String mail, @RequestParam String fax,
                                             @RequestParam String physicianid, ModelMap model,
                                             HttpServletRequest request)
            throws ParseException {

        logger.info("patientreqclinicalinfotophysician starts");

        JsonResponse jsonResponse = new JsonResponse();
        UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = user.getId();

        Integer organizationId = physicianManager.findPatientOrganizationId(userId);

        Object[] obj = physicianManager.getPatientPersonalDetailsForEfax(userId);
        String name = String.valueOf(obj[0]);
        Date dob = (Date) obj[1];
        String address = String.valueOf(obj[2]);

        String filePath;
        try {
            filePath = getPatientRequestClinicalInformationTemplate(request, name, dob, address, userId);
        } catch (Exception e) {
            logger.error("Failed to get request form", e);
            return NULL_JSON;
        }

        JSONObject faxResponse = FaxSupport.sendFax(fax, filePath);
        String queueId = (String) faxResponse.get("SendFaxQueueId");

        if (!queueId.equals("-1")) {
            auditFax(request, userId, fax, organizationId, queueId, "physician");

            PatientRequestVO patientRequestVO = new PatientRequestVO();

            patientRequestVO.setPatientid(userId);
            patientRequestVO.setPhysicianid(Integer.parseInt(physicianid));
            patientRequestVO.setEmailid("");
            patientRequestVO.setMailstatus(0);

            Integer id = userManager.savePatientRequestVo(patientRequestVO);

            AuditTrailVO dto1 = AuditTrailUtil.SaveAuditTrailDetails(id,
                    request, Constants.PATIENT_DOCUMENTS, Constants.INSERT,
                    "Requesting For Clinical Documents");

            userManager.saveAuditTrails(dto1);

        } else {
            jsonResponse.setMessage((String) faxResponse.get("message"));
        }

        boolean validEmail = EmailValidator.validate(mail);

        if (validEmail) {
            String serverUrl = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

            MailSupport.sendPatientRequestMail(mail, userManager.getUserName(userId), serverUrl);
        }

        logger.info("patientreqclinicalinfotophysician ends");
        return new Gson().toJson(jsonResponse);
    }

    @RequestMapping(value = "/patient/patientrequestmailinfo.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String savePatientMailRequest(@RequestParam String mailid, ModelMap model,
                                  HttpServletRequest request, @RequestParam String fax) throws ParseException {
        logger.info("savePatientMailRequest starts");

        if (logger.isDebugEnabled())
        {
            logger.debug(String.format("emailid='%s' fax='%s'", mailid, fax));
        }

        String json = "";
        UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = user.getId();

        if (!fax.equals("")) {
            Integer organizationId = physicianManager.findPatientOrganizationId(userId);
            Object[] obj = physicianManager.getPatientPersonalDetailsForEfax(userId);

            String name = String.valueOf(obj[0]);
            Date dob = (Date) obj[1];
            String address = String.valueOf(obj[2]);

            String filePath;
            try {
                filePath = getPatientRequestClinicalInformationTemplate(request, name, dob, address, userId);
            } catch (Exception e) {
                logger.error("Failed to construct template", e);
                return NULL_JSON;
            }

            if (logger.isDebugEnabled()) logger.debug("Fax file path: " + filePath);

            JSONObject faxResponse = FaxSupport.sendFax(fax, filePath);
            String queueId = (String) faxResponse.get("SendFaxQueueId");
            json = (String) faxResponse.get("message");

            if (logger.isDebugEnabled()) logger.debug("faxResponse: " + json + " queueId=" + queueId);

            if (!queueId.equals("-1")) {
                auditFax(request, userId, fax, organizationId, queueId, "physician");
            }
        }

        if (!mailid.isEmpty() && userManager.getUserName(userId) != null) {
            boolean validEmail = EmailValidator.validate(mailid);
            boolean haveJson = !json.isEmpty();

            if (logger.isDebugEnabled()) logger.debug("Send mail: mailid='" + mailid + "' valid=" + validEmail);

            if (validEmail) {
                String serverUrl = request.getScheme() + "://"
                        + request.getServerName() + ":"
                        + request.getServerPort() + "/" + request.getContextPath();

                if (logger.isDebugEnabled()) logger.debug("serverUrl=" + serverUrl);

                MailSupport.sendPatientRequestMail(mailid, userManager.getUserName(userId), serverUrl);
                json = json + " " + Constants.getPropertyValue(Constants.MAIL_SENT_SUCCESS);

                AuditTrailVO dto = AuditTrailUtil
                        .SaveAuditTrailDetails(0, request,
                                Constants.PATIENT_DOCUMENTS, Constants.NON_DB,
                                "Requesting For Clinical Information through physician email");

                userManager.saveAuditTrails(dto);

            } else {
                logger.info("error mail");
                if (haveJson) json += ", ";
                json = json + "Invalid EMail Id";
            }
        } else {
            if (!json.isEmpty()) {
                json = json + " ";
            }
        }

        logger.info("patientrequestmailinfo ends");
        return json;
    }

    @RequestMapping(value = "/patient/savePatientPersonalDetails.do", method = RequestMethod.POST)
    public String getPhysicianPersonalDetails(
            @ModelAttribute("physicianPersonalForm") phyPersonalForm physicianPersonalForm,
            SessionStatus sessionStatus, ModelMap model) {

        logger.info("PhysicianMainController physicianPersonalForm() starts");

        String viewPage = Constants.HOME_PATIENT_VIEW;
        try {
            Timestamp timestamp = new Timestamp(new Date().getTime());

            UserPersonalInfoVO userPersonalInfoDto = new UserPersonalInfoVO();
            userPersonalInfoDto.setFirstname(physicianPersonalForm.getFirstname());
            userPersonalInfoDto.setMiddlename(physicianPersonalForm.getMiddlename());
            userPersonalInfoDto.setLastname(physicianPersonalForm.getLastname());
            userPersonalInfoDto.setSsn(physicianPersonalForm.getSsn());

            userPersonalInfoDto.setContact_number(physicianPersonalForm.getContactNo());
            userPersonalInfoDto.setStreet(physicianPersonalForm.getStreet());
            userPersonalInfoDto.setCity(physicianPersonalForm.getCity());
            userPersonalInfoDto.setZip(physicianPersonalForm.getZip());
            userPersonalInfoDto.setStreet(physicianPersonalForm.getStreet());
            if (StringUtils.isNotBlank(physicianPersonalForm.getDob())) {
                userPersonalInfoDto.setDob(CommonUtils.parseDateFromString(physicianPersonalForm.getDob()));
            }
            if (StringUtils.isNotBlank(physicianPersonalForm.getInsurance_eff_date())) {
                userPersonalInfoDto.setInsurance_eff_date(CommonUtils.parseDateFromString(physicianPersonalForm.getInsurance_eff_date()));
            }
            if (StringUtils.isNotBlank(physicianPersonalForm.getInsurance_exp_date())) {
                userPersonalInfoDto.setInsurance_exp_date(CommonUtils.parseDateFromString(physicianPersonalForm.getInsurance_exp_date()));
            }
            userPersonalInfoDto.setPolicy_number(physicianPersonalForm.getPolicy_number());
            userPersonalInfoDto.setCreatedon(timestamp);

            UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Integer userid = user.getId();
            userPersonalInfoDto.setUserid(userid);

            physicianManager.savePatientPersonalDetails(userPersonalInfoDto);
            sessionStatus.setComplete();

            model.addAttribute("successMsg", Constants.getPropertyValue(Constants.REGISTER_SUCCESS));
            model.addAttribute("redirectMsg", Constants.getPropertyValue(Constants.redirect_msg));
            model.addAttribute("regsiter", 1);
        } catch (Exception e) {
            logger.error(Constants.LOG_ERROR + e.getMessage());
            model.addAttribute("EXCEPTION", Constants.getPropertyValue(Constants.SERVICE_ERROR));
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
            userPersonalInfoDto.setFirstname(physicianPersonalForm.getFirstname());
            userPersonalInfoDto.setMiddlename(physicianPersonalForm.getMiddlename());
            userPersonalInfoDto.setLastname(physicianPersonalForm.getLastname());
            userPersonalInfoDto.setSsn(physicianPersonalForm.getSsn());

            userPersonalInfoDto.setContact_number(physicianPersonalForm.getContactNo());
            userPersonalInfoDto.setStreet(physicianPersonalForm.getStreet());
            userPersonalInfoDto.setCity(physicianPersonalForm.getCity());
            userPersonalInfoDto.setZip(physicianPersonalForm.getZip());
            if (StringUtils.isNotBlank(physicianPersonalForm.getDob())) {
                userPersonalInfoDto.setDob(CommonUtils.parseDateFromString(physicianPersonalForm.getDob()));
            }
            if (StringUtils.isNotBlank(physicianPersonalForm.getInsurance_eff_date())) {
                userPersonalInfoDto.setInsurance_eff_date(CommonUtils.parseDateFromString(physicianPersonalForm.getInsurance_eff_date()));
            }
            if (StringUtils.isNotBlank(physicianPersonalForm.getInsurance_exp_date())) {
                userPersonalInfoDto.setInsurance_exp_date(CommonUtils.parseDateFromString(physicianPersonalForm.getInsurance_exp_date()));
            }
            userPersonalInfoDto.setPolicy_number(physicianPersonalForm.getPolicy_number());
            userPersonalInfoDto.setUpdatedon(timestamp);

            UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Integer userid = user.getId();

            userPersonalInfoDto.setUserid(userid);
            userPersonalInfoDto.setId(physicianPersonalForm.getHdnPKID());

            physicianManager.updatePersonalDetails(userPersonalInfoDto);
            sessionStatus.setComplete();

            model.addAttribute("successMsg", Constants.getPropertyValue(Constants.REGISTER_SUCCESS));
            model.addAttribute("redirectMsg", Constants.getPropertyValue(Constants.redirect_msg));
            model.addAttribute("regsiter", regsiter);

            Object[] obj = null;

            Integer userId = user.getId();

            obj = userManager.getPatientVisitRecords(userId);
            String note = null;
            int visitid = 0;

            if (obj != null) {

                visitid = Integer.parseInt(obj[3] + "");

                note = userManager.getPrivateNoteByVisitAndPatientId(visitid, userId);

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

            List<ShareClinicalInfo> shareList = adminOrganizationManager.getListOfPatientShareVo(userId);
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

        UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
    public
    @ResponseBody
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
    public
    @ResponseBody
    String getPatientAllDocuments(@RequestParam Integer patientId,
                                  ModelMap model) {

        UserProfile user = (UserProfile) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Integer userid = user.getId();

        String json = null;
        String orgName = null;
        List<Object[]> documentVO = null;
        try {
            if (patientId > 0) {
                logger.info("patientId " + patientId);
                documentVO = physicianManager.getAllDocumentsByUserId(patientId);
                orgName = physicianManager.getOrganizationNameByPatientId(patientId);

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
    public
    @ResponseBody
    String getPatientShareDocuments(@RequestParam Integer patientId,
                                    ModelMap model) {

        UserProfile user = (UserProfile) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Integer userid = user.getId();

        String json = null;
        String orgName = null;
        List<Object[]> documentVO = null;
        try {
            if (patientId > 0) {
                logger.info("patientId " + patientId);
                documentVO = physicianManager.getShareDocumentsByUserId(patientId);
                orgName = physicianManager.getOrganizationNameByPatientId(patientId);

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
    public
    @ResponseBody
    String getPatientShareVisitDetails(@RequestParam Integer type,
                                       @RequestParam Integer patientId, @RequestParam String visitDate,
                                       ModelMap model) {
        logger.debug("PatientMainController pa==> ");
        String json = null;
        List<Object[]> phyVisitsDto = null;
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
    public
    @ResponseBody
    String sendSharePatientMail(@RequestParam Integer physicianId,
                                @RequestParam Integer patientId, @RequestParam String shareId,
                                @RequestParam String phyMailId, @RequestParam String subject,
                                @RequestParam String bodyContent, HttpServletRequest request) {
        logger.debug("PatientMainController pa==> ");
        logger.info("phyMailId => " + phyMailId);
        String phyEmail = null;
        String result = "";

        try {
            if (!phyMailId.isEmpty()) {
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
                    bodyContent, serverUrl, patientId, shareId, userVo);

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
    public
    @ResponseBody
    String getVisitDetailsforShare(@RequestParam Integer visitId, ModelMap model) {
        logger.debug("PatientMainController pa==> ");
        String json = null;
        List<Object[]> phyVisitsDto = null;
        try {

            UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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
    public
    @ResponseBody
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


    public void fnmoveDocuments(Integer count, HttpServletRequest request, Integer patientId, Integer shareId) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        String filepath = Constants.getPlatformProperyValue(Constants.FAREFILE_UPLOAD_PATH);

        filepath = filepath + patientId + "/";

        for (int j = 0; j < count; j++) {

            String fileName = request.getParameter("checked1_" + j);

            if (fileName != null && !fileName.equals("0")) {

                Integer checkid = checkFileStatus(fileName, patientId);
                if (logger.isDebugEnabled()) logger.debug("checkid : " + checkid);

                if (checkid <= 0) {
                    filepath = filepath + "" + fileName;

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

    public void fnmoveDocuments1(Integer count, HttpServletRequest request, Integer patientId, Integer shareId) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        String filepath = Constants.getPlatformProperyValue(Constants.FAREFILE_UPLOAD_PATH);

        filepath = filepath + patientId + "/";

        for (int j = 0; j < count; j++) {

            String fileName = request.getParameter("checked_" + j);

            if (fileName != null && !fileName.equals("0")) {

                Integer checkid = checkFileStatus(fileName, patientId);
                if (logger.isDebugEnabled()) logger.debug("checkid : " + checkid);

                if (checkid <= 0) {
                    filepath = filepath + "" + fileName;

                    PatientDocument documentVO = new PatientDocument();

                    documentVO.setCreatedby(patientId);
                    documentVO.setCreatedon(timestamp);
                    documentVO.setDocname(fileName);
                    documentVO.setDocpath(filepath);
                    documentVO.setPatientid(patientId);
                    documentVO.setKeyid(shareId);
                    documentVO.setKey("Share Request");
                    documentVO.setUploadType("Share");
                    Integer id = userManager.savePatientDocument(documentVO);
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
    public
    @ResponseBody
    String shareStatusChange(@RequestParam Integer physicianId, @RequestParam String type, @RequestParam Integer shareId,
                             @RequestParam Integer patientId, ModelMap model, @RequestParam Integer count,
                             HttpServletRequest request) {
        logger.debug("PatientMainController pa==> ");
        String returnResult = "";
        if (patientId == null) {
            UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            patientId = user.getId();
        }

        try {
            String shareType = null;
            if (type.equals("Share")) {
                physicianManager.updateShareStatus(shareId);


                fnmoveDocuments1(count, request, patientId, shareId);


            } else {
                shareType = "New patient share";

//			

                ShareClinicalInfo shareVo = new ShareClinicalInfo();
                UserVO userVo = new UserVO();
                UserVO userVo1 = new UserVO();

                UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

                    fnmoveDocuments(count, request, patientId, shareid);

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
    public
    @ResponseBody
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
        return Constants.PATIENT_VIEW_ENCOUNTERS_VIEW;
    }


    @RequestMapping(value = "/patient/createSignatureFromText.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String createDigitalSignatureFromText(HttpServletRequest request, ModelMap model, @RequestParam String signText) {
        String json;
        logger.info("PatientMainController createDigitalSignatureFromText Starts ");
        try {
            JsonResponse jsonResponse = new JsonResponse();
            UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Integer patId = user.getId();
            createDigitalSign(request, signText, patId);
            jsonResponse.setMessage("Signature Created Successfully");
            json = new Gson().toJson(jsonResponse);

        } catch (Exception e) {
            logger.error(Constants.LOG_ERROR + e.getMessage(), e);
            model.addAttribute("EXCEPTION", Constants.getPropertyValue(Constants.SERVICE_ERROR));
            json = new Gson().toJson(Constants.getPropertyValue(Constants.SERVICE_ERROR));
        }
        logger.info("PatientMainController createDigitalSignatureFromText ends ");

        return json;
    }

    synchronized public String getPatientRequestClinicalInformationTemplate(HttpServletRequest request,
                                                                            String name, Date dob, String address, Integer userId)
            throws IOException {

        File finalFax = null;
        try {
            String destinationPath = Constants.getPlatformProperyValue(Constants.FAREFILE_UPLOAD_PATH);

            URL pdfUrl = getHipaaFormURL(request);

            if (logger.isDebugEnabled()) logger.debug("pdfUrl=" + pdfUrl);

            // PdfReader pdfReader = new PdfReader("D:/test/sample.pdf");
            // PdfReader pdfReader = new PdfReader(new URL("http://localhost:8090/bluehub/HIPAAForm.pdf"));

            final PdfReader pdfReader = new PdfReader(pdfUrl);
            final File uploadDir = new File(destinationPath, userId.toString());

            if (logger.isDebugEnabled())
                logger.debug("File upload dir: " + uploadDir + " exists: " + uploadDir.exists());

            if (!uploadDir.exists()) {
                if (!uploadDir.mkdirs()) logger.error("Failed to create upload directory: " + uploadDir);
            }

            finalFax = new File(uploadDir, "fax.pdf");
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(finalFax));

            //put content under
            PdfContentByte content = pdfStamper.getUnderContent(1);

            final BaseFont font = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);

            content.beginText();
            content.setFontAndSize(font, 12);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, name, 65, 674, 0);

            // Identifying info
            final int valueX = 120;
            final int labelX = valueX - 10;
            int y = 575;
            content.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Name:", labelX, y, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, name, valueX, y, 0);

            content.showTextAligned(PdfContentByte.ALIGN_RIGHT, "DOB:", labelX, y - 15, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, CommonUtils.parseStringFromDate(dob), valueX, y - 15, 0);

            content.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Address:", labelX, y - 30, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, address, valueX, y - 30, 0);

            // Add the Signature and date
            content.setFontAndSize(font, 16);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, name, 60f, 80f, 0);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, dateFormat.format(new Date()), 380f, 80f, 0);
            content.endText();

            pdfStamper.close();
        } catch (Exception e) {
            logger.error("Failed to create and send fax", e);
        }

        return finalFax == null ? null : finalFax.getAbsolutePath();
    }

    private String createDigitalSign(HttpServletRequest request, String text, Integer userName) {

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

        File sourceFolder = CommonUtils.createDir(Constants.getPlatformProperyValue(Constants.FAREFILE_UPLOAD_PATH), userName.toString());
        final File pngFile = new File(sourceFolder, "Signature.png");

        try {
            ImageIO.write(img, "png", pngFile);

            URL pdfUrl = getHipaaFormURL(request);
            PdfReader pdfReader = new PdfReader(pdfUrl);
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(new File(sourceFolder, "Signature.pdf")));

            Image image = Image.getInstance(pngFile.getAbsolutePath());

            //put content under
            PdfContentByte content = pdfStamper.getUnderContent(1);
            image.setAbsolutePosition(60f, 100f);

            //Text over the top name
            BaseFont username = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            content.beginText();
            content.setFontAndSize(username, 15);
            content.showTextAligned(PdfContentByte.ALIGN_CENTER, text, 200f, 660f, 0);
            content.endText();

            //Text over the existing page
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            content.beginText();
            content.setFontAndSize(bf, 20);
            content.showTextAligned(PdfContentByte.ALIGN_CENTER, text, 150f, 85f, 0);
            content.endText();

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();

            BaseFont bf1 = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            content.beginText();
            content.setFontAndSize(bf1, 20);
            content.showTextAligned(PdfContentByte.ALIGN_CENTER, dateFormat.format(date), 450f, 85f, 0);
            content.endText();

            pdfStamper.close();

            userManager.updateSignature(userName);

        } catch (Exception e) {
            logger.error("Failed to construct digital signature", e);
        }

        return pngFile.getAbsolutePath();
    }

    private URL getHipaaFormURL(HttpServletRequest request) throws MalformedURLException {
        final String scheme = request.getScheme();
        final String contextPath = request.getContextPath();
        final String serverName = request.getServerName();
        final int serverPort = request.getServerPort();

        if (logger.isDebugEnabled())
            logger.debug(String.format("Request: scheme='%s' server=%s:%s context='%s' ", scheme, serverName, serverPort, contextPath));

        final String uriBase;

        if (contextPath == null || contextPath.equalsIgnoreCase("")) {
            uriBase = serverName + ":" + serverPort;
        } else if (serverPort == 0) {
            uriBase = serverName + "/" + contextPath;
        } else {
            uriBase = serverName + ":" + serverPort + "/" + contextPath;
        }

        return new URL(scheme + "://" + uriBase + "/HIPAAForm.pdf");
    }

    @RequestMapping(value = "/patient/getEfaxAssocoatedDocuments.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String getEfaxAssociatedDocuments(ModelMap model, @RequestParam Integer patientId) {
        logger.info("moveFileToCourseLocation");

        String json = NULL_JSON;
        try {
            List<Object[]> patList = physicianManager.getEfaxAssocoatedPatientDocuments(patientId);
            json = new Gson().toJson(patList);
        } catch (Exception e) {
            logger.error("Fail", e);
        }

        return json;
    }

    @RequestMapping(value = "/patient/getEfaxDocuments.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String getEfaxDocuments(ModelMap model, @RequestParam Integer patientId) {
        logger.info("moveFileToCourseLocation");
        String json = null;
        try {

            // create upload directory
            File saveDir = CommonUtils.createDir(Constants.getPlatformProperyValue(Constants.FAX_DOWNLOAD_PATH), null);

            Date receivedDate = null;
            List<Object[]> fileList = new LinkedList<>();
            File[] listOfFiles = saveDir.listFiles();
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        receivedDate = getCreatedDateFromFile(file.getAbsolutePath());

                        Object[] obj = new Object[]{
                                file.getName(),
                                file.getAbsolutePath(),
                                file.getPath(),
                                CommonUtils.parseStringFromDate(receivedDate)
                        };

                        fileList.add(obj);
                    }
                }

                json = new Gson().toJson(fileList);
            }
        } catch (Exception e) {
            logger.error("Failed", e);
        }

        return json;
    }

    @RequestMapping(value = "/patient/shareFaxDocuments.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String shareEfaxDocuments(HttpServletRequest request, ModelMap model, @RequestParam Integer patientId, @RequestParam Integer count) {
        logger.info("moveFileToCourseLocation");
        String sourcePath = Constants.getPlatformProperyValue(Constants.FAX_DOWNLOAD_PATH);
        String json = null;
        JsonResponse jsonResponse = new JsonResponse();
        UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            File folder = new File(sourcePath);
            File[] listOfFiles = folder.listFiles();
            int k = 0;

            if (listOfFiles != null && listOfFiles.length > 0) {

                //check the file here and move to fare document folder

                for (int j = 0; j < count; j++) {

                    if (request.getParameter("hdnFaxFileCheckBox_" + j) != null && request.getParameter("hdnFaxFileCheckBox_" + j).equals("true")) {
                        k++;

                        if (patientId != 0) {
                            String fileFrom = request.getParameter("hdnFaxFile_" + j);
                            for (int i = 0; i < listOfFiles.length; i++) {
                                if (listOfFiles[i].isFile()) {
                                    File fileName = new File(sourcePath + listOfFiles[i].getName());
                                    String fileTo = fileName.getAbsolutePath();
                                    if (fileFrom.equals(fileTo)) {
                                        //here to move the files from fax folder to fare
                                        String receivedDate = request.getParameter("date_" + i);
                                        moveFaxFileToCourseLocation(fileName, patientId, 0, request, user.getId(), receivedDate);

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

            if (k > 0 && patientId == 0) {

                jsonResponse.setMessage("Please select a patient.");
                jsonResponse.setStatus("No");
            } else if (k > 0 && patientId != 0) {

                jsonResponse.setMessage("Documents shared successfully.");
                jsonResponse.setStatus("Yes");
            } else {

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
                                               HttpServletRequest request, Integer userid, String receivedDate) {

        logger.info("moveFileToCourseLocation");
        File sourceFile = null;
        File destinationFile = null;

        String result = null;
        String destinationPath = Constants.getPlatformProperyValue(Constants.FAREFILE_UPLOAD_PATH);
        String associatedDate = null;
        try {

            sourceFile = incomingFIle;
            Timestamp createdOnTime = new Timestamp(new Date().getTime());

            associatedDate = CommonUtils.parseStringFromTimeStampData(createdOnTime);

            File fileName = sourceFile;
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
    public
    @ResponseBody
    String deleteEfaxDocuments(HttpServletRequest request, ModelMap model, @RequestParam String fileName) {
        logger.info("PatientMainController deleteEfaxDocuments Starts==>");
        String json = null;
        JsonResponse jsonResponse = new JsonResponse();
        try {
            String sourcePath = Constants.getPlatformProperyValue(Constants.FAX_DOWNLOAD_PATH);
            File file = new File(sourcePath + fileName);
            if (file.exists()) {
                file.delete();
                jsonResponse.setMessage("Document deleted successfully");
            } else {
                jsonResponse.setMessage("Document unavailable");
            }
            json = new Gson().toJson(jsonResponse);
        } catch (Exception e) {
            logger.info("Error:" + e.getMessage());
        }
        logger.info("PatientMainController deleteEfaxDocuments Ends==>");
        return json;
    }


    public Date getCreatedDateFromFile(String location) {

        // Create a new date object and pass last modified information to the date object.
        return new Date(new File(location).lastModified());
    }

    @RequestMapping(value = "/patient/unAssociateFaxDocuments.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String unAssociateEfaxDocuments(HttpServletRequest request, ModelMap model, @RequestParam Integer count) {
        logger.info("PatientMainController unAssociateEfaxDocuments Starts==>");
        String json = null;
        JsonResponse jsonResponse = new JsonResponse();
        try {
            File sourceFile = null;
            File destinationFile = null;
            String destinationPath = Constants.getPlatformProperyValue(Constants.FAX_DOWNLOAD_PATH);
            String sourcePath = Constants.getPlatformProperyValue(Constants.FAREFILE_UPLOAD_PATH);

            Integer docId = null;
            Integer patientId = null;
            String fileName = null;
            int j = 0;

            for (int i = 0; i < count; i++) {
                patientId = Integer.parseInt(request.getParameter("patientId_" + i));
                fileName = request.getParameter("hdnFaxFile_" + i);
                if (request.getParameter("hdnFaxFileCheckBox_" + i) != null
                        && request.getParameter("hdnFaxFileCheckBox_" + i).equals("true")) {

                    docId = Integer.parseInt(request.getParameter("docId_" + i));

                    sourceFile = new File(sourcePath + patientId + "/" + fileName);

                    destinationFile = new File(destinationPath + fileName);

                    if (!destinationFile.exists()) {

                        FileUtils.moveFile(sourceFile, destinationFile);
                    }
                    if (sourceFile.exists()) {
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

            if (j > 0) {

                jsonResponse.setMessage("Documents Un Associated successfully.");
            } else {

                jsonResponse.setMessage("Choose a document to Un Associate.");
            }

            json = new Gson().toJson(jsonResponse);
            logger.info("PatientMainController deleteEfaxDocuments Ends==>");
        } catch (Exception e) {
            logger.info("Error:" + e.getMessage());
        }
        return json;
    }


    @RequestMapping(value = "/patient/checksignature.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String checkSignature(HttpServletRequest request, ModelMap model) {
        logger.info("PatientMainController checkSignature Starts==>");

        JsonResponse jsonResponse = new JsonResponse();
        UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer patId = user.getId();
        String sig = userManager.checkSignature(patId);
        if (logger.isDebugEnabled()) logger.debug("sig : " + sig);

        if (sig == null) {
            jsonResponse.setMessage("no");
        } else {
            jsonResponse.setMessage("yes");
        }
        return new Gson().toJson(jsonResponse);
    }

    public Integer checkFileStatus(String filename, Integer patientid) {

        Integer id = userManager.checkFileStatus(filename, patientid);
        if (logger.isDebugEnabled()) logger.debug("id : " + id);
        return id;
    }

    private String newJson(Collection c) {
        try {
            return c != null && !c.isEmpty()
                    ? new Gson().toJson(c)
                    : new Gson().toJson(null);
        } catch (Exception e) {
            logger.error("Failed to produce JSON", e);
        }

        return new Gson().toJson(null);
    }

    /**
     * Record an outbound fax event.
     *
     * @param request        Http request
     * @param userId         User initiating the request
     * @param toFaxNumber    Fax delivery number
     * @param organizationId Org id
     * @param queueId        Queue id used to send the fax
     * @param tag            Organization/physician
     */
    private void auditFax(HttpServletRequest request, Integer userId, String toFaxNumber, Integer organizationId, String queueId, String tag) {
        Timestamp timestamp = new Timestamp(new Date().getTime());

        String myFaxNumber = Constants.getEfaxPropertyValue("faxnumber");

        final FaxVo faxVo = new FaxVo();
        faxVo.setCreatedby(userId);
        faxVo.setStatus(1);
        faxVo.setCreatedon(timestamp);
        faxVo.setPatientid(userId);
        faxVo.setTofax(toFaxNumber);
        faxVo.setFromfax(myFaxNumber);
        faxVo.setOrganizationid(organizationId);
        faxVo.setSendfaxqueueid(queueId);

        Integer efaxid = physicianManager.saveEFaxDetails(faxVo);

        AuditTrailVO dto = AuditTrailUtil
                .SaveAuditTrailDetails(efaxid, request,
                        Constants.PATIENT_REQUEST, Constants.INSERT,
                        "Requesting For Clinical Information through " + tag + " fax");
        userManager.saveAuditTrails(dto);
    }
}
