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
	function checkSignature(){
		
		$.ajax({
			type : "GET",
			url : contextPath
					+ "/patient/checksignature.do",
			cache : false,
			async : false,
			success : function(response) {

				//var myRoleHidden = $('#myRoleHidden').val();
				var parsedJson = $.parseJSON(response);

				if(parsedJson.message=="yes"){
					document.getElementById("buttoncommon").style.display = "block";
					document.getElementById("buttonsig").style.display = "none";
				}else{
					document.getElementById("buttoncommon").style.display = "none";
					document.getElementById("buttonsig").style.display = "block";
				}
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});
	}

	function setPhysicianValue(id) {
		document.getElementById("hdnPhysicianId").value = id;
		document.getElementById('emailSuccess1').style.display = "none";
	}

	function fnClearMsgField() { 
		document.getElementById(displayMsgId).style.display = "none";
	}

	function fnSetTimeout(id, time) {
		if (time == undefined)
			time = 3000;
		displayMsgId = id;
		document.getElementById(displayMsgId).style.display = "block";
		//setTimeout('fnClearMsgField()', time);
	}

	function fnHomeTimeout(time) {
		if (time == undefined)
			time = 3000;
		
		// window.location.href = contextPath + "/patient/home.do";
		
		setTimeout('fnGoHome()', time);
	}

	function fnGoHome()
	{
		window.location.href = contextPath + "/patient/home.do";
	}

	function fnRefresh(time) {
		if (time == undefined)
			time = 3000;
		
		window.location.href = contextPath
		+ "/patient/patientrequest.do";
		
		//setTimeout('fnClearMsgField()', time);
	}

	function sendPatientRequest() {
		var fax = document.getElementById('fax').value;
		var physicianname = document.getElementById('physicianname').value;
		// var mailid = document.getElementById('mailid').value;
		var mailid = "";
		var physicianid = document.getElementById("hdnPhysicianId").value;

		if (mailid == "" && physicianid == "" && fax=="") {
			document.getElementById('emailSuccess1').style.display = "block";
			document.getElementById("emailSuccess1").innerHTML = "Please Choose a Physician Or Enter a Fax Number";
			fnSetTimeout("emailSuccess1", 4000);
		} else {

			document.getElementById('emailSuccess1').style.display = "none";

			if (physicianid=="") {

				$.ajax({
                    type : "GET",
                    url : contextPath + "/patient/patientrequestmailinfo.do",
                    data : "mailid=" + mailid+"&fax="+fax,
                    cache : false,
                    async : false,
                    success : function(response) {
                        var str = response;
                        document.getElementById('emailSuccess').style.display = "block";
                        var fieldNameElement = document.getElementById("emailSuccess");
                        fieldNameElement.innerHTML = str;
                        fnSetTimeout("emailSuccess", 5000);
                        /* document.getElementById('emailSuccess').innerText = "this is the message"; */
                        document.getElementById("hdnPhysicianId").value = "";
                        document.getElementById("physicianDetails").style.display = "none";

                        fnHomeTimeout(5000);
                    },
                    error : function(e) {
                        alert('Error: ' + e.responseText);
                    }
                });
			} else {

				$.ajax({
                    type : "GET",
                    url : contextPath + "/patient/patientrequestinfo.do",
                    data : "physicianid=" + physicianid,
                    /* data : "physicianid=" + document.getElementById('physicianRadio').value, */
                    cache : false,
                    async : false,
                    success : function(response) {
                        //var myRoleHidden = $('#myRoleHidden').val();
                        var parsedJson = $.parseJSON(response);

                        document.getElementById('emailSuccess1').innerHTML = parsedJson.message;

                        loadPhysicianTable();
                        fnSetTimeout("emailSuccess1", 3000);
                        document.getElementById("hdnPhysicianId").value = "";
                        document.getElementById("physicianDetails").style.display = "none";

                        fnHomeTimeout(5000);
                    },
                    error : function(e) {
                        alert('Error: ' + e.responseText);
                    }
                });
			}
		}
	}

    function fnCreateSignature(){
		
		var signText = document.getElementById('digitalSign').value;
		if(signText.trim()==""){
			
			document.getElementById('emailSuccess2').innerHTML = "Signature text cannot be empty.";
			fnSetTimeout("emailSuccess2", 3000);
		}else{
		
		$
		.ajax({
			type : "GET",
			url : contextPath
					+ "/patient/createSignatureFromText.do",
			data : "signText=" + signText.trim(),
			cache : false,
			async : false,
			success : function(response) {

				var parsedJson = $.parseJSON(response);
				document.getElementById('digitalSign').value = "";
				document.getElementById('emailSuccess2').innerHTML = parsedJson.message;
				fnSetTimeout("emailSuccess2", 3000);
				
				/* fnHomeTimeout(5000); */
				fnRefresh(5000);
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});
		
		}
	}
/* function fnCreateSignature(){
		
		var signText = document.getElementById('digitalSign').value;
		
		$
		.ajax({
			type : "GET",
			url : contextPath
					+ "/patient/createSignatureFromText.do",
			data : "signText=" + signText,
			cache : false,
			async : false,
			success : function(response) {

				var parsedJson = $.parseJSON(response);
				document.getElementById('emailSuccess2').innerHTML = parsedJson.message;
				fnSetTimeout("emailSuccess2", 3000);

			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});
	} */

	function fnOnchangeMailId(){
		
		document.getElementById('physicianDetails').style.display = "none";
	}
	
	function loadPhysicianTable() {
		<!-- document.getElementById("mailid").value = ""; -->
		document.getElementById('physicianDetails').style.display = "block";
		//         	document.getElementById('patientDetails').style.display = "none";

		var orgid = document.getElementById('selectOrganization').value;
		var searchphysician = document.getElementById('physicianname').value;
		// var practiceid = document.getElementById('selectPractice').value;
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

											return '<a data-target="modalBasic" href="javascript:setPhysicianValue('
													+ id
													+ ')" >'
													+ name
													+ '</a>'
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

                           <div class="modal fade" id="modalBasic" tabindex="-1" role="dialog" aria-labelledby="modalBasicLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="modalBasicLabel">Signature</h4>
                                        </div>
                                        <div class="modal-body">
                                            <input type="text"
										id="digitalSign" name="digitalSign" class="form-control"
										placeholder="Type Text Here"/>

						<div id="emailSuccess2"
										style="display: none; color: #da4f49; margin-left: 30%;" onclick="fnClearMsgField()"></div>


							<!-- signature uploader start -->
 								<!-- <div class="form-group">
                                    <label class="col-sm-3 control-label" for="fileinput_thumb">Image upload widgets</label>
                                    <div class="col-sm-5">
                                        <div class="fileinput fileinput-new" data-provides="fileinput">
                                            <div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 200px; height: 150px;">
                                                <img data-src="holder.js/100%x100%" alt="">
                                            </div>
                                            <div>
                                                <span class="btn btn-sm btn-icon btn-inverse btn-file">
                                                    <i class="fa fa-cloud-upload"></i>
                                                    <span class="fileinput-new">Select image</span>
                                                    <span class="fileinput-exists">Change</span>
                                                    <input type="file" name="fileinput_thumb" id="fileinput_thumb" accept="image/*">
                                                </span>
                                                <button class="btn btn-sm btn-icon btn-danger fileinput-exists" data-dismiss="fileinput">
                                                    <i class="fa fa-times"></i>
                                                    Remove
                                                </button>
                                            </div>
                                        </div>/fileinput
                                    </div>/cols
                                </div> -->
                              <!-- signature uploader start -->

                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                            <button onclick="fnCreateSignature()" type="button" class="btn btn-primary">Save</button>
                                        </div>
                                    </div><!-- /.modal-content -->
                                </div><!-- /.modal-dialog -->
                            </div>



				<div id="organizationlabel" >
					<div class="form-group" style="display: none;">
						<label class="col-sm-3 control-label" for="typeahead-local">Organization</label>
						<div class="col-sm-5">
							<select class="form-control" id="selectOrganization" onchange="loadPractice()"></select>
						</div>
					</div>

					<div class="form-group" id="practicelabel" style="display: none">
						<label class="col-sm-3 control-label" for="typeahead-local">Practice</label>
						<div class="col-sm-5">
							<select id="selectPractice" class="form-control"></select>
						</div>
					</div>

					<div id="physicianlabel" style="display: block;">
						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Physician Name</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver">
									    <i class="glyphicon glyphicon-user"></i>
                                    </span>
                                     <input type="text" id="physicianname" name="physicianname" class="form-control"
										placeholder="Physician Name" onkeyup="loadPhysicianTable()" />
								</div>
                                <div>
                                    <span class="required" style="float: left; margin: 1.5% 0px 2% 0.6%; width: 550px;">
                                        Just start typing to search for a physician by name
                                    </span>
                                </div>
							</div>
						</div>
					</div>

                    <!--
					 <div style="margin: 3% 0% 3% 40%; display: block;" id="ORSPANID">OR</div>
					<div id="emaillabel" style="display: block">
						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Enter Email Address</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="mailid" name="mailid" class="form-control"
										placeholder="Email Address" onkeyup="fnOnchangeMailId()" />
								</div>
								<div id="emailSuccess" style="display: none; color: #da4f49;"></div>
							</div>
						</div>
					</div>
					-->

                    <div style="margin: 3% 0% 3% 40%; display: block;" id="ORSPANID">OR</div>
                    <div id="faxlabel" style="display: block">
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="typeahead-local">Enter Fax Number</label>
                            <div class="col-sm-5">
                                <div class="input-group input-group-in">
                                    <span class="input-group-addon text-silver">
                                        <i class="glyphicon glyphicon-user"></i>
                                    </span>
                                    <input type="text" id="fax" name="fax" class="form-control"
                                        placeholder="Fax Number" onkeyup="" />
                                </div>
                                <div>
                                    <span class="required" style="float: left; margin: 1.5% 0px 2% 0.6%; width: 550px;">
                                        Example: 1-512-999-1111
                                    </span>
                                </div>

								<div id="emailSuccess" style="display: none; color: #da4f49;"></div>
                            </div>
                        </div>
                    </div>
			</div>

			<div id="physicianTableHolder" style="display: none;margin-left:30%;color: #da4f49;"></div>

				<div class="physicianInformations" id="physicianDetails" style="display: none">
					<div id="panel-tablesorter" class="panel panel-default">
						<div class="panel-heading bg-white">
							<div class="panel-actions">

								<button data-expand="#panel-tablesorter" title="expand" class="btn-panel">
									<i class="fa fa-expand"></i>
								</button>
								<button data-collapse="#panel-tablesorter" title="collapse" class="btn-panel">
									<i class="fa fa-caret-down"></i>
								</button>
								<button data-close="#panel-tablesorter" title="close" class="btn-panel">
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

				<div id="emailSuccess1" style="display: none; color: #da4f49; margin-left: 30%;" onclick="fnClearMsgField()"></div>

				<!-- <p style="color: #da4f49;margin-top: 4%; margin-left: 30%;">Please digitally sign the HIPAA release form to request your information</p> -->

				<div id="buttoncommon" style="margin-left: 37%; margin-top: 6%;">
					<div class="form-group">
					
					<button type="button" class="btn btn-inverse"  id="sigbutton" name="sigbutton"
						data-toggle="modal" data-target="#modalBasic">Create Digital Signature</button>
					
						<input type="button" class="btn btn-inverse" size="25" id="searchBtn" name="searchBtn"
							value="Send Request"
							onclick="sendPatientRequest()" />
					</div>
				</div>

 				<div id="buttonsig" style="margin-left: 37%; margin-top: 6%;">
					<div class="form-group">
					
					<button type="button" class="btn btn-inverse"  id="sigbutton" name="sigbutton"
						data-toggle="modal" data-target="#modalBasic">Create Digital Signature</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<script>
var contextPath = "<%=request.getContextPath()%>";
checkSignature();
</script>

<%
	UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	String role = user.getRoleid();
	Integer userId = user.getId();
%>

<jsp:include page="../footerbluehub.jsp"></jsp:include>