package com.tag.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tag.model.Employee;
import com.tag.model.Role;
import com.tag.model.User;
import com.tag.util.ConnectionUtil;

public class EmployeeDetailDAO {
	private JdbcTemplate jdbcTemplate = ConnectionUtil.getJdbcTemplate();

	public void save(Employee employee) {
		String sql = "INSERT INTO EMPLOYEE_DETAILS (ID,DEPARTMENT_ID,DESIGNATION_ID) VALUES(?,?,?);";
		Object[] args = { employee.getUser().getId(), employee.getDepartment().getId(),
				employee.getDesignation().getId() };
		jdbcTemplate.update(sql, args);
	}

	public int getDesignation(Integer employeeId) {
		String sql = "SELECT IFNULL((SELECT DESIGNATION_ID FROM EMPLOYEE_DETAILS WHERE ID=?),0)";
		Object[] args = { employeeId };
		return jdbcTemplate.queryForObject(sql, args, int.class);
	}

	public List<Employee> findAll(Integer userId) {
		String sql = "SELECT U.ID AS USER_ID,U.NAME AS USER_NAME,U.EMAIL_ID AS EMAIL_ID,U.PHONE_NUMBER AS PHONE_NUMBER,R.NAME AS DESIGNATION,ED.RATING AS RATING FROM USERS U JOIN EMPLOYEE_DETAILS ED ON U.ID=ED.ID JOIN ROLES R ON U.ROLE_ID=R.ID WHERE U.ID=?";
		Object[] args = { userId };
		return jdbcTemplate.query(sql,args, (rs, rowNum) -> {
			final Employee employee = new Employee();
			final User user = new User();
			user.setId(rs.getInt("USER_ID"));
			user.setName(rs.getString("USER_NAME"));
			user.setEmailId(rs.getString("EMAIL_ID"));
			user.setPhoneNumber(rs.getString("PHONE_NUMBER"));
			final Role role = new Role();
			role.setName(rs.getString("DESIGNATION"));
			user.setRole(role);
			employee.setRating(rs.getFloat("RATING"));
			employee.setUser(user);
			return employee;
		});
	}

}
