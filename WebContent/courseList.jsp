<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import ="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" %>
<%@ page import="com.msis.registration.*" %>
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
                Course List
                
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                <li class="active">Search Course</li>
            </ol>         			
        </section> <!-- end of pre-header -->
        <!-- Main content -->
        <section class="content">
        
        		<% 
         		ArrayList rows = new ArrayList();
         		if (request.getAttribute("courseList") != null){
             	rows = (ArrayList) request.getAttribute("courseList");
         		}
         		%>
        
        <div class="box">            
            <div class="box-body">
              <div class="dataTables_wrapper form-inline dt-bootstrap" id="example2_wrapper">	  
              <div class="row"><div class="col-sm-6"></div><div class="col-sm-6"></div></div>            
              <div class="row">
              <div class="col-sm-12">
	              <c:forEach items="${courseList}" var="courseInfo">          
	              <table aria-describedby="example2_info" role="grid" id="example2" class="table table-bordered table-hover dataTable">
	                <tbody>
	                <c:forEach items="${courseInfo[4]}" var="course">
		                <tr role="row">
		                	<td colspan="7" bgcolor="#E6E6E6"><b>${course}</b></td> 	                	               
		                </tr>
		            </c:forEach> 	            
		            <tr role="row">
			            <th>Instructor</th>
			            <th>Day Time</th>
			            <th>Location</th>
			            <th>Meeting Date</th>
			            <th>Term</th>
			            <th>Status</th>
			            <th>Add Course</th>
		            </tr>
		            <tr role="row">
			            <c:forEach items="${courseInfo[1]}" var="instructor">
			                	<td>${instructor}</td> 	         
			            </c:forEach>
			            <c:forEach items="${courseInfo[2]}" var="dayTime">
			                	<td>${dayTime}</td> 	         
			            </c:forEach>  
			            <c:forEach items="${courseInfo[3]}" var="location">
			                	<td>${location}</td> 	         
			            </c:forEach>  
			            <c:forEach items="${courseInfo[6]}" var="meeting">
			                	<td>${meeting}</td> 	         
			            </c:forEach> 
			            <c:forEach items="${courseInfo[0]}" var="term">
			                	<td>${term}</td> 	         
			            </c:forEach>
			            <c:forEach items="${courseInfo[5]}" var="status">
			                	<td>${status}</td> 	         
			            </c:forEach> 
			            <td>
                    		<button type="button" class="btn btn-block btn-success">Select</button>
                 		</td>    	               
		            </tr>
	                </tbody>                
	              </table>
	              </c:forEach>              
              </div>
            </div>   
            </div>
            </div> 
        </div>         
        </section><!-- /.content -->
    </div><!-- /.content-wrapper -->
    
    
    <jsp:include page="footer.jsp" />
