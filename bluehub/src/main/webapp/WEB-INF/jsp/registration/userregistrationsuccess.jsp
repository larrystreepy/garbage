<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Blue Hub</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="stilearning">

<meta http-equiv="x-pjax-version" content="v173">
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
	src="<%=request.getContextPath()%>/bower_components/jquery-minicolors/jquery.minicolors.min.js"></script>
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
	src="<%=request.getContextPath()%>/scripts/bootstrap-setup.js"></script>
<script class="re-execute"
	src="<%=request.getContextPath()%>/scripts/jqueryui-setup.js"></script>
<script class="re-execute"
	src="<%=request.getContextPath()%>/scripts/dependencies-setup.js"></script>
<script class="re-execute"
	src="<%=request.getContextPath()%>/scripts/demo-setup.js"></script>


<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="<%=request.getContextPath()%>/ico/favico-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="<%=request.getContextPath()%>/ico/favico-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="<%=request.getContextPath()%>/ico/favico-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="<%=request.getContextPath()%>/ico/favico-57-precomposed.png">
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/ico/favico.png">
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/ico/favico.ico">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/bower_components/bootstrap/dist/css/bootstrap.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/bower_components/font-awesome/css/font-awesome.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/bower_components/animate.css/animate.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/bower_components/Hover/css/hover.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style1.css">
<link id="style-components"
	href="<%=request.getContextPath()%>/styles/loaders.css"
	rel="stylesheet">
<link id="style-components"
	href="<%=request.getContextPath()%>/styles/bootstrap-theme.css"
	rel="stylesheet">
<link id="style-components"
	href="<%=request.getContextPath()%>/styles/dependencies.css"
	rel="stylesheet">
<link id="style-base"
	href="<%=request.getContextPath()%>/styles/stilearn.css"
	rel="stylesheet">
<link id="style-responsive"
	href="<%=request.getContextPath()%>/styles/stilearn-responsive.css"
	rel="stylesheet">
<link id="style-helper"
	href="<%=request.getContextPath()%>/styles/helper.css" rel="stylesheet">
<link id="style-sample"
	href="<%=request.getContextPath()%>/styles/pages-style.css"
	rel="stylesheet">

<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/login1.css">

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.ui.core.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/registration/userregister.js"></script>
<style>
.required1 {
	color: #da4f49 !important;
}

.success_message_style {
	color: #da4f49 !important;
	font: bolder;
}
</style>
</head>

<body class="animated fadeIn">
	<header class="header">
		<div class="header-brand">
			<a data-pjax=".content-body" href="" style="font-size: 34px;">
				Blue Hub </a>
		</div>

		<div class="header-profile">
			<div class="profile-nav">
				<span class="profile-username"><a href="userregistration.do">Create
						An Account</a></span>

			</div>

		</div>

		<ul class="hidden-xs header-menu pull-right">

		</ul>
	</header>
	<section class="section">

		<aside class="side-left"></aside>

		<div class="content">


			<script>

var contextPath = "<%=request.getContextPath()%>";
</script>
			<script type="text/javascript"
				src="<%=request.getContextPath()%>/js/registration/registrationsuccess.js"></script>

			<div class="content-body">

				<form method="GET" name="userForm" id="userForm">





					<div id="main">



						<span style="margin: 0px 0 0 460px; float: left; display: none"
							id="uploadprocess"></span>
						<div class="success_message_style" align="center"
							style="float: left; margin: 14px 0 0 432px; width: 255px; height: 20px; clear: left;">
							<c:out value="${successMsg}" />
						</div>
						<div class="success_message_style" align="center"
							style="margin: 11px 0 0 343px; width: 400px; clear: left; float: left;">
							<c:out value="${redirectMsg}" />
						</div>

					</div>
				</form>

			</div>



		</div>

	</section>


	<aside class="side-right">
		<div class="module" data-toggle="niceScroll">

			 
		</div>
	</aside>

	<footer>
		<p>&copy; Copyright 2014 - 2015 Blue Hub, All Rights Reserved</p>
	</footer>

</body>
</html>