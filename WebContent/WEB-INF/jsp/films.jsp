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
<link href="css/Items.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">

</head>
<body>
	<div class="container">
		<%@include file="inc/top-menu.jsp"%>
		<c:set var="films" value="${requestScope.films}" />
		<div class="container col-md-10 col-lg-10 col-sm-9">
			<h2 class="page-header">Фильмы</h2>

			<c:forEach items="${films}" var="film">
				<!--  		<div class="container-fluid">
					<div class="row-fluid">
						<a href="Controller?command=film_Card&film=${film.id}"> <c:out
								value="${film.title}" /></a>
					</div>
					<div class="container">
						<div class="container">
							<div class="col-sm-3">
								<a href="Controller?command=film_Card&film=${film.id}"><img
									src="${film.poster}" class="img-rounded"
									onerror="this.src = 'images/poster/noFoto.jpg'"
									alt="${film.title}" width="250"></a>
							</div>
							<div class="col-sm-7">
								<p>
									<c:out value="${film.description }" />
								</p>
							</div>
						</div>
						<div class="row">
							<a href="Controller?command=film_Card&film=${film.id}">Подробнее</a>
						</div>

					</div>
				</div> -->

				<div class="container-fluid  hide-text" >
					
					<div class=" padd-0 brdr bgc-fff btm-mrg-20 box-shad" >
						<div class="view">
							<a href="Controller?command=film_Card&film=${film.id}"><img
								src="${film.poster}" class="img-rounded"
								onerror="this.src = 'images/poster/noFoto.jpg'"
								alt="${film.title}" width="200"></a>

						</div>
						<div class="detail" >
							<div >
							<div class="title">
							<a href="Controller?command=film_Card&film=${film.id}" ><h3><c:out value="${film.title}" /></h3></a>
										</div>
								<table class="table table-hover">
									<tbody>
										<tr> 
											<td>Бюджет</td>
											<td>11111</td>
										</tr>
										<tr>
											<td>Бюджет</td>
											<td>11111</td>
										</tr>
										<tr>
											<td>Бюджет</td>
											<td>11111</td>
										</tr>
									</tbody>
								</table>
								<div>
									<p class="small hidden-xs">
										<c:out value="${film.description }" />
									</p>
								</div>
								
							</div>
							<div class="stats wb-red-bg">
									<span class="fa fa-photo pull-right" rel="tooltip"
										title="Photos"> <strong><a
											href="Controller?command=film_Card&film=${film.id}">Подробнее</a></strong>
									</span>
								</div> 
						</div>
								
					</div>

				</div>
			</c:forEach>


		</div>
<div class="container col-md-10 col-lg-10 col-sm-9">
<nav aria-label="Page navigation" style="text-align: center;">
  <ul class="pagination">
    <li>
      <a href="#" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
    <li><a href="#">1</a></li>
    <li><a href="#">2</a></li>
    <li><a href="#">3</a></li>
    <li><a href="#">4</a></li>
    <li><a href="#">5</a></li>
    <li>
      <a href="#" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>
</div>

		<%@include file="inc/footer.jsp"%>

		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</div>
</body>
</html>









