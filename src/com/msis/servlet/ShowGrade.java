package com.msis.servlet;

import com.msis.DBConnection.*;
import java.sql.*;
import java.util.ArrayList;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet implementation class ShowGrade
 */
@WebServlet("/ShowGrade")
public class ShowGrade extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowGrade() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String selected_term = request.getParameter("selected_term");
		int termId = Integer.parseInt(selected_term);
		System.out.println(termId);
		
		HttpSession session = request.getSession(true);
		MySQLAccess obj = new MySQLAccess();
		Connection connection = obj.getConnection();
		int studentId = (Integer)session.getAttribute("userId");
//		String sql = "SELECT ti.id, ti.term, CONCAT(sbj.subject_code,'-',cs.course_code,' ', cs.title) as course_title"+
//		" FROM registration_cart rgs, course_details cd, term_info ti, course cs, subject sbj where " +
//		" rgs.student_id="+ studentId + 
//		" and cs.id = cd.course_id " + 
//		" and cd.id=rgs.course_details_id " + 
//		" and cs.subject_id=sbj.id " + 
//		" and cd.term_id=ti.id "+
//		" and cd.term_id="+termId;
		
		String sql = "SELECT ti.id, ti.term, CONCAT(sbj.subject_code,'-',cs.course_code,' ', cs.title) as course_title, gpa, grade_scale," +
		" cs.units as unit FROM registration_cart rgs, course_details cd, term_info ti, course cs, subject sbj, grade grd, grading_points gp" + 
		" where rgs.student_id="+studentId+ 
		" and cs.id = cd.course_id " +  
		" and cd.id=rgs.course_details_id " + 
		" and cs.subject_id=sbj.id " +  
		" and cd.term_id=ti.id " + 
		" and cd.term_id="+termId+
		" and grd.course_id=rgs.course_details_id " +
		" and grd.gpa=gp.points";
				
		PreparedStatement prepareStm;
		try {
			prepareStm = connection.prepareStatement(sql);
			ResultSet results = prepareStm.executeQuery();
			ArrayList Rows = new ArrayList();
			while(results.next()){
				System.out.println(results.getString("course_title"));
				System.out.println(results.getString("gpa"));
				System.out.println(results.getString("grade_scale"));
				ArrayList row = new ArrayList();
				for (int i = 1; i <= 1 ; i++){
			    	row.add(results.getString("course_title"));
			    	row.add(results.getString("gpa"));
			    	row.add(results.getString("grade_scale"));
			    	row.add(results.getString("unit"));
			    }
			    Rows.add(row);
			}
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("your_grade.jsp");
			request.setAttribute("courseList", Rows);
			requestDispatcher.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
