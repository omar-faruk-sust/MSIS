
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.msis.DBConnection.*, java.util.*"%>
	
	<%
	String emailAtt = null; String userType = null;
	if(session.getAttribute("email") == null || session.getAttribute("userType") == null){
		response.sendRedirect("login.jsp");
	}
	else{
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
					Your <small>Course Grade</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-eye"></i> Add Grade </a></li>
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
								<form method="POST" action="#" accept-charset="UTF-8" class="form-horizontal" role="form">
									
									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">Upload Your Grade</h4>
										</div>

										<div class="form-group" style="padding-top: 25px;">
											<label for="avatar" class="col-md-4 control-label">Select a term</label>
											<div class="col-md-6">
												<select data-column="3" id="term-select" name="selected_term" class="form-control" required="required">
											
													<option value="">
														Select a term
													</option>
													<option value="1">Winter 2016</option>
													<option value="2">Summer1 2016</option>
													<option value="3">Summer2 2016</option>
													<option value="4">Fall 2016</option>
											</select>
											</div>
										</div>
										<div class="form-group" style="padding-top: 25px;">
											<label for="avatar" class="col-md-4 control-label">Select a term</label>
											<div class="col-md-6">
												<select data-column="3" id="term-select" name="selected_term" class="form-control" required="required">
											
													<option value="">
														Select a term
													</option>
													<option value="1">Winter 2016</option>
													<option value="2">Summer1 2016</option>
													<option value="3">Summer2 2016</option>
													<option value="4">Fall 2016</option>
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
	<%}%>