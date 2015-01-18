function callLoginScreen() {			
	 /*document.userForm.action=contextPath+"/login.do";
	 document.userForm.submit();*/
	document.userForm.action=contextPath+"/userregistration.do";
	document.userForm.submit();
	
};

$(document).ready(function(){
	$('#uploadprocess').show();
	$('#uploadprocess').addClass("loader");
	$('#helpMenuId').hide();
	$('#helpMenuLogId').hide();	
	var t=setTimeout(function(){callLoginScreen();},5000)	
});