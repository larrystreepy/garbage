

      
function callFileShow(filename) {
	var emailval =$('#emailval').val();
	var url=contextPath+"/DisplayImage?emailId=" +emailval+"&fileName="+filename;
	 window.open(url,'','width=700,height=500');			
	}

      
	function pageReverseNavigation() {
		document.clinicalUserTrainingInfoForm.action =contextPath+"/getClinicalBackgroundRegistrationBack.do"
		document.clinicalUserTrainingInfoForm.submit();
	}
	function pageNavigation() {

		document.clinicalUserTrainingInfoForm.action =contextPath+"/clinicalUserFinalRegistration.do"
		document.clinicalUserTrainingInfoForm.submit();
	};

	$(function() {
		for ( var e = 0; e < 19; e++) {
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
			$("#compDate" + e).datepicker({
				yearRange: "1960:+20",
				changeMonth: true,
				changeYear: true
			});
		}
		makeDatePicker();
	});

	function HandleFileButtonClick(myFile, txtFakeText) {
		document.getElementById(myFile).click();
		setInterval(function() {
			fileDispaly(myFile, txtFakeText)
		}, 1400);
	}
	function fileDispaly(myFile, txtFakeText) {
		var fakeTextValue = document.getElementById(myFile).value;
		fakeTextValue = fakeTextValue.replace("C:\\fakepath\\", "");
		document.getElementById(txtFakeText).value = fakeTextValue;
	}

	// Handles file format validation
	function checkFileUpload(element) {

		// valid doc types
		var fileTypes = [ "doc", "docx", "pdf", "png", "gif", "tif", "tiff",
				"jpeg", "jpg", "jif", "jfif", "jp2", "jpx", "j2k", "j2c",
				"fpx", "pcd" ];

		// split the input file name
		var fileNameArray = element.value.split(".");
		var isValidDoc = true;
		var falseCount = 0;

		// check whether doc type is valid
		for ( var i = 0; i < fileTypes.length; i++) {
			if (fileNameArray[1] != fileTypes[i]) {
				falseCount = falseCount + 1;
			}
		}
		if (falseCount == fileTypes.length) {
			isValidDoc = false;
		}
		var newLabel = document.createElement('label');
		var childNodes = element.parentNode.childNodes;
		var nodeToRemove;
		var nodeAlreadyPresent = false;

		// check whether error label is already present
		for (i = 0; i < childNodes.length; i++) {
			var childNode = childNodes[i];
			if (childNode.className == 'error') {
				nodeAlreadyPresent = true;
				nodeToRemove = childNode;
			}
		}

		// if error label is already present remove it
		if (nodeAlreadyPresent) {
			element.parentNode.removeChild(nodeToRemove);
		}

		// insert new error label
		if (isValidDoc == false) {
			element.value = "";
			element.parentNode.appendChild(newLabel);
			newLabel.innerHTML = "Please select file of valid type";
			newLabel.className = "error";
		}
		return isValidDoc;
	}

	function changeDocLink(docLinkId, fileUploadId) {
		var fileElementValue = document.getElementById(fileUploadId).value;
		fileElementValue = fileElementValue.replace("C:\\fakepath\\", "");
		document.getElementById(docLinkId).value = fileElementValue;
	}

	
	
$(function() {
	//uploadFile();
		
		$(".fileUploadBtn").uploadify({
	        swf           : 'flash/uploadify.swf',
	        uploader      :contextPath+'/uploadFile.do',
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
		uploadFile();
		});
	
	
	 function uploadFile() {		
		
	//	var emailId =$('#emailval').val();
    //	var ceuType = $('#ceuType0').val();
    	
    	$(".fileUploadCeu").uploadify({
	        swf           : 'flash/uploadify.swf',
	        uploader      :contextPath+'/uploadCeuTrainingFile.do',
			'method'         : 'post',
			'formData' :{ 'emailId' : "emailId", 'ceuType':"ceuType"},
			'buttonText' : 'Upload',
			'fileTypeDesc' : 'Image Files, Doc Files',
			'fileTypeExts' : '*.doc; *.docx; *.pdf; *.png; *.gif; *.tif; *.tiff; *.jpeg; *.jpg; *.jif; *.jfif; *.jp2; *.j2k; *.j2c; *.fpx; *.pcd;',
		    'onUploadStart' : function(file) {
		    	
		    	var uploadId = file.id;
		    	var upload = uploadId.split('_');
		    	var uploadNo = upload[1];
		    	//alert("uploadNo==>"+uploadNo);
		    	// form values
		    	var emailId =$('#emailval').val();
		    	var ceuType = $('#ceuType'+uploadNo).val();

		    	// upload file
		        $("#ceuTrainingUpLink"+uploadNo).uploadify("settings","formData", {'emailId' : emailId, 'ceuType':ceuType});
		    },
		    'onUploadComplete' : function(file) {
		    	// get id
		    	var uploadId = file.id;
		    	var upload = uploadId.split('_');
		    	var uploadNo = upload[1];
		    	
		    	// set file name in input box and doc link
		    	var fileName = file.name;
		    	var inputId = 'ceuTrainingLink'+uploadNo;
		    	var docLinkId = 'ceudoclink'+uploadNo;
		    	document.getElementById(inputId).value = file.name;
		    	document.getElementById(docLinkId).value = file.name;
	        } 
			
	    });  
		} 
	
	 function makeDatePicker(){
		  $(".calendar_txt").datepicker({
			yearRange: "1960:+20",
			changeMonth: true,
			changeYear: true
		});
		  }
	 
   var y = 17+ceuIndex;
    var ii = 0+ceuIndex;
    function addcert() {
        y = y + 1;
		$("#ceuTrainingTable").append('<tr> <td style="width: 100px">  <select name="clinicalUserCEUCertificateForm['+ii+'].ceuType" id="ceuType'+y+'" class="select" style="width: 100px !important;"><option value="">Select</option> <option value="BACB">BACB</option> <option value="Mental Health">Mental Health</option> <option value="BACB & Mental Health">BACB & Mental Health</option> </select></td> <td> <input type="text" id="ceudocdate'+y+'" name="clinicalUserCEUCertificateForm['+ii+'].ceudocdate" class="calendar_txt" onChange="trainingStartDateValidation(\'ceudocdate'+y+'\',\'ceucompldate'+y+'\',\'ceuexpdate'+y+'\' )" readonly> </td> <td> <input type="text" class="file_small" id="ceudoclink'+y+'" ondblclick="javascript:callFileShow(this.value);" name="clinicalUserCEUCertificateForm['+ii+'].ceudoclink"/> </td> <td><input type="text" id="ceucompldate'+y+'" name="clinicalUserCEUCertificateForm['+ii+'].ceucompldate" class="calendar_txt" onChange="trainingCompDateValidation(\'ceudocdate'+y+'\',\'ceucompldate'+y+'\',\'ceuexpdate'+y+'\' )" readonly></td> <td> <input type="text" id="ceuexpdate'+y+'" name="clinicalUserCEUCertificateForm['+ii+'].ceuexpdate" class="calendar_txt" onChange="trainingEndDateValidation(\'ceudocdate'+y+'\',\'ceucompldate'+y+'\',\'ceuexpdate'+y+'\' )" readonly> </td> <td> <input type="text" id="ceunoofhours'+y+'" name="clinicalUserCEUCertificateForm['+ii+'].ceunoofhours"> </td><td class="clinical-upload-td" style="border:none; background:none;"> <input type="text" name="clinicalUserCEUCertificateForm['+ii+'].fileName" id="ceuTrainingLink'+y+'" readonly style="width:90px;" value=""> <input type="file" id="ceuTrainingUpLink'+y+'" name="file" class="fileUploadCeu"> </td> <td class="noborder"><input type="button" value="" id="delCeuTrainingInfo" class="del_row" onclick="delceu('+y+')"/> </td> <td style="border:none; background:none;display: none;"><input type="text" name="clinicalUserCEUCertificateForm['+ii+'].findDel" id="findDel'+y+'" value="notdeleted"></td> </tr>');
		makeDatePicker();
		uploadFile();
		ii = ii + 1;
        }
		
        function delceu(delRow){
        	
        	$("#ceuType"+delRow).val(null);
        	$("#ceudocdate"+delRow).val(null);
        	$("#ceudoclink"+delRow).val(null);
        	$("#ceucompldate"+delRow).val(null);
        	$("#ceuexpdate"+delRow).val(null);
        	$("#ceunoofhours"+delRow).val(null);
        	$("#ceuTrainingLink"+delRow).val(null);
        	
        	$("#findDel"+delRow).val("deleted");
        	
        	
        	$(document).on("click", "#delCeuTrainingInfo", function() {
            $(this).parents("tr").remove();
        });
        }
    