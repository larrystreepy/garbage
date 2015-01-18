function practiceValidation() {

	var validator = $("#adminPracticeForm").validate(
			{
				focusCleanup : true,
				errorClass : "text-danger",
				errorPlacement : function(error, element) {
					if (element.parent().hasClass('nice-checkbox')
							|| element.parent().hasClass('nice-radio')
							|| element.parent().hasClass('input-group')) {
						error.appendTo(element.parent().parent());
					} else {
						error.appendTo(element.parent());
					}
				},
				rules : {

					practicename : {
						required : true,
					},
					address : {
						required : true,
					},
					city : {
						required : true,
					},
					state : {
						required : true,
					},
					zipcode : {
						required : true,
					}

				},
				messages : {

					practicename : "Please enter Practice name",

					address : "Please enter Address",

					city : "Please enter City",

					state : "Please enter State",

					zipcode : "Please enter Zipcode",

				}

			});

	var validCheck = validator.form();

	return validCheck;
}

function checkPracticeName() {
	var name = $('#practicename').val();
	var orgId = $('#selectOrganization').val();
	if(name!=""){
	ValidatePracticeName();
	
	
	if (name != "") {
		$
				.ajax({
					type : "GET",
					url : contextPath + "/administrator/checkpracticename.do",
					data : "practicename=" + name + "&orgId=" +orgId,
					success : function(response) {
						// we have the response
						var obj = $.parseJSON(response);
						var status = obj.status;
						var message = obj.message;
						if (status == 'Yes') {
							document.getElementById('orgnameValid').style.display = "block";
							$('#orgnameValid').html(message);
							$('#orgnameValid').fadeIn().delay(4000).fadeOut(
									'slow');
							document.getElementById("practicename").value = "";
							document.getElementById("practicename").focus();
						} else {
							$('#orgnameValid').html('');
							$('#orgnameValid').hide();
							document.getElementById('orgnameValid').style.display = "none";
						}
					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
	}
	
	}
}

function ValidatePracticeName() {
	var totalCharacterCount = document.getElementById("practicename").value;

	var strValidChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
	var strChar;
	var FilteredChars = "";
	for (i = 0; i < totalCharacterCount.length; i++) {
		strChar = totalCharacterCount.charAt(i);
		if (strValidChars.indexOf(strChar) != -1) {
			FilteredChars = FilteredChars + strChar;
		}
	}
	document.getElementById("practicename").value = FilteredChars;
	return false;
}

function editPracticeValidation() {

	var validator = $("#updateAdminPracticeForm").validate(
			{
				focusCleanup : true,
				errorClass : "text-danger",
				errorPlacement : function(error, element) {
					if (element.parent().hasClass('nice-checkbox')
							|| element.parent().hasClass('nice-radio')
							|| element.parent().hasClass('input-group')) {
						error.appendTo(element.parent().parent());
					} else {
						error.appendTo(element.parent());
					}
				},
				rules : {

					editpracticename : {
						required : true,
					}

				},
				messages : {

					editpracticename : "Please enter Practice name",

				}

			});

	var validCheck = validator.form();

	return validCheck;
}
function saveAdminPractice() {

	validCheck = practiceValidation();

	if (validCheck) {

		var practicename = $('#practicename').val();
		var orgid = $('#selectOrganization').val();

		var address = $('#address').val();

		var city = $('#city').val();
		var state = $('#state').val();
		var zipcode = $('#zipcode').val();

		$.ajax({
			type : "GET",
			url : contextPath + "/administrator/saveadminpractice.do",
			data : {
				practicename : practicename,
				orgid : orgid,
				address : address,
				city : city,
				state : state,
				zipcode : zipcode
			},
			
			success : function(response) {
				window.location.href = contextPath
						+ "/administrator/adminpractice.do";

			},
			error : function(e) {
				// alert('Error: ' + e);
			}
		});

		return;
	}
}

function runSaveAdminPractice(e) {
	if (e.keyCode == 13) {
		saveAdminPractice();
	}
}



function loadPractices() {
	if (document.getElementById("panel-typeahead")) {
		document.getElementById("panel-typeahead").style.display = "none";
	}
	$('#adminPracticetable')
			.dataTable(
					{

						"processing" : true,
						"bJQueryUI" : true,
						"bFilter" : false,
						"bDestroy" : true,
						"bServerSide" : true,
						"sAjaxSource" : contextPath
								+ "/administrator/adminpracticelist.do",
						"bProcessing" : true,
						"aoColumns" : [
								{
									"mDataProp" : "Organization",
									"bSortable" : false
								},
								{
									"mDataProp" : "Practice",
									"bSortable" : false
								},

								{
									"mDataProp" : "Address",
									"bSortable" : false
								},
								{
									"mDataProp" : "City",
									"bSortable" : false
								},
								{
									"mDataProp" : "State",
									"bSortable" : false
								},
								{
									"mDataProp" : "Zipcode",
									"bSortable" : false
								},
								{
									"mDataProp" : "Actions",
									"bSortable" : false,

									"fnRender" : function(oObj) {

										var id = oObj.aData['id'];
										return '<input type="button" title="Delete Practice" value="" id="delDocRow" class="del_row" onclick="deletePracticeRow('
												+ id
												+ ')"/> &nbsp;&nbsp;&nbsp;&nbsp; <input type="button" title="Edit Practice" value="" id="editDocRow" class="editIcon" onclick="editPractice('
												+ id + ');"/>'

									}
								}

						]

					// "aaSorting": [[ 2, 'desc' ]]

					});

}

function loadAdminPracticeList() {
	loadPractices();
	

}

function fnloadOrg() {
	window.location.href = contextPath + "/administrator/adminpractice.do";
}
function deletePracticeRow(id) {
	var answer = confirm("Are you sure want to delete the practice ?")
	if (answer) {
		deletePractice(id);
	}
	return false;
}
function deletePractice(id) {

	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/deleteadminpractice.do",
				data : "practiceid=" + id,
				success : function(response) {

					document.getElementById("deleteOrg").innerHTML = "Practice Successfully Deleted";
					setTimeout('fnloadOrg()', 1000);
					
				},
				error : function(e) {
					// alert('Error: ' + e);
				}
			});

}

function editPractice(id) {

	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/editadminpractice.do",
				cache : false,
				data : "orgid=" + id,
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);

					document.getElementById("panel-typeahead").style.display = "block";
					document.getElementById('editorganizationname').value = parsedJson['organizationname'];
					document.getElementById('editpracticename').value = parsedJson['practicename'];
					document.getElementById('editaddress').value = parsedJson['address'];
					document.getElementById('editcity').value = parsedJson['city'];
					document.getElementById('editstate').value = parsedJson['state'];
					document.getElementById('editzipcode').value = parsedJson['zipcode'];
					
					document.getElementById('practiceid').value = parsedJson['id'];

				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});

}

function cancelPractice() {
	document.getElementById("panel-typeahead").style.display = "none";
}

function updateAdminPractice() {

	validCheck = editPracticeValidation();

	if (validCheck) {

		var practicename = $('#editpracticename').val();
		var practiceid = $('#practiceid').val();

		var address = $('#editaddress').val();

		var city = $('#editcity').val();
		var state = $('#editstate').val();
		var zipcode = $('#editzipcode').val();

		$
				.ajax({
					type : "GET",
					url : contextPath + "/administrator/updateadminpractice.do",
					cache : false,
					data : {
						"practicename" : practicename,
						"practiceid" : practiceid,
						"address" : address,
						"city" : city,
						"state" : state,
						"zipcode" : zipcode
					},
					async : false,
					success : function(response) {
						document.getElementById("deleteOrg").innerHTML = "Practice Successfully Updated ";
						setTimeout('fnloadOrg()', 1000);
					},
					error : function(e) {
						// alert('Error: ' + e.responseText);
					}
				});
	}
}

function runUpdateAdminPractice(e) {
	if (e.keyCode == 13) {
		updateAdminPractice();
	}
}



function adminAuditTrialUserTypeChange() {

	var x = document.getElementById("mySelect").value;
	if (x == 1) {

		$.ajax({
			type : "GET",
			url : contextPath + "/administrator/adminorganizations.do",
			cache : false,
			async : false,
			success : function(response) {

				var parsedJson = $.parseJSON(response);
				document.getElementById("selectOrganization").innerHTML = "";

				var combo = document.getElementById("selectOrganization");
				for ( var i = 0; i < parsedJson.length; i++) {
					var option = document.createElement("option");
					option.text = parsedJson[i].organizationname;
					option.value = parsedJson[i].id;
					combo.add(option);
				}
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});

		document.getElementById('physicianlabel').style.display = 'block';
		document.getElementById('organizationlabel').style.display = 'block';
		document.getElementById('practiceDiv').style.display = 'block';
		document.getElementById('patientlabel').style.display = 'none';
		document.getElementById('physicianDetails').style.display = "none";
		document.getElementById('patientDetails').style.display = "none";
	} else if (x == 2) {
		document.getElementById('patientlabel').style.display = 'block';
		document.getElementById('physicianlabel').style.display = 'none';
		document.getElementById('organizationlabel').style.display = 'none';
		document.getElementById('practiceDiv').style.display = 'none';
		document.getElementById('physicianDetails').style.display = "none";
		document.getElementById('patientDetails').style.display = "none";

	} else {

		document.getElementById('physicianlabel').style.display = 'none';
		document.getElementById('organizationlabel').style.display = 'none';
		document.getElementById('practiceDiv').style.display = 'none';
		document.getElementById('patientlabel').style.display = 'none';
		document.getElementById('physicianDetails').style.display = "none";
		document.getElementById('patientDetails').style.display = "none";

	}
}

function loadAuditTrialPractice() {

	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/loadpractice.do",
		// data : "userGroup=" + userGroup,
		cache : false,
		async : false,
		success : function(response) {

			var parsedJson = $.parseJSON(response);
			document.getElementById("selectPractice").innerHTML = "";

			var combo = document.getElementById("selectPractice");

			for ( var i = 0; i < parsedJson.length; i++) {
				var option = document.createElement("option");
				option.text = parsedJson[i].practicename;
				option.value = parsedJson[i].id;

				combo.add(option);
			}
		},
		error : function(e) {
			alert('Error: ' + e.responseText);
		}
	});

}

function loadAuditTrialPhysicianTable() {
	document.getElementById('physicianDetails').style.display = "block";
	document.getElementById('patientDetails').style.display = "none";

	var content = '';
	content += '<div class="table-responsive table-responsive-datatables">';
	content += '<table style="width: 100%" class="tablesorter table table-hover table-bordered">';
	content += '<thead>';
	content += '<th></th>';
	content += '<th>Physician Name</th>';
	content += '<th>Organization</th>';
	content += '<th>Practice</th>';
	content += '</thead>';
	document.getElementById('physicianDetails').innerHTML = content;
	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/adminphysiciandetails.do",
				// data : "userGroup=" + userGroup,
				cache : false,
				async : false,
				// dataType: "json",
				success : function(response) {

					var parsedJson = $.parseJSON(response);

					
					for ( var i = 0; i < parsedJson.length; i++) {

						
						content += '<td ><input type="radio" id="physicianRadio" value='
								+ parsedJson[i].userid
								+ ' name="physicianRadio"></td>';
						content += '<td id="agencytd' + i
								+ '"  class="user_list_link">'
								+ parsedJson[i].firstname + '</td>';
						content += '<td id="emailtd' + i
								+ '"  class="user_list_link">'
								+ parsedJson[i].organizationName + '</td>';
						content += '<td id="addresstd' + i
								+ '"  class="user_list_link">'
								+ parsedJson[i].userid + '</td>';
						content = content + '</tr>';
					}

					content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>'
					content += '</table>';
					content += '</div>';
					document.getElementById('physicianDetails').innerHTML = content;

					// }

				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});

}

function loadAuditTrialPatientsTable() {

	document.getElementById('physicianDetails').style.display = "none";
	document.getElementById('patientDetails').style.display = "block";

	var content = '';
	content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
	content += '<thead>';
	content += '<th></th>';
	content += '<th>Patient Name</th>';
	content += '<th>DOB</th>';
	content += '<th>Address</th>';

	content += '</thead>';
	document.getElementById('patientDetails').innerHTML = content;

	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/adminpatientdetails.do",
				// data : "userGroup=" + userGroup,
				cache : false,
				async : false,
				success : function(response) {

					var parsedJson = $.parseJSON(response);

					if (parsedJson != null) {

						for ( var i = 0; i < parsedJson.length; i++) {

							content += '<td ><input type="radio" id="patientRadio" value='
									+ parsedJson[i].userid
									+ ' name="patientRadio"></td>';
							content += '<td id="agencytd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].firstname + '</td>';
							content += '<td id="emailtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].dateofbirth + '</td>';
							content += '<td id="addresstd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].address + '</td>';
							content = content + '</tr>';
						}

						content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>'
						content += '</table>';
						document.getElementById('patientDetails').innerHTML = content;
					}

				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});

}

function loadOrganization() {

	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/adminorganizations.do",
		// data : "userGroup=" + userGroup,
		cache : false,
		async : false,
		success : function(response) {

			var parsedJson = $.parseJSON(response);
			document.getElementById("selectOrganization").innerHTML = "";

			var combo = document.getElementById("selectOrganization");
			for ( var i = 0; i < parsedJson.length; i++) {
				var option = document.createElement("option");
				option.text = parsedJson[i].organizationname;
				option.value = parsedJson[i].id;
				
				combo.add(option);
			}

		},
		error : function(e) {
			//alert('Error: ' + e.responseText);
		}
	});
}
