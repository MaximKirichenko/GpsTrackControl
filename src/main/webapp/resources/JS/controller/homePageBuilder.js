$("document").ready(function() {
	setUpDataPicker();
	loadTabs();
	loadCarMenu();
	LoadMap(); //function from mapService.js
});

function setUpDataPicker(){
	$("#datepickerFrom" ).datepicker({dateFormat: 'dd/mm/yy'});
	$("#datepickerTo" ).datepicker({dateFormat: 'dd/mm/yy'});
}

function loadTabs(){
	$( "#tabs" ).tabs();
}

function loadCarMenu(){
	$( "#accordion" ).accordion();
}