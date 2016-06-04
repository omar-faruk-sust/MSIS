package com.msis.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.msis.model.PaymentModel;
import com.msis.DTO.*;

/**
 * Servlet implementation class Payment
 */
@WebServlet("/Payment")
public class Payment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Payment() {
        super();
        // TODO Auto-generated constructor stub
    }

	public Payment(int student_id, double amount) {
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
		int student_id = Integer.parseInt(request.getParameter("student_id"));
		Double amount = Double.parseDouble(request.getParameter("amount"));
		System.out.println(student_id + " " + amount);
		
		StudentPayment studentPayment = new StudentPayment(student_id, (double)amount);
		PaymentModel paymentModel = new PaymentModel();
		boolean paymentFlag = paymentModel.create(studentPayment);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("payment.jsp");
		if(paymentFlag) {
			request.setAttribute("successMsg", "Your payment has been made suecssfully.");
		} else {
			request.setAttribute("errorMsg", "Something is wrong. Please try again.");
		}
		
		requestDispatcher.forward(request, response);
	}

}
