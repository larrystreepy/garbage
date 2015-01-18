function loadPatientsTable() {
	document.getElementById('physicianDetails').style.display = "none";
	document.getElementById('patientDetails').style.display = "block";

	var searchPatient = document.getElementById('patientname').value;
	var orgid = -1;
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
										return '<a href="javascript:fnSetPatientHdnValue('
												+ id + ')" >' + name + '</a>'

									}
								},
							/*	{
									"mDataProp" : "Last Name",
									"bSortable" : false
								}, */
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

					});

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
			defaultOption.value = '';
			combo.add(defaultOption);

			for ( var i = 0; i < parsedJson.length; i++) {
				var option = document.createElement("option");
				option.text = parsedJson[i].practicename;
				option.value = parsedJson[i].id;
				combo.add(option);
			}
		},
		error : function(e) {
		}
	});
}

function fnSetPhysicianHdnValue(id, i) {

	document.getElementById("hdnPhysicianId").value = id;

	document.getElementById('visitRecords').style.display = "block";

	var userType = document.getElementById("mySelect").value;

	fnFindAuditTrails(userType);

}

function fnSetPatientHdnValue(id) {

	document.getElementById("hdnPatientId").value = id;
	
	document.getElementById('visitRecords').style.display = "block";

	var userType = document.getElementById("mySelect").value;

	fnFindAuditTrails(userType);
}

 
function loadPhysicianTable() {
	document.getElementById('physicianDetails').style.display = "block";
	document.getElementById('patientDetails').style.display = "none";

	var orgid = document.getElementById('selectOrganization').value;

	var practiceid = document.getElementById('selectPractice').value;
	var searchphysician = document.getElementById('physicianname').value;
	// if(practiceid!=""){
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

										return '<a href="javascript:fnSetPhysicianHdnValue('
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

										return '<a href="javascript:fnSetPhysicianHdnValue('
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

										return '<a href="javascript:fnSetPhysicianHdnValue('
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


 

function jsFunction() {


	var x = document.getElementById("mySelect").value;
	  if (x == 2) {

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
				//alert('Error: ' + e.responseText);
			}
		});

		document.getElementById('physicianlabel').style.display = 'block';
		document.getElementById('organizationlabel').style.display = 'block';
		document.getElementById('practiceDiv').style.display = 'block';
		document.getElementById('datelabel').style.display = 'none';
		document.getElementById('taglabel').style.display = 'none';
		document.getElementById('patientlabel').style.display = 'none';
		document.getElementById('keywordlabel').style.display = 'none';

		document.getElementById('visitRecords').style.display = 'none';
//		document.getElementById('searchButton').style.display = 'none';
		document.getElementById('patientDetails').style.display = 'none';
//		document.getElementById('physicianDetails').style.display = 'none';

	} else if (x == 4) {
		document.getElementById('patientlabel').style.display = 'block';
		document.getElementById('physicianlabel').style.display = 'none';
		document.getElementById('organizationlabel').style.display = 'none';
		document.getElementById('practiceDiv').style.display = 'none';
		document.getElementById('datelabel').style.display = 'none';
		document.getElementById('taglabel').style.display = 'none';
		document.getElementById('keywordlabel').style.display = 'none';
		
		document.getElementById('visitRecords').style.display = 'none';
//		document.getElementById('searchButton').style.display = 'none';
//		document.getElementById('patientDetails').style.display = 'none';
		document.getElementById('physicianDetails').style.display = 'none';
		document.getElementById('patientname').value="";
		

	}

	else {
		document.getElementById('visitRecords').style.display = 'none';
//		document.getElementById('searchButton').style.display = 'none';
		document.getElementById('patientDetails').style.display = 'none';
		document.getElementById('physicianDetails').style.display = 'none';
		document.getElementById('keywordlabel').style.display = 'none';
		document.getElementById('patientlabel').style.display = 'none';
		document.getElementById('taglabel').style.display = 'none';
		document.getElementById('physicianlabel').style.display = 'none';
		document.getElementById('organizationlabel').style.display = 'none';
		document.getElementById('datelabel').style.display = 'none';
	}
}



function showDetais() {

	document.getElementById('visitRecords').style.display = "block";

	var userType = document.getElementById("mySelect").value;

	fnFindAuditTrails(userType);

	
}

function fnFindAuditTrails(val) {
	var personId;
	if (val == "2") {

		personId = document.getElementById("hdnPhysicianId").value;

	} else if (val == "4") {

		personId = document.getElementById("hdnPatientId").value;
	}

	var content = '';
	content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
	content += '<thead>';
	content += '<th>Date</th>';
	content += '<th>IP Address</th>';
	content += '<th>Actions</th>';
	content += '</thead>';
	document.getElementById('visitRecords').innerHTML = content;

	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/audittrails.do",
				data : "userType=" + val + "&personId=" + personId,
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
									+ parsedJson[i][1] + '</td>';
							content += '<td id="emailtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][2] + '</td>';
							content += '<td id="addresstd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][3] + '</td>';
							
							content = content + '</tr>';
						}

						content += '</table>';
						document.getElementById('visitRecords').innerHTML = content;

					}

				},
				error : function(e) {
					//alert('Error: ' + e.responseText);
				}

			});

}
