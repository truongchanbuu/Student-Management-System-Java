package org.example.view;

import org.example.dao.CertificateDAO;
import org.example.model.Certificate;
import org.example.util.Utils;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.util.regex.Pattern;

public class AddCertificateForm {

    private String sid;

    private boolean isAddMode;
    private StudentDetailForm studentDetailForm;
    private CertificateDAO certificateDAO;
//    private JFrame CertificateCRUDForm;
    private JTextField tfCid;
    private JTextField tfCName;
    private JTextField tfDescription;
    private JFormattedTextField tfIssueDate;
    private JFormattedTextField tfExpiryDate;
    private JTextField tfOrganizationName;
    private JTextField tfIsValid;
    private JTextField tfSid;

    private JFrame AddCertificateForm;

    public AddCertificateForm(CertificateDAO certificateDAO, String sid ,StudentDetailForm studentDetailForm) {
        this.certificateDAO = certificateDAO;
        this.sid = sid;
        this.studentDetailForm = studentDetailForm;
        initialize();
    }
    private void initialize() {
        AddCertificateForm = new JFrame();
        AddCertificateForm.getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 15));
        AddCertificateForm.setTitle("Certificate");
        AddCertificateForm.setBounds(100, 100, 700, 700);
        AddCertificateForm.setLocationRelativeTo(null);
        AddCertificateForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        AddCertificateForm.getContentPane().setLayout(null);
        AddCertificateForm.setVisible(true);

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
        AddCertificateForm.getContentPane().add(lblId);

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblName.setBounds(223, 74, 69, 23);
        AddCertificateForm.getContentPane().add(lblName);

        JLabel lblDescription = new JLabel("Des:");
        lblDescription.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDescription.setBounds(223, 124, 69, 23);
        AddCertificateForm.getContentPane().add(lblDescription);

        JLabel lblIssueDate = new JLabel("Issue Date:");
        lblIssueDate.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblIssueDate.setBounds(223, 174, 69, 23);
        AddCertificateForm.getContentPane().add(lblIssueDate);

        JLabel lblExpiryDate = new JLabel("Expiry Date:");
        lblExpiryDate.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblExpiryDate.setBounds(223, 224, 69, 23);
        AddCertificateForm.getContentPane().add(lblExpiryDate);

        JLabel lblOrganizationName = new JLabel("Organization Name:");
        lblOrganizationName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblOrganizationName.setBounds(223, 274, 69, 23);
        AddCertificateForm.getContentPane().add(lblOrganizationName);

        JLabel lblIsValid = new JLabel("Is Valid:");
        lblIsValid.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblIsValid.setBounds(223, 324, 69, 23);
        AddCertificateForm.getContentPane().add(lblIsValid);

        JLabel lblSid = new JLabel("Student ID:");
        lblSid.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSid.setBounds(223, 374, 69, 23);
        AddCertificateForm.getContentPane().add(lblSid);

        int yPosition = 31; // Starting y position
        int yOffset = 50;   // Offset to space out the components

        tfCid = new JTextField();
        tfCid.setColumns(10);
        tfCid.setBounds(309, yPosition, 191, 20);
        AddCertificateForm.getContentPane().add(tfCid);

        yPosition += yOffset; // Move to the next line

        tfCName = new JTextField();
        tfCName.setColumns(10);
        tfCName.setBounds(309, yPosition, 191, 20);
        AddCertificateForm.getContentPane().add(tfCName);

        yPosition += yOffset; // Move to the next line

        tfDescription = new JTextField();
        tfDescription.setColumns(10);
        tfDescription.setBounds(309, yPosition, 191, 20);
        AddCertificateForm.getContentPane().add(tfDescription);

        yPosition += yOffset; // Move to the next line

        tfIssueDate = new JFormattedTextField(dateFormatter);
        tfIssueDate.setColumns(10);
        tfIssueDate.setBounds(309, yPosition, 191, 20);
        AddCertificateForm.getContentPane().add(tfIssueDate);

        yPosition += yOffset; // Move to the next line

        tfExpiryDate = new JFormattedTextField(dateFormatter);
        tfExpiryDate.setColumns(10);
        tfExpiryDate.setBounds(309, yPosition, 191, 20);
        AddCertificateForm.getContentPane().add(tfExpiryDate);

        yPosition += yOffset; // Move to the next line

        tfOrganizationName = new JTextField();
        tfOrganizationName.setColumns(10);
        tfOrganizationName.setBounds(309, yPosition, 191, 20);
        AddCertificateForm.getContentPane().add(tfOrganizationName);

        yPosition += yOffset; // Move to the next line

        tfIsValid = new JTextField();
        tfIsValid.setColumns(10);
        tfIsValid.setBounds(309, yPosition, 191, 20);
        AddCertificateForm.getContentPane().add(tfIsValid);

        yPosition += yOffset; // Move to the next line

        tfSid = new JTextField();
        tfSid.setText(this.sid); // Set the student ID text field with the passed student ID
        tfSid.setEditable(false);
        tfSid.setColumns(10);
        tfSid.setBounds(309, yPosition, 191, 20);
        AddCertificateForm.getContentPane().add(tfSid);

        yPosition += yOffset; // Move to the next line

        JButton btnAdd = new JButton("Add");
        JButton btnReset = new JButton("Reset");

        // Reset all fields
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tfCid.setText("");
                tfCName.setText("");
                tfDescription.setText("");
                tfIssueDate.setText("");
                tfExpiryDate.setText("");
                tfOrganizationName.setText("");
                tfIsValid.setText("");
                tfSid.setText("");
            }
        });

        btnReset.setBounds(223, yPosition, 95, 30);

        AddCertificateForm.getContentPane().add(btnReset);
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCertificate();
            }
        });

        btnSubmit.setBounds(323, yPosition, 95, 30);
        AddCertificateForm.getContentPane().add(btnSubmit);

    }

    private void addCertificate(){
        String Cid = tfCid.getText();
        String CName = tfCName.getText();
        String CDescription = tfDescription.getText();
        Object IssueDate = tfIssueDate.getValue();
        Object ExpiryDate = tfExpiryDate.getValue();
        String OrganizationName = tfOrganizationName.getText();
        String IsValid = tfIsValid.getText();
        String Sid = tfSid.getText();


        Pattern idPattern = Pattern.compile("^[a-zA-Z0-9]+$");


        // Validation checks
        if (Cid.isEmpty() || !idPattern.matcher(Cid).matches()) {
            JOptionPane.showMessageDialog(AddCertificateForm, "Invalid ID", "Invalid input", JOptionPane.OK_OPTION);
            return;
        }

        if (CDescription.isEmpty()) {
            JOptionPane.showMessageDialog(AddCertificateForm, "Invalid name", "Invalid input", JOptionPane.OK_OPTION);
            return;
        }

        if (CName.isEmpty()) {
            JOptionPane.showMessageDialog(AddCertificateForm, "Invalid name", "Invalid input", JOptionPane.OK_OPTION);
            return;
        }


        if (IssueDate == null || !Utils.isValidDate(IssueDate.toString())) {
            JOptionPane.showMessageDialog(AddCertificateForm, "Invalid date of birth", "Invalid input",
                    JOptionPane.OK_OPTION);
            return;
        }

        if (ExpiryDate == null || !Utils.isValidDate(IssueDate.toString())) {
            JOptionPane.showMessageDialog(AddCertificateForm, "Invalid date of birth", "Invalid input",
                    JOptionPane.OK_OPTION);
            return;
        }

        if (OrganizationName.isEmpty()) {
            JOptionPane.showMessageDialog(AddCertificateForm, "Invalid name", "Invalid input", JOptionPane.OK_OPTION);
            return;
        }

        if (IsValid.isEmpty()) {
            JOptionPane.showMessageDialog(AddCertificateForm, "Invalid name", "Invalid input", JOptionPane.OK_OPTION);
            return;
        }

        if (sid.isEmpty()) {
            JOptionPane.showMessageDialog(AddCertificateForm, "Invalid Id", "Invalid input", JOptionPane.OK_OPTION);
            return;
        }
        Date currentDate = new Date(System.currentTimeMillis());
        Date issueDateConvert = Utils.convertToDate(IssueDate.toString());
        Date expiryDateConvert = Utils.convertToDate(ExpiryDate.toString());

        Certificate addedCertificate = new Certificate(Cid, CName, CDescription, issueDateConvert, expiryDateConvert, OrganizationName, IsValid, Sid);
        addedCertificate.setCreatedAt(currentDate);

        Boolean isAdded = certificateDAO.add(addedCertificate);
        if (isAdded) {
            JOptionPane.showMessageDialog(AddCertificateForm, "Added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            studentDetailForm.updateCertificateTable(addedCertificate, false);
            AddCertificateForm.dispose();

        }else{
            JOptionPane.showMessageDialog(AddCertificateForm, "Added failed",
                    "Failed to add. Please check again or change your Id which maybe existed",
                    JOptionPane.OK_OPTION);
        }
    }

}

