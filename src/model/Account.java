package model;

import java.sql.ResultSet;
import java.util.ArrayList;

import model.DataAccess;

public class Account {

	private int id;
	private String user;
	private String title;
	private String website;
	private String username;
	private String password;
	private String iv;
	private String salt;
	
	
	// Getter and Setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIv() {
		return iv;
	}
	public void setIv(String iv) {
		this.iv = iv;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	//Saves an account with the Query account with current class member values
	//Id is autoincrement in database
	public boolean save() throws Exception {
		//TODO: Prevent Sql injection with prepared statement 
		String query = "INSERT INTO account (user,title,website,username,password,iv,salt) VALUES ('" + this.user + "','" + this.title + "','" + this.website + "','" + this.username + "','" + this.password + "','" + this.iv + "','" + this.salt + "')";
		DataAccess acc = new DataAccess();
		return acc.executeUpdate(query);
	}
	
	//Update given account with current class member values, based on primary key id.
	public boolean update() throws Exception {
		//TODO: Prevent Sql injection with prepared statement 
		String query = "UPDATE account SET title='" + this.title + "', website='" + this.website + "', username='" + this.username + "', password='" + this.password + "', iv='" + this.iv + "', salt='" + this.salt + "' WHERE id='" + this.id + "'";
		DataAccess acc = new DataAccess();
		return acc.executeUpdate(query);		
	}
	
	//Delete an account based on the primary key id
	public boolean delete() throws Exception {
		//TODO: Prevent Sql injection with prepared statement 
		String query = "DELETE FROM account WHERE id='" + id + "'";
		DataAccess acc = new DataAccess();
		return acc.executeUpdate(query);
	}
	
	//A single account can be retrieved so the enduser can edit the account values.
	public static Account getAccount(int id) throws Exception {
		//TODO: Prevent Sql injection with prepared statement 
		String query = "SELECT * FROM account WHERE id='" + id + "'";
		DataAccess access = new DataAccess();
		ResultSet res = access.executeQuery(query);
		Account acc = new Account();
		if(res.next()) {
			acc.id = res.getInt("id");
			acc.title = res.getString("title");
			acc.user = res.getString("user");
			acc.username = res.getString("username");
			acc.website = res.getString("website");
			acc.password = res.getString("password");
			acc.iv = res.getString("iv");
			acc.salt = res.getString("salt");
		}
		return acc;		
	}
	
	//Retrieves all accounts for a certain user. Every user can see all his accounts.
	public static ArrayList<Account> getAccounts(String username) throws Exception {
		//TODO: Prevent Sql injection with prepared statement 
		String query = "SELECT * FROM account WHERE user='" + username + "'";
		DataAccess access = new DataAccess();
		ResultSet res = access.executeQuery(query);
		ArrayList<Account> result = new ArrayList<Account>();
		while(res.next()) {
			Account acc = new Account();
			acc.id = res.getInt("id");
			acc.title = res.getString("title");
			acc.user = res.getString("user");
			acc.username = res.getString("username");
			acc.website = res.getString("website");
			acc.password = res.getString("password");
			acc.iv = res.getString("iv");
			acc.salt = res.getString("salt");
			result.add(acc);
		}
		return result;
	}
	
	
}
