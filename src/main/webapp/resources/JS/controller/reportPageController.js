/**
 * 
 */
var reportId;
var GROUP_REPORT = 1;
var progressbar;
$( document ).ready(function() {
	var period;
	$("#dateSubmit").click(function(){
		var period = getPeriod();
		console.log(period);
		if(reportId == GROUP_REPORT){
			getGroupReport(period);
		}
	});
});
function setReportId(input){
	reportId = input;
	if(reportId == GROUP_REPORT){
		$( "#vehicleName H3").text("Груповой отчет");
	}
}
function getGroupReport(period){
	terminalDateDto = {
			terminalNumber: 0,
			dataFrom: period.dataFrom,
			dataTo: period.dataTo		
	}
	console.log("GROUP REPORT REQUEST");
	progressbar = $( "#progressbar" ),
    progressLabel = $( ".progress-label" );
	$("#progressbar").show();
	$( ".progress-label" ).show();
	progressbar.progressbar({
    value: false,
    change: function() {
      progressLabel.text( progressbar.progressbar( "value" ) + "%" );
    },
    complete: function() {
      progressLabel.text( "Complete!" );
    }
	});
	
	$.ajax({
		type : "POST",
		url : 'groupReport',
		data : JSON.stringify(terminalDateDto),
		dataType : 'json',
		contentType : 'application/json',
		mimeType : 'application/json',
		success: function (data) {
			progress();
			$( "#reportLayer").empty();
			$( "#reportLayer").show();
			$( "#reportLayer").append("<table class='table-fill'></table>");
			$( "#reportLayer table").append("<tr>" +
					"<td>ID</td>" +
					"<td>Предприятие</td>" +
					"<td>Тип автомобиля</td>" +
					"<td>Наименнование</td>" +
					"<td>Гос.номер</td>" +
					"<td>Пробег, км</td>" +
					"<td>Расход по CAN, л</td>" +
				"</tr>");
			function compareData(firstData, secondData){
				return secondData.mileage- firstData.mileage;
			}	
			data.sort(compareData);
			for(var i=0; i<data.length; i++){

					$( "#reportLayer table tr:last").after("<tr>" +
							"<td>"+data[i].vehicle.numberTerminal+"</td>" +
							"<td>"+data[i].vehicle.enterprise.enterprise+"</td>" +
							"<td>"+data[i].vehicle.group.groupName+"</td>" +
							"<td>"+data[i].vehicle.name+"</td>" +
							"<td>"+data[i].vehicle.regNumber+"</td>" +
							"<td>"+data[i].mileage+"</td>" +
							"<td>"+data[i].consumption+"</td>" +
						"</tr>")

			}
			
			
        }
	});
}
function progress() {
    var val = progressbar.progressbar( "value" ) || 0;

    progressbar.progressbar( "value", val + 2 );

    if ( val < 99 ) {
      setTimeout( progress, 5 );
    }else{
    	$("#progressbar").hide();
    	val = 0;
    }
  }

 
