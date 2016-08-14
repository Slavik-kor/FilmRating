<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Film</title>
<c:set var="film" value="${requestScope.film}" />
<c:set var="prev_page" value="Controller?command=film_card&film=${film.id}" scope="session" />

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="locale.locbutton.name.ru"
	var="ru_button" />
<fmt:message bundle="${loc}" key="locale.locbutton.name.en"
	var="en_button" />
<fmt:message bundle="${loc}" key="locale.brand" var="brand" />
<fmt:message bundle="${loc}" key="locale.menu.top.reg" var="reg" />
<fmt:message bundle="${loc}" key="locale.menu.top.sign_in" var="signIn" />
<fmt:message bundle="${loc}" key="locale.menu.top.login" var="login" />
<fmt:message bundle="${loc}" key="locale.menu.top.password"
	var="password" />
<fmt:message bundle="${loc}" key="locale.menu.side.new" var="newFilm" />
<fmt:message bundle="${loc}" key="locale.menu.side.rate" var="rate" />
<fmt:message bundle="${loc}" key="locale.menu.side.genre" var="genre" />
<fmt:message bundle="${loc}" key="locale.menu.side.years" var="years" />

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">
</head>
<body>
	<%@include file="inc/top-menu.jsp"%>
	<%@include file="inc/left-menu.jsp"%>
	
<div class="container col-lg-9 col-md-9 col-sm-9">
     
        <div class="row">
            <div class="container col-lg-4 col-lg-offset-4"><h3><c:out value="${film.title}" /></h3></div>
            <div class="container col-lg-4 col-lg-offset-1">  
                <img src="${film.poster }" width="250" class="img-rounded" alt="постер">
            </div>
       <div class="container col-lg-6">
        <table class="table table-hover">
        <tbody>
            <tr>
                <th scope="row">Год</th>
                <td><fmt:formatDate value="${film.premierDate }" pattern="yyyy" /></td>
            </tr>
             <tr>
                <th scope="row">Страна</th>
                <td>
                <c:set var="countries" value="${requestScope.country_list}" />
                <c:forEach items="${countries}" var="country">
                <c:out value="${country.name} " />
                </c:forEach>
                </td>
            </tr>
             <tr>
                <th scope="row">Жанр</th>
                <td><a href="#">Боевик</a>,<a href="#">Триллер</a></td>
            </tr>
             <tr>
                <th scope="row">Бюджет</th>
                <td><fmt:formatNumber value="${film.budget}" type="currency" currencySymbol="$"/></td>
            </tr>
             <tr>
                <th scope="row">Кассовые сборы</th>
                <td><fmt:formatNumber value="${film.boxOfficeCash}" type="currency" currencySymbol="$"/></td>
            </tr>
             <tr>
                <th scope="row">Зрители</th>
                <td><fmt:formatNumber value="${film.audience }"/></td>
            </tr>
            <tr>
                <th scope="row">Премьера</th>
                <td><fmt:formatDate value="${film.premierDate }" /></td>
            </tr>
        </tbody>
           </table>
           <p>Режисеры</p>
       <table class="table table-hover">
       <tbody>
            <tr>
                <th scope="row">1</th>
                <td><a href="#"></a></td>
            </tr>
        </tbody>
           </table>
            <p>Сценаристы</p>
            <table class="table table-hover">
        <tbody>
            <tr>
                <th scope="row">1</th>
                <td><a href="#"></a></td>
            </tr>
            <tr>
                <th scope="row">2</th>
                <td><a href="#"></a></td>
            </tr>
        </tbody>
           </table>
            <p>Актеры</p>
            <table class="table table-hover">
        <tbody>
            <tr>
                <th scope="row">1</th>
                <td><a href="#"></a></td>
            </tr>
            <tr>
                <th scope="row">2</th>
                <td><a href="#"></a></td>
            </tr>
        </tbody>
           </table>
       </div>
       </div>
       </div>
	<%@include file="inc/footer.jsp"%>

	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>