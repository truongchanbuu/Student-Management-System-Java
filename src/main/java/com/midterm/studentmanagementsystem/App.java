package com.midterm.studentmanagementsystem;

import java.awt.EventQueue;
import java.sql.Connection;

import com.midterm.studentmanagementsystem.dao.UserDAO;
import com.midterm.studentmanagementsystem.utils.JDBCUtils;
import com.midterm.studentmanagementsystem.views.Auth.LoginForm;
import com.midterm.studentmanagementsystem.views.User.UserMainForm;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Connection conn = JDBCUtils.getConnection();
    	UserDAO userDAO = new UserDAO(conn);
    	
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new LoginForm(userDAO);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}
