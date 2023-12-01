package com.midterm.studentmanagementsystem.views.User;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.midterm.studentmanagementsystem.dao.LoginHistoryDAO;
import com.midterm.studentmanagementsystem.dao.UserDAO;
import com.midterm.studentmanagementsystem.models.User;
import com.midterm.studentmanagementsystem.utils.Utils;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserDetailForm {
	private String email;
	private UserDAO userDAO;
	private LoginHistoryDAO lhDAO;
	private UserMainForm userMainForm;
	
	private JFrame UserDetailForm;
	private JTextField tfCreatedAt;
	private JTextField tfAge;
	private JTextField tfName;
	private JTextField tfEmail;
	private JTextField tfDob;
	private JTextField tfUpdatedAt;
	private JTextField tfPhone;
	private JTextField tfStatus;
	private JTextField tfRole;

	/**
	 * Create the application.
	 */
	public UserDetailForm(UserDAO userDAO, LoginHistoryDAO lhDAO, String email, UserMainForm userMainForm) {
		this.userDAO = userDAO;
		this.lhDAO = lhDAO;
		this.email = email;
		this.userMainForm = userMainForm;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		UserDetailForm = new JFrame();
		UserDetailForm.setBounds(100, 100, 888, 336);
		UserDetailForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		UserDetailForm.getContentPane().setLayout(null);
		
		User user = userDAO.getById(email);
		
		if (user == null) {
			JOptionPane.showMessageDialog(UserDetailForm, "This user may not be existed in the system anymore", "There is something wrong", JOptionPane.OK_OPTION);
			return;
		}
		
		JLabel lblAvatar = new JLabel("This is an avatar");
		lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvatar.setBounds(22, 27, 176, 187);
		UserDetailForm.getContentPane().add(lblAvatar);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblEmail.setBounds(223, 27, 69, 23);
		UserDetailForm.getContentPane().add(lblEmail);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblName.setBounds(223, 74, 69, 23);
		UserDetailForm.getContentPane().add(lblName);
		
		JLabel lblAge = new JLabel("Age:");
		lblAge.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblAge.setBounds(223, 126, 69, 23);
		UserDetailForm.getContentPane().add(lblAge);
		
		JLabel lblDateOfBirth = new JLabel("Date of Birth:");
		lblDateOfBirth.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblDateOfBirth.setBounds(542, 71, 88, 23);
		UserDetailForm.getContentPane().add(lblDateOfBirth);
		
		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblPhone.setBounds(542, 123, 69, 23);
		UserDetailForm.getContentPane().add(lblPhone);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblStatus.setBounds(542, 26, 69, 23);
		UserDetailForm.getContentPane().add(lblStatus);
		
		JLabel lblCreatedAt = new JLabel("Created At:");
		lblCreatedAt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblCreatedAt.setBounds(223, 172, 88, 23);
		UserDetailForm.getContentPane().add(lblCreatedAt);
		
		JLabel lblUpdatedAt = new JLabel("Updated At:");
		lblUpdatedAt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblUpdatedAt.setBounds(542, 218, 88, 23);
		UserDetailForm.getContentPane().add(lblUpdatedAt);
		
		tfCreatedAt = new JTextField();
		tfCreatedAt.setEditable(false);
		tfCreatedAt.setBounds(309, 176, 191, 20);
		UserDetailForm.getContentPane().add(tfCreatedAt);
		tfCreatedAt.setColumns(10);
		
		tfAge = new JTextField();
		tfAge.setEditable(false);
		tfAge.setColumns(10);
		tfAge.setBounds(309, 130, 191, 20);
		UserDetailForm.getContentPane().add(tfAge);
		
		tfName = new JTextField();
		tfName.setEditable(false);
		tfName.setColumns(10);
		tfName.setBounds(309, 78, 191, 20);
		UserDetailForm.getContentPane().add(tfName);
		
		tfEmail = new JTextField();
		tfEmail.setEditable(false);
		tfEmail.setColumns(10);
		tfEmail.setBounds(309, 31, 191, 20);
		UserDetailForm.getContentPane().add(tfEmail);
		
		tfDob = new JTextField();
		tfDob.setEditable(false);
		tfDob.setColumns(10);
		tfDob.setBounds(640, 77, 191, 20);
		UserDetailForm.getContentPane().add(tfDob);
		
		tfUpdatedAt = new JTextField();
		tfUpdatedAt.setEditable(false);
		tfUpdatedAt.setColumns(10);
		tfUpdatedAt.setBounds(640, 224, 191, 20);
		UserDetailForm.getContentPane().add(tfUpdatedAt);
		
		tfPhone = new JTextField();
		tfPhone.setEditable(false);
		tfPhone.setColumns(10);
		tfPhone.setBounds(640, 129, 191, 20);
		UserDetailForm.getContentPane().add(tfPhone);
		
		tfStatus = new JTextField();
		tfStatus.setEditable(false);
		tfStatus.setColumns(10);
		tfStatus.setBounds(640, 30, 191, 20);
		UserDetailForm.getContentPane().add(tfStatus);
		
		JButton btnChangeAvatar = new JButton("Change Avatar");
		btnChangeAvatar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png",
						"gif", "bmp", "svg");
				chooser.setFileFilter(imageFilter);

				int result = chooser.showOpenDialog(UserDetailForm);

				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();

					if (!Utils.checkImageFile(selectedFile)) {
						JOptionPane.showMessageDialog(UserDetailForm, "Please upload an image",
								"There is something wrong", JOptionPane.OK_OPTION);
						return;
					}

					BufferedImage changedImage = null;
					try {
						changedImage = ImageIO.read(selectedFile);

						int labelWidth = lblAvatar.getWidth();
						int labelHeight = lblAvatar.getHeight();

						ImageIcon imgIcon = Utils.stretchImage(changedImage, labelWidth, labelHeight);
						lblAvatar.setIcon(imgIcon);
						Utils.changeAvatar(selectedFile, user.getEmail());
					} catch (IOException ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
		});
		btnChangeAvatar.setBounds(22, 223, 176, 34);
		UserDetailForm.getContentPane().add(btnChangeAvatar);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDetailForm.dispose();
				new UserCRUDForm(userDAO, lhDAO, userMainForm, email, false);
			}
		});
		btnUpdate.setBounds(223, 223, 128, 44);
		UserDetailForm.getContentPane().add(btnUpdate);
		
		JLabel lblRole = new JLabel("Role:");
		lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblRole.setBounds(542, 172, 69, 23);
		UserDetailForm.getContentPane().add(lblRole);
		
		tfRole = new JTextField();
		tfRole.setEditable(false);
		tfRole.setColumns(10);
		tfRole.setBounds(640, 176, 191, 20);
		UserDetailForm.getContentPane().add(tfRole);
		
		tfEmail.setText(user.getEmail());
		tfName.setText(user.getName());
		tfAge.setText(user.getAge()+"");

		if (user.getDob() != null) {
			tfDob.setText(user.getDob().toString());
		} else {
			tfDob.setText("N/A");
		}
		
		tfPhone.setText(user.getPhone());
		tfRole.setText(user.getRole());
		tfStatus.setText(user.getStatus());
		
		if (user.getCreatedAt() != null) {
		    tfCreatedAt.setText(user.getCreatedAt().toString());
		} else {
		    tfCreatedAt.setText("N/A");
		}

		if (user.getUpdatedAt() != null) {
		    tfUpdatedAt.setText(user.getUpdatedAt().toString());
		} else {
		    tfUpdatedAt.setText("N/A");
		}
		
		String baseResourcesDir = "src/main/resources";
		Path filePath = Paths.get(baseResourcesDir, "default-avatar.png");

		Path directoryPath = Paths.get(baseResourcesDir, user.getEmail());
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
		
		UserDetailForm.setVisible(true);
	}
}
