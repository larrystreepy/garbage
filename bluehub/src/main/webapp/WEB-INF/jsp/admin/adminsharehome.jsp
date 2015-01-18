<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script>
        var contextPath = "<%=request.getContextPath()%>";
        
        
		function fnClearMsgField() {
			document.getElementById('errPhysicianEmail').style.display = "none";
		}

		function loadPatientPersonalDetails(patientId){
			$.ajax({
				type : "GET",
				url : contextPath + "/patient/getPatientPersonalDetails.do",
				data : "patientId=" + patientId,		
				cache : false,
				async : false,				
				success : function(response) {
					var parsedJson = $.parseJSON(response);
					var dateOfBirth = dob = '';
					
					dateOfBirth = parsedJson[0][2];								
					
					if(dateOfBirth == undefined || dateOfBirth == 'undefined'){
						dob = '';
					}else{
						dateOfBirth = new Date(parsedJson[0][2]); 
						dob = (dateOfBirth.getMonth() + 1)  + "/" + dateOfBirth.getDate() + "/" + dateOfBirth.getFullYear();
					}
					
					document.getElementById('sharePatientName').innerHTML = parsedJson[0][1];
					document.getElementById('hdnPatientId').value = parsedJson[0][3];
					document.getElementById('sharePatientDob').innerHTML = dob;					
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});		
		}
		
		/* function pendingSubmitSharePatient(){
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
							url : contextPath + "/patient/shareStatusChange.do",
							data : { 
								"physicianId" : phyId,
								"patientId" : patId,
							},
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
		} */

		function pendingSubmitSharePatient(){
			var count = document.getElementById("hdnCount").value;
			var type = "New";
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
				
			
			/* if(document.getElementById("hdnDocCheck1_"+i).checked==true){
				var val = document.getElementById("hdnDocId1_"+i).value;
				
				ur  = ur + "&checked1_"+i+"="+val;
				
				
				
			}else{
				
				ur  = ur + "&checked1_"+i+"=0";
			} */
			
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
//							data : { 
//								"physicianId" : phyId,
//								"patientId" : patId,
//							},
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
		
		content += '</thead>';
		document.getElementById('physicianDetails').innerHTML = content;
	
		var searchPhysician=document.getElementById('physicianname').value;
	
		var orgid=document.getElementById('physicianVisitOrganization').value;
	
		var practiceid=document.getElementById('selectPractice').value;
		
		
		
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
		   
		 if (fileName == ''){
		 }else{    
		  var url=contextPath+"/DocumentDownload?fileName="+fileName; 
		  window.open(url,'','width=700,height=500'); 
		 }  
		}
    
	
	function loadPatientsTable(){
		document.getElementById('patientInformations').style.display = "block";
	  	var searchPatient=document.getElementById('patientname').value;
		var orgid=-1;
	  	var params = "searchPatient=" + searchPatient+"&orgid="+orgid;
		$('#patientLoadTable').dataTable( {
			 
		  	 "processing": true,
		  	 "bJQueryUI": true ,
		   	  "bFilter": false,
		  	  "bDestroy": true,
//	 	  	"bAutoWidth": true,
//		  	"sDom": '<"top"i>rt<"bottom"flp><"clear">',
		  	  "bServerSide": true,
		  	  "sAjaxSource": contextPath + "/administrator/adminpatientdetails.do?"+params,
		  	  "bProcessing": true,
			  "aoColumns": [{"mDataProp":"Patient Name","bSortable": false,
				  
						  "fnRender": function (oObj)  {   
				        	  
				        	  var id = oObj.aData['id'];
				        	  var name = oObj.aData['Patient Name'];
// 				        	  var org = oObj.aData['Organization'];
// 				        	  var practice = oObj.aData['Practice'];
				        	  
				        	 return '<a href="javascript:fnSetPatientvalue('+id+')" >'+name+'</a>'
				          }
					  },
					  /* {"mDataProp":"Last Name","bSortable": false}, */
					  {"mDataProp":"DOB","bSortable": false},
					  {"mDataProp":"SSN","bSortable": false},
					  {"mDataProp":"Contact Number","bSortable": false},
					  {"mDataProp":"Address","bSortable": false}
			                     
			                 ]
			                
			                
//			  "aaSorting": [[ 2, 'desc' ]]
			                
		
			  
		});
		
	 
		}
	
	
    	function loadPatientsTables() {
    		document.getElementById('patientInformations').style.display = "block";

    		var content = '';
    		content += '<table style="width: 100%" id="renderUserListTable" class="  table table-hover table-bordered">';
    		content += '<thead>';
    		content += '<th>Patient Name</th>';
    		content += '<th>DOB</th>';
    		content += '<th>Address</th>';
    		
    		content += '</thead>';
    		document.getElementById('patientInformations').innerHTML = content;


    		var searchPatient=document.getElementById('patientname').value;

    		$.ajax({
    					type : "GET",
    					url : contextPath + "/administrator/adminpatientdetails.do",
    					data : "searchPatient=" + searchPatient,
    					cache : false,
    					async : false,
    					success : function(response) {
    						if(response=="null"){
    							content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

    							/* content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
    								content += '</table>';
    								document.getElementById('patientInformations').innerHTML = content;
    								document.getElementById('searchButton').style.display="none";
    						}else{
    							var parsedJson = $.parseJSON(response);

    							if (parsedJson != null) {

    								for (var i = 0; i < parsedJson.length; i++) {

    									//alert(parsedJson[i].userName)userid
    									//content = content + '<tr onclick="displayUserData(\''+parsedJson[i].userId+'\',\''+i+'\',\''+parsedJson.length+'\')" name="patientRadio" value='+parsedJson[i].userid+'>';
    									content = content + '<tr onclick="fnSetPatientvalue('+parsedJson[i].userid+')" name="patientRadio" value='+parsedJson[i].userid+'>';
    									//content += '<td ><input type="radio" onclick="fnSetPatientvalue('+parsedJson[i].userid+')"  id="patientRadio" value='+parsedJson[i].userid+' name="patientRadio"></td>';
    									content += '<td id="agencytd'+i+'"  class="user_list_link">'
    											+ parsedJson[i].firstname + '</td>';
    									content += '<td id="emailtd'+i+'"  class="user_list_link">'
    											+ parsedJson[i].dateofbirth + '</td>';
    									content += '<td id="addresstd'+i+'"  class="user_list_link">'
    											+ parsedJson[i].address + '</td>';
    									
    									content = content + '</tr>';
    								}

    								/* content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
    								content += '</table>';
    								document.getElementById('patientInformations').innerHTML = content;
    							}
    							
    						}

    						

    					},
    					error : function(e) {
    						alert('Error: ' + e.responseText);
    					}
    				});

    	}                   	
    	
    	function fnSetPatientvalue(id){
    		document.getElementById("hdnPatientId").value = id;
    		loadPatientPersonalDetails(id);
    	    loadPatientShareDocuments(id);
    	    loadPhysicianOrganization();	    		
    		//loadPendingRequests(id);    		
    	}
    	
		function fnSetPhysicianvalue(id){
   
    		document.getElementById("hdnPhysicianId").value = id;
    	}
    	
    	
        </script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/admin/adminshare.js"></script>

<div class="content" style="margin-left: 22px;">

	<div class="content-body">
	<input type="hidden" id="hdnCount">
<input type="hidden" id="hdnShareType">
	
		<!-- APP CONTENT
                    ================================================== -->

		<div id="panel-typeahead"
			class="panel panel-default sortable-widget-item">
			<div class="panel-heading">
				<div class="panel-actions">
					
				</div>
				<h3 class="panel-title">Share Clinical Information</h3>
			</div>

			<div class="panel-body">
				<form role="form" class="form-horizontal form-bordered"
					method="post" commandName="physicianVisitsForm" name="phyVisitForm"
					id="phyVisitForm">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Patient</label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"><i
									class="glyphicon glyphicon-user"></i></span> <input type="text"
									id="patientname" name="patientname" class="form-control"
									placeholder="Patient" onkeyup="loadPatientsTable()" />
							</div>
						</div>
					</div>

					<div class="patientInformations" id="patientInformations"
						style="display: none">
						<table id="patientLoadTable"
							class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>Patient Name</th>
									<!-- <th>Last Name</th> -->
									<th>DOB</th>
									<th>SSN</th>
									<th>Contact Number</th>
									<th>Address</th>

								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>


					<div class="panel-body">
						<div class="table-responsive table-responsive-datatables"
							id="requestPendingTableDiv"></div>
					</div>

					

					<input type="hidden" id="hdnPatientId" name="patientId"> <input
						type="hidden" id="hdnPhysicianId" name="physicianId"> <input
						type="hidden" id="hdnshareId" name="shareId">

					

					<div id="patientVisitDetailsWholeDiv" style="display: none;">
						<div class="panel-body" style="display: none"
							id="patientShareVisitsDiv"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Patient
								Name :</label>
							<div class="col-sm-5">
								<label style="text-align: left; margin-left: 1%; width: 50%;"
									class="col-sm-3 control-label" id="sharePatientName"
									for="typeahead-local"></label>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">DOB
								:</label>
							<div class="col-sm-5">
								<label style="text-align: left; margin-left: 1%;"
									class="col-sm-3 control-label" id="sharePatientDob"
									for="typeahead-local"></label>
							</div>
						</div>

						<h3>Document List</h3>

						<div class="panel-body">
							<div class="table-responsive table-responsive-datatables"
								id="shareDocumentDiv" style="display: none;">

								
							</div>
						</div>

						<div id="searchButton" style="margin-left: 3%;">
							<input type="button" value="Share" class="btn btn-inverse"
								onclick="showPhysicianSearch()">
						</div>
					</div>

					<div id="errPhysicianEmail" class="requrired"
						style="display: none; color: red; margin: 0% 0% 1% 30%;" onclick="fnClearMsgField()">Please
						Enter Email Address or select one Physician from list.</div>

					<div id="sharephysicianSearchDiv" style="display: none;">

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Enter
								Email Address </label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="physicianEmailId" name="physicianEmailId"
										class="form-control" placeholder="Physician Email Id" />
								</div>
							</div>
						</div>

						<div style="margin: 0.5% 0 1% 35%" id="ORSPANID">OR</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Organization</label>
							<div class="col-sm-5">
								<select name="organization" id="physicianVisitOrganization"
									class="form-control" onchange="loadPractice()"
									placeholder="Select Organization">
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Practice</label>
							<div class="col-sm-5">
								<select name="practice" id="selectPractice" class="form-control">
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Physician
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="physicianname" name="physicianname" class="form-control"
										onkeyup="loadPhysicianTable()" />
								</div>
							</div>
						</div>

						<div class="physicianInformations" id="physicianDetails"
							style="display: none">
							<div id="panel-tablesorter" class="panel panel-default">
								<div class="panel-heading bg-white">
									<div class="panel-actions"></div>
									<h3 class="panel-title">Details</h3>
								</div>

								<div class="panel-body">
									<div class="table-responsive table-responsive-datatables">
										<table class="tablesorter table table-hover table-bordered">
											<thead>
												<tr>
													<th></th>
													<th>Physician Name</th>
													<th>Organization</th>
													<th>Practice</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
							</div>
						</div>
						<div id="submitButton" style="margin-left: 3%;">
							<input type="button" value="Submit" class="btn btn-inverse"
								onclick="pendingSubmitSharePatient()">
						</div>
					</div>

				</form>
			</div>
		</div>
	</div>
</div>

</section>



<script>
        
		//loadPatientShareVisits(2,1,'234234');            
        //loadPatientShareDocuments();
        //loadPhysicianOrganization();
        
        
        </script>

<jsp:include page="../footerbluehub.jsp"></jsp:include>