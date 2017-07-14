<%@page import="java.util.ListIterator"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.mani.theatre.beans.Seat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Select Seats</title>
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
							<li class="active"><a href="cancel.html">Cancel Seats</a></li>
							<li><a href="configure.html">Configure Theater</a></li>
						</ul></li>
				</ul>
			</div>

		</div>
	</nav>
	<div class="container">
		<div class="page-header"><h3>
			<strong>Cancel Seat/s</strong>
		</h3></div>
		<div>
			<form action="cancelSeats.html" method="post">
				<input type="hidden" name="screen"
					value=<%=request.getAttribute("screen")%>>
				<div class="form-group">
					<div class="row">
						<div class="col-md-12 ">
							<%
								List<Seat> seats = (List<Seat>) request.getAttribute("seats");
								ListIterator<Seat> iterator = seats.listIterator();
								String seatRow = "" + seats.get(0).getId().charAt(0);
								while (iterator.hasNext()) {
									Seat seat = iterator.next();
									if (seat.getId().contains(seatRow)) {
										if (seat.isAvailable()) {
							%>
							<label class="form-check-label col-md-1"
								style="display: inline-block; padding: 5px; position: relative;"><input
								class="form-check-input" type="checkbox" name="seats" disabled
								value=<%=seat.getSeatId()%> style="width: 20px; height: 20px;" /><%=seat.getId()%></label>
							<%
								} else {
							%>
							<label class="form-check-label col-md-1"
								style="display: inline-block; padding: 5px; position: relative;"><input
								class="form-check-input" type="checkbox" name="seats"
								value=<%=seat.getSeatId()%> 
								style="width: 20px; height: 20px;"> <%=seat.getId()%></label>
							<%
								}
									} else {
										seatRow = "" + seat.getId().charAt(0);
							%>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 ">
							<%
								iterator.previous();
									}
								}
							%>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6 col-md-offset-1">
						<label class="form-check-label"> <input
							class="form-check-descript" type="checkbox" name="types" disabled
							style="width: 20px; height: 20px;"> UnAvailable
						</label> <label class="form-check-label"> <input
							class="form-check-descript" type="checkbox" name="types" checked
							style="width: 20px; height: 20px;"> Selected
						</label> <label class="form-check-label"> <input
							class="form-check-descript" type="checkbox" name="types"
							style="width: 20px; height: 20px;"> Available
						</label>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6 col-md-offset-3">
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</div>
			</form>
		</div>

		<div id="error" class="alert alert-danger collapse">
			<p id="errorText"><%=request.getAttribute("error")%></p>
		</div>
		<div id="success" class="alert alert-success collapse">
			<p id="successText"><%=request.getAttribute("success")%></p>
		</div>
	</div>
	<script>
	$('.form-check-descript').click(function(e) {
		e.preventDefault();
	});
	if ("<%=request.getAttribute("error")%>"!=="null") {
		$('#error').show();
		$('#success').hide();
		
	} else if ("<%=request.getAttribute("success")%>"!=="null") {
			$('#success').show();
			$('#error').hide();
		}
	</script>
</body>
</html>