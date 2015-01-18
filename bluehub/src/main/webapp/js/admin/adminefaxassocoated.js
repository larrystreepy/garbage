function callDocumentOpenFile(fileName,userId) { 
//	var userId = document.getElementById('hdnpatId').value;
	 if (fileName == ''){
	 }else{    
	  var url=contextPath+"/DocumentDownload?fileName="+fileName+"&userId="+userId; 
	  window.open(url,'','width=700,height=500'); 
	 }  
	}
function fnUnAssociateFaxDocuments() {
	document.getElementById('faxDocuments').style.display = "block";
	
	var count = document.getElementById("hdnCount").value;
	if(count>0){
	var url = "count="+count;
	for(var i = 0; i<count;i++){
		var ur;
		if(document.getElementById("hdnFaxFileCheckBox_"+i).checked==true){
			ur = "&hdnFaxFileCheckBox_"+i+"=true";
		}else{
			ur = "&hdnFaxFileCheckBox_"+i+"=false";
		}
		
		var patientId = +document.getElementById("hdnPatientId_"+i).value;
		
		var docId = document.getElementById("hdnDocId_"+i).value;
		
		var hdnId = document.getElementById("hdnFaxFile_"+i).value;
		
		url+= ur+"&hdnFaxFile_"+i+"="+hdnId+"&docId_"+i+"="+docId+"&patientId_"+i+"="+patientId;
	}
	
	$
	.ajax({
		type : "GET",
		url : contextPath + "/patient/unAssociateFaxDocuments.do",
		data : url,
		cache : false,
		async : false,
		success : function(response) {
			var parsedJson = $.parseJSON(response);
			searchPatientFaxRecords("0");
//			document.getElementById('hdnPatientId')="";
			document.getElementById('success_msg_span').innerHTML = parsedJson.message;
			fnSetTimeout('success_msg_span',3000);
		},
		error : function(e) {
			alert('Error: ' + e.responseText);
		}
	});
	}else{
		
		document.getElementById('success_msg_span').innerHTML = 'No selected documents to Un Associate.';
		fnSetTimeout('success_msg_span',3000);
	}
}

function searchPatientFaxRecords(patId) {
	document.getElementById("faxDocuments").style.display = "block";
	var content = '';
	content += '<table style="width: 100%" id="faxDocTable" class="table table-hover table-bordered">';
	content += '<thead>';
	content += '<th>S.No</th>';
	content += '<th>Document</th>';
	content += '<th>Patient Name</th>';
	content += '<th>Organization</th>';
	content += '<th>Received Date</th>';
	content += '<th>Associated Date</th>';
	content += '<th>Choose File</th>';
	content += '</thead>';
	document.getElementById('faxDocuments').innerHTML = content;
	$
	.ajax({
		type : "GET",
		url : contextPath + "/patient/getEfaxAssocoatedDocuments.do",
		data : "patientId="+patId,
		cache : false,
		async : false,
		success : function(response) {
			document.getElementById("hdnCount").value = "";
			var parsedJson = $.parseJSON(response);
			if(parsedJson.length>'0'){
			if (parsedJson != null) {
				document.getElementById("hdnCount").value = parsedJson.length;
				for ( var i = 0; i < parsedJson.length; i++) {
					var file = parsedJson[i][0];
					
				content += '<tr><td id="agencytd' + i
						+ '"  class="user_list_link">'
						+ (i+1) + '<input type="hidden" id="hdnDocId_'+i+'" value="'+parsedJson[i][0]+'"></input>'
						+'<input type="hidden" id="hdnPatientId_'+i+'" value="'+parsedJson[i][4]+'"></input></td>';
				content += '<td id="emailtd' + i
		                + '"  class="user_list_link"><a href="#" onclick="callDocumentOpenFile(\''+parsedJson[i][1]+'\',\''+parsedJson[i][4]+'\')"/><span>'
						+ parsedJson[i][1] + '</span></td>';
			 	content += '<td id="emailtdf' + i
                + '"  class="user_list_link">'+parsedJson[i][3]+'</td>'; 
				content += '<td id="emailtdf' + i
                + '"  class="user_list_link">'+parsedJson[i][5]+'</td>'; 
				content += '<td id="emailtdtf' + i
                + '"  class="user_list_link">'+parsedJson[i][6]+'</td>'; 
				content += '<td id="emailtdrf' + i
                + '"  class="user_list_link">'+parsedJson[i][7]+'</td>'; 
									
				content += '<td id="emailtds' + i
                + '"  class=""><input id="hdnFaxFileCheckBox_'+i+'" name="hdnFaxFileCheckBox_'+i+'"  type="checkbox"'
                +'onclick="fnSetTotalCountDocuments(\''+i+'\')" ></input>'
                +'<input type="hidden" id="hdnFaxFile_'+i+'" name="hdnFaxFile_'+i+'" value="'+parsedJson[i][1]+'"></input></td>';
				
				content = content + '</tr>';
					
				}
				document.getElementById('faxDocuments').innerHTML = content;
			}
			
			}else{
				
				content  += '<td align="center" colspan="0" id="agencytd">No Documents Found</td>';
				content = content + '</tr>';
				
				document.getElementById('faxDocuments').innerHTML = content;
			}
			
		},
		error : function(e) {
			alert('Error: ' + e.responseText);
		}
	});
	
}


function loadPatientsTable() {
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
										return '<a href="javascript:searchPatientFaxRecords('
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
function proceedUploadClinicalInformation() {

	document.getElementById("uploadClinicalInfoDiv").style.display = "none";

	document.getElementById("dropzoneDiv").style.display = "block";
}



function displayUserData(id) {

	document.getElementById("hdnVisitId").value = id;

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



