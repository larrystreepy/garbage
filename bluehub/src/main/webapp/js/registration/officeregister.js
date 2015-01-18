function callBackOfficeUser() {
	document.officeUserRegistrationForm.action = contextPath
			+ "/getBackOfficeUserRegister.do";
	document.officeUserRegistrationForm.submit();
};

function pageNavigation() {
	// driving license validation made non mandatory - G Kan
	var validator = $("#officeUserRegistrationForm").validate({
		rules : {
			gender : "required",
			dob : "required",
			ssn : "required"
		},
		messages : {
			gender : "Please Select a Gender",
			dob : "Please Select a DOB",
			ssn : "Please enter SSN"
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
		$
				.ajax({
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
								document.officeUserRegistrationForm.action = contextPath
										+ "/registrationOfficeUserSubmit.do";
								document.officeUserRegistrationForm.submit();
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
	
	$("#ssn").ForceNumericOnly();
	$('.alphaNumericClass').alphanumeric();
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
	$('#nickName').focus();
	$("#demographicsRace").val($('#raceVal').val());
	$("#demographicsEthnicity").val($('#ethnicityVal').val());
	$("#gender").val($('#genderVal').val());
	$("#state").val($('#stateVal').val());
});

function termCheck(ele, id) {
	x = document.getElementById(id);
	if (ele.checked == true) x.disabled = false;
	else x.disabled = true;
}

	$(document).ready(function() {
		//open popup
		$("#terms").click(function() {
			$("#termsandcondition").fadeIn(400);
			positionPopup();
			
});

		//close popup
		$("#close").click(function() {
			$("#termsandcondition").fadeOut(400);
		});
	});
	$(document).ready(function() {
		//open popup
		$("#policy").click(function() {
			$("#privacypolicy").fadeIn(400);
			positionPopup1();
			
});

//close popup
$("#pclose").click(function() {
	$("#privacypolicy").fadeOut(400);
	});
});		
	//position the popup at the center of the page
	function positionPopup() {
		if (!$("#termsandcondition").is(':visible')) {
			return;
		}
		$("#termsandcondition").css({
		    right:'27%'
		   
		    });
		}
		$(window).bind('resize', positionPopup);  
		//position the popup1 at the center of the page
		function positionPopup1() {
			if (!$("#privacypolicy").is(':visible')) {
				return;
			}
			$("#privacypolicy").css({
			    right:'27%'
			  });
			}
			$(window).bind('resize', positionPopup1);     
