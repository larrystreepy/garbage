<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>

<script>
        var contextPath = "<%=request.getContextPath()%>";
        </script>

<%
List<Object[]> patList = null;
List<Object[]> docList = null;
Map<String,Object> map = (Map<String,Object>) request.getAttribute("shareDet");

if(map!=null){
 patList = (List<Object[]>)map.get("patientList");

 docList = (List<Object[]>)map.get("docList");
}
String info = (String)request.getAttribute("info");
%>


<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/physician/requestshare.js"></script>



<%
if(info==null){
%>

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
			<h3 class="panel-title">
				Shared Documents Of
				<%=patList.get(0)[0]+" " +patList.get(0)[1]+""%></h3>
		</div>


		<div class="panel-body">
			<div class="table-responsive table-responsive-datatables">
				<table class="table table-hover table-bordered">
					<thead>
						<tr>
							<th>Date</th>
							<th>Document</th>

						</tr>
					</thead>


					<tbody>
						<%
								 if(docList!=null){
								 for(Object[] obj: docList){ %>
						<tr>
							<td><%=obj[4]+"" %></td>

							<td><a href="#"
								onclick="callDocumentOpenFile('<%=obj[2]+""%>','<%=obj[1]+""%>')"><%=obj[2]+"" %></a></td>
						</tr>
						<%} }%>
					</tbody>
				</table>
			</div>
		</div>

	</div>

</div>



<%}else{ %>

<h2>
	<%=info %>
</h2>

<%} %>

<jsp:include page="../footerbluehub.jsp"></jsp:include>