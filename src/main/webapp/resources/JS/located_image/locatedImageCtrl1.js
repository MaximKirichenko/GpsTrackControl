/**
 * Created by kma on 14.05.17.
 */
// var map;
var selectedImage;

$("document").ready(function(){
    LoadMap();
    $("#image_container").hide();
    $("#image_container-submit").click(function(){
        $("#image_container").hide();
        $("#image_container-content").empty();
    });
    $("#uploadButton").click(function(e){
        uploadImage();
        e.preventDefault();
    });
    // $(".image_item").click(function(e){
    //     findImage(e.target.id);
    // });
});

function uploadImage(){
    var image = new FormData();
    image.append('image', $("#imageUpload")[0].files[0]);
    $.ajax({
        type: "POST",
        url: "/located/image/upload",
        enctype: 'multipart/form-data',
        data: image,
        success: function (baseJSON) {
            if(baseJSON.status==="SUCCESS"){
                alert("Image was uploaded");
            }
            if(baseJSON.status==="FAIL"){
                alert(baseJSON.message);
            }
        },
        cache: false,
        contentType: false,
        processData: false
    });
}

function findImage(imageKey){

    // setColorsToDefault();
    // var currentFeature = findFeature(imageKey);
    // currentFeature.style.strokeColor = '#FF0022';
    //
    // var center = new OpenLayers.LonLat(currentFeature.geometry.x, currentFeature.geometry.y);
    // map.setCenter(center, 14);
    // console.log(currentFeature);
    //
    // function setColorsToDefault() {
    //     var imgLayer = imageLayer();
    //     for(var i = 0; i<imgLayer.features.length; i++){
    //         console.log(imgLayer.features[i].style.strokeColor);
    //         if(imgLayer.features[i].style.strokeColor==="#FF0022"){
    //             imgLayer.features[i].style.strokeColor = "#00FF00";
    //             console.log("Color updated");
    //         }
    //
    //     }
    // }
    // function findFeature(imageKey) {
    //     var imgLayer = imageLayer();
    //     for(var i = 0; i<imgLayer.features.length; i++)
    //         if(imgLayer.features[i].imageName===imageKey)
    //             return imgLayer.features[i];
    // }
    //
    // function imageLayer(){
    //     for(var i = 0; i<map.layers.length; i++){
    //         if(map.layers[i].name==='Images')
    //             return map.layers[i];
    //     }
    // }
    //
    // return currentFeature;
}



function LoadMap() {
    var map = new OpenLayers.Map('map', mapOptions());
    map.addControl(new OpenLayers.Control.ScaleLine());
    map.addControl(new OpenLayers.Control.MousePosition());
    map.addControl(new OpenLayers.Control.LayerSwitcher());
    map.addLayers([initOpenStreetMap(), initGoogleMap(), initKadPodil(), initImageLayer()]);
    map.setCenter(center(), 8);

    function center(){
        var point = transformedCoordinate(48.470277, 35.051746);
        return new OpenLayers.LonLat(point.x, point.y);
    }

    function mapOptions() {
        var maxExtent = new OpenLayers.Bounds(-20037508, -20037508, 20037508, 20037508);
        var restrictedExtent = maxExtent.clone();
        var maxResolution = 156543.0339;

        //Отображаться карта будет в проекции EPSG:4326.
        var options = {
            projection: new OpenLayers.Projection("EPSG:900913"),
            displayProjection: new OpenLayers.Projection("EPSG:4326"),
            numZoomLevels: 18,
            maxResolution: maxResolution,
            maxExtent: maxExtent,
            restrictedExtent: restrictedExtent
        };
        return options;
    }

    function initGoogleMap(){
        return new OpenLayers.Layer.Google(
            "Google Hybrid",
            {type: google.maps.MapTypeId.HYBRID, numZoomLevels: 20});
    }

    function initKadPodil(){
        return new OpenLayers.Layer.WMS(
            "Kad podil", "http://212.26.144.110/geowebcache/service/wms?tiled=true", {
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
    }

    function initOpenStreetMap() {
        return new OpenLayers.Layer.OSM();
    }

    function initImageLayer(){
        var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
        renderer = (renderer) ? [renderer] : OpenLayers.Layer.Vector.prototype.renderers;

        var layer_style = OpenLayers.Util.extend({}, OpenLayers.Feature.Vector.style['default']);
        layer_style.fillOpacity = 0.2;
        layer_style.graphicOpacity = 1;

        function showImage(fileKey) {
            $("#image_container-content").empty();
            var image = getImage(fileKey);
            $("#image_container").show();
            $("#image_container-content").append("<img id='ItemPreview' src="+image+" style='width: 600px; height: 400px'/>");

            // console.log(image);
            // document.getElementById("ItemPreview").src = "data:image/png;base64," + image.data;
            function getImage(fileKey) {
                return "/images/" + fileKey + ".jpg";
            }
        }

        var listener = {
            featureclick: function(e) {
                console.log(e.feature.imageName);
                showImage(e.feature.imageName);
                return false;
            }
        };

        var vectorLayer = new OpenLayers.Layer.Vector("Images", {
            style: layer_style,
            renderers: renderer,
            eventListeners: listener
        });
        console.log(vectorLayer);
        vectorLayer.addFeatures(getImageThumbnail());

        function getImageThumbnail(){
            var points = [];
            jQuery.ajax({
                url: '/located/image/coordinates',
                success: function (data) {
                    data.forEach(function (item) {
                        var p = point(item.longitude, item.latitude);
                        p.imageName = item.fileKey;
                        points.push(p);
                    });
                },
                async: false
            });
            return points;
        }

        return vectorLayer;
    }

    function point(longitude, latitude){
        var style = {
            strokeColor: "#00FF00",
            pointRadius: 10
        };
        return new OpenLayers.Feature.Vector(transformedCoordinate(longitude, latitude),null,style);
    }
    function transformedCoordinate(longitude, latitude) {
        var point = new OpenLayers.Geometry.Point(latitude, longitude);
        point.transform(new OpenLayers.Projection("EPSG:4326"), new OpenLayers.Projection("EPSG:900913"));
        return point;
    }
}

