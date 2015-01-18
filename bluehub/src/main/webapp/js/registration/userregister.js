/*$(function() {
	$('.alphaNumericClass').alphanumeric();
	$("#ssn").inputmask({
		"mask" : "***-**-****"
	});
	$("#office").inputmask({
		"mask" : "***-***-****"
	});
	$("#phoneHome").inputmask({
		"mask" : "***-***-****"
	});
	
	$("#office").ForceNumericOnly();
	$("#phoneHome").ForceNumericOnly();
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
});*/



		
function commonPhyRegistrationValidation() {
	
	var validator =  $("#userPhyForm").validate({
        focusCleanup: true,
        errorClass: "text-danger",
        errorPlacement: function(error, element) {
            if ( element.parent().hasClass('nice-checkbox') || element.parent().hasClass('nice-radio') || element.parent().hasClass('input-group') ) {
                error.appendTo( element.parent().parent() );
            }
            else{
                error.appendTo( element.parent() );
            }
        },
        rules: {
        	phyUserEmail: {
                required: true,
                email: true
            },

        	
            phyPassword: {
                required: true,
            },
            phyConfirmPassword : {
				required : true,
				equalTo : "#phyPassword"
			},
			phyRecoveryAnswer: "required",
        	
			phyRecoveryAnswer: {
                required: true,
               
            },phyRecoveryQuestion: "required",
            phyRecoveryQuestion: {
                required: true,
               
            },
            
            selectOrganization: "required",
        	
            selectOrganization: {
                required: true,
               
            },
            selectPractice: "required",
        	
            selectPractice: {
                required: true,
               
            }
        },
        messages : {
        	phyUserEmail : {
				required : "Email Id is Required",
				email : "Enter valid Email Id"
			},
			phyPassword : "Please enter Password",
			phyConfirmPassword : {
				required : "Please enter Confirm Password",
				equalTo : "Password doesn't Match"
			},
			phyRecoveryQuestion : "Please enter Recovery Question",
			phyRecoveryAnswer : "Please enter Recovery Answer",
	 
			selectOrganization : "Select Organization",
			selectPractice : "Select Practice",
			
		 
		
		
		
		}
  

    });
	
	var validCheck = validator.form();
	
	return validCheck;
}

function commonUserRegistrationValidation() {
	
	var validator =  $("#userForm").validate({
        focusCleanup: true,
        errorClass: "text-danger",
        errorPlacement: function(error, element) {
            if ( element.parent().hasClass('nice-checkbox') || element.parent().hasClass('nice-radio') || element.parent().hasClass('input-group') ) {
                error.appendTo( element.parent().parent() );
            }
            else{
                error.appendTo( element.parent() );
            }
        },
        rules: {
        	userEmail: {
                required: true,
                email: true
            },

        	
        	password: {
                required: true,
            },
            confirmPassword : {
				required : true,
				equalTo : "#password"
			},
            recoveryAnswer: "required",
        	
            recoveryAnswer: {
                required: true,
               
            },recoveryQuestion: "required",
            recoveryQuestion: {
                required: true,
               
            },
        },
        messages : {
			userEmail : {
				required : "Email Id is Required",
				email : "Enter valid Email Id"
			},
			password : "Please enter Password",
			confirmPassword : {
				required : "Please enter Confirm Password",
				equalTo : "Password doesn't Match"
			},
			recoveryQuestion : "Please enter Recovery Question",
			recoveryAnswer : "Please enter Recovery Answer",
			
		}
  

    });
	
	var validCheck = validator.form();
	
	return validCheck;
}
function commonRegistrationValidation() {
	var validator = $("#userForm").validate({
		rules : {
			userEmail : {
				required : true,
//				email : {
//					depends: function() {
//                        return $("#myRole").val() != 'Child';
//                    }
//				}
			},
			password : "required",
			confirmPassword : {
				required : true,
				equalTo : "#password"
			},
			recoveryQuestion : "required",
			recoveryAnswer : "required",
			/*yourArea : "required",
			myRole : "required",
			firstName : "required",
			lastName : "required",
			phoneHome : {
				required : true,

			},
			phoneCell : {
				digits : true
			},

			addressLine1 : "required",
			city : "required",
			state : "required"*/
		},
		messages : {
			userEmail : {
				required : "Email Id is Required",
				email : "Enter valid Email Id"
			},
			password : "Please enter Password",
			confirmPassword : {
				required : "Please enter Confirm Password",
				equalTo : "Password doesn't Match"
			},
			recoveryQuestion : "Please enter Recovery Question",
			recoveryAnswer : "Please enter Recovery Answer",
		/*	yourArea : "Please select a Group",
			myRole : "Please select a Role",
			firstName : "Please enter your First Name",
			lastName : "Please enter your Last Name",
			phoneHome : {
				required : "Please enter Home Phone",

			},
			phoneCell : {
				digits : "Given Phone Number is not valid"
			},

			addressLine1 : "Please enter your Address",
			city : "Please Enter your City",
			state : "Please Select your State"*/
		},
		focusInvalid : false,
		invalidHandler : function(form, validator) {
			if (!validator.numberOfInvalids())
				return;

			$(validator.errorList[0].element).focus();
		}
	});
	$('#groupValid').hide();
	var validCheck = validator.form();

	return validCheck;
}

function pageNavigation() {
	// validation				
	$('#emailValid').html('');
	$('#emailValid').hide();
	$('#ssnValid').html('');
	$('#ssnValid').hide();
	var agencyInstitution = $('#agencyInstitution').val();
	$('#agencyValid').hide();

	var validCheck = null;
	var role = $("#myRole").val();

	validCheck = commonRegistrationValidation();

	//if form is valid proceed further		 
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
						fnCallRegisterSubmit();
					}
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	} else {
		if (validCheck) {
			fnCallRegisterSubmit();
		}
	}
};

function fnCancelBluehubUser() {
	window.location.href=contextPath+ "/j_spring_security_logout";
}

function fnPhyRegisterBluehubUser() {
	
	validCheck = commonPhyRegistrationValidation();
	
	
	if(validCheck){ 
	document.userPhyForm.action = contextPath + "/getPhyUserRegisterForm.do";
	document.userPhyForm.submit();
}
}
function fnRegisterBluehubUser() {
	
	//validCheck = commonRegistrationValidation();
	validCheck = commonUserRegistrationValidation();
	
	if(validCheck){ 
	document.userForm.action = contextPath + "/getUserRegisterForm.do";
	document.userForm.submit();
}
}

function fnCallRegisterSubmit() {
	var roleVal = document.getElementById("myRole").value;
	var amVal = document.getElementById("yourArea").value;
	var agencyInstitution = $('#agencyInstitution').val();
	if (amVal == "Family Member" && roleVal == "Child") {
		document.userForm.action = contextPath + "/getChildUserRegistForm.do";
		document.userForm.submit();
	} else if (amVal == "Family Member"
			&& (roleVal == "Parent" || roleVal == "Guardian")) {
		document.userForm.action = contextPath + "/getParentsInfoForm.do";
		document.userForm.submit();

	} else if (amVal == "Clinical Staff") {

		if (agencyInstitution == "") {
			$('#agencyValid').html("Please select a Agency");
			$('#agencyValid').show();
		} else {
			$('#agencyValid').hide();
			document.userForm.action = contextPath
					+ "/clinicalUserRegistrationFirstPage.do";
			document.userForm.submit();
		}

	} else if (amVal == "Academician") {
		document.userForm.action = contextPath
				+ "/showRegistrationAcademician.do";
		document.userForm.submit();
	} else if (amVal == "Office Staff") {
		if (agencyInstitution == "") {
			$('#agencyValid').html("Please select a Agency");
			$('#agencyValid').show();
		} else {
			$('#agencyValid').hide();
			document.userForm.action = contextPath
					+ "/showOfficeUserRegister.do";
			document.userForm.submit();
		}

	} else {

		$('#groupValid').html("This Role cannot be Registered.");
		$('#groupValid').show();
	}
}


function callCheckPhyUserEmail() {
	// get the form values		        
	var mailId = $('#phyUserEmail').val();
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
					$('#emailValid1').html(message);
					$('#emailValid1').fadeIn().delay(4000).fadeOut('slow');
					document.getElementById("phyUserEmail").value = "";
					document.getElementById("phyUserEmail").focus();
				} else {
					$('#emailValid1').html('');
					$('#emailValid1').hide();
				}
			},
			error : function(e) {
				//alert('Error: ' + e);
			}
		});
	}
}

function callCheckUserEmail() {
	// get the form values		        
	var mailId = $('#userEmail').val();
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
					$('#emailValid').html(message);
					$('#emailValid').fadeIn().delay(4000).fadeOut('slow');
					document.getElementById("userEmail").value = "";
					document.getElementById("userEmail").focus();
				} else {
					$('#emailValid').html('');
					$('#emailValid').hide();
				}
			},
			error : function(e) {
				//alert('Error: ' + e);
			}
		});
	}
}

/*function getUserRoles(userGroup) {
	$('#groupValid').hide();
	if (userGroup == "") {

		$("#agencyEmail").val('');
		$('#agencyEmail').attr('readonly', true);

		document.getElementById("myRole").innerHTML = "";
		var combo = document.getElementById("myRole");
		var defaultOption = document.createElement("option");
		defaultOption.text = 'Select';
		defaultOption.value = '';
		combo.add(defaultOption);

		document.getElementById("agencyInstitution").innerHTML = "";
		var combo1 = document.getElementById("agencyInstitution");
		var defaultOption1 = document.createElement("option");
		defaultOption1.text = 'Select';
		defaultOption1.value = '';
		combo1.add(defaultOption1);

	} else {
		$("#agencyEmail").val('');
		//getAgencyName();
		// get the form values		               
		$.ajax({
			type : "GET",
			url : contextPath + "/getUserRoles.do",
			data : "userGroup=" + userGroup,
			async : false,
			success : function(response) {

				var myRoleHidden = $('#myRoleHidden').val();
				var parsedJson = $.parseJSON(response);
				document.getElementById("myRole").innerHTML = "";

				var combo = document.getElementById("myRole");
				for ( var i = 0; i < parsedJson.length; i++) {
					var option = document.createElement("option");
					option.text = parsedJson[i];
					option.value = parsedJson[i];
					if (myRoleHidden == parsedJson[i]) {
						option.setAttribute("selected", "selected");
					}
					combo.add(option);
				}
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});
	}

}*/

/*$(document).ready(function() {
	$('#userEmail').focus();
	var genderVal = $('#genderHidden').val();
	var demographicsRaceVal = $('#demographicsRaceHidden').val();
	var demographicsEthnicityVal = $('#demographicsEthnicityHidden').val();
	var stateVal = $('#stateHidden').val();
	var yourArea = $('#yourAreaHidden').val();
	$("#demographicsRace").val(demographicsRaceVal);
	$("#state").val(stateVal);
	$("#demographicsEthnicity").val(demographicsEthnicityVal);
	$("#gender").val(genderVal);
	$("#yourArea").val(yourArea);
	if (yourArea != "") {
		getUserRoles(yourArea);
	}
	

	var agency = $('#agencyHidden').val();
	var agencyEmail = $('#agencyEmailHidden').val();
	$("#agencyInstitution").val(agency);
	$("#agencyEmail").val(agencyEmail);
});
*/
function getAgencyName() {
	var agencyGroup = $("#yourArea").val();
	if (agencyGroup == "Office Staff" || agencyGroup == "Clinical Staff") {
		// get the form values		    

		$('#agencyEmail').attr('readonly', false);
		$.ajax({
			type : "GET",
			url : contextPath + "/getAgencyName.do",
			async : false,
			success : function(response) {
				var parsedJson = $.parseJSON(response);
				document.getElementById("agencyInstitution").innerHTML = "";
				var combo = document.getElementById("agencyInstitution");
				var defaultOption = document.createElement("option");
				defaultOption.text = 'Select';
				defaultOption.value = '';
				combo.add(defaultOption);
				for ( var i = 0; i < parsedJson.length; i++) {
					var option = document.createElement("option");
					option.text = parsedJson[i].firstName;
					option.value = parsedJson[i].userId;
					combo.add(option);
				}
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});

	} else {
		$("#agencyEmail").val('');
		$('#agencyEmail').attr('readonly', true);
		document.getElementById("agencyInstitution").innerHTML = "";
		var combo = document.getElementById("agencyInstitution");
		var defaultOption = document.createElement("option");
		defaultOption.text = 'Select';
		defaultOption.value = '';
		combo.add(defaultOption);
	}

}

function getAgencyEmail() {
	$('#agencyValid').hide();
	// get the form values		
	var userId = $("#agencyInstitution").val();
	$.ajax({
		type : "GET",
		dataType : "json",
		url : contextPath + "/getAgencyEmail.do",
		data : "agencyid=" + userId,
		async : false,
		success : function(response) {
			var obj = response;
			var message = obj.message;
			$("#agencyEmail").val(message);
		},
		error : function(e) {
			alert('Error: ' + e.responseText);
		}
	});
}



function loadPractice() {

	var orgid=document.getElementById('selectOrganization').value;
	
	$.ajax({
		type : "GET",
		url : contextPath + "/loadpractice.do",
		data : "orgid=" + orgid,
		cache : false,
		async : false,
		success : function(response) {

			//var myRoleHidden = $('#myRoleHidden').val();
			var parsedJson = $.parseJSON(response);
			document.getElementById("selectPractice").innerHTML = "";

			var combo = document.getElementById("selectPractice");

			var defaultOption = document.createElement("option");
		    defaultOption.text = 'Select';
		    defaultOption.value = '';
		    combo.add(defaultOption);
			for (var i = 0; i < parsedJson.length; i++) {
				var option = document.createElement("option");
				option.text = parsedJson[i].practicename;
				option.value = parsedJson[i].id;
				/* if (myRoleHidden == parsedJson[i]) {
				 option.setAttribute("selected", "selected");
				} */
				combo.add(option);
			}
		},
		error : function(e) {
			 //alert('Error: ' + e.responseText);
		}
	});


	
}


function loadOrganization(){
	
	$.ajax({
		type : "GET",
		url : contextPath
				+ "/loadorganization.do",
		// data : "userGroup=" + userGroup,
		cache : false,
		async : false,
		success : function(response) {

			//var myRoleHidden = $('#myRoleHidden').val();
			var parsedJson = $.parseJSON(response);
			document.getElementById("selectOrganization").innerHTML = "";

			var combo = document
					.getElementById("selectOrganization");
			var defaultOption = document.createElement("option");
		    defaultOption.text = 'Select';
		    defaultOption.value = '';
		    combo.add(defaultOption);
			for (var i = 0; i < parsedJson.length; i++) {
				var option = document.createElement("option");
				option.text = parsedJson[i].organizationname;
				option.value = parsedJson[i].id;
				/* if (myRoleHidden == parsedJson[i]) {
				 option.setAttribute("selected", "selected");
				} */
				combo.add(option);
			}
			//loadPractice();
		},
		error : function(e) {
			 //alert('Error: ' + e.responseText);
		}
	});
	
}



function loadPhyOrganization(){
	
	$.ajax({
		type : "GET",
		url : contextPath
				+ "/loadorganization.do",
		// data : "userGroup=" + userGroup,
		cache : false,
		async : false,
		success : function(response) {

			//var myRoleHidden = $('#myRoleHidden').val();
			var parsedJson = $.parseJSON(response);
			document.getElementById("selectPhyOrganization").innerHTML = "";

			var combo = document
					.getElementById("selectPhyOrganization");
			var defaultOption = document.createElement("option");
		    defaultOption.text = 'Select';
		    defaultOption.value = '-1';
		    combo.add(defaultOption);
			for (var i = 0; i < parsedJson.length; i++) {
				var option = document.createElement("option");
				option.text = parsedJson[i].organizationname;
				option.value = parsedJson[i].id;
				/* if (myRoleHidden == parsedJson[i]) {
				 option.setAttribute("selected", "selected");
				} */
				combo.add(option);
			}
			//loadPractice();
		},
		error : function(e) {
			 //alert('Error: ' + e.responseText);
		}
	});
	
}