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
	
<!-- Firebase -->
<script src="https://cdn.firebase.com/js/client/2.0.2/firebase.js"></script>

<!-- CodeMirror -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/4.3.0/codemirror.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/4.3.0/codemirror.css" />

<!-- Firepad -->
<link rel="stylesheet" href="https://cdn.firebase.com/libs/firepad/1.1.0/firepad.css" />
<script src="https://cdn.firebase.com/libs/firepad/1.1.0/firepad.min.js"></script>

<!-- CSS for firepad -->
<style type="text/css">
	.firepad {
	  width: 100%;
	  height: 200px;
	  background-color: #808080; /* Grey backgroun */
	}
	
	/* Note: CodeMirror applies its own styles which can be customized in the same way.
	   To apply a background to the entire editor, we need to also apply it to CodeMirror. */
	.CodeMirror {
	  background-color: #808080;
	}
</style>

</head>

<body oncontextmenu="return false">
	<sec:authorize access="hasAnyRole('ROLE_STUDENT', 'ROLE_INSTRUCTOR')">
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
						<sec:authorize access="hasRole('ROLE_STUDENT')">
							<li><a href="/discussionforum/welcome"><i
									class="fa fa-fw fa-dashboard"></i> My Courses</a></li>
							<li><a href="/discussionforum/welcome/discussionBoard/${globalCourseIDSet}"><i
									class="fa fa-fw fa-bar-chart-o"></i> Discussion Board</a></li>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_INSTRUCTOR')">
							<li><a href="/discussionforum/welcomeInstructor"><i
									class="fa fa-fw fa-dashboard"></i> My Courses</a></li>
							<li><a href="/discussionforum/getMyCourses/discussionBoard/${globalCourseIDSet}"><i
									class="fa fa-fw fa-bar-chart-o"></i> Discussion Board</a></li>
						</sec:authorize>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</nav>

			<div id="page-wrapper">

				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">${threadname}</h1>
							<!-- Subscribe now feature -->
							<!-- Display the form only if the subscriptionMsg is empty -->
							<c:if test="${not empty displayForm}">
								<div class="text-right">
									<form:form method="POST" action="/discussionforum/welcome/discussionBoard/subscribeToThread">
										<small>Subscribe to thread: <input type="checkbox" value="yes" name="subscribeToThread" checked></small>
										<button type="submit" class="btn btn-default">Subscribe</button>
									</form:form>
								</div>
							</c:if>
							<!-- Display the subscription message if it is not empty -->
							<c:if test="${not empty subscriptionMsg}">
								<small>${subscriptionMsg}</small>
							</c:if>
							<c:forEach var="tickrInfo" items="${tickr}">
								<small><marquee>${tickrInfo.postedby} has posted last at ${tickrInfo.postedat} on this thread!</marquee></small>
							</c:forEach>
						</div>
					</div>
					<!-- /.row -->

					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">
										<i class="fa fa-bar-chart-o fa-fw"></i> ${threadsubject}
									</h3>
								</div>
								<div class="panel-body">
									<div id="morris-area-chart">
										<div class="panel-body">
											<!-- Show the thread information here -->
											<!-- Show the thread name, thread subject and thread content -->
											<div class="panel panel-default">
												${threadcontent}<br/><br/>
												<div class="text-right">
													<small>Posted by: ${createdby}</small>
												</div>
											</div>
											
											<!-- Wiki for collaborative editing -->
											<h3>${threadname} wiki</h3>
											<div id="firepad"></div><br/>
											
											<!-- Show the post which is basically text from the below textarea -->
											<!-- Use ajax to perform the post text request -->
											<!-- <div class="panel panel-default" id="showPost">
												
											</div> -->
											<c:forEach var="post" items="${getAllPosts}">
												<div class="panel panel-default">
													${post.postcontent}
													<div class="text-right">
														<small>Posted by: ${post.postedby} at ${post.postedat}</small>
													</div>
												</div>
											</c:forEach>
											
											<form:form action="/discussionforum/welcome/discussionBoard/showThread" method="POST">
												<textarea rows="2" cols="116" name="discussion" id="discussion" placeholder="Follow up discussion.."></textarea><br/>
												<div class="text-right">
													Post anonymously: Yes <input type="radio" name="postanonymously" value="yes"/> No <input type="radio" name="postanonymously" value="no" checked/>
												</div>
												<input type="submit" value="Post" id="postToDiscussion"/>
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
		
		<!-- Wiki for collaborative editing -->
		<!-- 
		1. Initialize firebase
		2. Initialize CodeMirror
		3. Initialize firepad
		 -->
		<script>
			var uniqueID = <c:out value="${firepadURL}" />;
			var firepadRef = new Firebase('https://blinding-torch-1602.firebaseio.com/firepads/'+uniqueID);
			var codeMirror = CodeMirror(document.getElementById('firepad'), { lineWrapping: true });
			var firepad = Firepad.fromCodeMirror(firepadRef, codeMirror,
				      { richTextShortcuts: true, richTextToolbar: true, defaultText: 'Collaborative Editing Area' });
		</script>
	</sec:authorize>
</body>

</html>