
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>



<script>
function callDocumentOpenFile(fileName) {
//	var userId = document.getElementById("hdnPatientId").value;
	if (fileName == '') {
		
	} else {
		var url = contextPath + "/DocumentDownload?fileName=" + fileName;
		window.open(url, '', 'width=700,height=500');
	}
}

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
				/* dateOfVisit = new Date(parsedJson.visit[0][3]); 
				dov = (dateOfVisit.getMonth() + 1) + "/" + dateOfVisit.getDate() + "/" + dateOfVisit.getFullYear();
				document.getElementById('visitid').value=parsedJson.visit[0][0];
				//document.getElementById("panel-typeahead").style.display = "block";
				document.getElementById('date_of_visit').value = dov;
				document.getElementById('reason_for_visit').value = parsedJson.visit[0][5];
				document.getElementById('prescription').value = parsedJson.visit[0][4];
				document.getElementById('vistAllPatientName').value = parsedJson.visit[0][1];
				document.getElementById('vistAllPhysicianName').value = parsedJson.visit[0][2];
				//document.getElementById('documentName').value = parsedJson[0][10];  
				document.getElementById("viewPhyAllVisit").style.display = "none"; */
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
					//
					//content = content + '<tr onclick="callDocumentOpenFile(\''+doc[i][0]+'\')">';
					content = content + '<tr onclick="fnSetVisitvalue1(\''+doc[i][4]+'\')">';
					content += '<td  class="user_list_link">'+ dov + '</td>';
						content += '<td  class="user_list_link"><a href="#" onclick="callDocumentOpenFile(\''+doc[i][0]+'\')"><span>'
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

function searchDateVisitRecords(){

	document.getElementById('visitRecords').style.display="block";
		
	displayUserData("0","0");
		$('#patientVisitTableId').dataTable( {
//	 		"bStateSave": true,
		  	 "processing": true,
		  	 "bJQueryUI": true ,
		   	  "bFilter": false,
		  	  "bDestroy": true,
//	 	  	"bAutoWidth": true,
//		  	"sDom": '<"top"i>rt<"bottom"flp><"clear">',
		  	  "bServerSide": true,
		  	  "sAjaxSource": contextPath + "/administrator/adminsearchdatevisitrecords.do?searchDate="+document.getElementById('datetext').value,
		  	  "bProcessing": true,
		  	   	  
			  "aoColumns": [{"mDataProp":"Patient Name","bSortable": false,
				  
						  "fnRender": function (oObj)  {   
							  var patid = oObj.aData['patId'];
							  displayUserData("0",patid);
				        	  var id = oObj.aData['id'];
				        	  var name = oObj.aData['Patient Name'];
				        	  return '<a href="javascript:fnSetVisitvalue('+id+','+patid+')" >'+name+'</a>'
				          }
					  },
					  {"mDataProp":"DOB","bSortable": false},
		                {"mDataProp":"Visit Date","bSortable": false},
		                {"mDataProp":"Physician","bSortable": false},
		                {"mDataProp":"Reason For Visit","bSortable": false}]
		});
		document.getElementById('datetext').value = "";


	}

function searchPhysicianVisitRecords(physicianid){

	 document.getElementById('visitRecords').style.display="block";
	 displayUserData("0","0");
	 
		var params = "physicianid="+physicianid;
				
	
	
	$('#patientVisitTableId').dataTable( {
// 		"bStateSave": true,
	  	 "processing": true,
	  	 "bJQueryUI": true ,
	   	  "bFilter": false,
	  	  "bDestroy": true,
// 	  	"bAutoWidth": true,
//	  	"sDom": '<"top"i>rt<"bottom"flp><"clear">',
	  	  "bServerSide": true,
	  	  "sAjaxSource": contextPath + "/administrator/adminsearchphysicianvisitrecords.do?"+params,
	  	  "bProcessing": true,
	  	   	  
		  "aoColumns": [{"mDataProp":"Patient Name","bSortable": false,
			  
					  "fnRender": function (oObj)  {   
			        	  var patid = oObj.aData['patId'];
			        		displayUserData("0",patid);
			        	  
			        	  var id = oObj.aData['id'];
			        	  var name = oObj.aData['Patient Name'];
			        	  return '<a href="javascript:fnSetVisitvalue('+id+','+patid+')" >'+name+'</a>'
			          }
				  },
				  {"mDataProp":"DOB","bSortable": false},
	                {"mDataProp":"Visit Date","bSortable": false},
	                {"mDataProp":"Physician","bSortable": false},
	                {"mDataProp":"Reason For Visit","bSortable": false}]
	});
	
}

function fnClearMsgField() {
   	document.getElementById(displayMsgId).style.display = "none";
   }
   function fnSetTimeout(id, time) {
   	if(time == undefined)
   		time = 3000;
   	displayMsgId = id;
   	document.getElementById(displayMsgId).style.display = "block";
   	setTimeout('fnClearMsgField()', time);
   }

	function submitTag(){
	var type =	document.getElementById("hdnType").value;
		var note = $('#note').val();

		if(note==""){

			 document.getElementById('emailSuccess1').innerHTML = "Enter Note";
			 
			fnSetTimeout("emailSuccess1" ,3000);

			
		}else{

			var visitid=document.getElementById("hdnVisitId").value


			$.ajax({
				type : "GET",
				/* url : contextPath + "/administrator/patientnoteentry.do", */
				url : contextPath + "/administrator/patientnoteentry.do",
				data : "note=" + note +"&visitid="+visitid+"&method=tag&type="+type,
				 /* data : "note=" + note +"&visitid="+visitid, */
				cache : false,
				async : false,
				success : function(response) {
					var str=response;
					document.getElementById('note').value="";
					
					document.getElementById('tagupdate').style.display = "block";
					 var fieldNameElement = document.getElementById("tagupdate");
					 fieldNameElement.innerHTML = str;
					 fnSetTimeout("tagupdate" ,3000);
				},
				error : function(e) {
					//alert('Error: ' + e.responseText);
				}
			});
			
		}
		
		
	}

	function updateTag(){
		
		var type =	document.getElementById("hdnType").value;

		var note = $('#note').val();

		var noteid = $('#noteid').val();

		if(note==""){

			 document.getElementById('emailSuccess1').innerHTML = "Enter Note";
			 
			fnSetTimeout("emailSuccess1" ,3000);

			
		}else{

			$.ajax({
				type : "GET",
				/* url : contextPath + "/administrator/patientnoteentry.do", */
				url : contextPath + "/administrator/updatepatientnoteentry.do",
				data : "note=" + note +"&noteid="+noteid+"&method=tag&type="+type,
				 /* data : "note=" + note +"&visitid="+visitid, */
				cache : false,
				async : false,
				success : function(response) {
					var str=response;
					document.getElementById('note').value="";
					
					document.getElementById('tagupdate').style.display = "block";
					 var fieldNameElement = document.getElementById("tagupdate");
					 fieldNameElement.innerHTML = str;
					 fnSetTimeout("tagupdate" ,3000);
				},
				error : function(e) {
					//alert('Error: ' + e.responseText);
				}
			});
			
		}
		
	}

		 
	function loadPractice() {

		var orgid = document.getElementById("selectOrganization").value;
		$.ajax({
			type : "GET",
			url : contextPath + "/administrator/loadpractice.do",
			// data : "userGroup=" + userGroup,
			cache : false,
			async : false,
			data : "orgid="+orgid,
			success : function(response) {

				//var myRoleHidden = $('#myRoleHidden').val();
				var parsedJson = $.parseJSON(response);
				document.getElementById("selectPractice").innerHTML = "";

				var combo = document.getElementById("selectPractice");

				var option = document.createElement("option");
				option.text = "Select";
				option.value = "-1";
				
				combo.add(option);
				
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

							/* content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
								content += '</table>';
								document.getElementById('physicianDetails').innerHTML = content;
							
						}else{

							var parsedJson = $.parseJSON(response);

							for (var i = 0; i < parsedJson.length; i++) {

								content = content + '<tr onclick="searchPhysicianVisitRecords(\''+parsedJson[i].userid+'\')">';
								/* content += '<td ><input type="radio" onclick="fnSetPhysicianvalue('+parsedJson[i].userid+')" id="physicianRadio" value='+parsedJson[i].userid+' name="physicianRadio"></td>'; */
								content += '<td id="agencytd'+i+'"  class="user_list_link">'
										+ parsedJson[i].firstname + '</td>';
								content += '<td id="emailtd'+i+'"  class="user_list_link">'
										+ parsedJson[i].organizationName + '</td>';
								content += '<td id="addresstd'+i+'"  class="user_list_link">'
										+ parsedJson[i].practicename + '</td>';
								content = content + '</tr>';
							}

							/* content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
							content += '</table>';
							content += '</div>';
							document.getElementById('physicianDetails').innerHTML = content;

							
						}

						

					},
					error : function(e) {
						 //alert('Error: ' + e.responseText);
					}
				});

	}
	function fnSetPatientvalue(id){    

		document.getElementById("hdnPatientId").value = id;
	}
	
	function fnSetPhysicianvalue(id){

		document.getElementById("hdnPhysicianId").value = id;
	}

	function fnSetVisitvalue1(id){
		document.getElementById("hdnVisitId").value = id;
		document.getElementById("hdnType").value = "Document";
		

		//displayUserData(id);
		
		var visitid=id
		
		$.ajax({
			type : "GET",
			url : contextPath + "/administrator/checknoteentry.do",
			data : "visitid="+visitid+"&method=tag&type=Document",
			cache : false,
			async : false,
			success : function(response) {

				if(response=="null"){
					document.getElementById("note").value="";
					document.getElementById("submitbutton").style.display = "block";
					document.getElementById("updatebutton").style.display = "none";
					
					 
				}else{
					var parsedJson = $.parseJSON(response);

					document.getElementById("updatebutton").style.display = "block";
					document.getElementById("submitbutton").style.display = "none";

					document.getElementById("note").value=parsedJson[0].note;
					document.getElementById("noteid").value=parsedJson[0].id
					
				}
				
			},
			error : function(e) {
				//alert('Error: ' + e.responseText);
			}
		});

		document.getElementById("tagEntry").style.display="block";
	}

	function fnSetVisitvalue(id,patid){
		
		document.getElementById("hdnPatientId").value = patid;
		document.getElementById("hdnVisitId").value = id;
		document.getElementById("hdnType").value = "Visit";

		displayUserData(id,patid);
		
		var visitid=id
		
		$.ajax({
			type : "GET",
			url : contextPath + "/administrator/checknoteentry.do",
			data : "visitid="+visitid+"&type=Visit&method=tag",
			cache : false,
			async : false,
			success : function(response) {

				if(response=="null"){
					document.getElementById("note").value="";
					document.getElementById("submitbutton").style.display = "block";
					document.getElementById("updatebutton").style.display = "none";
					
					 
				}else{
					var parsedJson = $.parseJSON(response);

					document.getElementById("updatebutton").style.display = "block";
					document.getElementById("submitbutton").style.display = "none";

					document.getElementById("note").value=parsedJson[0].note;
					document.getElementById("noteid").value=parsedJson[0].id
					
				}
				
			},
			error : function(e) {
				//alert('Error: ' + e.responseText);
			}
		});

		document.getElementById("tagEntry").style.display="block";
	}
	
	function loadPatientsTable() {

		document.getElementById('physicianDetails').style.display = "none";
		document.getElementById('patientDetails').style.display = "block";

		var content = '';
		content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
		content += '<thead>';
		content += '<th></th>';
		content += '<th>Patient Name</th>';
		content += '<th>DOB</th>';
		content += '<th>Address</th>';
		
		content += '</thead>';
		document.getElementById('patientDetails').innerHTML = content;


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
								document.getElementById('patientDetails').innerHTML = content;
								document.getElementById('searchButton').style.display="none";
						}else{

							var parsedJson = $.parseJSON(response);

							if (parsedJson != null) {

								for (var i = 0; i < parsedJson.length; i++) {

									
									content += '<td ><input type="radio" onclick="fnSetPatientvalue('+parsedJson[i].userid+')"  id="patientRadio" value='+parsedJson[i].userid+' name="patientRadio"></td>';
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
								document.getElementById('patientDetails').innerHTML = content;
							}
							
						}

						

					},
					error : function(e) {
						//alert('Error: ' + e.responseText);
					}
				});

	}

	//patientDetails

	function jsFunction() {

		document.getElementById("searchButton").style.display = "block";
		
		var x = document.getElementById("mySelect").value;
		if (x == 1) {

			document.getElementById("searchButton").style.display = "block";
			document.getElementById('datelabel').style.display = 'block';
			document.getElementById('physicianlabel').style.display = 'none';
			document.getElementById('organizationlabel').style.display = 'none';
			document.getElementById('practiceDiv').style.display = 'none';

			document.getElementById('taglabel').style.display = 'none';
			document.getElementById('patientlabel').style.display = 'none';
			document.getElementById('keywordlabel').style.display = 'none';
			if(document.getElementById('physicianDetails')){
				document.getElementById('physicianDetails').style.display = 'none';
				}
			
			if(document.getElementById('visitRecords')){
				document.getElementById('visitRecords').style.display = 'none';
				}
			if(document.getElementById('tagEntry')){
				document.getElementById('tagEntry').style.display = 'none';
				}

		} else if (x == 2) {

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
							document.getElementById("selectOrganization").innerHTML = "";

							var combo = document.getElementById("selectOrganization");
							var option = document.createElement("option");
							option.text = "Select";
							option.value = "-1";
							
							combo.add(option);		
							
							
							for (var i = 0; i < parsedJson.length; i++) {
								var option = document.createElement("option");
								option.text = parsedJson[i].organizationname;
								option.value = parsedJson[i].id;
								
								combo.add(option);
							}
							//loadPractice();
						},
						error : function(e) {
							//alert('Error: ' + e.responseText);
						}
					});

			document.getElementById("searchButton").style.display = "none";

			document.getElementById('physicianlabel').style.display = 'block';
			document.getElementById('organizationlabel').style.display = 'block';
			document.getElementById('practiceDiv').style.display = 'block';
			document.getElementById('datelabel').style.display = 'none';
			document.getElementById('taglabel').style.display = 'none';
			document.getElementById('patientlabel').style.display = 'none';
			document.getElementById('keywordlabel').style.display = 'none';
			if(document.getElementById('visitRecords')){
				document.getElementById('visitRecords').style.display = 'none';
				}
			if(document.getElementById('tagEntry')){
				document.getElementById('tagEntry').style.display = 'none';
				}
		} else if (x == 3) {
			if(document.getElementById('physicianDetails')){
				document.getElementById('physicianDetails').style.display = 'none';
				}
			
			if(document.getElementById('visitRecords')){
				document.getElementById('visitRecords').style.display = 'none';
				}
			if(document.getElementById('tagEntry')){
				document.getElementById('tagEntry').style.display = 'none';
				}
			document.getElementById("searchButton").style.display = "none";
			document.getElementById('taglabel').style.display = 'block';
			document.getElementById('physicianlabel').style.display = 'none';
			document.getElementById('organizationlabel').style.display = 'none';
			document.getElementById('practiceDiv').style.display = 'none';
			document.getElementById('datelabel').style.display = 'none';
			document.getElementById('patientlabel').style.display = 'none';
			document.getElementById('keywordlabel').style.display = 'none';

		} else if (x == 4) {
			document.getElementById("searchButton").style.display = "none";
			document.getElementById('patientlabel').style.display = 'block';
			document.getElementById('physicianlabel').style.display = 'none';
			document.getElementById('organizationlabel').style.display = 'none';
			document.getElementById('practiceDiv').style.display = 'none';
			document.getElementById('datelabel').style.display = 'none';
			document.getElementById('taglabel').style.display = 'none';
			document.getElementById('keywordlabel').style.display = 'none';

		} else {
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


	 

	function searchTagVisitRecords(){


		var content = '';
		content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
		content += '<thead>';
		content += '<th></th>';
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

							content += '<td ><input type="radio" onclick="fnSetVisitvalue('+parsedJson[i].visitid+')"  id="visitRadio" value='+parsedJson[i].visitid+' name="visitRadio"></td>';
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


	function searchKeywordVisitRecords(){
	
		var content = '';
		content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
		content += '<thead>';
		content += '<th></th>';
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

							/* content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
								content += '</table>';
								document.getElementById('visitRecords').innerHTML = content;
							
						}else{

							var parsedJson = $.parseJSON(response);
							for (var i = 0; i < parsedJson.length; i++) {

								content += '<td ><input type="radio" onclick="fnSetVisitvalue('+parsedJson[i].visitid+')"  id="visitRadio" value='+parsedJson[i].visitid+' name="visitRadio"></td>';
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
		document.getElementById('keyword').value = "";	
	}


	function searchPhysicianVisitRecordsss(physicianid){

		document.getElementById('visitRecords').style.display="block";

		var content = '';
		content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
		content += '<thead>';
		content += '<th>Visit Date</th>';
		content += '<th>Physician</th>';
		content += '<th>Reason For Visit</th>';
		content += '</thead>';
		document.getElementById('visitRecords').innerHTML = content;

		$.ajax({
					type : "GET",
					url : contextPath
							+ "/administrator/adminsearchphysicianvisitrecords.do",
					data : "physicianid="
							+ physicianid,
					cache : false,
					async : false,
					success : function(response) {
					if(response=="null" || response==""){
						content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';
	
						/* content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
							content += '</table>';
							document.getElementById('visitRecords').innerHTML = content;

							document.getElementById('tagEntry').style.display="none";
						
					}else{

						var parsedJson = $.parseJSON(response);

						for (var i = 0; i < parsedJson.length; i++) {

							content = content + '<tr onclick="fnSetVisitvalue(\''+parsedJson[i].visitid+'\')">';
							/* content += '<td ><input type="radio" onclick="fnSetVisitvalue('+parsedJson[i].visitid+')"  id="visitRadio" value='+parsedJson[i].visitid+' name="visitRadio"></td>'; */
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


	function searchPatientVisitRecords(){

		var content = '';
		content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
		content += '<thead>';
		content += '<th></th>';
		content += '<th>Visit Date</th>';
		content += '<th>Physician</th>';
		content += '<th>Reason For Visit</th>';
		content += '</thead>';
		document.getElementById('visitRecords').innerHTML = content;

		$
				.ajax({
					type : "GET",
					url : contextPath
							+ "/administrator/adminsearchpatientvisitrecords.do",
					data : "patientid="
							+ document.getElementById('patientRadio').value,
					cache : false,
					async : false,
					success : function(response) {

					//	alert(response)
						if(response=="null" || response==""){
							content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';
		
							/* content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
								content += '</table>';
								document.getElementById('visitRecords').innerHTML = content;

								
							
						}else{

							var parsedJson = $.parseJSON(response);

							for (var i = 0; i < parsedJson.length; i++) {

								content += '<td ><input type="radio" onclick="fnSetVisitvalue('+parsedJson[i].visitid+')"  id="visitRadio" value='+parsedJson[i].visitid+' name="visitRadio"></td>';
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

		document.getElementById('visitRecords').style.display = "block";
		if (document.getElementById('datetext').value != '') {

			searchDateVisitRecords();

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

<input type="hidden" id="hdnType">
<input type="hidden" id="hdnPatientId">
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
			<h3 class="panel-title">Tag Clinical Information</h3>
		</div>

		<div class="panel-body">
			<form role="form" class="form-horizontal form-bordered">
				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">Search
						Criteria</label>
					<div class="col-sm-5">

						<select id="mySelect" onchange="jsFunction()"
							data-input="selectboxit">
							<option value="1">Search by Date</option>
							<option value="2">Search by Physician</option>
							
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
								<select id="selectPractice" class="form-control">
									

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
									placeholder="Physician Name" onkeyup="loadPhysicianTable()" />
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
				type="hidden" id="hdnPhysicianId" name="physicianId"> <input
				type="hidden" id="hdnVisitId" name="hdnVisitId">

			<div class="physicianInformations" id="physicianDetails"
				style="display: none">
				<div id="panel-tablesorter" class="panel panel-default">
					<div class="panel-heading bg-white">
						<div class="panel-actions">

							<button data-expand="#panel-tablesorter" title="expand"
								class="btn-panel">
								<i class="fa fa-expand"></i>
							</button>
							<button data-collapse="#panel-tablesorter" title="collapse"
								class="btn-panel">
								<i class="fa fa-caret-down"></i>
							</button>
							<button data-close="#panel-tablesorter" title="close"
								class="btn-panel">
								<i class="fa fa-times"></i>
							</button>
						</div>
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

			<div class="patientInformations" style="display: none"
				id="patientDetails">
				<div id="panel-tablesorter" class="panel panel-default">
					<div class="panel-heading bg-white">
						<div class="panel-actions">

							<button data-expand="#panel-tablesorter" title="expand"
								class="btn-panel">
								<i class="fa fa-expand"></i>
							</button>
							<button data-collapse="#panel-tablesorter" title="collapse"
								class="btn-panel">
								<i class="fa fa-caret-down"></i>
							</button>
							<button data-close="#panel-tablesorter" title="close"
								class="btn-panel">
								<i class="fa fa-times"></i>
							</button>
						</div>
						<h3 class="panel-title">Patient Details</h3>
					</div>

					<div class="panel-body">
						<div class="table-responsive table-responsive-datatables">
							<table class="tablesorter table table-hover table-bordered">
								<thead>
									<tr>
										<th></th>
										<th>Patient Name</th>
										<th>DOB</th>
										<th>Address</th>

									</tr>
								</thead>



							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>



	<div id="searchButton" style="margin: 3%; display: none;">

		<input type="button" value="Search" class="btn btn-inverse"
			onclick="showDetais()">

		
	</div>



	<div class="panel-body" style="display: none" id="visitRecords">
		<table class="table table-striped table-bordered"
			id="patientVisitTableId">
			<thead>
				<tr>
					<th>Patient Name</th>
					<th>DOB</th>
					<th>Visit Date</th>
					<th>Physician</th>
					<th>Reason For Visit</th>

				</tr>
			</thead>

			<tbody></tbody>


		</table>
	</div>


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


	<div id="tagEntry" style="display: none">


		<div>
			<input type="hidden" id="hiddenPhysicianVisitId"
				name="hiddenPhysicianVisitId" value="" />
		</div>

		<div id="tagupdate"
			style="display: none; color: #da4f49; margin-left: 30%;"></div>
		<div id="emailSuccess1"
			style="display: none; color: #da4f49; margin-left: 30%;"></div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="typeahead-local">Enter
				the Tag </label>
			<div class="col-sm-5">
				<div class="input-group input-group-in">
					<span class="input-group-addon text-silver"><i
						class="glyphicon glyphicon-user"></i></span>
					<textarea rows="5" cols="50" style="resize: none;" maxlength="200"
						name="note" value="" id="note"></textarea>
				</div>
			</div>
		</div>


		<input type="hidden" name="noteid" id="noteid" value="">

		<div id="submitbutton" style="margin: 3%;">
			<input type="button" class="btn btn-inverse" id="phyVisitSubmit"
				onclick="submitTag();" value="Submit" />
		</div>
		<div id="updatebutton" style="margin: 3%;">
			<input type="button" class="btn btn-inverse" id="phyVisitSubmit"
				onclick="updateTag();" value="Update" />
		</div>

	</div>




</div>




<jsp:include page="../footerbluehub.jsp"></jsp:include>