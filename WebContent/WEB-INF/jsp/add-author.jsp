<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>Add author</title>
<c:set var="prev_page" value="Controller?command=add_author"
	scope="session" />

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">
<link href="css/bootstrap-select.css" rel="stylesheet">
<link href="css/fileInput.css" rel="stylesheet">
</head>
<body>

	<div class="container">
		<%@include file="inc/top-menu.jsp"%>
		<h2>Страница в разработке</h2>
		<div class="container col-lg-9 col-md-9 col-sm-9">
			<form action="Controller" enctype="multipart/form-data" method="post"
				role="form">
				<c:choose>
					<c:when test="${mode=='update' }">
						<input type="hidden" name="command" value="update_author" />
					</c:when>
					<c:otherwise>
						<input type="hidden" name="command" value="add_author" />
					</c:otherwise>
				</c:choose>
		<div class="form-group">
       	  	<h2 class="form-signin-heading">Заполните форму добавления автора:</h2>
     	</div>
     	<div class="form-group">
					<label for="first-name">Имя</label> <input type="text"
						class="form-control" name="first-name" placeholder="Имя"
						value="" required>
				</div>
				<div class="form-group">
					<label for="last-name">Фамилия</label> <input type="text"
						class="form-control" name="last-name" placeholder="Фамилия"
						value="" required>
				</div>
     	<fieldset class="form-group">
  					<label class="container" for="country">Страна рождения</label>
     					<select  class="selectpicker" id="country" name="country" multiple data-max-options="1">
    						<option value="">Беларусь</option>
    						<option value="">Россия</option>
    						<option value="">США</option>
    						<option value="">Канада</option>
    						<option value="">Франция</option>
    					 </select>
 				</fieldset> 
 				<div class="form-group">
					<label for="birthday">Дата рождения</label> <input type="date"
						class="form-control" name="birthday" value="Дата рождения">
				</div>
 				<c:if test="${mode != 'update' }">
		<div class="form-group">
     		<label for="file" class="control-label">Выбрать фото</label>
			<input id="input" type="file" class="file-loading" name="author">
		</div>
</c:if>				
 				 <button type="submit" class="btn btn-success">Добавить автора</button>
     <button type="reset" class="btn btn-warning">Очистить</button>
			</form>
		</div>
	</div>

	<%@include file="inc/footer.jsp"%>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-select.js"></script>
	<script src="js/fileInput.js"></script>
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