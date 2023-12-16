package com.midterm.studentmanagementsystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {
	private String cid;
	private String certificateName;
	private String description;
	private Date issuedDate;
	private Date expiryDate;
	private String organizationName;
	private String isValid;
	private String sid;
	private Date createdAt;
	private Date updatedAt;


	public Certificate(String cid, String certificateName,String description,Date issue_date,Date expiry_date,String organization_name,String isValid,String sid){
		this.cid = cid;
		this.certificateName = certificateName;
		this.description = description;
		this.issuedDate = issue_date;
		this. expiryDate = expiry_date;
		this.organizationName = organization_name;
		this.isValid = isValid;
		this.sid = sid;
	}

}
