<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/admin/physicianmanagement.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.inputmask.js"></script>
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
			<h3 class="panel-title">Physician Management</h3>
		</div>

		<div class="panel-body">
			<form role="form" class="form-horizontal form-bordered">



				<div id="organizationlabel">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Organization</label>
						<div class="col-sm-5">
							<select class="form-control" id="selectOrganization"
								onchange="loadPractice()">
							</select>
						</div>
					</div>
				</div>

				<div id="practicelabel">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Practice</label>
						<div class="col-sm-5">
							<select class="form-control" id="practiceSelect"
								onchange="loadSearchTable()">
							</select>
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
								id="physicianname" name="physicianname" class="form-control"
								placeholder="Physician Name" onkeyup="loadSearchTable()" />
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>

	<div class="physicianInformations" id="physicianInformations"
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
				<h3 class="panel-title">Physician Details</h3>
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

						<tbody>
							<tr>
								<td><input type="radio"></td>
								<td>Arsath</td>
								<td>Fortis</td>
								<td>Ortho</td>

							</tr>

						</tbody>
						
					</table>
				</div>
			</div>
		</div>
	</div>

	
	<span class="text-danger" id="status" style="margin-left: 30%;" onclick="fnloadOrg()"></span>
	<div id="searchButton" style="margin: 3%; display: none;">

		<button class="btn btn-inverse" disabled="" type="button"
			id="disableButton" onclick="statusDisableActivate()">Disable</button>
		<button class="btn btn-inverse" disabled="" type="button"
			id="enableButton" onclick="statusEnableActivate()">Enable</button>
		<button class="btn btn-inverse" disabled="" type="button" id="flagButton"
			onclick="flagasPracticeAdmin()">Flag as Practice Admin</button>
		<button class="btn btn-inverse" disabled="" type="button" id="unflagButton"
			onclick="unFlagasPracticeAdmin()">Un Flag as Practice Admin</button>
		<button class="btn btn-inverse" disabled="" type="button"
			id="resetPwdBn" onclick="fnResetPassword()">Reset Password</button>

	</div>

</div>


<script>
                	loadOrganization();
                	                
                </script>



<jsp:include page="../footerbluehub.jsp"></jsp:include>