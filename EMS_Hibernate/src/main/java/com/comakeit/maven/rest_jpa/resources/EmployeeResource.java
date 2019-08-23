package com.comakeit.maven.rest_jpa.resources;

import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.comakeit.maven.rest_jpa.JPA.EmployeeJPA;
import com.comakeit.maven.rest_jpa.entities.DepartmentEntity;
import com.comakeit.maven.rest_jpa.entities.EmployeeEntity;

@Path("employee")
public class EmployeeResource {
	EmployeeEntity employee;
	DepartmentEntity department;
	EmployeeJPA employeeJPA = new EmployeeJPA();

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createEmployee(EmployeeEntity employee) {
		return employeeJPA.createEmployee(employee);
	}

	@DELETE
	@Path("/delete/{employee_id}")
	@Produces(MediaType.APPLICATION_JSON)

	public boolean deleteEmployee(@PathParam("employee_id") String employee_id) {
		employee = new EmployeeEntity();
		employee.setEmployee_id(employee_id);
		return employeeJPA.deleteEmployee(employee);
	}

	@GET
	@Path("/viewDepartments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DepartmentEntity> viewDepartments() {
		return employeeJPA.viewDepartments();

	}

	@GET
	@Path("/viewEmployees")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EmployeeEntity> viewEmployees() {
		return employeeJPA.getEmployees();

	}

	@POST
	@Path("/viewEmployeeDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public EmployeeEntity viewEmployeeDetails(EmployeeEntity employee) {
		employee = employeeJPA.getEmployeeDetails(employee);
		employee.setPF(employee.getSalary() * 0.05);
		return employee;
	}

	@POST
	@Path("/viewEmployeesofSpecificManager")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EmployeeEntity> viewEmployeesofSpecificManager(EmployeeEntity employee) {
		return employeeJPA.getEmployeesofSpecificManager(employee);

	}

	@GET
	@Path("listEmployeesBySalary/{from}/{to}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EmployeeEntity> listEmployeesBySalary(@PathParam("from") double salaryFrom,
			@PathParam("to") double salaryTo) {
		return employeeJPA.getEmployeeListBySalary(salaryFrom, salaryTo);
	}

}