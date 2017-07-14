<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Create PayPAL Account</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-default">
		<div class="container">
		<div class=" page-header">
					<h1 style="color: #293db2; font: x-large;">RT Theater</h1>
					<p>4425 W Airport Fwy, Irving, TX 75062</p>
				</div>
			<!-- Logo -->
			<div class="navbar-header">
				
				<a href="#" class="navbar-brand"
					style="color: #293db2; font: x-large;">RT Theater</a>
			</div>

			<div>
				<ul class="nav navbar-nav">
					<li ><a href="welcome.jsp">Home</a></li>
					<li class="active"><a href="book.html">Book Tickets</a></li>
					<!-- drop down menu -->
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Admin<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="paypal.html">Create PayPal Account</a></li>
							<li><a href="cancel.html">Cancel Seats</a></li>
							<li><a href="configure.html">Configure Theater</a></li>
						</ul></li>
				</ul>
			</div>

		</div>
	</nav>
	<div class="container">
		<h3>
			<strong>PayPal Payment Page</strong>
		</h3>
		<form action="processPaypal.html" method="post">
			<div class="form-group">
			<input type="hidden" name="screen"
					value=<%=request.getAttribute("screen")%>>
					<input type="hidden" name="reservation_id"
					value=<%=request.getAttribute("reservation_id")%>>
				<label for="userL">User Name</label> <input type="text"
					class="form-control mx-sm-3" name="userName" id="username"
					aria-describedby="username" placeholder="Enter Username">
			</div>
			<div class="form-group">
				<label for="passwordL">Password</label> <input type="password"
					class="form-control mx-sm-3" id="password" name="password"
					placeholder="Enter password"> <small
					id="passwordHelp" class="form-text text-muted">*Your password will be encrypted</small>
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>
		</form>
		<div id="error" class="alert alert-danger collapse">
			<p id="errorText"><%=request.getAttribute("error")%></p>
		</div>
		<div id="success" class="alert alert-success collapse">
			<p id="successText"><%=request.getAttribute("success")%></p>
		</div>
	</div>
	<script>
		if ("<%=request.getAttribute("error")%>"!=="null") {
			$('#error').show();
			$('#success').hide();
		} else if ("<%=request.getAttribute("success")%>" !== "null") {
			$('#success').show();
			$('#error').hide();
		} else {
			$('#success').hide();
			$('#error').hide();
		}
	</script>
</body>
</html>