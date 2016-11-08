package ch.bfh.awebt.login.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Account {

	private int id;
	private String user;
	private String title;
	private String website;
	private String username;
	private String password;
	
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

	public boolean save() throws Exception {
		String query = "INSERT INTO account (user,title,website,username,password) VALUES ('" + this.user + "','" + this.title + "','" + this.website + "','" + this.username + "','" + this.password + "')";
		DataAccess acc = new DataAccess();
		return acc.executeUpdate(query);
	}
	
	public boolean update() throws Exception {
		String query = "UPDATE account SET title='" + this.title + "', website='" + this.website + "', username='" + this.username + "', password='" + this.password + "' WHERE id='" + this.id + "'";;
		DataAccess acc = new DataAccess();
		return acc.executeUpdate(query);		
	}
	
	public boolean delete() throws Exception {
		String query = "DELETE FROM account WHERE id='" + id + "'";
		DataAccess acc = new DataAccess();
		return acc.executeUpdate(query);
	}
	
	public static Account getAccount(int id) throws Exception {
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
		}
		return acc;		
	}
	
	public static ArrayList<Account> getAccounts(String username) throws Exception {
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
			result.add(acc);
		}
		return result;
	}
	
	
}
