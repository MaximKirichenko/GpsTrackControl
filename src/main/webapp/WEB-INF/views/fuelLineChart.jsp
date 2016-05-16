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



<script type="text/javascript"
	src="<c:url value="/resource/JS/Chart.js"/>"></script>

<script type="text/javascript"
	src="<c:url value="/resource/JS/jquery-1.11.3.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resource/JS/jquery-ui.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resource/JS/buttonController.js"/>"></script>
<script src="<c:url value="/resource/JS/service/dateMaintainingService.js"/>"></script>
<script src="<c:url value="/resource/JS/showPopupWindow.js"/>"></script>
<script src="<c:url value="/resource/JS/hidePopupWindow.js"/>"></script>
<script src="<c:url value="/resource/JS/service/vehicleClickMaintainingService.js"/>"></script>
<script src="<c:url value="/resource/JS/controller/chartPageController.js"/>"></script>



</head>
<body>
<input id="terminalNumber" type="hidden" name="vehicleId"
				value="${vehicleId}" />
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
				<li><a href="<spring:url value="/adminPage"/>">Администрирование</a></li> 
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
								<c:if test="${group.id == car.group.id && car.id != 72}">
									<li><a href="#" onclick="setTerminalNumber(${car.id})"> 
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
								<c:if test="${group.id == car.group.id && (group.id == 1 || group.id == 4)}">
									<li><a href="#" onclick="setTerminalNumber(${car.id})"> 
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
		<div id="chart">
			<div id="fuelChartDescription">
				
				<input id="rightTankCheckbox" type="checkbox" name="rightTank" checked/>
				<img alt="" src="<c:url value="/resource/blue_line_png.png"/>"/>
				<label>- Топливо в правом баке</label>
				
				<input id="leftTankCheckbox"  type="checkbox" name="leftTank"/>
				<img alt="" src="<c:url value="/resource/green_line_png.png"/>"/>
				<label>- Топливо в левом баке</label>
				
				<input id="engineeCheckbox" type="checkbox" name="enginee"/>
				<img alt="" src="<c:url value="/resource/red_line_png.png"/>"/>
				<label>- Обороты</label>
		
				<input id="speedCheckbox" type="checkbox" name="speed" checked/>
				<img alt="" src="<c:url value="/resource/yelow_line.png"/>"/>
				<label>- Скорость</label>
				
				
				<input id="voltageCheckbox" type="checkbox" name="voltage"/>
				<img alt="" src="<c:url value="/resource/purple_line.png"/>"/>
				<label>- Напряжение</label>
				
			</div>
			<canvas id="fuelChart"></canvas>
		
			<div id="reportBlock">
				<table id="reportTable">
					<thead>
						<td>№</td>
						<td>Остаток до, л</td>
						<td>Остаток после, л</td>
						<td>Заправка/Слив, л</td>
						<td>Дата начала</td>
						<td>Дата окончания</td>
						<td>Израсходованно по ДУТ, л</td>
						<td>Израсходованно по CAN, л</td>
					</thead>
					<tbody>
					</tbody>
				</table>
			<label id="LabelConsumptionFromCan"></label>
			</div>
		</div>

	<script type="text/javascript"
		src="<c:url value="/resource/JS/datafield.js"/>"></script>
</body>
</html>