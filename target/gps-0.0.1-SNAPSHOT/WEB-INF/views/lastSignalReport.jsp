<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/resource/css/OpenLayers.css"/>">
    <link rel="stylesheet" href="<c:url value="/resource/css/car-info-table.css"/>">

    <script src="<c:url value="/resource/JS/jquery-1.11.3.js"/>"></script>
    <script src="<c:url value="/resource/JS/OpenLayers.js"/>"></script>
    <script src="<c:url value="/resource/JS/Controller.js"/>"></script>
    <title>Kadastr maps</title>

</head>
<body>
<h1>Отчет по активности</h1>

<table class="table-fill">
	<tr>
		<td>№ п/п</td>
		<td>Модель</td>
		<td>гос.номер</td>
		<td>дата последней активности</td>

	</tr>
	<c:forEach var="cars" items="${cars}" varStatus="loop">
		<tr>
			<td>${loop.index + 1}</td>
			<td>${cars.name}</td>
			<td>${cars.regNumber}</td>
			<td>${cars.message.toLocaleString()}</td>
		</tr>
	</c:forEach>
</table>

</body>
</html>