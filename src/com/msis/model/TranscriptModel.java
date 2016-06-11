package com.msis.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.msis.DBConnection.MySQLAccess;

public class TranscriptModel {
	
	public ArrayList<ArrayList<String>> getFullTrascrip(int studentId){
		ArrayList<ArrayList<String>> Rows = null;
		
		// Check this functionality for a new student who does not have any grade yet or not registered any semester yet
		MySQLAccess obj = new MySQLAccess();
		Connection connection = obj.getConnection();
		
		String sql = "SELECT ti.id, ti.term, CONCAT(sbj.subject_code,'-',cs.course_code,' ', cs.title) as course_title, gpa, grade_scale," +
				" cs.units as unit, gpa*cs.units as point FROM registration_cart rgs, course_details cd, term_info ti, course cs, subject sbj, grade grd, grading_points gp" + 
				" where rgs.student_id="+studentId+ 
				" and cs.id = cd.course_id " +  
				" and cd.id=rgs.course_details_id " + 
				" and cs.subject_id=sbj.id " +  
				" and cd.term_id=ti.id " + 
				" and grd.course_id=rgs.course_details_id " +
				" and grd.gpa=gp.points";
		System.out.println(sql);
		PreparedStatement prepareStm;
		
		try {
			prepareStm = connection.prepareStatement(sql);
			ResultSet results = prepareStm.executeQuery();
			//make an String type arrayList containg all the info about course and GPA
			Rows = new ArrayList<ArrayList<String>>();
			while(results.next()){
				System.out.println(results.getString("course_title"));
				System.out.println(results.getString("gpa"));
				System.out.println(results.getString("grade_scale"));
				
				ArrayList<String> row = new ArrayList<String>();
				for (int i = 1; i <= 1 ; i++){
					row.add(results.getString("term"));
			    	row.add(results.getString("course_title"));
			    	row.add(results.getString("gpa"));
			    	row.add(results.getString("grade_scale"));
			    	row.add(results.getString("unit"));
			    	row.add(results.getString("point"));
			    }
				Rows.add(row);
				
			}
		} catch (SQLException e) {
			System.out.println("Something went wrong. Please contact system admin.");
			System.err.println(e.getMessage());
		}
		
		return Rows;
	}
}
