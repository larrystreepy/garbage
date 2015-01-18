
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>



<script>






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
			        	 return name;
			          }
				  },
				  {"mDataProp":"DOB","bSortable": false},
	                {"mDataProp":"Visit Date","bSortable": false},
	                {"mDataProp":"Physician","bSortable": false},
	                {"mDataProp":"Reason For Visit","bSortable": false}]
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
			        	 return name;
			          }
				  },
				  {"mDataProp":"DOB","bSortable": false},
	                {"mDataProp":"Visit Date","bSortable": false},
	                {"mDataProp":"Physician","bSortable": false},
	                {"mDataProp":"Reason For Visit","bSortable": false}]
	});
	
	
}

 



function loadAllPhysician(){
	document.getElementById('physicianDetails').style.display = "block";
	document.getElementById('patientDetails').style.display = "none";
	
	var orgid=document.getElementById('selectOrganization').value;

  	var practiceid=document.getElementById('selectPractice').value;
  	var searchphysician=document.getElementById('physicianname').value;
	//if(practiceid!=""){
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
	
 
	//}
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

		document.getElementById("hdnPatientId").value = id;
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
								
								combo.add(option);
							}
							loadAllPhysician();
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
			
			content += '</thead>';
			document.getElementById('patientDetails').innerHTML = content;


			var searchPatient=document.getElementById('patientname').value;

			$.ajax({
						type : "GET",
						url : contextPath + "/administrator/adminallpatientdetails.do",
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
				        	 return name
				          }
					  },
					  {"mDataProp":"DOB","bSortable": false},
		                {"mDataProp":"Visit Date","bSortable": false},
		                {"mDataProp":"Physician","bSortable": false},
		                {"mDataProp":"Reason For Visit","bSortable": false}]
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
				        	 return name
				          }
					  },
					  {"mDataProp":"DOB","bSortable": false},
		                {"mDataProp":"Visit Date","bSortable": false},
		                {"mDataProp":"Physician","bSortable": false},
		                {"mDataProp":"Reason For Visit","bSortable": false}]
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


		$.ajax({
					type : "GET",
					url : contextPath
							+ "/administrator/adminsearchpatientvisitrecords.do",
					data : "patientid="
							+ patientid,
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
</script>
<script>
var contextPath = "<%=request.getContextPath()%>";
</script>

<div class="content-body">


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
			<h3 class="panel-title">Search Clinical Informations</h3>
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
							<option value="2">Search by Physician</option>
							<option value="3">Search by Tag</option>
							
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

					<tbody>

					</tbody>

				</table>
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



	<div id="searchButton" style="margin: 3%;">

		<input type="button" value="Search" class="btn btn-inverse"
			onclick="showDetais()">

    </div>



	<div id="visitRecords" style="display: none">
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




</div>

<script>
	document.getElementById("searchButton").style.display = "none";
	onloadVisitList();
</script>


<jsp:include page="../footerbluehub.jsp"></jsp:include>