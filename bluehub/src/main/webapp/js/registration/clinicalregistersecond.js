function callFileShow(filename) {
	var emailval =$('#emailval').val();
	var url=contextPath+"/DisplayImage?emailId=" +emailval+"&fileName="+filename;
	 window.open(url,'','width=700,height=500');			
	}
	function pageReverseNavigation() {
		document.clinicalUserBackgroundInfoForm.action = contextPath+"/getclinicalUserRegistrationBack.do";
		document.clinicalUserBackgroundInfoForm.submit();
	}

	function pageNavigation() {
		document.clinicalUserBackgroundInfoForm.action = contextPath+"/clinicalUserRegistrationThirdPage.do";
		document.clinicalUserBackgroundInfoForm.submit();
	};

	$(function() {
		for ( var e = 0; e < 18; e++) {
			$("#docDate" + e).datepicker({
				yearRange: "1960:+20",
				changeMonth: true,
				changeYear: true
			});
			$("#expDate" + e).datepicker({
				yearRange: "1960:+20",
				changeMonth: true,
				changeYear: true
			});
		}
	});

	var refreshIntervalId  = 0;
	function HandleFileButtonClick(myFile, txtFakeText) {
		document.getElementById(myFile).click();
		refreshIntervalId  = setInterval(function() {
			fileDispaly(myFile, txtFakeText)
		}, 1400);
	}

	function fileDispaly(myFile, txtFakeText) {
		var fakeTextValue = document.getElementById(myFile).value;
		fakeTextValue = fakeTextValue.replace("C:\\fakepath\\", "");
		document.getElementById(txtFakeText).value = fakeTextValue;
		clearInterval(refreshIntervalId);
	}

	function changeDocLink(docLinkId, fileUploadId) {
		var fileElementValue = document.getElementById(fileUploadId).value;
		fileElementValue = fileElementValue.replace("C:\\fakepath\\", "");
		document.getElementById(docLinkId).value = fileElementValue;
	}
	
$(function() {
		
		$(".fileUploadBtn").uploadify({
	        swf           : 'flash/uploadify.swf',
	        uploader      : contextPath+'/uploadFile.do',
			'method'         : 'post',
			'formData' :{ 'docId': "docId", 'emailId' : "emailId", 'type':"type"},
			'buttonText' : 'Upload',
			'fileTypeDesc' : 'Image Files, Doc Files',
			'fileTypeExts' : '*.doc; *.docx; *.pdf; *.png; *.gif; *.tif; *.tiff; *.jpeg; *.jpg; *.jif; *.jfif; *.jp2; *.j2k; *.j2c; *.fpx; *.pcd;',
		    'onUploadStart' : function(file) {
		    	
		    	var uploadId = file.id;
		    	var upload = uploadId.split('_');
		    	var uploadNo = upload[1];
		    	
		    	// form values
		    	var docId = $('#docId'+uploadNo).val();
		    	var emailId = $('#emailId'+uploadNo).val();
		    	var type = $('#type'+uploadNo).val();
		    	
		    	// upload file
		        $("#resume_up_link"+uploadNo).uploadify("settings","formData", { 'docId': docId, 'emailId' : emailId, 'type':type});
		    },
		    'onUploadComplete' : function(file) {
		    	
		    	// get id
		    	var uploadId = file.id;
		    	var upload = uploadId.split('_');
		    	var uploadNo = upload[1];
		    	
		    	// set file name in input box and doc link
		    	var fileName = file.name;
		    	var inputId = 'resume_link'+uploadNo;
		    	var docLinkId = 'docLink'+uploadNo;
		    	document.getElementById(inputId).value = file.name;
		    	document.getElementById(docLinkId).value = file.name;
	        } 
			
	    }); 
		});