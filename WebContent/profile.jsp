<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
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
					User <small>Profile</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Dashboard
					</a></li>
					<li><i class="fa fa-eye"></i><%=(String)session.getAttribute("email")%></li>
				</ol>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="box col-xs-12">
							<div class="box-header">
								<h3>
									<%=(String)session.getAttribute("email")%>
									<!-- condition impose -->
									<a href="edit_profile.jsp"
										class="btn btn-primary btn-sm pull-right"><i
										class="fa fa-pencil-square-o"></i> Edit</a>

								</h3>
							</div>
							<!-- Here load the partial view of user information. because this portion is common in the user view -->

							<div class="box-body">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">Profile</h4>
									</div>
									<div class="panel-body">

										<img
											src="${pageContext.request.contextPath}/skins/adminLTE/dist/img/default-avatar.jpg"
											alt="" class="col-xs-4 col-md-2 img-circle">

										<ul class="list-unstyled col-xs-8 col-md-10">
											<li><strong>Name</strong>: <%=(String)session.getAttribute("name")%></li>
											<li><strong>Email</strong>: <%=(String)session.getAttribute("email")%></li>
										</ul>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">Roles</h4>
									</div>
									<table class="table table-bordered panel-body">
										<tr>
											<th style="width: 25%">Role</th>
											<th>Description</th>
										</tr>
										<tr>
											<td><%=(String)session.getAttribute("userType")%></td>
										</tr>
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

	<jsp:include page="footer.jsp" />
	<%}%>