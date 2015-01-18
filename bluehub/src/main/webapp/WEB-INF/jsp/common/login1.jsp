<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>BluehubLogin - Bluehub</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.validate.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/login.css" />
<script>   

        $(document).ready(function(){
        	 $('#username').focus();
        	 });
       
        
        function callLoginValid() {    		        	        	
    		// validation
    		var validator = $("#loginForm").validate({
    			rules : {
    				
    				j_username : {
    					required: true
    				},
    				j_password : "required"  				
    			},
    			messages : {    	    				
    				j_username : {
    					required: "Please enter User Name"
    				},
    				j_password : "Please enter Password"    			
    			},
    			  focusInvalid: false,
    			  invalidHandler: function(form, validator) {
    			     if (!validator.numberOfInvalids())
    	      			return;
    			     
    			    $(validator.errorList[0].element).focus();
    			  }
    			
    			
    	    });
    		var validCheck=validator.form();    
    		            if(validCheck){
    		            	document.loginForm.submit();
                    }else{
                    	return false;
                    }
                };    
	</script>
</head>

<body>

	<div id="login_main">
		<div class="login-nav">
			<p>
				<img src="images/logo1.png" class="login_logo" />
			</p>
			<div class="new_ac">
				<img src="images/user.png" /> <a href="userregistration.do">Create
					An Account</a>
			</div>
		</div>

		
		<div class="login-main-container">
			<p class="login_txt">Login</p>

			<div class="login_detailed_container">
				<section>

					<form name='loginForm' id="loginForm"
						action="<%=request.getContextPath()%>/<c:url value='j_spring_security_check' />"
						method='POST'>
						<div id="main">

							<div class="required" align="center">
								<c:out value="${EXCEPTION}" />
							</div>

							<p>
								<label class="loginLbl" for="username">Username <span
									class="required"> *</span></label> <input type="text" name="j_username"
									id="username" class="txt_box" value="${userEmail}">
							</p>
							<p>
								<label class="loginLbl" for="password">Password <span
									class="required"> *</span></label> <input type="password"
									name="j_password" id="password" class="txt_box">
							</p>
							<p style="margin: 0 0 0 92px;">
								<span class="fpassword"><a href="forgotpassword.do">Forgot
										Password</a></span> <input type="submit" value="Login"
									onclick="callLoginValid()" class="login_button">
							</p>



							
						</div>
					</form>



				</section>
			</div>

		</div>

		<div class="footer-login">
			<p>Copyright 2013 - 2014 Blue Hub, All Rights Reserved</p>
		</div>

		


	</div>
</body>
</html>
