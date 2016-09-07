<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<c:set var="account" value="${sessionScope.account}" />
<c:set var="film" value="${requestScope.film}" />
<c:set var="prev_page"
	value="Controller?command=film_card&film=${film.id}" scope="session" />
<title>Film</title>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/Items.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">
</head>
<body>
<div class="container">
	<%@include file="inc/top-menu.jsp"%>

	<div class="container col-lg-9 col-md-9 col-sm-9">

		<div class="row">
			<div class="container col-lg-4 col-lg-offset-4">
				<h3>
					<c:out value="${film.title}" />
				</h3>
			</div>
			<div class="container col-lg-4 col-lg-offset-1">
				<img src="${film.poster }" width="250" class="img-rounded"
					alt="постер">
			</div>
			<div class="container col-lg-6">
				<table class="table table-hover">
					<tbody>
						<tr>
							<th scope="row">Год</th>
							<td><fmt:formatDate value="${film.premierDate }"
									pattern="yyyy" /></td>
						</tr>
						<tr>
							<th scope="row">Страна</th>
							<td><c:set var="countries"
									value="${requestScope.country_list}" /> <c:forEach
									items="${countries}" var="country" varStatus="loopStatus">
									<c:out value="${country.name} " />
									<c:if test="${!loopStatus.last}">, </c:if>
								</c:forEach></td>
						</tr>
						<tr>
							<th scope="row">Жанр</th>
							<td><c:set var="genres" value="${requestScope.genre_list}" />
								<c:forEach items="${genres}" var="genre" varStatus="loopStatus">
									<c:out value="${genre.name} " />
									<c:if test="${!loopStatus.last}">, </c:if>
								</c:forEach></td>
						</tr>
						<tr>
							<th scope="row">Бюджет</th>
							<td><fmt:formatNumber value="${film.budget}" type="currency"
									currencySymbol="$" maxFractionDigits="0" /></td>
						</tr>
						<tr>
							<th scope="row">Кассовые сборы</th>
							<td><fmt:formatNumber value="${film.boxOfficeCash}"
									type="currency" currencySymbol="$" maxFractionDigits="0" /></td>
						</tr>
						<tr>
							<th scope="row">Зрители</th>
							<td><fmt:formatNumber value="${film.audience }" /></td>
						</tr>
						<tr>
							<th scope="row">Премьера</th>
							<td><fmt:formatDate value="${film.premierDate }" /></td>
						</tr>
					</tbody>
				</table>
				<p>Режисеры</p>
				<c:set var="directors" value="${requestScope.directors_list }" />
				<c:set var="count" value="1" />
				<table class="table table-hover">
					<tbody>
						<c:forEach items="${directors}" var="director">
							<tr>
								<th scope="row">${count}</th>
								<c:set var="count" value="${count + 1}" />
								<td><a
									href="Controller?command=author_card&author_id=${director.id}"><c:out
											value="${director.firstName} ${director.lastName}" /></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<p>Сценаристы</p>
				<c:set var="scWriters" value="${requestScope.scenarioWriters_list }" />
				<c:set var="count" value="1" />
				<table class="table table-hover">
					<tbody>
						<c:forEach items="${scWriters}" var="scWriter">
							<tr>
								<th scope="row">${count}</th>
								<c:set var="count" value="${count + 1}" />
								<td><a
									href="Controller?command=author_card&author_id=${scWriter.id}"><c:out
											value="${scWriter.firstName} ${scWriter.lastName}" /></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<p>Актеры</p>
				<c:set var="actors" value="${requestScope.actors_list }" />
				<c:set var="count" value="1" />
				<table class="table table-hover">
					<tbody>
						<c:forEach items="${actors}" var="actor">
							<tr>
								<th scope="row">${count}</th>
								<c:set var="count" value="${count + 1}" />
								<td><a
									href="Controller?command=author_card&author_id=${actor.id}"><c:out
											value="${actor.firstName} ${actor.lastName}" /></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="container col-md-10 col-lg-10 col-sm-9">
			<iframe width="560" height="315" src="${film.teaser}" frameborder="0"
				allowfullscreen align="center"></iframe>
		</div>
<div class="container col-md-10 col-lg-10 col-sm-9">
<h2 class="page-header">Описание</h2>
<c:out value="${film.description}" />
</div>
	</div>
	<c:set var="comments" value="${requestScope.comments}" />
	<c:set var="accounts" value="${requestScope.accounts}" />
	<div class="container col-md-10 col-lg-10 col-sm-9">
			
			<h2 class="page-header">Комментарии</h2>

			<c:forEach items="${comments}" var="comment">
				<div class="container-fluid  hide-text" >
					
					<div class=" padd-0 brdr bgc-fff btm-mrg-20 box-shad" >
						<div class="view">
							<a href="Controller?command=film_Card&film=${film.id}"><img
								src="${film.poster}" class="img-rounded"
								onerror="this.src = 'images/poster/noFoto.jpg'"
								alt="${film.title}" width="50"></a>

						</div>
						<div class="detail" >
							<div >
							<div class="title">
							<a href="Controller?command=film_Card&film=${film.id}" ><strong><c:out value="${film.title}" /></strong></a>
										</div>
								<table class="table table-hover">
									
								<div>
									<p class="small hidden-xs">
										<c:out value="${comment.comment }" />
									</p>
								</div>
								
							</div>
							
						</div>
								
					</div>

				</div>
			</c:forEach>


		</div>

	<%@include file="inc/footer.jsp"%>
</div>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>