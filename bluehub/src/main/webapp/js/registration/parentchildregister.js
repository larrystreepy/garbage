function callLoginScreen() {
	document.parentsInfoForm.action = contextPath + "/login.do";
	document.parentsInfoForm.submit();
};

function goBackParent() {
	document.parentsInfoForm.action = contextPath + "/getBackParentUser.do";
	document.parentsInfoForm.submit();
}


function callLoaderClass(){
		$('#uploadprocess').show();
		$('#uploadprocess').addClass("loader");
}
	

function callCheckSsn() {
	// get the form values		        
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
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}
}

function pageNavigation() {
	var parentDob=$('#parentDob').val();
	var childDob=$('#datepicker').val();
	//parentDateOfBirthValidation(childDob,parentDob);
	
	//validation
	var validator = $("#parentsInfoForm")
			.validate(
					{
						rules : {
							"childUserRegistrationForm.dob" : "required",
							"childUserRegistrationForm.gender" : "required",
							"childUserRegistrationForm.residenceType" : "required",
							"childUserRegistrationForm.legalStatusCompetency" : "required",
							"childUserRegistrationForm.school" : "required",
							"childUserRegistrationForm.eseCriteria" : "required",
							"childUserRegistrationForm.allergies" : "required"
						},
						messages : {
							"childUserRegistrationForm.dob" : "Please select a dob",
							"childUserRegistrationForm.gender" : "Please select a gender",
							"childUserRegistrationForm.residenceType" : "Please select a residence type",
							"childUserRegistrationForm.legalStatusCompetency" : "Please select legal status / competency",
							"childUserRegistrationForm.eseCriteria" : "Please select ESE Criteria",
							"childUserRegistrationForm.allergies" : "Please select a allergies"
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
	var ssnVal = $('#ssnVal').val();
	if (ssn != "" && ssnVal != ssn) {
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
						document.parentsInfoForm.action = contextPath
								+ "/registrationParentSubmit.do";
						document.parentsInfoForm.submit();

					}
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	} else {

		if (validCheck) {
			callLoaderClass();
			$('#submit_btn').attr("disabled", true);
			document.parentsInfoForm.action = contextPath
					+ "/registrationParentSubmit.do";
			document.parentsInfoForm.submit();

		}

	}

};

$(function() {

	$("#ssn").inputmask({
		"mask" : "***-**-****"
	});
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

function selectedComboVal(optionsToSelect, select) {
	if (select != null) {
		for ( var i = 0, l = select.options.length, o; i < l; i++) {
			o = select.options[i];
			if (optionsToSelect.indexOf(o.text) != -1) {
				o.selected = true;
			}
		}
	}
};

$(document).ready(function() {

	var regsiterval = $('#regsiterval').val();
	if (regsiterval == '0') {
		$('#cntDivId').hide();
		$('#registerDivId').show();
	} else {
		callDisplayPopUpParent();
		$('#cntDivId').show();
		$('#registerDivId').hide();
	}

	$('#nickName').focus();
	var optionsToSelect = $('#allerVal').val();
	var select = document.getElementById('allergies');
	selectedComboVal(optionsToSelect, select);
	optionsToSelect = $('#emploVal').val();
	select = document.getElementById('employment');
	selectedComboVal(optionsToSelect, select);
	optionsToSelect = $('#daytimeVal').val();
	select = document.getElementById('daytime');
	selectedComboVal(optionsToSelect, select);
	optionsToSelect = $('#criterVal').val();
	select = document.getElementById('criteria');
	selectedComboVal(optionsToSelect, select);
	optionsToSelect = $('#routineVal').val();
	select = document.getElementById('routine');
	selectedComboVal(optionsToSelect, select);

	optionsToSelect = $('#schoolVal').val();
	select = document.getElementById('school');
	selectedComboVal(optionsToSelect, select);

	$("#legalStatusCompetency").val($('#legalVal').val());
	$("#residenceType").val($('#resTypVal').val());
	$("#demographicsRace").val($('#raceVal').val());
	$("#demographicsethnicity").val($('#ethnicityVal').val());
	$("#gender").val($('#genderVal').val());

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
