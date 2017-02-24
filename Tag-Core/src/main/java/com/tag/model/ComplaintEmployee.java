package com.tag.model;

import lombok.Data;

@Data
public class ComplaintEmployee {
	Integer id;
	Complaint complaint;
	Employee employee;
}
