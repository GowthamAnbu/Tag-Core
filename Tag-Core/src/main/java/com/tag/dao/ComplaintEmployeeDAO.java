package com.tag.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tag.model.ComplaintEmployee;
import com.tag.util.ConnectionUtil;

public class ComplaintEmployeeDAO {
	private JdbcTemplate jdbcTemplate = ConnectionUtil.getJdbcTemplate();
	
	public void save(ComplaintEmployee complaintEmployee) {
		String sql = "INSERT INTO COMPLAINTS_EMPLOYEE (COMPLAINT_ID,EMPLOYEE_ID) VALUES(?,?)";
		Object[] args = { complaintEmployee.getComplaint().getId(),complaintEmployee.getEmployee().getUser().getId() };
		jdbcTemplate.update(sql, args);
	}
	
	public List<Integer> getEmployeeId(Integer userId){
		String sql = "SELECT DISTINCT EMPLOYEE_ID FROM complaints_employee WHERE COMPLAINT_ID IN(SELECT ID FROM COMPLAINTS WHERE USER_ID=?);";
		Object[] args = { userId };
		List<Integer> employeeList = jdbcTemplate.queryForList(sql, args,int.class);
		return employeeList;
	}

}
