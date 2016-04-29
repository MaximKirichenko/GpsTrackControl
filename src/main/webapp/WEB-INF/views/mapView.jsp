<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/resource/css/OpenLayers.css"/>">

    <script src="<c:url value="/resource/JS/jquery-1.11.3.js"/>"></script>
    <script src="<c:url value="/resource/JS/OpenLayers.js"/>"></script>
    <script src="<c:url value="/resource/JS/Controller.js"/>"></script>
    <title>Kadastr maps</title>

</head>
<body onload="loadSuccess()">

<div>
    <input id="searchField" type="text" placeholder="6521555100:07:049:0017" name="searchField" style="position: absolute; top: 10px; left: 50px; z-index: 10; width: 200px;"/>
</div>
<div id="map" style="position: absolute; top: 0px; right: 0px; bottom: 0px; left: 0px;  z-index: 0"></div>
<div style="position: absolute; bottom: 50px; right: 10px;  z-index: 10; background-color: #208b09;">
    <ul id="controlToggle" >
        <li>
            <input type="radio" name="type" value="none" id="noneToggle"
                   onclick="toggleControl(this);" checked="checked" />
            <label for="noneToggle">Просмотр</label>
        </li>

        <li>
            <input type="radio" name="type" value="polygon" id="polygonToggle" onclick="toggleControl(this);" />
            <label for="polygonToggle">Рисование</label>
        </li>
        <li>
            <input type="checkbox" name="allow-pan" value="allow-pan" id="allowPanCheckbox" checked=true onclick="allowPan(this);" />
            <label for="allowPanCheckbox">Рисование + просмотр</label>
        </li>
    </ul>
</div>
<script>LoadMap()</script>
<script>getFields('${fields}')</script>


</body>
</html>