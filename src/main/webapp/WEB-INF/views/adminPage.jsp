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
</body>
</html>