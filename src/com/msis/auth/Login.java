package com.msis.auth;
import com.msis.DBConnection.*;
import com.msis.DTO.Admin;
import com.msis.DTO.Student;

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
	
		String name = "Test Test";
		
		try{			
			// Establish Connection
			DBConnection obj = new DBConnection();
			Connection conn = null;
			conn = obj.DBConnect();
			
			// SQL Query
			PreparedStatement login = conn.prepareStatement(" select * from Student_information_system.users where email=? and password=?");
			
			login.setString(1,email);
			login.setString(2,password);
			
			ResultSet result = login.executeQuery();
			
			String dbEmail = "null";
			String dbPassword = "null";
			String type = "null";
			
			if(result.next()){
				dbEmail = result.getString("email");
				dbPassword = result.getString("password");
				type = result.getString("type");
				
				System.out.println(email + password);
				System.out.println(dbEmail + dbPassword);
				
				if(type.equals("1")){
					System.out.println("student");
				Student student = this.selectStudent(dbEmail);
				System.out.println(student);
				}else{
					System.out.println("admin");
					Admin admin = this.selectAdmin(dbEmail);
				}
				
				
				HttpSession session = request.getSession();
		        session.setAttribute("email", dbEmail);
		        session.setAttribute("password", dbPassword);
		        // for testing
		        session.setAttribute("name", name);
		        
		        session.setMaxInactiveInterval(30*60); //session expires in 30 minutes   
		        Cookie userEmail = new Cookie("email", dbEmail);
		        Cookie userPassword = new Cookie("password", dbPassword);
		        userEmail.setMaxAge(30*60);
		        userPassword.setMaxAge(30*60);
		        response.addCookie(userEmail);
		        response.addCookie(userPassword);
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("dashboard.jsp");
	        	requestDispatcher.forward(request, response);
	       
			}
				     
			else{
				System.out.println("Mismatch");
				System.out.println(dbEmail + dbPassword);
				System.out.println(email + password);
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
	
	public Student selectStudent(String email) {
		Student student = null;
		String sql = "select * from student where email=?";
		
		// Establish Connection
		DBConnection obj = new DBConnection();
		Connection connection = null;
		try {
			connection = obj.DBConnect();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			PreparedStatement prepareStm = connection.prepareStatement(sql);
			prepareStm.setString(1, email);
			ResultSet results = prepareStm.executeQuery();
			while(results.next()){
				email = results.getString("email");
				
				int id = results.getInt("id");
				String first_name = results.getString("first_name");
				String last_name = results.getString("last_name");
				String mobile = results.getString("mobile");
				String address = results.getString("address");
	
				student = new Student(id, first_name, last_name, email,mobile, address);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			obj.DBDisconnect();
		}
		return student;
	}
	
	public Admin selectAdmin(String email) {
		Admin admin = null;
		String sql = "select * from admin where email=?";
		
		// Establish Connection
		DBConnection obj = new DBConnection();
		Connection connection = null;
		try {
			connection = obj.DBConnect();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			PreparedStatement prepareStm = connection.prepareStatement(sql);
			prepareStm.setString(1, email);
			ResultSet results = prepareStm.executeQuery();
			while(results.next()){
				email = results.getString("email");
				
				int id = results.getInt("id");
				String first_name = results.getString("first_name");
				String last_name = results.getString("last_name");
				String address = results.getString("address");
				int emp_id = results.getInt("emp_id");
	
				admin = new Admin(id, first_name, last_name, email, emp_id, address);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			obj.DBDisconnect();
		}
		return admin;
	}
	
	
}