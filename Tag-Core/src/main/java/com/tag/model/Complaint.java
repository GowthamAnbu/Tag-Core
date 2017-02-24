package com.tag.model;

import lombok.Data;

@Data
public class Complaint {
	Integer id;
	String name;
	User user;
	Department department;
}
