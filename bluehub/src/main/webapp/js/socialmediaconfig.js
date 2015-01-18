   
    
    

    
    

    function callCheckUserEmail() {
    	// get the form values		        
    	var mailId = $('#newsletter').val();
    	if (mailId != "") {
    		$.ajax({
    			type : "GET",
    			dataType : "json",
    			url : contextPath + "/findNewsLetterEmail.do",
    			data : "userMail=" + mailId,
    			success : function(response) {
    				// we have the response		       
    				var obj = response;
    				var status = obj.status;
    				var message = obj.message;
    				if (status == 'Yes') {
    					$('#emailValid').html(message);
    					$('#emailValid').fadeIn().delay(4000).fadeOut('slow');
    					document.getElementById("newsletter").value = "";
    					document.getElementById("newsletter").focus();
    				} else {
    					saveNewsLetterEmail();
    				}
    			},
    			error : function(e) {
    				alert('Error: ' + e);
    			}
    		});
    	}
    }
    
    

    function saveNewsLetterEmail() {
    	// get the form values		        
    	var mailId = $('#newsletter').val();
    	if (mailId != "") {
    		$.ajax({
    			type : "GET",
    			dataType : "json",
    			url : contextPath + "/saveNewsLetterEmail.do",
    			data : "userMail=" + mailId,
    			success : function(response) {
    				// we have the response		       
    				var obj = response;
    				var status = obj.status;
    				var message = obj.message;
    				if (status == 'Yes') {
    					$('#emailValid').html(message);
    					$('#emailValid').fadeIn().delay(4000).fadeOut('slow');
    		
    					document.getElementById("newsletter").value = "";
    					document.getElementById("newsletter").focus();
    				}
    			},
    			error : function(e) {
    				alert('Error: ' + e);
    			}
    		});
    	}
    }
    
    