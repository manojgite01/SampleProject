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
	<form:form name="userForm" action="login.htm" method="POST">
	<input type="hidden" name="userAction">
		<div class="container" align="center">
			<h2 style="color: silver;" align="center">Login Details</h2>
			<div class="panel panel-default" style="width: 500px;">
				<div class="panel-body">
					<div class="table-responsive">
						<c:if test="${not empty error}">
							<div class="error">${error}</div>
						</c:if>
						<c:if test="${not empty msg}">
							<div class="msg">${msg}</div>
						</c:if>
						<div class="col-xs-6 col-md-6">
							<input class="form-control" type='text' name='username'
								placeholder="Username" required autofocus>
						</div>
						<div class="col-xs-6 col-md-6">
							<input class="form-control" type='password' name='password'
								placeholder="Password" required autofocus />
						</div>
						<input name="submit" type="submit" value="submit" id="submit"
							class="btn btn-lg btn-primary btn-block" /> <input
							type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</div>
				</div>
			</div>
		</div>
	</form:form>
</body>
<script type="text/javascript">

$(document).ready(function(){
		alert("submit clicked");
		var form = document.userForm;
		form.userAction.value="autheticate";
		form.action="login.htm";
		form.submit;
	});
});

</script>
</html>