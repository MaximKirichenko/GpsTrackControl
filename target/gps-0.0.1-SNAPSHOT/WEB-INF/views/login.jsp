<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<link rel="stylesheet" href="<c:url value="/resource/css/style.css"/>">
	<title>KSG Agro</title>
</head>
<body>
	<div id="languages" align="right">
		<a href="?language=en" ><spring:message code="menu.selector.english"/></a>|<a href="?language=ru" ><spring:message code="menu.selector.russian"/></a>
	</div>

	<form id="login" action="<c:url value="/j_spring_security_check"></c:url>" method="post">		
			<h1><spring:message code="authenticationForm.mainLabel"/> </h1>
			
			<fieldset id="inputs">
				<input id="username" placeholder="<spring:message code="authenticationForm.placeholder.username"/>" name='j_username' type="text">
				<input id="password" placeholder="<spring:message code="authenticationForm.placeholder.password"/>" name='j_password' type="password" value="">			
			</fieldset>
			
			<c:if test="${not empty error}">
				<div id="error">
					<spring:message code="authenticationForm.wrongAuthentication"/>
				</div>
			</c:if>
			<fieldset id="actions">
				<input id="submit" type="submit" value="<spring:message code="authenticationForm.submit.value"/>">
			</fieldset>						
	</form>
</body>