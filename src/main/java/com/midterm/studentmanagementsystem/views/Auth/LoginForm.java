package com.midterm.studentmanagementsystem.views.Auth;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

import javax.swing.JTextField;

import org.mindrot.jbcrypt.BCrypt;

import com.midterm.studentmanagementsystem.dao.CertificateDAO;
import com.midterm.studentmanagementsystem.dao.LoginHistoryDAO;
import com.midterm.studentmanagementsystem.dao.StudentDAO;
import com.midterm.studentmanagementsystem.dao.UserDAO;
import com.midterm.studentmanagementsystem.models.LoginHistory;
import com.midterm.studentmanagementsystem.models.User;
import com.midterm.studentmanagementsystem.views.User.StudentCRUDForm;
import com.midterm.studentmanagementsystem.views.User.StudentMainView;
import com.midterm.studentmanagementsystem.views.User.UserMainForm;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginForm {
	private UserDAO userDAO;
	private StudentDAO stdDAO;
	private CertificateDAO certDAO;
	private LoginHistoryDAO lhDAO;

	private JFrame LoginForm;
	private JTextField tfEmail;
	private JPasswordField tfPassword;

	/**
	 * Create the application.
	 */
	public LoginForm(UserDAO userDAO, StudentDAO stdDAO, CertificateDAO certDAO, LoginHistoryDAO lhDAO) {
		this.userDAO = userDAO;
		this.stdDAO = stdDAO;
		this.certDAO = certDAO;
		this.lhDAO = lhDAO;
		initialize();
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
				int result = JOptionPane.showConfirmDialog(LoginForm, "Do you really want to exit?", "Exit",
						JOptionPane.YES_NO_OPTION);

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
					JOptionPane.showMessageDialog(LoginForm, "Please fill your email or password in", "Login Failed",
							JOptionPane.OK_OPTION);
					return;
				}

				User u = userDAO.getById(email);
				if (u == null) {
					JOptionPane.showMessageDialog(LoginForm, "Invalid email or password", "Login Failed",
							JOptionPane.OK_OPTION);
					return;
				}

				if (u.getStatus().equalsIgnoreCase("locked") || u.getStatus() == null) {
					JOptionPane.showMessageDialog(LoginForm, "You are locked", "Login Failed", JOptionPane.OK_OPTION);
					return;
				}

				boolean isCorrectPassword = BCrypt.checkpw(passwordStr, u.getPassword());
				if (!isCorrectPassword) {
					JOptionPane.showMessageDialog(LoginForm, "Invalid email or password", "Login Failed",
							JOptionPane.OK_OPTION);
					return;
				}
				
				LoginHistory lh = new LoginHistory();
				lh.setEmail(email);

				lhDAO.add(lh);
				
				new StudentMainView(userDAO, stdDAO, certDAO, lhDAO, email);
				LoginForm.dispose();
			}
		});
		
		KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnLogin.doClick();
				}
				return false;
			}
		};

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);

		LoginForm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);
			}
		});

		LoginForm.setVisible(true);

		LoginForm.setVisible(true);
	}
}
