<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script>
        var contextPath = "<%=request.getContextPath()%>";

	function submitTag() {

		var note = $('#note').val();
		if (note == "") {

			document.getElementById('emailSuccess1').innerHTML = "Please Enter Encounter";

			fnSetTimeout("emailSuccess1", 3000);

		} else {

			var visitid = document.getElementById("hdnVisitId").value
			$
					.ajax({
						type : "GET",
						/* url : contextPath + "/administrator/patientnoteentry.do", */
						url : contextPath
								+ "/administrator/physicianencounters.do",
						data : "note=" + note + "&visitid=" + visitid,
						cache : false,
						async : false,
						success : function(response) {
							var str = response;
							document.getElementById('note').value = "";

							document.getElementById('tagupdate').style.display = "block";
							var fieldNameElement = document
									.getElementById("tagupdate");
							fieldNameElement.innerHTML = str;

							fnSetTimeout("tagupdate", 3000);
						},
						error : function(e) {
						}
					});

		}

	}
	function updateEncounter() {

		var encountername = document.getElementById("editencountername").value;
		var encounterid = document.getElementById("encounterid").value;

		$
				.ajax({
					type : "GET",
					url : contextPath + "/administrator/updateencounters.do",
					data : "encountername=" + encountername + "&encounterid="
							+ encounterid,
					cache : false,
					async : false,
					success : function(response) {
						document.getElementById("deleteencounter").innerHTML = "Encounter Successfully Updated";

						setTimeout('fnloadVisit()', 3000);
					},
					error : function(e) {
					}
				});

	}

	function fnloadVisit() {
		window.location.href = contextPath
				+ "/physician/physicianVisitDetails.do";
	}

	function editEncounterRow(id) {

		$
				.ajax({
					type : "GET",
					url : contextPath + "/administrator/editadminencounter.do",
					data : "encounterid=" + id,
					success : function(response) {

						var parsedJson = $.parseJSON(response);

						if (parsedJson != null) {

							document.getElementById("editencounter").style.display = "block";

							document.getElementById("editencountername").value = parsedJson[0].encounter;
							document.getElementById("encounterid").value = parsedJson[0].id;

						}
					},
					error : function(e) {
					}
				});

	}
	function deleteEncounterRow(id) {
		var answer = confirm("Are you sure want to delete the encounter ?")
		if (answer) {
			deleteEncounter(id);
		}
		return false;
	}

	function deleteEncounter(id) {

		$
				.ajax({
					type : "GET",
					url : contextPath
							+ "/administrator/deleteadminencounter.do",
					data : "encounterid=" + id,
					success : function(response) {

						document.getElementById("deleteencounter").innerHTML = "Encounter Successfully Deleted";

						setTimeout('fnloadVisit()', 3000);

					},
					error : function(e) {
					}
				});

	}
	function createVisit(id) {
		document.getElementById("hdnVisitId").value = id;
if(document.getElementById("createVisitCreate")){
		document.getElementById("createVisitCreate").style.display = "none";
}
		document.getElementById("createVisit").style.display = "block";
		document.getElementById("encounterDetails").style.display = "none";
		document.getElementById("createencounter").style.display = "none";


		$("#date_of_visit").val("");
		$("#reason_for_visit").val("");
		$("#prescription").val("");
		$("#physicianVisitOrganization").val("");

		$("#physicianVisitPractice").val("");

		document.getElementById('physicianVisitTitle').innerHTML = 'Create New Visit';
		document.getElementById('phyVisitSubmit').value = 'Submit';
		document.getElementById('phyVisitSubmit').setAttribute(
				'onclick', 'fnPhysicianVisitForm();');
	}
	function createEncountor(id) {
		document.getElementById("hdnVisitId").value = id;
		document.getElementById("createVisit").style.display = "none";
		if(document.getElementById("createVisitCreate")){
		document.getElementById("createVisitCreate").style.display = "none";
		}
		document.getElementById("encounterDetails").style.display = "none";
		document.getElementById("createencounter").style.display = "block";
	}

	function fnSetVisitvalue(id) {
		document.getElementById("hdnVisitId").value = id;
		if(document.getElementById("createVisitCreate")){
		document.getElementById("createVisitCreate").style.display = "none";
		}

		document.getElementById("encounterDetails").style.display = "block";
		document.getElementById("createencounter").style.display = "none";
		document.getElementById("createVisit").style.display = "none";

		var content = '';
		content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
		content += '<thead>';
		content += '<th>Encounters</th>';

		content += '</thead>';
		document.getElementById('encounterDetails').innerHTML = content;

		$
				.ajax({
					type : "GET",
					url : contextPath
							+ "/administrator/adminencounterdetails.do",
					data : "visitid=" + id,
					cache : false,
					async : false,
					success : function(response) {

						if (response == "null") {
							content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

							content += '</table>';
							document.getElementById('encounterDetails').innerHTML = content;

						} else {

							var parsedJson = $.parseJSON(response);

							if (parsedJson != null) {

								for ( var i = 0; i < parsedJson.length; i++) {

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

	//Added for 
	function editAdminVisit(id) {
		$
				.ajax({
					type : "GET",
					url : contextPath + "/physician/editphysicianvisit.do",
					cache : false,
					data : "phyid=" + id,
					async : false,
					success : function(response) {
						var parsedJson = $.parseJSON(response);
						var dateOfVisit = dov = '';
						if (parsedJson[0][3] == undefined
								|| parsedJson[0][3] == 'undefined') {
							dov = '';
						} else {
							dateOfVisit = new Date(parsedJson[0][3]);
							dov = (dateOfVisit.getMonth() + 1) + "/"
									+ dateOfVisit.getDate() + "/"
									+ dateOfVisit.getFullYear();
						}
						document.getElementById("createVisit").style.display = "block";

						$("#date_of_visit").val(dov);
						$("#reason_for_visit").val(parsedJson[0][5]);
						$("#prescription").val(parsedJson[0][4]);
						$("#physicianVisitOrganization").val(parsedJson[0][6]);

						$("#physicianVisitPractice").val(parsedJson[0][7]);
						$("#hdnPhysicianId").val(parsedJson[0][9]);
						$("#hdnPatientId").val(parsedJson[0][8]);
						$("#patientname").val(parsedJson[0][1]);
						$("#physicianname").val(parsedJson[0][2]);
						$("#hiddenPKID").val(parsedJson[0][0]);
						document.getElementById('physicianVisitTitle').innerHTML = 'Edit Visit';
						document.getElementById('phyVisitSubmit').value = 'Update';
						document.getElementById('phyVisitSubmit').setAttribute(
								'onclick', 'updateAdminvisit();');
					},
					error : function(e) {
						alert('Error: ' + e.responseText);
					}
				});
	}
	function updateAdminvisit() {

		validCheck = physicianVisitValidation('phyVisitForm');

		if (validCheck) {
			var phyid = $('#hdnPhysicianId').val();
			var visitDate = $('#date_of_visit').val();
			var reason = $('#reason_for_visit').val();
			var prescription = $('#prescription').val();
			var orgid = $('#physicianVisitOrganization').val();
			var practice = $('#physicianVisitPractice').val();
			var patid = $('#hdnPatientId').val();
			var pkid = $("#hiddenPKID").val();

			$.ajax({
				type : "GET",
				url : contextPath + "/physician/updatephysicianvisit.do",
				cache : false,
				data : {
					"phyid" : phyid,
					"visitDate" : visitDate,
					"reason" : reason,
					"practice" : practice,
					"org" : orgid,
					"prescription" : prescription,
					"patid" : patid,
					"pkid" : pkid
				},
				async : false,
				success : function(response) {
					window.location.href = contextPath
							+ "/physician/physicianVisitDetails.do";
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});
		}
	}

	function loadPatientsTable() {
		
		if(document.getElementById('physicianVisitShow')){
			document.getElementById('physicianVisitShow').style.display = 'none';
			}
		if(document.getElementById('createVisit')){
			document.getElementById('createVisit').style.display = 'none';
			}
		if(document.getElementById('createencounter')){
			document.getElementById('createencounter').style.display = 'none';
			}
		if(document.getElementById('encounterDetails')){
			document.getElementById('encounterDetails').style.display = 'none';
			}
		
		
		document.getElementById('patientInformations').style.display = "block";
		var searchPatient = document.getElementById('patientname').value;
		var orgid = -1;
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

											return '<a href="javascript:fnSetPatientvalue('
													+ id
													+ ')" >'
													+ name
													+ '</a>'
										}
									}, /* {
										"mDataProp" : "Last Name",
										"bSortable" : false
									}, */ {
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

						//     			  "aaSorting": [[ 2, 'desc' ]]

						});

	}

	function loadPhysicianOrganization() {
		document.getElementById('physicianVisitTitle').innerHTML = "Create New Visit";
		$
				.ajax({
					type : "GET",
					url : contextPath + "/administrator/adminorganizations.do",
					cache : false,
					async : false,
					success : function(response) {

						var parsedJson = $.parseJSON(response);
						document.getElementById("physicianVisitOrganization").innerHTML = "";

						var combo = document
								.getElementById("physicianVisitOrganization");
						var defaultOption = document.createElement("option");
						defaultOption.text = 'Select';
						defaultOption.value = '';
						combo.add(defaultOption);
						for ( var i = 0; i < parsedJson.length; i++) {
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

		var orgid = document.getElementById('physicianVisitOrganization').value;

		$
				.ajax({
					type : "GET",
					url : contextPath + "/administrator/loadpractice.do",
					data : "orgid=" + orgid,
					cache : false,
					async : false,
					success : function(response) {

						var parsedJson = $.parseJSON(response);
						document.getElementById("physicianVisitPractice").innerHTML = "";

						var combo = document
								.getElementById("physicianVisitPractice");

						var defaultOption = document.createElement("option");
						defaultOption.text = 'Select';
						defaultOption.value = '';
						combo.add(defaultOption);

						for ( var i = 0; i < parsedJson.length; i++) {
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

	function loadPhysicianVisitDetails1(patid) {

		document.getElementById('physicianVisitShow').style.display = "block";
		var searchPatient = document.getElementById('patientname').value;
		var orgid = -1;
		var params = "patientId=" + patid + "&orgid=" + orgid;

		$('#AdminVisittable')
				.dataTable(
						{

							"processing" : true,
							"bJQueryUI" : true,
							"bFilter" : false,
							"bDestroy" : true,
							//		 	  	"bAutoWidth": true,
							//			  	"sDom": '<"top"i>rt<"bottom"flp><"clear">',
							"bServerSide" : true,
							"sAjaxSource" : contextPath
									+ "/physician/getAdminPhysicianVisitDetails.do?"
									+ params,
							"bProcessing" : true,
							"aoColumns" : [
									{
										"mDataProp" : "Patient Name",
										"bSortable" : false,

										"fnRender" : function(oObj) {

											var id = oObj.aData['id'];
											var name = oObj.aData['Patient Name'];

											return name
										}
									},
									{
										"mDataProp" : "Date of Visit",
										"bSortable" : false
									},
									{
										"mDataProp" : "Reason for Visit","sWidth" : "22%",
										"bSortable" : false
									},
									{
										"mDataProp" : "Physician Consulted","sWidth" : "16%",
										"bSortable" : false
									},
									{
										"mDataProp" : "Prescription of Physician","sWidth" : "18%",
										"bSortable" : false
									},
									{
										"mDataProp" : "Visits",
										"bSortable" : false,"sWidth" : "8%",

										"fnRender" : function(oObj) {

											var id = oObj.aData['id'];
											var name = oObj.aData['Patient Name'];
											return '<input title="Create Visit" type="button" value="" id="delDocRow" class="addIcon" onclick="createVisit('+ id+ ')"/>  &nbsp;<input title="Delete Visit" type="button" value="" id="delDocRow" class="del_row" onclick="deleteAdminVisit('+ id+ ')"/>  &nbsp;&nbsp;';
										}

									},
									{
										"mDataProp" : "Encounters","sWidth" : "8%",
										"bSortable" : false,

										"fnRender" : function(oObj) {

											var id = oObj.aData['id'];
											var name = oObj.aData['Patient Name'];

											return '<input title="Create Encounter" type="button" value="" id="delDocRow" class="addIcon" onclick="createEncountor('
													+ id
													+ ')"/> &nbsp;&nbsp;&nbsp <input title="View Encounters" type="button" value="" id="editDocRow" class="viewIcon" onclick="fnSetVisitvalue('
													+ id + ');"/>'
										}

									} ]

						});

	}
	 

	function fnSetPatientvalue(id) {
		document.getElementById("physicianVisitTitle").innerHTML = "Create New Visit";
		document.getElementById("hdnPatientId").value = id;
		loadPhysicianVisitDetails1(id);
		clearInputValues();
		document.getElementById("physicianVisitShow").style.display = "block";
	}

	function clearInputValues() {
		document.getElementById("date_of_visit").value = "";
		document.getElementById("reason_for_visit").value = "";
		document.getElementById("prescription").value = "";
	}
	function fnSetPhysicianvalue(id) {
		document.getElementById("hdnPhysicianId").value = id;
	}
</script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/physician/physician.js"></script>

<div class="content" style="margin-left: 22px;">

	<div class="content-body">

		<div id="panel-typeahead"
			class="panel panel-default sortable-widget-item">
			<div class="panel-heading">
				<div class="panel-actions">
				</div>
				<h3 class="panel-title">Visits</h3>
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

					<input type="hidden" id="hdnPatientId" name="patientId"> <input
						type="hidden" id="hdnPhysicianId" name="physicianId"> <input
						type="hidden" id="hdnVisitId" name="hdnVisitId"> <input
						type="hidden" id="hdnOrganizationId" name="organization">

					<input type="hidden" id="hdnPracticeId" name="practice">

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

					<div id="physicianVisitShow" style="display: none">
						<div class="subscribe_top2" id="subscribe_top2">
							<div id="panel-tablesorter" class="panel panel-default">
								<div class="panel-heading bg-white">
									<div class="panel-actions">
									 
									</div>
									<h3 class="panel-title">Visit Details</h3>
								</div>
								<table class="table table-striped table-bordered"
									id="AdminVisittable">
									<thead>
										<tr>
											<th>Patient Name</th>
											<th>Date of Visit</th>
											<th>Reason for Visit</th>
											<th>Physician Consulted</th>
											<th>Prescription of Physician</th>
											<th>Visits</th>
											<th>Encounters</th>
										</tr>
									</thead>



								</table>




							</div>
						</div>

						<div>
							<input type="hidden" id="hiddenPKID" name="hiddenPKID" value="" />
						</div>
						<div id="createVisit">
							<h3 id="physicianVisitTitle">Create new Visit</h3>
							<div class="form-group">
								<label class="col-sm-3 control-label" for="typeahead-local">Date
									of Visit</label>
								<div class="col-sm-5">
									<div class="input-group input-group-in date"
										data-input="datepicker" data-format="mm/dd/yyyy">
										<input type="text" class="form-control" id="date_of_visit"
											name="date_of_visit" placeholder="Date of Visit" /> <span
											class="input-group-addon text-silver"><i
											class="fa fa-calendar"></i></span>
									</div>
									 
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label" for="typeahead-local">Reason
									for Visit<span class="required">*</span>
								</label>
								<div class="col-sm-5">
									<div class="input-group input-group-in">
										<span class="input-group-addon text-silver"><i
											class="glyphicon glyphicon-user"></i></span> <input type="text"
											id="reason_for_visit" name="reason_for_visit" value=""
											class="form-control" />
									</div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label" for="typeahead-local">Prescription
									<span class="required">*</span>
								</label>
								<div class="col-sm-5">
									<div class="input-group input-group-in">
										<span class="input-group-addon text-silver"><i
											class="glyphicon glyphicon-user"></i></span>
										<textarea rows="10" cols="45" name="prescription" value=""
											id="prescription" style="resize: none;"></textarea>
									</div>
								</div>
							</div>
							<input type="hidden" id="physicianVisitPractice"> <input
								type="hidden" id="physicianVisitOrganization">


						 

							<div class="physicianInformations" id="physicianDetails"
								style="display: none">
								<div id="panel-tablesorter" class="panel panel-default">
									<div class="panel-heading bg-white">
										<div class="panel-actions">

											 
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

							<div id="submitButton" style="margin: 3%;">
								<input type="button" class="btn btn-inverse" id="phyVisitSubmit"
									onclick="fnPhysicianVisitForm();" value="Submit" /> <input
									type="reset" class="btn btn-inverse" id="reset" value="Reset"
									onclick="fnBtnSubmitChange();" />
							</div>

						</div>

					</div>

					<div id="createencounter" style="display: none">


						<input type="hidden" id="hiddenPhysicianVisitId"
							name="hiddenPhysicianVisitId" value="" />

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
								<h3 class="panel-title">Create New Encounter</h3>
							</div>

							<div class="panel-body">

								<div class="form-group">
									<label class="col-sm-3 control-label" for="typeahead-local">Encounter</label>
									<div class="col-sm-5">
										<div class="input-group input-group-in">
											<textarea rows="5" maxlength="450" cols="50"
												style="resize: none;" name="note" id="note"></textarea>
										</div>
									</div>
								</div>
								<input type="button" class="btn btn-inverse" id="phyVisitSubmit"
									onclick="submitTag();" value="Submit" />
								<div id="emailSuccess1"
									style="display: none; color: #da4f49; margin-left: 30%;"></div>
								<div id="tagupdate"
									style="display: none; color: #da4f49; margin-left: 30%;"></div>
								<span class="text-danger" id="deleteencounter"
									style="margin-left: 30%;"></span>


								<div class="form-group">
									<div id="adminVisitButton" style="margin: 3%;">
									</div>
								</div>

							</div>

						</div>



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



				</form>

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
								<input type="hidden" name="encounterid" id="encounterid"
									value=""> <input type="button" class="btn btn-inverse"
									value="Submit" onclick="updateEncounter()"> <input
									type="reset" class="btn btn-inverse" value="Reset"> <input
									type="button" class="btn btn-inverse" value="Cancel"
									onclick="cancelPractice()">


							</div>
						</form>
					</div>



				</div>
			</div>
		</div>
	</div>
</div>

</section>

<script>
	//loadPhysicianOrganization();
	loadPhysicianOrganizations();
</script>

<jsp:include page="../footerbluehub.jsp"></jsp:include>