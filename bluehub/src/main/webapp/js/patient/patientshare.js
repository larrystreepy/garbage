 

function loadPatientAllDocuments() {
	var content = '';
	content += '<table class="table table-hover table-bordered" id="shareDocumentTable" style="width:60%;">';
	content += '<thead><th></th><th>Date</th><th>Document Name</th></thead>';
	
	var content1 = '';
	content1 += '<table class="table table-hover table-bordered" id="shareDocumentTable1" style="width:60%;">';
	content1 += '<thead><th></th><th>Date</th><th>Document Name</th></thead>';
	
	$
			.ajax({
				type : "GET",
				url : contextPath + "/patient/getpatientalldocuments.do",
				data : "patientId=" + 0,
				cache : false,
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);
					var count = parsedJson.length;
					document.getElementById("hdnCount").value = count;

					if (parsedJson && parsedJson.length > 0) {
						for ( var i = 0; i < parsedJson.length; i++) {
							content += '<tr><td class="user_list_link"><input id="hdnDocCheck_'+i+'" type="checkbox" > '
									+ '<input type="hidden" id="hdnDocId_'+i+'" value="'+parsedJson[i][2]+'"></td>';
							content += '<td class="user_list_link">'
									+ parsedJson[i][4] + '</td>';
							content += '<td class="user_list_link"> <a href="#" onClick="callDocumentOpenFile('
									+ parsedJson[i][2]
									+ ')">'
									+ parsedJson[i][2] + '</td>';

							content = content + '</tr>';
							
							
							content1 += '<tr><td class="user_list_link"><input id="hdnDocCheck1_'+i+'" type="checkbox" > '
							+ '<input type="hidden" id="hdnDocId1_'+i+'" value="'+parsedJson[i][2]+'"></td>';
					content1 += '<td class="user_list_link">'
							+ parsedJson[i][4] + '</td>';
					content1 += '<td class="user_list_link"> <a href="#" onClick="callDocumentOpenFile('
							+ parsedJson[i][2]
							+ ')">'
							+ parsedJson[i][2] + '</td>';

					content1 = content1 + '</tr>';
							
						}
					} else {
						content += '<tr><td colspan="3" align="center" > No Data Found.</td></tr>'
							content1 += '<tr><td colspan="3" align="center" > No Data Found.</td></tr>'
					}
					content += '</table>';
					content1 += '</table>';

					document.getElementById('shareDocumentDiv').innerHTML = content1;
					document.getElementById('pendingshareDocumentDiv').innerHTML = content;

				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}


function loadPatientShareDocuments() {
	var content = '';
	content += '<table class="table table-hover table-bordered" id="shareDocumentTable" style="width:60%;">';
	content += '<thead><th></th><th>Date</th><th>Document Name</th></thead>';
	
	var content1 = '';
	content1 += '<table class="table table-hover table-bordered" id="shareDocumentTable1" style="width:60%;">';
	content1 += '<thead><th></th><th>Date</th><th>Document Name</th></thead>';
	
	$
			.ajax({
				type : "GET",
				/*url : contextPath + "/patient/getpatientsharedocuments.do",*/
				url : contextPath + "/patient/getpatientalldocuments.do",
				data : "patientId=" + 0,
				cache : false,
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);
					var count = parsedJson.length;
					document.getElementById("hdnCount").value = count;

					if (parsedJson && parsedJson.length > 0) {
						for ( var i = 0; i < parsedJson.length; i++) {
							content += '<tr><td class="user_list_link"><input id="hdnDocCheck_'+i+'" type="checkbox" > '
									+ '<input type="hidden" id="hdnDocId_'+i+'" value="'+parsedJson[i][2]+'"></td>';
							content += '<td class="user_list_link">'
									+ parsedJson[i][4] + '</td>';
							content += '<td class="user_list_link"> <a href="#" onClick="callDocumentOpenFile('
									+ parsedJson[i][2]
									+ ')">'
									+ parsedJson[i][2] + '</td>';

							content = content + '</tr>';
							
							
							content1 += '<tr><td class="user_list_link"><input id="hdnDocCheck1_'+i+'" type="checkbox" > '
							+ '<input type="hidden" id="hdnDocId1_'+i+'" value="'+parsedJson[i][2]+'"></td>';
					content1 += '<td class="user_list_link">'
							+ parsedJson[i][4] + '</td>';
					content1 += '<td class="user_list_link"> <a href="#" onClick="callDocumentOpenFile('
							+ parsedJson[i][2]
							+ ')">'
							+ parsedJson[i][2] + '</td>';

					content1 = content1 + '</tr>';
							
						}
					} else {
						content += '<tr><td colspan="3" align="center" > No Data Found.</td></tr>'
							content1 += '<tr><td colspan="3" align="center" > No Data Found.</td></tr>'
					}
					content += '</table>';
					content1 += '</table>';

					document.getElementById('shareDocumentDiv').innerHTML = content1;
					document.getElementById('pendingshareDocumentDiv').innerHTML = content;

				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}

function loadPatientShareVisits(type, patId, visitDate) {
	var content = '';
	content += '<table class="table table-hover table-bordered" id="organizationTable">';
	content += '<thead><th></th><th>Date of Visit</th><th>Reason for Visit</th><th>Physician Consulted</th><th>Prescription of Physician</th></thead>';
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
						document.getElementById('sharePatientName').innerHTML = parsedJson[0][1];

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
						document.getElementById('sharePatientDob').innerHTML = dob;

					} else {
						content += '<tr><td colspan="5" align="center" > No Data Found.</td></tr>';
					}
					content += '</table>';
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}

function loadPatientShareSuccess() {
	var answer = confirm("Your clinical information shared successfully.");
	if (answer) {
		window.location.href = contextPath + "/patient/patientsharetab.do";
	}
}

function loadPendingRequests() {
	var content = '';
	content += '<table class="table table-hover table-bordered" id="organizationTable">';
	content += '<thead><th>Patient Name</th><th>Physician Name</th><th>Request Date</th><th>Status</th></thead>';
	document.getElementById('requestPendingTableDiv').innerHTML = content;
	$
			.ajax({
				type : "GET",
				url : contextPath + "/patient/getPatientRequestPending.do",
				data : "patientId=" + 0,
				cache : false,
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);
					var dateOfVisit = dov = '';
					if (parsedJson && parsedJson.length > 0) {
						var requestDate = ''; 
						var rdate = '';
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
//							for ( var i = 0; i < parsedJson.length; i++) {
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
										+ '"  class="user_list_link"><a href="#" onclick="javscript:patientRequestForm('
										+ parsedJson[i][0] + ','
										+ parsedJson[i][5] + ');">'
										+ parsedJson[i][4] + '</a></td>';

								content = content + '</tr>';
//							}
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

function patientRequestForm(shareId, physicianId) {
	document.getElementById("hdnShareType").value = "Share";
	
	if (shareId != null && shareId != undefined) {
		document.getElementById("hdnshareId").value = shareId;
	}
	if (physicianId != null && physicianId != undefined) {
		document.getElementById("hdnPhysicianId").value = physicianId;
	}

	document.getElementById("patientPendingShareDiv").style.display = "block";
}

function loadPatientPersonalDetails() {
	$
			.ajax({
				type : "GET",
				url : contextPath + "/patient/getPatientPersonalDetails.do",
				data : "patientId=" + 0,
				cache : false,
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);
					var dateOfBirth = dob = '';

					dateOfBirth = parsedJson[0][2];

					if (dateOfBirth == undefined || dateOfBirth == 'undefined') {
						dob = '';
					} else {
						dateOfBirth = new Date(parsedJson[0][2]);
						dob = (dateOfBirth.getMonth() + 1) + "/"
								+ dateOfBirth.getDate() + "/"
								+ dateOfBirth.getFullYear();
					}

					document.getElementById('sharePatientName').innerHTML = parsedJson[0][1];
					document.getElementById('hdnPatientId').value = parsedJson[0][3];
					document.getElementById('sharePatientDob').innerHTML = dob;

					document.getElementById('pendingSharePatientName').innerHTML = parsedJson[0][1];
					document.getElementById('pendingsharePatientDob').innerHTML = dob;
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
}


function fnClearMsgField() {
	document.getElementById('errPhysicianEmail').style.display = "none";
}

function pendingSubmitSharePatient(){
	var count = document.getElementById("hdnCount").value;
	var type = document.getElementById("hdnShareType").value;
	var shareId =document.getElementById("hdnshareId").value;
	if(shareId==""){
		shareId = 0;
	}
	var ur;
	ur = "&count="+count;
	for(var i = 0;i<count;i++){
		if(document.getElementById("hdnDocCheck_"+i).checked==true){
			var val = document.getElementById("hdnDocId_"+i).value;
			
			ur  = ur + "&checked_"+i+"="+val;
			
			
				
		}else{
			
			ur  = ur + "&checked_"+i+"=0";
		}
		
	
	if(document.getElementById("hdnDocCheck1_"+i).checked==true){
		var val = document.getElementById("hdnDocId1_"+i).value;
		
		ur  = ur + "&checked1_"+i+"="+val;
		
		
		
	}else{
		
		ur  = ur + "&checked1_"+i+"=0";
	}
	
	}
	var phyEmailId = '';
	var emailSubject = '';
	var shareId = '';
	phyEmailId = document.getElementById('physicianEmailId').value;
	
	var phyId = document.getElementById("hdnPhysicianId").value;
	var patId = document.getElementById("hdnPatientId").value;
	if(document.getElementById("hdnshareId").value != ""){
		var shareId = document.getElementById("hdnshareId").value;
	}
	
	if(phyId == '' && phyEmailId == ''){
		document.getElementById('errPhysicianEmail').style.display = "block";
		//setTimeout('fnClearMsgField()', 3000);
	}else{
		var answer = confirm("Are you sure you want to share this clinical information.");
		if(answer){			
			//var filenameArr = [];
			var filenameArr = "";
			
			var patName = document.getElementById('sharePatientName').innerHTML;
			emailSubject = "Email notification for information shared by " + patName;
						 
			var bodyContent = "sample mail";
			if(phyId && phyId != ''){						
			    $.ajax({
					type : "POST",
					url : contextPath + "/patient/shareStatusChange.do?shareId="+shareId+"&type="+type+"&physicianId="+phyId+"&patientId="+patId+ur,
//					data : { 
//						"physicianId" : phyId,
//						"patientId" : patId,
//					},
					cache : false,
					async : false,
					success : function(response) {
						if(response != ""){
							shareId = response;
						}
						
					    $.ajax({
							type : "POST",
							url : contextPath + "/patient/sendSharePatientMail.do",
							data : { 
								"physicianId" : phyId,
								"patientId" : patId,
								"shareId" : shareId,
								"phyMailId" : phyEmailId,
								"subject"   : emailSubject,
								"bodyContent" : bodyContent				
							},
							cache : false,
							async : false,
							success : function(response) {
								loadPatientShareSuccess();
							},								
							error : function(e) {
								alert('Error: ' + e.responseText);
							}
						});						
					},
					error : function(e) {
						alert('Error: ' + e.responseText);
					}
			    });
			}else{
				 var answer = confirm("Your clinical information shared successfully.");
				 if(answer){				
		 			window.location.href=contextPath+ "/patient/patientsharetab.do";
				 }
			}

		}
	}
}

function submitSharePatient(){
	var phyEmailId = '';
	var emailSubject = '';
	phyEmailId = document.getElementById('physicianEmailId').value;
	
	var phyId = document.getElementById("hdnPhysicianId").value;
	var patId = document.getElementById("hdnPatientId").value;
	
	if(phyId == '' && phyEmailId == ''){		
		document.getElementById('errPhysicianEmail').style.display = "block";
		setTimeout('fnClearMsgField()', 3000);
	}else{
		var answer = confirm("Are you sure you want to share this clinical information.");
		if(answer){			
			//var filenameArr = [];
			var filenameArr = "";
			var visitIdArr = [];
			
			var patName = document.getElementById('sharePatientName').innerHTML;
			emailSubject = "Email notification for information shared by " + patName;
						 
			var bodyContent = "sample mail";
/* 		 	var radioBtns = document.getElementsByName('patVisitId');
			var vals = "";
			var count = 0;
		 	for (var i=0, n=radioBtns.length;i<n;i++) {	   
			  if (radioBtns[i].checked){
				  visitId = radioBtns[i].value;
				  count = count + 1;
			  }
			} 	  */
		 	
		/*  	var checkboxes = document.getElementsByName('chkDocumentId');
			var vals = "";
			var count = 0;
		 	for (var i=0, n=checkboxes.length;i<n;i++) {	   
			  if (checkboxes[i].checked){
				  //filenameArr += checkboxes[i].value + ",";
				  filenameArr[count] = checkboxes[i].value;
				  count = count + 1;
			  }
			} 
		 */

/* 		    $.ajax({
				type : "GET",
				url : contextPath
						+ "/patient/getVisitDetailsforShare.do",				
				data :  "visitId=" + visitId,
				cache : false,
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);
					var dateOfVisit = dov = '';
					if (parsedJson && parsedJson.length > 0) {
						
						emailSubject = "Email notification for information shared by " + parsedJson[0][1];
						
						bodyContent += "<br>" + "Patient Name : " + parsedJson[0][1] + "<br> ";
						
						var dateofbirth = dob = '';
						dateofbirth = parsedJson[0][6];
						if(dateofbirth == undefined || dateofbirth == 'undefined'){
							dob = '';
						}else{
							dateofbirth = new Date(parsedJson[0][6]); 
							dob = (dateofbirth.getMonth() + 1)  + "/" + dateofbirth.getDate() + "/" + dateofbirth.getFullYear();							
						}
						bodyContent += "<br>" + "DOB : " + dob + "<br> ";
						
						dateOfVisit = parsedJson[0][3];								
						
						if(dateOfVisit == undefined || dateOfVisit == 'undefined'){
							dov = '';
						}else{
							dateOfVisit = new Date(parsedJson[0][3]); 
							dov = (dateOfVisit.getMonth() + 1)  + "/" + dateOfVisit.getDate() + "/" + dateOfVisit.getFullYear();
						}
						
						bodyContent += "<br>" + "Date of Visit : " + dov + "<br> ";
						
						bodyContent += "<br>" + "Physician Consulted : " + parsedJson[0][2] + "<br> ";
												
						bodyContent += "<br>" + "Reason of Visit : " + parsedJson[0][5] + "<br> ";
						
						bodyContent += "<br>" + "Prescription : " + parsedJson[0][4] + "<br> ";
					}
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});  */
		    
		 	//var data_to_send = $.serialize(filenameArr);	
				
/* 			var checkboxes = document.getElementsByName('chkDocumentId');
			var vals = "";
			var count = 0;
			for (var i=0, n=checkboxes.length;i<n;i++) {	   
			  if (checkboxes[i].checked){
				  filenameArr += checkboxes[i].value + ",";
				  //filenameArr[count] = checkboxes[i].value;
				  count = count + 1;
			  }
			}  
		 
			filenameArr = filenameArr.substring(0,(filenameArr.length)-1); */
			    $.ajax({
					type : "POST",
					url : contextPath + "/patient/sendSharePatientMail.do",
					data : { 
						"physicianId" : phyId,
						"patientId" : patId,
						"phyMailId" : phyEmailId,
						"subject"   : emailSubject,
						"bodyContent" : bodyContent				
					},
					cache : false,
					async : false,
					success : function(response) {
						if(phyId && phyId != ''){						
						    $.ajax({
								type : "POST",
								url : contextPath + "/patient/shareStatusChange.do",
								data : { 
									"physicianId" : phyId,
									"patientId" : patId,
								},
								cache : false,
								async : false,
								success : function(response) {						
									loadPatientShareSuccess();
								},
								error : function(e) {
									alert('Error: ' + e.responseText);
								}
						    });
						}else{
							 var answer = confirm("Your clinical information shared successfully.");
							 if(answer){				
					 			window.location.href=contextPath+ "/patient/patientsharetab.do";
							 }
						}
					},								
					error : function(e) {
						alert('Error: ' + e.responseText);
					}
				});
		}
	}
}

function fnSetPhysicianvalue(id){    
	document.getElementById("hdnPhysicianId").value = id;
}

	function loadPhysicianOrganization(){
	    $.ajax({
			type : "GET",
			url : contextPath
					+ "/administrator/adminorganizations.do",
			// data : "userGroup=" + userGroup,
			cache : false,
			async : false,
			success : function(response) {
	
				//var myRoleHidden = $('#myRoleHidden').val();
				var parsedJson = $.parseJSON(response);
				document.getElementById("physicianVisitOrganization").innerHTML = "";
					
				var combo = document.getElementById("physicianVisitOrganization");

				var defaultOption = document.createElement("option");
			    defaultOption.text = 'Select';
			    defaultOption.value = '';
			    combo.add(defaultOption);
				
				for (var i = 0; i < parsedJson.length; i++) {
					var option = document.createElement("option");
					option.text = parsedJson[i].organizationname;
					option.value = parsedJson[i].id;
					/* if (myRoleHidden == parsedJson[i]) {
					 option.setAttribute("selected", "selected");
					} */
					combo.add(option);
				}
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});    
    }
    
	function loadPractice() {

		var orgid=document.getElementById('physicianVisitOrganization').value;
		
		$.ajax({
			type : "GET",
			url : contextPath + "/administrator/loadpractice.do",
			data : "orgid=" + orgid,
			cache : false,
			async : false,
			success : function(response) {

				//var myRoleHidden = $('#myRoleHidden').val();
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
					/* if (myRoleHidden == parsedJson[i]) {
					 option.setAttribute("selected", "selected");
					} */
					combo.add(option);
				}
			},
			error : function(e) {
				 //alert('Error: ' + e.responseText);
			}
		});
	}
    
	function loadPhysicianTable() {
		document.getElementById('physicianDetails').style.display = "block";
	
		var content = '';
		content += '<div class="table-responsive table-responsive-datatables">';
		content += '<table style="width: 100%" class="tablesorter table table-hover table-bordered">';
		content += '<thead>';
		content += '<th></th>';
		content += '<th>Physician Name</th>';
		content += '<th>Organization</th>';
		content += '<th>Practice</th>';
		/* content += '<th>Billing Address </th>';
		content += '<th>Contact person</th>';
		content += '<th>Status</th>'; */
		content += '</thead>';
		document.getElementById('physicianDetails').innerHTML = content;
	
		var searchPhysician=document.getElementById('physicianname').value;
	
		var orgid=document.getElementById('physicianVisitOrganization').value;
	
		var practiceid=document.getElementById('selectPractice').value;
		
		//alert(orgid);
		
		$.ajax({
					type : "GET",
					url : contextPath
							+ "/administrator/adminphysiciandetails.do",
					data:{searchphysician:searchPhysician,orgid:orgid,practiceid:practiceid},
					//data : "searchphysician=" + searchPhysician +"&orgid=" +orgid+"&practiceid="+practiceid,
					cache : false,
					async : false,
					//dataType: "json",
					success : function(response) {
	
						if(response=="null" || response==""){
							content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';	
	/*     							content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
								content += '</table>';
								document.getElementById('physicianDetails').innerHTML = content;
							
						}else{
							var parsedJson = $.parseJSON(response);
							//alert("parsedJson.length : "+parsedJson.length)
							for (var i = 0; i < parsedJson.length; i++) {
	
								//alert(parsedJson[i].userName)
								//content = content + '<tr onclick="displayUserData(\''+parsedJson[i].userId+'\',\''+i+'\',\''+obj.length+'\')">';
								content += '<td ><input type="radio" onclick="fnSetPhysicianvalue('+parsedJson[i].userid+')" id="physicianRadio" value='+parsedJson[i].userid+' name="physicianRadio"></td>';
								content += '<td id="agencytd'+i+'"  class="user_list_link">'
										+ parsedJson[i].firstname + '</td>';
								content += '<td id="emailtd'+i+'"  class="user_list_link">'
										+ parsedJson[i].organizationName + '</td>';
								content += '<td id="addresstd'+i+'"  class="user_list_link">'
										+ parsedJson[i].practicename + '</td>';
								content = content + '</tr>';
							}
	
	/*     							content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
							content += '</table>';
							content += '</div>';
							document.getElementById('physicianDetails').innerHTML = content;
						}
					},
					error : function(e) {
						alert('Error: ' + e.responseText);
					}
				});
	}               
	
	function fnSetPatientvalue(id){    
		//alert("id : "+id)
		document.getElementById("hdnPatientId").value = id;
		//alert(document.getElementById("hdnPatientId").value);
		//document.getElementById("adminVisitShow").style.display = "block";
	}
	
	//patientDetails

	function jsFunction() {

		document.getElementById("searchButton").style.display = "block";
		
		var x = document.getElementById("mySelect").value;
		if (x == 1) {

			document.getElementById('datelabel').style.display = 'block';

			document.getElementById('taglabel').style.display = 'none';

		}  else if (x == 3) {
			document.getElementById('taglabel').style.display = 'block';
			document.getElementById('datelabel').style.display = 'none';
		} 
	}

	function showPhysicianSearch(){
		document.getElementById('sharephysicianSearchDiv').style.display = 'block';
		document.getElementById("hdnShareType").value = "New";
	}
	
	function searchDateVisitRecords(){
		var searchDate = document.getElementById("datetext").value;
		
		var content = '';
		content += '<table class="table table-hover table-bordered" id="organizationTable">';
		content += '<thead><th></th><th>Date of Visit</th><th>Reason for Visit</th><th>Physician Consulted</th><th>Prescription of Physician</th></thead>';
		document.getElementById('patientShareVisitsDiv').innerHTML = content;

				$.ajax({
					type : "GET",
					url : contextPath + "/patient/getPatientShareVisitDetails.do",
					data : {
						"type" : 3,
						"patientId" : 256,
						"visitDate" : searchDate
					},
					cache : false,
					async : false,				
					success : function(response) {
						var parsedJson = $.parseJSON(response);
						var dateOfVisit = dov = '';
						if (parsedJson && parsedJson.length > 0) {
							document.getElementById('sharePatientName').innerHTML = parsedJson[0][1];
							
							var dateofbirth = dob = '';
							dateofbirth = parsedJson[0][6];
							if(dateofbirth == undefined || dateofbirth == 'undefined'){
								dob = '';
							}else{
								dateofbirth = new Date(parsedJson[0][6]); 
								dob = (dateofbirth.getMonth() + 1)  + "/" + dateofbirth.getDate() + "/" + dateofbirth.getFullYear();							
							}
							document.getElementById('sharePatientDob').innerHTML = dob;
							
							for ( var i = 0; i < parsedJson.length; i++) {
								dateOfVisit = parsedJson[i][3];								
								
								if(dateOfVisit == undefined || dateOfVisit == 'undefined'){
									dov = '';
								}else{
									dateOfVisit = new Date(parsedJson[i][3]); 
									dov = (dateOfVisit.getMonth() + 1)  + "/" + dateOfVisit.getDate() + "/" + dateOfVisit.getFullYear();
								}
								content += '<td> <input type="checkbox" value="" id=' + parsedJson[i][0] +' class="chkPatShare"' 
										+ '/></td>';							
								content += '<td id="visitdate' + i
										+ '"  class="user_list_link">'
										+ dov + '</td>';
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
						/*content += '<tfoot><tr><th colspan="5" class="ts-pager form-horizontal"><button type="button" class="btn btn-default btn-sm first">	<i class="icon-step-backward fa fa-angle-double-left"></i></button>	<button type="button" class="btn btn-default btn-sm prev">';
						content += '<i class="icon-arrow-left fa fa-angle-left"></i>	</button> <span class="pagedisplay"></span> <!-- this can be any element, including an input --><button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>';
						content += '<button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i>	</button> <select class="pagesize input-sm" title="Select page size"><option value="5" selected>5</option><option value="10">10</option>';
						content += '<option value="25">25</option><option value="50">50</option></select> <select class="pagenum input-sm" title="Select page number"></select></th>	</tr></tfoot>';*/
						content += '</table>';

						document.getElementById('patientShareVisitsDiv').style.display = "block";
						document.getElementById('patientShareVisitsDiv').innerHTML = content;
						
					},
					error : function(e) {
						alert('Error: ' + e.responseText);
					}
				});			
	}

	function searchTagVisitRecords(){
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

		$.ajax({
					type : "GET",
					url : contextPath
							+ "/administrator/adminsearchtagvisitrecords.do",
					data : "tag=" + document.getElementById('tag').value,
					cache : false,
					async : false,
					success : function(response) {


					if(response=="null" || response==""){
						content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

						/* content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
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

						/* content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
						content += '</table>';
						document.getElementById('visitRecords').innerHTML = content;
					}

					},
					error : function(e) {
						 //alert('Error: ' + e.responseText);
					}

				});
		document.getElementById('tag').value = "";
	}

	function searchPatientVisitRecords(){
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

		$.ajax({
					type : "GET",
					url : contextPath
							+ "/administrator/adminsearchpatientvisitrecords.do",
					data : "patientid="
							+ document.getElementById('hdnPatientId').value,
					cache : false,
					async : false,
					success : function(response) {

						 //alert(response)
						if(response=="null" || response==""){
							content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';
		
							/* content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
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

							/* content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
							content += '</table>';
							document.getElementById('visitRecords').innerHTML = content;
						}
					},
					error : function(e) {
						 //alert('Error: ' + e.responseText);
					}
				});	
	}

	function showDetais() {
		//document.getElementById('visitRecords').style.display = "block";
		if (document.getElementById('datetext').value != '') {			
			searchDateVisitRecords();			
		} else if (document.getElementById('tag').value != '') {
			searchTagVisitRecords();
		} 
	}
	
	function callDocumentOpenFile(fileName) { 
		   // var filename=$('#fileName').val();  
		   // var groupname=$('#groupName').val();
		   //alert("fileName : "+fileName)
		 if (fileName == ''){
		 }else{    
		  var url=contextPath+"/DocumentDownload?fileName="+fileName; 
		  window.open(url,'','width=700,height=500'); 
		 }  
		}
		
	function showTabA(){
		document.getElementById("tab_a").style.display = "block";
		document.getElementById("tab_b").style.display = "none";
		document.getElementById("patientPendingShareDiv").style.display = "none";
	}
	
	function showTabB(){
		document.getElementById("tab_b").style.display = "block";
		document.getElementById("tab_a").style.display = "none";
	}	