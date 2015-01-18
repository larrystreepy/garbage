<%@page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.bluehub.bean.user.UserProfile"%>
<%@page import="com.bluehub.vo.user.RoleVO"%>
<%@page import="javax.management.relation.Role"%>
<%@page import="com.bluehub.vo.user.UserRoleVO"%>
<%@page import="com.bluehub.vo.user.UserVO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/patient/patientrequestclinicalinformations.js"></script>

<script>
var contextPath = "<%=request.getContextPath()%>";
</script>
 
<style>
#organizationFaxDivId {
    margin-top: 7%;
}

#emailAndFaxDivId {
    margin-top: 7%;
}
</style>

<div class="content-body">

	<input type="hidden" id="hdnPhysicianId" name="hdnPhysicianId">
	
	<input type="hidden" id="hdnrequestType" name="hdnrequestType">
	
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
			<h3 class="panel-title">Request Clinical Information</h3>
		</div>

		<div class="panel-body">
			<form role="form" name="form" class="form-horizontal form-bordered">

                           <div class="modal fade" id="modalBasic" tabindex="-1" role="dialog" aria-labelledby="modalBasicLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="modalBasicLabel">Signature</h4>
                                        </div>
                                        <div class="modal-body">
                                            <input type="text"
										id="digitalSign" name="digitalSign" class="form-control"
										placeholder="Type Text Here"/>

						<div id="emailSuccess2"
										style="display: none; color: #da4f49; margin-left: 30%;"></div>


							 

                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                            <button onclick="fnCreateSignature()" type="button" class="btn btn-primary">Save</button>
                                        </div>
                                    </div><!-- /.modal-content -->
                                </div><!-- /.modal-dialog -->
                            </div>



				<div id="organizationlabel" >
					

					<!-- <div class="form-group" id="practicelabel" style="display: none">
						<label class="col-sm-3 control-label" for="typeahead-local">Practice</label>


						<div class="col-sm-5">
							<select id="selectPractice" class="form-control">
								
							</select>
						</div>
						
					</div> -->


					<div id="physicianlabel" style="display: block;">
						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Physician
								Name</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="physicianname" name="physicianname" class="form-control"
										placeholder="Physician Name" onkeyup="loadPhysicianTable()" />

								</div>

							</div>
						</div>

					</div>


					<div style="margin: 3% 0% 3% 40%; display: block;" id="ORSPANID">OR</div>
					
					<div class="form-group" style="display: block;">
						<label class="col-sm-3 control-label" for="typeahead-local">Organization</label>
						<div class="col-sm-5">
							<select class="form-control" id="selectOrganization"
								onchange="loadOrgDetailsForEfax()">
							</select>
						</div>
					</div>
					
					<!-- <div id="emaillabel" style="display: block">
						<div class="form-group">
							<label class="col-sm-3 control-label" for="typeahead-local">Enter
								Email Address</label>
							<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="mailid" name="mailid" class="form-control"
										placeholder="Mail Id" onkeyup="fnOnchangeMailId()" />

								</div>
								<div id="emailSuccess" style="display: none; color: #da4f49;"></div>
							</div>
						</div>

					</div> -->

			</div>


				<div class="physicianInformations" id="physicianDetails"
					style="display: none">
					 

							<div class="table-responsive table-responsive-datatables">

								<table class="table table-striped table-bordered"
									id="DataTables_Table_1">
									<thead>
										<tr>
											<th>Physician Name</th>
											<th>Organization</th>
											<th>Practice</th>
										</tr>
									</thead>

									<tbody>

									</tbody>
								</table>

							</div>
				</div>


				<div id="emailAndFaxDivId" style="display: none;"> 
				
				<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Physician Email</label>
						<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="physicianEmail" name="physicianEmail" class="form-control"
										placeholder="Physician Email" />

								</div>

							</div>
					</div>
					
					
					<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Physician Fax</label>
						<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="physicianFax" name="physicianFax" class="form-control"
										placeholder="Physician Fax" />

								</div>

							</div>
					</div>
					
				
				</div>
				
					<div id="organizationFaxDivId" style="display: none;"> 
					
						<div class="form-group">
						<label class="col-sm-3 control-label" for="typeahead-local">Organization Fax</label>
						<div class="col-sm-5">
								<div class="input-group input-group-in">
									<span class="input-group-addon text-silver"><i
										class="glyphicon glyphicon-user"></i></span> <input type="text"
										id="orgFax" name="orgFax" class="form-control"
										placeholder="Organization Fax" />

								</div>

							</div>
					</div>
					
					</div>

				<div id="emailSuccess1"
					style="display: none; color: #da4f49; margin-left: 26%;"></div>
					
					

	<p style="color: #da4f49; margin-left: 30%;margin-top: 7%;">*Please digitally sign the HIPAA release form to request your information</p>



				<div id="buttonlabel" style="margin-left: 37%; display:none; margin-top: 6%;">
					<div class="form-group">
					
			 	<button type="button" class="btn btn-inverse" 
						data-toggle="modal" data-target="#modalBasic">Create Digital Signature</button>  
					
					
						<input type="button" size="25" id="searchBtn" name="searchBtn"
							value="Send Request" class="btn btn-inverse"
							onclick="patientRequestClinicalInfoByFaxAndMail()" />

					</div>
				</div>

 

			 


			</form>
		</div>
	</div>







</div>

<script>

loadOrganizations();
                          
var contextPath = "<%=request.getContextPath()%>";
</script>

<%
	UserProfile user = (UserProfile) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();

	String role = user.getRoleid();
	Integer userId = user.getId();
%>

<jsp:include page="../footerbluehub.jsp"></jsp:include>