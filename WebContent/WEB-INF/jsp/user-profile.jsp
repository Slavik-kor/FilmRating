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
	<%@include file="inc/top-menu.jsp"%>
	

	<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 container">
		<h3>Профиль пользователя</h3>
		<div class="container col-md-3">

			<img src="${account.photo}" width="250" class="img-rounded"
				onerror="this.src = 'images/author/noFoto.jpg'" alt="Аватар">
		</div>
		<div class="container col-lg-9 col-md-9 col-sm-9">

			<p>
				<c:out value="${account.firstName}" /> 
				<c:out value="${account.lastName}" />
			</p>
			<p>
				Логин:
				<c:out value="${account.login}" />
			</p>
			<p>
				Дата рождения:
				<fmt:formatDate value="${account.birthDay}" />
			</p>
			<p>E-mail:</p>
			<p>Родной город:</p>
			<p>Телефон:</p>
			<p>Статус:</p>

		</div>
	</div>
	<div>
		<button type="submit" class="btn btn-success">Редактировать
			профиль</button>
		<button type="button" class="btn btn-danger">Отмена</button>
	</div>

	<%@include file="inc/footer.jsp"%>

	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>

























