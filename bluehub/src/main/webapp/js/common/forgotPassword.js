window.onload = function() {

	var recoveryQuestion = $('#recoveryQuestion').val();

	if (recoveryQuestion == "") {
		document.getElementById("username").focus();
	} else {
		document.getElementById("recoveryAnswer").focus();
	}
}

function runScriptUsername(e) {
	if (e.keyCode == 13) {
		callCheckUserEmail();
	}
}
function callCheckUserEmail() {
	var mailId = $('#username').val().trim();

	if (mailId != "") {
		$
				.ajax({
					type : "GET",
					url : contextPath + "/checkUserEmail.do",
					dataType : "json",
					data : "userMail=" + mailId,
					success : function(response) {
						var obj = response;
						var status = obj.status;
						var message = obj.message;
						if (status == 'No') {
							document.getElementById("exception").innerHTML = message;
							
							document.getElementById("exception").style.display = "block";
							
							document.getElementById("recoveryQuestion").value = "";
							document.getElementById("recoveryAnswer").value = "";
							document.getElementById("cancelButton").style.display = "block";

							document.getElementById("questiondiv").style.display = "none";
							document.getElementById("answerdiv").style.display = "none";

							document.getElementById("searchButton").style.display = "none";
							fnSetTimeout("exception" ,6000);

						} else {

							fnSetSecurityQuestion(mailId);

							document.getElementById("questiondiv").style.display = "block";
							document.getElementById("answerdiv").style.display = "block";

							document.getElementById("searchButton").style.display = "block";
							document.getElementById("cancelButton").style.display = "none";

						}
					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
	} else {
		document.getElementById("username").value = "";
		document.getElementById("cancelButton").style.display = "block";

		document.getElementById("questiondiv").style.display = "none";
		document.getElementById("answerdiv").style.display = "none";

		document.getElementById("searchButton").style.display = "none";

	}
}

var displayMsgId = "";
function fnClearMsgField() {
	document.getElementById(displayMsgId).style.display = "none";
}
function fnSetTimeout(id, time) {
	if(time == undefined)
		time = 3000;
	displayMsgId = id;
	document.getElementById(displayMsgId).style.display = "block";
	setTimeout('fnClearMsgField()', time);
}
function fnSetSecurityQuestion(email) {

	$
			.ajax({
				type : "GET",
				url : contextPath + "/getForgetQuestion.do",
				dataType : "json",
				data : "userMail=" + email,
				success : function(response) {

					document.getElementById("recoveryQuestion").value = response.message;

				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});
}

function pageNavigation() {
	var recoveryQuestion = $('#recoveryQuestion').val();
	if (recoveryQuestion != "") {
		validateForm();
	} else {
		var validator = $("#forgotPassword").validate(
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
						username : "required",

						username : {
							required : true

						},
						recoveryAnswer : "required",

						recoveryAnswer : {
							required : true

						}
					},
					messages : {
						username : "Please enter your Username",

						username : {
							required : "Please enter your Username"
						},
						recoveryAnswer : "",

						recoveryAnswer : {
							required : "",
						}
					}

				});
		var validCheck = validator.form();
	}
};

function validateForm() {

	var validator = $("#forgotPassword").validate(
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
					username : "required",

					username : {
						required : true
					},
					recoveryAnswer : "required",

					recoveryAnswer : {
						required : true,

					}
				},
				messages : {
					username : "Please enter your Username",

					username : {
						required : "Please enter your Username"
					},
					recoveryAnswer : "Please enter your answer",

					recoveryAnswer : {
						required : "Please enter your answer"

					}
				}

			});

	var validCheck = validator.form();

	if (validCheck) {
		document.forgotPassword.action = contextPath
				+ "/checkForgetQuestion.do";
		document.forgotPassword.submit();
	}

};

$(document).ready(function() {
	$('#username').focus();
});