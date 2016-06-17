<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%
	String emailAtt = null;
	String nameAtt = null;
	String passwordAtt = null;
	if (session.getAttribute("email") == null || session.getAttribute("userType") == null) {
		response.sendRedirect("login.jsp");
	} else {
%>

<sql:setDataSource var="msis" driver="com.mysql.jdbc.Driver"
	url="jdbc:mysql://localhost/msis" user="root" password="root" />

<sql:query dataSource="${msis}" var="term_info_result">
SELECT * from term_info where CURDATE()< dne_date;
</sql:query>

<sql:query dataSource="${msis}" var="subject_result">
SELECT * from subject;
</sql:query>

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
					Course <small>Search Course</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li class="active">Search Course</li>
				</ol>
			</section>
			<!-- end of pre-header -->
			<!-- Main content -->
			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="box col-xs-12">
							<div class="box-header">
								<h3></h3>
							</div>
							<div class="box-body">
								<!-- include the error message handler -->
								<jsp:include page="error-success.jsp" />
								<form method="POST" action="SearchCourse" accept-charset="UTF-8"
									class="form-horizontal" role="form">
									
									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">Search</h4>
										</div>
										
										<div class="form-group" style="padding-top: 25px;">
										<label for="inputEmail3" class="col-md-4 control-label">Term</label>
										<div class="col-md-6">
											<select data-column="3" class="form-control" id="term" name="term">
												<c:forEach var="row" items="${term_info_result.rows}">
													<option value=<c:out value="${row.id}"/>><c:out
															value="${row.term}" /></option>
												</c:forEach>
											</select>
										</div>
									</div>

									<div class="form-group">
										<label for="inputPassword3" class="col-md-4 control-label">Course
											Career</label>

										<div class="col-md-6">
											<select data-column="3" class="form-control" id="program" name="program">
												<option value="G">Graduate</option>
												<option value="U">Undergraduate</option>												
											</select>
										</div>
									</div>

									<div class="form-group">
										<label for="inputPassword3" class="col-md-4 control-label">Subject</label>

										<div class="col-md-6">
											<select data-column="2" class="form-control" id="subject" name="subject">
												<c:forEach var="sub_row" items="${subject_result.rows}">
													<option value=<c:out value="${sub_row.subject_code}"/>><c:out
															value="${sub_row.subject_code}" /></option>
												</c:forEach>
											</select>
										</div>
									</div>

									<div class="form-group">
										<label for="inputPassword3" class="col-md-4 control-label">Course
											Number</label>

										<div class="col-md-6">
											<input type="text" class="form-control" id="courseNo"
												name="courseNo" placeholder="" maxlength="4" size="10">
										</div>
									</div>

									<%
										if (session.getAttribute("userType").equals("admin")) {
									%>
									<div class="form-group">
										<label for="inputPassword3" class="col-md-4 control-label">Student
											ID</label>

										<div class="col-md-6">
											<input type="text" class="form-control" required="required" id="studentID"
												name="studentID" placeholder="" maxlength="10" size="10">
										</div>
									</div>
									<%
										}
									%>									
									</div>

									<div class="form-group">
										<div class="col-md-6 col-md-offset-4">
											<button type="submit" class="btn btn-primary">
												<i class="fa fa-save"></i> Search
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
	<script type="text/javascript">
		
	</script>
	<jsp:include page="footer.jsp" />
	<%} %>>