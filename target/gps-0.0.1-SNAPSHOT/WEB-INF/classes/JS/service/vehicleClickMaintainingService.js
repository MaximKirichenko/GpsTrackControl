/**
 * 
 */

function setTerminalNumber(terminalNumber){
	$.ajax({
		type : "POST",
		url : 'carInfo',
		data : JSON.stringify(terminalNumber),
		dataType : 'json',
		contentType : 'application/json',
		mimeType : 'application/json',
		success: function (data) { 	
			console.log(data);
			var name = data.name + " (" + data.regNumber + ")";
			console.log(name);
			$("#vehicleName h3").text(name);
        }
	});
	$("#selectedTerminalNumber").val(terminalNumber);
}
function getTerminalNumber(){
	
	return $("#selectedTerminalNumber").val();
}