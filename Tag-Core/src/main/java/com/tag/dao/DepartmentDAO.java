package com.tag.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tag.util.ConnectionUtil;

public class DepartmentDAO {
	private JdbcTemplate jdbcTemplate = ConnectionUtil.getJdbcTemplate();
	
	public int getId(String name){
		String sql = "SELECT ID FROM DEPARTMENT_DETAILS WHERE NAME=?";
		Object[] args = { name };
		return jdbcTemplate.queryForObject(sql,args,int.class);
	}
}
