package com.tag.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tag.model.RegisteredUser;
import com.tag.model.User;
import com.tag.util.ConnectionUtil;

public class RegisteredUserDAO {
private JdbcTemplate jdbcTemplate=ConnectionUtil.getJdbcTemplate();
	
	public void save(RegisteredUser registeredUser){
		String sql="INSERT INTO REGISTERED_USERS (ID,DOOR_NUMBER,STREET_NAME,PINCODE) VALUES(?,?,?,?);";
		Object[] args={registeredUser.getUser().getId(),registeredUser.getDoorNo(),registeredUser.getStreetName(),registeredUser.getPincode()};
		jdbcTemplate.update(sql,args);
	}
	
	public List<RegisteredUser> findOne(Integer userId) {
		String sql = "SELECT U.ID,U.NAME,U.GENDER,U.EMAIL_ID,U.PHONE_NUMBER,RU.DOOR_NUMBER,RU.STREET_NAME,RU.PINCODE FROM USERS U JOIN REGISTERED_USERS RU ON U.`ID`=RU.`ID` WHERE U.`ID`=?";
		Object[] args = { userId };
		return jdbcTemplate.query(sql,args, (rs, rowNum) -> {
			final User user = new User();
			user.setId(rs.getInt("ID"));
			user.setName(rs.getString("NAME"));
			user.setGender(rs.getString("GENDER"));
			user.setEmailId(rs.getString("EMAIL_ID"));
			user.setPhoneNumber(rs.getString("PHONE_NUMBER"));
			final RegisteredUser registeredUser = new RegisteredUser();
			registeredUser.setDoorNo(rs.getString("DOOR_NUMBER"));
			registeredUser.setStreetName(rs.getString("STREET_NAME"));
			registeredUser.setPincode(rs.getString("PINCODE"));
			registeredUser.setUser(user);
			return registeredUser;
		});
	}
	
}
