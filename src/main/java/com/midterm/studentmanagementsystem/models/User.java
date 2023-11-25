package com.midterm.studentmanagementsystem.models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private String email;
	private String password;
	private String name;
	private int age;
	private Date dob;
	private String status;
	private String phone;
	private String role;
	private Date createdAt;
	private Date updatedAt;
}
