package com.midterm.studentmanagementsystem.views.User;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.midterm.studentmanagementsystem.dao.UserDAO;
import com.midterm.studentmanagementsystem.models.User;

import java.util.List;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class UserMainForm {
	private UserDAO userDAO;

	private JFrame UserMainForm;
	private JTable userTable;
	private JTextField tfSearch;

	/**
	 * Create the application.
	 */
	public UserMainForm(UserDAO userDAO) {
		this.userDAO = userDAO;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		UserMainForm = new JFrame();
		UserMainForm.setTitle("Student Management System");
		UserMainForm.setBounds(100, 100, 765, 484);
		UserMainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserMainForm.getContentPane().setLayout(null);

		JLabel lblIntro = new JLabel("USER");
		lblIntro.setBounds(10, 22, 49, 27);
		lblIntro.setFont(new Font("Segoe UI", Font.BOLD, 20));
		UserMainForm.getContentPane().add(lblIntro);
		List<User> users = userDAO.getAll();
		String[] columnNames = { "Email", "Name", "Age", "Date of Birth", "Phone", "Status", "Role", "Created At",
				"Updated At" };

		DefaultTableModel model = new DefaultTableModel(null, columnNames);
		
		for (User user : users) {
			Vector<Object> rowData = new Vector<>();
			rowData.add(user.getEmail());
			rowData.add(user.getName());
			rowData.add(user.getAge());
			rowData.add(user.getDob());
			rowData.add(user.getPhone());
			rowData.add(user.getStatus());
			rowData.add(user.getRole());
			rowData.add(user.getCreatedAt());
			rowData.add(user.getUpdatedAt());
			model.addRow(rowData);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 102, 725, 277);
		UserMainForm.getContentPane().add(scrollPane);
		
				// Load user to JTable
				userTable = new JTable() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
		
					public boolean editCellAt(int row, int column, java.util.EventObject e) {
						return false;
					}
				};
				scrollPane.setViewportView(userTable);

		userTable.setModel(model);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UserCRUDForm(userDAO, UserMainForm.this, null, true);
			}
		});
		
		btnAdd.setBounds(10, 390, 111, 43);
		UserMainForm.getContentPane().add(btnAdd);

		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = userTable.getSelectedRow();
				
				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(UserMainForm, "No selected row", "There is something wrong", JOptionPane.OK_OPTION);
					return;
				}
				
				String email = (String) userTable.getValueAt(selectedRow, 0);
		        new UserCRUDForm(userDAO, UserMainForm.this, email, false);
			}
		});
		btnEdit.setBounds(131, 390, 111, 43);
		UserMainForm.getContentPane().add(btnEdit);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(252, 390, 111, 43);
		UserMainForm.getContentPane().add(btnDelete);

		tfSearch = new JTextField();
		tfSearch.setBounds(10, 60, 615, 31);
		UserMainForm.getContentPane().add(tfSearch);
		tfSearch.setColumns(10);

		JButton button = new JButton("New button");
		button.setBounds(114, 118, 511, -47);
		UserMainForm.getContentPane().add(button);

		JButton btnNewButton = new JButton("Search");
		btnNewButton.setBounds(635, 60, 100, 30);
		UserMainForm.getContentPane().add(btnNewButton);

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedUserIndex = userTable.getSelectedRow();
				
				if (selectedUserIndex < 0) {
					JOptionPane.showMessageDialog(UserMainForm, "No selected row", "There is something wrong", JOptionPane.OK_OPTION);
					return;
				}
				
				String email = (String) userTable.getValueAt(selectedUserIndex, 0);
				int deletionResult = JOptionPane.showConfirmDialog(UserMainForm, "Do you really want to delete " + email + "?", "DELELTE USER", JOptionPane.YES_NO_OPTION);
				
				if (deletionResult == JOptionPane.YES_OPTION) {
					User deletedUser = userDAO.getById(email);
					
					if (deletedUser == null) {
						JOptionPane.showMessageDialog(UserMainForm, "There is no user to delete", "DELETE USER", JOptionPane.OK_OPTION);
						return;
					}
					
					userDAO.delete(deletedUser);
					updateTable(deletedUser, true);
				}
			}
		});
		UserMainForm.setVisible(true);
	}

	public void updateTable(User u, boolean isDeleteMode) {
		DefaultTableModel model = (DefaultTableModel) userTable.getModel();

		int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String emailInTable = (String) model.getValueAt(i, 0);
            if (emailInTable.equals(u.getEmail())) {
                model.removeRow(i);
                break;
            }
        }
		
        if (!isDeleteMode) {
        	Vector<Object> rowData = new Vector<>();
            rowData.add(u.getEmail());
            rowData.add(u.getName());
            rowData.add(u.getAge());
            rowData.add(u.getDob());
            rowData.add(u.getPhone());
            rowData.add(u.getStatus());
            rowData.add(u.getRole());
            rowData.add(u.getCreatedAt());
            rowData.add(u.getUpdatedAt());
            
            model.addRow(rowData);
        }
		
	    model.fireTableDataChanged();	
	}
}
