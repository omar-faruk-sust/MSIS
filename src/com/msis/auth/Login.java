package com.msis.auth;

import com.msis.DBConnection.*;
import com.msis.DTO.Admin;
import com.msis.DTO.Student;
import com.msis.model.AdminModel;
import com.msis.model.StudentModel;

import java.sql.*;
import java.sql.Connection;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
		
		try{
			MySQLAccess obj = new MySQLAccess();
			Connection conn = null;
			conn = obj.getConnection();
	
			// SQL Query
			PreparedStatement login = conn.prepareStatement(" select * from users where email=? and password=?");
			
			login.setString(1,email);
			login.setString(2,md5(password));
			System.out.println("Password id: " + md5(password));
			
			ResultSet result = login.executeQuery();

			String dbEmail = "null";
			int userId;
			String dbPassword = "null";
			String type = "null";
			String name = "null";
			String userType = "null";
	
			HttpSession session = request.getSession();
			
			// if login successful then set data based on user to session object
			if(result.next()){
				dbEmail = result.getString("email");
				dbPassword = result.getString("password");
				type = result.getString("type");
				
				if(type.equals("1")){
					StudentModel studentModel = new StudentModel();
					Student student = studentModel.selectStudent(dbEmail);
					name = student.getFirst_name()+ " " + student.getLast_name();
					userType = "student";
					userId = student.getId();
					session.setAttribute("studentId", userId);
				}else{
					AdminModel adminModel = new AdminModel();
					Admin admin = adminModel.selectAdmin(dbEmail);
					name = admin.getFirst_name()+ " " + admin.getLast_name();
					userType = "admin";
					userId = admin.getId();
				}
				
		        session.setAttribute("email", dbEmail);
		        session.setAttribute("password", dbPassword);
		        session.setAttribute("name", name);
		        session.setAttribute("userType", userType);
		        session.setAttribute("userId", userId);
		       
		        
		        session.setMaxInactiveInterval(30*60); //session expires in 30 minutes   
		        Cookie userEmail = new Cookie("email", dbEmail);
		        Cookie userPassword = new Cookie("password", dbPassword);
		        Cookie UType = new Cookie("userType", userType);
		        Cookie UName = new Cookie("name", name);
		        userEmail.setMaxAge(30*60);
		        userPassword.setMaxAge(30*60);
		        response.addCookie(userEmail);
		        response.addCookie(userPassword);
		        response.addCookie(UType);
		        response.addCookie(UName);
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("dashboard.jsp");
	        	requestDispatcher.forward(request, response);
	       
			}else{
				System.out.println("Mismatch");
				System.out.println(dbEmail + dbPassword);
				System.out.println(email + password);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
				request.setAttribute("errorMsg", "You email or password does not match.");
	        	requestDispatcher.forward(request, response);
			}
		}
		
		catch(Exception e){
			System.err.println(e.getMessage());

			RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
			request.setAttribute("errorMsg", "Something went wrong. Please contact system admin.");
			requestDispatcher.forward(request, response);
		}
	}
	
	/**
	 * covert the string to MD5 hash String for password
	 * @param input
	 * @return converted sash password
	 */
	public static String md5(String input) {
			
			String md5 = null;
			if(null == input) return null;
			
			try {
				
			//Create MessageDigest object for MD5
			MessageDigest digest = MessageDigest.getInstance("MD5");
			
			//Update input string in message digest
			digest.update(input.getBytes(), 0, input.length());
	
			//Converts message digest value in base 16 (hex) 
			md5 = new BigInteger(1, digest.digest()).toString(16);
	
			} catch (NoSuchAlgorithmException e) {
	
				e.printStackTrace();
			}
			return md5;
		}

	// @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
	
}
