<aside class="side-left">
	<ul class="sidebar">
		<li><a
			href="<%=request.getContextPath()%>/administrator/adminorganization.do"
			data-pjax=".content-body"> <i
				class="sidebar-icon fa fa-building-o"></i> <span
				class="sidebar-text">Organization</span>
		</a></li>
		<li><a 
			href="<%=request.getContextPath()%>/administrator/adminpractice.do">
				<i class="sidebar-icon fa fa-magnet"></i> <span class="sidebar-text">Practice</span>
		</a></li>
		<li><a href="#">  
				<i class="sidebar-icon fa fa-legal"></i> <span class="sidebar-text">Admin</span>
		</a>
			<ul class="sidebar-child animated flipInY">
				<li><a
					href="<%=request.getContextPath()%>/administrator/physicianmapping.do"
					data-pjax=".content-body"> <span class="sidebar-text">Physician
							Mapping</span>
				</a></li>
				<li>
					<a href="patientmanagement.do" data-pjax=".content-body"> <span
						class="sidebar-text">Patient Management</span>
				</a>
				</li>
				<li><a href="physicianmanagement.do" data-pjax=".content-body">
						<span class="sidebar-text">Physician Management</span>
				</a></li>
				<li><a
					href="<%=request.getContextPath()%>/administrator/adminaudittrial.do"
					data-pjax=".content-body"> <span class="sidebar-text">Audit
							Trial</span>
				</a></li>

			</ul> </li>
		<li><a href="#" data-pjax=".content-body"> <!-- <div class="badge badge-primary animated animated-repeat wobble">2</div> -->
				<i class="sidebar-icon fa fa-search"></i> <span class="sidebar-text">Search</span>
		</a>
			<ul class="sidebar-child animated flipInY">
				<li>
					<a
					href="<%=request.getContextPath()%>/administrator/adminsearch.do"
					data-pjax=".content-body"> <span class="sidebar-text">Search
							Clinical Information</span>
				</a>


				</li>
				<li>
					<a
					href="<%=request.getContextPath()%>/administrator/admindocumentsearch.do"
					data-pjax=".content-body"> <span class="sidebar-text">Document
							Search</span>
				</a>


				</li>
				<li>
					  <a
					href="<%=request.getContextPath()%>/administrator/adminshare.do"
					data-pjax=".content-body"> <span class="sidebar-text">Share
							Clinical Information</span>
				</a>
				</li>
			</ul>  </li>

		<li><a href="#" data-pjax=".content-body"> 
				<i class="sidebar-icon fa fa-wheelchair"></i> <span
				class="sidebar-text">Visits</span>
		</a>
			<ul class="sidebar-child animated flipInY">
				<li><a
					href="<%=request.getContextPath()%>/administrator/adminvisitdetails.do"
					data-pjax=".content-body"> <!-- 	 <a href="#" data-pjax=".content-body"> -->
						<span class="sidebar-text">Visits</span>
				</a></li>
				<li><a
					href="<%=request.getContextPath()%>/administrator/adminallvisits.do"
					data-pjax=".content-body"> 
						<span class="sidebar-text">View all visits of their offices</span>
				</a></li>
			</ul>  </li>


			<li>
				 <a href="<%=request.getContextPath()%>/administrator/adminupload.do"
				 data-pjax=".content-body"> <i class="sidebar-icon fa fa-upload"></i>
				 <span class="sidebar-text">Upload Clinical Information</span>
				 </a>
	
			</li>
 
		   <li>
					<a href="<%=request.getContextPath()%>/administrator/adminEfax.do"
					data-pjax=".content-body"> <i class="sidebar-icon fa fa-print"></i>
					<span class="sidebar-text">EFax Incoming</span>
					</a>
			 </li>     
			 
			  <li>
					<a href="<%=request.getContextPath()%>/administrator/adminEfaxAssociated.do"
					data-pjax=".content-body"> <i class="sidebar-icon fa fa-print"></i>
					<span class="sidebar-text">EFax Associated</span>
					</a>
			 </li>      

	</ul>
</aside>

<div class="content">
