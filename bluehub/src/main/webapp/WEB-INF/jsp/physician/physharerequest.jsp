<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script>
        var contextPath = "<%=request.getContextPath()%>";
        </script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/physician/requestshare.js"></script>

<style>
#success_msg_span {
	color: #da4f49 !important;
	margin-left: 32%;
}
</style>

<ul class="nav nav-tabs">
	<li class="active"><a href="#tab_a" data-toggle="tab"
		onclick='javascript:loadShareRequestData()'>Status</a></li>
	<li><a href="#tab_b" data-toggle="tab" onclick=''>Request a
			Share</a></li>

</ul>

<div class="content-body">
	<div class="tab-content">
		<div class="tab-pane active" id="tab_a">

			<div class="panel-body">

				<div class="subscribe_top2" id="subscribe_top2">
					<div id="panel-tablesorter" class="panel panel-default">
						<div class="panel-heading bg-white">
							<div class="panel-actions"></div>
							<h3 class="panel-title">Request Status</h3>

						</div>


						<div class="table-responsive table-responsive-datatables">

							<div id="RequestSharesDiv">
								<table class="table table-hover table-bordered"
									id="shareedRequestTable">
									<thead>
										<tr>

											<th>Patient Name</th>
											<th>Date of Visit</th>
											<th>Reason for Visit</th>
											<th>Physician Consulted</th>
											<th>Prescription</th>
											<th></th>

										</tr>
									</thead>


								</table>

							</div>

						</div>
					</div>
				</div>


			</div>



		</div>
		<div class="tab-pane" id="tab_b">


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
						type="hidden" id="hdnVisitId" name="hdnVisitId">

					<div class="patientInformations" id="patientInformations"
						style="display: none" >

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



					<!-- <div id="shareRequestDiv" style="display: none; margin-top: 4%;">

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Request</label>
							<div class="col-sm-5">
								<select id="requestShareOption"
									onchange="fnLoadRequestShareOption()" data-input=""
									class="form-control">
									<option value="-1">Select a Choice</option>
													                            <option value="1">Get Digital Signature</option>
									<option value="2">Send Request Mail</option>
								</select>
							</div>
						</div>

					</div> -->




					<span id="success_msg_span" onclick="fnClearMsgField()"></span>

					<div id="adminVisitButton" style="margin: 3%; display: none;">
						<input type="button" class="btn btn-inverse" id="phyVisitSubmit"
							onclick="fnRequestingShare();" value="Request a Share" />
					</div>

				</form>
			</div>

		</div>


	</div>

</div>



<script>
     loadShareRequestData();
     loadPatientsTable();
     </script>




<style>
#statusSelect {
	margin-left: 40%;
	margin-top: 3%;
}
</style>
<jsp:include page="../footerbluehub.jsp"></jsp:include>