<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/physician/viewrequestshareinfo.js"></script>
<script>
var contextPath = "<%=request.getContextPath()%>";
</script>

<style>
#success_msg_span {
	color: #da4f49 !important;
	margin-left: 32%;
}

#success_msg_span1 {
	color: #da4f49 !important;
	margin-left: 32%;
}

#patientDetails {
	margin-top: 10%;
}
</style>

<input type="hidden" id="hdnPatientId" name="hdnPatientId"
	value="${patientId}">

<input type="hidden" id="hdnRequestId" name="hdnRequestId"
	value="${requestId}">

<input type="hidden" id="hdnPatientName" name="hdnPatientName"
	value="${patientName}">






<div id="panel-dropzone"
	class="panel panel-default sortable-widget-item">
	<div class="panel-heading">
		<div class="panel-actions">
			<button data-refresh="#panel-dropzone" title="refresh"
				class="btn-panel">
				<i class="fa fa-refresh"></i>
			</button>
			<button data-expand="#panel-dropzone" title="expand"
				class="btn-panel">
				<i class="fa fa-expand"></i>
			</button>
			<button data-collapse="#panel-dropzone" title="collapse"
				class="btn-panel">
				<i class="fa fa-caret-down"></i>
			</button>
			<button data-close="#panel-dropzone" title="close" class="btn-panel">
				<i class="fa fa-times"></i>
			</button>
		</div>
		<h3 class="panel-title">Upload Documents To ${patientName}</h3>
	</div>

	<form action="<%=request.getContextPath()%>/uploadfile.do"
		data-input="dropzone" class="dropzone" id="dropBox" name="dropBox"
		enctype="multipart/form-data">
		<div class="fallback">
			<input name="mydropzone" id="mydropzone" type="file" multiple />


		</div>
	</form>
</div>

<span id="success_msg_span1" style="display: none;"></span>
<div id="searchButton1"
	style="display: block; margin-left: 29%; border-bottom: 1px solid white;"
	class="panel-body">
	<input type="button" class="btn btn-inverse" value="Submit"
		onclick="submitUploadClinicalInformation()">

</div>










<jsp:include page="../footerbluehub.jsp"></jsp:include>