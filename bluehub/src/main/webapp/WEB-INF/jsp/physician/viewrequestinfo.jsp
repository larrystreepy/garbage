<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/physician/viewrequestshareinfo.js"></script>

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
			<!-- <h3 class="panel-title">View Request Info Behalf Of Patient</h3> -->
			<h3 class="panel-title">View Requests of Information on Behalf of Patient</h3>
		</div>







		<div class="physicianInformations" id="physicianDetails"
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
				</div>

				<div class="panel-body">
					<div class="table-responsive table-responsive-datatables">
						<table id="physicianDetails1"
							class="tablesorter table table-hover table-bordered">
							<thead>
								<tr>
									<th></th>
									<th>Physician Name</th>
									<th>Organization</th>
									<th>Practice</th>

								</tr>
							</thead>



						</table>
					</div>
				</div>
			</div>
		</div>


	</div>


</div>

<script>


loadRequestPhysiciansList();
fnSample();

</script>

<jsp:include page="../footerbluehub.jsp"></jsp:include>