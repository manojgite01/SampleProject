<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="context" value="<%=request.getContextPath()%>"/>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<LINK rel=stylesheet type=text/css href="components/bootstrap/css/bootstrap.min.css"> 
<link rel="icon" href="img/favicon.ico" type="image/x-icon">
<script type="text/javascript" charset="utf8" src="js/jqueryui-1.10.9.js"></script>
<script type="text/javascript" src="components/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">FreeBird Developers</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="${context}/">Home</a></li>
      <%-- <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="${context}/search.htm">Search<span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="${context}/search.htm">Search</a></li>
          <li><a href="#">Admin</a></li>
          <li><a href="#">Reports</a></li>
        </ul>
      </li> --%>
      <li><a href="${context}/search.htm">Search</a></li>
      <li><a href="${context}/manageUsers.htm">Admin</a></li>
      <li><a href="#">Reports</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="${context}/signup.htm"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
      <c:choose>
      	<c:when test="${not empty user.username and user.enabled}">
      		<li><a href="${context}/logout.htm"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
      	</c:when>
      	<c:otherwise>
      		<li><a href="${context}/login.htm"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
      	</c:otherwise>
      </c:choose>
    </ul>
  </div>
</nav>

</body>
</html>