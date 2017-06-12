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
	<link rel="stylesheet" href="<c:url value="/resource/css/car-info-table.css"/>">
	<link rel="stylesheet" href="<c:url value="/resource/css/jquery-ui.css"/>">
	
	<script src="<c:url value="/resource/JS/jquery-1.11.3.js"/>"></script>
	<script src="<c:url value="/resource/JS/jquery-ui.js"/>"></script>
	<script src="<c:url value="/resource/JS/service/dateMaintainingService.js"/>"></script>
	<script src="<c:url value="/resource/JS/controller/reportPageController.js"/>"></script>
</head>
<body>
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
			<li class="block">
			<input type="checkbox" name="item" id="5"/>
			<label for="5">
				<i aria-hidden="true" class="icon-users"></i>Отчеты</label>
				<ul class="options">
					<li onclick="setReportId(1)"><a href="#"> 
					<i aria-hidden="true" class="icon-search"></i> Груповой отчет
									</a></li>
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
		</div>
		<div id="progressbar"><div class="progress-label">Loading...</div></div>
		<div id="reportLayer"></div>
		<script type="text/javascript"
		src="<c:url value="/resource/JS/datafield.js"/>"></script>

</body>
</html>