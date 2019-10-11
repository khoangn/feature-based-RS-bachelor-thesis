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

<script>
	var item_rated_count =  "${item_rated_count}" ;
	var username = "${username}" ;
	
	//CLICK COUNTER
	var filter_clicks = 0;
	var feature_recommendation_clicks = 0;
	var item_recommendation_clicks = 0;
	
	function click_filter(){
		if (!localStorage.getItem('${username}-itemBased-filter_clicks')) {
			filter_clicks += 1;
			localStorage.setItem('${username}-itemBased-filter_clicks', filter_clicks);
		} else if (localStorage.getItem('${username}-itemBased-filter_clicks')) {
			filter_clicks = parseInt(localStorage.getItem('${username}-itemBased-filter_clicks'), 10);
			filter_clicks += 1;
			localStorage.setItem('${username}-itemBased-filter_clicks', filter_clicks);
		}
	}
	function click_feature_recommendation(){
		if (!localStorage.getItem('${username}-itemBased-feature_recommendation_clicks')) {
			feature_recommendation_clicks += 1;
			localStorage.setItem('${username}-itemBased-feature_recommendation_clicks', feature_recommendation_clicks);
		} else if (localStorage.getItem('${username}-itemBased-feature_recommendation_clicks')) {
			feature_recommendation_clicks = parseInt(localStorage.getItem('${username}-itemBased-feature_recommendation_clicks'), 10);
			feature_recommendation_clicks += 1;
			localStorage.setItem('${username}-itemBased-feature_recommendation_clicks', feature_recommendation_clicks);
		}
	}
	function click_item_recommendation(){
		if (!localStorage.getItem('${username}-itemBased-item_recommendation_clicks')) {
			item_recommendation_clicks += 1;
			localStorage.setItem('${username}-itemBased-item_recommendation_clicks', item_recommendation_clicks);
		} else if (localStorage.getItem('${username}-itemBased-item_recommendation_clicks')) {
			item_recommendation_clicks = parseInt(localStorage.getItem('${username}-itemBased-item_recommendation_clicks'), 10);
			item_recommendation_clicks += 1;
			localStorage.setItem('${username}-itemBased-item_recommendation_clicks', item_recommendation_clicks);
		}
	}
	
	//SAVE CLICK DATA
	function saveData(data, fileName) {
		var a = document.createElement('a');
		document.body.appendChild(a);
		a.style = 'display: none';

		var json = JSON.stringify(data),
		blob = new Blob([data], {type: 'text/plain;charset=utf-8'}),
		url = window.URL.createObjectURL(blob);
		a.href = url;
		a.download = fileName;
		a.click();
		window.URL.revokeObjectURL(url);
	}

	function main() {
		var logout = document.getElementById("logout-button");
		logout.addEventListener("click", saveFile, false);
	}

	//SAVE FILE 
	function saveFile(){
		var clicks = username + '\n' + 'filter clicks: ' + localStorage.getItem('${username}-itemBased-filter_clicks') + '\n' + 
					 'feature clicks: ' + localStorage.getItem('${username}-itemBased-feature_recommendation_clicks') + '\n' +
					 'item clicks: ' + localStorage.getItem('${username}-itemBased-item_recommendation_clicks');
		saveData(clicks, "${username}-itemBased-clicks-item-based.txt");
		localStorage.removeItem('${username}-itemBased-filter_clicks');
		localStorage.removeItem('${username}-itemBased-feature_recommendation_clicks');
		localStorage.removeItem('${username}-itemBased-item_recommendation_clicks');
	}

	window.onload = main;	
	
</script>

<script type="text/javascript" charset="ISO-8859-1" src="js/home2.js"></script>

<title>Home</title>

</head>

<body class="w3-light-grey">
<div class="w3-panel w3-metro-darken banner"> 
	<form action="./login"><button id="logout-button" type="submit" class="w3-large w3-button w3-right w3-hover-red" style="position:relative;top:0.2px"><i class="material-icons" aria-hidden="true" style="position:relative;top:3px; right:6px;">exit_to_app</i>log out</button></form>
	<form action="./shopping_basket_item_based" method="post"><button type="submit" class="w3-bar-item w3-button w3-large w3-right w3-hover-green" style="position:relative;top:0.2px"><i class="material-icons" aria-hidden="true" style="position:relative; top:3px; right:6px;">shopping_cart</i>shopping basket</button></form>
	<form action="./user_profile_item_based" method="post"><button type="submit" class="w3-bar-item w3-button w3-large w3-right w3-hover-light-grey" style="position:relative;top:0.2px"><i class="material-icons" aria-hidden="true" style="position:relative; top:3px; right:6px;">account_circle</i>user profile</button></form>
</div>

<div class="w3-container whole-page">
	<div id="item-to-rate" class="item-to-rate" style="display:none; font-size:13px; padding:40px 0 0 0">
		<h2 style="padding: 0 0 8px 8px;">Please rate at least 10 of the items below</h2>
		<div class="w3-row">
			<c:forEach items="${listItemToRate}" var="itemToRate">
				<div class="w3-col" style="width:16.6%; padding: 0px 8px">
					<div class="w3-card w3-center w3-white w3-display-container w3-round-small" style="padding: 8px; margin:8px 0px; height:200px; position:relative;">
						<form action="./GetItemProfileServlet" method="post" target="item-profile">
							<input type="hidden" name="item-id" value="${itemToRate.id}">
							<button type="submit"  onclick="document.getElementById('item-profile').style.display='block'; click_filter();" class="btn-link">
								<h6 class="w3-text-deep-orange" style="font-size: 14px"> ${itemToRate.item_name} </h6>
								<div class="item-image" style="position:absolute; margin: 0 auto; left: 0"><img src="${itemToRate.item_img}" alt="${itemToRate.item_name}" style="width:50%;"></div>
							</button>
							
						</form>
						<div id="item-profile" class="w3-modal w3-animate-opacity">
							<div class="w3-modal-content w3-display-middle" style="width:960px; overflow:hidden;">
								<div class="w3-container">	
									<button onclick="document.getElementById('item-profile').style.display='none'; click_filter();" class="close"><i class="material-icons" aria-hidden="true" style="font-size: 24px;">close</i></button>
									<iframe name="item-profile" style="border:none; height:480px; width:974px"></iframe>
								</div>
							</div>
						</div>
							
						<div class="item-star">
							<div id="old${itemToRate.id}" style="display: none; font-size: 24px; text-shadow: 0 1px 1px rgba(0,0,0,.5);" class="stars w3-text-orange" data-rating="${itemToRate.rating}" data-num-stars="5"></div>
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
							<form method="post" action="./RateItemServlet">
							<input type="hidden" name="selected-item" value="${itemToRate.id}">
							<div style="padding: 8px 0 0 48px" id="new${itemToRate.id}" class="item-rating">
								<input type="radio" name="rating-value" id="${itemToRate.id}5" value="5" onClick="this.form.submit(); click_filter();"><label for="${itemToRate.id}5"></label>
								<input type="radio" name="rating-value" id="${itemToRate.id}4" value="4" onClick="this.form.submit(); click_filter();"><label for="${itemToRate.id}4"></label>
								<input type="radio" name="rating-value" id="${itemToRate.id}3" value="3" onClick="this.form.submit(); click_filter();"><label for="${itemToRate.id}3"></label>
								<input type="radio" name="rating-value" id="${itemToRate.id}2" value="2" onClick="this.form.submit(); click_filter();"><label for="${itemToRate.id}2"></label>
								<input type="radio" name="rating-value" id="${itemToRate.id}1" value="1" onClick="this.form.submit(); click_filter();"><label for="${itemToRate.id}1"></label>
							</div>							
							</form>																					
						</div>
						<button onclick="click_filter();" id ="btn${itemToRate.id}" class="w3-button w3-ripple w3-circle user-icon refresh-rating"><i class="material-icons" style="font-size: 24px">refresh</i></button>
						<form method="post" action="./DeleteItemRatingServlet">
							<input type="hidden" name="item-to-delete" value="${itemToRate.id}">
							<button onclick="click_filter();" id ="delete${itemToRate.id}" class="w3-button w3-ripple w3-circle user-icon delete-rating"><i class="material-icons" style="font-size: 24px">clear</i></button>							
						</form>
							<script>
								$('button[id^="btn${itemToRate.id}"]').on('click',function(){
								   	$('div[id^="old${itemToRate.id}"]').toggle();
								   	$('div[id^="new${itemToRate.id}"]').toggle();
								});
								
								var this_rating = ${itemToRate.rating};
								var this_rating_number = parseInt(this_rating, 10);
								if (this_rating_number == 0) {
 									$('button[id^="btn${itemToRate.id}"]').hide();
									$('button[id^="delete${itemToRate.id}"]').hide(); 
									$('div[id^="old${itemToRate.id}"]').hide();
									$('div[id^="new${itemToRate.id}"]').show();
								} else {
 									$('button[id^="btn${itemToRate.id}"]').show();
									$('button[id^="delete${itemToRate.id}"]').show(); 
									$('div[id^="old${itemToRate.id}"]').show();
									$('div[id^="new${itemToRate.id}"]').hide();									
								}
							</script>													
					</div>
				</div>
			</c:forEach>
		</div>		
	</div>
	
	<button id="hide-item-to-rate" style="padding:8px 12px 8px 0; font-size:16px;" class="btn-link w3-text-deep-orange w3-right">Show less</button>
	<button id="rate-more" style="padding:64px 0 8px; font-size:16px" class="btn-link w3-text-deep-orange w3-right">Show more items to rate</button>
			<script>
				$('button[id^="hide-item-to-rate"]').click(function(){
					localStorage.removeItem('rate-more');
					$('#item-to-rate').hide();
					$('#hide-item-to-rate').hide();
					$('#rate-more').show();
				});
				$('button[id^="rate-more"]').click(function(){
					localStorage.setItem('rate-more', 'true');
					$('#item-to-rate').show();
					$('#hide-item-to-rate').show();
					$('#rate-more').hide();
				});
				if (localStorage.getItem('rate-more') == 'true') {
					$('#item-to-rate').show();
					$('#hide-item-to-rate').show();
					$('#rate-more').hide();
				} else {
					$('#item-to-rate').hide();
					$('#hide-item-to-rate').hide();
					$('#rate-more').show();
				}
			</script>
	
	<div id="recommended-item" style="padding:36px 0 8px 8px ;">
		<div style="padding: 0 0 24px">
			<h2>Item recommendations for you</h2>
			<form action="./WhyRecommendedServlet" method="post" target="why-recommended">		
				<input type="hidden" name="auid" value="${auid}">
				<button style="position:relative; bottom:10px; font-size: 16px" type="submit" onclick="document.getElementById('why-recommended').style.display='block'; click_item_recommendation();" class="btn-link w3-text-deep-orange"> Why recommended? </button>
			</form>
			<div id="why-recommended" class="w3-modal w3-animate-opacity">
				<div class="w3-modal-content w3-display-middle" style="height:610px; width:500px; overflow:hidden;">
					<div class="w3-container">
						<form action="./RefreshRecommendationServlet" method="post">	
							<button onclick="document.getElementById('why-recommended').style.display='none';" class="close"><i class="material-icons" aria-hidden="true" style="font-size: 24px;">close</i></button>
						</form>
						<iframe name="why-recommended" style="border:none; height:580px; width:500px"></iframe>
					</div>
				</div>
			</div>			
		</div>		
		<table id="recommended-item-table" class="w3-table w3-white w3-bordered w3-centered w3-card-4 w3-round-small">
			<col width="20%">
			<col width="15%">
			<col width="20%">
			<col width="10%">
			<col width="15%">
			<col width="10%">
			<col width="10%">
			<tr class="w3-white" style="height: 50px;">
				<th></th>
				<th style="vertical-align: middle"></th>
				<th style="vertical-align: middle">Product</th>
				<th style="vertical-align: middle">Price</th>
				<th style="vertical-align: middle">Pixel number</th>
				<th></th>
				<th></th>
			</tr>
			<c:forEach items="${listRecommendedItem}" var="listRecommendedItem">
				<tr style="font-size: 14px;">
					<td style="vertical-align: middle"><img src="${listRecommendedItem.img_src}" alt="(${listRecommendedItem.product_name})" style="width: 40%; font-size: 11px;"></td>
					<td style="vertical-align:middle;">
						<div class="tooltip">
							<span style="font-size:20px; text-shadow: 0 1px 1px rgba(0,0,0,.5);" class="stars w3-text-orange" data-rating="${listRecommendedItem.avg_rating}" data-num-stars="5"> </span>
							<span style="font-size:12px; padding: 0 0 0px 4px; color:gray"> ${listRecommendedItem.count_rating} </span>	
		
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
				 				
			 				<span class="tooltiptext w3-round-small w3-white" style="width:250px; border-color:black; border-style: solid; border-width:1px;">
								<span style="font-size:14px; text-shadow: 0 1px 1px rgba(0,0,0,.5);" class="stars w3-text-orange" data-rating="${listRecommendedItem.all_avg_rating.average_rating}" data-num-stars="5"> </span>
								<span style="font-size:8px; color:gray"> ${listRecommendedItem.all_avg_rating.total_count_rating} </span>
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
								<br>
								<span style="position:relative; bottom:4px; color:gray; font-size:10px;">${listRecommendedItem.all_avg_rating.average_rating} out of 5 stars</span>				
								<span style="width:400px; font-size:12px">									
								<c:forEach items="${listRecommendedItem.all_avg_rating.list_each_rating}" var="listEachRating">
									<div class="w3-row">
										<div class="w3-col" style="width:20%; text-align:left">
											<div>${listEachRating.rating} star</div>
										</div>
										<div class="w3-col" style="width:60%;position:relative; top:2px;">
											<div style="width:100%; background-color: #d4d4d4; text-align:center; color:white;">
												<div style="width:${listEachRating.count_rating_percent}%; height:12px; background-color: #ff9800;"></div>
											</div>
										</div>
										<div class="w3-col" style="width:20%; text-align:right">
											<div>${listEachRating.count_rating_percent}%</div>
										</div>
									</div>
								</c:forEach>
								</span>				 				
			 				</span>
			 			</div>

						<div id="average-rating" class="w3-modal w3-animate-opacity">
							<div class="w3-modal-content w3-display-middle" style="width:700px; overflow:hidden;">
								<div class="w3-container">	
									<button onclick="document.getElementById('average-rating').style.display='none';" class="close"><i class="material-icons" aria-hidden="true" style="font-size: 24px;">close</i></button>
									<iframe name="average-rating" style="border:none; height:300px; width:700px"></iframe>
								</div>
							</div>
						</div>
			 							 				
					</td>
					<td style="vertical-align: middle;">
						<form action="./GetItemProfileServlet" method="post" target="item-profile-table">
							<input type="hidden" name="item-id" value="${listRecommendedItem.id}">							
							<button type="submit" onclick="document.getElementById('item-profile-table').style.display='block'; click_filter();" class="btn-link">
								<h6 class="w3-text-black">${listRecommendedItem.product_name}</h6>								
							</button>
						</form>
						<div id="item-profile-table" class="w3-modal w3-animate-opacity">
							<div class="w3-modal-content w3-display-middle" style="width:960px; overflow:hidden;">
								<div class="w3-container">	
									<button onclick="document.getElementById('item-profile-table').style.display='none'; click_filter();" class="close"><i class="material-icons" aria-hidden="true" style="font-size: 24px;">close</i></button>
									<iframe name="item-profile-table" style="border:none; height:480px; width:974px"></iframe>
								</div>
							</div>
						</div>							
					</td>
					<td style="vertical-align: middle">${listRecommendedItem.price} EUR</td>
					<td style="vertical-align: middle">${listRecommendedItem.pixel_number} MP</td>

					<td style="vertical-align: middle">
						<form class="tooltip" action="./RemoveRecommendedItemServlet" method="post">
							<input name="item-id" type="hidden" value="${listRecommendedItem.id}">
							<button class="user-icon w3-text-dark-grey w3-button w3-circle delete-recommendation" type="submit"><i class="material-icons" style="font-size: 22px">delete_outline</i></button>
							<span class="tooltiptext w3-round-small">remove this recommendation</span>
						</form>
					</td>
					<td style="vertical-align: middle">
						<form class="tooltip" action="./AddToBasketItemBasedServlet" method="post">
							<input name="item-id" type="hidden" value="${listRecommendedItem.id}">
							<button id="add-cart-${listRecommendedItem.id}-itemBased" onclick="click_item_recommendation();" class="w3-button w3-ripple w3-circle user-icon delete-rating-up" type="submit">
								<i class="material-icons" style="color:#63b663 ;font-size: 22px">add_shopping_cart</i>
							</button>
							<span class="tooltiptext w3-round-small">Add to Basket</span>
						</form>
						<form class="tooltip" action="RemoveFromBasketItemBasedServlet" method="post">
							<input name="item-id" type="hidden" value="${listRecommendedItem.id}">
							<button id="remove-cart-${listRecommendedItem.id}-itemBased" onclick="click_item_recommendation();" style="display:none" class="w3-button w3-ripple w3-circle user-icon delete-rating-up" type="submit">
								<i class="material-icons" style="color:red; font-size: 22px">remove_shopping_cart</i>
							</button>
							<span class="tooltiptext w3-round-small">Remove from Basket</span>
						</form>
						<script>
							$('button[id^="add-cart-${listRecommendedItem.id}-itemBased"]').click(function(){
								localStorage.setItem('${auid}-cart-${listRecommendedItem.id}-itemBased', 'true');
							});	
						
							$('button[id^="remove-cart-${listRecommendedItem.id}-itemBased"]').click(function(){
								localStorage.removeItem('${auid}-cart-${listRecommendedItem.id}-itemBased');
							});
							
							if (localStorage.getItem('${auid}-cart-${listRecommendedItem.id}-itemBased') == 'true') {
								$('button[id^="add-cart-${listRecommendedItem.id}-itemBased"]').hide();
								$('button[id^="remove-cart-${listRecommendedItem.id}-itemBased"]').show();
							} else {
								$('button[id^="remove-cart-${listRecommendedItem.id}-itemBased"]').hide();
								$('button[id^="add-cart-${listRecommendedItem.id}-itemBased"]').show();
							}
							
						</script>
					</td>					
				</tr>
			</c:forEach>		
		</table>
	</div>
	<%-- <div id="top-rated" class="top-rated" style="font-size: 13px; padding: 8px 0 16px">
		<div style="padding: 0 0 8px 8px;"><h3>top rated cameras</h3></div>					
		<div class="w3-row">
			<c:forEach items="${listTopRatedCamera}" var="topRatedCamera">
				<div class="w3-col" style="width:16.66%; padding: 0px 8px">
					<div class="w3-card w3-center w3-white w3-display-container" style="padding: 8px; margin:8px 0px; height:240px; position:relative;">
						<form action="./GetItemProfileServlet" method="post" target="item-profile">
							<input type="hidden" name="item-id" value="${topRatedCamera.id}">
							<button type="submit" onclick="document.getElementById('item-profile').style.display='block'" class="btn-link"><h6 class="w3-text-deep-orange">${topRatedCamera.product_name}</h6></button>
						</form>
						<div id="item-profile" class="w3-modal w3-animate-opacity">
							<div class="w3-modal-content w3-display-middle" style="width:960px; overflow:hidden;">
								<div class="w3-container">	
									<button onclick="document.getElementById('item-profile').style.display='none'" class="close"><i class="material-icons" aria-hidden="true" style="font-size: 24px;">close</i></button>
									<iframe name="item-profile" style="border:none; height:480px; width:974px"></iframe>
								</div>
							</div>
						</div>
						<br>
						<div class="item-image"><img src="${topRatedCamera.img_src}" alt="${topRatedCamera.product_name}" style="width:65%;"></div>
						<div id="old${topRatedCamera.id}" style="font-size: 18px; text-shadow: 0 1px 1px rgba(0,0,0,.5);" class="stars w3-text-orange" data-rating="${topRatedCamera.rating}" data-num-stars="5"></div>
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
						<h6 class="w3-bottom" style="position:absolute;"><span class="w3-text-red">${topRatedCamera.rating}</span> / 5 </h6>
						<div class="w3-display-bottommiddle"><form method="post" action="./RateItemServlet">
							<input type="hidden" name="selected-item" value="${topRatedCamera.id}">
							<div class="item-rating">
								<input type="radio" name="rating-value" id="${topRatedCamera.id}5" value="5" onClick="this.form.submit();"><label for="${topRatedCamera.id}5"></label>
								<input type="radio" name="rating-value" id="${topRatedCamera.id}4" value="4" onClick="this.form.submit();"><label for="${topRatedCamera.id}4"></label>
								<input type="radio" name="rating-value" id="${topRatedCamera.id}3" value="3" onClick="this.form.submit();"><label for="${topRatedCamera.id}3"></label>
								<input type="radio" name="rating-value" id="${topRatedCamera.id}2" value="2" onClick="this.form.submit();"><label for="${topRatedCamera.id}2"></label>
								<input type="radio" name="rating-value" id="${topRatedCamera.id}1" value="1" onClick="this.form.submit();"><label for="${topRatedCamera.id}1"></label>
							</div>
						</form></div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>		 --%>
</div>

</body>
</html>