<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="utf-8">
<c:set var="prev_page" value="Controller?command=reg_page"
	scope="session" />
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="locale.menu.top.reg" var="reg" />
<fmt:message bundle="${loc}" key="locale.registration.message"
	var="message" />
<fmt:message bundle="${loc}" key="locale.registration.fname" var="fname" />
<fmt:message bundle="${loc}" key="locale.registration.lname" var="lname" />
<fmt:message bundle="${loc}" key="locale.registration.birthday"
	var="birthday" />
<fmt:message bundle="${loc}" key="locale.registration.locality"
	var="locality" />
<fmt:message bundle="${loc}" key="locale.registration.phone_number"
	var="phone_number" />
<fmt:message bundle="${loc}" key="locale.registration.choose_photo"
	var="choose_photo" />
<fmt:message bundle="${loc}" key="locale.registration.sign_up"
	var="sign_up" />
<fmt:message bundle="${loc}" key="locale.registration.clear" var="clear" />
<fmt:message bundle="${loc}" key="locale.registration.cancel"
	var="cancel" />
<fmt:message bundle="${loc}" key="locale.registration.change"
	var="change" />
<c:set var="mode" value="${requestScope.mode}" />
<c:set var="account" value="${sessionScope.account}" />
<c:choose>
	<c:when test="${mode=='update'}">
		<title>${change }</title>
	</c:when>
	<c:otherwise>
		<title>${reg}</title>
	</c:otherwise>
</c:choose>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">
<link href="css/fileInput.css" rel="stylesheet">
<link href="css/bootstrap-select.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<%@include file="inc/top-menu.jsp"%>
		<div class="container col-md-9 col-lg-9 col-sm-9">
			<form id="reg" action="Controller" enctype="multipart/form-data" method="post"
				role="form">
				<c:choose>
					<c:when test="${mode=='update' }">
						<input type="hidden" name="command" value="update_account" />
					</c:when>
					<c:otherwise>
						<input type="hidden" name="command" value="registration" />
					</c:otherwise>
				</c:choose>

				<div class="form-group">
					<h2 class="form-signin-heading">${message}</h2>
				</div>
				<div class="form-group">
					<label for="login">${login}</label><span>*</span> <input id="login" type="text"
						class="form-control" name="login" placeholder="${login}"
						value="${account.login }" required>
				</div>
				
				<c:if test="${ mode !='update'}">
				
				<div class="form-group">
					<label for="pass">${password}</label><span>*</span> <input  type="password"
						class="form-control" id="pass" name="pass" placeholder="${password}" required>
				</div>
				<div class="form-group">
					<label for="pass">${password}</label><span>*</span> <input  type="password"
						class="form-control" id="re-pass" name="re-pass" placeholder="${password}" required>
				</div>
				
				</c:if>
				<div class="form-group">
					<label for="first-name">${fname }</label> <input type="text"
						class="form-control" name="first-name" placeholder="${fname }"
						value="${account.firstName }">
				</div>
				<div class="form-group">
					<label for="last-name">${lname }</label> <input type="text"
						class="form-control" name="last-name" placeholder="${lname }"
						value="${account.lastName }">
				</div>
				<div class="form-group">
					<label for="birthday">${birthday }</label> <input type="date"
						class="form-control" name="birthday" value="${account.birthDay }">
				</div>

				<c:set var="countryList"  value="${requestScope.countryList}"/>
				<fieldset class="form-group">
  					<label class="container" for="country">${locality }</label>
     					<select  class="selectpicker" id="country" name="country" multiple data-max-options="1">
    						<c:forEach items="${countryList}" var="countryItem" >
    						<option value="${countryItem.id }">${countryItem.name }</option>
    						</c:forEach>
    					 </select>
 				</fieldset> 

				<div class="form-group">
					<label for="email">Email</label> <input type="email"
						class="form-control" name="email" placeholder="email"
						value="${account.email }">
				</div>

				<div class="form-group">
					<label for="phone-number">${phone_number }</label> <input
						type="tel" class="form-control" name="phone-number"
						placeholder="${phone_number }" value="${account.phone }">

				</div>
<c:if test="${mode != 'update' }">
		<div class="form-group">
     		<label for="file" class="control-label">${choose_photo}</label>
			<input id="input" type="file" class="file-loading" name="avatar">
		</div>
</c:if>				
				<c:choose>
					<c:when test="${mode=='update'}">
						<button class="btn btn-danger" type="button"
											data-toggle="modal" data-target="#myModal">Обновить</button>
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
														<h4 class="modal-title" id="myModalLabel">Введите пароль</h4>
													</div>
														<div class="modal-body">
															<label for="pass">${password}</label><span>*</span> <input required type="password"
																class="form-control" name="pass" placeholder="${password}">
														</div>
													<div class="modal-footer">
																	
														<button type="submit" class="btn btn-primary">Обновить</button>
													<button type="button" class="btn btn-secondary"
															data-dismiss="modal">Закрыть</button>
													
													</div>
												</div>
											</div>
										</div> <!-- modal end -->
						
					</c:when>
					<c:otherwise>
						<button  type="submit" class="btn btn-success">${sign_up }</button>
					
					<!-- Modal -->
										<div class="modal fade" id="passRepeat" tabindex="-1"
											role="dialog" aria-labelledby="myModalLabel"
											aria-hidden="true">
											 <div class="modal-dialog" role="document">
												<div class="modal-content">
													<div class="modal-header">
														<h4 class="modal-title" id="myModalLabel">Предупреждение</h4>
													</div>
														<div class="modal-body">
															<h4 class="modal-body" id="myModalLabel">Пароль не совпадает</h4>
														</div>
													<div class="modal-footer">
																	
													<button type="button" class="btn btn-secondary"
															data-dismiss="modal">Закрыть</button>
													</div>
													</div>
												</div>
		
										</div> <!-- modal end -->
					
					</c:otherwise>
				</c:choose>
				<c:if test="${mode!='update'}">
				<button type="reset" class="btn btn-warning">${clear }</button>
				</c:if>
			</form>

		</div>
	</div>
	
	<%@include file="inc/footer.jsp"%>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="js/common.js"></script>
	<script src="js/fileInput.js"></script>
	<script src="js/bootstrap-select.js"></script>
<script>
$(document).on('ready', function() {
    $("#input").fileinput({
        browseClass: "btn btn-primary btn-block",
        showCaption: false,
        showRemove: false,
        showUpload: false
    });
});
</script>
</body>
</html>