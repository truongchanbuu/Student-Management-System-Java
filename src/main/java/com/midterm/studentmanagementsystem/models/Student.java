package com.midterm.studentmanagementsystem.models;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class Student {
	private String sid;
	private String name;
	private String gender;
	private String age;
	private Date dob;
	
	private List<Certificate> certificates;
}
