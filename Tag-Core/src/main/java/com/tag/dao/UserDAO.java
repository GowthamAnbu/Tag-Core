package com.tag.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tag.model.Role;
import com.tag.model.User;
import com.tag.util.ConnectionUtil;

public class UserDAO {
	private JdbcTemplate jdbcTemplate=ConnectionUtil.getJdbcTemplate();
	
	public void save(User user){
		String sql="INSERT INTO USERS (NAME,GENDER,EMAIL_ID,PASSWORD,PHONE_NUMBER) VALUES(?,?,?,?,?);";
		Object[] args={user.getName(),user.getGender(),user.getEmailId(),user.getPassword(),user.getPhoneNumber()};
		jdbcTemplate.update(sql,args);
	}
	
	public boolean isValidEmailId(String emailId){
		String sql="SELECT IFNULL((SELECT TRUE FROM USERS WHERE EMAIL_ID=?),FALSE)";
		Object[] args={emailId};
		return jdbcTemplate.queryForObject(sql,args,boolean.class);
	}
	
	public boolean isValidPassword(String emailId,String password){
		String sql="SELECT IFNULL((SELECT TRUE FROM USERS WHERE EMAIL_ID=? AND PASSWORD=?),FALSE)";
		Object[] args={emailId,password};
		return jdbcTemplate.queryForObject(sql,args,boolean.class);
	}
	
	public User getUser(String emailId) {
		String sql = "SELECT ID,NAME,ROLE_ID,GENDER,EMAIL_ID,PASSWORD,PHONE_NUMBER FROM USERS WHERE EMAIL_ID=?";
		Object[] args = { emailId };
		return jdbcTemplate.queryForObject(sql, args, (rs, rowNum) -> {
			final User user = new User();
			user.setId(rs.getInt("ID"));
			user.setName(rs.getString("NAME"));
			final Role role = new Role();
			role.setId(rs.getInt("ROLE_ID"));
			user.setRole(role);
			user.setEmailId(rs.getString("EMAIL_ID"));
			user.setPassword(rs.getString("PASSWORD"));
			user.setPhoneNumber(rs.getString("PHONE_NUMBER"));
			return user;
		});
	}
	
	public int getRole(String emailId){
		String sql = "SELECT ROLE_ID FROM USERS WHERE EMAIL_ID=?";
		Object[] args = { emailId };
		return jdbcTemplate.queryForObject(sql,args,int.class);
	}
	
	public int getUserId(String emailId){
		String sql = "SELECT ID FROM USERS WHERE EMAIL_ID=?";
		Object[] args = { emailId };
		return jdbcTemplate.queryForObject(sql,args,int.class);
	}
	
}
