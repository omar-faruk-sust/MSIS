package com.msis.servlet;

import com.msis.DBConnection.*;
import java.sql.*;
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

@WebServlet("/RegisterCourse")
public class RegisterCourse extends HttpServlet {

	MySQLAccess obj = new MySQLAccess();
	Connection conn = obj.getConnection();

	private static final long serialVersionUID = 1L;
	String successMSG = "";
	int type = 1;
	
	// Default constructor
	public RegisterCourse() {
		// TODO Auto-generated constructor stub
	}

	// @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	// response)
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		int studentId = 0;
		if (session.getAttribute("userType").equals("admin")) {
			studentId = Integer.parseInt(session.getAttribute("studentId").toString());
			session.setAttribute("studentId", studentId);
		} else {
			studentId = (Integer) session.getAttribute("userId");
			session.setAttribute("studentId", studentId);
		}
		String[] courseList = request.getParameterValues("courseList");
		

		int amountDue = 0;
		int dneValue = 0;
		

		// Check payment
		try {
			String dueSqlQuery = "";
			dueSqlQuery = "select ((select IFNULL(SUM(due_amount), 0)from due where student_id=" + studentId
					+ ")-(select IFNULL(SUM(paid_amount), 0)from payment where student_id=" + studentId
					+ ")) as amt_due  from dual";
			PreparedStatement payment = conn.prepareStatement(dueSqlQuery);
			ResultSet result = payment.executeQuery();
			while (result.next()) {
				amountDue = Integer.parseInt(result.getString("amt_due"));
				System.out.println("Amount:" + amountDue);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (request.getParameterValues("courseList") != null) {
			// Check DNE date
			if (amountDue <= 0) {
				for (String id : courseList) {
					int courseID = Integer.parseInt(id);
					String termValue = null;
					int termID = 0;
					boolean conflict = false;

					try {
						String dneSqlQuery = "";
						dneSqlQuery = "select if(ti.dne_date>CURDATE(),1,0) as dne, ti.id as id, ti.term from course_details cd, term_info ti where cd.term_id=ti.id and cd.id="
								+ courseID;
						PreparedStatement dne = conn.prepareStatement(dneSqlQuery);
						ResultSet result1 = dne.executeQuery();
						while (result1.next()) {
							dneValue = Integer.parseInt(result1.getString("dne"));
							termValue = result1.getString("term");
							termID = Integer.parseInt(result1.getString("id"));
							System.out.println("Dne:" + dneValue);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

					if (dneValue > 0) {
						System.out.println("Checking Credit");
						int totalCredit = 0;
						String term = null;

						// checking no of total credit for the specific semester
						try {
							String creditSqlQuery = "";
							creditSqlQuery += "select ti.term, cd.term_id, sum(cs.units) as sumCredit";
							creditSqlQuery += " from registration rgs, course_details cd, course cs, term_info ti";
							creditSqlQuery += " where student_id=" + studentId + " AND";
							creditSqlQuery += " cd.course_id=cs.id AND";
							creditSqlQuery += " ti.id=cd.term_id and";
							creditSqlQuery += " cd.id=rgs.course_details_id";
							creditSqlQuery += " group by cd.term_id";
							// creditSqlQuery="select
							// if(ti.dne_date>CURDATE(),1,0) as dne, ti.term
							// from course_details cd, term_info ti where
							// cd.term_id=ti.id and cd.id="+courseID;
							PreparedStatement credit = conn.prepareStatement(creditSqlQuery);
							ResultSet result2 = credit.executeQuery();
							while (result2.next()) {
								term = result2.getString("term");
								if (termValue == term) {
									totalCredit = Integer.parseInt(result2.getString("sumCredit"));
									System.out.println("Credit:" + totalCredit);
								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						System.out.println("Total Credit" + totalCredit);
						System.out.println(termValue.substring(0, 6));
						if (termValue.substring(0, 6).equals("Summer") && totalCredit < 12) {
							conflict = checkConflict(courseID, termID, studentId);
							if (!conflict) {
								//successMSG += "Done";
								
								CourseRegistration(courseID, studentId);

							} else {
								successMSG= "Time Conflicts";
							}
						} else if (totalCredit < 12) {
							conflict = checkConflict(courseID, termID, studentId);
							if (!conflict) {
								
								CourseRegistration(courseID, studentId);
								
								
							} else {
								successMSG= "Time Conflicts";
							}
						}
					} else {
						successMSG= "DNE date Over";
					}

				}

			} else {
				successMSG= "Payment Due";
			}
		} else {
			successMSG = "You Did Not Select Any Course";
		}

		request.setAttribute("message", successMSG);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("courseCart.jsp");
		requestDispatcher.forward(request, response);
	}

	// @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	// response)
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public boolean checkConflict(int courseID, int termID, int studentID) {

		int scheduleID = 0;
		int allscheduleID = 0;
		boolean conflict = false;

		int conflictCount = 0;

		// check the time confliction ... first take the expected course time
		// and match with all course schedule for a specific term

		try {

			
			String courseTime = "";
			courseTime = "select schedule_id from course_schedule where course_detail_id=" + courseID;
			PreparedStatement courseOFtime = conn.prepareStatement(courseTime);
			ResultSet result3 = courseOFtime.executeQuery();
			while (result3.next()) {
				scheduleID = result3.getInt("schedule_id");
				System.out.println("Schedule ID: " + scheduleID);
				String timeSqlQuery = "";
				timeSqlQuery += "select count(*) as ctn from registration rgs, course_details cd, course_schedule sch";
				timeSqlQuery += " where rgs.course_details_id=cd.id AND";
				timeSqlQuery += " sch.course_detail_id=cd.id AND";
				timeSqlQuery += " sch.schedule_id=" + scheduleID + " AND";
				timeSqlQuery += " student_id=" + studentID + " AND";
				timeSqlQuery += " cd.term_id=" + termID;

				System.out.println(timeSqlQuery);

				// creditSqlQuery="select if(ti.dne_date>CURDATE(),1,0) as dne,
				// ti.term from course_details cd, term_info ti where
				// cd.term_id=ti.id and cd.id="+courseID;
				PreparedStatement time = conn.prepareStatement(timeSqlQuery);
				ResultSet result4 = time.executeQuery();
				while (result4.next()) {
					allscheduleID = result4.getInt("ctn");
					// System.out.println("Schedule ID_1: "+allscheduleID);
					if (allscheduleID > 0) {
						conflictCount++;
					}
				}
				System.out.println(conflictCount);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (conflictCount > 0) {
			conflict = true;
		}

		return conflict;
	}
	
	public String CourseRegistration(int courseID, int studentId)
	{

		String regiSqlQuery="";
		int dbLimit=0;
		// SQL Query
		
		try{
			//System.out.println("Coming");
			//successMSG= "Done";
			regiSqlQuery="select ((select class_capacity from course_details where id="+courseID+")-(select count(*) from registration where course_details_id="+courseID+" and status=1)) as lmt from dual";
		System.out.println(regiSqlQuery);
		PreparedStatement courseLimit = conn.prepareStatement(regiSqlQuery);
		ResultSet result5 = courseLimit.executeQuery();
		while(result5.next()){
			dbLimit = Integer.parseInt(result5.getString("lmt"));
		}
		
		if(dbLimit<=0)
		{
			type=2;
		}
				
		PreparedStatement registerCourse = conn.prepareStatement("insert into registration(student_id,course_details_id, status)" + "values(?,?,?)");
		registerCourse.setInt(1,studentId);
		registerCourse.setInt(2,courseID); 
		registerCourse.setInt(3,type);
		int result6 = registerCourse.executeUpdate();
		try{
			if(null != registerCourse){
				registerCourse.close();
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		if(result6 == 1){

			String deleteCartQuery="delete from registration_cart where course_details_id="+courseID+" and student_id="+studentId;
			
			PreparedStatement deleteCart = conn.prepareStatement(deleteCartQuery);
			deleteCart.execute();
			
			successMSG= "Course Registration succesfully Done.";
            
			//
			// Delete from card have to implement here
			//
		}
		else{
			successMSG= "Database operation unsuccessful.";
		}
		}
		catch(Exception e){
			System.out.println("Something went wrong. Please contact system admin.");
			System.err.println(e.getMessage());
		}
		return successMSG;
	}
}
