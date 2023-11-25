package com.midterm.studentmanagementsystem.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

public class JDBCUtils {
	public static Connection getConnection() {
		String url = JDBCUtils.getDataPropertiesFile("db.url").orElse("");
		String username = JDBCUtils.getDataPropertiesFile("db.username").orElse("");
		String password = JDBCUtils.getDataPropertiesFile("db.password").orElse("");
		
		Connection conn = null;
		try {
			Driver myDriver = new com.microsoft.sqlserver.jdbc.SQLServerDriver();
			DriverManager.registerDriver(myDriver);
			
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		return conn;
	}
	
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
	public static Optional<String> getDataPropertiesFile(String property) {
		Properties properties = new Properties();
		try (InputStream input = JDBCUtils.class.getClassLoader().getResourceAsStream("dbconfig.properties")) {
		    properties.load(input);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Optional<String> receivedProperty = Optional.ofNullable(properties.getProperty(property));
		return receivedProperty;
	}
}
