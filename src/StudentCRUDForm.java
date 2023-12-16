package com.midterm.studentmanagementsystem.views.User;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import com.midterm.studentmanagementsystem.dao.StudentDAO;
import com.midterm.studentmanagementsystem.models.Student;
import com.midterm.studentmanagementsystem.utils.Utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StudentCRUDForm {

    private StudentDAO studentDAO;
    private StudentMainView studentMainView;
    private String sid;
    private String avatarName;
    private boolean isAddMode;

    private JFrame StudentCRUDForm;
    private JTextField tfId;
    private JTextField tfName;
    private JTextField tfGender;
    private JTextField tfClassName;
    private JTextField tfDepartment;
    private JTextField tfMajor;
    private JTextField tfCourseYear;
    private JTextField tfEduType;
    private JFormattedTextField tfDob;

    public StudentCRUDForm(StudentDAO studentDAO,String sid,StudentMainView studentMainView, boolean isAddMode) {
        this.studentDAO = studentDAO;
        this.sid = sid;
        this.studentMainView = studentMainView;
        this.isAddMode = isAddMode;
        initialize();
    }

    private void initialize(){
        StudentCRUDForm = new JFrame();
        StudentCRUDForm.getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 15));
        StudentCRUDForm.setTitle("Student Management System");
        StudentCRUDForm.setBounds(100, 100, 700, 700);
        StudentCRUDForm.setLocationRelativeTo(null);
        StudentCRUDForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        StudentCRUDForm.getContentPane().setLayout(null);
        StudentCRUDForm.setVisible(true);

        MaskFormatter dateFormatter = null;
        try {
            dateFormatter = new MaskFormatter("##-##-####");
            dateFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JLabel lblId = new JLabel("ID:");
        lblId.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblId.setBounds(150, 27, 150, 23);
        StudentCRUDForm.getContentPane().add(lblId);

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblName.setBounds(150, 74, 150, 23);
        StudentCRUDForm.getContentPane().add(lblName);

        JLabel lblDob = new JLabel("Date of Birth:");
        lblDob.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDob.setBounds(150, 124, 150, 23);
        StudentCRUDForm.getContentPane().add(lblDob);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblGender.setBounds(150, 174, 150, 23);
        StudentCRUDForm.getContentPane().add(lblGender);

        JLabel lblClassName = new JLabel("Class Name:");
        lblClassName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblClassName.setBounds(150, 224, 150, 23);
        StudentCRUDForm.getContentPane().add(lblClassName);

        JLabel lblDepartment = new JLabel("Department:");
        lblDepartment.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDepartment.setBounds(150, 274, 150, 23);
        StudentCRUDForm.getContentPane().add(lblDepartment);

        JLabel lblMajor = new JLabel("Major:");
        lblMajor.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblMajor.setBounds(150, 324, 150, 23);
        StudentCRUDForm.getContentPane().add(lblMajor);

        JLabel lblCourseYear = new JLabel("Course Year:");
        lblCourseYear.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblCourseYear.setBounds(150, 374, 150, 23);
        StudentCRUDForm.getContentPane().add(lblCourseYear);

        JLabel lblEduType = new JLabel("Edu Type:");
        lblEduType.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblEduType.setBounds(150, 424, 150, 23);
        StudentCRUDForm.getContentPane().add(lblEduType);

        int yPosition = 31; // Starting y position
        int yOffset = 50;   // Offset to space out the components

        tfId = new JTextField();
        tfId.setColumns(10);
        tfId.setBounds(309, yPosition, 191, 20);
        StudentCRUDForm.getContentPane().add(tfId);

        yPosition += yOffset; // Move to the next line

        tfName = new JTextField();
        tfName.setColumns(10);
        tfName.setBounds(309, yPosition, 191, 20);
        StudentCRUDForm.getContentPane().add(tfName);

        yPosition += yOffset; // Move to the next line

        tfDob = new JFormattedTextField(dateFormatter);
        tfDob.setColumns(10);
        tfDob.setBounds(309, yPosition, 191, 20);
        StudentCRUDForm.getContentPane().add(tfDob);

        yPosition += yOffset; // Move to the next line

        tfGender = new JTextField();
        tfGender.setColumns(10);
        tfGender.setBounds(309, yPosition, 191, 20);
        StudentCRUDForm.getContentPane().add(tfGender);

        yPosition += yOffset; // Move to the next line

        tfClassName = new JTextField();
        tfClassName.setColumns(10);
        tfClassName.setBounds(309, yPosition, 191, 20);
        StudentCRUDForm.getContentPane().add(tfClassName);

        yPosition += yOffset; // Move to the next line

        tfDepartment = new JTextField();
        tfDepartment.setColumns(10);
        tfDepartment.setBounds(309, yPosition, 191, 20);
        StudentCRUDForm.getContentPane().add(tfDepartment);

        yPosition += yOffset; // Move to the next line

        tfMajor = new JTextField();
        tfMajor.setColumns(10);
        tfMajor.setBounds(309, yPosition, 191, 20);
        StudentCRUDForm.getContentPane().add(tfMajor);

        yPosition += yOffset; // Move to the next line

        tfCourseYear = new JTextField();
        tfCourseYear.setColumns(10);
        tfCourseYear.setBounds(309, yPosition, 191, 20);
        StudentCRUDForm.getContentPane().add(tfCourseYear);

        yPosition += yOffset; // Move to the next line

        tfEduType = new JTextField();
        tfEduType.setColumns(10);
        tfEduType.setBounds(309, yPosition, 191, 20);
        StudentCRUDForm.getContentPane().add(tfEduType);

        yPosition += yOffset; // Move to the next line


        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnReset = new JButton("Reset");
        JButton btnChangeAvatar = new JButton("Change Avatar");

        if (isAddMode) {
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
        } else {
            tfId.setEditable(false);
            btnAdd.setEnabled(false);
        }

        // Reset all fields
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tfId.setText("");
                tfName.setText("");
                tfDob.setText("");
                tfGender.setText("");
                tfClassName.setText("");
                tfDepartment.setText("");
                tfMajor.setText("");
                tfCourseYear.setText("");
                tfEduType.setText("");
            }
        });

        btnReset.setBounds(209, yPosition, 97, 36);
        StudentCRUDForm.getContentPane().add(btnReset);

        btnUpdate.setBounds(309, yPosition, 97, 36);
        StudentCRUDForm.getContentPane().add(btnUpdate);

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = tfId.getText();
                String name = tfName.getText();
                Object dob = tfDob.getValue();
                String gender = tfGender.getText();
                String className = tfClassName.getText();
                String department = tfDepartment.getText();
                String major = tfMajor.getText();
                String courseYear = tfCourseYear.getText();
                String eduType = tfEduType.getText();


                Pattern idPattern = Pattern.compile("^[a-zA-Z0-9]+$");
                Pattern namePattern = Pattern.compile("^[a-zA-Z]+$");
                Pattern classNamePattern = Pattern.compile("^[a-zA-Z0-9]+$");
                Pattern departmentPattern = Pattern.compile("^[a-zA-Z]+$");
                Pattern majorPattern = Pattern.compile("^[a-zA-Z]+$");
                Pattern courseYearPattern = Pattern.compile("^[0-9]+$");
                Pattern eduTypePattern = Pattern.compile("^[a-zA-Z]+$");


                // Validation checks
                if (id.isEmpty() || !idPattern.matcher(id).matches()) {
                    JOptionPane.showMessageDialog(StudentCRUDForm, "Invalid ID", "Invalid input", JOptionPane.OK_OPTION);
                    return;
                }

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(StudentCRUDForm, "Invalid name", "Invalid input", JOptionPane.OK_OPTION);
                    return;
                }

                if (dob == null || !Utils.isValidDate(dob.toString())) {
                    JOptionPane.showMessageDialog(StudentCRUDForm, "Invalid date of birth", "Invalid input",
                            JOptionPane.OK_OPTION);
                    return;
                }

                if (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")) {
                    JOptionPane.showMessageDialog(StudentCRUDForm, "Gender must be 'male' or 'female'", "Invalid input", JOptionPane.OK_OPTION);
                    return;
                }

                if (className.isEmpty() || !classNamePattern.matcher(className).matches()) {
                    JOptionPane.showMessageDialog(StudentCRUDForm, "Invalid Class Name", "Invalid input", JOptionPane.OK_OPTION);
                    return;
                }

                if (department.isEmpty() || !departmentPattern.matcher(department).matches()) {
                    JOptionPane.showMessageDialog(StudentCRUDForm, "Invalid Department", "Invalid input", JOptionPane.OK_OPTION);
                    return;
                }

                if (major.isEmpty() || !majorPattern.matcher(major).matches()) {
                    JOptionPane.showMessageDialog(StudentCRUDForm, "Invalid Major", "Invalid input", JOptionPane.OK_OPTION);
                    return;
                }

                if (courseYear.isEmpty() || !courseYearPattern.matcher(courseYear).matches()) {
                    JOptionPane.showMessageDialog(StudentCRUDForm, "Invalid Course Year", "Invalid input", JOptionPane.OK_OPTION);
                    return;
                }

                if (eduType.isEmpty() || !eduTypePattern.matcher(eduType).matches()) {
                    JOptionPane.showMessageDialog(StudentCRUDForm, "Invalid Edu Type", "Invalid input", JOptionPane.OK_OPTION);
                    return;
                }

                Student existingStudent = studentDAO.getById(id);

                Date currentDate = new Date(System.currentTimeMillis());
                int courseYearInt = Integer.parseInt(courseYear);
                Date dobDate = Utils.convertToDate(dob.toString());

                // Create a Student object with the updated information
                Student updatedStudent = new Student(id, name, dobDate, gender, className, department, major, courseYearInt,eduType);
                updatedStudent.setCreatedAt(existingStudent.getCreatedAt());
                updatedStudent.setUpdatedAt(currentDate);
                Boolean isUpdated = studentDAO.update(updatedStudent);

                if (isUpdated) {
                    JOptionPane.showMessageDialog(StudentCRUDForm, "Student updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Refresh the student list in the main view if necessary
                    studentMainView.updateStudentTable(updatedStudent, false);
                    StudentCRUDForm.dispose();
                } else {
                    JOptionPane.showMessageDialog(StudentCRUDForm, "Failed to update the student", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve the student ID from a text field or other component
                String id = tfId.getText();

                int deletionResult = JOptionPane.showConfirmDialog(StudentCRUDForm,
                        "Do you really want to delete " + id + "?", "DELETE USER", JOptionPane.YES_NO_OPTION);

                if (deletionResult == JOptionPane.YES_OPTION) {
                    Student deletedStudent = studentDAO.getById(id);
                    // Perform the deletion using the studentDAO
                    boolean isDeleted = studentDAO.deleteById(id);

                    if (isDeleted) {
                        JOptionPane.showMessageDialog(StudentCRUDForm, "Student deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // Refresh the student list in the main view if necessary
                        studentMainView.updateStudentTable(deletedStudent, true);
                        StudentCRUDForm.dispose();
                    } else {
                        JOptionPane.showMessageDialog(StudentCRUDForm, "Failed to delete the student", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        btnDelete.setBounds(409, yPosition, 97, 36);
        StudentCRUDForm.getContentPane().add(btnDelete);

        Student selectedStudent = studentDAO.getById(sid);
        System.out.println(selectedStudent);
        if (selectedStudent == null) {
            JOptionPane.showMessageDialog(StudentCRUDForm, "There is no user to update", "There is something wrong",
                    JOptionPane.OK_OPTION);
            return;
        }

        tfId.setText(selectedStudent.getSid());
        tfName.setText(selectedStudent.getName());

        if (selectedStudent.getDob() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = dateFormat.format(selectedStudent.getDob());
            tfDob.setValue(formattedDate);
        }

        tfGender.setText(selectedStudent.getGender());
        tfClassName.setText(selectedStudent.getClassName());
        tfDepartment.setText(selectedStudent.getDepartment());
        tfMajor.setText(selectedStudent.getMajor());
        tfCourseYear.setText(selectedStudent.getCourseYear() + "");
        tfEduType.setText(selectedStudent.getEduType());

        // Set image
        String baseResourcesDir = "src/main/resources";
        avatarName = "default-avatar.png";
        Path filePath = Paths.get(baseResourcesDir, avatarName);

        if (sid != null) {
            Path directoryPath = Paths.get(baseResourcesDir, sid);
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
    }
}
