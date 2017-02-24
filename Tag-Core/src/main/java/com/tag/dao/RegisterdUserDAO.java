package com.tag.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tag.model.RegisteredUser;
import com.tag.util.ConnectionUtil;

public class RegisterdUserDAO {
private JdbcTemplate jdbcTemplate=ConnectionUtil.getJdbcTemplate();
	
	public void save(RegisteredUser registeredUser){
		String sql="INSERT INTO REGISTERED_USERS (ID,DOOR_NUMBER,STREET_NUMBER,PINCODE) VALUES(?,?,?,?);";
		Object[] args={registeredUser.getUser().getId(),registeredUser.getDoorNo(),registeredUser.getStreetName(),registeredUser.getPincode()};
		jdbcTemplate.update(sql,args);
	}
}
