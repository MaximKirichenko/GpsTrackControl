  $(function() {
	  $( "#pop-up-window" ).hide();
	  function runEffect() {
	      // get effect type from
	      var selectedEffect = "scale";
	      // most effect types need no options passed by default
	      var options = {percent: 100};
	      // run the effect
	      $( "#pop-up-window" ).show( selectedEffect, options, 500);
	    };
	  $("#vehicleName").click(function() {
		  var terminalNumber = $("#selectedTerminalNumber").val();
		  console.log("terminalNumber");
		  console.log(terminalNumber);
		  if(terminalNumber == null){
			  window.alert("Сперва выберите автомобиль");
		  }else{
			  $.ajax({
					type : "POST",
					url : 'getVehicleDeteilTable',
					data : JSON.stringify(terminalNumber),
					dataType : 'json',
					contentType : 'application/json',
					mimeType : 'application/json',
					success: function (data) { 	
						setupTable(data);
			        }
				});
			  runEffect(); 
		  }
		  
	  });
  });
  function setupTable(data){
	  console.log(data);
	  $("#carInfoTable tbody").append("<tr>" +
	  		"<td>IMEI</td><td>"+data.terminalDate.imei+"</td>" +
	  		"<td>ID автомобиля</td><td>"+data.vehicle.id+"</td>" +
	  		"</tr>");
	  $("#carInfoTable tr:last").after("<tr>" +
		  		"<td>Наименование</td><td>"+data.vehicle.name+"</td>" +
		  		"<td>гос.номер</td><td>"+data.vehicle.regNumber+"</td>" +
		  		"</tr>");
	  $("#carInfoTable tr:last").after("<tr>" +
		  		"<td>Терминал</td><td>"+data.terminal.name+"</td>" +
		  		"<td>Номер сим</td><td>"+data.terminal.telephone+"</td>" +
		  		"</tr>");
	  $("#carInfoTable tr:last").after("<tr>" +
		  		"<td>Левый бак</td><td>"+data.fuelLevelLeft+"</td>" +
		  		"<td>Правый бак</td><td>"+data.fuelLevelRight+"</td>" +
		  		"</tr>");
	  $("#carInfoTable tr:last").after("<tr>" +
		  		"<td>Левый бак (н.т)</td><td>"+data.terminalDate.leftGasTank+"</td>" +
		  		"<td>Правый бак (н.т)</td><td>"+data.terminalDate.rightGasTank+"</td>" +
		  		"</tr>");
	  $("#carInfoTable tr:last").after("<tr>" +
		  		"<td>Группа</td><td>"+data.vehicle.group.groupName+"</td>" +
		  		"<td>Принадлежность</td><td>"+data.vehicle.enterprise.enterprise+"</td>" +
		  		"</tr>");
	  $("#carInfoTable tr:last").after("<tr>" +
		  		"<td>Обороты</td><td>"+data.terminalDate.engineSpeed+"</td>" +
		  		"<td>Скорость</td><td>"+data.terminalDate.speed+"</td>" +
		  		"</tr>");
	  $("#carInfoTable tr:last").after("<tr>" +
		  		"<td>Напряжение на аккумуляторе</td><td>"+(data.terminalDate.psv/1000)+"</td>" +
		  		"<td>Напряжение на устройстве</td><td>"+(data.terminalDate.vBat/1000)+"</td>" +
		  		"</tr>");
	  $("#carInfoTable tr:last").after("<tr>" +
		  		"<td>Качество связи</td><td>"+data.terminalDate.numberSatellite+"</td>" +
		  		"<td>Дата последнего сигнала</td><td>"+new Date(data.terminalDate.messageDate).toLocaleString()+"</td>" +
		  		"</tr>");
	  $("#carInfoTable tr:last").after("<tr>" +
		  		"<td>Уровень топлива по штатному датчику, %</td><td>"+(data.terminalDate.canfuelConsumption*100/254)+"</td>" +
		  		"<td>Израсходовано топлива за весь период эксплуатации, л</td><td>"+(data.terminalDate.canfls*0.5)+"</td>" +
		  		"</tr>");
	
  }
 