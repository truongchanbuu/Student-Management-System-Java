package com.midterm.studentmanagementsystem.views.Auth;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

import javax.swing.JTextField;

import org.mindrot.jbcrypt.BCrypt;

import com.midterm.studentmanagementsystem.dao.UserDAO;
import com.midterm.studentmanagementsystem.models.User;
import com.midterm.studentmanagementsystem.views.User.UserMainForm;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginForm {
	private UserDAO userDAO;
	
	private JFrame LoginForm;
	private JTextField tfEmail;
	private JPasswordField tfPassword;
	
	/**
	 * Create the application.
	 */
	public LoginForm(UserDAO userDAO) {
		initialize();
		this.userDAO = userDAO;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		LoginForm = new JFrame();
		LoginForm.setTitle("Student Management System");
		LoginForm.setBounds(100, 100, 435, 339);
		LoginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LoginForm.getContentPane().setLayout(null);
		
		JLabel lblIntroduction = new JLabel("LOGIN TO STUDENT MANAGEMENT SYSTEM");
		lblIntroduction.setBounds(10, 11, 399, 78);
		lblIntroduction.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		LoginForm.getContentPane().add(lblIntroduction);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblEmail.setBounds(35, 118, 73, 21);
		LoginForm.getContentPane().add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblPassword.setBounds(35, 165, 91, 21);
		LoginForm.getContentPane().add(lblPassword);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(129, 123, 280, 21);
		LoginForm.getContentPane().add(tfEmail);
		tfEmail.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		
		btnLogin.setBounds(52, 237, 142, 38);
		LoginForm.getContentPane().add(btnLogin);
		
		JButton btnExit = new JButton("Exit");
		
		btnExit.setBounds(234, 237, 142, 38);
		LoginForm.getContentPane().add(btnExit);
		
		tfPassword = new JPasswordField();
		tfPassword.setBounds(129, 170, 280, 21);
		LoginForm.getContentPane().add(tfPassword);
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(LoginForm, "Do you really want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
				
				if (result == JOptionPane.YES_OPTION) {
					LoginForm.dispatchEvent(new WindowEvent(LoginForm, WindowEvent.WINDOW_CLOSING));
				}
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = tfEmail.getText();
				char[] password = tfPassword.getPassword();
				String passwordStr = new String(password);
				
				if (email.isEmpty() || password.length == 0) {
					JOptionPane.showMessageDialog(LoginForm, "Please fill your email or password in", "Login Failed", JOptionPane.OK_OPTION);
					return;
				}
				
				User u = userDAO.getById(email);
				if (u == null) {
					JOptionPane.showMessageDialog(LoginForm, "Invalid email or password", "Login Failed", JOptionPane.OK_OPTION);
					return;
				}
				
				boolean isCorrectPassword = BCrypt.checkpw(passwordStr, u.getPassword());
				if (!isCorrectPassword) {
					JOptionPane.showMessageDialog(LoginForm, "Invalid email or password", "Login Failed", JOptionPane.OK_OPTION);
					return;
				}
				
				// It should be Student Form
				// Delete if it is Student Form
				// ####
				if (!u.getRole().equalsIgnoreCase("admin")) {
					JOptionPane.showMessageDialog(LoginForm, "You are not allowed to this", "Authorization", JOptionPane.CLOSED_OPTION);
					return;
				}
				// ####
				new UserMainForm(userDAO, email);
				LoginForm.dispose();
			}
		});
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnLogin.doClick();
                    return true;
                }
                return false;
            }
        });
		LoginForm.setVisible(true);
	}
}
