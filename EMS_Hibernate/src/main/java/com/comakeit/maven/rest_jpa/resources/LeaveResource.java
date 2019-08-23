package com.comakeit.maven.rest_jpa.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.comakeit.maven.rest_jpa.JPA.LeaveJPA;
import com.comakeit.maven.rest_jpa.entities.EmployeeEntity;
import com.comakeit.maven.rest_jpa.entities.LeaveEntity;

@Path("leave")
public class LeaveResource {
	@POST
	@Path("/applyLeave")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean applyLeave(LeaveEntity leaveApplication) {

		boolean b = new LeaveJPA().applyLeave(leaveApplication);
		System.out.println(b);
		return b;
	}

	@POST
	@Path("/viewLeaves")
	@Produces(MediaType.APPLICATION_JSON)
	public List<LeaveEntity> viewLeaves(EmployeeEntity employee) {
		return new LeaveJPA().getLeavesOfEmployee(employee);
	}

	@POST
	@Path("/leaveRequests")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<LeaveEntity> getLeaveRequests(EmployeeEntity employee) {
		return new LeaveJPA().getLeaveRequests(employee);
	}

	@PUT
	@Path("/grantLeave")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean acceptLeave(LeaveEntity leave_application) {
		return new LeaveJPA().acceptLeave(leave_application);
	}

	@PUT
	@Path("/rejectLeave")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean rejectLeave(LeaveEntity leave_application) {
		return new LeaveJPA().rejectLeave(leave_application);
	}
	@PUT
	@Path("/cancelLeave")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean cancelLeave(LeaveEntity leave_application) {
		return new LeaveJPA().cancelLeave(leave_application);
	}

}
