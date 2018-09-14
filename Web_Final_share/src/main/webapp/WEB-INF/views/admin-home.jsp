<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<title>Admin main page</title>
<style>
body {
   	background: url(${contextPath}"/share/resources/images/background.jpg"); 
   	background-size : cover;
	height: 85vh;
	background-size: cover;
	 opacity:1;
}
img{
 opacity:0.8;
}
</style>
</head>
<body>

	<div class="container-fluid">
		<c:import url="admin-header.jsp"></c:import>
	</div>

	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<div class="container">
		<h2 class="text-center">All posts</h2>
		<hr />
		 
		<p>You can block anyone in your system or watch all the posts.</p>
		<div id="demo" class="carousel slide" data-ride="carousel">

			<!-- Indicators -->
			<ul class="carousel-indicators">
				<li data-target="#demo" data-slide-to="0" class="active"></li>
				<li data-target="#demo" data-slide-to="1"></li>
				<li data-target="#demo" data-slide-to="2"></li>
			</ul>

			<!-- The slideshow -->
			<div class="carousel-inner">
				<c:forEach items="${contentList}" var="content"
					begin="1" end="1">
					<div class="carousel-item active">
						<img class="d-block w-70" src="${contextPath}/resources${content.fileName}"
							alt="${content.menuName}" width="1050" height="400" >
					</div>
				</c:forEach>
				<c:forEach items="${contentList}" var="content"
					begin="2" end="3">
					<div class="carousel-item">
						<img class="d-block w-70" src="${contextPath}/resources${content.fileName}"
							alt="${content.menuName}" width="1050" height="400">
					</div>
				</c:forEach>
			</div>
			<!-- Left and right controls -->
			<a class="carousel-control-prev" href="#demo" data-slide="prev">
				<span class="carousel-control-prev-icon"></span>
			</a> <a class="carousel-control-next" href="#demo" data-slide="next">
				<span class="carousel-control-next-icon"></span>
			</a>
		</div>
		

	</div>
</body>
</html>