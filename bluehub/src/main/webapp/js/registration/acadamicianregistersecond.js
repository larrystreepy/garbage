$(document).ready(function() {
	var regsiterval = $('#regsiterval').val();
	if (regsiterval == '0') {
		$('#cntDivId').hide();
		$('#registerDivId').show();
	} else {
		$('#cntDivId').show();
		$('#registerDivId').hide();
	}
});

function showPopup() {
	$("#overlay_form").fadeIn(1000);
	positionPopup();
	$("#close").click(function() {
		$("#overlay_form").fadeOut(500);
	});
}
function positionPopup() {
	if (!$("#overlay_form").is(':visible')) {
		return;
	}
	$("#overlay_form").css({
		left : ($(window).width() - $('#overlay_form').width()) / 2,
		top : ($(window).width() - $('#overlay_form').width()) / 5,
		position : 'absolute',
		height : '600px !important',
		width : '600px !important'
	});
}
$(window).bind('resize', positionPopup);

function callFileShow1(filename) {
	var emailval = $('#emailval').val();
	document.getElementById("imageWrite").innerHTML = "<img src=contextPath+'/DisplayImage?emailId="
			+ emailval + "&fileName=" + filename + "'/>";
	showPopup();
}

function callFileShow12(filename) {
	var emailval = $('#emailval').val();
	$.ajax({
		type : "GET",
		url : contextPath + "/DisplayImage",
		data : 'emailId=' + emailval + '&fileName=' + filename,
		success : function(response) {
			$("#imageWrite").html(response);
		}
	});
}

function callFileShow(filename) {
	var emailval = $('#emailval').val();
	if (filename == '') {
	} else {
		var selectedText = filename.substring(0, 4);
		if (selectedText == 'http') {
			window.open(sel, '', 'width=700,height=500');
		} else {
			var url = contextPath + "/DisplayImage?emailId=" + emailval
					+ "&fileName=" + filename;
			window.open(url, '', 'width=700,height=500');
		}
	}
}
function callLoginScreen() {
	document.userRegistrationForm.action = contextPath + "/login.do";
	document.userRegistrationForm.submit();
};

function pageReverseNavigation() {
	document.userRegistrationForm.action = contextPath
			+ "/getAcademicianBackRegistration.do";
	document.userRegistrationForm.submit();
}
function pageNavigation() {
	$('#submit_btn').attr("disabled", true);
	document.userRegistrationForm.action = contextPath
			+ "/registrationAcademicianSubmit.do";
	document.userRegistrationForm.submit();
};

$(function() {
	for ( var e = 0; e < 18; e++) {
		$("#docDate" + e).datepicker({
			yearRange : "1960:+20",
			changeMonth : true,
			changeYear : true
		});
		$("#expDate" + e).datepicker({
			yearRange : "1960:+20",
			changeMonth : true,
			changeYear : true
		});

		$("#docTrainDate" + e).datepicker({
			yearRange : "1960:+20",
			changeMonth : true,
			changeYear : true
		});

		$("#expTrainDate" + e).datepicker({
			yearRange : "1960:+20",
			changeMonth : true,
			changeYear : true
		});

		$("#compTrainDate" + e).datepicker({
			yearRange : "1960:+20",
			changeMonth : true,
			changeYear : true
		});
	}
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
	clearInterval(refreshIntervalId);
}

// Handles file format validation
function checkFileUpload(element) {

	// valid doc types
	var fileTypes = [ "doc", "docx", "pdf", "png", "gif", "tif", "tiff",
			"jpeg", "jpg", "jif", "jfif", "jp2", "jpx", "j2k", "j2c", "fpx",
			"pcd" ];

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

	$(".fileUploadBtn")
			.uploadify(
					{
						swf : 'flash/uploadify.swf',
						uploader : contextPath + '/uploadFile.do',
						'method' : 'post',
						'formData' : {
							'docId' : "docId",
							'emailId' : "emailId",
							'type' : "type"
						},
						'buttonText' : 'Upload',
						'fileTypeDesc' : 'Image Files, Doc Files',
						'fileTypeExts' : '*.doc; *.docx; *.pdf; *.png; *.gif; *.tif; *.tiff; *.jpeg; *.jpg; *.jif; *.jfif; *.jp2; *.j2k; *.j2c; *.fpx; *.pcd;',
						'onUploadStart' : function(file) {

							var uploadId = file.id;
							var upload = uploadId.split('_');
							var uploadNo = upload[1];

							// form values
							var docId = $('#docId' + uploadNo).val();
							var emailId = $('#emailId' + uploadNo).val();
							var type = $('#type' + uploadNo).val();

							// upload file
							$("#resume_up_link" + uploadNo).uploadify(
									"settings", "formData", {
										'docId' : docId,
										'emailId' : emailId,
										'type' : type
									});
						},
						'onUploadComplete' : function(file) {

							// get id
							var uploadId = file.id;
							var upload = uploadId.split('_');
							var uploadNo = upload[1];

							// set file name in input box and doc link
							var fileName = file.name;
							var inputId = 'resume_link1' + uploadNo;
							var docLinkId = 'docLink' + uploadNo;
							document.getElementById(inputId).value = file.name;
							document.getElementById(docLinkId).value = file.name;
						}

					});
});

$(function() {

	$(".fileUploadBtnTrain")
			.uploadify(
					{
						swf : 'flash/uploadify.swf',
						uploader : 'uploadFile.do',
						'method' : 'post',
						'formData' : {
							'docId' : "docId",
							'emailId' : "emailId",
							'type' : "type"
						},
						'buttonText' : 'Upload',
						'fileTypeDesc' : 'Image Files, Doc Files',
						'fileTypeExts' : '*.doc; *.docx; *.pdf; *.png; *.gif; *.tif; *.tiff; *.jpeg; *.jpg; *.jif; *.jfif; *.jp2; *.j2k; *.j2c; *.fpx; *.pcd;',
						'onUploadStart' : function(file) {

							var uploadId = file.id;
							var upload = uploadId.split('_');
							var uploadNo = upload[1];
							uploadNo = parseInt(uploadNo) - 12;
							uploadNo = uploadNo.toString();

							// form values
							var docId = $('#docIdTrain' + uploadNo).val();
							var emailId = $('#emailIdTrain' + uploadNo).val();
							var type = $('#typeTrain' + uploadNo).val();

							// upload file
							$("#resume_up_linkTrain" + uploadNo).uploadify(
									"settings", "formData", {
										'docId' : docId,
										'emailId' : emailId,
										'type' : type
									});
						},
						'onUploadComplete' : function(file) {

							// get id
							var uploadId = file.id;
							var upload = uploadId.split('_');
							var uploadNo = upload[1];
							uploadNo = parseInt(uploadNo) - 12;
							uploadNo = uploadNo.toString();

							// set file name in input box and doc link
							var fileName = file.name;
							var inputId = 'resume_linkTrain' + uploadNo;
							var docLinkId = 'docLinkTrain' + uploadNo;
							document.getElementById(inputId).value = file.name;
							document.getElementById(docLinkId).value = file.name;
						}
					});
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
$("#closes").click(function() {
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