<%@page import="com.msis.servlet.Payment"%>
<%@page import="com.msis.DTO.StudentPayment"%>
<%@page import="com.msis.DTO.PaymentDue"%>
<%@page import="com.msis.model.DueAmountModel"%>
<%@page import="com.msis.model.PaymentModel"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.msis.DBConnection.*, java.util.*"%>
<%
	String emailAtt = null; String userType = null;
	if(session.getAttribute("email") == null || session.getAttribute("userType") == null){
		response.sendRedirect("login.jsp");
	} else if(!session.getAttribute("userType").equals("student")) {
		response.sendRedirect("dashboard.jsp");
	}
	else{
%>

<%
	MySQLAccess dbConnection = new MySQLAccess();
	// due model class
	DueAmountModel dueAmount = new DueAmountModel();
	// paid model class
	PaymentModel paidAmount = new PaymentModel();
	int studentId = (Integer)session.getAttribute("userId");

	PaymentDue paymentDue = dueAmount.totalDueAmount(studentId);
	StudentPayment alredyPaid = paidAmount.totalPaidAmount(studentId);
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
								<!-- include the error messgae handler -->
								<jsp:include page="error-success.jsp" />
								<form method="POST" action="Payment" accept-charset="UTF-8" class="form-horizontal" role="form">
									
									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">Account Summary</h4>
										</div>

										<div class="form-group" style="padding-top: 25px;">
											<label for="avatar" class="col-md-4 control-label">Total Amount</label>
											<div class="col-md-6">
												<%= paymentDue.getDue_amount() %>
											</div>
										</div>
										<div class="form-group" style="padding-top: 25px;">
											<label for="avatar" class="col-md-4 control-label">Total Paid Amount</label>
											<div class="col-md-6">
												<%= alredyPaid.getPaid_amount() %>
											</div>
										</div>
										<% if(paymentDue.getDue_amount() - alredyPaid.getPaid_amount() < 0) { %>
										<div class="form-group" style="padding-top: 25px;">
											<label for="avatar" class="col-md-4 control-label">Advance Paid Amount</label>
											<div class="col-md-6">
												<%= alredyPaid.getPaid_amount() - paymentDue.getDue_amount() %>
											</div>
										</div>
										<% } else {  %>
										
										<div class="form-group" style="padding-top: 25px;">
											<label for="avatar" class="col-md-4 control-label">Total Due Amount</label>
											<div class="col-md-6">
												<%= paymentDue.getDue_amount() - alredyPaid.getPaid_amount() %>
											</div>
										</div>
										<% }  %>
										
									</div>
									
									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">Payment Section</h4>
										</div>
										
										<input type="hidden" name="student_id" value="<%= studentId %>" />

										<div class="form-group" style="padding-top: 25px;">
											<label for="amount" class="col-md-4 control-label">Payment Amount</label>
											<div class="col-md-6">
												<input class="form-control" required="required" name="amount"
													type="number" min="1" value="" id="amount">
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
	<script type="text/javascript">
		
	</script>
	<jsp:include page="footer.jsp" />
	<%} %>>