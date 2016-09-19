<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	session.setAttribute("prev_page", "index.jsp");
%>
<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="utf-8">
<title>Film rating</title>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">



</head>
<body>
<div class="container">
 <%@include file="WEB-INF/jsp/inc/top-menu"%>  
	
	<div class="container col-md-10 col-lg-10 col-sm-9">
		<div id="carousel" class="carousel slide"
			data-ride="carousel" align="center">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<li data-target="#carousel" data-slide-to="0"
					class="active"></li>
				<li data-target="#carousel" data-slide-to="1"></li>
				<li data-target="#carousel" data-slide-to="2"></li>
			</ol>

			<!-- Wrapper for slides -->
			<div class="carousel-inner" role="listbox">
				<div class="item active">
					<a href="Controller?command=film_Card&film=1"><img src="images/poster/BrestCastle.jpg" 
						alt="picture1" ></a>
					<div class="carousel-caption">Брестская крепость</div>
				</div>
				<div class="item">
					<a href="Controller?command=film_Card&film=5"><img src="images/poster/DieHard.jpg" alt="picture2" ></a>
					<div class="carousel-caption">Крепкий орешек</div>
				</div>
				<div class="item">
					<a href="Controller?command=film_Card&film=4"><img src="images/poster/Duhless.jpg" alt="picture3"></a>
					<div class="carousel-caption">Духless</div>
				</div>
			</div>
			<!-- Элементы управления -->
			<a class="left carousel-control" href="#carousel"
				data-slide="prev"> <span
				class="glyphicon glyphicon-chevron-left"></span>
			</a> <a class="right carousel-control" href="#carousel"
				data-slide="next"> <span
				class="glyphicon glyphicon-chevron-right"></span>
			</a>
		</div>
	</div>

	 
</div>
<%@include file="WEB-INF/jsp/inc/footer"%> 
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>

</body>
</html>