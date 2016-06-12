<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<%@page session="true"%>
<html>
<head>
<title>Login Page</title>
<LINK rel=stylesheet type=text/css href="components/bootstrap/css/bootstrap.min.css">
<link rel="icon" href="img/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/jquery.dataTables.min.css">
<style type="text/css">
.form-control {
	margin-bottom: 10px;
}
</style>
<script type="text/javascript" charset="utf8" src="js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" charset="utf8" src="js/dataTables-1.10.9.js"></script>
<script type="text/javascript" charset="utf8" src="js/jqueryui-1.10.9.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="components/bootstrap/js/bootstrap.min.js"></script>
</head>
<body  background="img/bgimages/bg1.jpg">
	<jsp:include page="header.jsp"></jsp:include>
	<h1>Welcome to Login Screen</h1>
	<form:form name="userForm" action="login.htm" method="POST" modelAttribute="userForm">
	<input type="hidden" name="userAction">
		<div class="container">
			<div align="center">
				<c:if test="${not empty message}">
					<div class="alert alert-info">
						<strong>${message}</strong>
					</div>
				</c:if>
			</div>
			<div align="center">
			<c:if test="${not empty error}">
				<div class="alert alert-danger" id="errormsg-template">
					<strong>${error}</strong>
					 <button type="button" class="close">×</button>
				</div>
			</c:if>
		</div>
		<div align="center">
			<div class="alert alert-warning" id="alert_template" style="display: none;">
			    <button type="button" class="close">×</button>
			</div>
		</div>
		</div>
		<div class="container" align="center">
			<c:choose>
				<c:when test="${not empty user.username}">
					<h2 style="color: silver;" align="center">Welcome ${user.username}</h2>
				</c:when>
				<c:otherwise>
					<h2 style="color: silver;" align="center">Login Details</h2>
					<div class="panel panel-default" style="width: 500px;">
						<div class="panel-body">
							<div class="table-responsive">
								<c:if test="${not empty msg}">
									<div class="msg">${msg}</div>
								</c:if>
								<div class="col-xs-6 col-md-6">
									<form:input path="username" class="form-control" type="text" name="username" placeholder="Username" required="required" autofocus="true"></form:input>
								</div>
								<div class="col-xs-6 col-md-6">
									<form:input path="password" class="form-control" type="password" name="password" placeholder="Password" required="required" autofocus="true" ></form:input>
								</div>
								<input name="submit" type="submit" value="submit" id="submit"
									class="btn btn-lg btn-primary btn-block" /> <input
									type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</div>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</form:form>
</body>
<script type="text/javascript">

$(document).ready(function(){
	
		$('#alert_template .close').click(function(e) {
	        $('#alert_template').fadeOut('fast');
	    });
		
		$('#errormsg-template .close').click(function(e) {
	        $('#errormsg-template').fadeOut('fast');
	    });
	
		$("#submit").click(function(){
			var form = document.userForm;
			form.action="login.htm?userAction=autheticate";
			form.submit;
		});
	});
</script>
</html>