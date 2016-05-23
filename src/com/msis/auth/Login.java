package com.msis.auth;
import com.msis.DBConnection.*;
import java.sql.*;
import java.sql.Connection;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

// Servlet implementation class Login
@WebServlet("/Login")

public class Login extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
    
	// Default constructor
    public Login(){
    	
    }

	//@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String email = request.getParameter("email");
		String password = request.getParameter("password");	
		String type = "0";
		
		try{			
			// Establish Connection
			DBConnection obj = new DBConnection();
			Connection conn = null;
			conn = obj.DBConnect();
			
			// SQL Query
			PreparedStatement login = conn.prepareStatement(" select * from test.users where email=? and password=? and type=? ");
			
			login.setString(1,email);
			login.setString(2,password);
			login.setString(3,type);
			
			ResultSet result = login.executeQuery();
			
			String dbEmail = "null";
			String dbPassword = "null";
			String userType = "null";
			
			if(result.next()){
				dbEmail = result.getString("email");
				dbPassword = result.getString("password");
				userType = result.getString("type");
				
				System.out.println(userType);
				System.out.println(email + password + type);
				System.out.println(dbEmail + dbPassword + userType);
				
				HttpSession session = request.getSession();
		        session.setAttribute("email", dbEmail);
		        session.setAttribute("password", dbPassword);
		        session.setMaxInactiveInterval(30*60); //session expires in 30 minutes   
		        Cookie userEmail = new Cookie("email", dbEmail);
		        Cookie userPassword = new Cookie("password", dbPassword);
		        userEmail.setMaxAge(30*60);
		        userPassword.setMaxAge(30*60);
		        response.addCookie(userEmail);
		        response.addCookie(userPassword);
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("dashboard.jsp");
	        	requestDispatcher.forward(request, response);
	        	System.out.println("i am here after");
			}
				     
			else{
				System.out.println("Mismatch");
				System.out.println(dbEmail + dbPassword + userType);
				System.out.println(email + password + type);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
	        	requestDispatcher.forward(request, response);
			}
		}
		
		catch(Exception e){
			System.out.println("Something went wrong. Please contact system admin.");
			System.err.println(e.getMessage());
		}
	}

	// @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
	
}