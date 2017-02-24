package com.tag.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.tag.model.User;

public class MailUtil {
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
		email.setMsg("Dear "+ user.getName()+"," +" you have successfully registered");
		email.addTo(user.getEmailId());
		email.setStartTLSEnabled(true);
		email.send();
}
	public static void sendSimpleMail(User user,User author) throws EmailException {
		Email email = new SimpleEmail();
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("ticketmanagement.tms@gmail.com", "ticket123"));
		email.setDebug(true);
		email.setHostName("smtp.gmail.com");
		email.setSSLOnConnect(true);
		email.setFrom("ticketmanagement.tms@gmail.com");
		email.setSubject("COMMENT DETAILS");
		email.setMsg("Dear"+ author.getName() +","+user.getName()+"has commented on your article");
		email.addTo(author.getEmailId());
		email.setStartTLSEnabled(true);
		email.send();
}
}
