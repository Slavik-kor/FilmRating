<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>Accounts</title>
<c:set var="prev_page"	value="Controller?command=account_list" scope="session" />

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">
</head>
<body>

<div class="container">
	<%@include file="inc/top-menu"%>
	<h2 class="form-signin-heading">List of accounts:</h2>
	<c:set var="accounts" value = "${requestScope.accountList }" />
	<div class="container col-lg-9 col-md-9 col-sm-9">
	<div class="row">
		<div class="span5">
            <table class="table table-striped table-condensed">
                  <thead>
                  <tr>
                      <th>Login</th>
                      <th>Date registered</th>
                      <th>Role</th>
                      <th>Status</th>                                          
                  </tr>
              </thead>   
              <tbody>
              	<c:forEach items="${accounts }" var="account">
               	 <tr>
                    <td>${account.login }</td>
                    <td>${account.creationDate }</td>
                    <td>${account.role }</td>
                    <c:choose>
                    	<c:when test="${account.active == true }">
                    	<td><span class="label label-success">Active</span></td> 
                    	</c:when>
                    	<c:when test="${account.active == false }">
                    	<td><span class="label label-danger">Banned</span></td> 
                    	</c:when>
                    </c:choose>
                                                          
              	  </tr>
                </c:forEach>
              </tbody>
            </table>
            </div>
	</div>
</div>

	
	
</div>
<%@include file="inc/footer"%>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>

