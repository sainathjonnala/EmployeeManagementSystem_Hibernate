<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.comakeit.maven.rest_jpa.entities.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Results Page</title>
<style>
table {
	display: table;
	border-collapse: separate;
	border-spacing: 2px;
	border-color: grey;
}

th {
	color: #E4F1F6;
	text-align: left;
	padding: 8px;
	background-color: *#034F85*;
}

td {
	text-align: left;
	padding: 8px;
	background-color: #1A3E4C;
	color: white;
}

tr {
	color: black;
}
</style>

<script>
	function goBack() {
		window.history.back();
	}
</script>

</head>
<body>
	<%
		String result = request.getParameter("result");

		EmployeeEntity employee = (EmployeeEntity) session.getAttribute("employee");

		List<EmployeeEntity> employeeList = (ArrayList<EmployeeEntity>) session.getAttribute("employeeList");
		List<DepartmentEntity> departmentsList = (ArrayList<DepartmentEntity>) session
				.getAttribute("departmentsList");
	%>
	<table style="width: 100%">
		<%
			if (result.equals("created")) {
		%>
		<%="Employee Created " + employee.getEmployee_id()%>
		<div
			style="margin: 0px; padding: 10px 50px; font-size: 20px; color: blue;">
		<a href="AdminOperationsHomePage.jsp">Goto HomePage</a>
		</div>
		<%
			} else if (result.equals("deleted")) {
		%>
		<%="Employee Deleted " + employee.getEmployee_id()%>
		<a href="AdminOperationsHomePage.jsp">Goto HomePage</a>
		<%
			} else if (result.equals("viewEmployees")) {
				out.println("<h2>Employees Directory</h2>");
				out.println("<th>Employee ID </th><th>Employee Name</th><th>Department ID</th>");
				for (EmployeeEntity iterator : employeeList) {
					out.println("<tr><td>" + iterator.getEmployee_id() + "</td><td>" + iterator.getLast_name() + " "
							+ iterator.getFirst_name() + "</td><td>" + iterator.getDepartment().getDepartment_id()
							+ "</td></tr>");

				}
		%>
		<a href="javascript:goBack()">go back</a>
		<%
			} else if (result.equals("departmentsList")) {
				out.println("<h2>Departments List</h2>");
				out.println("<th>Department ID</th><th>Department Name</th>");
				for (DepartmentEntity iterator : departmentsList) {
					out.println("<tr><td>" + iterator.getDepartment_id() + "</td><td>" + iterator.getDepartment_name()
							+ "</td></tr>");
				}

			} else if (result.equals("viewEmployeesofSpecificManager")) {
				out.println("<h3>Reporting Manager: " + employee.getManager_id() + "</h3>");
				out.println("<th>Employee ID </th><th>Employee Name</th><th>Department ID</th>");
				for (EmployeeEntity iterator : employeeList) {
					out.println("<tr><td>" + iterator.getEmployee_id() + "</td><td> " + iterator.getLast_name() + " "
							+ iterator.getFirst_name() + "</td><td>" + iterator.getDepartment().getDepartment_id()
							+ "</td></tr>");
				}
			} else if (result.equals("employeeDetails")) {
		%>
		<%="<h2>Employee Details"%>
		<%="<th>Employee ID</th><th>Employee Name</th><th>Department ID</th><th>Employee Salary</th><th>PF</th><th>Reporting TO</th>"%>
		<%="<tr><td>" + employee.getEmployee_id() + "</td><td>" + employee.getLast_name() + " "
						+ employee.getFirst_name() + "</td><td>" + employee.getDepartment().getDepartment_id()
						+ "</td><td>" + employee.getSalary() + "</td><td>" + employee.getPF() + "</td><td>"
						+ employee.getManager_id() + "</td></tr>"%>

		<%
			} else if (result.equals("listEmployeesBySalary")) {
				out.println("<h3>Employees List</h3>");
				out.println("<th>Employee ID </th><th>Employee Name</th><th>Employee Salary</th>");
				for (EmployeeEntity iterator : employeeList) {
					out.println("<tr><td>" + iterator.getEmployee_id() + "</td><td> " + iterator.getLast_name() + " "
							+ iterator.getFirst_name() + "</td><td>" + iterator.getSalary() + "</td></tr>");
				}
			}
		%>
	</table>
</body>
</html>