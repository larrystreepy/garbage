function callDocumentOpenFile(fileName, userId) {
	 
	if (fileName == '') {
	} else {
		var url = contextPath + "/SharedDocumentDownload?fileName=" + fileName
				+ "&userId=" + userId;
		window.open(url, '', 'width=700,height=500');
	}
}

function fnLoadPendingAndApprovedShares() {

	var status = 1;

	var content = '';
	content += '<table class="table table-hover table-bordered" id="shareedRequestTable">';
	content += '<thead> <th>First Name</th><th>Last Name</th><th>Dob</th> <th>SSN</th><th>Contact Number</th><th>Address</th><th>Status</th></thead>';

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
	content += '<thead> <th>First Name</th><th>Last Name</th><th>Dob</th> <th>SSN</th><th>Contact Number</th><th>Address</th><th>Status</th></thead>';

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

	document.getElementById('patientInformations').style.display = "block";

	var searchPatient = document.getElementById('patientname').value;
	var orgid = -1;
	var params = "searchPatient=" + searchPatient + "&orgid=" + orgid;

	$('#patientLoadTable')
			.dataTable(
					{

						"bJQueryUI" : true,
						"bFilter" : false,
						"bDestroy" : true,
						"bAutoWidth": false,
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

										return '<a href="#" onclick = "fnSetPatientvalue('
												+ id + ')"> ' + name + '</a>'
									}
								},
//								{
//									"mDataProp" : "Last Name",
//									"bSortable" : false
//								}, 
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
	
	document.getElementById("adminVisitButton").style.display="block";

	document.getElementById("hdnPatientId").value = id;
	if (document.getElementById("organizationlabel")) {
		document.getElementById("organizationlabel").style.display = "block";
	}

	if (document.getElementById("shareRequestDiv")) {
		document.getElementById("shareRequestDiv").style.display = "block";
	}

}

function fnSetPatientVisitRecord(visitId) {

	document.getElementById("hdnVisitId").value = visitId;

}

function fnLoadRequestShareOption() {
	var opt = document.getElementById("requestShareOption").value;

	if (opt == "1") {

		document.getElementById("adminVisitButton").style.display = "block";

	} else if (opt == "2") {

		document.getElementById("adminVisitButton").style.display = "block";
	} else {

		document.getElementById("adminVisitButton").style.display = "none";
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

//	var opt = document.getElementById("requestShareOption").value;
var opt = 2;
	
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
