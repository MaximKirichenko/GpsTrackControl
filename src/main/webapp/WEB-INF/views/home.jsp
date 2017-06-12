<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<link rel="stylesheet" href="<c:url value="/resource/css/jquery-ui.css"/>">
	<link rel="stylesheet" href="<c:url value="/resource/css/home_page.css"/>">
	
	
<!-- 	<script src="http://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyD1fO8NBDPstlRrT0VDv7gVfsO19CTVV4g"></script>   -->
    <script src="http://maps.google.com/maps/api/js?v=3&key=AIzaSyD1fO8NBDPstlRrT0VDv7gVfsO19CTVV4g&sensor=false"></script> 
	<script src="<c:url value="/resource/JS/jquery-1.11.3.js"/>"></script>
	<script src="<c:url value="/resource/JS/jquery-ui.js"/>"></script>
	
	<script src="<c:url value="/resource/JS/OpenLayers.js"/>"></script>
	<script src="<c:url value="/resource/JS/ExtendedRenderers.js"/>"></script>
	<script src="<c:url value="/resource/JS/controller/homePageBuilder.js"/>"></script>
	<script src="<c:url value="/resource/JS/service/mapService.js"/>"></script>
	<script src="<c:url value="/resource/JS/service/dateMaintainingService.js"/>"></script>
	<script src="<c:url value="/resource/JS/canvasjs/canvasjs.min.js"/>"></script>
	<title>KSG Agro</title>
</head>
<body onload="getFields('${fields}')">
<div id="add-map-object-form">
	<div id="add-map-object-content"></div>
	<div id="add-map-object-button-box">
		<input type='button' class='button' id='add-map-object-submit' value='ok'/>
		<input type='button' class='button' id='add-map-object-cancel' value='cancel'/>
	</div>
</div>
<div id="view-window">
	<div id="view-window-content"></div>
	<div id="view-window-button-box">
		<input type='button' class='button' id='view-window-submit' value='ok'/>
	</div>
</div>
	<div class="page-container" >
		<div class="header-panel">
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
				<li><a href="<spring:url value="/located/image"/>">Загрузка картинок</a></li>
				<li><a href="<spring:url value="/logout"/>">Выход</a></li>
			</ul>
			</div>
			<div id="dataPicker">
				<input id="datepickerFrom"
				placeholder="<spring:message code="carDetailsForm.Label.BuildReport.From"/>" type="text" name="dateFrom" value="" /> 
				<input id="datepickerTo" placeholder="<spring:message code="carDetailsForm.Label.BuildReport.To" />"
				type="text" name="dateTo" /> 
				<input id="timeFrom" placeholder="<spring:message code="carDetailsForm.Label.BuildReport.TimeFormatFrom" />"
				name="timeFrom" type="text" name="timeFrom" /> 
				<input id="timeTo"
				placeholder="<spring:message  code="carDetailsForm.Label.BuildReport.TimeFormatTo" />"
				name="timeTo" type="text" name="timeTo" />
				<button id="dateSubmit">Показать</button>
			</div>
		</div>
		</div>
		<div class="left-col">
			<div id="cars_select_menu">
			<div id="accordion">
			<c:forEach var="groupItem" items="${vehicleGroups}" varStatus="loop">
				<h3>${groupItem.groupName }</h3>
				<div>
				<ul class="accordion_menu" id="accordion_menu">	
				<c:forEach var="menuItem" items="${vehicleMenuItems}" varStatus="loop">
					<c:if test="${menuItem.vehicle.group.id==groupItem.id }">
						<li><input type="checkbox" name="${menuItem.vehicle.id }_check" class="vehicle_checkbox"/>
						<input type="hidden" class="vehicleId" value="${menuItem.vehicle.id }">
						<input type="hidden" class="messageDateValue" value="${menuItem.lastSignal.messageDate }">
						<input type="hidden" class="speedValue" value="${menuItem.lastSignal.speed }">
							${menuItem.vehicle.name } (${menuItem.vehicle.regNumber })
						</li>		
					</c:if>
				</c:forEach>
				</ul>
				</div>
			</c:forEach>
			</div>

			</div>
		</div>
		<div class="right-col">
			<div id="tabs">
  				<ul>
    				<li><a href="#tabs-1">Карта</a></li>
    				<li><a href="#tabs-2">Информация о выбранных ТС</a></li>
    				<li><a href="#tabs-3">Отчет по выбранным ТС</a></li>
    				<li><a href="#tabs-4">График</a></li>
  				</ul>
  				<div id="tabs-1">
  					<div id="map-tools">
  						<label>Инструменты</label><br>
  						<div id="tool-selector">
  							<ul id="controlToggle">
        <li>
            <input type="radio" name="type" value="none" id="noneToggle"
                   onclick="toggleControl(this);" checked="checked" />
            <label for="noneToggle">Навигация</label>
        </li>
        <li>
            <input type="radio" name="type" value="line" id="lineToggle" onclick="toggleControl(this);" />
            <label for="lineToggle">Измерить растояние</label>
        </li>
        <li>
            <input type="radio" name="type" value="polygon" id="polygonToggle" onclick="toggleControl(this);" />
            <label for="polygonToggle">Измерить площадь</label>
        </li>
        <li>
            <input type="radio" name="type" value="drawPolygon" id="drawPolygon" onclick="toggleControl(this);" />
            <label for="drawPolygon">Добавить поле</label>
        </li>
    </ul>
  						</div>
  						
  					</div>
  					<div id="measure-window"></div>
  					
    				<div id="map"></div>
  				</div>
  				<div id="tabs-2">
    				<h3 class="empty-page-message">Для отображения информации, выберите необходимую технику в меню слева</h3>
  				</div>
  				<div id="tabs-3">
  					<h3 class="empty-page-message">Для отображения информации, выберите необходимую технику в меню слева</h3>
  				</div>
  				<div id="tabs-4">
  					<h3 class="empty-page-message">Для отображения информации, выберите необходимую технику в меню слева</h3>
  					<div class="car-selelect-field">
  						<select name="rendering-car-id" id="rendering-car-id">
  						</select>
  					</div>
    				<div id="chartContainer" style="height: 300px; width: 100%;"></div>
  				</div>
			</div>
		</div>
	</div>
</body>
</html>