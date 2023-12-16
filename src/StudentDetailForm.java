package com.midterm.studentmanagementsystem.views.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import com.midterm.studentmanagementsystem.dao.CertificateDAO;
import com.midterm.studentmanagementsystem.dao.StudentDAO;
import com.midterm.studentmanagementsystem.models.Certificate;
import com.midterm.studentmanagementsystem.models.Student;
import com.midterm.studentmanagementsystem.utils.Utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StudentDetailForm {

    private StudentDAO studentDAO;

    private CertificateDAO certificateDAO;
    private StudentMainView studentMainView;

    private JTable certificateTable;

    private String sid;

    private JFrame StudentDetailForm;
    private JTextField tfId;
    private JTextField tfName;
    private JTextField tfDob;
    private JTextField tfGender;
    private JTextField tfClassName;
    private JTextField tfDepartment;
    private JTextField tfMajor;
    private JTextField tfCourseYear;
    private JTextField tfEduType;
    private JTextField tfUpdatedAt;
    private JTextField tfCreatedAt;


    public StudentDetailForm(StudentDAO studentDAO,CertificateDAO certificateDAO,String sid,StudentMainView studentMainView) {
        this.studentDAO = studentDAO;
        this.certificateDAO = certificateDAO;
        this.sid = sid;
        this.studentMainView = studentMainView;
        initialize();
    }


    private void initialize() {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
        StudentDetailForm = new JFrame();
        StudentDetailForm.getContentPane().setBackground(UIManager.getColor("CheckBox.background"));
        StudentDetailForm.setTitle("Student Detail");
        StudentDetailForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        StudentDetailForm.getContentPane().setLayout(null);
        device.setFullScreenWindow(StudentDetailForm);
        Student s = studentDAO.getById(sid);

        if (s == null) {
            JOptionPane.showMessageDialog(StudentDetailForm, "This user may not be existed in the system anymore", "There is something wrong", JOptionPane.OK_OPTION);
            return;
        }

        JLabel lblId = new JLabel("ID:");
        lblId.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblId.setBounds(150, 27, 150, 23);
        StudentDetailForm.getContentPane().add(lblId);

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblName.setBounds(150, 74, 150, 23);
        StudentDetailForm.getContentPane().add(lblName);

        JLabel lblDob = new JLabel("Date of Birth:");
        lblDob.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDob.setBounds(150, 124, 150, 23);
        StudentDetailForm.getContentPane().add(lblDob);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblGender.setBounds(150, 174, 150, 23);
        StudentDetailForm.getContentPane().add(lblGender);

        JLabel lblClassName = new JLabel("Class Name:");
        lblClassName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblClassName.setBounds(150, 224, 150, 23);
        StudentDetailForm.getContentPane().add(lblClassName);

        JLabel lblDepartment = new JLabel("Department:");
        lblDepartment.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDepartment.setBounds(150, 274, 150, 23);
        StudentDetailForm.getContentPane().add(lblDepartment);

        JLabel lblMajor = new JLabel("Major:");
        lblMajor.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblMajor.setBounds(150, 324, 150, 23);
        StudentDetailForm.getContentPane().add(lblMajor);

        JLabel lblCourseYear = new JLabel("Course Year:");
        lblCourseYear.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblCourseYear.setBounds(150, 374, 150, 23);
        StudentDetailForm.getContentPane().add(lblCourseYear);

        JLabel lblEduType = new JLabel("Edu Type:");
        lblEduType.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblEduType.setBounds(150, 424, 150, 23);
        StudentDetailForm.getContentPane().add(lblEduType);

        JLabel lblCreatedAt = new JLabel("Created At:");
        lblCreatedAt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblCreatedAt.setBounds(150, 474, 150, 23);
        StudentDetailForm.getContentPane().add(lblCreatedAt);

        JLabel lblUpdatedAt = new JLabel("Updated At:");
        lblUpdatedAt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblUpdatedAt.setBounds(150, 524, 150, 23);
        StudentDetailForm.getContentPane().add(lblUpdatedAt);

        int yPosition = 31; // Starting y position
        int yOffset = 50;   // Offset to space out the components

        tfId = new JTextField();
        tfId.setEditable(false);
        tfId.setColumns(10);
        tfId.setBounds(309, yPosition, 191, 20);
        StudentDetailForm.getContentPane().add(tfId);

        yPosition += yOffset; // Move to the next line

        tfName = new JTextField();
        tfName.setEditable(false);
        tfName.setColumns(10);
        tfName.setBounds(309, yPosition, 191, 20);
        StudentDetailForm.getContentPane().add(tfName);

        yPosition += yOffset; // Move to the next line

        tfDob = new JTextField();
        tfDob.setEditable(false);
        tfDob.setColumns(10);
        tfDob.setBounds(309, yPosition, 191, 20);
        StudentDetailForm.getContentPane().add(tfDob);

        yPosition += yOffset; // Move to the next line

        tfGender = new JTextField();
        tfGender.setEditable(false);
        tfGender.setColumns(10);
        tfGender.setBounds(309, yPosition, 191, 20);
        StudentDetailForm.getContentPane().add(tfGender);

        yPosition += yOffset; // Move to the next line

        tfClassName = new JTextField();
        tfClassName.setEditable(false);
        tfClassName.setColumns(10);
        tfClassName.setBounds(309, yPosition, 191, 20);
        StudentDetailForm.getContentPane().add(tfClassName);

        yPosition += yOffset; // Move to the next line

        tfDepartment = new JTextField();
        tfDepartment.setEditable(false);
        tfDepartment.setColumns(10);
        tfDepartment.setBounds(309, yPosition, 191, 20);
        StudentDetailForm.getContentPane().add(tfDepartment);

        yPosition += yOffset; // Move to the next line

        tfMajor = new JTextField();
        tfMajor.setEditable(false);
        tfMajor.setColumns(10);
        tfMajor.setBounds(309, yPosition, 191, 20);
        StudentDetailForm.getContentPane().add(tfMajor);

        yPosition += yOffset; // Move to the next line

        tfCourseYear = new JTextField();
        tfCourseYear.setEditable(false);
        tfCourseYear.setColumns(10);
        tfCourseYear.setBounds(309, yPosition, 191, 20);
        StudentDetailForm.getContentPane().add(tfCourseYear);

        yPosition += yOffset; // Move to the next line

        tfEduType = new JTextField();
        tfEduType.setEditable(false);
        tfEduType.setColumns(10);
        tfEduType.setBounds(309, yPosition, 191, 20);
        StudentDetailForm.getContentPane().add(tfEduType);

        yPosition += yOffset; // Move to the next line

        tfCreatedAt = new JTextField();
        tfCreatedAt.setEditable(false);
        tfCreatedAt.setColumns(10);
        tfCreatedAt.setBounds(309, yPosition, 191, 20);
        StudentDetailForm.getContentPane().add(tfCreatedAt);

        yPosition += yOffset; // Move to the next line

        tfUpdatedAt = new JTextField();
        tfUpdatedAt.setEditable(false);
        tfUpdatedAt.setColumns(10);
        tfUpdatedAt.setBounds(309, yPosition, 191, 20);
        StudentDetailForm.getContentPane().add(tfUpdatedAt);

        tfId.setText(s.getSid());
        tfName.setText(s.getName());

        if (s.getDob() != null) {
            tfDob.setText(s.getDob().toString());
        } else {
            tfDob.setText("N/A");
        }

        tfGender.setText(s.getGender());
        tfClassName.setText(s.getClassName());
        tfDepartment.setText(s.getDepartment());
        tfMajor.setText(s.getMajor());
        tfCourseYear.setText(s.getCourseYear()+"");
        tfEduType.setText(s.getEduType());

        if (s.getCreatedAt() != null) {
            tfCreatedAt.setText(s.getCreatedAt().toString());
        } else {
            tfCreatedAt.setText("N/A");
        }

        if (s.getUpdatedAt() != null) {
            tfUpdatedAt.setText(s.getUpdatedAt().toString());
        } else {
            tfUpdatedAt.setText("N/A");
        }

        //CERTIFICATES TABLE
        List<Certificate> selectedStudentCertificated = certificateDAO.getAllById(tfId.getText());
        String[] columnNames = {"Id", "Title", "Description", "Issue Date", "Expiry Date"
                , "Organization Name", "Is Valid", "Sid", "Created At", "Update At"};

        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        for (Certificate c : selectedStudentCertificated) {
            Vector<Object> rowData = new Vector<>();
            rowData.add(c.getCid());
            rowData.add(c.getCertificateName());
            rowData.add(c.getDescription());
            rowData.add(c.getIssuedDate());
            rowData.add(c.getExpiryDate());
            rowData.add(c.getOrganizationName());
            rowData.add(c.getIsValid());
            rowData.add(c.getSid());
            rowData.add(c.getCreatedAt());
            rowData.add(c.getUpdatedAt());

            model.addRow(rowData);
        }


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(600, 71, 703, 303);
        StudentDetailForm.getContentPane().add(scrollPane);

        // Load user to JTable
        certificateTable = new JTable() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        certificateTable.setRowSorter(sorter);

        scrollPane.setViewportView(certificateTable);

        certificateTable.setModel(model);

        JTableHeader header = certificateTable.getTableHeader();
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columnIndex = header.columnAtPoint(e.getPoint());
                showSortPopupMenu(header, e.getX(), e.getY(), columnIndex, sorter);
            }
        });

        //Edit
        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedCertificated = certificateTable.getSelectedRow();
                System.out.println("Selected row index: " + selectedCertificated); // Debugging statement

                if (selectedCertificated < 0) {
                    JOptionPane.showMessageDialog(StudentDetailForm, "No selected row", "There is something wrong",
                            JOptionPane.OK_OPTION);
                    return;
                }
                String certificateId = (String) certificateTable.getValueAt(selectedCertificated, 0);
                String studentId = (String) certificateTable.getValueAt(selectedCertificated, 7);
                System.out.println("Certificate ID: " + certificateId + ", Student ID: " + studentId); // Debugging statement

                new CertificateCRUDForm(certificateId, certificateDAO, studentId, StudentDetailForm.this);
            }
        });

        btnEdit.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnEdit.setBounds(600, 400, 166, 50);
        StudentDetailForm.getContentPane().add(btnEdit);

        // Add user
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve the student ID from the StudentDetailForm
                String sid = StudentDetailForm.this.getStudentId();

                // Create the AddCertificateForm with the student ID
                AddCertificateForm addCertificateForm = new AddCertificateForm(certificateDAO, sid, StudentDetailForm.this);

            }
        });

        btnAdd.setBounds(780, 400, 166, 50);
        StudentDetailForm.getContentPane().add(btnAdd);

        JButton btnExport = new JButton("Export");
        btnExport.setBounds(1150, 27, 150, 30);
        StudentDetailForm.getContentPane().add(btnExport);

        JButton btnImport = new JButton("Import");
        btnImport.setBounds(970, 27, 150, 30);
        StudentDetailForm.getContentPane().add(btnImport);

        btnExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Choose Directory to store file");
                FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
                FileNameExtensionFilter xlsxFilter = new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx");
                chooser.addChoosableFileFilter(csvFilter);
                chooser.addChoosableFileFilter(xlsxFilter);

                int result = chooser.showSaveDialog(StudentDetailForm);

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

                    List<Certificate> certificates = certificateDAO.getAll();

                    if (extension.equals("csv")) {
                        Utils.exportToCSV(certificates, selectedFile.getPath());
                    } else if (extension.equals("xlsx")) {
                        Utils.exportToExcel(certificates, selectedFile.getPath());
                    } else {
                        JOptionPane.showMessageDialog(StudentDetailForm, "This extension is not supported!");
                        return;
                    }

                    JOptionPane.showMessageDialog(StudentDetailForm, "Exported", "Success", JOptionPane.OK_OPTION);
                }
            }
        });


        btnImport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirmResult = JOptionPane.showConfirmDialog(StudentDetailForm,
                        "This action can affect to the data. Are you sure to import?");

                if (confirmResult == JOptionPane.OK_OPTION) {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Choose a file");
                    FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
                    FileNameExtensionFilter xlsxFilter = new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx");
                    chooser.addChoosableFileFilter(csvFilter);
                    chooser.addChoosableFileFilter(xlsxFilter);

                    int result = chooser.showOpenDialog(StudentDetailForm);

                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = chooser.getSelectedFile();
                        String description = chooser.getFileFilter().getDescription();

                        List<Certificate> certificates = new ArrayList<>();

                        if (description.endsWith("CSV Files (*.csv)") || selectedFile.getName().endsWith(".csv")) {
                            certificates = Utils.importDataFromCsvFile(selectedFile.getPath(), Certificate.class);
                        } else {
                            certificates = Utils.importDataFromExcelFile(selectedFile.getPath(), Certificate.class);
                        }

                        for (Certificate c : certificates) {
                            Certificate existedCertificate = certificateDAO.getById(c.getCid());

                            if (existedCertificate != null) {
                                int resultConfirmChange = JOptionPane.showConfirmDialog(StudentDetailForm,
                                        "The user \"" + existedCertificate.getSid()
                                                + "\" is already in the data. Do you really want to change it?");

                                if (resultConfirmChange == JOptionPane.YES_OPTION) {
                                    boolean isDeleteExistedCertificate = certificateDAO.delete(existedCertificate);

                                    if (!isDeleteExistedCertificate) {
                                        JOptionPane.showMessageDialog(StudentDetailForm,
                                                "Cannot import " + existedCertificate.getCid());
                                        continue;
                                    }
                                } else {
                                    continue;
                                }
                            }

                            boolean isAdded = certificateDAO.add(c);
                            if (!isAdded) {
                                JOptionPane.showMessageDialog(StudentDetailForm, "Cannot import " + c.getCid());
                                continue;
                            }

                            updateCertificateTable(c, false);
                        }

                        JOptionPane.showMessageDialog(StudentDetailForm, "Imported", "Success", JOptionPane.OK_OPTION);
                    }
                }
            }
        });
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

    public void updateCertificateTable(Certificate c, boolean isDeleteMode) {
        DefaultTableModel model = (DefaultTableModel) certificateTable.getModel();

        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String idInTable = (String) model.getValueAt(i, 0);
            if (idInTable.equals(c.getCid())) {
                model.removeRow(i);
                break;
            }
        }

        if (!isDeleteMode) {
            Vector<Object> rowData = new Vector<>();
            rowData.add(c.getCid());
            rowData.add(c.getCertificateName());
            rowData.add(c.getDescription());
            rowData.add(c.getIssuedDate());
            rowData.add(c.getExpiryDate());
            rowData.add(c.getOrganizationName());
            rowData.add(c.getIsValid());
            rowData.add(c.getSid());
            rowData.add(c.getCreatedAt());
            rowData.add(c.getUpdatedAt());
            model.addRow(rowData);
        }

        model.fireTableDataChanged();
        certificateTable.revalidate(); // Revalidate the table layout
        certificateTable.repaint(); // Repaint the table to reflect changes

    }
    public String getStudentId() {
        // Return the student ID
        return this.sid;
    }
}
