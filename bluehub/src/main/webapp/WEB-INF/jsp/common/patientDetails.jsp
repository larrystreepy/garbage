<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Patient - Blue Hub</title>
<link rel="stylesheet" href="css/style.css" />
<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/sidemenu-launch.js"></script>


<link rel="stylesheet" href="jquery/css/style.css" type="text/css"
	media="screen" />

<link rel="stylesheet" href="jquery/css/jquery.ui.all.css">
<script type="text/javascript"></script>
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery.ui.core.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.ui.datepicker.js"></script>

<script>


        function pageNavigation(){
        	document.form.action="patient_dashboard.html";
        	document.form.submit();
        }
        
            $(function() {
    		//$( "#datepicker" ).datepicker();
    		 $( "#datepicker" ).datepicker({
				   yearRange: "1950:",
					changeMonth: true,
					changeYear: true
				});
    	});
            </script>

</head>

<header>

	<nav>
		<ul>
			<ul id="menu">
				
				<li class="login_user_bg">Welcome Patient</li>
				<li><a href="login.html">logout</a></li>



			</ul>
		</ul>
	</nav>

</header>
<section>

	<div id="main">

		

		<form type=get id="form" name="form" type=get
			onsubmit="pageNavigation()">
			<div id="main">

				<div class="patientAddtionalInformation">
					<h3>Additional Information</h3>

					<div class="user-top-left">

						<div>
							<label for="fname">First Name <span class="required">*</span>
							</label> <input type="text" size="25" id="mailId" name="mailId" />
						</div>

						<div>
							<label for="mname">Middle Name <span class="required">*</span>
							</label> <input type="text" size="25" id="mailId" name="mailId" />
						</div>

						<div>
							<label for="lname">Last Name <span class="required">*</span>
							</label> <input type="text" size="25" id="mailId" name="mailId" />
						</div>

						

						<div>
							<label for="dob">DOB</label> <input type="text" id="datepicker"
								name="dob" class="lrg_clndr_box"> <span class="required">mm/dd/yyyy</span>
						</div>

						<div>
							<label for="SSN">SSN <span class="required">*</span></label> <input
								type="text" size="25" id="SSN" name="SSN" />
						</div>
						<div>
							<label for="insuranceInformation">Insurance Information<span
								class="required">*</span></label> <input type="text" size="25"
								id="insuranceInformation" name="insuranceInformation" />
						</div>

						<div>
							<label for="address">Address<span class="required">*</span></label>
							<textarea rows="100" cols="25" name="address" id="address"></textarea>

							
						</div>
						<div>

							<div class="userRegBtn">
								<input type="submit" class="login_button" value="Submit">
							</div>
						</div>
					</div>





				</div>


			</div>



		</form>

</div>

	<footer>
		<div class="footer">Copyright 2013 - 2014 Blue Hub, All Rights
			Reserved</div>
	</footer>
</section>

</body>

<style>
.patientAddtionalInformation {
	border: 1px solid #3C89E0;
	background: #E3F1F9;
	float: left;
	margin: 14% 0 0 131px;
	width: 979px;
}
</style>
</html>
