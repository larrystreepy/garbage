
function showCountryName() {

	
	var zipcode = $('#zipcode').val();
	

	$.ajax({
				type : "GET",
				url : "http://maps.googleapis.com/maps/api/geocode/json?address="+zipcode+"&sensor=true",
				cache : false,
				async : false,
				success : function(response) {
alert(response);
					
				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});

}

function deletePractice(id) {

	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/deleteadminpractice.do",
		data : "orgid=" + id,
		success : function(response) {
			window.location.href=contextPath+ "/administrator/adminpractice.do";
			/*	var obj = response;
				var status = obj.status;
				var message = obj.message;
				if (status == 'No') {
					document.getElementById("exception").innerHTML = message;
					
					
				} else {
					document.getElementById("exception").innerHTML = ""; alert('in');
					
				}*/
		},
		error : function(e) {
			//alert('Error: ' + e);
		}
	});
	
	
}

function editPractice(id) {

	$.ajax({
				type : "GET",
				url : contextPath + "/administrator/editadminpractice.do",
				cache : false,
				data : "orgid=" + id,
				async : false,
				success : function(response) {
					var parsedJson = $.parseJSON(response);
					document.getElementById("panel-typeahead2").style.display = "block";
					document.getElementById('editpracticename').value = parsedJson['practicename'];
					/*document.getElementById('hidpracticename').value = parsedJson['practicename'];
					alert(document.getElementById('hidpracticename').value)*/
					document.getElementById('practiceid').value = parsedJson['id'];

				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});

}

function updateAdminPractice() {

	validCheck = editPracticeValidation();

	if (validCheck) {
		
	
	var practicename = $('#editpracticename').val();
	var practiceid = $('#practiceid').val();

	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/updateadminpractice.do",
		cache : false,
		data : {
			"practicename" : practicename,
			"practiceid" : practiceid
		},
		async : false,
		success : function(response) {
			window.location.href=contextPath+ "/administrator/adminpractice.do";
		},
		error : function(e) {
			//alert('Error: ' + e.responseText);
		}
	});
	}
}


function runUpdateAdminPractice(e) {
    if (e.keyCode == 13) {
    	updateAdminPractice();
    }
}

 /*  *********************************Audit Trial functions **************************************************/

function adminAuditTrialUserTypeChange() {

	var x = document.getElementById("mySelect").value;
	 if (x == 1) {

		$.ajax({
					type : "GET",
					url : contextPath+ "/administrator/adminorganizations.do",
					cache : false,
					async : false,
					success : function(response) {

						//var myRoleHidden = $('#myRoleHidden').val();
						var parsedJson = $.parseJSON(response);
						document.getElementById("selectOrganization").innerHTML = "";

						var combo = document.getElementById("selectOrganization");
						for (var i = 0; i < parsedJson.length; i++) {
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
		document.getElementById('physicianDetails').style.display="none";
		document.getElementById('patientDetails').style.display="none";
	}else if (x == 2) {
		document.getElementById('patientlabel').style.display = 'block';
		document.getElementById('physicianlabel').style.display = 'none';
		document.getElementById('organizationlabel').style.display = 'none';
		document.getElementById('practiceDiv').style.display = 'none';
		document.getElementById('physicianDetails').style.display="none";
		document.getElementById('patientDetails').style.display="none";
		

	} else {
	
		document.getElementById('physicianlabel').style.display = 'none';
		document.getElementById('organizationlabel').style.display = 'none';
		document.getElementById('practiceDiv').style.display = 'none';
		document.getElementById('patientlabel').style.display = 'none';
		document.getElementById('physicianDetails').style.display="none";
		document.getElementById('patientDetails').style.display="none";

	}
}

function loadAuditTrialPractice(){

	 $.ajax({
		   type : "GET",
		   url : contextPath + "/administrator/loadpractice.do",
		  // data : "userGroup=" + userGroup,
		   cache: false,
		   async: false,
		   success : function(response) {

		    //var myRoleHidden = $('#myRoleHidden').val();
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


function loadAuditTrialPhysicianTable(){
document.getElementById('physicianDetails').style.display="block";
document.getElementById('patientDetails').style.display="none";
	
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
	$.ajax({
		   type : "GET",
		   url : contextPath + "/administrator/adminphysiciandetails.do",
		  // data : "userGroup=" + userGroup,
		   cache: false,
		    async: false,		
			//dataType: "json",
		   success : function(response) {
			   
		    var parsedJson =  $.parseJSON(response);

			 /* alert("sdfsdfsdfsdfsd");
			 alert(parsedJson); */
		   //alert("parsedJson.length : "+parsedJson.length)
				for(var i=0; i<parsedJson.length; i++){
							
					//alert(parsedJson[i].userName)
					//content = content + '<tr onclick="displayUserData(\''+parsedJson[i].userId+'\',\''+i+'\',\''+obj.length+'\')">';
					content += '<td ><input type="radio" id="physicianRadio" value='+parsedJson[i].userid+' name="physicianRadio"></td>';
					content += '<td id="agencytd'+i+'"  class="user_list_link">'+parsedJson[i].firstname+'</td>';
					content += '<td id="emailtd'+i+'"  class="user_list_link">'+parsedJson[i].organizationName+'</td>';					
					content += '<td id="addresstd'+i+'"  class="user_list_link">'+parsedJson[i].userid+'</td>';
					content = content + '</tr>'; 
				}
			
				content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>'
				content += '</table>';
				content += '</div>';
				document.getElementById('physicianDetails').innerHTML = content;
				
				//}

			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});

}

function loadAuditTrialPatientsTable() {

	document.getElementById('physicianDetails').style.display="none";
	document.getElementById('patientDetails').style.display="block";
		
	var content = '';
	content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
	content += '<thead>';
	content += '<th></th>';
	content += '<th>Patient Name</th>';
	content += '<th>DOB</th>';
	content += '<th>Address</th>';
	
	content += '</thead>';
	document.getElementById('patientDetails').innerHTML = content;

	
	$.ajax({
		type : "GET",
		url : contextPath + "/administrator/adminpatientdetails.do",
		// data : "userGroup=" + userGroup,
		cache : false,
		async : false,
		success : function(response) {

			var parsedJson =  $.parseJSON(response);
			
		   if(parsedJson!=null){

			   for(var i=0; i<parsedJson.length; i++){
					
					
					content += '<td ><input type="radio" id="patientRadio" value='+parsedJson[i].userid+' name="patientRadio"></td>';
					content += '<td id="agencytd'+i+'"  class="user_list_link">'+parsedJson[i].firstname+'</td>';
					content += '<td id="emailtd'+i+'"  class="user_list_link">'+parsedJson[i].dateofbirth+'</td>';					
					content += '<td id="addresstd'+i+'"  class="user_list_link">'+parsedJson[i].address+'</td>';
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