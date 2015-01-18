function fff(physicianid) {
	document.getElementById('visitRecords').style.display = "block";
	document.getElementById('visitRecords').style.display = "block";
	var params = "physicianid=" + physicianid;
	$('#patientVisitTableId').dataTable(
			{
				"processing" : true,
				"bJQueryUI" : true,
				"bFilter" : false,
				"bDestroy" : true,
				"bServerSide" : true,
				"sAjaxSource" : contextPath
						+ "/administrator/adminsearchphysicianvisitrecords.do?"
						+ params,
				"bProcessing" : true,

				"aoColumns" : [
						{
							"mDataProp" : "Patient Name",
							"bSortable" : false,

							"fnRender" : function(oObj) {
								
								var id = oObj.aData['id'];
								var patId = oObj.aData['patId'];
								document.getElementById('hdnPatId').value = patId;
								
								displayUserData("0",patId);
								var name = oObj.aData['Patient Name'];
								return '<a href="javascript:displayUserData('
										+ id + ','+patId+')" >' + name + '</a>'
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


function callAdminDocumentOpenFile(fileName,userId) { 
//	  var userId = document.getElementById('hdnPatId').value;
	 if (fileName == ''){
	 }else{    
	  var url=contextPath+"/DocumentDownload?fileName="+fileName+"&userId="+userId; 
	  window.open(url,'','width=700,height=500');
	
	 }  
	}

 


function searchPhysicianVisitRecords(physicianid) {
	document.getElementById('visitDocuments').innerHTML = "";
	displayAdminSearchPhysicianData(physicianid);
	//fff(physicianid);
	

}

/*function displayUserData(visitid) {

	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/admindetailsvisit.do",
				cache : false,
				data : "visitid=" + visitid,
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);

					document.getElementById('visitDocuments').style.display = "block";
					document.getElementById("viewPhyAllVisit").style.display = "block";
					var content = '';
					content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
					content += '<thead>';
					content += '<th>Upload Date</th>';
					content += '<th>Documents</th>';
					content += '<th>Upload Type</th>';
					content += '<th>Visit Date</th>';
					content += '</thead>';
					document.getElementById('visitDocuments').innerHTML = content;

					var doc = parsedJson.documents;

					if (parsedJson.visit[0][3] == undefined
							|| parsedJson.visit[0][3] == 'undefined') {
						dov = '';
					} else {
						dateOfVisit = new Date(parsedJson.visit[0][3]);
						dov = (dateOfVisit.getMonth() + 1) + "/"
								+ dateOfVisit.getDate() + "/"
								+ dateOfVisit.getFullYear();
					}
					document.getElementById('visitid').value = parsedJson.visit[0][0];
					document.getElementById('date_of_visit').value = dov;
					document.getElementById('reason_for_visit').value = parsedJson.visit[0][5];
					document.getElementById('prescription').value = parsedJson.visit[0][4];
					document.getElementById('vistAllPatientName').value = parsedJson.visit[0][1];
					document.getElementById('vistAllPhysicianName').value = parsedJson.visit[0][2];
					document.getElementById("viewPhyAllVisit").style.display = "block";
					if (typeof doc == "" || doc == "undefined" || doc == null) {

						content = content + '<tr>';
						content += '<td  class="user_list_link">NO DOCUMENTS</td>';

						content = content + '</tr>';

						content += '</table>';
						document.getElementById('visitDocuments').innerHTML = content;
					} else {
						for ( var i = 0; i < doc.length; i++) {

							content = content
									+ '<tr onclick="callDocumentOpenFile(\''
									+ doc[i][0] + '\')">';
							content += '<td  class="user_list_link">' + doc[i][0]
							+ '</td>';
							content += '<td  class="user_list_link">' + doc[i][0]
									+ '</td>';

							content = content + '</tr>';
						}

						content += '</table>';
						document.getElementById('visitDocuments').innerHTML = content;
					}

				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});

}*/

function displayUserData(visitid,patid){

	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/admindetailsvisit.do",
		cache : false,
		data : "visitid=" +visitid+"&patientId="+patid,
		async : false,
		success : function(response) {
			var parsedJson = $.parseJSON(response);

			document.getElementById('visitDocuments').style.display="block";
		 	var content = '';
	 		content += '<table style="width: 60%;margin-top:5%;" id="renderUserListTable" class="table table-hover table-bordered">';
		 	content += '<thead>';
		 	content += '<th>Date</th>';
		 	content += '<th>Documents</th>';
//		 	content += '<th>Upload Type</th>';
//		 	content += '<th>Visit Date</th>';
	 	
	 		content += '</thead>';
		 	document.getElementById('visitDocuments').innerHTML = content;

		 	
			
			var doc=parsedJson.documents;
			
			if(parsedJson.visit == undefined || parsedJson.visit[0][3] == 'undefined' || parsedJson.visit[0][3] == undefined){
				dov = '';
				document.getElementById("viewPhyAllVisit").style.display = "none";
			}else{
				dateOfVisit = new Date(parsedJson.visit[0][3]); 
				dov = (dateOfVisit.getMonth() + 1) + "/" + dateOfVisit.getDate() + "/" + dateOfVisit.getFullYear();
				document.getElementById('visitid').value=parsedJson.visit[0][0];
				//document.getElementById("panel-typeahead").style.display = "block";
				document.getElementById('date_of_visit').value = dov;
				document.getElementById('reason_for_visit').value = parsedJson.visit[0][5];
				document.getElementById('prescription').value = parsedJson.visit[0][4];
				document.getElementById('vistAllPatientName').value = parsedJson.visit[0][1];
				document.getElementById('vistAllPhysicianName').value = parsedJson.visit[0][2];
				//document.getElementById('documentName').value = parsedJson[0][10];  
				document.getElementById("viewPhyAllVisit").style.display = "block";
			}	
			
			if(typeof doc=="" || doc=="undefined" || doc==null || doc.length == 0){

			 	content = content + '<tr>';
						content += '<td align="center" class="user_list_link" colspan="2">NO DOCUMENTS</td>';
						
					content = content + '</tr>';
					
				  
					content += '</table>';
					document.getElementById('visitDocuments').innerHTML = content;
			}else{
				for (var i = 0; i < doc.length; i++) {

					var uploadType;
					var visitdate;
					if(doc[i][2]=='Dummy'){
						uploadType = 'Auto Create Visit';
						visitdate = '-----';
					}else{
						uploadType = 'Normal Visit';
						
						var dateOfVisit = new Date(doc[i][2]); 
					  visitdate = (dateOfVisit.getMonth() + 1) + "/" + dateOfVisit.getDate() + "/" + dateOfVisit.getFullYear();
					}
					
					var dateOfVisit = new Date(doc[i][1]); 
					var dov = (dateOfVisit.getMonth() + 1) + "/" + dateOfVisit.getDate() + "/" + dateOfVisit.getFullYear();
//				if(doc[i][0]!='Signature.png'){
					
					content = content + '<tr onclick="callDocumentOpenFile(\''+doc[i][0]+'\')">';
					content += '<td  class="user_list_link">'+ dov + '</td>';
						content += '<td  class="user_list_link"><a href="#" onclick=""><span>'
								+ doc[i][0] + '</span></a></td>';
//						content += '<td class="user_list_link" >'+uploadType+'</td>';
//						content += '<td class="user_list_link" >'+visitdate+'</td>';
						
					content = content + '</tr>';
					
//				}
					}

				  
					content += '</table>';
					document.getElementById('visitDocuments').innerHTML = content;
			} 
			
			
			
		},
		error : function(e) {
			alert('Error: ' + e.responseText);
		}
	});
	
}
function onloadVisitList() {


	var content = '';
	content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
	content += '<thead>';
	content += '<th>Patient Name</th>';
	content += '<th>DOB</th>';
	content += '<th>Visit Date</th>';
	content += '<th>Physician</th>';
	content += '<th>Reason For Visit</th>';
	content += '</thead>';
	document.getElementById('visitRecords').innerHTML = content;

	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/loadalllist.do",
				// data : "orgid=" + orgid,
				cache : false,
				async : false,
				success : function(response) {

					if (response == "null" || response == "") {
						content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

						
						content += '</table>';
						document.getElementById('visitRecords').innerHTML = content;

					} else {

						var parsedJson = $.parseJSON(response);
						for ( var i = 0; i < parsedJson.length; i++) {

							content += '<td id="agencytd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].patientname + '</td>';
							content += '<td id="emailtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].dob + '</td>';
							content += '<td id="addresstd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].visitdate + '</td>';
							content += '<td id="billingtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].physicianname + '</td>';
							content += '<td id="contacttd' + i
									+ '" class="user_list_link">'
									+ parsedJson[i].reasonforvisit + '</td>';
							
							content = content + '</tr>';
						}
						content += '</table>';
						document.getElementById('visitRecords').innerHTML = content;

					}
				},
				error : function(e) {
					// alert('Error: ' + e.responseText);
				}
			});
}

function loadAllPhysician() {
	document.getElementById('physicianDetailss').style.display = "block";
	document.getElementById('patientDetails').style.display = "none";

	var orgid = document.getElementById('selectOrganization').value;

	var practiceid = document.getElementById('selectPractice').value;
	var searchphysician = document.getElementById('physicianname').value;
	var params = "orgid=" + orgid + "&practiceid=" + practiceid
			+ "&searchphysician=" + searchphysician;

	$('#DataTables_Table_1')
			.dataTable(
					{
						"bStateSave" : true,
						"processing" : true,
						"bJQueryUI" : true,
						"bFilter" : false,
						"bDestroy" : true,
						// "bAutoWidth": true,
						// "sDom": '<"top"i>rt<"bottom"flp><"clear">',
						"bServerSide" : true,
						"sAjaxSource" : contextPath
								+ "/administrator/adminallphysiciandetails.do?"
								+ params,
						"bProcessing" : true,
						"aoColumns" : [
								{
									"mDataProp" : "Physician Name",
									"bSortable" : false,

									"fnRender" : function(oObj) {

										var id = oObj.aData['id'];
										var name = oObj.aData['Physician Name'];
										var org = oObj.aData['Organization'];
										var practice = oObj.aData['Practice'];

										return '<a href="javascript:searchPhysicianVisitRecords('
												+ id + ')" >' + name + '</a>'
									}
								},
								{
									"mDataProp" : "Organization",
									"bSortable" : false,

									"fnRender" : function(oObj) {

										var id = oObj.aData['id'];
										var name = oObj.aData['Physician Name'];
										var org = oObj.aData['Organization'];
										var practice = oObj.aData['Practice'];

										return '<a href="javascript:searchPhysicianVisitRecords('
												+ id + ')">' + org + '</a>'
									}
								},
								{
									"mDataProp" : "Practice",
									"bSortable" : false,

									"fnRender" : function(oObj) {

										var id = oObj.aData['id'];
										var name = oObj.aData['Physician Name'];
										var org = oObj.aData['Organization'];
										var practice = oObj.aData['Practice'];

										return '<a href="javascript:searchPhysicianVisitRecords('
												+ id
												+ ')">'
												+ practice
												+ '</a>'
									}
								} ]

					// "aaSorting": [[ 2, 'desc' ]]

					});

	// }
}

function loadPractice() {

	var orgid = document.getElementById('selectOrganization').value;

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

			var defaultOption = document.createElement("option");
			defaultOption.text = 'Select';
			defaultOption.value = '-1';
			combo.add(defaultOption);
			for ( var i = 0; i < parsedJson.length; i++) {
				var option = document.createElement("option");
				option.text = parsedJson[i].practicename;
				option.value = parsedJson[i].id;
				
				combo.add(option);
			}
		},
		error : function(e) {
			// alert('Error: ' + e.responseText);
		}
	});

	document.getElementById('practiceDiv').style.display = 'block';
	document.getElementById('physicianlabel').style.display = 'block';
}

function loadPatientsTable() {

	document.getElementById('physicianDetailss').style.display = "none";
	document.getElementById('patientDetails').style.display = "block";
	var searchPatient = document.getElementById('patientname').value;
	var orgid = -1;

	var params = "searchPatient=" + searchPatient + "&orgid=" + orgid;
	$('#patientLoadTable')
			.dataTable(
					{
						"bStateSave" : true,
						"processing" : true,
						"bJQueryUI" : true,
						"bFilter" : false,
						"bDestroy" : true,
						// "bAutoWidth": true,
						// "sDom": '<"top"i>rt<"bottom"flp><"clear">',
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
										var patid = oObj.aData['patId'];
										var id = oObj.aData['id'];
										var name = oObj.aData['Patient Name'];
										// var org = oObj.aData['Organization'];
										// var practice =
										// oObj.aData['Practice'];

										return '<a href="javascript:searchPatientDocumentVisitRecords('
												+ id + ','+patid+')" >' + name + '</a>'
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

					// "aaSorting": [[ 2, 'desc' ]]

					});

}

function loadPatientsTabless() {

	document.getElementById('physicianDetailss').style.display = "none";
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

	if (searchPatient == "") {

		content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

		
		content += '</table>';
		document.getElementById('patientDetails').innerHTML = content;
		document.getElementById('searchButton').style.display = "none";

	} else {

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

							
							content += '</table>';
							document.getElementById('patientDetails').innerHTML = content;
							document.getElementById('searchButton').style.display = "none";
						} else {

							var parsedJson = $.parseJSON(response);

							if (parsedJson != null) {

								for ( var i = 0; i < parsedJson.length; i++) {

									content = content
											+ '<tr onclick="searchPatientVisitRecords(\''
											+ parsedJson[i].userid + '\')">';
									
									content += '<td id="agencytd' + i
											+ '"  class="user_list_link">'
											+ parsedJson[i].firstname + '</td>';
									content += '<td id="agencytd' + i
											+ '"  class="user_list_link">'
											+ parsedJson[i].lastname + '</td>';
									content += '<td id="emailtd' + i
											+ '"  class="user_list_link">'
											+ parsedJson[i].dateofbirth
											+ '</td>';
									content += '<td id="agencytd' + i
											+ '"  class="user_list_link">'
											+ parsedJson[i].ssn + '</td>';
									content += '<td id="agencytd' + i
											+ '"  class="user_list_link">'
											+ parsedJson[i].contact_number
											+ '</td>';
									content += '<td id="addresstd' + i
											+ '"  class="user_list_link">'
											+ parsedJson[i].address + '</td>';
									
									content = content + '</tr>';
								}

								
								content += '</table>';
								document.getElementById('patientDetails').innerHTML = content;
							}

						}

					},
					error : function(e) {
						// alert('Error: ' + e.responseText);
					}
				});

	}

}

// patientDetails

function jsFunction() {

	var x = document.getElementById("mySelect").value;
	if (x == 1) {
		/*if(document.getElementById('visitRecords')){
			document.getElementById('visitRecords').style.display = 'none';
			}*/
		if(document.getElementById('visitDocuments')){
			document.getElementById('visitDocuments').style.display = 'none';
			}
		if(document.getElementById('viewPhyAllVisit')){
			document.getElementById('viewPhyAllVisit').style.display = 'none';
			}
		if(document.getElementById('physicianDetails')){
			document.getElementById('physicianDetails').style.display = 'none';
			}
		
		if(document.getElementById('patientDetails')){
			document.getElementById('patientDetails').style.display = 'none';
			}
		document.getElementById('datelabel').style.display = 'block';
		document.getElementById("searchButton").style.display = "block";
		document.getElementById('physicianlabel').style.display = 'none';
		document.getElementById('organizationlabel').style.display = 'none';
		document.getElementById('practiceDiv').style.display = 'none';

		document.getElementById('taglabel').style.display = 'none';
		document.getElementById('patientlabel').style.display = 'none';
		document.getElementById('keywordlabel').style.display = 'none';
		document.getElementById('physicianDetailss').style.display = "none";
		document.getElementById('patientDetails').style.display = "none";
		//document.getElementById('visitRecords').style.display = "none";

		document.getElementById('viewPhyAllVisit').style.display = "none";
		

	} else if (x == 2) {

		document.getElementById("searchButton").style.display = "none";
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
				defaultOption.value = '';
				combo.add(defaultOption);
				for ( var i = 0; i < parsedJson.length; i++) {
					var option = document.createElement("option");
					option.text = parsedJson[i].organizationname;
					option.value = parsedJson[i].id;
					
					combo.add(option);
				}
				loadAllPhysician();
			},
			error : function(e) {
				// alert('Error: ' + e.responseText);
			}
		});
		if(document.getElementById('visitRecords')){
			document.getElementById('visitRecords').style.display = 'none';
			}
		if(document.getElementById('visitDocuments')){
			document.getElementById('visitDocuments').style.display = 'none';
			}
		if(document.getElementById('viewPhyAllVisit')){
			document.getElementById('viewPhyAllVisit').style.display = 'none';
			}
		if(document.getElementById('physicianDetails')){
			document.getElementById('physicianDetails').style.display = 'none';
			}
		
		if(document.getElementById('patientDetails')){
			document.getElementById('patientDetails').style.display = 'none';
			}
		document.getElementById('organizationlabel').style.display = 'block';
		document.getElementById('datelabel').style.display = 'none';
		document.getElementById('taglabel').style.display = 'none';
		document.getElementById('patientlabel').style.display = 'none';
		document.getElementById('keywordlabel').style.display = 'none';

	
		
	} else if (x == 3) {
		if(document.getElementById('visitRecords')){
			document.getElementById('visitRecords').style.display = 'none';
			}
		if(document.getElementById('visitDocuments')){
			document.getElementById('visitDocuments').style.display = 'none';
			}
		if(document.getElementById('viewPhyAllVisit')){
			document.getElementById('viewPhyAllVisit').style.display = 'none';
			}
		if(document.getElementById('physicianDetails')){
			document.getElementById('physicianDetails').style.display = 'none';
			}
		
		if(document.getElementById('patientDetails')){
			document.getElementById('patientDetails').style.display = 'none';
			}
		
		document.getElementById("searchButton").style.display = "block";
		document.getElementById('taglabel').style.display = 'block';
		document.getElementById('physicianlabel').style.display = 'none';
		document.getElementById('organizationlabel').style.display = 'none';
		document.getElementById('practiceDiv').style.display = 'none';
		document.getElementById('datelabel').style.display = 'none';
		document.getElementById('patientlabel').style.display = 'none';
		document.getElementById('keywordlabel').style.display = 'none';

		document.getElementById('patientDetails').style.display = "none";
		document.getElementById('visitRecords').style.display = "none";

		document.getElementById('viewPhyAllVisit').style.display = "none";

		

	} else if (x == 4) {
		if(document.getElementById('visitRecords')){
			document.getElementById('visitRecords').style.display = 'none';
			}
		if(document.getElementById('visitDocuments')){
			document.getElementById('visitDocuments').style.display = 'none';
			}
		if(document.getElementById('viewPhyAllVisit')){
			document.getElementById('viewPhyAllVisit').style.display = 'none';
			}
		if(document.getElementById('physicianDetails')){
			document.getElementById('physicianDetails').style.display = 'none';
			}
		
		if(document.getElementById('patientDetails')){
			document.getElementById('patientDetails').style.display = 'none';
			}
		
		document.getElementById("searchButton").style.display = "none";
		document.getElementById('patientlabel').style.display = 'block';
		document.getElementById('physicianlabel').style.display = 'none';
		document.getElementById('organizationlabel').style.display = 'none';
		document.getElementById('practiceDiv').style.display = 'none';
		document.getElementById('datelabel').style.display = 'none';
		document.getElementById('taglabel').style.display = 'none';
		document.getElementById('keywordlabel').style.display = 'none';
		document.getElementById('physicianDetailss').style.display = "none";
		document.getElementById('patientDetails').style.display = "block";

		loadPatientsTable();

		

	} else if (x == 5) {
		if(document.getElementById('visitRecords')){
			document.getElementById('visitRecords').style.display = 'none';
			}
		if(document.getElementById('visitDocuments')){
			document.getElementById('visitDocuments').style.display = 'none';
			}
		if(document.getElementById('viewPhyAllVisit')){
			document.getElementById('viewPhyAllVisit').style.display = 'none';
			}
		if(document.getElementById('physicianDetails')){
			document.getElementById('physicianDetails').style.display = 'none';
			}
		
		if(document.getElementById('patientDetails')){
			document.getElementById('patientDetails').style.display = 'none';
			}
		
		document.getElementById("searchButton").style.display = "none";
		document.getElementById('keywordlabel').style.display = 'block';
		document.getElementById('physicianlabel').style.display = 'none';
		document.getElementById('organizationlabel').style.display = 'none';
		document.getElementById('practiceDiv').style.display = 'none';
		document.getElementById('datelabel').style.display = 'none';
		document.getElementById('taglabel').style.display = 'none';
		document.getElementById('patientlabel').style.display = 'none';
		
		
	}
}

function searchDateVisitRecords(){
		document.getElementById('visitRecords').style.display="block";
		
		
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
								+ "/administrator/adminsearchdatevisitrecords.do?searchDate="
								+ document.getElementById('datetext').value,
						"bProcessing" : true,

						"aoColumns" : [
								{
									"mDataProp" : "Patient Name",
									"bSortable" : false,

									"fnRender" : function(oObj) {
										var patid = oObj.aData['patId'];
										var id = oObj.aData['id'];
										var name = oObj.aData['Patient Name'];
										return '<a href="javascript:displayUserData('
												+ id + ','+patid+')" >' + name + '</a>'
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

function searchDateVisitRecordsss() {

	document.getElementById('visitRecords').style.display = "block";
	var content = '';
	content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
	content += '<thead>';
	content += '<th>Patient Name</th>';
	content += '<th>DOB</th>';
	content += '<th>Visit Date</th>';
	content += '<th>Physician</th>';
	content += '<th>Reason For Visit</th>';
	content += '</thead>';
	document.getElementById('visitRecords').innerHTML = content;

	$
			.ajax({
				type : "GET",
				url : contextPath
						+ "/administrator/adminsearchdatevisitrecords.do",
				data : "searchDate="
						+ document.getElementById('datetext').value,
				cache : false,
				async : false,
				success : function(response) {
					if (response == "null" || response == "") {
						content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

						
						content += '</table>';
						document.getElementById('visitRecords').innerHTML = content;

					} else {

						var parsedJson = $.parseJSON(response);
						for ( var i = 0; i < parsedJson.length; i++) {
							content = content + '<tr onclick="displayUserData('
									+ parsedJson[i].visitid + ')">';
							content += '<td id="agencytd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].patientname + '</td>';
							content += '<td id="emailtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].dob + '</td>';
							content += '<td id="addresstd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].visitdate + '</td>';
							content += '<td id="billingtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].physicianname + '</td>';
							content += '<td id="contacttd' + i
									+ '" class="user_list_link">'
									+ parsedJson[i].reasonforvisit + '</td>';
							
							content = content + '</tr>';
						}

						
						content += '</table>';
						document.getElementById('visitRecords').innerHTML = content;

					}

				},
				error : function(e) {
					// alert('Error: ' + e.responseText);
				}

			});
	document.getElementById('datetext').value = "";

}

	function searchTagVisitRecords(){
		
document.getElementById('visitRecords').style.display="block";
		
		
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
						+ "/administrator/adminsearchtagvisitrecords.do?tag="
						+ document.getElementById('tag').value,
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

	document.getElementById('tag').value = "";
}

function searchTagVisitRecordsdd() {

	var content = '';
	content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
	content += '<thead>';
	content += '<th>Patient Tag</th>';
	content += '<th>DOB</th>';
	content += '<th>Visit Date</th>';
	content += '<th>Physician</th>';
	content += '<th>Reason For Visit</th>';
	content += '</thead>';
	document.getElementById('visitRecords').innerHTML = content;

	$
			.ajax({
				type : "GET",
				url : contextPath
						+ "/administrator/adminsearchtagvisitrecords.do",
				data : "tag=" + document.getElementById('tag').value,
				cache : false,
				async : false,
				success : function(response) {

					if (response == "null" || response == "") {
						content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

						
						content += '</table>';
						document.getElementById('visitRecords').innerHTML = content;

					} else {

						var parsedJson = $.parseJSON(response);
						for ( var i = 0; i < parsedJson.length; i++) {

							content = content + '<tr onclick="displayUserData('
									+ parsedJson[i].visitid + ')">';
							content += '<td id="agencytd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].patientname + '</td>';
							content += '<td id="emailtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].dob + '</td>';
							content += '<td id="addresstd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].visitdate + '</td>';
							content += '<td id="billingtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].physicianname + '</td>';
							content += '<td id="contacttd' + i
									+ '" class="user_list_link">'
									+ parsedJson[i].reasonforvisit + '</td>';
							content = content + '</tr>';
						}

						
						content += '</table>';
						document.getElementById('visitRecords').innerHTML = content;
					}

				},
				error : function(e) {
					// alert('Error: ' + e.responseText);
				}

			});
	document.getElementById('tag').value = "";

}

function searchKeywordVisitRecords() {

	var content = '';
	content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
	content += '<thead>';
	content += '<th>Patient Tag</th>';
	content += '<th>DOB</th>';
	content += '<th>Visit Date</th>';
	content += '<th>Physician</th>';
	content += '<th>Reason For Visit</th>';
	content += '</thead>';
	document.getElementById('visitRecords').innerHTML = content;

	$
			.ajax({
				type : "GET",
				url : contextPath
						+ "/administrator/adminsearchkeywordvisitrecords.do",
				data : "keyword=" + document.getElementById('keyword').value,
				cache : false,
				async : false,
				success : function(response) {

					if (response == "null" || response == "") {
						content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

						
						content += '</table>';
						document.getElementById('visitRecords').innerHTML = content;

					} else {

						var parsedJson = $.parseJSON(response);
						for ( var i = 0; i < parsedJson.length; i++) {

							content += '<td id="agencytd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].patientname + '</td>';
							content += '<td id="emailtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].dob + '</td>';
							content += '<td id="addresstd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].visitdate + '</td>';
							content += '<td id="billingtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].physicianname + '</td>';
							content += '<td id="contacttd' + i
									+ '" class="user_list_link">'
									+ parsedJson[i].reasonforvisit + '</td>';
							content = content + '</tr>';
						}

						
						content += '</table>';
						document.getElementById('visitRecords').innerHTML = content;
					}

				},
				error : function(e) {
					// alert('Error: ' + e.responseText);
				}

			});
	document.getElementById('keyword').value = "";
}

function searchPhysicianVisitRecordsssss(physicianid) {
	
	document.getElementById('visitRecords').style.display = "block";
	var content = '';
	content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
	content += '<thead>';
	content += '<th>Patient Tag</th>';
	content += '<th>DOB</th>';
	content += '<th>Visit Date</th>';
	content += '<th>Physician</th>';
	content += '<th>Reason For Visit</th>';
	content += '</thead>';
	document.getElementById('visitRecords').innerHTML = content;

	$
			.ajax({
				type : "GET",
				url : contextPath
						+ "/administrator/adminsearchphysicianvisitrecords.do",
				data : "physicianid=" + physicianid,
				cache : false,
				async : false,
				success : function(response) {
					if (response == "null" || response == "") {
						content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

						
						content += '</table>';
						document.getElementById('visitRecords').innerHTML = content;

					} else {

						var parsedJson = $.parseJSON(response);

						for ( var i = 0; i < parsedJson.length; i++) {

							content = content + '<tr onclick="displayUserData('
									+ parsedJson[i].visitid + ')">';
							content += '<td id="agencytd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].patientname + '</td>';
							content += '<td id="emailtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].dob + '</td>';
							content += '<td id="addresstd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].visitdate + '</td>';
							content += '<td id="billingtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].physicianname + '</td>';
							content += '<td id="contacttd' + i
									+ '" class="user_list_link">'
									+ parsedJson[i].reasonforvisit + '</td>';
							content = content + '</tr>';
						}

						
						content += '</table>';
						document.getElementById('visitRecords').innerHTML = content;

					}

				},
				error : function(e) {
					// alert('Error: ' + e.responseText);
				}

			});
}


function searchPatientDocumentVisitRecords(patientid,patid) {
	document.getElementById('hdnPatId').value = patientid;
		
	displayAdminSearchPatientData(patid);
}		

function displayAdminSearchPatientData(visitid){ 
	var patid = 0;
		$.ajax({
			type : "GET",
			url : contextPath + "/administrator/adminClinicalSearchPatientdetails.do",
			cache : false,
			data : "visitid=" +visitid,
			async : false,
			success : function(response) {
				var parsedJson = $.parseJSON(response);

				document.getElementById('visitDocuments').style.display="block";
			 	var content = '';
		 		content += '<table style="width: 60%;margin-top:5%;" id="renderUserListTable" class="table table-hover table-bordered">';
			 	content += '<thead>';
			 	content += '<th>Date</th>';
			 	content += '<th>Documents</th>';
	 	
		 		content += '</thead>';
			 	document.getElementById('visitDocuments').innerHTML = content;

			 	
				
				var doc=parsedJson.documents;
				
				
				if(typeof doc=="" || doc=="undefined" || doc==null || doc.length == 0){

				 	content = content + '<tr>';
							content += '<td align="center" class="user_list_link" colspan="2">NO DOCUMENTS</td>';
							
						content = content + '</tr>';
						
					  
						content += '</table>';
						document.getElementById('visitDocuments').innerHTML = content;
				}else{
					for (var i = 0; i < doc.length; i++) {

						var uploadType;
						var visitdate;
						if(doc[i][2]=='Dummy'){
							uploadType = 'Auto Create Visit';
							visitdate = '-----';
						}else{
							uploadType = 'Normal Visit';
							
							var dateOfVisit = new Date(doc[i][2]); 
						  visitdate = (dateOfVisit.getMonth() + 1) + "/" + dateOfVisit.getDate() + "/" + dateOfVisit.getFullYear();
						}
						
						var dateOfVisit = new Date(doc[i][1]); 
						var dov = (dateOfVisit.getMonth() + 1) + "/" + dateOfVisit.getDate() + "/" + dateOfVisit.getFullYear();
				
						content = content + '<tr onclick="callAdminDocumentOpenFile(\''+doc[i][0]+'\',\''+doc[i][5]+'\')">';
						content += '<td  class="user_list_link">'+ dov + '</td>';
							content += '<td  class="user_list_link"><a href="#" onclick=""><span>'
									+ doc[i][0] + '</span></a></td>';
				
						content = content + '</tr>';

						}

					  
						content += '</table>';
						document.getElementById('visitDocuments').innerHTML = content;
				} 
				
				
				
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});
		
	}

function searchPatientVisitRecords(patientid,patid) {
	document.getElementById('hdnPatId').value = patientid;
		document.getElementById('visitRecords').style.display="block";
		displayUserData("0",patid);
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
										var patid = oObj.aData['patId'];
										displayUserData("0",patid);
										var id = oObj.aData['id'];
										var name = oObj.aData['Patient Name'];
										return '<a href="javascript:displayUserData('
												+ id + ','+patid+')" >' + name + '</a>'
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
function searchPatientVisitRecords1(patientid) {

	document.getElementById('visitRecords').style.display = "block";

	var content = '';
	content += '<table id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
	content += '<thead>';
	content += '<th>Patient Tag</th>';
	content += '<th>DOB</th>';
	content += '<th>Visit Date</th>';
	content += '<th>Physician</th>';
	content += '<th>Reason For Visit</th>';
	content += '</thead>';
	document.getElementById('visitRecords').innerHTML = content;

	// alert(document.getElementById('hdnPatientId').value)

	$
			.ajax({
				type : "GET",
				url : contextPath
						+ "/administrator/adminsearchpatientvisitrecords.do",
				data : "patientid=" + patientid,
				cache : false,
				async : false,
				success : function(response) {

					if (response == "null" || response == "") {
						content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

						
						content += '</table>';
						document.getElementById('visitRecords').innerHTML = content;

					} else {

						var parsedJson = $.parseJSON(response);

						for ( var i = 0; i < parsedJson.length; i++) {

							content = content + '<tr onclick="displayUserData('
									+ parsedJson[i].visitid + ')">';
							content += '<td id="agencytd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].patientname + '</td>';
							content += '<td id="emailtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].dob + '</td>';
							content += '<td id="addresstd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].visitdate + '</td>';
							content += '<td id="billingtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].physicianname + '</td>';
							content += '<td id="contacttd' + i
									+ '" class="user_list_link">'
									+ parsedJson[i].reasonforvisit + '</td>';
							content = content + '</tr>';
						}

						
						content += '</table>';
						document.getElementById('visitRecords').innerHTML = content;

					}

				},
				error : function(e) {
					// alert('Error: ' + e.responseText);
				}

			});

}

function showDetais() {

//	document.getElementById('visitRecords').style.display = "block";
	if (document.getElementById('datetext').value != '') { 
		displaySearchAdminUserData("0");
		//searchDateVisitRecords();

	} else if (document.getElementById('tag').value != '') {

		searchTagVisitRecords();

	} else if (document.getElementById('keyword').value != '') {

		searchKeywordVisitRecords();

	} else if (document.getElementById('physicianRadio')
			&& document.getElementById('physicianRadio').checked == true) {

		searchPhysicianVisitRecords();

	}

	else if (document.getElementById('patientRadio')
			&& document.getElementById('patientRadio').checked == true) {

		searchPatientVisitRecords();

	}
}


function displaySearchAdminUserData(visitid){ 
	var patid = 0;
		$.ajax({
			type : "GET",
			url : contextPath + "/administrator/adminClinicalSearchdetails.do",
			cache : false,
			data : "visitid=" +visitid+"&patientId="+patid+"&searchDate="+document.getElementById('datetext').value,
			async : false,
			success : function(response) {
				var parsedJson = $.parseJSON(response);

				document.getElementById('visitDocuments').style.display="block";
			 	var content = '';
		 		content += '<table style="width: 60%;margin-top:5%;" id="renderUserListTable" class="table table-hover table-bordered">';
			 	content += '<thead>';
			 	content += '<th>Date</th>';
			 	content += '<th>Documents</th>';
	 	
		 		content += '</thead>';
			 	document.getElementById('visitDocuments').innerHTML = content;

			 	
				
				var doc=parsedJson.documents;
				
				
				if(typeof doc=="" || doc=="undefined" || doc==null || doc.length == 0){

				 	content = content + '<tr>';
							content += '<td align="center" class="user_list_link" colspan="2">NO DOCUMENTS</td>';
							
						content = content + '</tr>';
						
					  
						content += '</table>';
						document.getElementById('visitDocuments').innerHTML = content;
				}else{
					for (var i = 0; i < doc.length; i++) {

						var uploadType;
						var visitdate;
						if(doc[i][2]=='Dummy'){
							uploadType = 'Auto Create Visit';
							visitdate = '-----';
						}else{
							uploadType = 'Normal Visit';
							
							var dateOfVisit = new Date(doc[i][2]); 
						  visitdate = (dateOfVisit.getMonth() + 1) + "/" + dateOfVisit.getDate() + "/" + dateOfVisit.getFullYear();
						}
						
						var dateOfVisit = new Date(doc[i][1]); 
						var dov = (dateOfVisit.getMonth() + 1) + "/" + dateOfVisit.getDate() + "/" + dateOfVisit.getFullYear();
				
						content = content + '<tr onclick="callAdminDocumentOpenFile(\''+doc[i][0]+'\',\''+doc[i][5]+'\')">';
						content += '<td  class="user_list_link">'+ dov + '</td>';
							content += '<td  class="user_list_link"><a href="#" onclick=""><span>'
									+ doc[i][0] + '</span></a></td>';
				
						content = content + '</tr>';

						}

					  
						content += '</table>';
						document.getElementById('visitDocuments').innerHTML = content;
				} 
				
				
				
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});
		
	}

function displayAdminSearchPhysicianData(visitid){ 
	var patid = 0;
		$.ajax({
			type : "GET",
			url : contextPath + "/administrator/adminClinicalSearchPhysiciandetails.do",
			cache : false,
			data : "visitid=" +visitid,
			async : false,
			success : function(response) {
				var parsedJson = $.parseJSON(response);

				document.getElementById('visitDocuments').style.display="block";
			 	var content = '';
		 		content += '<table style="width: 60%;margin-top:5%;" id="renderUserListTable" class="table table-hover table-bordered">';
			 	content += '<thead>';
			 	content += '<th>Date</th>';
			 	content += '<th>Documents</th>';
	 	
		 		content += '</thead>';
			 	document.getElementById('visitDocuments').innerHTML = content;

			 	
				
				var doc=parsedJson.documents;
				
				
				if(typeof doc=="" || doc=="undefined" || doc==null || doc.length == 0){

				 	content = content + '<tr>';
							content += '<td align="center" class="user_list_link" colspan="2">NO DOCUMENTS</td>';
							
						content = content + '</tr>';
						
					  
						content += '</table>';
						document.getElementById('visitDocuments').innerHTML = content;
				}else{
					for (var i = 0; i < doc.length; i++) {

						var uploadType;
						var visitdate;
						if(doc[i][2]=='Dummy'){
							uploadType = 'Auto Create Visit';
							visitdate = '-----';
						}else{
							uploadType = 'Normal Visit';
							
							var dateOfVisit = new Date(doc[i][2]); 
						  visitdate = (dateOfVisit.getMonth() + 1) + "/" + dateOfVisit.getDate() + "/" + dateOfVisit.getFullYear();
						}
						
						var dateOfVisit = new Date(doc[i][1]); 
						var dov = (dateOfVisit.getMonth() + 1) + "/" + dateOfVisit.getDate() + "/" + dateOfVisit.getFullYear();
				
						content = content + '<tr onclick="callAdminDocumentOpenFile(\''+doc[i][0]+'\',\''+doc[i][5]+'\')">';
						content += '<td  class="user_list_link">'+ dov + '</td>';
							content += '<td  class="user_list_link"><a href="#" onclick=""><span>'
									+ doc[i][0] + '</span></a></td>';
				
						content = content + '</tr>';

						}

					  
						content += '</table>';
						document.getElementById('visitDocuments').innerHTML = content;
				} 
				
				
				
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});
		
	}

function goPhysician() {

	document.getElementById('physicianInformations').style.display = 'block';
	document.getElementById('patientInformations').style.display = 'none';
}

function goPatient() {
	document.getElementById('patientInformations').style.display = 'block';
	document.getElementById('physicianInformations').style.display = 'none';
}