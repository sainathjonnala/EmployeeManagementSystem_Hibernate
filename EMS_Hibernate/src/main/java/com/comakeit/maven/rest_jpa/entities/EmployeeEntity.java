package com.comakeit.maven.rest_jpa.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "employees")
public class EmployeeEntity implements Serializable {

	private static final long serialVersionUID = 9073467595802370918L;
	@Id
	@Column(length = 15)
	private String employee_id;
	@Column(length = 15)
	private String first_name;
	@Column(length = 15)
	private String last_name;
	@Column(length = 20)
	private String address;
	@Column(length = 30)
	private String email;

	@Column(length = 20)
	private String manager_id;

	@Column(precision = 10, scale = 2)
	private double salary;
	@Transient
	private double PF;

	@ManyToOne
	private DepartmentEntity department;

	@OneToOne(cascade = { CascadeType.ALL })
	private LeaveBalanceEntity leave_balance;

	@OneToOne(cascade = { CascadeType.ALL })
	private LoginEntity login;

	public LeaveBalanceEntity getLeave_balance() {
		return leave_balance;
	}

	public void setLeave_balance(LeaveBalanceEntity leave_balance) {
		this.leave_balance = leave_balance;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public DepartmentEntity getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LoginEntity getLogin() {
		return login;
	}

	public void setLogin(LoginEntity login) {
		this.login = login;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public double getPF() {
		return PF;
	}

	public void setPF(double pF) {
		PF = pF;
	}

	public String getManager_id() {
		return manager_id;
	}

	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}

}
