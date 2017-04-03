package com.tag.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tag.dto.Report;
import com.tag.model.Complaint;
import com.tag.model.Employee;
import com.tag.model.Role;
import com.tag.model.User;
import com.tag.util.ConnectionUtil;
import com.tag.util.MailUtil;

public class EmployeeDetailDAO {
	private JdbcTemplate jdbcTemplate = ConnectionUtil.getJdbcTemplate();

	public void save(Employee employee) {
		String sql = "INSERT INTO EMPLOYEE_DETAILS (ID,DEPARTMENT_ID,DESIGNATION_ID) VALUES(?,?,?);";
		Object[] args = { employee.getUser().getId(), employee.getDepartment().getId(),
				employee.getDesignation().getId() };
		jdbcTemplate.update(sql, args);
	}

	public int getDesignation(Integer employeeId) {
		String sql = "SELECT IFNULL((SELECT DESIGNATION_ID FROM EMPLOYEE_DETAILS WHERE ID=?),0)";
		Object[] args = { employeeId };
		return jdbcTemplate.queryForObject(sql, args, int.class);
	}

	public List<Employee> findAll(Integer userId) {
		String sql = "SELECT U.ID AS USER_ID,U.NAME AS USER_NAME,U.EMAIL_ID AS EMAIL_ID,U.PHONE_NUMBER AS PHONE_NUMBER,R.NAME AS DESIGNATION,ED.RATING AS RATING FROM USERS U JOIN EMPLOYEE_DETAILS ED ON U.ID=ED.ID JOIN ROLES R ON U.ROLE_ID=R.ID WHERE U.ID=?";
		Object[] args = { userId };
		return jdbcTemplate.query(sql,args, (rs, rowNum) -> {
			final Employee employee = new Employee();
			final User user = new User();
			user.setId(rs.getInt("USER_ID"));
			user.setName(rs.getString("USER_NAME"));
			user.setEmailId(rs.getString("EMAIL_ID"));
			user.setPhoneNumber(rs.getString("PHONE_NUMBER"));
			final Role role = new Role();
			role.setName(rs.getString("DESIGNATION"));
			user.setRole(role);
			employee.setRating(rs.getFloat("RATING"));
			employee.setUser(user);
			return employee;
		});
	}

	public List<Report> adminReport() {
		String sql = "SELECT DD.NAME AS DEPARTMENT_NAME,ED.ID AS EMPLOYEE_ID,U.EMAIL_ID,(SELECT DESIGNATION_DETAILS.`NAME` FROM DESIGNATION_DETAILS WHERE ID IN (SELECT DESIGNATION_ID FROM EMPLOYEE_DETAILS WHERE ID=ED.`ID`)) AS DESIGNATION_NAME,ED.RATING,(SELECT COUNT(CE.COMPLAINT_ID)FROM COMPLAINTS_EMPLOYEE CE WHERE EMPLOYEE_ID=ED.ID) AS COMPLAINTS_HANDLED FROM DEPARTMENT_DETAILS DD JOIN EMPLOYEE_DETAILS ED ON DD.ID=ED.DEPARTMENT_ID LEFT JOIN USERS U ON U.ID=ED.`ID`";
		return jdbcTemplate.query(sql,(rs, rowNum) -> {
			final Report report= new Report();
			report.setDepartmentName(rs.getString("DEPARTMENT_NAME"));
			report.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
			report.setDesignationName(rs.getString("DESIGNATION_NAME"));
			report.setRating(rs.getInt("RATING"));
			report.setComplaintsHandled(rs.getInt("COMPLAINTS_HANDLED"));
			report.setEmailId(rs.getString("EMAIL_ID"));
			return report;
		});
	}
	
	public String adminEmailId(){
		String sql ="SELECT EMAIL_ID FROM USERS WHERE ID IN(SELECT ID FROM EMPLOYEE_DETAILS WHERE DESIGNATION_ID=5)";
		return jdbcTemplate.queryForObject(sql,String.class);
	}
	
	public List<Integer> getWorker(){
		String sql ="SELECT ID FROM EMPLOYEE_DETAILS WHERE DESIGNATION_ID=1";
		List<Integer> employeeList=jdbcTemplate.queryForList(sql, Integer.class);
		return employeeList;
		
	}
	
	public List<Integer> getComplaintId(Integer i){
		String sql ="SELECT ID FROM COMPLAINTS WHERE STATUS_ID=?";
		Object[] args={i};
		return jdbcTemplate.queryForList(sql, args,Integer.class);
	}
	
	public Boolean level0(Integer id){
		String sql ="SELECT LEVEL0(?)";
		Object[] args={id};
		return jdbcTemplate.queryForObject(sql, args, Boolean.class);
	}
	
	public Boolean level1(Integer id){
		String sql ="SELECT LEVEL1(?)";
		Object[] args={id};
		return jdbcTemplate.queryForObject(sql, args, Boolean.class);
	}
	
	public Boolean level2(Integer id){
		String sql ="SELECT LEVEL2(?)";
		Object[] args={id};
		return jdbcTemplate.queryForObject(sql, args, Boolean.class);
	}
	
	public Boolean level3(Integer id){
		String sql ="SELECT LEVEL3(?)";
		Object[] args={id};
		return jdbcTemplate.queryForObject(sql, args, Boolean.class);
	}
	
	
	public String getEmailId(){
		String sql ="SELECT EMAIL_ID FROM USERS WHERE ID IN (SELECT ID FROM EMPLOYEE_DETAILS WHERE ISNULL(MANAGER_ID))";
		return jdbcTemplate.queryForObject(sql,String.class);
	}
	
	public void trackingWork(){
		int flag=0;
		StringBuffer content = new StringBuffer();
		ComplaintDAO complaintDAO = new ComplaintDAO();
		/*REGISTERED COMPLAINT TRACKING*/
		List<Integer> registeredComplaintIdList = getComplaintId(1);
		Iterator<Integer> rclIterator = registeredComplaintIdList.iterator();
		if(rclIterator.hasNext()){
content.append("<html><body> FOLLOWING COMPLAINTS HAS NOT YET BEEN ASSIGNED TO EMPLOYEE<table><tr><th>ID</th><th>NAME</th><th>COMPLAINTEE ID</th><th>STREET NAME</th><th>PINCODE</th><th>DETAILS</th><th>REGISTERED TIME</th><th>STATUS TIME</th></tr>");
				while(rclIterator.hasNext()) {
					Integer complaintId = rclIterator.next();
					if(level0(complaintId)){
						flag=1;
						Complaint complaint = complaintDAO.findassignedByComplaintId(complaintId);
						content.append("<tr>");
						if(complaint.getUser().getId()==0){
							content.append("<td>"+complaint.getId()+"</td>"+"<td>"+complaint.getName()+"</td>"+"<td>anonymouscomplaint</td>"+"<td>"+complaint.getStreetName()+"</td>"+"<td>"+complaint.getPincode()+"</td>"+"<td>"+complaint.getDetails()+"</td>"+"<td>"+complaint.getRegisteredTime()+"</td>"+"<td>"+complaint.getStatusTime()+"</td>");	
						}
						else{
content.append("<td>"+complaint.getId()+"</td>"+"<td>"+complaint.getName()+"</td>"+"<td>"+complaint.getUser().getId()+"</td>"+"<td>"+complaint.getStreetName()+"</td>"+"<td>"+complaint.getPincode()+"</td>"+"<td>"+complaint.getDetails()+"</td>"+"<td>"+complaint.getRegisteredTime()+"</td>"+"<td>"+complaint.getStatusTime()+"</td>");
						}
							content.append("</tr>");
						}
					}
				content.append("</table><p>ADMIN EMAIIL ID: "+adminEmailId()+"</p></body></table>");
				System.out.println(content.toString());
				if(flag==1){
					MailUtil.sendToHead(getEmailId(), content.toString());
				}
				}
		/*END OF REGISTERED COMPLAINT TRACKING*/
		/*ASSIGNED COMPLAINT TRACKING*/
		List<Integer> level3List = new ArrayList<Integer>();
		List<Integer> level2List = new ArrayList<Integer>();
		List<Integer> level1List = new ArrayList<Integer>();
		List<Integer> assignedComplaintList = getComplaintId(3);
		Iterator<Integer> aclIterator= assignedComplaintList.iterator();
		if(aclIterator.hasNext()){
			while(aclIterator.hasNext()) {
				Integer complaintId = aclIterator.next();
				if(level3(complaintId)){
					level3List.add(complaintId);
				}
				else if(level2(complaintId)){
					level2List.add(complaintId);
				}
				else if(level1(complaintId)){
					level1List.add(complaintId);
				}
			}
			/*LEVEL 3 COMPLAINTS*/
			Iterator<Integer> level3Iterator=level3List.iterator();
			if(level3Iterator.hasNext()){
				
				while(level3Iterator.hasNext()){
					
				}
			}
			/*END OF LEVEL 3 COMPLAINTS */
		}
		/*END OF ASSIGNED COMPLAINT TRACKING*/
		}
}
	

