package ch.bfh.awebt.login.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DataAccess {
	private Connection conn;
	
	public DataAccess() {
		try {
			conn = this.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
	        if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} }
	    }
		return result;
	}
}
