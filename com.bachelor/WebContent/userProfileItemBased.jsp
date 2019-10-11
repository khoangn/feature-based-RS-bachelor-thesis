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

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

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

<link rel="stylesheet" href="css/home2.css">


<script type="text/javascript" charset="ISO-8859-1" src="js/home2.js"></script>

<title>User Profile</title>

</head>
<body class="w3-light-grey">
<div class="w3-container whole-page">
	<div class="w3-panel w3-metro-darken banner">
		<form action="./home_item_based"><button id="back-button" class="w3-large w3-left w3-button w3-hover-light-grey" style="position:relative;top:0.2px"><i class="material-icons" aria-hidden="true" style="position:relative;top:3px; right:2px;">arrow_back</i>back</button></form>
	</div>
	
	<div id="rating-history" style="padding: 48px 8px 8px 8px;">
	<div id="empty-profile"><h2>Your Rating History is empty.</h2></div>
	<div id="not-empty-profile">	
		<h3>Your Rating History</h3>
		<table id="rating-history-table" class="w3-table w3-white w3-bordered w3-centered w3-card-4">
			<col width="25%">
			<col width="25%">
			<col width="35%">
			<col width="1%">
			<col width="14%">
			<tr class="w3-white" style="height: 50px;">
				<th></th>
				<th style="vertical-align: middle">Product</th>
				<th style="vertical-align: middle">Rating</th>
				<th style="vertical-align: middle"></th>
				<th style="vertical-align: middle"></th>
			</tr>
			<c:forEach items="${userprofile}" var="up">
				<tr style="font-size: 14px;">
					<td style="vertical-align: middle"><img src="${up.img_src}" alt="(${up.product_name})" style="width: 50%; font-size: 11px;"></td>
					<td style="vertical-align: middle"><h6>${up.product_name}</h6></td>
					<td style="vertical-align: middle">
							<div id="old${up.id}" style="font-size: 24px; text-shadow: 0 1px 1px rgba(0,0,0,.5);" class="stars w3-text-orange" data-rating="${up.rating}" data-num-stars="5"></div>
							<script>
							    $.fn.stars = function() {
							        return $(this).each(function() {
 							            var rating = $(this).data("rating");
							            var numStars = $(this).data("numStars");
							            var fullStar = new Array(Math.floor(rating) + 1).join('<i class="fa fa-star" style="padding: 1px"></i>');
							            var halfStar = ((rating%1) !== 0) ? '<i class="fa fa-star-half-empty" style="padding: 1px"></i>': '';
							            var noStar = new Array(Math.floor(numStars + 1 - rating)).join('<i class="fa fa-star-o" style="padding: 1px"></i>');
							            $(this).html(fullStar + halfStar + noStar);
							        });
							    }
							    $('.stars').stars();
			 				</script>						
							<form method="post" action="./RateItemUserProfileServlet">
							<input type="hidden" name="selected-item" value="${up.id}">
							<div id="new${up.id}" class="item-rating" style="display: none; right:96px">
								<input type="radio" name="rating-value" id="${up.id}5" value="5" onClick="this.form.submit();"><label for="${up.id}5"></label>
								<input type="radio" name="rating-value" id="${up.id}4" value="4" onClick="this.form.submit();"><label for="${up.id}4"></label>
								<input type="radio" name="rating-value" id="${up.id}3" value="3" onClick="this.form.submit();"><label for="${up.id}3"></label>
								<input type="radio" name="rating-value" id="${up.id}2" value="2" onClick="this.form.submit();"><label for="${up.id}2"></label>
								<input type="radio" name="rating-value" id="${up.id}1" value="1" onClick="this.form.submit();"><label for="${up.id}1"></label>
							</div>							
							</form>																					

					</td>
					<td style="vertical-align: middle">
						<button id ="btn${up.id}" class="w3-button w3-ripple w3-circle user-icon refresh-rating-up"><i class="material-icons" style="">refresh</i></button>
							<script>
								$('button[id^="btn${up.id}"]').on('click',function(){
								   	$('div[id^="old${up.id}"]').toggle();
								   	$('div[id^="new${up.id}"]').toggle();
								});
							</script>						
					</td>
					<td style="vertical-align: middle">
						<form method="post" action="./DeleteItemRatingUserProfileServlet">
							<input type="hidden" name="item-to-delete" value="${up.id}">
							<button id ="delete${up.id}" class="w3-button w3-ripple w3-circle user-icon delete-rating-up"><i class="material-icons" style="">delete_outline</i></button>							
						</form>
<%-- 						<form class="tooltip" action="./RemoveRecommendedItemServlet" method="post">
							<input name="item-id" type="hidden" value="${up.id}">
							<button class="user-icon w3-text-dark-grey w3-tiny" type="submit"><i class="material-icons" style="font-size: 22px">delete_outline</i></button>
							<span class="tooltiptext">remove this recommendation</span>
						</form> --%>
					</td>
				</tr>
			</c:forEach>
		</table>
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
</div>
</body>
</html>