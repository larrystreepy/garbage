function fnGetOrganizationname(id) {
	var name = null;
	$.ajax({
		type : "GET",
		url : contextPath + "/patient/getpatientOrganizationName.do",
		data : "patientId=" + id,
		cache : false,
		async : false,
		success : function(response) {
			var parsedJson = $.parseJSON(response);
			name = parsedJson;
		}

	});
	return name;
}

function loadPatientShareDocuments(patId) {
	var name = fnGetOrganizationname(patId);
	var content = '';
	content += '<table class="table table-hover table-bordered" id="shareDocumentTable" style="width:60%;">';
	content += '<thead><th></th><th>Date</th><th>Document Name</th><th>Organization</th></thead>';
	
	var content1 = '';
	content1 += '<table class="table table-hover table-bordered" id="shareDocumentTable1" style="width:60%;">';
	content1 += '<thead><th></th><th>Date</th><th>Document Name</th></thead>';
	
	$
			.ajax({
				type : "GET",
				url : contextPath + "/patient/getpatientsharedocuments.do",
				data : "patientId=" + patId,
				cache : false,
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);
					var count = parsedJson.length;
					document.getElementById("hdnCount").value = count;
					if (parsedJson && parsedJson.length > 0) {
						for ( var i = 0; i < parsedJson.length; i++) {/*
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

							content += '<td class="user_list_link">' + name
									+ '</td>';
							content = content + '</tr>';
						*/

							content += '<tr><td class="user_list_link"><input id="hdnDocCheck_'+i+'" type="checkbox" > '
									+ '<input type="hidden" id="hdnDocId_'+i+'" value="'+parsedJson[i][2]+'"></td>';
							content += '<td class="user_list_link">'
									+ parsedJson[i][4] + '</td>';
							content += '<td class="user_list_link"> <a href="#" onClick="callDocumentOpenFile('
									+ parsedJson[i][2]
									+ ')">'
									+ parsedJson[i][2] + '</td>';

							content = content + '</tr>';
							
							
							/*content1 += '<tr><td class="user_list_link"><input id="hdnDocCheck1_'+i+'" type="checkbox" > '
							+ '<input type="hidden" id="hdnDocId1_'+i+'" value="'+parsedJson[i][2]+'"></td>';
					content1 += '<td class="user_list_link">'
							+ parsedJson[i][4] + '</td>';
					content1 += '<td class="user_list_link"> <a href="#" onClick="callDocumentOpenFile('
							+ parsedJson[i][2]
							+ ')">'
							+ parsedJson[i][2] + '</td>';

					content1 = content1 + '</tr>';*/
							
						}
					} else {
						content += '<tr><td colspan="3" align="center" > No Data Found.</td></tr>'
					}
					content += '</table>';

					document.getElementById('shareDocumentDiv').style.display = "block";
					document.getElementById('patientVisitDetailsWholeDiv').style.display = "block";

					document.getElementById('shareDocumentDiv').innerHTML = content;

				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}

function loadPatientShareVisits(type, patId, visitDate) {
	var content = '';
	content += '<table class="table table-hover table-bordered" id="organizationTable">';
	content += '<thead><th></th><th>Patient Name</th><th>DOB</th><th>Date of Visit</th><th>Reason for Visit</th><th>Physician Consulted</th><th>Prescription of Physician</th></thead>';
	document.getElementById('patientShareVisitsDiv').innerHTML = content;
	$
			.ajax({
				type : "GET",
				url : contextPath + "/patient/getPatientShareVisitDetails.do",
				data : {
					"type" : type,
					"patientId" : patId,
					"visitDate" : visitDate
				},
				cache : false,
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);
					var dateOfVisit = dov = '';
					if (parsedJson && parsedJson.length > 0) {
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

						if (parsedJson[0][7] != ''
								&& parsedJson[0][7] != undefined) {
							document.getElementById('hdnPatientId').value = parsedJson[0][7];
						}
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
							
							content += '<td> <input type="radio" value='
									+ parsedJson[i][0]
									+ ' id="patVisitId" name="patVisitId"'
									+ '/></td>';
							content += '<td id="visitdate' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][1] + '</td>';
							content += '<td id="visitdate' + i
									+ '"  class="user_list_link">' + dob
									+ '</td>';
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

					document.getElementById('patientShareVisitsDiv').style.display = "block";
					document.getElementById('patientVisitDetailsWholeDiv').style.display = "block";
					document.getElementById('patientShareVisitsDiv').innerHTML = content;
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}

function loadPatientShareSuccess() {
	var answer = confirm("Your clinical information shared successfully.");
	if (answer) {
		window.location.href = contextPath + "/administrator/adminshare.do";
	}
}

function loadPendingRequests(patId) {
	var content = '';
	content += '<table class="table table-hover table-bordered" id="organizationTable">';
	content += '<thead><th>Patient Name</th><th>Physician Name</th><th>Request Date</th><th>Status</th></thead>';
	document.getElementById('requestPendingTableDiv').innerHTML = content;
	$
			.ajax({
				type : "GET",
				url : contextPath + "/patient/getPatientRequestPending.do",
				data : "patientId=" + patId,
				cache : false,
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);
					var dateOfVisit = dov = '';
					if (parsedJson && parsedJson.length > 0) {
						var requestDate = rdate = '';
						for ( var i = 0; i < parsedJson.length; i++) {
							requestDate = parsedJson[i][3];

							if (requestDate == undefined
									|| requestDate == 'undefined') {
								rdate = '';
							} else {
								requestDate = new Date(parsedJson[i][3]);
								rdate = (requestDate.getMonth() + 1) + "/"
										+ requestDate.getDate() + "/"
										+ requestDate.getFullYear();
							}

							document.getElementById('sharePatientName').value = parsedJson[0][1];
							for ( var i = 0; i < parsedJson.length; i++) {
								
								content += '<td id="visitdate' + i
										+ '"  class="user_list_link">'
										+ parsedJson[i][1] + '</td>';
								content += '<td id="physician' + i
										+ '"  class="user_list_link">'
										+ parsedJson[i][2] + '</td>';
								content += '<td id="prescription' + i
										+ '"  class="user_list_link">' + rdate
										+ '</td>';
								content += '<td id="prescription'
										+ i
										+ '"  class="user_list_link"><a href="#" onclick="javscript:adminShareRequestForm('
										+ parsedJson[i][0] + ','
										+ parsedJson[i][5] + ');">'
										+ parsedJson[i][4] + '</a></td>';

								content = content + '</tr>';
							}
						}
					} else {
						content += '<tr><td colspan="5" align="center" > No Data Found.</td></tr>';
					}
					
					content += '</table>';

					document.getElementById('requestPendingTableDiv').innerHTML = content;

				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}

function adminShareRequestForm(shareId, physicianId) {
	if (shareId != null && shareId != undefined) {
		document.getElementById("hdnshareId").value = shareId;
	}
	if (physicianId != null && physicianId != undefined) {
		document.getElementById("hdnPhysicianId").value = physicianId;
	}

	var patId = document.getElementById('hdnPatientId').value;
	// loadPatientShareVisits(2,patId,'234234');

	loadPatientShareDocuments(patId);
	loadPhysicianOrganization();
}
