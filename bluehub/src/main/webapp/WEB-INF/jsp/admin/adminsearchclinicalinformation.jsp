
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script
	src="<%=request.getContextPath()%>/js/admin/adminsearchclinicalinformation.js"></script>

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
							<!-- <option value="3">Search by Tag</option> -->
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

			<div class="physicianInformations" id="physicianDetailss"
				style="display: none; width: 100%;">

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
			<input type="hidden" name="hdnPatId" id="hdnPatId" value="">

		</div>

	</form>




</div>

<script>
            document.getElementById("searchButton").style.display = "none";

			</script>


<jsp:include page="../footerbluehub.jsp"></jsp:include>