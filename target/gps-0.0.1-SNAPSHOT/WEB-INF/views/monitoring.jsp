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
<script type="text/javascript" src="<c:url value="/resource/JS/map.js"/>"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&callback=initMap"
	async defer></script>
	
</head>
<body>

	<div id="languages" align="right">
		<a href="?language=en"><spring:message
				code="menu.selector.english" /></a>|<a href="?language=ru"><spring:message
				code="menu.selector.russian" /></a> |<a
			href="<c:url value="/j_spring_security_logout" />"><spring:message
				code="menu.button.logout" /></a>
	</div>

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
<%-- 			<c:forEach var="enterprise" items="${listLocation}" varStatus="loop"> --%>
<%-- 				<c:if test="${enterprise.id!=0 && enterprise.id!=2}"> --%>
<!-- 					<li class="block"><input type="checkbox" name="item" -->
<%-- 						id="item${loop.index }" /> <label for="item${loop.index }"> --%>
<!-- 							<i aria-hidden="true" class="icon-users"></i> -->
<%-- 							${enterprise.enterprise } <span>${count }</span> --%>
<!-- 					</label> -->
<!-- 						<ul class="options"> -->
<%-- 							<c:forEach var="car" items="${listVehicle}"> --%>
<%-- 								<c:if test="${enterprise.id == car.enterprise.id && car.numberTerminal != 72}"> --%>
<%-- 									<li><a href="<spring:url value="/carDetails?terminalNumber=${car.numberTerminal }"/>">  --%>
<%-- 									<i aria-hidden="true" class="icon-search"></i> ${car.name } ( ${car.regNumber } ) --%>
<!-- 									</a></li> -->
<%-- 								</c:if> --%>
<%-- 							</c:forEach> --%>
<!-- 						</ul></li> -->
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>

			
		</ul>
	</div>
	<div id="map"></div>
	
</body>
</html>