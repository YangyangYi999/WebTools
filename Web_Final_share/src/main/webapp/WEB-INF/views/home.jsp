<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<c:set var="contextPath" value="${pageContext.request.contextPath}" /> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<!-- <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
<!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
 --><link rel="stylesheet" href="${contextPath}/resources/images/assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<%--         <link rel="stylesheet" href="${contextPath}/resources/assets/bootstrap/css/bootstrap.min.css">
 --%>        <link rel="stylesheet" href="${contextPath}/resources/images/assets/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="${contextPath}/resources/images/assets/css/form-elements.css">
        <link rel="stylesheet" href="${contextPath}/resources/images/assets/css/style.css">
        <link rel="shortcut icon" href="${contextPath}/resources/images/assets/ico/favicon.png">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${contextPath}/resources/images/assets/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${contextPath}/resources/images/assets/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${contextPath}/resources/images/assets/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="${contextPath}/resources/images/assets/ico/apple-touch-icon-57-precomposed.png">

</head>

<body>


        <div class="top-content">
            <div class="inner-bg">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-8 col-sm-offset-2 text">
                            <h1><strong>Share your menu</strong> </h1>
                            <div class="description">
                            	<p>
	                            	This is a menu share website, you can share your menu with others!
                            	</p>
                            </div>
                            
                        </div>
                    </div>
                    <div class="row text-center">
						<div class="col-xs-4"></div>
						<h1 style="font-color:red;">${status}</h1>
					</div>
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3 form-box">
                        	<div class="form-top">
                        		<div class="form-top-left">
                        			<h3>Login to our site </h3> <h3>or click</h3> <a href="${contextPath}/user/register">Register</a>
                            		<p>Enter your username and password to log on:</p>
                        		</div>
                        		<div class="form-top-right">
                        			<i class="fa fa-key"></i>
                        		</div>
                            </div>
                            <div class="form-bottom">
			                    <form action="user/login" method="post">
			                    	<div class="form-group">
			                    		<label class="sr-only" for="form-username">Username</label>
			                        	<input type="text" name="username" placeholder="Username..." class="form-username form-control" id="form-username" required="required">
			                        </div>
			                        <div class="form-group">
			                        	<label class="sr-only" for="form-password">Password</label>
			                        	<input type="password" name="password" placeholder="Password..." class="form-password form-control" id="form-password" required="required">
			                        </div>
			                        <button type="submit" class="btn">Sign in!</button>
			                    </form>
		                    </div>
                        </div>
                    </div>
                    
                </div>
            </div>
            
        </div>
     


        <!-- Javascript -->
        <script src="${contextPath}/resources/images/assets/js/jquery-1.11.1.min.js"></script>
        <script src="${contextPath}/resources/images/assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="${contextPath}/resources/images/assets/js/jquery.backstretch.min.js"></script>
        <script src="${contextPath}/resources/images/assets/js/scripts.js"></script>

</body>
</html>