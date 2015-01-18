
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>




<script>
var contextPath = "<%=request.getContextPath()%>";

</script>


<script>


function functionHippa(){

	 var url="http://www.bluehubhealth.com/hipaaform"; 
	  window.open(url,'','width=700,height=500'); 

	
}

function functionStarted(){

	var url="http://www.bluehubhealth.com/getstarted"; 
	  window.open(url,'','width=700,height=500'); 
	
}


</script>

<div class="content-body">





	<div id="panel-dropzone"
		class="panel panel-default sortable-widget-item">
		<div class="panel-heading">
			<div class="panel-actions">

				
			</div>
			<h3 class="panel-title">Welcome ${pageContext.request.userPrincipal.name}</h3>
		</div>

		<div id="system-stats" class="tab-pane fade active in">


			<div class="row">

			<%--	< div class="col-md-4">
					<div id="overall-users"
						class="panel panel-animated panel-success bg-success">
						<div class="panel-body">
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

							<p class="lead">Visits</p>

							<ul class="list-percentages row">
								<li class="col-xs-4">
									<p class="text-ellipsis">Total Visits</p>
									<p class="text-lg">
										<strong><c:out value="${visits}" /></strong>
									</p>
								</li>
								<li class="col-xs-4">
									<p class="text-ellipsis">Last Visit</p>
									<p class="text-lg">
										<strong><c:out value="${date}" /></strong>
									</p>
								</li>
								
							</ul>
							<p class="helper-block">
							<div
								class="progress progress-xs progress-flat progress-inverse-inverse">
								<div class="progress-bar progress-bar" role="progressbar"
									aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
									style="width: ${visits}%">
									<span class="sr-only">60% Complete (inverse)</span>
								</div>
							</div>
							<!--                                                 <p class="text-ellipsis"><i class="fa fa-caret-up"></i> 63 &nbsp;&nbsp; +8<sup>%</sup> <small>Looks Good!, up from last month</small></p> -->
							</p>
						</div>
					</div>
				</div> --%>
			<%-- 	<div class="col-md-4">
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

							<p class="lead">Tag & note for last visit</p>

							<ul class="list-percentages row">
								
								<li class="col-xs-4">
									<p class="text-ellipsis">Tag</p>
									<p class="text-lg">
										<strong><c:out value="${tag}" /></strong>
									</p>
								</li>
								<li class="col-xs-4">
									<p class="text-ellipsis">Note</p>
									<p class="text-lg">
										<strong><c:out value="${note}" /></strong>
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
				</div> --%>
				
				
				
				<div class="panel-body">
						<form role="form" class="form-horizontal form-bordered"
							name='loginForm' id="loginForm"
							>
							<div class="form-group"
								style="padding-left: 100px; border-bottom: 1px solid white">

								<%-- <div id="ExeptionMsgDiv" style="margin-left: -18%;"
									class="required1" align="center">
									<c:out value="${EXCEPTION}" />
								</div> --%>
							</div>
							<div class="form-group"
								style="padding-left: 100px; border-bottom: 1px solid white">
								
								
								
								<h3> View the release form that we send to your providers to get your information <a href="#" onClick='functionHippa()'> here </a></h3>
								<br><br>
								<h3> View the guide to get started with MyBluehub <a href="#" onClick='functionStarted()'> here </a></h3>
								<%-- <label class="col-sm-3 control-label" for="username">Username<span
									class="required1"> *</span></label>
								<input type="text" style="width: 216px; color: black;"
									id="username" onkeypress="return runScriptUsername(event)"
									name="j_username" class="form-control" placeholder="Username"
									value="${userEmail}" required /> --%>
							</div>






							
							<!-- <div style="margin-left: 36%; border-bottom: 1px solid white;"
								class="panel-body">
								<input type="button" value="Login" class="btn btn-inverse"
									onclick="callLoginValid()">&nbsp;&nbsp;&nbsp;



								<span class="fpassword"><a href="forgotpassword.do">Forgot
										Password?</a></span>

							</div> -->

						</form>
					</div>


				<%-- <div class="col-md-4">
					<div id="overall-orders"
						class="panel panel-animated panel-danger bg-danger">
						<div class="panel-body">
							<div class="panel-actions-fly">
								<button data-refresh="#overall-orders"
									data-error-place="#error-placement" title="refresh"
									class="btn-panel">
									<i class="glyphicon glyphicon-refresh"></i>
								</button>
								<a href="#" title="Go to system stats page" class="btn-panel">
									<i class="glyphicon glyphicon-stats"></i>
								</a>
							</div>

							<p class="lead">Share Requests</p>

							<ul class="list-percentages row">
								<li class="col-xs-4">
									<p class="text-ellipsis">Total</p>
									<p class="text-lg">
										<strong><c:out value="${total}" /></strong>
									</p>
								</li>
								<li class="col-xs-4">
									<p class="text-ellipsis">Approved</p>
									<p class="text-lg">
										<strong><c:out value="${approved}" /></strong>
									</p>
								</li>
								<li class="col-xs-4">
									<p class="text-ellipsis">Pending</p>
									<p class="text-lg">
										<strong><c:out value="${pending}" /></strong>
									</p>
								</li>
							</ul>
							<p class="helper-block">
							<div
								class="progress progress-xs progress-flat progress-inverse-inverse">
								<div class="progress-bar progress-bar" role="progressbar"
									aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
									style="width: ${total}%">
									<span class="sr-only"><c:out value="${total}" /></span>
								</div>
							</div>
							</p>
						</div>
					</div>
				</div> --%>


<!--   Send request -->

		<%-- <div class="col-md-4">
					<div id="overall-users"
						class="panel panel-animated panel-success bg-success">
						<div class="panel-body">
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

							<p class="lead">Fax requests</p>

							<ul class="list-percentages row">
								<li class="col-xs-4">
									<p class="text-ellipsis">Total</p>
									<p class="text-lg">
										<strong><c:out value="${faxtotal}" /></strong>
									</p>
								</li>
								
							</ul>
							<p class="helper-block">
							<div
								class="progress progress-xs progress-flat progress-inverse-inverse">
								<div class="progress-bar progress-bar" role="progressbar"
									aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
									style="width: ${users}%">
								</div>
							</div>
							</p>
						</div>
					</div>
				</div> --%>
<!-- End Send Request -->

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