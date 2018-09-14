<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 




<style>
body {
   	background: url(${contextPath}"/share/resources/images/background.jpg"); 
   	background-size : cover;
	height: 85vh;
	background-size: cover;
	 opacity:1;
}
</style>

<title>PicShare</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<div class="container-fluid">
		<c:import url="header.jsp"></c:import>
	</div>

	<div class="container">
		
		<c:choose>
			<c:when test="${empty requestScope.model.content_list}">
				<h4>Start clicking !</h4>
			</c:when>

			<c:otherwise>
				<c:forEach items="${requestScope.model.content_list}" var="content">
				<div class="main">

						<div class="row" style="margin-top: 3%;">
							<div class="col-sm-2"></div>
							<div class="col-sm-3">
								<div class="card" style="width: 10rem; margin-right: 2%;">
									<a href="${contextPath}/content/comment/${content.contentID}"
										target="_blank"> <img
										src="${contextPath}/resources${content.fileName}"
										style="width: 100%; float: left;" />
									</a>
								</div>
							</div>
							<div class="col-sm-5">
								<h3>${content.category}</h3>
								
								<p>${content.artical}</p>
								<small><span class="glyphicon glyphicon-time" />
									${content.uploadDate}</small>
							</div>
							
						</div>
						<hr />
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>


	</div>
</body>
</html>