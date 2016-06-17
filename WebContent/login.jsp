<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/script.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<title>Mini-SIS | Login</title>
</head>

<body onload="noBack();">	
	<div class="wrapper">
		<form class="form-signin" method="POST" action="Login">
			<h2>
				<a class="btn btn-lg btn-block" href="#" role="button">MSIS</a>
			</h2>
			
			<%
			  if (session.getAttribute("email") != null)
			  {
			    %><jsp:forward page="dashboard.jsp" /><%
			  }
			%>
			
			<% 
			
			String str = (String) request.getAttribute("errorMsg");
			if( str != null && !str.isEmpty()) 
			
			{ %>
			<div class="alert alert-danger">
				<%= request.getAttribute("errorMsg") %>
			</div>
			<% } %>
			<input type="email" class="form-control" name="email"
				placeholder="Email Address" required="true" autofocus="" /> <input
				type="password" class="form-control" name="password"
				placeholder="Password" required="true" /> <br />
			<button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
			<br>
		</form>
	</div>
	<!-- custom JS for our project -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/project.js"></script>
</body>
</html>