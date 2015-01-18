//This method allows only numeric value to enter
$.fn.ForceNumericOnly = function() {
	return this
			.each(function() {
				$(this)
						.keydown(
								function(e) {
									var key = e.charCode || e.keyCode || 0;
									return (key == 8 || key == 9 || key == 46
											|| key == 190 || // normal .
											key == 110 || // keypad .
											(key >= 37 && key <= 40)
											|| (key >= 48 && key <= 57) || (key >= 96 && key <= 105));
								})
			})
};
$(function() {
	if ($("#ssn")) {
		$("#ssn").inputmask({
			"mask" : "***-**-****"
		});

		$("#ssn").ForceNumericOnly();
	}
});

var searchVal = 0;
if (document.getElementById('disableButton')) {
	document.getElementById('disableButton').disabled = true;
}
if (document.getElementById('enableButton')) {
	document.getElementById('enableButton').disabled = true;
}

function loadSearchTable() {
	var name;
	if (document.getElementById('patientInformations')) {
		document.getElementById('patientInformations').style.display = "block";
	}
	if (document.getElementById('searchButton')) {
		document.getElementById('searchButton').style.display = "block";
	}

	var content = '';
	content += '<table style="width: 100%" id="renderUserListTable" class="table table-hover table-bordered">';
	content += '<thead>';
	content += '<th></th>';
	content += '<th>First Name</th>';
	content += '<th>Last Name</th>';
	content += '<th>DOB</th>';
	content += '<th>Address</th>';
	content += '<th>Status</th>';
	content += '</thead>';
	document.getElementById('patientInformations').innerHTML = content;
	if (searchVal == 1) {

		name = document.getElementById("firstNames").value;

	}
	if (searchVal == 3) {

		name = document.getElementById("lastNames").value;
	}
	if (searchVal == 4) {

		name = document.getElementById("dobs").value;
	}
	if (searchVal == 5) {

		name = document.getElementById("ssns").value;
	}

	if (searchVal == 0) {

		name = "default";
		searchVal = 6;
	}

	if (name) {
		$
				.ajax({
					type : "GET",
					url : contextPath + "/administrator/patientDetails.do",
					/*data : "firstName=" + name,*/
					data : {
						"firstName" : name,
						"searchVal" : searchVal
					},
					/* data: "firstName=" +name+"&searchVal="+searchVal,	*/
					cache : false,
					async : false,
					success : function(response) {

						var parsedJson = $.parseJSON(response);
						var status;
						if (parsedJson) {
							for ( var i = 0; i < parsedJson.length; i++) {
								status = parsedJson[i].status;
								//alert(status)
								if (status == 0) {
									status = "Inactive";
								} else {
									status = "Active";
								}
								content += '<tr onclick="statusEnable('
										+ parsedJson[i].status + ',' + i
										+ ')">';
								content += '<td ><input id="selectOption'
										+ i
										+ '" type="radio" name="selectRadio" value="'
										+ parsedJson[i].userid
										+ '" onclick="statusEnable('
										+ parsedJson[i].status + ',' + i
										+ ')"></td>';
								content += '<td id="agencytd' + i
										+ '"  class="user_list_link">'
										+ parsedJson[i].firstname + '</td>';
								content += '<td id="agencytd' + i
										+ '"  class="user_list_link">'
										+ parsedJson[i].lastname + '</td>';
								content += '<td id="emailtd' + i
										+ '"  class="user_list_link">'
										+ parsedJson[i].dateofbirth + '</td>';
								content += '<td id="addresstd' + i
										+ '"  class="user_list_link">'
										+ parsedJson[i].address + '</td>';
								content += '<td id="statustd' + i
										+ '"  class="user_list_link">' + status
										+ '</td>';
								content = content + '</tr>';
							}
						}

						content += '</table>';
						document.getElementById('patientInformations').innerHTML = content;

					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
	}
}


function changeSearch() {

	var x = document.getElementById("mySelect").value;
	searchVal = x;
	if (x == 1) {

		document.getElementById('firstName').style.display = 'block';
		document.getElementById('lastName').style.display = 'none';
		document.getElementById('dob').style.display = 'none';
		document.getElementById('searchssn').style.display = 'none';

	} else if (x == 2) {

		$.ajax({
			type : "GET",
			url : contextPath + "/administrator/adminorganizations.do",
			// data : "userGroup=" + userGroup,
			cache : false,
			async : false,
			success : function(response) {

				//var myRoleHidden = $('#myRoleHidden').val();
				var parsedJson = $.parseJSON(response);
				document.getElementById("selectOrganization").innerHTML = "";

				var combo = document.getElementById("selectOrganization");
				for ( var i = 0; i < parsedJson.length; i++) {
					var option = document.createElement("option");
					option.text = parsedJson[i].organizationname;
					option.value = parsedJson[i].id;
					/* if (myRoleHidden == parsedJson[i]) {
					 option.setAttribute("selected", "selected");
					} */
					combo.add(option);
				}
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});

		document.getElementById('firstName').style.display = 'none';
		document.getElementById('lastName').style.display = 'none';
		document.getElementById('dob').style.display = 'none';
		document.getElementById('searchssn').style.display = 'none';

	} else if (x == 3) {
		document.getElementById('lastName').style.display = 'block';

		document.getElementById('firstName').style.display = 'none';
		document.getElementById('dob').style.display = 'none';
		document.getElementById('searchssn').style.display = 'none';

	} else if (x == 4) {
		document.getElementById('dob').style.display = 'block';

		document.getElementById('firstName').style.display = 'none';
		document.getElementById('lastName').style.display = 'none';
		document.getElementById('searchssn').style.display = 'none';

	} else if (x == -1) {

		searchVal = 0;
	} else {

		document.getElementById('searchssn').style.display = 'block';

		document.getElementById('firstName').style.display = 'none';
		document.getElementById('lastName').style.display = 'none';
		document.getElementById('dob').style.display = 'none';

	}
}

function statusEnable(status, row) {

	document.getElementById("selectOption" + row).checked = true;

	document.getElementById('resetPwdBn').disabled = false;
	document.getElementById("hdnStatusId").value = row;
	if (status == "0") {

		document.getElementById('disableButton').disabled = true;
		document.getElementById('enableButton').disabled = false;
	} else {

		document.getElementById('disableButton').disabled = false;
		document.getElementById('enableButton').disabled = true;
	}

}

function statusDisable() {
	document.getElementById('enableButton').disabled = false;
}

function statusDisableActivate() {
	var radios = document.getElementsByName('selectRadio'), value = '';

	for ( var i = radios.length; i--;) {
		if (radios[i].checked) {
			value = radios[i].value;
			break;
		}
	}
	var selectedId = value;

	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/patientDisableStatus.do",
				data : "selectedId=" + selectedId,
				cache : false,
				async : false,
				success : function(response) {
					document.getElementById("status").style.display = "block";
					document.getElementById("status").innerHTML = "Patient Disabled Successfully ";
					
					//setTimeout('fnloadOrg()', 3000);
					changeSearch();
					loadSearchTable();

					document.getElementById('disableButton').disabled = true;
					document.getElementById('enableButton').disabled = true;
				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});

}

function fnloadOrg() {
	document.getElementById("status").style.display = "none";
}

function statusEnableActivate() {
	var radios = document.getElementsByName('selectRadio'), value = '';

	for ( var i = radios.length; i--;) {
		if (radios[i].checked) {
			value = radios[i].value;
			break;
		}
	}

	var selectedId = value;

	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/patientEnableStatus.do",
				data : "selectedId=" + selectedId,
				cache : false,
				async : false,
				success : function(response) {
					document.getElementById("status").style.display = "block";
					document.getElementById("status").innerHTML = "Patient Enabled Successfully ";
					

					document.getElementById('disableButton').disabled = true;
					document.getElementById('enableButton').disabled = true;

					//setTimeout('fnloadOrg()', 3000);
					changeSearch();
					loadSearchTable();

				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});
}
function fnResetPassword() {
	document.getElementById('resetPwdBn').disabled = true;
	var radios = document.getElementsByName('selectRadio'), value = '';

	for ( var i = radios.length; i--;) {
		if (radios[i].checked) {
			value = radios[i].value;
			break;
		}
	}

	var selectedId = value;

	$.ajax({
		type : "GET",
		url : contextPath + "/resetUserPassword.do",
		dataType : "json",
		data : "userId=" + selectedId,
		cache : false,
		async : false,
		success : function(response) {
			var obj = response;
			var message = obj.message;
			flag = false;
			document.getElementById("status").style.display = "block";
			document.getElementById("status").innerHTML = message;
			document.getElementById('resetPwdBn').disabled = true;
			fnSetTimeout("status", 3000);
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}

function callPatCheckSSN() {
	var flag = true;
	document.getElementById("exception").style.display = "none";

	var ssn = $('#ssn').val();
	if (ssn != "") {
		$
				.ajax({
					type : "GET",
					url : contextPath + "/checkssn.do",
					dataType : "json",
					data : "ssn=" + ssn,
					cache : false,
					async : false,
					success : function(response) {
						var obj = response;
						var status = obj.status;
						var message = obj.message;
						if (status == 'Yes') {
							flag = false;
							document.getElementById("exception").style.display = "block";
							document.getElementById("exception").innerHTML = message;
							document.getElementById("exception").value = "";
						}
					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
	}

	return flag;
}

function fnSetTimeout(id, time) {
	if (time == undefined)
		time = 3000;
	displayMsgId = id;
	document.getElementById(displayMsgId).style.display = "block";
	setTimeout('fnClearMsgField()', time);
}
function fnClearMsgField() {
	document.getElementById(displayMsgId).style.display = "none";
}

function convertStringToDateCommon(dateAsString) {
	var dateArray = dateAsString.split("/");
	var date = dateArray[2] + '-' + dateArray[0] + '-' + dateArray[1];
	var dateObject = new Date(date);
	return dateObject;
}

function fnPatientPersonalForm() {
	var flag = true;
	if (document.getElementById('insurance_eff_date')
			&& document.getElementById('insurance_exp_date')) {
		var eff_date = document.getElementById('insurance_eff_date').value;
		var exp_date = document.getElementById('insurance_exp_date').value;

		var date_eff = convertStringToDateCommon(eff_date);
		var date_exp = convertStringToDateCommon(exp_date);

		if (eff_date && exp_date) {
			if (date_eff >= date_exp) {
				flag = false;
				document.getElementById("insuranceSpan").innerHTML = "Expiration date should be greater than Effective Date";
				fnSetTimeout("insuranceSpan", 3000);
				document.getElementById('insurance_exp_date').focus();
			}
		}
	}

	var validCheck = patientFormValidation();

	var validSSN = callPatCheckSSN();

	if (validCheck && flag && validSSN) {
		document.patientPersonalForm.action = contextPath
				+ "/patient/savePatientPersonalDetails.do";
		document.patientPersonalForm.submit();
	}
}

function fnUpdatePatientPersonalForm() {
	var flag = true;
	if (document.getElementById('insurance_eff_date')
			&& document.getElementById('insurance_exp_date')) {
		var eff_date = document.getElementById('insurance_eff_date').value;
		var exp_date = document.getElementById('insurance_exp_date').value;

		var date_eff = convertStringToDateCommon(eff_date);
		var date_exp = convertStringToDateCommon(exp_date);

		if (eff_date && exp_date) {
			if (date_eff >= date_exp) {
				flag = false;
				document.getElementById("insuranceSpan").innerHTML = "Expiration date should be greater than Effective Date";
				fnSetTimeout("insuranceSpan", 3000);
				document.getElementById('insurance_exp_date').focus();
			}
		}
	}

	var validCheck = patientFormValidation();


	if (validCheck && flag) {
		document.patientPersonalForm.action = contextPath
				+ "/patient/updatePatientPersonalDetails.do";
		document.patientPersonalForm.submit();
	}
}

function patientFormValidation() {
	var validator = $("#patientPersonalForm").validate(
			{
				focusCleanup : true,
				errorClass : "text-danger",
				errorPlacement : function(error, element) {
					if (element.parent().hasClass('nice-checkbox')
							|| element.parent().hasClass('nice-radio')
							|| element.parent().hasClass('input-group')) {
						error.appendTo(element.parent().parent());
					} else {
						error.appendTo(element.parent());
					}
				},
				rules : {
					firstname : {
						required : true
					},
					/*middlename : {
						required : true
					},*/
					lastname : {
						required : true
					},
					contactNo : {
						required : true
					},
					street : {
						required : true
					},
					city : {
						required : true
					},
					zip : {
						required : true
					},
					policy_number : {
						required : true
					},
					/*ssn : {
						required : true,
					},*/
					dob : {
						required : true,
					}
				},
				messages : {
					firstname : {
						required : "First name is Required"
					},
					/*middlename : {
						required : "Middle name is Required"
					},*/
					lastname : {
						required : "Last Name is Required"
					},
					contactNo : {
						required : "Contact Number is Required"
					},
					street : {
						required : "Street is Required"
					},
					city : {
						required : "City is Required"
					},
					zip : {
						required : "Zip is Required"
					},
					policy_number : {
						required : "Policy Number is Required"
					},
					/*ssn : {
						required : "SSN is Required"
					},*/
					dob : {
						required : "DOB is Required"
					}
				}

			});

	var validCheck = validator.form();
	return validCheck;
}
