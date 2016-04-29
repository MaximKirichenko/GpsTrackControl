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
	

	
	
<!-- <script type="text/javascript" -->
<%-- 	src="<c:url value="/resource/JS/buttonController.js"/>"></script> --%>
<script type="text/javascript"
	src="<c:url value="/resource/JS/showPopupWindow.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resource/JS/hidePopupWindow.js"/>"></script>



</head>
<body>
<div id="map"></div>
<input id="terminalNumber" type="hidden" name="terminalNumber"
				value="${numberTerminal}" /> 
<input id="selectedTerminalNumber" type="hidden" name="selectedTerminalNumber"
				value="" /> 
	<div id="pop-up-window" style="display: none;">
		<table id="carInfoTable">
				<tbody>
					
				</tbody>

			</table>
		<button id="hidePopUpButton">Ok</button>
	</div>
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
				<li><a href="<spring:url value="report/trackPage?terminalNumber=82"/>">Администрирование</a></li> 
				<li><a href="<spring:url value="/logout"/>">Выход</a></li> 
			</ul>
			</div>
			<div id="vehicleName">
				<h3>Объект не выбран</h3>
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
		<div id="menuBlock">
			<input class = "input search" id="searchField" type="text" placeholder="6521555100:07:049:0017" name="searchField" />
			<div id="ChartMenu">
				<ul>
				<% if (request.isUserInRole("ROLE_ADMIN")) { %>
				<c:forEach var="group" items="${listGroup}" varStatus="loop">
				<c:if test="${enterprise.id!=0 && enterprise.id!=2}">
					<li class="block"><input type="checkbox" name="item"
						id="item${loop.index }" /> <label for="item${loop.index }">
							<i aria-hidden="true" class="icon-users"></i>
							${group.groupName } <span>${count }</span>
					</label>
						<ul class="options">
							<c:forEach var="car" items="${listVehicle}">
								<c:if test="${group.id == car.group.id && car.numberTerminal != 72}">
									<li><a href="#" onclick="setTerminalNumber(${car.numberTerminal})"> 
									<i aria-hidden="true" class="icon-search"></i> ${car.name } ( ${car.regNumber } )
									</a></li>
								</c:if>
							</c:forEach>
						</ul>
						</li>
				</c:if>
			</c:forEach>
				<% } %>
				<% if (request.isUserInRole("ROLE_AGRO_USER")) { %>
				<c:forEach var="group" items="${listGroup}" varStatus="loop">
				<c:if test="${enterprise.id!=0 && enterprise.id!=2}">
					<li class="block"><input type="checkbox" name="item"
						id="item${loop.index }" /> <label for="item${loop.index }">
							<i aria-hidden="true" class="icon-users"></i>
							${group.groupName } <span>${count }</span>
					</label>
						<ul class="options">
							<c:forEach var="car" items="${listVehicle}">
								<c:if test="${group.id == car.group.id && group.id == 1}">
									<li><a href="#" onclick="setTerminalNumber(${car.numberTerminal})"> 
									<i aria-hidden="true" class="icon-search"></i> ${car.name } ( ${car.regNumber } )
									</a></li>
								</c:if>
							</c:forEach>
						</ul>
						</li>
				</c:if>
			</c:forEach>
				<% } %>
	
				</ul>
			</div>
		</div>
	<div id="reportBlockBottomBackground"></div>	
	<div id="reportBlockBottom">
		<table id="reportBlockBottomTable">
			<tr>
				<td>Место начала движения</td>
				<td>Место окончания движения</td>
				<td>Время начала движения</td>
				<td>Время окончания движения</td>
				<td>Время в движении</td>
				<td>Пройденный путь, км</td>
				<td>Расход по Can, л</td>
			</tr>
			<tr>
				<td>-</td>
				<td>-</td>
				<td id="startMovement">-</td>
				<td id="finishMovement">-</td>
				<td id="durationMovement">-</td>
				<td id="pathLength">-</td>
				<td id="canConsumption">-</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript"
		src="<c:url value="/resource/JS/datafield.js"/>"></script>
<!-- 		<script>LoadMap()</script> -->
	<input type="hidden" id="fieldsArray" value='${fields}'>
<%-- 	<div style="position: absolute; height: 100%; width: 100%; z-index: 100; background: white; padding: 10px;"><p>${fields}</p></div> --%>
<!-- 		<script>getFields('${fields}')</script> -->

</body>
</html>