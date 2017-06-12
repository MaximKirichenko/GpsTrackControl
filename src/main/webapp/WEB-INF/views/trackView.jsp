<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/resource/css/OpenLayers.css"/>">
    <link rel="stylesheet" href="<c:url value="/resource/css/jquery-ui.css"/>">
	<link rel="stylesheet" href="<c:url value="/resource/css/trackView.css"/>">

    <script src="<c:url value="/resource/JS/jquery-1.11.3.js"/>"></script>
    <script src="<c:url value="/resource/JS/OpenLayers.js"/>"></script>
    <script src="<c:url value="/resource/JS/ExtendedRenderers.js"/>"></script>
    <script src="<c:url value="/resource/JS/Controller.js"/>"></script>
    <script src="<c:url value="/resource/JS/jquery-ui.js"/>"></script>
    <title>Kadastr maps</title>

</head>
<body onload="loadSuccess()">

<div class = "input-block">
    <input class = "input search" id="searchField" type="text" placeholder="6521555100:07:049:0017" name="searchField" />
    <input class = "input" id="terminalNumber" type="hidden" name="terminalNumber" value="${numberTerminal}" /> 
    <input class = "input data" id="datepickerFrom" placeholder="<spring:message code="carDetailsForm.Label.BuildReport.From"/>" type="text" name="dateFrom" value="" /> 
    <input class = "input data" id="datepickerTo" placeholder="<spring:message code="carDetailsForm.Label.BuildReport.To" />" type="text" name="dateTo" /> 
    <input class = "input time" id="timeFrom" placeholder="<spring:message code="carDetailsForm.Label.BuildReport.TimeFormatFrom" />" name="timeFrom" type="text" name="timeFrom" /> 
    <input class = "input time" id="timeTo" placeholder="<spring:message  code="carDetailsForm.Label.BuildReport.TimeFormatTo" />" name="timeTo" type="text" name="timeTo" />
	<button class = "button ph-btn-blue" onclick="sendTimeInterval()">Показать</button>
</div>

<!-- Менюшка выбора машины -->
<div id="CarSelectedMenu">
		<ul>
				<c:forEach var="group" items="${listGroup}" varStatus="loop">
				<c:if test="${enterprise.id!=0 && enterprise.id!=2}">
					<li class="block"><input type="checkbox" name="item"
						id="item${loop.index }" /> <label for="item${loop.index }">
							<i aria-hidden="true" class="icon-users"></i>
							${group.groupName } <span>${count }</span>
					</label>
						<ul class="options">
							<c:forEach var="car" items="${listVehicle}">
								<c:if test="${group.id == car.group.id && car.numberTerminal != 72 && car.numberTerminal != 65}">
									<li><a href="<spring:url value="trackPage?terminalNumber=${car.numberTerminal }"/>"> 
									<i aria-hidden="true" class="icon-search"></i> ${car.name } ( ${car.regNumber } )
									</a></li>
								</c:if>
							</c:forEach>
						</ul>
						</li>
				</c:if>
		</c:forEach>			
	</ul>
</div>

<div id="map" style="position: absolute; top: 0px; right: 0px; bottom: 0px; left: 0px;  z-index: 0"></div>
<div style="position: absolute; bottom: 50px; right: 10px;  z-index: 10; background-color: #208b09; opacity: 0.8;">
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
<div id="info">
<label id = "lengthLabel" style="position: absolute; bottom: 50px; left: 10px;  z-index: 10; background-color: #208b09; opacity: 0.8;">Длина пути: 0</label>
</div>
<script>LoadMap()</script>
<script>setPoint('${terminalDate}')</script>
<script>getFields('${fields}')</script>

<script type="text/javascript"
				src="<c:url value="/resource/JS/datafield.js"/>"></script>

</body>
</html>