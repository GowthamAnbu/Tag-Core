package com.tag.model;

import lombok.Data;

@Data
public class Employee {
	User user;
	Department department;
	Designation designation;
	Integer rating;
}
