package org.example.view;

import org.example.dao.StudentDAO;
import org.example.model.Student;
import org.example.util.Utils;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.util.regex.Pattern;

public class AddStudentForm {

    private StudentDAO studentDAO;
    private StudentMainView studentMainView;
    private String sid;
    private String avatarName;
    private boolean isAddMode;

    private JFrame AddStudentForm;
    private JTextField tfId;
    private JTextField tfName;
    private JTextField tfGender;
    private JTextField tfClassName;
    private JTextField tfDepartment;
    private JTextField tfMajor;
    private JTextField tfCourseYear;
    private JTextField tfEduType;
    private JLabel lblAvatar;
    private JFormattedTextField tfDob;

    public AddStudentForm(StudentDAO studentDAO,StudentMainView studentMainView){
        this.studentDAO = studentDAO;
        this.studentMainView = studentMainView;
        initialize();
    }

    private void initialize() {
        AddStudentForm = new JFrame();
        AddStudentForm.getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 15));
        AddStudentForm.setTitle("Student Management System");
        AddStudentForm.setBounds(100, 100, 661, 404);
        AddStudentForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        AddStudentForm.getContentPane().setLayout(null);
        AddStudentForm.setVisible(true);

        MaskFormatter dateFormatter = null;
        try {
            dateFormatter = new MaskFormatter("##-##-####");
            dateFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JLabel lblId = new JLabel("ID:");
        lblId.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblId.setBounds(223, 27, 69, 23);
        AddStudentForm.getContentPane().add(lblId);

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblName.setBounds(223, 74, 69, 23);
        AddStudentForm.getContentPane().add(lblName);

        JLabel lblDob = new JLabel("Date of Birth:");
        lblDob.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDob.setBounds(223, 124, 69, 23);
        AddStudentForm.getContentPane().add(lblDob);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblGender.setBounds(223, 174, 69, 23);
        AddStudentForm.getContentPane().add(lblGender);

        JLabel lblClassName = new JLabel("Class Name:");
        lblClassName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblClassName.setBounds(223, 224, 69, 23);
        AddStudentForm.getContentPane().add(lblClassName);

        JLabel lblDepartment = new JLabel("Department:");
        lblDepartment.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDepartment.setBounds(223, 274, 69, 23);
        AddStudentForm.getContentPane().add(lblDepartment);

        JLabel lblMajor = new JLabel("Major:");
        lblMajor.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblMajor.setBounds(223, 324, 69, 23);
        AddStudentForm.getContentPane().add(lblMajor);

        JLabel lblCourseYear = new JLabel("Course Year:");
        lblCourseYear.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblCourseYear.setBounds(223, 374, 69, 23);
        AddStudentForm.getContentPane().add(lblCourseYear);

        JLabel lblEduType = new JLabel("Edu Type:");
        lblEduType.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblEduType.setBounds(223, 424, 69, 23);
        AddStudentForm.getContentPane().add(lblEduType);

        int yPosition = 31; // Starting y position
        int yOffset = 50;   // Offset to space out the components

        tfId = new JTextField();
        tfId.setColumns(10);
        tfId.setBounds(309, yPosition, 191, 20);
        AddStudentForm.getContentPane().add(tfId);

        yPosition += yOffset; // Move to the next line

        tfName = new JTextField();
        tfName.setColumns(10);
        tfName.setBounds(309, yPosition, 191, 20);
        AddStudentForm.getContentPane().add(tfName);

        yPosition += yOffset; // Move to the next line

        tfDob = new JFormattedTextField(dateFormatter);
        tfDob.setColumns(10);
        tfDob.setBounds(309, yPosition, 191, 20);
        AddStudentForm.getContentPane().add(tfDob);

        yPosition += yOffset; // Move to the next line

        tfGender = new JTextField();
        tfGender.setColumns(10);
        tfGender.setBounds(309, yPosition, 191, 20);
        AddStudentForm.getContentPane().add(tfGender);

        yPosition += yOffset; // Move to the next line

        tfClassName = new JTextField();
        tfClassName.setColumns(10);
        tfClassName.setBounds(309, yPosition, 191, 20);
        AddStudentForm.getContentPane().add(tfClassName);

        yPosition += yOffset; // Move to the next line

        tfDepartment = new JTextField();
        tfDepartment.setColumns(10);
        tfDepartment.setBounds(309, yPosition, 191, 20);
        AddStudentForm.getContentPane().add(tfDepartment);

        yPosition += yOffset; // Move to the next line

        tfMajor = new JTextField();
        tfMajor.setColumns(10);
        tfMajor.setBounds(309, yPosition, 191, 20);
        AddStudentForm.getContentPane().add(tfMajor);

        yPosition += yOffset; // Move to the next line

        tfCourseYear = new JTextField();
        tfCourseYear.setColumns(10);
        tfCourseYear.setBounds(309, yPosition, 191, 20);
        AddStudentForm.getContentPane().add(tfCourseYear);

        yPosition += yOffset; // Move to the next line

        tfEduType = new JTextField();
        tfEduType.setColumns(10);
        tfEduType.setBounds(309, yPosition, 191, 20);
        AddStudentForm.getContentPane().add(tfEduType);


        JButton btnReset = new JButton("Reset");

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

        btnReset.setBounds(516, 257, 97, 36);
        AddStudentForm.getContentPane().add(btnReset);


        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        btnSubmit.setBounds(516, 207, 97, 36);
        AddStudentForm.getContentPane().add(btnSubmit);
    }

    private void addStudent() {
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
            JOptionPane.showMessageDialog(AddStudentForm, "Invalid ID", "Invalid input", JOptionPane.OK_OPTION);
            return;
        }

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(AddStudentForm, "Invalid name", "Invalid input", JOptionPane.OK_OPTION);
            return;
        }

        if (dob == null || !Utils.isValidDate(dob.toString())) {
            JOptionPane.showMessageDialog(AddStudentForm, "Invalid date of birth", "Invalid input",
                    JOptionPane.OK_OPTION);
            return;
        }

        if (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")) {
            JOptionPane.showMessageDialog(AddStudentForm, "Gender must be 'male' or 'female'", "Invalid input", JOptionPane.OK_OPTION);
            return;
        }

        if (className.isEmpty() || !classNamePattern.matcher(className).matches()) {
            JOptionPane.showMessageDialog(AddStudentForm, "Invalid Class Name", "Invalid input", JOptionPane.OK_OPTION);
            return;
        }

        if (department.isEmpty() || !departmentPattern.matcher(department).matches()) {
            JOptionPane.showMessageDialog(AddStudentForm, "Invalid Department", "Invalid input", JOptionPane.OK_OPTION);
            return;
        }

        if (major.isEmpty() || !majorPattern.matcher(major).matches()) {
            JOptionPane.showMessageDialog(AddStudentForm, "Invalid Major", "Invalid input", JOptionPane.OK_OPTION);
            return;
        }

        if (courseYear.isEmpty() || !courseYearPattern.matcher(courseYear).matches()) {
            JOptionPane.showMessageDialog(AddStudentForm, "Invalid Course Year", "Invalid input", JOptionPane.OK_OPTION);
            return;
        }

        if (eduType.isEmpty() || !eduTypePattern.matcher(eduType).matches()) {
            JOptionPane.showMessageDialog(AddStudentForm, "Invalid Edu Type", "Invalid input", JOptionPane.OK_OPTION);
            return;
        }

        if (studentDAO.getById(id) != null) {
            JOptionPane.showMessageDialog(AddStudentForm, "A student with this ID already exists.", "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date currentDate = new Date(System.currentTimeMillis());
        int courseYearInt = Integer.parseInt(courseYear);
        Date dobDate = Utils.convertToDate(dob.toString());

        Student s = new Student(id, name, dobDate,gender,className,department,major,courseYearInt,null,null, eduType);

        s.setCreatedAt(currentDate);
        boolean isAdded = studentDAO.add(s);

        if (isAdded) {
            JOptionPane.showMessageDialog(AddStudentForm, "Added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            studentMainView.updateStudentTable(s, false);
            AddStudentForm.dispose();

        }else{
            JOptionPane.showMessageDialog(AddStudentForm, "Added failed",
                    "Failed to add. Please check again or change your Id which maybe existed",
                    JOptionPane.OK_OPTION);
        }
    }
}
