<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/physician/physician.js"></script>
        <script>
        var contextPath = "<%=request.getContextPath()%>";
	function jsFunction() {

		document.getElementById("searchButton").style.display = "block";
		document.getElementById("viewPhyAllVisit").style.display = "none";
		
		var x = document.getElementById("mySelect").value;
		if(x == -1){
			if(document.getElementById('phyAllVisitsTable')){
				document.getElementById('phyAllVisitsTable').style.display = "none";
				}
			if(document.getElementById('patientInformations')){
				document.getElementById('patientInformations').style.display = "none";
				}
			document.getElementById('searchButton').style.display = 'none';
			document.getElementById('keywordlabel').style.display = 'none';
			document.getElementById('datelabel').style.display = 'none';
			document.getElementById('taglabel').style.display = 'none';
			document.getElementById('patientlabel').style.display = 'none';
			document.getElementById('datetext').value = "";
// 			document.getElementById('patientDetails').style.display = "none";
		}
		else if (x == 1) {
			if(document.getElementById('phyAllVisitsTable')){
				document.getElementById('phyAllVisitsTable').style.display = "none";
				}
			if(document.getElementById('patientInformations')){
				document.getElementById('patientInformations').style.display = "none";
				}
			document.getElementById('datelabel').style.display = 'block';
			document.getElementById('taglabel').style.display = 'none';
			document.getElementById('patientlabel').style.display = 'none';
			document.getElementById('keywordlabel').style.display = 'none';
			document.getElementById('datetext').value = "";
// 			document.getElementById('patientDetails').style.display = "none";
		}  else if (x == 3) {
			document.getElementById('datetext').value = "";
			if(document.getElementById('phyAllVisitsTable')){
				document.getElementById('phyAllVisitsTable').style.display = "none";
				}
			if(document.getElementById('patientInformations')){
				document.getElementById('patientInformations').style.display = "none";
				}
			document.getElementById('taglabel').style.display = 'block';
			document.getElementById('datelabel').style.display = 'none';
			document.getElementById('patientlabel').style.display = 'none';
			document.getElementById('keywordlabel').style.display = 'none';
// 			document.getElementById('patientDetails').style.display = "none";
		} else if (x == 4) {
			document.getElementById('datetext').value = "";
			if(document.getElementById('phyAllVisitsTable')){
				document.getElementById('phyAllVisitsTable').style.display = "none";
				}
			if(document.getElementById('patientInformations')){
				document.getElementById('patientInformations').style.display = "none";
				}
			document.getElementById('patientlabel').style.display = 'block';
			document.getElementById('datelabel').style.display = 'none';
			document.getElementById('taglabel').style.display = 'none';
			document.getElementById('keywordlabel').style.display = 'none';
		} else {
			document.getElementById('datetext').value = "";
			if(document.getElementById('phyAllVisitsTable')){
				document.getElementById('phyAllVisitsTable').style.display = "none";
				}
			if(document.getElementById('patientInformations')){
				document.getElementById('patientInformations').style.display = "none";
				}
			document.getElementById('keywordlabel').style.display = 'block';
			document.getElementById('datelabel').style.display = 'none';
			document.getElementById('taglabel').style.display = 'none';
			document.getElementById('patientlabel').style.display = 'none';
// 			document.getElementById('patientDetails').style.display = "none";
		}
	}

	function showDetais() {		
		if (document.getElementById('datetext').value != '') {
			if(document.getElementById('phyAllVisitsTable')){
				document.getElementById('phyAllVisitsTable').style.display = "block";
				}
			var visitDate = document.getElementById('datetext').value;
			loadPhysicianAllVisits(2,visitDate,2325);
		} else if (document.getElementById('tag').value != '') {
		} else if (document.getElementById('keyword').value != '') {
		}else {
			if(document.getElementById('phyAllVisitsTable')){
				document.getElementById('phyAllVisitsTable').style.display = "block";
				}
			var patid = document.getElementById('hdnPatientId').value;
			loadPhysicianAllVisits(3,'32213',patid);
		}
	}
	
	function loadPatientsTable(){
     	
     	document.getElementById('patientInformations').style.display = "block";
 	  	var searchPatient=document.getElementById('patientname').value;
 	  	var orgid=-1;
	  	var params = "searchPatient=" + searchPatient+"&orgid="+orgid;
 		$('#patientLoadTable').dataTable( {
 			 
 		  	 "processing": true,
 		  	 "bJQueryUI": true ,
 		   	  "bFilter": false,
 		  	  "bDestroy": true,
 		  	  "bServerSide": true,
 		  	  "sAjaxSource": contextPath + "/administrator/adminpatientdetails.do?"+params,
 		  	  "bProcessing": true,
 			  "aoColumns": [{"mDataProp":"Patient Name","bSortable": false,
 				  
 						  "fnRender": function (oObj)  {   
 				        	  
 				        	  var id = oObj.aData['id'];
 				        	  var name = oObj.aData['Patient Name'];
 				        	  
 				        	 return '<a href="javascript:fnSetPatientvalue('+id+')" >'+name+'</a>'
 				          }
 					  },
 					  /* {"mDataProp":"Last Name","bSortable": false}, */
 					  {"mDataProp":"DOB","bSortable": false},
 					  {"mDataProp":"SSN","bSortable": false},
 					  {"mDataProp":"Contact Number","bSortable": false},
 					  {"mDataProp":"Address","bSortable": false}
 			                     
 			                 ]
 			                
 			                
 			                
 		
 			  
 		});
 		
 	 
 		}

		function fnSetPatientvalue(id){			   			
    		document.getElementById("hdnPatientId").value = id;
    	}        

        </script>



            <div class="content" style="margin-left:1%;">
                
                <div class="content-body">
					<div id="panel-typeahead" class="panel panel-default sortable-widget-item">
                        <div class="panel-heading">
                            <div class="panel-actions">
                            </div> 
                            <h3 class="panel-title">All Visits</h3>
                        </div>
																
					<div> <input type="hidden" id="hiddenPhysicianVisitId" name="hiddenPhysicianVisitId" value="" /></div>
					
                        <div class="panel-body">
                            <form role="form" class="form-horizontal form-bordered">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label" for="typeahead-local">Search Criteria</label>
                                    <div class="col-sm-5">
		                              <select  id="mySelect" onchange="jsFunction()" class="form-control" >
		                               <option value="-1">Select a Choice</option>
				                            <option value="1">Search by Date</option>
<!-- 				                            <option value="3">Search by Tag</option> -->
				                            <option value="4">Search by Patient</option>
											<!-- <option value="5">Search by Keyword</option> -->
				                            <!-- <option>physician4</option> -->
		                            </select>
	                           </div>
                           </div>
							<input type="hidden" id="hdnPatientId" name="patientId">

							<input type="hidden" id="hdnPhysicianId" name="physicianId">	
                            					
							<div id="datelabel" style="display:none">
								<div class="form-group">
                                    <label class="col-sm-3 control-label" for="typeahead-local">Search by Date</label>
                                    <div class="col-sm-5">
		                            	<div class="input-group input-group-in date" data-input="datepicker" data-format="yyyy/mm/dd">
                                            <input type="text" class="form-control" id="datetext"/>
                                            <span class="input-group-addon text-silver"><i class="fa fa-calendar"></i></span>
                                        </div> 
                                    </div> 
                                </div>
							</div>

							<div id="taglabel" style="display:none">
								<div class="form-group">
								 <label class="col-sm-3 control-label" for="typeahead-local">Tag</label>
                                    <div class="col-sm-5">
                                        <div class="input-group input-group-in">
                                            <span class="input-group-addon text-silver"><i class="glyphicon glyphicon-user"></i></span>
                                            <input type="text" id="tag" name="tag" class="form-control" placeholder="Tag"  />
                                        </div> 
                                    </div> 
								</div>
							</div>
																    
							<div id="patientlabel" style="display:none">
								<div class="form-group">
									 <label class="col-sm-3 control-label" for="typeahead-local">Patient Name</label>
                                     <div class="col-sm-5">
                                        <div class="input-group input-group-in">
                                            <span class="input-group-addon text-silver"><i class="glyphicon glyphicon-user"></i></span>
                                            <input type="text" id="patientname" name="patientname" class="form-control" placeholder="Patient Name" onkeyup="loadPatientsTable()" />
                                        </div> 
                                    </div> 
								</div>
                            </div> 
                                						
							<div id="keywordlabel" style="display:none">
								<div class="form-group">
									<label class="col-sm-3 control-label" for="typeahead-local">Keyword</label>
                                    <div class="col-sm-5">
                                        <div class="input-group input-group-in">
                                            <span class="input-group-addon text-silver"><i class="glyphicon glyphicon-user"></i></span>
                                            <input type="text" id="keyword" name="keyword" class="form-control" placeholder="Keyword"  />
                                        </div> 
                                    </div> 
								</div>
							</div>

			<div class="patientInformations" id="patientInformations" style="display: none">
					
                      <table id="patientLoadTable" class="table table-striped table-bordered" >
									<thead>
										<tr>
											<th>Patient Name</th>
											<th>DOB</th>
											<th>SSN</th>
											<th>Contact Number</th>
											<th>Address</th>
											
										</tr>
									</thead>
											<tbody></tbody>			
								</table>
			</div>    
					<div id="searchButton" style="margin-left: 26.5%; display:none;" >					
						<input type="button" value="Search" class="btn btn-inverse" onclick="showDetais()" >
					</div>
			
                        <div class="panel-body">
		                    <div class="table-responsive table-responsive-datatables" id="phyAllVisitsTable" style="margin-top:2%;">


                   <table id="visitTableAll" class="table table-striped table-bordered">
						<thead>
							<tr>
								 
								<th>Visit Date</th>
								<th>Patient</th>
								<th>Reason</th>
								<th>Prescription</th>
							</tr>
						</thead>

						<tbody></tbody>


						</table>



		                    </div> 
						</div> 
								                                																                            		
					<div id="viewPhyAllVisit" style="display:none;">
							<h3>View Visit</h3>
									<div class="form-group">
	                                    <label class="col-sm-3 control-label" for="typeahead-local">Patient Name</label>
	                                    <div class="col-sm-5">
	                                        <div class="input-group input-group-in">
	                                            <span class="input-group-addon text-silver"><i class="glyphicon glyphicon-user"></i></span>
	                                            <input type="text" id="vistAllPatientName" name="vistAllPatientName" readonly value="" class="form-control"/>
	                                        </div> 
	                                    </div> 
	                                </div> 
							
									<div class="form-group">
	                                    <label class="col-sm-3 control-label" for="typeahead-local">Date of Visit</label>
	                                    <div class="col-sm-5">
	                                        <div class="input-group input-group-in">
	                                            <span class="input-group-addon text-silver"><i class="glyphicon glyphicon-user"></i></span>
	                                            <input type="text" id="date_of_visit" name="date_of_visit" value="" readonly class="form-control"/>
	                                        </div> 
	                                    </div> 
	                                </div> 

									<div class="form-group">
	                                    <label class="col-sm-3 control-label" for="typeahead-local">Reason for Visit </label>
	                                    <div class="col-sm-5">
	                                        <div class="input-group input-group-in">
	                                            <span class="input-group-addon text-silver"><i class="glyphicon glyphicon-user"></i></span>
	                                            <input type="text" id="reason_for_visit" name="reason_for_visit" readonly value="" class="form-control"/>
	                                        </div> 
	                                    </div> 
	                                </div> 
                            	                                
									<div class="form-group">
	                                    <label class="col-sm-3 control-label" for="typeahead-local">Prescription </label>
	                                    <div class="col-sm-5">
	                                        <div class="input-group input-group-in">
	                                            <span class="input-group-addon text-silver"><i class="glyphicon glyphicon-user"></i></span>
	                                            <textarea rows="10" cols="50" name="prescription" value="" readonly id="prescription" style="resize:none;"></textarea>
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
                        
        loadPhysicianAllVisits(1,'20252',232323);        
        
        </script>

<jsp:include page="../footerbluehub.jsp"></jsp:include>