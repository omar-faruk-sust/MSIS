<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import ="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" %>
<%@ page import="com.msis.servlet.*" %>
<%
	String emailAtt = null; String nameAtt = null; String passwordAtt = null;
	if(session.getAttribute("email") == null || session.getAttribute("userType") == null){
		response.sendRedirect("login.jsp");
	}
	else{
		emailAtt = (String) session.getAttribute("email");
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
         		if (session.getAttribute("courseList") != null){
             		rows = (ArrayList) session.getAttribute("courseList");
         		}
         		%>
        
        
				<div class="container-fluid">
					<div class="row">
						<div class="box col-xs-12">
              				<div class="box-header">
								<h3>
									<a href="courseCart.jsp"
										class="btn btn-primary btn-sm pull-right"><i
										class="fa fa-pencil-square-o"></i>Course Cart</a>
								</h3>
							</div>
              
              <!-- include the error messgae handler -->
				<jsp:include page="error-success.jsp" />
	              <c:forEach items="<%=rows %>" var="courseInfo">          
	              <table aria-describedby="example2_info" role="grid" id="example2" class="table table-bordered table-hover dataTable">
	                <tbody>
	                <c:forEach items="${courseInfo[5]}" var="course">
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
			            <th>Action</th>
		            </tr>
		            <tr role="row">
			            <c:forEach items="${courseInfo[2]}" var="instructor">
			                	<td>${instructor}</td> 	         
			            </c:forEach>
			            <c:forEach items="${courseInfo[3]}" var="dayTime">
			                	<td>${dayTime}</td> 	         
			            </c:forEach>  
			            <c:forEach items="${courseInfo[4]}" var="location">
			                	<td>${location}</td> 	         
			            </c:forEach>  
			            <c:forEach items="${courseInfo[7]}" var="meeting">
			                	<td>${meeting}</td> 	         
			            </c:forEach> 
			            <c:forEach items="${courseInfo[1]}" var="term">
			                	<td>${term}</td> 	         
			            </c:forEach>
			            <c:forEach items="${courseInfo[6]}" var="status">
			                	<td>${status}</td> 	         
			            </c:forEach> 
			            <form role="form" method="post" id="RegistrationCartAdd" action="RegistrationCartAdd">			           
				             <c:forEach items="${courseInfo[8]}" var="registered">
				             	<c:forEach items="${courseInfo[0]}" var="id"><input type="hidden" name="courseID" value="${id}"></c:forEach>
				             
					             <c:if test="${registered < 1}">
										 <td>
		                    				<button type="submit" class="btn btn-block btn-success">Add to Cart</button>
		                 				</td>
								</c:if>			             
	                 		</c:forEach>    	
                 		</form>               
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
    <%} %>
