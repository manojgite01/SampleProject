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
	<div align="center" style="margin-top: 100px;">
		<c:choose>
			<c:when test="${not empty failureMsg}">
				<h2 style="color: red;">${failureMsg}</h2>
			</c:when>
			<c:otherwise>
				<h2 style="color: green;">${empName} :: ${message}</h2>
			</c:otherwise>
		</c:choose>
	</div>
	<form:form id="employeeForm" name="employeeForm" action="home.htm" method="post" modelAttribute="employeeForm">
		<div align="center" style="margin-top: 100px;">
			<form:label path="id">Employee ID:</form:label>
			<form:input id="id" name="id" path="id"/>
			<input type="submit" value="Get Details" onclick="return validateForm();">
		</div>
	</form:form>
	</body>
	<script type="text/javascript">
		function validateForm(){
			var form = document.employeeForm;
			var empId = document.getElementById("id").value;
			if(isNaN(empId)){
				alert("Please enter correct employee id.");
				return false;
			}
			return true;
		}
	</script>
</html>