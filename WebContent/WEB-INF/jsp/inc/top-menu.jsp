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
						<a href="reg" type="button" >${reg}</a>
					</form>
					<div class="container">
					<form action="Controller" method="post">
						<input type="hidden" name="locale" value="ru" />
						<input type="hidden" name="command" value="localization" />
						<input type="submit" value="${ru_button}" />
					</form>
					<form action="Controller" method="post">
						<input type="hidden" name="locale" value="en" />
						<input type="hidden" name="command" value="localization" />
						<input type="submit" value="${en_button}" />
					</form>
					</div>
				</div>
			</div>
		</div>
	</div>
