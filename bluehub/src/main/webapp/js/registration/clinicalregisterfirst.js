

function pageReverseNavigation() {
	document.userRegistrationForm.action = contextPath
			+ "/getUserRegistrationBack.do";
	document.userRegistrationForm.submit();
}
function pageNavigation() {
	// validation
	var validator = $("#userRegistrationForm").validate({
		rules : {
			dob : "required",
			gender : "required",
			ssn : "required"
		},
		messages : {
			dob : "Please enter DOB",
			gender : "Please select a gender",
			ssn : "Please enter SSN#"
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
						document.userRegistrationForm.action = contextPath
								+ "/clinicalUserRegistrationSecondPage.do";
						document.userRegistrationForm.submit();

					}
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}
};

function callCheckUserEmail() {
	// get the form values		        
	var mailId = $('#email').val();
	var education = $('#email').val();
	$.ajax({
		type : "GET",
		url : contextPath + "/checkUserEmail.do",
		dataType : "json",
		data : "userMail=" + mailId,
		success : function(response) {
			// we have the response	

			var obj = response;
			var status = obj.status;
			var message = obj.message;
			if (status == 'Yes') {
				alert(message);
				document.getElementById("email").value = "";
				document.getElementById("email").focus();
			}

		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}

$(function() {
	$("#ssn").inputmask({
		"mask" : "***-**-****"
	});
	
	$('.alphaNumericClass').alphanumeric();

	$("#homePhone").inputmask({
		"mask" : "***-***-****"
	});
  	$("#homePhone").ForceNumericOnly();
  	$("#ssn").ForceNumericOnly();


	var date = new Date();
	var currentMonth = date.getMonth();
	var currentDate = date.getDate();
	var currentYear = date.getFullYear();

	$("#dobdatepicker").datepicker({
		yearRange : "1913:+0",
		changeMonth : true,
		changeYear : true,
		maxDate : new Date(currentYear, currentMonth, currentDate)
	});
	$("#dlExpDate").datepicker({
		yearRange : "1960:+20",
		changeMonth : true,
		changeYear : true
	});
	$("#autoInsurancePolicyStateDate").datepicker({
		yearRange : "1960:+20",
		changeMonth : true,
		changeYear : true
	});
	$("#autoInsurancePolicyEndDate").datepicker({
		yearRange : "1960:+20",
		changeMonth : true,
		changeYear : true
	});
});

function getParams() {
	var idx = document.URL.indexOf('?');
	var params = new Array();
	if (idx != -1) {
		var pairs = document.URL.substring(idx + 1, document.URL.length).split(
				'&');
		for ( var i = 0; i < pairs.length; i++) {
			nameVal = pairs[i].split('=');
			params[nameVal[0]] = nameVal[1];
		}
	}
	return params;
}
params = getParams();

$(document).ready(function() {
	$('#firstName').focus();
	var genderVal = $('#genderHidden').val();
	var internVal = $('#internHidden').val();
	$("#gender").val(genderVal);
	$("#intern").val(internVal);

});
