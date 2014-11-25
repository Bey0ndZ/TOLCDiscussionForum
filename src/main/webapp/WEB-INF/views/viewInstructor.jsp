<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Welcome, ${pageContext.request.userPrincipal.name}</title>

<!-- Bootstrap Core CSS -->
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link
	href="${pageContext.request.contextPath}/resources/css/sb-admin.css"
	rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="${pageContext.request.contextPath}/resources/font-awesome-4.1.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

</head>

<body oncontextmenu="return false">
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<div id="wrapper">

			<!-- Navigation -->
			<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target=".navbar-ex1-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">Welcome,
						${pageContext.request.userPrincipal.name}</a>
				</div>
				<!-- Top Menu Items -->
				<ul class="nav navbar-right top-nav">


					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><i class="fa fa-user"></i> <!-- Accessing the session object -->
							<c:if test="${pageContext.request.userPrincipal.name != null }">
                    	${pageContext.request.userPrincipal.name}
                    </c:if> <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="accountSummary"><i class="fa fa-fw fa-user"></i>
									Profile</a></li>
							<li class="divider"></li>
							<li><a href="javascript:formSubmit()"><i
									class="fa fa-fw fa-power-off"></i> Log Out</a></li>
						</ul></li>
				</ul>

				<!-- Logout feature implementation -->
				<c:url value="/j_spring_security_logout" var="logoutUrl" />
				<form action="${logoutUrl}" method="post" id="logoutForm">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>

				<!-- Logout Script -->
				<script type="text/javascript">
					function formSubmit() {
						document.getElementById("logoutForm").submit();
					}
				</script>

				<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
				<div class="collapse navbar-collapse navbar-ex1-collapse">
					<ul class="nav navbar-nav side-nav">
						<li><a href="welcomeAdmin"><i
								class="fa fa-fw fa-dashboard"></i> Home</a></li>
						<li><a href="addNewInstructor"><i
								class="fa fa-fw fa-dashboard"></i> Add Instructor</a></li>
						<li class="active"><a href="viewInstructor"><i
								class="fa fa-fw fa-bar-chart-o"></i> View All Instructors</a></li>
						<li><a href="deleteAccount"><i
								class="fa fa-fw fa-bar-chart-o"></i>Delete Instructor</a></li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</nav>

			<div id="page-wrapper">

				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Add new instructor</h1>
						</div>
					</div>
					<!-- /.row -->

					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">
										<i class="fa fa-bar-chart-o fa-fw"></i> add new instructor
									</h3>
								</div>
								<div class="panel-body">
									<div id="morris-area-chart">
										<div class="panel-body">
											<c:if test="${successMsg != null}">
												<h4>
													${successMsg} <a href="index"> Click here to LogIn!</a>
												</h4>
											</c:if>
											<form:form method="POST" action="addNewInstructor"
												modelAttribute="registrationInformation" autocomplete="off">
												<br />
												<br />
												<b>User Name:</b>
												<FONT color="red"><form:errors path="username" /></FONT>
												<br />
												<input type="text" name="username" id="username"
													style="color: #999;" />
												<br />
												<br />
												<b>Password:</b>
												<FONT color="red"><form:errors path="password" /></FONT>
												<br />
												<input type="password" name="password" id="password"
													style="color: #999;" />
												<br />
												<br />
												<b>First Name:</b>
												<FONT color="red"><form:errors path="firstname" /></FONT>
												<br />
												<input type="text" name="firstname" id="f_name"
													style="color: #999;" />
												<br />
												<br />
												<b>Last Name:</b>
												<FONT color="red"><form:errors path="lastname" /></FONT>
												<br />
												<input type="text" name="lastname" id="l_name"
													style="color: #999;" />
												<br />
												<br />
												<b>Phone Number:</b>
												<FONT color="red"> <form:errors path="phonenumber" />
												</FONT>
												<br />
												<input type="text" name="phonenumber" id="contact"
													style="color: #999;" />
												<br />
												<br />
												<b>Email Address:</b>
												<br />
												<input type="email" name="email" id="email"
													style="color: #999;" />
												<br />
												<br />

												<br />
												<h4>
													<input type="submit" style="margin-right: 5%" name="login"
														id="log_in" value="Register" />
												</h4>
											</form:form>


											<div class="text-right">
												<a href="modifyUserExternal">Update Details <i
													class="fa fa-arrow-circle-right"></i></a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- /.row -->

				</div>
				<!-- /.container-fluid -->

			</div>
			<!-- /#page-wrapper -->

		</div>
		<!-- /#wrapper -->

		<!-- jQuery Version 1.11.0 -->
		<script
			src="${pageContext.request.contextPath}/resources/js/jquery-1.11.0.js"></script>

		<!-- Bootstrap Core JavaScript -->
		<script
			src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	</sec:authorize>
</body>

</html>