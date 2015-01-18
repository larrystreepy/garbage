
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script>


$(document).ready(function() {
// 	onloadVisitList();
});



function callAdminDocumentOpenFile(fileName,userId) { 
// 	var userId = document.getElementById('hdnpatId').value;
	 if (fileName == ''){
	 }else{    
	  var url=contextPath+"/DocumentDownload?fileName="+fileName+"&userId="+userId; 
	  window.open(url,'','width=700,height=500'); 
	 }  
	}
	
/* function callAdminDocumentOpenFile(fileName) { 
	  // var visitid=document.getElementById('visitid').value;
	 if (fileName == ''){
	 }else{    
	  var url=contextPath+"/DocumentDownload?fileName="+fileName; 
	  window.open(url,'','width=700,height=500'); 
	 }  
	} */
/* function displayUserData(visitid){

	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/admindetailsvisit.do",
		cache : false,
		data : "visitid=" +visitid,
		async : false,
		success : function(response) {
			var parsedJson = $.parseJSON(response);

			document.getElementById('visitDocuments').style.display="block";
		 	var content = '';
	 		content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
		 	content += '<thead>';
		 	content += '<th>Documents</th>';
	 	
	 		content += '</thead>';
		 	document.getElementById('visitDocuments').innerHTML = content;

		 	
			
			var doc=parsedJson.documents;
			
			if(parsedJson.visit[0][3] == undefined || parsedJson.visit[0][3] == 'undefined'){
				dov = '';
			}else{
				dateOfVisit = new Date(parsedJson.visit[0][3]); 
				dov = (dateOfVisit.getMonth() + 1) + "/" + dateOfVisit.getDate() + "/" + dateOfVisit.getFullYear();
			}	
			document.getElementById('visitid').value=parsedJson.visit[0][0];
			//document.getElementById("panel-typeahead").style.display = "block";
			document.getElementById('date_of_visit').value = dov;
			document.getElementById('reason_for_visit').value = parsedJson.visit[0][5];
			document.getElementById('prescription').value = parsedJson.visit[0][4];
			document.getElementById('vistAllPatientName').value = parsedJson.visit[0][1];
			document.getElementById('vistAllPhysicianName').value = parsedJson.visit[0][2];
			//document.getElementById('documentName').value = parsedJson[0][10];  
			document.getElementById("viewPhyAllVisit").style.display = "block";
			if(typeof doc=="" || doc=="undefined" || doc==null){

			 	content = content + '<tr>';
						content += '<td  class="user_list_link">NO DOCUMENTS</td>';
						
					content = content + '</tr>';
					
				  
					content += '</table>';
					document.getElementById('visitDocuments').innerHTML = content;
			}else{
				for (var i = 0; i < doc.length; i++) {


					content = content + '<tr onclick="callDocumentOpenFile(\''+doc[i][0]+'\')">';
						content += '<td  class="user_list_link">'
								+ doc[i][0] + '</td>';
						
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
	
} */

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
// 		 	content += '<th>Upload Type</th>';
// 		 	content += '<th>Visit Date</th>';
	 	
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
// 						content += '<td class="user_list_link" >'+uploadType+'</td>';
// 						content += '<td class="user_list_link" >'+visitdate+'</td>';
						
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
function onloadVisitList(){
	


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

	
	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/loadalllist.do",
		//data : "orgid=" + orgid,
		cache : false,
		async : false,
		success : function(response) {

			if(response=="null" || response==""){
				content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

					content += '</table>';
					document.getElementById('visitRecords').innerHTML = content;
				
			}else{

				var parsedJson = $.parseJSON(response);
				for (var i = 0; i < parsedJson.length; i++) {

					content += '<td id="agencytd'+i+'"  class="user_list_link">'
							+ parsedJson[i].patientname + '</td>';
					content += '<td id="emailtd'+i+'"  class="user_list_link">'
							+ parsedJson[i].dob + '</td>';
					content += '<td id="addresstd'+i+'"  class="user_list_link">'
							+ parsedJson[i].visitdate + '</td>';
					content += '<td id="billingtd'+i+'"  class="user_list_link">'
							+ parsedJson[i].physicianname + '</td>';
					content += '<td id="contacttd'+i+'" class="user_list_link">'
							+ parsedJson[i].reasonforvisit
							+ '</td>';
					content = content + '</tr>';
				}
				content += '</table>';
				document.getElementById('visitRecords').innerHTML = content;
				
			}
		},
		error : function(e) {
		}
	});
}



function loadAllPhysician(){
	document.getElementById('physicianDetails').style.display = "block";
	document.getElementById('patientDetails').style.display = "none";
	
	var orgid=document.getElementById('selectOrganization').value;

  	var practiceid=document.getElementById('selectPractice').value;
  	var searchphysician=document.getElementById('physicianname').value;
	if(practiceid!=""){
	var params = "orgid="+orgid+"&practiceid="+practiceid+"&searchphysician="+searchphysician;
	
	
	
	$('#DataTables_Table_1').dataTable( {
		 
	  	 "processing": true,
	  	 "bJQueryUI": true ,
	   	  "bFilter": false,
	  	  "bDestroy": true,
	  	  "bServerSide": true,
	  	  "sAjaxSource": contextPath + "/administrator/adminallphysiciandetails.do?"+params,
	  	  "bProcessing": true,
		  "aoColumns": [{"mDataProp":"Physician Name","bSortable": false,
			  
					  "fnRender": function (oObj)  {   
			        	  
			        	  var id = oObj.aData['id'];
			        	  var name = oObj.aData['Physician Name'];
			        	  var org = oObj.aData['Organization'];
			        	  var practice = oObj.aData['Practice'];
			        	  
			        	 return '<a href="javascript:searchPhysicianVisitRecords('+id+')" >'+name+'</a>'
			          }
				  },
		                {"mDataProp":"Organization","bSortable": false,
					  
		                	"fnRender": function (oObj)  {   
					        	  
					        	  var id = oObj.aData['id'];
					        	  var name = oObj.aData['Physician Name'];
					        	  var org = oObj.aData['Organization'];
					        	  var practice = oObj.aData['Practice'];
					        	  
					        	 return '<a href="javascript:searchPhysicianVisitRecords('+id+')">'+org+'</a>'
					          }
				  },
		                {"mDataProp":"Practice","bSortable": false,
					  
					  "fnRender": function (oObj)  {   
			        	  
			        	  var id = oObj.aData['id'];
			        	  var name = oObj.aData['Physician Name'];
			        	  var org = oObj.aData['Organization'];
			        	  var practice = oObj.aData['Practice'];
			        	  
			        	 return '<a href="javascript:searchPhysicianVisitRecords('+id+')">'+practice+'</a>'
			          }
		                }         
		                 ]
		                
		                
		                
	
		  
	});
	
 
	}
	}

		 
	function loadPractice() {

		var orgid=document.getElementById('selectOrganization').value;
		
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
				for (var i = 0; i < parsedJson.length; i++) {
					var option = document.createElement("option");
					option.text = parsedJson[i].practicename;
					option.value = parsedJson[i].id;
					combo.add(option);
				}
			},
			error : function(e) {
			}
		});


		document.getElementById('practiceDiv').style.display = 'block';
		document.getElementById('physicianlabel').style.display = 'block';
	}

	function loadPatientsTable(){

		document.getElementById('physicianDetails').style.display = "none";
		document.getElementById('patientDetails').style.display = "block";
	  	var searchPatient=document.getElementById('patientname').value;
	  	var orgid=-1;
	  	var params = "searchPatient=" + searchPatient+"&orgid="+orgid;
		
		$('#patientLoadTable').dataTable( {
			 
		  	 "processing": true,
		  	 "bJQueryUI": true ,
		   	  "bFilter": false,
		  	  "bDestroy": true,
		  	  "bServerSide": true,
		  	  "sAjaxSource": contextPath + "/administrator/adminpatientdetails.do?"+params,
		  	  "bProcessing": true,
			  "aoColumns": [{"mDataProp":"Patient Name","bSortable": false,
				  
						  "fnRender": function (oObj)  {   
							  var patid = oObj.aData['patId'];
				        	  var id = oObj.aData['id'];
				        	  var name = oObj.aData['Patient Name'];
				        	  
				        	 return '<a href="#" onclick = "searchPhysicianPatientVisitRecords('+id+')"> '+name+'</a>'
				          }
					  },
					  {"mDataProp":"DOB","bSortable": false},
					  {"mDataProp":"SSN","bSortable": false},
					  {"mDataProp":"Contact Number","bSortable": false},
					  {"mDataProp":"Address","bSortable": false}
			                     
			                 ]
			                
		
			  
		});
		
	 
		}
	
	
	function searchPhysicianPatientVisitRecords(patientid) {
			displayPhysicianSearchPatientData(patientid);
	}
	
	
	function displayPhysicianSearchPatientData(patientid){ 
		var patid = 0;
			$.ajax({
				type : "GET",
				url : contextPath + "/administrator/adminPhysicianClinicalSearchPatientdetails.do",
				cache : false,
				data : "patientid=" +patientid,
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
					
							content = content + '<tr onclick="callAdminDocumentOpenFile(\''+doc[i][0]+'\',\''+doc[i][4]+'\')">';
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
	
	function loadPhysicianTabless() {
		document.getElementById('physicianDetails').style.display = "block";
		document.getElementById('patientDetails').style.display = "none";

		var content = '';
		content += '<div class="table-responsive table-responsive-datatables">';
		content += '<table style="width: 100%" class="tablesorter table table-hover table-bordered">';
		content += '<thead>';
		content += '<th>Physician Name</th>';
		content += '<th>Organization</th>';
		content += '<th>Practice</th>';
		content += '</thead>';
		document.getElementById('physicianDetails').innerHTML = content;

		var searchPhysician=document.getElementById('physicianname').value;

		var orgid=document.getElementById('selectOrganization').value;

		var practiceid=document.getElementById('selectPractice').value;
		
		if(searchPhysician==""){

			content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

				content += '</table>';
				document.getElementById('physicianDetails').innerHTML = content;

			
		}else{

			$.ajax({
				type : "GET",
				url : contextPath
						+ "/administrator/adminphysiciandetails.do",
				data:{searchphysician:searchPhysician,orgid:orgid,practiceid:practiceid},
				cache : false,
				async : false,
				success : function(response) {

					if(response=="null" || response==""){
						content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

							content += '</table>';
							document.getElementById('physicianDetails').innerHTML = content;
						
					}else{

						var parsedJson = $.parseJSON(response);

						for (var i = 0; i < parsedJson.length; i++) {

							content = content + '<tr onclick="searchPhysicianVisitRecords(\''+parsedJson[i].userid+'\')">';
							content += '<td id="agencytd'+i+'"  class="user_list_link">'
									+ parsedJson[i].firstname + '</td>';
							content += '<td id="emailtd'+i+'"  class="user_list_link">'
									+ parsedJson[i].organizationName + '</td>';
							content += '<td id="addresstd'+i+'"  class="user_list_link">'
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
		
		

	}

	function fnSetPatientvalue(id){    

		document.getElementById("hdnPatientId").value = id;
	}
	
	function fnSetPhysicianvalue(id){

		document.getElementById("hdnPhysicianId").value = id;
	}
	
	function loadPatientsTabless() {

		document.getElementById('physicianDetails').style.display = "none";
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


		var searchPatient=document.getElementById('patientname').value;

		if(searchPatient==""){

			content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

				content += '</table>';
				document.getElementById('patientDetails').innerHTML = content;
				document.getElementById('searchButton').style.display="none";
			
		}else{

			$.ajax({
				type : "GET",
				url : contextPath + "/administrator/adminpatientdetails.do",
				data : "searchPatient=" + searchPatient,
				cache : false,
				async : false,
				success : function(response) {

					if(response=="null"){
						content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

							content += '</table>';
							document.getElementById('patientDetails').innerHTML = content;
							document.getElementById('searchButton').style.display="none";
					}else{

						var parsedJson = $.parseJSON(response);

						if (parsedJson != null) {

							for (var i = 0; i < parsedJson.length; i++) {

								content = content + '<tr onclick="searchPatientVisitRecords(\''+parsedJson[i].userid+'\')">';
								content += '<td id="agencytd'+i+'"  class="user_list_link">'
										+ parsedJson[i].firstname + '</td>';
										content += '<td id="agencytd'+i+'"  class="user_list_link">'
										+ parsedJson[i].lastname + '</td>';
								content += '<td id="emailtd'+i+'"  class="user_list_link">'
										+ parsedJson[i].dateofbirth + '</td>';
										content += '<td id="agencytd'+i+'"  class="user_list_link">'
										+ parsedJson[i].ssn + '</td>';
										content += '<td id="agencytd'+i+'"  class="user_list_link">'
										+ parsedJson[i].contact_number + '</td>';
								content += '<td id="addresstd'+i+'"  class="user_list_link">'
										+ parsedJson[i].address + '</td>';
								content = content + '</tr>';
							}

							content += '</table>';
							document.getElementById('patientDetails').innerHTML = content;
						}
						
					}

					

				},
				error : function(e) {
				}
			});
			
		}

		

	}

	//patientDetails

	function jsFunction() {

		var x = document.getElementById("mySelect").value;
		if (x == 1) {

			document.getElementById('datelabel').style.display = 'block';
			 document.getElementById("searchButton").style.display = "block";
			document.getElementById('physicianlabel').style.display = 'none';
			document.getElementById('organizationlabel').style.display = 'none';
			document.getElementById('practiceDiv').style.display = 'none';

			document.getElementById('taglabel').style.display = 'none';
			document.getElementById('patientlabel').style.display = 'none';
			document.getElementById('keywordlabel').style.display = 'none';

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
			
			
			
		} else if (x == 2) {
			
			 document.getElementById("searchButton").style.display = "none";
			$.ajax({
						type : "GET",
						url : contextPath
								+ "/administrator/adminorganizations.do",
						// data : "userGroup=" + userGroup,
						cache : false,
						async : false,
						success : function(response) {

							var parsedJson = $.parseJSON(response);
							document.getElementById("selectOrganization").innerHTML = "";

							var combo = document
									.getElementById("selectOrganization");
							var defaultOption = document.createElement("option");
						    defaultOption.text = 'Select';
						    defaultOption.value = '';
						    combo.add(defaultOption);
							for (var i = 0; i < parsedJson.length; i++) {
								var option = document.createElement("option");
								option.text = parsedJson[i].organizationname;
								option.value = parsedJson[i].id;
								combo.add(option);
							}
						},
						error : function(e) {
						}
					});

			
			document.getElementById('organizationlabel').style.display = 'block';
			document.getElementById('datelabel').style.display = 'none';
			document.getElementById('taglabel').style.display = 'none';
			document.getElementById('patientlabel').style.display = 'none';
			document.getElementById('keywordlabel').style.display = 'none';
			
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
			

		} else if (x == 3) {
			 document.getElementById("searchButton").style.display = "block";
			document.getElementById('taglabel').style.display = 'block';
			document.getElementById('physicianlabel').style.display = 'none';
			document.getElementById('organizationlabel').style.display = 'none';
			document.getElementById('practiceDiv').style.display = 'none';
			document.getElementById('datelabel').style.display = 'none';
			document.getElementById('patientlabel').style.display = 'none';
			document.getElementById('keywordlabel').style.display = 'none';
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
			

		} else if (x == 4) {
			 document.getElementById("searchButton").style.display = "none";
			document.getElementById('patientlabel').style.display = 'block';
			document.getElementById('physicianlabel').style.display = 'none';
			document.getElementById('organizationlabel').style.display = 'none';
			document.getElementById('practiceDiv').style.display = 'none';
			document.getElementById('datelabel').style.display = 'none';
			document.getElementById('taglabel').style.display = 'none';
			document.getElementById('keywordlabel').style.display = 'none';
			document.getElementById('physicianDetails').style.display = "none";
			document.getElementById('patientDetails').style.display = "block";
			loadPatientsTable();
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
			

		} else if (x == 5) {
			 document.getElementById("searchButton").style.display = "none";
			document.getElementById('keywordlabel').style.display = 'block';
			document.getElementById('physicianlabel').style.display = 'none';
			document.getElementById('organizationlabel').style.display = 'none';
			document.getElementById('practiceDiv').style.display = 'none';
			document.getElementById('datelabel').style.display = 'none';
			document.getElementById('taglabel').style.display = 'none';
			document.getElementById('patientlabel').style.display = 'none';
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
			
		}
	}

	function searchDateVisitRecords(){
		document.getElementById('visitRecords').style.display="block";
		
		displayUserData(0,0);
		
		$('#patientVisitTableId').dataTable( {
//	 		"bStateSave": true,
		  	 "processing": true,
		  	 "bJQueryUI": true ,
		   	  "bFilter": false,
		  	  "bDestroy": true,
		  	  "bServerSide": true,
		  	  "sAjaxSource": contextPath + "/administrator/adminsearchdatevisitrecords.do?searchDate="+document.getElementById('datetext').value,
		  	  "bProcessing": true,
		  	   	  
			  "aoColumns": [{"mDataProp":"Patient Name","bSortable": false,
				  
						  "fnRender": function (oObj)  {   
				        	  var patid = oObj.aData['patId'];
				        	  var id = oObj.aData['id'];
				        	  var name = oObj.aData['Patient Name'];
				        	  displayUserData(id,patid);
				        	  
				        	  return '<a href="javascript:displayUserData('+id+','+patid+')" >'+name+'</a>'
				          }
					  },
					  {"mDataProp":"DOB","bSortable": false},
		                {"mDataProp":"Visit Date","bSortable": false},
		                {"mDataProp":"Physician","bSortable": false},
		                {"mDataProp":"Reason For Visit","bSortable": false}]
		});
	}

	function searchTagVisitRecords(){
		
		document.getElementById('visitRecords').style.display="block";
				
				
				$('#patientVisitTableId').dataTable( {
//			 		"bStateSave": true,
				  	 "processing": true,
				  	 "bJQueryUI": true ,
				   	  "bFilter": false,
				  	  "bDestroy": true,
				  	  "bServerSide": true,
				  	  "sAjaxSource": contextPath + "/administrator/adminsearchtagvisitrecords.do?tag="+document.getElementById('tag').value,
				  	  "bProcessing": true,
				  	   	  
					  "aoColumns": [{"mDataProp":"Patient Name","bSortable": false,
						  
								  "fnRender": function (oObj)  {   
									  var patid = oObj.aData['patId'];
						        	  var id = oObj.aData['id'];
						        	  var name = oObj.aData['Patient Name'];
						        	  return '<a href="javascript:displayUserData('+id+','+patid+')" >'+name+'</a>'
						          }
							  },
							  {"mDataProp":"DOB","bSortable": false},
				                {"mDataProp":"Visit Date","bSortable": false},
				                {"mDataProp":"Physician","bSortable": false},
				                {"mDataProp":"Reason For Visit","bSortable": false},
				                {"mDataProp":"Tag","bSortable": false}]
				});
				
				document.getElementById('tag').value = "";
			}

 


	function searchKeywordVisitRecords(){
	
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
					data : "keyword="
							+ document.getElementById('keyword').value,
					cache : false,
					async : false,
					success : function(response) {

						if(response=="null" || response==""){
							content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

								content += '</table>';
								document.getElementById('visitRecords').innerHTML = content;
							
						}else{

							var parsedJson = $.parseJSON(response);
							for (var i = 0; i < parsedJson.length; i++) {

								content += '<td id="agencytd'+i+'"  class="user_list_link">'
										+ parsedJson[i].patientname + '</td>';
								content += '<td id="emailtd'+i+'"  class="user_list_link">'
										+ parsedJson[i].dob + '</td>';
								content += '<td id="addresstd'+i+'"  class="user_list_link">'
										+ parsedJson[i].visitdate + '</td>';
								content += '<td id="billingtd'+i+'"  class="user_list_link">'
										+ parsedJson[i].physicianname + '</td>';
								content += '<td id="contacttd'+i+'" class="user_list_link">'
										+ parsedJson[i].reasonforvisit
										+ '</td>';
								content = content + '</tr>';
							}

							content += '</table>';
							document.getElementById('visitRecords').innerHTML = content;
						}

					},
					error : function(e) {
					}

				});
		document.getElementById('keyword').value = "";	
	}


	function searchPhysicianVisitRecords(physicianid){
		document.getElementById('visitDocuments').innerHTML = "";
		 document.getElementById('visitRecords').style.display="block";
			
		 
		 document.getElementById('visitRecords').style.display="block";
			var params = "physicianid="+physicianid;
					
		document.getElementById("patientVisitTableId").style.display = "none";
		
		$('#patientVisitTableId').dataTable( {
		  	 "processing": true,
		  	 "bJQueryUI": true ,
		   	  "bFilter": false,
		  	  "bDestroy": true,
		  	  "bServerSide": true,
		  	  "sAjaxSource": contextPath + "/administrator/adminsearchphysicianvisitrecords.do?"+params,
		  	  "bProcessing": true,
		  	   	  
			  "aoColumns": [{"mDataProp":"Patient Name","bSortable": false,
				  
						  "fnRender": function (oObj)  {   
							  var patid = oObj.aData['patId'];
				        	  var id = oObj.aData['id'];
				        	  var name = oObj.aData['Patient Name'];
				        	  displayUserData(0,patid);
				        	  return '<a href="javascript:displayUserData('+id+','+patid+')" >'+name+'</a>'
				        	 //return name
				          }
					  },
					  {"mDataProp":"DOB","bSortable": false},
		                {"mDataProp":"Visit Date","bSortable": false},
		                {"mDataProp":"Physician","bSortable": false},
		                {"mDataProp":"Reason For Visit","bSortable": false},
		                {"mDataProp":"Tag","bSortable": false}]
		});
		
	}


	function searchPatientVisitRecords(patientid){
		displayUserData("0",patientid);
		/* document.getElementById('hdnpatId').value = patientid;
document.getElementById('visitRecords').style.display="block";
		
		$('#patientVisitTableId').dataTable( {
		  	 "processing": true,
		  	 "bJQueryUI": true ,
		   	  "bFilter": false,
		  	  "bDestroy": true,
		  	  "bServerSide": true,
		  	  "sAjaxSource": contextPath + "/administrator/adminsearchpatientvisitrecords.do?patientid="+patientid,
		  	  "bProcessing": true,
		  	   	  
			  "aoColumns": [{"mDataProp":"Patient Name","bSortable": false,
				  
						  "fnRender": function (oObj)  {   
							  displayUserData("0",patientid);
							  var patid = oObj.aData['patId'];
				        	  var id = oObj.aData['id'];
				        	  var name = oObj.aData['Patient Name'];
				        	  return '<a href="javascript:displayUserData('+id+','+patid+')" >'+name+'</a>'
				          }
					  },
					  {"mDataProp":"DOB","bSortable": false},
		                {"mDataProp":"Visit Date","bSortable": false},
		                {"mDataProp":"Physician","bSortable": false},
		                {"mDataProp":"Reason For Visit","bSortable": false},
		                {"mDataProp":"Tag","bSortable": false}]
		}); */
	
	}

	function showDetais() {

		//document.getElementById('visitRecords').style.display = "block";
		if (document.getElementById('datetext').value != '') {
			displayPhysicianSearchData();
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
				&& document.getElementById('patientRadio').checked == true) {//if(document.getElementById('patientRadio').checked)

			searchPatientVisitRecords();
			

		}
	}

	
	function displayPhysicianSearchData(){ 
		var patid = 0;
			$.ajax({
				type : "GET",
				url : contextPath + "/administrator/adminClinicalSearchPhysicianDatedetails.do",
				cache : false,
				data : "searchDate="+document.getElementById('datetext').value,
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
					
							content = content + '<tr onclick="callAdminDocumentOpenFile(\''+doc[i][0]+'\',\''+doc[i][4]+'\')">';
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
</script>
<script>
var contextPath = "<%=request.getContextPath()%>";
</script>

<div class="content-body">
<input type="hidden" id="hdnpatId" name="hdnpatId">

	<div id="panel-typeahead"
		class="panel panel-default sortable-widget-item">
		<div class="panel-heading">
			<div class="panel-actions">

				<button data-expand="#panel-typeahead" title="expand"
					class="btn-panel">
					<i class="fa fa-expand"></i>
				</button>
				<button data-collapse="#panel-typeahead" title="collapse"
					class="btn-panel">
					<i class="fa fa-caret-down"></i>
				</button>
			</div>
			<h3 class="panel-title">Search Clinical Information</h3>
		</div>

		<div class="panel-body">
			<form role="form" class="form-horizontal form-bordered">
				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">Search
						Criteria</label>
					<div class="col-sm-5">

						<select id="mySelect" onchange="jsFunction()" class="form-control">
							<option value="0">Select</option>
							<option value="1">Search by Date</option>
							<!-- <option value="2">Search by Physician</option>
							<option value="3">Search by Tag</option> -->
							<option value="4">Search by Patient</option>
						</select>



					</div>
				</div>
				<div id="datelabel" style="display: none">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Search
							by Date</label>
						<div class="col-sm-5">


							<div class="input-group input-group-in date"
								data-input="datepicker" data-format="yyyy/mm/dd">
								<input type="text" class="form-control" id="datetext" /> <span
									class="input-group-addon text-silver"><i
									class="fa fa-calendar"></i></span>
							</div>

						</div>
					</div>
				</div>
				<div id="organizationlabel" style="display: none">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Organization</label>
						<div class="col-sm-5">
							<select class="form-control" id="selectOrganization"
								onchange="loadPractice()">
							</select>
						</div>
					</div>

					<div id="practiceDiv" style="display: none">
						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Practice</label>
							<div class="col-sm-5">
								<select id="selectPractice" class="form-control"
									onchange="loadAllPhysician()">

								</select>
							</div>


						</div>
					</div>




				</div>



				<div id="physicianlabel" style="display: none">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Physician
							Name</label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"><i
									class="glyphicon glyphicon-user"></i></span> <input type="text"
									id="physicianname" name="physicianname" class="form-control"
									placeholder="Physician Name" onkeyup="loadAllPhysician()" />
							</div>
						</div>
					</div>
				</div>
				<div id="taglabel" style="display: none">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Tag</label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"><i
									class="glyphicon glyphicon-user"></i></span> <input type="text"
									id="tag" name="tag" class="form-control" placeholder="Tag" />
							</div>
						</div>
					</div>
				</div>

				<div id="patientlabel" style="display: none">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Patient
							Name</label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"><i
									class="glyphicon glyphicon-user"></i></span> <input type="text"
									id="patientname" name="patientname" class="form-control"
									placeholder="Patient Name" onkeyup="loadPatientsTable()" />
							</div>
						</div>
					</div>
				</div>

				<div id="keywordlabel" style="display: none">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Keyword</label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"><i
									class="glyphicon glyphicon-user"></i></span> <input type="text"
									id="keyword" name="keyword" class="form-control"
									placeholder="Keyword" />
							</div>
						</div>
					</div>
				</div>

			</form>

			<input type="hidden" id="hdnPatientId" name="patientId"> <input
				type="hidden" id="hdnPhysicianId" name="physicianId">

			<div class="physicianInformations" id="physicianDetails"
				style="display: none">


				<table class="table table-striped table-bordered"
					id="DataTables_Table_1">
					<thead>
						<tr>
							<th>Physician Name</th>
							<th>Organization</th>
							<th>Practice</th>

						</tr>
					</thead>



				</table>
			</div>


			<div class="patientInformations" style="display: none"
				id="patientDetails">

				<table class="table table-striped table-bordered"
					id="patientLoadTable">
					<thead>
						<tr>
							<th>Patient Name</th>
							<th>DOB</th>
							<th>SSN</th>
							<th>Contact Number</th>
							<th>Address</th>

						</tr>
					</thead>



				</table>

			</div>
		</div>
	</div>


	<div id="searchButton" style="margin: 3%;">

		<input type="button" value="Search" class="btn btn-inverse"
			onclick="showDetais()">

	</div>



	<!-- <div id="visitRecords" style="display: none">
		<table class="table table-striped table-bordered"
			id="patientVisitTableId">
			<thead>
				<tr>

					<th>Patient Name</th>
					<th>DOB</th>
					<th>Visit Date</th>
					<th>Physician</th>
					<th>Reason For Visit</th>
					<th>Tag</th>
				</tr>
			</thead>

			<tbody></tbody>


		</table>
	</div> -->

<div id="visitDocuments" style="display: none">
		<table class="table table-striped table-bordered"
			id="patientVisitTableId">
			<thead>
				<tr>

					<th>Documents</th>


				</tr>
			</thead>

			<tbody></tbody>


		</table>
	</div>
	
	
	<input type="hidden" id="visitid" name="visitid" value="" readonly
		class="form-control" />
	<form role="form" class="form-horizontal form-bordered">
		<div id="viewPhyAllVisit" style="display: none;">
			<h3>View Visit</h3>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="typeahead-local">Patient
					Name</label>
				<div class="col-sm-5">
					<div class="input-group input-group-in">
						<span class="input-group-addon text-silver"><i
							class="glyphicon glyphicon-user"></i></span> <input type="text"
							id="vistAllPatientName" name="vistAllPatientName" readonly
							value="" class="form-control" />
					</div>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label" for="typeahead-local">Date
					of Visit</label>
				<div class="col-sm-5">
					<div class="input-group input-group-in">
						<span class="input-group-addon text-silver"><i
							class="glyphicon glyphicon-user"></i></span> <input type="text"
							id="date_of_visit" name="date_of_visit" value="" readonly
							class="form-control" />
					</div>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label" for="typeahead-local">Reason
					for Visit</label>
				<div class="col-sm-5">
					<div class="input-group input-group-in">
						<span class="input-group-addon text-silver"><i
							class="glyphicon glyphicon-user"></i></span> <input type="text"
							id="reason_for_visit" name="reason_for_visit" readonly value=""
							class="form-control" />
					</div>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label" for="typeahead-local">Physician
					Name</label>
				<div class="col-sm-5">
					<div class="input-group input-group-in">
						<span class="input-group-addon text-silver"><i
							class="glyphicon glyphicon-user"></i></span> <input type="text"
							id="vistAllPhysicianName" name="vistAllPhysicianName" readonly
							value="" class="form-control" />
					</div>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label" for="typeahead-local">Prescription
				</label>
				<div class="col-sm-5">
					<div class="input-group input-group-in">
						<span class="input-group-addon text-silver"><i
							class="glyphicon glyphicon-user"></i></span>
						<textarea rows="10" cols="50" name="prescription" value=""
							readonly id="prescription" style="resize: none;"></textarea>
					</div>
				</div>
			</div>

			<input type="hidden" name="tag" id="tag" value="">

		</div>

	</form>



</div>

<script>
            document.getElementById("searchButton").style.display = "none";

			</script>


<jsp:include page="../footerbluehub.jsp"></jsp:include>