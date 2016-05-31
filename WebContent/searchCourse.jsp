<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
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

<sql:setDataSource var="msis" driver="com.mysql.jdbc.Driver"
     url="jdbc:mysql://localhost/msis"
     user="root"  password="root"/>
 
<sql:query dataSource="${msis}" var="term_info_result">
SELECT * from term_info;
</sql:query>

<sql:query dataSource="${msis}" var="subject_result">
SELECT * from subject;
</sql:query>

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
                Course
                <small>Search Course</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                <li class="active">Search Course</li>
            </ol>
        </section> <!-- end of pre-header -->
        <!-- Main content -->
        <section class="content">
      <div class="row">
        <!-- left column -->
        
        <!--/.col (left) -->
        <!-- right column -->
        <div class="col-md-6">
          <!-- Horizontal Form -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Horizontal Form</h3>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <form class="form-horizontal" method="post" action="SearchCourse"> 
              <div class="box-body">
                <div class="form-group">
                  <label for="inputEmail3" class="col-sm-2 control-label">Term</label>
                  <div class="col-sm-10">
                  <select id="term" name="term">
					<c:forEach var="row" items="${term_info_result.rows}">					
					<option value=<c:out value="${row.id}"/>><c:out value="${row.term}"/></option>
					</c:forEach>
					</select>
					</div>
                </div>
                
                <div class="form-group">
                  <label for="inputPassword3" class="col-sm-2 control-label">Course Career</label>

                  <div class="col-sm-10">
                    <select id="program" name="program"> 										
					<option value="U">Undergraduate</option>		
					<option value="G">Graduate</option>				
					</select>
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="inputPassword3" class="col-sm-2 control-label">Subject</label>

                  <div class="col-sm-10">
                    <select id="subject" name="subject"> 										
						<c:forEach var="sub_row" items="${subject_result.rows}">					
							<option value=<c:out value="${sub_row.subject_code}"/>><c:out value="${sub_row.subject_code}"/></option>
						</c:forEach>				
					</select>
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="inputPassword3" class="col-sm-2 control-label">Course Number</label>

                  <div class="col-sm-10">
                    <input type="text" class="form-control" id="courseNo" name="courseNo" placeholder="" maxlength="4" size="4" style="width:10%">
                  </div>
                </div>
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="submit" class="btn btn-info pull-right">Search</button>
              </div>
              <!-- /.box-footer -->
            </form>
          </div>
          <!-- /.box -->
          <!-- general form elements disabled -->
          
          <!-- /.box -->
        </div>
        <!--/.col (right) -->
      </div>
      <!-- /.row -->
    </section>
    </div><!-- /.content-wrapper -->
    
<jsp:include page="footer.jsp" />