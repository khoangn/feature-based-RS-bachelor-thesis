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

<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-camo.css">

<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/home.css">

<style>
#overlay {
    position: fixed;
    left: 0;
    top: 0;
    bottom: 0;
    right: 0;
    background: #000;
    opacity: 0.8;
    z-index: 20;
    width: 950px;
}

#loading {
    width: 80px;
    height: 80px;
    position: fixed;
    top: 50%;
    left: 50%;
    margin: -50px 0 0 -50px;
}
</style>

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script>
	var feature =  ${feature} ;
	var active_user_rating =  ${rating} ;
	var avg_rating_user_u =  ${avgrating} ;	
</script>

</head>
<body>
	<div style="padding:0 48px 0 32px;">
		<h5>These 20 users are most similar to you because they have similar preferences to you.</h5>
		<table class="w3-table w3-white w3-bordered w3-centered w3-card w3-round-small">
			<col width="30%">
			<col width="30%">
			<col width="40%">
			<tr class="w3-deep-orange" style="height:40px;">
				<th style="vertical-align: middle">Feature</th>
				<th style="vertical-align: middle">Your Choice</th>
				<th style="vertical-align: middle">Selections of your similar users</th>
			</tr>
			<c:forEach items="${listSimilarUserCompare}" var="listCompare">
				<tr style="font-size: 14px;">
					<td style="vertical-align: middle">${listCompare.feature}</td>
					<td style="vertical-align: middle"><b>${listCompare.your_choice}</b></td>
					<td style="vertical-align: middle"><b>${listCompare.similar_user_count}</b> out of ${listCompare.similar_user_count_all} selections</td>
				</tr>
			</c:forEach>
		</table>
		<h5>You can click on each of these features you selected for further information.</h5>
		
		<c:forEach items="${listselected}" var="selected">
			<div class="w3-show-inline-block">
			<form action="./GetChartFeatureValueSimilarUserServlet" method="post" target="frame-chart2" >
				<input type="hidden" name="selected-feat-id" value="${selected.id}">
				<input type="hidden" name="selected-feat-name" value="${selected.name}">
		 		<div class="w3-bar">
		 			<button class="selected-feature w3-button w3-small w3-deep-orange w3-round-small" type="submit">${selected.name}</button>
		 		</div>
			</form>
			</div>	
		</c:forEach>		
	</div>
	
	<div class="container" id="chart2" style="display:none; position:absolute; margin:0 auto; left:0">
		<iframe scrolling="no" name="frame-chart2" style="border:none; height:450px; width:950px;"></iframe>
	</div>
		<script>
			$(function(){
			    $('body').on('click', '.selected-feature', function() {
			        $('body').append(
			            '<div id="overlay">' +
			            '<img id="loading" src="css/loading.gif">' +
			            '</div>'
			        );	        
			        setTimeout(function(){
			          $('#overlay').remove();
			          $('#chart2').show();
			        }, 2000); // 3 seconds
			    });
			})
		</script>
		<%-- <div style="display:table; margin: auto;"><button class="show-similar-users btn-link w3-text-deep-orange w3-large"><i class="material-icons" style="position: relative; top:4px;">arrow_drop_down</i> your most similar users</button></div>
		<script type="text/javascript">
			$('.show-similar-users').on('click',function(){
			    if ($(this).find('i').text() == 'arrow_drop_down'){
			    	$(this).find('i').text('arrow_drop_up');
			    	$('#similar-users').fadeIn(100);
			    } else {
			    	$(this).find('i').text('arrow_drop_down');
			    	$('#similar-users').fadeOut(100);
			    }
			});
		</script>
	
	<div id="similar-users" style="display: none; padding: 0 0px 0 72px;">
	<h5>users who are most similar to you based on your feature-rating profile</h5>	
 	<c:forEach items="${su}" var="u">
		<div class="w3-show-inline-block">
			<form action="./GetChartUserURatingServlet" method="post" target="frame-chart3">
		 		<input type="hidden" name="user-u-id" value="${u}">
		 		<div class="w3-bar"><button style="padding: auto flex" class="w3-button w3-small w3-deep-orange" type="submit" onclick="document.getElementById('chart3').style.display='block'">${u}</button></div>
			</form>
		</div>	
	</c:forEach>
 	<div class="container" id="chart3" style="display:none">
		<iframe name="frame-chart3" style="border: none; height: 500px; width: 1064px; position: absolute; left:28px;"></iframe>
	</div>
	</div>
 --%>		
</body>






