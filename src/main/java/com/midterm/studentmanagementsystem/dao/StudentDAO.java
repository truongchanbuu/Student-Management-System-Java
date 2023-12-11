package org.example.dao;

import org.example.model.Student;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements DAO<Student> {

    Connection conn;

    public StudentDAO(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean add(Student obj) {

        if(getById(obj.getSid()) != null){
            return false;
        }

        String sql = "INSERT INTO Students (id, name, dob, gender, className ,department, major, courseYear, eduType, createdAt)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, obj.getSid());
            pstm.setString(2, obj.getName());
            pstm.setDate(3, obj.getDob());
            pstm.setString(4, obj.getGender());
            pstm.setString(5, obj.getClassName());
            pstm.setString(6, obj.getDepartment());
            pstm.setString(7, obj.getMajor());
            pstm.setInt(8, obj.getCourseYear());
            pstm.setString(9, obj.getEduType());
            pstm.setDate(10, new Date(System.currentTimeMillis()));

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
    public boolean update(Student obj) {
        String sql = "UPDATE students SET ";

        if (obj.getName() != null)
            sql += "name = ?, ";
        if (obj.getGender() != null)
            sql += "gender = ?, ";
        if (obj.getDob() != null)
            sql += "dob = ?, ";
        if (obj.getClassName() != null)
            sql += "className = ?, ";
        if (obj.getDepartment() != null)
            sql += "department = ?, ";
        if (obj.getMajor() != null)
            sql += "major = ?, ";
        if (obj.getCourseYear() > 0 && obj.getCourseYear() < Year.now().getValue())
            sql += "courseYear = ?, ";
        if (obj.getEduType() != null)
            sql += "eduType = ?, ";

        // Correctly remove the last comma and add the updated_at field
        sql = sql.substring(0, sql.length() - 2) + ", updatedAt = ? WHERE id = ?";

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);

            int parameterIndex = 1;

            if (obj.getName() != null)
                pstm.setString(parameterIndex++, obj.getName());
            if (obj.getGender() != null)
                pstm.setString(parameterIndex++, obj.getGender());
            if (obj.getDob() != null)
                pstm.setDate(parameterIndex++, obj.getDob());
            if (obj.getClassName() != null)
                pstm.setString(parameterIndex++, obj.getClassName());
            if (obj.getDepartment() != null)
                pstm.setString(parameterIndex++, obj.getDepartment());
            if (obj.getMajor() != null)
                pstm.setString(parameterIndex++, obj.getMajor());
            if (obj.getCourseYear() > 0 && obj.getCourseYear() < Year.now().getValue())
                pstm.setInt(parameterIndex++, obj.getCourseYear());
            if (obj.getEduType() != null)
                pstm.setString(parameterIndex++, obj.getEduType());

            pstm.setTimestamp(parameterIndex++, new Timestamp(System.currentTimeMillis()));
            pstm.setString(parameterIndex, obj.getSid());

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
    public boolean delete(Student obj) {

        String sql = "DELETE FROM Students WHERE id = ?";

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, obj.getSid());

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
        String sql = "DELETE FROM Students WHERE id = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, id);
            int rowsAffected = pstm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println("Error deleting student: " + ex.getMessage());
            return false;
        }
    }


    @Override
    public Student getById(String id) {

        String sql = "SELECT * FROM Students WHERE id = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, id);

            ResultSet result = pstm.executeQuery();
            if (result.next()) {
                String sid = result.getString("id");
                String name = result.getString("name");
                Date dob = result.getDate("dob");
                String gender = result.getString("gender");
                String className = result.getString("className");
                String department = result.getString("department");
                String major = result.getString("major");
                int courseYear = result.getInt("courseYear");
                Date createdAt = result.getDate("createdAt");
                Date updatedAt = result.getDate("updatedAt");
                String eduType = result.getString("eduType");

                return new Student(sid, name, dob, gender, className, department, major, courseYear, createdAt, updatedAt, eduType);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Student> getAll() {
        ArrayList<Student > students = new ArrayList<>();
        String sql = "SELECT * FROM Students";

        try {
            Statement stm = conn.createStatement();
            ResultSet result = stm.executeQuery(sql);

            while (result.next()) {
                String sid = result.getString("id");
                String name = result.getString("name");
                Date dob = result.getDate("dob");
                String gender = result.getString("gender");
                String className = result.getString("className");
                String department = result.getString("department");
                String major = result.getString("major");
                int courseYear = result.getInt("courseYear");
                Date createdAt = result.getDate("createdAt");
                Date updatedAt = result.getDate("updatedAt");
                String eduType = result.getString("eduType");

                Student s = new Student(sid, name, dob, gender, className, department, major, courseYear, createdAt, updatedAt, eduType);
                students.add(s);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return students;
    }

    public List<Student> searchStudent(String searchText) {
        List<Student> searchResults = new ArrayList<>();
        String sql = "SELECT * FROM Students WHERE id LIKE ? OR name LIKE ? OR dob LIKE ? OR gender LIKE ? OR className LIKE ? OR department LIKE ? OR major LIKE ? OR courseYear LIKE ?";

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            String searchTextWithWildcard = "%" + searchText + "%";

            for (int i = 1; i < 9; ++i) {
                pstm.setString(i, searchTextWithWildcard);
            }

            ResultSet result = pstm.executeQuery();
            while (result.next()) {
                String sid = result.getString("id");
                String name = result.getString("name");
                Date dob = result.getDate("dob");
                String gender = result.getString("gender");
                String className = result.getString("className");
                String department = result.getString("department");
                String major = result.getString("major");
                int courseYear = result.getInt("courseYear");
                Date createdAt = result.getDate("createdAt");
                Date updatedAt = result.getDate("updatedAt");
                String eduType = result.getString("eduType");

                Student s = new Student(sid, name, dob, gender, className, department, major, courseYear, createdAt, updatedAt, eduType);
                searchResults.add(s);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return searchResults;
    }

}
