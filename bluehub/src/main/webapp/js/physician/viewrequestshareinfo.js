var mywindow;
function uploadPhysicianDocument(reqId, patientId, name) {

	mywindow = window.open(contextPath
			+ "/physician/uploadClinicalDocuments.do?requestId=" + reqId
			+ "&patientId=" + patientId + "&patientName=" + name,
			"UploadDocuments", "height=700,width=900");
}

function fnReloadRequestSharePatient(reqId, patientId, name) {
	self.close();

}

function submitUploadClinicalInformation() {

	var patientId = $('#hdnPatientId').val();
	var requestId = $('#hdnRequestId').val();

	var name = $('#hdnPatientName').val();
	$
			.ajax({
				type : "GET",
				url : contextPath
						+ "/physician/saveShareRequestBehalfOfPatient.do",
				cache : false,
				async : false,
				data : "patientid=" + patientId + "&requestId=" + requestId,
				success : function(response) {

					var parsedJson = $.parseJSON(response);

					if (parsedJson.status == "YES") {

						document.getElementById("success_msg_span1").innerHTML = parsedJson.message;

						fnSetTimeout("success_msg_span1", 3000);

						setTimeout('fnReloadRequestSharePatient(\'' + requestId
								+ '\',\'' + patientId + '\',\'' + name + '\')',
								1000);

					} else {

						document.getElementById("success_msg_span1").innerHTML = parsedJson.message;

						fnSetTimeout("success_msg_span1", 3000);
					}

				},
				error : function(e) {
				}
			});

}

function fnSample() {

	loadRequestPhysiciansList();
	setTimeout('fnSample()', 10000);

}

function loadRequestPhysiciansList() {

	document.getElementById('physicianDetails').style.display = "block";

	var content = '';
	content += '<div class="table-responsive table-responsive-datatables">';
	content += '<table style="width: 100%" class="table table-hover table-bordered">';
	content += '<thead>';
	content += '<th>Request From(Physician)</th>';
	content += '<th>Request About(Patient)</th>';
	content += '<th>Status</th>';
	content += '</thead>';
	document.getElementById('physicianDetails').innerHTML = content;
	$
			.ajax({
				type : "GET",
				url : contextPath + "/physician/getrequestinfoofpatient.do",
				cache : false,
				async : false,
				success : function(response) {

					var parsedJson = $.parseJSON(response);
					if (parsedJson == "null" || parsedJson == "" || parsedJson.length <= 0) {
						content += '<td class="user_list_link" align="center" colspan="5">No records/data available</td>';

					 
						content += '</table>';
						document.getElementById('physicianDetails').innerHTML = content;

					} else {

						
						for ( var i = 0; i < parsedJson.length; i++) {

							content = content
									+ '<tr onclick="uploadPhysicianDocument(\''
									+ parsedJson[i][0] + '\',\''
									+ parsedJson[i][4] + '\',\''
									+ parsedJson[i][1] + '\')">';
							content += '<td id="agencytd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][2] + '</td>';
							content += '<td id="emailtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][1] + '</td>';
							content += '<td id="addresstd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][3] + '</td>';
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

var displayMsgId = "";
function fnClearMsgField() {
	document.getElementById(displayMsgId).style.display = "none";
}
function fnSetTimeout(id, time) {
	if (time == undefined)
		time = 3000;
	displayMsgId = id;
	document.getElementById(displayMsgId).style.display = "block";
	setTimeout('fnClearMsgField()', time);
}