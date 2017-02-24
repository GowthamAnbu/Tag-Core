package com.tag.model;

import lombok.Data;

@Data
public class RegisteredUser {
	User user;
	String doorNo;
	String streetName;
	String pincode;
}
