 

function fnOnclickRadio(i) {
	document.getElementById("practiceid_" + i).checked = true;

	document.getElementById("searchButton").style.display = "block";
}

function fnResetPassword() {
	document.getElementById('resetPwdBn').disabled = true;
	var radios = document.getElementsByName('selectRadio'), value = '';

	for ( var i = radios.length; i--;) {
		if (radios[i].checked) {
			value = radios[i].value;
			break;
		}
	}

	var selectedId = value;

	$.ajax({
		type : "GET",
		url : contextPath + "/resetUserPassword.do",
		dataType : "json",
		data : "userId=" + selectedId,
		cache : false,
		async : false,
		success : function(response) {
			var obj = response;

			var message = obj.message;

			flag = false;
			document.getElementById("status").style.display = "block";
			document.getElementById("status").innerHTML = message;
			fnSetTimeout("status", 3000);
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
var displayMsgId = "";
function fnClearMsgField() {
	document.getElementById(displayMsgId).style.display = "none";
}
function fnSetTimeout(id, time) {
	if (time == undefined)
		time = 3000;
	displayMsgId = id;
	document.getElementById(displayMsgId).style.display = "block";
	setTimeout('fnClearMsgField()', time);
}

function loadOrganization() {

	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/adminorganizations.do",

		cache : false,
		async : false,
		success : function(response) {

			var parsedJson = $.parseJSON(response);
			document.getElementById("selectOrganization").innerHTML = "";

			var combo1 = document.getElementById("selectOrganization");

			var defaultOption = document.createElement("option");
			defaultOption.text = 'Select';
			defaultOption.value = '';
			combo1.add(defaultOption);

			for ( var i = 0; i < parsedJson.length; i++) {
				var option = document.createElement("option");
				option.text = parsedJson[i].organizationname;
				option.value = parsedJson[i].id;

				combo1.add(option);
			}

			loadSearchTable();
		},
		error : function(e) {
			alert('Error: ' + e.responseText);
		}
	});

}
function loadPractice() {

	var orgid = document.getElementById('selectOrganization').value;

	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/loadpractice.do",
		data : "orgid=" + orgid,
		cache : false,
		async : false,
		success : function(response) {

			var parsedJson = $.parseJSON(response);
			document.getElementById("practiceSelect").innerHTML = "";

			var combo = document.getElementById("practiceSelect");

			var defaultOption = document.createElement("option");
			defaultOption.text = 'Select';
			defaultOption.value = '';
			combo.add(defaultOption);
			for ( var i = 0; i < parsedJson.length; i++) {
				var option = document.createElement("option");
				option.text = parsedJson[i].practicename;
				option.value = parsedJson[i].id;
				
				combo.add(option);
			}
		},
		error : function(e) {
			// alert('Error: ' + e.responseText);
		}
	});
}


function loadSearchTable() {
	var name;
	var organizationName;
	var practiceName;

	document.getElementById('physicianInformations').style.display = "block";
	document.getElementById('searchButton').style.display = "block";

	var content = '';
	content += '<table style="width: 100%" id="renderUserListTable" class="table table-hover table-bordered">';
	content += '<thead>';
	content += '<th></th>';
	content += '<th>Physician Name</th>';
	content += '<th>Organization</th>';
	content += '<th>Practice</th>';
	content += '<th>Status</th>';
	content += '<th>Role</th>';

	content += '</thead>';
	document.getElementById('physicianInformations').innerHTML = content;

	name = document.getElementById("physicianname").value;
	organizationName = document.getElementById("selectOrganization").value;
	practiceName = document.getElementById("practiceSelect").value;

	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/physicianDetails.do",
				data : {
					"firstName" : name,
					"organizationName" : organizationName,
					"practiceName" : practiceName
				},
				cache : false,
				async : false,
				success : function(response) {

					var parsedJson = $.parseJSON(response);

					var role;
					var roles;
					if (parsedJson) {
						for ( var i = 0; i < parsedJson.length; i++) {
							status = parsedJson[i].status;
							// alert(status)
							if (status == 0) {
								status = "Inactive";
							} else {
								status = "Active";
							}
							role = parsedJson[i].role;
							if(role=='Physician'){
								roles = 0;
							}else{
								roles = 1;
							}
							
							content += '<tr onclick="statusEnable('
									+ parsedJson[i].status + ',' + i + ',' + roles + ')">';
							content += '<td ><input id="selectOption'
									+ i
									+ '" type="radio" name="selectRadio" value="'
									+ parsedJson[i].userid
									+ '" onclick="statusEnable('
									+ parsedJson[i].status + ',' + i + ',' + roles + ')"></td>';
							content += '<td id="agencytd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].firstname + '</td>';
							content += '<td id="emailtd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].organizationName + '</td>';
							content += '<td id="addresstd' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].practicename + '</td>';
							content += '<td id="addresstd' + i
									+ '"  class="user_list_link">' + status
									+ '</td>';
							content += '<td id="roleid' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i].role + '</td>';
							content = content + '</tr>';
						}
					}

					
					content += '</table>';
					document.getElementById('physicianInformations').innerHTML = content;

				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});
}

function statusEnable(status,i,role) {
	document.getElementById('resetPwdBn').disabled = false;
	document.getElementById("selectOption" + i).checked = true;

	if (status == "1") {

		document.getElementById('disableButton').disabled = false;
		document.getElementById('enableButton').disabled = true;
	} else {

		document.getElementById('disableButton').disabled = true;
		document.getElementById('enableButton').disabled = false;
	}

	
	if(role=='0'){
		
		document.getElementById('unflagButton').disabled = true;
		document.getElementById('flagButton').disabled = false;
	}else{
		
		document.getElementById('unflagButton').disabled = false;
		document.getElementById('flagButton').disabled = true;
	}
}

function statusDisable() {
	document.getElementById('enableButton').disabled = false;
}

function statusDisableActivate() {
	var radios = document.getElementsByName('selectRadio'), value = '';

	for ( var i = radios.length; i--;) {
		if (radios[i].checked) {
			value = radios[i].value;
			break;
		}
	}
	var selectedId = value;

	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/physicianDisableStatus.do",
				data : "selectedId=" + selectedId,
				cache : false,
				async : false,
				success : function(response) {

					document.getElementById("status").style.display = "block";
					document.getElementById("status").innerHTML = "Physician Disabled Successfully ";
					loadSearchTable();
					document.getElementById('disableButton').disabled = true;
					document.getElementById('enableButton').disabled = true;
					//setTimeout('fnloadOrg()', 3000);
				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});

}

function fnloadOrg() {
	document.getElementById("status").style.display = "none";
}

function statusEnableActivate() {
	var radios = document.getElementsByName('selectRadio'), value = '';

	for ( var i = radios.length; i--;) {
		if (radios[i].checked) {
			value = radios[i].value;
			break;
		}
	}
	var selectedId = value;

	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/physicianEnableStatus.do",
				data : "selectedId=" + selectedId,
				cache : false,
				async : false,
				success : function(response) {

					document.getElementById("status").style.display = "block";
					document.getElementById("status").innerHTML = "Physician Enabled Successfully ";

					//setTimeout('fnloadOrg()', 3000);
					loadSearchTable();
					document.getElementById('disableButton').disabled = true;
					document.getElementById('enableButton').disabled = true;

				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});

}

function flagasPracticeAdmin() {
	var radios = document.getElementsByName('selectRadio'), value = '';

	for ( var i = radios.length; i--;) {
		if (radios[i].checked) {
			value = radios[i].value;
			break;
		}
	}
	var selectedId = value;
	if (selectedId != '') {
		$
				.ajax({
					type : "GET",
					url : contextPath
							+ "/administrator/physicianPracticeAdminStatus.do",
					data : "selectedId=" + selectedId,
					cache : false,
					async : false,
					success : function(response) {

						document.getElementById("status").style.display = "block";
						document.getElementById("status").innerHTML = "Physician flag as Practice admin Successfully ";
						loadSearchTable();
						document.getElementById('disableButton').disabled = true;
						document.getElementById('enableButton').disabled = true;
						document.getElementById('resetPwdBn').disabled = true;
						//setTimeout('fnloadOrg()', 3000);
					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
	}

}

function unFlagasPracticeAdmin() {
	var radios = document.getElementsByName('selectRadio'), value = '';

	for ( var i = radios.length; i--;) {
		if (radios[i].checked) {
			value = radios[i].value;
			break;
		}
	}
	var selectedId = value;
	if (selectedId != '') {
		$
				.ajax({
					type : "GET",
					url : contextPath
							+ "/administrator/physicianRemovePracticeAdminStatus.do",
					data : "selectedId=" + selectedId,
					cache : false,
					async : false,
					success : function(response) {

						document.getElementById("status").style.display = "block";
						document.getElementById("status").innerHTML = "Un Flag as Practice admin Successfully ";
						loadSearchTable();
						document.getElementById('disableButton').disabled = true;
						document.getElementById('enableButton').disabled = true;
						document.getElementById('resetPwdBn').disabled = true;
						
						//setTimeout('fnloadOrg()', 3000);
					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
	}

}

function loadAllPhysician() {

	document.getElementById("physicianInformations").style.display = "block";

	var content = '';
	content += '<div class="table-responsive table-responsive-datatables">';
	content += '<table style="width: 100%" class="tablesorter table table-hover table-bordered">';
	content += '<thead>';
	content += '<th></th>';
	content += '<th>Physician Name</th>';
	content += '<th>Organization</th>';
	content += '<th>Practice</th>';
	
	content += '</thead>';
	document.getElementById('physicianInformations').innerHTML = content;

	var orgid = document.getElementById('selectOrganization').value;

	var practiceid = document.getElementById('practiceSelect').value;


	$
			.ajax({
				type : "GET",
				url : contextPath
						+ "/administrator/adminallphysiciandetails.do",
				data : {
					orgid : orgid,
					practiceid : practiceid
				},
				cache : false,
				async : false,
				success : function(response) {

					if (response == "null" || response == "") {
						content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

						
						content += '</table>';
						document.getElementById('physicianInformations').innerHTML = content;

					} else {

						var parsedJson = $.parseJSON(response);
						for ( var i = 0; i < parsedJson.length; i++) {

							content += '<td ><input type="radio" onclick="statusEnable('
									+ parsedJson[i].userid
									+ ')" id="physicianRadio" value='
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
									+ parsedJson[i].practicename + '</td>';
							content = content + '</tr>';
						}

						
						content += '</table>';
						content += '</div>';
						document.getElementById('physicianInformations').innerHTML = content;

					}

				},
				error : function(e) {
					// alert('Error: ' + e.responseText);
				}
			});
}

function loadPhysicianRedirect() {

	var radios = document.getElementsByName('practiceid');
	value = '';

	for ( var i = radios.length; i--;) {
		if (radios[i].checked) {
			value = radios[i].value;
			break;
		}
	}
	var selectedId = value;

	$.ajax({
		type : "GET",
		url : contextPath + "/physician/loadphysiciandashboard.do",
		data : "id=" + selectedId,
		cache : false,
		async : false,
		success : function(response) {
			window.location.href = contextPath
					+ "/physician/physiciandashboard.do";

		},
		error : function(e) {
			alert('Error: ' + e.responseText);
		}
	});
}