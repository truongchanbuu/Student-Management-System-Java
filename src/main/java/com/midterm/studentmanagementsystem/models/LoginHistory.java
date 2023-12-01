package com.midterm.studentmanagementsystem.models;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class LoginHistory {
	private int id;
	private String email;
	private Timestamp history;
}
