package com.comakeit.maven.rest_jpa.JPA;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import com.comakeit.maven.rest_jpa.entities.EmployeeEntity;
import com.comakeit.maven.rest_jpa.entities.LeaveBalanceEntity;
import com.comakeit.maven.rest_jpa.entities.LeaveEntity;

public class LeaveJPA {
	LeaveEntity leave;
	List<LeaveBalanceEntity> leaveBalanceList;
	LeaveBalanceEntity leaveBalance;
	List<LeaveEntity> leavesList;

	private EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory("employee_management_persistence");
	private EntityManager entityManager = entityManagerFactory.createEntityManager();

	public boolean applyLeave(LeaveEntity leaveApplication) {
		try {
			if (entityManager != null) {
				entityManager.getTransaction().begin();
				leaveBalance = new LeaveBalanceEntity();
				leaveApplication.setLeave_id(createLeaveId());
				System.out.println(leaveApplication);
				leaveBalance = entityManager.find(LeaveBalanceEntity.class,
						leaveApplication.getEmployee().getLeave_balance().getId());
				Period period = Period.between(leaveApplication.getFrom_date(), leaveApplication.getTo_date());
				int duration = period.getDays();
				if (duration > 5)
					return false;
				int leave_balance_count;
				if (leaveApplication.getLeave_type().equals("casual")) {
					leave_balance_count = leaveApplication.getEmployee().getLeave_balance().getCasual_leaves();
					if (leave_balance_count >= 1 && duration < leave_balance_count) {
						leaveBalance.setCasual_leaves(leave_balance_count - duration);
						entityManager.persist(leaveApplication);
						return true;
					}
				} else if (leaveApplication.getLeave_type().equals("loss_of_pay")) {
					leave_balance_count = leaveApplication.getEmployee().getLeave_balance().getLoss_of_pay();
					if (leave_balance_count >= 1 && duration < leave_balance_count) {
						leavesList = entityManager.createQuery(
								"select l from LeaveEntity l where l.employee.employee_id=:employee_id AND l.status='approved' AND "
										+ "MONTH(l.from_date)=:start_month AND MONTH(l.to_date)=:end_month",
								LeaveEntity.class)
								.setParameter("employee_id", leaveApplication.getEmployee().getEmployee_id())
								.setParameter("start_month", leaveApplication.getFrom_date().getMonthValue())
								.setParameter("end_month", leaveApplication.getTo_date().getMonthValue())
								.getResultList();
						if (leavesList.size() < 3) {

							Period leaves_period;
							int leaves_duration = 0;
							for (LeaveEntity iterator : leavesList) {
								leaves_period = Period.between(iterator.getFrom_date(), iterator.getTo_date());

								leaves_duration += leaves_period.getDays();
							}
							if (leaves_duration + duration <= 3) {
								leaveApplication.setLeave_id(createLeaveId());
								leaveBalance.setLoss_of_pay(leave_balance_count - duration);
								entityManager.persist(leaveApplication);
								return true;
							}

						}
					}
				}
			}
		} catch (PersistenceException exception) {
			entityManager.getTransaction().rollback();
			exception.getMessage();
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
			entityManagerFactory.close();
		}
		return false;
	}

	public List<LeaveEntity> getLeavesOfEmployee(EmployeeEntity employee) {

		try {
			if (entityManager != null) {
				entityManager.getTransaction().begin();
				List<LeaveEntity> leavesList = entityManager
						.createQuery("select l from LeaveEntity l " + "where l.employee.employee_id =:employee_id",
								LeaveEntity.class)
						.setParameter("employee_id", employee.getEmployee_id()).getResultList();
				return leavesList;

			} else
				entityManager.getTransaction().rollback();
		} catch (PersistenceException exception) {
			exception.getMessage();
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
			entityManagerFactory.close();
		}

		return null;
	}

	public List<LeaveEntity> getLeaveRequests(EmployeeEntity employee) {

		try {
			if (entityManager != null) {
				entityManager.getTransaction().begin();
				System.out.println("in getLeaveRequests(EmployeeEntity employee)");
				List<LeaveEntity> leavesList = entityManager
						.createQuery("select l from LeaveEntity l "
								+ "where l.apply_to=: employee_id and l.status='pending'", LeaveEntity.class)
						.setParameter("employee_id", employee.getEmployee_id()).getResultList();
				return leavesList;

			} else
				entityManager.getTransaction().rollback();

		} catch (PersistenceException exception) {
			exception.getMessage();
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
			entityManagerFactory.close();
		}

		return null;
	}

	public boolean acceptLeave(LeaveEntity leave_application) {

		try {
			if (entityManager != null) {
				entityManager.getTransaction().begin();
				leave_application = entityManager.find(LeaveEntity.class, leave_application.getLeave_id());
				leave_application.setStatus("approved");
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

	public boolean rejectLeave(LeaveEntity leave_application) {
		try {
			if (entityManager != null) {
				entityManager.getTransaction().begin();
				leave_application = entityManager.find(LeaveEntity.class, leave_application.getLeave_id());

				Period period = Period.between(leave_application.getFrom_date(), leave_application.getTo_date());
				int duration = period.getDays();

				leaveBalance = new LeaveBalanceEntity();
				leaveBalance = entityManager.find(LeaveBalanceEntity.class,
						leave_application.getEmployee().getLeave_balance().getId());

				int leave_balance_count;
				if (leave_application.getLeave_type().equals("casual")) {
					leave_balance_count = leave_application.getEmployee().getLeave_balance().getCasual_leaves();
					leaveBalance.setCasual_leaves(leave_balance_count + duration);
					leave_application.setStatus("rejected");
					return true;
				} else if (leave_application.getLeave_type().equals("loss_of_pay")) {
					leave_balance_count = leave_application.getEmployee().getLeave_balance().getLoss_of_pay();
					leaveBalance.setLoss_of_pay(leave_balance_count + duration);
					leave_application.setStatus("rejected");
					return true;
				}

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

	public boolean cancelLeave(LeaveEntity leave_application) {
		try {
			if (entityManager != null) {
				entityManager.getTransaction().begin();
				leave_application = entityManager.find(LeaveEntity.class, leave_application.getLeave_id());

				Period period = Period.between(leave_application.getFrom_date(), leave_application.getTo_date());
				int duration = period.getDays();

				leaveBalance = new LeaveBalanceEntity();
				leaveBalance = entityManager.find(LeaveBalanceEntity.class,
						leave_application.getEmployee().getLeave_balance().getId());

				int leave_balance_count;
				if (leave_application.getLeave_type().equals("casual")) {
					leave_balance_count = leave_application.getEmployee().getLeave_balance().getCasual_leaves();
					leaveBalance.setCasual_leaves(leave_balance_count + duration);
					leave_application.setStatus("cancelled");
					return true;
				} else if (leave_application.getLeave_type().equals("loss_of_pay")) {
					leave_balance_count = leave_application.getEmployee().getLeave_balance().getLoss_of_pay();
					leaveBalance.setLoss_of_pay(leave_balance_count + duration);
					leave_application.setStatus("cancelled");
					return true;
				}

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

	public static String createLeaveId() {
		return "CMIL" + new SecureRandom().nextInt() % 100000;
	}

}
