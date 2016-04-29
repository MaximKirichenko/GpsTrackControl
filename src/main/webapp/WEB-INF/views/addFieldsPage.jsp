<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<title>KSG Agro</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" href="<c:url value="/resource/css/style.css"/>">
	<link rel="stylesheet" href="<c:url value="/resource/css/menu/horizontalMenu.css"/>">
	<link rel="stylesheet" href="<c:url value="/resource/css/chartReportMenu.css"/>">
	<link rel="stylesheet" href="<c:url value="/resource/css/popUpWindow.css"/>">
	<link rel="stylesheet" href="<c:url value="/resource/css/jquery-ui.css"/>">
	<link rel="stylesheet" href="<c:url value="/resource/css/adminMenuStyle.css"/>">
	<script src="http://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyD1fO8NBDPstlRrT0VDv7gVfsO19CTVV4g">
     </script>
	<script src="<c:url value="/resource/JS/jquery-1.11.3.js"/>"></script>
	<script src="<c:url value="/resource/JS/jquery-ui.js"/>"></script>
	<script src="<c:url value="/resource/JS/OpenLayers.js"/>"></script>
	<script src="<c:url value="/resource/JS/ExtendedRenderers.js"/>"></script>
	<script src="<c:url value="/resource/JS/service/mapService.js"/>"></script>
	<script src="<c:url value="/resource/JS/service/dateMaintainingService.js"/>"></script>
	<script src="<c:url value="/resource/JS/service/vehicleClickMaintainingService.js"/>"></script>
	<script src="<c:url value="/resource/JS/controller/homePageController.js"/>"></script>
</head>
<body>
	<div id="map"></div>
	<div id="headerBackground"></div>
	<div id = "header">
		<div id="logo">
			<img alt="" src="<c:url value="/resource/KSGAGRO_LOGO_PNG.png"/>"/>
		</div>
		<div id="headerContent">
			<div id="horizontalMenu">
			<ul>
				<li><a href="<spring:url value="/"/>">Карта</a></li>
				<li><a href="<spring:url value="/chart"/>">Графики</a></li>
				<li><a href="<spring:url value="/report"/>">Отчеты</a></li>
				<li><a href="<spring:url value="/adminPage"/>">Администрирование</a></li> 
				<li><a href="<spring:url value="/logout"/>">Выход</a></li> 
			</ul>
			</div>
		</div>
	</div>
		<div id="menuBlock">
			<div id="leftVerticalMenu">
				<ul>
					<c:forEach var="item" items="${menuItems }">
						<li><a href="${item.action }">${item.name }</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		
<div style="position: absolute; bottom: 20px; right: 10px;  z-index: 10; background-color: #208b09; opacity: 0.8;">
    <ul id="controlToggle" >
        <li>
            <input type="radio" name="type" value="none" id="noneToggle"
                   onclick="toggleControl(this);" checked="checked" />
            <label for="noneToggle">Навигация</label>
        </li>

        <li>
            <input type="radio" name="type" value="polygon" id="polygonToggle" onclick="toggleControl(this);" />
            <label for="polygonToggle">Редактор</label>
        </li>
        <li>
            <input type="checkbox" name="allow-pan" value="allow-pan" id="allowPanCheckbox" checked=true onclick="allowPan(this);" />
            <label for="allowPanCheckbox">Позволить навигацию во время редактирования</label>
        </li>
    </ul>
</div>
</body>
</html>