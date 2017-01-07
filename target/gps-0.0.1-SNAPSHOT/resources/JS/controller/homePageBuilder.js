$("document").ready(function() {
	setUpDataPicker();
	loadTabs();
	document.getElementById('noneToggle').checked = true;
	loadCarMenu();
	setTrackerActivitiIndicators();
	LoadMap(); //function from mapService.js
	submitButtonInit();
	setAnamyField();
	setMapObjectField();
	setFields();
	setupMapToolsMeneger();
	
	$("#add-map-object-form").hide();
	$("#view-window").hide();
	
	$("#add-map-object-cancel").click(function(){
		$("#add-map-object-form").hide();
		$("#add-map-object-content").empty();
	});
	$("#view-window-submit").click(function(){
		$("#view-window").hide();
		$("#view-window-content").empty();
	});
	
	$("#add-map-object-submit").click(function(){
		if($("input[name='fieldNumber']").val()==""){
			alert("Заполниите все поля");
		}else{
			sendMapObjectField();
			$("#add-map-object-form").hide();
			$("#add-map-object-content").empty();
		}
		
		
	});
});

function submitButtonInit(){
	$("#dateSubmit").click(function(){
		var selectedVehicleList = getSelectedVehiclesId();
		console.log(selectedVehicleList);
		var period = getPeriod();
		sendTracksRequest(selectedVehicleList, period);
	});
}
function sendTracksRequest(selectedVehicleList, period){
	var tracksRequestData = {
			dataFrom: period.dataFrom,
			dataTo: period.dataTo,
			terminalNumbers: selectedVehicleList
	}
	$.ajax({
		type : "POST",
		url : 'buildTracks',
		data : JSON.stringify(tracksRequestData),
		dataType : 'json',
		contentType : 'application/json',
		mimeType : 'application/json',
		success: function (data) { 	
			
			$("#tabs-3").empty();
			$("#tabs-2").empty();
			
			if(isEmptyTrack(data)){
				showEmptyTrackMessage();
			}
			else{
				renderTrackData(data);
			}
		}	
    });
}

function renderTrackData(data){
	
	//Builds chart
	requestChartData(data[0].vehicle.id.toString());
	for(var i=0; i<data.length; i++){
		$("#rendering-car-id").append(
				"<option value='"+data[i].vehicle.id+"'>"+
				data[i].vehicle.name + " " +data[i].vehicle.regNumber + "</option>");
	}
	$(".empty-page-message").remove();
	$(".car-selelect-field").show("fast");
	
	$("#rendering-car-id").selectmenu({
		change: function(event, data){
			requestChartData(data.item.value);
		}
	});
	
	console.log($("#rendering-car-id").selectmenu( "option" ));
	addTracks(data);
	if(isSingleTrack(data)){
		buildInfoAboutVehicle(data[0]);
		buildCalibrationService(data[0]);
	}else{
		for(var i=0; i<data.length; i++){
				buildInfoAboutVehicle(data[i]);
			}
		}
}

function buildCalibrationService(vehicleData){
	buildCalibrationForm();
	var oldMessageId = 0;
	$('#startButton').click(function(){
		setInterval(function(){
			$.ajax({
				type : "POST",
				url : 'getLastTerminalData',
				data : vehicleData.terminal.imei,
				dataType : 'json',
				contentType : 'application/json',
				mimeType : 'application/json',
				success: function (data) { 	
					console.log(data);
					if(data.id!=oldMessageId){
						$('#area').val($('#area').val()+ "\nid:" + data.id + " imei: "  + data.imei + " messageDate: " + new Date(data.messageDate).toLocaleString() +
								"\nleft tank " + data.leftGasTank + " right tank " + data.rightGasTank + 
								"\nlongitude: " + data.longitude + " latitude " + data.latitude + " number satelite: " + data.numberSatellite + 
								"\nspeed: " + data.speed + " azimuth: " + data.azimuth + " height: " + data.height + 
								"\nvBat: " + data.vBat + " vTerminal " + data.psv + " can fuel " + data.canfls + " can count " + data.canfuelConsumption + "\n"); 
						oldMessageId = data.id;
					}
					
					
		        }
			});
		}, 10000)
		
	});
}
function buildCalibrationForm(){
	$("#tabs-2").append("<h3>Мониторинг объекта</h3>");
	$("#tabs-2").append(
			"<textarea rows='15' cols='100' id='area'> "+
			"</textarea>"
			);
	$("#tabs-2").append("<p><button type='button' id='startButton'>start</button>");
	
}
function buildInfoAboutVehicle(vehicleData){
	$("#tabs-2").append("<p>-----------------------------------------------</p>" +
			"<p>Марка: "+vehicleData.vehicle.name + "</p>" +
			"<p> гос. номер: " + vehicleData.vehicle.regNumber + "</p>" + 
			"<p>IMEI: " + vehicleData.terminal.imei + " id: " + vehicleData.vehicle.id + "</p>" +
			"<p>Предприятие: " + vehicleData.vehicle.enterprise.enterprise + " Группа ТС: " + vehicleData.vehicle.group.groupName + "</p>" +
			"<p>Номер сим: " + vehicleData.terminal.telephone + "</p>");
			"<p>Левый бак: " + vehicleData.vehicle.enterprise.enterprise + " Группа ТС: " + vehicleData.vehicle.group.groupName + "</p>" +
			$("#tabs-3").append("<p>"+vehicleData.vehicle.name + " гос. номер: " + vehicleData.vehicle.regNumber + " пробег: " + vehicleData.trackInfo.totalLength+"</p>");
}
function getSelectedVehiclesId(){
	var selected = new Array();
	$(".vehicle_checkbox").each(function(index){
		if($(this).is(":checked")){
			var vehicleId = $(this).next().val();
			selected.push(vehicleId);
		}
	});
	return selected;
}


function setUpDataPicker(){
	$("#datepickerFrom" ).datepicker({dateFormat: 'dd/mm/yy'});
	$("#datepickerTo" ).datepicker({dateFormat: 'dd/mm/yy'});
}

function loadTabs(){
	$( "#tabs" ).tabs();
}

function loadCarMenu(){
	$( "#accordion" ).accordion({
		heightStyle: "fill"
	});
}

function setTrackerActivitiIndicators(){
	var today = new Date().getTime();
	$(".messageDateValue").each(function(index){
		var timeDif = today -  $(this).val();
		if(timeDif>1200000){
			$(this).after("<img src='/resource/red_circle.png'/>");
			$(this).parent().append((new Date($(this).val()*1).toLocaleString()));
		}else{
			if($(this).next().val()>0){
				$(this).after("<img src='/resource/green_circle.png'/>");
			}else{
				$(this).after("<img src='/resource/yelow_circle.png'/>");
			}
			
		}	
	});	
}

function requestChartData(vehicleId){
	console.log(vehicleId);
	$.ajax({
		type : "POST",
		url : 'buildChart',
		data : vehicleId,
		dataType : 'json',
		contentType : 'application/json',
		mimeType : 'application/json',
		success: function (serverResponse) { 	
			var chart = new CanvasJS.Chart("chartContainer");
			chart.options.axisX = {
					lineThickness: "1",
					valueFormatString: "M.D.Y HH:mm:ss",
					labelAngle: 45
					};
			chart.options.title = {
					text: serverResponse.vehicle.name + " - " + serverResponse.vehicle.regNumber,
					fontSize: "14",
					fontColor: "green"
					};
			chart.options.zoomEnabled = true;
			var series1 = {
					type: "line",
					name: "Левый бак",
					showInLegend: true
			};
			
			var series2 = {
					type: "line",
					name: "Правый бак",
					showInLegend: true
			};
			
			chart.options.data = [];
			chart.options.data.push(series1);
			chart.options.data.push(series2);
			var leftTankData = new Array();
			var rightTankData = new Array();
			for(var i=0; i<serverResponse.leftFuelLine.length; i++){
				leftTankData.push({x: new Date(serverResponse.leftFuelLine[i].x), y: serverResponse.leftFuelLine[i].y})
			}
			for(var i=0; i<serverResponse.rightFuelLine.length; i++){
				rightTankData.push({x: new Date(serverResponse.rightFuelLine[i].x), y: serverResponse.rightFuelLine[i].y})
			}
			
			series1.dataPoints = leftTankData;
			series2.dataPoints = rightTankData;
			chart.render();
		
		}	
    });

}
function setFields(){
	$.ajax({
		type : "POST",
		url : 'getFields',
		contentType : 'application/json',
		mimeType : 'application/json',
		success: function (data) { 	
			getFields(data);
        }
	});
}
function setMapObjectField(){
	$.ajax({
		type : "POST",
		url : 'getMapObjectField',
		contentType : 'application/json',
		mimeType : 'application/json',
		success: function (data) {
			getMapObjectField(data);
        }
	});
}

function setAnamyField(){
	$.ajax({
		type : "POST",
		url : 'getMapNeibor',
		contentType : 'application/json',
		mimeType : 'application/json',
		success: function (data) {
            getMapAnamyField(data);
		}
	});
}

function setupMapToolsMeneger(){
	$("#map-tools").mouseenter(function(){
		$("#tool-selector").show( "fast" );
	}).mouseleave(function(){
		$("#tool-selector").hide( "fast" );
	});

}
function isEmptyTrack(data){
	if(data.length==0) return true;
	return false;
}
function isSingleTrack(data){
	if(data.length==1) return true;
	return false;
}

function showEmptyTrackMessage(){
	window.alert("Не один из выбраных трекеров не был подключен");
}

jQuery.fn.center = function () {
	var top = $(window).height()/2- $(this).height()/2;
	var left = $(window).width()/2 - $(this).width()/2;
	console.log("width: " + $(this).width()/2);
    this.css("position","absolute");
    this.css("top", top + "px");
    this.css("left", left + "px");
    return this;
}

jQuery.fn.centeringIdBlock = function () {
	console.log("bottom width: " + $(this).outerWidth());
	var left = $(this).parent().width()/2 - $(this).outerWidth()/2;
    this.css("padding-left", left + "px");
    this.css("padding-right", left + "px");
    return this;
}