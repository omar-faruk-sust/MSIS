package com.msis.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.msis.DBConnection.MySQLAccess;

public class TranscriptModel {
	/**
	 * 
	 * @param studentId
	 * @return arraylist with course info and gpa
	 */
	public ArrayList<ArrayList<String>> getFullTrascrip(int studentId){
		ArrayList<ArrayList<String>> Rows = null;
		
		// Check this functionality for a new student who does not have any grade yet or not registered any semester yet
		MySQLAccess obj = new MySQLAccess();
		Connection connection = obj.getConnection();
		
		String sql = "select distinct course_details_id, CONCAT( sbj.subject_code, '-', cs.course_code, ' ', cs.title ) AS course_title, ti.term, "+
						" IF(EXISTS(select gpa from grade where course_id=rg.course_details_id), "+ 
						" (select gpa from grade where course_id=rg.course_details_id), '') as gpa, "+ 
						" (select grade_scale from grading_points grp, grade g where g.gpa = grp.points and g.course_id=rg.course_details_id) as grade_scale,"+
						" cs.units as unit " +
						" from registration rg, grade grd, course cs, course_details cd, term_info ti, subject sbj,grading_points gp" +
						" where rg.student_id="+studentId+
						" and rg.course_details_id=cd.id" +
						" and cd.course_id=cs.id" +
						" and cd.term_id=ti.id" +
						" and cs.subject_id= sbj.id" +
						" and grd.gpa = gp.points";
		
		System.out.println(sql);
		String point;
		PreparedStatement prepareStm;
		
		try {
			prepareStm = connection.prepareStatement(sql);
			ResultSet results = prepareStm.executeQuery();
			//make an String type arrayList containing all the info about course and GPA
			Rows = new ArrayList<ArrayList<String>>();
			while(results.next()){
				
				ArrayList<String> row = new ArrayList<String>();
				for (int i = 1; i <= 1 ; i++){
					row.add(results.getString("term"));
			    	row.add(results.getString("course_title"));
			    	if(!results.getString("gpa").equals("-1")){
			    		row.add(results.getString("gpa"));
			    	}
			    	else{
			    		row.add("");
			    	}
			    	if(!results.getString("gpa").equals("-1")){
			    		row.add(results.getString("grade_scale"));
			    	}
			    	else{
			    		row.add("");
			    	}
			    	//row.add(results.getString("gpa"));
			    	//row.add(results.getString("grade_scale"));
			    	row.add(results.getString("unit"));
			    	
			    	
			    	
			    	if(!results.getString("gpa").equals("-1") && results.getString("grade_scale") != null){
			    		point = (String.valueOf(Double.parseDouble(results.getString("gpa")) * Double.parseDouble(results.getString("unit"))));
			    		row.add(point);
			    	} else {
			    		row.add("");
			    	}
			    	
//			    	if(!results.getString("gpa").equals("") && results.getString("grade_scale") != null){
//			    		point = (String.valueOf(Double.parseDouble(results.getString("gpa")) * Double.parseDouble(results.getString("unit"))));
//			    		row.add(point);
//			    	} else {
//			    		row.add("");
//			    	}
			    	
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
