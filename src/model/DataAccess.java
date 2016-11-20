package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//Date Access class
//Original Idea was to design a class that abstracts the database connection
//from application logic. Thats why the Account and User class form sql querys
//and call this class to persist the data.
//Sql escaping is only possible with a connection, so this design doesn't work
//TODO: Extend this class, so that it handles the querys as well. User class
//for example just should Call dataAccess.saveUser(User). So this class can create
//Prepared statment so sql injection is no longer possible.
public class DataAccess {
	private Connection conn;
	
	public DataAccess() throws Exception {
		try {
			conn = this.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	//Creates a connection to the database server
	private Connection getConnection() throws Exception {
	    Connection conn = null;
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", "JSFLogin");
	    connectionProps.put("password", "JSFLogin");
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", connectionProps);
        Statement stt = conn.createStatement();
        stt.execute("USE jsflogin");
	    System.out.println("Connected to database");
	    return conn;
	}
	
	//Executes a query. The query cannot change data but returns a result
	public ResultSet executeQuery(String query) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} 
		
		return rs;
	}
	
	//Executes a query. This modifies data but doesn't return information
	//Result just true or false if success.
	public boolean executeUpdate(String query) throws Exception {
		Statement stmt = null;
		boolean result = false;
		try {
			stmt = conn.createStatement();
			if(stmt.executeUpdate(query) > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
	        	} catch (SQLException e) {
	        		e.printStackTrace();
	        		throw e;
	        	} 
	        }
	    }
		return result;
	}
}
