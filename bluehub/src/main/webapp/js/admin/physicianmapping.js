
function fnLoadOrganization() {
	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/loadorganization.do",
		cache : false,
		async : false,
		success : function(response) {
			var parsedJson = $.parseJSON(response);
			var combo = document.getElementById("selectOrganization");

			var defaultOption = document.createElement("option");
			defaultOption.text = 'Select';
			defaultOption.value = '';
			combo.add(defaultOption);
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
			document.getElementById("selectPractice").innerHTML = "";

			var combo = document.getElementById("selectPractice");

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
			//alert('Error: ' + e.responseText);
		}
	});
}

function clearPhysicianMapping() {

	$('#physicianname').val("");

	$('#hdnMappingId').val("");
	$('#selectOrganization').val("");
	$('#selectPractice').val("");

}
function fnValidatePhysicianMapping() {
	var flag = true;

	var user = $('#physicianname').val();

	if (user == "") {
		flag = false;
	} else {
		var value = user.split('_');

		if (value[1] == undefined || value[1] == "") {

			flag = false;
		} else {

			flag = true;

		}

	}
	if (!flag) {
		document.getElementById("success_msg_span1").innerHTML = "Please Choose the Physician";

		fnSetTimeout("success_msg_span1", 3000);
	}

	return flag;
}

function fnCheckExistingUser() {

	var organization = $('#selectOrganization').val();
	var practice = $('#selectPractice').val();
	var user = $('#physicianname').val();
	var flag = false;
	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/checkExistingPhysician.do",
		cache : false,
		async : false,
		data : "organizationId=" + organization + "&practiceId=" + practice
				+ "&userName=" + user,
		success : function(response) {
			var parsedJson = $.parseJSON(response);

			var msg = parsedJson.message;
			var status = parsedJson.status;

			if (status == "YES") {
				flag = true;
			} else {

				flag = false;

				document.getElementById("success_msg_span").innerHTML = msg;
				$('#physicianname').val("");
			}

			fnSetTimeout("success_msg_span", 3000);

		},
		error : function(e) {
			alert('Error: ' + e.responseText);
		}
	});

	return flag;
}

function savePhysicianMapping() {

	var organization = $('#selectOrganization').val();
	var practice = $('#selectPractice').val();
	var user = $('#physicianname').val();

	var mappingId = $("#hdnMappingId").val();

	var validcheck = fnValidatePhysicianMapping();

	if (validcheck) {

		if (organization == "" || practice == "") {
			document.getElementById("success_msg_span").innerHTML = "Select Organization and Practice";
			fnSetTimeout("success_msg_span", 3000);
		} else {
			var exist = fnCheckExistingUser();

			if (exist) {
				if (user == "") {

					user = "example_0";
				}
				$
						.ajax({
							type : "GET",
							url : contextPath
									+ "/administrator/savephysicianmapping.do",
							cache : false,
							async : false,
							data : "organizationId=" + organization
									+ "&practiceId=" + practice + "&userName="
									+ user + "&mappingId=" + mappingId,
							success : function(response) {
								var parsedJson = $.parseJSON(response);

								var msg = parsedJson.message;

								document.getElementById("success_msg_span").innerHTML = msg;

								fnLoadhysicianmapping();
								clearPhysicianMapping();
								fnSetTimeout("success_msg_span", 3000);

							},
							error : function(e) {
								alert('Error: ' + e.responseText);
							}
						});
			}
		}

	}

}


function fnLoadhysicianmapping() {
	$('#DataTables_Table_1')
			.dataTable(
					{

						"processing" : true,
						"bJQueryUI" : true,
						"bFilter" : false,
						"bDestroy" : true,
						"bAutoWidth" : false,
						//  	"sDom": '<"top"i>rt<"bottom"flp><"clear">',
						"bServerSide" : true,
						"sAjaxSource" : contextPath
								+ "/administrator/physicianmappinglist.do",
						"bProcessing" : true,
						"aoColumns" : [
								{
									"mDataProp" : "Physician Name",
									"bSortable" : false,
									"sWidth" : '9%'
								},
								{
									"mDataProp" : "Organization",
									"bSortable" : false,
									"sWidth" : '9%'
								},
								{
									"mDataProp" : "Practice",
									"bSortable" : false,
									"sWidth" : '9%'
								},
								{
									"mDataProp" : "Actions",
									"sWidth" : '1%',
									"bSortable" : false,
									"fnRender" : function(oObj) {

										var mapId = oObj.aData['id'];

										return '<input type="button" value="" id="delDocRow" class="del_row" title="Delete Physician Mapping" onclick="deletePhysicianmapping('
												+ mapId
												+ ')"/> &nbsp;&nbsp;&nbsp<input type="button" value="" id="editDocRow" class="editIcon" title="Edit Physician Mapping" onclick="editPhysicianmapping('
												+ mapId + ');"/>'
									}

								} ]

					//	  "aaSorting": [[ 2, 'desc' ]]

					});

}
function editPhysicianmapping(id) {

	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/editphysicianmapping.do",
		dataType : "json",
		data : "mappingId=" + id,
		success : function(response) {

			$("#hdnMappingId").val(response[0][0]);
			$("#physicianname").val(response[0][1]);
			$("#selectOrganization").val(response[0][2]);

			loadPractice();

			$("#selectPractice").val(response[0][3]);
		},
		error : function(e) {
			//alert('Error: ' + e);
		}
	});

}

function deletePhysicianmapping(id) {

	$
			.ajax({
				type : "GET",
				url : contextPath + "/administrator/deletephysicianmapping.do",
				dataType : "json",
				data : "mappingId=" + id,
				success : function(response) {
					document.getElementById("success_msg_span").innerHTML = response.message;

					fnLoadhysicianmapping();
					fnSetTimeout("success_msg_span", 3000);
				},
				error : function(e) {
					//alert('Error: ' + e);
				}
			});

}

function fnLoadPhysicians() {

	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/loadPhysicians.do",
		dataType : "json",
		success : function(response) {


			fnAutocompleteNew("physicianname", response);
		},
		error : function(e) {
			//alert('Error: ' + e);
		}
	});
}


function fnAutocompleteNew(autocomTxtFieldId, autocompleteList) {
	var stmt = '<datalist id="' + autocomTxtFieldId
			+ 'HalfAutoCompleteDataListId">';
	for ( var pos in autocompleteList) {
		stmt += '<option value="' + autocompleteList[pos] + '"></option>';
	}
	stmt += '</datalist>';
	var autocompleteInputTag = document.getElementById(autocomTxtFieldId);
	if (document.getElementById(autocomTxtFieldId
			+ 'HalfAutoCompleteDataListDivId')) {
		var prevAutocompleteDataListTag = document
				.getElementById(autocomTxtFieldId
						+ 'HalfAutoCompleteDataListDivId');
		prevAutocompleteDataListTag.parentNode
				.removeChild(prevAutocompleteDataListTag);
	}
	var autocompleteDivTag = document.createElement("div");
	autocompleteDivTag.setAttribute("id", autocomTxtFieldId
			+ "HalfAutoCompleteDataListDivId");
	autocompleteInputTag.appendChild(autocompleteDivTag);
	document
			.getElementById(autocomTxtFieldId + "HalfAutoCompleteDataListDivId").innerHTML = stmt;
	document.getElementById(autocomTxtFieldId).setAttribute("list",
			autocomTxtFieldId + 'HalfAutoCompleteDataListId');
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
	//setTimeout('fnClearMsgField()', time);
}