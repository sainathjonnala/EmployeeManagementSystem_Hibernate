package com.comakeit.maven.rest_jpa.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.comakeit.maven.rest_jpa.JPA.LoginValidationJPA;
import com.comakeit.maven.rest_jpa.entities.EmployeeEntity;
import com.comakeit.maven.rest_jpa.entities.LoginEntity;

@Path("login")
public class LoginResource {
	@POST
	@Path("/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LoginEntity validateAdmininstrator(LoginEntity loginCredentials) {
		LoginValidationJPA loginValidationJPA = new LoginValidationJPA();
		return loginValidationJPA.verifyUser(loginCredentials);
	}

	@POST
	@Path("/findUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EmployeeEntity findUser(LoginEntity loginCredentials) {
		return new LoginValidationJPA().findUser(loginCredentials);
	}

}
