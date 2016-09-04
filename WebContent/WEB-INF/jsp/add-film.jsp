<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<!DOCTYPE html>
<html>
<head>
 
  <script src="js/bootstrap-select.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Добавить фильм</title>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc" />
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">

</head>
<body>
<%@include file="inc/top-menu.jsp"%>

<div class="container col-lg-9 col-md-9 col-sm-9">
 <form role="form">
     <div class="form-group">
         <h2 class="form-signin-heading">Заполните форму добавления фильма:</h2>
     </div>
     <div class="form-group">
  <label for="text">Название фильма</label>
  <input type="text" class="form-control" placeholder="Название фильма">
 </div> 
     
 <fieldset class="form-group">
  <label for="genre">Жанр</label>
     <select multiple class="form-control" id="genre">
     <option>Боевик</option>
     <option>Драма</option>
     <option>Комедия</option>
     <option>Триллер</option>
     <option>Спорт</option>
     </select>
 </fieldset> 
     
    <div class="form-group">
  <label for="textarea">Описание</label>
        <textarea rows="3" class="form-control" style="resize:none" placeholder="Описание фильма"></textarea>
        
 </div> 
     <div class="form-group">
  <label for="text">Официальный веб-сайт</label>
           <div class="input-group">
  <span class="input-group-addon glyphicon glyphicon-globe"></span>
   <input type="text" class="form-control" placeholder="Адрес официального веб-сайта">
             </div>
  
 </div>
     
     <div class="form-group">
  <label for="number">Бюджет</label>
         <div class="input-group">
  <span class="input-group-addon">$</span>
  <input type="number" class="form-control" placeholder="Бюджет фильма">
             </div>
 </div>
     
     <fieldset class="form-group">
  <label for="director">Режисеры</label>
   <select class="form-control" id="director" multiple >
     <option>Сильвестр Сталлоне</option>
     <option>Александр Котт</option>
     <option>Николай Лебедев</option>
     <option>Роман Прыгунов</option>
     <option>Андрей Курейчик</option>
     </select> 
    
 </fieldset> 
          <fieldset class="form-group">
  <label for="scenario">Сценаристы</label>
   <select multiple class="form-control" id="scenario">
     <option>Сильвестр Сталлоне</option>
     <option>Александр Котт</option>
     <option>Николай Лебедев</option>
     <option>Роман Прыгунов</option>
     <option>Андрей Курейчик</option>
     </select>
 </fieldset> 
           <fieldset class="form-group">
  <label for="actor">Актеры</label>
   <select multiple class="form-control" id="actor">
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
   <input type="time" class="form-control" placeholder="Длительность">
             </div>
      
 </div>
     
    <div class="form-group">
  <label for="date">Премьера</label>
             <div class="input-group">
  <span class="input-group-addon glyphicon glyphicon-sunglasses"></span>
   <input type="date" class="form-control" placeholder="Дата премьеры">
 </div>
             </div>
       
        
         <fieldset class="form-group">
  <label for="country">Страны</label>
   <select multiple class="form-control" id="country">
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
  <input type="number" class="form-control " placeholder="Количество зрителей">
             </div>

      </div>
     
      <div class="form-group">
  <label for="number">Кассовые сборы</label>
           <div class="input-group">
  <span class="input-group-addon">$</span>
  <input type="number" class="form-control" placeholder="Кассовые сборы">
             </div>
 </div>
     
     
 <div class="form-group">
  <label for="email">Email</label>
  <input type="email" class="form-control" id="email" placeholder="Введите email">
 </div>

     <div class="form-group">
  <label for="file">Загрузите постер фильма</label>
         <div class="input-group">
  <label class="file">
      <input type="file" id="file">
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

	

</body>
</html>