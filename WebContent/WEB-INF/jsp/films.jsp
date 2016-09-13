<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	session.setAttribute("prev_page", "Controller?command=newfilms");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<c:set var="account" value="${sessionScope.account}" />
<title>Films</title>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="locale.filmList.films" var="filmList" />
<fmt:message bundle="${loc}" key="locale.filmList.release" var="release" />
<fmt:message bundle="${loc}" key="locale.filmList.budget" var="budget" />
<fmt:message bundle="${loc}" key="locale.filmList.audience" var="audience" />
<fmt:message bundle="${loc}" key="locale.filmList.more" var="more" />
<fmt:message bundle="${loc}" key="locale.filmList.notFound" var="notFound" />
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
		<c:set var="films" value="${requestScope.films}" />
		<div class="container col-md-10 col-lg-10 col-sm-9">
			<h2 class="page-header">${filmList}</h2>
			<c:choose>
				<c:when test="${not empty films}">
					<c:forEach items="${films}" var="film">
						<div class="container-fluid  hide-text">

							<div class=" padd-0 brdr bgc-fff btm-mrg-20 box-shad">
								<div class="view">
									<a href="Controller?command=film_Card&film=${film.id}"><img
										src="${film.poster}" class="img-rounded"
										onerror="this.src = 'images/poster/noFoto.jpg'"
										alt="${film.title}" width="200"></a>

								</div>
								<div class="detail">
									<div>
										<div class="title">
											<a href="Controller?command=film_Card&film=${film.id}"><strong><c:out
														value="${film.title}" /></strong></a>
										</div>
										<table class="table table-hover">
											<tbody>
												<tr>
													<td>${release }</td>
													<td><fmt:formatDate value="${film.premierDate }"
															pattern="yyyy" /></td>
												</tr>
												<tr>
													<td>${budget }</td>
													<td><fmt:formatNumber value="${film.budget}"
															type="currency" currencySymbol="$" maxFractionDigits="0" /></td>
												</tr>
												<tr>
													<td>${audience }</td>
													<td><fmt:formatNumber value="${film.audience }" /></td>
												</tr>
											</tbody>
										</table>
										<div>
											<p class="small hidden-xs">
												<c:out value="${film.description }" />
											</p>
										</div>

									</div>
									<div class="stats wb-gray-bg">
										<span class="fa fa-photo pull-right" title="Film"> <strong><a
												href="Controller?command=film_Card&film=${film.id}">${more }</a></strong>
										</span>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
				<h5>${notFound }</h5>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="container col-md-10 col-lg-10 col-sm-9">
			<nav aria-label="Page navigation" style="text-align: center;">
				<ul class="pagination">
					<li><a href="#" aria-label="Previous"> <span
							aria-hidden="true">&laquo;</span>
					</a></li>
					<li><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
					<li><a href="#" aria-label="Next"> <span
							aria-hidden="true">&raquo;</span>
					</a></li>
				</ul>
			</nav>
		</div>

		<%@include file="inc/footer.jsp"%>

		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</div>
</body>
</html>









