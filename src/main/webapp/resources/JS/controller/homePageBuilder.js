$("document").ready(function() {
	setUpDataPicker();
	loadTabs();
	loadCarMenu();
	setTrackerActivitiIndicators();
	LoadMap(); //function from mapService.js
	submitButtonInit();
});

function submitButtonInit(){
	$("#dateSubmit").click(function(){
		var selectedVehicleList = getSelectedVehicles();
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
			if(data.length == 0){
				window.alert("Не один из выбраных трекеров не был подключен");
			}
			else{
				addTracks(data);
				console.log(data);
				$("#tabs-3").empty();
				$("#tabs-2").empty();
				for(var i=0; i<data.length; i++){
					$("#tabs-2").append("<p>-----------------------------------------------</p>" +
							"<p>Марка: "+data[i].vehicle.name + "</p>" +
							"<p> гос. номер: " + data[i].vehicle.regNumber + "</p>" + 
							"<p>IMEI: " + data[i].terminal.imei + " id: " + data[i].vehicle.id + "</p>" +
							"<p>Предприятие: " + data[i].vehicle.enterprise.enterprise + " Группа ТС: " + data[i].vehicle.group.groupName + "</p>" +
							"<p>Номер сим: " + data[i].terminal.telephone + "</p>");
							"<p>Левый бак: " + data[i].vehicle.enterprise.enterprise + " Группа ТС: " + data[i].vehicle.group.groupName + "</p>" +
					$("#tabs-3").append("<p>"+data[i].vehicle.name + " гос. номер: " + data[i].vehicle.regNumber + " пробег: " + data[i].trackInfo.totalLength+"</p>");
				}
				
				
			}
			
        }
	});
}


function getSelectedVehicles(){
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
			$(this).after("<img src='/gps/resource/red_circle.png'/>");
		}else{
			if($(this).next().val()>0){
				$(this).after("<img src='/gps/resource/green_circle.png'/>");
			}else{
				$(this).after("<img src='/gps/resource/yelow_circle.png'/>");
			}
			
		}	
	});
	
	
}