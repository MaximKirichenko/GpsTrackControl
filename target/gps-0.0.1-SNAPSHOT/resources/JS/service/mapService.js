var MIN_STOP_TIME = 30000;
var map, drawControls;
var trackLayer, tmsoverlay_orto, kadpodil, polygonLayer, mapNik, mapObject, anamyLayer;
var gmap;
var vectorStartPoint;
var stopMarkersLayer;

var kadastrNumber, use, area;
var pointContent = new Array();
var mapBounds = new OpenLayers.Bounds(-160, -74, 160, 74);
var polygonHandler;
var latLngArray = new Array();
var periodDto = {
    dataFrom: 0,
    dataTo: 0,
    terminalNumber: 0
};
var lastPointContent;

//Load on start page
//$("document").ready(function(){
//    $("#searchField").keyup(function(e){
//        if(e.keyCode == 13){
//            searchKadNumber($("#searchField").val());
//            $(this).blur();
//            return false;
//        }
//    });
//});


function initLayer() {
    // style the sketch fancy
    var sketchSymbolizers = {
        "Point": {
            pointRadius: 4,
            graphicName: "square",
            fillColor: "white",
            fillOpacity: 1,
            strokeWidth: 1,
            strokeOpacity: 1,
            strokeColor: "red"
        },
        "Line": {
            strokeWidth: 3,
            strokeOpacity: 1,
            strokeColor: "red",
            strokeDashstyle: "dash"
        },
        "Polygon": {
            strokeWidth: 2,
            strokeOpacity: 1,
            strokeColor: "red",
            fillColor: "white",
            fillOpacity: 0.3
        }
    };
    var style = new OpenLayers.Style();
    style.addRules([
        new OpenLayers.Rule({symbolizer: sketchSymbolizers})
    ]);
    var styleMap = new OpenLayers.StyleMap({"default": style});
    var stylePoint = new OpenLayers.Style(
        {
            pointRadius: 8,
            strokeColor: "red",
            strokeWidth: 2,
            fillColor: "blue",
            labelYOffset: -16,
            label: "${label}",
            fontSize: 16
        }
    );
    var styleField = new OpenLayers.Style(
        {
            pointRadius: 8,
            strokeColor: "blue",
            strokeWidth: 2,
            fillColor: "#C9A646",
            fillOpacity: 0.3,
            strokeColor: "#413206",
            labelYOffset: -16,
            label: "${label}",
            fontSize: 16
        }
    );
//	var mapObjectLayerListener = {
//			featureclick: function(e) {
//		        alert(e.object.name + " says: " + e.feature.id + " clicked.");
//		        return false;
//		    }	
//	};
    mapObject = new OpenLayers.Layer.Vector("Пользовательские объекты", {
        styleMap: new OpenLayers.StyleMap(
            {
                "default": styleField,
                "select": {pointRadius: 20}
            })
//	   eventListeners: mapObjectLayerListener
    });
// Marker layers	
    vectorStartPoint = new OpenLayers.Layer.Vector("Остановки",
        {
            styleMap: new OpenLayers.StyleMap(
                {
                    "default": stylePoint,
                    "select": {pointRadius: 20}
                })
        });
    stopMarkersLayer = new OpenLayers.Layer.Markers("Stop markers")
//   var listeners = {
//		 featureclick: function(evt) {
//			 console.log(evt.feature.attributes.PoliID);
//			 $.ajax({
//					type : "POST",
//					url : 'getFieldInfo',
//					data : JSON.stringify(evt.feature.attributes.PoliID),
//					dataType : 'json',
//					contentType : 'application/json',
//					mimeType : 'application/json',
//					success: function (data) { 	
//						$("#view-window-content").append("Площадь, га " + data.fieldArea + "</br>");
//						$("#view-window-content").append("Номер поля " + data.fieldNumber + "</br>");
//						 $("#view-window").center();
//						 $("#view-window").show();
//			        }
//				});
//
//		    }
//	};
// Track layer	 
    var layerListeners = {
        featureclick: function (evt) {
            console.log(evt);
            $.ajax({
                type: "POST",
                url: 'getFieldInfo',
                data: JSON.stringify(evt.feature.attributes.PoliID),
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    console.log(data);
                    $("#view-window-content").append("Площадь, га " + data.fieldArea + "</br>");
                    $("#view-window-content").append("Номер поля " + data.fieldNumber + "</br>");
                    $("#view-window").center();
                    $("#view-window").show();
                }
            });
            return false;
        }
    };
    trackLayer = new OpenLayers.Layer.Vector("Трэк", {
        renderers: ['SVGExtended', 'VMLExtended', 'CanvasExtended'],
        eventListeners: layerListeners,
        styleMap: new OpenLayers.StyleMap({
            'default': OpenLayers.Util.extend({
                orientation: true

            }, OpenLayers.Feature.Vector.style['default']),
            'temporary': OpenLayers.Util.extend({
                orientation: true
            }, OpenLayers.Feature.Vector.style['temporary'])
        })
    });
    anamyLayer = new OpenLayers.Layer.Vector("Вражины", {
        renderers: ['SVGExtended', 'VMLExtended', 'CanvasExtended'],
        eventListeners: layerListeners,
        styleMap: new OpenLayers.StyleMap({
            'default': OpenLayers.Util.extend({
                orientation: true

            }, OpenLayers.Feature.Vector.style['default']),
            'temporary': OpenLayers.Util.extend({
                orientation: true
            }, OpenLayers.Feature.Vector.style['temporary'])
        })
    });
    trackLayer.styleMap.styles.default.defaultStyle.strokeWidth = 1;
    trackLayer.styleMap.styles.default.defaultStyle.strokeColor = "#FFF92C";
    console.log(trackLayer);

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

    polygonLayer = new OpenLayers.Layer.Vector("Вражины", {
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

    tmsoverlay_orto = new OpenLayers.Layer.TMS("Ортофотоплани", "", {
        type: 'jpg',
        getURL: overlay_getTileURL,
        alpha: true,
        isBaseLayer: true
    });
    gmap = new OpenLayers.Layer.Google(
        "Google Hybrid",
        {type: google.maps.MapTypeId.HYBRID, numZoomLevels: 20});

    kadpodil = new OpenLayers.Layer.WMS(
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

    // allow testing of specific renderers via "?renderer=Canvas", etc
    var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
    renderer = (renderer) ? [renderer] : OpenLayers.Layer.Vector.prototype.renderers;
    polygonHandler = OpenLayers.Handler.Polygon;
    drawControls = {
        line: new OpenLayers.Control.Measure(
            OpenLayers.Handler.Path, {
                persist: true,
                geodesic: true,
                handlerOptions: {
                    layerOptions: {
                        renderers: renderer,
                        styleMap: styleMap

                    }
                }
            }
        ),
        polygon: new OpenLayers.Control.Measure(
            OpenLayers.Handler.Polygon, {
                persist: true,
                geodesic: true,
                handlerOptions: {
                    layerOptions: {
                        renderers: renderer,
                        styleMap: styleMap

                    }
                }
            }
        ),
        drawPolygon: new OpenLayers.Control.DrawFeature(polygonLayer,
            polygonHandler, {
                persist: true,
                handlerOptions: {
                    layerOptions: {
                        renderers: renderer,
                        styleMap: styleMap

                    }
                }
            })
    };


    //Create OpenStreet map Layer
    mapNik = new OpenLayers.Layer.OSM();

}
function LoadMap() {
    initLayer();
    getFields($("#fieldsArray").val());
    //Границы задаются для проекции EPSG:900913.
    var maxExtent = new OpenLayers.Bounds(-20037508, -20037508, 20037508, 20037508);
    var restrictedExtent = maxExtent.clone();
    var maxResolution = 156543.0339;

    //Отображаться карта будет в проекции EPSG:4326.
    var options = {
        projection: new OpenLayers.Projection("EPSG:900913"),
        displayProjection: new OpenLayers.Projection("EPSG:4326"),
        numZoomLevels: 18,
//        featureEvents: true,
        maxResolution: maxResolution,
        maxExtent: maxExtent,
        restrictedExtent: restrictedExtent
//        eventListeners: {
//            featureclick: function(e) {            	
//            	if(e.feature.data.title == "point"){
//            		attachPointMessage(e.feature);
//            	}else if(e.feature.data.label == "fields"){
//            		attachFieldsMessage(e.feature);
//            	}else if(e.feature.data.title == "lastPoint"){
//            		attachLastPointMessage(e.feature);
//            	}
//            }
//        }
    };

    //Create a map
    map = new OpenLayers.Map('map', options);


//    if (OpenLayers.Util.alphaHack() == false) {
//        tmsoverlay_orto.setOpacity(0.7);
//    }

    //Add scale and position of mouse hint
    map.addControl(new OpenLayers.Control.ScaleLine());
    map.addControl(new OpenLayers.Control.MousePosition());
    //Add layers control panel
    map.addControl(new OpenLayers.Control.LayerSwitcher());


    //Add control to the map
    var control;
    for (var key in drawControls) {
        control = drawControls[key];
        control.geodesic = true;
        control.events.on({
            "measure": handleMeasurements,
            "measurepartial": handleMeasurements
        });
        map.addControl(control);
    }

    //Add all created layers
    map.addLayers([mapNik, gmap, kadpodil, tmsoverlay_orto, trackLayer, polygonLayer, anamyLayer, vectorStartPoint, stopMarkersLayer]);

    //Centered map
    var point0 = new OpenLayers.Geometry.Point(35.051746, 48.470277);
    point0.transform(new OpenLayers.Projection("EPSG:4326"), new OpenLayers.Projection("EPSG:900913"));
    map.setCenter(new OpenLayers.LonLat(point0.x, point0.y), 8);


    //Finished poligon event
    polygonLayer.events.register('featureadded', polygonLayer, function (evt) {
        console.log("Генерация события");


        var geom = evt.feature.geometry;
        console.log(geom);
        var coordinates = geom.getVertices(false);
        var area = (geom.getGeodesicArea(new OpenLayers.Projection("EPSG:900913"))) / 10000;
        showAddFieldForm(area);


        for (var i = 0; i < coordinates.length; i++) {
            coordinates[i].transform(new OpenLayers.Projection("EPSG:900913"), new OpenLayers.Projection("EPSG:4326"));
            var latLng = {
                x: coordinates[i].x,
                y: coordinates[i].y
            };

            latLngArray.push(latLng);
        }
    });

}

function showAddFieldForm(area) {
    createAddFieldsForm(area);
    $("#add-map-object-form").show();
}
var enterprises;
function createAddFieldsForm(area) {
    $.ajax({
        type: "POST",
        url: 'getEnterprises',
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (enterpriseList) {
            enterprises = enterpriseList;
            $("#add-map-object-content").append("<label class='object-label'>Тип объекта</label>");
            $("#add-map-object-content").append("<select name='select-object-name' id='select-object-name'>" +
                "<option>Поле</option></select>");
            $("#add-map-object-content").append("<label class='object-label'>№</label>");
            $("#add-map-object-content").append("<input type='text' class='object-field' name='fieldNumber'/>");
            $("#add-map-object-content").append("<label class='object-label'>Принадлежность</label>");
            $("#add-map-object-content").append("<select name='fieldEnterprice'></select>");

            for (var i = 0; i < enterprises.length; i++) {
                $("select[name='fieldEnterprice']").append("<option>" + enterprises[i].enterprise + "</option>");
            }
            $("#add-map-object-content").append("<label class='object-label'>Площадь, Га</label>");
            $("#add-map-object-content").append("<input type='text' name='fieldArea' value='" + Math.round(area * 100) / 100

                + "'/>");
            $("#add-map-object-form").center();
        }
    });

}

function sendMapObjectField() {
    var enterpriseString = $("select[name='fieldEnterprice'] option:selected").text();
    var enterpriseId = getEnterpriseIfFromString(enterpriseString);
    var field = {
        fieldNumber: $("input[name='fieldNumber']").val(),
        fieldEnterprice: enterpriseId,
        fieldArea: $("input[name='fieldArea']").val(),
        latLngArray: latLngArray
    };
    $.ajax({
        type: "POST",
        url: 'addMapObjectField',
        data: JSON.stringify(field),
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (data) {
            console.log(data);
            latLngArray = [];
        }
    });
    console.log(field);
}
function getEnterpriseIfFromString(enterpriseString) {
    for (var i = 0; i < enterprises.length; i++) {
        if (enterpriseString == enterprises[i].enterprise) return enterprises[i].id;
    }
}
function attachLastPointMessage(point) {

    var popup = new OpenLayers.Popup.FramedCloud("Popup",
        new OpenLayers.LonLat(point.geometry.x, point.geometry.y), null,
        lastPointContent, null,
        true
    );
    map.addPopup(popup);
}
function attachPointMessage(point) {
    console.log(point.geometry.x + " " + point.geometry.y);
    console.log(point.geometry.x + " " + point.geometry.y);
    var popup = new OpenLayers.Popup.FramedCloud("Popup",
        new OpenLayers.LonLat(point.geometry.x, point.geometry.y), null,
        pointContent[point.data.PointId], null,
        true
    );
    map.addPopup(popup);
}
function attachFieldsMessage(field) {
    console.log(field.data.PoliID);
    $.ajax({
        type: "POST",
        url: 'report/report/getKadastrInfo',
        data: JSON.stringify(field.data.PoliID),
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
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


function toggleControl(element) {
    for (key in drawControls) {

        var control = drawControls[key];
        if (element.value == key) {
            control.activate();
        } else {
            control.deactivate();
        }
    }
}

function handleMeasurements(event) {
    var geometry = event.geometry;
    var units = event.units;
    var order = event.order;
    var measure = event.measure;
    var element = document.getElementById('measure-window');
    var out = "";
    if (order == 1) {
        out += "measure: " + measure.toFixed(3) + " " + units;
    } else {
        out += "measure: " + measure.toFixed(3) + " " + units + "<sup>2</" + "sup>";
    }
    $("#measure-window").append("<div id='measure-window'>" + out + "</div>");
}
//function allowPan(element) {
//    var stop = !element.checked;
//    for(var key in drawControls) {
//        drawControls[key].handler.stopDown = stop;
//        drawControls[key].handler.stopUp = stop;
//    }
//}
//function searchKadNumber(kadNumber){
//	console.log("Other method");
//	console.log("kadNumber: " + kadNumber);
//    var cadnum = kadNumber;
//    var cad_arr = cadnum.split(":");
//    console.log("cadnum: " + cadnum);
//    $.ajax({
//    	type:"GET",
//    	dataType: "application/json",
//    	url: 'http://212.26.144.110/kadastrova-karta/find-Parcel',
//    	crossDomain: true,
//    	data:{'cadnum': cadnum,
//        	'activeArchLayer':1},
//    	success: function (data) {
//            //Устанавливаем границы
//            var x1 = data['data'][0]['st_xmin'];
//            var y1 = data['data'][0]['st_ymin'];
//            var x2 = data['data'][0]['st_xmax'];
//            var y2 = data['data'][0]['st_ymax'];
//            var new_bounds_res = new OpenLayers.Bounds.fromString(x1 + "," + y1 + "," + x2 + "," + y2);
//            console.log("Data first");
//            //console.log(data.getVertices());
//            //Подстроаиваем zoom
//            map.zoomToExtent(new_bounds_res);
//
//            //Получаем координаты центра
//            var x = new_bounds_res.centerLonLat.lat;
//            var y = new_bounds_res.centerLonLat.lon;
//            map.setCenter(new OpenLayers.LonLat(y, x), 16);
//            var popupContent;
//            $.ajax({
//                url: 'http://212.26.144.110/kadastrova-karta/get-parcel-Info',
//                dataType: 'json',
//                async: false,
//                data: {
//                    'koatuu': cad_arr[0],
//                    'zone': cad_arr[1],
//                    'quartal': cad_arr[2],
//                    'parcel': cad_arr[3]
//                },
//                success: function (data) {
//                
//                    kadastrNumber = data.data[0].cadnum;
//                    use = data.data[0].use;
//                    area = data.data[0].area;
//                    popupContent = "Кадастровий номер: " + data.data[0].cadnum + "<br\>" +
//                        "Назначение: " + data.data[0].use + "<br\>" +
//                        "Площадь: " + data.data[0].area;
//                }
//            });
//            var popup = new OpenLayers.Popup.FramedCloud("Popup",
//                new OpenLayers.LonLat(y, x), null,
//                popupContent, null,
//                true
//            );
//
//            map.addPopup(popup);
//            for(var i=0; i<data.data.length; i++){
//                console.log(data.data[i]);
//            }
//        }
//    });
//        
//}
//


var linearRing2;
var pointsVector;
function addTracks(trackInfo) {
    var track = [];
    var labels = new Array();
    var features = trackLayer.features;
    if (features.length != 0) {
        console.log("Clean. Track length " + features.length);
        for (var i = 0; i < features.length; i++) {
            if ((features[i].geometry.id).indexOf("LineString") !== -1) {
                console.log(features[i].geometry.id);
                trackLayer.removeFeatures(features[i]);
            }

        }

    }


//	vectorStartPoint.removeAllFeatures();
//	vectorStartPoint.destroyFeatures();
//	vectorStartPoint.addFeatures([]);

    stopMarkersLayer.clearMarkers();
    clearPopUp();

//	if(linearRing2!=null){
//	    	console.log("Clean point");
//	    	for(var i=0; i<labels.length; i++){
//	        	vectorStartPoint.removeFeatures(labels[i]);
//	        }
//	    	
//	    }

    for (var j = 0; j < trackInfo.length; j++) {
        var labelContent = new Array();
        var trackPoints = new Array();
        var oldData;
        for (var i = 0; i < trackInfo[j].data.length; i++) {
            var point = createPoint(trackInfo[j].data[i].longitude, trackInfo[j].data[i].latitude);
            trackPoints.push(point);
            if (i == 0) {
                var travelTime = (trackInfo[j].data[i].messageDate - trackInfo[j].data[0].messageDate) / 1000;
                travelTime = travelTime.toString().toHHMMSS();
                var popupContent = trackInfo[j].vehicle.name + " " + trackInfo[j].vehicle.regNumber +
                    "</br>Время старта: " + new Date(trackInfo[j].data[i].messageDate).toLocaleString();
                stopMarkersLayer.addMarker(createStartMarker(point, "Start", popupContent));
            } else if (i == trackInfo[j].data.length - 1) {
                var travelTime = (trackInfo[j].data[i].messageDate - trackInfo[j].data[0].messageDate) / 1000;
                travelTime = travelTime.toString().toHHMMSS();
                var popupContent = trackInfo[j].vehicle.name + " " + trackInfo[j].vehicle.regNumber +
                    "</br>Длина пути: " + trackInfo[j].trackInfo.totalLength + " км." +
                    "</br>Время в пути: " + travelTime +
                    "</br>Время финиша: " + new Date(trackInfo[j].data[i].messageDate).toLocaleString();

                stopMarkersLayer.addMarker(createFinishMarker(point, "Stop", popupContent));
                map.setCenter(new OpenLayers.LonLat(point.x, point.y));
            } else if (i > 0) {
                var stopTime = trackInfo[j].data[i].messageDate - oldData.messageDate;
                if (stopTime > MIN_STOP_TIME && stopTime < 360000) {
                    var travelTime = (trackInfo[j].data[i].messageDate - trackInfo[j].data[0].messageDate) / 1000;
                    travelTime = travelTime.toString().toHHMMSS();
                    var stopDuration = (stopTime / 1000).toString().toHHMMSS();
                    var popupContent = trackInfo[j].vehicle.name + " " + trackInfo[j].vehicle.regNumber +
                        "</br>Время в пути: " + travelTime +
                        "</br>Время остановки: " + new Date(trackInfo[j].data[i].messageDate).toLocaleString() +
                        "</br>продолжительность остановки: " + stopDuration;
                    stopMarkersLayer.addMarker(createStopMarker(point, stopTime, popupContent));
                } else if (stopTime >= 360000) {
                    console.log("Stop");
                    var travelTime = (trackInfo[j].data[i].messageDate - trackInfo[j].data[0].messageDate) / 1000;
                    travelTime = travelTime.toString().toHHMMSS();
                    var stopDuration = (stopTime / 1000).toString().toHHMMSS();
                    var popupContent = trackInfo[j].vehicle.name + " " + trackInfo[j].vehicle.regNumber +
                        "Время парковки: " + new Date(trackInfo[j].data[i].messageDate).toLocaleString() +
                        "</br>продолжительность стоянки: " + stopDuration;
                    stopMarkersLayer.addMarker(createStopMarker(point, stopTime, popupContent));
                }
            }
            oldData = trackInfo[j].data[i];

        }

        for (var i = 0; i < labels.length; i++) {
            vectorStartPoint.addFeatures(labels[i]);
        }

        track = new OpenLayers.Geometry.LineString(trackPoints);
        linearRing2 = new OpenLayers.Feature.Vector(track);
        trackLayer.addFeatures(linearRing2);
    }

}
function clearPopUp() {
    if (map.popups.length != 0) {
        while (map.popups.length != 0) {
            map.removePopup(map.popups[0]);
        }
    }
}
function createPopup(point, text) {
    var popupId = 'point.x' + 'point.y';
    var lonLat = new OpenLayers.LonLat(point.x, point.y);
    var size = new OpenLayers.Size(100, 50);
    var closeButton = true;

    var popUp = new OpenLayers.Popup(popupId, lonLat, size, text, closeButton);
    popUp.autoSize = true;
    map.addPopup(popUp);
}
function createParkMarker(point, text, popupContent) {
    return createMarker(point, '/resource/parkingIcon.png', text, popupContent);
}
function createStopMarker(point, text, popupContent) {
    return createMarker(point, '/resource/stopIcon.png', text, popupContent);
}
function createStartMarker(point, text, popupContent) {
    return createMarker(point, '/resource/startIcon.png', text, popupContent);
}
function createFinishMarker(point, text, popupContent) {
    return createMarker(point, '/resource/finishIcon.png', text, popupContent);
}
function createMarker(point, img, text, popupContent) {
    var size = new OpenLayers.Size(30, 30);
    var offset = new OpenLayers.Pixel(-(size.w / 2), -size.h);
    var icon = new OpenLayers.Icon(img, size, offset);
    var marker = new OpenLayers.Marker(new OpenLayers.LonLat(point.x, point.y), icon);
    marker.setOpacity(0.8);
    marker.events.register('mousedown', marker,
        function (evt) {
            createPopup(point, popupContent);
            OpenLayers.Event.stop(evt);
        });
    return marker;
}

function showMessage(text) {
    alert(text);
}

function createPoint(longitude, latitude) {
    var lonLat = new OpenLayers.LonLat(longitude, latitude);
    var point = new OpenLayers.Geometry.Point(lonLat.lon, lonLat.lat);
    point.transform(new OpenLayers.Projection("EPSG:4326"), new OpenLayers.Projection("EPSG:900913"));
    return point;
}

function addPoly(data, title, ident, layr) {
    var featuress = Array();

    for (var i = 0; i < data.length; i++) {

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
function addUserObject(field, layer) {
    var coordinates = field.latLngArray;
    var featuress = Array();
    for (var i = 0; i < coordinates.length; i++) {

        var ttt = new OpenLayers.LonLat(coordinates[i].x, coordinates[i].y);
        var point0 = new OpenLayers.Geometry.Point(ttt.lon, ttt.lat);
        point0.transform(new OpenLayers.Projection("EPSG:4326"), new OpenLayers.Projection("EPSG:900913"));
        featuress.push(point0);


    }
    var linearRing2 = new OpenLayers.Geometry.LinearRing(featuress);
    var polygonFeatures2 = new OpenLayers.Feature.Vector(
        new OpenLayers.Geometry.Polygon([linearRing2]),
        {
            label: field.fieldNumber,
            PoliID: field.id

        });
    polygonFeatures2.style = {
        pointRadius: 8,
        strokeColor: "blue",
        strokeWidth: 2,
        fillColor: field.color,
        fillOpacity: 0.3,
        strokeColor: "#413206"
    };
    if (layer == "field") trackLayer.addFeatures(polygonFeatures2);
    console.log(anamyLayer);
    if (layer == "anamy") anamyLayer.addFeatures(polygonFeatures2);
}
function getFields(fields){
	if(fields!=null){
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
		 polygonLayer.setVisibility(false);
		map.addLayer(polygonLayer);
		for(var i=0; i<fields.length; i++){
			addPoly(fields[i].coordinates, "fields", fields[i].kadastr, polygonLayer);
		}	
	}
	
}

function getMapObjectField(fields) {
    if (fields != null) {
        for (var i = 0; i < fields.length; i++) {
            addUserObject(fields[i], "field");
        }
    }
}

function getMapAnamyField(fields) {
    map.addLayer(anamyLayer);
    if (fields != null) {
        for (var i = 0; i < fields.length; i++) {
            addUserObject(fields[i], "anamy");
        }
    }
}

function parseDate(date, time) {
    var dataArray = date.split("/");
    var timeArray = time.split(":");

    var resultDate = new Date(dataArray[2], dataArray[1] - 1, dataArray[0],
        timeArray[0], timeArray[1]);
    return resultDate.getTime();
}
var vectorLastPoint;


function setPoint(data) {
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
    if (vectorLastPoint == null) {
        vectorLastPoint = new OpenLayers.Layer.Vector("Последняя точка",
            {
                styleMap: new OpenLayers.StyleMap(
                    {
                        "default": stylePoint,
                        "select": {pointRadius: 20}
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

function parseMap(kadNumber) {
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
            for (var i = 0; i < data.data.length; i++) {
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