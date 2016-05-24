<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%
	String emailAtt = null; String nameAtt = null; String passwordAtt = null;
	if(session.getAttribute("email") == null || session.getAttribute("name") == null){
		response.sendRedirect("login.jsp");
	}
	else{
		emailAtt = (String) session.getAttribute("email");
		nameAtt = (String) session.getAttribute("name");
	}
		
	String userEmail = null;
	String userName = null;
	String sessionID = null;
	Cookie[] cookies = request.getCookies();
		
	if(cookies != null){
		for(Cookie cookie : cookies){
		    if(cookie.getName().equals("email")) userEmail = cookie.getValue();
		    if(cookie.getName().equals("userName")) userName = cookie.getValue();
		    if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
		}
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
                Dashboard
                <small>Control panel</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                <li class="active">Dashboard</li>
            </ol>
        </section> <!-- end of pre-header -->
        <!-- Main content -->
        <section class="content">
            all content for functionality will goes here
        </section><!-- /.content -->
    </div><!-- /.content-wrapper -->
    
    
    <jsp:include page="footer.jsp" />