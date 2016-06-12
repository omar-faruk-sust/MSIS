package com.msis.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.msis.model.StudentModel;

/**
 * Servlet implementation class StudentTranscript
 */
@WebServlet("/StudentTranscript")
public class StudentTranscript extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentTranscript() {
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
		
		int studentId = 0;
		boolean validStudent= false;
		
		String selected_student = request.getParameter("studentId");
		studentId = Integer.parseInt(selected_student);
		StudentModel studentModel = new StudentModel();
		validStudent = studentModel.validateStudent(studentId);
		System.out.println(validStudent);
		if(validStudent == false){
			// Send the error message if student does not exit
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("student_transcript.jsp");
			request.setAttribute("errorMsg", "Sorry! This student id does not exit!");
			requestDispatcher.forward(request, response);
		} else {
			// Send to the actual view transcript page
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("view_transcript.jsp");
			request.setAttribute("studentId", studentId);
			requestDispatcher.forward(request, response);
		}
	
	}

}
