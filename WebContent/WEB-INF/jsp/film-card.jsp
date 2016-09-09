
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
								<td><fmt:formatNumber value="${film.budget}"
										type="currency" currencySymbol="$" maxFractionDigits="0" /></td>
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
					<c:set var="directors" value="${requestScope.directors_list }" />
					<c:if test="${not empty directors }">
						<p>Режисеры</p>

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
					</c:if>
					<c:set var="scWriters"
						value="${requestScope.scenarioWriters_list }" />
					<c:if test="${not empty scWriters }">
						<p>Сценаристы</p>

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
					</c:if>
					<c:set var="actors" value="${requestScope.actors_list }" />
					<c:if test="${not empty scWriters }">
						<p>Актеры</p>
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
					</c:if>
				</div>
			</div>
			<div class="container col-md-10 col-lg-10 col-sm-9">
				<iframe width="560" height="315" src="${film.teaser}"
					frameborder="0" allowfullscreen align="center"></iframe>
			</div>
			<div class="container col-md-10 col-lg-10 col-sm-9">
				<h2 class="page-header">Описание</h2>
				<c:out value="${film.description}" />
			</div>

			<c:set var="comments" value="${requestScope.comment_list}" />
			<c:set var="accounts" value="${requestScope.accounts}" />
			<div class="container col-md-10 col-lg-10 col-sm-9">

				<c:if test="${not empty comments }">
					<h2 class="page-header">Комментарии</h2>

					<c:forEach items="${comments}" var="comment">
						<div class="container-fluid  hide-text">
							<div class="row">
								<c:out value="${comment.accountId }" />
								|
								<fmt:formatDate value="${comment.commentDate}" />
								|
								<c:out value="${comment.rate }" />
							</div>
							<div class=" padd-0 brdr bgc-fff btm-mrg-20 box-shad">
								<div class="view">
									<img src="images\author\noFoto.jpg" width="50"
										class="img-rounded" alt="постер">
								</div>
								<div class="detail">

									<p>
										<c:out value="${comment.comment }" />
									</p>

								</div>
								<div class="row">
									<c:if test="${comment.accountId==account.id }">
										<button class="btn btn-success">Редактировать</button>
										<button class="btn btn-danger">Удалить</button>
									</c:if>
								</div>
							</div>

						</div>
					</c:forEach>
				</c:if>
				<c:choose>
					<c:when test="${not empty account}">
						<h2 class="page-header">Добавить комментарий</h2>
						<div class="container-fluid  hide-text">
							<div class="row">
								<c:out value="${account.login }" />

							</div>
							<form action="Controller" method="post" role="form">
								<input type="hidden" name="command" value="add_comment">
								<input type="hidden" name="account" value="${account.id}">
								<input type="hidden" name="film" value="${film.id }">
								<fieldset class="form-group">
									<label for="rate">Оценка</label> <select class="form-control"
										id="rate" name="rate">
										<option>0</option>
										<option>1</option>
										<option>2</option>
										<option>3</option>
										<option>4</option>
										<option>5</option>
										<option>6</option>
										<option>7</option>
										<option>8</option>
										<option>9</option>
										<option>10</option>
									</select>
								</fieldset>
								<div class="form-group">
									<label for="comment">Комментарий</label>
									<textarea class="form-control" name="comment"
										placeholder="Название фильма"></textarea>
								</div>
								<button type="submit" class="btn btn-success">Отправить</button>
								<button type="reset" class="btn btn-warning">Очистить</button>
							</form>

						</div>


					</c:when>
					<c:otherwise>
						<h5>Для добавления комментария необходима авторизация</h5>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<%@include file="inc/footer.jsp"%>
	</div>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>