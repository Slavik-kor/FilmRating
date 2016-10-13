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
<fmt:message bundle="${loc}" key="locale.film.conf" var="confirmation" />
<fmt:message bundle="${loc}" key="locale.profile.delMess" var="reallyDelMess" />
<fmt:message bundle="${loc}" key="locale.film.delete" var="del" />
<fmt:message bundle="${loc}" key="locale.registration.cancel" var="cancel" />

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<%@include file="inc/top-menu"%>

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
			   <button  class="btn btn-danger" type="button" data-toggle="modal" data-target="#myModal1">${delete }</button>
				<!-- Modal -->
										<div class="modal fade" id="myModal1" tabindex="-1"
											role="dialog" aria-labelledby="myModalLabel"
											aria-hidden="true">
											<div class="modal-dialog" role="document">
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal"
															aria-label="Close">
															<span aria-hidden="true">&times;</span>
														</button>
														<h4 class="modal-title" id="myModalLabel">${confirmation }</h4>
													</div>
													<div class="modal-body">${reallyDelMess }</div>
													<div class="modal-footer">
														
													<form action="Controller" method="post">
														<input type="hidden" name="command" value="delete_account" />
														<button type="submit" class="btn btn-primary">${del }</button>
													<button type="button" class="btn btn-secondary"
															data-dismiss="modal">${cancel }</button>
													</form>
													</div>
												</div>
											</div>
										</div> <!-- modal end -->
				</div>

	</div>

	<%@include file="inc/footer"%>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>

























