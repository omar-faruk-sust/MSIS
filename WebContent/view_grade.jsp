<%@page import="com.msis.DTO.PaymentDue"%>
<%@page import="com.msis.model.DueAmountModel"%>
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
								<% 
									String successStr = (String) request.getAttribute("successMsg");
									String errorStr = (String) request.getAttribute("errorMsg");
									if( successStr != null && !successStr.isEmpty()) 
									
									{ %>
								<div class="alert alert-success alert-dismissible" role="alert">
				                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
				                        <span aria-hidden="true">&times;</span>
				                    </button>
				                    <%= successStr %>
				                </div>
				                <% } else if( errorStr != null && !errorStr.isEmpty()) { %>
				                <div class="alert alert-error alert-dismissible" role="alert">
				                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
				                        <span aria-hidden="true">&times;</span>
				                    </button>
				                    <%= errorStr %>
				                </div>
								<% } %>
								<form method="POST" action="#" accept-charset="UTF-8" class="form-horizontal" role="form">
									
									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">Term Info</h4>
										</div>

										<div class="form-group" style="padding-top: 25px;">
											<label for="avatar" class="col-md-4 control-label">Select a term</label>
											<div class="col-md-6">
												<select data-column="3" id="term-select" name="term" class="form-control">
											
													<option value="">
														Select
													</option>
												
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
	<script type="text/javascript">
		
	</script>
	<jsp:include page="footer.jsp" />