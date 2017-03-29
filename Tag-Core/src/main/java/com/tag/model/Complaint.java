package com.tag.model;

import lombok.Data;

@Data
public class Complaint {
	Integer id;
	String name;
	User user;
	Department department;
	String doorNo;
	String streetName;
	String pincode;
	String details;
	String registeredTime;
	Status status;
	String statusTime;
}
