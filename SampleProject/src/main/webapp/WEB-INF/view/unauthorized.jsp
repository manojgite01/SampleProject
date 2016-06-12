<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="context" value="<%=request.getContextPath()%>"/>
<html>
<body background="img/bgimages/bg1.jpg">
	<jsp:include page="header.jsp"></jsp:include>
	<c:url value="/login.htm" var="messageUrl" />
	<h1 style="color: silver;">HTTP Status 403 - Access is denied</h1>

	<c:choose>
		<c:when test="${empty user.username}">
			<h2 style="color: silver;">You do not have permission to access this page!</h2>
			<b><a href="${messageUrl}">Click to return</a></b>
		</c:when>
		<c:otherwise>
			<h2 style="color: silver;">Username : ${user.username} <br/>You do not have permission to access this page!</h2>
			<b><a href="${messageUrl}">Click to return</a></b>
		</c:otherwise>
	</c:choose>
</body>
</html>