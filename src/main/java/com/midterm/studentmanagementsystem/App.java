package com.midterm.studentmanagementsystem;

import java.awt.EventQueue;
import java.sql.Connection;

import com.midterm.studentmanagementsystem.dao.CertificateDAO;
import com.midterm.studentmanagementsystem.dao.LoginHistoryDAO;
import com.midterm.studentmanagementsystem.dao.StudentDAO;
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
    	LoginHistoryDAO lhDAO = new LoginHistoryDAO(conn);
    	StudentDAO stdDAO = new StudentDAO(conn);
    	CertificateDAO certDAO = new CertificateDAO(conn); 
    	
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new LoginForm(userDAO, stdDAO, certDAO, lhDAO);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}
