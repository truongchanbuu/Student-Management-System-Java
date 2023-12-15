package com.midterm.studentmanagementsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.midterm.studentmanagementsystem.models.LoginHistory;

public class LoginHistoryDAO implements DAO<LoginHistory> {
	private Connection conn;

	public LoginHistoryDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean add(LoginHistory obj) {
		String sql = "INSERT INTO loginhistory (email) VALUES (?)";

		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, obj.getEmail());

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
	public boolean update(LoginHistory obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(LoginHistory obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LoginHistory getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LoginHistory> getAll() {
		ArrayList<LoginHistory> histories = new ArrayList<>();
		String sql = "SELECT * FROM loginhistory";

		try {
			Statement stm = conn.createStatement();
			ResultSet result = stm.executeQuery(sql);

			while (result.next()) {
				int id = result.getInt("id");
				String email = result.getString("email");
				Timestamp history = result.getTimestamp("history");

				LoginHistory lh = new LoginHistory();
				lh.setId(id);
				lh.setEmail(email);
				lh.setHistory(history);

				histories.add(lh);
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		return histories;
	}
	
	public boolean deleteAllByEmail(String email) {
		String sql = "DELETE FROM loginhistory WHERE email = ?";

		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, email);

			int rowAffected = pstm.executeUpdate();

			if (rowAffected > 0) {
				return true;
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		return false;
	}
}
