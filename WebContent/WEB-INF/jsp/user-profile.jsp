<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<c:set var="account" value="${sessionScope.account}" />
<title><c:out value="${account.firstName}" /> <c:out
		value="${account.lastName}" /></title>
<c:set var="prev_page"
	value="Controller?command=profile&account_id=${account.id}"
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
	<div class="container">
		<%@include file="inc/top-menu.jsp"%>


		<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 container"
			align="center">
			<h3>Профиль пользователя</h3>
			<div class="container col-lg-4 col-lg-offset-1">

				<img src="${account.photo }" width="250" class="img-rounded"
					onerror="this.src = 'images/author/noFoto.jpg'" alt="Аватар">
			</div>
			<div class="container col-lg-6">
				<table class="table table-hover">
					<tbody>
						<tr>
							<th scope="row">Имя</th>
							<td><c:out value="${account.firstName}" /></td>
						</tr>
						<tr>
							<th scope="row">Фамилия</th>
							<td><c:out value="${account.lastName}" /></td>
						</tr>
						<tr>
							<th scope="row">Логин</th>
							<td><c:out value="${account.login}" /></td>
						</tr>
						<tr>
							<th scope="row">Дата рождения</th>
							<td><fmt:formatDate value="${account.birthDay}" /></td>
						</tr>
						<tr>
							<th scope="row">E-mail</th>
							<td><c:out value="${account.email}" /></td>
						</tr>
						<tr>
							<th scope="row">Страна</th>
							<c:set var="country" value="${requestScope.country}" />
							<td><c:out value="${country.name }" /></td>
						</tr>
						<tr>
							<th scope="row">Телефон</th>
							<td><c:out value="${account.phone}" /></td>
						</tr>
						<tr>
							<th scope="row">Роль</th>
							<td><c:out value="${account.role}" /></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div align="right">
			<form action="Controller">
				<input type="hidden" name="command" value="update_account_page" />
				<button type="submit" class="btn btn-success">Редактировать
					профиль</button>
			</form>
			<button type="button" class="btn btn-danger">Отмена</button>
		</div>

		<%@include file="inc/footer.jsp"%>
	</div>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>

























