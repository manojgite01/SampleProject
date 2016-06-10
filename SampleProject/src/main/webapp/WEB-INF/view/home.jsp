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
	<body>
	<div class="container">
		<div align="center" style="margin-top: 100px;">
			<c:choose>
				<c:when test="${not empty failureMsg}">
					<div class="alert alert-danger">
						<strong>${failureMsg}</strong>
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-info">
						<strong>${message}</strong>
					</div>
				</c:otherwise>
			</c:choose>
			
			<div class="alert alert-warning" id="alert_template" style="display: none;">
			    <button type="button" class="close">×</button>
			</div>
		</div>
	</div>
	<form:form id="employeeForm" name="employeeForm" action="home.htm" method="post" modelAttribute="employeeForm">
		<div class="container">
		  <h2>Employee Search Screen</h2>
		  <div class="panel panel-default">
		    <div class="panel-body">
			    <div align="center">
					<form:label path="id">Employee ID:</form:label>
					<form:input id="id" name="id" path="id"/>
					<input data-toggle="tooltip" title="Click to get employee details!" data-placement="bottom" type="submit" class="btn btn-default" value="Get Details" onclick="return validateForm();">
				</div>
			</div>
		  </div>
		</div>
		<div class="container">
		  <h2>Employee Search Details</h2>
			<div class="panel panel-default">
			    <div class="panel-body">
					 <div class="table-responsive">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>Employee Name</th>
									<th>Role</th>
									<th>Joining Date</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${employeeForm.name}</td>
									<td>${employeeForm.role}</td>
									<td>${employeeForm.createdOn}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
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