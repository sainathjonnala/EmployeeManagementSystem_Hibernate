package com.comakeit.maven.rest_jpa.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "leave_application")
public class LeaveEntity implements Serializable {
	private static final long serialVersionUID = 6282510532443312302L;

	@Id
	@Column(length = 15)
	private String leave_id;

	@Column(length = 15)
	private String leave_type;

	private LocalDate from_date;

	private LocalDate to_date;

	@Column(length = 50)
	private String reason;

	@Column(length = 10)
	private String status;

	@Column(length = 20)
	private String apply_to;
	@ManyToOne//(cascade = CascadeType.ALL)
	private EmployeeEntity employee;

	public String getLeave_id() {
		return leave_id;
	}

	public void setLeave_id(String leave_id) {
		this.leave_id = leave_id;
	}

	public String getLeave_type() {
		return leave_type;
	}

	public void setLeave_type(String leave_type) {
		this.leave_type = leave_type;
	}

	public LocalDate getFrom_date() {
		return from_date;
	}

	public void setFrom_date(LocalDate from_date) {
		this.from_date = from_date;
	}

	public LocalDate getTo_date() {
		return to_date;
	}

	public void setTo_date(LocalDate to_date) {
		this.to_date = to_date;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public EmployeeEntity getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeEntity employee) {
		this.employee = employee;
	}

	public String getApply_to() {
		return apply_to;
	}

	public void setApply_to(String apply_to) {
		this.apply_to = apply_to;
	}

}
