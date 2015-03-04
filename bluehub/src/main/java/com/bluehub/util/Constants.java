package com.bluehub.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class Constants {
	public static final SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat(
			"yyyy.MM.dd h:mm a");
	public static final SimpleDateFormat dateformahhmmmmddyyyy = new SimpleDateFormat(
			"h:mm a  MM.dd.yyyy");

	public static final String LOG_ERROR = "error :";
	public static final String SERVICE_ERROR = "service_error";
	public static final String registration_subject = "registration_subject";
	public static final String registration_content = "registration_content";
	public static final String ANSWER_MISMATCH = "answer_mismatch";
	public static final String MAILSEND_INBOX = "mail_sendinbox";
	public static final String ROLE_NOT = "role_not_found";
	public static final String USER_NOT_REGISTERED = "user_not_registered";
	public static final String CHILD_NOT_REGISTERED = "child_not_registered";
	public static final String REGISTER_SUCCESS = "register_success";
	public static final String BACKGROUND_CLINICAL = "Background Clinical";
	public static final String TRAINING_CLINICAL = "Training Clinical";
	public static final String PRE_SKILLS = "Pre-Academic Skills";
	public static final String SAFETY_SKILLS = "Safety Skills";
	public static final String ROUTINE_ACTIVITIES = "Routine Activities";
	public static final String DAILY_LIVING = "Daily Living Skills";
	public static final String SELF_CARE = "Self-Care Skills";
	public static final String SOCIAL_SKILLS = "Social Skills";
	public static final String PLAY_LEISURE = "Play and Leisure";
	public static final String COMMUNITY_SKILLS = "Community Skills";
	public static final String VOCATION_SKILLS = "Vocational Skills";
	public static final String COPING_TOLERANCE = "Coping and Tolerance";
	public static final String LANGUAGE_COMMUNICATION = "Language and Communication";
	public static final String ATTEND_SOCIAL = "Attending And Social Referencing";
	public static final String ADMIN = "admin";
	public static final String REISSUE = "Reissue_Certificate";
	public static final String CERTIFI_PENDING = "pending_Certificate";
	public static final String ROUTINE_SKILLS = "Routine Activity";
	public static final String DAILY_LIVING_SKILLS = "Daily Living Skills";
	
	public static final String PATIENT_REQUEST_SUBJECT = "patientrequest_subject";
	public static final String PATIENT_REQUEST_DEAR = "patientrequest_dear";
	public static final String PATIENT_REQUEST_CONTENT = "patientrequest_content";
	public static final String PATIENT_REQUEST_MAILCONTENT = "patientrequest_mailcontent";
	public static final String PATIENT_REQUEST_BEST = "patientrequest_best";
	
	//share request 
	public static final String SHARE_REQUEST_CONTENT = "sharerequest_content";
	public static final String SHARE_REQUEST_SUBJECT = "sharerequest_subject";

	// change and forgot password
	public static final String PASSWORD_MISMATCH = "password_mismatch";
	public static final String PASSWORD_CHANGE = "password_change";
	public static final String FORGOT_PASSWORD_SUBJECT = "forgotpassword_subject";
	public static final String FORGOT_PASSWORD_DEAR = "forgotpassword_dear";
	public static final String FORGOT_PASSWORD_CONTENT = "forgotpassword_content";
	public static final String FORGOT_PASSWORD_MAILCONTENT = "forgotpassword_mailcontent";
	public static final String FORGOT_PASSWORD_BEST = "forgotpassword_best";
	public static final String ACTIVE = "Active";
	public static final String MM_DD_YYYY = "mm/dd/yyyy";
	public static final String DISPLAY_FORMS_LIST = "ceuOfferingFormsList";
	public static final String DISPLAY_FORMS = "ceuOfferingForm";
	public static final String DISPLAY_ROLE = "displayRoleList";
	public static final String SUCCESS = "success";
	public static final String CEU_REORDER="reorderpopup";
	public static final String CHILD_FORGOT_PASSWORD_SUBJECT = "child_forgotpassword_subject";
	public static final String CHILD_FORGOT_PASSWORD_DEAR = "child_forgotpassword_dear";
	public static final String CHILD_FORGOT_PASSWORD_CONTENT = "child_forgotpassword_content";
	public static final String CHILD_FORGOT_PASSWORD_MAILCONTENT = "child_forgotpassword_mailcontent";
	public static final String CHILD_FORGOT_PASSWORD_BEST = "child_forgotpassword_best";

	// Clinical User Registration
	public static final String CLINICAL_USER_STATUS_PENDING = "Pending Approval";
	public static final String CLINICAL_USER_STATUS_REJECTED = "Rejected";
	public static final String CLINICAL_USER_STATUS_APPROVED = "Approved";

	public static final String PHYSICAL_AGGRESSION = "Physical Aggression";
	public static final String VERBAL_AGGRESSION = "Verbal Aggression";
	public static final String PROPERTY_DESTRUCTION = "Property Destruction";
	public static final String TANTRUMS = "Tantrums";
	public static final String SELF_INJURIOUS_BEHAVIOR = "Self-injurious Behavior (SIB)";
	public static final String TASK_REFUSAL = "Task Refusal";
	public static final String INAPPROPRIATE_SEXUAL_BEHAVIOR = "Inappropriate Sexual Behavior";
	public static final String INAPPROPRIATE_SOCIAL_BEHAVIOR = "Inappropriate Social Behavior";
	public static final String ELOPEMENT = "Elopement";
	public static final String PICA = "PICA (Consuming non-edibles and swallowing)";
	public static final String STEREOTYPY = "Stereotypy";
	public static final String INAPPROPRIATE_SAFETY_BEHAVIOR = "Inappropriate Safety Behavior";
	public static final String LOGINFAILED = "login_failed";

	public static final String USER_REGISTERATION_SUBJECT = "user_registeration_subject";
	public static final String USER_REGISTERATION_SUBJECT1 = "user_registeration_subject1";
	public static final String USER_REGISTERATION_GREETINGS = "user_registeration_greetings";
	public static final String USER_REGISTERATION_WELCOME = "user_registeration_welcome";
	public static final String USER_REGISTERATION_CONTENT = "user_registeration_content";
	public static final String USER_REGISTERATION_MAILCONTENT = "user_registeration_mailcontent";
	public static final String USER_REGISTERATION_BEST = "user_registeration_best";

	public static final String CLINICALUSER_REGISTERATION_SUBJECT = "clinicaluser_registeration_subject";
	public static final String CLINICALUSER_REGISTERATION_SUBJECT1 = "clinicaluser_registeration_subject1";
	public static final String CLINICALUSER_REGISTERATION_GREETINGS = "clinicaluser_registeration_greetings";
	public static final String CLINICALUSER_REGISTERATION_WELCOME = "clinicaluser_registeration_welcome";
	public static final String CLINICALUSER_REGISTERATION_CONTENT = "clinicaluser_registeration_content";
	public static final String CLINICALUSER_REGISTERATION_APPROVAL = "clinicaluser_registeration_approval";
	public static final String CLINICALUSER_REGISTERATION_MAILCONTENT = "clinicaluser_registeration_mailcontent";
	public static final String CLINICALUSER_REGISTERATION_BEST = "clinicaluser_registeration_best";

	public static final String CLINICALUSER_CREDENTIAL_APPROVAL_SUBJECT = "clinicaluser_credential_approval_subject";
	public static final String CLINICALUSER_CREDENTIAL_APPROVAL_GREETINGS = "clinicaluser_credential_approval_greetings";
	public static final String CLINICALUSER_CREDENTIAL_APPROVAL_CONTENT = "clinicaluser_credential_approval_content";
	public static final String CLINICALUSER_CREDENTIAL_APPROVAL_BEST = "clinicaluser_credential_approval_best";

	public static final String CLINICALUSER_CREDENTIAL_REJECT_SUBJECT = "clinicaluser_credential_reject_subject";
	public static final String CLINICALUSER_CREDENTIAL_REJECT_GREETINGS = "clinicaluser_credential_reject_greetings";
	public static final String CLINICALUSER_CREDENTIAL_REJECT_CONTENT = "clinicaluser_credential_reject_content";
	public static final String CLINICALUSER_CREDENTIAL_REJECT_REASON="clinicaluser_credential_reject_reason";
	public static final String CLINICALUSER_CREDENTIAL_REJECT_BEST = "clinicaluser_credential_reject_best";
	
	public static final String AGENCY_REGISTERATION_SUBJECT = "agency_registeration_subject";
	public static final String AGENCY_REGISTERATION_SUBJECT1 = "agency_registeration_subject1";
	public static final String AGENCY_REGISTERATION_GREETINGS = "agency_registeration_greetings";
	public static final String AGENCY_REGISTERATION_WELCOME = "agency_registeration_welcome";
	public static final String AGENCY_REGISTERATION_CONTENT = "agency_registeration_content";
	public static final String AGENCY_REGISTERATION_MAILCONTENT = "agency_registeration_mailcontent";
	public static final String AGENCY_REGISTERATION_MAILCONTENT2 = "agency_registeration_mailcontent2";
	public static final String AGENCY_REGISTERATION_MAILCONTENT3 = "agency_registeration_mailcontent3";
	public static final String AGENCY_REGISTERATION_BEST = "agency_registeration_best";

	public static final String CLINICALUSER_CREDENTIAL_MODIFY_SUBJECT = "clinicaluser_credential_modify_subject";
	public static final String CLINICALUSER_CREDENTIAL_MODIFY_GREETINGS = "clinicaluser_credential_modify_greetings";
	public static final String CLINICALUSER_CREDENTIAL_MODIFY_CONTENT = "clinicaluser_credential_modify_content";
	public static final String CLINICALUSER_CREDENTIAL_MODIFY_MAILCONTENT = "clinicaluser_credential_modify_mailcontent";
	public static final String CLINICALUSER_CREDENTIAL_MODIFY_BEST = "clinicaluser_credential_modify_best";

	public static final String PARENT_NOTIFICATION_APPROVAL_SUBJECT = "parent_notification_approval_subject";
	public static final String PARENT_NOTIFICATION_APPROVAL_GREETINGS = "parent_notification_approval_greetings";
	public static final String PARENT_NOTIFICATION_APPROVAL_CONTENT = "parent_notification_approval_content";
	public static final String PARENT_NOTIFICATION_APPROVAL_BEST = "parent_notification_approval_best";

	public static final String PARENT_NOTIFICATION_REJECT_SUBJECT = "parent_notification_reject_subject";
	public static final String PARENT_NOTIFICATION_REJECT_GREETINGS = "parent_notification_reject_greetings";
	public static final String PARENT_NOTIFICATION_REJECT_CONTENT = "parent_notification_reject_content";
	public static final String PARENT_NOTIFICATION_REJECT_BEST = "parent_notification_reject_best";

	public static final String SUPERVISOR_NOTIFICATION_APPROVAL_SUBJECT = "supervisor_notification_approval_subject";
	public static final String SUPERVISOR_NOTIFICATION_APPROVAL_GREETINGS = "supervisor_notification_approval_greetings";
	public static final String SUPERVISOR_NOTIFICATION_APPROVAL_CONTENT = "supervisor_notification_approval_content";
	public static final String SUPERVISOR_NOTIFICATION_APPROVAL_BEST = "supervisor_notification_approval_best";

	public static final String SUPERVISOR_NOTIFICATION_REJECT_SUBJECT = "supervisor_notification_reject_subject";
	public static final String SUPERVISOR_NOTIFICATION_REJECT_GREETINGS = "supervisor_notification_reject_greetings";
	public static final String SUPERVISOR_NOTIFICATION_REJECT_CONTENT = "supervisor_notification_reject_content";
	public static final String SUPERVISOR_NOTIFICATION_REJECT_BEST = "supervisor_notification_reject_best";

	public static final String ADMINISTRATOR_NOTIFICATION_APPROVAL_SUBJECT = "administrator_notification_approval_subject";
	public static final String ADMINISTRATOR_NOTIFICATION_APPROVAL_GREETINGS = "administrator_notification_approval_greetings";
	public static final String ADMINISTRATOR_NOTIFICATION_APPROVAL_CONTENT = "administrator_notification_approval_content";
	public static final String ADMINISTRATOR_NOTIFICATION_APPROVAL_BEST = "administrator_notification_approval_best";

	public static final String ADMINISTRATOR_NOTIFICATION_REJECT_SUBJECT = "administrator_notification_reject_subject";
	public static final String ADMINISTRATOR_NOTIFICATION_REJECT_GREETINGS = "administrator_notification_reject_greetings";
	public static final String ADMINISTRATOR_NOTIFICATION_REJECT_CONTENT = "administrator_notification_reject_content";
	public static final String ADMINISTRATOR_NOTIFICATION_REJECT_BEST = "administrator_notification_reject_best";

	public static final String SERVICE_OFFERING_SUBSCRIBE_SUBJECT = "service_offering_subscribe_subject";
	public static final String SERVICE_OFFERING_SUBSCRIBE_GREETINGS = "service_offering_subscribe_greetings";
	public static final String SERVICE_OFFERING_SUBSCRIBE_CONTENT = "service_offering_subscribe_content";

	public static final String SERVICE_OFFERING_UNSUBSCRIBE_SUBJECT = "service_offering_unsubscribe_subject";
	public static final String SERVICE_OFFERING_UNSUBSCRIBE_GREETINGS = "service_offering_unsubscribe_greetings";
	public static final String SERVICE_OFFERING_UNSUBSCRIBE_CONTENT = "service_offering_unsubscribe_content";

	public static final String CEU_CERTIFICATE_SUBJECT = "ceu_certificate_subject";
	public static final String CEU_CERTIFICATE_HI = "ceu_certificate_hi";
	public static final String CEU_CERTIFICATE_CONTENT = "ceu_certificate_content";
	public static final String CEU_CERTIFICATE_MAILCONTENT = "ceu_certificate_mailcontent";
	public static final String CEU_CERTIFICATE_MAILLIST = "ceu_certificate_maillist";
	public static final String CEU_CERTIFICATE_BEST = "ceu_certificate_best";

	public static final String CEU_TRAINING_SUBJECT = "ceu_training_subject";
	public static final String CEU_TRAINING_DEAR = "ceu_training_dear";
	public static final String CEU_TRAINING_CONTENT = "ceu_training_content";
	public static final String CEU_TRAINING_MAILCONTENT = "ceu_training_mailcontent";
	public static final String CEU_TRAINING_PASSCODE = "ceu_training_passcode";
	public static final String CEU_TRAINING_VALID="ceu_training_valid";
	public static final String CEU_TRAINING_DAYS="ceu_training_days";
	public static final String CEU_TRAINING_BEST = "ceu_training_best";
	public static final String PASSCODE_MISMATCH = "passcode_mismatch";
	public static final String CEU_TRAINING_ADMINID = "ceu_training_adminid";
	
	public static final String RE_CEU_TRAINING_SUBJECT = "re_ceu_training_subject";
	public static final String RE_CEU_TRAINING_DEAR = "re_ceu_training_dear";
	public static final String RE_CEU_TRAINING_CONTENT = "re_ceu_training_content";
	public static final String RE_CEU_TRAINING_MAILCONTENT = "re_ceu_training_mailcontent";
	public static final String RE_CEU_TRAINING_PASSCODE = "re_ceu_training_passcode";
	public static final String RE_CEU_TRAINING_VALID="re_ceu_training_valid";
	public static final String RE_CEU_TRAINING_DAYS="re_ceu_training_days";
	public static final String RE_CEU_TRAINING_BEST = "re_ceu_training_best";
	public static final String RE_PASSCODE_MISMATCH = "re_passcode_mismatch";
	public static final String RE_CEU_TRAINING_ADMINID = "re_ceu_training_adminid";
	

	public static final String CASE_ALLOCATION_SUBJECT = "case_allocation_subject";
	public static final String CASE_ALLOCATION_MAIL = "case_allocation_mail";
	public static final String CASE_ALLOCATION_CONTENT = "case_allocation_content";
	public static final String CASE_ALLOCATION_MAILCONTENT = "case_allocation_mailcontent";
	public static final String ADMIN_ROLE = "admin";
	public static final String PENDING_SERVICE = "Pending Service Subscription";
	public static final String MENTION_ALL = "mention_all_those";

	//public static final String FINISH_TRAINING_HI="finish_training_hi";
	public static final String FINISH_TRAINING_SUBJECT ="finish_training_subject";
	public static final String FINISH_TRAINING_SUBJECT1 ="finish_training_subject1";
	public static final String FINISH_TRAINING ="finish_training";
	public static final String FINISH_TRAINING_ID="finish_training_id";
	public static final String FINISH_TRAINING_CERTIFICATE="finish_training_certificate";
	
	public static final String INACTIVE = "Inactive";
	public static final String ADMIN_EMAIL = "adminemail";
	public static final String ADMIN_NAME = "adminname";
	public static final String IIP_SUCCESS = "iip_success";
	public static final String IIP_NO_FUNDING_SOURCE = "iip_no_funding_source";

	public static final String CLINICAL_STAFF = "Clinical Staff";
	public static final String ACADEMICIAN = "Academician";
	public static final String FAMILY_MEMBER = "Family Member";
	public static final String PARENT = "Parent";
	public static final String GUARDIAN = "Guardian";
	public static final String CHILD = "Child";
	public static final String SYS_ADMIN = "systemadmin";
	public static final String ROLE_CLINICAL = "ROLE_CLINICAL";
	public static final String ROLE_ACADEMICIAN = "ROLE_ACADEMICIAN";
	public static final String ROLE_PARENT = "ROLE_PARENT";
	public static final String ROLE_CHILD = "ROLE_CHILD";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String SELECT_CHILD = "child_user_find";
	public static final String FILE_UPLOAD_PATH = "fileUpload.location";
	public static final String FAREFILE_UPLOAD_PATH = "farefileUpload.location";
	public static final String DOWNLOAD_REQ_PATH = "download.req.form.location";
	public static final String DOWNLOAD_TOOL_PATH = "download.tool.form.location";

	public static final String FAX_DOWNLOAD_PATH = "faxdownload.location";
	public static final String FAX_TRASH_PATH = "faxtrash.location";
	public static final String FAX_TEMPLATE_PATH = "faxtemplate.location";

	public static final String ADMINISTRATOR = "Admin";
	public static final String PHYSICIAN = "Physician";
	public static final String PATIENT = "Patient";
	public static final String PRACTICE_ADMINISTRATOR = "PracticeAdmin";
	public static final String ROLE_ADMINISTRATOR = "ROLE_ADMINISTRATOR";
	public static final String ROLE_PHYSICIAN = "ROLE_PHYSICIAN";
	public static final String ROLE_PATIENT = "ROLE_PATIENT";
	public static final String ROLE_PRACTICE_ADMINISTRATOR = "ROLE_PRACTICE_ADMINISTRATOR";
	
	// Subcsribe to services
	public static final String UNSUBSCRIBED = "Unsubscribed";
	public static final String SUSCRIBED = "Subscribed";
	public static final String BORDER_LOGO = "config/template/jasper/border.jpg";
	public static final String CHILD_LOGO = "config/template/jasper/childlogo.jpg";
	public static final String SIGNATURE_LOGO = "config/template/jasper/Signature.jpg";
	public static final String CERTIFICATE_FILE = "CertificateCompletion.pdf";
	public static final String JRXML_FILE = "trainingcertificate";
	public static final String CERTIFICATE_SENT = "Certificate generated and mailed to the registered user email";
	public static final String clinical_approve = "clinical_user_approve";
	public static final String clinical_rejcet = "clinical_user_reject";
	public static final String redirect_msg = "redirect_msg";
	public static final String parentA = "A";
	public static final String parentB = "B";
	public static final String CHILD_NAME = "ChildName";
	public static final String USER_NAME = "UserName";
	public static final String DOB = "DOB";
	public static final String PARENTA = "ParentA";
	public static final String FOUNDING_SOURCE = "FundingSource";
	public static final String POLICY_NO = "PolicyNo";
	public static final String STATUS = "Status";

	public static final String PENDING_CASE = "Pending Case Allocation";
	public static final String CASE_ALLOCATED = "Case Allocated";
	
	// ReportServices
	public static final String NEW = "New";
	public static final String REJECTED_BY_PARENT = "Rejected by Parent";
	public static final String REJECTED_BY_SUPERVISOR = "Rejected";
	public static final String PENDING_PARENT_APPROVAL = "Pending Parent Approval";
	public static final String APPROVED = "Approved";
	public static final String REPORT_SERVICE_DELETE_FLAG_YES = "Yes";
	public static final String REPORT_SERVICE_DELETE_FLAG_NO = "No";
	
	// IIP
	public static final String YES = "Yes";
	public static final String NO = "No";
	public static final int PASSWORD_LENGTH=10;
	public static final int PASSCODE_LENGTH=8;
	
	// CEU Certification
	public static final String PENDING_CERTIFICATE_ISSUE="Pending Certificate Issue";
	public static final String CEU_TRAINING_TYPE_QUIZ="Quiz";
	
	//BILLING
	public static final String REPORT_SERVICES_SEARCH_CRITERIA="reportSearchCriteria";
	public static final String REPORT_SERVICES_SEARCH_CRITERIA_BILLING_STATUS="Billing Status";
	public static final String REPORT_SERVICES_SEARCH_CRITERIA_CHILD_NAME="Child Name";
	public static final String REPORT_SERVICES_SEARCH_CRITERIA_PARENT_A="Parent A";
	public static final String REPORT_SERVICES_SEARCH_CRITERIA_FUNDING_SOURCE="Funding Source";
	public static final String REPORT_SERVICES_SEARCH_CRITERIA_POLICY_NO="Policy No";
	public static final String REPORT_SERVICES_SEARCH_STRING_PENDING_INVOICE_GENERATION="PendingInvoiceGeneration";
	public static final String REPORT_SERVICES_SEARCH_STRING_FULLY_INVOICED="FullyInvoiced";
	public static final String SEARCH_STRING="searchString";
	public static final String INVOICE_STATUS_PENDING_INVOICE_GENERATION="Service Availed, Pending Invoice Generation";
	public static final String INVOICE_STATUS_FULLY_INVOCED="Fully Invoiced";
	public static final String RECEIPT_STATUS_PENDING_COLLECTION="Pending Collection";
	public static final String RECEIPT_STATUS_FULLY_COLLECTED="Fully Collected";
	public static final String REPORT_SERVICES_SEARCH_CRITERIA_RECEIPT_STATUS="Receipt Status";
	public static final String REPORT_SERVICES_SEARCH_STRING_PENDING_COLLECTION="Pending Collection";
	public static final String REPORT_SERVICES_SEARCH_STRING_FULLY_COLLECTED="Fully Collected";
	
	// BILLING pdf generation
	public static final String INVOICE_JRXML_FILE = "Invoice";
	public static final String INVOICE_FILE = "Invoice.pdf";
	public static final String PAYROLL_FILE = "Payroll.pdf";
	
	public static final String SUBJECT_INVOICE ="Invoice_Generation";
	public static final String INVOICE_GREETINGS ="Invoice_greetings";
	public static final String INVOICE_MAIL ="Invoice_mail";
	public static final String INVOICE_CONTENT ="Invoice_content";
	public static final String INVOICE_WISHES ="Invoice_wishes";
	
	public static final String SUBJECT_PAYROLL ="Payroll_Generation";
	public static final String PAYROLL_GREETINGS ="Payroll_greetings";
	public static final String PAYROLL_MAIL ="Payroll_mail";
	public static final String PAYROLL_CONTENT ="Payroll_content";
	public static final String PAYROLL_WISHES ="Payroll_wishes";

	//PAYMENT
	public static final String PAYMENT_COMBO_PAYROLL_STATUS = "Payroll Status";
	public static final String PAYMENT_COMBO_ROLE = "Role";
	public static final String PAYMENT_COMBO_USERNAME = "User Name";
	public static final String PAYMENT_COMBO_FUNDING_SOURCE = "Funding Source";
	public static final String PAYMENT_COMBO_PENDING_PAYROLL = "Service Rendered, Pending Payroll Generation";
	public static final String PAYMENT_COMBO_FULLY_GENERATED = "Fully generated";
	
	public static final String PAYMENT_SEARCH_CRITERIA="paymentSearchCriteria";
	public static final String PAYMENT_SEARCH_VALUE="paymentSearchValue";
	public static final String PAYMENT_STATUS="PaymentStatus";
	public static final String PAYMENT_ROLE="PaymentRole";
	public static final String PAYMENT_USER="PaymentUserName";
	public static final String PAYMENT_FUNDING="PaymentFundingSource";
	public static final String PAYMENT_PENDING="Pending Payment";
	public static final String FULLY_PAID="Fully Paid";
	public static final String SERVICE_RENDERED="serviceRendered";
	public static final String INVOICE_PDF_FILE = "_invoice.pdf";
	public static final String PAYROLL_PDF_FILE = "_payroll.pdf";
	public static final String PAYMENT_JASPER = "payment.jasper";
	public static final String INVOICE_JASPER = "billinvoice.jasper";
	public static final String PUBLIC_USER = "General Public";
	public static final String FUNDING_SOURCE_ADD = "funding_source_added";
	public static final String CATEGORY_NAME_ADD = "category_name_added";
	public static final String SUBSCRIBED = "subscribed";
	public static final String CEU_TRAINING_EXPIRED = "ceu_training_expired";
	public static final String CEU_TRAINING_OVER = "ceu_training_over";
	public static final String SUBSCRIBE_STATUS = "Subscribed";
	public static final String CEU_SUBSCRIPTION_NOTSAVE = "ceu_subscription_notsaved";
	public static final String CEU_SUBSCRIPTION_SAVE = "ceu_subscription_saved";
	public static final String CUSTODIAN = "Custodian";
	public static final String ROLE_CUSTODIAN = "ROLE_CUSTODIAN";
	public static final String AGENCY_TERMDASHBOARD_VIEW = "agency/agencytermdashboard";
	public static final String GROUP_ADDED = "group_name_added";
	public static final String TREATMENT_FORMS = "Treatment Forms";
	public static final String CONSENT_FORMS = "Consent Forms";
	public static final String SIT_FORMS = "SIT forms";
	public static final String BEHAVIOR_MEASUREMENT= "Behavior Measurement (every/per)";
	public static final String TARGET_BEHAVIOR_CRITERIA= "Target Behavior Criteria";
	public static final String SKILL_TRAINING_CRITERIA= "Skill Training Criteria";
	public static final String ROLE_NOTACTIVE = "ROLE_NOTACTIVE";


	private static final String charset = "!0123456789abcdefghijklmnopqrstuvwxyz";
	public static Timestamp getCurrentTimestamp() {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		return timestamp;
	}

	public static String getPropertyValue(String msgKey) {
		String msgValue = SystemProperties.getProperty(msgKey);
		return msgValue;
	}

    public static String getPlatformProperyValue(String key)
    {
        return SystemProperties.getProperty(key +"." + OSUtils.getPlatform());
    }

	public static String getMailPropertyValue(String msgKey) {
		String msgValue = MailProperties.getProperty(msgKey);
		return msgValue;
	}
	
	public static String getEfaxPropertyValue(String msgKey) {
		String msgValue = EfaxProperties.getProperty(msgKey);
		return msgValue;
	}
	
	public static String getDataBasePropertyValue(String msgKey) {
		String msgValue = DatabaseProperties.getProperty(msgKey);
		return msgValue;
	}
	
	
	public static String getRandomPassword(){
		String password=getRandomString(Constants.PASSWORD_LENGTH);
		return password;
	}  
	
	public static String getRandomPassCode(){
		String passcode=getRandomString(Constants.PASSCODE_LENGTH);
		return passcode;
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
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i <= length; i++) {
			int pos = rand.nextInt(charset.length());
			sb.append(charset.charAt(pos));
		}
		return sb.toString();
	}

	public static String getRole(String roles) {
		if (roles.contains("ROLE_CHILD")) {
			roles = "child";
		} else if (roles.contains("ROLE_PARENT")) {
			roles = "parent";
		} else if (roles.contains("ROLE_ACADEMICIAN")) {
			roles = "academician";
		} else if (roles.contains("ROLE_ADMIN")) {
			roles = "admin";
		}
		return roles;
	}

	public static List<String> video_types = new ArrayList<String>();

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
	public static final String GLOBAL_ERROR = "globalError";

	@SuppressWarnings("unused")
	public static void main(String[] args) {
        System.out.println("platform = " + OSUtils.getPlatform());
        //String password = getPropertyValue("serviceError");
	}

	public static final String PENDING = "Pending";
	
	public static final String ADMIN_MAIL_ID="admin@gmail.com";
	
	public static final String TEMP_CEUOFFERING_FILE_UPLOAD_LOCATION="ceuofferingtempfileloc";
	
	public static final String PENDINGAPPROVAL = "Pending Parent Approval";
	public static final String ADMINMAILID = "admin@gmail.com";
	public static final String ADMIN_USER_ROLE = "systemadmin";
	public static final String PENDING_SUPERVISOR_APPROVAL = "Pending Supervisor Approval";
	public static final String ParentForm = "ParentForm";
	public static final String SupervisorForm = "SupervisorForm";
	public static final String caseallocate_submited = "caseallocate_submited";
	public static final String ssn_already_exist = "ssn_already_exist";
	public static final String OFFICE_STAFF = "Office Staff";
	public static final String ROLE_OFFICE = "ROLE_OFFICE";
	public static final String RE_CERTIFICATE_ISSUE="Re-issue Certificate";
	public static final String BACKGROUND_ACADEMIC="Background Academecian";
	public static final String TRAINING_ACADEMIC="Training Academecian";
	public static final String STATUSVALUE = "Active";
	public static final String AGENCY_ADMIN = "AgencyAdmin";
	public static final String agencyuser_submited = "agencyuser_submited";
	public static final String ROLE_AGENCY = "ROLE_AGENCY";
	public static final String DRIVER_CLASS = "datasource.driverClass";
	public static final String DB_URL = "datasource.url";
	public static final String DB_USERNAME = "datasource.username";
	public static final String DB_PWD = "datasource.password";
	public static final String JASPER_PATH = "config/template/jasper/";
	
	// Child Registration Send Mail
	public static final String PARENTA_FIRSTNAME = "parentAFirstName";
	public static final String PARENTA_LASTNAME = "parentALastName";
	public static final String PARENTA_EMAIL = "cparentAEmail";
	public static final String CHILD_USERNAME = "childUserName";
	public static final String NEW_PASSWORD = "newPassword";
	public static final String USER_EMAIL = "userEmail";

	/* user registration module jsp view name.*/
	
	public static final String USER_REGISTRATION_VIEW = "registration/usercommonregistration";
	public static final String USER_REGISTRATION_CHILD_VIEW = "registration/childuserregistration";
	public static final String USER_REGISTRATION_PARENT1_VIEW = "registration/parentuserregistrationfirst";
	public static final String USER_REGISTRATION_PARENT2_VIEW = "registration/parentuserregistrationsecond";	
	public static final String USER_REGISTRATION_ACADAMICIAN1_VIEW = "registration/academicanregistrationfirst";
	public static final String USER_REGISTRATION_ACADAMICIAN2_VIEW = "registration/academicanregistrationsecond";	
	public static final String CLINICAL_USER_REGISTRATION1_VIEW = "registration/clinicaluserregistrationfirst";
	public static final String CLINICAL_USER_REGISTRATION2_VIEW = "registration/clinicaluserregistrationsecond";
	public static final String CLINICAL_USER_REGISTRATION3_VIEW = "registration/clinicaluserregistrationthird";
	public static final String CLINICAL_USER_REGISTRATION4_VIEW = "registration/clinicaluserregistrationfourth";	
	public static final String OFFICE_REGISTRATION_FORM_VIEW = "registration/officeuserregistration";
	public static final String USER_REGISTRATION_SUCCESS_VIEW = "registration/userregistrationsuccess";

	/* Admin module jsp view name.*/
	
	public static final String ADMIN_DASHBOARD_VIEW = "admin/admindashboard";
	public static final String MASTER_VIEW = "admin/master";
	public static final String AGENCY_REGISTRATION_VIEW = "admin/agencyregistration";
	public static final String SERVICE_APPROVAL_ADMIN_VIEW = "admin/servicesapprovaladmin";
	public static final String DOCUMENT_MANAGER_ADMIN_VIEW = "admin/documentmanagementadmin";
	

	
	/* Agency module jsp view name.*/
	
	public static final String AGENCY_DASHBOARD_VIEW = "agency/agencydashboard";
	public static final String SERVICE_DEFINITION_VIEW = "agency/servicedefinition";
	public static final String SERVICE_OFFERING_VIEW = "agency/serviceoffering";
	public static final String USER_SERVICE_MAPPING_VIEW = "agency/userservicemapping";
	public static final String CLINICAL_USER_DETAILS = "agency/clinicaluserdetails";
	public static final String OFFICE_USER_DETAILS = "agency/officeuserdetails";
	public static final String CASE_ALLOCATION_VIEW = "agency/caseallocation";
	public static final String BILLING_VIEW = "agency/billing";
	public static final String PAYMENT_VIEW = "agency/payment";	
	public static final String CREDENTIAL_APPROVAL_VIEW = "agency/credentialapproval";
	public static final String CREDENTIAL_USER_DETAIL_VIEW = "agency/credentialuserdetails";
	public static final String CEU_CERTIFICATION_VIEW = "agency/ceucertification";
	public static final String CEU_OFFERING_VIEW = "agency/ceuoffering";
	public static final String CEU_OFFERING_CONTENT_VIEW = "agency/ceuofferingcontentpage";
	public static final String CEU_TRAINING_PREVIEW_VIEW = "agency/ceutrainingpreview";
	public static final String CUSTODIAN_MAPPING_VIEW = "agency/custodianmapping";

	/* Clinical module jsp view name.*/
	
	public static final String CLINICAL_CREDENTIAL_VIEW = "clinical/clinicalcredential";
	public static final String CLINICAL_GENERAL_VIEW = "clinical/clinicalgeneralinfo";
	public static final String CLINICAL_TRAINING_INFO_VIEW = "clinical/clinicaltraininginfo";
	public static final String CLINICAL_BACK_INFO_VIEW = "clinical/clinicalbackgroundInfo";
	public static final String CLINICAL_CEU_REQ_INFO_VIEW = "clinical/clinicalceureqinfo";
	public static final String SERVICE_APPROVAL_SUPERVISOR_VIEW = "clinical/servicesapprovalsupervisor";
	public static final String SUBSCRIBE_CEU_OFFERING_VIEW = "clinical/subscribeceuoffering";
	public static final String CLINICAL_CASE_VIEW = "clinical/clinicalcases";
	public static final String REPORT_SERVICES_VIEW = "clinical/reportservices";
	public static final String CEU_TRAINING_VIEW = "common/ceutraining";
	public static final String OFFICE_USER_DASHBOARD_VIEW = "common/officestaffdashboard";
	public static final String CHANGE_PASSWORD_VIEW = "common/changepassword";
	public static final String CUSTODIAN_DASHBOARD_VIEW = "custodian/custodiandashboard";

	
	/* Clinical module jsp view name.*/
	
	public static final String PUBLIC_CEU_VIEW = "public/publicceu";
	public static final String PUBLIC_CEU_OFFERING_VIEW = "public/publicceuoffering";
	public static final String PUBLIC_CEU_TRAINING_VIEW = "public/publicceutraining";
	public static final String PUBLIC_CEU_TRAINING_VIDEO_VIEW = "public/publicceutrainingvideo";

	/* Parent module jsp view name.*/
	
	public static final String PARENT_DASH_BOARD_VIEW = "parent/parentdashboard";
	public static final String PARENT_DASHBOARD_DYNAMIC_VIEW = "parent/parentdashboarddynamic";
	public static final String INDIVIDUAL_PLAN_VIEW = "parent/individualplan";
	public static final String SERVICE_APPROVAL_PARENT_VIEW = "parent/servicesapprovalparent";
	public static final String SUBSCRIBE_SERVICES_VIEW = "parent/subscribeservices";
	public static final String TODAYS_ACTIVITY_PARENT_VIEW = "parent/todaysactivityparentcalender";
	public static final String CHILD_DASHBOARD_PARENT_VIEW = "parent/childdashboardforparentview";
	public static final String PARENT_DOCUMENTS_VIEW = "parent/parentdocument";

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
	public static final String EDU_VIEW = "common/edu";
	public static final String FAMILY_VIEW = "common/family";
	public static final String COMMUNITY_VIEW = "common/community";
	public static final String CLINICAL_STAFF_VIEW = "common/clinicalstaff";
	public static final String CEU_TRAINING_VIDEO_VIEW = "common/ceutrainingvideo";
	public static final String FORGOT_PASSWORD_VIEW = "common/forgotpassword";
	public static final String SHOW_DOCUMENTMANAGEMENT_USER_VIEW="common/documentmanagementuser";
	/* Child jsp view name.*/
	
	
	/*
	 Bluehub common jSp
	 */
	
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
	
	public static final String CHILD_ACTIVITIES_VIEW = "child/childactivities";
	public static final String CHILD_DASHBOARD_VIEW = "child/childdashboard";
	public static final String CHILD_TOKEN_STORE_VIEW = "child/childtokenstore";
		
	//Patient View
	public static final String PATIENT_ADD_INFO_VIEW = "patient/patientDetails";
	public static final String USER_REG_VIEW = "patient/userRegistration";
	
	// Task Analysis
	public static final String TASK_ANALYSIS_VIEW = "taskanalysis/taskanalysis";
	public static final String CLINICAL_USER_BCABA = "BCaBA";
	public static final String CLINICAL_USER_BCBAD = "BCBA - D";
	public static final String CLINICAL_USER_BCBA_LEVEL_ONE = "BCBA Level 1";
	public static final String CLINICAL_USER_BCBA_LEVEL_TWO = "BCBA Level 2";
	public static final String CLINICAL_USER_BEHAVIOUR_ASSISTANT = "Behavior Assistant";
	public static final String CLINICAL_USER_LICENSED_PSYCOLOGIST_ABA = "Licensed Psychologist - ABA Srvc";
	public static final String CLINICAL_USER_LICENSED_PSYCOLOGIST_MH = "Licensed Psychologist - MH Srvc";
	public static final String CLINICAL_USER_LMFT = "LMFT";
	public static final String CLINICAL_USER_LMHC = "LMHC";
	public static final String CLINICAL_USER_LCSW = "LCSW";
	public static final Object RATINGREGISTERED = "Rating data updated Successfully";
	public static final String documentmanagement_submited="documentmanagement_submited";
	public static final String documentmanagement_deleted="documentmanagement_deleted";
	public static final String template_already_exist="template_already_exist";

	public static final String CUSTODIAN_REGISTERATION_SUBJECT = "custodian_registeration_subject";
	public static final String CUSTODIAN_REGISTERATION_CONTENTONE= "custodian_registeration_contentone";
	public static final String CUSTODIAN_REGISTERATION_CONTENTTWO = "custodian_registeration_contenttwo";

	public static final String CUSTODIAN_CHILD_SUBJECT = "custodianchild_subject";
	public static final String CUSTODIAN_CHILD_CONTENTONE= "custodianchild_contentone";
	public static final String CUSTODIAN_CHILD_CONTENTTWO = "custodianchild_contenttwo";
	public static final String CUSTODIAN_CHILD_BEST = "custodianchild_best";
	public static final String CUSTODIAN_CHILD_UNMAP_SUBJECT = "custodianchild_unmap_subject";
	
	public static final String CUSTODIAN_CHILDDELINK_SUBJECT= "custodianchilddelink_subject";
	public static final String CUSTODIAN_CHILDDELINK_CONTENTONE = "custodianchilddelink_contentone";
	public static final String CUSTODIAN_CHILDDELINK_CONTENTTWO = "custodianchilddelink_contenttwo";
	public static final String CUSTODIAN_CHILDDELINK_BEST= "custodianchilddelink_best";
	public static final String CUSTODIAN_CHILDDELINK_CONTENTDESC= "custodianchild_delinkcontentdesc";	

	public static final String STO_STATUS_UPDATE_NOTIFICATION_SUBJECT = "sto_status_update_notification_subject";
	public static final String STO_STATUS_UPDATE_NOTIFICATION_CONTENTONE= "sto_status_update_notification_contentone";
	public static final String STO_STATUS_UPDATE_NOTIFICATION_CONTENTTWO = "sto_status_update_notification_contenttwo";
	public static final String STO_STATUS_UPDATE_NOTIFICATION_CONTENTTHREE = "sto_status_update_notification_contentthree";
	public static final String STO_STATUS_UPDATE_NOTIFICATION_CONTENTFOUR = "sto_status_update_notification_contentfour";
	public static final String STO_STATUS_UPDATE_NOTIFICATION_CONTENTFIVE = "sto_status_update_notification_contentfive";
	public static final String STO_STATUS_UPDATE_NOTIFICATION_CONTENTSIX = "sto_status_update_notification_contentsix";
	public static final String STO_STATUS_UPDATE_NOTIFICATION_UPDATEFOR = "sto_status_update_notification_updatedfor";

	public static final String ACTIVITYCALENDER_PRIORITYVALUESET_SUBJECT="activitycalender_priorityvalueset_subject";
	public static final String ACTIVITYCALENDER_PRIORITYVALUESET_CONTENTONE="activitycalender_priorityvalueset_contentone";
	public static final String ACTIVITYCALENDER_PRIORITYVALUESET_CONTENTTWO="activitycalender_priorityvalueset_contenttwo";
	public static final String ACTIVITYCALENDER_PRIORITYVALUESET_CONTENTTHREE="activitycalender_priorityvalueset_contentthree";
	public static final String ACTIVITYCALENDER_PRIORITYVALUESET_CONTENTFOUR="activitycalender_priorityvalueset_contentfour";
	
	public static final String  IIPCHANGEDBY_CLINICAL_SUBJECT="iipchangedby_clinical_subject";
	public static final String	IIPCHANGEDBY_CLINICAL_CONTENTONE="iipchangedby_clinical_contentone";
	public static final String	IIPCHANGEDBY_CLINICAL_CONTENTTWO="iipchangedby_clinical_contenttwo";
	public static final String	IIPCHANGEDBY_CLINICAL_CONTENTTHREE="iipchangedby_clinical_contentthree";
	public static final String	IIPCHANGEDBY_CLINICAL_CONTENTFOUR="iipchangedby_clinical_contentfour";
	public static final String	IIPCHANGEDBY_CLINICAL_CONTENTFIVE="iipchangedby_clinical_contentfive";
	
	public static final String ACTIVITYCALENDERCHANGEDBY_CLINICIAN_SUBJECT="activitycalenderchangedby_clinician_subject";
	public static final String ACTIVITYCALENDERCHANGEDBY_CLINICIAN_CONTENTONE="activitycalenderchangedby_clinician_contentone";
	public static final String ACTIVITYCALENDERCHANGEDBY_CLINICIAN_CONTENTTWO="activitycalenderchangedby_clinician_contenttwo";
	public static final String ACTIVITYCALENDERCHANGEDBY_CLINICIAN_CONTENTTHREE="activitycalenderchangedby_clinician_contentthree";
	public static final String ACTIVITYCALENDERCHANGEDBY_CLINICIAN_CONTENTFOUR="activitycalenderchangedby_clinician_contentfour";
	public static final String ACTIVITYCALENDERCHANGEDBY_CLINICIAN_CONTENTFIVE="activitycalenderchangedby_clinician_contentfive";
	
	public static final String SITSCALECOLUMN_MEDIUMORHIGHISSET_BYPARENT_SUBJECT="sitscalecolumn_mediumorhighisset_byparent_subject";
	public static final String SITSCALECOLUMN_MEDIUMORHIGHISSET_BYPARENT_CONTENTONE="sitscalecolumn_mediumorhighisset_byparent_contentone";
	public static final String SITSCALECOLUMN_MEDIUMORHIGHISSET_BYPARENT_CONTENTTWO="sitscalecolumn_mediumorhighisset_byparent_contenttwo";
	public static final String SITSCALECOLUMN_MEDIUMORHIGHISSET_BYPARENT_CONTENTTHREE="sitscalecolumn_mediumorhighisset_byparent_contentthree";
	public static final String SITSCALECOLUMN_MEDIUMORHIGHISSET_BYPARENT_CONTENTFOUR="sitscalecolumn_mediumorhighisset_byparent_contentfour";
	
	// Task Progress Report
	public static final String TASK_REPORT_VIEW = "taskreport/taskreport";
	public static final String dtttlearning_submited = "dtttlearning_submited";
	public static final String TEMP = "temp";
	public static final String MASTER_AVAILABLE = "master_already_exist";
	public static final String Acquisition_Skill = "Acquisition Skill";
	public static final String Replacement_Skill = "Replacement Skill";
	public static final String SKILL_ADDED = "skill_name_added";
	public static final String SKILL_EXISTS = "skill_Exists";
	public static final String SKILL_MOVED_IIP = "skill_moved_success";
	public static final String ASSESSED_ADDED = "assessed_added_success";

	public static final String PLAN_SETUP_VIEW = "plan/plansetup";
	public static final String PLANSETUP_ADDED = "plansetup_added_success";
	public static final String PLANSETUP_REMOVED = "plansetup_removed_success";
	public static final String PLAN_ALREADY_EXIST="plan_already_exist";
	public static final String SUBSCRIP_PLAN_VIEW = "plan/subscriptionplan";

	public static final String PLAN_NOT_SUBSCRIBE = "Not Subscribed";
	public static final String PLAN_SUBSCRIBE = "Subscribed";
	public static final String PLAN_UN_SUBSCRIBE = "Unsubscribed";
	public static final String CONSENT_EXPIRE = "Consent Expired";


	
	public static final String PLAN_SUBSCIP_ADDED = "planssubscrip_added_success";
	public static final String CONSENT_ONE_ADDED = "consentform_added_success";

	public static final String SUBSCRIP_PLAN = "subscriptionplan_success";
	public static final String UNSUBSCRIP_PLAN = "unsubscriptionplan_success";
	public static final String SELF_PAY = "Self Pay";
	public static final String SUBSCRIBE_SERVICES_PARENT= "plan/parentsubscribeservices";
	public static final String SERVICE_OFFER_SUCCESS = "serviceoffer_success";
	public static final String policy_already_Exists = "policy_already_Exists";
	public static final String INVITE_TEAM_MEMBERS_VIEW = "plan/inviteteammembers";
	
	public static final String INVITE_TEAM_MEMBERS_CONSENTFOR_STATUS_PENDING_INVITATION = "Pending Invitation";
	public static final String INVITE_TEAM_MEMBERS_CONSENTFOR_STATUS_INVITED = "Invited";
	public static final String INVITE_TEAM_MEMBERS_CONSENTFORM_PARENT_SCREENPERMISSIONS = "BSA,IIP,Activity Calendar,Dashboard,Task Analysis,Document Mngmt";
	public static final String INVITE = "Invite";
	public static final String ROLE_INVITE = "ROLE_INVITE";

	public static final String INVITE_PASSWORD_SUBJECT = "invitepassword_subject";
	public static final String INVITE_PASSWORD_DEAR = "invitepassword_dear";
	public static final String INVITE_PASSWORD_CONTENT = "invitepassword_content";
	public static final String INVITE_PASSWORD_MAILCONTENT = "invitepassword_mailcontent";
	public static final String INVITE_PASSWORD_BEST = "invitepassword_best";
	
	public static final String PER_MONTH = "per month";
	public static final String PER_QUARTER = "per quarter";
	public static final String MONTHS = "months";
	public static final String QUARTER = "quarter";
	public static final String YEARS = "yeasr";

	public static final String REVIEW_GOAL_STATUS_INITIATED = "Initiated";
	public static final String REVIEW_GOAL_STATUS_MASTERED = "Mastered";
	public static final String PLAN_CANOT_ADD = "plan_cannot_added";

	public static final String PENDING_CONSENT = "Pending Consent";
	public static final String PENDING_SUBSCRIPTION = "Pending Subscription";
	public static final String PLAN_EXPIRED = "Subscription Expired";
	public static final String PLAN_SUBSCRIP_DELETED="plan_subscrip_deleted";
	
	public static final String BSA="BSA";
	public static final String IIP="IIP";
	public static final String ACTIVITY_CALENDAR="Activity Calendar";
	public static final String DASHBOARD="Dashboard";
	public static final String TASK_ANALYSIS="Task Analysis";
	public static final String DOC_MGMNT="Document Mngmt";
	
	public static final String SKILL_FOLDER = "SKILL_FOLDER";
	public static final String SKILL_FILENAME = "SkillsAssessmentReport";
	public static final String MAIL_SENT_SUCCESS="mail_sent_success";
	
	public static final String capture = "config/template/jasper/checkboxnontick.jpg";
	public static final String tickcapture = "config/template/jasper/checkboxtick.jpg";
	public static final String CONSENT_FORM_ONE = "consentFormOne";
	public static final String CONSENT_FORM_TWO = "consentFormTwo";
	public static final String ConsentForm = "ConsentForm";
	public static final String CONSENTONE_JRXML = "consentformone";
	public static final String CONSENTTWO_JRXML = "consentformtwo";
	public static final String PUBLIC_USER_REGISTRATION_VIEW = "registration/publicregistration";
	public static final String ROLE_PUBLIC = "ROLE_PUBLIC";
	public static final String PUBLIC_DASHBOARD_VIEW = "public/publicdashboard";
	public static final String PUBLIC_CEU_HOME = "common/publiccue";

	public static final String subscription_subject = "subscription_subject";
	public static final String subscription_belowplan = "subscription_belowplan";
	public static final String subscription_validtill = "subscription_validtill";
	public static final String unsubscription_belowplan = "unsubscription_belowplan";

	
	public static final String invitation_subject = "invitation_subject";
	public static final String invitation_content_one = "invitation_content_one";
	public static final String invitation_content_two = "invitation_content_two";
	public static final String invitation_content_three = "invitation_content_three";
	public static final String invitation_content_four = "invitation_content_four";
	public static final String NO_IMAGE = "config/template/jasper/noImage.jpg";

	public static final String PLAN_SUBSCIP_ADDED_ONE = "planssubscrip_added_success_one";
	public static final String PLAN_SUBSCIP_ADDED_TWO = "planssubscrip_added_success_two";

	public static final String SUBSCRIP_PLAN_ONE = "subscriptionplan_success_one";
	public static final String SUBSCRIP_PLAN_TWO = "subscriptionplan_success_two";
	
	public static final String DTT_MANUAL_TASK_YES = "Yes";
	public static final String DTT_MANUAL_TASK_NO = "No";
	
	public static final String subscriptionplan_validity = "subscriptionplan_validity";
	public static final String subscriptionplan_below_plan = "subscriptionplan_below_plan";
	public static final String subscriptionplan_will_expire_on = "subscriptionplan_will_expire_on";
	public static final String subscriptionplan_Kindly_renew = "subscriptionplan_Kindly_renew";
	public static final String subscriptionplan_expires_today = "subscriptionplan_expires_today";
	public static final String subscriptionplan_You_will = "subscriptionplan_You_will";
	public static final String subscriptionplan_Plan_invite = "subscriptionplan_Plan_invite";
	public static final String subscriptionplan_Your_invite = "subscriptionplan_Your_invite";
	public static final String subscriptionplan_Your_invite_one = "subscriptionplan_Your_invite_one";
	public static final String subscriptionplan_Your_invite_two = "subscriptionplan_Your_invite_two";
	public static final String subscriptionplan_Your_invite_three = "subscriptionplan_Your_invite_three";
	public static final String subscriptionplan_Your_invite_four = "subscriptionplan_Your_invite_four";
	public static final String EXP_RECURRINGPAY = "payment/exp_recurring_pay";
	public static final String subscription_service_charge = "subscription_service_charge";
	public static final String PAYMENT_PAID = "Paid";
	public static final String TEAM_MEMBER = "team member";
	public static final String subscription_payment_note = "subscription_payment_note";
	public static final String subscription_plan_payment = "subscription_plan_payment";
	public static final String subscription_has_subscribed = "subscription_has_subscribed";
	public static final String ceu_training_unsubscribed = "ceu_training_unsubscribed";
	public static final String braintree = "plan/braintree";
	public static final String sandbox = "sandbox";
	public static final String production = "production";
	public static final String development = "development";

	
	public static final String COURSE_BRAIN_TREE = "common/coursebraintree";
	public static final String PLAN_BRAIN_TREE = "common/planbraintree";	
	public static final String payment_gateway_error = "payment_gateway_error";
	public static final String reinforcerassesment = "parent/reinforcerassesment";

	
	public static final String Social_Reinforcers = "Social Reinforcers";
	public static final String Tangible_Reinforcers = "Tangible Reinforcers";
	public static final String Activity_Reinforcers = "Activity Reinforcers";
	public static final String Consumable_Reinforcers = "Consumable Reinforcers";
	public static final String Sensory_Reinforcers = "Sensory Reinforcers";
	
	public static final String reinforce_name_added = "reinforce_name_added";
	public static final String reinforce_Exists = "reinforce_Exists";
	public static final String reinforce_added_success = "reinforce_added_success";
	public static final String assessed_reinforce_updated_success = "assessed_reinforce_updated_success";
	public static final String reinforcers_Exists = "reinforcers_Exists";
	public static final String reinforcers_name_added = "reinforcers_name_added";
	public static final String RFA="RFA";
	
	public static final String REINFO_FOLDER = "REINFO_FOLDER";
	public static final String REINFO_FILENAME = "ReinforcesAssessmentReport";
	
	public static final String billing_details = "billing_details";
	public static final String billed_your = "billed_your";
	public static final String card_beginning = "card_beginning";
	public static final String amount_of = "amount_of";
	public static final String plan_valid = "plan_valid";
	public static final String billing_details_course = "billing_details_course";
	public static final String course_subscription = "course_subscription";
	
	public static final String TASK_ANALYSIS_BASELINE_VIEW = "taskanalysisbaseline/taskanalysisbaseline";
	public static final String TASK = "Task";
	public static final String DTT = "Dtt";
	public static final String PendingTADTT = "Pending TA / DTT";
	public static final String TACompleted = "TA / DTT completed";
	public static final String Pendingbaseline = "Pending Baseline";
	public static final String Baselined = "Baselined";
	public static final String donotreply = "donotreply";
	public static final String SERVICE_OFFER_SUCCESS_PLAN = "serviceoffer_success_plan";
	public static final String MANAGE_BASELINE_VIEW= "taskanalysisbaseline/managebaseline";

	public static final String Trials= "Trials";
	public static final String Mastery= "Mastery";
	public static final String Reduction_Behavior = "Reduction Behavior";

	public static final String reduction_behaviors_saved = "reduction_behaviors_saved";
	public static final String replacement_skill_saved = "replacement_skill_saved";
	public static final String acquisition_skill_saved = "acquisition_skill_saved";
	public static final String active = "active";

	public static final String thank_register = "thank_register";
	public static final String activate_register = "activate_register";
	public static final String confirm_register = "confirm_register";
	public static final String click_linkhere = "click_linkhere";
	public static final String login_userpwd = "login_userpwd";
	public static final String login_activated = "login_activated";
	public static final String login_invalid = "login_invalid";
	public static final String newsletter_success = "newsletter_success";
	public static final String enter_valid_id = "enter_valid_id";
	public static final String email_id_required = "email_id_required";
	public static final String SUPPORT_MAIL_ID = "support_mail_id";
	public static final String hello = "hello";
	public static final String kindly_use = "kindly_use";
	public static final String username_cont = "username_cont";
	public static final String password_cont = "password_cont";
	public static final String login_application = "login_application";
	
	public static final String todayactCalendar = "todayactCalendar";
	public static final String recordbehavior = "recordbehavior";
	
	public static final String click_linkview = "click_linkview";
	
	public static final String acc_activate = "acc_activate";
	
	// For audit trails Consttants
	
	public static final String PATIENT_REQUEST = "PATIENTREQUEST";
	public static final String PATIENT_SHARE = "PATIENTSHARE";
	
	
	public static final String PATIENT_DOCUMENTS = "CLINICAL_DOCUMENTS";
	
	public static final String PHYSICIAN_MAPPING = "PHYSICIANMAPPING";

	public static final String VISITS = "VISITS";
	public static final String SHARE_REQUEST = "SHARE_REQUEST";
	
	public static final String SHARE = "SHARE_CLINICAL_DOCUMENTS";
	
	
	
	public static final Integer INSERT = 0;
	public static final Integer UPDATE = 1;
	public static final Integer DELETE = 2;
	public static final Integer NON_DB = 3;
	
	
	// For audit trails Consttants

	// Wages 
	
	// Added For Request a Mail
	
	public static final String WAGES_COUNTRY_VIEW= "wages/wagescountry";
	
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
		
}

