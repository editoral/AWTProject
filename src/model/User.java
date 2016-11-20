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
	
	//Getter and Setter
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	
	//Sets password after hashing
	//Precondition: Salt needs to be set before password!!!
	public void setPassword(String password) throws Exception {
		System.out.println("Register salt: " + this.salt);
		System.out.println("Register bevor: " + password);
		//Salt is concatenated before password!
		//so in the rare case that password is Plaintext and not sha-512 hash (javascript on client not working)
		//large entropy region comes before low entropy region 
		this.password = this.salt + password;
		//MessageDigest used for SHA-256 hash algorithm so salt makes sense.
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		//Everythin encoded UTF-8
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
	
	//Saves an user with the Query account with current class member values
	//Primary key is username, unique true. If it already exists, error is thrown
	public boolean save() throws Exception {
		String query = "INSERT INTO user (username,password,salt) VALUES ('" + this.username + "','" + this.password + "','" + this.salt + "')";
		DataAccess acc = new DataAccess();
		return acc.executeUpdate(query);
	}
	
	//Update given user with current class member values, based on primary key username.
	public boolean update() throws Exception {
		String query = "UPDATE user SET password='" + this.password + "', salt='" + this.salt + "' WHERE username='" + this.username + "'";
		DataAccess acc = new DataAccess();
		return acc.executeUpdate(query);
	}
	
	//User needs to be loaded so login can proceed. 
	//load functions populates member variables from database
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
	
	//Actual login validation based on salt and requested password match against password in database
	public boolean validatePassword(String password) throws Exception {
		String test = this.salt + password;
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(test.getBytes("UTF-8"));
		byte[] digest = md.digest();
		test = String.format("%064x", new java.math.BigInteger(1, digest));
		System.out.println("Test: " + test);
		return this.password.equals(test);
	}
	
	//helper method, returns ResultSet with a user
	private ResultSet getUser(String username) throws Exception {
		String query = "SELECT * FROM user WHERE username='" + username + "'";
		DataAccess acc = new DataAccess();
		return acc.executeQuery(query);
	}
}
