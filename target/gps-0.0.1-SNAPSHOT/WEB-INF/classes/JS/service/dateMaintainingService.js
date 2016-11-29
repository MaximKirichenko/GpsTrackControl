var period = {
		dataFrom : 0,
		dataTo : 0,
	};

function getPeriod(){
	var datepickerFrom = $("#datepickerFrom").val();
	var timeFrom = $("#timeFrom").val();
	var datepickerTo = $("#datepickerTo").val();
	var timeTo = $("#timeTo").val();
	if (timeFrom == "")
		timeFrom = "00:00";
	if (timeTo == "")
		timeTo = "24:00";
	if(datepickerFrom == ""){
		date = new Date();
		datepickerFrom = date.getDate() + "/"+(date.getMonth()+1) + "/" + date.getFullYear();
	}
	if(datepickerTo == ""){
		date = new Date();
		datepickerTo = date.getDate() + "/"+(date.getMonth()+1) + "/" + date.getFullYear();
	}
	period.dataFrom = parseDate(datepickerFrom, timeFrom);
	period.dataTo = parseDate(datepickerTo, timeTo);
	return period;
}

function parseDate(date, time) {
	var dataArray = date.split("/");
	var timeArray = time.split(":");

	var resultDate = new Date(dataArray[2], dataArray[1] - 1, dataArray[0],
			timeArray[0], timeArray[1]);
	return resultDate.getTime();
}

String.prototype.toHHMMSS = function () {
    var sec_num = parseInt(this, 10); // don't forget the second param
    var hours   = Math.floor(sec_num / 3600);
    var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
    var seconds = sec_num - (hours * 3600) - (minutes * 60);

    if (hours   < 10) {hours   = "0"+hours;}
    if (minutes < 10) {minutes = "0"+minutes;}
    if (seconds < 10) {seconds = "0"+seconds;}
    var time    = hours+':'+minutes+':'+seconds;
    return time;
}