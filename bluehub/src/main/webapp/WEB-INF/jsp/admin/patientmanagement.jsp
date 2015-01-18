<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>


<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/admin/patientmanagement.js"></script>


<script>


var contextPath = "<%=request.getContextPath()%>";

$(function() {	
	if($("#ssns")){
		$("#ssns").inputmask({
			"mask" : "***-**-****"
		});
		
		$("#ssns").ForceNumericOnly();
	}
});


	


</script>
<div class="content-body">
	<!-- APP CONTENT
                    ================================================== -->

	<input type="hidden" id="hdnStatusId">
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
			<h3 class="panel-title">Patient Management</h3>
		</div>

		<div class="panel-body">
			<form role="form" class="form-horizontal form-bordered"
				name="patientManage" id="patientManage">
				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">Search
						Criteria</label>
					<div class="col-sm-5">

						<select id="mySelect" onchange="changeSearch()"
							class="form-control">
							<option value="-1">Select a Choice</option>
							<option value="1">Search by First Name</option>
							<!-- <option value="2">Search by Physician</option> -->
							<option value="3">Search by Last Name</option>
							<option value="4">Search by DOB</option>
							<option value="5">Search by SSN</option>
						</select>



					</div>
				</div>
				<div id="firstName" style="display: none">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Search
							by First Name</label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"><i
									class="glyphicon glyphicon-user"></i></span> <input type="text"
									id="firstNames" name="firstNames" class="form-control"
									placeholder="First Name" onkeyup="loadSearchTable()" />
							</div>
						</div>
					</div>
				</div>
				<div id="lastName" style="display: none">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Last
							Name</label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"><i
									class="glyphicon glyphicon-user"></i></span> <input type="text"
									id="lastNames" name="lastNames" class="form-control"
									placeholder="Last Name" onkeyup="loadSearchTable()" />
							</div>
						</div>
					</div>
				</div>
				<div id="dob" style="display: none">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">DOB</label>
						<div class="col-sm-5">
							<div class="input-group input-group-in date"
								data-input="datepicker" data-format="yyyy/mm/dd">
								<input type="text" class="form-control" id="dobs"
									placeholder="DOB" onchange="loadSearchTable()" /> <span
									class="input-group-addon text-silver"><i
									class="fa fa-calendar"></i></span>
							</div>
						</div>
					</div>
				</div>
				<div id="searchssn" style="display: none">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">SSN</label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"><i
									class="glyphicon glyphicon-user"></i></span> <input type="text"
									id="ssns" name="ssns" class="form-control" placeholder="SSN"
									maxlength="9" onkeyup="loadSearchTable()" />
							</div>
						</div>
					</div>
				</div>
		</div>

		<div class="patientInformations" id="patientInformations"></div>

		<span class="text-danger" id="status" style="margin-left: 30%;" onclick="fnloadOrg()"></span>
		<div id="searchButton" style="margin: 3%; display: none;">

			<button class="btn btn-inverse" disabled="" type="button"
				id="disableButton" onclick="statusDisableActivate()">Disable</button>
			<button class="btn btn-inverse" disabled="" type="button"
				id="enableButton" onclick="statusEnableActivate()">Enable</button>
			<button style="display: none;" class="btn btn-inverse" disabled=""
				type="button" id="resetPwdBn" onclick="fnResetPassword()">Reset
				Password</button>

		</div>

	</div>


	<script>
                loadSearchTable();
                </script>
	<jsp:include page="../footerbluehub.jsp"></jsp:include>