<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
		<h2>${empName} :: ${message}</h2>
	<form:form id="employeeForm" name="employeeForm" action="home.htm" method="post" modelAttribute="employeeForm">
	<%-- <c:url value="/home.htm" var="messageUrl" />
		<a href="${messageUrl}">Click to enter</a> --%>
		<form:label path="id">Employee ID:</form:label>
		<form:input id="id" name="id" path="id"/>
		<input type="submit" value="Get Details">
		<div>
		</div>
	</form:form>
	</body>
</html>