package com.comakeit.maven.rest_jpa.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "leave_balance")
public class LeaveBalanceEntity implements Serializable {

	private static final long serialVersionUID = 6104725997510934479L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int casual_leaves;
	private int loss_of_pay;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCasual_leaves() {
		return casual_leaves;
	}

	public void setCasual_leaves(int casual_leaves) {
		this.casual_leaves = casual_leaves;
	}

	public int getLoss_of_pay() {
		return loss_of_pay;
	}

	public void setLoss_of_pay(int loss_of_pay) {
		this.loss_of_pay = loss_of_pay;
	}

}
