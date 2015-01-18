<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/admin/adminorganization.js"></script>
<style>
.required1 {
	color: #da4f49 !important;
}
</style>
<script>
var contextPath = "<%=request.getContextPath()%>";
window.onload = loadOrganization

function testFileUpload() {

	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/testfileupload.do",
		cache : false,
		async : false,
		success : function(response) {

		 
		},
		error : function(e) {
			alert('Error: ' + e.responseText);
		}
	});

}

</script>
<div class="content-body">

	<ul class="nav nav-tabs">
		<li class="active"><a href="#tab_a" data-toggle="tab"
			onclick='javascript:document.getElementById("panel-typeahead").style.display = "none"'>Organizations</a></li>
		<li><a href="#tab_b" data-toggle="tab"
			onclick='javascript:document.getElementById("panel-typeahead").style.display = "none"'>Create
				Organization</a></li>

	</ul>

	<div class="tab-content">
		<div class="tab-pane active" id="tab_a">
			<form role="form" class="form-horizontal form-bordered">

				<p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p>
<div style="margin-top: 3%"></div>

				<table id="adminOrganizationtable"
					class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>Name</th>
							<th>Address</th>
							<th>City</th>
							<th>State</th>
							<th>Zipcode</th>
							<th>Fax</th>
							<th>Actions</th>

						</tr>
					</thead>
					<tbody></tbody>
				</table>
				
			</form>

		</div>
		<span class="text-danger" id="deleteOrg" style="margin-left: 30%;"></span>
		<div class="tab-pane" id="tab_b">
			<form role="form" class="form-horizontal form-bordered"
				name="adminOrganizationForm" id="adminOrganizationForm"
				commandName="adminOrganizationForm" method="POST">
				<!--  <form role="form" class="form-horizontal form-bordered" id="changePassword" name="changePassword" method="POST"> -->
				<!-- <h4>Pane B</h4> -->
				<div class="panel-body">
					<div class="required" id="exception"
						style="margin: 0px 0px 0px 108px;">
						<c:out value="${EXCEPTION}" />
					</div>
					<h3>Create New Organization</h3>
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Organization
							Name<span class="required1"> *</span>
						</label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"></span> <input
									type="text" id="organizationname" name="organizationname"
									class="form-control" placeholder="Organization Name" onkeyup=""
									required onchange="checkOrganizationname()" />



							</div>
							<span class="text-danger" id="orgnameValid"
								style="display: none;"></span>
							<!-- /input-group-in -->
						</div>
					</div>


					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Address<span
							class="required1"> *</span></label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"></span> <input
									type="text" id="address" name="address" class="form-control"
									placeholder="Address" onkeyup="" required
									onkeypress="return runSaveAdminOrganization(event)" />



							</div>
						</div>
					</div>


					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">City<span
							class="required1"> *</span></label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"></span> <input
									type="text" id="city" maxlength="20" name="city"
									class="form-control" placeholder="City" onkeyup="" />



							</div>
							<span class="text-danger" id="citynameValid"
								style="display: none;"></span>
						</div>
					</div>


					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">State<span
							class="required1"> *</span></label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"></span> <input
									type="text" id="state" name="state" maxlength="20"
									class="form-control" placeholder="State" onkeyup="" />



							</div>
							<span class="text-danger" id="statenameValid"
								style="display: none;"></span>
						</div>
					</div>


					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Zipcode<span
							class="required1"> *</span></label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"></span> <input
									type="text" id="zipcode" name="zipcode" maxlength="20"
									class="form-control" placeholder="Zipcode" onkeyup="" />



							</div>
							<span class="text-danger" id="zipcodenameValid"
								style="display: none;"></span>
						</div>
					</div>

				<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Fax Number<span
							class="required1"></span></label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"></span> <input
									type="text" id="fax" name="fax" maxlength="20"
									class="form-control" placeholder="Fax Number" onkeyup="" />



							</div>
							<span class="text-danger" id="faxValid"
								style="display: none;"></span>
						</div>
					</div>

				</div>

				<div id="searchButton" style="margin: 3%; display: block;">

					<!-- <input type="button"  class="btn btn-inverse"  value="Submit" onclick="testFileUpload()"> -->
					<input type="button" class="btn btn-inverse" value="Submit"
						onclick="saveAdminOrganization()"> <input type="reset"
						class="btn btn-inverse" value="Reset">


				</div>
			</form>

		</div>

	</div>




	<div id="panel-typeahead"
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
			<h3 class="panel-title">Edit Organization</h3>
		</div>

		<div class="panel-body">
			<form role="form" class="form-horizontal form-bordered"
				name="updateAdminOrganizationForm" id="updateAdminOrganizationForm"
				commandName="updateAdminOrganizationForm">
				<div class="required1" id="exception"
					style="margin: 0px 0px 0px 108px;">
					<c:out value="${EXCEPTION}" />
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">Organization
						Name<span class="required1"> *</span>
					</label>
					<div class="col-sm-5">
						<div class="input-group input-group-in">
							<span class="input-group-addon text-silver"></span> <input
								type="text" id="editorganizationname" name="organizationname"
								class="form-control" placeholder="Organization Name" onkeyup=""
								required onchange="checkUpdateOrganizationname()" /> <input
								type="text" id="hidorganizationname" name="hidorganizationname"
								style="display: none" class="form-control"
								placeholder="Organization Name" onkeyup="" />

						</div>

					</div>
					<span class="text-danger" id="orgnameUpdateValid"
						style="display: none;"></span>
				</div>


				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">Address<span
						class="required1"> *</span></label>
					<div class="col-sm-5">
						<div class="input-group input-group-in">
							<span class="input-group-addon text-silver"></span> <input
								type="text" id="editaddress" name="address" class="form-control"
								placeholder="Address" onkeyup="" required
								onkeypress="return runUpdateAdminOrganization(event)" />



						</div>
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">City<span
						class="required1"> *</span></label>
					<div class="col-sm-5">
						<div class="input-group input-group-in">
							<span class="input-group-addon text-silver"></span> <input
								type="text" id="editcity" maxlength="20" name="city"
								class="form-control" placeholder="City" onkeyup="" />



						</div>
						<span class="text-danger" id="citynameValid"
							style="display: none;"></span>
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">State<span
						class="required1"> *</span></label>
					<div class="col-sm-5">
						<div class="input-group input-group-in">
							<span class="input-group-addon text-silver"></span> <input
								type="text" id="editstate" name="state" maxlength="20"
								class="form-control" placeholder="State" onkeyup="" />



						</div>
						<span class="text-danger" id="statenameValid"
							style="display: none;"></span>
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">Zipcode<span
						class="required1"> *</span></label>
					<div class="col-sm-5">
						<div class="input-group input-group-in">
							<span class="input-group-addon text-silver"></span> <input
								type="text" id="editzipcode" name="zipcode" maxlength="20"
								class="form-control" placeholder="Zipcode" onkeyup="" />



						</div>
						<span class="text-danger" id="zipcodenameValid"
							style="display: none;"></span>
					</div>
				</div>


				<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Fax Number<span
							class="required1"> </span></label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"></span> <input
									type="text" id="editfax" name="fax" maxlength="20"
									class="form-control" placeholder="Fax Number" onkeyup="" />



							</div>
							<span class="text-danger" id="faxValid"
								style="display: none;"></span>
						</div>
					</div>

				</div>




				<div id="searchButton" style="margin: 3%; display: block;">
					<input type="hidden" name="organizationid" id="organizationid"
						value=""> <input type="button" class="btn btn-inverse"
						value="Submit" onclick="updateAdminOrganization()"> <input
						type="reset" class="btn btn-inverse" value="Reset"> <input
						type="button" class="btn btn-inverse" value="Cancel"
						onclick="cancelOrganization()">

				</div>
			</form>

		</div>


	</div>

</div>
<jsp:include page="../footerbluehub.jsp"></jsp:include>