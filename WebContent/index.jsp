<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="utf-8">
<title>Кинорейтинг</title>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.message" var="message" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
	var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en"
	var="en_button" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">

</head>
<body>
	<div class="container-fluid">
		<div class="navbar navbar-inverse navbar-static-top" role="navigation">

			<div class="container-fluid">
				<div class="navbar-header">

					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">КИНОРЕЙТИНГ</a>
				</div>
				<div class="navbar-collapse collapse">
					<form action="Controller"
						class="navbar-form navbar-right hidden-sm" method="post">
						<input type="hidden" name="command" value="authorization" />
						<div class="form-group">
							<input type="text" name="login" class="form-control"
								placeholder="Логин" value="">
						</div>
						<div class="form-group">
							<input type="password" name="password" class="form-control"
								placeholder="Пароль" value="">
						</div>
						<button type="submit" class="btn btn-primary">ВОЙТИ</button>
						<a href="" type="button">РЕГИСТРАЦИЯ</a>
					</form>
					<div class="container">
					<form action="Controller" method="post">
						<input type="hidden" name="local" value="ru" />
						<input type="hidden" name="command" value="localization" />
						<input type="submit" value="ru" />
					</form>
					<form action="Controller" method="post">
						<input type="hidden" name="local" value="en" />
						<input type="hidden" name="command" value="localization" />
						<input type="submit" value="en" />
					</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-lg-3 col-md-3 col-sm-3 sidebar">
		<ul class="nav nav-sidebar ">
			<li class="active"><a href="#">Новинки</a></li>
			<li><a href="#">По рейтингу</a></li>
			<li><a href="#">По жанру</a></li>
			<li><a href="#">По году</a></li>
		</ul>
	</div>
	<div class="container col-md-9 col-lg-9 col-sm-9">
		<table class="table table-hover">
			<tbody>
				<tr>
					<th>#</th>
					<th>Постер</th>
					<th>Название</th>
					<th>Добавлен</th>
					<th>Рейтинг</th>
				</tr>
				<tr>
					<th scope="row">1</th>
					<td><a href="#"><img src="images/Expandabales.jpg"
							width="50"></a></td>
					<td><a href="#">Неудержимые</a></td>
					<td>05.02.2016</td>
					<td>6.53</td>
				</tr>
				<tr>
					<th scope="row">2</th>
					<td><a href="#"><img src="images/element.jpg" width="50"></a></td>
					<td><a href="#">Пятый элемент</a></td>
					<td>05.02.2016</td>
					<td>8.05</td>
				</tr>
				<tr>
					<th scope="row">3</th>
					<td><a href="#"><img src="images/legend.jpg" width="50"></a></td>
					<td><a href="#">Легенда № 17</a></td>
					<td>07.03.2016</td>
					<td>8.33</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="container">
		<div class="row">
			<hr>
			<div class="col-lg-12">
				<div class="col-sm-8">
					<a href="#" id="menu-toggle">Terms of Service</a> | <a href="#">Privacy</a>
				</div>
				<div class="col-sm-4">
					<p>© 2016 Epam training. All rights reserved</p>
				</div>
			</div>
		</div>
	</div>

	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>

</body>
</html>