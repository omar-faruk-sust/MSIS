<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="com.msis.DBConnection.*, java.util.*,java.sql.*,java.sql.PreparedStatement,com.msis.model.TranscriptModel"%>
<%
	String emailAtt = null; String userType = null;
	if(session.getAttribute("email") == null || session.getAttribute("userType") == null){
		response.sendRedirect("login.jsp");
	}
	else{
%>
<jsp:include page="pre-header.jsp" />
<body onload="noBack();" class="skin-blue">
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<jsp:include page="sidebar.jsp" />
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					Student <small>Transcript</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-eye"></i> View Student Transcript
					</a></li>
				</ol>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="box col-xs-12">
							<div class="box-header">
								<h3>
									<a href="view_grade.jsp"
										class="btn btn-primary btn-sm pull-right"><i
										class="fa fa-pencil-square-o"></i> Transcript</a>
								</h3>
							</div>
							<div class="box-body">
							<jsp:include page="error-success.jsp" />
							
							<form method="POST" action="StudentTranscript" accept-charset="UTF-8" class="form-horizontal" role="form">						
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">Provide Student Info</h4>
									</div>
									<div class="panel-body">
										<div class="form-group" >
											<label for="avatar" class="col-md-4 control-label">Student Id</label>
											<div class="col-md-6">
												<input class="form-control" required="required" name="studentId"
													type="number" min="1" value="" id="studentId">
											</div>
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
	<% }%>