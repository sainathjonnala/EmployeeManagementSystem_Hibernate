package com.comakeit.maven.rest_jpa.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "departments")
public class DepartmentEntity implements Serializable {

	private static final long serialVersionUID = -334972747348463225L;
	@Id
	@Column(length = 15)
	private String department_id;
	@Column(length = 15)
	private String department_name;

	public String getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

}
