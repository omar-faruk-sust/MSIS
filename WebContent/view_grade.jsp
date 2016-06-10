<%@page import="com.msis.servlet.ShowGrade"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.msis.DBConnection.*, java.util.*,java.sql.*,java.sql.PreparedStatement"
	
	%>

<% 
	// Check this functionality for a new student who does not have any grade yet or not registered any semester yet
	MySQLAccess obj = new MySQLAccess();
	Connection connection = obj.getConnection();
	int studentId = (Integer)session.getAttribute("userId");
	//String sql = "SELECT ti.id, ti.term, CONCAT(sbj.subject_code,'-',cs.course_code,' ', cs.title) FROM registration_cart rgs, course_details cd, term_info ti, course cs, subject sbj where rgs.student_id="+ studentId + " and cs.id = cd.course_id and cd.id=rgs.course_details_id	and cs.subject_id=sbj.id and cd.term_id=ti.id";

	String sql = "select distinct ti.id,ti.term from registration_cart rgs, course_details cd, term_info ti"+
			" where rgs.student_id="+ studentId +
			" and rgs.course_details_id=cd.id"+
			" and cd.term_id=ti.id";
	PreparedStatement prepareStm = connection.prepareStatement(sql);
	ResultSet results = prepareStm.executeQuery();
%>

<jsp:include page="pre-header.jsp" />

<body class="skin-blue">
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<jsp:include page="sidebar.jsp" />
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					Your <small>Grade</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-eye"></i> View Grade </a></li>
				</ol>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="box col-xs-12">
							<div class="box-header">
								<h3>
									
								</h3>
							</div>
							<div class="box-body">
								<jsp:include page="error-success.jsp" />
								<form method="POST" action="ShowGrade" accept-charset="UTF-8" class="form-horizontal" role="form">
									
									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">Term Info</h4>
										</div>

										<div class="form-group" style="padding-top: 25px;">
											<label for="avatar" class="col-md-4 control-label">Select a term</label>
											<div class="col-md-6">
												<select data-column="3" id="term-select" name="selected_term" class="form-control" required="required">
											
													<option value="">
														Select a term
													</option>
													<% while(results.next()){ %>
														<option value="<%= results.getInt(1) %>"><%= results.getString(2) %></option>
													<%}%>
											</select>
											</div>
										</div>										
									</div>								

									<div class="form-group">
										<div class="col-md-6 col-md-offset-4">
											<button type="submit" class="btn btn-primary">
												<i class="fa fa-eye"></i> Show 
											</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!-- /.content -->
		</div>
	</div>
	<!-- /.content-wrapper -->
	<jsp:include page="footer.jsp" />