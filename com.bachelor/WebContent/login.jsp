<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/register.css">
<script src="js/validate.js"></script>

<title>Log in</title>
</head>
<body>
	<form class="register-box w3-container w3-display-middle w3-card w3-padding-large w3-round-small"
		method="post" name="registerform" onsubmit="return validate();" action="./login">
		<div class="w3-padding-large">
			<h1>Log in</h1>

			<h5> Username </h5>
			<h5> <input class="w3-input w3-border w3-round-small" type="text" name="username" id="username" placeholder="enter username"> </h5>
			
			<h5> Password </h5>
			<h5> <input class="w3-input w3-border w3-round-small" type="password" name="password" id="password" placeholder="enter password"> </h5>		
		
			<div style="padding: 32px 0"><h5>Not a user? <a class="italic" href="./"> <b> Register </b> </a>
				<button type="submit" class="w3-button w3-large w3-right w3-deep-orange w3-round-small">log in</button>
			</h5></div>
		</div>
	</form>
</body>
</html>