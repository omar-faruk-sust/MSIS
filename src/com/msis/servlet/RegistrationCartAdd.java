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

@WebServlet("/RegistrationCartAdd")
public class RegistrationCartAdd extends HttpServlet {

	public RegistrationCartAdd() {
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		int studentId = 0;
		if (session.getAttribute("userType").equals("admin")) {
			studentId = (Integer) session.getAttribute("studentId");			
		} else {
			studentId = (Integer) session.getAttribute("userId");
			session.setAttribute("studentId", studentId);
		}

		int courseID = Integer.parseInt(request.getParameter("courseID"));

		int type = 1;

		try {
			MySQLAccess obj = new MySQLAccess();
			Connection conn = obj.getConnection();
			String sqlQuery = null;
			int dbCount = 0;
			// SQL Query
			sqlQuery = "select count(*) as count from registration_cart where course_details_id=" + courseID
					+ " and student_id=" + studentId;

			PreparedStatement courseLimit = conn.prepareStatement(sqlQuery);
			ResultSet result1 = courseLimit.executeQuery();
			while (result1.next()) {
				dbCount = Integer.parseInt(result1.getString("count"));
			}

			if (dbCount < 1) {
				PreparedStatement registerCourse = conn.prepareStatement(
						"insert into registration_cart(student_id,course_details_id, status)" + "values(?,?,?)");
				registerCourse.setInt(1, studentId);
				registerCourse.setInt(2, courseID);
				registerCourse.setInt(3, type);
				int result2 = registerCourse.executeUpdate();
				try {
					if (null != registerCourse) {
						registerCourse.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				String successMSG = "Course successfully added to cart.";
				request.setAttribute("successMsg", successMSG);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("courseList.jsp");
				requestDispatcher.forward(request, response);
			} else if (dbCount > 0) {
				String errorMSG = "Course already in Cart.";

				// HTTP session
				request.setAttribute("errorMsg", errorMSG);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("courseList.jsp");
				requestDispatcher.forward(request, response);
			}

			else {
				System.out.println("Database operation unsuccessful.");
				request.setAttribute("errorMsg", "Operation unsuccessful. Please try again!");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("courseList.jsp");
				requestDispatcher.forward(request, response);
			}
		} catch (Exception e) {
			System.out.println("Something went wrong. Please contact system admin.");
			System.err.println(e.getMessage());
		}
	}

	// @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	// response)
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
