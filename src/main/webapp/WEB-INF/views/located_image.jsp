<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<link rel="stylesheet" href="<c:url value="/resource/css/jquery-ui.css"/>">
	<link rel="stylesheet" href="<c:url value="/resource/css/home_page.css"/>">

<!-- 	<script src="http://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyD1fO8NBDPstlRrT0VDv7gVfsO19CTVV4g"></script>   -->
    <script src="http://maps.google.com/maps/api/js?v=3&key=AIzaSyD1fO8NBDPstlRrT0VDv7gVfsO19CTVV4g&sensor=false"></script> 
	<script src="<c:url value="/resource/JS/jquery-1.11.3.js"/>"></script>
	<script src="<c:url value="/resource/JS/jquery-ui.js"/>"></script>
	
	<script src="<c:url value="/resource/JS/OpenLayers.js"/>"></script>
	<script src="<c:url value="/resource/JS/ExtendedRenderers.js"/>"></script>
	<script src="<c:url value="/resource/JS/located_image/locatedImageCtrl1.js"/>"></script>
	<script src="<c:url value="/resource/JS/service/dateMaintainingService.js"/>"></script>
	<script src="<c:url value="/resource/JS/canvasjs/canvasjs.min.js"/>"></script>
	<title>KSG Agro</title>
</head>
<body>
<div class="page-container" >
	<div id="image_container">
		<div id="image_container-content">

		</div>
		<div id="image_container-button-box">
			<input type='button' class='button' id='image_container-submit' value='ok'/>
		</div>
	</div>
	<div class="header-panel">
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
					<li><a href="<spring:url value="/located/image"/>">Загрузка картинок</a></li>
					<li><a href="<spring:url value="/logout"/>">Выход</a></li>
				</ul>
			</div>
		</div>
	</div>
		<div class="left-col">
			<input type="checkbox" name="showCheckbox" value="showCheckbox"> Показывать выделенное
			<div id="cars_select_menu">
				<div id="image_links">
					<ul>
						<c:forEach var="item" items="${images_info}">
							<li><a id="${item.key}" class="image_item">${item.name}</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	<div class="right-col">
		<div>
			File to upload: <input id="imageUpload" type="file" name="image">
			<input type="submit" value="Upload" id="uploadButton">
		</div>
		<div id="map"></div>
	</div>
</div>
</body>
</html>