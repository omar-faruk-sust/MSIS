<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="com.msis.DBConnection.*, java.util.*,java.sql.*,java.sql.PreparedStatement,com.msis.model.TranscriptModel"%>

<% 
	int studentId = (Integer)session.getAttribute("userId");
	ArrayList<ArrayList<String>> courseList = new ArrayList<ArrayList<String>>();
	TranscriptModel tcModle = new TranscriptModel();
	courseList = tcModle.getFullTrascrip(studentId);
	request.setAttribute("courseList", courseList);
	while(courseList.size() >0 ){
		
	}
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

									<a href="view_grade.jsp"
										class="btn btn-primary btn-sm pull-right"><i
										class="fa fa-pencil-square-o"></i> View grade Termwise</a>

								</h3>
							</div>
							<div class="box-body">
								<jsp:include page="error-success.jsp" />

								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">Your Full Transcript</h4>
									</div>

									<table class="table table-bordered" style="padding-top: 50px;">
										<tr>
											<th>Term</th>
											<th>Course Title</th>
											<th>Unit</th>
											<th>Grade Notation</th>
											<th>Point</th>
											<th>Grading Point</th>
										</tr>


										<c:forEach items="${courseList}" var="courseInfo">
											<tr role="row">
												<c:forEach items="${courseInfo[0]}" var="term">
													<td>${term}</td>
												</c:forEach>
												<c:forEach items="${courseInfo[1]}" var="course_title">
													<td>${course_title}</td>
												</c:forEach>
												<c:forEach items="${courseInfo[4]}" var="unit">
													<td>${unit}</td>
												</c:forEach>
												<c:forEach items="${courseInfo[3]}" var="grade_scale">
													<td>${grade_scale}</td>
												</c:forEach>
												<c:forEach items="${courseInfo[2]}" var="gpa">
													<td>${gpa}</td>
												</c:forEach>
												<c:forEach items="${courseInfo[5]}" var="obtain_point">
													<td>${obtain_point}</td>
												</c:forEach>
											</tr>
										</c:forEach>
									</table>
								</div>
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