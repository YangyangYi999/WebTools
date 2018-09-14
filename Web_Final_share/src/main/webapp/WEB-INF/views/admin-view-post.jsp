<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Share your menu</title>
<style>
body {
   	background: url(${contextPath}"/share/resources/images/background.jpg"); 
   	background-size : cover;
	height: 85vh;
	background-size: cover;
	opacity:1;
}
</style>
</head>

<body>

	<div class="container-fluid">
		<c:import url="admin-header.jsp"></c:import>
	</div>



	<div class="container" style="padding-bottom:50px;">
		<div class="thumbnail space" style="width: 70%; margin: 0 auto">
			<div class="row">
				<div class="col-sm-6"></div>
				<div class="col-sm-6"><h2>${map.content.menuName}</h2></div>
			</div>
			<img class="img img-responsive"
				src="${contextPath}/resources${map.content.fileName}" alt="images"
				width="800px" height="500px" />
			<div class="caption">
				<div class="row">
					<div class="col-sm-7">
					<p class="text-danger">${map.count} Like on this post</p>
					</div>

					<div class="col-sm-4">
						<small><span class="glyphicon glyphicon-time" />
							${map.content.uploadDate}</small><br /> <small>created by
							${map.createdByUser.username}</small>

					</div>
				</div>
				<div class="row bg-light" style="margin-bottom:10px;" >
					
					<div class="p-3 mb-2  text-dark">${map.content.artical}
				</div>
			</div>
			<c:forEach items="${map.content.comments}" var="com">
				<div class="row alert alert-primary">
					<div class="col-sm-1">
						<b>${com.user.username}: </b>
					</div>
					<div class="col-sm-11">${com.commentContent}</div>
				</div>
			</c:forEach>
		</div>
</div>
	</div>
</body>
</html>