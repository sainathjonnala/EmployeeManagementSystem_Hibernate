<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.comakeit.maven.rest_jpa.entities.LoginEntity"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Administrator Operations</title>
</head>
<body>
	<%
		LoginEntity loginCredentials = (LoginEntity) session.getAttribute("loginCredentials");
	%>
	<form>
		<div
			style="margin: 0px; padding: 10px 50px; font-size: 20px; color: blue;">
			<%="<h2>Welcome Administrator: " + loginCredentials.getUsername() + " </h2>"%>
		</div>
		<div
			style="margin: 0px; padding: 10px 50px; font-size: 20px; color: black;">
			<h3>Choose from below operations:</h3>
		</div>
		<div
			style="margin: 0px; padding: 10px 50px; font-size: 20px; color: red;">
			<a href="OperationsForm.jsp?action=create">Add Employee </a>
		</div>

		<div
			style="margin: 0px; padding: 10px 50px; font-size: 20px; color: red;">
			<a href="OperationsForm.jsp?action=delete">Delete Employee</a>
		</div>
		<div
			style="margin: 0px; padding: 10px 50px; font-size: 20px; color: red;">

			<a href="RestController?action=viewEmployees">View Employees</a>
		</div>
		<div
			style="margin: 0px; padding: 10px 50px; font-size: 20px; color: red;">

			<a href="RestController?action=viewDepartments">View Departments</a>
		</div>
		<div
			style="margin: 0px; padding: 10px 50px; font-size: 20px; color: red;">

			<a href="OperationsForm.jsp?action=viewEmployeesofSpecificManager">View
				Employees of specific Manager</a>
		</div>
		<div
			style="margin: 0px; padding: 10px 50px; font-size: 20px; color: red;">

			<a href="OperationsForm.jsp?action=viewEmployeeDetails">View
				Employee Details and PF</a>
		</div>
		<div
			style="margin: 0px; padding: 10px 50px; font-size: 20px; color: red;">

			<a href="OperationsForm.jsp?action=listEmployeesBySalary">View
				Employees By Salary Range</a>
		</div>
		<div
			style="margin: 0px; padding: 10px 50px; font-size: 20px; color: red;">

			<a href="index.jsp">Logout</a>
		</div>
	</form>
</body>
</html>


