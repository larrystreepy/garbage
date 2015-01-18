
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>



<script>

function callDocumentOpenFile(fileName) { 
	 if (fileName == ''){
	 }else{    
	  var url=contextPath+"/DocumentSearch?fileName="+fileName; 
	  window.open(url,'','width=700,height=500'); 
	 }  
	}

function showDetais() {


	if (document.getElementById('search').value != '') {

		
		var search=document.getElementById('search').value;
		
		var content = '';
		content += '<table style="width: 100%" id="renderUserListTable" class="tablesorter table table-hover table-bordered">';
		content += '<thead>';
		content += '<th>UserName</th>';
		content += '<th>Documents</th>';
		content += '</thead>';
		document.getElementById('visitRecords').innerHTML = content;

		$.ajax({
			type : "GET",
			url : contextPath
					+ "/administrator/admindocumentsearchlist.do",
			 data:{search:search},
			//data : "searchphysician=" + searchPhysician +"&orgid=" +orgid+"&practiceid="+practiceid,
			cache : false,
			async : false,
			//dataType: "json",
			success : function(response) {

				if(response=="null" || response=="" || response=="[]"){
					content += '<td class="user_list_link" align="center" colspan="5">NO RECORDS</td>';

					/* content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
						content += '</table>';
						document.getElementById('visitRecords').innerHTML = content;
					
				}else{

					var parsedJson = $.parseJSON(response);

					var username;
					
					for (var i = 0; i < parsedJson.length; i++) {

					username=parsedJson[i].username;
					if(typeof username=="" || username=="undefined" || username==null){
						
						username=" ";
						
					}else{
						username=username;
					}

					content = content + '<tr onclick="callDocumentOpenFile(\''+parsedJson[i].filepath+'\')">';
					content += '<td  class="user_list_link">'
						+ username + '</td>';
					content += '<td  class="user_list_link">'
									+ parsedJson[i].filename + '</td>';
						
						content = content + '</tr>';
					}

					 /* content += ' <tfoot><tr>                    <th colspan="5" class="ts-pager form-horizontal">                        <button type="button" class="btn btn-default btn-sm first"><i class="icon-step-backward fa fa-angle-double-left"></i></button>                        <button type="button" class="btn btn-default btn-sm prev"><i class="icon-arrow-left fa fa-angle-left"></i></button>                        <span class="pagedisplay"></span>                     <button type="button" class="btn btn-default btn-sm next"><i class="icon-arrow-right fa fa-angle-right"></i></button>                        <button type="button" class="btn btn-default btn-sm last"><i class="icon-step-forward fa fa-angle-double-right"></i></button>                        <select class="pagesize input-sm" title="Select page size">                            <option value="5" selected>5</option>                            <option value="10">10</option>                            <option value="25">25</option>                            <option value="50">50</option>                        </select>                        <select class="pagenum input-sm" title="Select page number"></select>                    </th>                </tr>            </tfoot>' */
					content += '</table>';
					document.getElementById('visitRecords').innerHTML = content;
					

					
				}
				document.getElementById('visitRecords').style.display="block";
				

			},
			error : function(e) {
				 //alert('Error: ' + e.responseText);
			}
		});

	}
	
}

</script>

<script>
var contextPath = "<%=request.getContextPath()%>";
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
			<h3 class="panel-title">Document Search Informations</h3>
		</div>

		<div class="panel-body">


			<form role="form" class="form-horizontal form-bordered">


				<div class="form-group">
					<label class="col-sm-3 control-label" for="typeahead-local">Advanced
						Search Text</label>
					<div class="col-sm-5">


						<div class="input-group input-group-in">
							<span class="input-group-addon text-silver"></span> <input
								type="text" id="search" name="search" class="form-control"
								placeholder="Advanced Search Text" />
						</div>



					</div>
				</div>







			</form>




		</div>
	</div>


	<div id="searchButton" style="margin: 3%;">

		<input type="button" value="Search" class="btn btn-inverse"
			onclick="showDetais()">

	</div>

	<div class="panel-body" id="visitRecords" style="display: none">
		<div class="table-responsive table-responsive-datatables">
			<table class="table table-hover table-bordered">
				<thead>
					<tr>
						<th></th>

						<th>Documents</th>

					</tr>
				</thead>



			</table>
		</div>
	</div>




	<input type="hidden" id="visitid" name="visitid" value="" readonly
		class="form-control" />
	<form role="form" class="form-horizontal form-bordered">
		<div id="viewPhyAllVisit" style="display: none;">
			<h3>View Visit</h3>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="typeahead-local">Patient
					Name</label>
				<div class="col-sm-5">
					<div class="input-group input-group-in">
						<span class="input-group-addon text-silver"><i
							class="glyphicon glyphicon-user"></i></span> <input type="text"
							id="vistAllPatientName" name="vistAllPatientName" readonly
							value="" class="form-control" />
					</div>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label" for="typeahead-local">Date
					of Visit</label>
				<div class="col-sm-5">
					<div class="input-group input-group-in">
						<span class="input-group-addon text-silver"><i
							class="glyphicon glyphicon-user"></i></span> <input type="text"
							id="date_of_visit" name="date_of_visit" value="" readonly
							class="form-control" />
					</div>
					
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label" for="typeahead-local">Reason
					for Visit</label>
				<div class="col-sm-5">
					<div class="input-group input-group-in">
						<span class="input-group-addon text-silver"><i
							class="glyphicon glyphicon-user"></i></span> <input type="text"
							id="reason_for_visit" name="reason_for_visit" readonly value=""
							class="form-control" />
					</div>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label" for="typeahead-local">Physician
					Name</label>
				<div class="col-sm-5">
					<div class="input-group input-group-in">
						<span class="input-group-addon text-silver"><i
							class="glyphicon glyphicon-user"></i></span> <input type="text"
							id="vistAllPhysicianName" name="vistAllPhysicianName" readonly
							value="" class="form-control" />
					</div>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label" for="typeahead-local">Prescription
				</label>
				<div class="col-sm-5">
					<div class="input-group input-group-in">
						<span class="input-group-addon text-silver"><i
							class="glyphicon glyphicon-user"></i></span>
						<textarea rows="10" cols="50" name="prescription" value=""
							readonly id="prescription" style="resize: none;"></textarea>
					</div>
				</div>
			</div>

			<input type="hidden" name="tag" id="tag" value="">

		</div>

	</form>




</div>




<jsp:include page="../footerbluehub.jsp"></jsp:include>