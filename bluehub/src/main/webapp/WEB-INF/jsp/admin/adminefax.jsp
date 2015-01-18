<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/admin/adminefax.js"></script>
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
<input type="hidden" id="hdnPatientId" name="hdnPatientId" />
<input type="hidden" id="hdnVisitId" name="hdnVisitId" />

<input type="hidden" id="hdnCount" name="hdnCount" />

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
				<h3 class="panel-title">EFax Incoming</h3>
			</div>

			<div class="panel-body">

				<form role="form" class="form-horizontal form-bordered">
				
				
					<div id="faxDocuments" style="display: none">
				 
			       </div>
				
					<div style="margin-top: 6%" id="organizationlabel">
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

<span id="success_msg_span" style="display: none;" onclick="fnClearMsgField()"></span>

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
					
					

		
					
					<div id="submitButton" style="margin-left: 29%; border-bottom: 1px solid white;"
				class="panel-body">
				<input type="button" id="uploadbutton" class="btn btn-inverse"
					value="Share Documents" onclick="fnShareFaxDocuments()">
			</div>

				</form>
			</div>


			


 


			
		</div>




</div>


 


 



<script>
          
		searchPatientFaxRecords();
     	loadOrganizations();
         </script>

<jsp:include page="../footerbluehub.jsp"></jsp:include>