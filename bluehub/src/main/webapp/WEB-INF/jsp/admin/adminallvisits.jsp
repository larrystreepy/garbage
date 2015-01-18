<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script>
        var contextPath = "<%=request.getContextPath()%>";
        
        
      function showDetails(id) {
    	  loadAdminAllVisits(1,id,250);
      }
      
      function loadAllVisitsByOrg(){
    	  document.getElementById('viewPhyAllVisit').style.display = "none";
    	  var orgId = document.getElementById("selectOrganization").value;
    	  loadAdminAllVisits(2,0,orgId);
      }      
      function loadAdminOrganization(){
          $.ajax({
  			type : "GET",
  			url : contextPath + "/administrator/adminorganizations.do",
  			// data : "userGroup=" + userGroup,
  			cache : false,
  			async : false,
  			success : function(response) {

  				//var myRoleHidden = $('#myRoleHidden').val();
  				var parsedJson = $.parseJSON(response);
  				document.getElementById("selectOrganization").innerHTML = "";

  				var combo = document.getElementById("selectOrganization");

  				var defaultOption = document.createElement("option");
  			    defaultOption.text = 'Select';
  			    defaultOption.value = '';
  			    combo.add(defaultOption);
  			    
  				for (var i = 0; i < parsedJson.length; i++) {
  					var option = document.createElement("option");
  					option.text = parsedJson[i].organizationname;
  					option.value = parsedJson[i].id;
  					combo.add(option);
  				}
  				//loadPractice();
  			},
  			error : function(e) {
  				alert('Error: ' + e.responseText);
  			}
  		});
          
          }
      
  	function jsFunction() {
		var x = document.getElementById("mySelect").value;
		if (x == 1) {
			document.getElementById("searchOrganizationDiv").style.display = "none";
			document.getElementById('searchPatientDiv').style.display = "block";
			document.getElementById('viewPhyAllVisit').style.display = "none";
			document.getElementById('phyAllVisitsTable').style.display = "none";			
		}else if (x == 2) {
			document.getElementById("searchOrganizationDiv").style.display = "block";
			document.getElementById('searchPatientDiv').style.display = "none";			
			document.getElementById('patientDetails').style.display = "none";
			document.getElementById('viewPhyAllVisit').style.display = "none";
			document.getElementById('phyAllVisitsTable').style.display = "none";
		}
	}      

	
	
	function loadPatientsTables(){
		if(document.getElementById('uploadbutton')){
		document.getElementById('uploadbutton').style.display = "none";
		}
		document.getElementById('patientDetails').style.display = "block";
	  	var searchPatient=document.getElementById('patientname').value;
	  	var orgid=-1;
	  	var params = "searchPatient=" + searchPatient+"&orgid="+orgid;
		$('#patientLoadTable').dataTable( {
			 
		  	 "processing": true,
		  	 "bJQueryUI": true ,
		   	  "bFilter": false,
		  	  "bDestroy": true,
//	 	  	"bAutoWidth": true,
//		  	"sDom": '<"top"i>rt<"bottom"flp><"clear">',
		  	  "bServerSide": true,
		  	  "sAjaxSource": contextPath + "/administrator/adminpatientdetails.do?"+params,
		  	  "bProcessing": true,
			  "aoColumns": [{"mDataProp":"Patient Name","bSortable": false,
				  
						  "fnRender": function (oObj)  {   
				        	  
				        	  var id = oObj.aData['id'];
				        	  var name = oObj.aData['Patient Name'];
// 				        	  var org = oObj.aData['Organization'];
// 				        	  var practice = oObj.aData['Practice'];
				        	  
				        	 return '<a href="javascript:showDetails('+id+')" >'+name+'</a>'
				          }
					  },
					  {"mDataProp":"Last Name","bSortable": false},
					  {"mDataProp":"DOB","bSortable": false},
					  {"mDataProp":"SSN","bSortable": false},
					  {"mDataProp":"Contact Number","bSortable": false},
					  {"mDataProp":"Address","bSortable": false}
			                     
			                 ]
			                
			                
//			  "aaSorting": [[ 2, 'desc' ]]
			                
		
			  
		});
		
	 
		}
	
	function loadPatientsTableq() {

		//document.getElementById('physicianDetails').style.display = "none";
		document.getElementById('patientDetails').style.display = "block";

		var content = '';
		content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
		content += '<thead>';
		content += '<th>Patient Name</th>';
		content += '<th>DOB</th>';
		content += '<th>Address</th>';
		
		content += '</thead>';
		document.getElementById('patientDetails').innerHTML = content;

		var searchPatient=document.getElementById('patientname').value;
		$.ajax({
					type : "GET",
					url : contextPath + "/administrator/adminpatientdetails.do",
					data : "searchPatient=" + searchPatient,
					cache : false,
					async : false,
					success : function(response) {

						if(response=="null"){
							content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

							/* content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
								content += '</table>';
								document.getElementById('patientDetails').innerHTML = content;
								document.getElementById('searchButton').style.display="none";
						}else{

							var parsedJson = $.parseJSON(response);

							if (parsedJson != null) {

								for (var i = 0; i < parsedJson.length; i++) {

									
									content = content + '<tr onclick="showDetails('+parsedJson[i].userid+')"" id="patientRadio" value='+parsedJson[i].userid+' name="patientRadio">' ;									
									
									content += '<td id="agencytd'+i+'"  class="user_list_link">'
											+ parsedJson[i].firstname + '</td>';
									content += '<td id="emailtd'+i+'"  class="user_list_link">'
											+ parsedJson[i].dateofbirth + '</td>';
									content += '<td id="addresstd'+i+'"  class="user_list_link">'
											+ parsedJson[i].address + '</td>';
									
									content = content + '</tr>';
								}

								
								content += '</table>';
								document.getElementById('patientDetails').innerHTML = content;
							}
							
						}

						

					},
					error : function(e) {
						 //alert('Error: ' + e.responseText);
					}
				});

	}
	
		function fnSetPhysicianvalue(id){			   			
    		document.getElementById("hdnPhysicianId").value = id;
    		//document.getElementById("allVisitsDiv").style.display="block";    		
    	}        

		function fnSetPatientvalue(id){			   			
    		document.getElementById("hdnPhysicianId").value = id;
    		//document.getElementById("allVisitsDiv").style.display="block";    		
    	}        

        </script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/physician/physician.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/admin/adminorganization.js"></script>

<div class="content" style="margin-left: 1%;">

	<div class="content-body">
		<!-- APP CONTENT
                    ================================================== -->

		<div id="panel-typeahead"
			class="panel panel-default sortable-widget-item">
			<div class="panel-heading">
				<div class="panel-actions">
					
				</div>
				
				<h3 class="panel-title">All Visits</h3>
			</div>
			

			<div>
				<input type="hidden" id="hiddenPhysicianVisitId"
					name="hiddenPhysicianVisitId" value="" />
			</div>

			<div class="panel-body">
				<form role="form" class="form-horizontal form-bordered">

					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Search
							Criteria</label>
						<div class="col-sm-5">
							<select id="mySelect" onchange="jsFunction()"
								class="form-control">
								<option value="0">Select</option>
								<option value="1">Search by Patient</option>
								<option value="2">Search by Organization</option>
							</select>
						</div>
					</div>

					<div id="searchPatientDiv" style="display: none;">
						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Patient
								Name</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="patientname" name="patientname" class="form-control"
										placeholder="Patient Name" onkeyup="loadPatientsTables()" />
								</div>
							</div>
						</div>
					</div>

					<div id="searchOrganizationDiv" style="display: none;">
						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Organization</label>
							<div class="col-sm-5">
								<select class="form-control" id="selectOrganization"
									onchange="loadAllVisitsByOrg()">
								</select>
							</div>
						</div>
					</div>


					<div class="patientInformations" style="display: none"
						id="patientDetails">
						<table id="patientLoadTable"
							class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>Patient Name</th>
									<th>Last Name</th>
									<th>DOB</th>
									<th>SSN</th>
									<th>Contact Number</th>
									<th>Address</th>

								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>

					<input type="hidden" id="hdnPatientId" name="patientId"> <input
						type="hidden" id="hdnPhysicianId" name="physicianId">



					<div class="panel-body">
						<div class="table-responsive table-responsive-datatables"
							id="phyAllVisitsTable" style="margin-top: 2%;"></div>
					</div>


					<div id="viewPhyAllVisit" style="display: none;">
						<h3>View Visit</h3>
						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Patient
								Name</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="vistAllPatientName" name="vistAllPatientName" readonly
										value="" class="form-control" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Date
								of Visit</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="date_of_visit" name="date_of_visit" value="" readonly
										class="form-control" />
								</div>
								
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Reason
								for Visit</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="reason_for_visit" name="reason_for_visit" readonly
										value="" class="form-control" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Physician
								Name</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="vistAllPhysicianName" name="vistAllPhysicianName" readonly
										value="" class="form-control" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Prescription
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span>
									<textarea rows="10" cols="50" name="prescription" value=""
										readonly id="prescription" style="resize: none;"></textarea>
								</div>
							</div>
						</div>

						<input type="hidden" name="tag" id="tag" value="">

					</div>

				</form>

			</div>

		</div>

	</div>
</div>


</section>

<script>
            loadAdminOrganization();
        </script>


<jsp:include page="../footerbluehub.jsp"></jsp:include>