package com.tag.model;

import java.time.LocalDateTime;

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
	LocalDateTime registeredTime;
	Status status;
	LocalDateTime statusTime;
}
