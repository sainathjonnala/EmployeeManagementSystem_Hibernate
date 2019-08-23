package com.comakeit.maven.rest_jpa.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.comakeit.maven.rest_jpa.entities.DepartmentEntity;
import com.comakeit.maven.rest_jpa.entities.EmployeeEntity;
import com.comakeit.maven.rest_jpa.entities.LeaveEntity;
import com.mysql.cj.Session;

public class LeaveController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	EmployeeEntity employee;
	LeaveEntity leave_application;
	Client client;
	WebTarget webTarget;
	Invocation.Builder invocationBuilder;
	Response restresponse;
	List<LeaveEntity> leavesList;

	public LeaveController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		HttpSession session = request.getSession(false);
		employee = (EmployeeEntity) session.getAttribute("employee");

		if (action.equals("apply")) {
			leave_application = new LeaveEntity();
			leave_application.setEmployee(employee);
			leave_application.setLeave_type(request.getParameter("LeaveType"));
			leave_application.setFrom_date(LocalDate.parse(request.getParameter("start_date")));
			leave_application.setTo_date(LocalDate.parse(request.getParameter("end_date")));
			leave_application.setReason(request.getParameter("reason"));
			leave_application.setApply_to(employee.getManager_id());
			leave_application.setStatus("pending");

			client = ClientBuilder.newClient(new ClientConfig());
			webTarget = client.target("http://localhost:8080/rest_jpa/webapi/leave").path("applyLeave");

			invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

			restresponse = invocationBuilder.post(Entity.entity(leave_application, MediaType.APPLICATION_JSON));

			if (restresponse.readEntity(String.class).equals("true")) {
				request.getRequestDispatcher("EmployeeHomePage.jsp?message=success").forward(request, response);
			} else {
				request.getRequestDispatcher("EmployeeHomePage.jsp?message=failed").forward(request, response);
			}

		} else if (action.equals("viewLeaves")) {
			
			String operation = request.getParameter("operation");
			
			client = ClientBuilder.newClient(new ClientConfig());
			webTarget = client.target("http://localhost:8080/rest_jpa/webapi/leave").path("viewLeaves");

			invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

			restresponse = invocationBuilder.post(Entity.entity(employee, MediaType.APPLICATION_JSON));
			GenericType<List<LeaveEntity>> genericTypeList = new GenericType<List<LeaveEntity>>() {
			};
			leavesList = restresponse.readEntity(genericTypeList);

			if (leavesList != null) {
				session.setAttribute("leavesList", leavesList);
				if(operation != null && operation.equals("cancel"))
				{
					request.getRequestDispatcher("EmployeeHomePage.jsp?action=cancel_leave").forward(request, response);
				}
				else {
				request.getRequestDispatcher("EmployeeHomePage.jsp?action=view_leaves").forward(request, response);
				}
			}
		} else if (action.equals("viewLeaveRequests")) {

			client = ClientBuilder.newClient(new ClientConfig());
			webTarget = client.target("http://localhost:8080/rest_jpa/webapi/leave").path("leaveRequests");

			invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

			restresponse = invocationBuilder.post(Entity.entity(employee, MediaType.APPLICATION_JSON));

			GenericType<List<LeaveEntity>> genericTypeList = new GenericType<List<LeaveEntity>>() {
			};

			leavesList = restresponse.readEntity(genericTypeList);
			if (leavesList != null) {
				session.setAttribute("leavesList", leavesList);
				request.getRequestDispatcher("EmployeeHomePage.jsp?action=view_leave_requests").forward(request,
						response);
			}
		} else if (action.equals("grantLeave")) {

			leave_application = new LeaveEntity();
			leave_application.setLeave_id(request.getParameter("leave_id"));

			webTarget = client.target("http://localhost:8080/rest_jpa/webapi/leave").path("grantLeave");

			invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

			restresponse = invocationBuilder.put(Entity.entity(leave_application, MediaType.APPLICATION_JSON));

			if (restresponse.readEntity(String.class).equals("true")) {
				request.getRequestDispatcher("LeaveController?action=viewLeaveRequests").forward(request, response);
			}

		} else if (action.equals("rejectLeave")) {

			leave_application = new LeaveEntity();
			leave_application.setLeave_id(request.getParameter("leave_id"));
			webTarget = client.target("http://localhost:8080/rest_jpa/webapi/leave").path("rejectLeave");

			invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

			restresponse = invocationBuilder.put(Entity.entity(leave_application, MediaType.APPLICATION_JSON));
			String isLeaveRejected = restresponse.readEntity(String.class);
			if (isLeaveRejected.equals("true")) {
				request.getRequestDispatcher("LeaveController?action=viewLeaveRequests").forward(request, response);
			}
		}
		else if (action.equals("cancelLeave")) {

			leave_application = new LeaveEntity();
			leave_application.setLeave_id(request.getParameter("leave_id"));
			webTarget = client.target("http://localhost:8080/rest_jpa/webapi/leave").path("cancelLeave");

			invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

			restresponse = invocationBuilder.put(Entity.entity(leave_application, MediaType.APPLICATION_JSON));
			String isLeaveCancelled = restresponse.readEntity(String.class);
			if (isLeaveCancelled.equals("true")) {
				request.getRequestDispatcher("LeaveController?action=viewLeaves&operation=cancel").forward(request, response);
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
