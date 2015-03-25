<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/admin/adminorganization.js"></script>
	<script type="text/javascript"
				src="<%=request.getContextPath()%>/js/patient/patientshare.js"></script>
<style>
.required1 {
	color: #da4f49 !important;
}
</style>
<script>
	var contextPath = "<%=request.getContextPath()%>";
</script>

<script>


</script>
<div class="content-body">

<input type="hidden" id="hdnCount">
<input type="hidden" id="hdnShareType">

	<ul class="nav nav-tabs">
		<li id="tabALi" class="active"><a href="#tab_a" data-toggle="tab"
			onclick='showTabA();'>Requests Pending</a></li>
		<li id="tabBLi"><a href="#tab_b" data-toggle="tab"
			onclick='showTabB(); '>Share Clinical Information</a></li>
	</ul>

	<div class="tab-content">
		<div class="tab-pane active" id="tab_a">
			<!-- <h4>Pane A</h4> -->


			<div class="panel-body">
				<form role="form" class="form-horizontal form-bordered">

					<div class="table-responsive table-responsive-datatables"
						id="requestPendingTableDiv"></div>

					<div id="patientPendingShareDiv"
						style="display: none; margin-top: 3%;">

						<div id="panel-typeahead"
							class="panel panel-default sortable-widget-item">
							<div class="panel-heading">
								<div class="panel-actions">
									<!--                                 <button data-expand="#panel-typeahead" title="expand" class="btn-panel">
		                                    <i class="fa fa-expand"></i>
		                                </button>
		                                <button data-collapse="#panel-typeahead" title="collapse" class="btn-panel">
		                                    <i class="fa fa-caret-down"></i>
		                                </button> -->
									<!-- <button data-close="#panel-typeahead" title="close" class="btn-panel">
		                                    <i class="fa fa-times"></i>
		                                </button> -->
								</div>
								<!-- /panel-actions -->
								<h3 class="panel-title">Share Clinical Information</h3>
							</div>
							<!-- /panel-heading -->

							<div class="form-group" style="margin: 1% 0% 0% 0%;">
								<label class="col-sm-3 control-label" for="typeahead-local">Patient
									Name :</label>
								<div class="col-sm-5">
									<label style="text-align: left; margin-left: 1%; width: 50%;"
										class="col-sm-3 control-label" id="pendingSharePatientName"
										for="typeahead-local"></label>
								</div>
								<!--/cols-->
							</div>

							<div class="form-group" style="margin: 1% 0% 0% 0%;">
								<label class="col-sm-3 control-label" for="typeahead-local">DOB
									:</label>
								<div class="col-sm-5">
									<label style="text-align: left; margin-left: 1%;"
										class="col-sm-3 control-label" id="pendingsharePatientDob"
										for="typeahead-local"></label>
								</div>
								<!--/cols-->
							</div>

							<h3 style="margin-left: 1%;">Document List</h3>

							<div class="panel-body">
								<div class="table-responsive table-responsive-datatables"
									id="pendingshareDocumentDiv" style="margin-left: 1%;"></div>
							</div>

							<div id="searchButton" style="margin: 0% 0% 1% 3%;">
								<input type="button" value="Share" class="btn btn-inverse"
									onclick="pendingSubmitSharePatient()">
							</div>
						</div>
					</div>

				</form>
			</div>


		</div>

		<div class="tab-pane" id="tab_b"
			style="margin-top: 2%; display: none;">

			

			<div id="panel-typeahead"
				class="panel panel-default sortable-widget-item">
				<div class="panel-heading">
					<div class="panel-actions">
						<!--                                 <button data-expand="#panel-typeahead" title="expand" class="btn-panel">
                                    <i class="fa fa-expand"></i>
                                </button>
                                <button data-collapse="#panel-typeahead" title="collapse" class="btn-panel">
                                    <i class="fa fa-caret-down"></i>
                                </button> -->
						<!-- <button data-close="#panel-typeahead" title="close" class="btn-panel">
                                    <i class="fa fa-times"></i>
                                </button> -->
					</div>
					<!-- /panel-actions -->
					<h3 class="panel-title">Share Clinical Information</h3>
				</div>
				<!-- /panel-heading -->

				<div class="panel-body">
					<form role="form" class="form-horizontal form-bordered">

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Patient Name :</label>
							<div class="col-sm-5">
								<label style="text-align: left; margin-left: 1%; width: 50%;"
									class="col-sm-3 control-label" id="sharePatientName"
									for="typeahead-local"></label>
							</div>
							<!--/cols-->
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">DOB :</label>
							<div class="col-sm-5">
								<label style="text-align: left; margin-left: 1%;"
									class="col-sm-3 control-label" id="sharePatientDob"
									for="typeahead-local"></label>
							</div>
							<!--/cols-->
						</div>

						<!--                                 <div class="form-group">
                                    <label class="col-sm-3 control-label" for="typeahead-local">Search Criteria</label>
                                    <div class="col-sm-5">                              
			                            <select  id="mySelect" onchange="jsFunction()" class="form-control" >
				                            <option value="1">Search by Date</option>
				                            <option value="3">Search by Tag</option>
				                            <option>physician4</option>
			                            </select>
                                    </div>/cols
                                </div>/form-group
                                
                           
								<div id="datelabel" style="display:none">
								<div class="form-group">
                                    <label class="col-sm-3 control-label" for="typeahead-local">Search by Date</label>
                                    <div class="col-sm-5">
                              
                              
                             <div class="input-group input-group-in date" data-input="datepicker" data-format="yyyy/mm/dd">
                                            <input type="text" class="form-control" id="datetext" readonly/>
                                            <span class="input-group-addon text-silver"><i class="fa fa-calendar"></i></span>
                                        </div>/input-group-in
                                    </div>/cols
                                </div>
								</div>

								 <div id="taglabel" style="display:none">
								<div class="form-group">
								 <label class="col-sm-3 control-label" for="typeahead-local">Tag</label>
                                    <div class="col-sm-5">
                                        <div class="input-group input-group-in">
                                            <span class="input-group-addon text-silver"><i class="glyphicon glyphicon-user"></i></span>
                                            <input type="text" id="tag" name="tag" class="form-control" placeholder="Tag"  />
                                        </div>/input-group-in
                                    </div>/cols
									</div>
								</div>
								
								<div id="patientlabel" style="display:none">
								<div class="form-group">
								 <label class="col-sm-3 control-label" for="typeahead-local">Patient Name</label>
                                    <div class="col-sm-5">
                                        <div class="input-group input-group-in">
                                            <span class="input-group-addon text-silver"><i class="glyphicon glyphicon-user"></i></span>
                                            <input type="text" id="patientname" name="patientname" class="form-control" placeholder="Patient Name" onkeyup="loadPatientsTable()" />
                                        </div>/input-group-in
                                    </div>/cols
									</div>
                                </div>/form-group -->

						<input type="hidden" id="hdnPatientId" name="patientId"> <input
							type="hidden" id="hdnPhysicianId" name="physicianId"> <input
							type="hidden" id="hdnshareId" name="shareId">


						<!--  					<div id="searchButton" style="margin-left: 3%; display:none;" >
					
							<input type="button" value="Search" class="btn btn-inverse" onclick="showDetais()" >
                            
							<button class="btn btn-inverse" disabled="" type="button" onclick="showDetais()">Search Here</button>
							
                            <button class="btn btn-inverse" disabled="" type="button">Reset Password</button>
                        </div> -->

						<!-- 						<div class="panel-body" style="display:none" id="patientShareVisitsDiv">						
		                    <div class="table-responsive table-responsive-datatables">
		                    	<table class="tablesorter table table-hover table-bordered" >
									<thead>
										<tr>
											<th></th>
											<th>Patient Name</th>
											<th>DOB</th>
											<th>Visit Date</th>
											<th>Physician</th>
											<th>Reason For Visit</th>											
										</tr>
									</thead>
                                    
								</table>
		                    </div>
						</div> -->

						<h3>Document List</h3>

						<div class="panel-body">
							<div class="table-responsive table-responsive-datatables"
								id="shareDocumentDiv">

								<%-- 		                    	<table class="table table-hover table-bordered" id="shareDocumentTable">
									<thead>
										<tr>										    
											<th>Date</th>				                            
				                            <th>Document</th>											
										</tr>
									</thead> 
									<tbody>
									
									<%
									for(Object[] obj :doc){%>
								<tr>
		                            <td class="user_list_link" ><%=obj[4] %></a></td>
									<td class="user_list_link"><a href="#" onClick="callDocumentOpenFile('<%=obj[2]%>')"><%=obj[2] %></a></td>
		                        </tr>
								<%	}
									%>
						
								</table>  --%>
							</div>
						</div>

						<div id="searchButton" style="margin-left: 3%;">
							<input type="button" value="Share" class="btn btn-inverse"
								onclick="showPhysicianSearch()">
						</div>

						<div id="errPhysicianEmail" class="required"
							style="display: none; color: red; margin: 0% 0% 1% 30%;" onclick="fnClearMsgField()">
							Please select one Physician from the list.</div>

						<div id="sharephysicianSearchDiv" style="display: none;">
                            <!--
							<div class="form-group">
								<label class="col-sm-3 control-label" for="typeahead-local">Enter Email Address </label>
								<div class="col-sm-5">
									<div class="input-group input-group-in">
										<span class="input-group-addon text-silver"><i
											class="glyphicon glyphicon-user"></i></span> <input type="text"
											id="physicianEmailId" name="physicianEmailId"
											class="form-control" placeholder="Physician Email Id" />
									</div>
								</div>
							</div>

							<div style="margin: 0.5% 0 1% 35%" id="ORSPANID">OR</div>
                            -->

							<div class="form-group">
								<label class="col-sm-3 control-label" for="typeahead-local">Organization</label>
								<div class="col-sm-5">
									<select name="organization" id="physicianVisitOrganization"
										class="form-control" onchange="loadPractice()"
										placeholder="Select Organization">
									</select>
								</div>
								<!--/cols-->
							</div>
							<!--/form-group-->

							<div class="form-group">
								<label class="col-sm-3 control-label" for="typeahead-local">Practice</label>
								<div class="col-sm-5">
									<select name="practice" id="selectPractice"
										class="form-control">
									</select>
								</div>
								<!--/cols-->
							</div>
							<!--/form-group-->

							<div class="form-group">
								<label class="col-sm-3 control-label" for="typeahead-local">Physician
								</label>
								<div class="col-sm-5">
									<div class="input-group input-group-in">
										<span class="input-group-addon text-silver"><i
											class="glyphicon glyphicon-user"></i></span> <input type="text"
											id="physicianname" name="physicianname" class="form-control"
											onkeyup="loadPhysicianTable()" />
									</div>
									<!-- /input-group-in -->
								</div>
								<!--/cols-->
							</div>
							<!--/form-group-->

							<div class="physicianInformations" id="physicianDetails"
								style="display: none">
								<div id="panel-tablesorter" class="panel panel-default">
									<div class="panel-heading bg-white">
										<div class="panel-actions">

											<!--                                 <button data-expand="#panel-tablesorter" title="expand" class="btn-panel">
                                    <i class="fa fa-expand"></i>
                                </button>
                                <button data-collapse="#panel-tablesorter" title="collapse" class="btn-panel">
                                    <i class="fa fa-caret-down"></i>
                                </button>
                                <button data-close="#panel-tablesorter" title="close" class="btn-panel">
                                    <i class="fa fa-times"></i>
                                </button> -->
										</div>
										<h3 class="panel-title">Details</h3>
									</div>

									<div class="panel-body">
										<div class="table-responsive table-responsive-datatables">
											<table class="tablesorter table table-hover table-bordered">
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
							<div id="submitButton" style="margin-left: 3%;">
								<input type="button" value="Submit" class="btn btn-inverse"
									onclick="pendingSubmitSharePatient()">
							</div>
						</div>

					</form>
				</div>

			</div>
			<!-- /panel-body -->
		</div>
	</div>

</div>

</div>
<script>
            loadPendingRequests();
            loadPatientPersonalDetails();
			//loadPatientShareVisits(1,1,'234234');            
            loadPatientShareDocuments();
            loadPatientAllDocuments();
            loadPhysicianOrganization();
            //loadPractice();
            </script>

<!--/content-body -->
<jsp:include page="../footerbluehub.jsp"></jsp:include>