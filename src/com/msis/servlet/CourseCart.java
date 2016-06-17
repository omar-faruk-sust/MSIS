package com.msis.servlet;

import com.msis.DBConnection.*;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CourseCart")
public class CourseCart extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MySQLAccess obj = new MySQLAccess();
	Connection conn = obj.getConnection();

	public CourseCart() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		int studentId = 0;

		int term = Integer.parseInt(request.getParameter("termId"));
		

		if (session.getAttribute("userType").equals("admin")) {
			studentId = Integer.parseInt(request.getParameter("studentId"));
			session.setAttribute("studentId", studentId);
		} else {
			studentId = (Integer) session.getAttribute("userId");
			session.setAttribute("studentId", studentId);
		}

		System.out.println(term);
		
		try {
			// Establish Connection
			String sqlQuery = null;

			sqlQuery = "SELECT cd.id, ti.term as term,CONCAT(ins.first_name,' ',ins.last_name) as instructor,GROUP_CONCAT(CONCAT(sch.day,' ',sch.start_time,' - ',sch.end_time) SEPARATOR ' & ') as time,"
					+ " CONCAT(bld.name,' ',rm.room_no,' ',bld.campus_short) as location, CONCAT(sbj.subject_code,' ',cs.course_code,' - ',cs.title) as course"
					+ " , if((cd.class_capacity -(select count(*) from registration rgs where cd.id=rgs.course_details_id))>0,'Open','Closed') as status,"
					+ " cs.units as unit"
					+ " FROM course_details cd, course cs, subject sbj, term_info ti, instructor ins, schedule sch, course_schedule csch, room rm, building bld, registration_cart rc"
					+ " WHERE rc.course_details_id=cd.id AND" + " cd.term_id=ti.id AND" + " cd.instructor_id=ins.id AND"
					+ " cd.room_id=rm.id AND" + " rm.building_id =bld.id AND" + " cd.course_id=cs.id AND"
					+ " cs.subject_id=sbj.id and" + " csch.schedule_id=sch.id AND" + " csch.course_detail_id=cd.id AND"
					+ " rc.student_id=?";			
			if (term > 0) {
				sqlQuery +=" and ti.id=?";
			}			
			sqlQuery +=" GROUP BY csch.course_detail_id";

			// SQL Query
			PreparedStatement cart = conn.prepareStatement(sqlQuery);
			cart.setInt(1, studentId);

			if (term > 0) {
				cart.setInt(2, term);
			}

			ResultSet result = cart.executeQuery();
			ArrayList Rows = new ArrayList();
			while (result.next()) {
				ArrayList row = new ArrayList();
				for (int i = 1; i <= 1; i++) {
					row.add(result.getString("id"));
					row.add(result.getString("term"));
					row.add(result.getString("instructor"));
					row.add(result.getString("time"));
					row.add(result.getString("location"));
					row.add(result.getString("course"));
					row.add(result.getString("status"));
					row.add(result.getString("unit"));
				}
				Rows.add(row);
			}
			if(Rows.size()<1)
			{
				request.setAttribute("errorMsg", "No Data Found. Please try again.");
			}
			request.setAttribute("courseList", Rows);
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/courseCart.jsp");
			requestDispatcher.forward(request, response);
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
