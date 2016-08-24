<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	session.setAttribute("prev_page", "Controller?command=newfilms&value=10");
%>
<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="utf-8">
<c:set var="account" value="${sessionScope.account}" />
<title>Films</title>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc" />

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">

</head>
<body>
	<%@include file="inc/top-menu.jsp"%>

	<c:set var="films" value="${requestScope.films}" />
	<div class="container col-md-9 col-lg-9 col-sm-9">
		<table class="table table-hover">
			<tbody>
				<tr>
					<th>#</th>
					<th>Постер</th>
					<th>Название</th>
					<th>Бюджет</th>
					<th>Кассовые сборы</th>
				</tr>
				<c:set var="count" value="1" scope="page" />
				<c:forEach items="${films}" var="film">
					<tr>
						<th scope="row">${count}</th>
						<c:set var="count" value="${count + 1}" scope="page" />
						<td><a href="Controller?command=film_Card&film=${film.id}"><img
								src="${film.poster}"
								onerror="this.src = 'images/poster/noFoto.jpg'"
								alt="${film.title}" width="150"></a></td>
						<td><a href="Controller?command=film_Card&film=${film.id}"><c:out
									value="${film.title}" /></a></td>
						<td><fmt:formatNumber value="${film.budget}" type="currency"
								currencySymbol="$" maxFractionDigits="0" /></td>
						<td><fmt:formatNumber value="${film.boxOfficeCash}"
								type="currency" currencySymbol="$" maxFractionDigits="0" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>


	<%@include file="inc/footer.jsp"%>

	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>

</body>
</html>