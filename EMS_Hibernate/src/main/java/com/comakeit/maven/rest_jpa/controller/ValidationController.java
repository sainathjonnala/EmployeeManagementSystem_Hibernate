package com.comakeit.maven.rest_jpa.controller;

import java.io.IOException;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.comakeit.maven.rest_jpa.entities.EmployeeEntity;
import com.comakeit.maven.rest_jpa.entities.LoginEntity;

public class ValidationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	RequestDispatcher requestDispatcher = null;
	Client client;
	WebTarget webTarget;
	Invocation.Builder invocationBuilder;
	Response restresponse;
	EmployeeEntity employee;

	public ValidationController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		response.setContentType("text/html");
		LoginEntity loginCredentials = new LoginEntity();
		loginCredentials.setUsername(request.getParameter("username"));
		loginCredentials.setPassword(request.getParameter("password"));

		client = ClientBuilder.newClient(new ClientConfig());
		webTarget = client.target("http://localhost:8080/rest_jpa/webapi/login").path("validate");

		invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		restresponse = invocationBuilder.post(Entity.entity(loginCredentials, MediaType.APPLICATION_JSON));

		loginCredentials = restresponse.readEntity(LoginEntity.class);

		if (loginCredentials != null) {
			String role = loginCredentials.getRole().getRole_name();
			System.out.println(role);
			if (role.equals("admin")) {
				session.setAttribute("loginCredentials", loginCredentials);
				requestDispatcher = request.getRequestDispatcher("AdminOperationsHomePage.jsp");
				requestDispatcher.forward(request, response);
			} else {

				client = ClientBuilder.newClient(new ClientConfig());
				webTarget = client.target("http://localhost:8080/rest_jpa/webapi/login").path("findUser");

				invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

				restresponse = invocationBuilder.post(Entity.entity(loginCredentials, MediaType.APPLICATION_JSON));

				employee = restresponse.readEntity(EmployeeEntity.class);
				session.setAttribute("employee", employee);
				request.getRequestDispatcher("EmployeeHomePage.jsp").forward(request, response);

			}

		} else {
			request.getRequestDispatcher("ErrorPage.jsp?error=invalidlogin").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
