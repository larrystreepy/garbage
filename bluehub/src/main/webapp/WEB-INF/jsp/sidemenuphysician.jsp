<aside class="side-left">
	<ul class="sidebar">
		<li>
			<a href="<%=request.getContextPath() %>/physician/physiciansearch.do">
				<i class="sidebar-icon fa fa-search"></i> <span class="sidebar-text">Search
					Clinical Information</span>
		</a>
		</li>
		<li>
			  <a
			href="<%=request.getContextPath() %>/physician/requestshare.do">
				<i class="sidebar-icon fa fa-share-square-o"></i> <span
				class="sidebar-text">Request a Share</span>
		</a>

		</li>
		<li>
			  <a
			href="<%=request.getContextPath() %>/physician/requestfrompatient.do">
				<i class="sidebar-icon fa fa-share-square-o"></i> <span
				class="sidebar-text">View Request from Patient</span>
		</a>

		</li>
		<li><a href="#" data-pjax=".content-body"
			title="Request Info Behalf Of Patient"> <!-- <div class="badge badge-primary animated animated-repeat wobble">3</div> -->
				<i class="sidebar-icon fa fa-search"></i> <span class="sidebar-text">Request
					Info On Behalf Of Patient</span>
		</a>
			<ul class="sidebar-child animated flipInY">
				<li>
					<a
					href="<%=request.getContextPath() %>/physician/requestinfopatient.do"
					data-pjax=".content-body"> <span class="sidebar-text">Request
							Info</span>
				</a>


				</li>
				<li>
					  <a
					href="<%=request.getContextPath() %>/physician/viewrequestinfopatient.do"
					data-pjax=".content-body"> <span class="sidebar-text">View Requests of Information
					 On Behalf of Patient</span>
				</a>
				</li>

				<li>
					  <a
					href="<%=request.getContextPath() %>/physician/viewapprovedrequestinfopatient.do"
					data-pjax=".content-body"> <span class="sidebar-text">Approved
							Requests</span>
				</a>
				</li>
			</ul>
			 </li>
		<li>
			  <a
			href="<%=request.getContextPath() %>/physician/physicianupload.do">
				<i class="sidebar-icon fa fa-upload"></i> <span class="sidebar-text">Upload
					Clinical Information</span>
		</a>

		</li>
		<%-- <li><a
			href="<%=request.getContextPath() %>/physician/physicianVisitDetails.do">
				<i class="sidebar-icon fa fa-magnet"></i> <span class="sidebar-text">Visits</span>
		</a></li>
		<li>
		
			<a
			href="<%=request.getContextPath() %>/physician/physicianallvisits.do"
			data-pjax=".content-body"> <i class="sidebar-icon fa fa-eye"></i>
				<span class="sidebar-text">View All Visits</span>
		</a>

		</li> --%>
		 
	</ul>
	 
</aside>
<div class="content">
