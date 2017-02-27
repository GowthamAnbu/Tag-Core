package com.tag.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tag.model.Employee;
import com.tag.util.ConnectionUtil;

public class EmployeeDetailDAO {
	private JdbcTemplate jdbcTemplate=ConnectionUtil.getJdbcTemplate();
	
	public void save(Employee employee){
		String sql="INSERT INTO EMPLOYEE_DETAILS (ID,DEPARTMENT_ID,DESIGNATION_ID) VALUES(?,?,?);";
		Object[] args={employee.getUser().getId(),employee.getDepartment().getId(),employee.getDesignation().getId()};
		jdbcTemplate.update(sql,args);
	}
	
	public int getDesignation(Integer employeeId){
		String sql="SELECT IFNULL((SELECT DESIGNATION_ID FROM EMPLOYEE_DETAILS WHERE ID=?),0)";
		Object[] args={employeeId};
		return jdbcTemplate.queryForObject(sql,args,int.class);
	}
}
