<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Configure Theatre</title>
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
					<li ><a href="book.html">Book Tickets</a></li>
					<!-- drop down menu -->
					<li class="dropdown active"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Admin<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="paypal.html">Create PayPal Account</a></li>
							<li><a href="cancel.html">Cancel Seats</a></li>
							<li class="active"><a href="configure.html">Configure Theater</a></li>
						</ul></li>
				</ul>
			</div>

		</div>
	</nav>
	<div class="container">
	<div class="page-header active">
		<h3>
			<strong>Configure Theatre</strong>
		</h3></div>
		<form action="configure.html" method="post">
			<div class="form-group">
				<label for="screensL">Number of Screens</label> <input type="text"
					class="form-control mx-sm-3" name="screens" id="screens"
					aria-describedby="screenHelp" placeholder="Screens#"> <small
					id="screensHelp" class="form-text text-muted">We'll set
					those many screens for you</small>
			</div>
			<div class="form-group">
				<label for="rowsL">Number of rows</label> <input type="text"
					class="form-control mx-sm-3" id="rows" name="rows"
					placeholder="Rows#">
			</div>
			<div class="form-group">
				<label for="seatsL">Number of Seats</label> <input type="text"
					class="form-control mx-sm-3" id="seats" name="seats"
					placeholder="Seats#"> <small id="seatsHelp"
					class="form-text text-muted">seats per Each Row</small>
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