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
			getChartFromServer(period.dataFrom, period.dataTo, terminalNumber);
		}
		
	});
});
function getChartFromServer(dataFrom, dataTo, terminalNumber){
	var period = {
			dataFrom: dataFrom,
			dataTo: dataTo,
			terminalNumber: terminalNumber
	}
	$.ajax({
		type : "POST",
		url : 'chart/fuel/build',
		data : JSON.stringify(period),
		dataType : 'json',
		contentType : 'application/json',
		mimeType : 'application/json',
		success : function(dataArray) {
				buildChart(dataArray);
			}

	});
}