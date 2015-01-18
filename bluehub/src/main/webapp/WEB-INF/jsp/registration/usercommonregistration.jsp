<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/registration/userregister.js"></script>
	
	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<script>

var contextPath = "<%=request.getContextPath()%>";

function fnOnchangeUserType(){
	
	var type = document.getElementById("userType").value;
	
	if(type=="-1"){
		
		document.getElementById("UserTypeDiv1").style.display = "none"
		
			document.getElementById("UserTypeDiv").style.display = "block"
	}else if(type=="Patient"){

		var type = document.getElementById("userType").value;

		 document.getElementById("userTypePat").value=type;

		document.getElementById("UserTypeDiv").style.display = "none"
			
			document.getElementById("UserTypeDiv1").style.display = "block"

				loadPhyOrganization();
	}else{

		var type = document.getElementById("userType").value;

		 document.getElementById("userTypePhy").value=type;
		 
			document.getElementById("UserTypeDiv").style.display = "none";
			
			document.getElementById("UserTypeDiv2").style.display = "block";

			loadOrganization();
	}
	
}

</script>


<style>
.required1 {
	color: #da4f49 !important;
}
</style>

<div class="content-body">

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
						<h3 class="panel-title">User Registration</h3>
					</div>

					<div class="required" id="exception"
						style="margin: 0px 0px 0px 108px;">
						<c:out value="${EXCEPTION}" />
					</div>

					<div class="panel-body">


						<div id="UserTypeDiv">
							<div class="form-group">
								<label class="col-sm-3 control-label" for="typeahead-local">User
									Type<span class="required1"> *</span>
								</label>


								<div class="col-sm-5">
									<select id="userType" name="userType" class="form-control"
										onchange="fnOnchangeUserType()">
										<option value="-1">Select User Type</option>
										<option value="Patient">Patient</option>
										<option value="Physician">Physician</option>
									</select>
								</div>
							</div>
						</div>



						<div id="UserTypeDiv1" style="display: none;">
							<form class="form-horizontal form-bordered" method="POST"
								commandName="userRegistrationForm" name="userForm" id="userForm">







								<input type="hidden" id="userTypePat" name="userTypePat"
									value="">

								<div id="organizationPhylabel">
									<div class="form-group">
										<label class="col-sm-3 control-label" for="typeahead-local">Organization</label>
										<div class="col-sm-5">
											<select class="form-control" id="selectPhyOrganization"
												name="selectPhyOrganization">
											</select>
										</div>
									</div>





								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label" for="typeahead-local">Email<span
										class="required1"> *</span></label>
									<div class="col-sm-5">
										<div class="input-group input-group-in">
											<span class="input-group-addon text-silver"> </span> <input
												type="text" id="userEmail" onchange="callCheckUserEmail()"
												value="${userRegistrationForm.userEmail}" name="userEmail"
												class="form-control" placeholder="Email" />
										</div>
										<span class="reg_span_error" id="emailValid"
											style="display: none;"></span>
									</div>
								</div>



								<div class="form-group">
									<label class="col-sm-3 control-label" for="typeahead-local">Password<span
										class="required1"> *</span></label>
									<div class="col-sm-5">
										<div class="input-group input-group-in">
											<span class="input-group-addon text-silver"></span> <input
												type="password" id="password"
												value="${userRegistrationForm.password}" name="password"
												class="form-control" placeholder="Password" required />
										</div>
									</div>
								</div>



								<div class="form-group">
									<label class="col-sm-3 control-label" for="typeahead-local">Confirm
										Password<span class="required1"> *</span>
									</label>
									<div class="col-sm-5">
										<div class="input-group input-group-in">
											<span class="input-group-addon text-silver"></span> <input
												type="password" id="confirmPassword"
												value="${userRegistrationForm.confirmPassword}"
												name="confirmPassword" class="form-control"
												placeholder="Confirm Password" required />
										</div>
									</div>
								</div>


								<div class="form-group">
									<label class="col-sm-3 control-label" for="typeahead-local">Password
										Recovery Question<span class="required1"> *</span>
									</label>
									<div class="col-sm-5">
										<select id="recoveryQuestion" name="recoveryQuestion"
											class="form-control"">
											<option value="What was your childhood nickname?">What
												was your childhood nickname?</option>
											<option value="In what city did you meet your spouse?">In
												what city did you meet your spouse?</option>
											<option value="What street did you live on in third grade?">What
												street did you live on in third grade?</option>
											<option
												value="What is the name of your favorite childhood friend?">What
												is the name of your favorite childhood friend?</option>

											<option value="In what city or town was your first job?">In
												what city or town was your first job?</option>


											<option value="What was your favorite sport in high school?">What
												was your favorite sport in high school?</option>
											<option
												value="What is the name of the High School you graduated from?">What
												is the name of the High School you graduated from?</option>
											<option value="What is your pet's name?">What is
												your pet's name?</option>
										</select>
									</div>
								</div>



								<div class="form-group">
									<label class="col-sm-3 control-label" for="typeahead-local">Password
										Recovery Answer<span class="required1"> *</span>
									</label>
									<div class="col-sm-5">
										<div class="input-group input-group-in">
											<span class="input-group-addon text-silver"></span> <input
												type="text" id="recoveryAnswer" name="recoveryAnswer"
												class="form-control"
												value="${userRegistrationForm.recoveryAnswer}"
												placeholder="Recovery Answer" required />
										</div>
									</div>
								</div>


								<div id="searchButton"
									style="display: block; margin-left: 29%; border-bottom: 1px solid white;"
									class="panel-body">


									<input type="button" value="Register" class="btn btn-inverse"
										onclick="fnRegisterBluehubUser();"> <input
										type="button" value="Cancel" class="btn btn-inverse"
										onclick="fnCancelBluehubUser();">
								</div>



							</form>

						</div>

						<form role="form" class="form-horizontal form-bordered"
							method="POST" commandName="userPhyForm" name="userPhyForm"
							id="userPhyForm">




							<div id="UserTypeDiv2" style="display: none;">


								<input type="hidden" id="userTypePhy" name="userTypePhy"
									value="">



								<div id="organizationlabel">
									<div class="form-group">
										<label class="col-sm-3 control-label" for="typeahead-local">Organization<span
											class="required1"> *</span></label>
										<div class="col-sm-5">
											<select class="form-control" id="selectOrganization"
												name="selectOrganization" onchange="loadPractice()">
											</select>
										</div>
									</div>

									<div id="practiceDiv">
										<div class="form-group">
											<label class="col-sm-3 control-label" for="typeahead-local">Practice<span
												class="required1"> *</span></label>
											<div class="col-sm-5">
												<select id="selectPractice" name="selectPractice"
													class="form-control">

												</select>
											</div>


										</div>
									</div>




								</div>


								<div class="form-group">
									<label class="col-sm-3 control-label" for="typeahead-local">Email<span
										class="required1"> *</span></label>
									<div class="col-sm-5">
										<div class="input-group input-group-in">
											<span class="input-group-addon text-silver"> </span> <input
												type="text" id="phyUserEmail"
												onchange="callCheckPhyUserEmail()"
												value="${userPhyForm.phyUserEmail}" name="phyUserEmail"
												class="form-control" placeholder="Email" />
										</div>
										<span class="reg_span_error" id="emailValid1"
											style="display: none;"></span>
									</div>
								</div>



								<div class="form-group">
									<label class="col-sm-3 control-label" for="typeahead-local">Password<span
										class="required1"> *</span></label>
									<div class="col-sm-5">
										<div class="input-group input-group-in">
											<span class="input-group-addon text-silver"></span> <input
												type="password" id="phyPassword"
												value="${userPhyForm.phyPassword}" name="phyPassword"
												class="form-control" placeholder="Password" required />
										</div>
									</div>
								</div>



								<div class="form-group">
									<label class="col-sm-3 control-label" for="typeahead-local">Confirm
										Password<span class="required1"> *</span>
									</label>
									<div class="col-sm-5">
										<div class="input-group input-group-in">
											<span class="input-group-addon text-silver"></span> <input
												type="password" id="phyConfirmPassword"
												value="${userPhyForm.phyConfirmPassword}"
												name="phyConfirmPassword" class="form-control"
												placeholder="Confirm Password" required />
										</div>
									</div>
								</div>



								<div class="form-group">
									<label class="col-sm-3 control-label" for="typeahead-local">Password
										Recovery Question<span class="required1"> *</span>
									</label>
									<div class="col-sm-5">
										<select id="phyRecoveryQuestion" name="phyRecoveryQuestion"
											class="form-control"">
											<option value="What was your childhood nickname?">What
												was your childhood nickname?</option>
											<option value="In what city did you meet your spouse?">In
												what city did you meet your spouse?</option>
											<option value="What street did you live on in third grade?">What
												street did you live on in third grade?</option>
											<option
												value="What is the name of your favorite childhood friend?">What
												is the name of your favorite childhood friend?</option>

											<option value="In what city or town was your first job?">In
												what city or town was your first job?</option>


											<option value="What was your favorite sport in high school?">What
												was your favorite sport in high school?</option>
											<option
												value="What is the name of the High School you graduated from?">What
												is the name of the High School you graduated from?</option>
											<option value="What is your pet's name?">What is
												your pet's name?</option>
										</select>
									</div>

								</div>



								<div class="form-group">
									<label class="col-sm-3 control-label" for="typeahead-local">Password
										Recovery Answer<span class="required1"> *</span>
									</label>
									<div class="col-sm-5">
										<div class="input-group input-group-in">
											<span class="input-group-addon text-silver"></span> <input
												type="text" id="phyRecoveryAnswer" name="phyRecoveryAnswer"
												class="form-control"
												value="${userPhyForm.phyRecoveryAnswer}"
												placeholder="Recovery Answer" required />
										</div>
									</div>
								</div>


								<div id="searchButton"
									style="display: block; margin-left: 29%; border-bottom: 1px solid white;"
									class="panel-body">

									<input type="button" value="Register" class="btn btn-inverse"
										onclick="fnPhyRegisterBluehubUser();">
									<!-- <input
										type="button" value="Cancel" class="btn btn-inverse"
										onclick="fnCancelBluehubUser();"> -->
								</div>
							</div>

						</form>




					</div>

				</div>

			</div>


<jsp:include page="../footerbluehub.jsp"></jsp:include>


