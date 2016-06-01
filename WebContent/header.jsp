<header class="main-header">
	<!-- Logo -->
	<a href="dashboard.jsp" class="logo"><b>MSIS</b></a>
	<!-- Header Navbar: style can be found in header.less -->
	<nav class="navbar navbar-static-top" role="navigation">
		<!-- Sidebar toggle button-->
		<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
			<span class="sr-only">Toggle navigation</span>
		</a>
		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">
				<!-- User Account: style can be found in dropdown.less -->
				<li class="dropdown user user-menu">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							<img src="${pageContext.request.contextPath}/skins/adminLTE/dist/img/default-avatar.jpg" class="user-image" alt="User Image"/>
						<span class="hidden-xs"><%=(String)session.getAttribute("name")%></span>
					</a>
					<ul class="dropdown-menu">
						<!-- User image -->
						<li class="user-header">
								<img src="${pageContext.request.contextPath}/skins/adminLTE/dist/img/default-avatar.jpg" class="img-circle" alt="User Image" />			
								
							<p>
								<small><%=(String)session.getAttribute("email")%></small>
							</p>
						</li>
						<!-- Menu Footer-->
						<li class="user-footer">
							<div class="pull-left">
								<a href="profile.jsp" class="btn btn-default btn-flat">Profile</a>
							</div>
							<div class="pull-right">
								<!-- <a href="" class="btn btn-default btn-flat">Sign out</a> -->
								<form method="post" action="Logout">
						        	<input type="submit" value="Logout" class="btn btn-default btn-flat">
						        </form>
							</div>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	</nav>
</header>