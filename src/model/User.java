package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

import model.DataAccess;

public class User {
	private Integer id;
	private String username;
	private String password;
	private String salt;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) throws Exception {
		System.out.println("Register salt: " + this.salt);
		System.out.println("Register bevor: " + password);
		this.password = this.salt + password;
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(this.password.getBytes("UTF-8"));
		byte[] digest = md.digest();
		this.password = String.format("%064x", new java.math.BigInteger(1, digest));
		System.out.println("Register: " + this.password);
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}	
	
	public boolean save() throws Exception {
		String query = "INSERT INTO user (username,password,salt) VALUES ('" + this.username + "','" + this.password + "','" + this.salt + "')";
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
				this.salt = result.getString("salt");
			}
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return success;
	}
	
	public boolean validatePassword(String password) throws Exception {
		String test = this.salt + password;
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(test.getBytes("UTF-8"));
		byte[] digest = md.digest();
		test = String.format("%064x", new java.math.BigInteger(1, digest));
		System.out.println("Test: " + test);
		return this.password.equals(test);
	}
	
	private ResultSet getUser(String username) throws Exception {
		String query = "SELECT * FROM user WHERE username='" + username + "'";
		DataAccess acc = new DataAccess();
		return acc.executeQuery(query);
	}
}
