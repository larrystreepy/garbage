<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script>
        var contextPath = "<%=request.getContextPath()%>";
        
        
    	function loadPhysicianTable() {
    		document.getElementById('physicianDetails').style.display = "block";
			
    		var content = '';
    		content += '<div class="table-responsive table-responsive-datatables">';
    		content += '<table style="width: 100%" class="tablesorter table table-hover table-bordered">';
    		content += '<thead>';
    		content += '<th></th>';
    		content += '<th>Physician Name</th>';
    		content += '<th>Organization</th>';
    		content += '<th>Practice</th>';
    		/* content += '<th>Billing Address </th>';
    		content += '<th>Contact person</th>';
    		content += '<th>Status</th>'; */
    		content += '</thead>';
    		document.getElementById('physicianDetails').innerHTML = content;

    		var searchPhysician=document.getElementById('physicianname').value;

    		var orgid=document.getElementById('physicianVisitOrganization').value;

    		var practiceid=document.getElementById('physicianVisitPractice').value;
    		
    		//alert(orgid);
    		
    		$.ajax({
    					type : "GET",
    					url : contextPath
    							+ "/administrator/adminphysiciandetails.do",
    					data:{searchphysician:searchPhysician,orgid:orgid,practiceid:practiceid},
    					//data : "searchphysician=" + searchPhysician +"&orgid=" +orgid+"&practiceid="+practiceid,
    					cache : false,
    					async : false,
    					//dataType: "json",
    					success : function(response) {

    						if(response=="null" || response==""){
    							content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

/*     							content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
    								content += '</table>';
    								document.getElementById('physicianDetails').innerHTML = content;
    							
    						}else{
    							var parsedJson = $.parseJSON(response);

    							/* alert("sdfsdfsdfsdfsd");
    							alert(parsedJson); */
    							//alert("parsedJson.length : "+parsedJson.length)
    							for (var i = 0; i < parsedJson.length; i++) {

    								//alert(parsedJson[i].userName)
    								//content = content + '<tr onclick="displayUserData(\''+parsedJson[i].userId+'\',\''+i+'\',\''+obj.length+'\')">';
    								content += '<td ><input type="radio" onclick="fnSetPhysicianvalue('+ parsedJson[i].userid +')" id="physicianRadio" value='+parsedJson[i].userid+' name="physicianRadio"></td>';
    								content += '<td id="agencytd'+i+'"  class="user_list_link">'
    										+ parsedJson[i].firstname + '</td>';
    								content += '<td id="emailtd'+i+'"  class="user_list_link">'
    										+ parsedJson[i].organizationName + '</td>';
    								content += '<td id="addresstd'+i+'"  class="user_list_link">'
    										+ parsedJson[i].practicename + '</td>';
    								content = content + '</tr>';
    							}

/*     							content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
    							content += '</table>';
    							content += '</div>';
    							document.getElementById('physicianDetails').innerHTML = content;
    						}
    					},
    					error : function(e) {
    						alert('Error: ' + e.responseText);
    					}
    				});
    	}        
    	
        function loadAdminOrganization(){
        $.ajax({
			type : "GET",
			url : contextPath
					+ "/administrator/adminorganizations.do",
			// data : "userGroup=" + userGroup,
			cache : false,
			async : false,
			success : function(response) {

				//var myRoleHidden = $('#myRoleHidden').val();
				var parsedJson = $.parseJSON(response);
				document.getElementById("physicianVisitOrganization").innerHTML = "";

				var combo = document
						.getElementById("physicianVisitOrganization");
				for (var i = 0; i < parsedJson.length; i++) {
					var option = document.createElement("option");
					option.text = parsedJson[i].organizationname;
					option.value = parsedJson[i].id;
					/* if (myRoleHidden == parsedJson[i]) {
					 option.setAttribute("selected", "selected");
					} */
					combo.add(option);
				}
				loadPractice();
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});
        
        }
        
        function loadPractice(){		
			$.ajax({
			type : "GET",
			url : contextPath + "/administrator/loadpractice.do",
			// data : "userGroup=" + userGroup,
			cache : false,
			async : false,
			success : function(response) {
			
				//var myRoleHidden = $('#myRoleHidden').val();
				var parsedJson = $.parseJSON(response);
				document.getElementById("physicianVisitPractice").innerHTML = "";
			
				var combo = document.getElementById("physicianVisitPractice");
			
				for (var i = 0; i < parsedJson.length; i++) {
					var option = document.createElement("option");
					option.text = parsedJson[i].practicename;
					option.value = parsedJson[i].id;
					/* if (myRoleHidden == parsedJson[i]) {
					 option.setAttribute("selected", "selected");
					} */
					combo.add(option);
				}
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
			});      
        }
        
		function fnSetPhysicianvalue(id){			   			
    		document.getElementById("hdnPhysicianId").value = id;
    		document.getElementById("allVisitsDiv").style.display="block";
    		loadAdminAllVisits();
    	}        
        </script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/admin/adminorganization.js"></script>

<div class="content" style="margin-left: 22px;">

	<div class="content-body">
		<!-- APP CONTENT
                    ================================================== -->

		<div id="panel-typeahead"
			class="panel panel-default sortable-widget-item">
			<div class="panel-heading">
				<div class="panel-actions">
					<!-- <button data-refresh="#panel-typeahead" title="refresh" class="btn-panel">
                                    <i class="fa fa-refresh"></i>
                                </button> -->
					<button data-expand="#panel-typeahead" title="expand"
						class="btn-panel">
						<i class="fa fa-expand"></i>
					</button>
					<button data-collapse="#panel-typeahead" title="collapse"
						class="btn-panel">
						<i class="fa fa-caret-down"></i>
					</button>
					<!-- <button data-close="#panel-typeahead" title="close" class="btn-panel">
                                    <i class="fa fa-times"></i>
                                </button> -->
				</div>
				<!-- /panel-actions -->
				<h3 class="panel-title">Physician Visit Records</h3>
			</div>
			<!-- /panel-heading -->

			<div class="panel-body">
				<form role="form" class="form-horizontal form-bordered"
					method="post" commandName="adminAllVisitsForm"
					name="adminAllVisitForm" id="adminAllVisitForm">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Organization</label>
						<div class="col-sm-5">
							<select name="organization" id="physicianVisitOrganization"
								class="form-control" placeholder="Select Organization">
								<option value="1">Prime Home Clinical care</option>
								<option value="2">Billroth Hospitals</option>
								<option value="3">Mayo Hospital</option>
								<option value="4">Preach care Health Systems</option>
								<option value="5">BeWell Hospitals</option>
							</select>
						</div>
						<!--/cols-->
					</div>
					<!--/form-group-->

					<input type="hidden" id="hdnPhysicianId" name="physicianId">

					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Practice</label>
						<div class="col-sm-5">
							<select name="practice" id="physicianVisitPractice"
								class="form-control" placeholder="Select Practice">
								<option value="1">Pediatric Cardiology</option>
								<option value="2">Cardiology</option>
								<option value="3">Thoracic</option>
								<option value="4">Orthopedic</option>
							</select>
						</div>
						<!--/cols-->
					</div>
					<!--/form-group-->

					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Physician</label>
						<div class="col-sm-5">
							<div class="input-group input-group-in">
								<span class="input-group-addon text-silver"><i
									class="glyphicon glyphicon-user"></i></span> <input type="text"
									id="physicianname" name="physicianname"
									onkeyup="loadPhysicianTable()" class="form-control"
									placeholder="Physician" />
							</div>
							<!-- /input-group-in -->
						</div>
						<!--/cols-->
					</div>
					<!--/form-group-->

					<input type="hidden" id="physician" name="physician" />

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

					<div id="allVisitsDiv" style="display: none; margin-top: 2%;">
						<div class="subscribe_top2" id="subscribe_top2">
							<div id="panel-tablesorter" class="panel panel-default">
								<div class="panel-heading bg-white">
									<div class="panel-actions">
										<!--                                 <button data-refresh="#panel-tablesorter" title="refresh" class="btn-panel">
                                    <i class="fa fa-refresh"></i>
                                </button> -->
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
									<!-- /panel-actions -->
									<h3 class="panel-title">Physician Details</h3>
								</div>
								<!-- /panel-heading -->

								<div class="panel-body">
									<div class="table-responsive table-responsive-datatables"
										id="adminAllVisitsTable"></div>
									<!--/table-responsive-->
								</div>
								<!--/panel-body-->
							</div>
						</div>

						<div>
							<input type="hidden" id="hiddenPhysicianVisitId"
								name="hiddenPhysicianVisitId" value="" />
						</div>

						<div id="editAdminAllVisit" style="display: none;">
							<h3>Edit Visit</h3>
							<div class="form-group">
								<label class="col-sm-3 control-label" for="typeahead-local">Date
									of Visit</label>
								<div class="col-sm-5">
									<div class="input-group input-group-in date"
										data-input="datepicker" data-format="yyyy/mm/dd">
										<input type="text" class="form-control" name="date_of_visit"
											id="date_of_visit" placeholder="Date of Visit" /> <span
											class="input-group-addon text-silver"><i
											class="fa fa-calendar"></i></span>
									</div>
									<!-- /input-group-in -->

									<%-- 	                                        <div class="input-group input-group-in">
	                                            <span class="input-group-addon text-silver"><i class="fa fa-calendar"></i></span>
	                                            <input type="text" id="date_of_visit" name="date_of_visit" value="${physicianVisitsForm.date_of_visit}" data-input="datepicker" data-view="2" class="form-control"/>
	                                        </div><!-- /input-group-in --> --%>
								</div>
								<!--/cols-->
							</div>
							<!--/form-group-->

							<div class="form-group">
								<label class="col-sm-3 control-label" for="typeahead-local">Reason
									for Visit<span class="required">*</span>
								</label>
								<div class="col-sm-5">
									<div class="input-group input-group-in">
										<span class="input-group-addon text-silver"><i
											class="glyphicon glyphicon-user"></i></span> <input type="text"
											id="reason_for_visit" name="reason_for_visit" value=""
											class="form-control" />
									</div>
									<!-- /input-group-in -->
								</div>
								<!--/cols-->
							</div>
							<!--/form-group-->

							<div class="form-group">
								<label class="col-sm-3 control-label" for="typeahead-local">Prescription
									<span class="required">*</span>
								</label>
								<div class="col-sm-5">
									<div class="input-group input-group-in">
										<span class="input-group-addon text-silver"><i
											class="glyphicon glyphicon-user"></i></span>
										<textarea rows="10" cols="50" name="prescription" value=""
											id="prescription"></textarea>
									</div>
									<!-- /input-group-in -->
								</div>
								<!--/cols-->
							</div>
							<!--/form-group-->

							<input type="hidden" name="tag" id="tag" value="">
							<!-- 	                                <div class="form-group">
	                                    <label class="col-sm-3 control-label" for="typeahead-local">Organization</label>
	                                    <div class="col-sm-5">
	                                        <select  name="organization" id="organization" data-input="selectboxit" placeholder="Select Organization">
					                            <option value="1">Prime Home Clinical care</option>
					                            <option value="2">Billroth Hospitals</option>
					                            <option value="3">Mayo Hospital</option>                                                      
					                            <option value="4">Preach care Health Systems</option>
					                            <option value="5">BeWell Hospitals</option>
	                            			</select>
	                                    </div>/cols
	                                </div>/form-group
                            
 	                                <div class="form-group">
	                                    <label class="col-sm-3 control-label" for="typeahead-local">Practice</label>
	                                    <div class="col-sm-5">
	                                        <select name="practice" id="practice" data-input="selectboxit">
					                            <option value="1">Pediatric Cardiology</option>
					                            <option value="2">Cardiology</option>
					                            <option value="3">Thoracic</option>   
					                            <option value="4">Orthopedic</option>                                        
	                            			</select>
	                                    </div>/cols
	                                </div>/form-group

									<div class="form-group">
	                                    <label class="col-sm-3 control-label" for="typeahead-local">Tag </label>
	                                    <div class="col-sm-5">
	                                        <div class="input-group input-group-in">
	                                            <span class="input-group-addon text-silver"><i class="glyphicon glyphicon-user"></i></span>
	                                            <input type="text" id="tag" name="tag" class="form-control"/>
	                                        </div>/input-group-in
	                                    </div>/cols
	                                </div>/form-group
                            	                                
									<div class="form-group">
	                                    <label class="col-sm-3 control-label" for="typeahead-local">Physician </label>
	                                    <div class="col-sm-5">
	                                        <div class="input-group input-group-in">
	                                            <span class="input-group-addon text-silver"><i class="glyphicon glyphicon-user"></i></span>
	                                            <input type="text" id="physician" name="physician" class="form-control"/>
	                                        </div>/input-group-in
	                                    </div>/cols
	                                </div>/form-group -->

							<div id="searchButton" style="margin: 3%;">
								<!-- 					<button class="btn btn-inverse" id="phyVisitSubmit" type="button" onclick="fnPhysicianVisitForm();">Submit</button> -->
								<!-- 					<button class="btn btn-inverse" type="button">Reset</button> -->
								<input type="button" class="btn btn-inverse" id="phyVisitSubmit"
									onclick="updateAdminAllVisit();" value="Update" />

							</div>
							<!--/content-body -->
						</div>
					</div>
				</form>
			</div>
			<!--/content -->

		</div>

	</div>
</div>

</section>
<!--/content section -->



<script>
                
        loadAdminOrganization();
        
        </script>

<jsp:include page="../footerbluehub.jsp"></jsp:include>