
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
<c:set var="rating" value="${requestScope.rate}" />
<c:set var="prev_page"
	value="Controller?command=film_card&film=${film.id}" scope="session" />
<title>Film</title>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="locale.film.year" var="year" />
<fmt:message bundle="${loc}" key="locale.film.country" var="country" />
<fmt:message bundle="${loc}" key="locale.film.genre" var="genreTitle" />
<fmt:message bundle="${loc}" key="locale.film.boxOffice" var="boxOffice" />
<fmt:message bundle="${loc}" key="locale.film.release" var="release" />
<fmt:message bundle="${loc}" key="locale.film.directors" var="directorsTitle" />
<fmt:message bundle="${loc}" key="locale.film.scenario" var="scenarioTitle" />
<fmt:message bundle="${loc}" key="locale.film.actors" var="actorsTitle" />
<fmt:message bundle="${loc}" key="locale.film.teaser" var="teaser" />
<fmt:message bundle="${loc}" key="locale.film.description" var="description" />
<fmt:message bundle="${loc}" key="locale.film.comments" var="userComments" />
<fmt:message bundle="${loc}" key="locale.film.authMessage" var="authMessage" />
<fmt:message bundle="${loc}" key="locale.filmList.budget" var="budget" />
<fmt:message bundle="${loc}" key="locale.filmList.audience" var="audience" />
<fmt:message bundle="${loc}" key="locale.film.red" var="editComment" />
<fmt:message bundle="${loc}" key="locale.film.delete" var="delComment" />
<fmt:message bundle="${loc}" key="locale.film.addComment" var="addComment" />
<fmt:message bundle="${loc}" key="locale.film.rate" var="userRate" />
<fmt:message bundle="${loc}" key="locale.film.comment" var="textComment" />
<fmt:message bundle="${loc}" key="locale.film.submit" var="submit" />
<fmt:message bundle="${loc}" key="locale.film.clear" var="clear" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.css" rel="stylesheet">

<link href="css/bootstrap-theme.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<%@include file="inc/top-menu"%>
		<c:if test="${account.role == 'Admin' }">
			<div class="container col-lg-9 col-md-9 col-sm-9">
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="delete_film" />
				<input type="hidden" name="idFilm" value="${film.id }" />
				<button type="submit" class="btn btn-danger" type="button">Удалить</button>
			</form>
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="update_film_page" />
				<input type="hidden" name="idFilm" value="${film.id }" />
				<button type="submit" class="btn btn-warning" type="button">Изменить</button>
			</form>
			</div>
		</c:if>
		<div class="container col-lg-9 col-md-9 col-sm-9">

			<div class="row">
				<div class="container col-lg-4 col-lg-offset-2">
					<h3><c:out value="${film.title}" /></h3>
				</div>
				<div class = "container col-lg-2 col-lg-offset-4">
				
					<h3>Рейтинг:<fmt:formatNumber type="number" maxFractionDigits="2" value="${rating }"/></h3>
				
				</div>
				<div class="container col-lg-4 col-lg-offset-1">
					<img src="${film.poster }" width="250" class="img-rounded"
						alt="постер" onerror="this.src = 'images/poster/noFoto.jpg'">
				</div>
				<div class="container col-lg-6 col-lg-offset-1">
					<table class="table table-hover">
						<tbody>
							<tr>
								<th scope="row">${year }</th>
								<td><fmt:formatDate value="${film.premierDate }"
										pattern="yyyy" /></td>
							</tr>
							<tr>
								<th scope="row">${country }</th>
								<td><c:set var="countries"
										value="${requestScope.country_list}" /> <c:forEach
										items="${countries}" var="country" varStatus="loopStatus">
										<c:out value="${country.name} " />
										<c:if test="${!loopStatus.last}">, </c:if>
									</c:forEach></td>
							</tr>
							<tr>
								<th scope="row">${genreTitle }</th>
								<td><c:set var="genres" value="${requestScope.genre_list}" />
									<c:forEach items="${genres}" var="genre" varStatus="loopStatus">
										<c:out value="${genre.name} " />
										<c:if test="${!loopStatus.last}">, </c:if>
									</c:forEach></td>
							</tr>
							<tr>
								<th scope="row">${budget }</th>
								<td><fmt:formatNumber value="${film.budget}"
										type="currency" currencySymbol="$" maxFractionDigits="0" /></td>
							</tr>
							<tr>
								<th scope="row">${boxOffice }</th>
								<td><fmt:formatNumber value="${film.boxOfficeCash}"
										type="currency" currencySymbol="$" maxFractionDigits="0" /></td>
							</tr>
							<tr>
								<th scope="row">${audience }</th>
								<td><fmt:formatNumber value="${film.audience }" /></td>
							</tr>
							<tr>
								<th scope="row">${release }</th>
								<td><fmt:formatDate value="${film.premierDate }" /></td>
							</tr>
						</tbody>
					</table>
					<c:set var="directors" value="${requestScope.directors_list }" />
					<c:if test="${not empty directors }">
						<p>${directorsTitle }</p>

						<c:set var="count" value="1" />
						<table class="table table-hover">
							<tbody>
								<c:forEach items="${directors}" var="director">
									<tr>
										<th scope="row">
										<c:if test="${fn:length(directors)>1 }">
										${count}
										</c:if>
										</th>
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
						<p>${scenarioTitle }</p>

						<c:set var="count" value="1" />
						<table class="table table-hover">
							<tbody>
								<c:forEach items="${scWriters}" var="scWriter">
									<tr>
									<th scope="row">
									<c:if test="${fn:length(scWriters)>1 }">
										${count}
									</c:if>
									</th>
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
					<c:if test="${not empty actors }">
						<p>${actorsTitle }</p>
						<c:set var="count" value="1" />
						<table class="table table-hover">
							<tbody>
								<c:forEach items="${actors}" var="actor">
									<tr>
										<th scope="row">
										<c:if test="${fn:length(actors)>1 }">
										${count}
										</c:if>
										</th>
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
			<c:if test="${not empty film.teaser }">
			<div class="container col-md-10 col-lg-10 col-sm-9">
			<h2 class="page-header">${teaser }</h2>
				<iframe width="560" height="315" src="${film.teaser}"
					frameborder="0" allowfullscreen ></iframe>
			</div>
			</c:if>
			<c:if test="${not empty film.description }">
			<div class="container col-md-10 col-lg-10 col-sm-9">
				<h2 class="page-header">${description }</h2>
				<c:out value="${film.description}" />
			</div>
			</c:if>
			<c:set var="comments" value="${requestScope.comment_list}" />
			<c:set var="accounts" value="${requestScope.account_comment_list}" />
			<div class="container col-md-10 col-lg-10 col-sm-9">

				<c:if test="${not empty comments }">
					<h2 class="page-header">${userComments }</h2>
					
					<c:forEach  var="index" begin = "0" end="${fn:length(comments)-1}">   
						<div class="container-fluid  hide-text">
							<div class="row">
								<c:out value="${accounts[index].login}" />
								|
								<fmt:formatDate value="${comments[index].commentDate}" />
								|
								<c:out value="${comments[index].rate }" />
							</div>
							<div class=" padd-0 brdr bgc-fff btm-mrg-20 box-shad">
								<div class="view">
									<img src="${accounts[index].photo }"  width="50"
										class="img-rounded" onerror="this.src = 'images/author/noFoto.jpg'" alt="постер">
								</div>
								<div class="detail">

									<p id="field" >
										<c:out value="${comments[index].comment }" />
									</p>

								</div>
								<div class="row">
									<c:if test="${comments[index].accountId==account.id }">			
										<button class="btn btn-success" >${editComment }</button>
										<form  action="Controller" method="post">
										<input type="hidden" name="command" value="delete_comment" />
										<input type="hidden" name="film" value="${film.id }" />
										<button class="btn btn-danger" type="button"
											data-toggle="modal" data-target="#myModal">${delComment }</button>
										</form>
										
										<!-- Modal -->
										<div class="modal fade" id="myModal" tabindex="-1"
											role="dialog" aria-labelledby="myModalLabel"
											aria-hidden="true">
											<div class="modal-dialog" role="document">
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal"
															aria-label="Close">
															<span aria-hidden="true">&times;</span>
														</button>
														<h4 class="modal-title" id="myModalLabel">Подтверждение</h4>
													</div>
													<div class="modal-body">Удалить комментарий?</div>
													<div class="modal-footer">
														
													<form action="Controller" method="post">
														<input type="hidden" name="command" value="delete_comment" />
														<input type="hidden" name="film" value="${film.id }" />
														<button type="submit" class="btn btn-primary">Удалить</button>
													<button type="button" class="btn btn-secondary"
															data-dismiss="modal">Закрыть</button>
													</form>
													</div>
												</div>
											</div>
										</div> <!-- modal end -->		
																	
									</c:if>
								</div>
							</div>

						</div> 
					</c:forEach>   
				</c:if>
				<c:choose>
					<c:when test="${not empty account}">
						<h2 class="page-header">${addComment }</h2>
						<div class="container-fluid  hide-text">
							<div class="row">
								<c:out value="${account.login }" />

							</div>
							<form action="Controller" method="post" role="form">
								<input type="hidden" name="command" value="add_comment">
								<input type="hidden" name="account" value="${account.id}">
								<input type="hidden" name="film" value="${film.id }">
								<fieldset class="form-group">
									<label for="rate">${userRate }</label> <select class="form-control"
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
									<label for="comment">${textComment }</label>
									<textarea class="form-control" name="comment"
										placeholder="Текст отзыва"></textarea>
								</div>
								<button type="submit" class="btn btn-success">${submit }</button>
								<button type="reset" class="btn btn-warning">${clear }</button>
							</form>

						</div>


					</c:when>
					<c:otherwise>
						<h5>${authMessage }</h5>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
	</div>
	<%@include file="inc/footer"%>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>
	
</body>
</html>