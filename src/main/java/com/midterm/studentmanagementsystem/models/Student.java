package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	private String sid;
	private String name;
	private Date dob;
	private String gender;
	private String className;
	private String department;
	private String major;
	private int courseYear;
	private Date createdAt;
	private Date updatedAt;
	private String eduType;


	//private List<Certificate> certificates;


	// Constructor without id
	public Student(String name, Date dob, String gender, String className, String department, String major, int courseYear, Date createdAt, Date updatedAt, String eduType) {
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.className = className;
		this.department = department;
		this.major = major;
		this.courseYear = courseYear;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.eduType = eduType;
	}
	public Student(String sid,String name, Date dob, String gender, String className, String department, String major, int courseYear, String eduType) {
		this.sid = sid;
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.className = className;
		this.department = department;
		this.major = major;
		this.courseYear = courseYear;
		this.eduType = eduType;
	}
}


