<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
<title>PicShare</title>
</head>
<body>

	<div class="header-top">
		<div class="container" style="padding-top:10px;">
			<h2>Welcome Admin</h2>
			</div>
			</div>
			
			<ul class="nav nav-pills justify-content-end">
  <li class="nav-item">
    <a class="nav-link" href="${contextPath}/user/login">Home page </a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="${contextPath}/user/blockStatus">Explore All Users</a>
  </li>
  <li class="nav-item">
   <a class="nav-link" href="${contextPath}/content/explore?pageNum=1">Explore All content</a>
  </li>
  <li class="nav-item">
    <a  class="nav-link" href="${contextPath}/logout"> Log Out</a>
  </li>
</ul>
			<hr/>
		
	

</body>
</html>