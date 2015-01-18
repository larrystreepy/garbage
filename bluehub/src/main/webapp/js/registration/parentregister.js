$(function() {

	$('.alphaNumericClass').alphanumeric();

	$("#ssn").inputmask({
		"mask" : "***-**-****"
	});

	$("#aparentHomePhone").inputmask({
		"mask" : "***-***-****"
	});

	$("#aparentWorkPhone").inputmask({
		"mask" : "***-***-****"
	});

	$("#bparentHomePhone").inputmask({
		"mask" : "***-***-****"
	});

	$("#bparentWorkPhone").inputmask({
		"mask" : "***-***-****"
	});
	
	$("#aparentHomePhone").ForceNumericOnly();
	$("#aparentWorkPhone").ForceNumericOnly();
	$("#bparentHomePhone").ForceNumericOnly();
	$("#bparentWorkPhone").ForceNumericOnly();
	$("#ssn").ForceNumericOnly();


	var date = new Date();
	var currentMonth = date.getMonth();
	var currentDate = date.getDate();
	var currentYear = date.getFullYear();

	$("#datepicker").datepicker({
		yearRange : "1913:+0",
		changeMonth : true,
		changeYear : true,
		maxDate : new Date(currentYear, currentMonth, currentDate)
	});
});

function callCheckAUserEmail(parentVal) {
	// get the form values		        
	var mailId = $('#aparentEmail').val();
	if (mailId != "") {
		$.ajax({
			type : "GET",
			dataType : "json",
			url : contextPath + "/checkUserEmail.do",
			data : "userMail=" + mailId,
			success : function(response) {
				// we have the response		       
				var obj = response;
				var status = obj.status;
				var message = obj.message;
				if (status == 'Yes') {
					$('#emailAValid').html(message);
					$('#emailAValid').show();
					document.getElementById("aparentEmail").value = "";
					document.getElementById("aparentEmail").focus();
				} else {
					$('#emailAValid').html('');
					$('#emailAValid').hide();
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}

}

function callCheckBUserEmail(parentVal) {
	// get the form values		        
	var mailId = $('#bparentEmail').val();
	if (mailId != "") {
		$.ajax({
			type : "GET",
			dataType : "json",
			url : contextPath + "/checkUserEmail.do",
			data : "userMail=" + mailId,
			success : function(response) {
				// we have the response		       
				var obj = response;
				var status = obj.status;
				var message = obj.message;
				if (status == 'Yes') {
					$('#emailBValid').html(message);
					$('#emailBValid').show();
					document.getElementById("bparentEmail").value = "";
					document.getElementById("bparentEmail").focus();
				} else {
					$('#emailBValid').html('');
					$('#emailBValid').hide();
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}

}
function callParentSubmitOld() {
	//validation

	var validator = $("#parentsInfoForm")
			.validate(
					{
						rules : {
							"parentsInfoForm.userNameofYourChild" : "required",
							firstName : "required",
							lastName : "required",
							dob : "required",
							gender : "required",
							ssn : "required",
							addressLine1 : "required",
							city : "required",
							state : "required",
							"parentsInfoForm.invitePlan" : "required"
						},
						messages : {
							"parentsInfoForm.userNameofYourChild" : "Please enter child user name",
							firstName : "Please enter your first name",
							lastName : "Please enter your last name",
							dob : "Please select a dob",
							gender : "Please select a gender",
							ssn : "Please enter your ssn",
							addressLine1 : "Please enter your address",
							city : "Please enter your city",
							state : "Please enter your state",
							"parentsInfoForm.invitePlan" : "Please select a invite plan"
						}
					});
	var validCheck = validator.form();
	if (validCheck) {
		document.parentForm.action = contextPath
				+ "/getChildInfoFormForParentUser.do";
		document.parentForm.submit();
	}
};

function callParentSubmit() {
	//validation

	var userNameofYourChild = $.trim($('#userNameofYourChild').val());
	$('#emailBValid').hide();
	$('#emailAValid').hide();
	if (userNameofYourChild != '') {
		$('#emailValid').hide();
		var userParentA = $('#userParentA').val();
		var userParentB = $('#userParentB').val();
		if (userParentA == 'true') {
			callParentA();
		} else if (userParentB == 'true') {
			callParentB();
		} else {
			callParentA();
		}
	} else {
		$('#emailValid').html("Please enter child user name");
		$('#emailValid').show();
		$('#userNameofYourChild').focus();
	}

};

function callParentA() {
	// GKan ssn non mandatory for parent
	var validator = $("#parentsInfoForm")
			.validate(
					{
						rules : {
							"parentsInfoForm.userNameofYourChild" : "required",
							firstName : "required",
							lastName : "required",
							dob : "required",
							gender : "required",
							addressLine1 : "required",
							city : "required",
							state : "required",
							"parentsInfoForm.invitePlan" : "required",
							"parentsInfoForm.aparentEmail" : {
								required : true,
								email : true
							},
						},
						messages : {
							"parentsInfoForm.userNameofYourChild" : "Please enter child user name",
							firstName : "Please enter your first name",
							lastName : "Please enter your last name",
							dob : "Please select a dob",
							gender : "Please select a gender",
							addressLine1 : "Please enter your address",
							city : "Please enter your city",
							state : "Please enter your state",
							"parentsInfoForm.invitePlan" : "Please select a invite plan",
							"parentsInfoForm.aparentEmail" : {
								required : "Email Id is required",
								email : "Enter valid Email Id"
							},
						},
						focusInvalid : false,
						invalidHandler : function(form, validator) {
							if (!validator.numberOfInvalids())
								return;

							$(validator.errorList[0].element).focus();
						}
					});
	var validCheckParentA = validator.form();
	var ssn = $('#ssn').val();
	if (ssn != "") {
		$.ajax({
			type : "GET",
			dataType : "json",
			url : contextPath + "/checkSsnValidation.do",
			data : "ssn=" + ssn,
			success : function(response) {
				// we have the response		       
				var obj = response;
				var status = obj.status;
				var message = obj.message;
				if (status == 'Yes') {
					$('#ssnValid').html(message);
					$('#ssnValid').fadeIn().delay(4000).fadeOut('slow');
					document.getElementById("ssn").value = "";
					document.getElementById("ssn").focus();
				} else {
					$('#ssnValid').html('');
					$('#ssnValid').hide();

					if (validCheckParentA) {
						$('#submit_btn').attr("disabled", true);
						document.parentForm.action = contextPath + "/getChildInfoFormForParentUser.do";
						document.parentForm.submit();
					}
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	} else {	// can submit directly need not validate ssn if it is blank gkan
		$('#ssnValid').html('');
		$('#ssnValid').hide();

		if (validCheckParentA) {
			$('#submit_btn').attr("disabled", true);
			document.parentForm.action = contextPath + "/getChildInfoFormForParentUser.do";
			document.parentForm.submit();
		}
	}

};

function callParentB() {
	// GKan ssn non mandatory for parent
	var validator = $("#parentsInfoForm")
			.validate(
					{
						rules : {
							"parentsInfoForm.userNameofYourChild" : "required",
							firstName : "required",
							lastName : "required",
							dob : "required",
							gender : "required",
							addressLine1 : "required",
							city : "required",
							state : "required",
							"parentsInfoForm.bparentEmail" : {
								required : true,
								email : true
							},
						},
						messages : {
							"parentsInfoForm.userNameofYourChild" : "Please enter child user name",
							firstName : "Please enter your first name",
							lastName : "Please enter your last name",
							dob : "Please select a dob",
							gender : "Please select a gender",
							addressLine1 : "Please enter your address",
							city : "Please enter your city",
							state : "Please enter your state",
							"parentsInfoForm.bparentEmail" : {
								required : "Email Id is required",
								email : "Enter valid Email Id"
							},
						},
						focusInvalid : false,
						invalidHandler : function(form, validator) {
							if (!validator.numberOfInvalids())
								return;

							$(validator.errorList[0].element).focus();
						}
					});
	var validCheck = validator.form();
	var ssn = $('#ssn').val();
	if (ssn != "") {
		$.ajax({
			type : "GET",
			dataType : "json",
			url : contextPath + "/checkSsnValidation.do",
			data : "ssn=" + ssn,
			success : function(response) {
				// we have the response		       
				var obj = response;
				var status = obj.status;
				var message = obj.message;
				if (status == 'Yes') {
					$('#ssnValid').html(message);
					$('#ssnValid').fadeIn().delay(4000).fadeOut('slow');
					document.getElementById("ssn").value = "";
					document.getElementById("ssn").focus();
				} else {
					$('#ssnValid').html('');
					$('#ssnValid').hide();

					if (validCheck) {
						$('#submit_btn').attr("disabled", true);
						document.parentForm.action = contextPath + "/getChildInfoFormForParentUser.do";
						document.parentForm.submit();
					}
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	} else { // can submit directly need not validate ssn if it is blank gkan
		$('#ssnValid').html('');
		$('#ssnValid').hide();

		if (validCheck) {
			$('#submit_btn').attr("disabled", true);
			document.parentForm.action = contextPath + "/getChildInfoFormForParentUser.do";
			document.parentForm.submit();
		}
	}

};

function callCheckUserEmail() {
	$('#submitbut').removeAttr("disabled");
	// get the form values		            
	var mailId = $('#userNameofYourChild').val();
	$.ajax({
		type : "GET",
		url : contextPath + "/checkUserChildEmail.do",
		dataType : "json",
		data : "userMail=" + mailId,
		success : function(response) {
			// we have the response		    
			var obj = response;
			var status = obj.status;
			var message = obj.message;
			if (status == 'Yes') {
				$('#emailValid').html("");
				$('#emailValid').hide();
				loadchild();
			} else {
				$('#emailValid').html(message);
				$('#emailValid').show();
				$('#userNameofYourChild').focus();
				$('#childId').val('');
				$('#childName').val('');
				$('#userNameofYourChild').val('');

				$('#invitePlan').removeAttr("disabled");
				$('#bparentEmail').attr('readonly', true);
				$('#bparentHomePhone').attr('readonly', true);
				$('#bparentCellPhone').attr('readonly', true);
				$('#bparentWorkPhone').attr('readonly', true);
				$('#aparentEmail').attr('readonly', true);
				$('#aparentHomePhone').attr('readonly', true);
				$('#aparentCellPhone').attr('readonly', true);
				$('#aparentWorkPhone').attr('readonly', true);
				$('#userParentA').val(false);
				$('#userParentB').val(false);

			}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}

function loadchild() {
	var parentEmail = $('#userEmailId').val();
	var parentHomePhone = $('#userHomePhone').val();
	var parentCellPhone = $('#userCellPhone').val();
	var parentWorkPhone = $('#userWorkPhone').val();
	var childMail = $('#userNameofYourChild').val();
	$
			.ajax({
				type : "post",
				url : contextPath + "/getParentDetail.do",
				cache : false,
				dataType : "json",
				data : 'childMail=' + childMail,
				success : function(response) {
					var obj = response;
					var userParent = obj.userParent;
					var userParentA = obj.userParentA;
					var userParentB = obj.userParentB;
					var childName = obj.childName;
					var childId = obj.childId;
					$('#childId').val(childId);
					$('#childName').val(childName);

					if (userParentA) {
						var aparentEmail = obj.aparentEmail;
						var aparentHomePhone = obj.aparentHomePhone;
						var aparentCellPhone = obj.aparentCellPhone;
						var aparentWorkPhone = obj.aparentWorkPhone;
						$('#aparentEmail').val(aparentEmail);
						$('#aparentHomePhone').val(aparentHomePhone);
						$('#aparentCellPhone').val(aparentCellPhone);
						$('#aparentWorkPhone').val(aparentWorkPhone);

					} else {

						$('#aparentEmail').val('');
						$('#aparentHomePhone').val('');
						$('#aparentCellPhone').val('');
						$('#aparentWorkPhone').val('');
					}
					if (userParentB) {
						var bparentEmail = obj.bparentEmail;
						var bparentHomePhone = obj.bparentHomePhone;
						var bparentCellPhone = obj.bparentCellPhone;
						var bparentWorkPhone = obj.bparentWorkPhone;

						$('#bparentEmail').val(bparentEmail);
						$('#bparentHomePhone').val(bparentHomePhone);
						$('#bparentCellPhone').val(bparentCellPhone);
						$('#bparentWorkPhone').val(bparentWorkPhone);
					} else {

						$('#bparentEmail').val('');
						$('#bparentHomePhone').val('');
						$('#bparentCellPhone').val('');
						$('#bparentWorkPhone').val('');
					}
					var isParent = true;
					if (userParentA && userParentB) {
						$('#emailValid')
								.html(
										"Only two parents are allowed to register for a child.");
						$('#emailValid').show();
						$('#userNameofYourChild').focus();
						$('#submitbut').attr("disabled", true);
					} else if (userParentA) {
						$('#emailValid').html("");
						$('#userParentB').val(isParent);
						$('#userParentA').val(false);
						$('#bparentEmail').val(parentEmail);
						$('#bparentHomePhone').val(parentHomePhone);
						$('#bparentCellPhone').val(parentCellPhone);
						$('#bparentWorkPhone').val(parentWorkPhone);

						$('#parentInfoId').html("About Parent B");
						$('#invitePlan').attr("disabled", true);
						$('#parent_a').removeClass("parent_color");
						$('#parent_b').addClass("parent_color");

						$('#bparentEmail').removeAttr("readonly");
						$('#bparentHomePhone').removeAttr("readonly");
						$('#bparentCellPhone').removeAttr("readonly");
						$('#bparentWorkPhone').removeAttr("readonly");

						$('#emailA').hide();
						$('#phoneA').hide();
						$('#emailB').html("*");
						$('#phoneB').html("*");
						$('#emailB').show();
						$('#phoneB').show();
					} else {
						$('#emailValid').html("");
						$('#parent_b').removeClass("parent_color");
						$('#parent_a').addClass("parent_color");

						$('#aparentEmail').val(parentEmail);
						$('#aparentHomePhone').val(parentHomePhone);
						$('#aparentCellPhone').val(parentCellPhone);
						$('#aparentWorkPhone').val(parentWorkPhone);
						$('#userParentA').val(isParent);
						$('#userParentB').val(false);
						$('#submitbut').removeAttr("disabled");

						$('#parentInfoId').html("About Parent A");
						$('#invitePlan').removeAttr("disabled");

						$('#aparentEmail').removeAttr("readonly");
						$('#aparentHomePhone').removeAttr("readonly");
						$('#aparentCellPhone').removeAttr("readonly");
						$('#aparentWorkPhone').removeAttr("readonly");

						$('#emailA').show();
						$('#phoneA').show();
						$('#emailB').hide();
						$('#phoneB').hide();
					}

				},
				error : function() {
					alert('Error while request..');
				}
			});
}

function goBackParent() {
	document.parentForm.action = contextPath + "/getParentBackUserRegistration.do";
	document.parentForm.submit();
};
$(document).ready(function() {
	$('#userNameofYourChild').focus();
	$("#demographicsRace").val($('#raceVal').val());
	$("#demographicsethnicity").val($('#ethnicityVal').val());
	$("#gender").val($('#genderVal').val());
	$("#state").val($('#stateVal').val());
	$("#invitePlan").val($('#invitePlanVal').val());

	var userParentA = $('#userParentA').val();
	var userParentB = $('#userParentB').val();
	if (userParentA == 'true') {
		$('#parent_b').removeClass("parent_color");
		$('#parent_a').addClass("parent_color");
		$('#parentInfoId').html("About Parent A");
		$('#invitePlan').removeAttr("disabled");

		$('#aparentEmail').removeAttr("readonly");
		$('#aparentHomePhone').removeAttr("readonly");
		$('#aparentCellPhone').removeAttr("readonly");
		$('#aparentWorkPhone').removeAttr("readonly");

		$('#emailA').show();
		$('#phoneA').show();
		$('#emailB').hide();
		$('#phoneB').hide();
	}
	if (userParentB == 'true') {
		$('#parentInfoId').html("About Parent B");
		$('#invitePlan').attr("disabled", true);
		$('#parent_a').removeClass("parent_color");
		$('#parent_b').addClass("parent_color");

		$('#bparentEmail').removeAttr("readonly");
		$('#bparentHomePhone').removeAttr("readonly");
		$('#bparentCellPhone').removeAttr("readonly");
		$('#bparentWorkPhone').removeAttr("readonly");

		$('#emailA').hide();
		$('#phoneA').hide();
		$('#emailB').html("*");
		$('#phoneB').html("*");
		$('#emailB').show();
		$('#phoneB').show();
	}

});
