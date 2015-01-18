function loadOrgDetailsForEfax() {
	
	document.getElementById("physicianname").value="";
	var orgId = document.getElementById('selectOrganization').value; 
	
	document.getElementById('emailSuccess1').style.display = "none";
	
	document.getElementById('physicianDetails').style.display = "none";
	
	document.getElementById('emailAndFaxDivId').style.display = "none";
	document.getElementById('organizationFaxDivId').style.display = "block";
	document.getElementById('buttonlabel').style.display = "block";
	 $
	.ajax({
		type : "GET",
		url : contextPath
				+ "/patient/patientsearchorganizationinfo.do",
		data : "orgId=" + orgId,
		cache : false,
		async : false,
		success : function(response) {
			document.getElementById("hdnrequestType").value = "Organization";
			var parsedJson = $.parseJSON(response);
			document.getElementById("orgFax").value = parsedJson.message;

		},
		error : function(e) {
			alert('Error: ' + e.responseText);
		}
	}); 
}

	function setPhysicianValue(id) {
	
		document.getElementById("hdnPhysicianId").value = id;
		document.getElementById('emailSuccess1').style.display = "none";
		document.getElementById('emailAndFaxDivId').style.display = "block";
		document.getElementById('organizationFaxDivId').style.display = "none";
		document.getElementById('buttonlabel').style.display = "block";
		$
		.ajax({
			type : "GET",
			url : contextPath
					+ "/patient/patientsearchphysicianinfoinfo.do",
			data : "physicianid=" + id,
			cache : false,
			async : false,
			success : function(response) {
				document.getElementById("hdnrequestType").value = "Physician";
				
				var parsedJson = $.parseJSON(response);
				document.getElementById("physicianEmail").value = parsedJson.message
				document.getElementById("physicianFax").value = parsedJson.status;

			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});
	}
	
	
	
	function patientRequestClinicalInfoByFaxAndMail() {

		 var reqType = document.getElementById("hdnrequestType").value;
		 if(reqType=="Physician"){
			
			 var email = document.getElementById("physicianEmail").value;
			 var fax = document.getElementById("physicianFax").value;
			var physicianid = document.getElementById("hdnPhysicianId").value;
			 $
				.ajax({
					type : "GET",
					url : contextPath
							+ "/patient/patientreqclinicalinfotophysician.do",
					data : "mail=" + email+"&fax="+fax+"&physicianid="+physicianid,
					cache : false,
					async : false,
					success : function(response) {
						
						
						var parsedJson = $.parseJSON(response);
						document.getElementById('emailSuccess1').innerHTML = parsedJson.message;
						fnSetTimeout("emailSuccess1", 3000);

					},
					error : function(e) {
						alert('Error: ' + e.responseText);
					}
				});
			 
		 }else if(reqType=="Organization"){
			 var orgId = document.getElementById("selectOrganization").value;
			 var fax = document.getElementById("orgFax").value;
			 $
				.ajax({
					type : "GET",
					url : contextPath
							+ "/patient/patientreqclinicalinfotoorg.do",
					data :"fax="+fax+"&orgId="+orgId,
					cache : false,
					async : false,
					success : function(response) {
						
						
						var parsedJson = $.parseJSON(response);
						document.getElementById('emailSuccess1').innerHTML = parsedJson.message;
						fnSetTimeout("emailSuccess1", 3000);

					},
					error : function(e) {
						alert('Error: ' + e.responseText);
					}
				});
			 
		 }else{
			 
			 
		 }

	}
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

	
function fnCreateSignature(){
		
		var signText = document.getElementById('digitalSign').value;
		if(signText.trim()==""){
			
			document.getElementById('emailSuccess2').innerHTML = "Signature text cannot be empty.";
			fnSetTimeout("emailSuccess2", 3000);
		}else{
		
		$
		.ajax({
			type : "GET",
			url : contextPath
					+ "/patient/createSignatureFromText.do",
			data : "signText=" + signText.trim(),
			cache : false,
			async : false,
			success : function(response) {

				var parsedJson = $.parseJSON(response);
				document.getElementById('digitalSign').value = "";
				document.getElementById('emailSuccess2').innerHTML = parsedJson.message;
				fnSetTimeout("emailSuccess2", 3000);

			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});
		
		}
	}
/* function fnCreateSignature(){
		
		var signText = document.getElementById('digitalSign').value;
		
		$
		.ajax({
			type : "GET",
			url : contextPath
					+ "/patient/createSignatureFromText.do",
			data : "signText=" + signText,
			cache : false,
			async : false,
			success : function(response) {

				var parsedJson = $.parseJSON(response);
				document.getElementById('emailSuccess2').innerHTML = parsedJson.message;
				fnSetTimeout("emailSuccess2", 3000);

			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});
	} */

	function fnOnchangeMailId(){
		
		document.getElementById('physicianDetails').style.display = "none";
	}
	
	function loadPhysicianTable() {
		document.getElementById('physicianDetails').style.display = "block";
		document.getElementById('selectOrganization').value = "-1";
		document.getElementById('organizationFaxDivId').style.display = "none";

		
		var searchphysician = document.getElementById('physicianname').value;

		var params = "searchphysician=" + searchphysician;

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
									+ "/administrator/adminallphysiciandetailswithphysician.do?"
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

											return '<a data-target="modalBasic" href="javascript:setPhysicianValue('
													+ id
													+ ')" >'
													+ name
													+ '</a>'
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

											return '<a href="javascript:setPhysicianValue('
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

											return '<a href="javascript:setPhysicianValue('
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