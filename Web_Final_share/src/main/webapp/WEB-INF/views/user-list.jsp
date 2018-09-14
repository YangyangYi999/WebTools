<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">



<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script> 
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<script>
	$(document).on(
			'click',
			'.follow',
			function(event) {
				debugger;
				event.preventDefault();
				var c = $(this).attr('name');
				var element = this;
				console.log(element);
				var toFollow = {
					"followeeID" : c
				};
				$.ajax({
					url : "../follow/add",
					data : toFollow,
					dataType : 'json',
					type : "POST",
					success : function(data) {
						if (data) {
							console.log(data);
							$(element).removeClass("btn-default").addClass(
									"btn-success");
							$(element).html("Following");
						} else {
							console.log(data);
							$(element).addClass("btn-default").removeClass(
									"btn-success");
							$(element).html("Follow");
						}
					}
				})
			});
	$(document).on(
			'click',
			'.block',
			function(event) {
				debugger;
				event.preventDefault();
				var c = $(this).attr('name');
				var element = this;
				console.log(element);
				var toFollow = {"followeeID" : c};
				$.ajax({
					url : "../user/block",
					data : toFollow,
					dataType : 'json',
					type : "POST",
					success : function(data) {
						if (data) {
							console.log(data);
							$(element).removeClass("btn-default").addClass(
									"btn-success");
							$(element).html("Blocking");
						} else {
							console.log(data);
							$(element).addClass("btn-default").removeClass(
									"btn-success");
							$(element).html("Block");
						}
					}
				})
			});
	
</script>
<style>
body {
   	background: url(${contextPath}"/share/resources/images/background.jpg"); 
   	background-size : cover;
	height: 85vh;
}
</style>
<title>PicShare</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:choose>
		<c:when test="${sessionScope.user.role=='customer'}">
			<div class="container-fluid">
				<c:import url="header.jsp"></c:import>
			</div>
			
			<div class="container">
				<h2 class="text-center">All Users</h2>
				<c:choose>
					<c:when test="${fn:length(requestScope.userlist)==1}">
						<h4>You are alone</h4>
					</c:when>
					<c:otherwise>
						<table class="table table-stripped table-hover">
							<tr>
								<th>User Name</th>
								<th></th>
							</tr>
							<c:forEach var="i" begin="1"
								end="${fn:length(requestScope.model.userlist)}">
								<c:choose>
									<c:when test="${model.userlist[i-1].personID == sessionScope.user.personID}">
										
									</c:when>
									<c:when test="${not model.checklist[i-1]}">
										<tr>
											<td>${model.userlist[i-1].username} </td>
											<td><a name="${model.userlist[i-1].personID}"
												class="btn btn-success follow" href="#">Following</a></td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td>${model.userlist[i-1].username}</td>
											<td><a name="${model.userlist[i-1].personID}"
												class="btn btn-default follow" href="#">Follow</a></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
		</c:when>
		<c:otherwise>
			<div class="container-fluid">
				<c:import url="admin-header.jsp"></c:import>
			</div>

			<div class="container">
				<h2 class="text-center">All Users</h2>
				<c:choose>
					<c:when test="${fn:length(userList)==1}">
						<h4>You are alone</h4>
					</c:when>
					<c:otherwise>
						<table class="table table-stripped table-hover">
							<tr>
								<th>User Name</th>
								<th></th>
							</tr>
							<c:forEach var="i" begin="1"
								end="${fn:length(userList)}">
								<c:choose>
									 <c:when test="${userList[i-1].role == 'admin'}">
										
									</c:when> 
									<c:when test="${userList[i-1].status == 'false'}">
										<tr>
											<td>${userList[i-1].username}</td>
											<td><a name="${userList[i-1].personID}"
												class="btn btn-success block" href="#">Blocking</a></td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td>${userList[i-1].username}</td>
											<td><a name="${userList[i-1].personID}"
												class="btn btn-default block" href="#">Block</a></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
		</c:otherwise>
	</c:choose>

</body>
</html>