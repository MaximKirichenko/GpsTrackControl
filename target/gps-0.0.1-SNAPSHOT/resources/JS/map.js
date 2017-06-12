var map; 

function initMap() {
	var zoom = 8;
	var centerLatLng = new google.maps.LatLng({lat: 48.029185, lng: 34.601537});
	map = new google.maps.Map(document.getElementById('map'));
	map.setCenter(centerLatLng);
	map.setZoom(zoom);

}