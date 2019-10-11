<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<base href="http://localhost:8080/com.bachelor/">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<meta http-equiv="Content-Style-Type" content="text/css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Lato">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Montserrat">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-metro.css">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/home.css">

<!-- <script type="text/javascript" charset="ISO-8859-1" src="js/home.js"></script> -->

</head>
<title>User Profile</title>
<body class="w3-light-grey">
<div class="w3-container whole-page">
	<div class="w3-panel w3-metro-darken banner">
	<form action="./home"><button id="back-button" class="w3-large w3-button w3-hover-light-grey" style="position:relative;top:0.2px"><i class="material-icons" aria-hidden="true" style="position:relative;top:3px; right:2px;">arrow_back</i>back</button></form> 
		<!-- <div style="padding: 0 0 0 36px"><form action="./home"><button id="back-button" class="user-icon w3-left"><i class="material-icons" aria-hidden="true">arrow_back</i></button></form></div> -->
	</div>
	
	<div style="padding: 0 16px 0 16px">
	<div id="empty-profile"><h2 style="padding: 16px 0px 2px 24px;">Your Rating History is empty.</h2></div>	
	<div id="not-empty-profile">
	<h3 style="padding: 16px 0px 2px 24px;">Your Rating History</h3>
	<div class="w3-container w3-padding-large" style="width: 100%; position: relative; margin:auto">
		<table id="user-profile-feature-based" class="w3-table w3-white w3-bordered w3-centered w3-card" >
			<col width="25%">
			<col width="25%">
			<col width="24.5%">
			<col width="0.5%">
			<col width="25%">
			<tr class="w3-deep-orange" style="height: 56px;">
				<th style="vertical-align: middle">Feature</th>
				<th style="vertical-align: middle">Value</th>
				<th style="vertical-align: middle">Rating</th>
				<th></th>
				<th></th>
			</tr>
			<c:forEach items="${userprofile}" var="up">
				<c:choose>	
				<c:when test="${not empty up.value}">	
					<tr style="font-size:15px;">				
						<td style="vertical-align: middle">${up.name}</td>
						<td style="vertical-align: middle">${up.value}</td>

						<td style="vertical-align: middle">
							<div id="old${up.id}" style="font-size: 18px; text-shadow: 0 1px 1px rgba(0,0,0,.5);" class="stars w3-text-orange" data-rating="${up.rating}" data-num-stars="5"></div>
 							<form id="refresh-rating" action="./RefreshRatingServlet" method="post">
 							<input type="hidden" id="feature-to-delete" name="rating-to-refresh"value="${up.id}">
							<div id="new${up.id}" class="rating" style="left: 115px; display:none">
								<input type="radio" name="rating-value" id="5${up.id}" value="5" onClick="this.form.submit();"><label for="5${up.id}"></label>
								<input type="radio" name="rating-value" id="4${up.id}" value="4" onClick="this.form.submit();"><label for="4${up.id}"></label>
								<input type="radio" name="rating-value" id="3${up.id}" value="3" onClick="this.form.submit();"><label for="3${up.id}"></label>
								<input type="radio" name="rating-value" id="2${up.id}" value="2" onClick="this.form.submit();"><label for="2${up.id}"></label>
								<input type="radio" name="rating-value" id="1${up.id}" value="1" onClick="this.form.submit();"><label for="1${up.id}"></label>
							</div>
							</form>						
							<script>
							    $.fn.stars = function() {
							        return $(this).each(function() {
							            var rating = $(this).data("rating");
							            var numStars = $(this).data("numStars");
							            var fullStar = new Array(Math.floor(rating + 1)).join('<i class="fa fa-star"></i>');
							            var halfStar = ((rating%1) !== 0) ? '<i class="fa fa-star-half-empty"></i>': '';
							            var noStar = new Array(Math.floor(numStars + 1 - rating)).join('<i class="fa fa-star-o"></i>');
							            $(this).html(fullStar + halfStar + noStar);
							        });
							    }
							    $('.stars').stars();
			 				</script>
						</td>
						<td style="vertical-align: middle">
							<div class="tooltip">
								<button id ="btn${up.id}" class="w3-button w3-ripple w3-circle user-icon refresh-rating-up"><i class="material-icons" style="font-size: 24px">refresh</i></button>
								<span class="tooltiptext w3-round-small">Refresh this Rating</span>
							</div>
							<script>
								var id_btn = document.querySelector('[id^="btn"]').id ;
								var id_old = 'old' + ${up.id} ;
								var id_new = 'new' + ${up.id} ;
								$('button[id^="btn${up.id}"]').on('click',function(){
								   	$('div[id^="old${up.id}"]').toggle();
								   	$('div[id^="new${up.id}"]').toggle();
								});
							</script>						
						</td>
						<td>
							<form class="tooltip" action="./DeleteFromUserProfileServlet" method="post" >
								<input type="hidden" id="feature-to-delete" name="feature-to-delete"value="${up.id}">
								<button type="submit" class="w3-button w3-ripple w3-circle user-icon delete-rating-up"><i class="material-icons" style="">delete_outline</i></button>								
								<span class="tooltiptext w3-round-small">Remove this Feature</span>
							</form>
						</td>				
					</tr>			
				</c:when>
				
				
				<c:otherwise>
					<tr style="font-size:15px">
						<td style="vertical-align: middle">${up.name}</td>
						<td style="vertical-align: middle">${up.min} - ${up.max}</td>
						<td style="vertical-align: middle">
							<div id="old${up.id}" style="font-size: 18px; text-shadow: 0 1px 1px rgba(0,0,0,.5);" class="stars w3-text-orange" data-rating="${up.rating}" data-num-stars="5"></div>
 							<form id="refresh-rating" action="./RefreshRatingServlet" method="post">
 							<input type="hidden" id="feature-to-delete" name="rating-to-refresh"value="${up.id}">
							<div id="new${up.id}" class="rating" style="left: 115px; display:none">
								<input type="radio" name="rating-value" id="5${up.id}" value="5" onClick="this.form.submit();"><label for="5${up.id}"></label>
								<input type="radio" name="rating-value" id="4${up.id}" value="4" onClick="this.form.submit();"><label for="4${up.id}"></label>
								<input type="radio" name="rating-value" id="3${up.id}" value="3" onClick="this.form.submit();"><label for="3${up.id}"></label>
								<input type="radio" name="rating-value" id="2${up.id}" value="2" onClick="this.form.submit();"><label for="2${up.id}"></label>
								<input type="radio" name="rating-value" id="1${up.id}" value="1" onClick="this.form.submit();"><label for="1${up.id}"></label>
							</div>
							</form>								
							<script>
							    $.fn.stars = function() {
							        return $(this).each(function() {
							            var rating = $(this).data("rating");
							            var numStars = $(this).data("numStars");
							            var fullStar = new Array(Math.floor(rating + 1)).join('<i class="fa fa-star"></i>');
							            var halfStar = ((rating%1) !== 0) ? '<i class="fa fa-star-half-empty"></i>': '';
							            var noStar = new Array(Math.floor(numStars + 1 - rating)).join('<i class="fa fa-star-o"></i>');
							            $(this).html(fullStar + halfStar + noStar);
							        });
							    }
							    $('.stars').stars();
			 				</script>
						</td>
						<td style="vertical-align: middle">						
							<div class="tooltip">
								<button id ="btn${up.id}" class="w3-button w3-ripple w3-circle user-icon refresh-rating-up"><i class="material-icons" style="font-size: 24px">refresh</i></button>
								<span class="tooltiptext w3-round-small">Refresh this Rating</span>
							</div>					
							<script>
								var id_btn = ${up.id} ;
								var id_old = 'old' + ${up.id} ;
								var id_new = 'new' + ${up.id} ;
								$('button[id^="btn${up.id}"]').on('click',function(){
								   	$('div[id^="old${up.id}"]').toggle();
								   	$('div[id^="new${up.id}"]').toggle();
								});
							</script>						
						</td>
						<td>
							<form class="tooltip" action="./DeleteFromUserProfileServlet" method="post" >
								<input type="hidden" id="feature-to-delete" name="feature-to-delete"value="${up.id}">
								<button type="submit" class="w3-button w3-ripple w3-circle user-icon delete-rating-up"><i class="material-icons" style="">delete_outline</i></button>								
								<span class="tooltiptext w3-round-small">Remove this Feature</span>
							</form>
						</td>
					</tr>
				</c:otherwise>
				</c:choose>				
			</c:forEach>
		</table>
	</div>
	</div>
	<script>
		var profile_size = parseInt(${userprofileSize}, 10) ;
		if (profile_size == 0) {
			document.getElementById('empty-profile').style.display = "block";
			document.getElementById('not-empty-profile').style.display = "none";
		} else {
			document.getElementById('empty-profile').style.display = "none";
			document.getElementById('not-empty-profile').style.display = "block";
		}	
	</script>
	</div>
	
<%-- 	<div class="w3-row-padding">
		<c:forEach items="${userprofile}" var="up">
		<c:choose>	
			<c:when test="${not empty up.value}">
			<div class="w3-col" style="width:12.5%">
				<form action="./DeleteFromUserProfileServlet" method="post" >				
				<div class="w3-blue-grey w3-card-2 w3-center" style="padding: 8px; margin: 8px auto; position: relative;">
					<input type="hidden" id="feature-to-delete" name="feature-to-delete"value="${up.id}">
					<h6>${up.name}</h6>
					<h6>${up.value}</h6>
					<div class="stars" data-rating="${up.rating}" data-num-stars="5"></div>
					<script>
					    $.fn.stars = function() {
					        return $(this).each(function() {
					            var rating = $(this).data("rating");
					            var numStars = $(this).data("numStars");
					            var fullStar = new Array(Math.floor(rating + 1)).join('<i class="fa fa-star"></i>');
					            var halfStar = ((rating%1) !== 0) ? '<i class="fa fa-star-half-empty"></i>': '';
					            var noStar = new Array(Math.floor(numStars + 1 - rating)).join('<i class="fa fa-star-o"></i>');
					            $(this).html(fullStar + halfStar + noStar);
					        });
					    }
					    $('.stars').stars();
	 				</script>
					<button type="submit" class="close">
						<i class="material-icons" aria-hidden="true" style="font-size: 20px; color: #fff; text-shadow: 0 1px 1px rgba(0,0,0,.5);">clear</i>
					</button>
				</div>
				</form>
			</div>
			</c:when>		
		
			<c:otherwise>
			<div class="w3-col" style="width:12.5%">
				<form action="./DeleteFromUserProfileServlet" method="post" >
				<div class="w3-blue-grey w3-card-2 w3-center" style="padding: 8px; margin: 8px auto; position: relative;">
					<input type="hidden" id="feature-to-delete" name="feature-to-delete" value="${up.id}">
					<h6>${up.name}</h6>
					<h6>${up.min} - ${up.max}</h6>
					<div class="stars" data-rating="${up.rating}" data-num-stars="5"></div>
					<script>
					    $.fn.stars = function() {
					        return $(this).each(function() {
					            var rating = $(this).data("rating");
					            var numStars = $(this).data("numStars");
					            var fullStar = new Array(Math.floor(rating + 1)).join('<i class="fa fa-star"></i>');
					            var halfStar = ((rating%1) !== 0) ? '<i class="fa fa-star-half-empty"></i>': '';
					            var noStar = new Array(Math.floor(numStars + 1 - rating)).join('<i class="fa fa-star-o"></i>');
					            $(this).html(fullStar + halfStar + noStar);
					        });
					    }
					    $('.stars').stars();
	 				</script>	
					<button type="submit" class="close">
						<i class="material-icons" aria-hidden="true" style="font-size: 20px; color: #fff; text-shadow: 0 1px 1px rgba(0,0,0,.5);">clear</i>
					</button>		
				</div>
				</form>
			</div>
			</c:otherwise>

		</c:choose>
		</c:forEach>
	</div> --%>
</div>
</body>