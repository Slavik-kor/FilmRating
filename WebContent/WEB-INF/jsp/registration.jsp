<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="utf-8">
<title>РЕГИСТРАЦИЯ</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-theme.css" rel="stylesheet">

</head>
<body>
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
					<a class="navbar-brand" href="#">ÐÐÐÐÐ ÐÐÐ¢ÐÐÐ</a>
				</div>
				<div class="navbar-collapse collapse">
					<form action="" class="navbar-form navbar-right hidden-sm">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="ÐÐ¾Ð³Ð¸Ð½"
								value="">
						</div>
						<div class="form-group">
							<input type="password" class="form-control" placeholder="ÐÐ°ÑÐ¾Ð»Ñ"
								value="">
						</div>
						<button type="submit" class="btn btn-primary">ÐÐÐÐ¢Ð</button>
						
							<a href="#">ÐÐÐ ÐÐÐÐ¡Ð¢Ð ÐÐ ÐÐÐÐ¢Ð¬Ð¡Ð¯</a>
						
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="col-lg-3 col-md-3 col-sm-3 sidebar">
		<ul class="nav nav-sidebar ">
			<li class="active"><a href="index.html">ÐÐ¾Ð²Ð¸Ð½ÐºÐ¸</a></li>
			<li><a href="index.html">ÐÐ¾ ÑÐµÐ¹ÑÐ¸Ð½Ð³Ñ</a></li>
			<li><a href="index.html">ÐÐ¾ Ð¶Ð°Ð½ÑÑ</a></li>
			<li><a href="index.html">ÐÐ¾ Ð³Ð¾Ð´Ñ</a></li>
		</ul>
	</div>
	<div class="container col-md-9 col-lg-9 col-sm-9">

		<form role="form">
			<div class="form-group">
				<h2 class="form-signin-heading">ÐÐ°Ð¿Ð¾Ð»Ð½Ð¸ÑÐµ ÑÐ¾ÑÐ¼Ñ ÑÐµÐ³Ð¸ÑÑÑÐ°ÑÐ¸Ð¸:</h2>
			</div>

			<div class="form-group">
				<label for="text">ÐÐ¾Ð³Ð¸Ð½</label> <input type="text"
					class="form-control" id="login" placeholder="ÐÑÐ¸Ð´ÑÐ¼Ð°Ð¹ÑÐµ Ð»Ð¾Ð³Ð¸Ð½">
			</div>
			<div class="form-group">
				<label for="pass">ÐÐ°ÑÐ¾Ð»Ñ</label> <input type="password"
					class="form-control" id="pass" placeholder="ÐÐ°ÑÐ¾Ð»Ñ">
			</div>

			<div class="form-group">
				<label for="text">ÐÐ¼Ñ</label> <input type="text"
					class="form-control" id="first-name" placeholder="ÐÐ¼Ñ">
			</div>
			<div class="form-group">
				<label for="text">Ð¤Ð°Ð¼Ð¸Ð»Ð¸Ñ</label> <input type="text"
					class="form-control" id="last-name" placeholder="Ð¤Ð°Ð¼Ð¸Ð»Ð¸Ñ">
			</div>
			<div class="form-group">
				<label for="date">ÐÐ°ÑÐ° ÑÐ¾Ð¶Ð´ÐµÐ½Ð¸Ñ</label> <input type="date"
					class="form-control" id="birthday" placeholder="ÐÐ°ÑÐ° ÑÐ¾Ð¶Ð´ÐµÐ½Ð¸Ñ">
			</div>

			<div class="form-group">
				<label for="text">ÐÐ¾ÑÐ¾Ð´</label> <input type="text"
					class="form-control " id="city" placeholder="ÐÐ¾ÑÐ¾Ð´">

			</div>

			<div class="form-group">
				<label for="email">Email</label> <input type="email"
					class="form-control" id="email" placeholder="ÐÐ²ÐµÐ´Ð¸ÑÐµ email">
			</div>

			<div class="form-group">
				<label for="text">ÐÐ¾Ð¼ÐµÑ ÑÐµÐ»ÐµÑÐ¾Ð½Ð°</label> <input type="tel"
					class="form-control" id="phone-number" placeholder="ÐÐ¾Ð¼ÐµÑ ÑÐµÐ»ÐµÑÐ¾Ð½Ð°">

			</div>

			<div class="form-group">
				<label for="file" class="input input-file">ÐÑÐ±ÑÐ°ÑÑ ÑÐ¾ÑÐ¾
					<div class="button">
						<input type="file" id="file"
							onchange="this.parentNode.nextSibling.value = this.value">
					</div>
				</label>

			</div>
			<button type="submit" class="btn btn-success">ÐÐ°ÑÐµÐ³Ð¸ÑÑÑÐ¸ÑÐ¾Ð²Ð°ÑÑÑÑ</button>
			<button type="reset" class="btn btn-warning">ÐÑÐ¸ÑÑÐ¸ÑÑ</button>
			<button type="button" class="btn btn-danger">ÐÑÐ¼ÐµÐ½Ð°</button>
		</form>


		<div class="container">
			<div class="row">
				<hr>
				<div class="col-lg-12">
					<div class="col-sm-8">
						<a href="#" id="menu-toggle">Terms of Service</a> | <a href="#">Privacy</a>
					</div>
					<div class="col-sm-4">
						<p>Â© 2016 Training Epam. All rights reserved</p>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>

</body>
</html>