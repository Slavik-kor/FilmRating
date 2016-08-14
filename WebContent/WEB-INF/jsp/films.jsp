<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% session.setAttribute("prev_page","Controller?command=newfilms&value=5");%>
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
<div class="container col-md-9 col-lg-9 col-sm-9">
<table class="table table-hover">
<tbody>
<tr>
					<th>#</th>
					<th>Постер</th>
					<th>Название</th>
					<th>Бюджет</th>
					<th>Сборы</th>
				</tr>
<c:set var="count" value="1" scope="page" />
<c:forEach items="${films}" var="film">
 <tr>
 <th scope="row">${count}</th>
 <c:set var="count" value="${count + 1}" scope="page"/>
					<td><a href="Controller?command=film_Card&film=${film.id}"><img src="${film.poster}" onerror="this.src = 'images/avatar/noFoto.jpg'" alt="${film.title}"
							width="150"></a></td>
					<td><a href="Controller?command=film_Card&film=${film.id}"><c:out value="${film.title}"/></a></td>
 					<td><fmt:formatNumber value="${film.budget}" type="currency" currencySymbol="$"/></td>
					<td><fmt:formatNumber value="${film.boxOfficeCash}" type="currency" currencySymbol="$"/></td>
 </tr>
 </c:forEach>
 </tbody> 
</table>  
</div>


    <%@include file="inc/footer.jsp" %>

	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>

</body>
</html>