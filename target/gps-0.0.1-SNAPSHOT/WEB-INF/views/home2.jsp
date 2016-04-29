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
    <script src="<c:url value="/resource/JS/ControllerHome.js"/>"></script>
    <script src="<c:url value="/resource/JS/jquery-ui.js"/>"></script>
    <title>Kadastr maps</title>

</head>
<body onload="loadSuccess()">
<div class = "input-block">
    <input class = "input search" id="searchField" type="text" placeholder="6521555100:07:049:0017" name="searchField" />
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
									<li><a href="<spring:url value="/carDetails?terminalNumber=${car.numberTerminal }"/>"> 
									<i aria-hidden="true" class="icon-search"></i> ${car.name } ( ${car.regNumber } )
									</a></li>
								</c:if>
							</c:forEach>
						</ul>
						</li>
				</c:if>
			</c:forEach>
			<li class="block">
			<input type="checkbox" name="item" id="5"/>
			<label for="5">
				<i aria-hidden="true" class="icon-users"></i>Отчеты</label>
				<ul class="options">
					<li><a href="<spring:url value="/closeContract"/>"> 
					<i aria-hidden="true" class="icon-search"></i> Заканчивается срок договора в 2016
									</a></li>
					<li><a href="<spring:url value="/openContract"/>"> 
					<i aria-hidden="true" class="icon-search"></i>Договор заключен в 2016
									</a></li>
					<li><a href="<spring:url value="/kadastrReport"/>"> 
					<i aria-hidden="true" class="icon-search"></i>Отчет по паям
									</a></li>
					<li><a href="<spring:url value="/carActiveReport"/>"> 
					<i aria-hidden="true" class="icon-search"></i>Отчет по активности
									</a></li>				
				</ul>
			</li>
			<li class="block">
			<input type="checkbox" name="item" id="6"/>
			<label for="6">
				<i aria-hidden="true" class="icon-users"></i>Обработка полей</label>
				<ul class="options">
					<li><a href="<spring:url value="/closeContract"/>"> 
					<i aria-hidden="true" class="icon-search"></i> Удобрение
									</a></li>
				</ul>
			</li>
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

<script>LoadMap()</script>
<!-- <script>setPoint('${terminalDate}')</script> -->
<script>getFields('${fields}')</script>

<script type="text/javascript"
				src="<c:url value="/resource/JS/datafield.js"/>"></script>

</body>
</html>