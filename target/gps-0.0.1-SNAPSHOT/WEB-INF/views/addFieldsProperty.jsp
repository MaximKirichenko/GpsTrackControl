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
<script type="text/javascript" src="http://scriptjava.net/source/scriptjava/scriptjava.js"></script>
<script type="text/javascript"
	src="<c:url value="/resource/JS/mapRedactor.js"/>"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&callback=initMap"
	async defer></script>
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

</head>
<body>

	<div id="languages" align="right">
		<a href="?language=en"><spring:message
				code="menu.selector.english" /></a>|<a href="?language=ru"><spring:message
				code="menu.selector.russian" /></a> |<a
			href="<c:url value="/j_spring_security_logout" />"><spring:message
				code="menu.button.logout" /></a>
	</div>
	
		<input id="location" type="text"
			value="Административно-территориальное назначение"> <input
			id="target" type="text" value="Целевое назначение"> <input
			id="cipher" type="text" value="Шифр">
		<button type="button" onclick="sendData()">Добавить</button>
</body>
</html>