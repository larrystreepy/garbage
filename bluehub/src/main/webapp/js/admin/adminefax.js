 
function callDocumentOpenFile(fileName) {
//	var userId = document.getElementById("hdnPatientId").value;
	if (fileName == '') {
		
	} else {
		var url = contextPath + "/DownloadEfaxFiles?fileName=" + fileName;
		window.open(url, '', 'width=700,height=500');
	}
}
function deleteFaxPatientDocument(fileName){
	$
	.ajax({
		type : "GET",
		url : contextPath + "/patient/deleteFaxDocument.do",
		data : "fileName="+fileName,
		cache : false,
		async : false,
		success : function(response) {
			var parsedJson = $.parseJSON(response);
			document.getElementById('success_msg_span').innerHTML = parsedJson.message;
			fnSetTimeout('success_msg_span',3000);
			searchPatientFaxRecords();
		},
		error : function(e) {
			alert('Error: ' + e.responseText);
		}
	});
}

function callDeleteFile(fileName){
	var answer = confirm("Are you want to delete the document ?")
	if (answer) {
		deleteFaxPatientDocument(fileName);
	}
	return false;
}
function fnShareFaxDocuments() {
	
//	if(document.getElementById('hdnPatientId').value==""){
//		
////		document.getElementById('success_msg_span').innerHTML = "Please select a patient";
////		fnSetTimeout('success_msg_span',3000);
//		
//	}else{
	document.getElementById('faxDocuments').style.display = "block";

	
	var count = document.getElementById("hdnCount").value;
	var patientId =document.getElementById("hdnPatientId").value; 
	if(patientId==""){
		patientId = 0;
	}
	var url = "patientId=" + patientId + "&count="+count;
	
	for(var i = 0; i<count;i++){
		var ur;
		if(document.getElementById("hdnFaxFileCheckBox_"+i).checked==true){
			ur = "&hdnFaxFileCheckBox_"+i+"=true";
		}else{
			ur = "&hdnFaxFileCheckBox_"+i+"=false";
		}
		var date = document.getElementById("hdnReceivedDate_"+i).value;
		url+= ur+"&date_"+i+"="+date+"&hdnFaxFile_"+i+"="+document.getElementById("hdnFaxFile_"+i).value;
	}
	
	$
	.ajax({
		type : "GET",
		url : contextPath + "/patient/shareFaxDocuments.do",
		data : url,
		cache : false,
		async : false,
		success : function(response) {
			var parsedJson = $.parseJSON(response);
			
			
			if(parsedJson.status=='Yes'){
				
				document.getElementById('success_msg_span').innerHTML = parsedJson.message;
				fnSetTimeout('success_msg_span',3000);
				searchPatientFaxRecords();
			}else{
				
				document.getElementById('success_msg_span').innerHTML = parsedJson.message;
				fnSetTimeout('success_msg_span',3000);
			}
//			 
			
		},
		error : function(e) {
			alert('Error: ' + e.responseText);
		}
	});
//	}
}

function setPatientFaxRecords(patientid){
	document.getElementById('hdnPatientId').value= patientid;
	
}

function searchPatientFaxRecords() {
	document.getElementById("faxDocuments").style.display = "block";
	var content = '';
	content += '<table style="width: 100%" id="faxDocTable" class="table table-hover table-bordered">';
	content += '<thead>';
	content += '<th>S.No</th>';
	content += '<th>Document</th>';
	content += '<th>Received Date</th>';
	content += '<th>Choose File To Share</th>';
	content += '<th>Delete Document</th>';
	content += '</thead>';
	document.getElementById('faxDocuments').innerHTML = content;
	$
	.ajax({
		type : "GET",
		url : contextPath + "/patient/getEfaxDocuments.do",
		data : "patientId=0",
		cache : false,
		async : false,
		success : function(response) {
			if(response!=""){
			var parsedJson = $.parseJSON(response);

			if (parsedJson != null) {
				document.getElementById("hdnCount").value = parsedJson.length;
				
				for ( var i = 0; i < parsedJson.length; i++) {
					var file = parsedJson[i][0];
					
				content += '<tr><td id="agencytd' + i
						+ '"  class="user_list_link">'
						+ (i+1) + '</td>';
				content += '<td id="emailtd' + i
		                + '"  class="user_list_link"><a href="#" onclick="callDocumentOpenFile(\''+file+'\')"/><span>'
						+ parsedJson[i][0] + '</span></td>';
				content += '<td id="emailtdf' + i
                + '"  class="user_list_link">'+parsedJson[i][3]+'<input type="hidden" id="hdnReceivedDate_'+i+'"'
                +'name="hdnReceivedDate_'+i+'" value='+parsedJson[i][3]+'></input></td>'; 
				
				content += '<td id="emailtds' + i
	                    + '"  class=""><input id="hdnFaxFileCheckBox_'+i+'" name="hdnFaxFileCheckBox_'+i+'"  type="checkbox"'
	                    +'onclick="fnSetTotalCountDocuments(\''+i+'\')" ></input>'
	                    +'<input type="hidden" id="hdnFaxFile_'+i+'" name="hdnFaxFile_'+i+'" value="'+parsedJson[i][1]+'"></input></td>';
			/*	content += '<td id="emailtdelete' + i
                        + '"  class="user_list_link"><a href="#" onclick="callDeleteFile(\''+file+'\')"/>'
                        +'<span>Delete Document</span></td>';*/
				
				content+='<td><input type="button" onclick="callDeleteFile(\''+file+'\')" '
						+'class="del_row" id="delDocRow" value="" title="Delete Document"> </td>';
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
function fnSetTotalCountDocuments(i) {
	var count = document.getElementById("hdnCount").value;
	
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
										return '<a href="javascript:setPatientFaxRecords('
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


