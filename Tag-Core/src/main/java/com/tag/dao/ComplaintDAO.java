package com.tag.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tag.dto.EmployeeComplaint;
import com.tag.model.Complaint;
import com.tag.model.Department;
import com.tag.model.Status;
import com.tag.model.User;
import com.tag.util.ConnectionUtil;
import com.tag.util.MailUtil;

public class ComplaintDAO {
	private JdbcTemplate jdbcTemplate = ConnectionUtil.getJdbcTemplate();

	public void save(Complaint complaint) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "INSERT INTO COMPLAINTS (NAME,USER_ID,DEPARTMENT_ID,DOOR_NUMBER,STREET_NAME,PINCODE,DETAILS,STATUS_TIME) VALUES(?,?,?,?,?,?,?,?);";
		Object[] args = { complaint.getName(), complaint.getUser().getId(), complaint.getDepartment().getId(),
				complaint.getDoorNo(), complaint.getStreetName(), complaint.getPincode(), complaint.getDetails(),sdf.format(System.currentTimeMillis())  };
		jdbcTemplate.update(sql, args);
	}

	public void uSave(Complaint complaint) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "INSERT INTO COMPLAINTS (NAME,DEPARTMENT_ID,STREET_NAME,PINCODE,DETAILS,STATUS_TIME) VALUES(?,?,?,?,?,?);";
		Object[] args = { complaint.getName(), complaint.getDepartment().getId(), complaint.getStreetName(),
				complaint.getPincode(), complaint.getDetails(),sdf.format(System.currentTimeMillis()) };
		jdbcTemplate.update(sql, args);
	}

	public void update(Integer complaintId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "UPDATE COMPLAINTS SET STATUS_ID=3,STATUS_TIME=? WHERE ID=?";
		Object[] args = { sdf.format(System.currentTimeMillis()),complaintId};
		jdbcTemplate.update(sql, args);
	}
	
	public List<Complaint> findAll() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "SELECT ID,NAME,USER_ID,DEPARTMENT_ID,DOOR_NUMBER,STREET_NAME,PINCODE,DETAILS,REGISTERED_TIME,STATUS_ID,STATUS_TIME FROM COMPLAINTS ORDER BY STATUS_ID";
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
			complaint.setRegisteredTime(sdf.format(rs.getTimestamp("REGISTERED_TIME")));
			final Status status = new Status();
			status.setId(rs.getInt("STATUS_ID"));
			complaint.setStatusTime(sdf.format(rs.getTimestamp("STATUS_TIME")));
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
	
	public Integer getUserId(Integer complaintId){
		String sql = "SELECT IFNULL((SELECT USER_ID FROM COMPLAINTS WHERE ID=?),NULL)";
		Object[] args = { complaintId };
		return jdbcTemplate.queryForObject(sql,args,Integer.class);
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "SELECT C.ID AS COMPLAINT_ID,C.NAME AS COMPLAINT_NAME,CE.`EMPLOYEE_ID` EMPLOYEE_ID,C.`STATUS_ID`AS STATUS_ID,C.`REGISTERED_TIME` AS REGISTERED_TIME,C.`STATUS_TIME` AS STATUS_TIME  FROM COMPLAINTS C LEFT JOIN COMPLAINTS_EMPLOYEE CE ON C.`ID`=CE.`COMPLAINT_ID`WHERE C.`STATUS_ID` IN (3,4) AND C.`USER_ID`=?";
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
			complaint.setRegisteredTime(sdf.format(rs.getTimestamp("REGISTERED_TIME")));
			complaint.setStatusTime(sdf.format(rs.getTimestamp("STATUS_TIME")));
			complaint.setUser(user);
			return complaint;
		});
	}
	
	public List<Complaint> viewComplaintStatus(Integer userId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "SELECT ID,NAME,USER_ID,DEPARTMENT_ID,DOOR_NUMBER,STREET_NAME,PINCODE,DETAILS,REGISTERED_TIME,STATUS_ID,STATUS_TIME FROM COMPLAINTS WHERE USER_ID=? ORDER BY STATUS_ID";
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
			complaint.setRegisteredTime(sdf.format(rs.getTimestamp("REGISTERED_TIME")));
			final Status status = new Status();
			status.setId(rs.getInt("STATUS_ID"));
			complaint.setStatusTime(sdf.format(rs.getTimestamp("STATUS_TIME")));
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
	
	public void sendSimpleMail(User complaintee,Integer complaintId,Integer statusId){
		try {
			MailUtil.sendSimpleMail(complaintee,complaintId,statusId);
		} catch (EmailException e) {
			e.printStackTrace(); 
		}
	}
	
	public void changeStatus(Integer complaintId,Integer statusId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "UPDATE COMPLAINTS SET STATUS_ID=?,STATUS_TIME=? WHERE ID=?";
		Object[] args = { statusId,sdf.format(System.currentTimeMillis()),complaintId};
		jdbcTemplate.update(sql, args);
	}
	
	public Integer getStatus(Integer id){
		String sql = "SELECT STATUS_ID FROM COMPLAINTS WHERE ID=?";
		Object[] args = {id};
		return jdbcTemplate.queryForObject(sql,args,Integer.class);
	}
	
	public Complaint findRegisteredByComplaintId(Integer complaintId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "SELECT ID,NAME,USER_ID,DEPARTMENT_ID,DOOR_NUMBER,STREET_NAME,PINCODE,DETAILS,REGISTERED_TIME,STATUS_ID,STATUS_TIME FROM COMPLAINTS WHERE STATUS_ID=1 AND ID=?";
		Object[] args={complaintId};
		return jdbcTemplate.queryForObject(sql,args,(rs, rowNum) -> {
			final Complaint complaint = new Complaint();
			final User user = new User();
			user.setId(rs.getInt("USER_ID"));
			complaint.setId(rs.getInt("ID"));
			complaint.setUser(user);
			complaint.setName(rs.getString("NAME"));
			complaint.setDoorNo(rs.getString("DOOR_NUMBER"));
			complaint.setStreetName(rs.getString("STREET_NAME"));
			complaint.setPincode(rs.getString("PINCODE"));
			complaint.setDetails(rs.getString("DETAILS"));
			complaint.setRegisteredTime(sdf.format(rs.getTimestamp("REGISTERED_TIME")));
			complaint.setStatusTime(sdf.format(rs.getTimestamp("STATUS_TIME")));
			return complaint;
		});
	}

	public EmployeeComplaint findAssignedByComplaintId(Integer complaintId) {
		String sql = "SELECT C.ID,C.NAME,C.USER_ID,C.REGISTERED_TIME,C.STATUS_TIME,CE.EMPLOYEE_ID,U.NAME AS EMPLOYEE_NAME,U.EMAIL_ID,U.PHONE_NUMBER FROM COMPLAINTS C JOIN COMPLAINTS_EMPLOYEE CE ON C.`ID`=CE.`COMPLAINT_ID` JOIN USERS U ON U.`ID`=CE.`EMPLOYEE_ID` WHERE C.`ID`=?";
		Object[] args={complaintId};
		return jdbcTemplate.queryForObject(sql,args,(rs, rowNum) -> {
			final EmployeeComplaint employeeComplaint = new EmployeeComplaint();
			employeeComplaint.setId(rs.getInt("ID"));
			employeeComplaint.setName(rs.getString("NAME"));
			employeeComplaint.setUserId(rs.getInt("USER_ID"));
			employeeComplaint.setRegisteredTime(rs.getString("REGISTERED_TIME"));
			employeeComplaint.setStatusTime(rs.getString("STATUS_TIME"));
			employeeComplaint.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
			employeeComplaint.setEmployeeName(rs.getString("EMPLOYEE_NAME"));
			employeeComplaint.setEmailId(rs.getString("EMAIL_ID"));
			employeeComplaint.setPhoneNumber(rs.getString("PHONE_NUMBER"));
			return employeeComplaint;
		});
	}
	
	public void cancel(Integer complaintId){
		String sql = "UPDATE COMPLAINTS SET STATUS_ID=2 WHERE ID=?";
		Object[] args = {complaintId};
		jdbcTemplate.update(sql, args);
	}
	
	public User getUser(Integer complaintId){
		String sql="SELECT NAME,EMAIL_ID FROM USERS WHERE ID IN (SELECT USER_ID FROM COMPLAINTS WHERE ID=?)";
		Object[] args = {complaintId};
		return jdbcTemplate.queryForObject(sql,args,(rs, rowNum) -> {
			final User user = new User();
			user.setName(rs.getString("NAME"));
			user.setEmailId(rs.getString("EMAIL_ID"));
			return user;
		});
		
	}
	
}

