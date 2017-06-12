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
<h1>Договора заключенные в 2016 году</h1>
<table class="table-fill">
	<tr>
		<td>№ п/п</td>
		<td>ID</td>
		<td>ФИО</td>
		<td>Дата заключения</td>
		<td>Дата окончания</td>
		<td>Кадастровый №</td>
		<td>Площадь м.кв.</td>
	</tr>
	<c:set var="area" value="0"/>
	<c:forEach var="contract" items="${contracts}" varStatus="loop">
	<c:set var="openContract" value="${fn:substring(contract.open, 0, 10) } "/>
	<c:set var="closeContract" value="${fn:substring(contract.close, 0, 10) } "/>
		<c:set var="area" value="${area + contract.pay.area }"/>
		<tr>
			<td>${loop.index + 1}</td>
			<td>${contract.id}</td>
			<td>${contract.emp.name} ${contract.emp.surname}</td>
			<td>${openContract}</td>
			<td>${closeContract}</td>
			<td>${contract.pay.nameR}</td>
			<td>${contract.pay.area}</td>
		</tr>
	</c:forEach>
	<tr>
			<td>Итого: </td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td>${area}</td>
		</tr>
</table>
	
</body>
</html>