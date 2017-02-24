package com.tag.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tag.model.User;
import com.tag.util.ConnectionUtil;

public class UserDAO {
	private JdbcTemplate jdbcTemplate=ConnectionUtil.getJdbcTemplate();
	
	public void save(User user){
		String sql="INSERT INTO USERS (NAME,ROLE_ID,GENDER,PASSWORD,EMAIL_ID,PHONE_NUMBER) VALUES(?,?,?,?,?,?);";
		Object[] args={user.getName(),user.getRole().getId(),user.getGender(),user.getPassword(),user.getEmailId(),user.getPhoneNumber()};
		jdbcTemplate.update(sql,args);
	}
}
