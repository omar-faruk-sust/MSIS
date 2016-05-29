package com.msis.DBConnection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;
import java.sql.PreparedStatement; // not the sql one
import java.sql.ResultSet;

public class MySQLAccess {
	
	// JDBC driver name and database URL
   	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   	static final String DB_URL = "jdbc:mysql://localhost/Student_information_system";

   	//  Database credentials
   	static final String USER = "root";
   	static final String PASS = "root";
	
	public Connection getConnection(){
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return connection;
		
	}
	
	public void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
