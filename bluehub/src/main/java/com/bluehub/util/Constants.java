package com.bluehub.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class Constants {

	public static final String LOG_ERROR = "error :";
	public static final String SERVICE_ERROR = "service_error";
	public static final String ANSWER_MISMATCH = "answer_mismatch";
	public static final String MAILSEND_INBOX = "mail_sendinbox";
	public static final String REGISTER_SUCCESS = "register_success";

	public static final String PATIENT_REQUEST_SUBJECT = "patientrequest_subject";
	public static final String PATIENT_REQUEST_DEAR = "patientrequest_dear";
	public static final String PATIENT_REQUEST_CONTENT = "patientrequest_content";
	public static final String PATIENT_REQUEST_BEST = "patientrequest_best";

	public static final String SUPPORT_EMAIL = "support_email";

	//share request 
	public static final String SHARE_REQUEST_SUBJECT = "sharerequest_subject";
	public static final String SHARE_REQUEST_LINK_TEXT = "sharerequest_link_text";
	public static final String SHARE_REQUEST_CONTENT1 = "sharerequest_content1";
	public static final String SHARE_REQUEST_CONTENT2 = "sharerequest_content2";
	public static final String SHARE_REQUEST_EMAIL_INTRO = "sharerequest_email_intro";

	// change and forgot password
	public static final String PASSWORD_MISMATCH = "password_mismatch";
	public static final String PASSWORD_CHANGE = "password_change";
	public static final String FORGOT_PASSWORD_SUBJECT = "forgotpassword_subject";
	public static final String FORGOT_PASSWORD_DEAR = "forgotpassword_dear";
	public static final String FORGOT_PASSWORD_CONTENT = "forgotpassword_content";
	public static final String FORGOT_PASSWORD_MAILCONTENT = "forgotpassword_mailcontent";
	public static final String FORGOT_PASSWORD_BEST = "forgotpassword_best";
	public static final String SUCCESS = "success";

	// Clinical User Registration
	public static final String LOGINFAILED = "login_failed";

	public static final String USER_REGISTERATION_SUBJECT = "user_registeration_subject";
	public static final String USER_REGISTERATION_SUBJECT1 = "user_registeration_subject1";
	public static final String USER_REGISTERATION_GREETINGS = "user_registeration_greetings";
	public static final String USER_REGISTERATION_WELCOME = "user_registeration_welcome";
	public static final String USER_REGISTERATION_CONTENT = "user_registeration_content";
	public static final String USER_REGISTERATION_MAILCONTENT = "user_registeration_mailcontent";
	public static final String USER_REGISTERATION_BEST = "user_registeration_best";

	public static final String CHILD = "Child";
	public static final String FILE_UPLOAD_PATH = "fileUpload.location";
	public static final String FAREFILE_UPLOAD_PATH = "farefileUpload.location";

	public static final String FAX_DOWNLOAD_PATH = "faxdownload.location";
	public static final String FAX_TRASH_PATH = "faxtrash.location";

	public static final String ADMINISTRATOR = "Admin";
	public static final String PHYSICIAN = "Physician";
	public static final String PATIENT = "Patient";
	public static final String PRACTICE_ADMINISTRATOR = "PracticeAdmin";
	public static final String ROLE_ADMINISTRATOR = "ROLE_ADMINISTRATOR";
	public static final String ROLE_PHYSICIAN = "ROLE_PHYSICIAN";
	public static final String ROLE_PATIENT = "ROLE_PATIENT";
	public static final String ROLE_PRACTICE_ADMINISTRATOR = "ROLE_PRACTICE_ADMINISTRATOR";
	
	// Subcsribe to services
	public static final String redirect_msg = "redirect_msg";
	public static final String DOB = "DOB";

	// ReportServices
	public static final String APPROVED = "Approved";

	// IIP
	public static final String YES = "Yes";
	public static final String NO = "No";
	public static final int PASSWORD_LENGTH=10;
	public static final int PASSCODE_LENGTH=8;
	

	private static final String charset = "!0123456789abcdefghijklmnopqrstuvwxyz";
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	public static String getPropertyValue(String msgKey) {
		return SystemProperties.getProperty(msgKey);
	}

    public static String getPlatformProperyValue(String key)
    {
        return SystemProperties.getProperty(key +"." + OSUtils.getPlatform());
    }

	public static String getMailPropertyValue(String msgKey) {
		return MailProperties.getProperty(msgKey);
	}
	
	public static String getEfaxPropertyValue(String msgKey) {
		return EfaxProperties.getProperty(msgKey);
	}
	
	public static String getDataBasePropertyValue(String msgKey) {
		return DatabaseProperties.getProperty(msgKey);
	}
	
	
	public static String getRandomPassword(){
		return getRandomString(Constants.PASSWORD_LENGTH);
	}  
	
	public static String getRandomPassCode(){
		return getRandomString(Constants.PASSCODE_LENGTH);
	}  
	
	public static String getGeneratedPassword(String password){
		if (password != null) {
			Md5PasswordEncoder ms = new Md5PasswordEncoder();
			password = ms.encodePassword(password, null);
		}		
		return password;
	}  

	public static String getRandomString(int length) {
		Random rand = new Random(System.currentTimeMillis());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= length; i++) {
			int pos = rand.nextInt(charset.length());
			sb.append(charset.charAt(pos));
		}
		return sb.toString();
	}

	public static List<String> video_types = new ArrayList<>();

	static {
		video_types.add("application/annodex");
		video_types.add("application/mp4");
		video_types.add("application/ogg");
		video_types.add("application/vnd.rn-realmedia");
		video_types.add("application/x-matroska");
		video_types.add("video/3gpp");
		video_types.add("video/3gpp2");
		video_types.add("video/annodex");
		video_types.add("video/divx");
		video_types.add("video/flv");
		video_types.add("video/h264");
		video_types.add("video/mp4");
		video_types.add("video/mp4v-es");
		video_types.add("video/mpeg");
		video_types.add("video/mpeg-2");
		video_types.add("video/mpeg4");
		video_types.add("video/ogg");
		video_types.add("video/ogm");
		video_types.add("video/quicktime");
		video_types.add("video/ty");
		video_types.add("video/vdo");
		video_types.add("video/vivo");
		video_types.add("video/vnd.rn-realvideo");
		video_types.add("video/vnd.vivo");
		video_types.add("video/webm");
		video_types.add("video/x-bin");
		video_types.add("video/x-cdg");
		video_types.add("video/x-divx");
		video_types.add("video/x-dv");
		video_types.add("video/x-flv");
		video_types.add("video/x-la-asf");
		video_types.add("video/x-m4v");
		video_types.add("video/x-matroska");
		video_types.add("video/x-motion-jpeg");
		video_types.add("video/x-ms-asf");
		video_types.add("video/x-ms-dvr");
		video_types.add("video/x-ms-wm");
		video_types.add("video/x-ms-wmv");
		video_types.add("video/x-msvideo");
		video_types.add("video/x-sgi-movie");
		video_types.add("video/x-tivo");
		video_types.add("video/avi");
		video_types.add("video/x-ms-asx");
		video_types.add("video/x-ms-wvx");
		video_types.add("video/x-ms-wmx");
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
        System.out.println("platform = " + OSUtils.getPlatform());
        //String password = getPropertyValue("serviceError");
	}

	public static final String PENDING = "Pending";
	
	/* user registration module jsp view name.*/
	
	public static final String USER_REGISTRATION_VIEW = "registration/usercommonregistration";
	public static final String USER_REGISTRATION_SUCCESS_VIEW = "registration/userregistrationsuccess";


	/* Clinical module jsp view name.*/
	
	public static final String CHANGE_PASSWORD_VIEW = "common/changepassword";

	
	/* Common jsp view name.*/
	
	public static final String HOME_VIEW = "common/home";
	public static final String SERVICES_VIEW = "common/services";
	public static final String EVENT_VIEW = "common/event";
	public static final String RESOURCES_VIEW = "common/resources";
	public static final String CONTACT_VIEW = "common/contact";
	public static final String HOME_BCABA_VIEW = "common/homebcaba";
	public static final String HOME_BCBA_VIEW = "common/homebcba";
	public static final String HOME_CHILD_VIEW = "common/homechild";
	public static final String HOME_PARENT_VIEW = "common/homeparent";
	public static final String HOME_STUDENT_VIEW = "common/homestudent";
	public static final String LOGIN_VIEW = "common/login";
	public static final String FAMILY_VIEW = "common/family";
	public static final String COMMUNITY_VIEW = "common/community";
	public static final String FORGOT_PASSWORD_VIEW = "common/forgotpassword";


	/* Bluehub common jSp */
	
	public static final String HOME_ADMIN_VIEW = "common/homeadmin";
	public static final String HOME_PATIENT_VIEW = "common/homepatient";
	public static final String HOME_PHYSICIAN_VIEW = "common/homephysician";
	public static final String HOME_PRACTICE_ADMIN_VIEW = "common/homepracticeadmin";
	public static final String HOME_PHYSICIAN_DASHBOARD = "common/homephysiciandashboard";
	
	/*Admin*/
	
	public static final String SEARCH_ADMIN_VIEW = "admin/adminsearchclinicalinformation";
	
	public static final String SEARCH_DOCUMENT_ADMIN_VIEW = "admin/admindocumentsearch";
	
	public static final String UPLOAD_ADMIN_VIEW = "admin/adminuploadclinicalinformation";
	public static final String ORGANIZATION_ADMIN_VIEW= "admin/adminorganization";
	public static final String PRACTICE_ADMIN_VIEW= "admin/adminpractice";
	public static final String ADMIN_PHYSICIAN_MAPPING= "admin/adminphysicianmapping";
	public static final String PATIENT_MANAGEMENT= "admin/patientmanagement";
	public static final String PHYSICIAN_MANAGEMENT= "admin/physicianmanagement";
	public static final String AUDITTRIAL_ADMIN_VIEW= "admin/adminaudittrial";
	public static final String FILEUPLOAD_ADMIN_VIEW = "admin/adminfileuploadclinicalinformation";
	
	public static final String ADMIN_VISIT_DETAILS_VIEW = "admin/adminvisitdetails";
	public static final String ADMIN_EFAX = "admin/adminefax";
	public static final String ADMIN_EFAX_ASS = "admin/adminefaxassociated";
	public static final String ADMIN_ALL_VISITS_VIEW = "admin/adminallvisits";
	public static final String ADMIN_ENCOUNTERS_VIEW = "admin/adminencounter";
	public static final String ADMIN_VIEW_ENCOUNTERS_VIEW = "admin/adminviewencounter";
	public static final String ADMIN_SHARE_HOME_VIEW = "admin/adminsharehome";
	
	
	/*Patient*/
	public static final String SEARCH_PATIENT_VIEW = "patient/patientsearchclinicalinformation";
	public static final String TAG_PATIENT_VIEW = "patient/patienttagclinicalinformation";
	public static final String PRIVATENOTE_PATIENT_VIEW = "patient/patientprivatenote";
	public static final String UPLOAD_PATIENT_VIEW = "patient/patientupload";
	public static final String REQUEST_PATIENT_VIEW = "patient/patientrequestclinicalinformation";
	public static final String REQUEST_PATIENT_VIEWS = "patient/patientrequestclinicalinformations";
	public static final String VIEW_PATIENT_VIEW = "patient/patientviewclinicalinformation";
	
	public static final String PATIENT_VISITDETAILS_VIEW = "patient/patientviewclinicaldetailsinformation";
	public static final String PATIENT_PERSONAL_DETAILS_VIEW = "patient/patientPersonalDetails";
	
	public static final String PATIENT_DOCUMENT_VIEW = "patient/patientviewclinicaldetailsinformation";
	public static final String SHARE_PATIENT_VIEW = "patient/patientshareclinicalinformation";
	public static final String TAB_SHARE_PATIENT_VIEW = "patient/patientsharetab";
	
	
	
	/*Physician*/
	
	public static final String SEARCH_PHYSICIAN_VIEW = "physician/physiciansearchclinicalinformation";
	public static final String UPLOAD_PHYSICIAN_VIEW = "physician/physicianuploadclinicalinformation";
	public static final String PHYSICIAN_VISIT_DETAILS_VIEW = "physician/physicianVisitDetails";
	public static final String PHYSICIAN_PERSONAL_DETAILS_VIEW = "physician/physicianPersonalDetails";
	
	public static final String PHYSICIAN_ALL_VISITS_VIEW = "physician/physicianallvisits";
	public static final String PHYSICIAN_ENCOUNTERS_VIEW = "physician/physicianencounter";
	
	public static final String PHYSICIAN_VIEW_ENCOUNTERS_VIEW = "physician/physicianviewencounter";
	
	public static final String PATIENT_VIEW_ENCOUNTERS_VIEW = "patient/patientviewencounter";
	public static final String PHYSICIAN_SHARE_REQUEST = "physician/physharerequest";
	
	
	
	public static final String PHYSICIAN_PATIENT_REQUEST = "physician/requestfrompatient";
	
	public static final String PHYSICIAN_REQUEST_INFO = "physician/phyrequestinfo";
	public static final String PHYSICIAN_SHARED_DOCUEMENT = "physician/physharedocinfo";
	public static final String PHYSICIAN_VIEW_REQUEST_INFO = "physician/viewrequestinfo";
	public static final String PHYSICIAN_VIEW_UPLOAD_DOCUMENTS = "physician/uploadclinicaldocument";
	
	public static final String PHYSICIAN_VIEW_APPROVED_REQUEST_INFO = "physician/viewapprovedrequests";
	
	public static final String PHYSICIAN_LOGIN_PATIENT_SHARE = "common/patientshareview";
	
	public static final String PHYSICIAN_SHARED_DOCUEMENT_BEHALF = "physician/physharedocinfotopatient";
	
	/*
	 Bluehub common jSp
	 */
	
	public static final String MAIL_SENT_SUCCESS="mail_sent_success";
	
	public static final String capture = "config/template/jasper/checkboxnontick.jpg";
	public static final String PUBLIC_DASHBOARD_VIEW = "public/publicdashboard";
	public static final String sandbox = "sandbox";
	public static final String production = "production";
	public static final String development = "development";

	public static final String donotreply = "donotreply";
	public static final String active = "active";

	public static final String click_linkhere = "click_linkhere";
	public static final String login_activated = "login_activated";
	public static final String SUPPORT_MAIL_ID = "support_mail_id";
	public static final String hello = "hello";

	public static final String click_linkview = "click_linkview";
	
	// For audit trails Constants
	
	public static final String PATIENT_REQUEST = "PATIENTREQUEST";
	public static final String PATIENT_DOCUMENTS = "CLINICAL_DOCUMENTS";
	public static final String PHYSICIAN_MAPPING = "PHYSICIANMAPPING";
	public static final String VISITS = "VISITS";
	public static final String SHARE = "SHARE_CLINICAL_DOCUMENTS";

	public static final Integer INSERT = 0;
	public static final Integer UPDATE = 1;
	public static final Integer DELETE = 2;
	public static final Integer NON_DB = 3;
	
	
	// For audit trails Constants

	// Added For Request a Mail
	
	public static final String newheadingsubject="newheadingsubject";
	public static final String newheadingsubject1="newheadingsubject1";
	public static final String newsubject="newsubject";
	public static final String newsubjectt="newsubjectt";
	public static final String subjecturl="subjecturl";
	public static final String  newsubject1="newsubject1";
	public static final String newsubject2="newsubject2";
	public static final String newsubject3="newsubject3";
	public static final String newsubject4="newsubject4";
	public static final String newsubject5="newsubject5";
	public static final String newsubject6="newsubject6";
	public static final String newsubject7="newsubject7";
	public static final String newsubject8="newsubject8";
	public static final String newsubject9="newsubject9";
	public static final String newsubject10="newsubject10";
	public static final String  newsubject11="newsubject11";
	public static final String newsubject12="newsubject12";
	public static final String newsubject13="newsubject13";
	public static final String newsubject14="newsubject14";
	public static final String  newsubject15="newsubject15";
	public static final String newsubject16="newsubject16";
	public static final String newsubject17="newsubject17";
	public static final String newsubject18="newsubject18";
	public static final String newsubject19="newsubject19";
	public static final String  newsubject20="newsubject20";
	public static final String newsubject21="newsubject21";
	public static final String newsubject111="newsubject111";
	public static final String newsubjectfaxno="newsubjectfaxno";

	public static final String PATIENT_SHARE_SUBJECT = "patient_share_subject";
	public static final String PATIENT_SHARE_MESSAGE = "patient_share_message";
	public static final String PATIENT_SHARE_LINK_TEXT = "patient_share_link_text";
}
