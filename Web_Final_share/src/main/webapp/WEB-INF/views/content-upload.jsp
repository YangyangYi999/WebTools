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

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<style>
.btn{
margin:0 auto;
}

body {
   	background: url(${contextPath}"/share/resources/images/background.jpg"); 
   	background-size : cover;
	height: 85vh;
	background-size: cover;
	 opacity:1;
}
</style>

<script>
	$(document).ready(function() {

		$('#read').change(function() {
			readURL(this);

		});

		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#one').attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
			}
		}

	});
</script>
<title>PicShare</title>
</head>
<body>
	<div class="container-fluid">
		<c:import url="header.jsp"></c:import>
	</div>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<div class="continer">

		<h2 class="text-center">Image Upload</h2>

		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4" id="loc">
				<img id="one" src="" width="430" height="300" />
			</div>
		</div>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<%-- <form:form commandName="content" method="post"
					enctype="multipart/form-data">
					<input type="file" class="form-control loader" id="read" name="pic" />
					<textarea name="artical"  rows="10" cols="30"></textarea>
					<input type="submit" class="btn btn-primary" value="Upload Button" />
				</form:form> --%>
				<form action="${contextPath}/content/upload" method="post" enctype="multipart/form-data">
				
					<input type="file" class="form-control loader" id="read" name="pic" />
					<br/>
					<input type="text" name="menuName"/>
					<select name="category">
					  <option value ="Chinese Cuisine">Chinese Cuisine</option>
					  <option value ="Eastern Cuisine">Eastern Cuisine</option>
					  <option value="Korean Cursine">Korean Cursine</option>
					  <option value="Japan Cuisine">Japan Cuisine</option>
					</select>
					<br/>
					
					<br/>
				
					<textarea name="artical"  rows="10" cols="60"></textarea>
					<br/>
							
					<input type="submit" class="btn btn-primary" value="Upload" />
					
					<br/>
					<br/>
				</form>
			</div>
		</div>

	</div>
</body>
</html>