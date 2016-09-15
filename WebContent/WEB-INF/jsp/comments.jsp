<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>Comments</title>
<c:set var="prev_page" value="Controller?command=comment_list"
	scope="session" />

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">
<link href="css/Items.css" rel="stylesheet">
</head>
<body>

	<div class="container">
		<%@include file="inc/top-menu.jsp"%>
		<c:set var="comments" value="${requestScope.comment_list }" />
		<div class="container col-md-9 col-lg-9 col-sm-9">
			<h2 class="page-header">Мои отзывы:</h2>
			<c:set var="comments" value="${requestScope.comment_list}" />
			<c:set var="films" value="${requestScope.film_list}" />
			<div class="container col-lg-12 col-md-12 col-sm-12">
				<c:choose>
					<c:when test="${not empty comments }">
						<c:forEach var="index" begin="0" end="${fn:length(comments)-1}">

							<div class="container-fluid  hide-text">
								<div class="row">
									<a href="Controller?command=film_Card&film=${films[index].id}"><c:out
											value="${films[index].title}" /></a> |
									<fmt:formatDate value="${comments[index].commentDate}" />
									|Оценка:
									<c:out value="${comments[index].rate }" />
								</div>
								<div class=" padd-0 brdr bgc-fff btm-mrg-20 box-shad">
									<div class="view">
										<a href="Controller?command=film_Card&film=${films[index].id}"><img
											src="${films[index].poster }" width="150" class="img-rounded"
											onerror="this.src = 'images/poster/noFoto.jpg'" alt="постер"></a>
									</div>
									<div class="detail">

										<p>
											<c:out value="${comments[index].comment }" />
										</p>

									</div>
									<div class="row">

										<button class="btn btn-success">Редактировать</button>

										<button class="btn btn-danger" type="button"
											data-toggle="modal" data-target="#myModal">Удалить</button>
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
														
													<form type="submit" action="Controller" method="post">
														<input type="hidden" name="command" value="delete_comment" />
														<input type="hidden" name="film" value="${films[index].id }" />
														<button type="submit" class="btn btn-primary">Удалить</button>
													<button type="button" class="btn btn-secondary"
															data-dismiss="modal">Закрыть</button>
													</form>
													</div>
												</div>
											</div>
										</div> <!-- modal end -->
									</div>
								</div>

							</div>


						</c:forEach>
					</c:when>
					<c:otherwise>
						<h5>У Вас пока нет отзывов</h5>
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

























