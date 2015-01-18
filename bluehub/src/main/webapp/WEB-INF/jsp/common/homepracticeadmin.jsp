
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>


<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/admin/physicianmanagement.js"></script>


<script>
var contextPath = "<%=request.getContextPath()%>";
</script>

<div class="content-body">



	<form role="form" class="form-horizontal form-bordered"
		name="adminOrganizationForm" id="adminOrganizationForm"
		commandName="adminOrganizationForm" method="POST">
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
				<h3 class="panel-title">Physician List</h3>
			</div>


			<div class="panel-body">
				<div class="table-responsive table-responsive-datatables">
					<table class=" table table-hover table-bordered">
						<thead>
							<tr>
								<th></th>
								<th>Physician Name</th>
								<th>Organization</th>
								<th>Practice</th>

							</tr>
						</thead>

						<tbody>
							<%int i = 0; %>
							<c:forEach var="itemName" varStatus="status" items="${userList}">



								<tr onclick="fnOnclickRadio(<%=i %>)">
									<td><input type="radio" id="practiceid_<%=i %>"
										onclick="fnOnclickRadio(<%=i %>)" name="practiceid"
										value="${itemName.userid}"></td>
									<td><c:out value="${itemName.firstname}" /></td>
									<td><c:out value="${itemName.organizationName}" /></td>
									<td><c:out value="${itemName.practicename}" /></td>

								</tr>

								<%i++; %>
							</c:forEach>




						</tbody>
						
					</table>
				</div>
			</div>


			<div id="searchButton" style="margin: 3%; display: none;">
				<input type="hidden" name="organizationid" id="organizationid"
					value=""> <input type="button" class="btn btn-inverse"
					value="Continue" onclick="loadPhysicianRedirect()">


			</div>


		</div>

	</form>


</div>




<jsp:include page="../footerbluehub.jsp"></jsp:include>