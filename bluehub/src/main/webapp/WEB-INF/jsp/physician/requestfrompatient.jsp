<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>
<style>
#success_msg_span1 {
	color: #da4f49 !important;
	margin-left: 32%;
}
</style>


<script> 
var contextPath = "<%=request.getContextPath()%>";
</script>
<script type="text/javascript">
function proceedUploadClinicalInformation() {
	document.getElementById("dropzoneDiv").style.display = "block";
}


function displayUserData(id) {
	document.getElementById("hdnVisitId").value = id;
	document.getElementById('searchButton').style.display = "block";

}

function fnReloadClinicalInformation() {

	window.location.reload(contextPath + "/physician/requestfrompatient.do");

}

function fnClearMsgField() { 
	document.getElementById("success_msg_span1").style.display = "none";
}

function fnSetTimeout(id, time) {
	if (time == undefined)
		time = 3000;
	displayMsgId = id;
	document.getElementById(displayMsgId).style.display = "block";
	//setTimeout('fnClearMsgField()', time);
}


function submitUploadClinicalInformation() {

	var patientId = $('#hdnPhysicianId').val();
	var visitid = $('#hdnVisitId').val();
	if(visitid==""){
		visitid = 0;
	}
	var requestId = $('#hdnRequestId').val();
	if(requestId==""){
		requestId = 0;
	}
	
	$
			.ajax({
				type : "GET",
				url : contextPath
						+ "/administrator/uploadClinicalInformation.do",
				cache : false,
				async : false,
				data : "patientid=" + patientId + "&visitid=" + visitid + "&requestId=" + requestId,
				success : function(response) {

					var parsedJson = $.parseJSON(response);

					if (parsedJson.status == "YES") {

						document.getElementById("success_msg_span1").innerHTML = parsedJson.message;

						fnSetTimeout("success_msg_span1", 3000);

						setTimeout('fnReloadClinicalInformation()', 1000);
					} else {

						document.getElementById("success_msg_span1").innerHTML = parsedJson.message;

						fnSetTimeout("success_msg_span1", 3000);

						
					}

				},
				error : function(e) {
				}
			});

}
function searchPatientVisitRecords(patientid,requestId){
	document.getElementById('searchButton').style.display = "block";
	document.getElementById("hdnRequestId").value = requestId;
	//document.getElementById('visitRecords').style.display="block"; -- new requirement-- uncomment to see old one
	document.getElementById("hdnPhysicianId").value = patientid;
			/* $('#patientVisitTableId').dataTable( {
//		 		"bStateSave": true,
			  	 "processing": true,
			  	 "bJQueryUI": true ,
			   	  "bFilter": false,
			  	  "bDestroy": true,
			  	  "bServerSide": true,
			  	  "sAjaxSource": contextPath + "/administrator/adminsearchpatientvisitrecords.do?patientid="+patientid,
			  	  "bProcessing": true,
			  	   	  
				  "aoColumns": [{"mDataProp":"Patient Name","bSortable": false,
					  
							  "fnRender": function (oObj)  {   
					        	  
					        	  var id = oObj.aData['id'];
					        	  var name = oObj.aData['Patient Name'];
					        	  return '<a href="javascript:displayUserData('
									+ id + ')" >' + name + '</a>'
					          }
						  },
						  {"mDataProp":"DOB","bSortable": false},
			                {"mDataProp":"Visit Date","bSortable": false},
			                {"mDataProp":"Physician","bSortable": false},
			                {"mDataProp":"Reason For Visit","bSortable": false}]
			}); */
		
		}


 function patientRequestList(){
	 
	 	var searchVal = document.getElementById('patientname').value;
		document.getElementById('requestRecords').style.display="block";
		var params = "searchVal=" + searchVal;
		$('#patientrequestVisitTableId').dataTable({
//	 		"bStateSave": true,
		  	 "processing": true,
		  	 "bJQueryUI": true ,
		   	  "bFilter": false,
		  	  "bDestroy": true,
	 	  	"bAutoWidth": false,
		  	  "bServerSide": true,
		  	  "sAjaxSource": contextPath + "/physician/patientrequest.do?"+params,
		  	  "bProcessing": true,
		  	   	  
			  "aoColumns": [{"mDataProp":"Request From","bSortable": false,
				  
						  "fnRender": function (oObj)  {   
				        	  var id = oObj.aData['id'];
				        	  var name = oObj.aData['Request From'];
				        	  var key = oObj.aData['key'];

				        	  var status = oObj.aData['Status'];
				        	  if(status=='Pending'){
				        	  return '<a href="#" onclick = "searchPatientVisitRecords('+id+','+key+')"> '+name+'</a>'
				        	  }else{
				        		  
				        		  return name;
				        	  }
				        	
				          }
					  },{"mDataProp":"Status","bSortable": false}
					  ]
		});
	}
</script>

<div class="content" style="margin-left: 22px;">

	<div class="content-body">

		<div id="panel-typeahead"
			class="panel panel-default sortable-widget-item">
			<div class="panel-heading">
				<div class="panel-actions">
				</div>
				<h3 class="panel-title">Request from Patient</h3>
			</div>

			<div class="panel-body">
				<form role="form" class="form-horizontal form-bordered"
					method="post" commandName="physicianVisitsForm" name="phyVisitForm"
					id="phyVisitForm">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Patient</label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"><i
									class="glyphicon glyphicon-user"></i></span> <input type="text"
									id="patientname" name="patientname" class="form-control"
									placeholder="Patient" onkeyup="patientRequestList()" />
							</div>
						</div>
					</div>

				</form>


			</div>
<input type="hidden" id="hdnRequestId" name="hdnRequestId" />
<input type="hidden" id="hdnPhysicianId" name="hdnPhysicianId" />
<input type="hidden" id="hdnVisitId" name="hdnVisitId" />
			<div id="requestRecords" style="display: none">
				<table class="table table-striped table-bordered"
					id="patientrequestVisitTableId" style="width: 100%;">
					<thead>
						<tr>
							<th>Request From</th>
							<th>Status</th>

						</tr>
					</thead>

					<tbody></tbody>


				</table>
			</div>
			
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

				</tr>
			</thead>

			<tbody></tbody>


		</table>
	</div>
	
	
			<div id="searchButton" style="display: none; margin-left: 29%; border-bottom: 1px solid white;"
				class="panel-body">
				<input type="button" id="uploadbutton" class="btn btn-inverse"
					value="Proceed" onclick="proceedUploadClinicalInformation()">
			</div>
			
			
			
			
			<div id="dropzoneDiv" style="display: none;">

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
				enctype="multipart/form-data">
				<div class="fallback">
					<input name="mydropzone" id="mydropzone" type="file" multiple />


				</div>
			</form>
		</div>



		<span id="success_msg_span1" style="display: none;" onclick="fnClearMsgField()"></span>
		<div id="searchButton1"
			style="display: block; margin-left: 29%; border-bottom: 1px solid white;"
			class="panel-body">
			<input type="button" class="btn btn-inverse" value="Submit"
				onclick="submitUploadClinicalInformation()">

		</div>


	</div>
		</div>
	</div>
</div>

</section>



<script>
        patientRequestList();
              
        </script>

<jsp:include page="../footerbluehub.jsp"></jsp:include>