<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<jsp:include page="../headerfirsttime.jsp"></jsp:include>

<script>
        function cancelPatientHome(){
        	window.location.href=contextPath+ "/patient/home.do";
        }
        
       function getPatientAddInfo(){
	   		$.ajax({
				type : "GET",
				url : contextPath + "/patient/getPatientAddInfo.do",
				cache : false,
				async : false,
				success : function(response) {				
					var parsedJson = $.parseJSON(response);
					
					if(parsedJson != null){					
						var dateOfBirth = dob = '';
						if(parsedJson[0]["dob"] == undefined || parsedJson[0]["dob"] == 'undefined'){
							dob = '';
						}else{
							dateOfBirth = new Date(parsedJson[0]["dob"]); 
							dob = (dateOfBirth.getMonth() + 1) + "/" + dateOfBirth.getDate() + "/" + dateOfBirth.getFullYear();					
						}
						
						document.getElementById("hdnPKID").value = parsedJson[0]["id"];
						
						var effDate = efd = '';
						if(parsedJson[0]["insurance_eff_date"] == undefined || parsedJson[0]["insurance_eff_date"] == 'undefined'){
							efd = '';
						}else{
							effDate = new Date(parsedJson[0]["insurance_eff_date"]); 
							efd = (effDate.getMonth() + 1) + "/" + effDate.getDate() + "/" + effDate.getFullYear();					
						}
						
						var expDate = exd = '';
						if(parsedJson[0]["insurance_exp_date"] == undefined || parsedJson[0]["insurance_exp_date"] == 'undefined'){
							exd = '';
						}else{
							expDate = new Date(parsedJson[0]["insurance_exp_date"]); 
							exd = (expDate.getMonth() + 1) + "/" + expDate.getDate() + "/" + expDate.getFullYear();					
						}
						
						if(parsedJson[0]["firstname"] != '' || parsedJson[0]["firstname"] != 'undefined'){
							$("#firstname").val(parsedJson[0]["firstname"]);
						}
						if(parsedJson[0]["middlename"] != '' || parsedJson[0]["middlename"] != 'undefined'){
							$("#middlename").val(parsedJson[0]["middlename"].trim());
						}
						if(parsedJson[0]["lastname"] != '' || parsedJson[0]["lastname"] != 'undefined'){
							$("#lastname").val(parsedJson[0]["lastname"]);
						}
						
						$("#dob").val(dob);
						
						if(parsedJson[0]["ssn"] != '' || parsedJson[0]["ssn"] != 'undefined'){
							$("#ssn").val(parsedJson[0]["ssn"]);
						}
						if(parsedJson[0]["contact_number"] != '' || parsedJson[0]["contact_number"] != 'undefined'){
							$("#contactNo").val(parsedJson[0]["contact_number"]);
						}
						if(parsedJson[0]["street"] != '' || parsedJson[0]["street"] != 'undefined'){
							$("#street").val(parsedJson[0]["street"]);
						}
						if(parsedJson[0]["city"] != '' || parsedJson[0]["city"] != 'undefined'){
							$("#city").val(parsedJson[0]["city"]);
						}
						if(parsedJson[0]["zip"] != '' || parsedJson[0]["zip"] != 'undefined'){
							$("#zip").val(parsedJson[0]["zip"]);
						}
						if(parsedJson[0]["policy_number"] != '' || parsedJson[0]["policy_number"] != 'undefined'){
							$("#policy_number").val(parsedJson[0]["policy_number"]);
						}
	
						$("#insurance_eff_date").val(efd);
						
						$("#insurance_exp_date").val(exd);
						
						document.getElementById("submitPatientAddInfo").setAttribute("onclick","fnUpdatePatientPersonalForm();");
						
					}else{
						
					}
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
		});
   		
       }
       
        $(function() {
        	var date = new Date();
        	var currentMonth = date.getMonth();
        	var currentDate = date.getDate();
        	var currentYear = date.getFullYear();
            
        	$("#dob").datepicker({
        		yearRange : "1913:+0",
        		changeMonth : true,
        		changeYear : true,
        		endDate : new Date(currentYear, currentMonth, currentDate)
        	});
        });
        
        var contextPath = "<%=request.getContextPath()%>";
        
        function validNameCheck(nameId){
        	var flag = true;
        	var errspan = nameId + 'Span';
        	
      	  var totalCharacterCount = document.getElementById(nameId).value;
        	
        var strValidChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
        var strChar;
        var FilteredChars = "";
        for (i = 0; i < totalCharacterCount.length; i++) {
            strChar = totalCharacterCount.charAt(i);
            if (strValidChars.indexOf(strChar) != -1) {
                FilteredChars = FilteredChars + strChar;
            }else{
          	  flag = false;
            }
        }
        document.getElementById(nameId).value = FilteredChars;
        
        if(flag == false){
	          document.getElementById(errspan).innerHTML= 'Numeric Value Not Allowed';          
        }
        
        return false;
      }        
		
        function clearErrorSpan(nameId){
        	var errspan = nameId + 'Span';
        	document.getElementById(errspan).innerHTML="";
        }
        
	       function callProceed(){
   				document.form.action="UploadOptions.html";
   				document.form.submit();           
      	   }		
        
        </script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/admin/patientmanagement.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/physician/physician.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.inputmask.js"></script>


<section class="section">

	<div class="content">

		<div class="content-body">
			<!-- APP CONTENT
                    ================================================== -->

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
					<h3 class="panel-title">Additional Information</h3>
				</div>

				<div class="panel-body">
					<form class="form-horizontal form-bordered" method="post"
						commandName="physicianPersonalForm" name="patientPersonalForm"
						id="patientPersonalForm">

						<input type="hidden" id="hdnPKID" name="hdnPKID" value="">
						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">First
								Name<span class="required">*</span>
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"> </span> <input
										type="text" placeholder="First Name" id="firstname"
										name="firstname" class="form-control" maxlength="50"
										onchange="validNameCheck('firstname');"
										onfocus="clearErrorSpan('firstname');" value="" />
								</div>
								<span style="color: #da4f49 !important;" id="firstnameSpan"></span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Middle
								Name 
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"> </span> <input
										type="text" id="middlename" placeholder="Middle Name"
										name="middlename" maxlength="15" class="form-control"
										onchange="validNameCheck('middlename');"
										onfocus="clearErrorSpan('middlename');" value="" />
								</div>
								<span style="color: #da4f49 !important;" id="middlenameSpan"></span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Last
								Name <span class="required">*</span>
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"></span> <input
										type="text" id="lastname" placeholder="Last Name"
										name="lastname" maxlength="50"
										onchange="validNameCheck('lastname');" class="form-control"
										onfocus="clearErrorSpan('lastname');" value="" />
								</div>
								<span style="color: #da4f49 !important;" id="lastnameSpan"></span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">DOB
								<span class="required">*</span>
							</label>
							<div class="col-sm-5" style="width: 51.5%;">
								<div class="input-group input-group-in date"
									data-input="datepicker" data-format="mm/dd/yyyy"
									style="width: 80%; float: left;">
									<input type="text" class="form-control" id="dob" name="dob"
										placeholder="DOB" /> <span
										class="input-group-addon text-silver"><i
										class="fa fa-calendar"></i></span>
								</div>
								<span style="float: left; margin: 1.5% 0% 2% 1.5%;"
									class="required">MM/DD/YYYY</span>

								
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">SSN
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"></span> <input
										type="text" placeholder="SSN" id="ssn" name="ssn"
										class="form-control" value="${physicianPersonalForm.ssn}" />
								</div>
								<div class="required" id="exception" style="display: none;">
									<c:out value="${EXCEPTION}" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Contact
								Number <span class="required">*</span>
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"></span> <input
										type="text" id="contactNo" placeholder="Contact Number"
										name="contactNo" class="form-control"
										value="${physicianPersonalForm.contactNo}" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Street
								<span class="required">*</span>
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"></span>
									<input type="text" id="street" placeholder="Street"
										name="street" class="form-control"
										value="${physicianPersonalForm.street}" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">City
								<span class="required">*</span>
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"></span> <input
										type="text" id="city" placeholder="City" name="city"
										class="form-control" value="${physicianPersonalForm.city}" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Zip
								<span class="required">*</span>
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"></span> <input
										type="text" id="zip" placeholder="Zip" name="zip"
										class="form-control" maxlength="8"
										value="${physicianPersonalForm.zip}" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Policy
								Number <span class="required">*</span>
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"></span> <input
										type="text" id="policy_number" placeholder="Policy Number"
										name="policy_number" class="form-control"
										value="${physicianPersonalForm.policy_number}" maxlength="15" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Insurance
								Effective Date</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in date"
									data-input="datepicker" data-format="mm/dd/yyyy">
									<input type="text" class="form-control" id="insurance_eff_date"
										name="insurance_eff_date"
										placeholder="Insurance Effective Date" /> <span
										class="input-group-addon text-silver"><i
										class="fa fa-calendar"></i></span>
								</div>
								
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Insurance
								Expiration Date</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in date"
									data-input="datepicker" data-format="mm/dd/yyyy">
									<input type="text" class="form-control" id="insurance_exp_date"
										name="insurance_exp_date"
										placeholder="Insurance Expiration Date" /> <span
										class="input-group-addon text-silver"><i
										class="fa fa-calendar"></i></span>
								</div>

								<span style="color: #da4f49 !important;" id="insuranceSpan"></span>
								
							</div>
						</div>

						<div id="searchButton" style="margin: 3%;">
							<input class="btn btn-inverse" type="button"
								id="submitPatientAddInfo" onclick="fnPatientPersonalForm();"
								value="Submit" /> <input class="btn btn-inverse" type="button"
								id="submitPatientAddInfo" onclick="cancelPatientHome();"
								value="Cancel" />
						</div>

					</form>
				</div>
			</div>
		</div>
	</div>


</section>

<script>
        	getPatientAddInfo();
        </script>

<jsp:include page="../footerbluehub.jsp"></jsp:include>