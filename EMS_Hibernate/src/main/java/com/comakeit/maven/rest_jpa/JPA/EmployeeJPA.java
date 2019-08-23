package com.comakeit.maven.rest_jpa.JPA;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import com.comakeit.maven.rest_jpa.entities.DepartmentEntity;
import com.comakeit.maven.rest_jpa.entities.EmployeeEntity;
import com.comakeit.maven.rest_jpa.entities.LeaveBalanceEntity;
import com.comakeit.maven.rest_jpa.entities.LeaveEntity;

public class EmployeeJPA {
	DepartmentEntity department;
	EmployeeEntity employee;
	List<EmployeeEntity> employeesList;
	LeaveBalanceEntity leaveBalance;
	List<LeaveEntity> leavesList;

	private EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory("employee_management_persistence");
	private EntityManager entityManager = entityManagerFactory.createEntityManager();

	public boolean createEmployee(EmployeeEntity employee) throws PersistenceException {
		try {
			if (entityManager != null) {
				entityManager.getTransaction().begin();

				department = entityManager.find(DepartmentEntity.class, employee.getDepartment().getDepartment_id());

				employee.setDepartment(department);

				leaveBalance = new LeaveBalanceEntity();
				leaveBalance.setCasual_leaves(10);
				leaveBalance.setLoss_of_pay(10);
				employee.setLeave_balance(leaveBalance);

				entityManager.persist(employee);

				return true;

			} else
				entityManager.getTransaction().rollback();

		} catch (PersistenceException exception) {
			exception.getMessage();
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
			entityManagerFactory.close();
		}
		return false;
	}

	public boolean deleteEmployee(EmployeeEntity employee) throws PersistenceException {
		if (entityManager != null) {
			try {

				entityManager.getTransaction().begin();
				employee = entityManager.find(EmployeeEntity.class, employee.getEmployee_id());
				if (employee != null) {
					leavesList = entityManager
							.createQuery("SELECT l from LeaveEntity l where l.employee.employee_id = :employee_id ",
									LeaveEntity.class)
							.setParameter("employee_id", employee.getEmployee_id()).getResultList();
					for (LeaveEntity leave : leavesList) {
						entityManager.remove(leave);
					}
					entityManager.remove(employee);
					return true;
				}

			} catch (PersistenceException exception) {
				exception.getMessage();
			} finally {
				entityManager.getTransaction().commit();
				entityManager.close();
				entityManagerFactory.close();
			}
		}
		return false;
	}

	public List<EmployeeEntity> getEmployees() throws PersistenceException {

		if (entityManager != null) {
			try {
				employeesList = entityManager.createQuery("SELECT e FROM EmployeeEntity e", EmployeeEntity.class)
						.getResultList();
				if (!employeesList.isEmpty())
					return employeesList;
			} catch (PersistenceException exception) {
				exception.getMessage();
			} finally {

				entityManager.close();
				entityManagerFactory.close();
			}
		}
		return null;
	}

	public List<DepartmentEntity> viewDepartments() throws PersistenceException {
		if (entityManager != null) {
			try {
				List<DepartmentEntity> departmentsList = entityManager
						.createQuery("SELECT d from DepartmentEntity d", DepartmentEntity.class).getResultList();
				return departmentsList;
			} catch (PersistenceException exception) {
				exception.getMessage();
			} finally {

				entityManager.close();
				entityManagerFactory.close();
			}

		}
		return null;
	}

	public List<EmployeeEntity> getEmployeesofSpecificManager(EmployeeEntity employee) throws PersistenceException {
		if (entityManager != null) {
			try {
				employeesList = entityManager
						.createQuery("select e from EmployeeEntity e where e.manager_id = :reporting_manager",
								EmployeeEntity.class)
						.setParameter("reporting_manager", employee.getManager_id()).getResultList();

				if (!employeesList.isEmpty())
					return employeesList;
			} catch (PersistenceException exception) {
				exception.getMessage();
			}

			finally {
				entityManager.close();
				entityManagerFactory.close();
			}
		}
		return null;
	}

	public EmployeeEntity getEmployeeDetails(EmployeeEntity employee) throws PersistenceException {
		if (entityManager != null) {
			try {

				employee = entityManager.find(EmployeeEntity.class, employee.getEmployee_id());
				if (employee != null) {
					return employee;
				}

			} catch (PersistenceException exception) {
				exception.getMessage();
			} finally {
				entityManager.close();
				entityManagerFactory.close();
			}
		}
		return null;
	}

	public List<EmployeeEntity> getEmployeeListBySalary(double salaryFrom, double salaryTo)
			throws PersistenceException {
		if (entityManager != null) {
			try {
				employeesList = entityManager
						.createQuery("select e from EmployeeEntity e where e.salary BETWEEN :salaryFrom AND :salaryTo",
								EmployeeEntity.class)
						.setParameter("salaryFrom", salaryFrom).setParameter("salaryTo", salaryTo).getResultList();
				if (!employeesList.isEmpty())
					return employeesList;
			} catch (PersistenceException exception) {
				exception.getMessage();
			} finally {
				entityManager.close();
				entityManagerFactory.close();
			}
		}
		return null;
	}

}
