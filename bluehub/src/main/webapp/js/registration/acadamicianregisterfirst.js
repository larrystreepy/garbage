function callBackAcademician() {
	document.userAcademicianForm.action = contextPath
			+ "/getAcademicianBackUserRegistration.do";
	document.userAcademicianForm.submit();
};

function pageNavigation() {
	var validator = $("#userAcademicianForm")
			.validate(
					{
						rules : {
							gender : "required",
							dob : "required",
							ssn : "required",
							"userAcademicianForm.institutionName" : "required",
							"userAcademicianForm.institutionAddress" : "required",
							"userAcademicianForm.department" : "required",
							"userAcademicianForm.adminEmail" : {
								required : true,
								email : true
							},
							"userAcademicianForm.administrator" : "required",
						},
						messages : {
							dob : "Please Select a DOB",
							gender : "Please Select a Gender",
							ssn : "Please enter SSN",
							"userAcademicianForm.institutionName" : "Please enter Institution Name",
							"userAcademicianForm.institutionAddress" : "Please enter Institution Address",
							"userAcademicianForm.department" : "Please enter Department",
							"userAcademicianForm.adminEmail" : {
								required : "Admin Email Id is Required",
								email : "Enter valid Email Id"
							},
							"userAcademicianForm.administrator" : "Please enter Administrator"
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
						document.userAcademicianForm.action = contextPath
								+ "/getAcademicianDocument.do";
						document.userAcademicianForm.submit();

					}
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}

};

$(function() {

	$("#ssn").inputmask({
		"mask" : "***-**-****"
	});

	$('.alphaNumericClass').alphanumeric();

	$("#instPhone").inputmask({
		"mask" : "***-***-****"
	});
	
  	$("#instPhone").ForceNumericOnly();
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

$(document).ready(function() {

	$("#demographicsRace").val($('#raceVal').val());
	$("#demographicsEthnicity").val($('#ethnicityVal').val());
	$("#gender").val($('#genderVal').val());
	$("#state").val($('#stateVal').val());
	$("#instState").val($('#insStateVal').val());
});