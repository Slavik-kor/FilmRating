<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<!DOCTYPE html>
<html>
<head>
<c:set var="prev_page"
	value="Controller?command=add_film_page" scope="session" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Add film</title>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="locale.addFilm.fasade" var="facade" />
<fmt:message bundle="${loc}" key="locale.addFilm.title_ru" var="title_ru" />
<fmt:message bundle="${loc}" key="locale.addFilm.title_en" var="title_en" />
<fmt:message bundle="${loc}" key="locale.addFilm.desc_ru" var="desc_ru" />
<fmt:message bundle="${loc}" key="locale.addFilm.desc_en" var="desc_en" />
<fmt:message bundle="${loc}" key="locale.addFilm.poster" var="poster" />
<fmt:message bundle="${loc}" key="locale.addFilm.add" var="add" />
<fmt:message bundle="${loc}" key="locale.film.genre" var="genre_loc" />
<fmt:message bundle="${loc}" key="locale.film.site" var="site" />
<fmt:message bundle="${loc}" key="locale.film.duration" var="duration" />
<fmt:message bundle="${loc}" key="locale.filmList.budget" var="budget" />
<fmt:message bundle="${loc}" key="locale.film.directors" var="directors" />
<fmt:message bundle="${loc}" key="locale.film.scenario" var="scens" />
<fmt:message bundle="${loc}" key="locale.film.actors" var="actors" />
<fmt:message bundle="${loc}" key="locale.film.country" var="country_loc" />
<fmt:message bundle="${loc}" key="locale.film.teaser" var="teaser" />
<fmt:message bundle="${loc}" key="locale.film.release" var="realise_loc" />
<fmt:message bundle="${loc}" key="locale.filmList.audience" var="audience" />
<fmt:message bundle="${loc}" key="locale.film.boxOffice" var="boxOffice" />
<fmt:message bundle="${loc}" key="locale.registration.clear" var="clear" />
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">
<link href="css/bootstrap-select.css" rel="stylesheet">
<link href="css/fileInput.css" rel="stylesheet">
 
</head>
<body>
<div class="container">
<%@include file="inc/top-menu.jsp"%>

<div class="container col-lg-9 col-md-9 col-sm-9">
 <form action="Controller" enctype="multipart/form-data" method="post" role="form">
     			<c:choose>
					<c:when test="${mode=='update' }">
						<input type="hidden" name="command" value="update_film" />
					</c:when>
					<c:otherwise>
						<input type="hidden" name="command" value="add_film" />
					</c:otherwise>
				</c:choose>
     <div class="form-group">
         <h2 class="form-signin-heading">${facade }</h2>
     </div>
     <div class="form-group col-lg-6 col-md-6 col-sm-6">
  <label for="text">${title_ru }</label><span>*</span>
  <input name="title_ru" type="text" class="form-control" placeholder="${title_ru }" required>
  </div>
   <div class="form-group col-lg-6 col-md-6 col-sm-6">
   <label for="text">${title_en }</label>
  <input name="title_en" type="text" class="form-control" placeholder="${title_en }" required>
     </div>
  <c:set var="genres" value="${requestScope.genreList }"/>
 <fieldset class="form-group col-lg-12 col-md-12 col-sm-12">
  <label class="container" for="genre">${genre_loc }</label>
     <select name="genre" id="genre" class="selectpicker show-tick " data-live-search="true" multiple>
    <c:forEach items="${genres}" var="genre" >
    	<option value="${genre.id }">${genre.name }</option>
    </c:forEach>
     </select>
 </fieldset> 
     
    <div class="form-group col-lg-6 col-md-6 col-sm-6">
  		<label for="textarea">${desc_ru }</label>
        <textarea name="description_ru" rows="3" class="form-control" style="resize:none" placeholder="${desc_ru }"></textarea>
     </div> 
 	<div class="form-group col-lg-6 col-md-6 col-sm-6">
  		<label for="textarea">${desc_en }</label>
        <textarea name="description_en" rows="3" class="form-control" style="resize:none" placeholder="${desc_en }"></textarea>
     </div> 
     <div class="form-group">
  <label for="text">${site }</label>
           <div class="input-group">
  <span class="input-group-addon glyphicon glyphicon-globe"></span>
   <input name="site" type="text" class="form-control" placeholder="${site }">
             </div>
  
 </div>
     
     <div class="form-group">
  <label for="number">${budget}</label>
         <div class="input-group">
  <span class="input-group-addon">$</span>
  <input name="budget" type="number" class="form-control" placeholder="${budget}">
             </div>
 </div>
   <c:set var="authors" value="${requestScope.authorList }"/>    
     <fieldset class="form-group col-lg-4 col-md-4">
  <label class="container" for="director">${directors }</label>
   <select name="directors" id="director" class="selectpicker show-tick form-group" data-live-search="true" multiple >
    <c:forEach items="${authors}" var="author" >
    	<option value="${author.id }">${author.firstName } ${ author.lastName}</option>
    </c:forEach>
     </select> 
 </fieldset> 
 
          <fieldset class="form-group col-lg-4 col-md-4">
  <label class="container" for="scenario">${scens }</label>
   <select name="scenarios" id="scenario" class="selectpicker show-tick form-group" data-live-search="true" multiple>
    <c:forEach items="${authors}" var="author" >
    	<option value="${author.id }">${author.firstName } ${ author.lastName}</option>
    </c:forEach>
     </select>
 </fieldset> 
           <fieldset class="form-group col-lg-4 col-md-4">
  <label class="container" for="actor">${actors }</label>
   <select name="actors" id="actor" class="selectpicker show-tick form-group" data-live-search="true" multiple>
    <c:forEach items="${authors}" var="author" >
    	<option value="${author.id }">${author.firstName } ${ author.lastName}</option>
    </c:forEach>
     </select>
 </fieldset> 
   
        <div class="form-group">
  <label for="time">${duration }</label>
             <div class="input-group">
  <span class="input-group-addon glyphicon glyphicon-time"></span>
   <input name="duration" type="time" class="form-control" placeholder="${duration }">
             </div>
      
 </div>
     
    <div class="form-group">
  <label for="date">${realise_loc }</label>
             <div class="input-group">
  <span class="input-group-addon glyphicon glyphicon-sunglasses"></span>
   <input name="release" type="date" class="form-control" placeholder="${realise_loc }">
 </div>
             </div>
       
   <c:set var="countries" value="${requestScope.countryList }"/>    
         <fieldset class="form-group ">
  <label class="container" for="country">${country_loc }</label>
   <select name="countries" id="country" class="selectpicker show-tick form-group" data-live-search="true" multiple>
     <c:forEach items="${countries}" var="country" >
    	<option value="${country.id }">${country.name }</option>
    </c:forEach>
     </select>
 </fieldset> 
        
     <div class="form-group">
      <label for="number">${audience }</label>
          <div class="input-group">
  <span class="input-group-addon glyphicon glyphicon-eye-open"></span>
  <input name="audience" type="number" class="form-control " placeholder="${audience }">
             </div>

      </div>
     
      <div class="form-group">
  <label for="number">${boxOffice }</label>
           <div class="input-group">
  <span class="input-group-addon">$</span>
  <input name="boxOffice" type="number" class="form-control" placeholder="${boxOffice }">
             </div>
 </div>

     <div class="form-group">
 			<label for="file" class="control-label">${poster }</label>
			<input id="input" type="file" class="file-loading" name="poster">
 </div>
     
     <button type="submit" class="btn btn-success">${add }</button>
     <button type="reset" class="btn btn-warning">${clear }</button>
</form>
</div>

</div>

<%@include file="inc/footer.jsp"%>

	
<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="js/bootstrap-select.js"></script>
	<script src="js/defaults-ru_RU.js"></script>
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