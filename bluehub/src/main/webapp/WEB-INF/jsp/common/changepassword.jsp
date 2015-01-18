<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/common/changePassword.js"></script>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
var contextPath = "<%=request.getContextPath()%>";

if(document.getElementById("changePwdID")){
document.getElementById("changePwdID").style.display = "none";



}

</script>


<style>
.required1 {
	color: #da4f49 !important;
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
			<h3 class="panel-title">Change Password</h3>
		</div>



		<div class="panel-body">
			<form role="form" class="form-horizontal form-bordered"
				id="changePassword" name="changePassword" method="POST">

				<div class="required1" id="exception" style="margin: 0 32%;">
					<c:out value="${EXCEPTION}" />
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">Current
						password</label>
					<div class="col-sm-5">
						<div class="input-group input-group-in">
							<span class="input-group-addon text-silver"></span> <input
								type="password" id="currentPassword" value=""
								onchange="callCheckPassword()" autofocus name="currentPassword"
								class="form-control" placeholder="Current Password" />
						</div>
					</div>
				</div>



				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">New
						Password</label>
					<div class="col-sm-5">
						<div class="input-group input-group-in">
							<span class="input-group-addon text-silver"></span> <input
								type="password" id="password" name="password"
								class="form-control" placeholder="New Password" required />
						</div>
					</div>
				</div>



				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">Retype
						new password</label>
					<div class="col-sm-5">
						<div class="input-group input-group-in">
							<span class="input-group-addon text-silver"></span> <input
								type="password" id="newpassword" name="newpassword"
								class="form-control" placeholder="Retype New Password" required />
						</div>
					</div>
				</div>


				<div id="searchButton"
					style="margin-left: 29%; display: block; border-bottom: 1px solid white;"
					class="panel-body">


					<input type="button" value="Submit" class="btn btn-inverse"
						onclick="pageNavigation();">
				</div>

			</form>
		</div>




	</div>
</div>


<script>
document.getElementById("currentPassword").value="";
</script>



<jsp:include page="../footerbluehub.jsp"></jsp:include>


