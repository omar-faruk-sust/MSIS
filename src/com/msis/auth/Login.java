package com.msis.auth;
import com.msis.DBConnection.*;
import com.msis.DTO.Admin;
import com.msis.DTO.Student;
import com.msis.model.AdminModel;
import com.msis.model.StudentModel;

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
		
		try{
			
			MySQLAccess obj = new MySQLAccess();
			Connection conn = null;
			conn = obj.getConnection();
			
			// SQL Query
			PreparedStatement login = conn.prepareStatement(" select * from Student_information_system.users where email=? and password=?");
			
			login.setString(1,email);
			login.setString(2,password);
			
			ResultSet result = login.executeQuery();
			
			String dbEmail = "null";
			String dbPassword = "null";
			String type = "null";
			String name = "null";
			String userType = "null";
			//String errorMsg = "null";
			if(result.next()){
				dbEmail = result.getString("email");
				dbPassword = result.getString("password");
				type = result.getString("type");
				
				System.out.println(email + password);
				System.out.println(dbEmail + dbPassword);
				
				if(type.equals("1")){
					StudentModel studentModel = new StudentModel();
					Student student = studentModel.selectStudent(dbEmail);
					name = student.getFirst_name()+ " " + student.getLast_name();
					userType = "student";
				}else{
					AdminModel adminModel = new AdminModel();
					Admin admin = adminModel.selectAdmin(dbEmail);
					name = admin.getFirst_name()+ " " + admin.getLast_name();
					userType = "admin";
				}
				
				HttpSession session = request.getSession();
		        session.setAttribute("email", dbEmail);
		        session.setAttribute("password", dbPassword);
		        session.setAttribute("name", name);
		        session.setAttribute("userType", userType);
		        
		        session.setMaxInactiveInterval(30*60); //session expires in 30 minutes   
		        Cookie userEmail = new Cookie("email", dbEmail);
		        Cookie userPassword = new Cookie("password", dbPassword);
		        userEmail.setMaxAge(30*60);
		        userPassword.setMaxAge(30*60);
		        response.addCookie(userEmail);
		        response.addCookie(userPassword);
				
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
			System.out.println("Something went wrong. Please contact system admin.");
			System.err.println(e.getMessage());
		}
	}

	// @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
	
}