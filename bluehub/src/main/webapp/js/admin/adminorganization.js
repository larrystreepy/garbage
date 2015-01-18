
function loadOrganization() {
	if (document.getElementById("panel-typeahead")) {
		document.getElementById("panel-typeahead").style.display = "none";
	}
	$('#adminOrganizationtable')
			.dataTable(
					{

						"processing" : true,
						"bJQueryUI" : true,
						"bFilter" : false,
						"bDestroy" : true,
						"bServerSide" : true,
						"sAjaxSource" : contextPath
								+ "/administrator/adminorganizationlist.do",
						"bProcessing" : true,
						"aoColumns" : [
								{
									"mDataProp" : "Name",
									"bSortable" : false
								},
								{
									"mDataProp" : "Address",
									"bSortable" : false
								},
								{
									"mDataProp" : "City",
									"bSortable" : false
								},
								{
									"mDataProp" : "State",
									"bSortable" : false
								},
								{
									"mDataProp" : "Zipcode",
									"bSortable" : false
								},
								{
									"mDataProp" : "Fax",
									"bSortable" : false
								},
								{
									"mDataProp" : "Actions",
									"bSortable" : false,

									"fnRender" : function(oObj) {

										var id = oObj.aData['id'];

										return '<input type="button" title="Delete Organization" value="" id="delDocRow" class="del_row" onclick="deleteOrganizationRow('
												+ id
												+ ')"/> &nbsp;&nbsp;&nbsp;&nbsp; <input type="button" title="Edit Organization" value="" id="editDocRow" class="editIcon" onclick="editOrganization('
												+ id + ');"/>'

									}
								}

						]


					});

}





function loadPatientsTable() {
	if (document.getElementById('uploadbutton')) {
		document.getElementById('uploadbutton').style.display = "none";
	}
	document.getElementById('patientDetails').style.display = "block";
	var searchPatient = document.getElementById('patientname').value;
	var orgid = document.getElementById('selectOrganization').value;
	var params = "searchPatient=" + searchPatient + "&orgid=" + orgid;
	$('#patientLoadTable')
			.dataTable(
					{

						"processing" : true,
						"bJQueryUI" : true,
						"bFilter" : false,
						"bDestroy" : true,
						"bServerSide" : true,
						"sAjaxSource" : contextPath
								+ "/administrator/adminpatientdetails.do?"
								+ params,
						"bProcessing" : true,
						"aoColumns" : [
								{
									"mDataProp" : "Patient Name",
									"bSortable" : false,

									"fnRender" : function(oObj) {

										var id = oObj.aData['id'];
										var name = oObj.aData['Patient Name'];
										return '<a href="javascript:searchPatientVisitRecords('
												+ id + ')" >' + name + '</a>'

									}
								}, /*{
									"mDataProp" : "Last Name",
									"bSortable" : false
								},*/ {
									"mDataProp" : "DOB",
									"bSortable" : false
								}, {
									"mDataProp" : "SSN",
									"bSortable" : false
								}, {
									"mDataProp" : "Contact Number",
									"bSortable" : false
								}, {
									"mDataProp" : "Address",
									"bSortable" : false
								}

						]


					});

}

function loadAdminVisitDetails(patId) {

	document.getElementById("panel-typeahead").style.display = "none";
	var content = '';
	content += '<table class="table table-hover table-bordered" id="organizationTable">';
	content += '<thead><th>Patient Name</th><th>Date of Visit</th><th>Reason for Visit</th><th>Physician Consulted</th><th>Prescription of Physician</th></thead>';
	document.getElementById('adminVisitsTable').innerHTML = content;

	$
			.ajax({
				type : "GET",
				url : contextPath
						+ "/physician/getAdminPhysicianVisitDetails.do",
				data : "patientId=" + patId,
				cache : false,
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
							content += '<td style="border: none; margin-left: 2px;"> <input title="Delete Visit" type="button" value="" id="delDocRow" class="del_row" onclick="deleteAdminVisit('
									+ parsedJson[i][0] + ')"/></td>';
							content += '<td style="border: none; margin-left: 2px;"> <input title="Edit Visit" type="button" value="" id="editDocRow" class="editIcon" onclick="editAdminVisit('
									+ parsedJson[i][0] + ');"/></td>';

							content = content + '</tr>';
						}
					} else {
						content += '<tr><td colspan="5" align="center" > No Data Found.</td></tr>';
					}
					content += '</table>';

					document.getElementById('adminVisitsTable').innerHTML = content;

					document.getElementById("panel-typeahead").style.display = "block";
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});

}

function fnAdminVisitForm() {
	validCheck = physicianVisitValidation('phyVisitForm');

	if (validCheck) {
		var phyId = document.getElementById("hdnPhysicianId").value;

		if (!phyId) {
			alert('please search and select one physician from list');
		} else {
			document.phyVisitForm.action = contextPath
					+ "/administrator/saveAdminVisitDetails.do";
			document.phyVisitForm.submit();
		}
	}
};

function deleteAdminVisit(id) {
	$.ajax({
		type : "GET",
		url : contextPath + "/physician/deletephysicianvisit.do",
		data : "phyid=" + id,
		success : function(response) {
			window.location.href = contextPath
					+ "/administrator/adminvisitdetails.do";
			
		},
		error : function(e) {
		}
	});
}

function editAdminVisit(id) {
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
					document.getElementById("createVisit").style.display = "block";
					$("#date_of_visit").val(dov);
					$("#reason_for_visit").val(parsedJson[0][5]);
					$("#prescription").val(parsedJson[0][4]);
					$("#physicianVisitOrganization").val(parsedJson[0][6]);
					loadPractice();
					$("#physicianVisitPractice").val(parsedJson[0][7]);
					$("#hdnPhysicianId").val(parsedJson[0][9]);
					$("#hdnPatientId").val(parsedJson[0][8]);
					$("#patientname").val(parsedJson[0][1]);
					$("#physicianname").val(parsedJson[0][2]);
					$("#hiddenPKID").val(parsedJson[0][0]);
					document.getElementById('adminVisitTitle').innerHTML = 'Edit Visit';
					document.getElementById('phyVisitSubmit').value = 'Update';
					document.getElementById('phyVisitSubmit').setAttribute(
							'onclick', 'updateAdminvisit();');
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}

function updateAdminvisit() {

	validCheck = physicianVisitValidation('phyVisitForm');

	if (validCheck) {
		var phyid = $('#hdnPhysicianId').val();
		var visitDate = $('#date_of_visit').val();
		var reason = $('#reason_for_visit').val();
		var prescription = $('#prescription').val();
		var orgid = $('#physicianVisitOrganization').val();
		var practice = $('#physicianVisitPractice').val();
		var patid = $('#hdnPatientId').val();
		var pkid = $("#hiddenPKID").val();

		$.ajax({
			type : "GET",
			url : contextPath + "/physician/updateadminphysicianvisit.do",
			cache : false,
			data : {
				"phyid" : phyid,
				"visitDate" : visitDate,
				"reason" : reason,
				"prescription" : prescription,
				"practice" : practice,
				"org" : orgid,
				"patid" : patid,
				"pkid" : pkid
			},
			async : false,
			success : function(response) {
				window.location.href = contextPath
						+ "/administrator/adminvisitdetails.do";
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});
	}
}

function organizationValidation() {
	var validator = $("#adminOrganizationForm").validate(
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

					organizationname : {
						required : true,
					},
					address : {
						required : true,
					},
					city : {
						required : true,
					},
					state : {
						required : true,
					},
					zipcode : {
						required : true,
					}
					/*,
					fax : {
						required : true,
					}*/

				},
				messages : {

					organizationname : "Please enter Organization name",

					address : "Please enter Address",

					city : "Please enter City",

					state : "Please enter State",

					zipcode : "Please enter Zipcode",
					
//					fax : "Please enter Fax Number",
				}

			});

	var validCheck = validator.form();

	return validCheck;
}

function editOrganizationValidation() {

	var validator = $("#updateAdminOrganizationForm").validate(
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

					organizationname : {
						required : true,
					},
					address : {
						required : true,
					}

				},
				messages : {

					organizationname : "Please enter Organization name",

					address : "Please enter Address",
				}

			});

	var validCheck = validator.form();

	return validCheck;
}
function saveAdminOrganization() {


	validCheck = organizationValidation();

	if (validCheck) {

		var organizationname = $('#organizationname').val();
		var address = $('#address').val();

		var city = $('#city').val();
		var state = $('#state').val();
		var zipcode = $('#zipcode').val();
		var fax =  $('#fax').val();
		$.ajax({
			type : "GET",
			url : contextPath + "/administrator/saveadminorganization.do",
			data : {
				organizationname : organizationname,
				address : address,
				city : city,
				state : state,
				zipcode : zipcode,
				fax : fax
			},
			success : function(response) {

				window.location.href = contextPath
						+ "/administrator/adminorganization.do";
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});

		return;
	}
}

function runSaveAdminOrganization(e) {
	if (e.keyCode == 13) {
		saveAdminOrganization();
	}
}



function loadOrganizations() {

	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/adminorganizations.do",
		// data : "userGroup=" + userGroup,
		cache : false,
		async : false,
		success : function(response) {

			var parsedJson = $.parseJSON(response);
			document.getElementById("selectOrganization").innerHTML = "";

			var combo = document.getElementById("selectOrganization");
			var defaultOption = document.createElement("option");
			defaultOption.text = 'Select';
			defaultOption.value = '-1';
			combo.add(defaultOption);
			for ( var i = 0; i < parsedJson.length; i++) {
				var option = document.createElement("option");
				option.text = parsedJson[i].organizationname;
				option.value = parsedJson[i].id;
				
				combo.add(option);
			}
			// loadPractice();
		},
		error : function(e) {
			// alert('Error: ' + e.responseText);
		}
	});

}

function deleteOrganizationRow(id) {
	var answer = confirm("Are you sure want to delete the organization ?")
	if (answer) {
		deleteOrganization(id);
	}
	return false;
}

function fnloadOrg() {
	window.location.href = contextPath + "/administrator/adminorganization.do";
}

function deleteOrganization(id) {

	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/deleteadminorganization.do",

				data : "orgid=" + id,
				success : function(response) {

					document.getElementById("deleteOrg").innerHTML = "Organization Successfully Deleted";
					setTimeout('fnloadOrg()', 1000);
				},
				error : function(e) {
					// alert('Error: ' + e);
				}
			});

}
function cancelOrganization() {
	document.getElementById("panel-typeahead").style.display = "none";
}

function editOrganization(id) {
			$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/editadminorganization.do",
				cache : false,
				data : "orgid=" + id,
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);
					document.getElementById("panel-typeahead").style.display = "block";
					document.getElementById('editorganizationname').value = parsedJson['organizationname'];
					document.getElementById('hidorganizationname').value = parsedJson['organizationname'];

					document.getElementById('editaddress').value = parsedJson['address'];
					document.getElementById('editcity').value = parsedJson['city'];
					document.getElementById('editstate').value = parsedJson['state'];
					document.getElementById('editzipcode').value = parsedJson['zipcode'];
					document.getElementById('organizationid').value = parsedJson['id'];
					document.getElementById('editfax').value = parsedJson['fax'];
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}

function updateAdminOrganization() {

	validCheck = editOrganizationValidation();

	if (validCheck) {

		var organizationname = $('#editorganizationname').val();
		var address = $('#editaddress').val();

		var city = $('#editcity').val();
		var state = $('#editstate').val();
		var zipcode = $('#editzipcode').val();
		var fax = $('#editfax').val();
//			faxValid
		var orgid = $('#organizationid').val();

		$
				.ajax({
					type : "GET",
					url : contextPath
							+ "/administrator/updateadminorganization.do",
					cache : false,
					data : {
						"organizationname" : organizationname,
						"address" : address,
						"orgid" : orgid,
						"city" : city,
						"state" : state,
						"zipcode" : zipcode,
						"fax" : fax
					},
					async : false,
					success : function(response) {

						document.getElementById("deleteOrg").innerHTML = "Organization Updated Successfully";
						setTimeout('fnloadOrg()', 1000);
					},
					error : function(e) {
						alert('Error: ' + e.responseText);
					}
				});
	}
}

function runUpdateAdminOrganization(e) {
	if (e.keyCode == 13) {
		updateAdminOrganization();
	}
}

function checkOrganizationname() {
	ValidateorganizationName();
	var name = $('#organizationname').val();
	if (name != "") {
		$
				.ajax({
					type : "GET",
					url : contextPath
							+ "/administrator/checkorganizationname.do",
					data : "orgname=" + name,
					success : function(response) {
						var obj = $.parseJSON(response);
						var status = obj.status;
						var message = obj.message;
						if (status == 'Yes') {
							document.getElementById('orgnameValid').style.display = "block";
							$('#orgnameValid').html(message);
							$('#orgnameValid').fadeIn().delay(4000).fadeOut(
									'slow');
							document.getElementById("organizationname").value = "";
							document.getElementById("organizationname").focus();
						} else {
							$('#orgnameValid').html('');
							$('#orgnameValid').hide();
							document.getElementById('orgnameValid').style.display = "none";
						}
					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
	}
}

function checkUpdateOrganizationname() {
	var name = $('#editorganizationname').val();
	var hidname = $('#hidorganizationname').val();

	if (name != "") {

		if (name == hidname) {

		} else {

			$
					.ajax({
						type : "GET",
						url : contextPath
								+ "/administrator/checkorganizationname.do",
						data : "orgname=" + name,
						success : function(response) {
							var obj = $.parseJSON(response);
							var status = obj.status;
							var message = obj.message;
							if (status == 'Yes') {
								document.getElementById('orgnameUpdateValid').style.display = "block";
								$('#orgnameUpdateValid').html(message);
								$('#orgnameUpdateValid').fadeIn().delay(4000)
										.fadeOut('slow');
								document.getElementById("editorganizationname").value = "";
								document.getElementById("editorganizationname")
										.focus();
							} else {
								$('#orgnameUpdateValid').html('');
								$('#orgnameUpdateValid').hide();
								document.getElementById('orgnameUpdateValid').style.display = "none";
							}
						},
						error : function(e) {
							alert('Error: ' + e);
						}
					});

		}

	}
}

function proceedUploadClinicalInformation() {

	document.getElementById("uploadClinicalInfoDiv").style.display = "none";

	document.getElementById("dropzoneDiv").style.display = "block";
}

function submitUploadClinicalInformation() {

	var patientId = $('#hdnPhysicianId').val();
	var visitid = $('#hdnVisitId').val();
	
	if(visitid=""){
		visitid = 0;
	}

	var requestId = 0;
	$
			.ajax({
				type : "GET",
				url : contextPath
						+ "/administrator/uploadClinicalInformation.do",
				cache : false,
				async : false,
				data : "patientid=" + patientId + "&visitid=" + visitid + "&requestId="+requestId,
				success : function(response) {

					var parsedJson = $.parseJSON(response);

					if (parsedJson.status == "YES") {

						document.getElementById("success_msg_span1").innerHTML = parsedJson.message;

						fnSetTimeout("success_msg_span1", 6000);

						

						setTimeout('fnReloadClinicalInformation()', 3000);
					} else if(parsedJson.status == "NO") {

						document.getElementById("success_msg_span1").innerHTML = parsedJson.message;

						fnSetTimeout("success_msg_span1", 6000);

						document.getElementById("uploadClinicalInfoDiv").style.display = "none";

						document.getElementById("dropzoneDiv").style.display = "block";

						document.getElementById('uploadbutton').style.display = "block";
					}else{

						document.getElementById("success_msg_span1").innerHTML = parsedJson.message;

						fnSetTimeout("success_msg_span1", 6000);
						
					}

				},
				error : function(e) {
					// alert('Error: ' + e.responseText);
				}
			});

}


function submitFaxClinicalInformation() {

	var faxid = $('#faxNumber').val();
	var patientId = $('#hdnPhysicianId').val();
	var visitid = $('#hdnVisitId').val();

	var requestId = 0;
	$
			.ajax({
				type : "GET",
				url : contextPath
						+ "/administrator/sendFaxPopup.do",
				cache : false,
				async : false,
				data : "patientId=" + patientId + "&visitid=" + visitid + "&requestId="+requestId+"&faxid="+faxid,
				success : function(response) {

					var parsedJson = $.parseJSON(response);
					if (parsedJson.status == "YES") {
						
						document.getElementById("success_msg_span2").innerHTML = parsedJson.message;

						fnSetTimeout("success_msg_span2", 6000);
						
						window.location.reload(contextPath + "/physician/physicianupload.do");
						
					} else {

						document.getElementById("success_msg_span2").innerHTML = parsedJson.message;

						fnSetTimeout("success_msg_span2", 6000);

						document.getElementById("uploadClinicalInfoDiv").style.display = "none";

						document.getElementById("dropzoneDiv").style.display = "block";

						document.getElementById('uploadbutton').style.display = "block";
						
						window.location.reload(contextPath + "/physician/physicianupload.do");
					}

				},
				error : function(e) {
					
				}
			});

}


function fnReloadClinicalInformation() {

	window.location.reload(contextPath + "/administrator/adminupload.do");

}

function displayUserData(id) {

	document.getElementById("hdnVisitId").value = id;
	document.getElementById('uploadbutton').style.display = "block";

}

function searchPatientVisitRecords(patientid) {
	
	document.getElementById("hdnVisitId").value = "0"; // for dummy visit
	
	document.getElementById('uploadbutton').style.display = "block";
	
	document.getElementById('visitRecords').style.display="none"; // for hide visit
	
	document.getElementById("hdnPhysicianId").value = patientid;
	$('#patientVisitTableId').dataTable( {
						// "bStateSave": true,
						"processing" : true,
						"bJQueryUI" : true,
						"bFilter" : false,
						"bDestroy" : true,
						// "bAutoWidth": true,
						// "sDom": '<"top"i>rt<"bottom"flp><"clear">',
						"bServerSide" : true,
						"sAjaxSource" : contextPath
								+ "/administrator/adminsearchpatientvisitrecords.do?patientid="
								+ patientid,
						"bProcessing" : true,

						"aoColumns" : [
								{
									"mDataProp" : "Patient Name",
									"bSortable" : false,

									"fnRender" : function(oObj) {

										var id = oObj.aData['id'];
										var name = oObj.aData['Patient Name'];
										return '<a href="javascript:displayUserData('
												+ id + ')" >' + name + '</a>'
									}
								}, {
									"mDataProp" : "DOB",
									"bSortable" : false
								}, {
									"mDataProp" : "Visit Date",
									"bSortable" : false
								}, {
									"mDataProp" : "Physician",
									"bSortable" : false
								}, {
									"mDataProp" : "Reason For Visit",
									"bSortable" : false
								} ]
					});

}

function loadUploadPatientsTable() {

	document.getElementById('uploadbutton').style.display = "none";

	document.getElementById('patientDetails').style.display = "block";

	var content = '';
	content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
	content += '<thead>';
	content += '<th>Patient Name</th>';
	content += '<th>Last Name</th>';
	content += '<th>DOB</th>';
	content += '<th>SSN</th>';
	content += '<th>Contact Number</th>';
	content += '<th>Address</th>';
	
	content += '</thead>';
	document.getElementById('patientDetails').innerHTML = content;

	var searchPatient = document.getElementById('patientname').value;

	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/adminpatientdetails.do",
				data : "searchPatient=" + searchPatient,
				cache : false,
				async : false,
				success : function(response) {

					var parsedJson = $.parseJSON(response);

					if (parsedJson != null) {

						for ( var i = 0; i < parsedJson.length; i++) {

							
							content += '<tr onclick="displayUserData('
									+ parsedJson[i].userid
									+ ')"><td id="agencytd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].firstname + '</td>';
							content += '<td id="emailtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].lastname + '</td>';
							content += '<td id="emailtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].dateofbirth + '</td>';
							content += '<td id="emailtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].ssn + '</td>';
							content += '<td id="emailtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].contact_number + '</td>';
							content += '<td id="addresstd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].address + '</td>';
							
							content = content + '</tr>';
						}

						
						content += '</table>';
						document.getElementById('patientDetails').innerHTML = content;
						document.getElementById('searchButton').style.display = "block";
					}

				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});

}

function loadAdminAllVisits(type, patientId, orgId) {
	var content = '';
	content += '<table class="table table-hover table-bordered" id="organizationTable">';
	content += '<thead><th>Date of Visit</th><th>Patient Name</th><th>Reason for Visit</th><th>Physician Consulted</th><th>Prescription of Physician</th></thead>';
	document.getElementById('phyAllVisitsTable').innerHTML = content;

	$
			.ajax({
				type : "GET",
				url : contextPath + "/physician/getAllVisitsByAdmin.do",
				data : {
					"type" : type,
					"patientId" : patientId,
					"orgId" : orgId
				},
				cache : false,
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

							content += '<tr><td id="visitdate' + i
									+ '"  class="user_list_link">' + dov
									+ '</td>';
							content += '<td id="patient' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][1] + '</td>';
							content += '<td id="reason' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][5] + '</td>';
							content += '<td id="prescription' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][2] + '</td>';
							content += '<td id="prescription' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][4] + '</td>';
							
							content += '<td style="border: none; margin-left: 2px;"> <input title="View Visit" type="button" value="" id="viewIcon" class="viewIcon" onclick="editAdminAllVisit('
									+ parsedJson[i][0] + ');"/></td>';

							content = content + '</tr>';
						}
					} else {
						content += '<tr><td colspan="5" align="center" > No Data Found.</td></tr>';
					}
					
					content += '</table>';

					document.getElementById('phyAllVisitsTable').style.display = "block";
					document.getElementById('phyAllVisitsTable').innerHTML = content;

					
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}

function editAdminAllVisit(id) {
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
					document.getElementById('date_of_visit').value = dov;
					document.getElementById('reason_for_visit').value = parsedJson[0][5];
					document.getElementById('prescription').value = parsedJson[0][4];
					document.getElementById('vistAllPatientName').value = parsedJson[0][1];
					document.getElementById('vistAllPhysicianName').value = parsedJson[0][2];
					document.getElementById("viewPhyAllVisit").style.display = "block";
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}

function updateAdminAllVisit() {

	validCheck = adminAllVisitValidation('adminAllVisitForm');

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
						+ "/administrator/adminallvisits.do";
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});
	}
}

function adminAllVisitValidation(formId) {
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
					practice : "required"
				},
				messages : {
					userEmail : {
						required : "Email Id is Required",
						email : "Enter valid Email Id"
					},
					reason_for_visit : "Please enter Reason for visit",
					prescription : "Please enter Prescription",
					organization : "Please select one Organization",
					practice : "Please select one Practice"
				
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

function deleteAdminAllVisit(id) {
	$.ajax({
		type : "GET",
		url : contextPath + "/physician/deletephysicianvisit.do",
		data : "phyid=" + id,
		success : function(response) {
			window.location.href = contextPath
					+ "/administrator/adminallvisits.do";
			
		},
		error : function(e) {
		}
	});
}

function ValidateorganizationName() {
	var totalCharacterCount = document.getElementById("organizationname").value;

	var strValidChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
	var strChar;
	var FilteredChars = "";
	for (i = 0; i < totalCharacterCount.length; i++) {
		strChar = totalCharacterCount.charAt(i);
		if (strValidChars.indexOf(strChar) != -1) {
			FilteredChars = FilteredChars + strChar;
		}
	}
	document.getElementById("organizationname").value = FilteredChars;
	return false;
}

var displayMsgId = "";


function fnClearMsgField() { 
	document.getElementById(displayMsgId).style.display = "none";
}
function fnSetTimeout(id, time) {
	if (time == undefined)
		time = 4000;
	displayMsgId = id;
	document.getElementById(displayMsgId).style.display = "block";
	//setTimeout('fnClearMsgField()', time);
}
