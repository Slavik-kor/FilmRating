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

<div class="container-fluid">
	<div class="navbar navbar-inverse navbar-static-top" role="navigation">

		<div class="container-fluid">
			<div class="navbar-header">

				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.jsp">${brand}</a>
			</div>
			<div class="navbar-collapse collapse">
				<c:choose>
					<c:when test="${account.role=='User'}">
						<ul class="nav navbar-nav navbar-right">
							<li><a href="Controller?command=profile">${profile}</a></li>
							<li><a href="Controller?command=comment_list">${comment}</a></li>
							<li><a href="Controller?command=newFilms&value=10&type=favorite">${favor_film}</a></li>
							<li><a href="Controller?command=sign_out">${sign_out}</a></li>
						</ul>
					</c:when>
					<c:when test="${account.role=='Admin'}">
						<ul class="nav navbar-nav navbar-right">
							<li><a href="Controller?command=profile">${profile}</a></li>
							<li><a href="Controller?command=comment_list">${comment}</a></li>
							<li><a href="Controller?command=add_film">${add_film}</a></li>
							<li><a href="Controller?command=add_author">${add_author}</a></li>
							<li><a href="Controller?command=newFilms&value=10&type=favorite">${favor_film}</a></li>
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
							<a href="reg" type="button">${reg}</a>
						</form>
					</c:otherwise>
				</c:choose>

				<div class="container">
					<form action="Controller" method="post">
						<input type="hidden" name="locale" value="ru" /> <input
							type="hidden" name="command" value="localization" /> <input
							type="submit" value="${ru_button}" />
					</form>
					<form action="Controller" method="post">
						<input type="hidden" name="locale" value="en" /> <input
							type="hidden" name="command" value="localization" /> <input
							type="submit" value="${en_button}" />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="col-lg-3 col-md-3 col-sm-3 sidebar">
		<ul class="nav nav-sidebar ">
			<li><a href="Controller?command=newFilms&value=10">${newFilm}</a></li>
			<li><a href="Controller?command=newFilms&value=10">${rate}</a></li>
			<li><a href="Controller?command=newFilms&value=10">${genre}</a></li>
			<li><a href="Controller?command=newFilms&value=10">${years}</a></li>
		</ul>
	</div>