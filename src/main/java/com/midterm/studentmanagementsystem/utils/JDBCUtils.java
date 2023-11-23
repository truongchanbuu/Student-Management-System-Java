package com.midterm.studentmanagementsystem.utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
	public static Connection getConnection(String url, String username, String password) {
		Connection conn = null;
		try {
			Driver myDriver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(myDriver);
			
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		return conn;
	}
	
	public void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}
