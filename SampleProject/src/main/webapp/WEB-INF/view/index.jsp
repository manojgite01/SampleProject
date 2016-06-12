<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="context" value="<%=request.getContextPath()%>"/>

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
		<LINK rel=stylesheet type=text/css href="components/bootstrap/css/bootstrap.css"> 
		<link rel="icon" href="img/favicon.ico" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
		<link rel="stylesheet" href="css/jquery.dataTables.min.css">
		<script type="text/javascript" charset="utf8" src="components/jquery/jquery-2.1.1.js"></script>
		<script type="text/javascript" charset="utf8" src="js/dataTables-1.10.9.js"></script>
		<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="components/bootstrap/js/bootstrap.js"></script>
	</head> 
	<body background="img/bgimages/bg1.jpg">
		<jsp:include page="header.jsp"></jsp:include>
		<c:url value="/login.htm" var="messageUrl" />
		<div align="center" style="margin-top: 100px;">
			<h1><b><a href="${messageUrl}">Click to enter</a></b></h1>
		</div>
	</body>
</html>
