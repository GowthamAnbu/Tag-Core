package com.tag.dao;

import java.util.List;

import org.apache.commons.mail.EmailException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tag.model.Complaint;
import com.tag.model.Department;
import com.tag.model.Status;
import com.tag.model.User;
import com.tag.util.ConnectionUtil;
import com.tag.util.MailUtil;

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

	public void update(Integer complaintId) {
		String sql = "UPDATE COMPLAINTS SET STATUS_ID=3 WHERE ID=?";
		Object[] args = { complaintId};
		jdbcTemplate.update(sql, args);
	}
	
	public List<Complaint> findAll() {
		String sql = "SELECT ID,NAME,USER_ID,DEPARTMENT_ID,DOOR_NUMBER,STREET_NAME,PINCODE,DETAILS,STATUS_ID FROM COMPLAINTS";
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
			final Status status = new Status();
			status.setId(rs.getInt("STATUS_ID"));
			complaint.setStatus(status);
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
	
	public List<Complaint> findbyUserId(Integer userId) {
		String sql = "SELECT ID,NAME,USER_ID,DEPARTMENT_ID,DOOR_NUMBER,STREET_NAME,PINCODE,DETAILS FROM COMPLAINTS WHERE USER_ID=? AND STATUS_ID=1";
		Object[] args={userId};
		return jdbcTemplate.query(sql, args, (rs, rowNum) -> {
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
	
	public List<Complaint> getEmployee(Integer userId) {
		String sql = "SELECT C.ID AS COMPLAINT_ID,C.NAME AS COMPLAINT_NAME,CE.`EMPLOYEE_ID` EMPLOYEE_ID,C.`STATUS_ID`AS STATUS_ID FROM COMPLAINTS C LEFT JOIN COMPLAINTS_EMPLOYEE CE ON C.`ID`=CE.`COMPLAINT_ID`WHERE C.`STATUS_ID` IN (3,4) AND C.`USER_ID`=?";
		Object[] args = { userId };
		return jdbcTemplate.query(sql, args, (rs, rowNum) -> {
			final Complaint complaint = new Complaint();
			complaint.setId(rs.getInt("COMPLAINT_ID"));
			complaint.setName(rs.getString("COMPLAINT_NAME"));
			final User user = new User();
			user.setId(rs.getInt("EMPLOYEE_ID"));
			final Status status = new Status();
			status.setId(rs.getInt("STATUS_ID"));
			complaint.setStatus(status);
			complaint.setUser(user);
			return complaint;
		});
	}
	
	public List<Complaint> viewComplaintStatus(Integer userId) {
		String sql = "SELECT ID,NAME,USER_ID,DEPARTMENT_ID,DOOR_NUMBER,STREET_NAME,PINCODE,DETAILS,STATUS_ID FROM COMPLAINTS WHERE USER_ID=?";
		Object[] args={userId};
		return jdbcTemplate.query(sql, args, (rs, rowNum) -> {
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
			final Status status = new Status();
			status.setId(rs.getInt("STATUS_ID"));
			complaint.setStatus(status);
			return complaint;
		});
	}
	
	public List<Complaint> findbyEmployeeId(Integer employeeId) {
		String sql = "SELECT C.ID,C.NAME,C.DOOR_NUMBER,C.STREET_NAME,C.PINCODE,C.DETAILS,C.STATUS_ID FROM COMPLAINTS C JOIN COMPLAINTS_EMPLOYEE CE ON C.ID=CE.COMPLAINT_ID WHERE CE.EMPLOYEE_ID=?";
		Object[] args={employeeId};
		return jdbcTemplate.query(sql, args, (rs, rowNum) -> {
			final Complaint complaint = new Complaint();
			complaint.setId(rs.getInt("ID"));
			complaint.setName(rs.getString("NAME"));
			complaint.setDoorNo(rs.getString("DOOR_NUMBER"));
			complaint.setStreetName(rs.getString("STREET_NAME"));
			complaint.setPincode(rs.getString("PINCODE"));
			complaint.setDetails(rs.getString("DETAILS"));
			final Status status = new Status();
			status.setId(rs.getInt("STATUS_ID"));
			complaint.setStatus(status);
			return complaint;
		});
	}
	
	public void sendSimpleMail(User user){
		try {
			MailUtil.sendSimpleMail(user);
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
}
