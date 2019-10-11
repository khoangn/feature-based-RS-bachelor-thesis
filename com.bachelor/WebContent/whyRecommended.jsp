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
<div style="padding: 0 0 16px 0">
	<h3 style="padding: 0 0 16px 0">These items are recommended to you <br> <span style="font-size:20px"> because <span class="w3-text-deep-orange">you rated </span></span></h3>
	<table class="w3-table w3-white" style="position: absolute; right:16px; left:0">
		<col width="31%">
		<col width="31%">
		<col width="30%">
		<col width="8%">
		<c:forEach items="${listrateditem}" var="rateditem">
			<tr style="font-size: 14px; height: 90px;">
				<td style="vertical-align: middle;"><img src="${rateditem.img_src}" alt="(${rateditem.product_name})" style="width: 80%; font-size: 11px;"></td>
				<td style="vertical-align: middle">${rateditem.product_name}</td>
				<td style="vertical-align: middle;font-size:11px;">
					<form action="./DeleteFromWhyRecommendedServlet" method="post">
						<input type="hidden" value="${rateditem.id}" name="item-id">
						<input type="hidden" value="${auid}" name="active-user-id">
						<input style="vertical-align: middle;" type="checkbox" onClick="if(this.checked) this.form.submit();"> <label style="vertical-align: middle;">  I prefer not to use this for recommendations </label>
					</form>
<%-- 					<div style="font-size: 18px; text-shadow: 0 1px 1px rgba(0,0,0,.5);" class="stars w3-text-orange" data-rating="${rateditem.rating}" data-num-stars="5"></div>
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
	 				</script> --%>	
				</td>
				<td style="vertical-align: middle"></td>
			</tr>
		</c:forEach>
	</table>
</div>