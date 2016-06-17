<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">
		<!-- Sidebar user panel -->
		<div class="user-panel">
			<div class="pull-left image">

				<img
					src="${pageContext.request.contextPath}/skins/adminLTE/dist/img/default-avatar.jpg"
					class="img-circle" alt="User Image" />

			</div>
			<div class="pull-left info">
				<p>
					<a href=""></a>
				</p>
			</div>
		</div>
		<!-- sidebar menu: : style can be found in sidebar.less -->
		<ul class="sidebar-menu">
			<li class="header">MAIN NAVIGATION</li>


			<li class="treeview">
				<a href="#">
					<i class="fa fa-file-o"></i> 
					<span>Course</span>
		
					<i class="fa fa-angle-left pull-right"></i>
				</a>
				<ul class="treeview-menu">
		            		<li class="active"><a href="searchCourse.jsp"><i class="fa fa-circle-o"></i> Search Course</a></li>
            				<li><a href="courseCart.jsp"><i class="fa fa-circle-o"></i> Course Cart</a></li>
            				<li><a href="enrolledCourse.jsp"><i class="fa fa-circle-o"></i> Enrolled Course</a></li>
            				<li><a href="dropCourse.jsp"><i class="fa fa-circle-o"></i> Drop Course</a></li>
		         </ul>
			</li>
			<li class="treeview"><a href="#"> <i class="fa fa-book"></i>
					<span>Grade</span> <i class="fa fa-angle-left pull-right"></i>
			</a>
				<ul class="treeview-menu">
					<li><a href="view_grade.jsp"><i class="fa fa-book"></i>View
							Your Grade</a></li>
							<% if(session.getAttribute("userType").equals("student")){ %>
									<li><a href="view_transcript.jsp"><i class="fa fa-book"></i>View Your Transcript</a></li>	
							<% } else { %>
								<li><a href="student_transcript.jsp"><i class="fa fa-book"></i>View Student Transcript</a></li>
							<% } %>
				</ul>
			</li>
			<% if(session.getAttribute("userType").equals("student")){ %>
			<li>
				<a href="payment.jsp"> <i class="fa-cc-visa"></i> <span>Payment</span>
			</a>
			</li>
			<% } %>

		</ul>
	</section>
	<!-- /.sidebar -->
</aside>
