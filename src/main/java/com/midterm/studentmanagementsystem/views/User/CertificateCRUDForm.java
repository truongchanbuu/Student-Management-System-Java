package com.midterm.studentmanagementsystem.views.User;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import com.midterm.studentmanagementsystem.dao.CertificateDAO;
import com.midterm.studentmanagementsystem.models.Certificate;
import com.midterm.studentmanagementsystem.utils.Utils;

public class CertificateCRUDForm {

    private String sid;

    private String cid;
    private boolean isAddMode;
    private StudentDetailForm studentDetailForm;
    private CertificateDAO certificateDAO;
    private JFrame CertificateCRUDForm;
    private JTextField tfCid;
    private JTextField tfCName;
    private JTextField tfDescription;
    private JFormattedTextField tfIssueDate;
    private JFormattedTextField tfExpiryDate;
    private JTextField tfOrganizationName;
    private JTextField tfIsValid;
    private JTextField tfSid;

    public CertificateCRUDForm(String cid,CertificateDAO certificateDAO, String sid, StudentDetailForm studentDetailForm) {
        this.certificateDAO = certificateDAO;
        this.cid = cid;
        this.sid = sid;
        this.studentDetailForm = studentDetailForm;
        initialize();
    }

    private void initialize() {
        System.out.println("Initializing CertificateCRUDForm with CID: " + cid + ", SID: " + sid);
        CertificateCRUDForm = new JFrame();
        CertificateCRUDForm.getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 15));
        CertificateCRUDForm.setTitle("Certificate");
        CertificateCRUDForm.setBounds(100, 100, 700, 700);
        CertificateCRUDForm.setLocationRelativeTo(null);
        CertificateCRUDForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        CertificateCRUDForm.getContentPane().setLayout(null);
        CertificateCRUDForm.setVisible(true);

        MaskFormatter dateFormatter = null;
        try {
            dateFormatter = new MaskFormatter("##-##-####");
            dateFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JLabel lblId = new JLabel("Certificate ID:");
        lblId.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblId.setBounds(150, 27, 150, 23);
        CertificateCRUDForm.getContentPane().add(lblId);

        JLabel lblName = new JLabel("Certificate Name:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblName.setBounds(150, 74, 150, 23);
        CertificateCRUDForm.getContentPane().add(lblName);

        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDescription.setBounds(150, 124, 150, 23);
        CertificateCRUDForm.getContentPane().add(lblDescription);

        JLabel lblIssueDate = new JLabel("Issue Date:");
        lblIssueDate.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblIssueDate.setBounds(150, 174, 150, 23);
        CertificateCRUDForm.getContentPane().add(lblIssueDate);

        JLabel lblExpiryDate = new JLabel("Expiry Date:");
        lblExpiryDate.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblExpiryDate.setBounds(150, 224, 150, 23);
        CertificateCRUDForm.getContentPane().add(lblExpiryDate);

        JLabel lblOrganizationName = new JLabel("Organization Name:");
        lblOrganizationName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblOrganizationName.setBounds(150, 274, 150, 23);
        CertificateCRUDForm.getContentPane().add(lblOrganizationName);

        JLabel lblIsValid = new JLabel("Is Valid:");
        lblIsValid.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblIsValid.setBounds(150, 324, 150, 23);
        CertificateCRUDForm.getContentPane().add(lblIsValid);

        JLabel lblSid = new JLabel("Student ID:");
        lblSid.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSid.setBounds(150, 374, 150, 23);
        CertificateCRUDForm.getContentPane().add(lblSid);

        int yPosition = 31; // Starting y position
        int yOffset = 50;   // Offset to space out the components

        tfCid = new JTextField();
        tfCid.setText(cid);
        tfCid.setColumns(10);
        tfCid.setBounds(309, yPosition, 191, 20);
        CertificateCRUDForm.getContentPane().add(tfCid);

        yPosition += yOffset; // Move to the next line

        tfCName = new JTextField();
        tfCName.setColumns(10);
        tfCName.setBounds(309, yPosition, 191, 20);
        CertificateCRUDForm.getContentPane().add(tfCName);

        yPosition += yOffset; // Move to the next line

        tfDescription = new JTextField();
        tfDescription.setColumns(10);
        tfDescription.setBounds(309, yPosition, 191, 20);
        CertificateCRUDForm.getContentPane().add(tfDescription);

        yPosition += yOffset; // Move to the next line

        tfIssueDate = new JFormattedTextField(dateFormatter);
        tfIssueDate.setColumns(10);
        tfIssueDate.setBounds(309, yPosition, 191, 20);
        CertificateCRUDForm.getContentPane().add(tfIssueDate);

        yPosition += yOffset; // Move to the next line

        tfExpiryDate = new JFormattedTextField(dateFormatter);
        tfExpiryDate.setColumns(10);
        tfExpiryDate.setBounds(309, yPosition, 191, 20);
        CertificateCRUDForm.getContentPane().add(tfExpiryDate);

        yPosition += yOffset; // Move to the next line

        tfOrganizationName = new JTextField();
        tfOrganizationName.setColumns(10);
        tfOrganizationName.setBounds(309, yPosition, 191, 20);
        CertificateCRUDForm.getContentPane().add(tfOrganizationName);

        yPosition += yOffset; // Move to the next line

        tfIsValid = new JTextField();
        tfIsValid.setColumns(10);
        tfIsValid.setBounds(309, yPosition, 191, 20);
        CertificateCRUDForm.getContentPane().add(tfIsValid);

        yPosition += yOffset; // Move to the next line

        tfSid = new JTextField();
        tfSid.setColumns(10);
        tfSid.setText(sid);
        tfSid.setBounds(309, yPosition, 191, 20);
        CertificateCRUDForm.getContentPane().add(tfSid);

        yPosition += yOffset; // Move to the next line


        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnReset = new JButton("Reset");

        if (isAddMode) {
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
        } else {
            tfSid.setEditable(false);
            btnAdd.setEnabled(false);
        }

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
                    JOptionPane.showMessageDialog(CertificateCRUDForm, "Invalid ID", "Invalid input", JOptionPane.OK_OPTION);
                    return;
                }

                if (CDescription.isEmpty()) {
                    JOptionPane.showMessageDialog(CertificateCRUDForm, "Invalid Descrption", "Invalid input", JOptionPane.OK_OPTION);
                    return;
                }

                if (CName.isEmpty()) {
                    JOptionPane.showMessageDialog(CertificateCRUDForm, "Invalid name", "Invalid input", JOptionPane.OK_OPTION);
                    return;
                }


                if (IssueDate == null || !Utils.isValidDate(IssueDate.toString())) {
                    JOptionPane.showMessageDialog(CertificateCRUDForm, "Invalid Date", "Invalid input",
                            JOptionPane.OK_OPTION);
                    return;
                }

                if (ExpiryDate == null || !Utils.isValidDate(IssueDate.toString())) {
                    JOptionPane.showMessageDialog(CertificateCRUDForm, "Invalid Date", "Invalid input",
                            JOptionPane.OK_OPTION);
                    return;
                }

                if (OrganizationName.isEmpty()) {
                    JOptionPane.showMessageDialog(CertificateCRUDForm, "Invalid OGname", "Invalid input", JOptionPane.OK_OPTION);
                    return;
                }

                if (IsValid.isEmpty()) {
                    JOptionPane.showMessageDialog(CertificateCRUDForm, "Invalid Is Valid", "Invalid input", JOptionPane.OK_OPTION);
                    return;
                }

                if (sid.isEmpty()) {
                    JOptionPane.showMessageDialog(CertificateCRUDForm, "Invalid ID", "Invalid input",JOptionPane.OK_OPTION);
                    return;
                }

                Certificate exsisCertificate = certificateDAO.getById(cid);

                Date currentDate = new Date(System.currentTimeMillis());
                Date issueDateConvert = Utils.convertToDate(IssueDate.toString());
                Date expiryDateConvert = Utils.convertToDate(ExpiryDate.toString());

                // Create a Student object with the updated information
                Certificate updatedCertificate = new Certificate(Cid, CName, CDescription, issueDateConvert, expiryDateConvert, OrganizationName, IsValid, Sid);
                updatedCertificate.setCreatedAt(exsisCertificate.getCreatedAt());
                updatedCertificate.setUpdatedAt(currentDate);
                Boolean isUpdated = certificateDAO.update(updatedCertificate);

                if (isUpdated) {
                    JOptionPane.showMessageDialog(CertificateCRUDForm, "Student updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Refresh the student list in the main view if necessary
                    studentDetailForm.updateCertificateTable(updatedCertificate, false);
                    CertificateCRUDForm.dispose();
                } else {
                    JOptionPane.showMessageDialog(CertificateCRUDForm, "Failed to update the student", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        btnUpdate.setBounds(223, yPosition, 95, 30);
        CertificateCRUDForm.getContentPane().add(btnUpdate);



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

        btnReset.setBounds(339, yPosition, 95, 30);
        CertificateCRUDForm.getContentPane().add(btnReset);


        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve the student ID from a text field or other component
                String id = tfCid.getText();

                int deletionResult = JOptionPane.showConfirmDialog(CertificateCRUDForm,
                        "Do you really want to delete " + id + "?", "DELETE CERTIFICATE", JOptionPane.YES_NO_OPTION);

                if (deletionResult == JOptionPane.YES_OPTION) {
                    Certificate deletedCertificate = certificateDAO.getById(id);
                    // Perform the deletion using the studentDAO
                    boolean isDeleted = certificateDAO.deleteById(id);

                    if (isDeleted) {
                        JOptionPane.showMessageDialog(CertificateCRUDForm, "Certificate deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // Refresh the student list in the main view if necessary
                        studentDetailForm.updateCertificateTable(deletedCertificate, true);
                        CertificateCRUDForm.dispose();
                    } else {
                        JOptionPane.showMessageDialog(CertificateCRUDForm, "Failed to delete the certificate", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        btnDelete.setBounds(455, yPosition, 95, 30);
        CertificateCRUDForm.getContentPane().add(btnDelete);
        yPosition += yOffset; // Move to the next line

        Certificate selectedCertificate = certificateDAO.getById(cid);
        System.out.println(selectedCertificate);
        if (selectedCertificate == null) {
            JOptionPane.showMessageDialog(CertificateCRUDForm, "There is no user to update", "There is something wrong",
                    JOptionPane.OK_OPTION);
            return;
        }
        tfCid.setText(selectedCertificate.getCid());
        tfCName.setText(selectedCertificate.getCertificateName());
        tfDescription.setText(selectedCertificate.getDescription());

        if (selectedCertificate.getIssuedDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = dateFormat.format(selectedCertificate.getIssuedDate());
            tfIssueDate.setValue(formattedDate);
        }

        if (selectedCertificate.getExpiryDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = dateFormat.format(selectedCertificate.getExpiryDate());
            tfExpiryDate.setValue(formattedDate);
        }

        tfOrganizationName.setText(selectedCertificate.getOrganizationName());
        tfIsValid.setText(selectedCertificate.getIsValid());
        tfSid.setText(selectedCertificate.getSid());
    }

}
