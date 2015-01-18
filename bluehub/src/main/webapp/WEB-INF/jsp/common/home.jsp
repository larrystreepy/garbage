<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Home - Bluehub</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/jquery/css/jquery.ui.all.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.9.1.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/prettify.js"></script>
<script src="<%=request.getContextPath()%>/js/simple.carousel.js"
	type="text/javascript"></script>
<script src='<%=request.getContextPath()%>/js/jquery.kwicks.js'
	type='text/javascript'></script>
<link media="all"
	href="<%=request.getContextPath()%>/css/style_whole.css"
	type="text/css" rel="stylesheet">
<link media="screen" type="text/css"
	href="<%=request.getContextPath()%>/css/colorbox.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/jquery.kwicks.css"
	type="text/css" rel="stylesheet">
<link media="all" href="<%=request.getContextPath()%>/css/custom.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/sidemenu.js"></script>

<script type='text/javascript'>
            $(function() {
            	
            	
                $('.aut_slide').kwicks({
                    size: 225,
                    /* maxSize : 945, */
                    maxSize: 745,
                    spacing: 15,
                    behavior: 'menu'
                            /* easing: 'easeOutBounce' */
                });
            });
           
        </script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/simple.carousel.js"></script>
<script type="text/javascript">
        $(document).ready(function(){
    		$('#helpMenuId').hide();
    		$('#helpMenuLogId').hide();
    	});
    	
        
            jQuery(document).ready(function()
            {
                $("#latest_news ul.slider_config").simplecarousel({
                    width: 317,
                    height: 175,
                    visible: 1,
                    next: $('#latest_news .forward'),
                    prev: $('#latest_news .backward')
                });
            });

        </script>
<script src="<%=request.getContextPath()%>/js/jquery.colorbox.js"></script>
<script>
        
            $(document).ready(function() {

                $(".youtube").colorbox({iframe: true, innerWidth: 425, innerHeight: 344});

            });
        </script>

<style type="text/css">
.div#rssincl-box-877287 div.rssincl-head {
	background-color: #C9C9C9 !important;
	height: 26px !important;
	padding: 5px !important;
	position: relative !important;
	z-index: 3 !important;
	height: 26px !important;
}

div.rssincl-entry div.rssincl-itemdesc * {
	color: #333333 !important;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px !important;
	margin-bottom: 6px !important;
	margin-top: 6px !important;
	display: inline-block;
}
</style>


</head>
<header>

	<jsp:include page="../header.jsp"></jsp:include>


</header>

<section>

	<div id="main">
		<jsp:include page="../leftmenu.jsp"></jsp:include>
		<div class="staic_main">

			<div id="wrap" class="clearfix">



				<a id="top-of-page"></a>
				<div id="wrap" class="clearfix">

					<div class="header_quote">
						<p>Bluehub-U is the joint creation of a dedicated team of
							clinicians, web designers, and logistics, hosting, and security
							experts developed to assist parents, educators, and clinicians
							with real-time, virtual, clinical management of a child diagnosed
							with Bluehub (or any intellectual disability). Our mission is to
							empower others to reach their dreams.</p>
					</div>
					<div class="col_12 column">
						<div class="inner">
							<ul style="width: 945px; height: 406.067px;"
								class="aut_slide kwicks kwicks-horizontal kwicks-processed">
								<li style="left: 0px; width: 225px;" class="first" id="panel-1">
									<div class="aut_slide_left">
										<div class="aut_slide_left_top"
											style="height: 137px; margin: 5px 0px;">
											<h3 class="title">FAMILY</h3>
											<!-- <p>Where parents can harness the tools of behavior science to share goals, plans, and achievements with clinicians, teachers, grandparents, caregivers, and children while contributing to research in Bluehub by contributing/sharing all non-personally identifiable electronic health information.</p> -->
											<p>Where parents access the science of behavior analysis
												to increase skill acquisitions and reduce problem behavior
												to help their kids, connect the circle of care, invite
												feedback, and contribute non-personally identifiable
												information to accelerate research in Bluehub.</p>
											<div class="anchor">
												<a href="#">MORE </a>
											</div>
										</div>
										<center>
											<img src="<%=request.getContextPath()%>/images/pane_1.jpg"
												alt="">
										</center>
									</div> <a
									href="<%=request.getContextPath()%>/family.do"> </a>
								<div class="aut_slide_right">
										<a href="<%=request.getContextPath()%>/family.do">
											<p>
												<b>PARENTS:</b> Where parents can harness the tools of
												behavior science to share goals, plans, and achievements
												with clinicians, teachers, grandparents, caregivers, and
												children while contributing to research in Bluehub by
												contributing/sharing all non-personally identifiable
												electronic health information. If you don't have an analyst
												nearby, you can follow our intuitive templates to create and
												track acquisition skill training strategies that
												automatically translate to calendar activities for your
												child!
											</p>
										</a>
										<div class="min_height">
											<a href="<%=request.getContextPath()%>/family.do"> <img
												src="<%=request.getContextPath()%>/images/kids.jpg" alt=""
												class="cnt_img" align="right">
												<p>
													<strong>KIDS</strong> Where you can organize your
													activities, earn badges and level up your game! You are the
													team captain, where your team mates will follow your
													progress and cheer your success!
												</p>
											</a><a class="more"
												href="<%=request.getContextPath()%>/family.do"> More ...
											</a>
										</div>
									</div>
								</li>
								<li class="" style="left: 240px; width: 225px;" id="panel-2">
									<div class="aut_slide_left">
										<div class="aut_slide_left_top"
											style="height: 137px; margin: 5px 0px;">
											<h3 class="title">COMMUNITY</h3>
											<!--  <p>Implement a plan in your facility to view important real time compliance reporting/ reporting from the circle of care!</p> -->
											<p>Where community support agencies, staff, and teachers
												connect their services to the circle of care, and contribute
												to data collection to advance scientific research.</p>
											<div class="anchor">
												<a href="#">MORE </a>
											</div>
										</div>
										<center>
											<img src="<%=request.getContextPath()%>/images/pane_2.jpg"
												alt="">
										</center>
									</div> <a
									href="<%=request.getContextPath()%>/community.do"> </a>
								<div class="aut_slide_right">
										<a href="<%=request.getContextPath()%>/community.do">
											<p>
												<b>SOCIAL WORKERS/CASE MANAGERS:</b> Implement a plan in
												your facility to view important real time compliance
												reporting/ reporting from the circle of care!
											</p>
										</a>
										<div class="min_height">
											<a href="<%=request.getContextPath()%>/community.do"> <img
												src="<%=request.getContextPath()%>/images/CommunitySlider.JPG"
												alt="" class="cnt_img" align="right">

											</a><a class="more"
												href="<%=request.getContextPath()%>/community.do"> More
												... </a>
										</div>
									</div>
								</li>
								<li class="" style="left: 480px; width: 225px;" id="panel-3">
									<div class="aut_slide_left">
										<div class="aut_slide_left_top"
											style="height: 137px; margin: 5px 0px;">
											<h3 class="title">CLINICAL STAFF</h3>
											<!-- <p>Create an Individualized Implementation Plan, and watch goals become dynamic in the child"s calendar! Create a BASP. </p> -->
											<p>Where clinicians meet to assess, implement, and manage
												behavior analysis plans with live feedback from the circle
												of care participants, to advance scientific research, manage
												ABA credentialing, billing, and comply with government and
												insurance requirements.</p>
											<div class="anchor">
												<a href="#">MORE </a>
											</div>
										</div>
										<center>
											<img src="<%=request.getContextPath()%>/images/pane_3.jpg"
												alt="">
										</center>
									</div>  <a
									href="request.getContextPath()%>/clinicalstaff.do"> </a>
								<div class="aut_slide_right">
										<a href="<%=request.getContextPath()%>/clinicalstaff.do">
											<p style="padding: 2px 0 0 0 !important;">
												<b>BCBA/Certified/Licensed Personnel:</b> Create an
												Individualized Implementation Plan, and watch goals become
												dynamic in the child's calendar! Create a BASP or store the
												previous one. Intuitive billing saves time!
											</p>
										</a>
										<div class="min_height">
											<a href="<%=request.getContextPath()%>/clinicalstaff.do">
												<img
												src="<%=request.getContextPath()%>/images/ClinicalStaffSlider.jpg"
												alt="" class="cnt_img" align="right">
												<p style="padding: 2px 0 0 0 !important;">
													<b>BCaBA: </b>Follow the BASP, IIP, LTOs and STOs for
													detailed intervention strategies. Data collection and
													reporting has never been so easy!
												</p>
												<p style="padding: 2px 0 0 0 !important;">
													<b>Physicians/Medical Researchers:</b> Implement a plan in
													your facility to view goals/strategies and other important
													clinical data in secure dynamic EMR database. Interested in
													conducting clinical research? Our depersonalized data may
													help...
												</p>
												<p style="padding: 2px 0 0 0 !important;">
													<b>Other Therapists/Clinicians:</b> Learn about the team
													strategies you can help reinforce!
												</p>
											</a><a class="more"
												href="<%=request.getContextPath()%>/clinicalstaff.do">
												More ... </a>
										</div>
									</div> 
								</li>
								<li style="right: 0; width: 225px;" class="last" id="panel-4">
									<div class="aut_slide_left">
										<div class="aut_slide_left_top"
											style="height: 137px; margin: 5px 0px;">
											<h3 class="title">EDU</h3>
											<!--  <p>View Behavior Action Plan, and learn about the team strategies you can easily help reinforce! Easy compliance monitoring/ reporting.</p> -->
											<p>Where school districts, educators, counselors, and
												administrative staff can assess, implement, and manage the
												behavior intervention plans (BIP) with live feedback and
												graphing to accelerate academic achievements.</p>
											<div class="anchor">
												<a href="#">MORE </a>
											</div>
										</div>
										<center>
											<img src="<%=request.getContextPath()%>/images/pane_4.jpg"
												alt="">
										</center>
									</div> <a
									href="<%=request.getContextPath()%>/edu.do"> </a>
								<div class="aut_slide_right">
										<a href="<%=request.getContextPath()%>/edu.do">
											<p>
												<b>TEACHERS:</b> View Behavior Action Plan, and learn about
												the team strategies you can easily help reinforce! Easy
												compliance monitoring/ reporting to the circle of care!
											</p>
										</a>
										<div class="min_height">
											<a href="<%=request.getContextPath()%>/edu.do"> <img
												src="<%=request.getContextPath()%>/images/kids.jpg" alt=""
												class="cnt_img" align="right">
												<p>
													<b>SCHOOLS:</b> Implement a plan in your facility to view
													and provide important compliance monitoring/ reporting to
													the circle of care! View real time data!
												</p>
											</a><a class="more" href="<%=request.getContextPath()%>/edu.do">
												More ... </a>
										</div>
									</div>
								</li>
							</ul>
						</div>
					</div>

					<div class="col_7 column" style="width: 50.667%;">
						<div id="feed" class="inner" style="margin-top: 40px;">
							<a class="twitter-timeline" href="https://twitter.com/BluehubU"
								data-widget-id="464366746627883008">Tweets by @BluehubU</a>
							<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>


							<br>

							<div style="" id="peoples_voice">
								<h3 class="voice">
								</h3>
								<p>
									&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
									<a href="#" class="rd_more">
										<!-- Read More... -->
									</a>
								</p>
							</div>
							

						</div>
					</div>
					<!-- end of col_7 -->
					<div class="col_5 column">
						<div class="inner">
							<!--  <div id="latest_news" style="margin-top:30px;margin-left:-21px;"> -->
							<div id="rssfeed" style="margin-top: 39px;">
								<script type="text/javascript"
									src="http://output72.rssinclude.com/output?type=js&amp;id=877287&amp;hash=090fb8e81d87012069f59086f063eb50"></script>


								
								<div id="lnews_slider">
									
								</div>
							</div>

						</div>
					</div>



				</div>
				<div class="clear"></div>
				<div class="clear"></div>


			</div>

			<div id="fancybox-tmp"></div>
			<div id="fancybox-loading">
				<div></div>
			</div>
			<div id="fancybox-overlay"></div>
			<div id="fancybox-wrap">
				<div id="fancybox-outer">
					<div class="fancybox-bg" id="fancybox-bg-n"></div>
					<div class="fancybox-bg" id="fancybox-bg-ne"></div>
					<div class="fancybox-bg" id="fancybox-bg-e"></div>
					<div class="fancybox-bg" id="fancybox-bg-se"></div>
					<div class="fancybox-bg" id="fancybox-bg-s"></div>
					<div class="fancybox-bg" id="fancybox-bg-sw"></div>
					<div class="fancybox-bg" id="fancybox-bg-w"></div>
					<div class="fancybox-bg" id="fancybox-bg-nw"></div>
					<div id="fancybox-content"></div>
					<a id="fancybox-close"></a>
					<div id="fancybox-title"></div>
					<a href="javascript:;" id="fancybox-left"> <span
						class="fancy-ico" id="fancybox-left-ico"></span></a> <a
						href="javascript:;" id="fancybox-right"> <span
						class="fancy-ico" id="fancybox-right-ico"></span>
					</a>
				</div>
			</div>
			<div style="display: none;" id="cboxOverlay"></div>
			<div
				style="display: none; padding-bottom: 42px; padding-right: 42px;"
				class="" id="colorbox">
				<div id="cboxWrapper">
					<div>
						<div style="float: left;" id="cboxTopLeft"></div>
						<div style="float: left;" id="cboxTopCenter"></div>
						<div style="float: left;" id="cboxTopRight"></div>

					</div>
					<div style="clear: left;">
						<div style="float: left;" id="cboxMiddleLeft"></div>
						<div style="float: left;" id="cboxContent">
							<div
								style="width: 0px; height: 0px; overflow: hidden; float: left;"
								id="cboxLoadedContent"></div>
							<div style="float: left;" id="cboxTitle"></div>
							<div style="float: left;" id="cboxCurrent"></div>
							<div style="float: left;" id="cboxNext"></div>
							<div style="float: left;" id="cboxPrevious"></div>
							<div style="float: left;" id="cboxSlideshow"></div>
							<div style="float: left;" id="cboxClose"></div>

						</div>
						<div style="float: left;" id="cboxMiddleRight"></div>
					</div>
					<div style="clear: left;">
						<div style="float: left;" id="cboxBottomLeft"></div>
						<div style="float: left;" id="cboxBottomCenter"></div>
						<div style="float: left;" id="cboxBottomRight"></div>
					</div>
				</div>
				<div
					style="position: absolute; width: 9999px; visibility: hidden; display: none;">

				</div>
			</div>

		</div>

		<div id="s_footer_bootom" style="margin-top: -79px;">
			<jsp:include page="../sociallinkfooter.jsp"></jsp:include>
		</div>
	</div>




</section>
<footer>
	<jsp:include page="../footer.jsp"></jsp:include>
</footer>


</body>
</html>
