package ch.bfh.awebt.login.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserDAO {

	private Connection getConnection() throws SQLException {

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException ex) {
			throw new SQLException("Could not load JDBC driver.", ex);
		}

		return DriverManager.getConnection("jdbc:sqlserver://", "AWebT", "AWebT");
	}

	public User create(User entity) throws SQLException {

		try (Connection con = this.getConnection();
				 PreparedStatement stmIns = con.prepareStatement("insert tblUsers(usrLogin, usrPassword, usrDateOfBirth) values(?, ?, ?)");
				 PreparedStatement stmSel = con.prepareStatement("select usrID from tblUsers where usrLogin=?")) {

			stmIns.setString(1, entity.getLogin());
			stmIns.setBytes(2, entity.getPasswordHash());
			stmIns.setString(3, entity.getDateOfBirth().toString());
			stmIns.executeUpdate();

			stmSel.setString(1, entity.getLogin());
			try (ResultSet res = stmSel.executeQuery()) {
				if (res.next())
					entity.setId(res.getInt("usrID"));
			}
		}

		return entity;
	}

	public User findByID(int id) throws SQLException {

		try (Connection con = this.getConnection();
				 PreparedStatement stm = con.prepareStatement("select usrID, usrLogin, usrPassword from tblUsers where usrID=?")) {

			stm.setInt(1, id);
			try (ResultSet res = stm.executeQuery()) {
				if (res.next())
					return new User(res.getInt("usrID"), res.getString("usrLogin"), res.getBytes("usrPassword"), LocalDate.parse(res.getString("usrDateOfBirth")));
			}
		}

		return null;
	}

	public User findByLogin(String login) throws SQLException {

		try (Connection con = this.getConnection();
				 PreparedStatement stm = con.prepareStatement("select usrID, usrLogin, usrPassword, usrDateOfBirth from tblUsers where usrLogin=?")) {

			stm.setString(1, login);
			try (ResultSet res = stm.executeQuery()) {
				if (res.next())
					return new User(res.getInt("usrID"), res.getString("usrLogin"), res.getBytes("usrPassword"), LocalDate.parse(res.getString("usrDateOfBirth")));
			}
		}

		return null;
	}

	public User update(User entity) throws SQLException {

		try (Connection con = this.getConnection();
				 PreparedStatement stm = con.prepareStatement("update tblUsers set usrLogin=?, usrPassword=?, usrDateOfBirth=? where usrID=?")) {

			stm.setString(1, entity.getLogin());
			stm.setBytes(2, entity.getPasswordHash());
			stm.setString(3, entity.getDateOfBirth().toString());
			stm.setInt(4, entity.getId());
			stm.executeUpdate();
		}

		return entity;
	}

	public void delete(User entity) throws SQLException {

		try (Connection con = this.getConnection();
				 PreparedStatement stm = con.prepareStatement("delete tblUsers where usrID=?")) {

			stm.setInt(1, entity.getId());
			stm.executeUpdate();
		}
	}
}
