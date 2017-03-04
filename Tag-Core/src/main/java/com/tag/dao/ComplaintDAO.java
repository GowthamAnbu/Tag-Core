package com.tag.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tag.model.Complaint;
import com.tag.model.Department;
import com.tag.model.User;
import com.tag.util.ConnectionUtil;

public class ComplaintDAO {
	private JdbcTemplate jdbcTemplate = ConnectionUtil.getJdbcTemplate();

	public void save(Complaint complaint) {
		String sql = "INSERT INTO COMPLAINTS (NAME,USER_ID,DEPARTMENT_ID,DOOR_NUMBER,STREET_NAME,PINCODE,DETAILS) VALUES(?,?,?,?,?,?,?);";
		Object[] args = { complaint.getName(), complaint.getUser().getId(), complaint.getDepartment().getId(),
				complaint.getDoorNo(), complaint.getStreetName(), complaint.getPincode(), complaint.getDetails() };
		jdbcTemplate.update(sql, args);
	}

	public void uSave(Complaint complaint) {
		String sql = "INSERT INTO COMPLAINTS (NAME,DEPARTMENT_ID,STREET_NAME,PINCODE,DETAILS) VALUES(?,?,?,?,?);";
		Object[] args = { complaint.getName(), complaint.getDepartment().getId(), complaint.getStreetName(),
				complaint.getPincode(), complaint.getDetails() };
		jdbcTemplate.update(sql, args);
	}

	public List<Complaint> findAll() {
		String sql = "SELECT ID,NAME,USER_ID,DEPARTMENT_ID,DOOR_NUMBER,STREET_NAME,PINCODE,DETAILS FROM COMPLAINTS";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			final Complaint complaint = new Complaint();
			final User user = new User();
			user.setId(rs.getInt("USER_ID"));
			complaint.setId(rs.getInt("ID"));
			complaint.setUser(user);
			complaint.setName(rs.getString("NAME"));
			Department department = new Department();
			department.setId(rs.getInt("DEPARTMENT_ID"));
			complaint.setDepartment(department);
			complaint.setDoorNo(rs.getString("DOOR_NUMBER"));
			complaint.setStreetName(rs.getString("STREET_NAME"));
			complaint.setPincode(rs.getString("PINCODE"));
			complaint.setDetails(rs.getString("DETAILS"));
			return complaint;
		});
	}
	
	public List<Complaint> findassigned() {
		String sql = "SELECT ID,NAME,USER_ID,DEPARTMENT_ID,DOOR_NUMBER,STREET_NAME,PINCODE,DETAILS FROM COMPLAINTS WHERE STATUS_ID=1";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			final Complaint complaint = new Complaint();
			final User user = new User();
			user.setId(rs.getInt("USER_ID"));
			complaint.setId(rs.getInt("ID"));
			complaint.setUser(user);
			complaint.setName(rs.getString("NAME"));
			Department department = new Department();
			department.setId(rs.getInt("DEPARTMENT_ID"));
			complaint.setDepartment(department);
			complaint.setDoorNo(rs.getString("DOOR_NUMBER"));
			complaint.setStreetName(rs.getString("STREET_NAME"));
			complaint.setPincode(rs.getString("PINCODE"));
			complaint.setDetails(rs.getString("DETAILS"));
			return complaint;
		});
	}
	
	public int getUserId(Integer complaintId){
		String sql = "SELECT USER_ID FROM COMPLAINTS WHERE ID=?";
		Object[] args = { complaintId };
		return jdbcTemplate.queryForObject(sql,args,int.class);
	}
	
}
