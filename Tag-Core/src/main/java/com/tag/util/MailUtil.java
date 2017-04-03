package com.tag.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import com.tag.dao.EmployeeDetailDAO;
import com.tag.model.User;

public class MailUtil {
	EmployeeDetailDAO employeeDetailDAO = new EmployeeDetailDAO();
	private MailUtil() {

	}

	public static void sendSimpleMail(User user) throws EmailException {
		Email email = new SimpleEmail();
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("ticketmanagement.tms@gmail.com", "ticket123"));
		email.setDebug(true);
		email.setHostName("smtp.gmail.com");
		email.setSSLOnConnect(true);
		email.setFrom("ticketmanagement.tms@gmail.com");
		email.setSubject("REGISTRATION");
		email.setMsg("Dear "+ user.getName()+"," +" your complaint has been successfully registered");
		email.addTo(user.getEmailId());
		email.setStartTLSEnabled(true);
		email.send();
}
	public static void sendSimpleMail(User complaintee,User user) throws EmailException {
		Email email = new SimpleEmail();
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("ticketmanagement.tms@gmail.com", "ticket123"));
		email.setDebug(true);
		email.setHostName("smtp.gmail.com");
		email.setSSLOnConnect(true);
		email.setFrom("ticketmanagement.tms@gmail.com");
		email.setSubject("EMPLOYEE ASSIGNED");
		email.setMsg("Dear "+ complaintee.getName() +", Employee with ID:"+user.getId()+" has been assigned to your complaint");
		email.addTo(complaintee.getEmailId());
		email.setStartTLSEnabled(true);
		email.send();
}
	
	public static void sendSimpleMail(User complaintee,Integer complaintId,Integer statusId) throws EmailException {
		String status;
		switch(statusId){
		case 2: status="you have cancelled your complaint";
				break;
		case 4:status="your complaint with complaint ID: "+complaintId +" has been successfully finished.";
				break;
		case 5:status="your complaint with complaint ID: "+complaintId+" has been declined due to false information";
				break;
		default:status="";
		}
		Email email = new SimpleEmail();
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("ticketmanagement.tms@gmail.com", "ticket123"));
		email.setDebug(true);
		email.setHostName("smtp.gmail.com");
		email.setSSLOnConnect(true);
		email.setFrom("ticketmanagement.tms@gmail.com");
		email.setSubject("PROGRESS DETAILS AND TRACKING");
		email.setMsg("Dear "+ complaintee.getName() +", "+status);
		email.addTo(complaintee.getEmailId());
		email.setStartTLSEnabled(true);
		email.send();
}
	public static void sendHtmlMail(String content){
		try{
			
		HtmlEmail email = new HtmlEmail();
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("ticketmanagement.tms@gmail.com", "ticket123"));
		email.setDebug(true);
		email.setHostName("smtp.gmail.com");
		email.setSSLOnConnect(true);
		email.setFrom("ticketmanagement.tms@gmail.com");
		email.setSubject("Monthly Report");
		email.setHtmlMsg(content);
		email.addTo("gautamgow@gmail.com");
		email.setStartTLSEnabled(true);
		email.send();
		} catch (EmailException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
		
		public static void sendToHead(String emailId,String content){
			try{
			HtmlEmail email = new HtmlEmail();
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("ticketmanagement.tms@gmail.com", "ticket123"));
			email.setDebug(true);
			email.setHostName("smtp.gmail.com");
			email.setSSLOnConnect(true);
			email.setFrom("ticketmanagement.tms@gmail.com");
			email.setSubject("Complaint Report");
			email.setHtmlMsg(content);
			email.addTo(emailId);
			email.setStartTLSEnabled(true);
			email.send();
			} catch (EmailException e) {
			e.printStackTrace();
		}
		
	}
	
}
