//This method allows only numeric value to enter
/*$.fn.ForceNumericOnly =
 function()
 {
 return this.each(function()
 {
 $(this).keydown(function(e)
 {
 var key = e.charCode || e.keyCode || 0;
 return (
 key == 8 ||
 key == 9 ||
 key == 46 ||
 key == 190 ||   // normal .
 key == 110 ||   // keypad .
 (key >= 37 && key <= 40) ||
 (key >= 48 && key <= 57) ||
 (key >= 96 && key <= 105));
 })
 })
 };
 $(function() {

 if($("#ssn")){
 $("#ssn").inputmask({
 "mask" : "***-**-****"
 });

 $("#ssn").ForceNumericOnly();
 }
 });
 function clearSSN(){
 document.getElementById("exception").style.display = "none";
 }*/
function callCheckSSN() {
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
function fnCancelBluehubUser() {
	window.location.href = contextPath + "/j_spring_security_logout";
};

function fnPhysicianVisitForm() {
	validCheck = physicianVisitValidation('phyVisitForm');

	if (validCheck) {
		document.phyVisitForm.action = contextPath
				+ "/physician/savePhyVisitDetails.do";
		document.phyVisitForm.submit();
	}
};

function convertStringToDateCommon(dateAsString) {
	var dateArray = dateAsString.split("/");
	var date = dateArray[2] + '-' + dateArray[0] + '-' + dateArray[1];
	var dateObject = new Date(date);
	return dateObject;
}

function fnPhysicianPersonalForm() {

	var validCheck = physicianFormValidation();

	if (validCheck) {
		document.phyPersonalForm.action = contextPath
				+ "/savePhyPersonalDetails.do";
		document.phyPersonalForm.submit();
	}
}

function fnUpdatePhysicianPersonalForm() {
	var validCheck = physicianFormValidation();


	if (validCheck) {
		document.phyPersonalForm.action = contextPath
				+ "/physician/updatePhysicianPersonalDetails.do";
		document.phyPersonalForm.submit();
	}
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
//prithivi
function physicianFormValidation() {
	var validator = $("#phyPersonalForm").validate(
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
					middlename : {
						required : true
					},
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
					degree : {
						required : true
					}
				},
				messages : {
					firstname : {
						required : "First name is Required"
					},
					middlename : {
						required : "Middle name is Required"
					},
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
					degree : {
						required : "Degree is Required"
					}
				}
			});

	var validCheck = validator.form();
	return validCheck;
}

function physicianVisitValidation(formId) {
	var validator = $("#" + formId).validate(
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
					reason_for_visit : "required",
					prescription : "required",
					organization : "required",
					practice : "required",
				},
				messages : {
					userEmail : {
						required : "Email Id is Required",
						email : "Enter valid Email Id"
					},
					reason_for_visit : "Please enter Reason for visit",
					prescription : "Please enter Prescription",
					organization : "Please select one Organization",
					practice : "Please select one Practice",
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

function loadPhysicianVisitDetails(patId) {
	document.getElementById("panel-typeahead").style.display = "none";
	var content = '';
	content += '<table class="table table-hover table-bordered" id="organizationTable">';
	content += '<thead><th>Patient Name</th><th>Date of Visit</th><th>Reason for Visit</th><th>Physician Consulted</th><th>Prescription of Physician</th></thead>';
	document.getElementById('phyVisitsTable').innerHTML = content;

	$
			.ajax({
				type : "GET",
				url : contextPath + "/physician/getPhysicianVisitDetails.do",
				data : "patientId=" + patId,
				async : false,
				success : function(response) {

					var parsedJson = $.parseJSON(response);
					var dateOfVisit = dov = '';
					if (parsedJson && parsedJson.length > 0) {
						for ( var i = 0; i < parsedJson.length; i++) {
							dateOfVisit = parsedJson[i][3];

							if (dateOfVisit == undefined
									|| dateOfVisit == 'undefined') {
								dov = '';
							} else {
								dateOfVisit = new Date(parsedJson[i][3]);
								dov = (dateOfVisit.getMonth() + 1) + "/"
										+ dateOfVisit.getDate() + "/"
										+ dateOfVisit.getFullYear();
							}
							content += '<tr><td id="patient' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][1] + '</td>';
							content += '<td id="visitdate' + i
									+ '"  class="user_list_link">' + dov
									+ '</td>';
							content += '<td id="reason' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][5] + '</td>';
							content += '<td id="physician' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][2] + '</td>';
							content += '<td id="prescription' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][4] + '</td>';
							content += '<td style="border: none; margin-left: 2px;"> <input title="Delete Visit" type="button" value="" id="delDocRow" class="del_row" onclick="deletePhyVisit('
									+ parsedJson[i][0] + ')"/></td>';
							content += '<td style="border: none; margin-left: 2px;"> <input title="Edit Visit" type="button" value="" id="editDocRow" class="editIcon" onclick="editPhyVisit('
									+ parsedJson[i][0] + ');"/></td>';

							content = content + '</tr>';
						}
					} else {
						content += '<tr><td colspan="5" align="center" > No Data Found.</td></tr>';
					}
					content += '</table>';

					document.getElementById('phyVisitsTable').innerHTML = content;

					document.getElementById("panel-typeahead").style.display = "block";
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}

function loadPhysicianOrganizations() {
	$
			.ajax({
				type : "GET",
				url : contextPath + "/physician/getPhysicianOrganizations.do",
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);
					var dateOfVisit = dov = '';
					if (parsedJson && parsedJson.length > 0) {
						document.getElementById("hdnOrganizationId").value = parsedJson[0][0];
						document.getElementById("hdnPracticeId").value = parsedJson[0][1];
					}
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}

function deletePhyVisit(id) {
	$.ajax({
		type : "GET",
		url : contextPath + "/physician/deletephysicianvisit.do",
		data : "phyid=" + id,
		success : function(response) {
			window.location.href = contextPath
					+ "/physician/physicianVisitDetails.do";
		},
		error : function(e) {
		}
	});
}

function editPhyVisit(id) {
	$
			.ajax({
				type : "GET",
				url : contextPath + "/physician/editphysicianvisit.do",
				cache : false,
				data : "phyid=" + id,
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);
					var dateOfVisit = dov = '';
					if (parsedJson[0][3] == undefined
							|| parsedJson[0][3] == 'undefined') {
						dov = '';
					} else {
						dateOfVisit = new Date(parsedJson[0][3]);
						dov = (dateOfVisit.getMonth() + 1) + "/"
								+ dateOfVisit.getDate() + "/"
								+ dateOfVisit.getFullYear();
					}
					$("#date_of_visit").val(dov);
					$("#reason_for_visit").val(parsedJson[0][5]);
					$("#prescription").val(parsedJson[0][4]);
					$("#hdnPhysicianId").val(parsedJson[0][9]);
					$("#hdnPatientId").val(parsedJson[0][8]);
					$("#patientname").val(parsedJson[0][1]);
					$("#physicianname").val(parsedJson[0][2]);
					$("#hiddenPKID").val(parsedJson[0][0]);
					document.getElementById('physicianVisitTitle').innerHTML = "Edit Visit";
					document.getElementById('phyVisitSubmit').value = "Update";
					document.getElementById('phyVisitSubmit').setAttribute(
							'onclick', 'updatePhyvisit();');
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}

function updatePhyvisit() {

	validCheck = physicianVisitValidation('phyVisitForm');

	if (validCheck) {
		var phyid = $('#hdnPhysicianId').val();
		var visitDate = $('#date_of_visit').val();
		var reason = $('#reason_for_visit').val();
		var prescription = $('#prescription').val();
		var orgid = $('#hdnOrganizationId').val();
		var practice = $('#hdnPracticeId').val();
		var patid = $('#hdnPatientId').val();
		var pkid = $("#hiddenPKID").val();

		$.ajax({
			type : "GET",
			url : contextPath + "/physician/updatephysicianvisit.do",
			cache : false,
			data : {
				"phyid" : phyid,
				"visitDate" : visitDate,
				"reason" : reason,
				"practice" : practice,
				"org" : orgid,
				"prescription" : prescription,
				"patid" : patid,
				"pkid" : pkid
			},
			async : false,
			success : function(response) {
				window.location.href = contextPath
						+ "/physician/physicianVisitDetails.do";
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});
	}
}

function fnBtnSubmitChange() {
	document.getElementById('phyVisitSubmit').value = 'Submit';
	document.getElementById('phyVisitSubmit').setAttribute('onclick',
			'fnPhysicianVisitForm();');
}

function loadPhysicianAllVisits(type, visitDate, patientId) {

	var params = "type=" + type + "&visitDate=" + visitDate + "&patientId="
			+ patientId;

	$('#visitTableAll').dataTable(
			{

				"processing" : true,
				"bJQueryUI" : true,
				"bFilter" : false,
				"bDestroy" : true,
				"bServerSide" : true,
				"sAjaxSource" : contextPath
						+ "/physician/getAllVisitsByPhysicianId.do?" + params,
				"bProcessing" : true,
				"aoColumns" : [ {
					"mDataProp" : "Visit Date"
				}, {
					"mDataProp" : "Patient"
				}, {
					"mDataProp" : "Reason"
				}, {
					"mDataProp" : "Prescription"
				} ]

			});

}

 

function editPhyAllVisit(id) {
	$
			.ajax({
				type : "GET",
				url : contextPath + "/physician/editphysicianvisit.do",
				cache : false,
				data : "phyid=" + id,
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);
					var dateOfVisit = dov = '';
					if (parsedJson[0][3] == undefined
							|| parsedJson[0][3] == 'undefined') {
						dov = '';
					} else {
						dateOfVisit = new Date(parsedJson[0][3]);
						dov = dateOfVisit.getMonth() + "/"
								+ dateOfVisit.getDate() + "/"
								+ dateOfVisit.getFullYear();
					}
					document.getElementById('date_of_visit').value = dov;
					document.getElementById('reason_for_visit').value = parsedJson[0][5];
					document.getElementById('prescription').value = parsedJson[0][4];
					document.getElementById('vistAllPatientName').value = parsedJson[0][1];
					document.getElementById("viewPhyAllVisit").style.display = "block";
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}

function updatePhyAllVisit() {

	validCheck = physicianVisitValidation('phyAllVisitForm');

	if (validCheck) {
		var phyid = $('#hiddenPhysicianVisitId').val();
		var visitDate = $('#date_of_visit').val();
		var reason = $('#reason_for_visit').val();
		var prescription = $('#prescription').val();
		var org = $('#physicianVisitOrganization').val();
		var practice = $('#physicianVisitPractice').val();
		var tag = $('#tag').val();
		var physician = $('#physician').val();

		$.ajax({
			type : "GET",
			url : contextPath + "/physician/updatephysicianvisit.do",
			cache : false,
			data : {
				"phyid" : phyid,
				"visitDate" : visitDate,
				"reason" : reason,
				"prescription" : prescription,
				"org" : org,
				"practice" : practice,
				"tag" : tag,
				"physician" : physician
			},
			async : false,
			success : function(response) {
				window.location.href = contextPath
						+ "/physician/physicianallvisits.do";
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});
	}
}

function deletePhyAllVisit(id) {
	$.ajax({
		type : "GET",
		url : contextPath + "/physician/deletephysicianvisit.do",
		data : "phyid=" + id,
		success : function(response) {
			window.location.href = contextPath
					+ "/physician/physicianallvisits.do";
		},
		error : function(e) {
		}
	});
}

function loadPatientShareDocuments() {
	var content = '';
	content += '<table class="table table-hover table-bordered" id="shareDocumentTable" style="width:60%;">';
	content += '<thead><th></th><th>Date</th><th>Docuement Name</th></thead>';
	$
			.ajax({
				type : "GET",
				url : contextPath + "/patient/getpatientsharedocuments.do",
				data : "patientId=" + 0,
				cache : false,
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);

					if (parsedJson && parsedJson.length > 0) {
						for ( var i = 0; i < parsedJson.length; i++) {
							content += '<tr><td class="user_list_link"> <input type="checkbox" name="chkDocumentId" value="'
									+ parsedJson[i][2]
									+ '" id="'
									+ parsedJson[i][0] + '" />' + '</td>';
							content += '<td class="user_list_link">'
									+ parsedJson[i][4] + '</td>';
							content += '<td class="user_list_link"> <a href="#" onClick="callDocumentOpenFile('
									+ parsedJson[i][2]
									+ ')">'
									+ parsedJson[i][2] + '</td>';

							content = content + '</tr>';
						}
					} else {
						content += '<tr><td colspan="3" align="center" > No Data Found.</td></tr>'
					}
					content += '</table>';

					document.getElementById('shareDocumentDiv').innerHTML = content;
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}

function loadAllPatientDetails() {
	var content = '';
	content += '<table class="table table-hover table-bordered" id="shareDocumentTable" style="width:60%;">';
	content += '<thead><th></th><th>Date</th><th>Docuement Name</th></thead>';
	var patId = 360448
	$
			.ajax({
				type : "GET",
				url : contextPath
						+ "/physician/physicianPatientShareDocuments.do",
				data : "patientId=" + patId,
				cache : false,
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);

					if (parsedJson && parsedJson.length > 0) {
						document.getElementById("sharePatientName").innerHTML = parsedJson[0][5];

						var dateofbirth = dob = '';
						dateofbirth = parsedJson[0][6];
						if (dateofbirth == undefined
								|| dateofbirth == 'undefined') {
							dob = '';
						} else {
							dateofbirth = new Date(parsedJson[0][6]);
							dob = (dateofbirth.getMonth() + 1) + "/"
									+ dateofbirth.getDate() + "/"
									+ dateofbirth.getFullYear();
						}

						document.getElementById("sharePatientDob").innerHTML = dob;

						for ( var i = 0; i < parsedJson.length; i++) {
							content += '<tr><td class="user_list_link"> <input type="checkbox" name="chkDocumentId" value="'
									+ parsedJson[i][2]
									+ '" id="'
									+ parsedJson[i][0] + '" />' + '</td>';
							content += '<td class="user_list_link">'
									+ parsedJson[i][4] + '</td>';
							content += '<td class="user_list_link"> <a href="#" onClick="callDocumentOpenFile('
									+ parsedJson[i][2]
									+ ')">'
									+ parsedJson[i][2] + '</td>';

							content = content + '</tr>';
						}
					} else {
						content += '<tr><td colspan="3" align="center" > No Data Found.</td></tr>'
					}
					content += '</table>';

					document.getElementById('shareDocumentDiv').innerHTML = content;
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}