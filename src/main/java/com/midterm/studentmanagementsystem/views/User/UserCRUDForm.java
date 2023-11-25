package com.midterm.studentmanagementsystem.views.User;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.ParseException;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.swing.JTextField;

import com.midterm.studentmanagementsystem.dao.UserDAO;
import com.midterm.studentmanagementsystem.models.User;
import com.midterm.studentmanagementsystem.utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;

public class UserCRUDForm {
	private UserDAO userDAO;
    private UserMainForm userMainForm;
	
	private JFrame UserCRUDForm;
	private JTextField tfEmail;
	private JTextField tfName;
	private JTextField tfPhone;
	private JPasswordField tfPassword;
	private JPasswordField tfConfirmPassword;

	/**
	 * Create the application.
	 */
	public UserCRUDForm(UserDAO userDAO, UserMainForm userMainForm) {
		this.userDAO = userDAO;
		this.userMainForm = userMainForm;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		UserCRUDForm = new JFrame();
		UserCRUDForm.getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 15));
		UserCRUDForm.setTitle("Student Management System");
		UserCRUDForm.setBounds(100, 100, 661, 404);
		UserCRUDForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		UserCRUDForm.getContentPane().setLayout(null);
		UserCRUDForm.setVisible(true);
		
		MaskFormatter dateFormatter = null;
		try {
			dateFormatter = new MaskFormatter("##-##-####");
			dateFormatter.setPlaceholderCharacter('_');
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblEmail.setBounds(10, 29, 46, 14);
		UserCRUDForm.getContentPane().add(lblEmail);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblName.setBounds(10, 71, 46, 14);
		UserCRUDForm.getContentPane().add(lblName);
		
		JLabel lblDateOfBirth = new JLabel("Date Of Birth:");
		lblDateOfBirth.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblDateOfBirth.setBounds(10, 112, 97, 14);
		UserCRUDForm.getContentPane().add(lblDateOfBirth);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblPhone.setBounds(10, 232, 46, 25);
		UserCRUDForm.getContentPane().add(lblPhone);
		
		JLabel lblRole = new JLabel("Role:");
		lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblRole.setBounds(10, 279, 46, 14);
		UserCRUDForm.getContentPane().add(lblRole);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblStatus.setBounds(10, 320, 46, 14);
		UserCRUDForm.getContentPane().add(lblStatus);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(148, 29, 220, 20);
		UserCRUDForm.getContentPane().add(tfEmail);
		tfEmail.setColumns(10);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(148, 71, 220, 20);
		UserCRUDForm.getContentPane().add(tfName);
		
		tfPhone = new JTextField();
		tfPhone.setColumns(10);
		tfPhone.setBounds(148, 237, 220, 20);
		UserCRUDForm.getContentPane().add(tfPhone);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblPassword.setBounds(10, 155, 65, 14);
		UserCRUDForm.getContentPane().add(lblPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblConfirmPassword.setBounds(10, 197, 123, 14);
		UserCRUDForm.getContentPane().add(lblConfirmPassword);
		
		JLabel lblAvatar = new JLabel("This is avatar");
		lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvatar.setSize(500, 500);
		lblAvatar.setBounds(429, 29, 153, 174);
		// Set image
		String baseResourcesDir = "src/main/resources";
		Path filePath = Paths.get(baseResourcesDir, "default-avatar.png");
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filePath.toUri()));
			
			int labelWidth = lblAvatar.getWidth();
            int labelHeight = lblAvatar.getHeight();

            Image stretchedImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
            ImageIcon imgIcon = new ImageIcon(stretchedImage);
            lblAvatar.setIcon(imgIcon);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "There is something wrong", JOptionPane.CLOSED_OPTION);
		}
		
		UserCRUDForm.getContentPane().add(lblAvatar);
		
		JButton btnChangeAvatar = new JButton("Change Avatar");
		btnChangeAvatar.setBounds(429, 214, 153, 32);
		UserCRUDForm.getContentPane().add(btnChangeAvatar);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(516, 257, 97, 36);
		UserCRUDForm.getContentPane().add(btnReset);
		
		tfPassword = new JPasswordField();
		tfPassword.setBounds(148, 155, 220, 20);
		UserCRUDForm.getContentPane().add(tfPassword);
		
		tfConfirmPassword = new JPasswordField();
		tfConfirmPassword.setBounds(148, 197, 220, 20);
		UserCRUDForm.getContentPane().add(tfConfirmPassword);
		
		JFormattedTextField tfDob = new JFormattedTextField(dateFormatter);
		tfDob.setColumns(10);
		tfDob.setBounds(148, 112, 220, 20);
		UserCRUDForm.getContentPane().add(tfDob);

        String[] options = { "Employee", "Manager", "Admin" };
		JComboBox<String> cbbRole = new JComboBox<>(options);
		cbbRole.setBounds(143, 278, 225, 22);
		UserCRUDForm.getContentPane().add(cbbRole);

		String[] statuses = { "Normal", "Locked" };
		JComboBox<String> cbbStatus = new JComboBox<>(statuses);
		cbbStatus.setBounds(143, 319, 225, 22);
		UserCRUDForm.getContentPane().add(cbbStatus);
		
		// Add a new user
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = tfEmail.getText();
				String name = tfName.getText();
				Object dob = tfDob.getValue();
				String phone = tfPhone.getText();
				Object status = cbbStatus.getSelectedItem();
				Object role = cbbRole.getSelectedItem();
				char[] password = tfPassword.getPassword();
				char[] confirmPassword = tfConfirmPassword.getPassword();
				
		        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
		        Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
		        Pattern phonePattern = Pattern.compile("^\\d{10}$");
		        
		        if (email.isEmpty() || !emailPattern.matcher(email).matches()) {
		        	JOptionPane.showMessageDialog(UserCRUDForm, "Invalid email", "Invalid input", JOptionPane.OK_OPTION);
		        	return;
		        }
		        
		        String passwordStr = new String(password);
		        if (!passwordPattern.matcher(passwordStr).matches() || !Arrays.equals(password, confirmPassword)) {
		            JOptionPane.showMessageDialog(UserCRUDForm, "Invalid password or confirmed password does not match", "Invalid input", JOptionPane.OK_OPTION);
		            return;
		        }
		        
		        if (dob == null || !Utils.isValidDate(dob.toString())) {
		        	JOptionPane.showMessageDialog(UserCRUDForm, "Invalid date of birth", "Invalid input", JOptionPane.OK_OPTION);
		        	return;
		        }
		        
		        if (phone.isEmpty() || !phonePattern.matcher(phone).matches()) {
		        	JOptionPane.showMessageDialog(UserCRUDForm, "Invalid phone number", "Invalid input", JOptionPane.OK_OPTION);
		        	return;
		        }
		        
		        if (name.isEmpty() || status == null || role == null) {
		        	JOptionPane.showMessageDialog(UserCRUDForm, "Invalid information", "Invalid input", JOptionPane.OK_OPTION);
		        	return;
		        }
		        
		        Date createdAt = new Date(System.currentTimeMillis());
		        
		        Date dobDate = Utils.convertToDate(dob.toString());
		        int age = Utils.calculateAge(dobDate);
		        
		        User u = new User(email, passwordStr, name, age, dobDate, status.toString(), phone, role.toString(), createdAt, null);
		        
		        boolean isAdded = userDAO.add(u);
		        
		        if (!isAdded) {
		        	JOptionPane.showMessageDialog(UserCRUDForm, "Added failed", "Failed to add. Please check again or change your email which maybe existed", JOptionPane.OK_OPTION);
		        	return;
		        }
		        
	        	JOptionPane.showMessageDialog(UserCRUDForm, "Added successfully", "Success", JOptionPane.OK_OPTION);
                userMainForm.updateTable(u);
			}
		});
		
		btnAdd.setBounds(409, 257, 97, 36);
		UserCRUDForm.getContentPane().add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(409, 312, 97, 36);
		UserCRUDForm.getContentPane().add(btnUpdate);
	}
}
