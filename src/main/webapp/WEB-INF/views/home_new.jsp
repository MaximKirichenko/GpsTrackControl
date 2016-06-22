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

	
	<script src="http://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyD1fO8NBDPstlRrT0VDv7gVfsO19CTVV4g">
     </script> 
     
	<script src="<c:url value="/resource/JS/jquery-1.11.3.js"/>"></script>
	<script src="<c:url value="/resource/JS/jquery-ui.js"/>"></script>
	
	<script src="<c:url value="/resource/JS/OpenLayers.js"/>"></script>
	<script src="<c:url value="/resource/JS/ExtendedRenderers.js"/>"></script>
	<script src="<c:url value="/resource/JS/service/mapService.js"/>"></script>
	<title>KSG Agro</title>
</head>
<body>
	<div class="page-container" >
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
				<li><a href="<spring:url value="/logout"/>">Выход</a></li> 
			</ul>
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
		<div class="left-col">
			<div id="cars_select_menu">
				<div id="accordion">
  <h3>Section 1</h3>
  <div>
    <ul class="accordion_menu">
    	<li><input type="checkbox" name="item1">item 1</li>
    	<li><input type="checkbox" name="item1">item 2</li>
    	<li><input type="checkbox" name="item1">item 3</li>
    	<li><input type="checkbox" name="item1">item 4</li>
    	<li><input type="checkbox" name="item1">item 5</li>
    	<li><input type="checkbox" name="item1">item 1</li>
    	<li><input type="checkbox" name="item1">item 6</li>
    	<li><input type="checkbox" name="item1">item 7</li>
    	<li><input type="checkbox" name="item1">item 8</li>
    	<li><input type="checkbox" name="item1">item 9</li>
    	<li><input type="checkbox" name="item1">item 10</li>
    	<li><input type="checkbox" name="item1">item 11</li>
    	<li><input type="checkbox" name="item1">item 12</li>
    	<li><input type="checkbox" name="item1">item 13</li>
    	<li><input type="checkbox" name="item1">item 14</li>
    	<li><input type="checkbox" name="item1">item 15</li>
    </ul>
  </div>
  <h3>Section 2</h3>
  <div>
    <ul class="accordion_menu">
    	<li><input type="checkbox" name="item1">item 1</li>
    	<li><input type="checkbox" name="item1">item 2</li>
    	<li><input type="checkbox" name="item1">item 3</li>
    	<li><input type="checkbox" name="item1">item 4</li>
    	<li><input type="checkbox" name="item1">item 5</li>
    </ul>
  </div>
  <h3>Section 3</h3>
  <div>
    <ul class="accordion_menu">
    	<li><input type="checkbox" name="item1">item 1</li>
    	<li><input type="checkbox" name="item1">item 2</li>
    	<li><input type="checkbox" name="item1">item 3</li>
    	<li><input type="checkbox" name="item1">item 4</li>
    	<li><input type="checkbox" name="item1">item 5</li>
    </ul>
  </div>
  <h3>Section 4</h3>
  <div>
    <ul class="accordion_menu">
    	<li><input type="checkbox" name="item1">item 1</li>
    	<li><input type="checkbox" name="item1">item 2</li>
    	<li><input type="checkbox" name="item1">item 3</li>
    	<li><input type="checkbox" name="item1">item 4</li>
    	<li><input type="checkbox" name="item1">item 5</li>
    </ul>
  </div>
</div>
			</div>
		</div>
		<div class="right-col">
			<div id="tabs">
  				<ul>
    				<li><a href="#tabs-1">Карта</a></li>
    				<li><a href="#tabs-2">Отчет по выбранным</a></li>
  				</ul>
  				<div id="tabs-1">
    				<div id="map"></div>
  				</div>
  				<div id="tabs-2">
    				<h1>Report content</h1>
  				</div>
			</div>
		</div>
		
		
<!-- 		<div class="clear"></div> -->
	</div>
</body>
</html>