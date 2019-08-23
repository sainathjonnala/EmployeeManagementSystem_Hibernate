package com.comakeit.maven.rest_jpa.controller;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import javax.servlet.RequestDispatcher;
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
import com.comakeit.maven.rest_jpa.entities.LeaveBalanceEntity;
import com.comakeit.maven.rest_jpa.entities.LoginEntity;
import com.comakeit.maven.rest_jpa.entities.RoleEntity;

public class RestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	EmployeeEntity employee;
	DepartmentEntity department;
	Client client;
	WebTarget webTarget;
	Invocation.Builder invocationBuilder;
	Response restresponse;
	RequestDispatcher requestDispatcher;
	List<EmployeeEntity> employeesList;
	RoleEntity role;
	LoginEntity login;
	LeaveBalanceEntity leaveBalance;

	public RestController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		String operation = request.getParameter("action");
		HttpSession session = request.getSession(false);
		if (operation != null)
			switch (operation) {
			case "add":

				employee = new EmployeeEntity();
				employee.setEmployee_id(request.getParameter("employee_id"));
				employee.setLast_name(request.getParameter("lastname"));
				employee.setFirst_name(request.getParameter("firstname"));
				employee.setEmail(request.getParameter("email"));
				employee.setSalary(Double.parseDouble(request.getParameter("salary")));
				employee.setManager_id(request.getParameter("reporting_manager"));
				employee.setAddress(request.getParameter("address"));

				role = new RoleEntity();
				role.setRole_id(Integer.parseInt(request.getParameter("role-id")));
				if (role.getRole_id() == 2)
					role.setRole_name("employee");
				else
					role.setRole_name("manager");

				login = new LoginEntity();
				login.setUsername(createUsername());
				login.setPassword(createPassword());
				login.setRole(role);
				employee.setLogin(login);

				department = new DepartmentEntity();
				department.setDepartment_id(request.getParameter("department_id"));
				employee.setDepartment(department);

				client = ClientBuilder.newClient(new ClientConfig());
				webTarget = client.target("http://localhost:8080/rest_jpa/webapi/employee").path("create");

				invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
				restresponse = invocationBuilder.post(Entity.entity(employee, MediaType.APPLICATION_JSON));

				if (restresponse.readEntity(String.class).equals("true")) {
					session.setAttribute("employee", employee);
					request.getRequestDispatcher("ResultsPage.jsp?result=created").forward(request, response);
				} else {
					requestDispatcher = request.getRequestDispatcher("Error.jsp?error=create");
					requestDispatcher.forward(request, response);
				}

				break;
			case "delete":
				employee = new EmployeeEntity();
				employee.setEmployee_id(request.getParameter("employee_id"));
				System.out.println(employee.getEmployee_id());
				client = ClientBuilder.newClient(new ClientConfig());
				webTarget = client.target("http://localhost:8080/rest_jpa/webapi/employee").path("delete")
						.path(employee.getEmployee_id());

				invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
				restresponse = invocationBuilder.delete();

				System.out.println(restresponse.getStatus());

				if (restresponse.readEntity(String.class).equals("true")) {
					session.setAttribute("employee", employee);
					request.getRequestDispatcher("ResultsPage.jsp?result=deleted").forward(request, response);
				} else {
					requestDispatcher = request.getRequestDispatcher("ErrorPage.jsp?error=delete");
					requestDispatcher.forward(request, response);
				}
				break;
			case "viewDepartments":
				client = ClientBuilder.newClient(new ClientConfig());
				webTarget = client.target("http://localhost:8080/rest_jpa/webapi/employee").path("viewDepartments");

				invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
				restresponse = invocationBuilder.get();
				System.out.println(restresponse.getStatus());
				GenericType<List<DepartmentEntity>> genericTypeDepartmentsList = new GenericType<List<DepartmentEntity>>() {
				};
				List<DepartmentEntity> departmentsList = restresponse.readEntity(genericTypeDepartmentsList);

				if (departmentsList != null) {
					session.setAttribute("departmentsList", departmentsList);
					request.getRequestDispatcher("ResultsPage.jsp?result=departmentsList").forward(request, response);
				}

				break;
			case "viewEmployees":
				client = ClientBuilder.newClient(new ClientConfig());
				webTarget = client.target("http://localhost:8080/rest_jpa/webapi/employee").path("viewEmployees");

				invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
				restresponse = invocationBuilder.get();

				GenericType<List<EmployeeEntity>> genericTypeEmloyeeList = new GenericType<List<EmployeeEntity>>() {
				};
				List<EmployeeEntity> employeesList = restresponse.readEntity(genericTypeEmloyeeList);
				if (employeesList != null) {
					session.setAttribute("employeeList", employeesList);
					request.getRequestDispatcher("ResultsPage.jsp?result=viewEmployees").forward(request, response);

				}
				break;
			case "viewEmployeeDetails":

				employee = new EmployeeEntity();
				employee.setEmployee_id(request.getParameter("employee_id"));

				client = ClientBuilder.newClient(new ClientConfig());
				webTarget = client.target("http://localhost:8080/rest_jpa/webapi/employee").path("viewEmployeeDetails");

				invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
				restresponse = invocationBuilder.post(Entity.entity(employee, MediaType.APPLICATION_JSON));

				employee = restresponse.readEntity(EmployeeEntity.class);

				if (employee != null) {
					session.setAttribute("employee", employee);
					request.getRequestDispatcher("ResultsPage.jsp?result=employeeDetails").forward(request, response);

				} else {
					requestDispatcher = request.getRequestDispatcher("ErrorPage.jsp?error=viewEmployeeDetails");
					requestDispatcher.forward(request, response);
				}

				break;
			case "viewEmployeesofSpecificManager":
				employee = new EmployeeEntity();

				employee.setManager_id(request.getParameter("reportingManager"));
				client = ClientBuilder.newClient(new ClientConfig());
				webTarget = client.target("http://localhost:8080/rest_jpa/webapi/employee")
						.path("viewEmployeesofSpecificManager");

				invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
				restresponse = invocationBuilder.post(Entity.entity(employee, MediaType.APPLICATION_JSON));
				System.out.println(restresponse.getStatus());
				GenericType<List<EmployeeEntity>> genericTypeList = new GenericType<List<EmployeeEntity>>() {
				};
				employeesList = restresponse.readEntity(genericTypeList);
				System.out.println(employeesList);

				if (employeesList != null) {
					session.setAttribute("employee", employee);
					session.setAttribute("employeeList", employeesList);
					request.getRequestDispatcher("ResultsPage.jsp?result=viewEmployeesofSpecificManager")
							.forward(request, response);
				} else {
					requestDispatcher = request
							.getRequestDispatcher("ErrorPage.jsp?error=viewEmployeesofSpecificManager");
					requestDispatcher.forward(request, response);
				}

				break;
			case "listEmployeesBySalary":

				double salaryFrom = Double.parseDouble(request.getParameter("from"));
				double salaryTo = Double.parseDouble(request.getParameter("to"));

				client = ClientBuilder.newClient(new ClientConfig());
				webTarget = client.target("http://localhost:8080/rest_jpa/webapi/employee")
						.path("listEmployeesBySalary").path(String.valueOf(salaryFrom)).path(String.valueOf(salaryTo));
				invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
				restresponse = invocationBuilder.get();

				GenericType<List<EmployeeEntity>> listEmployeesBySalary = new GenericType<List<EmployeeEntity>>() {
				};
				employeesList = restresponse.readEntity(listEmployeesBySalary);

				if (employeesList != null) {
					session.setAttribute("employeeList", employeesList);
					requestDispatcher = request.getRequestDispatcher("ResultsPage.jsp?result=listEmployeesBySalary");
					requestDispatcher.forward(request, response);
				} else {
					requestDispatcher = request.getRequestDispatcher("ErrorPage.jsp?error=listEmployeesBySalary");
					requestDispatcher.forward(request, response);
				}

				break;
			}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	public static String createUsername() {
		return "UN" + new SecureRandom().nextInt() % 100000;
	}

	public static String createPassword() {
		return "PW" + new SecureRandom().nextInt() % 100000;
	}

}
