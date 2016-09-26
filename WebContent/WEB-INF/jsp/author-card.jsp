<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<fmt:message bundle="${loc}" key="locale.author.filmography" var="filmography" />
<fmt:message bundle="${loc}" key="locale.author.director" var="dir_loc" />
<fmt:message bundle="${loc}" key="locale.author.scenario" var="scen_loc" />
<fmt:message bundle="${loc}" key="locale.author.actor" var="act_loc" />
<fmt:message bundle="${loc}" key="locale.author.edit" var="edit" />
<fmt:message bundle="${loc}" key="locale.author.delete" var="delete" />
<fmt:message bundle="${loc}" key="locale.profile.firstName" var="fName_loc" />
<fmt:message bundle="${loc}" key="locale.profile.lastName" var="lName_loc" />
<fmt:message bundle="${loc}" key="locale.profile.birthday" var="birthday_loc" />
<fmt:message bundle="${loc}" key="locale.profile.country" var="country_loc" />

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">
</head>

<body>
<div class="container">
	<%@include file="inc/top-menu"%>
	<c:if test="${account.role=='Admin'}">
		<div class="container col-lg-9 col-md-9 col-sm-9">
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="delete_author" />
				<input type="hidden" name="idAuthor" value="${author.id }" />
				<button type="submit" class="btn btn-danger" type="button">${delete }</button>
			</form>
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="update_author_page" />
				<input type="hidden" name="idAuthor" value="${author.id }" />
				<button type="submit" class="btn btn-warning" type="button">Изменить автора</button>
			</form>
		</div>
	</c:if>
	<div class="container col-lg-9 col-md-9 col-sm-9">

		<div class="row">
			
			<div class="container col-lg-4 col-lg-offset-1">
				<img src="${author.photo}" width="250" class="img-rounded"
					onerror="this.src = 'images/author/noFoto.jpg'" alt="фото">
			</div>
			<div class="container col-lg-6">
				<table class="table table-hover">
					<tbody>
						<tr>
							<th scope="row">${fName_loc }</th>
							<td><c:out value="${author.firstName}" /></td>
						</tr>
						<tr>
							<th scope="row">${lName_loc }</th>
							<td><c:out value="${author.lastName}" /></td>
						</tr>
						<tr>
							<th scope="row">${birthday_loc }</th>
							<td><fmt:formatDate value="${author.birthDay}" /></td>
						</tr>
						<tr>
							<th scope="row">${country_loc }</th>
							<td><c:set var="country" value="${requestScope.country}" />
								<c:out value="${country.name}" /></td>
						</tr>
						<tr>
				</table>
				<h3>${filmography }</h3>
				<c:set var="directorFilms" value="${requestScope.directorFilms }" />
				<c:if test="${not empty directorFilms }">
				<p>${dir_loc }</p>
				
				<c:set var="count" value="1" />
				<table class="table table-hover">
					<tbody>
						<c:forEach items="${directorFilms}" var="dFilm">
							<tr>
								<th scope="row">
								<c:if test="${fn:length(directorFilms)>1 }">
								${count}
								</c:if>
								</th>
								<c:set var="count" value="${count + 1}" />
								<td><a href="Controller?command=film_card&film=${dFilm.id}"><c:out
											value="${dFilm.title}" /></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</c:if>
				<c:set var="scWriterFilms"	value="${requestScope.scenarioWriterFilms }" />
				<c:if test="${not empty scWriterFilms }">
				<p>${scen_loc }</p>
				<c:set var="count" value="1" />
				<table class="table table-hover">
					<tbody>
						<c:forEach items="${scWriterFilms}" var="scWriterFilm">
							<tr>
								<th scope="row">
								<c:if test="${fn:length(scWriterFilms)>1 }">
								${count}
								</c:if>
								</th>
								<c:set var="count" value="${count + 1}" />
								<td><a
									href="Controller?command=film_card&film=${scWriterFilm.id}"><c:out
											value="${scWriterFilm.title}" /></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</c:if>
				<c:set var="actorFilms" value="${requestScope.actorFilms }" />
				<c:if test="${not empty actorFilms }">
				<p>${act_loc }</p>
				
				<c:set var="count" value="1" />
				<table class="table table-hover">
					<tbody>
						<c:forEach items="${actorFilms}" var="aFilm">
							<tr>
								<th scope="row">
								<c:if test="${fn:length(actorFilms)>1 }">
								${count}
								</c:if>
								</th>
								<c:set var="count" value="${count + 1}" />
								<td><a href="Controller?command=film_card&film=${aFilm.id}"><c:out
											value="${aFilm.title}" /></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</c:if>
			</div>
		</div>
	
	</div>

</div>
<%@include file="inc/footer"%>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>