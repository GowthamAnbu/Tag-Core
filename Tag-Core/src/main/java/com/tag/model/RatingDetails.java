package com.tag.model;

import lombok.Data;

@Data
public class RatingDetails {
	
	Integer id;
	Employee employee;
	User user;
	Integer rating;
}