package com.midterm.studentmanagementsystem.views.User;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.swing.JTextField;

import com.midterm.studentmanagementsystem.dao.LoginHistoryDAO;
import com.midterm.studentmanagementsystem.dao.UserDAO;
import com.midterm.studentmanagementsystem.models.User;
import com.midterm.studentmanagementsystem.utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

public class UserCRUDForm {
	private UserDAO userDAO;
	private LoginHistoryDAO lhDAO;
	private UserMainForm userMainForm;
	private String email;
	private String avatarName;

	private boolean isAddMode;
	private JFrame UserCRUDForm;
	private JTextField tfEmail;
	private JTextField tfName;
	private JTextField tfPhone;
	private JPasswordField tfPassword;
	private JPasswordField tfConfirmPassword;
	private JLabel lblAvatar;
	private JFormattedTextField tfDob;
	private JComboBox<String> cbbRole;
	private JComboBox<String> cbbStatus;

	/**
	 * Create the application.
	 */
	public UserCRUDForm(UserDAO userDAO, LoginHistoryDAO lhDAO, UserMainForm userMainForm, String email, boolean isAddMode) {
		this.userDAO = userDAO;
		this.userMainForm = userMainForm;
		this.isAddMode = isAddMode;
		this.email = email;
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

		lblAvatar = new JLabel("This is avatar");
		lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvatar.setSize(500, 500);
		lblAvatar.setBounds(429, 29, 153, 174);

		UserCRUDForm.getContentPane().add(lblAvatar);

		tfPassword = new JPasswordField();
		tfPassword.setBounds(148, 155, 220, 20);
		UserCRUDForm.getContentPane().add(tfPassword);

		tfConfirmPassword = new JPasswordField();
		tfConfirmPassword.setBounds(148, 197, 220, 20);
		UserCRUDForm.getContentPane().add(tfConfirmPassword);

		tfDob = new JFormattedTextField(dateFormatter);
		tfDob.setColumns(10);
		tfDob.setBounds(148, 112, 220, 20);
		UserCRUDForm.getContentPane().add(tfDob);

		String[] options = { "Employee", "Manager", "Admin" };
		cbbRole = new JComboBox<>(options);
		cbbRole.setBounds(143, 278, 225, 22);
		UserCRUDForm.getContentPane().add(cbbRole);

		String[] statuses = { "Normal", "Locked" };
		cbbStatus = new JComboBox<>(statuses);
		cbbStatus.setBounds(143, 319, 225, 22);
		UserCRUDForm.getContentPane().add(cbbStatus);

		JButton btnAdd = new JButton("Add");
		JButton btnUpdate = new JButton("Update");
		JButton btnDelete = new JButton("Delete");
		JButton btnReset = new JButton("Reset");
		JButton btnChangeAvatar = new JButton("Change Avatar");

		if (isAddMode) {
			btnUpdate.setEnabled(false);
			btnDelete.setEnabled(false);
		} else {
			tfEmail.setEditable(false);
			btnAdd.setEnabled(false);
		}

		// Reset all fields
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfEmail.setText("");
				tfName.setText("");
				tfPassword.setText("");
				tfConfirmPassword.setText("");
				tfPhone.setText("");
			}
		});
		btnReset.setBounds(516, 257, 97, 36);
		UserCRUDForm.getContentPane().add(btnReset);

		btnDelete.setBounds(516, 312, 97, 36);
		UserCRUDForm.getContentPane().add(btnDelete);

		User selectedUser = userDAO.getById(email);
		if (selectedUser == null && !isAddMode) {
			JOptionPane.showMessageDialog(UserCRUDForm, "There is no user to update", "There is something wrong",
					JOptionPane.OK_OPTION);
			return;
		}

		if (selectedUser != null && !isAddMode) {
			tfEmail.setText(selectedUser.getEmail());
			tfName.setText(selectedUser.getName());

			if (selectedUser.getDob() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				String formattedDate = dateFormat.format(selectedUser.getDob());
				tfDob.setValue(formattedDate);
			}

			tfPhone.setText(selectedUser.getPhone());
			cbbRole.setSelectedItem(selectedUser.getRole());
			cbbStatus.setSelectedItem(selectedUser.getStatus());
		}

		// Set image
		String baseResourcesDir = "src/main/resources";
		avatarName = "default-avatar.png";
		Path filePath = Paths.get(baseResourcesDir, avatarName);

		if (email != null) {
			Path directoryPath = Paths.get(baseResourcesDir, email);
			File directory = directoryPath.toFile();

			if (directory.exists() && directory.isDirectory()) {
				try (Stream<Path> pathStream = Files.find(directoryPath, 1, (p, basicFileAttributes) -> {
					String fileName = p.getFileName().toString();
					return Pattern.compile("^avatar.*", Pattern.CASE_INSENSITIVE).matcher(fileName).matches();
				})) {
					Path foundPath = pathStream.findFirst().orElse(null);

					if (foundPath != null) {
						filePath = foundPath;
					}
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				}
			}
		}

		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filePath.toUri()));

			int labelWidth = lblAvatar.getWidth();
			int labelHeight = lblAvatar.getHeight();

			ImageIcon imgIcon = Utils.stretchImage(image, labelWidth, labelHeight);
			lblAvatar.setIcon(imgIcon);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "There is something wrong", JOptionPane.CLOSED_OPTION);
		}

		// Delete user
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmDeletion = JOptionPane.showConfirmDialog(UserCRUDForm,
						"Do you really want to delete " + selectedUser.getEmail() + "?", "DELETE USER",
						JOptionPane.YES_NO_OPTION);

				if (confirmDeletion == JOptionPane.YES_OPTION) {
					lhDAO.deleteAllByEmail(selectedUser.getEmail());
					boolean isDeleted = userDAO.delete(selectedUser);
					if (!isDeleted) {
						JOptionPane.showMessageDialog(UserCRUDForm, "Failed to delete", "Failed", JOptionPane.OK_OPTION);
						return;
					}
					
					JOptionPane.showMessageDialog(UserCRUDForm, "Deleted successful", "Success", JOptionPane.OK_OPTION);
					userMainForm.updateTable(selectedUser, true);
					UserCRUDForm.dispatchEvent(new WindowEvent(UserCRUDForm, WindowEvent.WINDOW_CLOSING));
				
				}
			}
		});

		// Change picture
		btnChangeAvatar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png",
						"gif", "bmp", "svg");
				chooser.setFileFilter(imageFilter);
		        chooser.setDialogTitle("Choose picture");

				int result = chooser.showOpenDialog(UserCRUDForm);

				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();

					if (!Utils.checkImageFile(selectedFile)) {
						JOptionPane.showMessageDialog(UserCRUDForm, "Please upload an image",
								"There is something wrong", JOptionPane.OK_OPTION);
						return;
					}
					avatarName = selectedFile.getName();

					BufferedImage changedImage = null;
					try {
						changedImage = ImageIO.read(selectedFile);

						int labelWidth = lblAvatar.getWidth();
						int labelHeight = lblAvatar.getHeight();

						ImageIcon imgIcon = Utils.stretchImage(changedImage, labelWidth, labelHeight);
						imgIcon.setDescription(selectedFile.getPath());
						lblAvatar.setIcon(imgIcon);
					} catch (IOException ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
		});
		btnChangeAvatar.setBounds(429, 214, 153, 32);
		UserCRUDForm.getContentPane().add(btnChangeAvatar);

		// Update user
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addOrUpdate(isAddMode);
			}
		});
		btnUpdate.setBounds(409, 312, 97, 36);
		UserCRUDForm.getContentPane().add(btnUpdate);

		// Add a new user
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addOrUpdate(isAddMode);
			}
		});

		btnAdd.setBounds(409, 257, 97, 36);
		UserCRUDForm.getContentPane().add(btnAdd);
	}

	public void addOrUpdate(boolean isAddMode) {
		String email = tfEmail.getText();
		String name = tfName.getText();
		Object dob = tfDob.getValue();
		String phone = tfPhone.getText();
		Object status = cbbStatus.getSelectedItem();
		Object role = cbbRole.getSelectedItem();
		char[] password = tfPassword.getPassword();
		char[] confirmPassword = tfConfirmPassword.getPassword();

		Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
		Pattern passwordPattern = Pattern
				.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
		Pattern phonePattern = Pattern.compile("^\\d{10}$");

		if (email.isEmpty() || !emailPattern.matcher(email).matches()) {
			JOptionPane.showMessageDialog(UserCRUDForm, "Invalid email", "Invalid input", JOptionPane.OK_OPTION);
			return;
		}

		if (dob == null || !Utils.isValidDate(dob.toString())) {
			JOptionPane.showMessageDialog(UserCRUDForm, "Invalid date of birth", "Invalid input",
					JOptionPane.OK_OPTION);
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

		Date currentDate = new Date(System.currentTimeMillis());

		Date dobDate = Utils.convertToDate(dob.toString());
		int age = Utils.calculateAge(dobDate);

		User u = new User(email, null, name, age, dobDate, status.toString(), phone, role.toString(), null, null);

		if (isAddMode) {
			String passwordStr = new String(password);
			if (!passwordPattern.matcher(passwordStr).matches() || !Arrays.equals(password, confirmPassword)) {
				JOptionPane.showMessageDialog(UserCRUDForm, "Invalid password or confirmed password does not match",
						"Invalid input", JOptionPane.OK_OPTION);
				return;
			}

			u.setPassword(passwordStr);
			u.setCreatedAt(currentDate);
			boolean isAdded = userDAO.add(u);

			if (!isAdded) {
				JOptionPane.showMessageDialog(UserCRUDForm, "Added failed",
						"Failed to add. Please check again or change your email which maybe existed",
						JOptionPane.OK_OPTION);
				return;
			}

			JOptionPane.showMessageDialog(UserCRUDForm, "Added successfully", "Success", JOptionPane.OK_OPTION);
		} else {
			if (password.length > 0) {
				String passwordStr = new String(password);
				if (!passwordPattern.matcher(passwordStr).matches() || !Arrays.equals(password, confirmPassword)) {
					JOptionPane.showMessageDialog(UserCRUDForm, "Invalid password or confirmed password does not match",
							"Invalid input", JOptionPane.OK_OPTION);
					return;
				}

				u.setPassword(passwordStr);
			}

			u.setUpdatedAt(currentDate);
			boolean isUpdate = userDAO.update(u);

			if (!isUpdate) {
				JOptionPane.showMessageDialog(UserCRUDForm, "Updated failed",
						"Failed to update. Please check again or change your email which maybe existed",
						JOptionPane.OK_OPTION);
				return;
			}

			JOptionPane.showMessageDialog(UserCRUDForm, "Updated successfully", "Success", JOptionPane.OK_OPTION);
		}

		if (!avatarName.equalsIgnoreCase("default-avatar.png")) {
			Icon avatarIcon = lblAvatar.getIcon();
			String imagePath = ((ImageIcon) avatarIcon).getDescription();

			File avatarFile = new File(imagePath);
			Utils.changeAvatar(avatarFile, email);
		}

		UserCRUDForm.dispatchEvent(new WindowEvent(UserCRUDForm, WindowEvent.WINDOW_CLOSING));
		userMainForm.updateTable(u, false);
	}
}
