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
	src="<%=request.getContextPath()%>/bower_components/dropzone/downloads/dropzone.min.js"></script>
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
<%@ page isELIgnored="false"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<link media="screen" rel="stylesheet"
	href="<%=request.getContextPath()%>/bower_components/bootstrap/dist/css/bootstrap.css">
<link media="screen" rel="stylesheet"
	href="<%=request.getContextPath()%>/bower_components/font-awesome/css/font-awesome.css">
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

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link media="screen" rel="stylesheet"
	href="<%=request.getContextPath()%>/css/login1.css">

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.ui.core.js"></script>

<style>
.required1 {
	color: #da4f49 !important;
}

.error {
	color: #da4f49 !important;
	margin-left: 25%;
}
</style>

</head>

<body class="animated fadeIn">
	<header class="header">


		<div class="header-brand">
			<a data-pjax=".content-body" href="" style="font-size: 34px;">
				Blue Hub </a>
		</div>

	</header>
	<section class="section">

		<aside class="side-left"></aside>

		<div class="content">

			<div class="content-body">

				<div class="error-wrapper">
					<h1 class="error-title">
						<i
							class="fa fa-warning animated animated-infinite animated-dur-3 flash text-danger"></i>
						Request Not found
					</h1>
					<p class="lead">Oops! The requested page could not be found</p>

					<form action="" role="form">
						<div class="form-group">
						</div>
					</form>
				</div>

			</div>


		</div>

	</section>


	<aside class="side-right">
		<div class="module" data-toggle="niceScroll">
			<div class="chat-contact">
				<h3 class="contact-heading">
					<div class="btn-group pull-right">
						<a href="#" data-toggle="side-right"> <i
							class="fa fa-times text-sm"></i>
						</a>
					</div>
					<i class="fa fa-group"></i> Hangouts
					<div class="btn-group">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i
							class="fa fa-angle-down text-sm"></i>
						</a>
						<ul class="dropdown-menu" role="menu">
							<li class="active"><a href="#"><i
									class="fa fa-circle text-primary"></i> Online</a></li>
							<li><a href="#"><i class="fa fa-circle text-danger"></i>
									Offline</a></li>
							<li><a href="#"><i class="fa fa-circle text-warning"></i>
									Idle</a></li>
							<li class="divider"></li>
							<li><a href="#"><i class="fa fa-circle text-midnight"></i>
									Disable</a></li>
						</ul>
					</div>
				</h3>

				<div class="contact-body">
					<ul class="contacts-list">
						<li class="separate">Top friends</li>
						<li class="online"><a href="#"><i class="fa fa-circle-o"
								title="online" rel="tooltip-bottom"></i> Phillip Morrison</a></li>
						<li class="offline"><a href="#"><i class="fa fa-circle-o"
								title="offline" rel="tooltip-bottom"></i> Samuel Porter</a></li>
						<li class="online"><a href="#"><i class="fa fa-circle-o"
								title="online" rel="tooltip-bottom"></i> Kathy Reynolds</a></li>
						<li class="online"><a href="#"><i class="fa fa-circle-o"
								title="online" rel="tooltip-bottom"></i> Aaron James</a></li>
						<li class="idle"><a href="#"><i class="fa fa-circle-o"
								title="idle" rel="tooltip-bottom"></i> Teresa May</a></li>

						<li class="separate">Teams</li>
						<li class="online"><a href="#"><i class="fa fa-circle-o"
								title="online" rel="tooltip-bottom"></i> John Doe</a></li>
						<li class="online"><a href="#"><i class="fa fa-circle-o"
								title="online" rel="tooltip-bottom"></i> Iin Triasneni</a></li>
						<li class="online"><a href="#"><i class="fa fa-circle-o"
								title="online" rel="tooltip-bottom"></i> Mahatma</a></li>
						<li class="offline"><a href="#"><i class="fa fa-circle-o"
								title="offline" rel="tooltip-bottom"></i> Lawrence Ramirez</a></li>
						<li class="offline"><a href="#"><i class="fa fa-circle-o"
								title="offline" rel="tooltip-bottom"></i> Samantha Jenkins</a></li>
						<li class="disable"><a href="#"><i class="fa fa-circle-o"
								title="disable" rel="tooltip-bottom"></i> Sarah Payne</a></li>
						<li class="offline"><a href="#"><i class="fa fa-circle-o"
								title="offline" rel="tooltip-bottom"></i> Justin Perry</a></li>

						<li class="separate">Clients</li>
						<li class="idle"><a href="#"><i class="fa fa-circle-o"
								title="idle" rel="tooltip-bottom"></i> Rebecca Vargas</a></li>
						<li class="online"><a href="#"><i class="fa fa-circle-o"
								title="online" rel="tooltip-bottom"></i> Sean Carpenter</a></li>
						<li class="idle"><a href="#"><i class="fa fa-circle-o"
								title="idle" rel="tooltip-bottom"></i> Arthur Pearson</a></li>
						<li class="offline"><a href="#"><i class="fa fa-circle-o"
								title="offline" rel="tooltip-bottom"></i> Julie Jimenez</a></li>
						<li class="idle"><a href="#"><i class="fa fa-circle-o"
								title="idle" rel="tooltip-bottom"></i> Sandra Ramirez</a></li>
						<li class="online"><a href="#"><i class="fa fa-circle-o"
								title="online" rel="tooltip-bottom"></i> Nicholas Cole</a></li>
						<li class="idle"><a href="#"><i class="fa fa-circle-o"
								title="idle" rel="tooltip-bottom"></i> Madison Hall</a></li>
						<li class="offline"><a href="#"><i class="fa fa-circle-o"
								title="offline" rel="tooltip-bottom"></i> Alan Shaw</a></li>
						<li class="online"><a href="#"><i class="fa fa-circle-o"
								title="online" rel="tooltip-bottom"></i> Randy Mills</a></li>

						<li class="separate">All</li>
						<li class="disable"><a href="#"><i class="fa fa-circle-o"
								title="disable" rel="tooltip-bottom"></i> Kenneth Soto</a></li>
						<li class="disable"><a href="#"><i class="fa fa-circle-o"
								title="disable" rel="tooltip-bottom"></i> Harold James</a></li>
						<li class="online"><a href="#"><i class="fa fa-circle-o"
								title="online" rel="tooltip-bottom"></i> Paul Greene</a></li>
					</ul>
				</div>
			</div>

			<div class="chatbox">
				<h3 class="chatbox-heading">
					<a data-toggle="chatbox-close" href="#"
						class="pull-right text-sm text-silver"><i class="fa fa-times"></i></a>
					<i class="fa fa-circle-o text-primary"></i> Phillip Morrison
				</h3>

				<div class="chatbox-body" data-toggle="niceScroll">

					<div class="chat-separate">
						<time class="chat-time" datetime="">Jan 9, 2014</time>
					</div>

					<div class="chat-in">
						<div class="chat-user">Phillip Morrison</div>
						<div class="chat-msg">
							<p>Praesent cras quisque. Volutpat sit interdum. Magnis leo,
								duis faucibus eu, in rutrum nulla. Eget sed, dolor nibh. Vero mi
								habitant</p>
							<time class="chat-time" datetime="2013-12-13T20:00">01:14
								PM</time>
						</div>
					</div>

					<div class="chat-out">
						<div class="chat-user">Me</div>
						<div class="chat-msg">
							<p>Dictum at aenean, faucibus euismod convallis. Ipsum nec,
								platea amet nulla.</p>
							<time class="chat-time" datetime="2013-12-13T20:00">01:16
								PM</time>
						</div>
					</div>

					<div class="chat-in">
						<div class="chat-user">Phillip Morrison</div>
						<div class="chat-msg">
							<p>Eu augue, proin rutrum. Et vehicula, wisi fusce, ut ipsum</p>
							<time class="chat-time" datetime="2013-12-13T20:00">01:20
								PM</time>
						</div>
					</div>

					<div class="chat-separate">
						<time class="chat-time" datetime="">Jan 14, 2014</time>
					</div>

					<div class="chat-out">
						<div class="chat-user">Me</div>
						<div class="chat-msg">
							<p>Est penatibus pellentesque, augue tincidunt, a suspendisse</p>
							<time class="chat-time" datetime="2013-12-13T20:00">09:36
								AM</time>
						</div>
					</div>

					<div class="chat-in">
						<div class="chat-user">Phillip Morrison</div>
						<div class="chat-msg">
							<p>Nunc nulla. Donec laoreet non, lobortis praesent</p>
							<time class="chat-time" datetime="2013-12-13T20:00">09:40
								AM</time>
						</div>
					</div>

					<div class="chat-separate">
						<time class="chat-time" datetime="">Recent</time>
					</div>

					<div class="chat-in">
						<div class="chat-user">Phillip Morrison</div>
						<div class="chat-msg">
							<p>Dui posuere. Reprehenderit felis libero, potenti vitae</p>
							<time class="chat-time" datetime="2013-12-13T20:00">12 min</time>
						</div>
					</div>

				</div>

				<div class="chatbox-footer">
					<form class="chat-form">
						<p class="chat-status">
							<i class="fa fa-spinner fa-spin"></i> Phillip Morrison is type...
						</p>
						<div class="form-group">
							<button type="submit" class="btn" title="Send">
								<i class="fa fa-share"></i>
							</button>
							<input type="text" class="chat-input" name="send_chat"
								placeholder="Type a message" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</aside>

	<footer>
		<p>&copy; Copyright 2014 - 2015 Blue Hub, All Rights Reserved</p>
	</footer>

</body>
</html>
