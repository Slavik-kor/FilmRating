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
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">
<link href="css/bootstrap-select.css" rel="stylesheet">

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
         <h2 class="form-signin-heading">Заполните форму добавления фильма:</h2>
     </div>
     <div class="form-group col-lg-6 col-md-6 col-sm-6">
  <label for="text">Название фильма на русском</label>
  <input name="title_ru" type="text" class="form-control" placeholder="Название фильма">
  </div>
   <div class="form-group col-lg-6 col-md-6 col-sm-6">
   <label for="text">Название фильма на английском</label>
  <input name="title_en" type="text" class="form-control" placeholder="Название фильма">
     </div>
 
  
 <fieldset class="form-group col-lg-12 col-md-12 col-sm-12">
  <label class="container" for="genre">Жанр</label>
     <select name="genre" id="genre" class="selectpicker show-tick " data-live-search="true" multiple>
     <option value="1" >Боевик</option>
     <option value="2">Драма</option>
     <option value="3">Комедия</option>
     <option value="4">Триллер</option>
     <option value="5">Спорт</option>
     </select>
 </fieldset> 
     
    <div class="form-group col-lg-6 col-md-6 col-sm-6">
  		<label for="textarea">Описание на русском</label>
        <textarea name="description_ru" rows="3" class="form-control" style="resize:none" placeholder="Описание фильма"></textarea>
     </div> 
 	<div class="form-group col-lg-6 col-md-6 col-sm-6">
  		<label for="textarea">Описание на английском</label>
        <textarea name="description_en" rows="3" class="form-control" style="resize:none" placeholder="Описание фильма"></textarea>
     </div> 
     <div class="form-group">
  <label for="text">Официальный веб-сайт</label>
           <div class="input-group">
  <span class="input-group-addon glyphicon glyphicon-globe"></span>
   <input name="site" type="text" class="form-control" placeholder="Адрес официального веб-сайта">
             </div>
  
 </div>
     
     <div class="form-group">
  <label for="number">Бюджет</label>
         <div class="input-group">
  <span class="input-group-addon">$</span>
  <input name="budget" type="number" class="form-control" placeholder="Бюджет фильма">
             </div>
 </div>
     
     <fieldset class="form-group col-lg-4 col-md-4">
  <label class="container" for="director">Режисеры</label>
   <select name="directors" id="director" class="selectpicker show-tick form-group" data-live-search="true" multiple >
     <option>Сильвестр Сталлоне</option>
     <option>Александр Котт</option>
     <option>Николай Лебедев</option>
     <option>Роман Прыгунов</option>
     <option>Андрей Курейчик</option>
     </select> 
 </fieldset> 
 
          <fieldset class="form-group col-lg-4 col-md-4">
  <label class="container" for="scenario">Сценаристы</label>
   <select name="scenarios" id="scenario" class="selectpicker show-tick form-group" data-live-search="true" multiple>
     <option>Сильвестр Сталлоне</option>
     <option>Александр Котт</option>
     <option>Николай Лебедев</option>
     <option>Роман Прыгунов</option>
     <option>Андрей Курейчик</option>
     </select>
 </fieldset> 
           <fieldset class="form-group col-lg-4 col-md-4">
  <label class="container" for="actor">Актеры</label>
   <select id="actor" class="selectpicker show-tick form-group" data-live-search="true" multiple>
     <option>Сильвестр Сталлоне</option>
     <option>Александр Котт</option>
     <option>Николай Лебедев</option>
     <option>Роман Прыгунов</option>
     <option>Андрей Курейчик</option>
     </select>
 </fieldset> 
   
        <div class="form-group">
  <label for="time">Длительность</label>
             <div class="input-group">
  <span class="input-group-addon glyphicon glyphicon-time"></span>
   <input name="duration" type="time" class="form-control" placeholder="Длительность">
             </div>
      
 </div>
     
    <div class="form-group">
  <label for="date">Премьера</label>
             <div class="input-group">
  <span class="input-group-addon glyphicon glyphicon-sunglasses"></span>
   <input name="release" type="date" class="form-control" placeholder="Дата премьеры">
 </div>
             </div>
       
        
         <fieldset class="form-group ">
  <label class="container" for="country">Страны</label>
   <select name="countries" id="country" class="selectpicker show-tick form-group" data-live-search="true" multiple>
     <option>США</option>
     <option>Канада</option>
     <option>Россия</option>
     <option>Беларусь</option>
     <option>Франция</option>
     </select>
 </fieldset> 
        
     <div class="form-group">
      <label for="number">Зрители</label>
          <div class="input-group">
  <span class="input-group-addon glyphicon glyphicon-eye-open"></span>
  <input name="audience" type="number" class="form-control " placeholder="Количество зрителей">
             </div>

      </div>
     
      <div class="form-group">
  <label for="number">Кассовые сборы</label>
           <div class="input-group">
  <span class="input-group-addon">$</span>
  <input name="boxOffice" type="number" class="form-control" placeholder="Кассовые сборы">
             </div>
 </div>

     <div class="form-group">
  <label for="file">Загрузите постер фильма</label>
         <div class="input-group">
  <label class="file">
      <input name="poster" type="file" id="file">
      <span class="file-custom"></span>
         </label>
             </div>
 </div>
     
     
     <button type="submit" class="btn btn-success">Добавить фильм</button>
     <button type="reset" class="btn btn-warning">Очистить</button>
     <button type="button" class="btn btn-danger">Отмена</button>
</form>
</div>



<%@include file="inc/footer.jsp"%>
</div>
	
<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="js/bootstrap-select.js"></script>
	<script src="js/defaults-ru_RU.js"></script>

</body>
</html>