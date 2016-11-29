/**
 * 
 */
$( document ).ready(function() {
	var period;
	$("#dateSubmit").click(function(){
		period = getPeriod();
		terminalNumber = getTerminalNumber();
		if(terminalNumber == ""){
			window.alert("Сперва выберите автомобиль");
		}else if(period.dataTo < period.dataFrom) {
			window.alert("Ошибка! Поменяйте местами конец и начало периода");
		}else{
			getTrackFromServer(period.dataFrom, period.dataTo, terminalNumber);
		}
		
	});
});

function getTrackFromServer(dataFrom, dataTo, terminalNumber){
	var period = {
			dataFrom: dataFrom,
			dataTo: dataTo,
			terminalNumber: terminalNumber
	}
	$.ajax({
		type : "POST",
		url : 'buildTrack',
		data : JSON.stringify(period),
		dataType : 'json',
		contentType : 'application/json',
		mimeType : 'application/json',
		success: function (data) { 	
			if(data.terminalDateList.length == 0){
				$("#durationMovement").text("-");
				$("#startMovement").text("-");
				$("#finishMovement").text("-");
				$("#canConsumption").text("-");
				$("#pathLength").text("-");
				window.alert("За данный период не было движения");
			}else if((data.terminalDateList[0].latitude==0 || data.terminalDateList[0].longitude==0) && data.terminalDateList.length == 1){
				$("#durationMovement").text("-");
				$("#startMovement").text("-");
				$("#finishMovement").text("-");
				$("#canConsumption").text("-");
				$("#pathLength").text("-");
				window.alert("Трактор вне зоны покрытия");
			}else if(data.terminalDateList[0].isCorrect==1 && data.terminalDateList.length == 1){
				$("#durationMovement").text("-");
				$("#startMovement").text("-");
				$("#finishMovement").text("-");
				
				$("#canConsumption").text(data.canConsumption);
				$("#pathLength").text(data.pathLength);
				setPoint(data.terminalDateList[0]);
			}
			else{
				console.log(data);
				$("#durationMovement").text(((data.startMovement-data.finishMovement)/1000).toString().toHHMMSS());
				$("#startMovement").text(new Date(data.finishMovement).toLocaleString());
				$("#finishMovement").text(new Date(data.startMovement).toLocaleString());
				$("#canConsumption").text(data.canConsumption);
				$("#pathLength").text(data.pathLength)
				addTrack(data.terminalDateList);
			}
			
        }
	});
}

