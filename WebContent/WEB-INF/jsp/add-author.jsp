<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>Add author</title>
<c:set var="prev_page" value="Controller?command=add_author_page"
	scope="session" />

<fmt:setLocale value="${sessionScope.locale}" />
<c:set var="localeCode" value="${pageContext.response.locale}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="locale.addAuthor.title" var="facade" />
<fmt:message bundle="${loc}" key="locale.addAuthor.fName_ru" var="fname_ru" />
<fmt:message bundle="${loc}" key="locale.addAuthor.fName_en" var="fname_en" />
<fmt:message bundle="${loc}" key="locale.addAuthor.lName_ru" var="lName_ru" />
<fmt:message bundle="${loc}" key="locale.addAuthor.lName_en" var="lName_en" />
<fmt:message bundle="${loc}" key="locale.addAuthor.films" var="films_loc" />
<fmt:message bundle="${loc}" key="locale.addAuthor.asDir" var="asDir" />
<fmt:message bundle="${loc}" key="locale.addAuthor.asScen" var="asScen" />
<fmt:message bundle="${loc}" key="locale.addAuthor.asAct" var="asAct" />
<fmt:message bundle="${loc}" key="locale.addAuthor.photo" var="photo_loc" />
<fmt:message bundle="${loc}" key="locale.addAuthor.add" var="add" />
<fmt:message bundle="${loc}" key="locale.profile.country" var="country_loc" />
<fmt:message bundle="${loc}" key="locale.profile.birthday" var="birthday_loc" />
<fmt:message bundle="${loc}" key="locale.film.clear" var="clear" />

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
		<%@include file="inc/top-menu"%>
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
       	  	<h2 class="form-signin-heading">${facade }</h2>
     	</div>
     	<div class="form-group col-lg-6 col-md-6 col-sm-6">
					<label for="first-name">${fname_ru }</label><span>*</span><input type="text"
						class="form-control" name="first-name-ru" placeholder="${fname_ru }"
						value="" required>
				</div>
				
				<div class="form-group col-lg-6 col-md-6 col-sm-6">
					<label for="first-name">${fname_en}</label><span>*</span> <input type="text"
						class="form-control" name="first-name-en" placeholder="${fname_en}"
						value="" required>
				</div>
				
				<div class="form-group col-lg-6 col-md-6 col-sm-6">
					<label for="last-name">${lName_ru }</label><span>*</span> <input type="text"
						class="form-control" name="last-name-ru" placeholder="${lName_ru }"
						value="" required>
				</div>
				
				<div class="form-group col-lg-6 col-md-6 col-sm-6">
					<label for="last-name">${lName_en }</label><span>*</span> <input type="text"
						class="form-control" name="last-name-en" placeholder="${lName_en }"
						value="" required>
				</div>
				
     	<c:set var="countries" value="${requestScope.countryList }"/>    
         <fieldset class="form-group container">
  <label class="container" for="country">${country_loc }</label>
   <select name="countries" id="country" class="selectpicker show-tick form-group" data-live-search="true" multiple data-max-options="1">
     <c:forEach items="${countries}" var="country" >
    	<option value="${country.id }">${country.name }</option>
    </c:forEach>
     </select>
 </fieldset> 
 				<div class="form-group">
					<label for="birthday">${birthday_loc }</label> <input type="date"
						class="form-control" name="birthday" value="${birthday_loc }">
				</div>
	<c:set var="films" value="${requestScope.filmList }"/>    
     <fieldset class="form-group col-lg-4 col-md-4">
  <label class="container" for="director">${asDir }</label>
   <select name="dirFilms" class="selectpicker show-tick form-group" data-live-search="true" multiple >
    <c:forEach items="${films}" var="film" >
    	<option value="${film.id }">${film.title }</option>
    </c:forEach>
     </select> 
 </fieldset> 
 
          <fieldset class="form-group col-lg-4 col-md-4">
  <label class="container" for="scenario">${asScen }</label>
   <select name="scenFilms" class="selectpicker show-tick form-group" data-live-search="true" multiple >
    <c:forEach items="${films}" var="film" >
    	<option value="${film.id }">${film.title }</option>
    </c:forEach>
     </select> 
 </fieldset> 
           <fieldset class="form-group col-lg-4 col-md-4">
  <label class="container" for="actor">${asAct }</label>
  <select name="actFilms" class="selectpicker show-tick form-group" data-live-search="true" multiple >
    <c:forEach items="${films}" var="film" >
    	<option value="${film.id }">${film.title }</option>
    </c:forEach>
     </select> 
 </fieldset> 
 				<c:if test="${mode != 'update' }">
		<div class="form-group">
     		<label for="file" class="control-label">${photo_loc }</label>
			<input id="input" type="file" class="file-loading" name="authorPhoto">
		</div>
</c:if>				
 				 <button type="submit" class="btn btn-success">${add }</button>
     <button type="reset" class="btn btn-warning">${clear }</button>
			</form>
		</div>
	</div>

	<%@include file="inc/footer"%>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-select.js"></script>
	<script src="js/fileInput.js"></script>
	<c:choose>
		<c:when test="${localeCode =='ru' }">
			<script src="js/defaults-ru_RU.js"></script>
		</c:when>
		<c:when test="${localeCode =='en' }">
			<script src="js/defaults-en_US.js"></script>
		</c:when>
		<c:otherwise>
			<script src="js/defaults-ru_RU.js"></script>
		</c:otherwise>
	</c:choose>
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