<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<%@page session="true"%>
<html>
<head>
<title>SignUp Page</title>
<LINK rel=stylesheet type=text/css href="components/bootstrap/css/bootstrap.min.css"> 
<link rel="icon" href="img/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/jquery.dataTables.min.css">
<style type="text/css">
.form-control { margin-bottom: 10px; }
</style>
<script type="text/javascript" charset="utf8" src="js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" charset="utf8" src="js/dataTables-1.10.9.js"></script>
<script type="text/javascript" charset="utf8" src="js/jqueryui-1.10.9.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="components/bootstrap/js/bootstrap.min.js"></script>
</head>
<body background="img/bgimages/bg1.jpg">
	<jsp:include page="header.jsp"></jsp:include>
	<h1>Welcome to SignUp Screen</h1>
	<form:form name="userDetailsForm" action="signup.htm" method="POST">
	<input type="hidden" name="userAction">
		<div class="container" align="center">
			 <div class="row">
		        <div class="col-xs-12 col-sm-12 col-md-4 well well-sm">
		            <legend><a href="#"><i class="glyphicon glyphicon-globe"></i></a> Sign up!</legend>
		            <div class="row">
		                <div class="col-xs-6 col-md-6">
		                    <input class="form-control" name="firstname" placeholder="First Name" type="text"
		                        required autofocus />
		                </div>
		                <div class="col-xs-6 col-md-6">
		                    <input class="form-control" name="lastname" placeholder="Last Name" type="text" required />
		                </div>
		            </div>
		            <input class="form-control" name="youremail" placeholder="Your Email" type="email" />
		            <input class="form-control" name="reenteremail" placeholder="Re-enter Email" type="email" />
		            <input class="form-control" name="password" placeholder="New Password" type="password" />
		            <label for="">
		                Birth Date</label>
		            <div class="row">
		                <div class="col-xs-4 col-md-4">
		                    <select class="form-control">
		                        <option value="Month">Month</option>
		                    </select>
		                </div>
		                <div class="col-xs-4 col-md-4">
		                    <select class="form-control">
		                        <option value="Day">Day</option>
		                    </select>
		                </div>
		                <div class="col-xs-4 col-md-4">
		                    <select class="form-control">
		                        <option value="Year">Year</option>
		                    </select>
		                </div>
		            </div>
		            <label class="radio-inline">
		                <input type="radio" name="sex" id="inlineCheckbox1" value="male" />
		                Male
		            </label>
		            <label class="radio-inline">
		                <input type="radio" name="sex" id="inlineCheckbox2" value="female" />
		                Female
		            </label>
		            <br />
		            <br />
		            <button class="btn btn-lg btn-primary btn-block" type="submit">
		                Sign up</button>
		        </div>
		    </div>
		</div>
	</form:form>
</body>
</html>