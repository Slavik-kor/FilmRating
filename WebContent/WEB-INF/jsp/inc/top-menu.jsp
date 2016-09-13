<fmt:message bundle="${loc}" key="locale.locbutton.name.ru"	var="ru_button" />
<fmt:message bundle="${loc}" key="locale.locbutton.name.en"	var="en_button" />
<fmt:message bundle="${loc}" key="locale.brand" var="brand" />
<fmt:message bundle="${loc}" key="locale.menu.top.reg" var="reg" />
<fmt:message bundle="${loc}" key="locale.menu.top.sign_in" var="signIn" />
<fmt:message bundle="${loc}" key="locale.menu.top.login" var="login" />
<fmt:message bundle="${loc}" key="locale.menu.top.password"	var="password" />
<fmt:message bundle="${loc}" key="locale.menu.top.profile" var="profile" />
<fmt:message bundle="${loc}" key="locale.menu.top.comment" var="comment" />
<fmt:message bundle="${loc}" key="locale.menu.top.add_film" var="add_film" />
<fmt:message bundle="${loc}" key="locale.menu.top.add_author" var="add_author" />
<fmt:message bundle="${loc}" key="locale.menu.top.favor_film" var="favor_film" />
<fmt:message bundle="${loc}" key="locale.menu.top.sign_out" var="sign_out" />	
	
<fmt:message bundle="${loc}" key="locale.menu.side.new" var="newFilm" />
<fmt:message bundle="${loc}" key="locale.menu.side.rate" var="rate" />
<fmt:message bundle="${loc}" key="locale.menu.side.genre" var="genre" />
<fmt:message bundle="${loc}" key="locale.menu.side.years" var="years" />
<fmt:message bundle="${loc}" key="locale.menu.side.drama" var="drama" />
<fmt:message bundle="${loc}" key="locale.menu.side.comedy" var="comedy" />
<fmt:message bundle="${loc}" key="locale.menu.side.sport" var="sport" />
<fmt:message bundle="${loc}" key="locale.menu.side.biography" var="biography" />
<fmt:message bundle="${loc}" key="locale.menu.side.action" var="action" />
<fmt:message bundle="${loc}" key="locale.menu.side.thriller" var="thriller" />
<fmt:message bundle="${loc}" key="locale.menu.side.adventure" var="adventure" />
<fmt:message bundle="${loc}" key="locale.menu.side.fantasy" var="fantasy" />


<link href="css/accordeon.css" rel="stylesheet">


<div class="container-fluid">
	<div class="navbar navbar-inverse navbar-static-top" role="navigation">

		<div class="container-fluid">
			<div class="navbar-header">

				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
			<!-- 		<span class="sr-only">Toggle navigation</span>  -->
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.jsp">${brand}</a>
			</div>
			<div class="navbar-collapse collapse">
				<c:choose>
					<c:when test="${account.role=='User'}">
						<ul class="nav navbar-nav navbar-right">
							<li><a href="Controller?command=profile">${profile}</a></li>
							<li><a href="Controller?command=comment_list">${comment}</a></li>
							<li><a href="Controller?command=newFilms&type=favorite">${favor_film}</a></li>
							<li><a href="Controller?command=sign_out">${sign_out}</a></li>
						</ul>
					</c:when>
					<c:when test="${account.role=='Admin'}">
						<ul class="nav navbar-nav navbar-right">
							<li><a href="Controller?command=profile">${profile}</a></li>
							<li><a href="Controller?command=comment_list">${comment}</a></li>
							<li><a href="Controller?command=add_film">${add_film}</a></li>
							<li><a href="Controller?command=add_author">${add_author}</a></li>
							<li><a href="Controller?command=newFilms&type=favorite">${favor_film}</a></li>
							<li><a href="Controller?command=account_list">Accounts</a></li>
							<li><a href="Controller?command=sign_out">${sign_out}</a></li>
						</ul>
					</c:when>
					<c:otherwise>
						<form action="Controller"
							class="navbar-form navbar-right hidden-sm" method="post">
							<input type="hidden" name="command" value="authorization" />
							<div class="form-group">
								<input type="text" name="login" class="form-control"
									placeholder="${login}" value="">
							</div>
							<div class="form-group">
								<input type="password" name="password" class="form-control"
									placeholder="${password}" value="">
							</div>
							<button type="submit" class="btn btn-primary">${signIn}</button>
							<a href="Controller?command=reg_page" type="button">${reg}</a>
						</form>
					</c:otherwise>
				</c:choose>

				<div class="container">
				<!-- 	<form action="Controller" method="post">
						<input type="hidden" name="locale" value="ru" /> <input
							type="hidden" name="command" value="localization" /> <input
							type="submit" value="${ru_button}" />
					</form>
					<form action="Controller" method="post">
						<input type="hidden" name="locale" value="en" /> <input
							type="hidden" name="command" value="localization" /> <input
							type="submit" value="${en_button}" />
					</form>    -->
					<a href="Controller?command=Localization&locale=ru"><img alt="" src="images/RU.png" width="45"></a>
					<a href="Controller?command=Localization&locale=en"><img alt="" src="images/GB.png" width="45"></a>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="col-lg-2 col-md-2 col-sm-3 sidebar">
		<!--  <ul class="nav nav-sidebar ">
			<li><a href="Controller?command=newFilms&value=10">${newFilm}</a></li>
			<li><a href="Controller?command=newFilms&value=10">${rate}</a></li>
			<li><a href="Controller?command=newFilms&value=10">${genre}</a></li>
			<li><a href="Controller?command=newFilms&value=10">${years}</a></li>
		</ul>  -->
		<div class="panel-group nav nav-sidebar" id="accordion">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-parent="#accordion" href="Controller?command=newFilms"><span class="glyphicon glyphicon-download-alt">
                            </span>${newFilm }</a>
                        </h4>
                    </div>
                   
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a  data-parent="#accordion" href="Controller?command=newFilms"><span class="glyphicon glyphicon-thumbs-up">
                            </span>${rate }</a>
                        </h4>
                    </div>
                
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree"><span class="glyphicon glyphicon-film">
                            </span>${genre }</a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table">
                                <tr>
                                    <td>
                                        <a href="Controller?command=films_By_Genre&idGenre=1">${drama }</a> 
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="Controller?command=films_By_Genre&idGenre=8">${comedy}</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="Controller?command=films_By_Genre&idGenre=2">${sport}</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="Controller?command=films_By_Genre&idGenre=3">${biography}</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="Controller?command=films_By_Genre&idGenre=4">${action}</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="Controller?command=films_By_Genre&idGenre=5">${thriller }</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="Controller?command=films_By_Genre&idGenre=6">${adventure }</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="Controller?command=films_By_Genre&idGenre=7">${fantasy }</a>
                                    </td>
                                </tr>
                                
                            </table>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour"><span class="glyphicon glyphicon-calendar">
                            </span>${years }</a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table">
                                <tr>
                                    <td>
                                        <a href="#">2010</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="#">2011</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="#">2012</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="#">2013</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                </div>
            </div>
        </div>
	</div>
	
	<div class="container col-md-10 col-lg-10 col-sm-9">
	<ol class="breadcrumb">
 		 <li><a href="#">Home</a></li>
 		 <li><a href="#">Library</a></li>
  			<li class="active">Data</li>
   </ol>
   </div>