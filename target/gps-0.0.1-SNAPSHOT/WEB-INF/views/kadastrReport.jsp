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
<h1>Отчет по паям</h1>
<p>Всего паев: ${totalAmount }</p>
<table class="table-fill">
	<tr>
		<td>№ п/п</td>
		<td>Наименование филиала</td>
		<td>Площадь полей, ГА</td>

	</tr>
	<c:forEach var="firms" items="${firmsArea}" varStatus="loop">
		<tr>
			<td>${loop.index + 1}</td>
			<td>${firms.name}</td>
			<td>${firms.area}</td>

		</tr>
	</c:forEach>
	<tr>
			<td>Итого: </td>
			<td></td>
			<td>${totalArea }</td>
		</tr>
</table>

<p>Общая площадь может не совпадать, т.к. она считается по сумме площадей указанных во всех действующих
контрактах. Если один контракт не анулирован но уже открыт новый на данный пай, то соответственно площадь по данному паю будет удвоенна.</p>
<p> Ниже приведенна таблица где перечислены все паи, на которые у нас более одного действующего контракта</p>
<table class="table-fill">
	<tr>
		<td>п/п</td>
		<td>Кадастровый номер</td>
		<td>Предприятие</td>
		<td>Тип контракта</td>
		<td>Статус</td>
		<td>ФИО</td>
		<td>Дата начала</td>
		<td>Дата окончания</td>
		<td>Площадь</td>

	</tr>
	<c:forEach var="с" items="${notNullContract}" varStatus="loop">
		<tr>
			<td>${loop.index + 1}</td>
			<td>${с.kadastr}</td>
			<td>${с.firmsName}</td>
			<td>${с.contractType}</td>
			<td>${с.status}</td>
			<td>${с.fio}</td>
			<td>${с.open}</td>
			<td>${с.close}</td>
			<td>${с.area}</td>
		</tr>
	</c:forEach>

</table>

<!-- notNullContract	 -->
</body>
</html>