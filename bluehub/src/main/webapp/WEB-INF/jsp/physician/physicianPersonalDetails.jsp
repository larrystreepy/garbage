<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../headerfirsttime.jsp"></jsp:include>

<script>
        function canceltohome(){
        	window.location.href=contextPath+ "/physician/physiciandashboard.do";
        }
        
        function getPhysicianAddInfo(){
	   		$.ajax({
				type : "GET",
				url : contextPath + "/physician/getPhysicianAddInfo.do",
				cache : false,
				async : false,
				success : function(response) {				
					var parsedJson = $.parseJSON(response);
					
					if(parsedJson != null){					
						
						document.getElementById("hdnPKID").value = parsedJson[0]["id"];
						
						if(parsedJson[0]["firstname"] != '' || parsedJson[0]["firstname"] != 'undefined'){
							$("#firstname").val(parsedJson[0]["firstname"]);
						}
						if(parsedJson[0]["middlename"] != '' || parsedJson[0]["middlename"] != 'undefined'){
							$("#middlename").val(parsedJson[0]["middlename"].trim());
						}
						if(parsedJson[0]["lastname"] != '' || parsedJson[0]["lastname"] != 'undefined'){
							$("#lastname").val(parsedJson[0]["lastname"]);
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
						if(parsedJson[0]["degree"] != '' || parsedJson[0]["degree"] != 'undefined'){
							$("#degree").val(parsedJson[0]["degree"]);
						}
						if(parsedJson[0]["language"] != '' || parsedJson[0]["language"] != 'undefined'){
							$("#language").val(parsedJson[0]["language"]);
						}						
						if(parsedJson[0]["email"] != '' || parsedJson[0]["email"] != 'undefined'){
							$("#email").val(parsedJson[0]["email"]);
						}
						if(parsedJson[0]["office_phone"] != '' || parsedJson[0]["office_phone"] != 'undefined'){
							$("#office_phone").val(parsedJson[0]["office_phone"]);
						}
						if(parsedJson[0]["fax"] != '' || parsedJson[0]["fax"] != 'undefined'){
							$("#fax").val(parsedJson[0]["fax"]);
						}

						document.getElementById("submitPhysicianAddInfo").setAttribute("onclick","fnUpdatePhysicianPersonalForm();");
						
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

	function validNameCheck(nameId) {
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
			} else {
				flag = false;
			}
		}
		document.getElementById(nameId).value = FilteredChars;

		if (flag == false) {
			document.getElementById(errspan).innerHTML = 'Numeric Value Not Allowed';
		}

		return false;
	}

	function clearErrorSpan(nameId) {
		var errspan = nameId + 'Span';
		document.getElementById(errspan).innerHTML = "";
	}

	function loadPhysicianTable() {
		if (document.getElementById('physicianname').value == 'a') {
			document.getElementById('physicianInformations').style.display = 'block';
		}
	}

	function callProceed() {
		document.form.action = "UploadOptions.html";
		document.form.submit();
	}
</script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/physician/physician.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.inputmask.js"></script>
<section class="section">

	<div class="content">

		<div class="content-body">

			<div id="panel-typeahead"
				class="panel panel-default sortable-widget-item">
				<div class="panel-heading">
					<div class="panel-actions">
						<!--  <button data-refresh="#panel-typeahead" title="refresh" class="btn-panel">
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
					</div>
					<h3 class="panel-title">Additional Information</h3>
				</div>

				<div class="panel-body">
					<form class="form-horizontal form-bordered" method="post"
						commandName="physicianPersonalForm" name="phyPersonalForm"
						id="phyPersonalForm">

						<input type="hidden" id="hdnPKID" name="hdnPKID" value="">
						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">First
								Name<span class="required">*</span>
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="firstname" name="firstname" class="form-control"
										maxlength="50" onchange="validNameCheck('firstname');"
										onfocus="clearErrorSpan('firstname');" placeholder="First Name" value="" />
								</div>
								<span style="color: #da4f49 !important;" id="firstnameSpan"></span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Middle
								Name <span class="required">*</span></label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="middlename" name="middlename" maxlength="15"
										onchange="validNameCheck('middlename');"
										onfocus="clearErrorSpan('middlename');" class="form-control"
										value="" placeholder="Middle Name" />
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
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="lastname" name="lastname" maxlength="50"
										onchange="validNameCheck('lastname');"
										onfocus="clearErrorSpan('lastname');" class="form-control"
										value="" placeholder="Last Name" />
								</div>
								<span style="color: #da4f49 !important;" id="lastnameSpan"></span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Contact
								Number <span class="required">*</span>
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="contactNo" name="contactNo" class="form-control" placeholder="Contact Number" value="" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Street
								<span class="required">*</span>
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span>
									<!-- 	                                            <textarea rows="10" cols="50" name="address" id="address" style="resize:none;" value=""></textarea> -->
									<input type="text" id="street" placeholder="Street"
										name="street" class="form-control" value="" />
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
										class="form-control" value="" />
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
										class="form-control" maxlength="8" value="" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Degree
								<span class="required">*</span>
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="degree" name="degree" class="form-control" value="" placeholder="Degree" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Email
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="email" name="email" class="form-control"
										value="${physicianPersonalForm.contactNo}" placeholder="Email" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Language
								Spoken </label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="language" name="language" class="form-control" value="" placeholder="Language Spoken"  />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Office
								Phone </label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="office_phone" name="office_phone" class="form-control"
										value=""  placeholder="Office Phone" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Fax
							</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="fax" name="fax" class="form-control" value="" placeholder="Fax" />
								</div>
							</div>
						</div>
						 

						<div id="searchButton" style="margin: 3%;">
							<input class="btn btn-inverse" type="button"
								id="submitPhysicianAddInfo" onclick="fnPhysicianPersonalForm();"
								value="Submit" /> <input class="btn btn-inverse" type="button"
								id="submitPhysicianAddInfo" onclick="canceltohome();"
								value="Cancel" />
						</div>

					</form>
				</div>
			</div>
		</div>
	</div>


</section>

<script>
	getPhysicianAddInfo();
</script>

<jsp:include page="../footerbluehub.jsp"></jsp:include>