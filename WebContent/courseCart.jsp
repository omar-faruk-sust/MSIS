<%@page import="com.msis.model.CourseModel"%>
<%@page import="com.msis.model.TermInfoModel"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*"%>
<%@ page import="com.msis.servlet.*"%>

<%
	String emailAtt = null; String userType = null;
	if(session.getAttribute("email") == null || session.getAttribute("userType") == null){
		response.sendRedirect("login.jsp");
	}
	else{
%>

<%
	String msg = "";
	ArrayList rows = new ArrayList();
	ArrayList termRows = new ArrayList();
	int studentId = 0;
	if (session.getAttribute("studentId") != null) {
		if (request.getAttribute("courseCart") != null) {
			rows = (ArrayList) request.getAttribute("courseCart");
		} else {
			studentId = (Integer) session.getAttribute("studentId");
			CourseModel cartModel = new CourseModel();
			rows = cartModel.courseListCart(studentId);
		}
		//if (session.getAttribute("courseCart") != null){
		//rows = (ArrayList) session.getAttribute("courseCart");
		TermInfoModel term = new TermInfoModel();
		termRows = term.termList();
	} else {
		msg = "No Student Select";
	}

	//}
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
				<h1>Course List</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li class="active">Search Course</li>
				</ol>
			</section>
			<!-- end of pre-header -->
			<!-- Main content -->
			<section class="content">
			
			<jsp:include page="error-success.jsp" />
				<%
											if (session.getAttribute("userType").equals("admin")) {
										%>
				<!-- /Admin Part Starts -->
				<div class="container-fluid">
					<div class="row">
						<div class="box col-xs-12">

							<div class="box-header">
								<h3></h3>
							</div>
							<div class="box-body">
				
								<form method="POST" action="CourseCart" accept-charset="UTF-8"
									class="form-horizontal" role="form">

									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">Term Info</h4>
										</div>


										<div class="form-group" style="padding-top: 25px;">
											<label class="col-md-5 control-label">Student ID</label>

											<div class="col-md-6">
												<input type="text" class="form-control" required="required"
													min="1" pattern="\d*" id="studentId" name="studentId"
													placeholder="" maxlength="10" size="50" style="width: 50%">

											</div>
										</div>


										<div class="form-group" style="padding-top: 25px;">
											<label for="avatar" class="col-md-5 control-label">Select
												a term</label>
											<div class="col-md-6">
												<select data-column="3" id="termId" name="termId"
													class="form-control" required="required" style="width: 50%">

													<option value="0">All term</option>
													<c:forEach items="<%=termRows%>" var="termInfo">
														<c:forEach items="${termInfo[0]}" var="id">
															<option value="${id}">
														</c:forEach>
														<c:forEach items="${termInfo[1]}" var="term">
															${term}</option>
														</c:forEach>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>

									<div class="form-group">
										<div class="col-md-6 col-md-offset-5">
											<input type="hidden" name="type" value="drop">
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
				<%
											}
										%>
				<!-- /Admin Part Ends -->


				<%
					if (session.getAttribute("studentId") != null) {
				%>
				<div class="box">
					<div class="box-body">
						<div class="dataTables_wrapper form-inline dt-bootstrap"
							id="example2_wrapper">
							<div class="row">
								<div class="col-sm-6"></div>
								<div class="col-sm-6"></div>
							</div>
							<div class="row">
								<div class="col-sm-12">

									<form method="post" name="form">
										<c:forEach items="<%=rows%>" var="courseInfo">

											<table aria-describedby="example2_info" role="grid"
												id="example2"
												class="table table-bordered table-hover dataTable">
												<tbody>
													<tr role="row">
														<th>Select</th>
														<th>Class</th>
														<th>Instructor</th>
														<th>Term</th>
														<th>Days/Times</th>
														<th>Room</th>
														<th>Units</th>
														<th>Status</th>
													</tr>

													<tr role="row">
														<c:forEach items="${courseInfo[0]}" var="courseID">
															<td><input type="checkbox" name="courseList"
																value="${courseID}"></td>
														</c:forEach>
														<c:forEach items="${courseInfo[1]}" var="course">
															<td>${course}</td>
														</c:forEach>
														<c:forEach items="${courseInfo[2]}" var="instructor">
															<td>${instructor}</td>
														</c:forEach>
														<c:forEach items="${courseInfo[3]}" var="term">
															<td>${term}</td>
														</c:forEach>
														<c:forEach items="${courseInfo[4]}" var="time">
															<td>${time}</td>
														</c:forEach>
														<c:forEach items="${courseInfo[5]}" var="room">
															<td>${room}</td>
														</c:forEach>
														<c:forEach items="${courseInfo[6]}" var="unit">
															<td>${unit}</td>
														</c:forEach>
														<c:forEach items="${courseInfo[7]}" var="status">
															<td>${status}</td>
														</c:forEach>
													</tr>

												</tbody>
											</table>
											<c:set var="rowCount2" value="1" />
										</c:forEach>
										<c:choose>
											<c:when test="${rowCount2>0}">
												<input type="submit" class="btn btn-info pull-right"
													value="Confirm"
													onclick="javascript: form.action='RegisterCourse';" />
												<input type="submit" style="margin-right: 10px"
													class="btn btn-danger pull-right" value="Delete"
													onclick="javascript: form.action='DeleteFromCart';" />
											</c:when>
											<c:otherwise>

											</c:otherwise>
										</c:choose>

									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
				<%
					}
				%>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->


		<jsp:include page="footer.jsp" />
		<%}%>