<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
	integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4"
	crossorigin="anonymous">


<link href="${contextPath}/resources/images/css/style.css"
	rel='stylesheet' type='text/css' />
<script src="${contextPath}/resources/images/js/jquery.min.js"></script>
<title>menu share</title>

</head>
<body>

	
	<div class="header-top">
		<div class="container">
			<div class="head-main">
				<h1>
					<a href="${contextPath}/user/login">${user.firstName}
						${user.lastName}</a>
				</h1>
				<ul class="nav justify-content-end">
					<li><a href="${contextPath}/follow/followers">Followers</a></li>

				</ul>
				<ul class="nav justify-content-end">
					<li><a href="${contextPath}/follow/following">Following</a></li>
				</ul>

			</div>
		</div>
	</div>
	<!--header-top-end-->
	<!--start-header-->
	<div class="header">
		<div class="container">
			<div class="head">
				<div class="navigation">
					<span class="menu"></span>
					<ul class="navig">
						<li><a href="${contextPath}/content/upload">Upload photo</a>
						</li>
						<li><a href="${contextPath}/content/gallery">My Gallery</a></li>
						<li><a href="${contextPath}/user/available">Explore Users</a></li>
						<li><a href="${contextPath}/content/explore?pageNum=1">Explore
								content</a></li>
						<li><a href="${contextPath}/logout">Log Out</a></li>
					</ul>
				</div>

			</div>
			<div class="clearfix"></div>
		</div>

	</div>

</body>
</html>