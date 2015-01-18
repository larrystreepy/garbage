<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script>
        var contextPath = "<%=request.getContextPath()%>";
</script>

<style>
#ExeptionMsgDiv {
	color: #da4f49 !important;
}
</style>


<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/physician/requestshareinfo.js"></script>

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
			<h3 class="panel-title">Request Info Of Patient</h3>
		</div>
		<!-- /panel-heading -->
		<div id="ExeptionMsgDiv" style="margin-left: -18%;" class="required1"
			align="center" onclick="fnClearMsgFieldEcxeption()">
			<c:out value="${EXCEPTION}" />
		</div>
		<div class="panel-body">
			<form role="form" class="form-horizontal form-bordered" method="post"
				commandName="RequestInfo" name="RequestInfoPatient"
				id="RequestInfoPatient">
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
					type="hidden" id="hdnPhysicianId" name="physicianId">

				<div class="patientInformations" id="patientInformations"
					style="display: none">
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

				<div id="organizationlabel" style="display: none">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Organization</label>
						<div class="col-sm-5">
							<select class="form-control" id="selectOrganization"
								onchange="loadPractice()">
							</select>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Practice</label>
						<div class="col-sm-5">
							<select id="selectPractice" class="form-control"
								onchange="loadAllPhysician()">

							</select>
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


				<div id="adminVisitButton" style="margin: 3%; display: none;">
					<input type="button" class="btn btn-inverse" id="phyVisitSubmit"
						onclick="fnSendRequestToPatientAndPhysician();"
						value="Send Request" />
				</div>

			</form>
		</div>
	</div>
</div>




<script>
	fnSetTimeout("ExeptionMsgDiv", 3000);
	
	function fnClearMsgFieldEcxeption() { 
		document.getElementById("ExeptionMsgDiv").style.display = "none";
	}
	
	
</script>




<jsp:include page="../footerbluehub.jsp"></jsp:include>