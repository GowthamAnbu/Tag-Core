package com.tag.model;

import lombok.Data;

@Data
public class User {
	Integer id;
	String name;
	String gender;
	String emailId;
	String password;
	String phoneNumber;
	Role role;
}
