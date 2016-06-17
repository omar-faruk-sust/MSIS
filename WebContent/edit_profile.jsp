<%@page import="com.msis.model.StudentModel"%>
<%@page import="com.msis.DTO.Student"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.msis.DBConnection.*, java.util.*"%>

<%
	String emailAtt = null; String userType = null;
	if(session.getAttribute("email") == null || session.getAttribute("userType") == null){
		response.sendRedirect("login.jsp");
	}
	else{
%>
<%
	MySQLAccess mysqlApp = new MySQLAccess();
	StudentModel studentModel = new StudentModel();
	Student student = studentModel.selectStudent((String)session.getAttribute("email"));

%>

<jsp:include page="pre-header.jsp" />

<body class="skin-blue" onload="noBack();">
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<jsp:include page="sidebar.jsp" />
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					Profile <small>Edit</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li><i class="fa fa-pencil-square-o"></i> Edit</li>
				</ol>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="box col-xs-12">
							<div class="box-header">
								<h3>
									<%=(String)session.getAttribute("name")%> <a href="profile.jsp"
										class="btn btn-primary btn-sm pull-right"><i
										class="fa fa-eye"></i> View </a>
								</h3>
							</div>
							<div class="box-body">
								<form method="POST"
									action="#"
									accept-charset="UTF-8" class="form-horizontal" role="form"
									enctype="multipart/form-data">
									
									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">Basic Information</h4>
										</div>

										<div class="form-group" style="padding-top: 10px;">
											<label for="first_name" class="col-md-4 control-label">First Name</label>
											<input type="hidden" name="user_type" value="<%= (String) session.getAttribute("userType")%>">
											<div class="col-md-6">
												<input class="form-control" required="required" name="first_name"
													type="text" value="<%= student.getFirst_name()%>" id="first_name">
											</div>
										</div>
										
										<div class="form-group" style="padding-top: 10px;">
											<label for="last_name" class="col-md-4 control-label">Last Name</label>
											<div class="col-md-6">
												<input class="form-control" required="required" name="last_name"
													type="text" value="<%= student.getLast_name()%>" id="last_name">
											</div>
										</div>

										<div class="form-group">
											<label for="email" class="col-md-4 control-label">E-Mail
												Address</label>
											<div class="col-md-6">
												<input class="form-control" required="required" name="email"
													type="email" value="<%= student.getEmail()%>" id="email">
											</div>
										</div>
										
										<div class="form-group">
											<label for="mobile" class="col-md-4 control-label">Mobile</label>
											<div class="col-md-6">
												<input class="form-control" required="required" name="mobile"
													type="text" value="<%= student.getMobile() %>" id="mobile">
											</div>
										</div>
										
									</div>


									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">Change Password</h4>
										</div>

										<div class="form-group" style="padding-top: 10px;">
											<label for="old_password" class="col-md-4 control-label">Old
												Password</label>
											<div class="col-md-6">
												<input name="old_password" type="password" value=""
													id="old_password">
											</div>
										</div>

										<div class="form-group" style="padding-top: 10px;">
											<label for="password" class="col-md-4 control-label">New
												Password</label>
											<div class="col-md-6">
												<input name="password" type="password" value=""
													id="password">
											</div>
										</div>

										<div class="form-group">
											<label for="password_confirmation"
												class="col-md-4 control-label">Confirm New Password</label>
											<div class="col-md-6">
												<input name="password_confirmation" type="password" value=""
													id="password_confirmation">
											</div>
										</div>
									</div>


									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">Change Address</h4>
										</div>

										<div class="form-group" style="padding-top: 25px;">
											<label for="avatar" class="col-md-4 control-label">Address</label>
											<div class="col-md-6">
												<textarea name="address" id="address"><%= (student.getAddress()== null)?"":student.getAddress()%></textarea>
											</div>
										</div>
									</div>

									<div class="form-group">
										<div class="col-md-6 col-md-offset-4">
											<button type="submit" class="btn btn-primary">
												<i class="fa fa-save"></i> Save
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
	<%}%>