var map, drawControls;
var kadastrNumber, use, area;
var polygonLayer;
var vectorStartPoint;
var pointContent = new Array();
var mapBounds = new OpenLayers.Bounds(-160, -74, 160, 74);
var periodDto = {
		dataFrom : 0,
		dataTo : 0,
		terminalNumber : 0
	};
var lastPointContent;
$("document").ready(function(){
	LoadMap()
//    $("#searchField").keyup(function(e){
//        if(e.keyCode == 13){
//            searchKadNumber($("#searchField").val());
//            $(this).blur();
//            return false;
//        }
//    });
   
});

function LoadMap(){
    //Границы задаются для проекции EPSG:900913.
    var maxExtent = new OpenLayers.Bounds(-20037508, -20037508, 20037508, 20037508);
    var restrictedExtent = maxExtent.clone();
    var maxResolution = 156543.0339;
    
    var stylePoint = new OpenLayers.Style(
            {
                pointRadius: 8,
                strokeColor: "red",
                strokeWidth: 2,
                fillColor: "lime",
                labelYOffset: -16,
                label: "${label}",
                fontSize: 16
            }
        );
    vectorStartPoint = new OpenLayers.Layer.Vector("Остановки",
    		{
    		        styleMap: new OpenLayers.StyleMap(
    		        { "default": stylePoint,
    		          "select": { pointRadius: 20}
    		        })
    		    });

    //Отображаться карта будет в проекции EPSG:4326.
    var options = {
        projection: new OpenLayers.Projection("EPSG:900913"),
        displayProjection: new OpenLayers.Projection("EPSG:4326"),
        numZoomLevels: 18,
        maxResolution: maxResolution,
        maxExtent: maxExtent,
        restrictedExtent: restrictedExtent,
        eventListeners: {
            featureclick: function(e) {            	
            	if(e.feature.data.title == "point"){
            		attachPointMessage(e.feature);
            	}else if(e.feature.data.label == "fields"){
            		attachFieldsMessage(e.feature);
            	}else if(e.feature.data.title == "lastPoint"){
            		attachLastPointMessage(e.feature);
            	}
            }
        }
    };

    //Create a map
    map = new OpenLayers.Map('map', options);
    
    //Create OpenStreet map Layer
    var mapNik = new OpenLayers.Layer.OSM();
   
    //Create Kadastr map Layer
    var kadpodil = new OpenLayers.Layer.WMS(
        "Кадастровий поділ", "http://212.26.144.110/geowebcache/service/wms?tiled=true", {
            LAYERS: 'kadastr',
            STYLES: '',
            format: 'image/png',
            tiled: true,
            transparent: 'true'
        }, {
            buffer: 0,
            displayOutsideMaxExtent: true,
            isBaseLayer: false,
            yx: {'EPSG:900913': false}
            //transitionEffect:       'resize'
        });
 // -- ORTHO 10K --
    var tmsoverlay_orto = new OpenLayers.Layer.TMS("Ортофотоплани", "", {// url: '', serviceVersion: '.', layername: '.',
        type: 'jpg',
        getURL: overlay_getTileURL,
        alpha: true,
        isBaseLayer: true
    });
    if (OpenLayers.Util.alphaHack() == false) {
        tmsoverlay_orto.setOpacity(0.7);
    }
    //map.zoomToMaxExtent();
    //Add scale and position of mouse hint
    map.addControl(new OpenLayers.Control.ScaleLine());
    map.addControl(new OpenLayers.Control.MousePosition());

    //Add all created layers
    map.addLayers([mapNik, kadpodil, tmsoverlay_orto]);
  //Create layers for images
    var styleImage = new OpenLayers.Style(
        {
            graphicWidth: 21,
            graphicHeight: 25,
            graphicYOffset: -28,
            label: "${label}",
            externalGraphic: "http://icons.iconarchive.com/icons/paomedia/small-n-flat/24/map-marker-icon.png",
            fontSize: 12
        }
    );

    //Add layers control panel
    map.addControl(new OpenLayers.Control.LayerSwitcher());

    //Create pont that map will be centered when start
    var point0 = new OpenLayers.Geometry.Point(34.05460, 48.02305);
    //Transform projection
    point0.transform(new OpenLayers.Projection("EPSG:4326"), new OpenLayers.Projection("EPSG:900913"));
    //Do centering with scale at 9
    map.setCenter(new OpenLayers.LonLat(point0.x, point0.y), 14);

    polygonLayer = new OpenLayers.Layer.Vector("Поля", {
        renderers: ['SVGExtended', 'VMLExtended', 'CanvasExtended'],
        styleMap: new OpenLayers.StyleMap({
            'default': OpenLayers.Util.extend({
                orientation: true
            }, OpenLayers.Feature.Vector.style['default']),
            'temporary': OpenLayers.Util.extend({
                orientation: true
            }, OpenLayers.Feature.Vector.style['temporary'])
        })
    });

    //Finished poligon event
    polygonLayer.events.register('featureadded', polygonLayer, function(evt) {
    	console.log("Генерация события");
        var geom = evt.feature.geometry;
        var coordinates = geom.getVertices(false);
        var latLng;
        var latLngArray = new Array();

        for(var i=0; i<coordinates.length; i++){
            coordinates[i].transform(new OpenLayers.Projection("EPSG:900913"), new OpenLayers.Projection("EPSG:4326"));
            latLng = {
                x:coordinates[i].x,
                y:coordinates[i].y
            };
            latLngArray.push(latLng);
        }
        var agroFieldJson = JSON.stringify({
            coordinates: latLngArray,
            kadastr: kadastrNumber,
            use: use,
            area: area
        });
        $.ajax({
        	type : "POST",
            url: 'report/mapRedactor',
            dataType : 'json',
            contentType : 'application/json',
    		mimeType : 'application/json',
            data: agroFieldJson,
            success: function (data) { 	
    			console.log("Ok");
    			}
    			
        });

        console.log(agroFieldJson);
        //getFields(fields)

    });

    drawControls = {
        polygon: new OpenLayers.Control.DrawFeature(polygonLayer,
            OpenLayers.Handler.Polygon)
    };
    for(var key in drawControls) {
        map.addControl(drawControls[key]);
    }

    map.addLayer(polygonLayer);
    map.addLayer(vectorStartPoint);
}
function attachLastPointMessage(point){

	var popup = new OpenLayers.Popup.FramedCloud("Popup",
            new OpenLayers.LonLat(point.geometry.x, point.geometry.y), null,
            lastPointContent, null,
            true
        );
	map.addPopup(popup);
}
function attachPointMessage(point){
	console.log(point.geometry.x + " " + point.geometry.y);
	console.log(point.geometry.x + " " + point.geometry.y);
	var popup = new OpenLayers.Popup.FramedCloud("Popup",
            new OpenLayers.LonLat(point.geometry.x, point.geometry.y), null,
            pointContent[point.data.PointId], null,
            true
        );
	map.addPopup(popup);
}
function attachFieldsMessage(field){
    console.log(field.data.PoliID);
	$.ajax({
		type : "POST",
		url : 'report/report/getKadastrInfo',
		data : JSON.stringify(field.data.PoliID),
		dataType : 'json',
		contentType : 'application/json',
		mimeType : 'application/json',
		success: function (data) { 	
			//console.log(data);
			var popup = new OpenLayers.Popup.FramedCloud("Popup",
		            new OpenLayers.LonLat(
		            		field.geometry.bounds.getCenterLonLat().lon,
		            		field.geometry.bounds.getCenterLonLat().lat), 
		            		null,
		            		"Кадастровыцй номер: " + data, null,
		            true
		        );
			map.addPopup(popup);
        }
	});
    
//	var popup = new OpenLayers.Popup.FramedCloud("Popup",
//            new OpenLayers.LonLat(
//            		field.geometry.bounds.getCenterLonLat().lon,
//            		field.geometry.bounds.getCenterLonLat().lat), 
//            		null,
//            		"Кадастровыцй номер: " + field.data.PoliID, null,
//            true
//        );
//	map.addPopup(popup);
}

//Add poligon control
function toggleControl(element) {
    for(key in drawControls) {
        var control = drawControls[key];
        if(element.value == key && element.checked) {
            control.activate();
        } else {
            control.deactivate();
        }
    }
}

function allowPan(element) {
    var stop = !element.checked;
    for(var key in drawControls) {
        drawControls[key].handler.stopDown = stop;
        drawControls[key].handler.stopUp = stop;
    }
}
function searchKadNumber(kadNumber){
	console.log("Other method");
	console.log("kadNumber: " + kadNumber);
    var cadnum = kadNumber;
    var cad_arr = cadnum.split(":");
    console.log("cadnum: " + cadnum);
    $.ajax({
    	dataType: "json",
    	url: 'http://212.26.144.110/kadastrova-karta/find-Parcel',
    	data:{'cadnum': cadnum,
        	'activeArchLayer':0},
    	success: function (data) {
            //Устанавливаем границы
            var x1 = data['data'][0]['st_xmin'];
            var y1 = data['data'][0]['st_ymin'];
            var x2 = data['data'][0]['st_xmax'];
            var y2 = data['data'][0]['st_ymax'];
            var new_bounds_res = new OpenLayers.Bounds.fromString(x1 + "," + y1 + "," + x2 + "," + y2);
            console.log("Data first");
            //console.log(data.getVertices());
            //Подстроаиваем zoom
            map.zoomToExtent(new_bounds_res);

            //Получаем координаты центра
            var x = new_bounds_res.centerLonLat.lat;
            var y = new_bounds_res.centerLonLat.lon;
            map.setCenter(new OpenLayers.LonLat(y, x), 16);
            var popupContent;
            $.ajax({
                url: 'http://212.26.144.110/kadastrova-karta/get-parcel-Info',
                dataType: 'json',
                async: false,
                data: {
                    'koatuu': cad_arr[0],
                    'zone': cad_arr[1],
                    'quartal': cad_arr[2],
                    'parcel': cad_arr[3]
                },
                success: function (data) {
                
                    kadastrNumber = data.data[0].cadnum;
                    use = data.data[0].use;
                    area = data.data[0].area;
                    popupContent = "Кадастровий номер: " + data.data[0].cadnum + "<br\>" +
                        "Назначение: " + data.data[0].use + "<br\>" +
                        "Площадь: " + data.data[0].area;
                }
            });
            var popup = new OpenLayers.Popup.FramedCloud("Popup",
                new OpenLayers.LonLat(y, x), null,
                popupContent, null,
                true
            );

            map.addPopup(popup);
            for(var i=0; i<data.data.length; i++){
                console.log(data.data[i]);
            }
        }
    });
        
}




var linearRing2;
var pointsVector,pointFutures; 

function addTrack(data, layr){
    var featuress = new Array();
    var pointsArray = new Array();
    var pointNumber = 0;
    var stopTimeInMinutes= new Array();
    
    if(linearRing2!=null){
    	console.log("Clean point");
    	for(var i=0; i<pointFutures.length; i++){
        	vectorStartPoint.removeFeatures(pointFutures[i]);
        }
    }
    pointFutures = new Array();
    for(var i=0; i<data.length; i++){
    	var oldData;
        
    	var ttt = new OpenLayers.LonLat(data[i].longitude, data[i].latitude);
        var point0 = new OpenLayers.Geometry.Point(ttt.lon, ttt.lat);
        point0.transform(new OpenLayers.Projection("EPSG:4326"), new OpenLayers.Projection("EPSG:900913"));
        if(i>0){
        	var stopTime = data[i].messageDate - oldData.messageDate;
        	if (stopTime > 300000) {
        		stopTimeInMinutes.push((stopTime/60000).toFixed(2));
        		pointContent.push("Остановился: " + new Date(oldData.messageDate).toLocaleString() + "</br>"+
        				"Поехал: " + new Date(data[i].messageDate).toLocaleString() + "</br>" + 
        				"Простоял: " + (stopTime/60000).toFixed(2));
        		pointFutures.push(new OpenLayers.Feature.Vector(point0, {label: "P" + pointNumber, title: "point", PointId: pointNumber}));
        		pointNumber++;
        		
			}
        }
        oldData = data[i];
        if(i==data.length-1){
        	pointContent.push("Последняя точка");
    		pointFutures.push(new OpenLayers.Feature.Vector(point0, {label: "F" + pointNumber, title: "point", PointId: pointNumber}));
        	map.setCenter(new OpenLayers.LonLat(point0.x, point0.y));
        }
        featuress.push(point0);
    }
    
    for(var i=0; i<pointFutures.length; i++){
    	if(i==0){
    		pointFutures[i].data.label = "Start";
    		pointFutures[i].attributes.label = "Start";

    		
    	}
    	if(i==pointFutures.length-1){
    		pointFutures[i].data.label = "Finish";
    		pointFutures[i].attributes.label = "Finish";
    	}

    	vectorStartPoint.addFeatures(pointFutures[i]);
    	
    	
    }
   
    if(linearRing2 != null){
    	polygonLayer.removeFeatures(linearRing2);
    }
    var track = new OpenLayers.Geometry.LineString(featuress); 
    linearRing2=new OpenLayers.Feature.Vector(track);
    var length = (track.getLength()/1000).toFixed(2); 
    $('#lengthLabel').text('Длина пути: ' + length);
    console.log(length);
    polygonLayer.addFeatures(linearRing2);
    
}

function addPoly(data, title, ident, layr){
    var featuress = Array();

    for(var i=0; i<data.length; i++){
    	
        var ttt = new OpenLayers.LonLat(data[i].x, data[i].y);
        var point0 = new OpenLayers.Geometry.Point(ttt.lon, ttt.lat);
        point0.transform(new OpenLayers.Projection("EPSG:4326"), new OpenLayers.Projection("EPSG:900913"));
        featuress.push(point0);
    }
    var linearRing2 = new OpenLayers.Geometry.LinearRing(featuress);
    var polygonFeatures2 = new OpenLayers.Feature.Vector(
        new OpenLayers.Geometry.Polygon([linearRing2]),
        {
            label: title,
            PoliID: ident
        });
    layr.addFeatures(polygonFeatures2);
}

function getFields(fields){
	 polygonLayer = new OpenLayers.Layer.Vector("Кадастры", {
	        renderers: ['SVGExtended', 'VMLExtended', 'CanvasExtended'],
	        styleMap: new OpenLayers.StyleMap({
	            'default': OpenLayers.Util.extend({
	                orientation: true
	            }, OpenLayers.Feature.Vector.style['default']),
	            'temporary': OpenLayers.Util.extend({
	                orientation: true
	            }, OpenLayers.Feature.Vector.style['temporary'])
	        })
	    });
	//console.log(fields);
	map.addLayer(polygonLayer);
	var fields = JSON.parse(fields);
	for(var i=0; i<fields.length; i++){
		addPoly(fields[i].coordinates, "fields", fields[i].kadastr, polygonLayer);
	}	
}
function sendTimeInterval() {

	var period = buildPeriodDto();

	$.ajax({
		type : "POST",
		url : 'report/buildTrack',
		data : JSON.stringify(period),
		dataType : 'json',
		contentType : 'application/json',
		mimeType : 'application/json',
		success: function (data) { 	
			addTrack(data);
        }
	});
}
function buildPeriodDto() {
	periodDto.dataFrom = parseDate($("#datepickerFrom").val(), $("#timeFrom")
			.val());
	periodDto.dataTo = parseDate($("#datepickerTo").val(), $("#timeTo").val());
	periodDto.terminalNumber = $("#terminalNumber").val();
	return periodDto;
}
function parseDate(date, time) {
	var dataArray = date.split("/");
	var timeArray = time.split(":");

	var resultDate = new Date(dataArray[2], dataArray[1] - 1, dataArray[0],
			timeArray[0], timeArray[1]);
	return resultDate.getTime();
}
var vectorLastPoint;


function setPoint(data){
	//Set point
	var ttt = new OpenLayers.LonLat(data.longitude, data.latitude);
    var point0 = new OpenLayers.Geometry.Point(ttt.lon, ttt.lat);
    point0.transform(new OpenLayers.Projection("EPSG:4326"), new OpenLayers.Projection("EPSG:900913"));
    
	 var stylePoint = new OpenLayers.Style(
	            {
	                pointRadius: 8,
	                strokeColor: "red",
	                strokeWidth: 2,
	                fillColor: "lime",
	                labelYOffset: -32,
	                label: "${label}",
	                fontSize: 16
	            }
	        );
	if(vectorLastPoint == null){
		vectorLastPoint = new OpenLayers.Layer.Vector("Последняя точка",
	    		{
	    		        styleMap: new OpenLayers.StyleMap(
	    		        { "default": stylePoint,
	    		          "select": { pointRadius: 20}
	    		        })
	    		 });
		map.addLayer(vectorLastPoint);
	}
	
	
	
	map.setCenter(new OpenLayers.LonLat(point0.x, point0.y), 13);
     
    var lastPoint = new OpenLayers.Feature.Vector(
			point0, {
				label: "Местонахождение", 
				title: "lastPoint", 
				PointId: "lastPoint"
				}
			);
    vectorLastPoint.removeFeatures(vectorLastPoint.features);
    vectorLastPoint.addFeatures(lastPoint);
    vectorLastPoint.refresh();
    console.log(vectorLastPoint.features);
    lastPointContent = "Дата: " + new Date(data.messageDate).toLocaleString();

}

function parseMap(kadNumber){
    var cadnum = kadNumber;
    var cad_arr = cadnum.split(":");
    $.getJSON('http://212.26.144.110/kadastrova-karta/find-Parcel',
        {'cadnum': cadnum},
        function (data) {
            //Устанавливаем границы
            var x1 = data['data'][0]['st_xmin'];
            var y1 = data['data'][0]['st_ymin'];
            var x2 = data['data'][0]['st_xmax'];
            var y2 = data['data'][0]['st_ymax'];
            var new_bounds_res = new OpenLayers.Bounds.fromString(x1 + "," + y1 + "," + x2 + "," + y2);
            console.log("Data first");
            //console.log(data.getVertices());
            //Подстроаиваем zoom
            map.zoomToExtent(new_bounds_res);

            //Получаем координаты центра
            var x = new_bounds_res.centerLonLat.lat;
            var y = new_bounds_res.centerLonLat.lon;
            map.setCenter(new OpenLayers.LonLat(y, x));
            var popupContent;
            $.ajax({
                url: 'http://212.26.144.110/kadastrova-karta/get-parcel-Info',
                dataType: 'json',
                async: false,
                data: {
                    'koatuu': cad_arr[0],
                    'zone': cad_arr[1],
                    'quartal': cad_arr[2],
                    'parcel': cad_arr[3]
                },
                success: function (data) {
                
                    kadastrNumber = data.data[0].cadnum;
                    use = data.data[0].use;
                    area = data.data[0].area;
                    popupContent = "Кадастровий номер: " + data.data[0].cadnum + "<br\>" +
                        "Назначение: " + data.data[0].use + "<br\>" +
                        "Площадь: " + data.data[0].area;
                }
            });
            var popup = new OpenLayers.Popup.FramedCloud("Popup",
                new OpenLayers.LonLat(y, x), null,
                popupContent, null,
                true
            );

            map.addPopup(popup);
            for(var i=0; i<data.data.length; i++){
                console.log(data.data[i]);
            }
        });
}
function overlay_getTileURL(bounds) {
    var res = this.map.getResolution();
    var x = Math.round((bounds.left - this.maxExtent.left) / (res * this.tileSize.w));
    var y = Math.round((bounds.bottom - this.tileOrigin.lat) / (res * this.tileSize.h));
    var z = this.map.getZoom();
    console.log(x + " " + y + " " + z);

    if (this.map.baseLayer.name == 'Virtual Earth Roads' || this.map.baseLayer.name == 'Virtual Earth Aerial' || this.map.baseLayer.name == 'Virtual Earth Hybrid') {
        z = z + 1;
    }
    return "http://212.26.144.110" + "/tile2/map_100000/" + z + "/" + x + "/" + y + "." + this.type;
}