package org.example.view;


import org.example.dao.CertificateDAO;
import org.example.dao.StudentDAO;
import org.example.model.Certificate;
import org.example.model.Student;
import org.example.util.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
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

        JLabel lblAvatar = new JLabel("This is an avatar");
        lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);
        lblAvatar.setBounds(22, 27, 176, 187);
        StudentDetailForm.getContentPane().add(lblAvatar);

        JLabel lblId = new JLabel("ID:");
        lblId.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblId.setBounds(223, 27, 69, 23);
        StudentDetailForm.getContentPane().add(lblId);

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblName.setBounds(223, 74, 69, 23);
        StudentDetailForm.getContentPane().add(lblName);

        JLabel lblDob = new JLabel("Date of Birth:");
        lblDob.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDob.setBounds(223, 124, 69, 23);
        StudentDetailForm.getContentPane().add(lblDob);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblGender.setBounds(223, 174, 69, 23);
        StudentDetailForm.getContentPane().add(lblGender);

        JLabel lblClassName = new JLabel("Class Name:");
        lblClassName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblClassName.setBounds(223, 224, 69, 23);
        StudentDetailForm.getContentPane().add(lblClassName);

        JLabel lblDepartment = new JLabel("Department:");
        lblDepartment.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDepartment.setBounds(223, 274, 69, 23);
        StudentDetailForm.getContentPane().add(lblDepartment);

        JLabel lblMajor = new JLabel("Major:");
        lblMajor.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblMajor.setBounds(223, 324, 69, 23);
        StudentDetailForm.getContentPane().add(lblMajor);

        JLabel lblCourseYear = new JLabel("Course Year:");
        lblCourseYear.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblCourseYear.setBounds(223, 374, 69, 23);
        StudentDetailForm.getContentPane().add(lblCourseYear);

        JLabel lblEduType = new JLabel("Edu Type:");
        lblEduType.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblEduType.setBounds(223, 424, 69, 23);
        StudentDetailForm.getContentPane().add(lblEduType);

        JLabel lblCreatedAt = new JLabel("Created At:");
        lblCreatedAt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblCreatedAt.setBounds(223, 474, 88, 23);
        StudentDetailForm.getContentPane().add(lblCreatedAt);

        JLabel lblUpdatedAt = new JLabel("Updated At:");
        lblUpdatedAt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblUpdatedAt.setBounds(223, 524, 88, 23);
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


        JButton btnChangeAvatar = new JButton("Change Avatar");
        btnChangeAvatar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png",
                        "gif", "bmp", "svg");
                chooser.setFileFilter(imageFilter);

                int result = chooser.showOpenDialog(StudentDetailForm);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooser.getSelectedFile();

                    if (!Utils.checkImageFile(selectedFile)) {
                        JOptionPane.showMessageDialog(StudentDetailForm, "Please upload an image",
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
                        Utils.changeAvatar(selectedFile, s.getSid());
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

        btnChangeAvatar.setBounds(22, 223, 176, 34);
        StudentDetailForm.getContentPane().add(btnChangeAvatar);

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

//        String baseResourcesDir = "src/main/resources";
//        Path filePath = Paths.get(baseResourcesDir, "default-avatar.png");
//
//        Path directoryPath = Paths.get(baseResourcesDir, s.getSid());
//        File directory = directoryPath.toFile();
//
//        if (directory.exists() && directory.isDirectory()) {
//            try (Stream<Path> pathStream = Files.find(directoryPath, 1, (p, basicFileAttributes) -> {
//                String fileName = p.getFileName().toString();
//                return Pattern.compile("^avatar.*", Pattern.CASE_INSENSITIVE).matcher(fileName).matches();
//            })) {
//                Path foundPath = pathStream.findFirst().orElse(null);
//
//                if (foundPath != null) {
//                    filePath = foundPath;
//                }
//            } catch (IOException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
//
//        BufferedImage image = null;
//        try {
//            image = ImageIO.read(new File(filePath.toUri()));
//
//            int labelWidth = lblAvatar.getWidth();
//            int labelHeight = lblAvatar.getHeight();
//
//            ImageIcon imgIcon = Utils.stretchImage(image, labelWidth, labelHeight);
//            lblAvatar.setIcon(imgIcon);
//        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage(), "There is something wrong", JOptionPane.CLOSED_OPTION);
//        }

        StudentDetailForm.setVisible(true);

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
