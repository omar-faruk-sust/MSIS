package com.msis.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
		
		// JDBC driver name and database URL
	   	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   	static final String DB_URL = "jdbc:mysql://localhost/Student_information_system";

	   	//  Database credentials
	   	static final String USER = "root";
	   	static final String PASS = "root";
	   	Connection conn = null;
	   	
	   	public Connection DBConnect() throws ClassNotFoundException {
	     
	   		try{	   			
	   			
	   			// Register JDBC driver
	   			Class.forName("com.mysql.jdbc.Driver");
	   			
	   			// Establish connection
	   			System.out.println("Connecting to database...");
	   			conn = DriverManager.getConnection(DB_URL,USER,PASS);
	   			System.out.println("Connected.");
	   		}
	   
	   		catch(SQLException se){
	   			se.printStackTrace(); //Handle errors for JDBC
	   		}   		
	   		return conn;   
	   	}
	   
	   	public void DBDisconnect(){
	   		try {
	   			conn.close();
	   		}
	   		catch (SQLException e) {
	   			e.printStackTrace();
	   		}
	   		
	   		System.out.println("Database connection terminated.");
	   	}
	   	
}