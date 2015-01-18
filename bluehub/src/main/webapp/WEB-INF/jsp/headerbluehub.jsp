<!DOCTYPE html>
<%@page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.bluehub.bean.user.UserProfile"%>
<%@page import="com.bluehub.vo.user.RoleVO"%>
<%@page import="javax.management.relation.Role"%>
<%@page import="com.bluehub.vo.user.UserRoleVO"%>
<%@page import="com.bluehub.vo.user.UserVO"%>
<html lang="en">


<head>
<meta charset="utf-8">
<title>Blue Hub</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- <meta name="author" content="stilearning"> -->

<meta http-equiv="x-pjax-version" content="v173">

<%@ page isELIgnored="false"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<link media="screen" rel="stylesheet"
	href="<%=request.getContextPath()%>/bower_components/bootstrap/dist/css/bootstrap.css">
 <%-- <link media="screen" rel="stylesheet"
	href="<%=request.getContextPath()%>/bower_components/font-awesome/css/font-awesome.css">  --%>

<%-- <link rel="stylesheet"
	href="<%=request.getContextPath()%>/bower_components/font-awesome-4.2.0/css/font-awesome.css">  --%>
	
	 <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
	
	
<link media="screen" rel="stylesheet"
	href="<%=request.getContextPath()%>/bower_components/animate.css/animate.css">
<link media="screen" rel="stylesheet"
	href="<%=request.getContextPath()%>/bower_components/Hover/css/hover.css">
<link media="screen" rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style1.css">
<link media="screen" id="style-components"
	href="<%=request.getContextPath()%>/styles/loaders.css"
	rel="stylesheet">
<link media="screen" id="style-components"
	href="<%=request.getContextPath()%>/styles/bootstrap-theme.css"
	rel="stylesheet">
<link media="screen" id="style-components"
	href="<%=request.getContextPath()%>/styles/dependencies.css"
	rel="stylesheet">
<link media="screen" id="style-base"
	href="<%=request.getContextPath()%>/styles/stilearn.css"
	rel="stylesheet">
<link media="screen" id="style-responsive"
	href="<%=request.getContextPath()%>/styles/stilearn-responsive.css"
	rel="stylesheet">
<link media="screen" id="style-helper"
	href="<%=request.getContextPath()%>/styles/helper.css" rel="stylesheet">
<link media="screen" id="style-sample"
	href="<%=request.getContextPath()%>/styles/pages-style.css"
	rel="stylesheet">

<script
	src="<%=request.getContextPath()%>/bower_components/jquery/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jqueryui/ui/jquery-ui.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jqueryui-touch-punch/jquery.ui.touch-punch.min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/bootstrap/dist/js/bootstrap.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/pace/pace.min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jquery-pjax/jquery.pjax.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/masonry/masonry.pkgd.min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/screenfull/dist/screenfull.min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jquery.nicescroll/jquery.nicescroll.min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/countUp.js/countUp.min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/skycons/skycons.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jquery.lazyload/jquery.lazyload.min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/WOW/dist/wow.min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jquery.validation/jquery.validate.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jquery.validation/additional-methods.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/autogrow-textarea/jquery.autogrowtextarea.min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/typeahead.js/dist/typeahead.min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jQuery-Mask-Plugin/jquery.mask.min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jquery.tagsinput/jquery.tagsinput.min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/multiselect/js/jquery.multi-select.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/select2/select2.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jquery-selectboxit/src/javascripts/jquery.selectBoxIt.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/momentjs/moment.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/bootstrap-daterangepicker/daterangepicker.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/dropzone/downloads/dropzone.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jquery-steps/build/jquery.steps.min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/fullcalendar/fullcalendar.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/wysihtml5/dist/wysihtml5-0.3.0.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/bootstrap-wysihtml5/dist/bootstrap-wysihtml5-0.0.2.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/bootstrap-markdown/js/markdown.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/bootstrap-markdown/js/to-markdown.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/bootstrap-markdown/js/bootstrap-markdown.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/raphael/raphael-min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/morris.js/morris.min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/flot/jquery.flot.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/flot/jquery.flot.resize.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/flot/jquery.flot.categories.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/flot/jquery.flot.time.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/flot-axislabels/jquery.flot.axislabels.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jquery.easy-pie-chart/dist/jquery.easypiechart.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/sparkline/dist/jquery.sparkline.min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/datatables/media/js/jquery.dataTables.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/datatables-tools/js/dataTables.tableTools.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/datatables-bootstrap3/BS3/assets/js/datatables.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jquery.tablesorter/js/jquery.tablesorter.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jquery.tablesorter/js/jquery.tablesorter.widgets.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jquery.tablesorter/addons/pager/jquery.tablesorter.pager.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jqvmap/jqvmap/jquery.vmap.min.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jqvmap/jqvmap/maps/jquery.vmap.world.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jqvmap/jqvmap/maps/jquery.vmap.algeria.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jqvmap/jqvmap/maps/jquery.vmap.france.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jqvmap/jqvmap/maps/jquery.vmap.germany.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jqvmap/jqvmap/maps/jquery.vmap.russia.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jqvmap/jqvmap/maps/jquery.vmap.usa.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jqvmap/jqvmap/maps/continents/jquery.vmap.africa.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jqvmap/jqvmap/maps/continents/jquery.vmap.asia.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jqvmap/jqvmap/maps/continents/jquery.vmap.australia.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jqvmap/jqvmap/maps/continents/jquery.vmap.europe.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jqvmap/jqvmap/maps/continents/jquery.vmap.north-america.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jqvmap/jqvmap/maps/continents/jquery.vmap.south-america.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/jqvmap/jqvmap/data/jquery.vmap.sampledata.js"></script>
<script
	src="<%=request.getContextPath()%>/bower_components/holderjs/holder.js"></script>
<script
	src="<%=request.getContextPath()%>/scripts/bootstrap-jasny/js/fileinput.js"></script>
<script src="<%=request.getContextPath()%>/scripts/js-prototype.js"></script>
<script src="<%=request.getContextPath()%>/scripts/slip.js"></script>
<script src="<%=request.getContextPath()%>/scripts/hogan-2.0.0.js"></script>
<script src="<%=request.getContextPath()%>/scripts/theme-setup.js"></script>
<script src="<%=request.getContextPath()%>/scripts/chat-setup.js"></script>
<script src="<%=request.getContextPath()%>/scripts/panel-setup.js"></script>
<script class="re-execute"
	src="<%=request.getContextPath()%>/scripts/google-code-prettify/run_prettify.js"></script>
<script class="re-execute"
	src="<%=request.getContextPath()%>/scripts/bootstrap-setup.js"></script>
<script class="re-execute"
	src="<%=request.getContextPath()%>/scripts/jqueryui-setup.js"></script>
<script class="re-execute"
	src="<%=request.getContextPath()%>/scripts/dependencies-setup.js"></script>
<script class="re-execute"
	src="<%=request.getContextPath()%>/scripts/demo-setup.js"></script>
<link media="screen" rel="apple-touch-icon-precomposed" sizes="144x144"
	href="<%=request.getContextPath()%>/ico/favico-144-precomposed.png">
<link media="screen" rel="apple-touch-icon-precomposed" sizes="114x114"
	href="<%=request.getContextPath()%>/ico/favico-114-precomposed.png">
<link media="screen" rel="apple-touch-icon-precomposed" sizes="72x72"
	href="<%=request.getContextPath()%>/ico/favico-72-precomposed.png">
<link media="screen" rel="apple-touch-icon-precomposed"
	href="<%=request.getContextPath()%>/ico/favico-57-precomposed.png">
<link media="screen" rel="shortcut icon"
	href="<%=request.getContextPath()%>/ico/favico.png">
<link media="screen" rel="shortcut icon"
	href="<%=request.getContextPath()%>/ico/favico.ico">

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<link media="screen" rel="stylesheet"
	href="<%=request.getContextPath()%>/css/login1.css">

<script src="<%=request.getContextPath()%>/js/basics/basic.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.ui.core.js"></script>



<script type="text/javascript">
        var contextPathHead="<%=request.getContextPath()%>"
       	 function mainLogout(){ 	
       	 	window.location.href=contextPathHead+ "/j_spring_security_logout";	 	
       	 }
        
        function changePassword(){
        	
        	window.location.href=contextPathHead+ "/changepassword.do";	 	
        }
		function userRegistration(){
        	
        	window.location.href=contextPathHead+ "/userregistration.do";	 	
        }
        
        
		function editProfile(){
        	
        	window.location.href=contextPathHead+ "/editprofile.do";	 	
        }

		function editPatientProfile(){
			window.location.href=contextPathHead+ "/editpatientprofile.do";
		}

		function viewpracticeadminhome(){
			window.location.href=contextPathHead+ "/practiceadmin/home.do";
		}
		
		
        
      
        </script>
</head>

<body class="animated fadeIn">

	<%
   String roleName = (String) request.getSession().getAttribute("role");
    Integer practiceid = (Integer) request.getSession().getAttribute("practiceid");
    
    %>

	<header class="header">
		<sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">

			<div class="header-brand">
				<a data-pjax=".content-body"
					href="<%=request.getContextPath()%>/administrator/home.do"
					style=""><img width="320px" height="77px" src="<%=request.getContextPath()%>/images/mybluehub.png" style="margin-left: -50px;"> </a>
					
 			
 			
			</div>
		</sec:authorize>

		<%if(roleName !=null && roleName.equals("Physician")){ %>

		<div class="header-brand">
			<a data-pjax=".content-body"
				href="<%=request.getContextPath()%>/physician/physiciandashboard.do"
				style=""> <img width="320px" height="77px" src="<%=request.getContextPath()%>/images/mybluehub.png" style="margin-left: -50px;"> </a>
		</div>
		<%} %>

		<sec:authorize access="hasRole('ROLE_PATIENT')">

			<div class="header-brand">
				<a data-pjax=".content-body"
					href="<%=request.getContextPath()%>/patient/home.do"
					style=""> <img width="320px" height="77px" src="<%=request.getContextPath()%>/images/mybluehub.png" style="margin-left: -50px;"> </a>
			</div>
		</sec:authorize>

		<%if(roleName !=null && roleName.equals("PracticeAdmin")){ %>

		<div class="header-brand">
			<a data-pjax=".content-body"
				href="<%=request.getContextPath()%>/practiceadmin/home.do"
				style=""> <img width="320px" height="77px" src="<%=request.getContextPath()%>/images/mybluehub.png" style="margin-left: -50px;"> </a>
		</div>

		<%} %>

		<div class="header-profile">
			<div class="profile-nav">
				<span class="profile-username">Welcome <c:out
						value="${pageContext.request.userPrincipal.name}"></c:out></span> <a
					href="#" class="dropdown-toggle" data-toggle="dropdown"> <span
					class="fa fa-angle-down"></span>
				</a>
				<ul class="dropdown-menu animated flipInX pull-right" role="menu">

					<li><a href="javascript:void(0)"
						onclick="javascript:mainLogout();"><i class="fa fa-sign-out"></i>
							Log Out</a></li>
				</ul>
			</div>

		</div>
		<ul class="hidden-xs header-menu pull-right" id="changePwdID">
			<% if(roleName !=null  && practiceid !=null){ %>
			<li><a href="javascript:void(0)"
				onclick="javascript:viewpracticeadminhome()" title="Home"
				class="dropdown-toggle" data-toggle="dropdown" role="button">
					Home </a></li>
			<% }  %>

			<sec:authorize access="hasRole('ROLE_PHYSICIAN')">
				<li><a href="javascript:void(0)"
					onclick="javascript:editProfile()" title="Eit Profile"
					class="dropdown-toggle" data-toggle="dropdown" role="button">
						Edit Profile </a></li>



			</sec:authorize>
			<li><a href="javascript:void(0)"
				onclick="javascript:changePassword()" title="Change password"
				class="dropdown-toggle" data-toggle="dropdown" role="button">
					Change Password </a></li>
		<sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">			
		<li><a href="javascript:void(0)"
				onclick="javascript:userRegistration()" title="Create User"
				class="dropdown-toggle" data-toggle="dropdown" role="button">
					Create An Account</a></li>
					
		</sec:authorize>
					
					<!-- <div class="header-profile">
			<div class="profile-nav">
				<span class="profile-username"><a href="userregistration.do">Create
						An Account</a></span>

			</div>

		</div> -->


		</ul>
		<ul class="hidden-xs header-menu pull-right" id="changePwdID">
			<sec:authorize access="hasRole('ROLE_PATIENT')">
				<li><a href="javascript:void(0)"
					onclick="javascript:editPatientProfile()" title="Change password"
					class="dropdown-toggle" data-toggle="dropdown" role="button">
						Edit Profile </a></li>
			</sec:authorize>
		</ul>
	</header>
	<section class="section">

		<sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
			<jsp:include page="../jsp/sidemenuadmin.jsp"></jsp:include>
		</sec:authorize>

		<%if(roleName !=null && roleName.equals("Physician")){ %>
		<jsp:include page="../jsp/sidemenuphysician.jsp"></jsp:include>
		<% }  %>

		<sec:authorize access="hasRole('ROLE_PATIENT')">
			<jsp:include page="../jsp/sidemenupatient.jsp"></jsp:include>
		</sec:authorize>
