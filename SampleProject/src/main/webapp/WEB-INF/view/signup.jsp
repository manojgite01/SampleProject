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
	<form:form name="userDetailsForm" action="signup.htm" method="POST" modelAttribute="userDetailsForm">
	<input type="hidden" name="userAction">
		<div class="container" align="center">
			 <div class="row">
		        <div class="col-xs-12 col-sm-12 col-md-4 well well-sm">
		            <legend><a href="#"><i class="glyphicon glyphicon-globe"></i></a> Sign up!</legend>
		            <div class="row">
		                <div class="col-xs-6 col-md-6">
		                    <form:input path="fname" class="form-control" name="fname" id="fname" placeholder="First Name" type="text" required="required" autofocus="autofocus" ></form:input>
		                </div>
		                <div class="col-xs-6 col-md-6">
		                    <form:input path="lname" class="form-control" name="lname" id="lname" placeholder="Last Name" type="text" required="required" autofocus="autofocus" />
		                </div>
		            </div>
		            <form:input path="username" class="form-control" name="username" id="username" placeholder="Username" type="text" required="required" autofocus="autofocus"/>
		            <form:input path="email" class="form-control" name="email" id="email" placeholder="Your Email" type="email" required="required" autofocus="autofocus"/>
		            <form:input path="password" class="form-control" name="password" id="password" placeholder="New Password" type="password" required="required" autofocus="autofocus"/>
		            <input class="form-control" name="repassword" id="repassword" placeholder="Re-enter Password" type="password" required="required" autofocus="autofocus"/>
		            <!-- <label for="">
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
		            </div> -->
		            <label class="radio-inline">
		                <form:radiobutton path="gender" name="gender" id="inlineCheckbox1" value="M" />
		                Male
		            </label>
		            <label class="radio-inline">
		                <form:radiobutton path="gender" name="gender" id="inlineCheckbox2" value="F" />
		                Female
		            </label>
		            <br />
		            <br />
		            <button class="btn btn-lg btn-primary btn-block" type="submit" id="signUpBtn">Sign up</button>
		        </div>
		    </div>
		</div>
	</form:form>
</body>
<script type="text/javascript">
$(document).ready(function (){
	$('#alert_template .close').click(function(e) {
        $('#alert_template').fadeOut('fast');
    });
	
	$('#errormsg-template .close').click(function(e) {
        $('#errormsg-template').fadeOut('fast');
    });
	
	$("#signUpBtn").click(function(){
		if(validateForm()){
			var form = document.userDetailsForm;
			form.userAction.value="createUser";
			form.submit;
		}else{
			return false;
		}
	});
});

function validateForm(){
	if($("#password").val() != $("#repassword").val()){
		$("#alert_template span").remove();
		$("#alert_template button").after('<span>Please enter same password.</span>');
		$('#alert_template').fadeIn('slow');
		return false;
	}
	return true;
}
</script>
</html>