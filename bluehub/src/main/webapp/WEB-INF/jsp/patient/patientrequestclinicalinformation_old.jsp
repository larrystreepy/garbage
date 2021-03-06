<%@page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.bluehub.bean.user.UserProfile"%>
<%@page import="com.bluehub.vo.user.RoleVO"%>
<%@page import="javax.management.relation.Role"%>
<%@page import="com.bluehub.vo.user.UserRoleVO"%>
<%@page import="com.bluehub.vo.user.UserVO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>



<script>
              
var contextPath = "<%=request.getContextPath()%>";
</script>

<script>
function setPhysicianValue(id){
	
	document.getElementById("hdnPhysicianId").value = id;
	
	document.getElementById('emailSuccess1').style.display = "none";
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
        
        
       

        function showDetais(){

         var physicianname=document.getElementById('physicianname').value;
       	 var mailid=document.getElementById('mailid').value;
       	var physicianid=document.getElementById("hdnPhysicianId").value;
        if(physicianname=="" && mailid=="" && physicianid==""){
	   		 document.getElementById('emailSuccess1').style.display = "block";
	   		 document.getElementById("emailSuccess1").innerHTML = "Please Choose Physician Or Enter Email"; 
      	 }else{

      		document.getElementById('emailSuccess1').style.display = "none";
      		 
       		  	

    	var physicianname=document.getElementById('physicianname').value;
    	var mailid=document.getElementById('mailid').value;
 		
    	if(document.getElementById("mailid").value!=""){
    		
    		
    		$.ajax({
				type : "GET",
				 url : contextPath
						+ "/patient/patientrequestmailinfo.do", 
				data : "mailid=" + mailid,
				/* data : "physicianid=" + document.getElementById('physicianRadio').value, */
				cache : false,
				async : false,
				success : function(response) {
					var str=response;
					 document.getElementById('emailSuccess').style.display = "block";
					 var fieldNameElement = document.getElementById("emailSuccess");
					 fieldNameElement.innerHTML = str;
					/* document.getElementById('emailSuccess').innerText = "trhis is the message"; */
					 document.getElementById("hdnPhysicianId").value = "";
				
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
        }else {
		
        	
    		$.ajax({
				type : "GET",
				url : contextPath
						+ "/patient/patientrequestinfo.do",
				data : "physicianid=" + physicianid,
				/* data : "physicianid=" + document.getElementById('physicianRadio').value, */
				cache : false,
				async : false,
				success : function(response) {

					//var myRoleHidden = $('#myRoleHidden').val();
					var parsedJson = $.parseJSON(response);

					
					 document.getElementById('emailSuccess1').innerHTML = parsedJson.message;
					 
					 loadPhysicianTable();
					 fnSetTimeout("emailSuccess1" ,3000);
					 document.getElementById("hdnPhysicianId").value = "";
					
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
        }
    		
       		 
       	 }
 		
          	 
      	 }

        
        
        
        function loadPhysicianTable() {
        	document.getElementById("mailid").value="";
        	document.getElementById('physicianDetails').style.display = "block";
//         	document.getElementById('patientDetails').style.display = "none";

        	var orgid = document.getElementById('selectOrganization').value;

        	var practiceid = document.getElementById('selectPractice').value;
        	var searchphysician = document.getElementById('physicianname').value;
        	// if(practiceid!=""){
//         	var params = "orgid=" + orgid + "&practiceid=" + practiceid
//         			+ "&searchphysician=" + searchphysician;


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

        										return '<a href="javascript:setPhysicianValue('
        												+ id + ')" >' + name + '</a>'
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

        
        </script>


<div class="content-body">

	<input type="hidden" id="hdnPhysicianId" name="hdnPhysicianId">
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
			<h3 class="panel-title">Request Clinical Information</h3>
		</div>

		<div class="panel-body">
			<form role="form" name="form" class="form-horizontal form-bordered">


				<div id="organizationlabel" >
					<div class="form-group" style="display: none;">
						<label class="col-sm-3 control-label" for="typeahead-local">Organization</label>
						<div class="col-sm-5">
							<select class="form-control" id="selectOrganization"
								onchange="loadPractice()">
							</select>
						</div>
					</div>

					<div class="form-group" id="practicelabel" style="display: none">
						<label class="col-sm-3 control-label" for="typeahead-local">Practice</label>


						<div class="col-sm-5">
							<select id="selectPractice" class="form-control">
								
							</select>
						</div>
						
					</div>


					<div id="physicianlabel" style="display: block;">
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


					<div style="margin: 3% 0% 3% 40%; display: block;" id="ORSPANID">OR</div>
					<div id="emaillabel" style="display: block">
						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Enter
								Email Address</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="mailid" name="mailid" class="form-control"
										placeholder="Mail Id" />

								</div>
								<div id="emailSuccess" style="display: none; color: #da4f49;"></div>
							</div>
						</div>

					</div>

			</div>


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
						</div>
					</div>
				</div>



				<div id="emailSuccess1"
					style="display: none; color: #da4f49; margin-left: 30%;"></div>



				<div id="buttonlabel" style="margin-left: 37%; margin-top: 6%;">
					<div class="form-group">
						<input type="button" size="25" id="searchBtn" name="searchBtn"
							value="Send Request" class="btn btn-inverse"
							onclick="showDetais()" />

					</div>
				</div>







				<div class="panel-body">
					<div class="table-responsive table-responsive-datatables"
						id="patientAddtionalInformations" style="display: none">
						<table class="tablesorter table table-hover table-bordered"
							id="documentTable">
							<thead>
								<tr>

									<th>First Name</th>
									<th>Last Name</th>
									<th>Contact Number</th>
									<th>Address</th>

								</tr>
							</thead>

							<tbody>
								<tr>
									<td>John</td>
									<td>Nicholas</td>
									<td>9826852369</td>
									<td>N03 los angles US</td>
								</tr>


							</tbody>

							
						</table>

						<div id="buttonlabel">
							<div class="form-group">
								<input type="button" size="25" id="searchBtn" name="searchBtn"
									value="Confirm Request" class="btn btn-inverse"
									onclick="showDetais()" />

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

				<div id="patientlabel" style="display: none">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Patient
							Name</label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"><i
									class="glyphicon glyphicon-user"></i></span> <input type="text"
									id="patientname" name="patientname" class="form-control"
									placeholder="Patient Name" onkeyup="loadPatientTable()" />
							</div>
						</div>
					</div>
				</div>



			</form>
		</div>
	</div>







</div>

<script>


                          
var contextPath = "<%=request.getContextPath()%>";
</script>

<%
	UserProfile user = (UserProfile) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();

	String role = user.getRoleid();
	Integer userId = user.getId();
%>

<jsp:include page="../footerbluehub.jsp"></jsp:include>