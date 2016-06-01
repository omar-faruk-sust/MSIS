package com.msis.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.msis.DBConnection.MySQLAccess;
import com.msis.DTO.PaymentDue;

public class DueAmountModel {
	protected String tableName = "due";
	
	public PaymentDue totalDueAmount(int studentId) {
		PaymentDue dueAmount = null;
		String sql = "select id,student_id,term_id, due_amount from "+ tableName +" where id=?";
		
		// Establish Connection
		MySQLAccess obj = new MySQLAccess();
		Connection connection = obj.getConnection();
		
		try {
			PreparedStatement prepareStm = connection.prepareStatement(sql);
			prepareStm.setInt(1, studentId);
			ResultSet results = prepareStm.executeQuery();
			while(results.next()){
				int id = results.getInt("id");
				int student_Id = results.getInt("student_id");
				int term_id = results.getInt("term_id");
				Double payableDueAmount = results.getDouble("due_amount");
	
				dueAmount = new PaymentDue(id, student_Id, term_id, payableDueAmount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dueAmount;
	}
}
