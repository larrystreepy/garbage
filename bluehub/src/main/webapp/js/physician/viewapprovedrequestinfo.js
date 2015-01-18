function fngetSharedRequestInfoBehalfOfPatient(shareId) {

	window.open(contextPath + "/physician/viewShareRequestInfo.do?requestId="
			+ shareId);

}

function fnLoadApprovedRequests() {

	var status = 1;

	var content = '';
	content += '<table class="table table-hover table-bordered" id="shareedRequestTable">';
	content += '<thead> <th>First Name</th><th>Last Name</th><th>Dob</th> <th>SSN</th><th>Contact Number</th><th>Address</th><th>Status</th></thead>';

	$
			.ajax({
				type : "GET",
				url : contextPath
						+ "/physician/getPhysicianSharedRequestInfo.do",
				cache : false,
				async : false,
				success : function(response) {

					var parsedJson = $.parseJSON(response);
					var dateOfVisit = dov = '';
					if (parsedJson && parsedJson.length > 0) {
						for ( var i = 0; i < parsedJson.length; i++) {
							content += '<tr onclick="fngetSharedRequestInfoBehalfOfPatient('
									+ parsedJson[i][0] + ') ">'
							content += '<td id="patient' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][1] + '</td>';
							content += '<td id="visitdate' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][3] + '</td>';
							content += '<td id="reason' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][2] + '</td>';
							content += '<td id="prescription' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][4] + '</td>';
							content += '<td id="prescription' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][5] + '</td>';
							content += '<td id="prescription' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][6] + '</td>';
							content += '<td id="prescription' + i
									+ '"  class="user_list_link">'
									+ parsedJson[i][7] + '</td>';
							content = content + '</tr>';
						}
					} else {
						content += '<tr><td colspan="5" align="center" >No records/data available</td></tr>';
					}
					content += '</table>';

					document.getElementById('RequestSharesDiv').innerHTML = content;

				},
				error : function(e) {
					alert('Error: ' + e.responseText);
				}
			});

}