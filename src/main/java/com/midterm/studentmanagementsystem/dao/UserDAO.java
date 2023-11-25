package com.midterm.studentmanagementsystem.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.midterm.studentmanagementsystem.models.User;


public class UserDAO implements DAO<User> {
	Connection conn;
	
	public UserDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean add(User obj) {
		if (getById(obj.getEmail()) != null) {
			return false;
		}
		
		String sql = "INSERT INTO Users (email, password, name, age, dob, phone, status, role, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, obj.getEmail());
			
			String hashed = BCrypt.hashpw(obj.getPassword(), BCrypt.gensalt(10));
			pstm.setString(2, hashed);
			
			pstm.setString(3, obj.getName());
			pstm.setInt(4, obj.getAge());
			pstm.setDate(5, obj.getDob());
			pstm.setString(6, obj.getPhone());
			pstm.setString(7, obj.getStatus());
			pstm.setString(8, obj.getRole());
			pstm.setDate(9, new Date(System.currentTimeMillis()));
			
			int rowAffected = pstm.executeUpdate();
			
			if (rowAffected > 0) {
				return true;
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		return false;
	}

	@Override
	public boolean update(User obj) {
		String sql = "Update Users SET ";
		
		if (obj.getPassword() != null) sql += "password = ?, ";
        if (obj.getName() != null) sql += "name = ?, ";
        if (obj.getAge() > 0 && obj.getAge() <= 100) sql += "age = ?, ";
        if (obj.getDob() != null) sql += "dob = ?, ";
        if (obj.getPhone() != null) sql += "phone = ?, ";
        if (obj.getStatus() != null) sql += "status = ?, ";
        if (obj.getRole() != null) sql += "role = ?, ";
        
        sql = sql.substring(0, sql.length() - 2) + ", updatedAt = ? WHERE email = ?";
        try {
        	PreparedStatement pstm = conn.prepareStatement(sql);
        	
        	int parameterIndex = 1;
        	String hashed = BCrypt.hashpw(obj.getPassword(), BCrypt.gensalt(10));
        	
            if (obj.getPassword() != null) pstm.setString(parameterIndex++, hashed);
            if (obj.getName() != null) pstm.setString(parameterIndex++, obj.getName());
            if (obj.getAge() > 0 && obj.getAge() <= 100) pstm.setInt(parameterIndex++, obj.getAge());
            if (obj.getDob() != null) pstm.setDate(parameterIndex++, obj.getDob());
            if (obj.getPhone() != null) pstm.setString(parameterIndex++, obj.getPhone());
            if (obj.getStatus() != null) pstm.setString(parameterIndex++, obj.getStatus());
            if (obj.getRole() != null) pstm.setString(parameterIndex++, obj.getRole());

            pstm.setDate(parameterIndex++, new Date(System.currentTimeMillis()));
        	pstm.setString(parameterIndex, obj.getEmail());
        	
        	int rowAffected = pstm.executeUpdate();
        	if (rowAffected > 0) {
        		return true;
        	}
        } catch (SQLException ex) {
        	System.out.println(ex.getMessage());
        }
        
		return false;
	}

	@Override
	public boolean delete(User obj) {
		String sql = "DELETE FROM Users WHERE email = ?";
		
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, obj.getEmail());
			
			int rowAffected = pstm.executeUpdate();
			
			if (rowAffected > 0) {
				return true;
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}

	@Override
	public User getById(String email) {
		String sql = "SELECT * FROM Users u WHERE u.email = ?";
		
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, email);
			
			ResultSet result = pstm.executeQuery();
			if (result.next()) {
				String password = result.getString("password");
				String name = result.getString("name");
				int age = result.getInt("age");
				Date dob = result.getDate("dob");
				String status = result.getString("status");
				String phone = result.getString("phone");
				String role = result.getString("role");
				Date createdAt = result.getDate("createdAt");
				Date updatedAt = result.getDate("updatedAt");
				
				return new User(email, password, name, age, dob, status, phone, role, createdAt, updatedAt);
			}
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}

	@Override
	public List<User> getAll() {
		ArrayList<User> users = new ArrayList<>();
		String sql = "SELECT * FROM Users";
		
		try {
			Statement stm = conn.createStatement();
			ResultSet result = stm.executeQuery(sql);
			
			while (result.next()) {
				String email = result.getString("email");
				String password = result.getString("password");
				String name = result.getString("name");
				int age = result.getInt("age");
				Date dob = result.getDate("dob");
				String status = result.getString("status");
				String phone = result.getString("phone");
				String role = result.getString("role");
				Date createdAt = result.getDate("createdAt");
				Date updatedAt = result.getDate("updatedAt");
				
				User u = new User(email, password, name, age, dob, status, phone, role, createdAt, updatedAt);
				users.add(u);
			}
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		return users;
	}
	
	
}
