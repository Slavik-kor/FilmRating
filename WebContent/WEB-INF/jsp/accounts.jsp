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
	<h2 class="form-signin-heading">Список аккаунтов:</h2>
	
	<div class="container col-lg-9 col-md-9 col-sm-9">
	<div class="row">
		<div class="span5">
            <table class="table table-striped table-condensed">
                  <thead>
                  <tr>
                      <th>Username</th>
                      <th>Date registered</th>
                      <th>Role</th>
                      <th>Status</th>                                          
                  </tr>
              </thead>   
              <tbody>
                <tr>
                    <td>Donna R. Folse</td>
                    <td>2012/05/06</td>
                    <td>Admin</td>
                    <td><span class="label label-success">Active</span>
                    </td>                                       
                </tr>
                <tr>
                    <td>Emily F. Burns</td>
                    <td>2011/12/01</td>
                    <td>User</td>
                    <td><span class="label label-danger">Banned</span></td>                                       
                </tr>
                <tr>
                    <td>Andrew A. Stout</td>
                    <td>2010/08/21</td>
                    <td>User</td>
                    <td><span class="label label-success">Active</span></td>                                        
                </tr>
                <tr>
                    <td>Mary M. Bryan</td>
                    <td>2009/04/11</td>
                    <td>User</td>
                    <td><span class="label label-success">Active</span></td>                                       
                </tr>
                <tr>
                    <td>Mary A. Lewis</td>
                    <td>2007/02/01</td>
                    <td>User</td>
                    <td><span class="label label-success">Active</span></td>                                        
                </tr>                                   
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

