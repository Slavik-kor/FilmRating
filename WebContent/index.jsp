<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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

	<%@include file="WEB-INF/jsp/inc/top-menu.jsp"%>

<div class="container">
<div id="carousel-example-generic" class="carousel slide" data-ride="carousel" align="center">
  <!-- Indicators -->
  <ol class="carousel-indicators">
    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
  </ol>

  <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox">
    <div class="item active">
      <img src="images/poster/BrestCastle.jpg" width="100%" alt="picture1">
      <div class="carousel-caption">
        ...
      </div>
    </div>
    <div class="item">
      <img src="images/poster/DieHard.jpg" width="100%" alt="picture2">
      <div class="carousel-caption">
        ...
      </div>
    </div>
     <div class="item">
      <img src="images/poster/Duhless.jpg" width="100%" alt="picture3">
      <div class="carousel-caption">
        ...
      </div>
    </div>
  </div>
  <!-- Элементы управления -->
  <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
    <span class="glyphicon glyphicon-chevron-left"></span>
  </a>
  <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
    <span class="glyphicon glyphicon-chevron-right"></span>
  </a>
</div>
</div>

	<%@include file="WEB-INF/jsp/inc/footer.jsp"%>

	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>

</body>
</html>