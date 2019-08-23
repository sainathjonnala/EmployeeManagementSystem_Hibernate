package com.comakeit.maven.rest_jpa.JPA;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import com.comakeit.maven.rest_jpa.entities.DepartmentEntity;
import com.comakeit.maven.rest_jpa.entities.EmployeeEntity;
import com.comakeit.maven.rest_jpa.entities.LeaveBalanceEntity;
import com.comakeit.maven.rest_jpa.entities.LeaveEntity;
import com.comakeit.maven.rest_jpa.entities.LoginEntity;
import com.comakeit.maven.rest_jpa.entities.RoleEntity;

public class LoginValidationJPA {
	private static EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory("employee_management_persistence");
	private static EntityManager entityManager = entityManagerFactory.createEntityManager();

	public LoginEntity verifyUser(LoginEntity loginCredentials) throws PersistenceException {
		try {
			loginCredentials = entityManager.find(LoginEntity.class, loginCredentials.getUsername());
			if (loginCredentials != null) {
				return loginCredentials;
			}
		} catch (PersistenceException exception) {
			exception.getMessage();
		}
		return null;
	}

	public EmployeeEntity findUser(LoginEntity loginCredentials) {
		try {
			System.out.println("in find user" + loginCredentials.getUsername());
			if (entityManager != null) {
				Query query = entityManager
						.createQuery("select e from EmployeeEntity e " + "where e.login.username=:username",
								EmployeeEntity.class)
						.setParameter("username", loginCredentials.getUsername());
				EmployeeEntity employee = (EmployeeEntity) query.getResultList().get(0);
				return employee;
			}

		} catch (PersistenceException exception) {
			exception.getMessage();
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}

		return null;
	}

}
