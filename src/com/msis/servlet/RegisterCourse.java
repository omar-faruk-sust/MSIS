package com.msis.servlet;

import com.msis.DBConnection.*;
import java.sql.*;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

		String successMsg="";

		
		String errorMsg = "";
		if (request.getParameterValues("courseList") != null) {
			// Check DNE date
			
				for (String id : courseList) {
					
					int courseID = Integer.parseInt(id);
					String termValue = null;
					int termID = 0;
					int regiStart=0;
					boolean conflict = false;
					int registered=0;
					
					try {
						String checkCourseCount = "";
						checkCourseCount = "select count(*) as courseCount from course_details cd, registration rgs where cd.id=rgs.course_details_id and cd.term_id=(select term_id from course_details cd where id=?)";
						PreparedStatement courseCheck = conn.prepareStatement(checkCourseCount);
						courseCheck.setInt(1, courseID);
						ResultSet result01 = courseCheck.executeQuery();
						while (result01.next()) {
							registered = Integer.parseInt(result01.getString("courseCount"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					if(registered<=0)
					{
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
					}
					
					if (amountDue <= 0) {
						//Check DNE Date
					try {
						String dneSqlQuery = "";
						dneSqlQuery = "select if(ti.dne_date>CURDATE(),1,0) as dne, if(ti.registration_start<=CURDATE(),1,0) as regi_start, ti.id as id, ti.term from course_details cd, term_info ti where cd.term_id=ti.id and cd.id="
								+ courseID;
						PreparedStatement dne = conn.prepareStatement(dneSqlQuery);
						ResultSet result1 = dne.executeQuery();
						while (result1.next()) {
							dneValue = Integer.parseInt(result1.getString("dne"));
							termValue = result1.getString("term");
							termID = Integer.parseInt(result1.getString("id"));
							regiStart = Integer.parseInt(result1.getString("regi_start"));
							System.out.println("Dne:" + dneValue);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (regiStart>0) {
					if (dneValue > 0) {
						System.out.println("Checking Credit");
						int totalCredit = 0;
						int max_credit = 0;
						int countcrs=0;
						String term = null;

						// checking no of total credit for the specific semester
						try {
							String creditSqlQuery = "";
							creditSqlQuery= "select ti.term, cd.term_id, ti.max_credit, (IFNULL(sum(cs.units),0)+ (select crs.units from course crs, course_details cds where cds.course_id=crs.id and cds.id=?)) as sumCredit";
							creditSqlQuery += " from registration rgs, course_details cd, course cs, term_info ti";
							creditSqlQuery += " where student_id=? AND";
							creditSqlQuery += " cd.course_id=cs.id AND";
							creditSqlQuery += " ti.id=cd.term_id and";
							creditSqlQuery += " cd.id=rgs.course_details_id and ti.id=?";
							creditSqlQuery += " group by cd.term_id";
							// creditSqlQuery="select
							// if(ti.dne_date>CURDATE(),1,0) as dne, ti.term
							// from course_details cd, term_info ti where
							// cd.term_id=ti.id and cd.id="+courseID;
							PreparedStatement credit = conn.prepareStatement(creditSqlQuery);
							credit.setInt(1, courseID);
							credit.setInt(2, studentId);
							credit.setInt(3, termID);
							ResultSet result2 = credit.executeQuery();
							while (result2.next()) {
								
									totalCredit = Integer.parseInt(result2.getString("sumCredit"));
									max_credit = Integer.parseInt(result2.getString("max_credit"));
									System.out.println("Credit:" + totalCredit);
								
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
						try {
							String countCourse = "";
							countCourse= "select COUNT(*) as countcourse from registration rgs, course_details cd "+
										 "where rgs.student_id=? " +
										 "AND rgs.course_details_id=cd.id "+
										 "and cd.term_id=?";
							// creditSqlQuery="select
							// if(ti.dne_date>CURDATE(),1,0) as dne, ti.term
							// from course_details cd, term_info ti where
							// cd.term_id=ti.id and cd.id="+courseID;
							PreparedStatement courseCount = conn.prepareStatement(countCourse);							
							courseCount.setInt(1, studentId);
							courseCount.setInt(2, termID);
							ResultSet result2 = courseCount.executeQuery();
							
							while (result2.next()) {								
									countcrs = result2.getInt("countcourse");									
									System.out.println("Credit:" + totalCredit);
								}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
						System.out.println("Total Course" + countcrs);
						System.out.println("Total Credit" + totalCredit);
						System.out.println("Max Credit" + max_credit);
						//System.out.println(termValue.substring(0, 6));
						//if (termValue.substring(0, 6).equals("Summer") && totalCredit < 12) {
						if(totalCredit<=max_credit){
							conflict = checkConflict(courseID, termID, studentId);
							if (!conflict) {					
								
								successMsg=CourseRegistration(courseID, studentId);
								AddGrade(studentId, termID,courseID);
								if(countcrs<1)
								{									
									AddDue(studentId, termID);									
								}

							} else {
								errorMsg= "You have class time conflicts with another course.";
							}
						} 
						
						else{
							errorMsg= "Course takn more than " +max_credit+" credits";
						}
						/*else if (totalCredit < 12) {
							conflict = checkConflict(courseID, termID, studentId);
							if (!conflict) {
								
								CourseRegistration(courseID, studentId);
								
								
							} else {
								successMSG= "Time Conflicts";
							}
						}*/
					} else {
						errorMsg= "DNE date is over.";
					}
				} else {
					errorMsg= "Registration not started yet!";
				}

				//}

			} else {
				errorMsg= "Please pay your due payment.";
			}
			}
		} else {
			errorMsg = "You did not select any course.";
		}

		request.setAttribute("errorMsg", errorMsg);
		request.setAttribute("successMsg", successMsg);
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
		String errorMsg = "";
		String successMsg = "";
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
			
			successMsg= "Course Registration succesfully Done.";
            
			//
			// Delete from card have to implement here
			//
		}
		else{
			errorMsg= "Database operation unsuccessful.";
		}
		}
		catch(Exception e){
			System.out.println("Something went wrong. Please contact system admin.");
			System.err.println(e.getMessage());
		}
		return successMsg;
	}
	
	public void AddDue(int studentId, int tremId)
	{		
		try{		
				
		PreparedStatement addDue = conn.prepareStatement("insert into due(student_id,term_id, due_amount) values(?,?,?)");
		addDue.setInt(1,studentId);
		addDue.setInt(2,tremId); 
		addDue.setInt(3,7000);
		addDue.executeUpdate();
		try{
			if(null != addDue){
				addDue.close();
				System.out.println("Payment----1");
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}		
	
		}
		catch(Exception e){
			System.out.println("Something went wrong. Please contact system admin.");
			System.err.println(e.getMessage());
		}
	}
	
	public void AddGrade(int studentId, int tremId, int course_id)
	{		
		try{		
				
		PreparedStatement addDue = conn.prepareStatement("insert into grade(student_id,term_id, course_id,gpa) values(?,?,?,?)");
		addDue.setInt(1,studentId);
		addDue.setInt(2,tremId); 
		addDue.setInt(3,course_id);
		addDue.setDouble(4,-1);
		addDue.executeUpdate();
		try{
			if(null != addDue){
				addDue.close();
				System.out.println("Grade----1");
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}		
	
		}
		catch(Exception e){
			System.out.println("Something went wrong. Please contact system admin.");
			System.err.println(e.getMessage());
		}
	}
}
