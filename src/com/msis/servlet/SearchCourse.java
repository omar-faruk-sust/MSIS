package com.msis.servlet;

import com.msis.DBConnection.*;
import com.msis.model.StudentModel;

import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

@WebServlet("/SearchCourse")
public class SearchCourse extends HttpServlet {

	MySQLAccess obj = new MySQLAccess();
	Connection conn = obj.getConnection();

	public SearchCourse() {

	}

	// @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	// response)
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String term = request.getParameter("term");
		String program = request.getParameter("program");
		String subject = request.getParameter("subject");
		String courseNo = request.getParameter("courseNo");
		int studentId = 0;

		if (session.getAttribute("userType").equals("admin")) {
			studentId = Integer.parseInt(request.getParameter("studentID"));
			session.setAttribute("studentId", studentId);
			// check the student validity
			StudentModel studentModel = new StudentModel();
			if(studentModel.validateStudent(studentId) == false){
				request.setAttribute("errorMsg", "Not a valid student id.");
				RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/searchCourse.jsp");
				requestDispatcher.forward(request, response);
			}
		} else {
			studentId = (Integer) session.getAttribute("userId");
			session.setAttribute("studentId", studentId);
		} 

		try {
			// Establish Connection
			String sqlQuery = null;

			sqlQuery = "SELECT cd.id, ti.term as term,CONCAT(ins.first_name,' ',ins.last_name) as instructor,GROUP_CONCAT(CONCAT(sch.day,' ',sch.start_time,' - ',sch.end_time) SEPARATOR ' & ') as time, CONCAT(bld.name,' ',rm.room_no,' ',bld.campus_short) as location, CONCAT(sbj.subject_code,' ',cs.course_code,' - ',cs.title) as course";
			sqlQuery += ", if((cd.class_capacity -(select count(*) from registration rgs where cd.id=rgs.course_details_id))>0,'Open','Closed') as status, CONCAT(DATE_FORMAT(ti.start_date,'%d/%m/%Y'),' - ', DATE_FORMAT(DATE_ADD(ti.start_date, INTERVAL cd.duration week),'%d/%m/%Y')) as meeting";
			sqlQuery += " FROM course_details cd, course cs, subject sbj, term_info ti, instructor ins, schedule sch, course_schedule csch, room rm, building bld";
			sqlQuery += " WHERE ti.id=? and";
			sqlQuery += " cs.level=? AND";
			sqlQuery += " sbj.subject_code=? AND";
			if (courseNo != "") {
				sqlQuery += " cs.course_code=? AND";
			}
			sqlQuery += " cd.term_id=ti.id AND";
			sqlQuery += " cd.instructor_id=ins.id and";
			sqlQuery += " cd.room_id=rm.id AND";
			sqlQuery += " rm.building_id =bld.id AND";
			sqlQuery += " cd.course_id=cs.id AND";
			sqlQuery += " cs.subject_id=sbj.id and";
			sqlQuery += " csch.schedule_id=sch.id AND";
			sqlQuery += " csch.course_detail_id=cd.id";
			sqlQuery += " GROUP BY csch.course_detail_id";

			System.out.print(sqlQuery + "\n");
			// String sqlQuery="SELECT ti.term as term,CONCAT(ins.first_name,'
			// ',ins.last_name) as instructor,CONCAT(sch.day,'
			// ',sch.start_time,' - ',sch.end_time) as time, CONCAT(bld.name,'
			// ',rm.room_no,' ',bld.campus_short) as location,
			// CONCAT(sbj.subject_code,' ',cs.course_code,' - ',cs.title) as
			// course FROM course_details cd, course cs, subject sbj, term_info
			// ti, instructor ins, schedule sch, room rm, building bld WHERE
			// ti.id=? and cs.level=? AND sbj.subject_code=? AND
			// cd.term_id=ti.id and cd.instructor_id=ins.id and
			// cd.schedule_id=sch.id AND cd.room_id=rm.id AND rm.building_id
			// =bld.id AND cd.course_id=cs.id AND cs.subject_id=sbj.id";

			// SQL Query
			PreparedStatement course = conn.prepareStatement(sqlQuery);
			// PreparedStatement course = conn.prepareStatement(" select id,
			// name, status_client from test.projects where client=? ");
			course.setString(1, term);
			course.setString(2, program);
			course.setString(3, subject);

			if (courseNo != "") {
				course.setString(4, courseNo);
			}

			ResultSet result = course.executeQuery();
			ArrayList Rows = new ArrayList();
			while (result.next()) {
				// System.out.println(dbID + dbName + dbStatusClient);
				ArrayList row = new ArrayList();
				for (int i = 1; i <= 1; i++) {
					row.add(result.getString("id"));
					row.add(result.getString("term"));
					row.add(result.getString("instructor"));
					row.add(result.getString("time"));
					row.add(result.getString("location"));
					row.add(result.getString("course"));
					row.add(result.getString("status"));
					row.add(result.getString("meeting"));
					PreparedStatement registered = conn.prepareStatement(
							"select if(count(*)>0,1,0) as registered from registration where course_details_id="
									+ result.getString("id") + " and student_id=" + studentId);
					ResultSet regiResult = registered.executeQuery();
					while (regiResult.next()) {

						if (Integer.parseInt(regiResult.getString("registered")) < 1) {

							PreparedStatement registeredCart = conn.prepareStatement(
									"select if(count(*)>0,1,0) as registered from registration_cart where course_details_id="
											+ result.getString("id") + " and student_id=" + studentId);
							ResultSet cartResult = registeredCart.executeQuery();
							while (cartResult.next()) {
								row.add(cartResult.getString("registered"));
							}
						} else {
							row.add(regiResult.getString("registered"));
						}
						System.out.print(regiResult.getString("registered"));
					}
				}
				Rows.add(row);
			}
			if(Rows.size()<1)
			{
				request.setAttribute("errorMsg", "No Data Found. Please Try With Different Value");
				RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/searchCourse.jsp");
				requestDispatcher.forward(request, response);
			}
			else{
			session.setAttribute("courseList", Rows);
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/courseList.jsp");
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

	private void doPerform(HttpServletRequest request, HttpServletResponse response) {

	}

}
