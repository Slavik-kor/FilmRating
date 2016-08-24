<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<c:set var="account" value="${sessionScope.account}" />
<c:set var="author" value="${requestScope.author}" />
<title><c:out value="${author.firstName}" /><c:out
		value=" ${author.lastName}" /></title>
<c:set var="prev_page"
	value="Controller?command=author_card&author_id=${author.id}"
	scope="session" />

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

	<div class="container col-lg-9 col-md-9 col-sm-9">

		<div class="row">
			<div class="container col-lg-4 col-lg-offset-4"></div>
			<div class="container col-lg-4 col-lg-offset-1">
				<img src="${author.photo}" width="250" class="img-rounded"
					onerror="this.src = 'images/author/noFoto.jpg'" alt="фото">
			</div>
			<div class="container col-lg-6">
				<table class="table table-hover">
					<tbody>
						<tr>
							<th scope="row">Имя</th>
							<td><c:out value="${author.firstName}" /></td>
						</tr>
						<tr>
							<th scope="row">Фамилия</th>
							<td><c:out value="${author.lastName}" /></td>
						</tr>
						<tr>
							<th scope="row">Дата рождения</th>
							<td><fmt:formatDate value="${author.birthDay}" /></td>
						</tr>
						<tr>
							<th scope="row">Страна рождения</th>
							<td><c:set var="country" value="${requestScope.country}" />
								<c:out value="${country.name}" /></td>
						</tr>
						<tr>
				</table>
				<h3>Фильмография</h3>
				<p>Режисер</p>
				<c:set var="directorFilms" value="${requestScope.directorFilms }" />
				<c:set var="count" value="1" />
				<table class="table table-hover">
					<tbody>
						<c:forEach items="${directorFilms}" var="dFilm">
							<tr>
								<th scope="row">${count}</th>
								<c:set var="count" value="${count + 1}" />
								<td><a href="Controller?command=film_card&film=${dFilm.id}"><c:out
											value="${dFilm.title}" /></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<p>Сценарист</p>
				<c:set var="scWriterFilms"
					value="${requestScope.scenarioWriterFilms }" />
				<c:set var="count" value="1" />
				<table class="table table-hover">
					<tbody>
						<c:forEach items="${scWriterFilms}" var="scWriterFilm">
							<tr>
								<th scope="row">${count}</th>
								<c:set var="count" value="${count + 1}" />
								<td><a
									href="Controller?command=film_card&film=${scWriterFilm.id}"><c:out
											value="${scWriterFilm.title}" /></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<p>Актер</p>
				<c:set var="actorFilms" value="${requestScope.actorFilm }" />
				<c:set var="count" value="1" />
				<table class="table table-hover">
					<tbody>
						<c:forEach items="${actorFilm}" var="aFilm">
							<tr>
								<th scope="row">${count}</th>
								<c:set var="count" value="${count + 1}" />
								<td><a href="Controller?command=film_card&film=${aFilm.id}"><c:out
											value="${aFilm}" /></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

	</div>

	<%@include file="inc/footer.jsp"%>

	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>