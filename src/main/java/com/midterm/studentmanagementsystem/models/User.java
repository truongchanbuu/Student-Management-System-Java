package com.midterm.studentmanagementsystem.models;

import lombok.Data;

@Data
public class User {
	private String email;
	private String password;
	private String name;
	private String phone;
	private String role;
}
