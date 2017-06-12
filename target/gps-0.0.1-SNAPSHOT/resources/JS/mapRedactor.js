
var coordinates = new Array();
var markerArray = new Array();
var trackPolyline;
var map;

function initMap() {
	var zoom = 12;
	var centerLatLng = new google.maps.LatLng({lat: 47.835506, lng: 34.328588});	
	map = new google.maps.Map(document.getElementById('map'));
	var agroFields = document.getElementById('fields').value;
	map.addListener('click', function(e) {
	    placeMarkerAndPanTo(e.latLng, map);
	});


	map.setCenter(centerLatLng);
	map.setZoom(zoom);
	map.setMapTypeId(google.maps.MapTypeId.HYBRID);
	paintFields(agroFields);
}
function placeMarkerAndPanTo(latLng, map) {
	  var marker = new google.maps.Marker({
	    position: latLng,
	    map: map
	  });
	  markerArray.push(marker);
	  coordinates.push(latLng);
	  marker.addListener('click', function(e) {
		  closeCircle(map);
		});
	  marker.addListener('rightclick', function(e) {
		  deleteMarker(coordinates, marker);
		});
	  repaintPath(map);
	  
	 // map.panTo(latLng);
}

function closeCircle(map){
	
	coordinates.push(coordinates[0]);
	
	var field = new google.maps.Polygon({
		paths: coordinates,
	    strokeColor: '#0000FF',
	    strokeOpacity: 0.8,
	    strokeWeight: 2,
	    fillColor: '#0000FF',
	    fillOpacity: 0.35
	});
	field.setMap(map);
	
//	window.open ('/gps/mapRedactor/addFieldsProperty','_self',coordinates);
	
}

	
function repaintPath(map){
	trackPolyline = new google.maps.Polyline({
		path : coordinates,
		map: map,
		geodesic : true,
		strokeColor : '#0000FF',
		strokeOpacity : 2.0,
		strokeWeight : 3
	});
	
}

function sendData(){
	//var jsnCoordinates = JSON.stringify(coordinates);
	var loc = document.getElementById("location").value;
	var tar = document.getElementById("target").value;
	var c = document.getElementById("cipher").value;
	var agroField = {
			coordinates: coordinates,
			location: loc,
			target: tar,
			cipher: c
	};
	var agroFieldJson = JSON.stringify(agroField);
	if(coordinates.length>0){
		$.ajax({
			type: 'POST',
			dataType: 'json',
			contentType: 'application/json',
		    mimeType: 'application/json',
			url: '/gps/mapRedactor',
			data: agroFieldJson,	
		});
	}else{
		window.alert("Предварительно необходимо ввести поле на карте");
	}
	
}
function paintFields(AgroFields){
	var fieldsObject = JSON.parse(AgroFields);
	var path = new Array();
	for(var i=0; i<fieldsObject.length; i++){
		
		var field = new google.maps.Polygon({
			paths: fieldsObject[i].coordinates,
		    strokeColor: '#0000FF',
		    strokeOpacity: 0.8,
		    strokeWeight: 2,
		    fillColor: '#0000FF',
		    fillOpacity: 0.35
		});
		field.setMap(map);
		
		
	}
}





