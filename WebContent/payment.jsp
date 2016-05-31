<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.msis.DBConnection.*, java.util.*"%>


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
					Make <small>Payment</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Payment </a></li>
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
								<form method="POST"
									action="EditProfile"
									accept-charset="UTF-8" class="form-horizontal" role="form"
									enctype="multipart/form-data">
									
									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">Student Information</h4>
										</div>

										<div class="form-group" style="padding-top: 10px;">
											<label for="student_email" class="col-md-4 control-label">Student Email</label>
											<div class="col-md-6">
												<input class="form-control" required="required" name="student_email"
													type="text" value="" id="student_email">
											</div>
										</div>

										<div class="form-group">
											<label for="amount" class="col-md-4 control-label">Payment Amount</label>
											<div class="col-md-6">
												<input class="form-control" required="required" name="amount"
													type="text" value="" id="amount">
											</div>
										</div>
										
										
										
									</div>

									<div class="form-group">
										<div class="col-md-6 col-md-offset-4">
											<button type="submit" class="btn btn-primary">
												<i class="fa fa-save"></i> Save Payment
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