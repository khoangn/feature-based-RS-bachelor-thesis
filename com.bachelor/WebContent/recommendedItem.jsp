<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta charset="UTF-8">
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

<div class="rec-item-table">
<table id="recommended-item-table" class="w3-table w3-white w3-bordered w3-centered w3-card">
	<col width="250">
 		<col width="268">
 		<col width="175">
 		<col width="175">
 		<col width="200">
	<tr class="w3-white" style="height: 40px;">
		<th></th>
		<th style="vertical-align:middle">Product</th>
		<th style="vertical-align:middle">Price</th>
		<th style="vertical-align:middle">Pixel number</th>
		<th style="vertical-align:middle">Explanation</th>
	</tr>
	<c:forEach items="${listRecommendedItem}" var="listRecommendedItem">
	<tr>
		<td style="vertical-align:middle"><img src="${listRecommendedItem.img_src}" alt="(${listRecommendedItem.product_name})" style="width:65%; font-size:11px;"></td>	
		<td style="vertical-align:middle">${listRecommendedItem.product_name}</td>
		<td style="vertical-align:middle">${listRecommendedItem.price}</td>
		<td style="vertical-align:middle">${listRecommendedItem.pixel_number}</td>
		<td style="vertical-align:middle">
			<form action="./GetComparisonServlet" method="post">
				<input name="item-id" type="hidden" value="${listRecommendedItem.id}">
				<input name="active-user" type="hidden" value="${active-user-id}">
				<button type="submit" class="w3-button w3-green w3-small w3-card">explain recommendation</button>
			</form>
		</td>
	</tr>
	</c:forEach>
</table>
</div>

<div class="pro-table">
<table id="pro-item-table" class="w3-table w3-white w3-bordered w3-centered w3-card">
</table>
</div>

<div class="con-table">
<table id="con-item-table" class="w3-table w3-white w3-bordered w3-centered w3-card">
</table>
</div>
