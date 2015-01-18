$(function() {
                $("#accordion").accordion({
                    collapsible: true
                });
            });

            $(function() {
                $("#tabs").tabs();
            });
     
            function callLoginScreen() {				
       		 document.clinicalUserCeuRequirementInfoForm.action=contextPath+"/login.do";
       		 document.clinicalUserCeuRequirementInfoForm.submit();	 		
       		};
            
            function pageNavigation() {
            	$('#submit_btn').attr("disabled", true);
            	document.clinicalUserCeuRequirementInfoForm.action = contextPath+"/submitClinicalUserRegistrationForms.do";
        		document.clinicalUserCeuRequirementInfoForm.submit();
            };
            
            function pageReverseNavigation() {
            	document.clinicalUserCeuRequirementInfoForm.action = contextPath+"/getClinicalUserTrainingInfoFormBack.do";
        		document.clinicalUserCeuRequirementInfoForm.submit();
            }
            
            $(function() {
            	$("#bcbaLicenseRenewalDate").datepicker({
            		yearRange: "1960:+0",
        			changeMonth: true,
        			changeYear: true
            	});
            	$("#bcbaLicenseExpirationDate").datepicker({
            		yearRange: "1960:+0",
        			changeMonth: true,
        			changeYear: true
            	});
            	$("#bcabaLicenseRenewalDate").datepicker({
            		yearRange: "1960:+0",
        			changeMonth: true,
        			changeYear: true
            	});
            	$("#bcabaLicenseExpirationDate").datepicker({
            		yearRange: "1960:+0",
        			changeMonth: true,
        			changeYear: true
            	});
            	$("#mhProfCeuLicenseRenDate").datepicker({
            		yearRange: "1960:+0",
        			changeMonth: true,
        			changeYear: true
            	});
            	$("#mhProfCeuLicenseExpDate").datepicker({
            		yearRange: "1960:+0",
        			changeMonth: true,
        			changeYear: true
            	});
            	$("#Minimum_2years_experience").datepicker({
            		yearRange: "1960:+0",
        			changeMonth: true,
        			changeYear: true
            	});
            	$("#Minimum_2years_experience2").datepicker({
            		yearRange: "1960:+0",
        			changeMonth: true,
        			changeYear: true
            	});
            	$("#profLicenseCeuRenDate").datepicker({
            		yearRange: "1960:+0",
        			changeMonth: true,
        			changeYear: true
            	});
            	$("#profLicenseCeuExpDate").datepicker({
            		yearRange: "1960:+0",
        			changeMonth: true,
        			changeYear: true
            	});            	
            });
            function HandleFileButtonClick(myFile,txtFakeText){
              	 document.getElementById(myFile).click();	 
              	 setInterval(function(){fileDispaly(myFile,txtFakeText)},1400);
                }
            function fileDispaly(myFile,txtFakeText){
             	  document.getElementById(txtFakeText).value = document.getElementById(myFile).value;
               }
            
            function calculateRemaining(totalId, actualId, remainingId){
            	var total = document.getElementById(totalId).value;
            	var actual = document.getElementById(actualId).value;
            	if(actual != '' && actual != '.'){
            		var intTotal = parseFloat(total);
            		var floatActual = parseFloat(actual);
            		
            		if (floatActual < 0){
                		floatActual = 0;
                	}
                	if (floatActual > intTotal){
                		floatActual = intTotal;
                		document.getElementById(actualId).value = floatActual.toString();
                	}
                	
                	var floatRemaining = intTotal - floatActual;
                	var remainingString = floatRemaining.toString();
                	document.getElementById(remainingId).value = remainingString;
            	}
            }
            
            $(document).ready(function(){        		
        		var regsiterval =$('#regsiterval').val();
        		if(regsiterval=='0'){
        			$('#cntDivId').hide();
        			$('#registerDivId').show();		
        		}else{
        			$('#cntDivId').show();		
        			$('#registerDivId').hide();
        		}	        		        
        	});
            
            function termCheck(ele, id) {
            	x = document.getElementById(id);
            	if (ele.checked == true) x.disabled = false;
            	else x.disabled = true;
            }

            	$(document).ready(function() {
            		//open popup
            		$("#terms").click(function() {
            			$("#termsandcondition").fadeIn(400);
            			positionPopup();
            			
            });

            		//close popup
            		$("#close").click(function() {
            			$("#termsandcondition").fadeOut(400);
            		});
            	});
            	
            $(document).ready(function() {
            		//open popup
            		$("#policy").click(function() {
            			$("#privacypolicy").fadeIn(400);
            			positionPopup1();
            			
            });

            //close popup
            $("#pclose").click(function() {
            	$("#privacypolicy").fadeOut(400);
            	});
            });	
            	//position the popup at the center of the page
            	function positionPopup() {
            		if (!$("#termsandcondition").is(':visible')) {
            			return;
            		}
            		$("#termsandcondition").css({
            		    right:'27%'
            		   
            		    });
            		}
            		$(window).bind('resize', positionPopup);   
            		//position the popup1 at the center of the page
            		function positionPopup1() {
            			if (!$("#privacypolicy").is(':visible')) {
            				return;
            			}
            			$("#privacypolicy").css({
            			    right:'27%'
            			  });
            			}
            			$(window).bind('resize', positionPopup1);     
