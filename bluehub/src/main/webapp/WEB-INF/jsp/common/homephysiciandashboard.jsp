
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>





<script>
var contextPath = "<%=request.getContextPath()%>";
</script>

<div class="content-body">




	<div id="panel-dropzone"
		class="panel panel-default sortable-widget-item">
		<div class="panel-heading">
			<div class="panel-actions">

				<button data-expand="#panel-dropzone" title="expand"
					class="btn-panel">
					<i class="fa fa-expand"></i>
				</button>
				<button data-collapse="#panel-dropzone" title="collapse"
					class="btn-panel">
					<i class="fa fa-caret-down"></i>
				</button>
				
			</div>
			<h3 class="panel-title">Dashboard</h3>
		</div>

		<div id="system-stats" class="tab-pane fade active in">

			<div class="row">

				<div class="col-md-4">
					<div id="overall-users"
						class="panel panel-animated panel-success bg-success">
						<div class="panel-body" style="padding: 0px">
							<div class="panel-actions-fly">
								<button data-refresh="#overall-users"
									data-error-place="#error-placement" title="refresh"
									class="btn-panel">
									<i class="glyphicon glyphicon-refresh"></i>
								</button>
								<a href="#" title="Go to system stats page" class="btn-panel">
									<i class="glyphicon glyphicon-stats"></i>
								</a>
							</div>

							<p class="lead">Users</p>

							<ul class="list-percentages row">
								<li class="col-xs-4">
									<p class="text-ellipsis1">Total Patients</p>
									<p class="text-lg">
										<strong><c:out value="${users}" /></strong>
									</p>
								</li>
								<li class="col-xs-4">
									<p class="text-ellipsis1">Outstanding Requests</p>
									<p class="text-lg">
										Patient:<strong><c:out value="${totRequests}" /></strong>
									</p>
									
									<p class="text-lg">
										Physician:<strong><c:out value="${totRequestPhysician}" /></strong>
									</p>
								</li>
							</ul>
							<p class="helper-block">
							<div
								class="progress progress-xs progress-flat progress-inverse-inverse">
								<div class="progress-bar progress-bar" role="progressbar"
									aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
									style="width: 0%">
								</div>
							</div>
							</p>
						</div>
					</div>
				</div>

				<div class="col-md-4">
					<div id="overall-visitor"
						class="panel panel-animated panel-primary bg-primary">
						<div class="panel-body">
							<div class="panel-actions-fly">
								<button data-refresh="#overall-visitor"
									data-error-place="#error-placement" title="refresh"
									class="btn-panel">
									<i class="glyphicon glyphicon-refresh"></i>
								</button>
								<a href="#" title="Go to system stats page" class="btn-panel">
									<i class="glyphicon glyphicon-stats"></i>
								</a>
							</div>

							<p class="lead">Patient Visit Details</p>

							<ul class="list-percentages row">
								<li class="col-xs-4">
									<p class="text-ellipsis">Total Visits</p>
									<p class="text-lg">
										<strong><c:out value="${totalVisits}" /></strong>
									</p>
								</li>
							</ul>
							<p class="helper-block">
							<div
								class="progress progress-xs progress-flat progress-inverse-inverse">
								<div class="progress-bar progress-bar" role="progressbar"
									aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
									style="width: 0%">
									<span class="sr-only">60% Complete (inverse)</span>
								</div>
							</div>
							</p>
						</div>
					</div>
				</div>

			</div>

		</div>
	</div>




</div>

<style>
.col-md-4 {
	margin-left: 3%;
	width: 29.333%;
}

.row {
	margin-top: 2%;
}
</style>


<jsp:include page="../footerbluehub.jsp"></jsp:include>