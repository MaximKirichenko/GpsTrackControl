<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>

<title>KSG Agro</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet"
	href="<c:url value="/resource/css/details-form.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resource/css/car-info-table.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resource/css/jquery-ui.css"/>">

<script type="text/javascript"
	src="<c:url value="/resource/JS/jquery-1.11.3.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resource/JS/jquery-ui.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resource/JS/buttonController.js"/>"></script>

</head>
<body>
	<div id="languages">
		<a href="?terminalNumber=${car.numberTerminal }&language=en"><spring:message
				code="menu.selector.english" /></a>|<a
			href="?terminalNumber=${car.numberTerminal }&language=ru"><spring:message
				code="menu.selector.russian" /></a> |<a
			href="<c:url value="/j_spring_security_logout" />"><spring:message
				code="menu.button.logout" /></a>
	</div>
	<div id="carDetailsForm" align="center">
		<h1>
			<spring:message code="carDetailsForm.Label.Title" />
		</h1>
		<!-- 		Information from sensor -->
		<div id="infoField">
			<h3>
				<spring:message code="carDetailsForm.Label.SensorInfo" />
			</h3>
			<table class="table-fill">
				<tbody class="table-hover">
					<tr>
						<td class="text-left"><spring:message
								code="carDetailsForm.Label.InputVoltage" /></td>
						<td class="text-left">${inputVoltage } V</td>
					</tr>
					<tr>
						<td class="text-left"><spring:message
								code="carDetailsForm.Label.VoltageOnDevice" /></td>
						<td class="text-left">${voltageOnDevice } V</td>
					</tr>
					<tr>
						<td class="text-left"><spring:message
								code="carDetailsForm.Label.LeftTanks" /></td>
						<td class="text-left">${leftTanks } л.</td>
					</tr>
					<tr>
						<td class="text-left"><spring:message
								code="carDetailsForm.Label.RightTanks" /></td>
						<td class="text-left">${rightTanks } л.</td>
					</tr>
					<tr>
						<td class="text-left"><spring:message
								code="carDetailsForm.Label.TotalFuel" /></td>
						<td class="text-left">${ totalFuel} л.</td>
					</tr>
<!-- 					<tr> -->
<%-- 						<td class="text-left"><spring:message --%>
<%-- 								code="carDetailsForm.Label.ConsumptionCan" /></td> --%>
<%-- 						<td class="text-left">${canFuelLevel  } л.</td> --%>
<!-- 					</tr> -->
					<tr>
						<td class="text-left"><spring:message
								code="carDetailsForm.Label.CanFuelLevel" /></td>
						<td class="text-left">${consumptionCan  } %</td>
					</tr>
					<tr>
						<td class="text-left"><spring:message
								code="carDetailsForm.Label.EngineSpeed" /></td>
						<td class="text-left">${engineSpeed  }</td>
					</tr>
					<tr>
						<td class="text-left"><spring:message
								code="carDetailsForm.Label.Speed" /></td>
						<td class="text-left">${speed } км/ч</td>
					</tr>
				</tbody>

			</table>
		</div>
<!-- 		Info about car -->
		<div id="infoField">
			<h3>
				<spring:message code="carDetailsForm.Label.CarInfo" />
			</h3>
			<table class="table-fill">
				<tbody class="table-hover">
					<tr>
						<td class="text-left"><spring:message
								code="carDetailsForm.Label.NumberTerminal" /></td>
						<td class="text-left">${car.numberTerminal }</td>
					</tr>
					<tr>
						<td class="text-left"><spring:message
								code="carDetailsForm.Label.StatusTerminal" /></td>
						<td class="text-left">${statusTerminal }</td>
					</tr>
					<tr>
						<td class="text-left"><spring:message
								code="carDetailsForm.Label.CarModel" /></td>
						<td class="text-left">${car.name }</td>
					</tr>
					<tr>
						<td class="text-left"><spring:message
								code="carDetailsForm.Label.RegNumber" /></td>
						<td class="text-left">${car.regNumber }</td>
					</tr>
					<tr>
						<td class="text-left"><spring:message
								code="carDetailsForm.Label.Group" /></td>
						<td class="text-left">${car.group.groupName  }</td>
					</tr>
					<tr>
						<td class="text-left"><spring:message
								code="carDetailsForm.Label.Enterprise" /></td>
						<td class="text-left">${car.enterprise.enterprise  }</td>
					</tr>
				</tbody>

			</table>
			<a id="button" href="/gps/chart/fuel?terminalNumber=${car.numberTerminal}"><spring:message code="carDetailsForm.Button.Chart" /></a>
			<a id="button" href="/gps/report/trackPage?terminalNumber=${car.numberTerminal}"><spring:message code="carDetailsForm.Button.Track" /></a>
		</div>
		
	</div>
	
	<div id="map"></div>
	<script type="text/javascript"
		src="<c:url value="/resource/JS/datafield.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resource/JS/map.js"/>"></script>
	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&callback=initMap"
		async defer></script>
</body>
</html>