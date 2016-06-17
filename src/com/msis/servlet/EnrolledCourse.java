package com.msis.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.msis.DBConnection.MySQLAccess;
import com.msis.model.StudentModel;

@WebServlet("/EnrolledCourse")
public class EnrolledCourse extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EnrolledCourse() {
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		int studentId;
		if(session.getAttribute("studentId")!=null){
			studentId = (Integer) session.getAttribute("studentId");	
		}
		else
		{
			studentId= Integer.parseInt(request.getParameter("studentId"));
			// check the student validity for admin enroll
			StudentModel studentModel = new StudentModel();
			if(studentModel.validateStudent(studentId) == false){
				request.setAttribute("errorMsg", "Not a valid student id.");
				RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/searchCourse.jsp");
				requestDispatcher.forward(request, response);
			}
		}
		
		int termId = Integer.parseInt(request.getParameter("termId"));
		String type = request.getParameter("type");
		String successMSG = "";
		ArrayList courseList = null;
		RequestDispatcher requestDispatcher;
		
		try {
			MySQLAccess obj = new MySQLAccess();
			Connection conn = obj.getConnection();
			String sqlQuery = null;

			sqlQuery = "SELECT cd.id, ti.term as term,CONCAT(ins.first_name,' ',ins.last_name) as instructor,GROUP_CONCAT(CONCAT(sch.day,' ',sch.start_time,' - ',sch.end_time) SEPARATOR ' & ') as time,"
					+ " CONCAT(bld.name,' ',rm.room_no,' ',bld.campus_short) as location, CONCAT(sbj.subject_code,' ',cs.course_code,' - ',cs.title) as course, "
					+ " if((cd.class_capacity -(select count(*) from registration rgs where cd.id=rgs.course_details_id))>0,'Open','Closed') as status, cs.units as unit"
					+ " FROM course_details cd, course cs, subject sbj, term_info ti, instructor ins, schedule sch, course_schedule csch, room rm, building bld, registration rc"
					+ " WHERE rc.course_details_id=cd.id AND" + " cd.term_id=ti.id AND" + " ti.id=? AND"
					+ " cd.instructor_id=ins.id AND" + " cd.room_id=rm.id AND" + " rm.building_id =bld.id AND"
					+ " cd.course_id=cs.id AND" + " cs.subject_id=sbj.id and" + " csch.schedule_id=sch.id AND"
					+ " csch.course_detail_id=cd.id AND" + " rc.student_id=?" + " GROUP BY csch.course_detail_id";

			PreparedStatement course = conn.prepareStatement(sqlQuery);
			// PreparedStatement course = conn.prepareStatement(" select id,
			// name, status_client from test.projects where client=? ");
			course.setInt(1, termId);
			course.setInt(2, studentId);

			ResultSet result = course.executeQuery();
			ArrayList Rows = new ArrayList();
			while (result.next()) {
				// System.out.println(dbID + dbName + dbStatusClient);
				ArrayList row = new ArrayList();
				for (int i = 1; i <= 1; i++) {
					row.add(result.getString("id"));
					row.add(result.getString("course"));
					row.add(result.getString("instructor"));
					row.add(result.getString("term"));
					row.add(result.getString("time"));
					row.add(result.getString("location"));
					row.add(result.getString("unit"));
					row.add(result.getString("status"));
				}
				Rows.add(row);
				courseList = Rows;
			}

			request.setAttribute("courseList", courseList);
			if (type.equals("enroll")) {
				requestDispatcher = request.getRequestDispatcher("enrolledCourse.jsp");
			} else {
				requestDispatcher = request.getRequestDispatcher("dropCourse.jsp");
			}
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

}
