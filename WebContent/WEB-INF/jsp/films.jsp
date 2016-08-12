<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% session.setAttribute("prev_page","WEB-INF/jsp/films.jsp");%>
<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="utf-8">
<title>Films</title>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="locale.locbutton.name.ru"
	var="ru_button" />
<fmt:message bundle="${loc}" key="locale.locbutton.name.en"
	var="en_button" />
<fmt:message bundle="${loc}" key="locale.brand"
	var="brand" />
<fmt:message bundle="${loc}" key="locale.menu.top.reg"
	var="reg" />
<fmt:message bundle="${loc}" key="locale.menu.top.sign_in"
	var="signIn" />
<fmt:message bundle="${loc}" key="locale.menu.top.login"
	var="login" />
<fmt:message bundle="${loc}" key="locale.menu.top.password"
	var="password" />	
<fmt:message bundle="${loc}" key="locale.menu.side.new"
	var="newFilm" />
<fmt:message bundle="${loc}" key="locale.menu.side.rate"
	var="rate" />			
<fmt:message bundle="${loc}" key="locale.menu.side.genre"
	var="genre" />		
<fmt:message bundle="${loc}" key="locale.menu.side.years"
	var="years" />				
	
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">

</head>
<body>
	<%@include file="inc/top-menu.jsp" %>
	<%@include file="inc/left-menu.jsp" %>

<c:set var="films" value="${requestScope.films}" />
<c:forEach items="${films}" var="film">
      <h2>
          <c:out value="${film}"/>
      </h2>
  </c:forEach>


	<div class="container col-md-9 col-lg-9 col-sm-9">
		<table class="table table-hover">
			<tbody>
				<tr>
					<th>#</th>
					<th>Постер</th>
					<th>Название</th>
					<th>Добавлен</th>
					<th>Рейтинг</th>
				</tr>
				<tr>
					<th scope="row">1</th>
					<td><a href="#"><img src="images/Expandabales.jpg"
							width="50"></a></td>
					<td><a href="#">Неудержимые</a></td>
					<td>05.02.2016</td>
					<td>6.53</td>
				</tr>
				<tr>
					<th scope="row">2</th>
					<td><a href="#"><img src="images/element.jpg" width="50"></a></td>
					<td><a href="#">Пятый элемент</a></td>
					<td>05.02.2016</td>
					<td>8.05</td>
				</tr>
				<tr>
					<th scope="row">3</th>
					<td><a href="#"><img src="images/legend.jpg" width="50"></a></td>
					<td><a href="#">Легенда № 17</a></td>
					<td>07.03.2016</td>
					<td>8.33</td>
				</tr>
			</tbody>
		</table>
	</div>

    <%@include file="inc/footer.jsp" %>

	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>

</body>
</html>