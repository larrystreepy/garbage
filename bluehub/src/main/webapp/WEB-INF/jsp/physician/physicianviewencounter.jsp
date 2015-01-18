
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script>
function searchDateVisitRecords(){

	document.getElementById('visitRecords').style.display="block";
		
		
		$('#patientVisitTableId').dataTable( {
		  	 "processing": true,
		  	 "bJQueryUI": true ,
		   	  "bFilter": false,
		  	  "bDestroy": true,
		  	  "bServerSide": true,
		  	  "sAjaxSource": contextPath + "/administrator/adminsearchdatevisitrecords.do?searchDate="+document.getElementById('datetext').value,
		  	  "bProcessing": true,
		  	   	  
			  "aoColumns": [{"mDataProp":"Patient Name","bSortable": false,
				  
						  "fnRender": function (oObj)  {   
				        	  
				        	  var id = oObj.aData['id'];
				        	  var name = oObj.aData['Patient Name'];
				        	  return '<a href="javascript:fnSetVisitvalue('+id+')" >'+name+'</a>'
				          }
					  },
					  {"mDataProp":"DOB","bSortable": false},
		                {"mDataProp":"Visit Date","bSortable": false},
		                {"mDataProp":"Physician","bSortable": false},
		                {"mDataProp":"Reason For Visit","bSortable": false}]
		});
		document.getElementById('datetext').value = "";


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



   function updateEncounter(){

	  	var encountername=document.getElementById("editencountername").value;
		var encounterid=document.getElementById("encounterid").value;

		$.ajax({
			type : "GET",
			url : contextPath + "/administrator/updateencounters.do",
			data : "encountername=" + encountername +"&encounterid="+encounterid,
			cache : false,
			async : false,
			success : function(response) {
				document.getElementById("deleteencounter").innerHTML="Encounter Successfully Updated";

				setTimeout('fnloadOrg()', 3000);
			},
			error : function(e) {
			}
		});
	   
	}
		 
	function loadPractice() {

		var orgid = document.getElementById("selectOrganization").value;
		$.ajax({
			type : "GET",
			url : contextPath + "/administrator/loadpractice.do",
			cache : false,
			async : false,
			data : "orgid="+orgid,
			success : function(response) {
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
	function fnSetPatientvalue(id){    

		document.getElementById("hdnPatientId").value = id;
	}
	
	function fnSetPhysicianvalue(id){

		document.getElementById("hdnPhysicianId").value = id;
	}


	function fnloadOrg(){
		window.location.href=contextPath+ "/administrator/adminviewencounter.do";
	}

	function editEncounterRow(id){

		$.ajax({
			type : "GET",
			url : contextPath + "/administrator/editadminencounter.do",
			data : "encounterid=" + id,
			success : function(response) {


				var parsedJson = $.parseJSON(response);

				if (parsedJson != null) {

					document.getElementById("editencounter").style.display="block";

					document.getElementById("editencountername").value = parsedJson[0].encounter;
					document.getElementById("encounterid").value=parsedJson[0].id;
					
				}
			},
			error : function(e) {
			}
		});
		
	}

	function deleteEncounterRow(id) {  
		 var answer = confirm("Are you sure want to delete the encounter ?")
		    if (answer){
		    	deleteEncounter(id);
		    }
		 return false;
		}

	function deleteEncounter(id) {
		
		$.ajax({
			type : "GET",
			url : contextPath + "/administrator/deleteadminencounter.do",
			data : "encounterid=" + id,
			success : function(response) {
				
				document.getElementById("deleteencounter").innerHTML="Encounter Successfully Deleted";

				setTimeout('fnloadOrg()', 3000);
				
			},
			error : function(e) {
			}
		});

	}

	function fnSetVisitvalue(id){
		document.getElementById("hdnVisitId").value = id;

		document.getElementById("encounterDetails").style.display="block";

		

		var content = '';
		content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
		content += '<thead>';
		content += '<th>Encounters</th>';
		
		content += '</thead>';
		document.getElementById('encounterDetails').innerHTML = content;


		$.ajax({
					type : "GET",
					url : contextPath + "/administrator/adminencounterdetails.do",
					data : "visitid=" + id,
					cache : false,
					async : false,
					success : function(response) {

						if(response=="null"){
							content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

								content += '</table>';
								document.getElementById('encounterDetails').innerHTML = content;
								
						}else{

							var parsedJson = $.parseJSON(response);

							if (parsedJson != null) {

								for (var i = 0; i < parsedJson.length; i++) {

									content += '<td id="agencytd'+i+'"  class="user_list_link">'
											+ parsedJson[i].encounter + '</td>';
									content += '<td style="border: none; margin-left: 2px;"> <input type="button" value="" id="delDocRow" class="del_row" onclick="deleteEncounterRow('
										+ parsedJson[i].id + ')"/></td>';
									content += '<td style="border: none; margin-left: 2px;"> <input type="button" value="" id="editDocRow" class="editIcon" onclick="editEncounterRow('
											+ parsedJson[i].id + ');"/></td>';
									content = content + '</tr>';
								}

								content += '</table>';
								document.getElementById('encounterDetails').innerHTML = content;
							}
							
						}

						

					},
					error : function(e) {
					}
				});
	

		
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

								content += '</table>';
								document.getElementById('patientDetails').innerHTML = content;
							}
							
						}

						

					},
					error : function(e) {
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

		} else if (x == 2) {

			$.ajax({
						type : "GET",
						url : contextPath
								+ "/administrator/adminorganizations.do",
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

			document.getElementById("searchButton").style.display = "none";

			document.getElementById('physicianlabel').style.display = 'block';
			document.getElementById('organizationlabel').style.display = 'block';
			document.getElementById('practiceDiv').style.display = 'block';
			document.getElementById('datelabel').style.display = 'none';
			document.getElementById('taglabel').style.display = 'none';
			document.getElementById('patientlabel').style.display = 'none';
			document.getElementById('keywordlabel').style.display = 'none';

		} else if (x == 3) {
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

	function searchPhysicianVisitRecords(physicianid){

		 document.getElementById('visitRecords').style.display="block";
			
		 
			var params = "physicianid="+physicianid;
					
		
		
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
				        	  
				        	  var id = oObj.aData['id'];
				        	  var name = oObj.aData['Patient Name'];
				        	  return '<a href="javascript:fnSetVisitvalue('+id+')" >'+name+'</a>'
				          }
					  },
					  {"mDataProp":"DOB","bSortable": false},
		                {"mDataProp":"Visit Date","bSortable": false},
		                {"mDataProp":"Physician","bSortable": false},
		                {"mDataProp":"Reason For Visit","bSortable": false}]
		});
		
	}


	function searchDateVisitRecordsdd(){


		var content = '';
		content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
		content += '<thead>';
		content += '<th>Visit Date</th>';
		content += '<th>Physician</th>';
		content += '<th>Reason For Visit</th>';
		content += '</thead>';
		document.getElementById('visitRecords').innerHTML = content;

		$
				.ajax({
					type : "GET",
					url : contextPath
							+ "/administrator/adminsearchdatevisitrecords.do",
					data : "searchDate="
							+ document.getElementById('datetext').value,
					cache : false,
					async : false,
					success : function(response) {
						if(response=="null" || response==""){
							content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

								content += '</table>';
								document.getElementById('visitRecords').innerHTML = content;
							
						}else{

							var parsedJson = $.parseJSON(response);

							var patientname;
							var dob;
							var visitdate;
							var reasonforvisit;
							

							for (var i = 0; i < parsedJson.length; i++) {

								content = content + '<tr onclick="fnSetVisitvalue(\''+parsedJson[i].visitid+'\')">';
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
		document.getElementById('datetext').value = "";


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

						content += '</table>';
						document.getElementById('visitRecords').innerHTML = content;
					}

						

					},
					error : function(e) {
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

							content += '</table>';
							document.getElementById('visitRecords').innerHTML = content;
						}

					},
					error : function(e) {
					}

				});
		document.getElementById('keyword').value = "";	
	}


	function searchPhysicianVisitRecordsdd(physicianid){

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
	
							content += '</table>';
							document.getElementById('visitRecords').innerHTML = content;
						
					}else{

						var parsedJson = $.parseJSON(response);

						for (var i = 0; i < parsedJson.length; i++) {

							content = content + '<tr onclick="fnSetVisitvalue(\''+parsedJson[i].visitid+'\')">';
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

						if(response=="null" || response==""){
							content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';
		
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

							content += '</table>';
							document.getElementById('visitRecords').innerHTML = content;

						}

					},
					error : function(e) {
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
			<h3 class="panel-title">Encounters</h3>
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

			<input type="hidden" id="hdnPhysicianId" name="physicianId">

			<input type="hidden" id="hdnVisitId" name="hdnVisitId">

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

		<!-- <button class="btn btn-inverse" disabled="" type="button" onclick="showDetais()">Search Here</button> -->

		<!-- <button class="btn btn-inverse" disabled="" type="button">Reset Password</button> -->
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


	<div class="panel-body" style="display: none" id="encounterDetails">
		<div class="table-responsive table-responsive-datatables">
			<table class="tablesorter table table-hover table-bordered">
				<thead>
					<tr>
						<th></th>
						<th>Encounters</th>
						<th>Id</th>


					</tr>
				</thead>



			</table>
		</div>
	</div>

	<div id="emailSuccess1"
		style="display: none; color: #da4f49; margin-left: 30%;"></div>

	<span class="text-danger" id="deleteencounter"
		style="margin-left: 30%;"></span>



	<div id="editencounter"
		class="panel panel-default sortable-widget-item"
		style="display: none;">

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
			<h3 class="panel-title">Edit Encounter</h3>
		</div>

		<div class="panel-body">
			<form role="form" class="form-horizontal form-bordered"
				name="updateAdminPracticeForm" id="updateAdminPracticeForm">

				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">Encounter</label>
					<div class="col-sm-5">
						<div class="input-group input-group-in">
							<span class="input-group-addon text-silver"></span> <input
								type="text" id="editencountername" name="editencountername"
								class="form-control" />
						</div>
					</div>
				</div>
				<div id="searchButton" style="margin: 3%; display: block;">
					<input type="hidden" name="encounterid" id="encounterid" value="">
					<input type="button" class="btn btn-inverse" value="Submit"
						onclick="updateEncounter()"> <input type="reset"
						class="btn btn-inverse" value="Reset"> <input
						type="button" class="btn btn-inverse" value="Cancel"
						onclick="cancelPractice()">


				</div>
			</form>
		</div>

	</div>

</div>

<jsp:include page="../footerbluehub.jsp"></jsp:include>