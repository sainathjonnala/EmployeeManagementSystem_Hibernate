package com.comakeit.maven.rest_jpa.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class RoleEntity implements Serializable {

	private static final long serialVersionUID = 3880486014750768712L;

	@Id
	@Column(length = 4)
	private int role_id;

	@Column(length = 8)
	private String role_name;

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

}
