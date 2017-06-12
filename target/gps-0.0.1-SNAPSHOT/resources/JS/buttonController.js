var periodDto = {
	dataFrom : 0,
	dataTo : 0,
	terminalNumber : 0
};

var total = new Array();
var time;
var ctx;

function writeReport(serverData) {
	
	document.getElementById('LabelConsumptionFromCan').innerHTML = 'Расход по счетчику CAN-шины: '
			+ serverData.fuelConsumptionFromCan;
	
	$("#reportTable").find("tr:gt(0)").remove();
	for (var i = 0; i < serverData.refuelings.length; i++) {
		if(i==0){
			$("#reportTable tbody").append(
					"<tr><td>"
							+ (i + 1)
							+ "</td>"
							+ "<td>"
							+ serverData.refuelings[i].fuelForStart
							+ "</td>"
							+ "<td>"
							+ serverData.refuelings[i].fuelForEnd
							+ "</td>"
							+ "<td>"
							+ serverData.refuelings[i].scope
							+ "</td>"
							+ "<td>"
							+ new Date(serverData.refuelings[i].startTime)
									.toLocaleString()
							+ "</td>"
							+ "<td>"
							+ new Date(serverData.refuelings[i].endTime)
									.toLocaleString() + "</td>"
							+"<td>"+serverData.refuelings[i].fuelUsedFromSensor+"</td>"
							+"<td>"+serverData.refuelings[i].fuelCanDifferent+"</td>"
							+"</tr>");
		}else
		$("#reportTable tr:last").after(
				"<tr><td>"
						+ (i + 1)
						+ "</td>"
						+ "<td>"
						+ serverData.refuelings[i].fuelForStart
						+ "</td>"
						+ "<td>"
						+ serverData.refuelings[i].fuelForEnd
						+ "</td>"
						+ "<td>"
						+ serverData.refuelings[i].scope
						+ "</td>"
						+ "<td>"
						+ new Date(serverData.refuelings[i].startTime)
								.toLocaleString()
						+ "</td>"
						+ "<td>"
						+ new Date(serverData.refuelings[i].endTime)
						.toLocaleString() + "</td>"
						+"<td>"+serverData.refuelings[i].fuelUsedFromSensor+"</td>"
						+"<td>"+serverData.refuelings[i].fuelCanDifferent+"</td>"
						+"</tr>");
	}

}
function getFilteredDateArray(serversResponse){
	var dateItemsOnPage = 25;
	var dataArrayDelimeter = Math.floor(serversResponse.engineSpeedDatas.length / dateItemsOnPage);
	var time = new Array();
	for(var i=0; i<serversResponse.engineSpeedDatas.length; i++){
		if(i%dataArrayDelimeter == 0)
			time.push(new Date(serversResponse.messageData[i]).toLocaleString());
		else
			time.push(" ");
	}
	return time;
	
}
var myLineChart;
var datasets;
function buildChart(dataArray) {
	var time = getFilteredDateArray(dataArray);
	writeReport(dataArray);
	var leftTankData = {
			label : "left",
			fillColor : "rgba(151,187,205,0)",
			strokeColor : "rgba(0,255,0,1)",
			pointColor : "rgba(0,255,0,1)",
			pointStrokeColor : "#fff",
			pointHighlightFill : "#fff",
			pointHighlightStroke : "rgba(151,187,205,1)",
			data : dataArray.leftTankDatas	
	};
	var rightTankData = {
			label : "right",
			fillColor : "rgba(151,187,205,0)",
			strokeColor : "rgba(0, 0, 255, 1)",
			pointColor : "rgba(0, 0, 255, 1)",
			pointStrokeColor : "#fff",
			pointHighlightFill : "#fff",
			pointHighlightStroke : "rgba(151,187,205,1)",
			data : dataArray.rightTankDatas
	};
	var engineeData = {
			label : "enginee",
			fillColor : "rgba(151,187,205,0)",
			strokeColor : "rgba(255, 0, 0, 0.5)",
			pointColor : "rgba(255, 0, 0, 1)",
			pointStrokeColor : "#fff",
			pointHighlightFill : "#fff",
			pointHighlightStroke : "rgba(151,187,205,1)",
			data : dataArray.engineSpeedDatas	
	};
	
	var voltage = {
			label : "Voltage",
			fillColor : "rgba(151,187,205,0)",
			strokeColor : "rgba(102, 11, 193, 0.5)",
			pointColor : "rgba(102, 11, 193, 0.5)",
			pointStrokeColor : "#fff",
			pointHighlightFill : "#fff",
			pointHighlightStroke : "rgba(151,187,205,1)",
			data : dataArray.voltage
	};
	
	var speed = {
			label : "speed",
			fillColor : "rgba(151,187,205,0)",
			strokeColor : "rgba(192, 192, 11, 0.5)",
			pointColor : "rgba(192, 192, 11, 0.5)",
			pointStrokeColor : "#fff",
			pointHighlightFill : "#fff",
			pointHighlightStroke : "rgba(151,187,205,1)",
			data : dataArray.speeds
	}
	
	datasets=[];
	if($("#rightTankCheckbox").prop("checked")){
		console.log("rightTankCheckbox");
		datasets.push(rightTankData);
	}
	if($("#leftTankCheckbox").prop("checked")){
		console.log("leftTankCheckbox");
		datasets.push(leftTankData);
	}
	if($("#engineeCheckbox").prop("checked")){
		console.log("engineeCheckbox");
		datasets.push(engineeData);
	}
	if($("#voltageCheckbox").prop("checked")){
		console.log("voltageCheckbox");
		datasets.push(voltage);
	}
	if($("#speedCheckbox").prop("checked")){
		console.log("speedCheckbox");
		datasets.push(speed);
	}
//	if(!isEmptyArray(dataArray.rightTankDatas)){
//		$("#rightTankCheckbox").attr('checked', true);
//		datasets.push(rightTankData);
//	}else{
//		$("#rightTankCheckbox").attr('checked', false);
//	}
//	if(!isEmptyArray(dataArray.leftTankDatas)){
//		$("#leftTankCheckbox").attr('checked', true);
//		datasets.push(leftTankData);
//	}else{
//		$("#leftTankCheckbox").attr('checked', false);
//	}
//	if(!isEmptyArray(dataArray.engineSpeedDatas)){
//		$("#engineeCheckbox").attr('checked', true);
//		datasets.push(engineeData);
//	}else{
//		$("#engineeCheckbox").attr('checked', false);
//	}
//	if(!isEmptyArray(dataArray.voltage)){
//		$("#voltageCheckbox").attr('checked', true);
//		datasets.push(voltage);
//	}else{
//		$("#voltageCheckbox").attr('checked', false);
//	}
//	if(!isEmptyArray(dataArray.speeds)){
//		$("#speedCheckbox").attr('checked', true);
//		datasets.push(speed);
//	}else{
//		$("#speedCheckbox").attr('checked', false);
//	}
//	if(datasets.length==0){
//		window.alert("Нет данных для построения графика");
//	}else{
		var lineChartData = {
				labels : time,
				datasets :datasets
			}
			
			var ctx;
			if(myLineChart!=null) {
				$('#fuelChart').remove();
				$('#chart').append('<canvas id="fuelChart"></canvas>');
			}
			ctx = document.getElementById("fuelChart").getContext("2d");
			myLineChart = new Chart(ctx);
			myLineChart = new Chart(ctx);
			myLineChart.Line(lineChartData, {
				responsive : true,
				showScale : true,
				pointDot : false,
				datasetStroke : true,
				scaleUse2Y : true
			});
		
	}
	var lineChartData = {
	labels : time,
	datasets :datasets
}

//var ctx;
//if(myLineChart!=null) {
//	$('#fuelChart').remove();
//	$('#chart').append('<canvas id="fuelChart"></canvas>');
//}
//	var lineChartData = {
//	labels : time,
//	datasets :datasets
//}
//ctx = document.getElementById("fuelChart").getContext("2d");
//myLineChart = new Chart(ctx);
//myLineChart = new Chart(ctx);
//myLineChart.Line(lineChartData, {
//	responsive : true,
//	showScale : true,
//	pointDot : false,
//	datasetStroke : true,
//	scaleUse2Y : true
//});
//}
function isEmptyArray(array){
	if(getArraySumm(array)>0) return false;
	return true;
}
function getArraySumm(array){
	var sum = 0;
	console.log(array.length);
	for(var i = 0; i<array.length; i++){
		sum = sum + array[i];
	}
	console.log(sum);
	return sum;
}



