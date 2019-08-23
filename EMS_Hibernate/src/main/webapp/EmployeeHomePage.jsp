<%@page import="com.comakeit.maven.rest_jpa.entities.*"
	import="java.time.LocalDate" import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee Home Page</title>
<style>
.link {
	margin: 0px;
	padding: 10px 10px;
	font-size: 19px;
}

table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	text-align: left;
	padding: 9px;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}
</style>
</head>
<body>

	<%
		EmployeeEntity employee = (EmployeeEntity) session.getAttribute("employee");
		List<LeaveEntity> leavesList = (List<LeaveEntity>) session.getAttribute("leavesList");
		String role = employee.getLogin().getRole().getRole_name();
		if (role.equals("employee")) {
	%>
	<div class="link" style="color: blue;">
		<%="<h2>Welcome Employee: " + employee.getEmployee_id() + " </h2>"%>
		<%="Reporting to : " + employee.getManager_id()%>
	</div>
	<div class="link" style="color: black;">
		<h2>Choose from below operations:</h2>

		<%
			String message = request.getParameter("message");
				if (message != null) {
					if (message.equals("success"))
						out.println("<h2>Leave Applied Successfully!!</h2>");
					else if (message.equals("failed"))
						out.println("<h2>Leave Application Failed!! Try Again!!</h2>");
				}
		%>
	</div>


	<div class="link" style="color: red;">
		<a href="EmployeeHomePage.jsp?action=apply">Apply For Leave</a>
	</div>
	<div class="link" style="color: red;">
		<a href="LeaveController?action=viewLeaves">View My Leave Details
		</a>
	</div>
	<div class="link" style="color: red;">
		<a href="LeaveController?action=viewLeaves&operation=cancel">Cancel
			Leave </a>
	</div>

	<div class="link" style="color: black;">
		<a href="index.jsp">Logout </a>
	</div>
	<%
		} else if (role.equals("manager")) {
	%>
	<div class="link" style="color: blue;">
		<%
			String message = request.getParameter("message");
				out.println("<h2>Welcome Manager: " + employee.getEmployee_id() + " </h2>");
				if (!employee.getManager_id().equals(employee.getEmployee_id())) {
					out.println("<h3>Reporting To" + employee.getManager_id() + "</h3>");

					if (message != null) {
						if (message.equals("success"))
							out.println("<h2>Leave Applied Successfully!!</h2>");
						else if (message.equals("failed"))
							out.println("<h2>Leave Application Failed!! Try Again!!</h2>");
					}
		%>
	</div>
	<div class="link" style="color: red;">
		<a href="EmployeeHomePage.jsp?action=apply">Apply Leave</a>
	</div>
	<%
		}
	%>
	<div class="link" style="color: red;">
		<a href="LeaveController?action=viewLeaveRequests">View Leave
			Requests</a>
	</div>
	<div class="link" style="color: red;">
		<a href="LeaveController?action=viewLeaves">View My Leave Details
		</a>
	</div>
	<div class="link" style="color: red;">
		<a href="LeaveController?action=viewLeaves&operation=cancel">Cancel
			Leave </a>
	</div>
	<div class="link" style="color: black;">
		<a href="index.jsp">Logout </a>
	</div>

	<%
		}
		String action = request.getParameter("action");
		if (action != null) {
			if (action.equals("apply")) {
	%>
	<form class="link" action="LeaveController?action=apply" method="post">
		LeaveType::<select name="LeaveType">
			<option value="casual" selected>Casual</option>
			<option value="loss_of_pay">Loss Of Pay</option>
		</select><br /> From date::<input type="date" name="start_date"
			value="<%=LocalDate.now()%>" placeholder="yyyy-MM-dd"><br />
		To date::<input type="date" name="end_date" placeholder="yyyy-MM-dd" /><br />
		Apply To <input type="text" value="<%=employee.getManager_id()%>"
			disabled="disabled"> Reason::<input type="text" name="reason" /><br />
		<input type="submit" name="Apply" />
	</form>

	<%
		} else if (action.equals("view_leaves")) {
	%>
	<table>
		<tr>
			<th>Leave Balance-Id</th>
			<th>No of Casual Leaves</th>
			<th>Loss of Pay Leaves</th>
		</tr>

		<h3>Leave Balance</h3>
		<%
			out.println("<tr><td>" + leavesList.get(0).getEmployee().getLeave_balance().getId() + "</td>");
					out.println(
							"<td>" + leavesList.get(0).getEmployee().getLeave_balance().getCasual_leaves() + "</td>");
					out.println("<td>" + leavesList.get(0).getEmployee().getLeave_balance().getLoss_of_pay()
							+ "</td></tr>");
		%>
	</table>
	<table>
		<h3>Leave Directory</h3>
		<tr>
			<th>Leave-Id</th>
			<th>Leave Type</th>
			<th>Status</th>
			<th>From Date</th>
			<th>To Date</th>
		</tr>

		<%
			for (LeaveEntity iterator : leavesList) {
						out.println("<tr><td>" + iterator.getLeave_id() + "</td>");
						out.println("<td>" + iterator.getLeave_type() + "</td>");
						out.println("<td>" + iterator.getStatus() + "</td>");
						out.println("<td>" + iterator.getFrom_date() + "</td>");
						out.println("<td>" + iterator.getTo_date() + "</td></tr>");
					}
				}

				else if (action.equals("view_leave_requests")) {
		%>
	</table>
	<table>
		<tr>
			<th>employee-Id</th>
			<th>name</th>
			<th>leave-id</th>
			<th>from_date</th>
			<th>to_date</th>
			<th>reason</th>
			<th>accept</th>
			<th>reject</th>
		</tr>
		<%
			for (LeaveEntity iterator : leavesList) {
						out.println("<tr><td>" + iterator.getEmployee().getEmployee_id() + "</td>");
						out.println("<td>" + iterator.getEmployee().getLast_name()
								+ iterator.getEmployee().getFirst_name() + "</td>");
						out.println("<td>" + iterator.getLeave_id() + "</td>");
						out.println("<td>" + iterator.getFrom_date() + "</td>");
						out.println("<td>" + iterator.getTo_date() + "</td>");
						out.println("<td>" + iterator.getReason() + "</td>");
						out.println("<td><form action=\"LeaveController?action=grantLeave&leave_id="
								+ iterator.getLeave_id() + "\" method=\"post\">");
						out.println("<input type=\"submit\" value=\"accept\"/></form></td>");

						out.println("<td><form action=\"LeaveController?action=rejectLeave&leave_id="
								+ iterator.getLeave_id() + "\" method=\"post\">");
						out.println("<input type=\"submit\" value=\"reject\"/></form></td></tr>");
					}
				} else if (action.equals("cancel_leave")) {
		%>
	</table>
	<table>
		<tr>
			<th>leave-id</th>
			<th>from_date</th>
			<th>to_date</th>
			<th>status</th>
			<th>reason</th>
			<th>cancel</th>
		</tr>
		<%
			for (LeaveEntity iterator : leavesList) {
						if (iterator.getStatus().equals("pending") || iterator.getStatus().equals("approved")
								&& iterator.getTo_date().isAfter(LocalDate.now())) {

							out.println("<tr><td>" + iterator.getLeave_id() + "</td>");
							out.println("<td>" + iterator.getFrom_date() + "</td>");
							out.println("<td>" + iterator.getTo_date() + "</td>");
							out.println("<td>" + iterator.getStatus() + "</td>");
							out.println("<td>" + iterator.getReason() + "</td>");
							out.println("<td><form action=\"LeaveController?action=cancelLeave&leave_id="
									+ iterator.getLeave_id() + "\" method=\"post\">");
							out.println("<input type=\"submit\" value=\"cancel\"/></form></td></tr>");

						}
					}
				}
			}
		%>


	</table>


</body>
</html>