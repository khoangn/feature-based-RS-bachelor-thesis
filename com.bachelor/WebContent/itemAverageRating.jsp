<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/home.css">
<script type="text/javascript" charset="utf-8" src="js/home.js"></script>

<div class="w3-row-padding" style="position:absolute;">
	<div class="w3-col" style="width:30%">
		<img src="${itemAverageRating.img_src} " style="width:150px; padding:16px 0px">
	</div>
	<div class="w3-col" style="width:70%">
		<h2>${itemAverageRating.product_name}</h2>
		
		<span style="font-size:24px; text-shadow: 0 1px 1px rgba(0,0,0,.5);" class="stars w3-text-orange" data-rating="${itemAverageRating.average_rating}" data-num-stars="5"> </span>
		<span style="font-size:14px; color:gray"> ${itemAverageRating.total_count_rating} </span>
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
		<div style="color:gray">${itemAverageRating.average_rating} out of 5 stars</div>
		<div style="padding:16px 16px 16px 0; width:400px">
		<c:forEach items="${itemAverageRating.list_each_rating}" var="listEachRating">
			<div class="w3-row">
				<div class="w3-col" style="width:20%;">
					<div>${listEachRating.rating} star</div>
				</div>
				<div class="w3-col" style="width:60%;">
					<div style="width:100%; background-color: #f1f1f1; text-align:center; color:white;">
						<div style="width:${listEachRating.count_rating_percent}%; height:14px; background-color: #ff9800;"></div>
					</div>
				</div>
				<div class="w3-col" style="width:20%; text-align:right">
					<div>${listEachRating.count_rating_percent}%</div>
				</div>
			</div>
		</c:forEach>
		</div>
		
	</div>
	
</div>