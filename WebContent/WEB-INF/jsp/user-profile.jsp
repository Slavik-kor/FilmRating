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
<fmt:message bundle="${loc}" key="locale.profile.title" var="title_page" />
<fmt:message bundle="${loc}" key="locale.profile.firstName" var="fName" />
<fmt:message bundle="${loc}" key="locale.profile.lastName" var="lName" />
<fmt:message bundle="${loc}" key="locale.profile.login" var="login" />
<fmt:message bundle="${loc}" key="locale.profile.birthday" var="bday" />
<fmt:message bundle="${loc}" key="locale.profile.country" var="country" />
<fmt:message bundle="${loc}" key="locale.profile.phoneNumber" var="phone" />
<fmt:message bundle="${loc}" key="locale.profile.role" var="role" />
<fmt:message bundle="${loc}" key="locale.profile.edit" var="edit" />
<fmt:message bundle="${loc}" key="locale.profile.delete" var="delete" />
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
			<h3>${title_page }</h3>
			<div class="container col-lg-3 col-lg-offset-1">

				<img src="${account.photo }" width="250" class="img-rounded"
					onerror="this.src = 'images/author/noFoto.jpg'" alt="Аватар">
			</div>
			<div class="container col-lg-7 col-lg-offset-1">
				<table class="table table-hover">
					<tbody>
						<tr>
							<th scope="row">${fName }</th>
							<td><c:out value="${account.firstName}" /></td>
						</tr>
						<tr>
							<th scope="row">${lName }</th>
							<td><c:out value="${account.lastName}" /></td>
						</tr>
						<tr>
							<th scope="row">${login }</th>
							<td><c:out value="${account.login}" /></td>
						</tr>
						<tr>
							<th scope="row">${bday }</th>
							<td><fmt:formatDate value="${account.birthDay}" /></td>
						</tr>
						<tr>
							<th scope="row">E-mail</th>
							<td><c:out value="${account.email}" /></td>
						</tr>
						<tr>
							<th scope="row">${country }</th>
							<c:set var="country" value="${requestScope.country}" />
							<td><c:out value="${country.name }" /></td>
						</tr>
						<tr>
							<th scope="row">${phone }</th>
							<td><c:out value="${account.phone}" /></td>
						</tr>
						<tr>
							<th scope="row">${role }</th>
							<td><c:out value="${account.role}" /></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div align="right">
			<form action="Controller">
				<input type="hidden" name="command" value="update_account_page" />
				<button type="submit" class="btn btn-success">${edit }</button>
			</form>
			<form action="Controller">
			   <input type="hidden" name="command" value="delete_account" />
			   <button type="submit" class="btn btn-danger">${delete }</button>
			</form>
		</div>

		
	</div>
	<%@include file="inc/footer.jsp"%>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>

























