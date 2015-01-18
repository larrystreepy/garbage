
function fnSendRequestToPatientAndPhysician() {

	document.RequestInfoPatient.action = contextPath
			+ "/physician/requestBehalfOfPatient.do";
	document.RequestInfoPatient.submit();
}

function setRequestedPhysician(id, i) {

	document.getElementById('adminVisitButton').style.display = "block";
	document.getElementById("hdnPhysicianId").value = id;
	document.getElementById("physicianRadio_" + i).checked = true;
}
function searchPhysicianVisitRecords(id, i) {
	document.getElementById('adminVisitButton').style.display = "block";
	document.getElementById("hdnPhysicianId").value = id;
	document.getElementById("PhysicianSelectBoxId_" + i).checked = true;
}

function loadPhysicianTable() {
	document.getElementById('adminVisitButton').style.display = "none";
	document.getElementById('physicianlabel').style.display = "block";
	document.getElementById('physicianDetails').style.display = "block";

	var content = '';
	content += '<div class="table-responsive table-responsive-datatables">';
	content += '<table style="width: 100%" class="tablesorter table table-hover table-bordered">';
	content += '<thead>';
	content += '<th></th>';
	content += '<th>Physician Name</th>';
	content += '<th>Organization</th>';
	content += '<th>Practice</th>';
	content += '</thead>';
	document.getElementById('physicianDetails').innerHTML = content;

	var searchPhysician = document.getElementById('physicianname').value;

	var orgid = document.getElementById('selectOrganization').value;

	var practiceid = document.getElementById('selectPractice').value;

	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/adminphysiciandetails.do",
				data : {
					searchphysician : searchPhysician,
					orgid : orgid,
					practiceid : practiceid
				},
				cache : false,
				async : false,
				success : function(response) {

					if (response == "null" || response == "") {
						content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

						content += '</table>';
						document.getElementById('physicianDetails').innerHTML = content;

					} else {

						var parsedJson = $.parseJSON(response);

						for ( var i = 0; i < parsedJson.length; i++) {

							content = content
									+ '<tr id = "physicianTrId" onclick="setRequestedPhysician('
									+ parsedJson[i].userid + ',' + i + ')">';
							content += '<td ><input for="physicianTrId" type="radio" id="physicianRadio_'
									+ i
									+ '" value='
									+ parsedJson[i].userid
									+ ' name="physicianRadio"></td>';
							content += '<td id="agencytd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].firstname + '</td>';
							content += '<td id="emailtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].organizationName + '</td>';
							content += '<td id="addresstd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].practicename + '</td>';
							content = content + '</tr>';
						}

						content += '</table>';
						content += '</div>';
						document.getElementById('physicianDetails').innerHTML = content;

					}

				},
				error : function(e) {
				}
			});

}

function loadAllPhysician() {
	document.getElementById('adminVisitButton').style.display = "none";
	document.getElementById('physicianlabel').style.display = "block";
	document.getElementById('physicianDetails').style.display = "block";

	var content = '';
	content += '<div class="table-responsive table-responsive-datatables">';
	content += '<table style="width: 100%" class="tablesorter table table-hover table-bordered">';
	content += '<thead>';
	content += '<th></th>';
	content += '<th>Physician Name</th>';
	content += '<th>Organization</th>';
	content += '<th>Practice</th>';
	content += '</thead>';
	document.getElementById('physicianDetails').innerHTML = content;

	var orgid = document.getElementById('selectOrganization').value;

	var practiceid = document.getElementById('selectPractice').value;


	$
			.ajax({
				type : "GET",
				url : contextPath
						+ "/administrator/adminallphysiciandetails.do",
				data : {
					orgid : orgid,
					practiceid : practiceid,
					searchphysician : ''
				},
				cache : false,
				async : false,
				success : function(response) {

					if (response == "null" || response == "") {
						content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

						content += '</table>';
						document.getElementById('physicianDetails').innerHTML = content;

					} else {

						var parsedJson = $.parseJSON(response);
						for ( var i = 0; i < parsedJson.length; i++) {

							content = content
									+ '<tr onclick="searchPhysicianVisitRecords(\''
									+ parsedJson[i].userid + '\',\'' + i
									+ '\')">';
							content += '<td ><input id="PhysicianSelectBoxId_'
									+ i
									+ '" type="radio" onclick="fnSetPhysicianvalue('
									+ parsedJson[i].userid
									+ ')" id="physicianRadio" value='
									+ parsedJson[i].userid
									+ ' name="physicianRadio"></td>';
							content += '<td id="agencytd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].firstname + '</td>';
							content += '<td id="emailtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].organizationName + '</td>';
							content += '<td id="addresstd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].practicename + '</td>';
							content = content + '</tr>';
						}

						content += '</table>';
						content += '</div>';
						document.getElementById('physicianDetails').innerHTML = content;

					}

				},
				error : function(e) {
				}
			});
}

function fnLoadPendingAndApprovedShares() {

	var status = 1;

	var content = '';
	content += '<table class="table table-hover table-bordered" id="shareedRequestTable">';
	content += '<thead> <th>Patient Name</th><th>Last Name</th><th>Dob</th> <th>SSN</th><th>Contact Number</th><th>Address</th><th>Status</th></thead>';

	$
			.ajax({
				type : "GET",
				url : contextPath + "/physician/getPhysicianSharedRequest.do",
				data : "status=" + status,
				cache : false,
				async : false,
				success : function(response) {

					var parsedJson = $.parseJSON(response);
					var dateOfVisit = dov = '';
					if (parsedJson && parsedJson.length > 0) {
						for ( var i = 0; i < parsedJson.length; i++) {
							content += '<tr><td id="patient' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][1] + '</td>';
							content += '<td id="visitdate' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][3] + '</td>';
							content += '<td id="reason' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][2] + '</td>';
							content += '<td id="prescription' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][4] + '</td>';
							content += '<td id="prescription' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][5] + '</td>';
							content += '<td id="prescription' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][6] + '</td>';
							content += '<td id="prescription' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][7] + '</td>';
							content = content + '</tr>';
						}
					} else {
						content += '<tr><td colspan="5" align="center" > No Data Found.</td></tr>';
					}
					content += '</table>';

					document.getElementById('RequestSharesDiv').innerHTML = content;
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});

}
function fnSetPatientVisitRecordNew(shareId) {

	alert(shareId);

}

function fngetSharedRequestInfo(shareId) {

	window.open(contextPath + "/physician/viewShareRequest.do?shareId="
			+ shareId);

}

function loadShareRequestData() {

	var status = 1;
	var content = '';
	content += '<table class="table table-hover table-bordered" id="shareedRequestTable">';
	content += '<thead> <th>Patient Name</th><th>Last Name</th><th>Dob</th> <th>SSN</th><th>Contact Number</th><th>Address</th><th>Status</th></thead>';

	$
			.ajax({
				type : "GET",
				url : contextPath + "/physician/getPhysicianSharedRequest.do",
				data : "status=" + status,
				cache : false,
				async : false,
				success : function(response) {

					var parsedJson = $.parseJSON(response);
					var dateOfVisit = dov = '';
					if (parsedJson && parsedJson.length > 0) {
						for ( var i = 0; i < parsedJson.length; i++) {

							var status = parsedJson[i][7];
							content += '<tr onclick="fngetSharedRequestInfo('
									+ parsedJson[i][0] + ') ">'
							content += '<td id="patient' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][1] + '</td>';
							content += '<td id="visitdate' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][3] + '</td>';
							content += '<td id="reason' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][2] + '</td>';
							content += '<td id="prescription' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][4] + '</td>';
							content += '<td id="prescription' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][5] + '</td>';
							content += '<td id="prescription' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][6] + '</td>';
							content += '<td id="prescription' + i
									+ '"  class="user_list_link"> ' 
									+ parsedJson[i][7] + ' </td>';
							content = content + '</tr>';
						}
					} else {
						content += '<tr><td colspan="5" align="center" > No Data Found.</td></tr>';
					}
					content += '</table>';

					document.getElementById('RequestSharesDiv').innerHTML = content;

				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}

function loadAdminVisitDetails(patId) {

	var content = '';
	content += '<table class="table table-hover table-bordered" id="organizationTable">';
	content += '<thead><th></th><th>Patient Name</th><th>Date of Visit</th><th>Reason for Visit</th><th>Physician Consulted</th><th>Prescription of Physician</th></thead>';

	$
			.ajax({
				type : "GET",
				url : contextPath + "/physician/getPhysicianVisitDetails.do",
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

							content += '<tr><td ><input type="radio" onclick="fnSetPatientVisitRecord('
									+ parsedJson[i][0]
									+ ')" id="patientVisitRadio" value='
									+ parsedJson[i][0]
									+ ' name="patientVisitRadio"></td>';
							content += '<td id="patient' + i
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

							content = content + '</tr>';
						}
					} else {
						content += '<tr><td colspan="5" align="center" > No Data Found.</td></tr>';
					}
					content += '</table>';

					document.getElementById('adminVisitsTable').innerHTML = content;
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});

}

function loadPatientsTable() {
	loadAdminOrganization();
	document.getElementById('patientInformations').style.display = "block";
	var searchPatient = document.getElementById('patientname').value;
	var orgid = -1;
	var params = "searchPatient=" + searchPatient + "&orgid=" + orgid;
	$('#patientLoadTable').dataTable(
			{

				"processing" : true,
				"bJQueryUI" : true,
				"bFilter" : false,
				"bDestroy" : true,
				"bServerSide" : true,
				"sAjaxSource" : contextPath
						+ "/administrator/adminpatientdetails.do?" + params,
				"bProcessing" : true,
				"aoColumns" : [
						{
							"mDataProp" : "Patient Name",
							"bSortable" : false,

							"fnRender" : function(oObj) {

								var id = oObj.aData['id'];
								var name = oObj.aData['Patient Name'];

								return '<a href="javascript:fnSetPatientvalue('
										+ id + ')" >' + name + '</a>'
							}
						},
//						{
//							"mDataProp" : "Last Name",
//							"bSortable" : false
//						},
						{
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

			//		  "aaSorting": [[ 2, 'desc' ]]

			});

}

function loadPatientsTabless() {

	document.getElementById('patientInformations').style.display = "block";

	var content = '';
	content += '<table style="width: 100%" id="renderUserListTable" class="  table table-hover table-bordered">';
	content += '<thead>';
	content += '<th>Patient Name</th>';
	content += '<th>Last Name</th>';
	content += '<th>DOB</th>';
	content += '<th>SSN</th>';
	content += '<th>Contact Number</th>';
	content += '<th>Address</th>';
	content += '</thead>';
	document.getElementById('patientInformations').innerHTML = content;

	var searchPatient = document.getElementById('patientname').value;

	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/adminpatientdetails.do",
				data : "searchPatient=" + searchPatient,
				cache : false,
				async : false,
				success : function(response) {

					if (response == "null") {
						content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

						document.getElementById('patientInformations').innerHTML = content;
					} else {

						var parsedJson = $.parseJSON(response);

						if (parsedJson != null) {

							for ( var i = 0; i < parsedJson.length; i++) {

								content += '<tr onclick="fnSetPatientvalue('
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
										+ parsedJson[i].contact_number
										+ '</td>';
								content += '<td id="addresstd' + i
										+ '"  class="user_list_link">'
										+ parsedJson[i].address + '</td>';
								content = content + '</tr>';
							}
							loadAdminOrganization();
							content += '</table>';
							document.getElementById('patientInformations').innerHTML = content;
						}

					}
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});

}

function loadAdminOrganization() {
	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/adminorganizations.do",
		cache : false,
		async : false,
		success : function(response) {
			var parsedJson = $.parseJSON(response);
			document.getElementById("selectOrganization").innerHTML = "";

			var combo = document.getElementById("selectOrganization");

			var option = document.createElement("option");
			option.text = "Select";
			option.value = "-1";
			combo.add(option);

			for ( var i = 0; i < parsedJson.length; i++) {
				var option = document.createElement("option");
				option.text = parsedJson[i].organizationname;
				option.value = parsedJson[i].id;
				combo.add(option);
			}
		},
		error : function(e) {
			alert('Error: ' + e.responseText);
		}
	});

}

function loadPractice() {
	var orgid = document.getElementById("selectOrganization").value;
	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/loadpractice.do",
		data : "orgid=" + orgid,
		cache : false,
		async : false,
		success : function(response) {

			var parsedJson = $.parseJSON(response);
			document.getElementById("selectPractice").innerHTML = "";

			var combo = document.getElementById("selectPractice");

			var option = document.createElement("option");
			option.text = "Select";
			option.value = "-1";
			combo.add(option);

			for ( var i = 0; i < parsedJson.length; i++) {
				var option = document.createElement("option");
				option.text = parsedJson[i].practicename;
				option.value = parsedJson[i].id;
				combo.add(option);
			}
		},
		error : function(e) {
			alert('Error: ' + e.responseText);
		}
	});
}

function fnSetPatientvalue(id) {

	document.getElementById("hdnPatientId").value = id;
	if (document.getElementById("organizationlabel")) {
		document.getElementById("organizationlabel").style.display = "block";
	}

	if (document.getElementById("shareRequestDiv")) {
		document.getElementById("shareRequestDiv").style.display = "block";
	}

}

var displayMsgId = "";
function fnClearMsgField() { 
	document.getElementById(displayMsgId).style.display = "none";
}
function fnSetTimeout(id, time) {
	if (time == undefined)
		time = 3000;
	displayMsgId = id;
	document.getElementById(displayMsgId).style.display = "block";
	//setTimeout('fnClearMsgField()', time);
}

function fnRequestingShare() {

	var opt = document.getElementById("requestShareOption").value;

	var patientId = document.getElementById("hdnPatientId").value;

	$
			.ajax({
				type : "GET",
				url : contextPath + "/physician/sharerequest.do",
				data : "option=" + opt + "&visitId=" + 0 + "&patientId="
						+ patientId,
				cache : false,
				async : false,
				success : function(response) {

					var parsedJson = $.parseJSON(response);

					loadShareRequestData();

					loadPatientsTable();

					document.getElementById("success_msg_span").innerHTML = parsedJson.message;

					fnSetTimeout("success_msg_span", 3000);

				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});

}
