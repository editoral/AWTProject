package ch.bfh.awebt.login.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import ch.bfh.awebt.login.model.DataAccess;

public class User {
	private Integer id;
	private String username;
	private String password;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean save() throws Exception {
		String query = "INSERT INTO user (username,password) VALUES ('" + this.username + "','" + this.password + "')";
		DataAccess acc = new DataAccess();
		return acc.executeUpdate(query);
	}
	
	public boolean loadUser(String username) throws Exception {
		ResultSet result = this.getUser(username);
		boolean success = false;
		try {
			if(result.next()){
				this.username = result.getString("username");
				this.password = result.getString("password");
			}
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return success;
	}
	
	public boolean validatePassword(String password) {
		return this.password.equals(password);
	}
	
	private ResultSet getUser(String username) throws SQLException {
		String query = "SELECT * FROM user WHERE username='" + username + "'";
		DataAccess acc = new DataAccess();
		return acc.executeQuery(query);
	}
}
