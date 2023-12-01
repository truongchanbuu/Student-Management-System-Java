package com.midterm.studentmanagementsystem.views.User;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.midterm.studentmanagementsystem.dao.LoginHistoryDAO;
import com.midterm.studentmanagementsystem.models.LoginHistory;

import javax.swing.JScrollPane;

public class UserLoginHistoryForm {
	private LoginHistoryDAO lhDAO;

	private JFrame UserLoginHistoryForm;
	private JTable loginHistoryTable;

	/**
	 * Create the application.
	 */
	public UserLoginHistoryForm(LoginHistoryDAO lhDAO) {
		this.lhDAO = lhDAO;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		UserLoginHistoryForm = new JFrame();
		UserLoginHistoryForm.setTitle("Student Management System");
		UserLoginHistoryForm.setBounds(100, 100, 479, 553);
		UserLoginHistoryForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		UserLoginHistoryForm.getContentPane().setLayout(null);

		JLabel lblIntroduction = new JLabel("Login History");
		lblIntroduction.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblIntroduction.setBounds(10, 23, 138, 27);
		UserLoginHistoryForm.getContentPane().add(lblIntroduction);

		JScrollPane scrollPane = new JScrollPane();
		
		loginHistoryTable = new JTable();
		String[] columnNames = { "ID", "Email", "Login Time" };

		List<LoginHistory> histories = lhDAO.getAll();
		
		DefaultTableModel dataModel = new DefaultTableModel(null, columnNames);
		for (LoginHistory lh : histories) {
			Vector<Object> rowData = new Vector<>();
			rowData.add(lh.getId());
			rowData.add(lh.getEmail());
			rowData.add(lh.getHistory());
			
			dataModel.addRow(rowData);
		}

		loginHistoryTable.setModel(dataModel);
		scrollPane.setViewportView(loginHistoryTable);
		scrollPane.setBounds(10, 71, 443, 370);
		UserLoginHistoryForm.getContentPane().add(scrollPane);
		
		UserLoginHistoryForm.setVisible(true);
	}
}
