<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html ng-app="trackApp" ng-init="dateFrom = '${dateFrom }'; dateTo = '${dateTo }'; 
timeFrom = '${timeFrom }'; timeTo = '${timeTo }'; terminalNumber = ${terminalNumber };">
<head>
<title>KSG Agro</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<link rel="stylesheet" href="<c:url value="/resource/css/track.css"/>">

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.1/angular.min.js"></script>


</head>
<body ng-controller="trackCtrl" >
<div id="empty">Трекер отключен. Последний сигнал ${date}</div>
<div id="back-button"><a href="/gps">BACK</a></div>		
<div id="map"></div>

	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&callback=initMap"
		async defer></script>
</body>
</html>