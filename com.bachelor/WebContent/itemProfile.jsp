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
<div class="w3-row" style="position:absolute; width: 974px;">
	<div class="w3-container w3-third">
		<img src="${img}" style="width:100%; padding:16px 0px">
	</div>
	
	<div class="w3-container w3-twothird">
		<h2>${name}</h2>
		<h3>Price: <span class="w3-text-red"> ${price} EUR</span></h3>
		<h5>Pixel number: ${pixel} MP <br>
		Zoom factor: ${zoom}x <br>
		Exposure mode: ${exposure}</h5>
		<button style="" class="btn-link w3-text-deep-orange show-more">
			<i class="material-icons" style="position: relative; top:4px;">arrow_drop_down</i>
			show more features
		</button>
			<script type="text/javascript">
				$('.show-more').on('click',function(){
				    if ($(this).find('i').text() == 'arrow_drop_down'){
				    	$(this).find('i').text('arrow_drop_up');
				    	$('#to-show').fadeIn(100);
				    } else {
				    	$(this).find('i').text('arrow_drop_down');
				    	$('#to-show').fadeOut(100);
				    }
				});
			</script>		
	</div>
	
	<div id="to-show" class="w3-row-padding" style="display:none; padding:8px">
		<c:forEach items="${listFeatureValue}" var="featureValue">
		<div class="w3-container w3-third w3-padding-small">
    		<i class="material-icons" style="font-size:8px; position: relative; bottom:3px">lens</i> ${featureValue}
  		</div>
  		</c:forEach>
	</div>
</div>

