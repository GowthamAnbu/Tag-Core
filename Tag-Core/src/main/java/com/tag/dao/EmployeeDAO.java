package com.tag.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tag.model.Employee;
import com.tag.util.ConnectionUtil;

public class EmployeeDAO {
	private JdbcTemplate jdbcTemplate=ConnectionUtil.getJdbcTemplate();
	
	public void save(Employee employee){
		String sql="INSERT INTO EMPLOYEES (ID,DEPAERTMENT_ID,DESIGNATION_ID,RATING) VALUES(?,?,?,?);";
		Object[] args={employee.getUser().getId(),employee.getDepartment().getId(),employee.getDesignation().getId(),employee.getRating()};
		jdbcTemplate.update(sql,args);
	}
}
