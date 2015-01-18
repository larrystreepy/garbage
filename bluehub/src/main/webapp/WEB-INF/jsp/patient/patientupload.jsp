<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/admin/adminorganization.js"></script>
<script>
var contextPath = "<%=request.getContextPath()%>";

function onloadVisitList(){
	

	document.getElementById('visitRecords').style.display="block";


	$('#patientVisitTableId').dataTable( {
// 		"bStateSave": true,
	  	 "processing": true,
	  	 "bJQueryUI": true ,
	   	  "bFilter": false,
	  	  "bDestroy": true,
	  	  "bServerSide": true,
	  	  "sAjaxSource": contextPath + "/administrator/loadalllist.do",
	  	  "bProcessing": true,
	  	   	  
		  "aoColumns": [{"mDataProp":"Patient Name","bSortable": false,
			  
					  "fnRender": function (oObj)  {   
			        	  
			        	  var id = oObj.aData['id'];
			        	  var name = oObj.aData['Patient Name'];
			        	  return '<a href="javascript:displayUserData('+id+')" >'+name+'</a>'
			          }
				  },
				  {"mDataProp":"DOB","bSortable": false},
	                {"mDataProp":"Visit Date","bSortable": false},
	                {"mDataProp":"Physician","bSortable": false},
	                {"mDataProp":"Reason For Visit","bSortable": false},
	                {"mDataProp":"Tag","bSortable": false}]
	});

	
}

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

 #success_msg_span2 {
 color: #da4f49 !important;
 margin-left: 2%;
}
#patientDetails {
	margin-top: 10%;
}
</style>
<input type="hidden" id="hdnPhysicianId" name="hdnPhysicianId" />
<input type="hidden" id="hdnVisitId" name="hdnVisitId" />

<div class="content-body">

	<div id="uploadClinicalInfoDiv">

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
				<h3 class="panel-title">Upload Clinical Information</h3>
			</div>

			<div class="panel-body">

				<form role="form" class="form-horizontal form-bordered">

				</form>
			</div>


			<span id="success_msg_span" style="display: none;"></span>



			<div id="visitRecords" style="display: none">
				<table class="table table-striped table-bordered"
					id="patientVisitTableId">
					<thead>
						<tr>
							<th>Patient Name</th>
							<th>DOB</th>
							<th>Visit Date</th>
							<th>Physician</th>
							<th>Reason For Visit</th>
							<th>Tag</th>
						</tr>
					</thead>

					<tbody></tbody>


				</table>
			</div>


			<div id="searchButton"
				style="display: none; margin-left: 29%; border-bottom: 1px solid white;"
				class="panel-body">
				<input type="button" id="uploadbutton" class="btn btn-inverse"
					value="Proceed" onclick="proceedUploadClinicalInformation()">
			</div>
		</div>


	</div>


</div>


<div id="dropzoneDiv1" style="display: none;">

	<div id="panel-typeahead"
		class="panel panel-default sortable-widget-item">

		<div class="panel-body">

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
						<button data-close="#panel-dropzone" title="close"
							class="btn-panel">
							<i class="fa fa-times"></i>
						</button>
					</div>
					<h3 class="panel-title">Dropzone</h3>
				</div>

				<form action="<%=request.getContextPath()%>/uploadfile.do"
					data-input="dropzone" class="dropzone" id="dropBox" name="dropBox"
					enctype="multipart/form-data">
					<div class="fallback">
						<input name="mydropzone1" id="mydropzone1" type="file" multiple />

					</div>
				</form>
			</div>



		</div>
	</div>

</div>


<div class="content-body">
	<div id="dropzoneDiv">

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
					<button data-close="#panel-dropzone" title="close"
						class="btn-panel">
						<i class="fa fa-times"></i>
					</button>
				</div>
				<h3 class="panel-title">Upload Documents</h3>
			</div>

			<form action="<%=request.getContextPath()%>/uploadfile.do"
				data-input="dropzone" class="dropzone" id="dropBox" name="dropBox"
				enctype="multipart/form-data" accept="application/pdf" acceptedMimeTypes="application/pdf" acceptedFiles="application/pdf">
				<div class="fallback">
					<input name="mydropzone" id="mydropzone" type="file" acceptedFiles="application/pdf" acceptedMimeTypes="application/pdf" />
					


				</div>
			</form>
		</div>

		<span id="success_msg_span1" style="display: none;"  onclick="fnClearMsgField()"></span>
		<span id="success_msg_span2" style="display: block;">* Upload .pdf Files Only.</span>
		
		<div id="searchButton1"
			style="display: block; margin-left: 29%; border-bottom: 1px solid white;"
			class="panel-body">
			<input type="button" class="btn btn-inverse" value="Upload Documents"
				onclick="submitUploadClinicalInformation()">
		</div>



</div>



<script>
         document.getElementById("uploadClinicalInfoDiv").style.display = "none";
     	
     	document.getElementById("dropzoneDiv").style.display = "block";
     	
//      	document.getElementById("uploadbutton").style.display = "none";

        onloadVisitList();
         </script>

<jsp:include page="../footerbluehub.jsp"></jsp:include>