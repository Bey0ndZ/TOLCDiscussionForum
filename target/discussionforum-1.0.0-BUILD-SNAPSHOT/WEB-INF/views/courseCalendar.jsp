<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	
<link href="${pageContext.request.contextPath}/resources/css/jquery.datetimepicker.css" rel="stylesheet" type="text/css">

</head>

<body oncontextmenu="return false">
	<sec:authorize access="hasAnyRole('ROLE_INSTRUCTOR', 'ROLE_STUDENT')">
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
					<a class="navbar-brand" href="#">Welcome, ${pageContext.request.userPrincipal.name}</a>
				</div>
				<!-- Top Menu Items -->
				<ul class="nav navbar-right top-nav">


					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><i class="fa fa-user"></i> 
						<!-- Accessing the session object -->
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
						<li class="active"><a href="addCourse"><i
								class="fa fa-fw fa-dashboard"></i> Add Course</a></li>
						<li><a href="getMyCourses"><i
								class="fa fa-fw fa-bar-chart-o"></i> Get My Courses</a></li>
						<li><a href="getEnrolledStudentsList"><i
								class="fa fa-fw fa-bar-chart-o"></i> Get Enrolled Students</a></li>
						<li><a href="deleteCourse"><i
								class="fa fa-fw fa-bar-chart-o"></i> Delete Course</a></li>
						<li><a href="courseCalendar"><i
								class="fa fa-fw fa-bar-chart-o"></i> Course Calendar</a></li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</nav>

			<div id="page-wrapper">

				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Information</h1>
						</div>
					</div>
					<!-- /.row -->

					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">
										<i class="fa fa-bar-chart-o fa-fw"></i> Courses Summary
									</h3>
								</div>
								<div class="panel-body">
									<div id="morris-area-chart">
										<div class="panel-body">
											<c:if test="${not empty eventCreationMsg}">
												<small>${eventCreationMsg}</small>
											</c:if>
											<form:form method="POST" action="courseCalendar">
												<!-- Course Calendar -->
												<p>Date and Time: <input type="text" id="datetimepicker" name="dateandtime"></p><br/>
												Event details:<br/>
												<textarea cols="116" rows="2" name="eventDetails"></textarea><br/><br/>
												Event type: <select name="eventType">
													<option value="personalEvent">Personal</option>
													<sec:authorize access="hasRole('ROLE_INSTRUCTOR')">
														<option value="courseEvent">Course</option>
													</sec:authorize>
												</select><br/><br/>
												<button type="submit" class="btn btn-default">Create Event</button>
											</form:form>
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
		<!-- datetimepicker -->
		<script
			src="${pageContext.request.contextPath}/resources/js/jquery.datetimepicker.js"></script>
			
		<!-- Activate datetimepicker -->
		<script>
			jQuery('#datetimepicker').datetimepicker();
		</script>	
			
	</sec:authorize>
</body>

</html>