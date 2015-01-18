<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/admin/adminpractice.js"></script>
<script>
var contextPath = "<%=request.getContextPath()%>";


</script>

<style>
.required1 {
	color: #da4f49 !important;
}
</style>
<div class="content-body">

	<ul class="nav nav-tabs">
		<li class="active"><a href="#tab_a" data-toggle="tab"
			onclick='javascript:document.getElementById("panel-typeahead").style.display = "none"'>Practices</a></li>
		<li><a href="#tab_b" data-toggle="tab"
			onclick='javascript:document.getElementById("panel-typeahead").style.display = "none"'>Create
				New Practice</a></li>

	</ul>

	<div class="tab-content">
		<div class="tab-pane active" id="tab_a">
			<form role="form" class="form-horizontal form-bordered">
				<!-- <h4>Pane A</h4> -->


				<p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p>
<div style="margin-top: 3%"></div>

				<table id="adminPracticetable"
					class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>Organization</th>
							<th>Practice</th>
							<th>Address</th>
							<th>City</th>
							<th>State</th>
							<th>Zipcode</th>
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
				name="adminPracticeForm" id="adminPracticeForm"
				commandName="adminPracticeForm">
				<!--  <form role="form" class="form-horizontal form-bordered" id="changePassword" name="changePassword" method="POST"> -->
				<!-- <h4>Pane B</h4> -->
				<div class="panel-body">
					<div class="required1" id="exception"
						style="margin: 0px 0px 0px 108px;">
						<c:out value="${EXCEPTION}" />
					</div>
					<h3>Create New Practice</h3>

					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Organization<span
							class="required1"> *</span></label>

						<div class="col-sm-5">
							<select class="form-control" id="selectOrganization" onchange="checkPracticeName()">
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Practice
							Name<span class="required1"> *</span>
						</label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"></span> <input
									type="text" id="practicename" name="practicename"
									class="form-control" placeholder="Practice Name"
									onchange="checkPracticeName()" required
									onkeypress="return runSaveAdminPractice(event)" />



							</div>
							<span class="text-danger" id="orgnameValid"
								style="display: none;"></span>
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

				</div>

				<div id="searchButton" style="margin: 3%; display: block;">

					<input type="button" class="btn btn-inverse" value="Submit"
						onclick="saveAdminPractice()"> <input type="reset"
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
			<h3 class="panel-title">Edit Practice</h3>
		</div>

		<div class="panel-body">
			<form role="form" class="form-horizontal form-bordered"
				name="updateAdminPracticeForm" id="updateAdminPracticeForm">



				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">Organization
						Name<span class="required1"> *</span>
					</label>
					<div class="col-sm-5">
						<div class="input-group input-group-in">
							<span class="input-group-addon text-silver"></span> <input
								type="text" id="editorganizationname"
								name="editorganizationname" readonly value=""
								class="form-control" onkeyup="" required />
						</div>

						
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">Practice
						Name<span class="required1"> *</span>
					</label>
					<div class="col-sm-5">
						<div class="input-group input-group-in">
							<span class="input-group-addon text-silver"></span> <input
								type="text" id="editpracticename" name="practicename"
								class="form-control" placeholder="Practice Name" onkeyup=""
								required onkeypress="return runUpdateAdminPractice(event)" />
						</div>

						
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">Address<span
						class="required1"> *</span></label>
					<div class="col-sm-5">
						<div class="input-group input-group-in">
							<span class="input-group-addon text-silver"></span> <input
								type="text" id="editaddress" name="address" class="form-control"
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






				<div id="searchButton" style="margin: 3%; display: block;">
					<input type="hidden" name="practiceid" id="practiceid" value="">
					<input type="button" class="btn btn-inverse" value="Submit"
						onclick="updateAdminPractice()"> <input type="reset"
						class="btn btn-inverse" value="Reset"> <input
						type="button" class="btn btn-inverse" value="Cancel"
						onclick="cancelPractice()">


				</div>
			</form>
		</div>



	</div>

	<script>
               loadAdminPracticeList();
               loadOrganization();
                    </script>

</div>
<jsp:include page="../footerbluehub.jsp"></jsp:include>