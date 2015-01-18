<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/admin/adminorganization.js"></script>
<script>
var contextPath = "<%=request.getContextPath()%>";
</script>

<style>
#success_msg_span {
	color: #da4f49 !important;
	margin-left: 32%;
}

#success_msg_span1 {
	color: #da4f49 !important;
	margin-left: 32%;
}

#patientDetails {
	margin-top: 10%;
}
</style>
<input type="hidden" id="hdnPhysicianId" name="hdnPhysicianId" />
<input type="hidden" id="hdnVisitId" name="hdnVisitId" />

<div class="content-body">

	<div id="uploadClinicalInfoDiv">

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
				<h3 class="panel-title">Upload Clinical Informations</h3>
			</div>

			<div class="panel-body">

				<form role="form" class="form-horizontal form-bordered">
					<div id="organizationlabel">
						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Organization</label>

							<div class="col-sm-5">
								<select class="form-control" id="selectOrganization">
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
					




					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Name</label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"><i
									class="glyphicon glyphicon-user"></i></span> <input type="text"
									id="patientname" name="patientname" class="form-control"
									placeholder="Patient Name" onkeyup="loadPatientsTable()" />
							</div>
						</div>
					</div>


					<div class="patientInformations"
						style="display: none; margin-top: 10%;" id="patientDetails">


						<table id="patientLoadTable"
							class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>Patient Name</th>
									<th>DOB</th>
									<th>SSN</th>
									<th>Contact Number</th>
									<th>Address</th>

								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>

				</form>
			</div>


			<span id="success_msg_span" style="display: none; margin-top: 10%;"></span>



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


			<div id="searchButton"
				style="display: block; margin-left: 29%; border-bottom: 1px solid white;"
				class="panel-body">
				<input type="button" id="uploadbutton" class="btn btn-inverse"
					value="Proceed" onclick="proceedUploadClinicalInformation()">
			</div>
		</div>


	</div>


</div>


<div id="dropzoneDiv1" style="display: none;">

	<div id="panel-typeahead"
		class="panel panel-default sortable-widget-item">

		<div class="panel-body">

			<div id="panel-dropzone"
				class="panel panel-default sortable-widget-item">
				<div class="panel-heading">
					<div class="panel-actions">
						<button data-refresh="#panel-dropzone" title="refresh"
							class="btn-panel">
							<i class="fa fa-refresh"></i>
						</button>
						<button data-expand="#panel-dropzone" title="expand"
							class="btn-panel">
							<i class="fa fa-expand"></i>
						</button>
						<button data-collapse="#panel-dropzone" title="collapse"
							class="btn-panel">
							<i class="fa fa-caret-down"></i>
						</button>
						<button data-close="#panel-dropzone" title="close"
							class="btn-panel">
							<i class="fa fa-times"></i>
						</button>
					</div>
					<h3 class="panel-title">Dropzone</h3>
				</div>

				<form action="<%=request.getContextPath()%>/uploadfile.do"
					data-input="dropzone" class="dropzone" id="dropBox" name="dropBox"
					enctype="multipart/form-data">
					<div class="fallback">
						<input name="mydropzone1" id="mydropzone1" type="file" multiple />

					</div>
				</form>
			</div>



		</div>
	</div>

</div>


<div class="content-body">
	<div id="dropzoneDiv">

		<div id="panel-dropzone"
			class="panel panel-default sortable-widget-item">
			<div class="panel-heading">
				<div class="panel-actions">
					<button data-refresh="#panel-dropzone" title="refresh"
						class="btn-panel">
						<i class="fa fa-refresh"></i>
					</button>
					<button data-expand="#panel-dropzone" title="expand"
						class="btn-panel">
						<i class="fa fa-expand"></i>
					</button>
					<button data-collapse="#panel-dropzone" title="collapse"
						class="btn-panel">
						<i class="fa fa-caret-down"></i>
					</button>
					<button data-close="#panel-dropzone" title="close"
						class="btn-panel">
						<i class="fa fa-times"></i>
					</button>
				</div>
				<h3 class="panel-title">Upload Documents</h3>
			</div>

			

			<form action="<%=request.getContextPath()%>/uploadfile.do"
				data-input="dropzone" class="dropzone" id="dropBox" name="dropBox"
				enctype="multipart/form-data">
				<div class="fallback">
					<input name="mydropzone" id="mydropzone" type="file" multiple />


				</div>
			</form>
		</div>



		<span id="success_msg_span1" style="display: none;" onclick="fnClearMsgField()"></span>
		<div id="searchButton1"
			style="display: block; margin-left: 29%; border-bottom: 1px solid white;"
			class="panel-body">
			<input type="button" class="btn btn-inverse" value="Submit"
				onclick="submitUploadClinicalInformation()">

		</div>


	</div>




</div>



<script>
         document.getElementById("uploadClinicalInfoDiv").style.display = "block";
     	
     	document.getElementById("dropzoneDiv").style.display = "none";
     	
     	document.getElementById("uploadbutton").style.display = "none";
     	
     	loadOrganizations();
         </script>

<jsp:include page="../footerbluehub.jsp"></jsp:include>