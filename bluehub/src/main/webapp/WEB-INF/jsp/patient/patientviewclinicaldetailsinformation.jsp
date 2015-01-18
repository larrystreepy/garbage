
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>


<%
List<Object[]> doc = (List<Object[]>)request.getAttribute("documentList");


%>

<script type="text/javascript">


function callDocumentOpenFile(fileName) { 
 if (fileName == ''){
 }else{    
  var url=contextPath+"/DocumentDownload?fileName="+fileName; 
  window.open(url,'','width=700,height=500'); 
 }  
}

</script>

<div class="content-body">



	<div id="panel-typeahead"
		class="panel panel-default sortable-widget-item">
		<div class="panel-heading">
			<div class="panel-actions">
				<button data-expand="#panel-typeahead" title="expand"
					class="btn-panel">
					<i class="fa fa-expand"></i>
				</button>
				<button data-collapse="#panel-typeahead" title="collapse"
					class="btn-panel">
					<i class="fa fa-caret-down"></i>
				</button>
			</div>
			<!-- /panel-actions -->
			<h3 class="panel-title">Document List</h3>
		</div>




		<div class="panel-body">




			<div class="panel-body">
				<div class="table-responsive table-responsive-datatables"
					id="patientAddtionalInformations">
					<table class="table table-hover table-bordered" id="documentTable">
						<thead>
							<tr>

								<th>Date</th>
								<th>Physician</th>
								<th>Document</th>

							</tr>
						</thead>

						<tbody>

							<%
									for(Object[] obj :doc){%>


							<tr>
								<td class="user_list_link"><%=obj[4] %></a></td>
								<td class="user_list_link"><%=obj[5] %></a></td>
								<td class="user_list_link"><a href="#"
									onClick="callDocumentOpenFile('<%=obj[2]%>')"><%=obj[2] %></a></td>
							</tr>


							<%	}
									%>
						
					</table>
				</div>
			</div>








		</div>

		<script>
var contextPath = "<%=request.getContextPath()%>";
</script>


		<jsp:include page="../footerbluehub.jsp"></jsp:include>