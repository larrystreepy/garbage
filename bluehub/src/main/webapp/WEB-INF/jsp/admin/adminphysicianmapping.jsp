<jsp:include page="../headerbluehub.jsp"></jsp:include>


<link media="screen" rel="stylesheet"
	href="<%=request.getContextPath()%>/auto/hautocomplete.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/admin/physicianmapping.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/auto/autocomplete.js"></script>


<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
#success_msg_span {
	color: #da4f49 !important;
	margin-left: 32%;
}

#success_msg_span1 {
	color: #da4f49 !important;
	margin-left: 26%;
	margin-top: 4%;
	width: 14em;
}

.del_row {
	background: url("../images/DeleteRow.png") no-repeat scroll 0 0
		rgba(0, 0, 0, 0);
	border: 0 none;
	cursor: pointer;
	margin: 6px 0 2px !important;
	width: 18px;
}

.editIcon {
	background: url("../images/editIcon.png") no-repeat scroll 0 0
		rgba(0, 0, 0, 0);
	border: 0 none;
	cursor: pointer;
	margin: 6px 0 2px !important;
	width: 18px;
}
</style>


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
			<h3 class="panel-title">Physician Mapping</h3>
		</div>

		<table id="DataTables_Table_1"
			class="table table-striped table-bordered">
			<thead>
				<tr>

					<th>Physician Name</th>
					<th>Organization</th>
					<th>Practice</th>
					<th>Actions</th>
				</tr>
			</thead>

			<tbody></tbody>


		</table>




		<div id="organizationTable"></div>



		<div class="panel-body">




			<form role="form" class="form-horizontal form-bordered" action=""
				name="assignPhysicianForm" id="assignPhysicianForm"
				commandName="physicianMappingForm" method="POST">


				<div class="form-group">
					<span id="success_msg_span" onclick="fnClearMsgField()"></span>
				</div>

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
						<select id="selectPractice" class="form-control">


						</select>
					</div>



				</div>


				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">Physician
						Name</label>
					<div class="col-sm-5">
						<div class="input-group input-group-in">
							<span class="input-group-addon text-silver"></span> <input
								type="text" id="physicianname" name="physicianname"
								class="form-control" placeholder="Physician Name" onkeyup="" />
							<input type="hidden" id="hdnMappingId" name="hdnMappingId"></input>
						</div>
					</div>
					<span id="success_msg_span1" onclick="fnClearMsgField()"></span>
				</div>



				<div id="searchButton" style="margin: 3%; display: block;">

					<input type="button" class="btn btn-inverse" value="Submit"
						onclick="savePhysicianMapping()"> <input type="button"
						class="btn btn-inverse" value="Clear"
						onclick="clearPhysicianMapping()">


				</div>

			</form>

		</div>

	</div>
</div>












<script>
var contextPath = "<%=request.getContextPath()%>";


fnLoadOrganization();

// sample();
fnLoadhysicianmapping();

fnLoadPhysicians();

clearPhysicianMapping();
//physicianname

</script>


<jsp:include page="../footerbluehub.jsp"></jsp:include>