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
<title>Shopping Basket</title>
<body class="w3-light-grey">
<div class="w3-container whole-page">
	<div class="w3-panel w3-metro-darken banner">
	<form action="./home_item_based"><button id="back-button" class="w3-large w3-button w3-hover-light-grey" style="position:relative;top:0.2px"><i class="material-icons" aria-hidden="true" style="position:relative;top:3px; right:2px;">arrow_back</i>back</button></form> 
		<!-- <div style="padding: 0 0 0 36px"><form action="./home"><button id="back-button" class="user-icon w3-left"><i class="material-icons" aria-hidden="true">arrow_back</i></button></form></div> -->
	</div>
	
	<div style="padding: 0 16px 0 16px">
		<div id="not-empty-basket"><h3 style="padding: 16px 0px 2px 24px;">Your Shopping Basket</h3></div>
		<div id="empty-basket" style="display:none"><h2 style="padding: 16px 0px 2px 24px;">Your Shopping Basket is empty.</h2></div>
			<script>
				var basket_size = parseInt(${basketSize}, 10) ;
				if (basket_size == 0) {
					document.getElementById('empty-basket').style.display = "block";
					document.getElementById('not-empty-basket').style.display = "none";
				} else {
					document.getElementById('empty-basket').style.display = "none";
					document.getElementById('not-empty-basket').style.display = "block";
				}	
			</script>
		<div class="w3-container w3-padding-large" style="width: 100%; position: relative; margin:auto">
		
		<table class="w3-table w3-white w3-bordered w3-centered w3-card" >
			<col width="10%">
			<col width="25%">
			<col width="15%">
			<col width="30%">
			<col width="20%">
			
			<c:forEach items="${shoppingBasketItemBased}" var="basket">
			<tr>
				<td style="vertical-align: middle;"><img src="${basket.img_src}" alt="(${basket.product_name})" style="width: 100%; font-size: 11px;"></td>
				<td style="vertical-align: middle; text-align:left">
					<h4> ${basket.product_name} </h4>
				</td>
				<td></td>
				<td style="vertical-align: middle; font-weight:bold;" class="w3-text-deep-orange"> ${basket.price} EUR </td>
				<td style="vertical-align: middle">
					<form class="tooltip" action="RemoveFromBasketItemBasedInBasketServlet" method="post">
						<input name="item-id" type="hidden" value="${basket.id}">
						<button id="remove-cart-${basket.id}-itemBased" class="w3-button w3-ripple w3-circle user-icon delete-rating-up" type="submit">
							<i class="material-icons" style="font-size: 22px">remove_shopping_cart</i>
						</button>
						<span class="tooltiptext w3-round-small">Remove from Basket</span>
					</form>
					<script>
						$('button[id^="remove-cart-${basket.id}-itemBased"]').click(function(){
							localStorage.removeItem('${auid}-cart-${basket.id}-itemBased');
						}); 
						
					</script>
				</td>
			</tr>								
			</c:forEach>			
		</table>
		</div>
	</div>	
</div>
</body>