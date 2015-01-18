function callCheckPassword() {
	var password = $('#currentPassword').val();
	$.ajax({
		type : "GET",
		url : contextPath + "/checkUserPassword.do",
		dataType : "json",
		data : "userPassword=" + password,
		success : function(response) {
			var obj = response;
			var status = obj.status;
			var message = obj.message;
			if (status == 'No') {
				document.getElementById("exception").innerHTML = message;
				document.getElementById("currentPassword").value = "";
				document.getElementById("currentPassword").focus();
			} else {
				a
				document.getElementById("exception").innerHTML = "";
			}
		},
		error : function(e) {
		}
	});
}
function pageNavigation() {
	
	callCheckPassword();

	var validator = $("#changePassword").validate(
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
					password : "required",

					currentPassword : {
						required : true,
					},
					newpassword : {
						required : true,
						equalTo : "#password"
					}
				},
				messages : {
					currentPassword : {
						required : "Please enter your Current Password",
					},
					password : "Please enter your New Password",
					newpassword : {
						required : "Please Retype your New Password",
						equalTo : "Password Mismatch"
					}
				}

			});

	var validCheck = validator.form();

	if (validCheck) {
		document.changePassword.action = contextPath
				+ "/changePasswordSubmit.do";
		document.changePassword.submit();
	} else {

	}
};

$(document).ready(function() {
	$('#currentPassword').focus();
});