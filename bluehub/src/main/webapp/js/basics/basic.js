var displayMsgId = "";
function fnClearMsgField() {
	document.getElementById(displayMsgId).style.display = "none";
}
function fnSetTimeout(id, time) { // here id is span id or some ids
	if (time == undefined)
		time = 3000;
	displayMsgId = id;
	document.getElementById(displayMsgId).style.display = "block";
	setTimeout('fnClearMsgField()', time);
}

function validateCharacter(obj)// here object is textbox id
{
	var totalCharacterCount = document.getElementById(obj).value;

	// window.clipboardData.getData(â€˜Textâ€™);

	// var strValidChars =
	// â€œABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-.â€�;
	var strValidChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-. ";
	var strChar;
	var FilteredChars = "";
	for (i = 0; i < totalCharacterCount.length; i++) {
		strChar = totalCharacterCount.charAt(i);
		if (strValidChars.indexOf(strChar) != -1) {
			FilteredChars = FilteredChars + strChar;
		}
	}
	document.getElementById(obj).value = FilteredChars;
	return false;
}