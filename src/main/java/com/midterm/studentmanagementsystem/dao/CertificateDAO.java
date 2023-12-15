package org.example.dao;

import org.example.model.Certificate;
import org.example.model.Student;

import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class CertificateDAO implements DAO<Certificate>{

    Connection conn;

    public CertificateDAO(Connection conn){
        this.conn = conn;
    }

    public boolean add(Certificate obj) {

        if(getById(obj.getSid()) != null){
            return false;
        }

        String sql = "INSERT INTO certificates (id, title, description, issue_date, expiry_date, organization_name, isValid, sid, createdAT)" + "VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, obj.getCid());
            pstm.setString(2, obj.getCertificateName());
            pstm.setString(3, obj.getDescription());
            pstm.setDate(4, obj.getIssuedDate());
            pstm.setDate(5, obj.getExpiryDate());
            pstm.setString(6, obj.getOrganizationName());
            pstm.setString(7, obj.getIsValid());
            pstm.setString(8, obj.getSid());
            pstm.setDate(9, new Date(System.currentTimeMillis()));

            int rowAffected = pstm.executeUpdate();

            if (rowAffected > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return false;
    }

    public boolean update(Certificate obj) {
        String sql = "UPDATE certificates SET ";

        if (obj.getCid() != null)
            sql += "id = ?, ";
        if (obj.getCertificateName() != null)
            sql += "title = ?, ";
        if (obj.getDescription() != null)
            sql += "description = ?, ";
        if (obj.getIssuedDate() != null)
            sql += "issue_date = ?, ";
        if (obj.getExpiryDate() != null)
            sql += "expiry_date = ?, ";
        if (obj.getOrganizationName() != null)
            sql += "organization_name = ?, ";
        if (obj.getIsValid() != null)
            sql += "isValid = ?, ";

        // Correctly remove the last comma and add the updated_at field
        sql = sql.substring(0, sql.length() - 2) + ", updatedAt = ? WHERE id = ?";

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);

            int parameterIndex = 1;

            if (obj.getCid() != null)
                pstm.setString(parameterIndex++, obj.getCid());
            if (obj.getCertificateName() != null)
                pstm.setString(parameterIndex++, obj.getCertificateName());
            if (obj.getDescription() != null)
                pstm.setString(parameterIndex++, obj.getDescription());
            if (obj.getIssuedDate() != null)
                pstm.setDate(parameterIndex++, obj.getIssuedDate());
            if (obj.getExpiryDate() != null)
                pstm.setDate(parameterIndex++, obj.getExpiryDate());
            if (obj.getOrganizationName() != null)
                pstm.setString(parameterIndex++, obj.getOrganizationName());
            if (obj.getIsValid() != null)
                pstm.setString(parameterIndex++, obj.getIsValid());

            pstm.setTimestamp(parameterIndex++, new Timestamp(System.currentTimeMillis()));
            pstm.setString(parameterIndex, obj.getCid());

            int rowAffected = pstm.executeUpdate();
            if (rowAffected > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return false;
    }

    @Override
    public boolean delete(Certificate obj) {

        String sql = "DELETE FROM certificates WHERE id = ?";

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, obj.getCid());

            int rowAffected = pstm.executeUpdate();

            if (rowAffected > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean deleteById(String id) {

        String sql = "DELETE FROM certificates WHERE id = ?";

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, id);

            int rowAffected = pstm.executeUpdate();

            if (rowAffected > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }



    @Override
    public Certificate getById(String id) {

        String sql = "SELECT * FROM certificates WHERE id = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, id);

            ResultSet result = pstm.executeQuery();
            if (result.next()) {
                String cid = result.getString("id");
                String title = result.getString("title");
                String description = result.getString("description");
                Date issue_date = result.getDate("issue_date");
                Date expiry_date = result.getDate("issue_date");
                String organization_name = result.getString("organization_name");
                String isValid = result.getString("isValid");
                String sid = result.getString("sid");
                Date createdAt = result.getDate("createdAt");
                Date updatedAt = result.getDate("updatedAt");

                return new Certificate(cid, title, description, issue_date, expiry_date, organization_name, isValid, sid, createdAt, updatedAt);
//                return new Student(sid, name, dob, gender, className, department, major, courseYear, createdAt, updatedAt, eduType);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Certificate> getAll() {
        ArrayList<Certificate > certificates = new ArrayList<>();
        String sql = "SELECT * FROM certificates";

        try {
            Statement stm = conn.createStatement();
            ResultSet result = stm.executeQuery(sql);

            while (result.next()) {
                String cid = result.getString("id");
                String title = result.getString("title");
                String description = result.getString("description");
                Date issue_date = result.getDate("issue_date");
                Date expiry_date = result.getDate("issue_date");
                String organization_name = result.getString("organization_name");
                String isValid = result.getString("isValid");
                String sid = result.getString("sid");
                Date createdAt = result.getDate("createdAt");
                Date updatedAt = result.getDate("updatedAt");

                Certificate c = new Certificate(cid, title, description, issue_date, expiry_date, organization_name, isValid, sid, createdAt, updatedAt);
                certificates.add(c);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return certificates;
    }


    public List<Certificate> getAllById(String id) {
        ArrayList<Certificate> certificates = new ArrayList<>();
        String sql = "SELECT * FROM certificates WHERE sid = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, id);
            ResultSet result = pstm.executeQuery(); // No SQL string here

            while (result.next()) {
                String cid = result.getString("id");
                String title = result.getString("title");
                String description = result.getString("description");
                Date issue_date = result.getDate("issue_date");
                Date expiry_date = result.getDate("issue_date");
                String organization_name = result.getString("organization_name");
                String isValid = result.getString("isValid");
                String sid = result.getString("sid");
                Date createdAt = result.getDate("createdAt");
                Date updatedAt = result.getDate("updatedAt");

                Certificate c = new Certificate(cid, title, description, issue_date, expiry_date, organization_name, isValid, sid, createdAt, updatedAt);
                certificates.add(c);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return certificates;
    }


    public List<Certificate> searchStudent(String searchText) {
        List<Certificate> searchResults = new ArrayList<>();
        String sql = "SELECT * FROM certificates WHERE id LIKE ? OR title LIKE ? OR description LIKE ? OR issue_date LIKE ? OR expiry_date LIKE ? OR organization_name LIKE ? OR isValed LIKE ? OR sid LIKE ?";

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            String searchTextWithWildcard = "%" + searchText + "%";

            for (int i = 1; i < 9; ++i) {
                pstm.setString(i, searchTextWithWildcard);
            }

            ResultSet result = pstm.executeQuery();
            while (result.next()) {
                String cid = result.getString("id");
                String title = result.getString("title");
                String description = result.getString("description");
                Date issue_date = result.getDate("issue_date");
                Date expiry_date = result.getDate("issue_date");
                String organization_name = result.getString("organization_name");
                String isValid = result.getString("isValid");
                String sid = result.getString("sid");

                Certificate c = new Certificate(cid, title, description, issue_date, expiry_date, organization_name, isValid, sid);
                searchResults.add(c);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return searchResults;
    }




}
