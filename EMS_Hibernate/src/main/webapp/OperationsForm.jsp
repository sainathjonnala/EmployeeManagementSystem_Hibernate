<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Operations</title>
<script type="text/javascript">
	function myfunction(email) {
		var mailformat = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/
		if (email.value.match(mailformat)) {
			return true
		} else {
			document.getElementById("demo").innerHTML = " Enter email in correct format "
			return false
		}
	}
</script>

</head>
<body>

	<%
		String action = request.getParameter("action");
		if (action.equals("create")) {
	%>

	<div style="padding-left: 50px; font-family: monospace;">
		<p>Employee Details:</p>
		<br />
		<p id="demo"></p>
		<h2>Create User</h2>
		<form action="RestController?action=add" method="POST"
			onsubmit="return myfunction(email)">
			<div style="width: 100px; text-align: left;">
				<div style="padding: 10px;">
					Employee-ID: <input name="employee_id" />
				</div>

				<div style="padding: 10px;">
					LastName: <input name="lastname" />
				</div>
				<div style="padding: 10px;">
					FirstName: <input name="firstname" />
				</div>
				<div style="padding: 10px;">
					Email-ID: <input name="email" />
				</div>
				<div style="padding: 10px;">
					Address: <input name="address" />
				</div>
				<div style="padding: 10px;">
					Department-ID: <input name="department_id" />
				</div>
				<div style="padding: 10px;">
					Salary: <input name="salary" />
				</div>
				<div style="padding: 10px;">
					Reporting-Manager-id: <input name="reporting_manager" />
				</div>
				<div style="padding: 10px;">
					Role-Id: <input name="role-id" />
				</div>
				<div style="padding: 20px; text-align: center">
					<input type="submit" name="operation" value="add" />
				</div>
			</div>
		</form>
	</div>


	<%
		} else if (action.equals("delete")) {
	%>
	<form action="RestController?action=delete" method="post">
		<p>Enter the Employee-ID to delete from records:</p>
		<div style="width: 100px; text-align: left;">

			<div style="padding: 10px;">
				Employee-ID: <input name="employee_id" /> <input type="submit"
					value="delete" />
			</div>
		</div>
	</form>


	<%
		} else if (action.equals("viewEmployeesofSpecificManager")) {
	%>
	<form action="RestController?action=viewEmployeesofSpecificManager"
		method="post">
		<p>Enter the Reporting Manager:</p>
		<div style="width: 100px; text-align: left;">
			<div style="padding: 10px;">
				Reporting Manager: <input name="reportingManager" /> <input
					type="submit" value="view" />
			</div>
		</div>
	</form>


	<%
		} else if (action.equals("viewEmployeeDetails")) {
	%>
	<form action="RestController?action=viewEmployeeDetails" method="post">
		<p>Enter the EmployeeID :</p>
		<div style="width: 100px; text-align: left;">
			<div style="padding: 10px;">
				Employee-ID: <input name="employee_id" /> <input type="submit"
					value="view" />
			</div>
		</div>
	</form>


	<%
		}else if(action.equals("listEmployeesBySalary")){
	%>
	<form action="RestController?action=listEmployeesBySalary"
		method="post">
		<p>Enter the Range :</p>
		<div style="width: 100px; text-align: left;">
			<div style="padding: 10px;">
				Salary From: <input name="from" /> To:<input name="to" /> <input
					type="submit" value="view" />
			</div>
		</div>
	</form>


	<% } %>
</body>
</html>