<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="context" value="${pageContext.request.contextPath}" />

<html>
	<head>
		<meta charset="utf-8">
		<title>Manage Users</title>
		<LINK rel=stylesheet type=text/css href="components/bootstrap/css/bootstrap.min.css"> 
		<link rel="icon" href="img/favicon.ico" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
		<link rel="stylesheet" href="css/jquery.dataTables.min.css">
		<script type="text/javascript" charset="utf8" src="js/jquery-1.10.1.min.js"></script>
		<script type="text/javascript" charset="utf8" src="js/dataTables-1.10.9.js"></script>
		<script type="text/javascript" charset="utf8" src="js/jqueryui-1.10.9.js"></script>
		<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="components/bootstrap/js/bootstrap.min.js"></script>
	</head> 
	<body background="img/bgimages/bg1.jpg">
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<div align="center">
			<c:if test="${not empty message}">
				<div class="alert alert-info">
					<strong>${message}</strong>
				</div>
			</c:if>
		</div>
		<div align="center">
			<c:if test="${not empty failureMsg}">
				<div class="alert alert-danger">
					<strong>${failureMsg}</strong>
				</div>
			</c:if>
		</div>
		<div align="center">
			<div class="alert alert-warning" id="alert_template" style="display: none;">
			    <button type="button" class="close">×</button>
			</div>
		</div>
	</div>
	<form:form id="employeeForm" name="employeeForm" action="search.htm" method="post" modelAttribute="userDetailsForm">
		<div class="container">
		  <h2 style="color: silver;">Manage Users Screen</h2>
		</div>
		<c:if test="${not empty usersList}">
		<div class="container">
		  <h2 style="color: silver;">User Details</h2>
			<div class="panel panel-default">
			    <div class="panel-body">
					 <div class="table-responsive">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>First Name</th>
									<th>Last Name</th>
									<th>Username</th>
									<th>Email</th>
									<th>Gender</th>
									<th>Activation Status</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="user" items="${usersList }">
									<tr>
										<td>${user.userDetails.fname}</td>
										<td>${user.userDetails.lname}</td>
										<td>${user.username}</td>
										<td>${user.userDetails.email}</td>
										<td>${user.userDetails.gender}</td>
										<td>${user.enabled}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		</c:if>
	</form:form>
	</body>
	<script type="text/javascript">
	
		$(document).ready(function(){
		    $('[data-toggle="tooltip"]').tooltip(); 
		    
		    $('#alert_template .close').click(function(e) {
		        $('#alert_template').fadeOut('fast');
		    });
		});
		function validateForm(){
			var form = document.employeeForm;
			var empId = document.getElementById("id").value;
			if(isNaN(empId)){
				$("#alert_template span").remove();
				$("#alert_template button").after('<span>Please enter correct Emploee ID.</span>');
				$('#alert_template').fadeIn('slow');
				//alert("Please enter correct employee id.");
				return false;
			}
			return true;
		}
	</script>
</html>