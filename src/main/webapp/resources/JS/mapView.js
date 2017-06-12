
var coordinates = new Array();
var markerArray = new Array();
var fieldsArray = new Array();
var infoWindowsArray = new Array();


var trackPolyline;
var map;

function initMap() {
	var zoom = 12;
	var centerLatLng = new google.maps.LatLng({lat: 47.835506, lng: 34.328588});		
	map = new google.maps.Map(document.getElementById('map'));
	var agroFields = document.getElementById('fields').value;

	
	map.setCenter(centerLatLng);
	map.setZoom(zoom);
	map.setMapTypeId(google.maps.MapTypeId.HYBRID);
	paintFields(agroFields)
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


function paintFields(AgroFields){
	var fieldsObject = JSON.parse(AgroFields);
	var fieldsArray = new Array();
	var infoWindowsArray = new Array();
	var infoObject = new Array;
	
	for(var i=0; i<fieldsObject.length; i++){
		
		var field = new google.maps.Polygon({
			paths: fieldsObject[i].coordinates,
		    strokeColor: '#0000FF',
		    strokeOpacity: 0.8,
		    strokeWeight: 2,
		    fillColor: '#0000FF',
		    fillOpacity: 0.35,
		    title: fieldsObject[i].cipher
		});	
		
		var contentString = ' ' + fieldsObject[i].cipher;
		//window.alert(" " + contentString);
		var infowindow = new google.maps.InfoWindow({
		    content: contentString
		  });
		var fieldObject = {
				field: field,
				info: infowindow
		}
		infoWindowsArray.push(contentString);		
		fieldsArray.push(field);
		infoObject.push(fieldObject);
	}
	for(var i=0; i<fieldsArray.length; i++){
		//var info = infoWindowsArray[i];
		//window.alert("Metod " + infoWindowsArray[i]);
//		fieldsArray[i].addListener('click', function() {
//			window.alert("Предварительно " + infoWindowsArray[i]);
//			
//		});
		fieldsArray[i].setMap(map);
		
		
	}
	
	
}





