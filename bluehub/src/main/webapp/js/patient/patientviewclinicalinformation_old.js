function callDocumentOpenFile(fileName) { 
	   var visitid=document.getElementById('visitid').value;
	 if (fileName == ''){
	 }else{    
	  var url=contextPath+"/DocumentDownload?fileName="+fileName+"&visitid="+visitid; 
	  window.open(url,'','width=700,height=500'); 
	 }  
	}


function onloadVisitList(){
	

	document.getElementById('visitRecords').style.display="block";


	$('#patientVisitTableId').dataTable( {
// 		"bStateSave": true,
	  	 "processing": true,
	  	 "bJQueryUI": true ,
	   	  "bFilter": false,
	  	  "bDestroy": true,
// 	  	"bAutoWidth": true,
//	  	"sDom": '<"top"i>rt<"bottom"flp><"clear">',
	  	  "bServerSide": true,
	  	  "sAjaxSource": contextPath + "/administrator/loadalllist.do",
	  	  "bProcessing": true,
	  	   	  
		  "aoColumns": [{"mDataProp":"Patient Name","bSortable": false,
			  
					  "fnRender": function (oObj)  {   
			        	  
			        	  var id = oObj.aData['id'];
			        	  var name = oObj.aData['Patient Name'];
			        	  return '<a href="javascript:displayUserData('+id+')" >'+name+'</a>'
			          }
				  },
				  {"mDataProp":"DOB","bSortable": false},
	                {"mDataProp":"Visit Date","bSortable": false},
	                {"mDataProp":"Physician","bSortable": false},
	                {"mDataProp":"Reason For Visit","bSortable": false},
	                {"mDataProp":"Tag","bSortable": false}]
	});

	
}



function searchPhysicianVisitRecords(physicianid){
	
	 document.getElementById('visitRecords').style.display="block";
		
	 
	 document.getElementById('visitRecords').style.display="block";
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
			        	  
			        	  var id = oObj.aData['id'];
			        	  var name = oObj.aData['Patient Name'];
			        	  return '<a href="javascript:displayUserData('+id+')" >'+name+'</a>'
			          }
				  },
				  {"mDataProp":"DOB","bSortable": false},
	                {"mDataProp":"Visit Date","bSortable": false},
	                {"mDataProp":"Physician","bSortable": false},
	                {"mDataProp":"Reason For Visit","bSortable": false},
	                {"mDataProp":"Tag","bSortable": false}]
	});
	
	
}

function displayUserData(visitid){

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

					content = content + '<tr onclick="callDocumentOpenFile(\''+doc[i][0]+'\')">';
					content += '<td  class="user_list_link">'+ dov + '</td>';
						content += '<td  class="user_list_link"><a href="#" onclick=""><span>'
								+ doc[i][0] + '</span></a></td>';
//						content += '<td class="user_list_link" >'+uploadType+'</td>';
//						content += '<td class="user_list_link" >'+visitdate+'</td>';
						
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
// 	  	"bAutoWidth": true,
//	  	"sDom": '<"top"i>rt<"bottom"flp><"clear">',
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
		                
		                
//		  "aaSorting": [[ 2, 'desc' ]]
		                
	
		  
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


		document.getElementById('practiceDiv').style.display = 'block';
		document.getElementById('physicianlabel').style.display = 'block';
	}
	
 

	function fnSetPatientvalue(id){    

		//alert("id : "+id)
		document.getElementById("hdnPatientId").value = id;
		//alert(document.getElementById("hdnPatientId").value);
		//document.getElementById("adminVisitShow").style.display = "block";
	}
	
	function fnSetPhysicianvalue(id){

		document.getElementById("hdnPhysicianId").value = id;
	}
	
	 

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

							//var myRoleHidden = $('#myRoleHidden').val();
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
								/* if (myRoleHidden == parsedJson[i]) {
								 option.setAttribute("selected", "selected");
								} */
								combo.add(option);
							}
							//loadPractice();
						},
						error : function(e) {
							 //alert('Error: ' + e.responseText);
						}
					});

			
			document.getElementById('organizationlabel').style.display = 'block';
			document.getElementById('datelabel').style.display = 'none';
			document.getElementById('taglabel').style.display = 'none';
			document.getElementById('patientlabel').style.display = 'none';
			document.getElementById('keywordlabel').style.display = 'none';

		} else if (x == 3) {
			 document.getElementById("searchButton").style.display = "block";
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
			document.getElementById('physicianDetails').style.display = "none";
			document.getElementById('patientDetails').style.display = "block";

			var content = '';
			content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
			content += '<thead>';
			content += '<th>Patient Name</th>';
			content += '<th>DOB</th>';
			content += '<th>SSN</th>';
			content += '<th>Address</th>';
			/* content += '<th>Billing Address </th>';
			content += '<th>Contact person</th>';
			content += '<th>Status</th>'; */
			content += '</thead>';
			document.getElementById('patientDetails').innerHTML = content;


			var searchPatient=document.getElementById('patientname').value;

			$.ajax({
						type : "GET",
						url : contextPath + "/administrator/adminallpatientdetails.do",
						cache : false,
						async : false,
						success : function(response) {

							//alert(response)
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

										content = content + '<tr onclick="searchPatientVisitRecords(\''+parsedJson[i].userid+'\')">';
										/* content += '<td ><input type="radio" onclick="fnSetPatientvalue('+parsedJson[i].userid+')"  id="patientRadio" value='+parsedJson[i].userid+' name="patientRadio"></td>'; */
										content += '<td id="agencytd'+i+'"  class="user_list_link">'
												+ parsedJson[i].firstname + '</td>';
										content += '<td id="emailtd'+i+'"  class="user_list_link">'
												+ parsedJson[i].dateofbirth + '</td>';
										content += '<td id="addresstd'+i+'"  class="user_list_link">'
												+ parsedJson[i].ssn + '</td>';
										content += '<td id="addresstd'+i+'"  class="user_list_link">'
												+ parsedJson[i].address + '</td>';
										/* content += '<td id="billingtd'+i+'"  class="user_list_link">'+obj[i].billingAddress+'</td>';
										content += '<td id="contacttd'+i+'" class="user_list_link">'+obj[i].contactPerson+'</td>'; */
										/* content += '<td id="statustd'+i+'" class="user_list_link"> <input type="hidden" value="'+obj.length+'" name="agencycnt" id="agencycnt"/>'+obj[i].status+'</td>'; */
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

		} else if (x == 5) {
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
				        	  
				        	  var id = oObj.aData['id'];
				        	  var name = oObj.aData['Patient Name'];
				        	  return '<a href="javascript:displayUserData('+id+')" >'+name+'</a>'
				          }
					  },
					  {"mDataProp":"DOB","bSortable": false},
		                {"mDataProp":"Visit Date","bSortable": false},
		                {"mDataProp":"Physician","bSortable": false},
		                {"mDataProp":"Reason For Visit","bSortable": false},
		                {"mDataProp":"Tag","bSortable": false}]
		});
		document.getElementById('datetext').value = "";


	}

	function searchTagVisitRecords(){


document.getElementById('visitRecords').style.display="block";
		
		
		$('#patientVisitTableId').dataTable( {
//	 		"bStateSave": true,
		  	 "processing": true,
		  	 "bJQueryUI": true ,
		   	  "bFilter": false,
		  	  "bDestroy": true,
//	 	  	"bAutoWidth": true,
//		  	"sDom": '<"top"i>rt<"bottom"flp><"clear">',
		  	  "bServerSide": true,
		  	  "sAjaxSource": contextPath + "/administrator/adminsearchtagvisitrecords.do?tag="+document.getElementById('tag').value,
		  	  "bProcessing": true,
		  	   	  
			  "aoColumns": [{"mDataProp":"Patient Name","bSortable": false,
				  
						  "fnRender": function (oObj)  {   
				        	  
				        	  var id = oObj.aData['id'];
				        	  var name = oObj.aData['Patient Name'];
				        	  return '<a href="javascript:displayUserData('+id+')" >'+name+'</a>'
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
		document.getElementById('keyword').value = "";	
	}


	


	function searchPatientVisitRecords(patientid){


		document.getElementById('visitRecords').style.display="block";

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

		//alert(document.getElementById('hdnPatientId').value)

		$.ajax({
					type : "GET",
					url : contextPath
							+ "/administrator/adminsearchpatientvisitrecords.do",
					data : "patientid="
							+ patientid,
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
				&& document.getElementById('patientRadio').checked == true) {//if(document.getElementById('patientRadio').checked)

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