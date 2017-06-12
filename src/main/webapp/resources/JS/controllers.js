var trackApp = angular.module('trackApp', []);

trackApp.controller('trackCtrl', function($scope, $http) {
	$http.get(
			'/gps/report?terminalNumber=' + $scope.terminalNumber
					+ '&dateFrom=' + $scope.dateFrom + '&dateTo='
					+ $scope.dateTo + '&timeFrom=' + $scope.timeFrom
					+ '&timeTo=' + $scope.timeTo).success(function(data) {
		initMap(data)
	});
});

function initMap(obj) {
	var zoom = 12;
	var centerLatLng;
	var path = new Array();
	var stop = new Array();
	var stopTimeInMinutes= new Array();
	var image = ctx + '/resource/JS/img/thumbnail.png';
	
	for (var i = 0; i < obj.length; i++) {
		var oldPoint;
		var point = new google.maps.LatLng(obj[i].latitude, obj[i].longitude);
		if (i > 0) {
			var stopTime = obj[i].messageDate - oldPoint.messageDate;
			if (stopTime > 300000) {	
				stopTimeInMinutes.push((stopTime/60000).toFixed(2));
				stop.push(obj[i]);
			}
		}
		oldPoint = obj[i];

		path.push(point);
		if (i == obj.length - 1) {
			centerLatLng = new google.maps.LatLng(obj[i].latitude,
					obj[i].longitude);
		}
	}

	var trackPolyline = new google.maps.Polyline({
		path : path,
		geodesic : true,
		strokeColor : '#0000FF',
		strokeOpacity : 2.0,
		strokeWeight : 3
	});
	var map = new google.maps.Map(document.getElementById('map'));

	
	for (var i = 0; i < stop.length; i++) {
		var point = new google.maps.LatLng(stop[i].latitude, stop[i].longitude);
		var date = new Date(stop[i].messageDate).toLocaleString();
		var titleText = date + " простоял: " + stopTimeInMinutes[i] + " минут ";
		var start = new Date(stop[i].messageDate + stopTimeInMinutes[i]*60*1000).toLocaleString(); 
		var infoWindow = "Остановился: " + date + 
		"<br/>Поехал: "+ start + "<br/>простоял: " + stopTimeInMinutes[i] + " минут ";
//		var infowindow = new google.maps.InfoWindow({
//		    content: titleText
//		  });
		var marker;

		if(i==0){
			marker = new google.maps.Marker({
				position : point,
				map : map,
				label: "S",
				title: "Начал " + titleText
			});
			

			
		}else if(i==stop.length-1){
			marker = new google.maps.Marker({
				position : point,
				map : map,
				label: "F",
				title: "Закончил " + titleText
			});
			

		}else{
			marker = new google.maps.Marker({
				position : point,
				map : map,
				icon : image,
				title: titleText
			});
			

		}
		
		attachMesssage(marker, infoWindow);
		var previous = stop[i];
	}

	map.setCenter(centerLatLng);
	map.setZoom(zoom);
	trackPolyline.setMap(map);
}
function attachMesssage(marker, message){
    var infowindow = new google.maps.InfoWindow({
        content: message
    });
    google.maps.event.addListener(marker, 'click', function() {
        infowindow.open(marker.get('map'), marker);
    });
}
