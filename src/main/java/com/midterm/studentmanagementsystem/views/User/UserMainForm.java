package com.midterm.studentmanagementsystem.views.User;
import com.midterm.studentmanagementsystem.dao.CertificateDAO;
import com.midterm.studentmanagementsystem.dao.StudentDAO;
import com.midterm.studentmanagementsystem.views.Auth.LoginForm;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import java.awt.*;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import com.midterm.studentmanagementsystem.dao.LoginHistoryDAO;
import com.midterm.studentmanagementsystem.dao.UserDAO;
import com.midterm.studentmanagementsystem.models.User;
import com.midterm.studentmanagementsystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;


public class UserMainForm{
	private UserDAO userDAO;
	private LoginHistoryDAO lhDAO;

	private StudentDAO studentDAO;
	private CertificateDAO certificateDAO;
	private String email;

	private JFrame UserMainForm;
	private JTable userTable;
	private JTextField tfSearch;
	private List<JFrame> openWindows;


	/**
	 * Create the application.
	 */
	public UserMainForm(UserDAO userDAO, LoginHistoryDAO lhDAO, String email) {
		this.userDAO = userDAO;
		this.email = email;
		this.lhDAO = lhDAO;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		UserMainForm = new JFrame();
		UserMainForm.getContentPane().setBackground(UIManager.getColor("CheckBox.background"));
		UserMainForm.setTitle("Student Management System");
		UserMainForm.setBounds(100, 100, 740, 600);
		UserMainForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		UserMainForm.getContentPane().setLayout(null);

		openWindows = new java.util.ArrayList<>();

		JLabel lblIntro = new JLabel("USER");
		lblIntro.setBounds(10, 22, 49, 27);
		lblIntro.setFont(new Font("Segoe UI", Font.BOLD, 20));
		UserMainForm.getContentPane().add(lblIntro);
		List<User> users = userDAO.getAll();

		String[] columnNames = {"Email", "Name", "Age", "Date of Birth", "Phone", "Status", "Role", "Created At",
				"Updated At"};
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
		scrollPane.setBounds(10, 102, 703, 277);
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
		final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
		userTable.setRowSorter(sorter);

		scrollPane.setViewportView(userTable);

		userTable.setModel(model);

		final JTableHeader header = userTable.getTableHeader();
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int columnIndex = header.columnAtPoint(e.getPoint());
				showSortPopupMenu(header, e.getX(), e.getY(), columnIndex, sorter);
			}
		});

		// Add user
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UserCRUDForm(userDAO, lhDAO, UserMainForm.this, null, true);
			}
		});

		btnAdd.setBounds(10, 390, 111, 43);
		UserMainForm.getContentPane().add(btnAdd);

		// Edit user
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = userTable.getSelectedRow();

				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(UserMainForm, "No selected row", "There is something wrong",
							JOptionPane.OK_OPTION);
					return;
				}

				String email = (String) userTable.getValueAt(selectedRow, 0);
				new UserCRUDForm(userDAO, lhDAO, UserMainForm.this, email, false);
			}
		});
		userTable.addMouseListener(new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Check for double-click
				if (e.getClickCount() == 2) {
					int selectedRow = userTable.getSelectedRow();
					if (selectedRow != -1) {
						String email = (String) userTable.getValueAt(selectedRow, 0);
						openUserDetailForm(email);
					}
				}
			}
		});

		btnEdit.setBounds(131, 390, 111, 43);
		UserMainForm.getContentPane().add(btnEdit);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(252, 390, 111, 43);
		UserMainForm.getContentPane().add(btnDelete);

		tfSearch = new JTextField();
		tfSearch.setBounds(10, 60, 315, 31);
		UserMainForm.getContentPane().add(tfSearch);
		tfSearch.setColumns(10);

		// Search
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchText = tfSearch.getText();

				List<User> searchResult = userDAO.searchUser(searchText);

				if (searchResult.size() == 0) {
					JOptionPane.showMessageDialog(UserMainForm, "Not Found", "NOT FOUND", JOptionPane.OK_OPTION);
					return;
				}

				DefaultTableModel model = (DefaultTableModel) userTable.getModel();
				model.setRowCount(0);

				for (User user : searchResult) {
					Object[] rowData = {user.getEmail(), user.getName(), user.getAge(), user.getDob(), user.getPhone(),
							user.getStatus(), user.getRole(), user.getCreatedAt(), user.getUpdatedAt()};
					model.addRow(rowData);
				}

				// Notify the table that the model has changed
				model.fireTableDataChanged();
			}
		});
		btnSearch.setBounds(335, 60, 100, 30);
		UserMainForm.getContentPane().add(btnSearch);

		JButton btnUser = new JButton(email);
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UserDetailForm(userDAO, lhDAO, email, UserMainForm.this);
			}
		});
		btnUser.setBackground(Color.LIGHT_GRAY);
		btnUser.setHorizontalAlignment(SwingConstants.RIGHT);
		btnUser.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnUser.setBounds(548, 22, 165, 23);
		UserMainForm.getContentPane().add(btnUser);

		JButton btnViewLoginHistory = new JButton("View Login History");
		btnViewLoginHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UserLoginHistoryForm(lhDAO);
			}
		});
		btnViewLoginHistory.setBounds(548, 390, 165, 43);
		UserMainForm.getContentPane().add(btnViewLoginHistory);

		JButton btnExport = new JButton("Export");
		btnExport.setBounds(613, 60, 100, 30);
		UserMainForm.getContentPane().add(btnExport);

		JButton btnImport = new JButton("Import");
		btnImport.setBounds(503, 61, 100, 30);
		UserMainForm.getContentPane().add(btnImport);

		// Delete user
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedUserIndex = userTable.getSelectedRow();

				if (selectedUserIndex < 0) {
					JOptionPane.showMessageDialog(UserMainForm, "No selected row", "There is something wrong",
							JOptionPane.OK_OPTION);
					return;
				}

				String email = (String) userTable.getValueAt(selectedUserIndex, 0);
				int deletionResult = JOptionPane.showConfirmDialog(UserMainForm,
						"Do you really want to delete " + email + "?", "DELELTE USER", JOptionPane.YES_NO_OPTION);

				if (deletionResult == JOptionPane.YES_OPTION) {
					User deletedUser = userDAO.getById(email);

					if (deletedUser == null) {
						JOptionPane.showMessageDialog(UserMainForm, "There is no user to delete", "DELETE USER",
								JOptionPane.OK_OPTION);
						return;
					}

					lhDAO.deleteAllByEmail(deletedUser.getEmail());
					userDAO.delete(deletedUser);
					updateTable(deletedUser, true);
				}
			}
		});

		// Export
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Choose Directory to store file");
				FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
				FileNameExtensionFilter xlsxFilter = new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx");
				chooser.addChoosableFileFilter(csvFilter);
				chooser.addChoosableFileFilter(xlsxFilter);

				int result = chooser.showSaveDialog(UserMainForm);

				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					String extension = "";
					String description = chooser.getFileFilter().getDescription();

					if (selectedFile.getName().contains(".")) {
						extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
					} else {
						if (description.endsWith("CSV Files (*.csv)")) {
							extension = "csv";
						} else if (description.endsWith("Excel Files (*.xlsx)")) {
							extension = "xlsx";
						} else {
							extension = "csv";
						}
					}

					String filePath = selectedFile.getPath();
					if (!filePath.toLowerCase().endsWith("." + extension)) {
						selectedFile = new File(filePath + "." + extension);
					}

					List<User> users = userDAO.getAll();

					if (extension.equals("csv")) {
						Utils.exportToCSV(users, selectedFile.getPath());
					} else if (extension.equals("xlsx")) {
						Utils.exportToExcel(users, selectedFile.getPath());
					} else {
						JOptionPane.showMessageDialog(UserMainForm, "This extension is not supported!");
						return;
					}

					JOptionPane.showMessageDialog(UserMainForm, "Exported", "Success", JOptionPane.OK_OPTION);
				}
			}
		});

		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmResult = JOptionPane.showConfirmDialog(UserMainForm,
						"This action can affect to the data. Are you sure to import?");

				if (confirmResult == JOptionPane.OK_OPTION) {
					JFileChooser chooser = new JFileChooser();
					chooser.setDialogTitle("Choose a file");
					FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
					FileNameExtensionFilter xlsxFilter = new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx");
					chooser.addChoosableFileFilter(csvFilter);
					chooser.addChoosableFileFilter(xlsxFilter);

					int result = chooser.showOpenDialog(UserMainForm);

					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = chooser.getSelectedFile();
						String description = chooser.getFileFilter().getDescription();

						List<User> users = new ArrayList<>();

						if (description.endsWith("CSV Files (*.csv)") || selectedFile.getName().endsWith(".csv")) {
							users = Utils.importDataFromCsvFile(selectedFile.getPath(), User.class);
						} else {
							users = Utils.importDataFromExcelFile(selectedFile.getPath(), User.class);
						}

						for (User u : users) {
							User existedUser = userDAO.getById(u.getEmail());

							if (existedUser != null) {
								int resultConfirmChange = JOptionPane.showConfirmDialog(UserMainForm,
										"The user \"" + existedUser.getEmail()
												+ "\" is already in the data. Do you really want to changet it?");

								if (resultConfirmChange == JOptionPane.YES_OPTION) {
									boolean isDeleteAllLoginHistory = lhDAO.deleteAllByEmail(existedUser.getEmail());
									boolean isDeleteExistedUser = userDAO.delete(existedUser);

									if (!isDeleteExistedUser && !isDeleteAllLoginHistory) {
										JOptionPane.showMessageDialog(UserMainForm,
												"Cannot import " + existedUser.getEmail());
										continue;
									}
								} else {
									continue;
								}
							}

							boolean isAdded = userDAO.add(u);
							if (!isAdded) {
								JOptionPane.showMessageDialog(UserMainForm, "Cannot import " + u.getEmail());
								continue;
							}

							updateTable(u, false);
						}

						JOptionPane.showMessageDialog(UserMainForm, "Imported", "Success", JOptionPane.OK_OPTION);
					}
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

	private void showSortPopupMenu(JTableHeader header, int x, int y, final int columnIndex, final TableRowSorter<DefaultTableModel> sorter) {
		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem ascendingItem = new JMenuItem("Sort Ascending");
		ascendingItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sortTable(sorter, columnIndex, SortOrder.ASCENDING);
			}
		});

		JMenuItem descendingItem = new JMenuItem("Sort Descending");
		descendingItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sortTable(sorter, columnIndex, SortOrder.DESCENDING);
			}
		});

		popupMenu.add(ascendingItem);
		popupMenu.add(descendingItem);

		popupMenu.show(header, x, y);
	}

	private void sortTable(TableRowSorter<DefaultTableModel> sorter, int columnIndex, SortOrder sortOrder) {
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(columnIndex, sortOrder));

		sorter.setSortKeys(sortKeys);
		sorter.sort();
	}

	private void openUserDetailForm(String email) {
		UserDetailForm studentDetailForm = new UserDetailForm(userDAO, lhDAO, email, UserMainForm.this);
		// Additional code to set up and display the StudentDetailForm as needed.
	}

}