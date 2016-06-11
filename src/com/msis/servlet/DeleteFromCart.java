package com.msis.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.msis.DBConnection.MySQLAccess;

@WebServlet("/DeleteFromCart")
public class DeleteFromCart extends HttpServlet{	
	
	public DeleteFromCart(){
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(true);
		int studentID = (Integer) session.getAttribute("userId");
		String[] courseList = request.getParameterValues("courseList");
		String successMSG = "";	
		
		if(request.getParameterValues("courseList")!=null){
		
		try{		
			MySQLAccess obj = new MySQLAccess();
			Connection conn = obj.getConnection();
			String sqlQuery=null;
			
			for(String id : courseList){
				
				int courseID = Integer.parseInt(id);
			// SQL Query
				sqlQuery="delete from registration_cart where course_details_id="+courseID+" and student_id="+studentID;
			
				PreparedStatement courseLimit = conn.prepareStatement(sqlQuery);
				courseLimit.execute();	

				
				// HTTP session
				
			}
			successMSG="Successfully Deleted from Cart";
			request.setAttribute("message", successMSG);	            
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("courseCart.jsp");
            requestDispatcher.forward(request, response);
		}
		catch(SQLException e){
			System.out.println("Something went wrong. Please contact system admin.");
			System.err.println(e.getMessage());
		}
		}
		else
		{
			successMSG="No Course Selected";
			request.setAttribute("message", successMSG);	            
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("courseCart.jsp");
            requestDispatcher.forward(request, response);
		}
	}
	
	// @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			doGet(request, response);
		}

}

