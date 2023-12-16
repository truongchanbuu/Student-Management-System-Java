package com.midterm.studentmanagementsystem.views.User;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import com.midterm.studentmanagementsystem.dao.CertificateDAO;
import com.midterm.studentmanagementsystem.dao.LoginHistoryDAO;
import com.midterm.studentmanagementsystem.dao.StudentDAO;
import com.midterm.studentmanagementsystem.dao.UserDAO;
import com.midterm.studentmanagementsystem.models.Student;
import com.midterm.studentmanagementsystem.models.User;
import com.midterm.studentmanagementsystem.utils.Utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class StudentMainView{

    private StudentDAO studentDAO;
    private LoginHistoryDAO lhDAO;
    private CertificateDAO certificateDAO;
    private UserDAO userDAO;
    private String sid;
    private String email;

    private JFrame StudentMainView;
    private JFrame StudentCRUDForm;
    private JTable studentTable;
    private JTextField tfSearch;

    public StudentMainView(UserDAO userDAO, StudentDAO studentDAO, CertificateDAO certificateDAO, LoginHistoryDAO lhDAO, String email){
        this.userDAO = userDAO;
    	this.studentDAO = studentDAO;
        this.certificateDAO = certificateDAO;
        this.lhDAO = lhDAO;
        this.email = email;
        initialize();
    }

    private void initialize(){
        StudentMainView = new JFrame();
        StudentMainView.getContentPane().setBackground(UIManager.getColor("CheckBox.background"));
        StudentMainView.setTitle("Student Management System");
        StudentMainView.setBounds(100, 100, 790, 500);
        StudentMainView.setLocationRelativeTo(null);
        StudentMainView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        StudentMainView.getContentPane().setLayout(null);
        StudentMainView.setResizable(false);

        JLabel lblIntro = new JLabel("STUDENT");
        lblIntro.setBounds(10, 22, 100, 27);
        lblIntro.setFont(new Font("Segoe UI", Font.BOLD, 20));
        StudentMainView.getContentPane().add(lblIntro);
        List<Student> students = studentDAO.getAll();

        String[] columnNames = { "ID", "Name", "Date of Birth", "Gender", "Class Name", "Department", "Major", "Course Year","Edu Type","Created At",
                "Updated At" };
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        for (Student s : students) {
            Vector<Object> rowData = new Vector<>();
            rowData.add(s.getSid());
            rowData.add(s.getName());
            rowData.add(s.getDob());
            rowData.add(s.getGender());
            rowData.add(s.getClassName());
            rowData.add(s.getDepartment());
            rowData.add(s.getMajor());
            rowData.add(s.getCourseYear());
            rowData.add(s.getEduType());
            rowData.add(s.getCreatedAt());
            rowData.add(s.getUpdatedAt());
            model.addRow(rowData);
        }

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 102, 750, 277);
        StudentMainView.getContentPane().add(scrollPane);

        // Load user to JTable
        studentTable = new JTable() {
            private static final long serialVersionUID = 1L;

            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        studentTable.setRowSorter(sorter);

        scrollPane.setViewportView(studentTable);

        studentTable.setModel(model);

        JTableHeader header = studentTable.getTableHeader();
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columnIndex = header.columnAtPoint(e.getPoint());
                showSortPopupMenu(header, e.getX(), e.getY(), columnIndex, sorter);
            }
        });

        // Add a MouseListener to the studentTable
        studentTable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check for double-click
                if (e.getClickCount() == 2) {
                    int selectedRow = studentTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String studentId = (String) studentTable.getValueAt(selectedRow, 0);
                        openStudentDetailForm(studentId);
                    }
                }
            }
        });


        // Add user
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddStudentForm addStudentForm = new AddStudentForm(studentDAO, StudentMainView.this);
            }
        });

        btnAdd.setBounds(10, 390, 111, 43);
        StudentMainView.getContentPane().add(btnAdd);

        // Edit user
        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentTable.getSelectedRow();

                if (selectedRow < 0) {
                    JOptionPane.showMessageDialog(StudentMainView, "No selected row", "There is something wrong",
                            JOptionPane.OK_OPTION);
                    return;
                }

                String id = (String) studentTable.getValueAt(selectedRow, 0);
                new StudentCRUDForm(studentDAO, id, StudentMainView.this, false);
            }
        });
        btnEdit.setBounds(131, 390, 111, 43);
        StudentMainView.getContentPane().add(btnEdit);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(252, 390, 111, 43);
        StudentMainView.getContentPane().add(btnDelete);

        tfSearch = new JTextField();
        tfSearch.setBounds(10, 60, 315, 31);
        StudentMainView.getContentPane().add(tfSearch);
        tfSearch.setColumns(10);

        // Search
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchText = tfSearch.getText();

                List<Student> searchResult = studentDAO.searchStudent(searchText);

                if (searchResult.size() == 0) {
                    JOptionPane.showMessageDialog(StudentMainView, "Not Found", "NOT FOUND", JOptionPane.OK_OPTION);
                    return;
                }

                DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
                model.setRowCount(0);

                for (Student student : searchResult) {
                    Object[] rowData = {student.getSid(), student.getName(), student.getDob(),student.getGender(),student.getClassName(),
                    student.getDepartment(), student.getMajor(), student.getCourseYear(),student.getEduType(),student.getCreatedAt(),student.getUpdatedAt()};
                    model.addRow(rowData);
                }

                // Notify the table that the model has changed
                model.fireTableDataChanged();
            }
        });

        btnSearch.setBounds(335, 60, 100, 30);
        StudentMainView.getContentPane().add(btnSearch);

        JButton btnStudent = new JButton(email);
        btnStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UserDetailForm(userDAO, lhDAO, email, null);
            }
        });
        btnStudent.setBackground(Color.LIGHT_GRAY);
        btnStudent.setHorizontalAlignment(SwingConstants.RIGHT);
        btnStudent.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnStudent.setBounds(595, 22, 165, 23);
        StudentMainView.getContentPane().add(btnStudent);

        JButton btnExport = new JButton("Export");
        btnExport.setBounds(660, 60, 100, 30);
        StudentMainView.getContentPane().add(btnExport);

        JButton btnImport = new JButton("Import");
        btnImport.setBounds(550, 60, 100, 30);
        StudentMainView.getContentPane().add(btnImport);
        
        User currentUser = userDAO.getById(email);
        
        if (currentUser.getRole().equalsIgnoreCase("admin")) {
        	JButton btnUserManagement = new JButton("User Management");
            btnUserManagement.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		new UserMainForm(userDAO, lhDAO, email);
            	}
            });
            btnUserManagement.setBounds(595, 390, 165, 43);
            StudentMainView.getContentPane().add(btnUserManagement);
        }
        
        // Delete user
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedStudentIndex = studentTable.getSelectedRow();

                if (selectedStudentIndex < 0) {
                    JOptionPane.showMessageDialog(StudentMainView, "No selected row", "There is something wrong",
                            JOptionPane.OK_OPTION);
                    return;
                }

                String studentId = (String) studentTable.getValueAt(selectedStudentIndex, 0);
                int deletionResult = JOptionPane.showConfirmDialog(StudentMainView,
                        "Do you really want to delete " + studentId + "?", "DELETE USER", JOptionPane.YES_NO_OPTION);

                if (deletionResult == JOptionPane.YES_OPTION) {
                    Student deletedStudent = studentDAO.getById(studentId);

                    if (deletedStudent == null) {
                        JOptionPane.showMessageDialog(StudentMainView, "There is no user to delete", "DELETE USER",
                                JOptionPane.OK_OPTION);
                        return;
                    }

                    studentDAO.delete(deletedStudent);
                    updateStudentTable(deletedStudent, true);
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

                int result = chooser.showSaveDialog(StudentMainView);

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

                    List<Student> students = studentDAO.getAll();

                    if (extension.equals("csv")) {
                        Utils.exportToCSV(students, selectedFile.getPath());
                    } else if (extension.equals("xlsx")) {
                        Utils.exportToExcel(students, selectedFile.getPath());
                    } else {
                        JOptionPane.showMessageDialog(StudentMainView, "This extension is not supported!");
                        return;
                    }

                    JOptionPane.showMessageDialog(StudentMainView, "Exported", "Success", JOptionPane.OK_OPTION);
                }
            }
        });

        btnImport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirmResult = JOptionPane.showConfirmDialog(StudentMainView,
                        "This action can affect to the data. Are you sure to import?");

                if (confirmResult == JOptionPane.OK_OPTION) {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Choose a file");
                    FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
                    FileNameExtensionFilter xlsxFilter = new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx");
                    chooser.addChoosableFileFilter(csvFilter);
                    chooser.addChoosableFileFilter(xlsxFilter);

                    int result = chooser.showOpenDialog(StudentMainView);

                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = chooser.getSelectedFile();
                        String description = chooser.getFileFilter().getDescription();

                        List<Student> students = new ArrayList<>();

                        if (description.endsWith("CSV Files (*.csv)") || selectedFile.getName().endsWith(".csv")) {
                            students = Utils.importDataFromCsvFile(selectedFile.getPath(), Student.class);
                        } else {
                            students = Utils.importDataFromExcelFile(selectedFile.getPath(), Student.class);
                        }

                        for (Student s : students) {
                            Student existedStudent = studentDAO.getById(s.getSid());

                            if (existedStudent != null) {
                                int resultConfirmChange = JOptionPane.showConfirmDialog(StudentMainView,
                                        "The user \"" + existedStudent.getSid()
                                                + "\" is already in the data. Do you really want to change it?");

                                if (resultConfirmChange == JOptionPane.YES_OPTION) {
                                    boolean isDeleteExistedUser = studentDAO.delete(existedStudent);

                                    if (!isDeleteExistedUser) {
                                        JOptionPane.showMessageDialog(StudentMainView,
                                                "Cannot import " + existedStudent.getSid());
                                        continue;
                                    }
                                } else {
                                    continue;
                                }
                            }

                            boolean isAdded = studentDAO.add(s);
                            if (!isAdded) {
                                JOptionPane.showMessageDialog(StudentMainView, "Cannot import " + s.getSid());
                                continue;
                            }

                            updateStudentTable(s, false);
                        }

                        JOptionPane.showMessageDialog(StudentMainView, "Imported", "Success", JOptionPane.OK_OPTION);
                    }
                }
            }
        });
        
        StudentMainView.setVisible(true);
    }
    public void updateStudentTable(Student s, boolean isDeleteMode) {
        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();

        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String idInTable = (String) model.getValueAt(i, 0);
            if (idInTable.equals(s.getSid())) {
                model.removeRow(i);
                break;
            }
        }

        if (!isDeleteMode) {
            Vector<Object> rowData = new Vector<>();
            rowData.add(s.getSid());
            rowData.add(s.getName());
            rowData.add(s.getDob());
            rowData.add(s.getGender());
            rowData.add(s.getClassName());
            rowData.add(s.getDepartment());
            rowData.add(s.getMajor());
            rowData.add(s.getCourseYear());
            rowData.add(s.getEduType());
            rowData.add(s.getCreatedAt());
            rowData.add(s.getUpdatedAt());
            model.addRow(rowData);
        }

        model.fireTableDataChanged();
    }

    private void showSortPopupMenu(JTableHeader header, int x, int y, int columnIndex, TableRowSorter<DefaultTableModel> sorter) {
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

    private void openStudentDetailForm(String studentId) {
        StudentDetailForm studentDetailForm = new StudentDetailForm(studentDAO,certificateDAO,studentId,StudentMainView.this);
        // Additional code to set up and display the StudentDetailForm as needed.
    }

}
