<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/physician/viewapprovedrequestinfo.js"></script>
<script>
var contextPath = "<%=request.getContextPath()%>";
</script>



<div class="content-body">

	<div class="panel-body">



		<div class="subscribe_top2" id="subscribe_top2">
			<div id="panel-tablesorter" class="panel panel-default">
				<div class="panel-heading bg-white">
					<div class="panel-actions"></div>
					<h3 class="panel-title">Approved Requests</h3>


				</div>


				<div class="table-responsive table-responsive-datatables">

					<div id="RequestSharesDiv">
						<table class="table table-hover table-bordered"
							id="shareedRequestTable">
							<thead>
								<tr>

									<th>Patient Name</th>
									<th>Date of Visit</th>
									<th>Reason for Visit</th>
									<th>Physician Consulted</th>
									<th>Prescription</th>
									<th></th>

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
                      
                      fnLoadApprovedRequests();
                      </script>







<jsp:include page="../footerbluehub.jsp"></jsp:include>