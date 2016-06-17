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

@WebServlet("/DropCourse")
public class DropCourse extends HttpServlet {

	public DropCourse() {
		// TODO Auto-generated constructor stub
	}
	String errorMsg = "";
	String successMsg="";
	MySQLAccess obj = new MySQLAccess();
	Connection conn = obj.getConnection();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		int studentID = (Integer) session.getAttribute("userId");
		System.out.println(studentID);
		int courseID = Integer.parseInt(request.getParameter("courseID"));
		System.out.println(courseID);
		String successMSG = "";

		try {

			String sqlQuery = "";
			String delSqlQuery = "";
			String eligible = "no";

			sqlQuery = "select if(curdate()<= ti.disc_date,'yes','no') as eligible, ti.id from course_details cd, term_info ti where cd.term_id = ti.id AND cd.id="
					+ courseID;
			System.out.println(sqlQuery);
			PreparedStatement checkDate = conn.prepareStatement(sqlQuery);
			ResultSet result1 = checkDate.executeQuery();
			while (result1.next()) {
				eligible = result1.getString("eligible");
				session.setAttribute("termId", result1.getInt("id"));
			}

			if (session.getAttribute("userType").equals("admin") || eligible.equals("yes")) {
				delSqlQuery = "delete from registration where course_details_id=" + courseID + " and student_id="
						+ studentID;

				PreparedStatement courseDrop = conn.prepareStatement(delSqlQuery);
				courseDrop.execute();
				successMsg = "Successfully Dropped the Couse";
			} else {
				if (eligible.equals("no")) {
					errorMsg = "DNE Date is Over, You Cann't Drop. Please Contact With Admin";
				} else {
					errorMsg = "Please Contact With Admin";
				}
			}
			request.setAttribute("errorMsg", errorMsg);
			request.setAttribute("successMsg", successMsg);
			deleteCourseFromGrade(studentID,courseID);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("dropCourse.jsp");
			requestDispatcher.forward(request, response);
		} catch (SQLException e) {
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

	public void deleteCourseFromGrade(int studentID, int courseID) {

		try {
			String delSqlQuery = "";
			delSqlQuery = "delete from grade where course_id=" + courseID + " and student_id="
					+ studentID;

			PreparedStatement courseDelete = conn.prepareStatement(delSqlQuery);
			courseDelete.execute();
		} catch (SQLException e) {
			System.out.println("Something went wrong. Please contact system admin.");
			System.err.println(e.getMessage());
		}

	}

}
