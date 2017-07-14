<%@page import="com.mani.theatre.beans.Screen"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Book Tickets</title>
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
	<div class="page-header">
	<h3>Select Screen</h3>
	</div>
		<%if(request.getAttribute("error") == null){ %>
		<form action="checkTickets.html" method="post">
			<div class="form-group">
				<label for="screensL">Screen#</label> <select
					class="custom-select mb-2 mr-sm-2 mb-sm-0"
					id="inlineFormCustomSelect" name="screen">
					<%List<Screen> screens=((List)request.getAttribute("screens")); 
						for(int i=0; i < screens.size();i++){ %>
					<option value=<%=screens.get(i).getId()%>><%=screens.get(i).getName()%></option>
					<%} %>
				</select>
			</div>
			<div class="form-group">
				<label for="rowsL">Number of Tickets</label> <input type="text"
					class="form-control mx-sm-3" id="tickets" name="tickets"
					placeholder="number of tickets">
			</div>


			<button type="submit" class="btn btn-primary">Submit</button>
		</form>
	</div>
	<%} %>
	<div id="error" class="alert alert-danger collapse">
			<p id="errorText"><%=request.getAttribute("error")%></p>
		</div>
	<script>
		if ("<%=request.getAttribute("error")%>"!=="null") {
			$('#error').show();
		} 
	</script>
</body>
</html>