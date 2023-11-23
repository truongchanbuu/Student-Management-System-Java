package com.midterm.studentmanagementsystem.models;

import java.sql.Date;

import lombok.Data;

@Data
public class Certificate {
	private String cid;
	private String certificateName;
	private Date issuedDate;
	private Date expiryDate;
	private String organizationName;
	private String sid;
}
