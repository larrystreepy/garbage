

function commonRegistrationValidation() {
	var validator = $("#userForm").validate({
		rules : {
			userEmail : {
				required : true,
				email : {
					depends: function() {
                        return $("#myRole").val() != 'Child';
                    }
				}
			},
			password : "required",
			confirmPassword : {
				required : true,
				equalTo : "#password"
			},
			recoveryQuestion : "required",
			recoveryAnswer : "required",
			yourArea : "required",
			firstName : "required",
			lastName : "required"
		},
		messages : {
			userEmail : {
				required : "Email Id is Required",
				email : "Enter valid Email Id"
			},
			password : "Please enter Password",
			confirmPassword : {
				required : "Please enter Confirm Password",
				equalTo : "Password doesn't Match"
			},
			recoveryQuestion : "Please enter Recovery Question",
			recoveryAnswer : "Please enter Recovery Answer",
			firstName : "Please enter your First Name",
			lastName : "Please enter your Last Name"		
		},
		focusInvalid : false,
		invalidHandler : function(form, validator) {
			if (!validator.numberOfInvalids())
				return;

			$(validator.errorList[0].element).focus();
		}
	});
	$('#groupValid').hide();
	var validCheck = validator.form();

	return validCheck;
}


function callLoaderClass(){
$('#uploadprocess').show();
$('#uploadprocess').addClass("loader");
}
	

function pageNavigation() {

	var validCheck = null;
	validCheck = commonRegistrationValidation();
	if (validCheck) {
		callLoaderClass();
		$('#submit_btn').attr("disabled", true);
		document.userForm.action = contextPath + "/publicUserRegistrationSubmit.do";
		document.userForm.submit();
	}

};

function callCheckUserEmail() {
	// get the form values		        
	var mailId = $('#userEmail').val();
	if (mailId != "") {
		$.ajax({
			type : "GET",
			dataType : "json",
			url : contextPath + "/checkUserEmail.do",
			data : "userMail=" + mailId,
			success : function(response) {
				// we have the response		       
				var obj = response;
				var status = obj.status;
				var message = obj.message;
				if (status == 'Yes') {
					$('#emailValid').html(message);
					$('#emailValid').fadeIn().delay(4000).fadeOut('slow');
					document.getElementById("userEmail").value = "";
					document.getElementById("userEmail").focus();
				} else {
					$('#emailValid').html('');
					$('#emailValid').hide();
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}
}

function getUserRoles(userGroup) {
	$('#groupValid').hide();
	if (userGroup == "") {

		document.getElementById("myRole").innerHTML = "";
		var combo = document.getElementById("myRole");
		var defaultOption = document.createElement("option");
		defaultOption.text = 'Select';
		defaultOption.value = '';
		combo.add(defaultOption);

	

	} else {
		// get the form values		               
		$.ajax({
			type : "GET",
			url : contextPath + "/getUserRoles.do",
			data : "userGroup=" + userGroup,
			async : false,
			success : function(response) {

				var myRoleHidden = $('#myRoleHidden').val();
				var parsedJson = $.parseJSON(response);
				document.getElementById("myRole").innerHTML = "";

				var combo = document.getElementById("myRole");
				for ( var i = 0; i < parsedJson.length; i++) {
					var option = document.createElement("option");
					option.text = parsedJson[i];
					option.value = parsedJson[i];
					if (myRoleHidden == parsedJson[i]) {
						option.setAttribute("selected", "selected");
					}
					combo.add(option);
				}
			},
			error : function(e) {
				alert('Error: ' + e.responseText);
			}
		});
	}

}

