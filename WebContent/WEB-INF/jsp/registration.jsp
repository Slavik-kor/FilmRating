<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="utf-8">
<c:set var="prev_page"
	value="reg"
	scope="session" />
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="locale.menu.top.reg" var="reg" />
<fmt:message bundle="${loc}" key="locale.registration.message" var="message" />
<fmt:message bundle="${loc}" key="locale.registration.fname" var="fname" />
<fmt:message bundle="${loc}" key="locale.registration.lname" var="lname" />
<fmt:message bundle="${loc}" key="locale.registration.birthday" var="birthday" />
<fmt:message bundle="${loc}" key="locale.registration.locality" var="locality" />
<fmt:message bundle="${loc}" key="locale.registration.phone_number" var="phone_number" />
<fmt:message bundle="${loc}" key="locale.registration.choose_photo" var="choose_photo" />
<fmt:message bundle="${loc}" key="locale.registration.sign_up" var="sign_up" />
<fmt:message bundle="${loc}" key="locale.registration.clear" var="clear" />
<fmt:message bundle="${loc}" key="locale.registration.cancel" var="cancel" />
<title>${reg}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">

</head>
<body>
<div class="container">
	<%@include file="inc/top-menu.jsp"%>
	<div class="container col-md-9 col-lg-9 col-sm-9">
 <form action="Controller" enctype="multipart/form-data"  method="post" role="form">
 <input type="hidden" name="command" value="registration" />
     <div class="form-group">
         <h2 class="form-signin-heading">${message}</h2>
     </div>
     <div class="form-group">
  <label for="login" >${login}</label>
  <input type="text" class="form-control" name="login" placeholder="${login}">
 </div> 
 <div class="form-group">
  <label for="pass">${password}</label>
  <input type="password" class="form-control" name="pass" placeholder="${password}">
 </div>     
    <div class="form-group">
  <label for="first-name">${fname }</label>
  <input type="text" class="form-control" name="first-name" placeholder="${fname }">
 </div>  
     <div class="form-group">
  <label for="last-name">${lname }</label>
  <input type="text" class="form-control" name="last-name" placeholder="${lname }">
 </div> 
    <div class="form-group">
  <label for="birthday">${birthday }</label>
  <input type="date" class="form-control" name="birthday">
 </div>
        
        <div class="form-group">
      <label for="country">${locality }</label>
  <input type="text" class="form-control " name="country" placeholder="${locality }">
      </div>
        
 <div class="form-group">
  <label for="email">Email</label>
  <input type="email" class="form-control" name="email" placeholder="email">
 </div>
 
 <div class="form-group">
  <label for="phone-number">${phone_number }</label>
     <input type="tel" class="form-control" name="phone-number" placeholder="${phone_number }">
      
 </div>

     <div class="form-group">
         <label for="file" class="input input-file">${choose_photo}
         <input type="file" name="avatar" ></label>
         
         </div>
     <button type="submit" class="btn btn-success">${sign_up }</button>
     <button type="reset" class="btn btn-warning">${clear }</button>
     <button type="button" class="btn btn-danger">${cancel }</button>
    </form>
	
	</div>
	
    <%@include file="inc/footer.jsp"%>
</div>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>

</body>
</html>